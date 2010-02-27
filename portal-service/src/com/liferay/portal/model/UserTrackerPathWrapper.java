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
 * <a href="UserTrackerPathSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link UserTrackerPath}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserTrackerPath
 * @generated
 */
public class UserTrackerPathWrapper implements UserTrackerPath {
	public UserTrackerPathWrapper(UserTrackerPath userTrackerPath) {
		_userTrackerPath = userTrackerPath;
	}

	public long getPrimaryKey() {
		return _userTrackerPath.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_userTrackerPath.setPrimaryKey(pk);
	}

	public long getUserTrackerPathId() {
		return _userTrackerPath.getUserTrackerPathId();
	}

	public void setUserTrackerPathId(long userTrackerPathId) {
		_userTrackerPath.setUserTrackerPathId(userTrackerPathId);
	}

	public long getUserTrackerId() {
		return _userTrackerPath.getUserTrackerId();
	}

	public void setUserTrackerId(long userTrackerId) {
		_userTrackerPath.setUserTrackerId(userTrackerId);
	}

	public java.lang.String getPath() {
		return _userTrackerPath.getPath();
	}

	public void setPath(java.lang.String path) {
		_userTrackerPath.setPath(path);
	}

	public java.util.Date getPathDate() {
		return _userTrackerPath.getPathDate();
	}

	public void setPathDate(java.util.Date pathDate) {
		_userTrackerPath.setPathDate(pathDate);
	}

	public com.liferay.portal.model.UserTrackerPath toEscapedModel() {
		return _userTrackerPath.toEscapedModel();
	}

	public boolean isNew() {
		return _userTrackerPath.isNew();
	}

	public boolean setNew(boolean n) {
		return _userTrackerPath.setNew(n);
	}

	public boolean isCachedModel() {
		return _userTrackerPath.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_userTrackerPath.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _userTrackerPath.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_userTrackerPath.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _userTrackerPath.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userTrackerPath.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userTrackerPath.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _userTrackerPath.clone();
	}

	public int compareTo(
		com.liferay.portal.model.UserTrackerPath userTrackerPath) {
		return _userTrackerPath.compareTo(userTrackerPath);
	}

	public int hashCode() {
		return _userTrackerPath.hashCode();
	}

	public java.lang.String toString() {
		return _userTrackerPath.toString();
	}

	public java.lang.String toXmlString() {
		return _userTrackerPath.toXmlString();
	}

	public UserTrackerPath getWrappedUserTrackerPath() {
		return _userTrackerPath;
	}

	private UserTrackerPath _userTrackerPath;
}