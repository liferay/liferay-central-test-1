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

import com.liferay.portlet.asset.NoSuchTagStatsException;
import com.liferay.portlet.asset.model.AssetTagStats;
import com.liferay.portlet.asset.model.impl.AssetTagStatsImpl;
import com.liferay.portlet.asset.model.impl.AssetTagStatsModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="AssetTagStatsPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagStatsPersistence
 * @see       AssetTagStatsUtil
 * @generated
 */
public class AssetTagStatsPersistenceImpl extends BasePersistenceImpl
	implements AssetTagStatsPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AssetTagStatsImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_TAGID = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByTagId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_TAGID = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByTagId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TAGID = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByTagId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_CLASSNAMEID = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByClassNameId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_CLASSNAMEID = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByClassNameId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_CLASSNAMEID = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByClassNameId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_T_C = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByT_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_T_C = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByT_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(AssetTagStats assetTagStats) {
		EntityCacheUtil.putResult(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsImpl.class, assetTagStats.getPrimaryKey(),
			assetTagStats);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C,
			new Object[] {
				new Long(assetTagStats.getTagId()),
				new Long(assetTagStats.getClassNameId())
			}, assetTagStats);
	}

	public void cacheResult(List<AssetTagStats> assetTagStatses) {
		for (AssetTagStats assetTagStats : assetTagStatses) {
			if (EntityCacheUtil.getResult(
						AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
						AssetTagStatsImpl.class, assetTagStats.getPrimaryKey(),
						this) == null) {
				cacheResult(assetTagStats);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(AssetTagStatsImpl.class.getName());
		EntityCacheUtil.clearCache(AssetTagStatsImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public AssetTagStats create(long tagStatsId) {
		AssetTagStats assetTagStats = new AssetTagStatsImpl();

		assetTagStats.setNew(true);
		assetTagStats.setPrimaryKey(tagStatsId);

		return assetTagStats;
	}

	public AssetTagStats remove(long tagStatsId)
		throws NoSuchTagStatsException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetTagStats assetTagStats = (AssetTagStats)session.get(AssetTagStatsImpl.class,
					new Long(tagStatsId));

			if (assetTagStats == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No AssetTagStats exists with the primary key " +
						tagStatsId);
				}

				throw new NoSuchTagStatsException(
					"No AssetTagStats exists with the primary key " +
					tagStatsId);
			}

			return remove(assetTagStats);
		}
		catch (NoSuchTagStatsException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AssetTagStats remove(AssetTagStats assetTagStats)
		throws SystemException {
		for (ModelListener<AssetTagStats> listener : listeners) {
			listener.onBeforeRemove(assetTagStats);
		}

		assetTagStats = removeImpl(assetTagStats);

		for (ModelListener<AssetTagStats> listener : listeners) {
			listener.onAfterRemove(assetTagStats);
		}

		return assetTagStats;
	}

	protected AssetTagStats removeImpl(AssetTagStats assetTagStats)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (assetTagStats.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AssetTagStatsImpl.class,
						assetTagStats.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(assetTagStats);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		AssetTagStatsModelImpl assetTagStatsModelImpl = (AssetTagStatsModelImpl)assetTagStats;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_C,
			new Object[] {
				new Long(assetTagStatsModelImpl.getOriginalTagId()),
				new Long(assetTagStatsModelImpl.getOriginalClassNameId())
			});

		EntityCacheUtil.removeResult(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsImpl.class, assetTagStats.getPrimaryKey());

		return assetTagStats;
	}

	/**
	 * @deprecated Use {@link #update(AssetTagStats, boolean merge)}.
	 */
	public AssetTagStats update(AssetTagStats assetTagStats)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(AssetTagStats assetTagStats) method. Use update(AssetTagStats assetTagStats, boolean merge) instead.");
		}

		return update(assetTagStats, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  assetTagStats the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when assetTagStats is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public AssetTagStats update(AssetTagStats assetTagStats, boolean merge)
		throws SystemException {
		boolean isNew = assetTagStats.isNew();

		for (ModelListener<AssetTagStats> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(assetTagStats);
			}
			else {
				listener.onBeforeUpdate(assetTagStats);
			}
		}

		assetTagStats = updateImpl(assetTagStats, merge);

		for (ModelListener<AssetTagStats> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(assetTagStats);
			}
			else {
				listener.onAfterUpdate(assetTagStats);
			}
		}

		return assetTagStats;
	}

	public AssetTagStats updateImpl(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats,
		boolean merge) throws SystemException {
		boolean isNew = assetTagStats.isNew();

		AssetTagStatsModelImpl assetTagStatsModelImpl = (AssetTagStatsModelImpl)assetTagStats;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, assetTagStats, merge);

			assetTagStats.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsImpl.class, assetTagStats.getPrimaryKey(),
			assetTagStats);

		if (!isNew &&
				((assetTagStats.getTagId() != assetTagStatsModelImpl.getOriginalTagId()) ||
				(assetTagStats.getClassNameId() != assetTagStatsModelImpl.getOriginalClassNameId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_C,
				new Object[] {
					new Long(assetTagStatsModelImpl.getOriginalTagId()),
					new Long(assetTagStatsModelImpl.getOriginalClassNameId())
				});
		}

		if (isNew ||
				((assetTagStats.getTagId() != assetTagStatsModelImpl.getOriginalTagId()) ||
				(assetTagStats.getClassNameId() != assetTagStatsModelImpl.getOriginalClassNameId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C,
				new Object[] {
					new Long(assetTagStats.getTagId()),
					new Long(assetTagStats.getClassNameId())
				}, assetTagStats);
		}

		return assetTagStats;
	}

	public AssetTagStats findByPrimaryKey(long tagStatsId)
		throws NoSuchTagStatsException, SystemException {
		AssetTagStats assetTagStats = fetchByPrimaryKey(tagStatsId);

		if (assetTagStats == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No AssetTagStats exists with the primary key " +
					tagStatsId);
			}

			throw new NoSuchTagStatsException(
				"No AssetTagStats exists with the primary key " + tagStatsId);
		}

		return assetTagStats;
	}

	public AssetTagStats fetchByPrimaryKey(long tagStatsId)
		throws SystemException {
		AssetTagStats assetTagStats = (AssetTagStats)EntityCacheUtil.getResult(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
				AssetTagStatsImpl.class, tagStatsId, this);

		if (assetTagStats == null) {
			Session session = null;

			try {
				session = openSession();

				assetTagStats = (AssetTagStats)session.get(AssetTagStatsImpl.class,
						new Long(tagStatsId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (assetTagStats != null) {
					cacheResult(assetTagStats);
				}

				closeSession(session);
			}
		}

		return assetTagStats;
	}

	public List<AssetTagStats> findByTagId(long tagId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tagId) };

		List<AssetTagStats> list = (List<AssetTagStats>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TAGID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetTagStats FROM AssetTagStats assetTagStats WHERE ");

				query.append("assetTagStats.tagId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("assetTagStats.assetCount DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetTagStats>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TAGID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetTagStats> findByTagId(long tagId, int start, int end)
		throws SystemException {
		return findByTagId(tagId, start, end, null);
	}

	public List<AssetTagStats> findByTagId(long tagId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tagId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AssetTagStats> list = (List<AssetTagStats>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_TAGID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetTagStats FROM AssetTagStats assetTagStats WHERE ");

				query.append("assetTagStats.tagId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("assetTagStats.");
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

					query.append("assetTagStats.assetCount DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				list = (List<AssetTagStats>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetTagStats>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_TAGID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetTagStats findByTagId_First(long tagId, OrderByComparator obc)
		throws NoSuchTagStatsException, SystemException {
		List<AssetTagStats> list = findByTagId(tagId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetTagStats exists with the key {");

			msg.append("tagId=" + tagId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTagStatsException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetTagStats findByTagId_Last(long tagId, OrderByComparator obc)
		throws NoSuchTagStatsException, SystemException {
		int count = countByTagId(tagId);

		List<AssetTagStats> list = findByTagId(tagId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetTagStats exists with the key {");

			msg.append("tagId=" + tagId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTagStatsException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetTagStats[] findByTagId_PrevAndNext(long tagStatsId, long tagId,
		OrderByComparator obc) throws NoSuchTagStatsException, SystemException {
		AssetTagStats assetTagStats = findByPrimaryKey(tagStatsId);

		int count = countByTagId(tagId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT assetTagStats FROM AssetTagStats assetTagStats WHERE ");

			query.append("assetTagStats.tagId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("assetTagStats.");
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

				query.append("assetTagStats.assetCount DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tagId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					assetTagStats);

			AssetTagStats[] array = new AssetTagStatsImpl[3];

			array[0] = (AssetTagStats)objArray[0];
			array[1] = (AssetTagStats)objArray[1];
			array[2] = (AssetTagStats)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetTagStats> findByClassNameId(long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(classNameId) };

		List<AssetTagStats> list = (List<AssetTagStats>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_CLASSNAMEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetTagStats FROM AssetTagStats assetTagStats WHERE ");

				query.append("assetTagStats.classNameId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("assetTagStats.assetCount DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetTagStats>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_CLASSNAMEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetTagStats> findByClassNameId(long classNameId, int start,
		int end) throws SystemException {
		return findByClassNameId(classNameId, start, end, null);
	}

	public List<AssetTagStats> findByClassNameId(long classNameId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AssetTagStats> list = (List<AssetTagStats>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_CLASSNAMEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetTagStats FROM AssetTagStats assetTagStats WHERE ");

				query.append("assetTagStats.classNameId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("assetTagStats.");
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

					query.append("assetTagStats.assetCount DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				list = (List<AssetTagStats>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetTagStats>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_CLASSNAMEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetTagStats findByClassNameId_First(long classNameId,
		OrderByComparator obc) throws NoSuchTagStatsException, SystemException {
		List<AssetTagStats> list = findByClassNameId(classNameId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetTagStats exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTagStatsException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetTagStats findByClassNameId_Last(long classNameId,
		OrderByComparator obc) throws NoSuchTagStatsException, SystemException {
		int count = countByClassNameId(classNameId);

		List<AssetTagStats> list = findByClassNameId(classNameId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetTagStats exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTagStatsException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetTagStats[] findByClassNameId_PrevAndNext(long tagStatsId,
		long classNameId, OrderByComparator obc)
		throws NoSuchTagStatsException, SystemException {
		AssetTagStats assetTagStats = findByPrimaryKey(tagStatsId);

		int count = countByClassNameId(classNameId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT assetTagStats FROM AssetTagStats assetTagStats WHERE ");

			query.append("assetTagStats.classNameId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("assetTagStats.");
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

				query.append("assetTagStats.assetCount DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					assetTagStats);

			AssetTagStats[] array = new AssetTagStatsImpl[3];

			array[0] = (AssetTagStats)objArray[0];
			array[1] = (AssetTagStats)objArray[1];
			array[2] = (AssetTagStats)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AssetTagStats findByT_C(long tagId, long classNameId)
		throws NoSuchTagStatsException, SystemException {
		AssetTagStats assetTagStats = fetchByT_C(tagId, classNameId);

		if (assetTagStats == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetTagStats exists with the key {");

			msg.append("tagId=" + tagId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTagStatsException(msg.toString());
		}

		return assetTagStats;
	}

	public AssetTagStats fetchByT_C(long tagId, long classNameId)
		throws SystemException {
		return fetchByT_C(tagId, classNameId, true);
	}

	public AssetTagStats fetchByT_C(long tagId, long classNameId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tagId), new Long(classNameId)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_T_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetTagStats FROM AssetTagStats assetTagStats WHERE ");

				query.append("assetTagStats.tagId = ?");

				query.append(" AND ");

				query.append("assetTagStats.classNameId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("assetTagStats.assetCount DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				qPos.add(classNameId);

				List<AssetTagStats> list = q.list();

				result = list;

				AssetTagStats assetTagStats = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C,
						finderArgs, list);
				}
				else {
					assetTagStats = list.get(0);

					cacheResult(assetTagStats);

					if ((assetTagStats.getTagId() != tagId) ||
							(assetTagStats.getClassNameId() != classNameId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C,
							finderArgs, assetTagStats);
					}
				}

				return assetTagStats;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C,
						finderArgs, new ArrayList<AssetTagStats>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (AssetTagStats)result;
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

	public List<AssetTagStats> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AssetTagStats> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<AssetTagStats> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AssetTagStats> list = (List<AssetTagStats>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetTagStats FROM AssetTagStats assetTagStats ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("assetTagStats.");
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

					query.append("assetTagStats.assetCount DESC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<AssetTagStats>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AssetTagStats>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetTagStats>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByTagId(long tagId) throws SystemException {
		for (AssetTagStats assetTagStats : findByTagId(tagId)) {
			remove(assetTagStats);
		}
	}

	public void removeByClassNameId(long classNameId) throws SystemException {
		for (AssetTagStats assetTagStats : findByClassNameId(classNameId)) {
			remove(assetTagStats);
		}
	}

	public void removeByT_C(long tagId, long classNameId)
		throws NoSuchTagStatsException, SystemException {
		AssetTagStats assetTagStats = findByT_C(tagId, classNameId);

		remove(assetTagStats);
	}

	public void removeAll() throws SystemException {
		for (AssetTagStats assetTagStats : findAll()) {
			remove(assetTagStats);
		}
	}

	public int countByTagId(long tagId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tagId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TAGID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(assetTagStats) ");
				query.append("FROM AssetTagStats assetTagStats WHERE ");

				query.append("assetTagStats.tagId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TAGID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByClassNameId(long classNameId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(classNameId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CLASSNAMEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(assetTagStats) ");
				query.append("FROM AssetTagStats assetTagStats WHERE ");

				query.append("assetTagStats.classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CLASSNAMEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByT_C(long tagId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tagId), new Long(classNameId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(assetTagStats) ");
				query.append("FROM AssetTagStats assetTagStats WHERE ");

				query.append("assetTagStats.tagId = ?");

				query.append(" AND ");

				query.append("assetTagStats.classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_C, finderArgs,
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
						"SELECT COUNT(assetTagStats) FROM AssetTagStats assetTagStats");

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
						"value.object.listener.com.liferay.portlet.asset.model.AssetTagStats")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetTagStats>> listenersList = new ArrayList<ModelListener<AssetTagStats>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetTagStats>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetEntryPersistence assetEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetTagPersistence assetTagPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPropertyPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetTagPropertyPersistence assetTagPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagStatsPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetTagStatsPersistence assetTagStatsPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetVocabularyPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(AssetTagStatsPersistenceImpl.class);
}