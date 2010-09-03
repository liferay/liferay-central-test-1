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

package com.liferay.portlet.wiki.model;

/**
 * <p>
 * This class is a wrapper for {@link WikiPage}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPage
 * @generated
 */
public class WikiPageWrapper implements WikiPage {
	public WikiPageWrapper(WikiPage wikiPage) {
		_wikiPage = wikiPage;
	}

	/**
	* Gets the primary key of this wiki page.
	*
	* @return the primary key of this wiki page
	*/
	public long getPrimaryKey() {
		return _wikiPage.getPrimaryKey();
	}

	/**
	* Sets the primary key of this wiki page
	*
	* @param pk the primary key of this wiki page
	*/
	public void setPrimaryKey(long pk) {
		_wikiPage.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this wiki page.
	*
	* @return the uuid of this wiki page
	*/
	public java.lang.String getUuid() {
		return _wikiPage.getUuid();
	}

	/**
	* Sets the uuid of this wiki page.
	*
	* @param uuid the uuid of this wiki page
	*/
	public void setUuid(java.lang.String uuid) {
		_wikiPage.setUuid(uuid);
	}

	/**
	* Gets the page id of this wiki page.
	*
	* @return the page id of this wiki page
	*/
	public long getPageId() {
		return _wikiPage.getPageId();
	}

	/**
	* Sets the page id of this wiki page.
	*
	* @param pageId the page id of this wiki page
	*/
	public void setPageId(long pageId) {
		_wikiPage.setPageId(pageId);
	}

	/**
	* Gets the resource prim key of this wiki page.
	*
	* @return the resource prim key of this wiki page
	*/
	public long getResourcePrimKey() {
		return _wikiPage.getResourcePrimKey();
	}

	/**
	* Sets the resource prim key of this wiki page.
	*
	* @param resourcePrimKey the resource prim key of this wiki page
	*/
	public void setResourcePrimKey(long resourcePrimKey) {
		_wikiPage.setResourcePrimKey(resourcePrimKey);
	}

	/**
	* Gets the group id of this wiki page.
	*
	* @return the group id of this wiki page
	*/
	public long getGroupId() {
		return _wikiPage.getGroupId();
	}

	/**
	* Sets the group id of this wiki page.
	*
	* @param groupId the group id of this wiki page
	*/
	public void setGroupId(long groupId) {
		_wikiPage.setGroupId(groupId);
	}

	/**
	* Gets the company id of this wiki page.
	*
	* @return the company id of this wiki page
	*/
	public long getCompanyId() {
		return _wikiPage.getCompanyId();
	}

	/**
	* Sets the company id of this wiki page.
	*
	* @param companyId the company id of this wiki page
	*/
	public void setCompanyId(long companyId) {
		_wikiPage.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this wiki page.
	*
	* @return the user id of this wiki page
	*/
	public long getUserId() {
		return _wikiPage.getUserId();
	}

	/**
	* Sets the user id of this wiki page.
	*
	* @param userId the user id of this wiki page
	*/
	public void setUserId(long userId) {
		_wikiPage.setUserId(userId);
	}

	/**
	* Gets the user uuid of this wiki page.
	*
	* @return the user uuid of this wiki page
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getUserUuid();
	}

	/**
	* Sets the user uuid of this wiki page.
	*
	* @param userUuid the user uuid of this wiki page
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_wikiPage.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this wiki page.
	*
	* @return the user name of this wiki page
	*/
	public java.lang.String getUserName() {
		return _wikiPage.getUserName();
	}

	/**
	* Sets the user name of this wiki page.
	*
	* @param userName the user name of this wiki page
	*/
	public void setUserName(java.lang.String userName) {
		_wikiPage.setUserName(userName);
	}

	/**
	* Gets the create date of this wiki page.
	*
	* @return the create date of this wiki page
	*/
	public java.util.Date getCreateDate() {
		return _wikiPage.getCreateDate();
	}

	/**
	* Sets the create date of this wiki page.
	*
	* @param createDate the create date of this wiki page
	*/
	public void setCreateDate(java.util.Date createDate) {
		_wikiPage.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this wiki page.
	*
	* @return the modified date of this wiki page
	*/
	public java.util.Date getModifiedDate() {
		return _wikiPage.getModifiedDate();
	}

	/**
	* Sets the modified date of this wiki page.
	*
	* @param modifiedDate the modified date of this wiki page
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_wikiPage.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the node id of this wiki page.
	*
	* @return the node id of this wiki page
	*/
	public long getNodeId() {
		return _wikiPage.getNodeId();
	}

	/**
	* Sets the node id of this wiki page.
	*
	* @param nodeId the node id of this wiki page
	*/
	public void setNodeId(long nodeId) {
		_wikiPage.setNodeId(nodeId);
	}

	/**
	* Gets the title of this wiki page.
	*
	* @return the title of this wiki page
	*/
	public java.lang.String getTitle() {
		return _wikiPage.getTitle();
	}

	/**
	* Sets the title of this wiki page.
	*
	* @param title the title of this wiki page
	*/
	public void setTitle(java.lang.String title) {
		_wikiPage.setTitle(title);
	}

	/**
	* Gets the version of this wiki page.
	*
	* @return the version of this wiki page
	*/
	public double getVersion() {
		return _wikiPage.getVersion();
	}

	/**
	* Sets the version of this wiki page.
	*
	* @param version the version of this wiki page
	*/
	public void setVersion(double version) {
		_wikiPage.setVersion(version);
	}

	/**
	* Gets the minor edit of this wiki page.
	*
	* @return the minor edit of this wiki page
	*/
	public boolean getMinorEdit() {
		return _wikiPage.getMinorEdit();
	}

	/**
	* Determines whether this wiki page is minor edit.
	*
	* @return whether this wiki page is minor edit
	*/
	public boolean isMinorEdit() {
		return _wikiPage.isMinorEdit();
	}

	/**
	* Sets whether this {$entity.humanName} is minor edit.
	*
	* @param minorEdit the minor edit of this wiki page
	*/
	public void setMinorEdit(boolean minorEdit) {
		_wikiPage.setMinorEdit(minorEdit);
	}

	/**
	* Gets the content of this wiki page.
	*
	* @return the content of this wiki page
	*/
	public java.lang.String getContent() {
		return _wikiPage.getContent();
	}

	/**
	* Sets the content of this wiki page.
	*
	* @param content the content of this wiki page
	*/
	public void setContent(java.lang.String content) {
		_wikiPage.setContent(content);
	}

	/**
	* Gets the summary of this wiki page.
	*
	* @return the summary of this wiki page
	*/
	public java.lang.String getSummary() {
		return _wikiPage.getSummary();
	}

	/**
	* Sets the summary of this wiki page.
	*
	* @param summary the summary of this wiki page
	*/
	public void setSummary(java.lang.String summary) {
		_wikiPage.setSummary(summary);
	}

	/**
	* Gets the format of this wiki page.
	*
	* @return the format of this wiki page
	*/
	public java.lang.String getFormat() {
		return _wikiPage.getFormat();
	}

	/**
	* Sets the format of this wiki page.
	*
	* @param format the format of this wiki page
	*/
	public void setFormat(java.lang.String format) {
		_wikiPage.setFormat(format);
	}

	/**
	* Gets the head of this wiki page.
	*
	* @return the head of this wiki page
	*/
	public boolean getHead() {
		return _wikiPage.getHead();
	}

	/**
	* Determines whether this wiki page is head.
	*
	* @return whether this wiki page is head
	*/
	public boolean isHead() {
		return _wikiPage.isHead();
	}

	/**
	* Sets whether this {$entity.humanName} is head.
	*
	* @param head the head of this wiki page
	*/
	public void setHead(boolean head) {
		_wikiPage.setHead(head);
	}

	/**
	* Gets the parent title of this wiki page.
	*
	* @return the parent title of this wiki page
	*/
	public java.lang.String getParentTitle() {
		return _wikiPage.getParentTitle();
	}

	/**
	* Sets the parent title of this wiki page.
	*
	* @param parentTitle the parent title of this wiki page
	*/
	public void setParentTitle(java.lang.String parentTitle) {
		_wikiPage.setParentTitle(parentTitle);
	}

	/**
	* Gets the redirect title of this wiki page.
	*
	* @return the redirect title of this wiki page
	*/
	public java.lang.String getRedirectTitle() {
		return _wikiPage.getRedirectTitle();
	}

	/**
	* Sets the redirect title of this wiki page.
	*
	* @param redirectTitle the redirect title of this wiki page
	*/
	public void setRedirectTitle(java.lang.String redirectTitle) {
		_wikiPage.setRedirectTitle(redirectTitle);
	}

	/**
	* Gets the status of this wiki page.
	*
	* @return the status of this wiki page
	*/
	public int getStatus() {
		return _wikiPage.getStatus();
	}

	/**
	* Sets the status of this wiki page.
	*
	* @param status the status of this wiki page
	*/
	public void setStatus(int status) {
		_wikiPage.setStatus(status);
	}

	/**
	* Gets the status by user id of this wiki page.
	*
	* @return the status by user id of this wiki page
	*/
	public long getStatusByUserId() {
		return _wikiPage.getStatusByUserId();
	}

	/**
	* Sets the status by user id of this wiki page.
	*
	* @param statusByUserId the status by user id of this wiki page
	*/
	public void setStatusByUserId(long statusByUserId) {
		_wikiPage.setStatusByUserId(statusByUserId);
	}

	/**
	* Gets the status by user uuid of this wiki page.
	*
	* @return the status by user uuid of this wiki page
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this wiki page.
	*
	* @param statusByUserUuid the status by user uuid of this wiki page
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_wikiPage.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Gets the status by user name of this wiki page.
	*
	* @return the status by user name of this wiki page
	*/
	public java.lang.String getStatusByUserName() {
		return _wikiPage.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this wiki page.
	*
	* @param statusByUserName the status by user name of this wiki page
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_wikiPage.setStatusByUserName(statusByUserName);
	}

	/**
	* Gets the status date of this wiki page.
	*
	* @return the status date of this wiki page
	*/
	public java.util.Date getStatusDate() {
		return _wikiPage.getStatusDate();
	}

	/**
	* Sets the status date of this wiki page.
	*
	* @param statusDate the status date of this wiki page
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_wikiPage.setStatusDate(statusDate);
	}

	/**
	* @deprecated {@link #isApproved}
	*/
	public boolean getApproved() {
		return _wikiPage.getApproved();
	}

	/**
	* Determines whether this wiki page is approved.
	*
	* @return true if this wiki page is approved; false otherwise
	*/
	public boolean isApproved() {
		return _wikiPage.isApproved();
	}

	/**
	* Determines whether this wiki page is a draft.
	*
	* @return true if this wiki page is a draft; false otherwise
	*/
	public boolean isDraft() {
		return _wikiPage.isDraft();
	}

	/**
	* Determines whether this wiki page is expired.
	*
	* @return true if this wiki page is expired; false otherwise
	*/
	public boolean isExpired() {
		return _wikiPage.isExpired();
	}

	/**
	* Determines whether this wiki page is pending.
	*
	* @return true if this wiki page is pending; false otherwise
	*/
	public boolean isPending() {
		return _wikiPage.isPending();
	}

	public boolean isNew() {
		return _wikiPage.isNew();
	}

	public void setNew(boolean n) {
		_wikiPage.setNew(n);
	}

	public boolean isCachedModel() {
		return _wikiPage.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_wikiPage.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _wikiPage.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_wikiPage.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _wikiPage.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _wikiPage.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_wikiPage.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _wikiPage.clone();
	}

	public int compareTo(com.liferay.portlet.wiki.model.WikiPage wikiPage) {
		return _wikiPage.compareTo(wikiPage);
	}

	public int hashCode() {
		return _wikiPage.hashCode();
	}

	public com.liferay.portlet.wiki.model.WikiPage toEscapedModel() {
		return _wikiPage.toEscapedModel();
	}

	public java.lang.String toString() {
		return _wikiPage.toString();
	}

	public java.lang.String toXmlString() {
		return _wikiPage.toXmlString();
	}

	public java.lang.String getAttachmentsDir() {
		return _wikiPage.getAttachmentsDir();
	}

	public java.lang.String[] getAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getAttachmentsFiles();
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getChildPages() {
		return _wikiPage.getChildPages();
	}

	public com.liferay.portlet.wiki.model.WikiNode getNode() {
		return _wikiPage.getNode();
	}

	public com.liferay.portlet.wiki.model.WikiPage getParentPage() {
		return _wikiPage.getParentPage();
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getParentPages() {
		return _wikiPage.getParentPages();
	}

	public com.liferay.portlet.wiki.model.WikiPage getRedirectPage() {
		return _wikiPage.getRedirectPage();
	}

	public void setAttachmentsDir(java.lang.String attachmentsDir) {
		_wikiPage.setAttachmentsDir(attachmentsDir);
	}

	public WikiPage getWrappedWikiPage() {
		return _wikiPage;
	}

	private WikiPage _wikiPage;
}