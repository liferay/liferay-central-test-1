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

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link LayoutSetBranch}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetBranch
 * @generated
 */
public class LayoutSetBranchWrapper implements LayoutSetBranch {
	public LayoutSetBranchWrapper(LayoutSetBranch layoutSetBranch) {
		_layoutSetBranch = layoutSetBranch;
	}

	/**
	* Gets the primary key of this layout set branch.
	*
	* @return the primary key of this layout set branch
	*/
	public long getPrimaryKey() {
		return _layoutSetBranch.getPrimaryKey();
	}

	/**
	* Sets the primary key of this layout set branch
	*
	* @param pk the primary key of this layout set branch
	*/
	public void setPrimaryKey(long pk) {
		_layoutSetBranch.setPrimaryKey(pk);
	}

	/**
	* Gets the layout set branch ID of this layout set branch.
	*
	* @return the layout set branch ID of this layout set branch
	*/
	public long getLayoutSetBranchId() {
		return _layoutSetBranch.getLayoutSetBranchId();
	}

	/**
	* Sets the layout set branch ID of this layout set branch.
	*
	* @param layoutSetBranchId the layout set branch ID of this layout set branch
	*/
	public void setLayoutSetBranchId(long layoutSetBranchId) {
		_layoutSetBranch.setLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Gets the group ID of this layout set branch.
	*
	* @return the group ID of this layout set branch
	*/
	public long getGroupId() {
		return _layoutSetBranch.getGroupId();
	}

	/**
	* Sets the group ID of this layout set branch.
	*
	* @param groupId the group ID of this layout set branch
	*/
	public void setGroupId(long groupId) {
		_layoutSetBranch.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this layout set branch.
	*
	* @return the company ID of this layout set branch
	*/
	public long getCompanyId() {
		return _layoutSetBranch.getCompanyId();
	}

	/**
	* Sets the company ID of this layout set branch.
	*
	* @param companyId the company ID of this layout set branch
	*/
	public void setCompanyId(long companyId) {
		_layoutSetBranch.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this layout set branch.
	*
	* @return the user ID of this layout set branch
	*/
	public long getUserId() {
		return _layoutSetBranch.getUserId();
	}

	/**
	* Sets the user ID of this layout set branch.
	*
	* @param userId the user ID of this layout set branch
	*/
	public void setUserId(long userId) {
		_layoutSetBranch.setUserId(userId);
	}

	/**
	* Gets the user uuid of this layout set branch.
	*
	* @return the user uuid of this layout set branch
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetBranch.getUserUuid();
	}

	/**
	* Sets the user uuid of this layout set branch.
	*
	* @param userUuid the user uuid of this layout set branch
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_layoutSetBranch.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this layout set branch.
	*
	* @return the user name of this layout set branch
	*/
	public java.lang.String getUserName() {
		return _layoutSetBranch.getUserName();
	}

	/**
	* Sets the user name of this layout set branch.
	*
	* @param userName the user name of this layout set branch
	*/
	public void setUserName(java.lang.String userName) {
		_layoutSetBranch.setUserName(userName);
	}

	/**
	* Gets the create date of this layout set branch.
	*
	* @return the create date of this layout set branch
	*/
	public java.util.Date getCreateDate() {
		return _layoutSetBranch.getCreateDate();
	}

	/**
	* Sets the create date of this layout set branch.
	*
	* @param createDate the create date of this layout set branch
	*/
	public void setCreateDate(java.util.Date createDate) {
		_layoutSetBranch.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this layout set branch.
	*
	* @return the modified date of this layout set branch
	*/
	public java.util.Date getModifiedDate() {
		return _layoutSetBranch.getModifiedDate();
	}

	/**
	* Sets the modified date of this layout set branch.
	*
	* @param modifiedDate the modified date of this layout set branch
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_layoutSetBranch.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the private layout of this layout set branch.
	*
	* @return the private layout of this layout set branch
	*/
	public boolean getPrivateLayout() {
		return _layoutSetBranch.getPrivateLayout();
	}

	/**
	* Determines if this layout set branch is private layout.
	*
	* @return <code>true</code> if this layout set branch is private layout; <code>false</code> otherwise
	*/
	public boolean isPrivateLayout() {
		return _layoutSetBranch.isPrivateLayout();
	}

	/**
	* Sets whether this layout set branch is private layout.
	*
	* @param privateLayout the private layout of this layout set branch
	*/
	public void setPrivateLayout(boolean privateLayout) {
		_layoutSetBranch.setPrivateLayout(privateLayout);
	}

	/**
	* Gets the name of this layout set branch.
	*
	* @return the name of this layout set branch
	*/
	public java.lang.String getName() {
		return _layoutSetBranch.getName();
	}

	/**
	* Sets the name of this layout set branch.
	*
	* @param name the name of this layout set branch
	*/
	public void setName(java.lang.String name) {
		_layoutSetBranch.setName(name);
	}

	/**
	* Gets the description of this layout set branch.
	*
	* @return the description of this layout set branch
	*/
	public java.lang.String getDescription() {
		return _layoutSetBranch.getDescription();
	}

	/**
	* Sets the description of this layout set branch.
	*
	* @param description the description of this layout set branch
	*/
	public void setDescription(java.lang.String description) {
		_layoutSetBranch.setDescription(description);
	}

	public boolean isNew() {
		return _layoutSetBranch.isNew();
	}

	public void setNew(boolean n) {
		_layoutSetBranch.setNew(n);
	}

	public boolean isCachedModel() {
		return _layoutSetBranch.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_layoutSetBranch.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _layoutSetBranch.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_layoutSetBranch.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutSetBranch.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutSetBranch.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layoutSetBranch.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new LayoutSetBranchWrapper((LayoutSetBranch)_layoutSetBranch.clone());
	}

	public int compareTo(
		com.liferay.portal.model.LayoutSetBranch layoutSetBranch) {
		return _layoutSetBranch.compareTo(layoutSetBranch);
	}

	public int hashCode() {
		return _layoutSetBranch.hashCode();
	}

	public com.liferay.portal.model.LayoutSetBranch toEscapedModel() {
		return new LayoutSetBranchWrapper(_layoutSetBranch.toEscapedModel());
	}

	public java.lang.String toString() {
		return _layoutSetBranch.toString();
	}

	public java.lang.String toXmlString() {
		return _layoutSetBranch.toXmlString();
	}

	public boolean isMaster() {
		return _layoutSetBranch.isMaster();
	}

	public LayoutSetBranch getWrappedLayoutSetBranch() {
		return _layoutSetBranch;
	}

	private LayoutSetBranch _layoutSetBranch;
}