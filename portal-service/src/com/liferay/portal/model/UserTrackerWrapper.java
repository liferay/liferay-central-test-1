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
 * This class is a wrapper for {@link UserTracker}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserTracker
 * @generated
 */
public class UserTrackerWrapper implements UserTracker {
	public UserTrackerWrapper(UserTracker userTracker) {
		_userTracker = userTracker;
	}

	/**
	* Gets the primary key of this user tracker.
	*
	* @return the primary key of this user tracker
	*/
	public long getPrimaryKey() {
		return _userTracker.getPrimaryKey();
	}

	/**
	* Sets the primary key of this user tracker
	*
	* @param pk the primary key of this user tracker
	*/
	public void setPrimaryKey(long pk) {
		_userTracker.setPrimaryKey(pk);
	}

	/**
	* Gets the user tracker id of this user tracker.
	*
	* @return the user tracker id of this user tracker
	*/
	public long getUserTrackerId() {
		return _userTracker.getUserTrackerId();
	}

	/**
	* Sets the user tracker id of this user tracker.
	*
	* @param userTrackerId the user tracker id of this user tracker
	*/
	public void setUserTrackerId(long userTrackerId) {
		_userTracker.setUserTrackerId(userTrackerId);
	}

	/**
	* Gets the company id of this user tracker.
	*
	* @return the company id of this user tracker
	*/
	public long getCompanyId() {
		return _userTracker.getCompanyId();
	}

	/**
	* Sets the company id of this user tracker.
	*
	* @param companyId the company id of this user tracker
	*/
	public void setCompanyId(long companyId) {
		_userTracker.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this user tracker.
	*
	* @return the user id of this user tracker
	*/
	public long getUserId() {
		return _userTracker.getUserId();
	}

	/**
	* Sets the user id of this user tracker.
	*
	* @param userId the user id of this user tracker
	*/
	public void setUserId(long userId) {
		_userTracker.setUserId(userId);
	}

	/**
	* Gets the user uuid of this user tracker.
	*
	* @return the user uuid of this user tracker
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTracker.getUserUuid();
	}

	/**
	* Sets the user uuid of this user tracker.
	*
	* @param userUuid the user uuid of this user tracker
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_userTracker.setUserUuid(userUuid);
	}

	/**
	* Gets the modified date of this user tracker.
	*
	* @return the modified date of this user tracker
	*/
	public java.util.Date getModifiedDate() {
		return _userTracker.getModifiedDate();
	}

	/**
	* Sets the modified date of this user tracker.
	*
	* @param modifiedDate the modified date of this user tracker
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_userTracker.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the session id of this user tracker.
	*
	* @return the session id of this user tracker
	*/
	public java.lang.String getSessionId() {
		return _userTracker.getSessionId();
	}

	/**
	* Sets the session id of this user tracker.
	*
	* @param sessionId the session id of this user tracker
	*/
	public void setSessionId(java.lang.String sessionId) {
		_userTracker.setSessionId(sessionId);
	}

	/**
	* Gets the remote addr of this user tracker.
	*
	* @return the remote addr of this user tracker
	*/
	public java.lang.String getRemoteAddr() {
		return _userTracker.getRemoteAddr();
	}

	/**
	* Sets the remote addr of this user tracker.
	*
	* @param remoteAddr the remote addr of this user tracker
	*/
	public void setRemoteAddr(java.lang.String remoteAddr) {
		_userTracker.setRemoteAddr(remoteAddr);
	}

	/**
	* Gets the remote host of this user tracker.
	*
	* @return the remote host of this user tracker
	*/
	public java.lang.String getRemoteHost() {
		return _userTracker.getRemoteHost();
	}

	/**
	* Sets the remote host of this user tracker.
	*
	* @param remoteHost the remote host of this user tracker
	*/
	public void setRemoteHost(java.lang.String remoteHost) {
		_userTracker.setRemoteHost(remoteHost);
	}

	/**
	* Gets the user agent of this user tracker.
	*
	* @return the user agent of this user tracker
	*/
	public java.lang.String getUserAgent() {
		return _userTracker.getUserAgent();
	}

	/**
	* Sets the user agent of this user tracker.
	*
	* @param userAgent the user agent of this user tracker
	*/
	public void setUserAgent(java.lang.String userAgent) {
		_userTracker.setUserAgent(userAgent);
	}

	public boolean isNew() {
		return _userTracker.isNew();
	}

	public void setNew(boolean n) {
		_userTracker.setNew(n);
	}

	public boolean isCachedModel() {
		return _userTracker.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_userTracker.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _userTracker.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_userTracker.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _userTracker.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userTracker.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userTracker.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new UserTrackerWrapper((UserTracker)_userTracker.clone());
	}

	public int compareTo(com.liferay.portal.model.UserTracker userTracker) {
		return _userTracker.compareTo(userTracker);
	}

	public int hashCode() {
		return _userTracker.hashCode();
	}

	public com.liferay.portal.model.UserTracker toEscapedModel() {
		return new UserTrackerWrapper(_userTracker.toEscapedModel());
	}

	public java.lang.String toString() {
		return _userTracker.toString();
	}

	public java.lang.String toXmlString() {
		return _userTracker.toXmlString();
	}

	public java.lang.String getFullName() {
		return _userTracker.getFullName();
	}

	public java.lang.String getEmailAddress() {
		return _userTracker.getEmailAddress();
	}

	public java.util.List<com.liferay.portal.model.UserTrackerPath> getPaths() {
		return _userTracker.getPaths();
	}

	public void addPath(com.liferay.portal.model.UserTrackerPath path) {
		_userTracker.addPath(path);
	}

	public int getHits() {
		return _userTracker.getHits();
	}

	public UserTracker getWrappedUserTracker() {
		return _userTracker;
	}

	private UserTracker _userTracker;
}