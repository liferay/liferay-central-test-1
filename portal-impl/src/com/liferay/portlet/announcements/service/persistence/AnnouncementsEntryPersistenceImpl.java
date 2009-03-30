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

import com.liferay.portlet.announcements.NoSuchEntryException;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.announcements.model.impl.AnnouncementsEntryImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="AnnouncementsEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementsEntryPersistenceImpl extends BasePersistenceImpl
	implements AnnouncementsEntryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AnnouncementsEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_C = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_C_A = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C_A = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_A = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(AnnouncementsEntry announcementsEntry) {
		EntityCacheUtil.putResult(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryImpl.class, announcementsEntry.getPrimaryKey(),
			announcementsEntry);
	}

	public void cacheResult(List<AnnouncementsEntry> announcementsEntries) {
		for (AnnouncementsEntry announcementsEntry : announcementsEntries) {
			if (EntityCacheUtil.getResult(
						AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
						AnnouncementsEntryImpl.class,
						announcementsEntry.getPrimaryKey(), this) == null) {
				cacheResult(announcementsEntry);
			}
		}
	}

	public AnnouncementsEntry create(long entryId) {
		AnnouncementsEntry announcementsEntry = new AnnouncementsEntryImpl();

		announcementsEntry.setNew(true);
		announcementsEntry.setPrimaryKey(entryId);

		String uuid = PortalUUIDUtil.generate();

		announcementsEntry.setUuid(uuid);

		return announcementsEntry;
	}

	public AnnouncementsEntry remove(long entryId)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AnnouncementsEntry announcementsEntry = (AnnouncementsEntry)session.get(AnnouncementsEntryImpl.class,
					new Long(entryId));

			if (announcementsEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No AnnouncementsEntry exists with the primary key " +
						entryId);
				}

				throw new NoSuchEntryException(
					"No AnnouncementsEntry exists with the primary key " +
					entryId);
			}

			return remove(announcementsEntry);
		}
		catch (NoSuchEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AnnouncementsEntry remove(AnnouncementsEntry announcementsEntry)
		throws SystemException {
		for (ModelListener<AnnouncementsEntry> listener : listeners) {
			listener.onBeforeRemove(announcementsEntry);
		}

		announcementsEntry = removeImpl(announcementsEntry);

		for (ModelListener<AnnouncementsEntry> listener : listeners) {
			listener.onAfterRemove(announcementsEntry);
		}

		return announcementsEntry;
	}

	protected AnnouncementsEntry removeImpl(
		AnnouncementsEntry announcementsEntry) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (announcementsEntry.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AnnouncementsEntryImpl.class,
						announcementsEntry.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(announcementsEntry);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryImpl.class, announcementsEntry.getPrimaryKey());

		return announcementsEntry;
	}

	/**
	 * @deprecated Use <code>update(AnnouncementsEntry announcementsEntry, boolean merge)</code>.
	 */
	public AnnouncementsEntry update(AnnouncementsEntry announcementsEntry)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(AnnouncementsEntry announcementsEntry) method. Use update(AnnouncementsEntry announcementsEntry, boolean merge) instead.");
		}

		return update(announcementsEntry, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        announcementsEntry the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when announcementsEntry is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public AnnouncementsEntry update(AnnouncementsEntry announcementsEntry,
		boolean merge) throws SystemException {
		boolean isNew = announcementsEntry.isNew();

		for (ModelListener<AnnouncementsEntry> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(announcementsEntry);
			}
			else {
				listener.onBeforeUpdate(announcementsEntry);
			}
		}

		announcementsEntry = updateImpl(announcementsEntry, merge);

		for (ModelListener<AnnouncementsEntry> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(announcementsEntry);
			}
			else {
				listener.onAfterUpdate(announcementsEntry);
			}
		}

		return announcementsEntry;
	}

	public AnnouncementsEntry updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry,
		boolean merge) throws SystemException {
		if (Validator.isNull(announcementsEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			announcementsEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, announcementsEntry, merge);

			announcementsEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryImpl.class, announcementsEntry.getPrimaryKey(),
			announcementsEntry);

		return announcementsEntry;
	}

	public AnnouncementsEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = fetchByPrimaryKey(entryId);

		if (announcementsEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No AnnouncementsEntry exists with the primary key " +
					entryId);
			}

			throw new NoSuchEntryException(
				"No AnnouncementsEntry exists with the primary key " + entryId);
		}

		return announcementsEntry;
	}

	public AnnouncementsEntry fetchByPrimaryKey(long entryId)
		throws SystemException {
		AnnouncementsEntry announcementsEntry = (AnnouncementsEntry)EntityCacheUtil.getResult(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsEntryImpl.class, entryId, this);

		if (announcementsEntry == null) {
			Session session = null;

			try {
				session = openSession();

				announcementsEntry = (AnnouncementsEntry)session.get(AnnouncementsEntryImpl.class,
						new Long(entryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (announcementsEntry != null) {
					cacheResult(announcementsEntry);
				}

				closeSession(session);
			}
		}

		return announcementsEntry;
	}

	public List<AnnouncementsEntry> findByUuid(String uuid)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");

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
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AnnouncementsEntry> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<AnnouncementsEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("priority ASC, ");
					query.append("modifiedDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<AnnouncementsEntry>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AnnouncementsEntry findByUuid_First(String uuid,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<AnnouncementsEntry> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsEntry exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsEntry findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		int count = countByUuid(uuid);

		List<AnnouncementsEntry> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsEntry exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsEntry[] findByUuid_PrevAndNext(long entryId,
		String uuid, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry WHERE ");

			if (uuid == null) {
				query.append("uuid_ IS NULL");
			}
			else {
				query.append("uuid_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					announcementsEntry);

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = (AnnouncementsEntry)objArray[0];
			array[1] = (AnnouncementsEntry)objArray[1];
			array[2] = (AnnouncementsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AnnouncementsEntry> findByUserId(long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry WHERE ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AnnouncementsEntry> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<AnnouncementsEntry> findByUserId(long userId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("priority ASC, ");
					query.append("modifiedDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<AnnouncementsEntry>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AnnouncementsEntry findByUserId_First(long userId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<AnnouncementsEntry> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsEntry exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsEntry findByUserId_Last(long userId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		int count = countByUserId(userId);

		List<AnnouncementsEntry> list = findByUserId(userId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsEntry exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsEntry[] findByUserId_PrevAndNext(long entryId,
		long userId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					announcementsEntry);

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = (AnnouncementsEntry)objArray[0];
			array[1] = (AnnouncementsEntry)objArray[1];
			array[2] = (AnnouncementsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AnnouncementsEntry> findByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AnnouncementsEntry> findByC_C(long classNameId, long classPK,
		int start, int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	public List<AnnouncementsEntry> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("priority ASC, ");
					query.append("modifiedDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<AnnouncementsEntry>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AnnouncementsEntry findByC_C_First(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<AnnouncementsEntry> list = findByC_C(classNameId, classPK, 0, 1,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsEntry exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsEntry findByC_C_Last(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		int count = countByC_C(classNameId, classPK);

		List<AnnouncementsEntry> list = findByC_C(classNameId, classPK,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsEntry exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsEntry[] findByC_C_PrevAndNext(long entryId,
		long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		int count = countByC_C(classNameId, classPK);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry WHERE ");

			query.append("classNameId = ?");

			query.append(" AND ");

			query.append("classPK = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					announcementsEntry);

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = (AnnouncementsEntry)objArray[0];
			array[1] = (AnnouncementsEntry)objArray[1];
			array[2] = (AnnouncementsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AnnouncementsEntry> findByC_C_A(long classNameId, long classPK,
		boolean alert) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK), Boolean.valueOf(alert)
			};

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("alert = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(alert);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C_A,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AnnouncementsEntry> findByC_C_A(long classNameId, long classPK,
		boolean alert, int start, int end) throws SystemException {
		return findByC_C_A(classNameId, classPK, alert, start, end, null);
	}

	public List<AnnouncementsEntry> findByC_C_A(long classNameId, long classPK,
		boolean alert, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK), Boolean.valueOf(alert),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("alert = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("priority ASC, ");
					query.append("modifiedDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(alert);

				list = (List<AnnouncementsEntry>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C_A,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AnnouncementsEntry findByC_C_A_First(long classNameId, long classPK,
		boolean alert, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		List<AnnouncementsEntry> list = findByC_C_A(classNameId, classPK,
				alert, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsEntry exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(", ");
			msg.append("alert=" + alert);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsEntry findByC_C_A_Last(long classNameId, long classPK,
		boolean alert, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		int count = countByC_C_A(classNameId, classPK, alert);

		List<AnnouncementsEntry> list = findByC_C_A(classNameId, classPK,
				alert, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsEntry exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(", ");
			msg.append("alert=" + alert);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsEntry[] findByC_C_A_PrevAndNext(long entryId,
		long classNameId, long classPK, boolean alert, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		int count = countByC_C_A(classNameId, classPK, alert);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry WHERE ");

			query.append("classNameId = ?");

			query.append(" AND ");

			query.append("classPK = ?");

			query.append(" AND ");

			query.append("alert = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			qPos.add(alert);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					announcementsEntry);

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = (AnnouncementsEntry)objArray[0];
			array[1] = (AnnouncementsEntry)objArray[1];
			array[2] = (AnnouncementsEntry)objArray[2];

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

	public List<AnnouncementsEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AnnouncementsEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<AnnouncementsEntry> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("priority ASC, ");
					query.append("modifiedDate ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<AnnouncementsEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AnnouncementsEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (AnnouncementsEntry announcementsEntry : findByUuid(uuid)) {
			remove(announcementsEntry);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (AnnouncementsEntry announcementsEntry : findByUserId(userId)) {
			remove(announcementsEntry);
		}
	}

	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (AnnouncementsEntry announcementsEntry : findByC_C(classNameId,
				classPK)) {
			remove(announcementsEntry);
		}
	}

	public void removeByC_C_A(long classNameId, long classPK, boolean alert)
		throws SystemException {
		for (AnnouncementsEntry announcementsEntry : findByC_C_A(classNameId,
				classPK, alert)) {
			remove(announcementsEntry);
		}
	}

	public void removeAll() throws SystemException {
		for (AnnouncementsEntry announcementsEntry : findAll()) {
			remove(announcementsEntry);
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

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
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

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C_A(long classNameId, long classPK, boolean alert)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK), Boolean.valueOf(alert)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_A,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsEntry WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("alert = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(alert);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_A,
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
						"SELECT COUNT(*) FROM com.liferay.portlet.announcements.model.AnnouncementsEntry");

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
						"value.object.listener.com.liferay.portlet.announcements.model.AnnouncementsEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AnnouncementsEntry>> listenersList = new ArrayList<ModelListener<AnnouncementsEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AnnouncementsEntry>)Class.forName(
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
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence.impl")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence.impl")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	private static Log _log = LogFactoryUtil.getLog(AnnouncementsEntryPersistenceImpl.class);
}