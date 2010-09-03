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
 * This class is a wrapper for {@link PortletItem}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletItem
 * @generated
 */
public class PortletItemWrapper implements PortletItem {
	public PortletItemWrapper(PortletItem portletItem) {
		_portletItem = portletItem;
	}

	/**
	* Gets the primary key of this portlet item.
	*
	* @return the primary key of this portlet item
	*/
	public long getPrimaryKey() {
		return _portletItem.getPrimaryKey();
	}

	/**
	* Sets the primary key of this portlet item
	*
	* @param pk the primary key of this portlet item
	*/
	public void setPrimaryKey(long pk) {
		_portletItem.setPrimaryKey(pk);
	}

	/**
	* Gets the portlet item id of this portlet item.
	*
	* @return the portlet item id of this portlet item
	*/
	public long getPortletItemId() {
		return _portletItem.getPortletItemId();
	}

	/**
	* Sets the portlet item id of this portlet item.
	*
	* @param portletItemId the portlet item id of this portlet item
	*/
	public void setPortletItemId(long portletItemId) {
		_portletItem.setPortletItemId(portletItemId);
	}

	/**
	* Gets the group id of this portlet item.
	*
	* @return the group id of this portlet item
	*/
	public long getGroupId() {
		return _portletItem.getGroupId();
	}

	/**
	* Sets the group id of this portlet item.
	*
	* @param groupId the group id of this portlet item
	*/
	public void setGroupId(long groupId) {
		_portletItem.setGroupId(groupId);
	}

	/**
	* Gets the company id of this portlet item.
	*
	* @return the company id of this portlet item
	*/
	public long getCompanyId() {
		return _portletItem.getCompanyId();
	}

	/**
	* Sets the company id of this portlet item.
	*
	* @param companyId the company id of this portlet item
	*/
	public void setCompanyId(long companyId) {
		_portletItem.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this portlet item.
	*
	* @return the user id of this portlet item
	*/
	public long getUserId() {
		return _portletItem.getUserId();
	}

	/**
	* Sets the user id of this portlet item.
	*
	* @param userId the user id of this portlet item
	*/
	public void setUserId(long userId) {
		_portletItem.setUserId(userId);
	}

	/**
	* Gets the user uuid of this portlet item.
	*
	* @return the user uuid of this portlet item
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletItem.getUserUuid();
	}

	/**
	* Sets the user uuid of this portlet item.
	*
	* @param userUuid the user uuid of this portlet item
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_portletItem.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this portlet item.
	*
	* @return the user name of this portlet item
	*/
	public java.lang.String getUserName() {
		return _portletItem.getUserName();
	}

	/**
	* Sets the user name of this portlet item.
	*
	* @param userName the user name of this portlet item
	*/
	public void setUserName(java.lang.String userName) {
		_portletItem.setUserName(userName);
	}

	/**
	* Gets the create date of this portlet item.
	*
	* @return the create date of this portlet item
	*/
	public java.util.Date getCreateDate() {
		return _portletItem.getCreateDate();
	}

	/**
	* Sets the create date of this portlet item.
	*
	* @param createDate the create date of this portlet item
	*/
	public void setCreateDate(java.util.Date createDate) {
		_portletItem.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this portlet item.
	*
	* @return the modified date of this portlet item
	*/
	public java.util.Date getModifiedDate() {
		return _portletItem.getModifiedDate();
	}

	/**
	* Sets the modified date of this portlet item.
	*
	* @param modifiedDate the modified date of this portlet item
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_portletItem.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the name of this portlet item.
	*
	* @return the name of this portlet item
	*/
	public java.lang.String getName() {
		return _portletItem.getName();
	}

	/**
	* Sets the name of this portlet item.
	*
	* @param name the name of this portlet item
	*/
	public void setName(java.lang.String name) {
		_portletItem.setName(name);
	}

	/**
	* Gets the portlet id of this portlet item.
	*
	* @return the portlet id of this portlet item
	*/
	public java.lang.String getPortletId() {
		return _portletItem.getPortletId();
	}

	/**
	* Sets the portlet id of this portlet item.
	*
	* @param portletId the portlet id of this portlet item
	*/
	public void setPortletId(java.lang.String portletId) {
		_portletItem.setPortletId(portletId);
	}

	/**
	* Gets the class name of the model instance this portlet item is polymorphically associated with.
	*
	* @return the class name of the model instance this portlet item is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _portletItem.getClassName();
	}

	/**
	* Gets the class name id of this portlet item.
	*
	* @return the class name id of this portlet item
	*/
	public long getClassNameId() {
		return _portletItem.getClassNameId();
	}

	/**
	* Sets the class name id of this portlet item.
	*
	* @param classNameId the class name id of this portlet item
	*/
	public void setClassNameId(long classNameId) {
		_portletItem.setClassNameId(classNameId);
	}

	public boolean isNew() {
		return _portletItem.isNew();
	}

	public void setNew(boolean n) {
		_portletItem.setNew(n);
	}

	public boolean isCachedModel() {
		return _portletItem.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_portletItem.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _portletItem.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_portletItem.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _portletItem.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _portletItem.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_portletItem.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _portletItem.clone();
	}

	public int compareTo(com.liferay.portal.model.PortletItem portletItem) {
		return _portletItem.compareTo(portletItem);
	}

	public int hashCode() {
		return _portletItem.hashCode();
	}

	public com.liferay.portal.model.PortletItem toEscapedModel() {
		return _portletItem.toEscapedModel();
	}

	public java.lang.String toString() {
		return _portletItem.toString();
	}

	public java.lang.String toXmlString() {
		return _portletItem.toXmlString();
	}

	public PortletItem getWrappedPortletItem() {
		return _portletItem;
	}

	private PortletItem _portletItem;
}