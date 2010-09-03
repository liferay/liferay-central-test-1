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
 * This class is a wrapper for {@link Group}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Group
 * @generated
 */
public class GroupWrapper implements Group {
	public GroupWrapper(Group group) {
		_group = group;
	}

	/**
	* Gets the primary key of this group.
	*
	* @return the primary key of this group
	*/
	public long getPrimaryKey() {
		return _group.getPrimaryKey();
	}

	/**
	* Sets the primary key of this group
	*
	* @param pk the primary key of this group
	*/
	public void setPrimaryKey(long pk) {
		_group.setPrimaryKey(pk);
	}

	/**
	* Gets the group id of this group.
	*
	* @return the group id of this group
	*/
	public long getGroupId() {
		return _group.getGroupId();
	}

	/**
	* Sets the group id of this group.
	*
	* @param groupId the group id of this group
	*/
	public void setGroupId(long groupId) {
		_group.setGroupId(groupId);
	}

	/**
	* Gets the company id of this group.
	*
	* @return the company id of this group
	*/
	public long getCompanyId() {
		return _group.getCompanyId();
	}

	/**
	* Sets the company id of this group.
	*
	* @param companyId the company id of this group
	*/
	public void setCompanyId(long companyId) {
		_group.setCompanyId(companyId);
	}

	/**
	* Gets the creator user id of this group.
	*
	* @return the creator user id of this group
	*/
	public long getCreatorUserId() {
		return _group.getCreatorUserId();
	}

	/**
	* Sets the creator user id of this group.
	*
	* @param creatorUserId the creator user id of this group
	*/
	public void setCreatorUserId(long creatorUserId) {
		_group.setCreatorUserId(creatorUserId);
	}

	/**
	* Gets the creator user uuid of this group.
	*
	* @return the creator user uuid of this group
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getCreatorUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _group.getCreatorUserUuid();
	}

	/**
	* Sets the creator user uuid of this group.
	*
	* @param creatorUserUuid the creator user uuid of this group
	*/
	public void setCreatorUserUuid(java.lang.String creatorUserUuid) {
		_group.setCreatorUserUuid(creatorUserUuid);
	}

	/**
	* Gets the class name of the model instance this group is polymorphically associated with.
	*
	* @return the class name of the model instance this group is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _group.getClassName();
	}

	/**
	* Gets the class name id of this group.
	*
	* @return the class name id of this group
	*/
	public long getClassNameId() {
		return _group.getClassNameId();
	}

	/**
	* Sets the class name id of this group.
	*
	* @param classNameId the class name id of this group
	*/
	public void setClassNameId(long classNameId) {
		_group.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this group.
	*
	* @return the class p k of this group
	*/
	public long getClassPK() {
		return _group.getClassPK();
	}

	/**
	* Sets the class p k of this group.
	*
	* @param classPK the class p k of this group
	*/
	public void setClassPK(long classPK) {
		_group.setClassPK(classPK);
	}

	/**
	* Gets the parent group id of this group.
	*
	* @return the parent group id of this group
	*/
	public long getParentGroupId() {
		return _group.getParentGroupId();
	}

	/**
	* Sets the parent group id of this group.
	*
	* @param parentGroupId the parent group id of this group
	*/
	public void setParentGroupId(long parentGroupId) {
		_group.setParentGroupId(parentGroupId);
	}

	/**
	* Gets the live group id of this group.
	*
	* @return the live group id of this group
	*/
	public long getLiveGroupId() {
		return _group.getLiveGroupId();
	}

	/**
	* Sets the live group id of this group.
	*
	* @param liveGroupId the live group id of this group
	*/
	public void setLiveGroupId(long liveGroupId) {
		_group.setLiveGroupId(liveGroupId);
	}

	/**
	* Gets the name of this group.
	*
	* @return the name of this group
	*/
	public java.lang.String getName() {
		return _group.getName();
	}

	/**
	* Sets the name of this group.
	*
	* @param name the name of this group
	*/
	public void setName(java.lang.String name) {
		_group.setName(name);
	}

	/**
	* Gets the description of this group.
	*
	* @return the description of this group
	*/
	public java.lang.String getDescription() {
		return _group.getDescription();
	}

	/**
	* Sets the description of this group.
	*
	* @param description the description of this group
	*/
	public void setDescription(java.lang.String description) {
		_group.setDescription(description);
	}

	/**
	* Gets the type of this group.
	*
	* @return the type of this group
	*/
	public int getType() {
		return _group.getType();
	}

	/**
	* Sets the type of this group.
	*
	* @param type the type of this group
	*/
	public void setType(int type) {
		_group.setType(type);
	}

	/**
	* Gets the type settings of this group.
	*
	* @return the type settings of this group
	*/
	public java.lang.String getTypeSettings() {
		return _group.getTypeSettings();
	}

	/**
	* Sets the type settings of this group.
	*
	* @param typeSettings the type settings of this group
	*/
	public void setTypeSettings(java.lang.String typeSettings) {
		_group.setTypeSettings(typeSettings);
	}

	/**
	* Gets the friendly u r l of this group.
	*
	* @return the friendly u r l of this group
	*/
	public java.lang.String getFriendlyURL() {
		return _group.getFriendlyURL();
	}

	/**
	* Sets the friendly u r l of this group.
	*
	* @param friendlyURL the friendly u r l of this group
	*/
	public void setFriendlyURL(java.lang.String friendlyURL) {
		_group.setFriendlyURL(friendlyURL);
	}

	/**
	* Gets the active of this group.
	*
	* @return the active of this group
	*/
	public boolean getActive() {
		return _group.getActive();
	}

	/**
	* Determines whether this group is active.
	*
	* @return whether this group is active
	*/
	public boolean isActive() {
		return _group.isActive();
	}

	/**
	* Sets whether this {$entity.humanName} is active.
	*
	* @param active the active of this group
	*/
	public void setActive(boolean active) {
		_group.setActive(active);
	}

	public boolean isNew() {
		return _group.isNew();
	}

	public void setNew(boolean n) {
		_group.setNew(n);
	}

	public boolean isCachedModel() {
		return _group.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_group.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _group.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_group.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _group.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _group.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_group.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _group.clone();
	}

	public int compareTo(com.liferay.portal.model.Group group) {
		return _group.compareTo(group);
	}

	public int hashCode() {
		return _group.hashCode();
	}

	public com.liferay.portal.model.Group toEscapedModel() {
		return _group.toEscapedModel();
	}

	public java.lang.String toString() {
		return _group.toString();
	}

	public java.lang.String toXmlString() {
		return _group.toXmlString();
	}

	public long getDefaultPrivatePlid() {
		return _group.getDefaultPrivatePlid();
	}

	public long getDefaultPublicPlid() {
		return _group.getDefaultPublicPlid();
	}

	public java.lang.String getDescriptiveName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _group.getDescriptiveName();
	}

	public com.liferay.portal.model.Group getLiveGroup() {
		return _group.getLiveGroup();
	}

	public long getOrganizationId() {
		return _group.getOrganizationId();
	}

	public java.lang.String getPathFriendlyURL(boolean privateLayout,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _group.getPathFriendlyURL(privateLayout, themeDisplay);
	}

	public com.liferay.portal.model.LayoutSet getPrivateLayoutSet() {
		return _group.getPrivateLayoutSet();
	}

	public int getPrivateLayoutsPageCount() {
		return _group.getPrivateLayoutsPageCount();
	}

	public com.liferay.portal.model.LayoutSet getPublicLayoutSet() {
		return _group.getPublicLayoutSet();
	}

	public int getPublicLayoutsPageCount() {
		return _group.getPublicLayoutsPageCount();
	}

	public com.liferay.portal.model.Group getStagingGroup() {
		return _group.getStagingGroup();
	}

	public java.lang.String getTypeLabel() {
		return _group.getTypeLabel();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _group.getTypeSettingsProperties();
	}

	public java.lang.String getTypeSettingsProperty(java.lang.String key) {
		return _group.getTypeSettingsProperty(key);
	}

	public java.lang.String getWorkflowRoleNames() {
		return _group.getWorkflowRoleNames();
	}

	public int getWorkflowStages() {
		return _group.getWorkflowStages();
	}

	public boolean hasPrivateLayouts() {
		return _group.hasPrivateLayouts();
	}

	public boolean hasPublicLayouts() {
		return _group.hasPublicLayouts();
	}

	public boolean hasStagingGroup() {
		return _group.hasStagingGroup();
	}

	public boolean isCommunity() {
		return _group.isCommunity();
	}

	public boolean isCompany() {
		return _group.isCompany();
	}

	public boolean isControlPanel() {
		return _group.isControlPanel();
	}

	public boolean isLayout() {
		return _group.isLayout();
	}

	public boolean isLayoutPrototype() {
		return _group.isLayoutPrototype();
	}

	public boolean isLayoutSetPrototype() {
		return _group.isLayoutSetPrototype();
	}

	public boolean isOrganization() {
		return _group.isOrganization();
	}

	public boolean isStaged() {
		return _group.isStaged();
	}

	public boolean isStagedPortlet(java.lang.String portletId) {
		return _group.isStagedPortlet(portletId);
	}

	public boolean isStagedRemotely() {
		return _group.isStagedRemotely();
	}

	public boolean isStagingGroup() {
		return _group.isStagingGroup();
	}

	public boolean isUser() {
		return _group.isUser();
	}

	public boolean isUserGroup() {
		return _group.isUserGroup();
	}

	public boolean isWorkflowEnabled() {
		return _group.isWorkflowEnabled();
	}

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_group.setTypeSettingsProperties(typeSettingsProperties);
	}

	public Group getWrappedGroup() {
		return _group;
	}

	private Group _group;
}