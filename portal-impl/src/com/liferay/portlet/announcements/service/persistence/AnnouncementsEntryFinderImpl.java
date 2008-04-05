/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.announcements.model.impl.AnnouncementsEntryImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsFlagImpl;
import com.liferay.util.cal.CalendarUtil;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="AnnouncementsEntryFinderImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Thiago Moreira
 * @author Raymond Augé
 *
 */
public class AnnouncementsEntryFinderImpl implements AnnouncementsEntryFinder {

	public static String COUNT_BY_HIDDEN =
		AnnouncementsEntryFinder.class.getName() + ".countByHidden";

	public static String COUNT_BY_NOT_HIDDEN =
		AnnouncementsEntryFinder.class.getName() + ".countByNotHidden";

	public static String FIND_BY_DISPLAY_DATE =
		AnnouncementsEntryFinder.class.getName() + ".findByDisplayDate";

	public static String FIND_BY_HIDDEN =
		AnnouncementsEntryFinder.class.getName() + ".findByHidden";

	public static String FIND_BY_NOT_HIDDEN =
		AnnouncementsEntryFinder.class.getName() + ".findByNotHidden";

	public int countByScope(
			long userId, long classNameId, long[] classPKs,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, boolean alert,
			int flagValue)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_HIDDEN);

			if (flagValue == AnnouncementsFlagImpl.NOT_HIDDEN) {
				sql = CustomSQLUtil.get(COUNT_BY_NOT_HIDDEN);
			}

			sql = StringUtil.replace(
				sql, "[$CLASS_PKS$]", getClassPKs(classNameId, classPKs));
			sql = CustomSQLUtil.replaceAndOperator(sql, true);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setClassPKs(qPos, classNameId, classPKs);

			setDates(
				qPos, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute);

			qPos.add(alert);
			qPos.add(userId);
			qPos.add(AnnouncementsFlagImpl.HIDDEN);

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
			HibernateUtil.closeSession(session);
		}
	}

	public int countByScopes(
			long userId, LinkedHashMap<Long, long[]> scopes,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, boolean alert,
			int flagValue)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_HIDDEN);

			if (flagValue == AnnouncementsFlagImpl.NOT_HIDDEN) {
				sql = CustomSQLUtil.get(COUNT_BY_NOT_HIDDEN);
			}

			sql = StringUtil.replace(sql, "[$CLASS_PKS$]", getClassPKs(scopes));
			sql = CustomSQLUtil.replaceAndOperator(sql, true);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setClassPKs(qPos, scopes);

			setDates(
				qPos, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute);

			qPos.add(alert);
			qPos.add(userId);
			qPos.add(AnnouncementsFlagImpl.HIDDEN);

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
			HibernateUtil.closeSession(session);
		}
	}

	public List<AnnouncementsEntry> findByDisplayDate(
			Date displayDateLT, Date displayDateGT)
		throws SystemException {

		Timestamp displayDateLT_TS = CalendarUtil.getTimestamp(displayDateLT);
		Timestamp displayDateGT_TS = CalendarUtil.getTimestamp(displayDateGT);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_DISPLAY_DATE);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AnnouncementsEntry", AnnouncementsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(displayDateGT_TS);
			qPos.add(displayDateLT_TS);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public List<AnnouncementsEntry> findByScope(
			long userId, long classNameId, long[] classPKs,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, boolean alert,
			int flagValue, int begin, int end)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_HIDDEN);

			if (flagValue == AnnouncementsFlagImpl.NOT_HIDDEN) {
				sql = CustomSQLUtil.get(FIND_BY_NOT_HIDDEN);
			}

			sql = StringUtil.replace(
				sql, "[$CLASS_PKS$]", getClassPKs(classNameId, classPKs));
			sql = CustomSQLUtil.replaceAndOperator(sql, true);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AnnouncementsEntry", AnnouncementsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setClassPKs(qPos, classNameId, classPKs);

			setDates(
				qPos, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute);

			qPos.add(alert);
			qPos.add(userId);
			qPos.add(AnnouncementsFlagImpl.HIDDEN);

			return (List<AnnouncementsEntry>)QueryUtil.list(
				q, HibernateUtil.getDialect(), begin, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public List<AnnouncementsEntry> findByScopes(
			long userId, LinkedHashMap<Long, long[]> scopes,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, boolean alert,
			int flagValue, int begin, int end)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_HIDDEN);

			if (flagValue == AnnouncementsFlagImpl.NOT_HIDDEN) {
				sql = CustomSQLUtil.get(FIND_BY_NOT_HIDDEN);
			}

			sql = StringUtil.replace(sql, "[$CLASS_PKS$]", getClassPKs(scopes));
			sql = CustomSQLUtil.replaceAndOperator(sql, true);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AnnouncementsEntry", AnnouncementsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setClassPKs(qPos, scopes);

			setDates(
				qPos, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute);

			qPos.add(alert);
			qPos.add(userId);
			qPos.add(AnnouncementsFlagImpl.HIDDEN);

			return (List<AnnouncementsEntry>)QueryUtil.list(
				q, HibernateUtil.getDialect(), begin, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	protected String getClassPKs(long classNameId, long[] classPKs) {
		StringMaker sm = new StringMaker();

		sm.append("(AnnouncementsEntry.classNameId = ?) AND (");

		for (int i = 0; i < classPKs.length; i++) {
			sm.append("(AnnouncementsEntry.classPK = ?)");

			if ((i + 1) < classPKs.length) {
				sm.append(" OR ");
			}
			else {
				sm.append(")");
			}
		}

		return sm.toString();
	}

	protected String getClassPKs(LinkedHashMap<Long, long[]> scopes) {
		if (scopes == null) {
			return StringPool.BLANK;
		}

		StringMaker sm = new StringMaker();

		Iterator<Map.Entry<Long, long[]>> itr = scopes.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<Long, long[]> entry = itr.next();

			Long classNameId = entry.getKey();
			long[] classPKs = entry.getValue();

			sm.append("(");
			sm.append(getClassPKs(classNameId.longValue(), classPKs));
			sm.append(")");

			if (itr.hasNext()) {
				sm.append(" OR ");
			}
		}

		return sm.toString();
	}

	protected void setClassPKs(
		QueryPos qPos, long classNameId, long[] classPKs) {

		qPos.add(classNameId);

		for (int i = 0; i < classPKs.length; i++) {
			qPos.add(classPKs[i]);
		}
	}

	protected void setClassPKs(
		QueryPos qPos, LinkedHashMap<Long, long[]> scopes) {

		if (scopes == null) {
			return;
		}

		Iterator<Map.Entry<Long, long[]>> itr = scopes.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<Long, long[]> entry = itr.next();

			Long classNameId = entry.getKey();
			long[] classPKs = entry.getValue();

			setClassPKs(qPos, classNameId.longValue(), classPKs);
		}
	}

	protected void setDates(
		QueryPos qPos, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute) {

		Date displayDate = null;

		try {
			displayDate = PortalUtil.getDate(
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, new PortalException());
		}
		catch (PortalException pe) {
			displayDate = new Date();
		}

		Timestamp displayDateTS = CalendarUtil.getTimestamp(displayDate);

		Date expirationDate = null;

		try {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute,
				new PortalException());
		}
		catch (PortalException pe) {
			expirationDate = new Date();
		}

		Timestamp expirationDateTS = CalendarUtil.getTimestamp(expirationDate);

		qPos.add(displayDateTS);
		qPos.add(displayDateTS);
		qPos.add(expirationDateTS);
		qPos.add(expirationDateTS);
	}

}