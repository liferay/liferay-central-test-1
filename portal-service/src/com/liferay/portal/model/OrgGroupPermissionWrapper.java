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
 * <a href="OrgGroupPermissionSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link OrgGroupPermission}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgGroupPermission
 * @generated
 */
public class OrgGroupPermissionWrapper implements OrgGroupPermission {
	public OrgGroupPermissionWrapper(OrgGroupPermission orgGroupPermission) {
		_orgGroupPermission = orgGroupPermission;
	}

	public com.liferay.portal.service.persistence.OrgGroupPermissionPK getPrimaryKey() {
		return _orgGroupPermission.getPrimaryKey();
	}

	public void setPrimaryKey(
		com.liferay.portal.service.persistence.OrgGroupPermissionPK pk) {
		_orgGroupPermission.setPrimaryKey(pk);
	}

	public long getOrganizationId() {
		return _orgGroupPermission.getOrganizationId();
	}

	public void setOrganizationId(long organizationId) {
		_orgGroupPermission.setOrganizationId(organizationId);
	}

	public long getGroupId() {
		return _orgGroupPermission.getGroupId();
	}

	public void setGroupId(long groupId) {
		_orgGroupPermission.setGroupId(groupId);
	}

	public long getPermissionId() {
		return _orgGroupPermission.getPermissionId();
	}

	public void setPermissionId(long permissionId) {
		_orgGroupPermission.setPermissionId(permissionId);
	}

	public com.liferay.portal.model.OrgGroupPermission toEscapedModel() {
		return _orgGroupPermission.toEscapedModel();
	}

	public boolean isNew() {
		return _orgGroupPermission.isNew();
	}

	public boolean setNew(boolean n) {
		return _orgGroupPermission.setNew(n);
	}

	public boolean isCachedModel() {
		return _orgGroupPermission.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_orgGroupPermission.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _orgGroupPermission.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_orgGroupPermission.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _orgGroupPermission.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _orgGroupPermission.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_orgGroupPermission.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _orgGroupPermission.clone();
	}

	public int compareTo(
		com.liferay.portal.model.OrgGroupPermission orgGroupPermission) {
		return _orgGroupPermission.compareTo(orgGroupPermission);
	}

	public int hashCode() {
		return _orgGroupPermission.hashCode();
	}

	public java.lang.String toString() {
		return _orgGroupPermission.toString();
	}

	public java.lang.String toXmlString() {
		return _orgGroupPermission.toXmlString();
	}

	public boolean containsGroup(
		java.util.List<com.liferay.portal.model.Group> groups) {
		return _orgGroupPermission.containsGroup(groups);
	}

	public boolean containsOrganization(
		java.util.List<com.liferay.portal.model.Organization> organizations) {
		return _orgGroupPermission.containsOrganization(organizations);
	}

	public OrgGroupPermission getWrappedOrgGroupPermission() {
		return _orgGroupPermission;
	}

	private OrgGroupPermission _orgGroupPermission;
}