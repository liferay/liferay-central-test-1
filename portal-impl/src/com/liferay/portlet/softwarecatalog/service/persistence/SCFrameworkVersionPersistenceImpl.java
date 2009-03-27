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

import com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="SCFrameworkVersionPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCFrameworkVersionPersistenceImpl extends BasePersistenceImpl
	implements SCFrameworkVersionPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SCFrameworkVersion.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = SCFrameworkVersion.class.getName() +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_A = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_A = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SCFrameworkVersion scFrameworkVersion) {
		EntityCacheUtil.putResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersion.class, scFrameworkVersion.getPrimaryKey(),
			scFrameworkVersion);
	}

	public void cacheResult(List<SCFrameworkVersion> scFrameworkVersions) {
		for (SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
			if (EntityCacheUtil.getResult(
						SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
						SCFrameworkVersion.class,
						scFrameworkVersion.getPrimaryKey(), this) == null) {
				cacheResult(scFrameworkVersion);
			}
		}
	}

	public SCFrameworkVersion create(long frameworkVersionId) {
		SCFrameworkVersion scFrameworkVersion = new SCFrameworkVersionImpl();

		scFrameworkVersion.setNew(true);
		scFrameworkVersion.setPrimaryKey(frameworkVersionId);

		return scFrameworkVersion;
	}

	public SCFrameworkVersion remove(long frameworkVersionId)
		throws NoSuchFrameworkVersionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion scFrameworkVersion = (SCFrameworkVersion)session.get(SCFrameworkVersionImpl.class,
					new Long(frameworkVersionId));

			if (scFrameworkVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No SCFrameworkVersion exists with the primary key " +
						frameworkVersionId);
				}

				throw new NoSuchFrameworkVersionException(
					"No SCFrameworkVersion exists with the primary key " +
					frameworkVersionId);
			}

			return remove(scFrameworkVersion);
		}
		catch (NoSuchFrameworkVersionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCFrameworkVersion remove(SCFrameworkVersion scFrameworkVersion)
		throws SystemException {
		for (ModelListener<SCFrameworkVersion> listener : listeners) {
			listener.onBeforeRemove(scFrameworkVersion);
		}

		scFrameworkVersion = removeImpl(scFrameworkVersion);

		for (ModelListener<SCFrameworkVersion> listener : listeners) {
			listener.onAfterRemove(scFrameworkVersion);
		}

		return scFrameworkVersion;
	}

	protected SCFrameworkVersion removeImpl(
		SCFrameworkVersion scFrameworkVersion) throws SystemException {
		try {
			clearSCProductVersions.clear(scFrameworkVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}

		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SCFrameworkVersionImpl.class,
						scFrameworkVersion.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(scFrameworkVersion);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SCFrameworkVersionModelImpl scFrameworkVersionModelImpl = (SCFrameworkVersionModelImpl)scFrameworkVersion;

		EntityCacheUtil.removeResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersion.class, scFrameworkVersion.getPrimaryKey());

		return scFrameworkVersion;
	}

	/**
	 * @deprecated Use <code>update(SCFrameworkVersion scFrameworkVersion, boolean merge)</code>.
	 */
	public SCFrameworkVersion update(SCFrameworkVersion scFrameworkVersion)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(SCFrameworkVersion scFrameworkVersion) method. Use update(SCFrameworkVersion scFrameworkVersion, boolean merge) instead.");
		}

		return update(scFrameworkVersion, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        scFrameworkVersion the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when scFrameworkVersion is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public SCFrameworkVersion update(SCFrameworkVersion scFrameworkVersion,
		boolean merge) throws SystemException {
		boolean isNew = scFrameworkVersion.isNew();

		for (ModelListener<SCFrameworkVersion> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(scFrameworkVersion);
			}
			else {
				listener.onBeforeUpdate(scFrameworkVersion);
			}
		}

		scFrameworkVersion = updateImpl(scFrameworkVersion, merge);

		for (ModelListener<SCFrameworkVersion> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(scFrameworkVersion);
			}
			else {
				listener.onAfterUpdate(scFrameworkVersion);
			}
		}

		return scFrameworkVersion;
	}

	public SCFrameworkVersion updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion,
		boolean merge) throws SystemException {
		boolean isNew = scFrameworkVersion.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, scFrameworkVersion, merge);

			scFrameworkVersion.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SCFrameworkVersionModelImpl scFrameworkVersionModelImpl = (SCFrameworkVersionModelImpl)scFrameworkVersion;

		EntityCacheUtil.putResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersion.class, scFrameworkVersion.getPrimaryKey(),
			scFrameworkVersion);

		return scFrameworkVersion;
	}

	public SCFrameworkVersion findByPrimaryKey(long frameworkVersionId)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = fetchByPrimaryKey(frameworkVersionId);

		if (scFrameworkVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SCFrameworkVersion exists with the primary key " +
					frameworkVersionId);
			}

			throw new NoSuchFrameworkVersionException(
				"No SCFrameworkVersion exists with the primary key " +
				frameworkVersionId);
		}

		return scFrameworkVersion;
	}

	public SCFrameworkVersion fetchByPrimaryKey(long frameworkVersionId)
		throws SystemException {
		SCFrameworkVersion result = (SCFrameworkVersion)EntityCacheUtil.getResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
				SCFrameworkVersion.class, frameworkVersionId, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SCFrameworkVersion scFrameworkVersion = (SCFrameworkVersion)session.get(SCFrameworkVersionImpl.class,
						new Long(frameworkVersionId));

				if (scFrameworkVersion != null) {
					cacheResult(scFrameworkVersion);
				}

				return scFrameworkVersion;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (SCFrameworkVersion)result;
		}
	}

	public List<SCFrameworkVersion> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<SCFrameworkVersion> list = q.list();

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				cacheResult(list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SCFrameworkVersion>)result;
		}
	}

	public List<SCFrameworkVersion> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<SCFrameworkVersion> findByGroupId(long groupId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<SCFrameworkVersion> list = (List<SCFrameworkVersion>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				cacheResult(list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SCFrameworkVersion>)result;
		}
	}

	public SCFrameworkVersion findByGroupId_First(long groupId,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		List<SCFrameworkVersion> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCFrameworkVersion exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCFrameworkVersion findByGroupId_Last(long groupId,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		int count = countByGroupId(groupId);

		List<SCFrameworkVersion> list = findByGroupId(groupId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCFrameworkVersion exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCFrameworkVersion[] findByGroupId_PrevAndNext(
		long frameworkVersionId, long groupId, OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("name DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scFrameworkVersion);

			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];

			array[0] = (SCFrameworkVersion)objArray[0];
			array[1] = (SCFrameworkVersion)objArray[1];
			array[2] = (SCFrameworkVersion)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCFrameworkVersion> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<SCFrameworkVersion> list = q.list();

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				cacheResult(list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SCFrameworkVersion>)result;
		}
	}

	public List<SCFrameworkVersion> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<SCFrameworkVersion> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<SCFrameworkVersion> list = (List<SCFrameworkVersion>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				cacheResult(list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SCFrameworkVersion>)result;
		}
	}

	public SCFrameworkVersion findByCompanyId_First(long companyId,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		List<SCFrameworkVersion> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCFrameworkVersion exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCFrameworkVersion findByCompanyId_Last(long companyId,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		int count = countByCompanyId(companyId);

		List<SCFrameworkVersion> list = findByCompanyId(companyId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCFrameworkVersion exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCFrameworkVersion[] findByCompanyId_PrevAndNext(
		long frameworkVersionId, long companyId, OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");

			query.append("companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("name DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scFrameworkVersion);

			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];

			array[0] = (SCFrameworkVersion)objArray[0];
			array[1] = (SCFrameworkVersion)objArray[1];
			array[2] = (SCFrameworkVersion)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCFrameworkVersion> findByG_A(long groupId, boolean active)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(active)
			};

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_A,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("active_ = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

				List<SCFrameworkVersion> list = q.list();

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_A, finderArgs,
					list);

				cacheResult(list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SCFrameworkVersion>)result;
		}
	}

	public List<SCFrameworkVersion> findByG_A(long groupId, boolean active,
		int start, int end) throws SystemException {
		return findByG_A(groupId, active, start, end, null);
	}

	public List<SCFrameworkVersion> findByG_A(long groupId, boolean active,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(active),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_A,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("active_ = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

				List<SCFrameworkVersion> list = (List<SCFrameworkVersion>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_A,
					finderArgs, list);

				cacheResult(list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SCFrameworkVersion>)result;
		}
	}

	public SCFrameworkVersion findByG_A_First(long groupId, boolean active,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		List<SCFrameworkVersion> list = findByG_A(groupId, active, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCFrameworkVersion exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("active=" + active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCFrameworkVersion findByG_A_Last(long groupId, boolean active,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		int count = countByG_A(groupId, active);

		List<SCFrameworkVersion> list = findByG_A(groupId, active, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCFrameworkVersion exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("active=" + active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCFrameworkVersion[] findByG_A_PrevAndNext(long frameworkVersionId,
		long groupId, boolean active, OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);

		int count = countByG_A(groupId, active);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			query.append("active_ = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("name DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scFrameworkVersion);

			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];

			array[0] = (SCFrameworkVersion)objArray[0];
			array[1] = (SCFrameworkVersion)objArray[1];
			array[2] = (SCFrameworkVersion)objArray[2];

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

	public List<SCFrameworkVersion> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SCFrameworkVersion> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SCFrameworkVersion> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name DESC");
				}

				Query q = session.createQuery(query.toString());

				List<SCFrameworkVersion> list = null;

				if (obc == null) {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				cacheResult(list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SCFrameworkVersion>)result;
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (SCFrameworkVersion scFrameworkVersion : findByGroupId(groupId)) {
			remove(scFrameworkVersion);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (SCFrameworkVersion scFrameworkVersion : findByCompanyId(companyId)) {
			remove(scFrameworkVersion);
		}
	}

	public void removeByG_A(long groupId, boolean active)
		throws SystemException {
		for (SCFrameworkVersion scFrameworkVersion : findByG_A(groupId, active)) {
			remove(scFrameworkVersion);
		}
	}

	public void removeAll() throws SystemException {
		for (SCFrameworkVersion scFrameworkVersion : findAll()) {
			remove(scFrameworkVersion);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Object result = FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Object result = FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByG_A(long groupId, boolean active)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(active)
			};

		Object result = FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("active_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_A, finderArgs,
					count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Object result = FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk) throws SystemException {
		return getSCProductVersions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk, int start, int end) throws SystemException {
		return getSCProductVersions(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_SCPRODUCTVERSIONS = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			"SCFrameworkVersi_SCProductVers", "getSCProductVersions",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		Object result = FinderCacheUtil.getResult(FINDER_PATH_GET_SCPRODUCTVERSIONS,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETSCPRODUCTVERSIONS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

					sb.append("SCProductVersion.createDate DESC");
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("SCProductVersion",
					com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> list =
					(List<com.liferay.portlet.softwarecatalog.model.SCProductVersion>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(FINDER_PATH_GET_SCPRODUCTVERSIONS,
					finderArgs, list);

				scProductVersionPersistence.cacheResult(list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<com.liferay.portlet.softwarecatalog.model.SCProductVersion>)result;
		}
	}

	public static final FinderPath FINDER_PATH_GET_SCPRODUCTVERSIONS_SIZE = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			"SCFrameworkVersi_SCProductVers", "getSCProductVersionsSize",
			new String[] { Long.class.getName() });

	public int getSCProductVersionsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Object result = FinderCacheUtil.getResult(FINDER_PATH_GET_SCPRODUCTVERSIONS_SIZE,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETSCPRODUCTVERSIONSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_SCPRODUCTVERSIONS_SIZE,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public static final FinderPath FINDER_PATH_CONTAINS_SCPRODUCTVERSION = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			"SCFrameworkVersi_SCProductVers", "containsSCProductVersion",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsSCProductVersion(long pk, long scProductVersionPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk),
				
				new Long(scProductVersionPK)
			};

		Object result = FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_SCPRODUCTVERSION,
				finderArgs, this);

		if (result == null) {
			try {
				Boolean value = Boolean.valueOf(containsSCProductVersion.contains(
							pk, scProductVersionPK));

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_SCPRODUCTVERSION,
					finderArgs, value);

				return value.booleanValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
		}
		else {
			return ((Boolean)result).booleanValue();
		}
	}

	public boolean containsSCProductVersions(long pk) throws SystemException {
		if (getSCProductVersionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addSCProductVersion(long pk, long scProductVersionPK)
		throws SystemException {
		try {
			addSCProductVersion.add(pk, scProductVersionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void addSCProductVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws SystemException {
		try {
			addSCProductVersion.add(pk, scProductVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void addSCProductVersions(long pk, long[] scProductVersionPKs)
		throws SystemException {
		try {
			for (long scProductVersionPK : scProductVersionPKs) {
				addSCProductVersion.add(pk, scProductVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void addSCProductVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
				addSCProductVersion.add(pk, scProductVersion.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void clearSCProductVersions(long pk) throws SystemException {
		try {
			clearSCProductVersions.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void removeSCProductVersion(long pk, long scProductVersionPK)
		throws SystemException {
		try {
			removeSCProductVersion.remove(pk, scProductVersionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void removeSCProductVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws SystemException {
		try {
			removeSCProductVersion.remove(pk, scProductVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void removeSCProductVersions(long pk, long[] scProductVersionPKs)
		throws SystemException {
		try {
			for (long scProductVersionPK : scProductVersionPKs) {
				removeSCProductVersion.remove(pk, scProductVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void removeSCProductVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
				removeSCProductVersion.remove(pk,
					scProductVersion.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void setSCProductVersions(long pk, long[] scProductVersionPKs)
		throws SystemException {
		try {
			clearSCProductVersions.clear(pk);

			for (long scProductVersionPK : scProductVersionPKs) {
				addSCProductVersion.add(pk, scProductVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void setSCProductVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions)
		throws SystemException {
		try {
			clearSCProductVersions.clear(pk);

			for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
				addSCProductVersion.add(pk, scProductVersion.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("SCFrameworkVersi_SCProductVers");
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SCFrameworkVersion>> listenersList = new ArrayList<ModelListener<SCFrameworkVersion>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SCFrameworkVersion>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsSCProductVersion = new ContainsSCProductVersion(this);

		addSCProductVersion = new AddSCProductVersion(this);
		clearSCProductVersions = new ClearSCProductVersions(this);
		removeSCProductVersion = new RemoveSCProductVersion(this);
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
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	protected ContainsSCProductVersion containsSCProductVersion;
	protected AddSCProductVersion addSCProductVersion;
	protected ClearSCProductVersions clearSCProductVersions;
	protected RemoveSCProductVersion removeSCProductVersion;

	protected class ContainsSCProductVersion {
		protected ContainsSCProductVersion(
			SCFrameworkVersionPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSSCPRODUCTVERSION,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long frameworkVersionId,
			long productVersionId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(frameworkVersionId), new Long(productVersionId)
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

	protected class AddSCProductVersion {
		protected AddSCProductVersion(
			SCFrameworkVersionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO SCFrameworkVersi_SCProductVers (frameworkVersionId, productVersionId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long frameworkVersionId, long productVersionId)
			throws SystemException {
			if (!_persistenceImpl.containsSCProductVersion.contains(
						frameworkVersionId, productVersionId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion>[] scProductVersionListeners =
					scProductVersionPersistence.getListeners();

				for (ModelListener<SCFrameworkVersion> listener : listeners) {
					listener.onBeforeAddAssociation(frameworkVersionId,
						com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
						productVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
					listener.onBeforeAddAssociation(productVersionId,
						SCFrameworkVersion.class.getName(), frameworkVersionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(frameworkVersionId), new Long(productVersionId)
					});

				for (ModelListener<SCFrameworkVersion> listener : listeners) {
					listener.onAfterAddAssociation(frameworkVersionId,
						com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
						productVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
					listener.onAfterAddAssociation(productVersionId,
						SCFrameworkVersion.class.getName(), frameworkVersionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private SCFrameworkVersionPersistenceImpl _persistenceImpl;
	}

	protected class ClearSCProductVersions {
		protected ClearSCProductVersions(
			SCFrameworkVersionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCFrameworkVersi_SCProductVers WHERE frameworkVersionId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long frameworkVersionId) throws SystemException {
			ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion>[] scProductVersionListeners =
				scProductVersionPersistence.getListeners();

			List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions =
				null;

			if ((listeners.length > 0) ||
					(scProductVersionListeners.length > 0)) {
				scProductVersions = getSCProductVersions(frameworkVersionId);

				for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
					for (ModelListener<SCFrameworkVersion> listener : listeners) {
						listener.onBeforeRemoveAssociation(frameworkVersionId,
							com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
							scProductVersion.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
						listener.onBeforeRemoveAssociation(scProductVersion.getPrimaryKey(),
							SCFrameworkVersion.class.getName(),
							frameworkVersionId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(frameworkVersionId) });

			if ((listeners.length > 0) ||
					(scProductVersionListeners.length > 0)) {
				for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
					for (ModelListener<SCFrameworkVersion> listener : listeners) {
						listener.onAfterRemoveAssociation(frameworkVersionId,
							com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
							scProductVersion.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
						listener.onBeforeRemoveAssociation(scProductVersion.getPrimaryKey(),
							SCFrameworkVersion.class.getName(),
							frameworkVersionId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveSCProductVersion {
		protected RemoveSCProductVersion(
			SCFrameworkVersionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCFrameworkVersi_SCProductVers WHERE frameworkVersionId = ? AND productVersionId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long frameworkVersionId, long productVersionId)
			throws SystemException {
			if (_persistenceImpl.containsSCProductVersion.contains(
						frameworkVersionId, productVersionId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion>[] scProductVersionListeners =
					scProductVersionPersistence.getListeners();

				for (ModelListener<SCFrameworkVersion> listener : listeners) {
					listener.onBeforeRemoveAssociation(frameworkVersionId,
						com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
						productVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
					listener.onBeforeRemoveAssociation(productVersionId,
						SCFrameworkVersion.class.getName(), frameworkVersionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(frameworkVersionId), new Long(productVersionId)
					});

				for (ModelListener<SCFrameworkVersion> listener : listeners) {
					listener.onAfterRemoveAssociation(frameworkVersionId,
						com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
						productVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
					listener.onAfterRemoveAssociation(productVersionId,
						SCFrameworkVersion.class.getName(), frameworkVersionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private SCFrameworkVersionPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_GETSCPRODUCTVERSIONS = "SELECT {SCProductVersion.*} FROM SCProductVersion INNER JOIN SCFrameworkVersi_SCProductVers ON (SCFrameworkVersi_SCProductVers.productVersionId = SCProductVersion.productVersionId) WHERE (SCFrameworkVersi_SCProductVers.frameworkVersionId = ?)";
	private static final String _SQL_GETSCPRODUCTVERSIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM SCFrameworkVersi_SCProductVers WHERE frameworkVersionId = ?";
	private static final String _SQL_CONTAINSSCPRODUCTVERSION = "SELECT COUNT(*) AS COUNT_VALUE FROM SCFrameworkVersi_SCProductVers WHERE frameworkVersionId = ? AND productVersionId = ?";
	private static Log _log = LogFactoryUtil.getLog(SCFrameworkVersionPersistenceImpl.class);
}