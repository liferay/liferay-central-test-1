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

package com.liferay.portlet.asset.model;

/**
 * <p>
 * This class is a wrapper for {@link AssetEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetEntry
 * @generated
 */
public class AssetEntryWrapper implements AssetEntry {
	public AssetEntryWrapper(AssetEntry assetEntry) {
		_assetEntry = assetEntry;
	}

	public Class<?> getModelClass() {
		return AssetEntry.class;
	}

	public String getModelClassName() {
		return AssetEntry.class.getName();
	}

	/**
	* Gets the primary key of this asset entry.
	*
	* @return the primary key of this asset entry
	*/
	public long getPrimaryKey() {
		return _assetEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this asset entry
	*
	* @param pk the primary key of this asset entry
	*/
	public void setPrimaryKey(long pk) {
		_assetEntry.setPrimaryKey(pk);
	}

	/**
	* Gets the entry ID of this asset entry.
	*
	* @return the entry ID of this asset entry
	*/
	public long getEntryId() {
		return _assetEntry.getEntryId();
	}

	/**
	* Sets the entry ID of this asset entry.
	*
	* @param entryId the entry ID of this asset entry
	*/
	public void setEntryId(long entryId) {
		_assetEntry.setEntryId(entryId);
	}

	/**
	* Gets the group ID of this asset entry.
	*
	* @return the group ID of this asset entry
	*/
	public long getGroupId() {
		return _assetEntry.getGroupId();
	}

	/**
	* Sets the group ID of this asset entry.
	*
	* @param groupId the group ID of this asset entry
	*/
	public void setGroupId(long groupId) {
		_assetEntry.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this asset entry.
	*
	* @return the company ID of this asset entry
	*/
	public long getCompanyId() {
		return _assetEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this asset entry.
	*
	* @param companyId the company ID of this asset entry
	*/
	public void setCompanyId(long companyId) {
		_assetEntry.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this asset entry.
	*
	* @return the user ID of this asset entry
	*/
	public long getUserId() {
		return _assetEntry.getUserId();
	}

	/**
	* Sets the user ID of this asset entry.
	*
	* @param userId the user ID of this asset entry
	*/
	public void setUserId(long userId) {
		_assetEntry.setUserId(userId);
	}

	/**
	* Gets the user uuid of this asset entry.
	*
	* @return the user uuid of this asset entry
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this asset entry.
	*
	* @param userUuid the user uuid of this asset entry
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_assetEntry.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this asset entry.
	*
	* @return the user name of this asset entry
	*/
	public java.lang.String getUserName() {
		return _assetEntry.getUserName();
	}

	/**
	* Sets the user name of this asset entry.
	*
	* @param userName the user name of this asset entry
	*/
	public void setUserName(java.lang.String userName) {
		_assetEntry.setUserName(userName);
	}

	/**
	* Gets the create date of this asset entry.
	*
	* @return the create date of this asset entry
	*/
	public java.util.Date getCreateDate() {
		return _assetEntry.getCreateDate();
	}

	/**
	* Sets the create date of this asset entry.
	*
	* @param createDate the create date of this asset entry
	*/
	public void setCreateDate(java.util.Date createDate) {
		_assetEntry.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this asset entry.
	*
	* @return the modified date of this asset entry
	*/
	public java.util.Date getModifiedDate() {
		return _assetEntry.getModifiedDate();
	}

	/**
	* Sets the modified date of this asset entry.
	*
	* @param modifiedDate the modified date of this asset entry
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_assetEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the class name of the model instance this asset entry is polymorphically associated with.
	*
	* @return the class name of the model instance this asset entry is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _assetEntry.getClassName();
	}

	/**
	* Gets the class name ID of this asset entry.
	*
	* @return the class name ID of this asset entry
	*/
	public long getClassNameId() {
		return _assetEntry.getClassNameId();
	}

	/**
	* Sets the class name ID of this asset entry.
	*
	* @param classNameId the class name ID of this asset entry
	*/
	public void setClassNameId(long classNameId) {
		_assetEntry.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this asset entry.
	*
	* @return the class p k of this asset entry
	*/
	public long getClassPK() {
		return _assetEntry.getClassPK();
	}

	/**
	* Sets the class p k of this asset entry.
	*
	* @param classPK the class p k of this asset entry
	*/
	public void setClassPK(long classPK) {
		_assetEntry.setClassPK(classPK);
	}

	/**
	* Gets the class uuid of this asset entry.
	*
	* @return the class uuid of this asset entry
	*/
	public java.lang.String getClassUuid() {
		return _assetEntry.getClassUuid();
	}

	/**
	* Sets the class uuid of this asset entry.
	*
	* @param classUuid the class uuid of this asset entry
	*/
	public void setClassUuid(java.lang.String classUuid) {
		_assetEntry.setClassUuid(classUuid);
	}

	/**
	* Gets the visible of this asset entry.
	*
	* @return the visible of this asset entry
	*/
	public boolean getVisible() {
		return _assetEntry.getVisible();
	}

	/**
	* Determines if this asset entry is visible.
	*
	* @return <code>true</code> if this asset entry is visible; <code>false</code> otherwise
	*/
	public boolean isVisible() {
		return _assetEntry.isVisible();
	}

	/**
	* Sets whether this asset entry is visible.
	*
	* @param visible the visible of this asset entry
	*/
	public void setVisible(boolean visible) {
		_assetEntry.setVisible(visible);
	}

	/**
	* Gets the start date of this asset entry.
	*
	* @return the start date of this asset entry
	*/
	public java.util.Date getStartDate() {
		return _assetEntry.getStartDate();
	}

	/**
	* Sets the start date of this asset entry.
	*
	* @param startDate the start date of this asset entry
	*/
	public void setStartDate(java.util.Date startDate) {
		_assetEntry.setStartDate(startDate);
	}

	/**
	* Gets the end date of this asset entry.
	*
	* @return the end date of this asset entry
	*/
	public java.util.Date getEndDate() {
		return _assetEntry.getEndDate();
	}

	/**
	* Sets the end date of this asset entry.
	*
	* @param endDate the end date of this asset entry
	*/
	public void setEndDate(java.util.Date endDate) {
		_assetEntry.setEndDate(endDate);
	}

	/**
	* Gets the publish date of this asset entry.
	*
	* @return the publish date of this asset entry
	*/
	public java.util.Date getPublishDate() {
		return _assetEntry.getPublishDate();
	}

	/**
	* Sets the publish date of this asset entry.
	*
	* @param publishDate the publish date of this asset entry
	*/
	public void setPublishDate(java.util.Date publishDate) {
		_assetEntry.setPublishDate(publishDate);
	}

	/**
	* Gets the expiration date of this asset entry.
	*
	* @return the expiration date of this asset entry
	*/
	public java.util.Date getExpirationDate() {
		return _assetEntry.getExpirationDate();
	}

	/**
	* Sets the expiration date of this asset entry.
	*
	* @param expirationDate the expiration date of this asset entry
	*/
	public void setExpirationDate(java.util.Date expirationDate) {
		_assetEntry.setExpirationDate(expirationDate);
	}

	/**
	* Gets the mime type of this asset entry.
	*
	* @return the mime type of this asset entry
	*/
	public java.lang.String getMimeType() {
		return _assetEntry.getMimeType();
	}

	/**
	* Sets the mime type of this asset entry.
	*
	* @param mimeType the mime type of this asset entry
	*/
	public void setMimeType(java.lang.String mimeType) {
		_assetEntry.setMimeType(mimeType);
	}

	/**
	* Gets the title of this asset entry.
	*
	* @return the title of this asset entry
	*/
	public java.lang.String getTitle() {
		return _assetEntry.getTitle();
	}

	/**
	* Sets the title of this asset entry.
	*
	* @param title the title of this asset entry
	*/
	public void setTitle(java.lang.String title) {
		_assetEntry.setTitle(title);
	}

	/**
	* Gets the description of this asset entry.
	*
	* @return the description of this asset entry
	*/
	public java.lang.String getDescription() {
		return _assetEntry.getDescription();
	}

	/**
	* Sets the description of this asset entry.
	*
	* @param description the description of this asset entry
	*/
	public void setDescription(java.lang.String description) {
		_assetEntry.setDescription(description);
	}

	/**
	* Gets the summary of this asset entry.
	*
	* @return the summary of this asset entry
	*/
	public java.lang.String getSummary() {
		return _assetEntry.getSummary();
	}

	/**
	* Sets the summary of this asset entry.
	*
	* @param summary the summary of this asset entry
	*/
	public void setSummary(java.lang.String summary) {
		_assetEntry.setSummary(summary);
	}

	/**
	* Gets the url of this asset entry.
	*
	* @return the url of this asset entry
	*/
	public java.lang.String getUrl() {
		return _assetEntry.getUrl();
	}

	/**
	* Sets the url of this asset entry.
	*
	* @param url the url of this asset entry
	*/
	public void setUrl(java.lang.String url) {
		_assetEntry.setUrl(url);
	}

	/**
	* Gets the height of this asset entry.
	*
	* @return the height of this asset entry
	*/
	public int getHeight() {
		return _assetEntry.getHeight();
	}

	/**
	* Sets the height of this asset entry.
	*
	* @param height the height of this asset entry
	*/
	public void setHeight(int height) {
		_assetEntry.setHeight(height);
	}

	/**
	* Gets the width of this asset entry.
	*
	* @return the width of this asset entry
	*/
	public int getWidth() {
		return _assetEntry.getWidth();
	}

	/**
	* Sets the width of this asset entry.
	*
	* @param width the width of this asset entry
	*/
	public void setWidth(int width) {
		_assetEntry.setWidth(width);
	}

	/**
	* Gets the priority of this asset entry.
	*
	* @return the priority of this asset entry
	*/
	public double getPriority() {
		return _assetEntry.getPriority();
	}

	/**
	* Sets the priority of this asset entry.
	*
	* @param priority the priority of this asset entry
	*/
	public void setPriority(double priority) {
		_assetEntry.setPriority(priority);
	}

	/**
	* Gets the view count of this asset entry.
	*
	* @return the view count of this asset entry
	*/
	public int getViewCount() {
		return _assetEntry.getViewCount();
	}

	/**
	* Sets the view count of this asset entry.
	*
	* @param viewCount the view count of this asset entry
	*/
	public void setViewCount(int viewCount) {
		_assetEntry.setViewCount(viewCount);
	}

	public boolean isNew() {
		return _assetEntry.isNew();
	}

	public void setNew(boolean n) {
		_assetEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_assetEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new AssetEntryWrapper((AssetEntry)_assetEntry.clone());
	}

	public int compareTo(com.liferay.portlet.asset.model.AssetEntry assetEntry) {
		return _assetEntry.compareTo(assetEntry);
	}

	public int hashCode() {
		return _assetEntry.hashCode();
	}

	public com.liferay.portlet.asset.model.AssetEntry toEscapedModel() {
		return new AssetEntryWrapper(_assetEntry.toEscapedModel());
	}

	public java.lang.String toString() {
		return _assetEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _assetEntry.toXmlString();
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntry.getCategories();
	}

	public long[] getCategoryIds()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntry.getCategoryIds();
	}

	public double getSocialInformationEquity() {
		return _assetEntry.getSocialInformationEquity();
	}

	public java.lang.String[] getTagNames()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntry.getTagNames();
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntry.getTags();
	}

	public void updateSocialInformationEquity(double value) {
		_assetEntry.updateSocialInformationEquity(value);
	}

	public AssetEntry getWrappedAssetEntry() {
		return _assetEntry;
	}

	private AssetEntry _assetEntry;
}