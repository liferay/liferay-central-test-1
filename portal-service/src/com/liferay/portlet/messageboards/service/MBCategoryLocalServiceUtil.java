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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the message boards category local service. This utility wraps {@link com.liferay.portlet.messageboards.service.impl.MBCategoryLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.messageboards.service.impl.MBCategoryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBCategoryLocalService
 * @see com.liferay.portlet.messageboards.service.base.MBCategoryLocalServiceBaseImpl
 * @see com.liferay.portlet.messageboards.service.impl.MBCategoryLocalServiceImpl
 * @generated
 */
public class MBCategoryLocalServiceUtil {
	/**
	* Adds the message boards category to the database. Also notifies the appropriate model listeners.
	*
	* @param mbCategory the message boards category to add
	* @return the message boards category that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory addMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addMBCategory(mbCategory);
	}

	/**
	* Creates a new message boards category with the primary key. Does not add the message boards category to the database.
	*
	* @param categoryId the primary key for the new message boards category
	* @return the new message boards category
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory createMBCategory(
		long categoryId) {
		return getService().createMBCategory(categoryId);
	}

	/**
	* Deletes the message boards category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryId the primary key of the message boards category to delete
	* @throws PortalException if a message boards category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteMBCategory(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMBCategory(categoryId);
	}

	/**
	* Deletes the message boards category from the database. Also notifies the appropriate model listeners.
	*
	* @param mbCategory the message boards category to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMBCategory(mbCategory);
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
	* Gets the message boards category with the primary key.
	*
	* @param categoryId the primary key of the message boards category to get
	* @return the message boards category
	* @throws PortalException if a message boards category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory getMBCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBCategory(categoryId);
	}

	/**
	* Gets the message boards category with the UUID and group id.
	*
	* @param uuid the UUID of message boards category to get
	* @param groupId the group id of the message boards category to get
	* @return the message boards category
	* @throws PortalException if a message boards category with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory getMBCategoryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBCategoryByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Gets a range of all the message boards categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards categories to return
	* @param end the upper bound of the range of message boards categories to return (not inclusive)
	* @return the range of message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getMBCategories(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBCategories(start, end);
	}

	/**
	* Gets the number of message boards categories.
	*
	* @return the number of message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static int getMBCategoriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBCategoriesCount();
	}

	/**
	* Updates the message boards category in the database. Also notifies the appropriate model listeners.
	*
	* @param mbCategory the message boards category to update
	* @return the message boards category that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory updateMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateMBCategory(mbCategory);
	}

	/**
	* Updates the message boards category in the database. Also notifies the appropriate model listeners.
	*
	* @param mbCategory the message boards category to update
	* @param merge whether to merge the message boards category with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the message boards category that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory updateMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateMBCategory(mbCategory, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String emailAddress,
		java.lang.String inProtocol, java.lang.String inServerName,
		int inServerPort, boolean inUseSSL, java.lang.String inUserName,
		java.lang.String inPassword, int inReadInterval,
		java.lang.String outEmailAddress, boolean outCustom,
		java.lang.String outServerName, int outServerPort, boolean outUseSSL,
		java.lang.String outUserName, java.lang.String outPassword,
		boolean mailingListActive,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addCategory(userId, parentCategoryId, name, description,
			emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
			inUserName, inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive, serviceContext);
	}

	public static void addCategoryResources(long categoryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addCategoryResources(categoryId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addCategoryResources(long categoryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addCategoryResources(categoryId, communityPermissions,
			guestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addCategoryResources(category, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addCategoryResources(category, communityPermissions,
			guestPermissions);
	}

	public static void deleteCategories(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategories(groupId);
	}

	public static void deleteCategory(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategory(categoryId);
	}

	public static void deleteCategory(
		com.liferay.portlet.messageboards.model.MBCategory category)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategory(category);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategories(groupId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategories(groupId, parentCategoryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategories(groupId, parentCategoryIds, start, end);
	}

	public static int getCategoriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoriesCount(groupId);
	}

	public static int getCategoriesCount(long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoriesCount(groupId, parentCategoryId);
	}

	public static int getCategoriesCount(long groupId, long[] parentCategoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoriesCount(groupId, parentCategoryIds);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory getCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategory(categoryId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCompanyCategories(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyCategories(companyId, start, end);
	}

	public static int getCompanyCategoriesCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyCategoriesCount(companyId);
	}

	public static java.util.List<java.lang.Long> getSubcategoryIds(
		java.util.List<java.lang.Long> categoryIds, long groupId,
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSubcategoryIds(categoryIds, groupId, categoryId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getSubscribedCategories(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSubscribedCategories(groupId, userId, start, end);
	}

	public static int getSubscribedCategoriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSubscribedCategoriesCount(groupId, userId);
	}

	public static void subscribeCategory(long userId, long groupId,
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().subscribeCategory(userId, groupId, categoryId);
	}

	public static void unsubscribeCategory(long userId, long groupId,
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsubscribeCategory(userId, groupId, categoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String emailAddress,
		java.lang.String inProtocol, java.lang.String inServerName,
		int inServerPort, boolean inUseSSL, java.lang.String inUserName,
		java.lang.String inPassword, int inReadInterval,
		java.lang.String outEmailAddress, boolean outCustom,
		java.lang.String outServerName, int outServerPort, boolean outUseSSL,
		java.lang.String outUserName, java.lang.String outPassword,
		boolean mailingListActive, boolean mergeWithParentCategory,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateCategory(categoryId, parentCategoryId, name,
			description, emailAddress, inProtocol, inServerName, inServerPort,
			inUseSSL, inUserName, inPassword, inReadInterval, outEmailAddress,
			outCustom, outServerName, outServerPort, outUseSSL, outUserName,
			outPassword, mailingListActive, mergeWithParentCategory,
			serviceContext);
	}

	public static MBCategoryLocalService getService() {
		if (_service == null) {
			_service = (MBCategoryLocalService)PortalBeanLocatorUtil.locate(MBCategoryLocalService.class.getName());

			ReferenceRegistry.registerReference(MBCategoryLocalServiceUtil.class,
				"_service");
			MethodCache.remove(MBCategoryLocalService.class);
		}

		return _service;
	}

	public void setService(MBCategoryLocalService service) {
		MethodCache.remove(MBCategoryLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(MBCategoryLocalServiceUtil.class,
			"_service");
		MethodCache.remove(MBCategoryLocalService.class);
	}

	private static MBCategoryLocalService _service;
}