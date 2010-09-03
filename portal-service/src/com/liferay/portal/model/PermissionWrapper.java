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
 * This class is a wrapper for {@link Permission}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Permission
 * @generated
 */
public class PermissionWrapper implements Permission {
	public PermissionWrapper(Permission permission) {
		_permission = permission;
	}

	/**
	* Gets the primary key of this permission.
	*
	* @return the primary key of this permission
	*/
	public long getPrimaryKey() {
		return _permission.getPrimaryKey();
	}

	/**
	* Sets the primary key of this permission
	*
	* @param pk the primary key of this permission
	*/
	public void setPrimaryKey(long pk) {
		_permission.setPrimaryKey(pk);
	}

	/**
	* Gets the permission id of this permission.
	*
	* @return the permission id of this permission
	*/
	public long getPermissionId() {
		return _permission.getPermissionId();
	}

	/**
	* Sets the permission id of this permission.
	*
	* @param permissionId the permission id of this permission
	*/
	public void setPermissionId(long permissionId) {
		_permission.setPermissionId(permissionId);
	}

	/**
	* Gets the company id of this permission.
	*
	* @return the company id of this permission
	*/
	public long getCompanyId() {
		return _permission.getCompanyId();
	}

	/**
	* Sets the company id of this permission.
	*
	* @param companyId the company id of this permission
	*/
	public void setCompanyId(long companyId) {
		_permission.setCompanyId(companyId);
	}

	/**
	* Gets the action id of this permission.
	*
	* @return the action id of this permission
	*/
	public java.lang.String getActionId() {
		return _permission.getActionId();
	}

	/**
	* Sets the action id of this permission.
	*
	* @param actionId the action id of this permission
	*/
	public void setActionId(java.lang.String actionId) {
		_permission.setActionId(actionId);
	}

	/**
	* Gets the resource id of this permission.
	*
	* @return the resource id of this permission
	*/
	public long getResourceId() {
		return _permission.getResourceId();
	}

	/**
	* Sets the resource id of this permission.
	*
	* @param resourceId the resource id of this permission
	*/
	public void setResourceId(long resourceId) {
		_permission.setResourceId(resourceId);
	}

	public boolean isNew() {
		return _permission.isNew();
	}

	public void setNew(boolean n) {
		_permission.setNew(n);
	}

	public boolean isCachedModel() {
		return _permission.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_permission.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _permission.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_permission.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _permission.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _permission.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_permission.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _permission.clone();
	}

	public int compareTo(com.liferay.portal.model.Permission permission) {
		return _permission.compareTo(permission);
	}

	public int hashCode() {
		return _permission.hashCode();
	}

	public com.liferay.portal.model.Permission toEscapedModel() {
		return _permission.toEscapedModel();
	}

	public java.lang.String toString() {
		return _permission.toString();
	}

	public java.lang.String toXmlString() {
		return _permission.toXmlString();
	}

	public java.lang.String getName() {
		return _permission.getName();
	}

	public java.lang.String getPrimKey() {
		return _permission.getPrimKey();
	}

	public int getScope() {
		return _permission.getScope();
	}

	public void setName(java.lang.String name) {
		_permission.setName(name);
	}

	public void setPrimKey(java.lang.String primKey) {
		_permission.setPrimKey(primKey);
	}

	public void setScope(int scope) {
		_permission.setScope(scope);
	}

	public Permission getWrappedPermission() {
		return _permission;
	}

	private Permission _permission;
}