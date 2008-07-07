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
 * <a href="ShoppingItemPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface ShoppingItemPersistence {
	public com.liferay.portlet.shopping.model.ShoppingItem create(long itemId);

	public com.liferay.portlet.shopping.model.ShoppingItem remove(long itemId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException;

	public com.liferay.portlet.shopping.model.ShoppingItem remove(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(ShoppingItem shoppingItem, boolean merge)</code>.
	 */
	public com.liferay.portlet.shopping.model.ShoppingItem update(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem)
		throws com.liferay.portal.SystemException;

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
	public com.liferay.portlet.shopping.model.ShoppingItem update(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingItem updateImpl(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingItem findByPrimaryKey(
		long itemId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException;

	public com.liferay.portlet.shopping.model.ShoppingItem fetchByPrimaryKey(
		long itemId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findByCategoryId(
		long categoryId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findByCategoryId(
		long categoryId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findByCategoryId(
		long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingItem findByCategoryId_First(
		long categoryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException;

	public com.liferay.portlet.shopping.model.ShoppingItem findByCategoryId_Last(
		long categoryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException;

	public com.liferay.portlet.shopping.model.ShoppingItem[] findByCategoryId_PrevAndNext(
		long itemId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException;

	public com.liferay.portlet.shopping.model.ShoppingItem findBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException;

	public com.liferay.portlet.shopping.model.ShoppingItem fetchBySmallImageId(
		long smallImageId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingItem findByMediumImageId(
		long mediumImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException;

	public com.liferay.portlet.shopping.model.ShoppingItem fetchByMediumImageId(
		long mediumImageId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingItem findByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException;

	public com.liferay.portlet.shopping.model.ShoppingItem fetchByLargeImageId(
		long largeImageId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingItem findByC_S(
		long companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException;

	public com.liferay.portlet.shopping.model.ShoppingItem fetchByC_S(
		long companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByCategoryId(long categoryId)
		throws com.liferay.portal.SystemException;

	public void removeBySmallImageId(long smallImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException;

	public void removeByMediumImageId(long mediumImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException;

	public void removeByLargeImageId(long largeImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException;

	public void removeByC_S(long companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByCategoryId(long categoryId)
		throws com.liferay.portal.SystemException;

	public int countBySmallImageId(long smallImageId)
		throws com.liferay.portal.SystemException;

	public int countByMediumImageId(long mediumImageId)
		throws com.liferay.portal.SystemException;

	public int countByLargeImageId(long largeImageId)
		throws com.liferay.portal.SystemException;

	public int countByC_S(long companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getShoppingItemPrices(
		long pk) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getShoppingItemPrices(
		long pk, int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getShoppingItemPrices(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int getShoppingItemPricesSize(long pk)
		throws com.liferay.portal.SystemException;

	public boolean containsShoppingItemPrice(long pk, long shoppingItemPricePK)
		throws com.liferay.portal.SystemException;

	public boolean containsShoppingItemPrices(long pk)
		throws com.liferay.portal.SystemException;

	public void registerListener(
		com.liferay.portal.model.ModelListener listener);

	public void unregisterListener(
		com.liferay.portal.model.ModelListener listener);
}