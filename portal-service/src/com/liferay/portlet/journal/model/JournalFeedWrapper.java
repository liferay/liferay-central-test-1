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

package com.liferay.portlet.journal.model;


/**
 * <a href="JournalFeedSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link JournalFeed}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalFeed
 * @generated
 */
public class JournalFeedWrapper implements JournalFeed {
	public JournalFeedWrapper(JournalFeed journalFeed) {
		_journalFeed = journalFeed;
	}

	public long getPrimaryKey() {
		return _journalFeed.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_journalFeed.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _journalFeed.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_journalFeed.setUuid(uuid);
	}

	public long getId() {
		return _journalFeed.getId();
	}

	public void setId(long id) {
		_journalFeed.setId(id);
	}

	public long getGroupId() {
		return _journalFeed.getGroupId();
	}

	public void setGroupId(long groupId) {
		_journalFeed.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _journalFeed.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_journalFeed.setCompanyId(companyId);
	}

	public long getUserId() {
		return _journalFeed.getUserId();
	}

	public void setUserId(long userId) {
		_journalFeed.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _journalFeed.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_journalFeed.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _journalFeed.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_journalFeed.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _journalFeed.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_journalFeed.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _journalFeed.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_journalFeed.setModifiedDate(modifiedDate);
	}

	public java.lang.String getFeedId() {
		return _journalFeed.getFeedId();
	}

	public void setFeedId(java.lang.String feedId) {
		_journalFeed.setFeedId(feedId);
	}

	public java.lang.String getName() {
		return _journalFeed.getName();
	}

	public void setName(java.lang.String name) {
		_journalFeed.setName(name);
	}

	public java.lang.String getDescription() {
		return _journalFeed.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_journalFeed.setDescription(description);
	}

	public java.lang.String getType() {
		return _journalFeed.getType();
	}

	public void setType(java.lang.String type) {
		_journalFeed.setType(type);
	}

	public java.lang.String getStructureId() {
		return _journalFeed.getStructureId();
	}

	public void setStructureId(java.lang.String structureId) {
		_journalFeed.setStructureId(structureId);
	}

	public java.lang.String getTemplateId() {
		return _journalFeed.getTemplateId();
	}

	public void setTemplateId(java.lang.String templateId) {
		_journalFeed.setTemplateId(templateId);
	}

	public java.lang.String getRendererTemplateId() {
		return _journalFeed.getRendererTemplateId();
	}

	public void setRendererTemplateId(java.lang.String rendererTemplateId) {
		_journalFeed.setRendererTemplateId(rendererTemplateId);
	}

	public int getDelta() {
		return _journalFeed.getDelta();
	}

	public void setDelta(int delta) {
		_journalFeed.setDelta(delta);
	}

	public java.lang.String getOrderByCol() {
		return _journalFeed.getOrderByCol();
	}

	public void setOrderByCol(java.lang.String orderByCol) {
		_journalFeed.setOrderByCol(orderByCol);
	}

	public java.lang.String getOrderByType() {
		return _journalFeed.getOrderByType();
	}

	public void setOrderByType(java.lang.String orderByType) {
		_journalFeed.setOrderByType(orderByType);
	}

	public java.lang.String getTargetLayoutFriendlyUrl() {
		return _journalFeed.getTargetLayoutFriendlyUrl();
	}

	public void setTargetLayoutFriendlyUrl(
		java.lang.String targetLayoutFriendlyUrl) {
		_journalFeed.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
	}

	public java.lang.String getTargetPortletId() {
		return _journalFeed.getTargetPortletId();
	}

	public void setTargetPortletId(java.lang.String targetPortletId) {
		_journalFeed.setTargetPortletId(targetPortletId);
	}

	public java.lang.String getContentField() {
		return _journalFeed.getContentField();
	}

	public void setContentField(java.lang.String contentField) {
		_journalFeed.setContentField(contentField);
	}

	public java.lang.String getFeedType() {
		return _journalFeed.getFeedType();
	}

	public void setFeedType(java.lang.String feedType) {
		_journalFeed.setFeedType(feedType);
	}

	public double getFeedVersion() {
		return _journalFeed.getFeedVersion();
	}

	public void setFeedVersion(double feedVersion) {
		_journalFeed.setFeedVersion(feedVersion);
	}

	public com.liferay.portlet.journal.model.JournalFeed toEscapedModel() {
		return _journalFeed.toEscapedModel();
	}

	public boolean isNew() {
		return _journalFeed.isNew();
	}

	public boolean setNew(boolean n) {
		return _journalFeed.setNew(n);
	}

	public boolean isCachedModel() {
		return _journalFeed.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_journalFeed.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _journalFeed.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_journalFeed.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _journalFeed.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalFeed.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalFeed.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _journalFeed.clone();
	}

	public int compareTo(
		com.liferay.portlet.journal.model.JournalFeed journalFeed) {
		return _journalFeed.compareTo(journalFeed);
	}

	public int hashCode() {
		return _journalFeed.hashCode();
	}

	public java.lang.String toString() {
		return _journalFeed.toString();
	}

	public java.lang.String toXmlString() {
		return _journalFeed.toXmlString();
	}

	private JournalFeed _journalFeed;
}