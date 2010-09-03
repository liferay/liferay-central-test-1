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
 * This class is a wrapper for {@link MBStatsUser}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBStatsUser
 * @generated
 */
public class MBStatsUserWrapper implements MBStatsUser {
	public MBStatsUserWrapper(MBStatsUser mbStatsUser) {
		_mbStatsUser = mbStatsUser;
	}

	/**
	* Gets the primary key of this message boards stats user.
	*
	* @return the primary key of this message boards stats user
	*/
	public long getPrimaryKey() {
		return _mbStatsUser.getPrimaryKey();
	}

	/**
	* Sets the primary key of this message boards stats user
	*
	* @param pk the primary key of this message boards stats user
	*/
	public void setPrimaryKey(long pk) {
		_mbStatsUser.setPrimaryKey(pk);
	}

	/**
	* Gets the stats user id of this message boards stats user.
	*
	* @return the stats user id of this message boards stats user
	*/
	public long getStatsUserId() {
		return _mbStatsUser.getStatsUserId();
	}

	/**
	* Sets the stats user id of this message boards stats user.
	*
	* @param statsUserId the stats user id of this message boards stats user
	*/
	public void setStatsUserId(long statsUserId) {
		_mbStatsUser.setStatsUserId(statsUserId);
	}

	/**
	* Gets the stats user uuid of this message boards stats user.
	*
	* @return the stats user uuid of this message boards stats user
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatsUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbStatsUser.getStatsUserUuid();
	}

	/**
	* Sets the stats user uuid of this message boards stats user.
	*
	* @param statsUserUuid the stats user uuid of this message boards stats user
	*/
	public void setStatsUserUuid(java.lang.String statsUserUuid) {
		_mbStatsUser.setStatsUserUuid(statsUserUuid);
	}

	/**
	* Gets the group id of this message boards stats user.
	*
	* @return the group id of this message boards stats user
	*/
	public long getGroupId() {
		return _mbStatsUser.getGroupId();
	}

	/**
	* Sets the group id of this message boards stats user.
	*
	* @param groupId the group id of this message boards stats user
	*/
	public void setGroupId(long groupId) {
		_mbStatsUser.setGroupId(groupId);
	}

	/**
	* Gets the user id of this message boards stats user.
	*
	* @return the user id of this message boards stats user
	*/
	public long getUserId() {
		return _mbStatsUser.getUserId();
	}

	/**
	* Sets the user id of this message boards stats user.
	*
	* @param userId the user id of this message boards stats user
	*/
	public void setUserId(long userId) {
		_mbStatsUser.setUserId(userId);
	}

	/**
	* Gets the user uuid of this message boards stats user.
	*
	* @return the user uuid of this message boards stats user
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbStatsUser.getUserUuid();
	}

	/**
	* Sets the user uuid of this message boards stats user.
	*
	* @param userUuid the user uuid of this message boards stats user
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_mbStatsUser.setUserUuid(userUuid);
	}

	/**
	* Gets the message count of this message boards stats user.
	*
	* @return the message count of this message boards stats user
	*/
	public int getMessageCount() {
		return _mbStatsUser.getMessageCount();
	}

	/**
	* Sets the message count of this message boards stats user.
	*
	* @param messageCount the message count of this message boards stats user
	*/
	public void setMessageCount(int messageCount) {
		_mbStatsUser.setMessageCount(messageCount);
	}

	/**
	* Gets the last post date of this message boards stats user.
	*
	* @return the last post date of this message boards stats user
	*/
	public java.util.Date getLastPostDate() {
		return _mbStatsUser.getLastPostDate();
	}

	/**
	* Sets the last post date of this message boards stats user.
	*
	* @param lastPostDate the last post date of this message boards stats user
	*/
	public void setLastPostDate(java.util.Date lastPostDate) {
		_mbStatsUser.setLastPostDate(lastPostDate);
	}

	public boolean isNew() {
		return _mbStatsUser.isNew();
	}

	public void setNew(boolean n) {
		_mbStatsUser.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbStatsUser.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbStatsUser.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbStatsUser.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_mbStatsUser.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbStatsUser.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbStatsUser.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbStatsUser.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _mbStatsUser.clone();
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser) {
		return _mbStatsUser.compareTo(mbStatsUser);
	}

	public int hashCode() {
		return _mbStatsUser.hashCode();
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser toEscapedModel() {
		return _mbStatsUser.toEscapedModel();
	}

	public java.lang.String toString() {
		return _mbStatsUser.toString();
	}

	public java.lang.String toXmlString() {
		return _mbStatsUser.toXmlString();
	}

	public MBStatsUser getWrappedMBStatsUser() {
		return _mbStatsUser;
	}

	private MBStatsUser _mbStatsUser;
}