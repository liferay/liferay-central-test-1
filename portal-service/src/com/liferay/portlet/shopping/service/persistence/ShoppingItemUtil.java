/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

/**
 * <a href="ShoppingItemUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingItemUtil {
	public static com.liferay.portlet.shopping.model.ShoppingItem create(
		long itemId) {
		return getPersistence().create(itemId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem remove(
		long itemId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().remove(itemId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem remove(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(shoppingItem);
	}

	/**
	 * @deprecated Use <code>update(ShoppingItem shoppingItem, boolean merge)</code>.
	 */
	public static com.liferay.portlet.shopping.model.ShoppingItem update(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(shoppingItem);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        shoppingItem the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when shoppingItem is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.shopping.model.ShoppingItem update(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(shoppingItem, merge);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem updateImpl(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(shoppingItem, merge);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByPrimaryKey(
		long itemId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findByPrimaryKey(itemId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByPrimaryKey(
		long itemId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(itemId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findByCategoryId(
		long categoryId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findByCategoryId(
		long categoryId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findByCategoryId(
		long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId, start, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByCategoryId_First(
		long categoryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findByCategoryId_First(categoryId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByCategoryId_Last(
		long categoryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findByCategoryId_Last(categoryId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem[] findByCategoryId_PrevAndNext(
		long itemId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence()
				   .findByCategoryId_PrevAndNext(itemId, categoryId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findBySmallImageId(smallImageId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem fetchBySmallImageId(
		long smallImageId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchBySmallImageId(smallImageId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByMediumImageId(
		long mediumImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findByMediumImageId(mediumImageId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByMediumImageId(
		long mediumImageId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByMediumImageId(mediumImageId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findByLargeImageId(largeImageId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByLargeImageId(
		long largeImageId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByLargeImageId(largeImageId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByC_S(
		long companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findByC_S(companyId, sku);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByC_S(
		long companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_S(companyId, sku);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findWithDynamicQuery(queryInitializer, start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCategoryId(long categoryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCategoryId(categoryId);
	}

	public static void removeBySmallImageId(long smallImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		getPersistence().removeBySmallImageId(smallImageId);
	}

	public static void removeByMediumImageId(long mediumImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		getPersistence().removeByMediumImageId(mediumImageId);
	}

	public static void removeByLargeImageId(long largeImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		getPersistence().removeByLargeImageId(largeImageId);
	}

	public static void removeByC_S(long companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		getPersistence().removeByC_S(companyId, sku);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCategoryId(long categoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCategoryId(categoryId);
	}

	public static int countBySmallImageId(long smallImageId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countBySmallImageId(smallImageId);
	}

	public static int countByMediumImageId(long mediumImageId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByMediumImageId(mediumImageId);
	}

	public static int countByLargeImageId(long largeImageId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByLargeImageId(largeImageId);
	}

	public static int countByC_S(long companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_S(companyId, sku);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getShoppingItemPrices(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getShoppingItemPrices(pk);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getShoppingItemPrices(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getShoppingItemPrices(pk, start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getShoppingItemPrices(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getShoppingItemPrices(pk, start, end, obc);
	}

	public static int getShoppingItemPricesSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getShoppingItemPricesSize(pk);
	}

	public static boolean containsShoppingItemPrice(long pk,
		long shoppingItemPricePK) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .containsShoppingItemPrice(pk, shoppingItemPricePK);
	}

	public static boolean containsShoppingItemPrices(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsShoppingItemPrices(pk);
	}

	public static void registerListener(
		com.liferay.portal.model.ModelListener listener) {
		getPersistence().registerListener(listener);
	}

	public static void unregisterListener(
		com.liferay.portal.model.ModelListener listener) {
		getPersistence().unregisterListener(listener);
	}

	public static ShoppingItemPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(ShoppingItemPersistence persistence) {
		_persistence = persistence;
	}

	private static ShoppingItemUtil _getUtil() {
		if (_util == null) {
			_util = (ShoppingItemUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = ShoppingItemUtil.class.getName();
	private static ShoppingItemUtil _util;
	private ShoppingItemPersistence _persistence;
}