/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.announcements.model;


/**
 * <a href="AnnouncementsEntrySoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AnnouncementsEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsEntry
 * @generated
 */
public class AnnouncementsEntryWrapper implements AnnouncementsEntry {
	public AnnouncementsEntryWrapper(AnnouncementsEntry announcementsEntry) {
		_announcementsEntry = announcementsEntry;
	}

	public long getPrimaryKey() {
		return _announcementsEntry.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_announcementsEntry.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _announcementsEntry.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_announcementsEntry.setUuid(uuid);
	}

	public long getEntryId() {
		return _announcementsEntry.getEntryId();
	}

	public void setEntryId(long entryId) {
		_announcementsEntry.setEntryId(entryId);
	}

	public long getCompanyId() {
		return _announcementsEntry.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_announcementsEntry.setCompanyId(companyId);
	}

	public long getUserId() {
		return _announcementsEntry.getUserId();
	}

	public void setUserId(long userId) {
		_announcementsEntry.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _announcementsEntry.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_announcementsEntry.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _announcementsEntry.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_announcementsEntry.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _announcementsEntry.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_announcementsEntry.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _announcementsEntry.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_announcementsEntry.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _announcementsEntry.getClassName();
	}

	public long getClassNameId() {
		return _announcementsEntry.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_announcementsEntry.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _announcementsEntry.getClassPK();
	}

	public void setClassPK(long classPK) {
		_announcementsEntry.setClassPK(classPK);
	}

	public java.lang.String getTitle() {
		return _announcementsEntry.getTitle();
	}

	public void setTitle(java.lang.String title) {
		_announcementsEntry.setTitle(title);
	}

	public java.lang.String getContent() {
		return _announcementsEntry.getContent();
	}

	public void setContent(java.lang.String content) {
		_announcementsEntry.setContent(content);
	}

	public java.lang.String getUrl() {
		return _announcementsEntry.getUrl();
	}

	public void setUrl(java.lang.String url) {
		_announcementsEntry.setUrl(url);
	}

	public java.lang.String getType() {
		return _announcementsEntry.getType();
	}

	public void setType(java.lang.String type) {
		_announcementsEntry.setType(type);
	}

	public java.util.Date getDisplayDate() {
		return _announcementsEntry.getDisplayDate();
	}

	public void setDisplayDate(java.util.Date displayDate) {
		_announcementsEntry.setDisplayDate(displayDate);
	}

	public java.util.Date getExpirationDate() {
		return _announcementsEntry.getExpirationDate();
	}

	public void setExpirationDate(java.util.Date expirationDate) {
		_announcementsEntry.setExpirationDate(expirationDate);
	}

	public int getPriority() {
		return _announcementsEntry.getPriority();
	}

	public void setPriority(int priority) {
		_announcementsEntry.setPriority(priority);
	}

	public boolean getAlert() {
		return _announcementsEntry.getAlert();
	}

	public boolean isAlert() {
		return _announcementsEntry.isAlert();
	}

	public void setAlert(boolean alert) {
		_announcementsEntry.setAlert(alert);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsEntry toEscapedModel() {
		return _announcementsEntry.toEscapedModel();
	}

	public boolean isNew() {
		return _announcementsEntry.isNew();
	}

	public boolean setNew(boolean n) {
		return _announcementsEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _announcementsEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_announcementsEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _announcementsEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_announcementsEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _announcementsEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _announcementsEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_announcementsEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _announcementsEntry.clone();
	}

	public int compareTo(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry) {
		return _announcementsEntry.compareTo(announcementsEntry);
	}

	public int hashCode() {
		return _announcementsEntry.hashCode();
	}

	public java.lang.String toString() {
		return _announcementsEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _announcementsEntry.toXmlString();
	}

	public AnnouncementsEntry getWrappedAnnouncementsEntry() {
		return _announcementsEntry;
	}

	private AnnouncementsEntry _announcementsEntry;
}