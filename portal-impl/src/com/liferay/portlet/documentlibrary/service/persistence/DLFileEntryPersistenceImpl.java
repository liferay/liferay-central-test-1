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

import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="DLFileEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntryPersistence
 * @see       DLFileEntryUtil
 * @generated
 */
public class DLFileEntryPersistenceImpl extends BasePersistenceImpl
	implements DLFileEntryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_U = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F_N = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_N = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_F_T = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_F_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_F_T = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_F_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_T = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_F_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(DLFileEntry dlFileEntry) {
		EntityCacheUtil.putResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryImpl.class, dlFileEntry.getPrimaryKey(), dlFileEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFileEntry.getUuid(), new Long(dlFileEntry.getGroupId())
			}, dlFileEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
			new Object[] {
				new Long(dlFileEntry.getGroupId()),
				new Long(dlFileEntry.getFolderId()),
				
			dlFileEntry.getName()
			}, dlFileEntry);
	}

	public void cacheResult(List<DLFileEntry> dlFileEntries) {
		for (DLFileEntry dlFileEntry : dlFileEntries) {
			if (EntityCacheUtil.getResult(
						DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
						DLFileEntryImpl.class, dlFileEntry.getPrimaryKey(), this) == null) {
				cacheResult(dlFileEntry);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(DLFileEntryImpl.class.getName());
		EntityCacheUtil.clearCache(DLFileEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public DLFileEntry create(long fileEntryId) {
		DLFileEntry dlFileEntry = new DLFileEntryImpl();

		dlFileEntry.setNew(true);
		dlFileEntry.setPrimaryKey(fileEntryId);

		String uuid = PortalUUIDUtil.generate();

		dlFileEntry.setUuid(uuid);

		return dlFileEntry;
	}

	public DLFileEntry remove(long fileEntryId)
		throws NoSuchFileEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileEntry dlFileEntry = (DLFileEntry)session.get(DLFileEntryImpl.class,
					new Long(fileEntryId));

			if (dlFileEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No DLFileEntry exists with the primary key " +
						fileEntryId);
				}

				throw new NoSuchFileEntryException(
					"No DLFileEntry exists with the primary key " +
					fileEntryId);
			}

			return remove(dlFileEntry);
		}
		catch (NoSuchFileEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileEntry remove(DLFileEntry dlFileEntry)
		throws SystemException {
		for (ModelListener<DLFileEntry> listener : listeners) {
			listener.onBeforeRemove(dlFileEntry);
		}

		dlFileEntry = removeImpl(dlFileEntry);

		for (ModelListener<DLFileEntry> listener : listeners) {
			listener.onAfterRemove(dlFileEntry);
		}

		return dlFileEntry;
	}

	protected DLFileEntry removeImpl(DLFileEntry dlFileEntry)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (dlFileEntry.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(DLFileEntryImpl.class,
						dlFileEntry.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(dlFileEntry);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		DLFileEntryModelImpl dlFileEntryModelImpl = (DLFileEntryModelImpl)dlFileEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFileEntryModelImpl.getOriginalUuid(),
				new Long(dlFileEntryModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_N,
			new Object[] {
				new Long(dlFileEntryModelImpl.getOriginalGroupId()),
				new Long(dlFileEntryModelImpl.getOriginalFolderId()),
				
			dlFileEntryModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryImpl.class, dlFileEntry.getPrimaryKey());

		return dlFileEntry;
	}

	/**
	 * @deprecated Use {@link #update(DLFileEntry, boolean merge)}.
	 */
	public DLFileEntry update(DLFileEntry dlFileEntry)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(DLFileEntry dlFileEntry) method. Use update(DLFileEntry dlFileEntry, boolean merge) instead.");
		}

		return update(dlFileEntry, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  dlFileEntry the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when dlFileEntry is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public DLFileEntry update(DLFileEntry dlFileEntry, boolean merge)
		throws SystemException {
		boolean isNew = dlFileEntry.isNew();

		for (ModelListener<DLFileEntry> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(dlFileEntry);
			}
			else {
				listener.onBeforeUpdate(dlFileEntry);
			}
		}

		dlFileEntry = updateImpl(dlFileEntry, merge);

		for (ModelListener<DLFileEntry> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(dlFileEntry);
			}
			else {
				listener.onAfterUpdate(dlFileEntry);
			}
		}

		return dlFileEntry;
	}

	public DLFileEntry updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry,
		boolean merge) throws SystemException {
		boolean isNew = dlFileEntry.isNew();

		DLFileEntryModelImpl dlFileEntryModelImpl = (DLFileEntryModelImpl)dlFileEntry;

		if (Validator.isNull(dlFileEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, dlFileEntry, merge);

			dlFileEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryImpl.class, dlFileEntry.getPrimaryKey(), dlFileEntry);

		if (!isNew &&
				(!Validator.equals(dlFileEntry.getUuid(),
					dlFileEntryModelImpl.getOriginalUuid()) ||
				(dlFileEntry.getGroupId() != dlFileEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					dlFileEntryModelImpl.getOriginalUuid(),
					new Long(dlFileEntryModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(dlFileEntry.getUuid(),
					dlFileEntryModelImpl.getOriginalUuid()) ||
				(dlFileEntry.getGroupId() != dlFileEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					dlFileEntry.getUuid(), new Long(dlFileEntry.getGroupId())
				}, dlFileEntry);
		}

		if (!isNew &&
				((dlFileEntry.getGroupId() != dlFileEntryModelImpl.getOriginalGroupId()) ||
				(dlFileEntry.getFolderId() != dlFileEntryModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileEntry.getName(),
					dlFileEntryModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_N,
				new Object[] {
					new Long(dlFileEntryModelImpl.getOriginalGroupId()),
					new Long(dlFileEntryModelImpl.getOriginalFolderId()),
					
				dlFileEntryModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((dlFileEntry.getGroupId() != dlFileEntryModelImpl.getOriginalGroupId()) ||
				(dlFileEntry.getFolderId() != dlFileEntryModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileEntry.getName(),
					dlFileEntryModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
				new Object[] {
					new Long(dlFileEntry.getGroupId()),
					new Long(dlFileEntry.getFolderId()),
					
				dlFileEntry.getName()
				}, dlFileEntry);
		}

		return dlFileEntry;
	}

	public DLFileEntry findByPrimaryKey(long fileEntryId)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByPrimaryKey(fileEntryId);

		if (dlFileEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No DLFileEntry exists with the primary key " +
					fileEntryId);
			}

			throw new NoSuchFileEntryException(
				"No DLFileEntry exists with the primary key " + fileEntryId);
		}

		return dlFileEntry;
	}

	public DLFileEntry fetchByPrimaryKey(long fileEntryId)
		throws SystemException {
		DLFileEntry dlFileEntry = (DLFileEntry)EntityCacheUtil.getResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
				DLFileEntryImpl.class, fileEntryId, this);

		if (dlFileEntry == null) {
			Session session = null;

			try {
				session = openSession();

				dlFileEntry = (DLFileEntry)session.get(DLFileEntryImpl.class,
						new Long(fileEntryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (dlFileEntry != null) {
					cacheResult(dlFileEntry);
				}

				closeSession(session);
			}
		}

		return dlFileEntry;
	}

	public List<DLFileEntry> findByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

				if (uuid == null) {
					query.append("dlFileEntry.uuid IS NULL");
				}
				else {
					query.append("dlFileEntry.uuid = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFileEntry.folderId ASC, ");
				query.append("dlFileEntry.name ASC");

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
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileEntry> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<DLFileEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

				if (uuid == null) {
					query.append("dlFileEntry.uuid IS NULL");
				}
				else {
					query.append("dlFileEntry.uuid = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("dlFileEntry.");
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

					query.append("dlFileEntry.folderId ASC, ");
					query.append("dlFileEntry.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileEntry findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileEntry exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		int count = countByUuid(uuid);

		List<DLFileEntry> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileEntry exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry[] findByUuid_PrevAndNext(long fileEntryId, String uuid,
		OrderByComparator obc) throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

			if (uuid == null) {
				query.append("dlFileEntry.uuid IS NULL");
			}
			else {
				query.append("dlFileEntry.uuid = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("dlFileEntry.");
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

				query.append("dlFileEntry.folderId ASC, ");
				query.append("dlFileEntry.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileEntry);

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = (DLFileEntry)objArray[0];
			array[1] = (DLFileEntry)objArray[1];
			array[2] = (DLFileEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByUUID_G(uuid, groupId);

		if (dlFileEntry == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileEntry exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileEntryException(msg.toString());
		}

		return dlFileEntry;
	}

	public DLFileEntry fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public DLFileEntry fetchByUUID_G(String uuid, long groupId,
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

				query.append(
					"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

				if (uuid == null) {
					query.append("dlFileEntry.uuid IS NULL");
				}
				else {
					query.append("dlFileEntry.uuid = ?");
				}

				query.append(" AND ");

				query.append("dlFileEntry.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFileEntry.folderId ASC, ");
				query.append("dlFileEntry.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<DLFileEntry> list = q.list();

				result = list;

				DLFileEntry dlFileEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					dlFileEntry = list.get(0);

					cacheResult(dlFileEntry);

					if ((dlFileEntry.getUuid() == null) ||
							!dlFileEntry.getUuid().equals(uuid) ||
							(dlFileEntry.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, dlFileEntry);
					}
				}

				return dlFileEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<DLFileEntry>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (DLFileEntry)result;
			}
		}
	}

	public List<DLFileEntry> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFileEntry.folderId ASC, ");
				query.append("dlFileEntry.name ASC");

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
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileEntry> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<DLFileEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("dlFileEntry.");
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

					query.append("dlFileEntry.folderId ASC, ");
					query.append("dlFileEntry.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileEntry findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		int count = countByGroupId(groupId);

		List<DLFileEntry> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry[] findByGroupId_PrevAndNext(long fileEntryId,
		long groupId, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

			query.append("dlFileEntry.groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("dlFileEntry.");
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

				query.append("dlFileEntry.folderId ASC, ");
				query.append("dlFileEntry.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileEntry);

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = (DLFileEntry)objArray[0];
			array[1] = (DLFileEntry)objArray[1];
			array[2] = (DLFileEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFileEntry> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFileEntry.folderId ASC, ");
				query.append("dlFileEntry.name ASC");

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
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileEntry> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<DLFileEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("dlFileEntry.");
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

					query.append("dlFileEntry.folderId ASC, ");
					query.append("dlFileEntry.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileEntry findByCompanyId_First(long companyId,
		OrderByComparator obc) throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileEntry exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry findByCompanyId_Last(long companyId,
		OrderByComparator obc) throws NoSuchFileEntryException, SystemException {
		int count = countByCompanyId(companyId);

		List<DLFileEntry> list = findByCompanyId(companyId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileEntry exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry[] findByCompanyId_PrevAndNext(long fileEntryId,
		long companyId, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

			query.append("dlFileEntry.companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("dlFileEntry.");
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

				query.append("dlFileEntry.folderId ASC, ");
				query.append("dlFileEntry.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileEntry);

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = (DLFileEntry)objArray[0];
			array[1] = (DLFileEntry)objArray[1];
			array[2] = (DLFileEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFileEntry> findByG_U(long groupId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.groupId = ?");

				query.append(" AND ");

				query.append("dlFileEntry.userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFileEntry.folderId ASC, ");
				query.append("dlFileEntry.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileEntry> findByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	public List<DLFileEntry> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.groupId = ?");

				query.append(" AND ");

				query.append("dlFileEntry.userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("dlFileEntry.");
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

					query.append("dlFileEntry.folderId ASC, ");
					query.append("dlFileEntry.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_U,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileEntry findByG_U_First(long groupId, long userId,
		OrderByComparator obc) throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByG_U(groupId, userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry findByG_U_Last(long groupId, long userId,
		OrderByComparator obc) throws NoSuchFileEntryException, SystemException {
		int count = countByG_U(groupId, userId);

		List<DLFileEntry> list = findByG_U(groupId, userId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry[] findByG_U_PrevAndNext(long fileEntryId, long groupId,
		long userId, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		int count = countByG_U(groupId, userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

			query.append("dlFileEntry.groupId = ?");

			query.append(" AND ");

			query.append("dlFileEntry.userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("dlFileEntry.");
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

				query.append("dlFileEntry.folderId ASC, ");
				query.append("dlFileEntry.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileEntry);

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = (DLFileEntry)objArray[0];
			array[1] = (DLFileEntry)objArray[1];
			array[2] = (DLFileEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFileEntry> findByG_F(long groupId, long folderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(folderId) };

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_F,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.groupId = ?");

				query.append(" AND ");

				query.append("dlFileEntry.folderId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFileEntry.folderId ASC, ");
				query.append("dlFileEntry.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_F, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileEntry> findByG_F(long groupId, long folderId, int start,
		int end) throws SystemException {
		return findByG_F(groupId, folderId, start, end, null);
	}

	public List<DLFileEntry> findByG_F(long groupId, long folderId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_F,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.groupId = ?");

				query.append(" AND ");

				query.append("dlFileEntry.folderId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("dlFileEntry.");
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

					query.append("dlFileEntry.folderId ASC, ");
					query.append("dlFileEntry.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_F,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileEntry findByG_F_First(long groupId, long folderId,
		OrderByComparator obc) throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByG_F(groupId, folderId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("folderId=" + folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry findByG_F_Last(long groupId, long folderId,
		OrderByComparator obc) throws NoSuchFileEntryException, SystemException {
		int count = countByG_F(groupId, folderId);

		List<DLFileEntry> list = findByG_F(groupId, folderId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("folderId=" + folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry[] findByG_F_PrevAndNext(long fileEntryId, long groupId,
		long folderId, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		int count = countByG_F(groupId, folderId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

			query.append("dlFileEntry.groupId = ?");

			query.append(" AND ");

			query.append("dlFileEntry.folderId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("dlFileEntry.");
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

				query.append("dlFileEntry.folderId ASC, ");
				query.append("dlFileEntry.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileEntry);

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = (DLFileEntry)objArray[0];
			array[1] = (DLFileEntry)objArray[1];
			array[2] = (DLFileEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileEntry findByG_F_N(long groupId, long folderId, String name)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByG_F_N(groupId, folderId, name);

		if (dlFileEntry == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("folderId=" + folderId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileEntryException(msg.toString());
		}

		return dlFileEntry;
	}

	public DLFileEntry fetchByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		return fetchByG_F_N(groupId, folderId, name, true);
	}

	public DLFileEntry fetchByG_F_N(long groupId, long folderId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId),
				
				name
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_F_N,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.groupId = ?");

				query.append(" AND ");

				query.append("dlFileEntry.folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("dlFileEntry.name IS NULL");
				}
				else {
					query.append("dlFileEntry.name = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFileEntry.folderId ASC, ");
				query.append("dlFileEntry.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				List<DLFileEntry> list = q.list();

				result = list;

				DLFileEntry dlFileEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
						finderArgs, list);
				}
				else {
					dlFileEntry = list.get(0);

					cacheResult(dlFileEntry);

					if ((dlFileEntry.getGroupId() != groupId) ||
							(dlFileEntry.getFolderId() != folderId) ||
							(dlFileEntry.getName() == null) ||
							!dlFileEntry.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
							finderArgs, dlFileEntry);
					}
				}

				return dlFileEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
						finderArgs, new ArrayList<DLFileEntry>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (DLFileEntry)result;
			}
		}
	}

	public List<DLFileEntry> findByG_F_T(long groupId, long folderId,
		String title) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId),
				
				title
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_F_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.groupId = ?");

				query.append(" AND ");

				query.append("dlFileEntry.folderId = ?");

				query.append(" AND ");

				if (title == null) {
					query.append("dlFileEntry.title IS NULL");
				}
				else {
					query.append("dlFileEntry.title = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dlFileEntry.folderId ASC, ");
				query.append("dlFileEntry.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (title != null) {
					qPos.add(title);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_F_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileEntry> findByG_F_T(long groupId, long folderId,
		String title, int start, int end) throws SystemException {
		return findByG_F_T(groupId, folderId, title, start, end, null);
	}

	public List<DLFileEntry> findByG_F_T(long groupId, long folderId,
		String title, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId),
				
				title,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_F_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.groupId = ?");

				query.append(" AND ");

				query.append("dlFileEntry.folderId = ?");

				query.append(" AND ");

				if (title == null) {
					query.append("dlFileEntry.title IS NULL");
				}
				else {
					query.append("dlFileEntry.title = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("dlFileEntry.");
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

					query.append("dlFileEntry.folderId ASC, ");
					query.append("dlFileEntry.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (title != null) {
					qPos.add(title);
				}

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_F_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileEntry findByG_F_T_First(long groupId, long folderId,
		String title, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByG_F_T(groupId, folderId, title, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("folderId=" + folderId);

			msg.append(", ");
			msg.append("title=" + title);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry findByG_F_T_Last(long groupId, long folderId,
		String title, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		int count = countByG_F_T(groupId, folderId, title);

		List<DLFileEntry> list = findByG_F_T(groupId, folderId, title,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("folderId=" + folderId);

			msg.append(", ");
			msg.append("title=" + title);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry[] findByG_F_T_PrevAndNext(long fileEntryId,
		long groupId, long folderId, String title, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		int count = countByG_F_T(groupId, folderId, title);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ");

			query.append("dlFileEntry.groupId = ?");

			query.append(" AND ");

			query.append("dlFileEntry.folderId = ?");

			query.append(" AND ");

			if (title == null) {
				query.append("dlFileEntry.title IS NULL");
			}
			else {
				query.append("dlFileEntry.title = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("dlFileEntry.");
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

				query.append("dlFileEntry.folderId ASC, ");
				query.append("dlFileEntry.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			if (title != null) {
				qPos.add(title);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileEntry);

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = (DLFileEntry)objArray[0];
			array[1] = (DLFileEntry)objArray[1];
			array[2] = (DLFileEntry)objArray[2];

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

	public List<DLFileEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<DLFileEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<DLFileEntry> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT dlFileEntry FROM DLFileEntry dlFileEntry ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("dlFileEntry.");
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

					query.append("dlFileEntry.folderId ASC, ");
					query.append("dlFileEntry.name ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (DLFileEntry dlFileEntry : findByUuid(uuid)) {
			remove(dlFileEntry);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByUUID_G(uuid, groupId);

		remove(dlFileEntry);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (DLFileEntry dlFileEntry : findByGroupId(groupId)) {
			remove(dlFileEntry);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (DLFileEntry dlFileEntry : findByCompanyId(companyId)) {
			remove(dlFileEntry);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (DLFileEntry dlFileEntry : findByG_U(groupId, userId)) {
			remove(dlFileEntry);
		}
	}

	public void removeByG_F(long groupId, long folderId)
		throws SystemException {
		for (DLFileEntry dlFileEntry : findByG_F(groupId, folderId)) {
			remove(dlFileEntry);
		}
	}

	public void removeByG_F_N(long groupId, long folderId, String name)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByG_F_N(groupId, folderId, name);

		remove(dlFileEntry);
	}

	public void removeByG_F_T(long groupId, long folderId, String title)
		throws SystemException {
		for (DLFileEntry dlFileEntry : findByG_F_T(groupId, folderId, title)) {
			remove(dlFileEntry);
		}
	}

	public void removeAll() throws SystemException {
		for (DLFileEntry dlFileEntry : findAll()) {
			remove(dlFileEntry);
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

				query.append("SELECT COUNT(dlFileEntry) ");
				query.append("FROM DLFileEntry dlFileEntry WHERE ");

				if (uuid == null) {
					query.append("dlFileEntry.uuid IS NULL");
				}
				else {
					query.append("dlFileEntry.uuid = ?");
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

				query.append("SELECT COUNT(dlFileEntry) ");
				query.append("FROM DLFileEntry dlFileEntry WHERE ");

				if (uuid == null) {
					query.append("dlFileEntry.uuid IS NULL");
				}
				else {
					query.append("dlFileEntry.uuid = ?");
				}

				query.append(" AND ");

				query.append("dlFileEntry.groupId = ?");

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

				query.append("SELECT COUNT(dlFileEntry) ");
				query.append("FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.groupId = ?");

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

				query.append("SELECT COUNT(dlFileEntry) ");
				query.append("FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.companyId = ?");

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

	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(dlFileEntry) ");
				query.append("FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.groupId = ?");

				query.append(" AND ");

				query.append("dlFileEntry.userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_F(long groupId, long folderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(folderId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(dlFileEntry) ");
				query.append("FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.groupId = ?");

				query.append(" AND ");

				query.append("dlFileEntry.folderId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId),
				
				name
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(dlFileEntry) ");
				query.append("FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.groupId = ?");

				query.append(" AND ");

				query.append("dlFileEntry.folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("dlFileEntry.name IS NULL");
				}
				else {
					query.append("dlFileEntry.name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_N,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_F_T(long groupId, long folderId, String title)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId),
				
				title
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(dlFileEntry) ");
				query.append("FROM DLFileEntry dlFileEntry WHERE ");

				query.append("dlFileEntry.groupId = ?");

				query.append(" AND ");

				query.append("dlFileEntry.folderId = ?");

				query.append(" AND ");

				if (title == null) {
					query.append("dlFileEntry.title IS NULL");
				}
				else {
					query.append("dlFileEntry.title = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (title != null) {
					qPos.add(title);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_T,
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
						"SELECT COUNT(dlFileEntry) FROM DLFileEntry dlFileEntry");

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
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFileEntry>> listenersList = new ArrayList<ModelListener<DLFileEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFileEntry>)Class.forName(
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
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence.impl")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence.impl")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetEntryPersistence assetEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetTagPersistence assetTagPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence.impl")
	protected com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence ratingsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence.impl")
	protected com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence.impl")
	protected com.liferay.portlet.social.service.persistence.SocialActivityPersistence socialActivityPersistence;
	private static Log _log = LogFactoryUtil.getLog(DLFileEntryPersistenceImpl.class);
}