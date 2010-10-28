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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the layout branch local service. This utility wraps {@link com.liferay.portal.service.impl.LayoutBranchLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutBranchLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutBranchLocalService
 * @see com.liferay.portal.service.base.LayoutBranchLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutBranchLocalServiceImpl
 * @generated
 */
public class LayoutBranchLocalServiceUtil {
	/**
	* Adds the layout branch to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutBranch the layout branch to add
	* @return the layout branch that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutBranch addLayoutBranch(
		com.liferay.portal.model.LayoutBranch layoutBranch)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addLayoutBranch(layoutBranch);
	}

	/**
	* Creates a new layout branch with the primary key. Does not add the layout branch to the database.
	*
	* @param layoutBranchId the primary key for the new layout branch
	* @return the new layout branch
	*/
	public static com.liferay.portal.model.LayoutBranch createLayoutBranch(
		long layoutBranchId) {
		return getService().createLayoutBranch(layoutBranchId);
	}

	/**
	* Deletes the layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutBranchId the primary key of the layout branch to delete
	* @throws PortalException if a layout branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteLayoutBranch(long layoutBranchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayoutBranch(layoutBranchId);
	}

	/**
	* Deletes the layout branch from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutBranch the layout branch to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteLayoutBranch(
		com.liferay.portal.model.LayoutBranch layoutBranch)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayoutBranch(layoutBranch);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the layout branch with the primary key.
	*
	* @param layoutBranchId the primary key of the layout branch to get
	* @return the layout branch
	* @throws PortalException if a layout branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutBranch getLayoutBranch(
		long layoutBranchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutBranch(layoutBranchId);
	}

	/**
	* Gets a range of all the layout branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of layout branchs to return
	* @param end the upper bound of the range of layout branchs to return (not inclusive)
	* @return the range of layout branchs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutBranch> getLayoutBranchs(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutBranchs(start, end);
	}

	/**
	* Gets the number of layout branchs.
	*
	* @return the number of layout branchs
	* @throws SystemException if a system exception occurred
	*/
	public static int getLayoutBranchsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutBranchsCount();
	}

	/**
	* Updates the layout branch in the database. Also notifies the appropriate model listeners.
	*
	* @param layoutBranch the layout branch to update
	* @return the layout branch that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutBranch updateLayoutBranch(
		com.liferay.portal.model.LayoutBranch layoutBranch)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateLayoutBranch(layoutBranch);
	}

	/**
	* Updates the layout branch in the database. Also notifies the appropriate model listeners.
	*
	* @param layoutBranch the layout branch to update
	* @param merge whether to merge the layout branch with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the layout branch that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutBranch updateLayoutBranch(
		com.liferay.portal.model.LayoutBranch layoutBranch, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateLayoutBranch(layoutBranch, merge);
	}

	public static com.liferay.portal.model.LayoutBranch addLayoutBranch(
		long userId, long groupId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addLayoutBranch(userId, groupId, name, description,
			serviceContext);
	}

	public static void deleteLayoutBranches(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayoutBranches(groupId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutBranch> getLayoutBranches(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutBranches(groupId);
	}

	public static com.liferay.portal.model.LayoutBranch getMasterLayoutBranch(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMasterLayoutBranch(groupId);
	}

	public static com.liferay.portal.model.LayoutBranch updateLayoutBranch(
		long layoutBranchId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLayoutBranch(layoutBranchId, name, description,
			serviceContext);
	}

	public static LayoutBranchLocalService getService() {
		if (_service == null) {
			_service = (LayoutBranchLocalService)PortalBeanLocatorUtil.locate(LayoutBranchLocalService.class.getName());

			ReferenceRegistry.registerReference(LayoutBranchLocalServiceUtil.class,
				"_service");
			MethodCache.remove(LayoutBranchLocalService.class);
		}

		return _service;
	}

	public void setService(LayoutBranchLocalService service) {
		MethodCache.remove(LayoutBranchLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(LayoutBranchLocalServiceUtil.class,
			"_service");
		MethodCache.remove(LayoutBranchLocalService.class);
	}

	private static LayoutBranchLocalService _service;
}