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

package com.liferay.portlet.messageboards.model;

/**
 * <p>
 * This class is a wrapper for {@link MBThread}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBThread
 * @generated
 */
public class MBThreadWrapper implements MBThread {
	public MBThreadWrapper(MBThread mbThread) {
		_mbThread = mbThread;
	}

	/**
	* Gets the primary key of this message boards thread.
	*
	* @return the primary key of this message boards thread
	*/
	public long getPrimaryKey() {
		return _mbThread.getPrimaryKey();
	}

	/**
	* Sets the primary key of this message boards thread
	*
	* @param pk the primary key of this message boards thread
	*/
	public void setPrimaryKey(long pk) {
		_mbThread.setPrimaryKey(pk);
	}

	/**
	* Gets the thread id of this message boards thread.
	*
	* @return the thread id of this message boards thread
	*/
	public long getThreadId() {
		return _mbThread.getThreadId();
	}

	/**
	* Sets the thread id of this message boards thread.
	*
	* @param threadId the thread id of this message boards thread
	*/
	public void setThreadId(long threadId) {
		_mbThread.setThreadId(threadId);
	}

	/**
	* Gets the group id of this message boards thread.
	*
	* @return the group id of this message boards thread
	*/
	public long getGroupId() {
		return _mbThread.getGroupId();
	}

	/**
	* Sets the group id of this message boards thread.
	*
	* @param groupId the group id of this message boards thread
	*/
	public void setGroupId(long groupId) {
		_mbThread.setGroupId(groupId);
	}

	/**
	* Gets the category id of this message boards thread.
	*
	* @return the category id of this message boards thread
	*/
	public long getCategoryId() {
		return _mbThread.getCategoryId();
	}

	/**
	* Sets the category id of this message boards thread.
	*
	* @param categoryId the category id of this message boards thread
	*/
	public void setCategoryId(long categoryId) {
		_mbThread.setCategoryId(categoryId);
	}

	/**
	* Gets the root message id of this message boards thread.
	*
	* @return the root message id of this message boards thread
	*/
	public long getRootMessageId() {
		return _mbThread.getRootMessageId();
	}

	/**
	* Sets the root message id of this message boards thread.
	*
	* @param rootMessageId the root message id of this message boards thread
	*/
	public void setRootMessageId(long rootMessageId) {
		_mbThread.setRootMessageId(rootMessageId);
	}

	/**
	* Gets the message count of this message boards thread.
	*
	* @return the message count of this message boards thread
	*/
	public int getMessageCount() {
		return _mbThread.getMessageCount();
	}

	/**
	* Sets the message count of this message boards thread.
	*
	* @param messageCount the message count of this message boards thread
	*/
	public void setMessageCount(int messageCount) {
		_mbThread.setMessageCount(messageCount);
	}

	/**
	* Gets the view count of this message boards thread.
	*
	* @return the view count of this message boards thread
	*/
	public int getViewCount() {
		return _mbThread.getViewCount();
	}

	/**
	* Sets the view count of this message boards thread.
	*
	* @param viewCount the view count of this message boards thread
	*/
	public void setViewCount(int viewCount) {
		_mbThread.setViewCount(viewCount);
	}

	/**
	* Gets the last post by user id of this message boards thread.
	*
	* @return the last post by user id of this message boards thread
	*/
	public long getLastPostByUserId() {
		return _mbThread.getLastPostByUserId();
	}

	/**
	* Sets the last post by user id of this message boards thread.
	*
	* @param lastPostByUserId the last post by user id of this message boards thread
	*/
	public void setLastPostByUserId(long lastPostByUserId) {
		_mbThread.setLastPostByUserId(lastPostByUserId);
	}

	/**
	* Gets the last post by user uuid of this message boards thread.
	*
	* @return the last post by user uuid of this message boards thread
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getLastPostByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThread.getLastPostByUserUuid();
	}

	/**
	* Sets the last post by user uuid of this message boards thread.
	*
	* @param lastPostByUserUuid the last post by user uuid of this message boards thread
	*/
	public void setLastPostByUserUuid(java.lang.String lastPostByUserUuid) {
		_mbThread.setLastPostByUserUuid(lastPostByUserUuid);
	}

	/**
	* Gets the last post date of this message boards thread.
	*
	* @return the last post date of this message boards thread
	*/
	public java.util.Date getLastPostDate() {
		return _mbThread.getLastPostDate();
	}

	/**
	* Sets the last post date of this message boards thread.
	*
	* @param lastPostDate the last post date of this message boards thread
	*/
	public void setLastPostDate(java.util.Date lastPostDate) {
		_mbThread.setLastPostDate(lastPostDate);
	}

	/**
	* Gets the priority of this message boards thread.
	*
	* @return the priority of this message boards thread
	*/
	public double getPriority() {
		return _mbThread.getPriority();
	}

	/**
	* Sets the priority of this message boards thread.
	*
	* @param priority the priority of this message boards thread
	*/
	public void setPriority(double priority) {
		_mbThread.setPriority(priority);
	}

	/**
	* Gets the status of this message boards thread.
	*
	* @return the status of this message boards thread
	*/
	public int getStatus() {
		return _mbThread.getStatus();
	}

	/**
	* Sets the status of this message boards thread.
	*
	* @param status the status of this message boards thread
	*/
	public void setStatus(int status) {
		_mbThread.setStatus(status);
	}

	/**
	* Gets the status by user id of this message boards thread.
	*
	* @return the status by user id of this message boards thread
	*/
	public long getStatusByUserId() {
		return _mbThread.getStatusByUserId();
	}

	/**
	* Sets the status by user id of this message boards thread.
	*
	* @param statusByUserId the status by user id of this message boards thread
	*/
	public void setStatusByUserId(long statusByUserId) {
		_mbThread.setStatusByUserId(statusByUserId);
	}

	/**
	* Gets the status by user uuid of this message boards thread.
	*
	* @return the status by user uuid of this message boards thread
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThread.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this message boards thread.
	*
	* @param statusByUserUuid the status by user uuid of this message boards thread
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_mbThread.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Gets the status by user name of this message boards thread.
	*
	* @return the status by user name of this message boards thread
	*/
	public java.lang.String getStatusByUserName() {
		return _mbThread.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this message boards thread.
	*
	* @param statusByUserName the status by user name of this message boards thread
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_mbThread.setStatusByUserName(statusByUserName);
	}

	/**
	* Gets the status date of this message boards thread.
	*
	* @return the status date of this message boards thread
	*/
	public java.util.Date getStatusDate() {
		return _mbThread.getStatusDate();
	}

	/**
	* Sets the status date of this message boards thread.
	*
	* @param statusDate the status date of this message boards thread
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_mbThread.setStatusDate(statusDate);
	}

	/**
	* @deprecated {@link #isApproved}
	*/
	public boolean getApproved() {
		return _mbThread.getApproved();
	}

	/**
	* Determines whether this message boards thread is approved.
	*
	* @return true if this message boards thread is approved; false otherwise
	*/
	public boolean isApproved() {
		return _mbThread.isApproved();
	}

	/**
	* Determines whether this message boards thread is a draft.
	*
	* @return true if this message boards thread is a draft; false otherwise
	*/
	public boolean isDraft() {
		return _mbThread.isDraft();
	}

	/**
	* Determines whether this message boards thread is expired.
	*
	* @return true if this message boards thread is expired; false otherwise
	*/
	public boolean isExpired() {
		return _mbThread.isExpired();
	}

	/**
	* Determines whether this message boards thread is pending.
	*
	* @return true if this message boards thread is pending; false otherwise
	*/
	public boolean isPending() {
		return _mbThread.isPending();
	}

	public boolean isNew() {
		return _mbThread.isNew();
	}

	public void setNew(boolean n) {
		_mbThread.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbThread.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbThread.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbThread.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_mbThread.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbThread.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbThread.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbThread.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _mbThread.clone();
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBThread mbThread) {
		return _mbThread.compareTo(mbThread);
	}

	public int hashCode() {
		return _mbThread.hashCode();
	}

	public com.liferay.portlet.messageboards.model.MBThread toEscapedModel() {
		return _mbThread.toEscapedModel();
	}

	public java.lang.String toString() {
		return _mbThread.toString();
	}

	public java.lang.String toXmlString() {
		return _mbThread.toXmlString();
	}

	public java.lang.String getAttachmentsDir() {
		return _mbThread.getAttachmentsDir();
	}

	public com.liferay.portal.model.Lock getLock() {
		return _mbThread.getLock();
	}

	public boolean hasLock(long userId) {
		return _mbThread.hasLock(userId);
	}

	public boolean isLocked() {
		return _mbThread.isLocked();
	}

	public MBThread getWrappedMBThread() {
		return _mbThread;
	}

	private MBThread _mbThread;
}