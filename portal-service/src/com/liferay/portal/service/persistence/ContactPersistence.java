/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.Contact;

/**
 * The persistence interface for the contact service.
 *
 * <p>
 * Never modify this interface directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regnerate this interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ContactPersistenceImpl
 * @see ContactUtil
 * @generated
 */
public interface ContactPersistence extends BasePersistence<Contact> {
	/**
	* Caches the contact in the entity cache if it is enabled.
	*
	* @param contact the contact to cache
	*/
	public void cacheResult(com.liferay.portal.model.Contact contact);

	/**
	* Caches the contacts in the entity cache if it is enabled.
	*
	* @param contacts the contacts to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.Contact> contacts);

	/**
	* Creates a new contact with the primary key.
	*
	* @param contactId the primary key for the new contact
	* @return the new contact
	*/
	public com.liferay.portal.model.Contact create(long contactId);

	/**
	* Removes the contact with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param contactId the primary key of the contact to remove
	* @return the contact that was removed
	* @throws com.liferay.portal.NoSuchContactException if a contact with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Contact remove(long contactId)
		throws com.liferay.portal.NoSuchContactException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Contact updateImpl(
		com.liferay.portal.model.Contact contact, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the contact with the primary key or throws a {@link com.liferay.portal.NoSuchContactException} if it could not be found.
	*
	* @param contactId the primary key of the contact to find
	* @return the contact
	* @throws com.liferay.portal.NoSuchContactException if a contact with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Contact findByPrimaryKey(long contactId)
		throws com.liferay.portal.NoSuchContactException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the contact with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param contactId the primary key of the contact to find
	* @return the contact, or <code>null</code> if a contact with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Contact fetchByPrimaryKey(long contactId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the contacts where companyId = &#63;.
	*
	* @param companyId the company id to search with
	* @return the matching contacts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Contact> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the contacts where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company id to search with
	* @param start the lower bound of the range of contacts to return
	* @param end the upper bound of the range of contacts to return (not inclusive)
	* @return the range of matching contacts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Contact> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the contacts where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company id to search with
	* @param start the lower bound of the range of contacts to return
	* @param end the upper bound of the range of contacts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching contacts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Contact> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first contact in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching contact
	* @throws com.liferay.portal.NoSuchContactException if a matching contact could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Contact findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchContactException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last contact in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching contact
	* @throws com.liferay.portal.NoSuchContactException if a matching contact could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Contact findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchContactException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the contacts before and after the current contact in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param contactId the primary key of the current contact
	* @param companyId the company id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next contact
	* @throws com.liferay.portal.NoSuchContactException if a contact with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Contact[] findByCompanyId_PrevAndNext(
		long contactId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchContactException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the contacts.
	*
	* @return the contacts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Contact> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the contacts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of contacts to return
	* @param end the upper bound of the range of contacts to return (not inclusive)
	* @return the range of contacts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Contact> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the contacts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of contacts to return
	* @param end the upper bound of the range of contacts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of contacts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Contact> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the contacts where companyId = &#63; from the database.
	*
	* @param companyId the company id to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the contacts from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the contacts where companyId = &#63;.
	*
	* @param companyId the company id to search with
	* @return the number of matching contacts
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the contacts.
	*
	* @return the number of contacts
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}