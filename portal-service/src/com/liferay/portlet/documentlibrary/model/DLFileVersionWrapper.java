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

package com.liferay.portlet.documentlibrary.model;

/**
 * <p>
 * This class is a wrapper for {@link DLFileVersion}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileVersion
 * @generated
 */
public class DLFileVersionWrapper implements DLFileVersion {
	public DLFileVersionWrapper(DLFileVersion dlFileVersion) {
		_dlFileVersion = dlFileVersion;
	}

	public Class<?> getModelClass() {
		return DLFileVersion.class;
	}

	public String getModelClassName() {
		return DLFileVersion.class.getName();
	}

	/**
	* Returns the primary key of this d l file version.
	*
	* @return the primary key of this d l file version
	*/
	public long getPrimaryKey() {
		return _dlFileVersion.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d l file version.
	*
	* @param primaryKey the primary key of this d l file version
	*/
	public void setPrimaryKey(long primaryKey) {
		_dlFileVersion.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the file version ID of this d l file version.
	*
	* @return the file version ID of this d l file version
	*/
	public long getFileVersionId() {
		return _dlFileVersion.getFileVersionId();
	}

	/**
	* Sets the file version ID of this d l file version.
	*
	* @param fileVersionId the file version ID of this d l file version
	*/
	public void setFileVersionId(long fileVersionId) {
		_dlFileVersion.setFileVersionId(fileVersionId);
	}

	/**
	* Returns the group ID of this d l file version.
	*
	* @return the group ID of this d l file version
	*/
	public long getGroupId() {
		return _dlFileVersion.getGroupId();
	}

	/**
	* Sets the group ID of this d l file version.
	*
	* @param groupId the group ID of this d l file version
	*/
	public void setGroupId(long groupId) {
		_dlFileVersion.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this d l file version.
	*
	* @return the company ID of this d l file version
	*/
	public long getCompanyId() {
		return _dlFileVersion.getCompanyId();
	}

	/**
	* Sets the company ID of this d l file version.
	*
	* @param companyId the company ID of this d l file version
	*/
	public void setCompanyId(long companyId) {
		_dlFileVersion.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this d l file version.
	*
	* @return the user ID of this d l file version
	*/
	public long getUserId() {
		return _dlFileVersion.getUserId();
	}

	/**
	* Sets the user ID of this d l file version.
	*
	* @param userId the user ID of this d l file version
	*/
	public void setUserId(long userId) {
		_dlFileVersion.setUserId(userId);
	}

	/**
	* Returns the user uuid of this d l file version.
	*
	* @return the user uuid of this d l file version
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersion.getUserUuid();
	}

	/**
	* Sets the user uuid of this d l file version.
	*
	* @param userUuid the user uuid of this d l file version
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_dlFileVersion.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this d l file version.
	*
	* @return the user name of this d l file version
	*/
	public java.lang.String getUserName() {
		return _dlFileVersion.getUserName();
	}

	/**
	* Sets the user name of this d l file version.
	*
	* @param userName the user name of this d l file version
	*/
	public void setUserName(java.lang.String userName) {
		_dlFileVersion.setUserName(userName);
	}

	/**
	* Returns the create date of this d l file version.
	*
	* @return the create date of this d l file version
	*/
	public java.util.Date getCreateDate() {
		return _dlFileVersion.getCreateDate();
	}

	/**
	* Sets the create date of this d l file version.
	*
	* @param createDate the create date of this d l file version
	*/
	public void setCreateDate(java.util.Date createDate) {
		_dlFileVersion.setCreateDate(createDate);
	}

	/**
	* Returns the repository ID of this d l file version.
	*
	* @return the repository ID of this d l file version
	*/
	public long getRepositoryId() {
		return _dlFileVersion.getRepositoryId();
	}

	/**
	* Sets the repository ID of this d l file version.
	*
	* @param repositoryId the repository ID of this d l file version
	*/
	public void setRepositoryId(long repositoryId) {
		_dlFileVersion.setRepositoryId(repositoryId);
	}

	/**
	* Returns the file entry ID of this d l file version.
	*
	* @return the file entry ID of this d l file version
	*/
	public long getFileEntryId() {
		return _dlFileVersion.getFileEntryId();
	}

	/**
	* Sets the file entry ID of this d l file version.
	*
	* @param fileEntryId the file entry ID of this d l file version
	*/
	public void setFileEntryId(long fileEntryId) {
		_dlFileVersion.setFileEntryId(fileEntryId);
	}

	/**
	* Returns the extension of this d l file version.
	*
	* @return the extension of this d l file version
	*/
	public java.lang.String getExtension() {
		return _dlFileVersion.getExtension();
	}

	/**
	* Sets the extension of this d l file version.
	*
	* @param extension the extension of this d l file version
	*/
	public void setExtension(java.lang.String extension) {
		_dlFileVersion.setExtension(extension);
	}

	/**
	* Returns the mime type of this d l file version.
	*
	* @return the mime type of this d l file version
	*/
	public java.lang.String getMimeType() {
		return _dlFileVersion.getMimeType();
	}

	/**
	* Sets the mime type of this d l file version.
	*
	* @param mimeType the mime type of this d l file version
	*/
	public void setMimeType(java.lang.String mimeType) {
		_dlFileVersion.setMimeType(mimeType);
	}

	/**
	* Returns the title of this d l file version.
	*
	* @return the title of this d l file version
	*/
	public java.lang.String getTitle() {
		return _dlFileVersion.getTitle();
	}

	/**
	* Sets the title of this d l file version.
	*
	* @param title the title of this d l file version
	*/
	public void setTitle(java.lang.String title) {
		_dlFileVersion.setTitle(title);
	}

	/**
	* Returns the description of this d l file version.
	*
	* @return the description of this d l file version
	*/
	public java.lang.String getDescription() {
		return _dlFileVersion.getDescription();
	}

	/**
	* Sets the description of this d l file version.
	*
	* @param description the description of this d l file version
	*/
	public void setDescription(java.lang.String description) {
		_dlFileVersion.setDescription(description);
	}

	/**
	* Returns the change log of this d l file version.
	*
	* @return the change log of this d l file version
	*/
	public java.lang.String getChangeLog() {
		return _dlFileVersion.getChangeLog();
	}

	/**
	* Sets the change log of this d l file version.
	*
	* @param changeLog the change log of this d l file version
	*/
	public void setChangeLog(java.lang.String changeLog) {
		_dlFileVersion.setChangeLog(changeLog);
	}

	/**
	* Returns the extra settings of this d l file version.
	*
	* @return the extra settings of this d l file version
	*/
	public java.lang.String getExtraSettings() {
		return _dlFileVersion.getExtraSettings();
	}

	/**
	* Sets the extra settings of this d l file version.
	*
	* @param extraSettings the extra settings of this d l file version
	*/
	public void setExtraSettings(java.lang.String extraSettings) {
		_dlFileVersion.setExtraSettings(extraSettings);
	}

	/**
	* Returns the document type ID of this d l file version.
	*
	* @return the document type ID of this d l file version
	*/
	public long getDocumentTypeId() {
		return _dlFileVersion.getDocumentTypeId();
	}

	/**
	* Sets the document type ID of this d l file version.
	*
	* @param documentTypeId the document type ID of this d l file version
	*/
	public void setDocumentTypeId(long documentTypeId) {
		_dlFileVersion.setDocumentTypeId(documentTypeId);
	}

	/**
	* Returns the version of this d l file version.
	*
	* @return the version of this d l file version
	*/
	public java.lang.String getVersion() {
		return _dlFileVersion.getVersion();
	}

	/**
	* Sets the version of this d l file version.
	*
	* @param version the version of this d l file version
	*/
	public void setVersion(java.lang.String version) {
		_dlFileVersion.setVersion(version);
	}

	/**
	* Returns the size of this d l file version.
	*
	* @return the size of this d l file version
	*/
	public long getSize() {
		return _dlFileVersion.getSize();
	}

	/**
	* Sets the size of this d l file version.
	*
	* @param size the size of this d l file version
	*/
	public void setSize(long size) {
		_dlFileVersion.setSize(size);
	}

	/**
	* Returns the status of this d l file version.
	*
	* @return the status of this d l file version
	*/
	public int getStatus() {
		return _dlFileVersion.getStatus();
	}

	/**
	* Sets the status of this d l file version.
	*
	* @param status the status of this d l file version
	*/
	public void setStatus(int status) {
		_dlFileVersion.setStatus(status);
	}

	/**
	* Returns the status by user ID of this d l file version.
	*
	* @return the status by user ID of this d l file version
	*/
	public long getStatusByUserId() {
		return _dlFileVersion.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this d l file version.
	*
	* @param statusByUserId the status by user ID of this d l file version
	*/
	public void setStatusByUserId(long statusByUserId) {
		_dlFileVersion.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this d l file version.
	*
	* @return the status by user uuid of this d l file version
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersion.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this d l file version.
	*
	* @param statusByUserUuid the status by user uuid of this d l file version
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_dlFileVersion.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this d l file version.
	*
	* @return the status by user name of this d l file version
	*/
	public java.lang.String getStatusByUserName() {
		return _dlFileVersion.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this d l file version.
	*
	* @param statusByUserName the status by user name of this d l file version
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_dlFileVersion.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this d l file version.
	*
	* @return the status date of this d l file version
	*/
	public java.util.Date getStatusDate() {
		return _dlFileVersion.getStatusDate();
	}

	/**
	* Sets the status date of this d l file version.
	*
	* @param statusDate the status date of this d l file version
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_dlFileVersion.setStatusDate(statusDate);
	}

	/**
	* @deprecated Renamed to {@link #isApproved()}
	*/
	public boolean getApproved() {
		return _dlFileVersion.getApproved();
	}

	/**
	* Determines if this d l file version is approved.
	*
	* @return <code>true</code> if this d l file version is approved; <code>false</code> otherwise
	*/
	public boolean isApproved() {
		return _dlFileVersion.isApproved();
	}

	/**
	* Determines if this d l file version is a draft.
	*
	* @return <code>true</code> if this d l file version is a draft; <code>false</code> otherwise
	*/
	public boolean isDraft() {
		return _dlFileVersion.isDraft();
	}

	/**
	* Determines if this d l file version is expired.
	*
	* @return <code>true</code> if this d l file version is expired; <code>false</code> otherwise
	*/
	public boolean isExpired() {
		return _dlFileVersion.isExpired();
	}

	/**
	* Determines if this d l file version is pending.
	*
	* @return <code>true</code> if this d l file version is pending; <code>false</code> otherwise
	*/
	public boolean isPending() {
		return _dlFileVersion.isPending();
	}

	public boolean isNew() {
		return _dlFileVersion.isNew();
	}

	public void setNew(boolean n) {
		_dlFileVersion.setNew(n);
	}

	public boolean isCachedModel() {
		return _dlFileVersion.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_dlFileVersion.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _dlFileVersion.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_dlFileVersion.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileVersion.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_dlFileVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileVersion.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileVersion.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new DLFileVersionWrapper((DLFileVersion)_dlFileVersion.clone());
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion) {
		return _dlFileVersion.compareTo(dlFileVersion);
	}

	@Override
	public int hashCode() {
		return _dlFileVersion.hashCode();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion toEscapedModel() {
		return new DLFileVersionWrapper(_dlFileVersion.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _dlFileVersion.toString();
	}

	public java.lang.String toXmlString() {
		return _dlFileVersion.toXmlString();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getExtraSettingsProperties() {
		return _dlFileVersion.getExtraSettingsProperties();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersion.getFileEntry();
	}

	public java.lang.String getIcon() {
		return _dlFileVersion.getIcon();
	}

	public void setExtraSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties extraSettingsProperties) {
		_dlFileVersion.setExtraSettingsProperties(extraSettingsProperties);
	}

	public DLFileVersion getWrappedDLFileVersion() {
		return _dlFileVersion;
	}

	public void resetOriginalValues() {
		_dlFileVersion.resetOriginalValues();
	}

	private DLFileVersion _dlFileVersion;
}