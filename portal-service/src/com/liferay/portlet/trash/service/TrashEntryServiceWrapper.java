/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.trash.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link TrashEntryService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TrashEntryService
 * @generated
 */
public class TrashEntryServiceWrapper implements TrashEntryService,
	ServiceWrapper<TrashEntryService> {
	public TrashEntryServiceWrapper(TrashEntryService trashEntryService) {
		_trashEntryService = trashEntryService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _trashEntryService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_trashEntryService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Deletes the trash entries with the matching group ID considering
	* permissions.
	*
	* @param groupId the primary key of the group
	* @throws SystemException if a system exception occurred
	* @throws PrincipalException if a principal exception occurred
	*/
	public void deleteEntries(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.security.auth.PrincipalException {
		_trashEntryService.deleteEntries(groupId);
	}

	/**
	* Returns the trash entries with the matching group ID.
	*
	* @param groupId the primary key of the group
	* @return the matching trash entries
	* @throws SystemException if a system exception occurred
	* @throws PrincipalException if a principal exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashEntryList getEntries(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.security.auth.PrincipalException {
		return _trashEntryService.getEntries(groupId);
	}

	/**
	* Returns a range of all the trash entries matching the group ID.
	*
	* @param groupId the primary key of the group
	* @param start the lower bound of the range of trash entries to return
	* @param end the upper bound of the range of trash entries to return (not
	inclusive)
	* @param obc the comparator to order the trash entries (optionally
	<code>null</code>)
	* @return the range of matching trash entries ordered by comparator
	<code>obc</code>
	* @throws SystemException if a system exception occurred
	* @throws PrincipalException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashEntryList getEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.security.auth.PrincipalException {
		return _trashEntryService.getEntries(groupId, start, end, obc);
	}

	/**
	* Returns an ordered range of all the trash entries which match the
	* keywords and the group ID, using the indexer. It is preferable to use
	* this method instead of the non-indexed version whenever possible for
	* performance reasons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the company
	* @param groupId the primary key of the group
	* @param userId the primary key of the user
	* @param keywords the keywords (space separated), which may occur in the
	user's first name, middle name, last name, screen name, or email
	address
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param sort the field and direction to sort by (optionally
	<code>null</code>)
	* @return the matching users
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portlet.usersadmin.util.UserIndexer
	*/
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, long userId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _trashEntryService.search(companyId, groupId, userId, keywords,
			start, end, sort);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public TrashEntryService getWrappedTrashEntryService() {
		return _trashEntryService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedTrashEntryService(TrashEntryService trashEntryService) {
		_trashEntryService = trashEntryService;
	}

	public TrashEntryService getWrappedService() {
		return _trashEntryService;
	}

	public void setWrappedService(TrashEntryService trashEntryService) {
		_trashEntryService = trashEntryService;
	}

	private TrashEntryService _trashEntryService;
}