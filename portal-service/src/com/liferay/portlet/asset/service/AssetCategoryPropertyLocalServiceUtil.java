/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the asset category property local service. This utility wraps {@link com.liferay.portlet.asset.service.impl.AssetCategoryPropertyLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryPropertyLocalService
 * @see com.liferay.portlet.asset.service.base.AssetCategoryPropertyLocalServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetCategoryPropertyLocalServiceImpl
 * @generated
 */
public class AssetCategoryPropertyLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetCategoryPropertyLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the asset category property to the database. Also notifies the appropriate model listeners.
	*
	* @param assetCategoryProperty the asset category property to add
	* @return the asset category property that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty addAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addAssetCategoryProperty(assetCategoryProperty);
	}

	/**
	* Creates a new asset category property with the primary key. Does not add the asset category property to the database.
	*
	* @param categoryPropertyId the primary key for the new asset category property
	* @return the new asset category property
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty createAssetCategoryProperty(
		long categoryPropertyId) {
		return getService().createAssetCategoryProperty(categoryPropertyId);
	}

	/**
	* Deletes the asset category property with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryPropertyId the primary key of the asset category property to delete
	* @throws PortalException if a asset category property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteAssetCategoryProperty(long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetCategoryProperty(categoryPropertyId);
	}

	/**
	* Deletes the asset category property from the database. Also notifies the appropriate model listeners.
	*
	* @param assetCategoryProperty the asset category property to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetCategoryProperty(assetCategoryProperty);
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
	* Gets the asset category property with the primary key.
	*
	* @param categoryPropertyId the primary key of the asset category property to get
	* @return the asset category property
	* @throws PortalException if a asset category property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty getAssetCategoryProperty(
		long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetCategoryProperty(categoryPropertyId);
	}

	/**
	* Gets a range of all the asset category properties.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset category properties to return
	* @param end the upper bound of the range of asset category properties to return (not inclusive)
	* @return the range of asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getAssetCategoryProperties(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetCategoryProperties(start, end);
	}

	/**
	* Gets the number of asset category properties.
	*
	* @return the number of asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static int getAssetCategoryPropertiesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetCategoryPropertiesCount();
	}

	/**
	* Updates the asset category property in the database. Also notifies the appropriate model listeners.
	*
	* @param assetCategoryProperty the asset category property to update
	* @return the asset category property that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty updateAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAssetCategoryProperty(assetCategoryProperty);
	}

	/**
	* Updates the asset category property in the database. Also notifies the appropriate model listeners.
	*
	* @param assetCategoryProperty the asset category property to update
	* @param merge whether to merge the asset category property with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the asset category property that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty updateAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateAssetCategoryProperty(assetCategoryProperty, merge);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty addCategoryProperty(
		long userId, long categoryId, java.lang.String key,
		java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addCategoryProperty(userId, categoryId, key, value);
	}

	public static void deleteCategoryProperties(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategoryProperties(entryId);
	}

	public static void deleteCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty categoryProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategoryProperty(categoryProperty);
	}

	public static void deleteCategoryProperty(long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategoryProperty(categoryPropertyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryProperties()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoryProperties();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryProperties(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoryProperties(entryId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty getCategoryProperty(
		long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoryProperty(categoryPropertyId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty getCategoryProperty(
		long categoryId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoryProperty(categoryId, key);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryPropertyValues(
		long groupId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoryPropertyValues(groupId, key);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty updateCategoryProperty(
		long categoryPropertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateCategoryProperty(categoryPropertyId, key, value);
	}

	public static AssetCategoryPropertyLocalService getService() {
		if (_service == null) {
			_service = (AssetCategoryPropertyLocalService)PortalBeanLocatorUtil.locate(AssetCategoryPropertyLocalService.class.getName());

			ReferenceRegistry.registerReference(AssetCategoryPropertyLocalServiceUtil.class,
				"_service");
			MethodCache.remove(AssetCategoryPropertyLocalService.class);
		}

		return _service;
	}

	public void setService(AssetCategoryPropertyLocalService service) {
		MethodCache.remove(AssetCategoryPropertyLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(AssetCategoryPropertyLocalServiceUtil.class,
			"_service");
		MethodCache.remove(AssetCategoryPropertyLocalService.class);
	}

	private static AssetCategoryPropertyLocalService _service;
}