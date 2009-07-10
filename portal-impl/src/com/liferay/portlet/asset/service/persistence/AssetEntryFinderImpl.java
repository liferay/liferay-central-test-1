/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.impl.AssetEntryImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="AssetEntryFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class AssetEntryFinderImpl
	extends BasePersistenceImpl implements AssetEntryFinder {

	public static String FIND_BY_AND_CATEGORY_IDS =
		AssetEntryFinder.class.getName() + ".findByAndCategoryIds";

	public static String FIND_BY_AND_TAG_IDS =
		AssetEntryFinder.class.getName() + ".findByAndTagIds";

	public int countEntries(AssetEntryQuery entryQuery) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = buildAssetQuerySQL(entryQuery, true, session);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetEntry> findEntries(AssetEntryQuery entryQuery)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = buildAssetQuerySQL(entryQuery, false, session);

			return (List<AssetEntry>)QueryUtil.list(
				q, getDialect(), entryQuery.getStart(), entryQuery.getEnd());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void buildAllCategoriesSQL(long[] categoryIds, StringBuilder sb) {
		sb.append(" AND AssetEntry.entryId IN (");

		for (int i = 0; i < categoryIds.length; i++) {
			sb.append(CustomSQLUtil.get(FIND_BY_AND_CATEGORY_IDS));

			if ((i + 1) < categoryIds.length) {
				sb.append(" AND AssetEntry.entryId IN (");
			}
		}

		for (int i = 0; i < categoryIds.length; i++) {
			if ((i + 1) < categoryIds.length) {
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);
	}

	protected void buildAllTagsSQL(long[] tagIds, StringBuilder sb) {
		sb.append(" AND AssetEntry.entryId IN (");

		for (int i = 0; i < tagIds.length; i++) {
			sb.append(CustomSQLUtil.get(FIND_BY_AND_TAG_IDS));

			if ((i + 1) < tagIds.length) {
				sb.append(" AND AssetEntry.entryId IN (");
			}
		}

		for (int i = 0; i < tagIds.length; i++) {
			if ((i + 1) < tagIds.length) {
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);
	}

	protected SQLQuery buildAssetQuerySQL(
		AssetEntryQuery entryQuery, boolean count, Session session) {

		StringBuilder sb = new StringBuilder();

		if (count) {
			sb.append("SELECT COUNT(AssetEntry.entryId) AS COUNT_VALUE ");
		}
		else {
			sb.append("SELECT DISTINCT {AssetEntry.*} ");
		}

		sb.append("FROM AssetEntry ");

		if (entryQuery.getAnyTagIds().length > 0) {
			sb.append("INNER JOIN ");
			sb.append("AssetEntries_AssetTags ON ");
			sb.append("(AssetEntries_AssetTags.entryId = ");
			sb.append("AssetEntry.entryId) ");
			sb.append("INNER JOIN ");
			sb.append("AssetTag ON ");
			sb.append("(AssetTag.tagId = AssetEntries_AssetTags.tagId) ");
		}

		if (entryQuery.getAnyCategoryIds().length > 0) {
			sb.append("INNER JOIN ");
			sb.append("AssetEntries_AssetCategories ON ");
			sb.append("(AssetEntries_AssetCategories.entryId = ");
			sb.append("AssetEntry.entryId) ");
			sb.append("INNER JOIN ");
			sb.append("AssetCategory ON ");
			sb.append("(AssetCategory.categoryId = ");
			sb.append("AssetEntries_AssetCategories.categoryId) ");
		}

		sb.append("WHERE (1 = 1)");

		if (entryQuery.isVisible() != null) {
			sb.append(" AND (visible = ?)");
		}

		if (entryQuery.isExcludeZeroViewCount()) {
			sb.append(" AND (AssetEntry.viewCount > 0)");
		}

		// Category conditions

		if (entryQuery.getAllCategoryIds().length > 0) {
			buildAllCategoriesSQL(entryQuery.getAllCategoryIds(), sb);
		}

		if (entryQuery.getAnyCategoryIds().length > 0) {
			sb.append(" AND (");
			sb.append(getCategoryIds(
				entryQuery.getAnyCategoryIds(), StringPool.EQUAL));
			sb.append(") ");
		}

		if (entryQuery.getNotAllCategoryIds().length > 0) {
			buildNotAnyCategoriesSQL(entryQuery.getNotAllCategoryIds(), sb);
		}

		if (entryQuery.getNotAnyCategoryIds().length > 0) {
			sb.append(" AND (");
			sb.append(getNotCategoryIds(entryQuery.getNotAnyCategoryIds()));
			sb.append(") ");
		}

		// Tag conditions

		if (entryQuery.getAllTagIds().length > 0) {
			buildAllTagsSQL(entryQuery.getAllTagIds(), sb);
		}

		if (entryQuery.getAnyTagIds().length > 0) {
			sb.append(" AND (");
			sb.append(getTagIds(entryQuery.getAnyTagIds(), StringPool.EQUAL));
			sb.append(") ");
		}

		if (entryQuery.getNotAllTagIds().length > 0) {
			buildNotAnyTagsSQL(entryQuery.getNotAllTagIds(), sb);
		}

		if (entryQuery.getNotAnyTagIds().length > 0) {
			sb.append(" AND (");
			sb.append(getNotTagIds(entryQuery.getNotAnyTagIds()));
			sb.append(") ");
		}

		// Other conditions

		sb.append("[$DATES$]");

		if (entryQuery.getGroupId() > 0) {
			sb.append(" AND (AssetEntry.groupId = ?)");
		}

		sb.append(getClassNameIds(entryQuery.getClassNameIds()));

		if (!count) {
			sb.append(" ORDER BY AssetEntry.");
			sb.append(entryQuery.getOrderByCol1());
			sb.append(StringPool.SPACE);
			sb.append(entryQuery.getOrderByType1());

			if (Validator.isNotNull(entryQuery.getOrderByCol2()) &&
				!entryQuery.getOrderByCol1().equals(
					entryQuery.getOrderByCol2())) {

				sb.append(", AssetEntry.");
				sb.append(entryQuery.getOrderByCol2());
				sb.append(StringPool.SPACE);
				sb.append(entryQuery.getOrderByType2());
			}
		}

		String sql = sb.toString();

		sql = getDates(
			sql, entryQuery.getPublishDate(), entryQuery.getExpirationDate());

		SQLQuery q = session.createSQLQuery(sql);

		if (count) {
			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);
		}
		else {
			q.addEntity("AssetEntry", AssetEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (entryQuery.isVisible() != null) {
			qPos.add(entryQuery.isVisible().booleanValue());
		}

		qPos.add(entryQuery.getAllCategoryIds());
		qPos.add(entryQuery.getAnyCategoryIds());
		qPos.add(entryQuery.getNotAllCategoryIds());
		qPos.add(entryQuery.getNotAnyCategoryIds());

		qPos.add(entryQuery.getAllTagIds());
		qPos.add(entryQuery.getAnyTagIds());
		qPos.add(entryQuery.getNotAllTagIds());
		qPos.add(entryQuery.getNotAnyTagIds());

		setDates(
			qPos, entryQuery.getPublishDate(),
			entryQuery.getExpirationDate());

		if (entryQuery.getGroupId() > 0) {
			qPos.add(entryQuery.getGroupId());
		}

		qPos.add(entryQuery.getClassNameIds());

		return q;
	}

	protected void buildNotAnyCategoriesSQL(
		long[] categoryIds, StringBuilder sb) {

		sb.append(" AND (");

		for (int i = 0; i < categoryIds.length; i++) {
			sb.append("AssetEntry.entryId NOT IN (");
			sb.append(CustomSQLUtil.get(FIND_BY_AND_CATEGORY_IDS));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			if ((i + 1) < categoryIds.length) {
				sb.append(" OR ");
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);
	}

	protected void buildNotAnyTagsSQL(long[] tagIds, StringBuilder sb) {
		sb.append(" AND (");

		for (int i = 0; i < tagIds.length; i++) {
			sb.append("AssetEntry.entryId NOT IN (");
			sb.append(CustomSQLUtil.get(FIND_BY_AND_TAG_IDS));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			if ((i + 1) < tagIds.length) {
				sb.append(" OR ");
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);
	}

	protected String getCategoryIds(long[] categoryIds, String operator) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < categoryIds.length; i++) {
			sb.append("AssetCategory.categoryId ");
			sb.append(operator);
			sb.append(" ? ");

			if ((i + 1) != categoryIds.length) {
				sb.append("OR ");
			}
		}

		if (sb.length() == 0) {
			sb.append("(1 = 1)");
		}

		return sb.toString();
	}

	protected String getClassNameIds(long[] classNameIds) {
		StringBuilder sb = new StringBuilder();

		if (classNameIds.length > 0) {
			sb.append(" AND (");

			for (int i = 0; i < classNameIds.length; i++) {
				sb.append("classNameId = ?");

				if (i > 0) {
					sb.append(" AND ");
				}
			}

			sb.append(") ");
		}

		return sb.toString();
	}

	protected String getDates(
		String sql, Date publishDate, Date expirationDate) {

		StringBuilder sb = new StringBuilder();

		if (publishDate != null) {
			sb.append(" AND (publishDate IS NULL OR publishDate < ?)");
		}

		if (expirationDate != null) {
			sb.append(" AND (expirationDate IS NULL OR expirationDate > ?)");
		}

		sql = StringUtil.replace(sql, "[$DATES$]", sb.toString());

		return sql;
	}

	protected String getNotCategoryIds(long[] notCategoryIds) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < notCategoryIds.length; i++) {
			sb.append("AssetEntry.entryId NOT IN (");
			sb.append(CustomSQLUtil.get(FIND_BY_AND_CATEGORY_IDS));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			if ((i + 1) < notCategoryIds.length) {
				sb.append(" AND ");
			}
		}

		return sb.toString();
	}

	protected String getNotTagIds(long[] notTagIds) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < notTagIds.length; i++) {
			sb.append("AssetEntry.entryId NOT IN (");
			sb.append(CustomSQLUtil.get(FIND_BY_AND_TAG_IDS));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			if ((i + 1) < notTagIds.length) {
				sb.append(" AND ");
			}
		}

		return sb.toString();
	}

	protected String getTagIds(long[] tagIds, String operator) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < tagIds.length; i++) {
			sb.append("AssetTag.tagId ");
			sb.append(operator);
			sb.append(" ? ");

			if ((i + 1) != tagIds.length) {
				sb.append("OR ");
			}
		}

		if (sb.length() == 0) {
			sb.append("(1 = 1)");
		}

		return sb.toString();
	}

	protected void setDates(
		QueryPos qPos, Date publishDate, Date expirationDate) {

		if (publishDate != null) {
			Timestamp publishDate_TS = CalendarUtil.getTimestamp(publishDate);

			qPos.add(publishDate_TS);
		}

		if (expirationDate != null) {
			Timestamp expirationDate_TS =
				CalendarUtil.getTimestamp(expirationDate);

			qPos.add(expirationDate_TS);
		}
	}

}