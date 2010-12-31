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

/**
 * <p>
 * This class is a wrapper for {@link AssetLinkLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetLinkLocalService
 * @generated
 */
public class AssetLinkLocalServiceWrapper implements AssetLinkLocalService {
	public AssetLinkLocalServiceWrapper(
		AssetLinkLocalService assetLinkLocalService) {
		_assetLinkLocalService = assetLinkLocalService;
	}

	/**
	* Adds the asset link to the database. Also notifies the appropriate model listeners.
	*
	* @param assetLink the asset link to add
	* @return the asset link that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink addAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.addAssetLink(assetLink);
	}

	/**
	* Creates a new asset link with the primary key. Does not add the asset link to the database.
	*
	* @param linkId the primary key for the new asset link
	* @return the new asset link
	*/
	public com.liferay.portlet.asset.model.AssetLink createAssetLink(
		long linkId) {
		return _assetLinkLocalService.createAssetLink(linkId);
	}

	/**
	* Deletes the asset link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param linkId the primary key of the asset link to delete
	* @throws PortalException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteAssetLink(long linkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetLinkLocalService.deleteAssetLink(linkId);
	}

	/**
	* Deletes the asset link from the database. Also notifies the appropriate model listeners.
	*
	* @param assetLink the asset link to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetLinkLocalService.deleteAssetLink(assetLink);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.dynamicQuery(dynamicQuery);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.dynamicQuery(dynamicQuery, start, end);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the asset link with the primary key.
	*
	* @param linkId the primary key of the asset link to get
	* @return the asset link
	* @throws PortalException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink getAssetLink(long linkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getAssetLink(linkId);
	}

	/**
	* Gets a range of all the asset links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset links to return
	* @param end the upper bound of the range of asset links to return (not inclusive)
	* @return the range of asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getAssetLinks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getAssetLinks(start, end);
	}

	/**
	* Gets the number of asset links.
	*
	* @return the number of asset links
	* @throws SystemException if a system exception occurred
	*/
	public int getAssetLinksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getAssetLinksCount();
	}

	/**
	* Updates the asset link in the database. Also notifies the appropriate model listeners.
	*
	* @param assetLink the asset link to update
	* @return the asset link that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink updateAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.updateAssetLink(assetLink);
	}

	/**
	* Updates the asset link in the database. Also notifies the appropriate model listeners.
	*
	* @param assetLink the asset link to update
	* @param merge whether to merge the asset link with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the asset link that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink updateAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.updateAssetLink(assetLink, merge);
	}

	public com.liferay.portlet.asset.model.AssetLink addLink(long userId,
		long entryId1, long entryId2, int type, int weight)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.addLink(userId, entryId1, entryId2, type,
			weight);
	}

	public void deleteLink(long linkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetLinkLocalService.deleteLink(linkId);
	}

	public void deleteLinks(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetLinkLocalService.deleteLinks(entryId);
	}

	public void deleteLinks(long entryId1, long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetLinkLocalService.deleteLinks(entryId1, entryId2);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getLinks(
		long entryId, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getLinks(entryId, typeId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getReverseLinks(
		long entryId, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getReverseLinks(entryId, typeId);
	}

	public AssetLinkLocalService getWrappedAssetLinkLocalService() {
		return _assetLinkLocalService;
	}

	public void setWrappedAssetLinkLocalService(
		AssetLinkLocalService assetLinkLocalService) {
		_assetLinkLocalService = assetLinkLocalService;
	}

	private AssetLinkLocalService _assetLinkLocalService;
}