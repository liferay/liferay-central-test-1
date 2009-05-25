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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.softwarecatalog.NoSuchLicenseException;
import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.model.impl.SCLicenseImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="SCLicensePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCLicensePersistenceImpl extends BasePersistenceImpl
	implements SCLicensePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SCLicenseImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_ACTIVE = new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByActive", new String[] { Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ACTIVE = new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByActive",
			new String[] {
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ACTIVE = new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByActive", new String[] { Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_A_R = new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByA_R",
			new String[] { Boolean.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_A_R = new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByA_R",
			new String[] {
				Boolean.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_A_R = new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByA_R",
			new String[] { Boolean.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(SCLicense scLicense) {
		EntityCacheUtil.putResult(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseImpl.class, scLicense.getPrimaryKey(), scLicense);
	}

	public void cacheResult(List<SCLicense> scLicenses) {
		for (SCLicense scLicense : scLicenses) {
			if (EntityCacheUtil.getResult(
						SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
						SCLicenseImpl.class, scLicense.getPrimaryKey(), this) == null) {
				cacheResult(scLicense);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(SCLicenseImpl.class.getName());
		EntityCacheUtil.clearCache(SCLicenseImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public SCLicense create(long licenseId) {
		SCLicense scLicense = new SCLicenseImpl();

		scLicense.setNew(true);
		scLicense.setPrimaryKey(licenseId);

		return scLicense;
	}

	public SCLicense remove(long licenseId)
		throws NoSuchLicenseException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCLicense scLicense = (SCLicense)session.get(SCLicenseImpl.class,
					new Long(licenseId));

			if (scLicense == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No SCLicense exists with the primary key " +
						licenseId);
				}

				throw new NoSuchLicenseException(
					"No SCLicense exists with the primary key " + licenseId);
			}

			return remove(scLicense);
		}
		catch (NoSuchLicenseException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCLicense remove(SCLicense scLicense) throws SystemException {
		for (ModelListener<SCLicense> listener : listeners) {
			listener.onBeforeRemove(scLicense);
		}

		scLicense = removeImpl(scLicense);

		for (ModelListener<SCLicense> listener : listeners) {
			listener.onAfterRemove(scLicense);
		}

		return scLicense;
	}

	protected SCLicense removeImpl(SCLicense scLicense)
		throws SystemException {
		try {
			clearSCProductEntries.clear(scLicense.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}

		Session session = null;

		try {
			session = openSession();

			if (scLicense.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SCLicenseImpl.class,
						scLicense.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(scLicense);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseImpl.class, scLicense.getPrimaryKey());

		return scLicense;
	}

	/**
	 * @deprecated Use <code>update(SCLicense scLicense, boolean merge)</code>.
	 */
	public SCLicense update(SCLicense scLicense) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(SCLicense scLicense) method. Use update(SCLicense scLicense, boolean merge) instead.");
		}

		return update(scLicense, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        scLicense the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when scLicense is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public SCLicense update(SCLicense scLicense, boolean merge)
		throws SystemException {
		boolean isNew = scLicense.isNew();

		for (ModelListener<SCLicense> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(scLicense);
			}
			else {
				listener.onBeforeUpdate(scLicense);
			}
		}

		scLicense = updateImpl(scLicense, merge);

		for (ModelListener<SCLicense> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(scLicense);
			}
			else {
				listener.onAfterUpdate(scLicense);
			}
		}

		return scLicense;
	}

	public SCLicense updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, scLicense, merge);

			scLicense.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseImpl.class, scLicense.getPrimaryKey(), scLicense);

		return scLicense;
	}

	public SCLicense findByPrimaryKey(long licenseId)
		throws NoSuchLicenseException, SystemException {
		SCLicense scLicense = fetchByPrimaryKey(licenseId);

		if (scLicense == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SCLicense exists with the primary key " +
					licenseId);
			}

			throw new NoSuchLicenseException(
				"No SCLicense exists with the primary key " + licenseId);
		}

		return scLicense;
	}

	public SCLicense fetchByPrimaryKey(long licenseId)
		throws SystemException {
		SCLicense scLicense = (SCLicense)EntityCacheUtil.getResult(SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
				SCLicenseImpl.class, licenseId, this);

		if (scLicense == null) {
			Session session = null;

			try {
				session = openSession();

				scLicense = (SCLicense)session.get(SCLicenseImpl.class,
						new Long(licenseId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (scLicense != null) {
					cacheResult(scLicense);
				}

				closeSession(session);
			}
		}

		return scLicense;
	}

	public List<SCLicense> findByActive(boolean active)
		throws SystemException {
		Object[] finderArgs = new Object[] { Boolean.valueOf(active) };

		List<SCLicense> list = (List<SCLicense>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ACTIVE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT scLicense FROM SCLicense scLicense WHERE ");

				query.append("scLicense.active = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("scLicense.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCLicense>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ACTIVE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SCLicense> findByActive(boolean active, int start, int end)
		throws SystemException {
		return findByActive(active, start, end, null);
	}

	public List<SCLicense> findByActive(boolean active, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				Boolean.valueOf(active),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SCLicense> list = (List<SCLicense>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ACTIVE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT scLicense FROM SCLicense scLicense WHERE ");

				query.append("scLicense.active = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("scLicense.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("scLicense.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				list = (List<SCLicense>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCLicense>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ACTIVE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SCLicense findByActive_First(boolean active, OrderByComparator obc)
		throws NoSuchLicenseException, SystemException {
		List<SCLicense> list = findByActive(active, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCLicense exists with the key {");

			msg.append("active=" + active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLicenseException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCLicense findByActive_Last(boolean active, OrderByComparator obc)
		throws NoSuchLicenseException, SystemException {
		int count = countByActive(active);

		List<SCLicense> list = findByActive(active, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCLicense exists with the key {");

			msg.append("active=" + active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLicenseException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCLicense[] findByActive_PrevAndNext(long licenseId, boolean active,
		OrderByComparator obc) throws NoSuchLicenseException, SystemException {
		SCLicense scLicense = findByPrimaryKey(licenseId);

		int count = countByActive(active);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT scLicense FROM SCLicense scLicense WHERE ");

			query.append("scLicense.active = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("scLicense.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append("ORDER BY ");

				query.append("scLicense.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scLicense);

			SCLicense[] array = new SCLicenseImpl[3];

			array[0] = (SCLicense)objArray[0];
			array[1] = (SCLicense)objArray[1];
			array[2] = (SCLicense)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCLicense> findByA_R(boolean active, boolean recommended)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				Boolean.valueOf(active), Boolean.valueOf(recommended)
			};

		List<SCLicense> list = (List<SCLicense>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_A_R,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT scLicense FROM SCLicense scLicense WHERE ");

				query.append("scLicense.active = ?");

				query.append(" AND ");

				query.append("scLicense.recommended = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("scLicense.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				qPos.add(recommended);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCLicense>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_A_R, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SCLicense> findByA_R(boolean active, boolean recommended,
		int start, int end) throws SystemException {
		return findByA_R(active, recommended, start, end, null);
	}

	public List<SCLicense> findByA_R(boolean active, boolean recommended,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				Boolean.valueOf(active), Boolean.valueOf(recommended),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SCLicense> list = (List<SCLicense>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_A_R,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT scLicense FROM SCLicense scLicense WHERE ");

				query.append("scLicense.active = ?");

				query.append(" AND ");

				query.append("scLicense.recommended = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("scLicense.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("scLicense.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				qPos.add(recommended);

				list = (List<SCLicense>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCLicense>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_A_R,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SCLicense findByA_R_First(boolean active, boolean recommended,
		OrderByComparator obc) throws NoSuchLicenseException, SystemException {
		List<SCLicense> list = findByA_R(active, recommended, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCLicense exists with the key {");

			msg.append("active=" + active);

			msg.append(", ");
			msg.append("recommended=" + recommended);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLicenseException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCLicense findByA_R_Last(boolean active, boolean recommended,
		OrderByComparator obc) throws NoSuchLicenseException, SystemException {
		int count = countByA_R(active, recommended);

		List<SCLicense> list = findByA_R(active, recommended, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCLicense exists with the key {");

			msg.append("active=" + active);

			msg.append(", ");
			msg.append("recommended=" + recommended);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLicenseException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCLicense[] findByA_R_PrevAndNext(long licenseId, boolean active,
		boolean recommended, OrderByComparator obc)
		throws NoSuchLicenseException, SystemException {
		SCLicense scLicense = findByPrimaryKey(licenseId);

		int count = countByA_R(active, recommended);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT scLicense FROM SCLicense scLicense WHERE ");

			query.append("scLicense.active = ?");

			query.append(" AND ");

			query.append("scLicense.recommended = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("scLicense.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append("ORDER BY ");

				query.append("scLicense.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(active);

			qPos.add(recommended);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scLicense);

			SCLicense[] array = new SCLicenseImpl[3];

			array[0] = (SCLicense)objArray[0];
			array[1] = (SCLicense)objArray[1];
			array[2] = (SCLicense)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCLicense> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SCLicense> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SCLicense> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SCLicense> list = (List<SCLicense>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT scLicense FROM SCLicense scLicense ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("scLicense.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("scLicense.name ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<SCLicense>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SCLicense>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCLicense>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByActive(boolean active) throws SystemException {
		for (SCLicense scLicense : findByActive(active)) {
			remove(scLicense);
		}
	}

	public void removeByA_R(boolean active, boolean recommended)
		throws SystemException {
		for (SCLicense scLicense : findByA_R(active, recommended)) {
			remove(scLicense);
		}
	}

	public void removeAll() throws SystemException {
		for (SCLicense scLicense : findAll()) {
			remove(scLicense);
		}
	}

	public int countByActive(boolean active) throws SystemException {
		Object[] finderArgs = new Object[] { Boolean.valueOf(active) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACTIVE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(scLicense) ");
				query.append("FROM SCLicense scLicense WHERE ");

				query.append("scLicense.active = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ACTIVE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByA_R(boolean active, boolean recommended)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				Boolean.valueOf(active), Boolean.valueOf(recommended)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_A_R,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(scLicense) ");
				query.append("FROM SCLicense scLicense WHERE ");

				query.append("scLicense.active = ?");

				query.append(" AND ");

				query.append("scLicense.recommended = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				qPos.add(recommended);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_A_R, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(scLicense) FROM SCLicense scLicense");

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk) throws SystemException {
		return getSCProductEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk, int start, int end) throws SystemException {
		return getSCProductEntries(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_SCPRODUCTENTRIES = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES,
			"SCLicenses_SCProductEntries", "getSCProductEntries",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> list = (List<com.liferay.portlet.softwarecatalog.model.SCProductEntry>)FinderCacheUtil.getResult(FINDER_PATH_GET_SCPRODUCTENTRIES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETSCPRODUCTENTRIES);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

					sb.append("SCProductEntry.modifiedDate DESC, ");
					sb.append("SCProductEntry.name DESC");
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("SCProductEntry",
					com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.softwarecatalog.model.SCProductEntry>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portlet.softwarecatalog.model.SCProductEntry>();
				}

				scProductEntryPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_SCPRODUCTENTRIES,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_SCPRODUCTENTRIES_SIZE = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES,
			"SCLicenses_SCProductEntries", "getSCProductEntriesSize",
			new String[] { Long.class.getName() });

	public int getSCProductEntriesSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_SCPRODUCTENTRIES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETSCPRODUCTENTRIESSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_SCPRODUCTENTRIES_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_SCPRODUCTENTRY = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCLicenseModelImpl.FINDER_CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES,
			"SCLicenses_SCProductEntries", "containsSCProductEntry",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsSCProductEntry(long pk, long scProductEntryPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk),
				
				new Long(scProductEntryPK)
			};

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_SCPRODUCTENTRY,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsSCProductEntry.contains(pk,
							scProductEntryPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_SCPRODUCTENTRY,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsSCProductEntries(long pk) throws SystemException {
		if (getSCProductEntriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addSCProductEntry(long pk, long scProductEntryPK)
		throws SystemException {
		try {
			addSCProductEntry.add(pk, scProductEntryPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void addSCProductEntry(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws SystemException {
		try {
			addSCProductEntry.add(pk, scProductEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void addSCProductEntries(long pk, long[] scProductEntryPKs)
		throws SystemException {
		try {
			for (long scProductEntryPK : scProductEntryPKs) {
				addSCProductEntry.add(pk, scProductEntryPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void addSCProductEntries(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry : scProductEntries) {
				addSCProductEntry.add(pk, scProductEntry.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void clearSCProductEntries(long pk) throws SystemException {
		try {
			clearSCProductEntries.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void removeSCProductEntry(long pk, long scProductEntryPK)
		throws SystemException {
		try {
			removeSCProductEntry.remove(pk, scProductEntryPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void removeSCProductEntry(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws SystemException {
		try {
			removeSCProductEntry.remove(pk, scProductEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void removeSCProductEntries(long pk, long[] scProductEntryPKs)
		throws SystemException {
		try {
			for (long scProductEntryPK : scProductEntryPKs) {
				removeSCProductEntry.remove(pk, scProductEntryPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void removeSCProductEntries(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry : scProductEntries) {
				removeSCProductEntry.remove(pk, scProductEntry.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void setSCProductEntries(long pk, long[] scProductEntryPKs)
		throws SystemException {
		try {
			clearSCProductEntries.clear(pk);

			for (long scProductEntryPK : scProductEntryPKs) {
				addSCProductEntry.add(pk, scProductEntryPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void setSCProductEntries(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws SystemException {
		try {
			clearSCProductEntries.clear(pk);

			for (com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry : scProductEntries) {
				addSCProductEntry.add(pk, scProductEntry.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCLicense")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SCLicense>> listenersList = new ArrayList<ModelListener<SCLicense>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SCLicense>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsSCProductEntry = new ContainsSCProductEntry(this);

		addSCProductEntry = new AddSCProductEntry(this);
		clearSCProductEntries = new ClearSCProductEntries(this);
		removeSCProductEntry = new RemoveSCProductEntry(this);
	}

	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCLicensePersistence.impl")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCLicensePersistence scLicensePersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence.impl")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence scFrameworkVersionPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence.impl")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence scProductEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductScreenshotPersistence.impl")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCProductScreenshotPersistence scProductScreenshotPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionPersistence.impl")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionPersistence scProductVersionPersistence;
	protected ContainsSCProductEntry containsSCProductEntry;
	protected AddSCProductEntry addSCProductEntry;
	protected ClearSCProductEntries clearSCProductEntries;
	protected RemoveSCProductEntry removeSCProductEntry;

	protected class ContainsSCProductEntry {
		protected ContainsSCProductEntry(
			SCLicensePersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSSCPRODUCTENTRY,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long licenseId, long productEntryId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(licenseId), new Long(productEntryId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddSCProductEntry {
		protected AddSCProductEntry(SCLicensePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO SCLicenses_SCProductEntries (licenseId, productEntryId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long licenseId, long productEntryId)
			throws SystemException {
			if (!_persistenceImpl.containsSCProductEntry.contains(licenseId,
						productEntryId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductEntry>[] scProductEntryListeners =
					scProductEntryPersistence.getListeners();

				for (ModelListener<SCLicense> listener : listeners) {
					listener.onBeforeAddAssociation(licenseId,
						com.liferay.portlet.softwarecatalog.model.SCProductEntry.class.getName(),
						productEntryId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductEntry> listener : scProductEntryListeners) {
					listener.onBeforeAddAssociation(productEntryId,
						SCLicense.class.getName(), licenseId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(licenseId), new Long(productEntryId)
					});

				for (ModelListener<SCLicense> listener : listeners) {
					listener.onAfterAddAssociation(licenseId,
						com.liferay.portlet.softwarecatalog.model.SCProductEntry.class.getName(),
						productEntryId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductEntry> listener : scProductEntryListeners) {
					listener.onAfterAddAssociation(productEntryId,
						SCLicense.class.getName(), licenseId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private SCLicensePersistenceImpl _persistenceImpl;
	}

	protected class ClearSCProductEntries {
		protected ClearSCProductEntries(
			SCLicensePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCLicenses_SCProductEntries WHERE licenseId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long licenseId) throws SystemException {
			ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductEntry>[] scProductEntryListeners =
				scProductEntryPersistence.getListeners();

			List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries =
				null;

			if ((listeners.length > 0) || (scProductEntryListeners.length > 0)) {
				scProductEntries = getSCProductEntries(licenseId);

				for (com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry : scProductEntries) {
					for (ModelListener<SCLicense> listener : listeners) {
						listener.onBeforeRemoveAssociation(licenseId,
							com.liferay.portlet.softwarecatalog.model.SCProductEntry.class.getName(),
							scProductEntry.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductEntry> listener : scProductEntryListeners) {
						listener.onBeforeRemoveAssociation(scProductEntry.getPrimaryKey(),
							SCLicense.class.getName(), licenseId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(licenseId) });

			if ((listeners.length > 0) || (scProductEntryListeners.length > 0)) {
				for (com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry : scProductEntries) {
					for (ModelListener<SCLicense> listener : listeners) {
						listener.onAfterRemoveAssociation(licenseId,
							com.liferay.portlet.softwarecatalog.model.SCProductEntry.class.getName(),
							scProductEntry.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductEntry> listener : scProductEntryListeners) {
						listener.onBeforeRemoveAssociation(scProductEntry.getPrimaryKey(),
							SCLicense.class.getName(), licenseId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveSCProductEntry {
		protected RemoveSCProductEntry(SCLicensePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCLicenses_SCProductEntries WHERE licenseId = ? AND productEntryId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long licenseId, long productEntryId)
			throws SystemException {
			if (_persistenceImpl.containsSCProductEntry.contains(licenseId,
						productEntryId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductEntry>[] scProductEntryListeners =
					scProductEntryPersistence.getListeners();

				for (ModelListener<SCLicense> listener : listeners) {
					listener.onBeforeRemoveAssociation(licenseId,
						com.liferay.portlet.softwarecatalog.model.SCProductEntry.class.getName(),
						productEntryId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductEntry> listener : scProductEntryListeners) {
					listener.onBeforeRemoveAssociation(productEntryId,
						SCLicense.class.getName(), licenseId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(licenseId), new Long(productEntryId)
					});

				for (ModelListener<SCLicense> listener : listeners) {
					listener.onAfterRemoveAssociation(licenseId,
						com.liferay.portlet.softwarecatalog.model.SCProductEntry.class.getName(),
						productEntryId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductEntry> listener : scProductEntryListeners) {
					listener.onAfterRemoveAssociation(productEntryId,
						SCLicense.class.getName(), licenseId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private SCLicensePersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_GETSCPRODUCTENTRIES = "SELECT {SCProductEntry.*} FROM SCProductEntry INNER JOIN SCLicenses_SCProductEntries ON (SCLicenses_SCProductEntries.productEntryId = SCProductEntry.productEntryId) WHERE (SCLicenses_SCProductEntries.licenseId = ?)";
	private static final String _SQL_GETSCPRODUCTENTRIESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM SCLicenses_SCProductEntries WHERE licenseId = ?";
	private static final String _SQL_CONTAINSSCPRODUCTENTRY = "SELECT COUNT(*) AS COUNT_VALUE FROM SCLicenses_SCProductEntries WHERE licenseId = ? AND productEntryId = ?";
	private static Log _log = LogFactoryUtil.getLog(SCLicensePersistenceImpl.class);
}