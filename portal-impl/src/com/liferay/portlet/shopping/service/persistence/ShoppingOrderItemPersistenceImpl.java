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

package com.liferay.portlet.shopping.service.persistence;

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

import com.liferay.portlet.shopping.NoSuchOrderItemException;
import com.liferay.portlet.shopping.model.ShoppingOrderItem;
import com.liferay.portlet.shopping.model.impl.ShoppingOrderItemImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingOrderItemModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ShoppingOrderItemPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingOrderItemPersistence
 * @see       ShoppingOrderItemUtil
 * @generated
 */
public class ShoppingOrderItemPersistenceImpl extends BasePersistenceImpl
	implements ShoppingOrderItemPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ShoppingOrderItemImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_ORDERID = new FinderPath(ShoppingOrderItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderItemModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByOrderId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ORDERID = new FinderPath(ShoppingOrderItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderItemModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByOrderId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ORDERID = new FinderPath(ShoppingOrderItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderItemModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByOrderId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ShoppingOrderItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderItemModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ShoppingOrderItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderItemModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(ShoppingOrderItem shoppingOrderItem) {
		EntityCacheUtil.putResult(ShoppingOrderItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderItemImpl.class, shoppingOrderItem.getPrimaryKey(),
			shoppingOrderItem);
	}

	public void cacheResult(List<ShoppingOrderItem> shoppingOrderItems) {
		for (ShoppingOrderItem shoppingOrderItem : shoppingOrderItems) {
			if (EntityCacheUtil.getResult(
						ShoppingOrderItemModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingOrderItemImpl.class,
						shoppingOrderItem.getPrimaryKey(), this) == null) {
				cacheResult(shoppingOrderItem);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ShoppingOrderItemImpl.class.getName());
		EntityCacheUtil.clearCache(ShoppingOrderItemImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public ShoppingOrderItem create(long orderItemId) {
		ShoppingOrderItem shoppingOrderItem = new ShoppingOrderItemImpl();

		shoppingOrderItem.setNew(true);
		shoppingOrderItem.setPrimaryKey(orderItemId);

		return shoppingOrderItem;
	}

	public ShoppingOrderItem remove(long orderItemId)
		throws NoSuchOrderItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingOrderItem shoppingOrderItem = (ShoppingOrderItem)session.get(ShoppingOrderItemImpl.class,
					new Long(orderItemId));

			if (shoppingOrderItem == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No ShoppingOrderItem exists with the primary key " +
						orderItemId);
				}

				throw new NoSuchOrderItemException(
					"No ShoppingOrderItem exists with the primary key " +
					orderItemId);
			}

			return remove(shoppingOrderItem);
		}
		catch (NoSuchOrderItemException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingOrderItem remove(ShoppingOrderItem shoppingOrderItem)
		throws SystemException {
		for (ModelListener<ShoppingOrderItem> listener : listeners) {
			listener.onBeforeRemove(shoppingOrderItem);
		}

		shoppingOrderItem = removeImpl(shoppingOrderItem);

		for (ModelListener<ShoppingOrderItem> listener : listeners) {
			listener.onAfterRemove(shoppingOrderItem);
		}

		return shoppingOrderItem;
	}

	protected ShoppingOrderItem removeImpl(ShoppingOrderItem shoppingOrderItem)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (shoppingOrderItem.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ShoppingOrderItemImpl.class,
						shoppingOrderItem.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(shoppingOrderItem);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(ShoppingOrderItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderItemImpl.class, shoppingOrderItem.getPrimaryKey());

		return shoppingOrderItem;
	}

	/**
	 * @deprecated Use {@link #update(ShoppingOrderItem, boolean merge)}.
	 */
	public ShoppingOrderItem update(ShoppingOrderItem shoppingOrderItem)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ShoppingOrderItem shoppingOrderItem) method. Use update(ShoppingOrderItem shoppingOrderItem, boolean merge) instead.");
		}

		return update(shoppingOrderItem, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  shoppingOrderItem the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when shoppingOrderItem is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public ShoppingOrderItem update(ShoppingOrderItem shoppingOrderItem,
		boolean merge) throws SystemException {
		boolean isNew = shoppingOrderItem.isNew();

		for (ModelListener<ShoppingOrderItem> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(shoppingOrderItem);
			}
			else {
				listener.onBeforeUpdate(shoppingOrderItem);
			}
		}

		shoppingOrderItem = updateImpl(shoppingOrderItem, merge);

		for (ModelListener<ShoppingOrderItem> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(shoppingOrderItem);
			}
			else {
				listener.onAfterUpdate(shoppingOrderItem);
			}
		}

		return shoppingOrderItem;
	}

	public ShoppingOrderItem updateImpl(
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, shoppingOrderItem, merge);

			shoppingOrderItem.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ShoppingOrderItemModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderItemImpl.class, shoppingOrderItem.getPrimaryKey(),
			shoppingOrderItem);

		return shoppingOrderItem;
	}

	public ShoppingOrderItem findByPrimaryKey(long orderItemId)
		throws NoSuchOrderItemException, SystemException {
		ShoppingOrderItem shoppingOrderItem = fetchByPrimaryKey(orderItemId);

		if (shoppingOrderItem == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ShoppingOrderItem exists with the primary key " +
					orderItemId);
			}

			throw new NoSuchOrderItemException(
				"No ShoppingOrderItem exists with the primary key " +
				orderItemId);
		}

		return shoppingOrderItem;
	}

	public ShoppingOrderItem fetchByPrimaryKey(long orderItemId)
		throws SystemException {
		ShoppingOrderItem shoppingOrderItem = (ShoppingOrderItem)EntityCacheUtil.getResult(ShoppingOrderItemModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingOrderItemImpl.class, orderItemId, this);

		if (shoppingOrderItem == null) {
			Session session = null;

			try {
				session = openSession();

				shoppingOrderItem = (ShoppingOrderItem)session.get(ShoppingOrderItemImpl.class,
						new Long(orderItemId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (shoppingOrderItem != null) {
					cacheResult(shoppingOrderItem);
				}

				closeSession(session);
			}
		}

		return shoppingOrderItem;
	}

	public List<ShoppingOrderItem> findByOrderId(long orderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(orderId) };

		List<ShoppingOrderItem> list = (List<ShoppingOrderItem>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ORDERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT shoppingOrderItem FROM ShoppingOrderItem shoppingOrderItem WHERE ");

				query.append("shoppingOrderItem.orderId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("shoppingOrderItem.name ASC, ");
				query.append("shoppingOrderItem.description ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(orderId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingOrderItem>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ORDERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ShoppingOrderItem> findByOrderId(long orderId, int start,
		int end) throws SystemException {
		return findByOrderId(orderId, start, end, null);
	}

	public List<ShoppingOrderItem> findByOrderId(long orderId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(orderId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ShoppingOrderItem> list = (List<ShoppingOrderItem>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ORDERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT shoppingOrderItem FROM ShoppingOrderItem shoppingOrderItem WHERE ");

				query.append("shoppingOrderItem.orderId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("shoppingOrderItem.");
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

					query.append("shoppingOrderItem.name ASC, ");
					query.append("shoppingOrderItem.description ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(orderId);

				list = (List<ShoppingOrderItem>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingOrderItem>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ORDERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ShoppingOrderItem findByOrderId_First(long orderId,
		OrderByComparator obc) throws NoSuchOrderItemException, SystemException {
		List<ShoppingOrderItem> list = findByOrderId(orderId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingOrderItem exists with the key {");

			msg.append("orderId=" + orderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrderItemException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingOrderItem findByOrderId_Last(long orderId,
		OrderByComparator obc) throws NoSuchOrderItemException, SystemException {
		int count = countByOrderId(orderId);

		List<ShoppingOrderItem> list = findByOrderId(orderId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingOrderItem exists with the key {");

			msg.append("orderId=" + orderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrderItemException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingOrderItem[] findByOrderId_PrevAndNext(long orderItemId,
		long orderId, OrderByComparator obc)
		throws NoSuchOrderItemException, SystemException {
		ShoppingOrderItem shoppingOrderItem = findByPrimaryKey(orderItemId);

		int count = countByOrderId(orderId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT shoppingOrderItem FROM ShoppingOrderItem shoppingOrderItem WHERE ");

			query.append("shoppingOrderItem.orderId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("shoppingOrderItem.");
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

				query.append("shoppingOrderItem.name ASC, ");
				query.append("shoppingOrderItem.description ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(orderId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingOrderItem);

			ShoppingOrderItem[] array = new ShoppingOrderItemImpl[3];

			array[0] = (ShoppingOrderItem)objArray[0];
			array[1] = (ShoppingOrderItem)objArray[1];
			array[2] = (ShoppingOrderItem)objArray[2];

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

	public List<ShoppingOrderItem> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ShoppingOrderItem> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ShoppingOrderItem> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ShoppingOrderItem> list = (List<ShoppingOrderItem>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT shoppingOrderItem FROM ShoppingOrderItem shoppingOrderItem ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("shoppingOrderItem.");
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

					query.append("shoppingOrderItem.name ASC, ");
					query.append("shoppingOrderItem.description ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<ShoppingOrderItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ShoppingOrderItem>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingOrderItem>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByOrderId(long orderId) throws SystemException {
		for (ShoppingOrderItem shoppingOrderItem : findByOrderId(orderId)) {
			remove(shoppingOrderItem);
		}
	}

	public void removeAll() throws SystemException {
		for (ShoppingOrderItem shoppingOrderItem : findAll()) {
			remove(shoppingOrderItem);
		}
	}

	public int countByOrderId(long orderId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(orderId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ORDERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(shoppingOrderItem) ");
				query.append("FROM ShoppingOrderItem shoppingOrderItem WHERE ");

				query.append("shoppingOrderItem.orderId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(orderId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ORDERID,
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
						"SELECT COUNT(shoppingOrderItem) FROM ShoppingOrderItem shoppingOrderItem");

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
						"value.object.listener.com.liferay.portlet.shopping.model.ShoppingOrderItem")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ShoppingOrderItem>> listenersList = new ArrayList<ModelListener<ShoppingOrderItem>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ShoppingOrderItem>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence shoppingCartPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCategoryPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCategoryPersistence shoppingCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCouponPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCouponPersistence shoppingCouponPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingItemPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingItemPersistence shoppingItemPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldPersistence shoppingItemFieldPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingItemPricePersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingItemPricePersistence shoppingItemPricePersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingOrderPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingOrderPersistence shoppingOrderPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPersistence shoppingOrderItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(ShoppingOrderItemPersistenceImpl.class);
}