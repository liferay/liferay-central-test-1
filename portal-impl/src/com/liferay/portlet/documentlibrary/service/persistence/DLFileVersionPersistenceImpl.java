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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="DLFileVersionPersistenceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    DLFileVersionPersistence
 * @see    DLFileVersionUtil
 */
public class DLFileVersionPersistenceImpl extends BasePersistenceImpl
	implements DLFileVersionPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_F_N = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByF_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_F_N = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByF_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_F_N = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByF_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_F_N_V = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByF_N_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_F_N_V = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByF_N_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(DLFileVersion dlFileVersion) {
		EntityCacheUtil.putResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionImpl.class, dlFileVersion.getPrimaryKey(),
			dlFileVersion);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_N_V,
			new Object[] {
				new Long(dlFileVersion.getFolderId()),
				
			dlFileVersion.getName(), new Double(dlFileVersion.getVersion())
			}, dlFileVersion);
	}

	public void cacheResult(List<DLFileVersion> dlFileVersions) {
		for (DLFileVersion dlFileVersion : dlFileVersions) {
			if (EntityCacheUtil.getResult(
						DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
						DLFileVersionImpl.class, dlFileVersion.getPrimaryKey(),
						this) == null) {
				cacheResult(dlFileVersion);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(DLFileVersionImpl.class.getName());
		EntityCacheUtil.clearCache(DLFileVersionImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public DLFileVersion create(long fileVersionId) {
		DLFileVersion dlFileVersion = new DLFileVersionImpl();

		dlFileVersion.setNew(true);
		dlFileVersion.setPrimaryKey(fileVersionId);

		return dlFileVersion;
	}

	public DLFileVersion remove(long fileVersionId)
		throws NoSuchFileVersionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileVersion dlFileVersion = (DLFileVersion)session.get(DLFileVersionImpl.class,
					new Long(fileVersionId));

			if (dlFileVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No DLFileVersion exists with the primary key " +
						fileVersionId);
				}

				throw new NoSuchFileVersionException(
					"No DLFileVersion exists with the primary key " +
					fileVersionId);
			}

			return remove(dlFileVersion);
		}
		catch (NoSuchFileVersionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileVersion remove(DLFileVersion dlFileVersion)
		throws SystemException {
		for (ModelListener<DLFileVersion> listener : listeners) {
			listener.onBeforeRemove(dlFileVersion);
		}

		dlFileVersion = removeImpl(dlFileVersion);

		for (ModelListener<DLFileVersion> listener : listeners) {
			listener.onAfterRemove(dlFileVersion);
		}

		return dlFileVersion;
	}

	protected DLFileVersion removeImpl(DLFileVersion dlFileVersion)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (dlFileVersion.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(DLFileVersionImpl.class,
						dlFileVersion.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(dlFileVersion);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		DLFileVersionModelImpl dlFileVersionModelImpl = (DLFileVersionModelImpl)dlFileVersion;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_F_N_V,
			new Object[] {
				new Long(dlFileVersionModelImpl.getOriginalFolderId()),
				
			dlFileVersionModelImpl.getOriginalName(),
				new Double(dlFileVersionModelImpl.getOriginalVersion())
			});

		EntityCacheUtil.removeResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionImpl.class, dlFileVersion.getPrimaryKey());

		return dlFileVersion;
	}

	/**
	 * @deprecated Use {@link #update(DLFileVersion, boolean merge)}.
	 */
	public DLFileVersion update(DLFileVersion dlFileVersion)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(DLFileVersion dlFileVersion) method. Use update(DLFileVersion dlFileVersion, boolean merge) instead.");
		}

		return update(dlFileVersion, false);
	}

	public DLFileVersion update(DLFileVersion dlFileVersion, boolean merge)
		throws SystemException {
		boolean isNew = dlFileVersion.isNew();

		for (ModelListener<DLFileVersion> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(dlFileVersion);
			}
			else {
				listener.onBeforeUpdate(dlFileVersion);
			}
		}

		dlFileVersion = updateImpl(dlFileVersion, merge);

		for (ModelListener<DLFileVersion> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(dlFileVersion);
			}
			else {
				listener.onAfterUpdate(dlFileVersion);
			}
		}

		return dlFileVersion;
	}

	public DLFileVersion updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion,
		boolean merge) throws SystemException {
		boolean isNew = dlFileVersion.isNew();

		DLFileVersionModelImpl dlFileVersionModelImpl = (DLFileVersionModelImpl)dlFileVersion;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, dlFileVersion, merge);

			dlFileVersion.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionImpl.class, dlFileVersion.getPrimaryKey(),
			dlFileVersion);

		if (!isNew &&
				((dlFileVersion.getFolderId() != dlFileVersionModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileVersion.getName(),
					dlFileVersionModelImpl.getOriginalName()) ||
				(dlFileVersion.getVersion() != dlFileVersionModelImpl.getOriginalVersion()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_F_N_V,
				new Object[] {
					new Long(dlFileVersionModelImpl.getOriginalFolderId()),
					
				dlFileVersionModelImpl.getOriginalName(),
					new Double(dlFileVersionModelImpl.getOriginalVersion())
				});
		}

		if (isNew ||
				((dlFileVersion.getFolderId() != dlFileVersionModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileVersion.getName(),
					dlFileVersionModelImpl.getOriginalName()) ||
				(dlFileVersion.getVersion() != dlFileVersionModelImpl.getOriginalVersion()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_N_V,
				new Object[] {
					new Long(dlFileVersion.getFolderId()),
					
				dlFileVersion.getName(), new Double(dlFileVersion.getVersion())
				}, dlFileVersion);
		}

		return dlFileVersion;
	}

	public DLFileVersion findByPrimaryKey(long fileVersionId)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByPrimaryKey(fileVersionId);

		if (dlFileVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No DLFileVersion exists with the primary key " +
					fileVersionId);
			}

			throw new NoSuchFileVersionException(
				"No DLFileVersion exists with the primary key " +
				fileVersionId);
		}

		return dlFileVersion;
	}

	public DLFileVersion fetchByPrimaryKey(long fileVersionId)
		throws SystemException {
		DLFileVersion dlFileVersion = (DLFileVersion)EntityCacheUtil.getResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
				DLFileVersionImpl.class, fileVersionId, this);

		if (dlFileVersion == null) {
			Session session = null;

			try {
				session = openSession();

				dlFileVersion = (DLFileVersion)session.get(DLFileVersionImpl.class,
						new Long(fileVersionId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (dlFileVersion != null) {
					cacheResult(dlFileVersion);
				}

				closeSession(session);
			}
		}

		return dlFileVersion;
	}

	public List<DLFileVersion> findByF_N(long folderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(folderId), name };

		List<DLFileVersion> list = (List<DLFileVersion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_F_N,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileVersion FROM DLFileVersion dlFileVersion WHERE ");

				query.append("dlFileVersion.folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("dlFileVersion.name IS NULL");
				}
				else {
					query.append("dlFileVersion.name = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFileVersion.folderId DESC, ");
				query.append("dlFileVersion.name DESC, ");
				query.append("dlFileVersion.version DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileVersion>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_F_N, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileVersion> findByF_N(long folderId, String name, int start,
		int end) throws SystemException {
		return findByF_N(folderId, name, start, end, null);
	}

	public List<DLFileVersion> findByF_N(long folderId, String name, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(folderId),
				
				name,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileVersion> list = (List<DLFileVersion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_F_N,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileVersion FROM DLFileVersion dlFileVersion WHERE ");

				query.append("dlFileVersion.folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("dlFileVersion.name IS NULL");
				}
				else {
					query.append("dlFileVersion.name = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("dlFileVersion.");
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

					query.append("dlFileVersion.folderId DESC, ");
					query.append("dlFileVersion.name DESC, ");
					query.append("dlFileVersion.version DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileVersion>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_F_N,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileVersion findByF_N_First(long folderId, String name,
		OrderByComparator obc)
		throws NoSuchFileVersionException, SystemException {
		List<DLFileVersion> list = findByF_N(folderId, name, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileVersion exists with the key {");

			msg.append("folderId=" + folderId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileVersion findByF_N_Last(long folderId, String name,
		OrderByComparator obc)
		throws NoSuchFileVersionException, SystemException {
		int count = countByF_N(folderId, name);

		List<DLFileVersion> list = findByF_N(folderId, name, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileVersion exists with the key {");

			msg.append("folderId=" + folderId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileVersion[] findByF_N_PrevAndNext(long fileVersionId,
		long folderId, String name, OrderByComparator obc)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		int count = countByF_N(folderId, name);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT dlFileVersion FROM DLFileVersion dlFileVersion WHERE ");

			query.append("dlFileVersion.folderId = ?");

			query.append(" AND ");

			if (name == null) {
				query.append("dlFileVersion.name IS NULL");
			}
			else {
				query.append("dlFileVersion.name = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("dlFileVersion.");
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

				query.append("dlFileVersion.folderId DESC, ");
				query.append("dlFileVersion.name DESC, ");
				query.append("dlFileVersion.version DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(folderId);

			if (name != null) {
				qPos.add(name);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileVersion);

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = (DLFileVersion)objArray[0];
			array[1] = (DLFileVersion)objArray[1];
			array[2] = (DLFileVersion)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileVersion findByF_N_V(long folderId, String name, double version)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByF_N_V(folderId, name, version);

		if (dlFileVersion == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileVersion exists with the key {");

			msg.append("folderId=" + folderId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(", ");
			msg.append("version=" + version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileVersionException(msg.toString());
		}

		return dlFileVersion;
	}

	public DLFileVersion fetchByF_N_V(long folderId, String name, double version)
		throws SystemException {
		return fetchByF_N_V(folderId, name, version, true);
	}

	public DLFileVersion fetchByF_N_V(long folderId, String name,
		double version, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(folderId),
				
				name, new Double(version)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_F_N_V,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileVersion FROM DLFileVersion dlFileVersion WHERE ");

				query.append("dlFileVersion.folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("dlFileVersion.name IS NULL");
				}
				else {
					query.append("dlFileVersion.name = ?");
				}

				query.append(" AND ");

				query.append("dlFileVersion.version = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFileVersion.folderId DESC, ");
				query.append("dlFileVersion.name DESC, ");
				query.append("dlFileVersion.version DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(version);

				List<DLFileVersion> list = q.list();

				result = list;

				DLFileVersion dlFileVersion = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_N_V,
						finderArgs, list);
				}
				else {
					dlFileVersion = list.get(0);

					cacheResult(dlFileVersion);

					if ((dlFileVersion.getFolderId() != folderId) ||
							(dlFileVersion.getName() == null) ||
							!dlFileVersion.getName().equals(name) ||
							(dlFileVersion.getVersion() != version)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_N_V,
							finderArgs, dlFileVersion);
					}
				}

				return dlFileVersion;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_N_V,
						finderArgs, new ArrayList<DLFileVersion>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (DLFileVersion)result;
			}
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

	public List<DLFileVersion> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<DLFileVersion> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<DLFileVersion> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileVersion> list = (List<DLFileVersion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileVersion FROM DLFileVersion dlFileVersion ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("dlFileVersion.");
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

					query.append("dlFileVersion.folderId DESC, ");
					query.append("dlFileVersion.name DESC, ");
					query.append("dlFileVersion.version DESC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileVersion>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByF_N(long folderId, String name)
		throws SystemException {
		for (DLFileVersion dlFileVersion : findByF_N(folderId, name)) {
			remove(dlFileVersion);
		}
	}

	public void removeByF_N_V(long folderId, String name, double version)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = findByF_N_V(folderId, name, version);

		remove(dlFileVersion);
	}

	public void removeAll() throws SystemException {
		for (DLFileVersion dlFileVersion : findAll()) {
			remove(dlFileVersion);
		}
	}

	public int countByF_N(long folderId, String name) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(folderId), name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_F_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(dlFileVersion) ");
				query.append("FROM DLFileVersion dlFileVersion WHERE ");

				query.append("dlFileVersion.folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("dlFileVersion.name IS NULL");
				}
				else {
					query.append("dlFileVersion.name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_F_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByF_N_V(long folderId, String name, double version)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(folderId),
				
				name, new Double(version)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_F_N_V,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(dlFileVersion) ");
				query.append("FROM DLFileVersion dlFileVersion WHERE ");

				query.append("dlFileVersion.folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("dlFileVersion.name IS NULL");
				}
				else {
					query.append("dlFileVersion.name = ?");
				}

				query.append(" AND ");

				query.append("dlFileVersion.version = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(version);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_F_N_V,
					finderArgs, count);

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
						"SELECT COUNT(dlFileVersion) FROM DLFileVersion dlFileVersion");

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

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileVersion")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFileVersion>> listenersList = new ArrayList<ModelListener<DLFileVersion>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFileVersion>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence.impl")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence.impl")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence.impl")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence dlFileShortcutPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence.impl")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence dlFileVersionPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence.impl")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence dlFolderPersistence;
	private static Log _log = LogFactoryUtil.getLog(DLFileVersionPersistenceImpl.class);
}