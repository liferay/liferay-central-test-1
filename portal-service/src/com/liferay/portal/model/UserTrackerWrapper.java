/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.model;


/**
 * <a href="UserTrackerSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
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

	public long getPrimaryKey() {
		return _userTracker.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_userTracker.setPrimaryKey(pk);
	}

	public long getUserTrackerId() {
		return _userTracker.getUserTrackerId();
	}

	public void setUserTrackerId(long userTrackerId) {
		_userTracker.setUserTrackerId(userTrackerId);
	}

	public long getCompanyId() {
		return _userTracker.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_userTracker.setCompanyId(companyId);
	}

	public long getUserId() {
		return _userTracker.getUserId();
	}

	public void setUserId(long userId) {
		_userTracker.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _userTracker.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_userTracker.setUserUuid(userUuid);
	}

	public java.util.Date getModifiedDate() {
		return _userTracker.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_userTracker.setModifiedDate(modifiedDate);
	}

	public java.lang.String getSessionId() {
		return _userTracker.getSessionId();
	}

	public void setSessionId(java.lang.String sessionId) {
		_userTracker.setSessionId(sessionId);
	}

	public java.lang.String getRemoteAddr() {
		return _userTracker.getRemoteAddr();
	}

	public void setRemoteAddr(java.lang.String remoteAddr) {
		_userTracker.setRemoteAddr(remoteAddr);
	}

	public java.lang.String getRemoteHost() {
		return _userTracker.getRemoteHost();
	}

	public void setRemoteHost(java.lang.String remoteHost) {
		_userTracker.setRemoteHost(remoteHost);
	}

	public java.lang.String getUserAgent() {
		return _userTracker.getUserAgent();
	}

	public void setUserAgent(java.lang.String userAgent) {
		_userTracker.setUserAgent(userAgent);
	}

	public com.liferay.portal.model.UserTracker toEscapedModel() {
		return _userTracker.toEscapedModel();
	}

	public boolean isNew() {
		return _userTracker.isNew();
	}

	public boolean setNew(boolean n) {
		return _userTracker.setNew(n);
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
		return _userTracker.clone();
	}

	public int compareTo(com.liferay.portal.model.UserTracker userTracker) {
		return _userTracker.compareTo(userTracker);
	}

	public int hashCode() {
		return _userTracker.hashCode();
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