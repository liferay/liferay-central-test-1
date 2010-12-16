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

package com.liferay.portlet.documentlibrary.model;

/**
 * <p>
 * This class is a wrapper for {@link DLFileShortcut}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileShortcut
 * @generated
 */
public class DLFileShortcutWrapper implements DLFileShortcut {
	public DLFileShortcutWrapper(DLFileShortcut dlFileShortcut) {
		_dlFileShortcut = dlFileShortcut;
	}

	/**
	* Gets the primary key of this d l file shortcut.
	*
	* @return the primary key of this d l file shortcut
	*/
	public long getPrimaryKey() {
		return _dlFileShortcut.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d l file shortcut
	*
	* @param pk the primary key of this d l file shortcut
	*/
	public void setPrimaryKey(long pk) {
		_dlFileShortcut.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this d l file shortcut.
	*
	* @return the uuid of this d l file shortcut
	*/
	public java.lang.String getUuid() {
		return _dlFileShortcut.getUuid();
	}

	/**
	* Sets the uuid of this d l file shortcut.
	*
	* @param uuid the uuid of this d l file shortcut
	*/
	public void setUuid(java.lang.String uuid) {
		_dlFileShortcut.setUuid(uuid);
	}

	/**
	* Gets the file shortcut id of this d l file shortcut.
	*
	* @return the file shortcut id of this d l file shortcut
	*/
	public long getFileShortcutId() {
		return _dlFileShortcut.getFileShortcutId();
	}

	/**
	* Sets the file shortcut id of this d l file shortcut.
	*
	* @param fileShortcutId the file shortcut id of this d l file shortcut
	*/
	public void setFileShortcutId(long fileShortcutId) {
		_dlFileShortcut.setFileShortcutId(fileShortcutId);
	}

	/**
	* Gets the group id of this d l file shortcut.
	*
	* @return the group id of this d l file shortcut
	*/
	public long getGroupId() {
		return _dlFileShortcut.getGroupId();
	}

	/**
	* Sets the group id of this d l file shortcut.
	*
	* @param groupId the group id of this d l file shortcut
	*/
	public void setGroupId(long groupId) {
		_dlFileShortcut.setGroupId(groupId);
	}

	/**
	* Gets the company id of this d l file shortcut.
	*
	* @return the company id of this d l file shortcut
	*/
	public long getCompanyId() {
		return _dlFileShortcut.getCompanyId();
	}

	/**
	* Sets the company id of this d l file shortcut.
	*
	* @param companyId the company id of this d l file shortcut
	*/
	public void setCompanyId(long companyId) {
		_dlFileShortcut.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this d l file shortcut.
	*
	* @return the user id of this d l file shortcut
	*/
	public long getUserId() {
		return _dlFileShortcut.getUserId();
	}

	/**
	* Sets the user id of this d l file shortcut.
	*
	* @param userId the user id of this d l file shortcut
	*/
	public void setUserId(long userId) {
		_dlFileShortcut.setUserId(userId);
	}

	/**
	* Gets the user uuid of this d l file shortcut.
	*
	* @return the user uuid of this d l file shortcut
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcut.getUserUuid();
	}

	/**
	* Sets the user uuid of this d l file shortcut.
	*
	* @param userUuid the user uuid of this d l file shortcut
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_dlFileShortcut.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this d l file shortcut.
	*
	* @return the user name of this d l file shortcut
	*/
	public java.lang.String getUserName() {
		return _dlFileShortcut.getUserName();
	}

	/**
	* Sets the user name of this d l file shortcut.
	*
	* @param userName the user name of this d l file shortcut
	*/
	public void setUserName(java.lang.String userName) {
		_dlFileShortcut.setUserName(userName);
	}

	/**
	* Gets the create date of this d l file shortcut.
	*
	* @return the create date of this d l file shortcut
	*/
	public java.util.Date getCreateDate() {
		return _dlFileShortcut.getCreateDate();
	}

	/**
	* Sets the create date of this d l file shortcut.
	*
	* @param createDate the create date of this d l file shortcut
	*/
	public void setCreateDate(java.util.Date createDate) {
		_dlFileShortcut.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this d l file shortcut.
	*
	* @return the modified date of this d l file shortcut
	*/
	public java.util.Date getModifiedDate() {
		return _dlFileShortcut.getModifiedDate();
	}

	/**
	* Sets the modified date of this d l file shortcut.
	*
	* @param modifiedDate the modified date of this d l file shortcut
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_dlFileShortcut.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the folder id of this d l file shortcut.
	*
	* @return the folder id of this d l file shortcut
	*/
	public long getFolderId() {
		return _dlFileShortcut.getFolderId();
	}

	/**
	* Sets the folder id of this d l file shortcut.
	*
	* @param folderId the folder id of this d l file shortcut
	*/
	public void setFolderId(long folderId) {
		_dlFileShortcut.setFolderId(folderId);
	}

	/**
	* Gets the to file entry id of this d l file shortcut.
	*
	* @return the to file entry id of this d l file shortcut
	*/
	public long getToFileEntryId() {
		return _dlFileShortcut.getToFileEntryId();
	}

	/**
	* Sets the to file entry id of this d l file shortcut.
	*
	* @param toFileEntryId the to file entry id of this d l file shortcut
	*/
	public void setToFileEntryId(long toFileEntryId) {
		_dlFileShortcut.setToFileEntryId(toFileEntryId);
	}

	/**
	* Gets the status of this d l file shortcut.
	*
	* @return the status of this d l file shortcut
	*/
	public int getStatus() {
		return _dlFileShortcut.getStatus();
	}

	/**
	* Sets the status of this d l file shortcut.
	*
	* @param status the status of this d l file shortcut
	*/
	public void setStatus(int status) {
		_dlFileShortcut.setStatus(status);
	}

	/**
	* Gets the status by user id of this d l file shortcut.
	*
	* @return the status by user id of this d l file shortcut
	*/
	public long getStatusByUserId() {
		return _dlFileShortcut.getStatusByUserId();
	}

	/**
	* Sets the status by user id of this d l file shortcut.
	*
	* @param statusByUserId the status by user id of this d l file shortcut
	*/
	public void setStatusByUserId(long statusByUserId) {
		_dlFileShortcut.setStatusByUserId(statusByUserId);
	}

	/**
	* Gets the status by user uuid of this d l file shortcut.
	*
	* @return the status by user uuid of this d l file shortcut
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcut.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this d l file shortcut.
	*
	* @param statusByUserUuid the status by user uuid of this d l file shortcut
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_dlFileShortcut.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Gets the status by user name of this d l file shortcut.
	*
	* @return the status by user name of this d l file shortcut
	*/
	public java.lang.String getStatusByUserName() {
		return _dlFileShortcut.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this d l file shortcut.
	*
	* @param statusByUserName the status by user name of this d l file shortcut
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_dlFileShortcut.setStatusByUserName(statusByUserName);
	}

	/**
	* Gets the status date of this d l file shortcut.
	*
	* @return the status date of this d l file shortcut
	*/
	public java.util.Date getStatusDate() {
		return _dlFileShortcut.getStatusDate();
	}

	/**
	* Sets the status date of this d l file shortcut.
	*
	* @param statusDate the status date of this d l file shortcut
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_dlFileShortcut.setStatusDate(statusDate);
	}

	/**
	* @deprecated {@link #isApproved}
	*/
	public boolean getApproved() {
		return _dlFileShortcut.getApproved();
	}

	/**
	* Determines if this d l file shortcut is approved.
	*
	* @return <code>true</code> if this d l file shortcut is approved; <code>false</code> otherwise
	*/
	public boolean isApproved() {
		return _dlFileShortcut.isApproved();
	}

	/**
	* Determines if this d l file shortcut is a draft.
	*
	* @return <code>true</code> if this d l file shortcut is a draft; <code>false</code> otherwise
	*/
	public boolean isDraft() {
		return _dlFileShortcut.isDraft();
	}

	/**
	* Determines if this d l file shortcut is expired.
	*
	* @return <code>true</code> if this d l file shortcut is expired; <code>false</code> otherwise
	*/
	public boolean isExpired() {
		return _dlFileShortcut.isExpired();
	}

	/**
	* Determines if this d l file shortcut is pending.
	*
	* @return <code>true</code> if this d l file shortcut is pending; <code>false</code> otherwise
	*/
	public boolean isPending() {
		return _dlFileShortcut.isPending();
	}

	public boolean isNew() {
		return _dlFileShortcut.isNew();
	}

	public void setNew(boolean n) {
		_dlFileShortcut.setNew(n);
	}

	public boolean isCachedModel() {
		return _dlFileShortcut.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_dlFileShortcut.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _dlFileShortcut.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_dlFileShortcut.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileShortcut.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileShortcut.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileShortcut.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new DLFileShortcutWrapper((DLFileShortcut)_dlFileShortcut.clone());
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut) {
		return _dlFileShortcut.compareTo(dlFileShortcut);
	}

	public int hashCode() {
		return _dlFileShortcut.hashCode();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut toEscapedModel() {
		return new DLFileShortcutWrapper(_dlFileShortcut.toEscapedModel());
	}

	public java.lang.String toString() {
		return _dlFileShortcut.toString();
	}

	public java.lang.String toXmlString() {
		return _dlFileShortcut.toXmlString();
	}

	public com.liferay.portal.kernel.repository.model.Folder getFolder() {
		return _dlFileShortcut.getFolder();
	}

	public java.lang.String getToTitle() {
		return _dlFileShortcut.getToTitle();
	}

	public DLFileShortcut getWrappedDLFileShortcut() {
		return _dlFileShortcut;
	}

	private DLFileShortcut _dlFileShortcut;
}