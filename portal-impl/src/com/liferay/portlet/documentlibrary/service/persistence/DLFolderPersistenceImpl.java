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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="DLFolderPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    DLFolderPersistence
 * @see    DLFolderUtil
 * @generated
 */
public class DLFolderPersistenceImpl extends BasePersistenceImpl
	implements DLFolderPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = DLFolderImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_P = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_P = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_P_N = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_P_N = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_N = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByP_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_P_N = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_N = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(DLFolder dlFolder) {
		EntityCacheUtil.putResult(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderImpl.class, dlFolder.getPrimaryKey(), dlFolder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { dlFolder.getUuid(), new Long(dlFolder.getGroupId()) },
			dlFolder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
			new Object[] {
				new Long(dlFolder.getGroupId()),
				new Long(dlFolder.getParentFolderId()),
				
			dlFolder.getName()
			}, dlFolder);
	}

	public void cacheResult(List<DLFolder> dlFolders) {
		for (DLFolder dlFolder : dlFolders) {
			if (EntityCacheUtil.getResult(
						DLFolderModelImpl.ENTITY_CACHE_ENABLED,
						DLFolderImpl.class, dlFolder.getPrimaryKey(), this) == null) {
				cacheResult(dlFolder);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(DLFolderImpl.class.getName());
		EntityCacheUtil.clearCache(DLFolderImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public DLFolder create(long folderId) {
		DLFolder dlFolder = new DLFolderImpl();

		dlFolder.setNew(true);
		dlFolder.setPrimaryKey(folderId);

		String uuid = PortalUUIDUtil.generate();

		dlFolder.setUuid(uuid);

		return dlFolder;
	}

	public DLFolder remove(long folderId)
		throws NoSuchFolderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFolder dlFolder = (DLFolder)session.get(DLFolderImpl.class,
					new Long(folderId));

			if (dlFolder == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No DLFolder exists with the primary key " +
						folderId);
				}

				throw new NoSuchFolderException(
					"No DLFolder exists with the primary key " + folderId);
			}

			return remove(dlFolder);
		}
		catch (NoSuchFolderException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFolder remove(DLFolder dlFolder) throws SystemException {
		for (ModelListener<DLFolder> listener : listeners) {
			listener.onBeforeRemove(dlFolder);
		}

		dlFolder = removeImpl(dlFolder);

		for (ModelListener<DLFolder> listener : listeners) {
			listener.onAfterRemove(dlFolder);
		}

		return dlFolder;
	}

	protected DLFolder removeImpl(DLFolder dlFolder) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (dlFolder.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(DLFolderImpl.class,
						dlFolder.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(dlFolder);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		DLFolderModelImpl dlFolderModelImpl = (DLFolderModelImpl)dlFolder;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFolderModelImpl.getOriginalUuid(),
				new Long(dlFolderModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_N,
			new Object[] {
				new Long(dlFolderModelImpl.getOriginalGroupId()),
				new Long(dlFolderModelImpl.getOriginalParentFolderId()),
				
			dlFolderModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderImpl.class, dlFolder.getPrimaryKey());

		return dlFolder;
	}

	/**
	 * @deprecated Use {@link #update(DLFolder, boolean merge)}.
	 */
	public DLFolder update(DLFolder dlFolder) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(DLFolder dlFolder) method. Use update(DLFolder dlFolder, boolean merge) instead.");
		}

		return update(dlFolder, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  dlFolder the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when dlFolder is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public DLFolder update(DLFolder dlFolder, boolean merge)
		throws SystemException {
		boolean isNew = dlFolder.isNew();

		for (ModelListener<DLFolder> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(dlFolder);
			}
			else {
				listener.onBeforeUpdate(dlFolder);
			}
		}

		dlFolder = updateImpl(dlFolder, merge);

		for (ModelListener<DLFolder> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(dlFolder);
			}
			else {
				listener.onAfterUpdate(dlFolder);
			}
		}

		return dlFolder;
	}

	public DLFolder updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder,
		boolean merge) throws SystemException {
		boolean isNew = dlFolder.isNew();

		DLFolderModelImpl dlFolderModelImpl = (DLFolderModelImpl)dlFolder;

		if (Validator.isNull(dlFolder.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFolder.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, dlFolder, merge);

			dlFolder.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderImpl.class, dlFolder.getPrimaryKey(), dlFolder);

		if (!isNew &&
				(!Validator.equals(dlFolder.getUuid(),
					dlFolderModelImpl.getOriginalUuid()) ||
				(dlFolder.getGroupId() != dlFolderModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					dlFolderModelImpl.getOriginalUuid(),
					new Long(dlFolderModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(dlFolder.getUuid(),
					dlFolderModelImpl.getOriginalUuid()) ||
				(dlFolder.getGroupId() != dlFolderModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] { dlFolder.getUuid(), new Long(
						dlFolder.getGroupId()) }, dlFolder);
		}

		if (!isNew &&
				((dlFolder.getGroupId() != dlFolderModelImpl.getOriginalGroupId()) ||
				(dlFolder.getParentFolderId() != dlFolderModelImpl.getOriginalParentFolderId()) ||
				!Validator.equals(dlFolder.getName(),
					dlFolderModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_N,
				new Object[] {
					new Long(dlFolderModelImpl.getOriginalGroupId()),
					new Long(dlFolderModelImpl.getOriginalParentFolderId()),
					
				dlFolderModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((dlFolder.getGroupId() != dlFolderModelImpl.getOriginalGroupId()) ||
				(dlFolder.getParentFolderId() != dlFolderModelImpl.getOriginalParentFolderId()) ||
				!Validator.equals(dlFolder.getName(),
					dlFolderModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
				new Object[] {
					new Long(dlFolder.getGroupId()),
					new Long(dlFolder.getParentFolderId()),
					
				dlFolder.getName()
				}, dlFolder);
		}

		return dlFolder;
	}

	public DLFolder findByPrimaryKey(long folderId)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByPrimaryKey(folderId);

		if (dlFolder == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No DLFolder exists with the primary key " +
					folderId);
			}

			throw new NoSuchFolderException(
				"No DLFolder exists with the primary key " + folderId);
		}

		return dlFolder;
	}

	public DLFolder fetchByPrimaryKey(long folderId) throws SystemException {
		DLFolder dlFolder = (DLFolder)EntityCacheUtil.getResult(DLFolderModelImpl.ENTITY_CACHE_ENABLED,
				DLFolderImpl.class, folderId, this);

		if (dlFolder == null) {
			Session session = null;

			try {
				session = openSession();

				dlFolder = (DLFolder)session.get(DLFolderImpl.class,
						new Long(folderId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (dlFolder != null) {
					cacheResult(dlFolder);
				}

				closeSession(session);
			}
		}

		return dlFolder;
	}

	public List<DLFolder> findByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

				if (uuid == null) {
					query.append("dlFolder.uuid IS NULL");
				}
				else {
					query.append("dlFolder.uuid = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFolder.parentFolderId ASC, ");
				query.append("dlFolder.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFolder> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<DLFolder> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

				if (uuid == null) {
					query.append("dlFolder.uuid IS NULL");
				}
				else {
					query.append("dlFolder.uuid = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("dlFolder.");
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

					query.append("dlFolder.parentFolderId ASC, ");
					query.append("dlFolder.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFolder findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		List<DLFolder> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFolder exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFolder findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		int count = countByUuid(uuid);

		List<DLFolder> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFolder exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFolder[] findByUuid_PrevAndNext(long folderId, String uuid,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

			if (uuid == null) {
				query.append("dlFolder.uuid IS NULL");
			}
			else {
				query.append("dlFolder.uuid = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("dlFolder.");
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

				query.append("dlFolder.parentFolderId ASC, ");
				query.append("dlFolder.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, dlFolder);

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = (DLFolder)objArray[0];
			array[1] = (DLFolder)objArray[1];
			array[2] = (DLFolder)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFolder findByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByUUID_G(uuid, groupId);

		if (dlFolder == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFolder exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFolderException(msg.toString());
		}

		return dlFolder;
	}

	public DLFolder fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public DLFolder fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

				if (uuid == null) {
					query.append("dlFolder.uuid IS NULL");
				}
				else {
					query.append("dlFolder.uuid = ?");
				}

				query.append(" AND ");

				query.append("dlFolder.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFolder.parentFolderId ASC, ");
				query.append("dlFolder.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<DLFolder> list = q.list();

				result = list;

				DLFolder dlFolder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					dlFolder = list.get(0);

					cacheResult(dlFolder);

					if ((dlFolder.getUuid() == null) ||
							!dlFolder.getUuid().equals(uuid) ||
							(dlFolder.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, dlFolder);
					}
				}

				return dlFolder;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<DLFolder>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (DLFolder)result;
			}
		}
	}

	public List<DLFolder> findByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

				query.append("dlFolder.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFolder.parentFolderId ASC, ");
				query.append("dlFolder.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFolder> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<DLFolder> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

				query.append("dlFolder.groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("dlFolder.");
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

					query.append("dlFolder.parentFolderId ASC, ");
					query.append("dlFolder.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFolder findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		List<DLFolder> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFolder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFolder findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		int count = countByGroupId(groupId);

		List<DLFolder> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFolder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFolder[] findByGroupId_PrevAndNext(long folderId, long groupId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

			query.append("dlFolder.groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("dlFolder.");
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

				query.append("dlFolder.parentFolderId ASC, ");
				query.append("dlFolder.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, dlFolder);

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = (DLFolder)objArray[0];
			array[1] = (DLFolder)objArray[1];
			array[2] = (DLFolder)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFolder> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

				query.append("dlFolder.companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFolder.parentFolderId ASC, ");
				query.append("dlFolder.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFolder> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<DLFolder> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

				query.append("dlFolder.companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("dlFolder.");
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

					query.append("dlFolder.parentFolderId ASC, ");
					query.append("dlFolder.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFolder findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		List<DLFolder> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFolder exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFolder findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		int count = countByCompanyId(companyId);

		List<DLFolder> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFolder exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFolder[] findByCompanyId_PrevAndNext(long folderId,
		long companyId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

			query.append("dlFolder.companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("dlFolder.");
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

				query.append("dlFolder.parentFolderId ASC, ");
				query.append("dlFolder.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, dlFolder);

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = (DLFolder)objArray[0];
			array[1] = (DLFolder)objArray[1];
			array[2] = (DLFolder)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFolder> findByG_P(long groupId, long parentFolderId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentFolderId)
			};

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

				query.append("dlFolder.groupId = ?");

				query.append(" AND ");

				query.append("dlFolder.parentFolderId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFolder.parentFolderId ASC, ");
				query.append("dlFolder.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_P, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFolder> findByG_P(long groupId, long parentFolderId,
		int start, int end) throws SystemException {
		return findByG_P(groupId, parentFolderId, start, end, null);
	}

	public List<DLFolder> findByG_P(long groupId, long parentFolderId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentFolderId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

				query.append("dlFolder.groupId = ?");

				query.append(" AND ");

				query.append("dlFolder.parentFolderId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("dlFolder.");
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

					query.append("dlFolder.parentFolderId ASC, ");
					query.append("dlFolder.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFolder findByG_P_First(long groupId, long parentFolderId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		List<DLFolder> list = findByG_P(groupId, parentFolderId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFolder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("parentFolderId=" + parentFolderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFolder findByG_P_Last(long groupId, long parentFolderId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		int count = countByG_P(groupId, parentFolderId);

		List<DLFolder> list = findByG_P(groupId, parentFolderId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFolder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("parentFolderId=" + parentFolderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFolder[] findByG_P_PrevAndNext(long folderId, long groupId,
		long parentFolderId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		int count = countByG_P(groupId, parentFolderId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

			query.append("dlFolder.groupId = ?");

			query.append(" AND ");

			query.append("dlFolder.parentFolderId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("dlFolder.");
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

				query.append("dlFolder.parentFolderId ASC, ");
				query.append("dlFolder.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentFolderId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, dlFolder);

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = (DLFolder)objArray[0];
			array[1] = (DLFolder)objArray[1];
			array[2] = (DLFolder)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFolder> findByP_N(long parentFolderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(parentFolderId), name };

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_P_N,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

				query.append("dlFolder.parentFolderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("dlFolder.name IS NULL");
				}
				else {
					query.append("dlFolder.name = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFolder.parentFolderId ASC, ");
				query.append("dlFolder.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentFolderId);

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
					list = new ArrayList<DLFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_P_N, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFolder> findByP_N(long parentFolderId, String name,
		int start, int end) throws SystemException {
		return findByP_N(parentFolderId, name, start, end, null);
	}

	public List<DLFolder> findByP_N(long parentFolderId, String name,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(parentFolderId),
				
				name,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_P_N,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

				query.append("dlFolder.parentFolderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("dlFolder.name IS NULL");
				}
				else {
					query.append("dlFolder.name = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("dlFolder.");
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

					query.append("dlFolder.parentFolderId ASC, ");
					query.append("dlFolder.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentFolderId);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<DLFolder>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_P_N,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFolder findByP_N_First(long parentFolderId, String name,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		List<DLFolder> list = findByP_N(parentFolderId, name, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFolder exists with the key {");

			msg.append("parentFolderId=" + parentFolderId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFolder findByP_N_Last(long parentFolderId, String name,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		int count = countByP_N(parentFolderId, name);

		List<DLFolder> list = findByP_N(parentFolderId, name, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFolder exists with the key {");

			msg.append("parentFolderId=" + parentFolderId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFolder[] findByP_N_PrevAndNext(long folderId, long parentFolderId,
		String name, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);

		int count = countByP_N(parentFolderId, name);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

			query.append("dlFolder.parentFolderId = ?");

			query.append(" AND ");

			if (name == null) {
				query.append("dlFolder.name IS NULL");
			}
			else {
				query.append("dlFolder.name = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("dlFolder.");
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

				query.append("dlFolder.parentFolderId ASC, ");
				query.append("dlFolder.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(parentFolderId);

			if (name != null) {
				qPos.add(name);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, dlFolder);

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = (DLFolder)objArray[0];
			array[1] = (DLFolder)objArray[1];
			array[2] = (DLFolder)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFolder findByG_P_N(long groupId, long parentFolderId, String name)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByG_P_N(groupId, parentFolderId, name);

		if (dlFolder == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFolder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("parentFolderId=" + parentFolderId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFolderException(msg.toString());
		}

		return dlFolder;
	}

	public DLFolder fetchByG_P_N(long groupId, long parentFolderId, String name)
		throws SystemException {
		return fetchByG_P_N(groupId, parentFolderId, name, true);
	}

	public DLFolder fetchByG_P_N(long groupId, long parentFolderId,
		String name, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentFolderId),
				
				name
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_P_N,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT dlFolder FROM DLFolder dlFolder WHERE ");

				query.append("dlFolder.groupId = ?");

				query.append(" AND ");

				query.append("dlFolder.parentFolderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("dlFolder.name IS NULL");
				}
				else {
					query.append("dlFolder.name = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFolder.parentFolderId ASC, ");
				query.append("dlFolder.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				if (name != null) {
					qPos.add(name);
				}

				List<DLFolder> list = q.list();

				result = list;

				DLFolder dlFolder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
						finderArgs, list);
				}
				else {
					dlFolder = list.get(0);

					cacheResult(dlFolder);

					if ((dlFolder.getGroupId() != groupId) ||
							(dlFolder.getParentFolderId() != parentFolderId) ||
							(dlFolder.getName() == null) ||
							!dlFolder.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
							finderArgs, dlFolder);
					}
				}

				return dlFolder;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
						finderArgs, new ArrayList<DLFolder>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (DLFolder)result;
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

	public List<DLFolder> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<DLFolder> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<DLFolder> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFolder> list = (List<DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT dlFolder FROM DLFolder dlFolder ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("dlFolder.");
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

					query.append("dlFolder.parentFolderId ASC, ");
					query.append("dlFolder.name ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<DLFolder>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFolder>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (DLFolder dlFolder : findByUuid(uuid)) {
			remove(dlFolder);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByUUID_G(uuid, groupId);

		remove(dlFolder);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (DLFolder dlFolder : findByGroupId(groupId)) {
			remove(dlFolder);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (DLFolder dlFolder : findByCompanyId(companyId)) {
			remove(dlFolder);
		}
	}

	public void removeByG_P(long groupId, long parentFolderId)
		throws SystemException {
		for (DLFolder dlFolder : findByG_P(groupId, parentFolderId)) {
			remove(dlFolder);
		}
	}

	public void removeByP_N(long parentFolderId, String name)
		throws SystemException {
		for (DLFolder dlFolder : findByP_N(parentFolderId, name)) {
			remove(dlFolder);
		}
	}

	public void removeByG_P_N(long groupId, long parentFolderId, String name)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByG_P_N(groupId, parentFolderId, name);

		remove(dlFolder);
	}

	public void removeAll() throws SystemException {
		for (DLFolder dlFolder : findAll()) {
			remove(dlFolder);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(dlFolder) ");
				query.append("FROM DLFolder dlFolder WHERE ");

				if (uuid == null) {
					query.append("dlFolder.uuid IS NULL");
				}
				else {
					query.append("dlFolder.uuid = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(dlFolder) ");
				query.append("FROM DLFolder dlFolder WHERE ");

				if (uuid == null) {
					query.append("dlFolder.uuid IS NULL");
				}
				else {
					query.append("dlFolder.uuid = ?");
				}

				query.append(" AND ");

				query.append("dlFolder.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(dlFolder) ");
				query.append("FROM DLFolder dlFolder WHERE ");

				query.append("dlFolder.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(dlFolder) ");
				query.append("FROM DLFolder dlFolder WHERE ");

				query.append("dlFolder.companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_P(long groupId, long parentFolderId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentFolderId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(dlFolder) ");
				query.append("FROM DLFolder dlFolder WHERE ");

				query.append("dlFolder.groupId = ?");

				query.append(" AND ");

				query.append("dlFolder.parentFolderId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByP_N(long parentFolderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(parentFolderId), name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(dlFolder) ");
				query.append("FROM DLFolder dlFolder WHERE ");

				query.append("dlFolder.parentFolderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("dlFolder.name IS NULL");
				}
				else {
					query.append("dlFolder.name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentFolderId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_P_N(long groupId, long parentFolderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentFolderId),
				
				name
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(dlFolder) ");
				query.append("FROM DLFolder dlFolder WHERE ");

				query.append("dlFolder.groupId = ?");

				query.append(" AND ");

				query.append("dlFolder.parentFolderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("dlFolder.name IS NULL");
				}
				else {
					query.append("dlFolder.name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_N,
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
						"SELECT COUNT(dlFolder) FROM DLFolder dlFolder");

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
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFolder")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFolder>> listenersList = new ArrayList<ModelListener<DLFolder>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFolder>)Class.forName(
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
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence.impl")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	private static Log _log = LogFactoryUtil.getLog(DLFolderPersistenceImpl.class);
}