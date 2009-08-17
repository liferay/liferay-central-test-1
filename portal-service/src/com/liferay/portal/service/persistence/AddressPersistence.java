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


/**
 * <a href="AddressPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AddressPersistenceImpl
 * @see       AddressUtil
 * @generated
 */
public interface AddressPersistence extends BasePersistence {
	public void cacheResult(com.liferay.portal.model.Address address);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Address> addresses);

	public void clearCache();

	public com.liferay.portal.model.Address create(long addressId);

	public com.liferay.portal.model.Address remove(long addressId)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address remove(
		com.liferay.portal.model.Address address)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use {@link #update(Address, boolean merge)}.
	 */
	public com.liferay.portal.model.Address update(
		com.liferay.portal.model.Address address)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  address the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when address is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public com.liferay.portal.model.Address update(
		com.liferay.portal.model.Address address, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address updateImpl(
		com.liferay.portal.model.Address address, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address findByPrimaryKey(long addressId)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address fetchByPrimaryKey(long addressId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address[] findByCompanyId_PrevAndNext(
		long addressId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByUserId(
		long userId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address[] findByUserId_PrevAndNext(
		long addressId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByC_C(
		long companyId, long classNameId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByC_C(
		long companyId, long classNameId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByC_C(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address findByC_C_First(long companyId,
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address findByC_C_Last(long companyId,
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address[] findByC_C_PrevAndNext(
		long addressId, long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByC_C_C(
		long companyId, long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address findByC_C_C_First(long companyId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address findByC_C_C_Last(long companyId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address[] findByC_C_C_PrevAndNext(
		long addressId, long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByC_C_C_M(
		long companyId, long classNameId, long classPK, boolean mailing)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByC_C_C_M(
		long companyId, long classNameId, long classPK, boolean mailing,
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByC_C_C_M(
		long companyId, long classNameId, long classPK, boolean mailing,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address findByC_C_C_M_First(
		long companyId, long classNameId, long classPK, boolean mailing,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address findByC_C_C_M_Last(long companyId,
		long classNameId, long classPK, boolean mailing,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address[] findByC_C_C_M_PrevAndNext(
		long addressId, long companyId, long classNameId, long classPK,
		boolean mailing, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByC_C_C_P(
		long companyId, long classNameId, long classPK, boolean primary)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByC_C_C_P(
		long companyId, long classNameId, long classPK, boolean primary,
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findByC_C_C_P(
		long companyId, long classNameId, long classPK, boolean primary,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address findByC_C_C_P_First(
		long companyId, long classNameId, long classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address findByC_C_C_P_Last(long companyId,
		long classNameId, long classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address[] findByC_C_C_P_PrevAndNext(
		long addressId, long companyId, long classNameId, long classPK,
		boolean primary, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findAll(int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Address> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public void removeByC_C(long companyId, long classNameId)
		throws com.liferay.portal.SystemException;

	public void removeByC_C_C(long companyId, long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public void removeByC_C_C_M(long companyId, long classNameId, long classPK,
		boolean mailing) throws com.liferay.portal.SystemException;

	public void removeByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary) throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public int countByC_C(long companyId, long classNameId)
		throws com.liferay.portal.SystemException;

	public int countByC_C_C(long companyId, long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public int countByC_C_C_M(long companyId, long classNameId, long classPK,
		boolean mailing) throws com.liferay.portal.SystemException;

	public int countByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary) throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}