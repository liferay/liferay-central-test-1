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

package com.liferay.portlet.shopping.service;

/**
 * <p>
 * This class is a wrapper for {@link ShoppingItemLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItemLocalService
 * @generated
 */
public class ShoppingItemLocalServiceWrapper implements ShoppingItemLocalService {
	public ShoppingItemLocalServiceWrapper(
		ShoppingItemLocalService shoppingItemLocalService) {
		_shoppingItemLocalService = shoppingItemLocalService;
	}

	public com.liferay.portlet.shopping.model.ShoppingItem addShoppingItem(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.addShoppingItem(shoppingItem);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem createShoppingItem(
		long itemId) {
		return _shoppingItemLocalService.createShoppingItem(itemId);
	}

	public void deleteShoppingItem(long itemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingItemLocalService.deleteShoppingItem(itemId);
	}

	public void deleteShoppingItem(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		_shoppingItemLocalService.deleteShoppingItem(shoppingItem);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem getShoppingItem(
		long itemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.getShoppingItem(itemId);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> getShoppingItems(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.getShoppingItems(start, end);
	}

	public int getShoppingItemsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.getShoppingItemsCount();
	}

	public com.liferay.portlet.shopping.model.ShoppingItem updateShoppingItem(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.updateShoppingItem(shoppingItem);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem updateShoppingItem(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.updateShoppingItem(shoppingItem, merge);
	}

	public void addBookItems(long userId, long groupId, long categoryId,
		java.lang.String[] isbns)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingItemLocalService.addBookItems(userId, groupId, categoryId,
			isbns);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem addItem(
		long userId, long groupId, long categoryId, java.lang.String sku,
		java.lang.String name, java.lang.String description,
		java.lang.String properties, java.lang.String fieldsQuantities,
		boolean requiresShipping, int stockQuantity, boolean featured,
		java.lang.Boolean sale, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile,
		boolean mediumImage, java.lang.String mediumImageURL,
		java.io.File mediumFile, boolean largeImage,
		java.lang.String largeImageURL, java.io.File largeFile,
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> itemFields,
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> itemPrices,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.addItem(userId, groupId, categoryId,
			sku, name, description, properties, fieldsQuantities,
			requiresShipping, stockQuantity, featured, sale, smallImage,
			smallImageURL, smallFile, mediumImage, mediumImageURL, mediumFile,
			largeImage, largeImageURL, largeFile, itemFields, itemPrices,
			serviceContext);
	}

	public void addItemResources(long itemId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingItemLocalService.addItemResources(itemId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addItemResources(long itemId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingItemLocalService.addItemResources(itemId,
			communityPermissions, guestPermissions);
	}

	public void addItemResources(
		com.liferay.portlet.shopping.model.ShoppingItem item,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingItemLocalService.addItemResources(item,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addItemResources(
		com.liferay.portlet.shopping.model.ShoppingItem item,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingItemLocalService.addItemResources(item, communityPermissions,
			guestPermissions);
	}

	public void deleteItem(long itemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingItemLocalService.deleteItem(itemId);
	}

	public void deleteItem(com.liferay.portlet.shopping.model.ShoppingItem item)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingItemLocalService.deleteItem(item);
	}

	public void deleteItems(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingItemLocalService.deleteItems(groupId, categoryId);
	}

	public int getCategoriesItemsCount(long groupId,
		java.util.List<java.lang.Long> categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.getCategoriesItemsCount(groupId,
			categoryIds);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> getFeaturedItems(
		long groupId, long categoryId, int numOfItems)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.getFeaturedItems(groupId, categoryId,
			numOfItems);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem getItem(long itemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.getItem(itemId);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem getItem(
		long companyId, java.lang.String sku)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.getItem(companyId, sku);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem getItemByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.getItemByLargeImageId(largeImageId);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem getItemByMediumImageId(
		long mediumImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.getItemByMediumImageId(mediumImageId);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem getItemBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.getItemBySmallImageId(smallImageId);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> getItems(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.getItems(groupId, categoryId);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> getItems(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.getItems(groupId, categoryId, start,
			end, obc);
	}

	public int getItemsCount(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.getItemsCount(groupId, categoryId);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem[] getItemsPrevAndNext(
		long itemId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.getItemsPrevAndNext(itemId, obc);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> getSaleItems(
		long groupId, long categoryId, int numOfItems)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.getSaleItems(groupId, categoryId,
			numOfItems);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> search(
		long groupId, long[] categoryIds, java.lang.String keywords, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.search(groupId, categoryIds, keywords,
			start, end);
	}

	public int searchCount(long groupId, long[] categoryIds,
		java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.searchCount(groupId, categoryIds,
			keywords);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem updateItem(
		long userId, long itemId, long groupId, long categoryId,
		java.lang.String sku, java.lang.String name,
		java.lang.String description, java.lang.String properties,
		java.lang.String fieldsQuantities, boolean requiresShipping,
		int stockQuantity, boolean featured, java.lang.Boolean sale,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, boolean mediumImage,
		java.lang.String mediumImageURL, java.io.File mediumFile,
		boolean largeImage, java.lang.String largeImageURL,
		java.io.File largeFile,
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> itemFields,
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> itemPrices,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItemLocalService.updateItem(userId, itemId, groupId,
			categoryId, sku, name, description, properties, fieldsQuantities,
			requiresShipping, stockQuantity, featured, sale, smallImage,
			smallImageURL, smallFile, mediumImage, mediumImageURL, mediumFile,
			largeImage, largeImageURL, largeFile, itemFields, itemPrices,
			serviceContext);
	}

	public ShoppingItemLocalService getWrappedShoppingItemLocalService() {
		return _shoppingItemLocalService;
	}

	private ShoppingItemLocalService _shoppingItemLocalService;
}