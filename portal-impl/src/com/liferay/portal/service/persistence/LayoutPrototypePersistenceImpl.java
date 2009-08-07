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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchLayoutPrototypeException;
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
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutPrototypeImpl;
import com.liferay.portal.model.impl.LayoutPrototypeModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="LayoutPrototypePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    LayoutPrototypePersistence
 * @see    LayoutPrototypeUtil
 * @generated
 */
public class LayoutPrototypePersistenceImpl extends BasePersistenceImpl
	implements LayoutPrototypePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutPrototypeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_A = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_A = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_A = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(LayoutPrototype layoutPrototype) {
		EntityCacheUtil.putResult(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeImpl.class, layoutPrototype.getPrimaryKey(),
			layoutPrototype);
	}

	public void cacheResult(List<LayoutPrototype> layoutPrototypes) {
		for (LayoutPrototype layoutPrototype : layoutPrototypes) {
			if (EntityCacheUtil.getResult(
						LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
						LayoutPrototypeImpl.class,
						layoutPrototype.getPrimaryKey(), this) == null) {
				cacheResult(layoutPrototype);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(LayoutPrototypeImpl.class.getName());
		EntityCacheUtil.clearCache(LayoutPrototypeImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public LayoutPrototype create(long layoutPrototypeId) {
		LayoutPrototype layoutPrototype = new LayoutPrototypeImpl();

		layoutPrototype.setNew(true);
		layoutPrototype.setPrimaryKey(layoutPrototypeId);

		return layoutPrototype;
	}

	public LayoutPrototype remove(long layoutPrototypeId)
		throws NoSuchLayoutPrototypeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LayoutPrototype layoutPrototype = (LayoutPrototype)session.get(LayoutPrototypeImpl.class,
					new Long(layoutPrototypeId));

			if (layoutPrototype == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No LayoutPrototype exists with the primary key " +
						layoutPrototypeId);
				}

				throw new NoSuchLayoutPrototypeException(
					"No LayoutPrototype exists with the primary key " +
					layoutPrototypeId);
			}

			return remove(layoutPrototype);
		}
		catch (NoSuchLayoutPrototypeException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public LayoutPrototype remove(LayoutPrototype layoutPrototype)
		throws SystemException {
		for (ModelListener<LayoutPrototype> listener : listeners) {
			listener.onBeforeRemove(layoutPrototype);
		}

		layoutPrototype = removeImpl(layoutPrototype);

		for (ModelListener<LayoutPrototype> listener : listeners) {
			listener.onAfterRemove(layoutPrototype);
		}

		return layoutPrototype;
	}

	protected LayoutPrototype removeImpl(LayoutPrototype layoutPrototype)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (layoutPrototype.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(LayoutPrototypeImpl.class,
						layoutPrototype.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(layoutPrototype);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeImpl.class, layoutPrototype.getPrimaryKey());

		return layoutPrototype;
	}

	/**
	 * @deprecated Use {@link #update(LayoutPrototype, boolean merge)}.
	 */
	public LayoutPrototype update(LayoutPrototype layoutPrototype)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(LayoutPrototype layoutPrototype) method. Use update(LayoutPrototype layoutPrototype, boolean merge) instead.");
		}

		return update(layoutPrototype, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  layoutPrototype the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when layoutPrototype is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public LayoutPrototype update(LayoutPrototype layoutPrototype, boolean merge)
		throws SystemException {
		boolean isNew = layoutPrototype.isNew();

		for (ModelListener<LayoutPrototype> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(layoutPrototype);
			}
			else {
				listener.onBeforeUpdate(layoutPrototype);
			}
		}

		layoutPrototype = updateImpl(layoutPrototype, merge);

		for (ModelListener<LayoutPrototype> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(layoutPrototype);
			}
			else {
				listener.onAfterUpdate(layoutPrototype);
			}
		}

		return layoutPrototype;
	}

	public LayoutPrototype updateImpl(
		com.liferay.portal.model.LayoutPrototype layoutPrototype, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, layoutPrototype, merge);

			layoutPrototype.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeImpl.class, layoutPrototype.getPrimaryKey(),
			layoutPrototype);

		return layoutPrototype;
	}

	public LayoutPrototype findByPrimaryKey(long layoutPrototypeId)
		throws NoSuchLayoutPrototypeException, SystemException {
		LayoutPrototype layoutPrototype = fetchByPrimaryKey(layoutPrototypeId);

		if (layoutPrototype == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No LayoutPrototype exists with the primary key " +
					layoutPrototypeId);
			}

			throw new NoSuchLayoutPrototypeException(
				"No LayoutPrototype exists with the primary key " +
				layoutPrototypeId);
		}

		return layoutPrototype;
	}

	public LayoutPrototype fetchByPrimaryKey(long layoutPrototypeId)
		throws SystemException {
		LayoutPrototype layoutPrototype = (LayoutPrototype)EntityCacheUtil.getResult(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPrototypeImpl.class, layoutPrototypeId, this);

		if (layoutPrototype == null) {
			Session session = null;

			try {
				session = openSession();

				layoutPrototype = (LayoutPrototype)session.get(LayoutPrototypeImpl.class,
						new Long(layoutPrototypeId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (layoutPrototype != null) {
					cacheResult(layoutPrototype);
				}

				closeSession(session);
			}
		}

		return layoutPrototype;
	}

	public List<LayoutPrototype> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<LayoutPrototype> list = (List<LayoutPrototype>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT layoutPrototype FROM LayoutPrototype layoutPrototype WHERE ");

				query.append("layoutPrototype.companyId = ?");

				query.append(" ");

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
					list = new ArrayList<LayoutPrototype>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<LayoutPrototype> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<LayoutPrototype> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<LayoutPrototype> list = (List<LayoutPrototype>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT layoutPrototype FROM LayoutPrototype layoutPrototype WHERE ");

				query.append("layoutPrototype.companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("layoutPrototype.");
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

				qPos.add(companyId);

				list = (List<LayoutPrototype>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutPrototype>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public LayoutPrototype findByCompanyId_First(long companyId,
		OrderByComparator obc)
		throws NoSuchLayoutPrototypeException, SystemException {
		List<LayoutPrototype> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No LayoutPrototype exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutPrototypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public LayoutPrototype findByCompanyId_Last(long companyId,
		OrderByComparator obc)
		throws NoSuchLayoutPrototypeException, SystemException {
		int count = countByCompanyId(companyId);

		List<LayoutPrototype> list = findByCompanyId(companyId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No LayoutPrototype exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutPrototypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public LayoutPrototype[] findByCompanyId_PrevAndNext(
		long layoutPrototypeId, long companyId, OrderByComparator obc)
		throws NoSuchLayoutPrototypeException, SystemException {
		LayoutPrototype layoutPrototype = findByPrimaryKey(layoutPrototypeId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT layoutPrototype FROM LayoutPrototype layoutPrototype WHERE ");

			query.append("layoutPrototype.companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("layoutPrototype.");
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

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					layoutPrototype);

			LayoutPrototype[] array = new LayoutPrototypeImpl[3];

			array[0] = (LayoutPrototype)objArray[0];
			array[1] = (LayoutPrototype)objArray[1];
			array[2] = (LayoutPrototype)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<LayoutPrototype> findByC_A(long companyId, boolean active)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(active)
			};

		List<LayoutPrototype> list = (List<LayoutPrototype>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT layoutPrototype FROM LayoutPrototype layoutPrototype WHERE ");

				query.append("layoutPrototype.companyId = ?");

				query.append(" AND ");

				query.append("layoutPrototype.active = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(active);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutPrototype>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_A, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<LayoutPrototype> findByC_A(long companyId, boolean active,
		int start, int end) throws SystemException {
		return findByC_A(companyId, active, start, end, null);
	}

	public List<LayoutPrototype> findByC_A(long companyId, boolean active,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(active),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<LayoutPrototype> list = (List<LayoutPrototype>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT layoutPrototype FROM LayoutPrototype layoutPrototype WHERE ");

				query.append("layoutPrototype.companyId = ?");

				query.append(" AND ");

				query.append("layoutPrototype.active = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("layoutPrototype.");
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

				qPos.add(companyId);

				qPos.add(active);

				list = (List<LayoutPrototype>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutPrototype>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_A,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public LayoutPrototype findByC_A_First(long companyId, boolean active,
		OrderByComparator obc)
		throws NoSuchLayoutPrototypeException, SystemException {
		List<LayoutPrototype> list = findByC_A(companyId, active, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No LayoutPrototype exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("active=" + active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutPrototypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public LayoutPrototype findByC_A_Last(long companyId, boolean active,
		OrderByComparator obc)
		throws NoSuchLayoutPrototypeException, SystemException {
		int count = countByC_A(companyId, active);

		List<LayoutPrototype> list = findByC_A(companyId, active, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No LayoutPrototype exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("active=" + active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutPrototypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public LayoutPrototype[] findByC_A_PrevAndNext(long layoutPrototypeId,
		long companyId, boolean active, OrderByComparator obc)
		throws NoSuchLayoutPrototypeException, SystemException {
		LayoutPrototype layoutPrototype = findByPrimaryKey(layoutPrototypeId);

		int count = countByC_A(companyId, active);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT layoutPrototype FROM LayoutPrototype layoutPrototype WHERE ");

			query.append("layoutPrototype.companyId = ?");

			query.append(" AND ");

			query.append("layoutPrototype.active = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("layoutPrototype.");
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

			qPos.add(companyId);

			qPos.add(active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					layoutPrototype);

			LayoutPrototype[] array = new LayoutPrototypeImpl[3];

			array[0] = (LayoutPrototype)objArray[0];
			array[1] = (LayoutPrototype)objArray[1];
			array[2] = (LayoutPrototype)objArray[2];

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

	public List<LayoutPrototype> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<LayoutPrototype> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<LayoutPrototype> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<LayoutPrototype> list = (List<LayoutPrototype>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT layoutPrototype FROM LayoutPrototype layoutPrototype ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("layoutPrototype.");
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
					list = (List<LayoutPrototype>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<LayoutPrototype>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutPrototype>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (LayoutPrototype layoutPrototype : findByCompanyId(companyId)) {
			remove(layoutPrototype);
		}
	}

	public void removeByC_A(long companyId, boolean active)
		throws SystemException {
		for (LayoutPrototype layoutPrototype : findByC_A(companyId, active)) {
			remove(layoutPrototype);
		}
	}

	public void removeAll() throws SystemException {
		for (LayoutPrototype layoutPrototype : findAll()) {
			remove(layoutPrototype);
		}
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

				query.append("SELECT COUNT(layoutPrototype) ");
				query.append("FROM LayoutPrototype layoutPrototype WHERE ");

				query.append("layoutPrototype.companyId = ?");

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

	public int countByC_A(long companyId, boolean active)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(active)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_A,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(layoutPrototype) ");
				query.append("FROM LayoutPrototype layoutPrototype WHERE ");

				query.append("layoutPrototype.companyId = ?");

				query.append(" AND ");

				query.append("layoutPrototype.active = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_A, finderArgs,
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
						"SELECT COUNT(layoutPrototype) FROM LayoutPrototype layoutPrototype");

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
						"value.object.listener.com.liferay.portal.model.LayoutPrototype")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LayoutPrototype>> listenersList = new ArrayList<ModelListener<LayoutPrototype>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LayoutPrototype>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence.impl")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence.impl")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.BrowserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence.impl")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence.impl")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence.impl")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence.impl")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPrototypePersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPrototypePersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence.impl")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence.impl")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence.impl")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence.impl")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence.impl")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence.impl")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence.impl")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence.impl")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence.impl")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence.impl")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence.impl")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence.impl")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence.impl")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence.impl")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence.impl")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	private static Log _log = LogFactoryUtil.getLog(LayoutPrototypePersistenceImpl.class);
}