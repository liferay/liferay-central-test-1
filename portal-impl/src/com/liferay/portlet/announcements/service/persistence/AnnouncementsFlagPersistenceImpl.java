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

package com.liferay.portlet.announcements.service.persistence;

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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.announcements.NoSuchFlagException;
import com.liferay.portlet.announcements.model.AnnouncementsFlag;
import com.liferay.portlet.announcements.model.impl.AnnouncementsFlagImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsFlagModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="AnnouncementsFlagPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    AnnouncementsFlagPersistence
 * @see    AnnouncementsFlagUtil
 * @generated
 */
public class AnnouncementsFlagPersistenceImpl extends BasePersistenceImpl
	implements AnnouncementsFlagPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AnnouncementsFlagImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_ENTRYID = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ENTRYID = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByEntryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ENTRYID = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U_E_V = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_E_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_E_V = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_E_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(AnnouncementsFlag announcementsFlag) {
		EntityCacheUtil.putResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey(),
			announcementsFlag);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_E_V,
			new Object[] {
				new Long(announcementsFlag.getUserId()),
				new Long(announcementsFlag.getEntryId()),
				new Integer(announcementsFlag.getValue())
			}, announcementsFlag);
	}

	public void cacheResult(List<AnnouncementsFlag> announcementsFlags) {
		for (AnnouncementsFlag announcementsFlag : announcementsFlags) {
			if (EntityCacheUtil.getResult(
						AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
						AnnouncementsFlagImpl.class,
						announcementsFlag.getPrimaryKey(), this) == null) {
				cacheResult(announcementsFlag);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(AnnouncementsFlagImpl.class.getName());
		EntityCacheUtil.clearCache(AnnouncementsFlagImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public AnnouncementsFlag create(long flagId) {
		AnnouncementsFlag announcementsFlag = new AnnouncementsFlagImpl();

		announcementsFlag.setNew(true);
		announcementsFlag.setPrimaryKey(flagId);

		return announcementsFlag;
	}

	public AnnouncementsFlag remove(long flagId)
		throws NoSuchFlagException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AnnouncementsFlag announcementsFlag = (AnnouncementsFlag)session.get(AnnouncementsFlagImpl.class,
					new Long(flagId));

			if (announcementsFlag == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No AnnouncementsFlag exists with the primary key " +
						flagId);
				}

				throw new NoSuchFlagException(
					"No AnnouncementsFlag exists with the primary key " +
					flagId);
			}

			return remove(announcementsFlag);
		}
		catch (NoSuchFlagException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AnnouncementsFlag remove(AnnouncementsFlag announcementsFlag)
		throws SystemException {
		for (ModelListener<AnnouncementsFlag> listener : listeners) {
			listener.onBeforeRemove(announcementsFlag);
		}

		announcementsFlag = removeImpl(announcementsFlag);

		for (ModelListener<AnnouncementsFlag> listener : listeners) {
			listener.onAfterRemove(announcementsFlag);
		}

		return announcementsFlag;
	}

	protected AnnouncementsFlag removeImpl(AnnouncementsFlag announcementsFlag)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (announcementsFlag.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AnnouncementsFlagImpl.class,
						announcementsFlag.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(announcementsFlag);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		AnnouncementsFlagModelImpl announcementsFlagModelImpl = (AnnouncementsFlagModelImpl)announcementsFlag;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_E_V,
			new Object[] {
				new Long(announcementsFlagModelImpl.getOriginalUserId()),
				new Long(announcementsFlagModelImpl.getOriginalEntryId()),
				new Integer(announcementsFlagModelImpl.getOriginalValue())
			});

		EntityCacheUtil.removeResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey());

		return announcementsFlag;
	}

	/**
	 * @deprecated Use {@link #update(AnnouncementsFlag, boolean merge)}.
	 */
	public AnnouncementsFlag update(AnnouncementsFlag announcementsFlag)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(AnnouncementsFlag announcementsFlag) method. Use update(AnnouncementsFlag announcementsFlag, boolean merge) instead.");
		}

		return update(announcementsFlag, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  announcementsFlag the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when announcementsFlag is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public AnnouncementsFlag update(AnnouncementsFlag announcementsFlag,
		boolean merge) throws SystemException {
		boolean isNew = announcementsFlag.isNew();

		for (ModelListener<AnnouncementsFlag> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(announcementsFlag);
			}
			else {
				listener.onBeforeUpdate(announcementsFlag);
			}
		}

		announcementsFlag = updateImpl(announcementsFlag, merge);

		for (ModelListener<AnnouncementsFlag> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(announcementsFlag);
			}
			else {
				listener.onAfterUpdate(announcementsFlag);
			}
		}

		return announcementsFlag;
	}

	public AnnouncementsFlag updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag,
		boolean merge) throws SystemException {
		boolean isNew = announcementsFlag.isNew();

		AnnouncementsFlagModelImpl announcementsFlagModelImpl = (AnnouncementsFlagModelImpl)announcementsFlag;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, announcementsFlag, merge);

			announcementsFlag.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey(),
			announcementsFlag);

		if (!isNew &&
				((announcementsFlag.getUserId() != announcementsFlagModelImpl.getOriginalUserId()) ||
				(announcementsFlag.getEntryId() != announcementsFlagModelImpl.getOriginalEntryId()) ||
				(announcementsFlag.getValue() != announcementsFlagModelImpl.getOriginalValue()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_E_V,
				new Object[] {
					new Long(announcementsFlagModelImpl.getOriginalUserId()),
					new Long(announcementsFlagModelImpl.getOriginalEntryId()),
					new Integer(announcementsFlagModelImpl.getOriginalValue())
				});
		}

		if (isNew ||
				((announcementsFlag.getUserId() != announcementsFlagModelImpl.getOriginalUserId()) ||
				(announcementsFlag.getEntryId() != announcementsFlagModelImpl.getOriginalEntryId()) ||
				(announcementsFlag.getValue() != announcementsFlagModelImpl.getOriginalValue()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_E_V,
				new Object[] {
					new Long(announcementsFlag.getUserId()),
					new Long(announcementsFlag.getEntryId()),
					new Integer(announcementsFlag.getValue())
				}, announcementsFlag);
		}

		return announcementsFlag;
	}

	public AnnouncementsFlag findByPrimaryKey(long flagId)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = fetchByPrimaryKey(flagId);

		if (announcementsFlag == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No AnnouncementsFlag exists with the primary key " +
					flagId);
			}

			throw new NoSuchFlagException(
				"No AnnouncementsFlag exists with the primary key " + flagId);
		}

		return announcementsFlag;
	}

	public AnnouncementsFlag fetchByPrimaryKey(long flagId)
		throws SystemException {
		AnnouncementsFlag announcementsFlag = (AnnouncementsFlag)EntityCacheUtil.getResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsFlagImpl.class, flagId, this);

		if (announcementsFlag == null) {
			Session session = null;

			try {
				session = openSession();

				announcementsFlag = (AnnouncementsFlag)session.get(AnnouncementsFlagImpl.class,
						new Long(flagId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (announcementsFlag != null) {
					cacheResult(announcementsFlag);
				}

				closeSession(session);
			}
		}

		return announcementsFlag;
	}

	public List<AnnouncementsFlag> findByEntryId(long entryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(entryId) };

		List<AnnouncementsFlag> list = (List<AnnouncementsFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ENTRYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT announcementsFlag FROM AnnouncementsFlag announcementsFlag WHERE ");

				query.append("announcementsFlag.entryId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("announcementsFlag.userId ASC, ");
				query.append("announcementsFlag.createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ENTRYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AnnouncementsFlag> findByEntryId(long entryId, int start,
		int end) throws SystemException {
		return findByEntryId(entryId, start, end, null);
	}

	public List<AnnouncementsFlag> findByEntryId(long entryId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(entryId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AnnouncementsFlag> list = (List<AnnouncementsFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ENTRYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT announcementsFlag FROM AnnouncementsFlag announcementsFlag WHERE ");

				query.append("announcementsFlag.entryId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("announcementsFlag.");
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

					query.append("announcementsFlag.userId ASC, ");
					query.append("announcementsFlag.createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				list = (List<AnnouncementsFlag>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ENTRYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AnnouncementsFlag findByEntryId_First(long entryId,
		OrderByComparator obc) throws NoSuchFlagException, SystemException {
		List<AnnouncementsFlag> list = findByEntryId(entryId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsFlag exists with the key {");

			msg.append("entryId=" + entryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsFlag findByEntryId_Last(long entryId,
		OrderByComparator obc) throws NoSuchFlagException, SystemException {
		int count = countByEntryId(entryId);

		List<AnnouncementsFlag> list = findByEntryId(entryId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsFlag exists with the key {");

			msg.append("entryId=" + entryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsFlag[] findByEntryId_PrevAndNext(long flagId,
		long entryId, OrderByComparator obc)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = findByPrimaryKey(flagId);

		int count = countByEntryId(entryId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT announcementsFlag FROM AnnouncementsFlag announcementsFlag WHERE ");

			query.append("announcementsFlag.entryId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("announcementsFlag.");
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

				query.append("announcementsFlag.userId ASC, ");
				query.append("announcementsFlag.createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(entryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					announcementsFlag);

			AnnouncementsFlag[] array = new AnnouncementsFlagImpl[3];

			array[0] = (AnnouncementsFlag)objArray[0];
			array[1] = (AnnouncementsFlag)objArray[1];
			array[2] = (AnnouncementsFlag)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AnnouncementsFlag findByU_E_V(long userId, long entryId, int value)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = fetchByU_E_V(userId, entryId,
				value);

		if (announcementsFlag == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsFlag exists with the key {");

			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("entryId=" + entryId);

			msg.append(", ");
			msg.append("value=" + value);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFlagException(msg.toString());
		}

		return announcementsFlag;
	}

	public AnnouncementsFlag fetchByU_E_V(long userId, long entryId, int value)
		throws SystemException {
		return fetchByU_E_V(userId, entryId, value, true);
	}

	public AnnouncementsFlag fetchByU_E_V(long userId, long entryId, int value,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(entryId), new Integer(value)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_E_V,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT announcementsFlag FROM AnnouncementsFlag announcementsFlag WHERE ");

				query.append("announcementsFlag.userId = ?");

				query.append(" AND ");

				query.append("announcementsFlag.entryId = ?");

				query.append(" AND ");

				query.append("announcementsFlag.value = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("announcementsFlag.userId ASC, ");
				query.append("announcementsFlag.createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(entryId);

				qPos.add(value);

				List<AnnouncementsFlag> list = q.list();

				result = list;

				AnnouncementsFlag announcementsFlag = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_E_V,
						finderArgs, list);
				}
				else {
					announcementsFlag = list.get(0);

					cacheResult(announcementsFlag);

					if ((announcementsFlag.getUserId() != userId) ||
							(announcementsFlag.getEntryId() != entryId) ||
							(announcementsFlag.getValue() != value)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_E_V,
							finderArgs, announcementsFlag);
					}
				}

				return announcementsFlag;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_E_V,
						finderArgs, new ArrayList<AnnouncementsFlag>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (AnnouncementsFlag)result;
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

	public List<AnnouncementsFlag> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AnnouncementsFlag> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<AnnouncementsFlag> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AnnouncementsFlag> list = (List<AnnouncementsFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT announcementsFlag FROM AnnouncementsFlag announcementsFlag ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("announcementsFlag.");
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

					query.append("announcementsFlag.userId ASC, ");
					query.append("announcementsFlag.createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<AnnouncementsFlag>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AnnouncementsFlag>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByEntryId(long entryId) throws SystemException {
		for (AnnouncementsFlag announcementsFlag : findByEntryId(entryId)) {
			remove(announcementsFlag);
		}
	}

	public void removeByU_E_V(long userId, long entryId, int value)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = findByU_E_V(userId, entryId, value);

		remove(announcementsFlag);
	}

	public void removeAll() throws SystemException {
		for (AnnouncementsFlag announcementsFlag : findAll()) {
			remove(announcementsFlag);
		}
	}

	public int countByEntryId(long entryId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(entryId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ENTRYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(announcementsFlag) ");
				query.append("FROM AnnouncementsFlag announcementsFlag WHERE ");

				query.append("announcementsFlag.entryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ENTRYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_E_V(long userId, long entryId, int value)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(entryId), new Integer(value)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_E_V,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(announcementsFlag) ");
				query.append("FROM AnnouncementsFlag announcementsFlag WHERE ");

				query.append("announcementsFlag.userId = ?");

				query.append(" AND ");

				query.append("announcementsFlag.entryId = ?");

				query.append(" AND ");

				query.append("announcementsFlag.value = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(entryId);

				qPos.add(value);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_E_V,
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
						"SELECT COUNT(announcementsFlag) FROM AnnouncementsFlag announcementsFlag");

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
						"value.object.listener.com.liferay.portlet.announcements.model.AnnouncementsFlag")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AnnouncementsFlag>> listenersList = new ArrayList<ModelListener<AnnouncementsFlag>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AnnouncementsFlag>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.announcements.service.persistence.AnnouncementsDeliveryPersistence.impl")
	protected com.liferay.portlet.announcements.service.persistence.AnnouncementsDeliveryPersistence announcementsDeliveryPersistence;
	@BeanReference(name = "com.liferay.portlet.announcements.service.persistence.AnnouncementsEntryPersistence.impl")
	protected com.liferay.portlet.announcements.service.persistence.AnnouncementsEntryPersistence announcementsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.announcements.service.persistence.AnnouncementsFlagPersistence.impl")
	protected com.liferay.portlet.announcements.service.persistence.AnnouncementsFlagPersistence announcementsFlagPersistence;
	private static Log _log = LogFactoryUtil.getLog(AnnouncementsFlagPersistenceImpl.class);
}