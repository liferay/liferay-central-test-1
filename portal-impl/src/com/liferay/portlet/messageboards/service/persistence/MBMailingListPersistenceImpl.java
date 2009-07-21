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

package com.liferay.portlet.messageboards.service.persistence;

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

import com.liferay.portlet.messageboards.NoSuchMailingListException;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.model.impl.MBMailingListImpl;
import com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MBMailingListPersistenceImpl extends BasePersistenceImpl
	implements MBMailingListPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = MBMailingListImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_CATEGORYID = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByCategoryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_CATEGORYID = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCategoryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_ACTIVE = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByActive",
			new String[] { Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ACTIVE = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByActive",
			new String[] {
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ACTIVE = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByActive",
			new String[] { Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(MBMailingList mbMailingList) {
		EntityCacheUtil.putResult(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListImpl.class, mbMailingList.getPrimaryKey(),
			mbMailingList);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				mbMailingList.getUuid(), new Long(mbMailingList.getGroupId())
			}, mbMailingList);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CATEGORYID,
			new Object[] { new Long(mbMailingList.getCategoryId()) },
			mbMailingList);
	}

	public void cacheResult(List<MBMailingList> mbMailingLists) {
		for (MBMailingList mbMailingList : mbMailingLists) {
			if (EntityCacheUtil.getResult(
						MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
						MBMailingListImpl.class, mbMailingList.getPrimaryKey(),
						this) == null) {
				cacheResult(mbMailingList);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(MBMailingListImpl.class.getName());
		EntityCacheUtil.clearCache(MBMailingListImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public MBMailingList create(long mailingListId) {
		MBMailingList mbMailingList = new MBMailingListImpl();

		mbMailingList.setNew(true);
		mbMailingList.setPrimaryKey(mailingListId);

		String uuid = PortalUUIDUtil.generate();

		mbMailingList.setUuid(uuid);

		return mbMailingList;
	}

	public MBMailingList remove(long mailingListId)
		throws NoSuchMailingListException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBMailingList mbMailingList = (MBMailingList)session.get(MBMailingListImpl.class,
					new Long(mailingListId));

			if (mbMailingList == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No MBMailingList exists with the primary key " +
						mailingListId);
				}

				throw new NoSuchMailingListException(
					"No MBMailingList exists with the primary key " +
					mailingListId);
			}

			return remove(mbMailingList);
		}
		catch (NoSuchMailingListException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBMailingList remove(MBMailingList mbMailingList)
		throws SystemException {
		for (ModelListener<MBMailingList> listener : listeners) {
			listener.onBeforeRemove(mbMailingList);
		}

		mbMailingList = removeImpl(mbMailingList);

		for (ModelListener<MBMailingList> listener : listeners) {
			listener.onAfterRemove(mbMailingList);
		}

		return mbMailingList;
	}

	protected MBMailingList removeImpl(MBMailingList mbMailingList)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (mbMailingList.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(MBMailingListImpl.class,
						mbMailingList.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(mbMailingList);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		MBMailingListModelImpl mbMailingListModelImpl = (MBMailingListModelImpl)mbMailingList;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				mbMailingListModelImpl.getOriginalUuid(),
				new Long(mbMailingListModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CATEGORYID,
			new Object[] {
				new Long(mbMailingListModelImpl.getOriginalCategoryId())
			});

		EntityCacheUtil.removeResult(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListImpl.class, mbMailingList.getPrimaryKey());

		return mbMailingList;
	}

	public MBMailingList update(MBMailingList mbMailingList)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(MBMailingList mbMailingList) method. Use update(MBMailingList mbMailingList, boolean merge) instead.");
		}

		return update(mbMailingList, false);
	}

	public MBMailingList update(MBMailingList mbMailingList, boolean merge)
		throws SystemException {
		boolean isNew = mbMailingList.isNew();

		for (ModelListener<MBMailingList> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(mbMailingList);
			}
			else {
				listener.onBeforeUpdate(mbMailingList);
			}
		}

		mbMailingList = updateImpl(mbMailingList, merge);

		for (ModelListener<MBMailingList> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(mbMailingList);
			}
			else {
				listener.onAfterUpdate(mbMailingList);
			}
		}

		return mbMailingList;
	}

	public MBMailingList updateImpl(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList,
		boolean merge) throws SystemException {
		boolean isNew = mbMailingList.isNew();

		MBMailingListModelImpl mbMailingListModelImpl = (MBMailingListModelImpl)mbMailingList;

		if (Validator.isNull(mbMailingList.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mbMailingList.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, mbMailingList, merge);

			mbMailingList.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListImpl.class, mbMailingList.getPrimaryKey(),
			mbMailingList);

		if (!isNew &&
				(!Validator.equals(mbMailingList.getUuid(),
					mbMailingListModelImpl.getOriginalUuid()) ||
				(mbMailingList.getGroupId() != mbMailingListModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					mbMailingListModelImpl.getOriginalUuid(),
					new Long(mbMailingListModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(mbMailingList.getUuid(),
					mbMailingListModelImpl.getOriginalUuid()) ||
				(mbMailingList.getGroupId() != mbMailingListModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					mbMailingList.getUuid(),
					new Long(mbMailingList.getGroupId())
				}, mbMailingList);
		}

		if (!isNew &&
				(mbMailingList.getCategoryId() != mbMailingListModelImpl.getOriginalCategoryId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CATEGORYID,
				new Object[] {
					new Long(mbMailingListModelImpl.getOriginalCategoryId())
				});
		}

		if (isNew ||
				(mbMailingList.getCategoryId() != mbMailingListModelImpl.getOriginalCategoryId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CATEGORYID,
				new Object[] { new Long(mbMailingList.getCategoryId()) },
				mbMailingList);
		}

		return mbMailingList;
	}

	public MBMailingList findByPrimaryKey(long mailingListId)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = fetchByPrimaryKey(mailingListId);

		if (mbMailingList == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MBMailingList exists with the primary key " +
					mailingListId);
			}

			throw new NoSuchMailingListException(
				"No MBMailingList exists with the primary key " +
				mailingListId);
		}

		return mbMailingList;
	}

	public MBMailingList fetchByPrimaryKey(long mailingListId)
		throws SystemException {
		MBMailingList mbMailingList = (MBMailingList)EntityCacheUtil.getResult(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
				MBMailingListImpl.class, mailingListId, this);

		if (mbMailingList == null) {
			Session session = null;

			try {
				session = openSession();

				mbMailingList = (MBMailingList)session.get(MBMailingListImpl.class,
						new Long(mailingListId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (mbMailingList != null) {
					cacheResult(mbMailingList);
				}

				closeSession(session);
			}
		}

		return mbMailingList;
	}

	public List<MBMailingList> findByUuid(String uuid)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<MBMailingList> list = (List<MBMailingList>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT mbMailingList FROM MBMailingList mbMailingList WHERE ");

				if (uuid == null) {
					query.append("mbMailingList.uuid IS NULL");
				}
				else {
					query.append("mbMailingList.uuid = ?");
				}

				query.append(" ");

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
					list = new ArrayList<MBMailingList>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMailingList> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<MBMailingList> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMailingList> list = (List<MBMailingList>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT mbMailingList FROM MBMailingList mbMailingList WHERE ");

				if (uuid == null) {
					query.append("mbMailingList.uuid IS NULL");
				}
				else {
					query.append("mbMailingList.uuid = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbMailingList.");
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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<MBMailingList>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMailingList>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMailingList findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchMailingListException, SystemException {
		List<MBMailingList> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMailingList exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMailingListException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMailingList findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchMailingListException, SystemException {
		int count = countByUuid(uuid);

		List<MBMailingList> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMailingList exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMailingListException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMailingList[] findByUuid_PrevAndNext(long mailingListId,
		String uuid, OrderByComparator obc)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = findByPrimaryKey(mailingListId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT mbMailingList FROM MBMailingList mbMailingList WHERE ");

			if (uuid == null) {
				query.append("mbMailingList.uuid IS NULL");
			}
			else {
				query.append("mbMailingList.uuid = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbMailingList.");
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

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMailingList);

			MBMailingList[] array = new MBMailingListImpl[3];

			array[0] = (MBMailingList)objArray[0];
			array[1] = (MBMailingList)objArray[1];
			array[2] = (MBMailingList)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBMailingList findByUUID_G(String uuid, long groupId)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = fetchByUUID_G(uuid, groupId);

		if (mbMailingList == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMailingList exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchMailingListException(msg.toString());
		}

		return mbMailingList;
	}

	public MBMailingList fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public MBMailingList fetchByUUID_G(String uuid, long groupId,
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
					"SELECT mbMailingList FROM MBMailingList mbMailingList WHERE ");

				if (uuid == null) {
					query.append("mbMailingList.uuid IS NULL");
				}
				else {
					query.append("mbMailingList.uuid = ?");
				}

				query.append(" AND ");

				query.append("mbMailingList.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<MBMailingList> list = q.list();

				result = list;

				MBMailingList mbMailingList = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					mbMailingList = list.get(0);

					cacheResult(mbMailingList);

					if ((mbMailingList.getUuid() == null) ||
							!mbMailingList.getUuid().equals(uuid) ||
							(mbMailingList.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, mbMailingList);
					}
				}

				return mbMailingList;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<MBMailingList>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (MBMailingList)result;
			}
		}
	}

	public MBMailingList findByCategoryId(long categoryId)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = fetchByCategoryId(categoryId);

		if (mbMailingList == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMailingList exists with the key {");

			msg.append("categoryId=" + categoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchMailingListException(msg.toString());
		}

		return mbMailingList;
	}

	public MBMailingList fetchByCategoryId(long categoryId)
		throws SystemException {
		return fetchByCategoryId(categoryId, true);
	}

	public MBMailingList fetchByCategoryId(long categoryId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(categoryId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CATEGORYID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT mbMailingList FROM MBMailingList mbMailingList WHERE ");

				query.append("mbMailingList.categoryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				List<MBMailingList> list = q.list();

				result = list;

				MBMailingList mbMailingList = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CATEGORYID,
						finderArgs, list);
				}
				else {
					mbMailingList = list.get(0);

					cacheResult(mbMailingList);

					if ((mbMailingList.getCategoryId() != categoryId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CATEGORYID,
							finderArgs, mbMailingList);
					}
				}

				return mbMailingList;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CATEGORYID,
						finderArgs, new ArrayList<MBMailingList>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (MBMailingList)result;
			}
		}
	}

	public List<MBMailingList> findByActive(boolean active)
		throws SystemException {
		Object[] finderArgs = new Object[] { Boolean.valueOf(active) };

		List<MBMailingList> list = (List<MBMailingList>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ACTIVE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT mbMailingList FROM MBMailingList mbMailingList WHERE ");

				query.append("mbMailingList.active = ?");

				query.append(" ");

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
					list = new ArrayList<MBMailingList>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ACTIVE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMailingList> findByActive(boolean active, int start, int end)
		throws SystemException {
		return findByActive(active, start, end, null);
	}

	public List<MBMailingList> findByActive(boolean active, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				Boolean.valueOf(active),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMailingList> list = (List<MBMailingList>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ACTIVE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT mbMailingList FROM MBMailingList mbMailingList WHERE ");

				query.append("mbMailingList.active = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbMailingList.");
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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				list = (List<MBMailingList>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMailingList>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ACTIVE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMailingList findByActive_First(boolean active,
		OrderByComparator obc)
		throws NoSuchMailingListException, SystemException {
		List<MBMailingList> list = findByActive(active, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMailingList exists with the key {");

			msg.append("active=" + active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMailingListException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMailingList findByActive_Last(boolean active, OrderByComparator obc)
		throws NoSuchMailingListException, SystemException {
		int count = countByActive(active);

		List<MBMailingList> list = findByActive(active, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMailingList exists with the key {");

			msg.append("active=" + active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMailingListException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMailingList[] findByActive_PrevAndNext(long mailingListId,
		boolean active, OrderByComparator obc)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = findByPrimaryKey(mailingListId);

		int count = countByActive(active);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT mbMailingList FROM MBMailingList mbMailingList WHERE ");

			query.append("mbMailingList.active = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbMailingList.");
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

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMailingList);

			MBMailingList[] array = new MBMailingListImpl[3];

			array[0] = (MBMailingList)objArray[0];
			array[1] = (MBMailingList)objArray[1];
			array[2] = (MBMailingList)objArray[2];

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

	public List<MBMailingList> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MBMailingList> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<MBMailingList> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMailingList> list = (List<MBMailingList>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT mbMailingList FROM MBMailingList mbMailingList ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbMailingList.");
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

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<MBMailingList>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MBMailingList>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMailingList>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (MBMailingList mbMailingList : findByUuid(uuid)) {
			remove(mbMailingList);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = findByUUID_G(uuid, groupId);

		remove(mbMailingList);
	}

	public void removeByCategoryId(long categoryId)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = findByCategoryId(categoryId);

		remove(mbMailingList);
	}

	public void removeByActive(boolean active) throws SystemException {
		for (MBMailingList mbMailingList : findByActive(active)) {
			remove(mbMailingList);
		}
	}

	public void removeAll() throws SystemException {
		for (MBMailingList mbMailingList : findAll()) {
			remove(mbMailingList);
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

				query.append("SELECT COUNT(mbMailingList) ");
				query.append("FROM MBMailingList mbMailingList WHERE ");

				if (uuid == null) {
					query.append("mbMailingList.uuid IS NULL");
				}
				else {
					query.append("mbMailingList.uuid = ?");
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

				query.append("SELECT COUNT(mbMailingList) ");
				query.append("FROM MBMailingList mbMailingList WHERE ");

				if (uuid == null) {
					query.append("mbMailingList.uuid IS NULL");
				}
				else {
					query.append("mbMailingList.uuid = ?");
				}

				query.append(" AND ");

				query.append("mbMailingList.groupId = ?");

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

	public int countByCategoryId(long categoryId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(categoryId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CATEGORYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(mbMailingList) ");
				query.append("FROM MBMailingList mbMailingList WHERE ");

				query.append("mbMailingList.categoryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CATEGORYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
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

				query.append("SELECT COUNT(mbMailingList) ");
				query.append("FROM MBMailingList mbMailingList WHERE ");

				query.append("mbMailingList.active = ?");

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

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(mbMailingList) FROM MBMailingList mbMailingList");

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
						"value.object.listener.com.liferay.portlet.messageboards.model.MBMailingList")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MBMailingList>> listenersList = new ArrayList<ModelListener<MBMailingList>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MBMailingList>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBBanPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBBanPersistence mbBanPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence mbCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMailingListPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBMailingListPersistence mbMailingListPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence mbMessageFlagPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence mbThreadPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(MBMailingListPersistenceImpl.class);
}