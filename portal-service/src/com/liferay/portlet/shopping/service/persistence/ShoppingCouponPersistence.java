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
 * <a href="ShoppingCouponPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface ShoppingCouponPersistence {
	public com.liferay.portlet.shopping.model.ShoppingCoupon create(
		long couponId);

	public com.liferay.portlet.shopping.model.ShoppingCoupon remove(
		long couponId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCouponException;

	public com.liferay.portlet.shopping.model.ShoppingCoupon remove(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(ShoppingCoupon shoppingCoupon, boolean merge)</code>.
	 */
	public com.liferay.portlet.shopping.model.ShoppingCoupon update(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        shoppingCoupon the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when shoppingCoupon is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portlet.shopping.model.ShoppingCoupon update(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingCoupon updateImpl(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingCoupon findByPrimaryKey(
		long couponId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCouponException;

	public com.liferay.portlet.shopping.model.ShoppingCoupon fetchByPrimaryKey(
		long couponId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingCoupon findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCouponException;

	public com.liferay.portlet.shopping.model.ShoppingCoupon findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCouponException;

	public com.liferay.portlet.shopping.model.ShoppingCoupon[] findByGroupId_PrevAndNext(
		long couponId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCouponException;

	public com.liferay.portlet.shopping.model.ShoppingCoupon findByCode(
		java.lang.String code)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCouponException;

	public com.liferay.portlet.shopping.model.ShoppingCoupon fetchByCode(
		java.lang.String code) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByCode(java.lang.String code)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCouponException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByCode(java.lang.String code)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;

	public void registerListener(
		com.liferay.portal.model.ModelListener listener);

	public void unregisterListener(
		com.liferay.portal.model.ModelListener listener);
}