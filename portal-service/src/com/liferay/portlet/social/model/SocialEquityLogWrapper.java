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

package com.liferay.portlet.social.model;

/**
 * <p>
 * This class is a wrapper for {@link SocialEquityLog}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityLog
 * @generated
 */
public class SocialEquityLogWrapper implements SocialEquityLog {
	public SocialEquityLogWrapper(SocialEquityLog socialEquityLog) {
		_socialEquityLog = socialEquityLog;
	}

	/**
	* Gets the primary key of this social equity log.
	*
	* @return the primary key of this social equity log
	*/
	public long getPrimaryKey() {
		return _socialEquityLog.getPrimaryKey();
	}

	/**
	* Sets the primary key of this social equity log
	*
	* @param pk the primary key of this social equity log
	*/
	public void setPrimaryKey(long pk) {
		_socialEquityLog.setPrimaryKey(pk);
	}

	/**
	* Gets the equity log id of this social equity log.
	*
	* @return the equity log id of this social equity log
	*/
	public long getEquityLogId() {
		return _socialEquityLog.getEquityLogId();
	}

	/**
	* Sets the equity log id of this social equity log.
	*
	* @param equityLogId the equity log id of this social equity log
	*/
	public void setEquityLogId(long equityLogId) {
		_socialEquityLog.setEquityLogId(equityLogId);
	}

	/**
	* Gets the group id of this social equity log.
	*
	* @return the group id of this social equity log
	*/
	public long getGroupId() {
		return _socialEquityLog.getGroupId();
	}

	/**
	* Sets the group id of this social equity log.
	*
	* @param groupId the group id of this social equity log
	*/
	public void setGroupId(long groupId) {
		_socialEquityLog.setGroupId(groupId);
	}

	/**
	* Gets the company id of this social equity log.
	*
	* @return the company id of this social equity log
	*/
	public long getCompanyId() {
		return _socialEquityLog.getCompanyId();
	}

	/**
	* Sets the company id of this social equity log.
	*
	* @param companyId the company id of this social equity log
	*/
	public void setCompanyId(long companyId) {
		_socialEquityLog.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this social equity log.
	*
	* @return the user id of this social equity log
	*/
	public long getUserId() {
		return _socialEquityLog.getUserId();
	}

	/**
	* Sets the user id of this social equity log.
	*
	* @param userId the user id of this social equity log
	*/
	public void setUserId(long userId) {
		_socialEquityLog.setUserId(userId);
	}

	/**
	* Gets the user uuid of this social equity log.
	*
	* @return the user uuid of this social equity log
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLog.getUserUuid();
	}

	/**
	* Sets the user uuid of this social equity log.
	*
	* @param userUuid the user uuid of this social equity log
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_socialEquityLog.setUserUuid(userUuid);
	}

	/**
	* Gets the asset entry id of this social equity log.
	*
	* @return the asset entry id of this social equity log
	*/
	public long getAssetEntryId() {
		return _socialEquityLog.getAssetEntryId();
	}

	/**
	* Sets the asset entry id of this social equity log.
	*
	* @param assetEntryId the asset entry id of this social equity log
	*/
	public void setAssetEntryId(long assetEntryId) {
		_socialEquityLog.setAssetEntryId(assetEntryId);
	}

	/**
	* Gets the action id of this social equity log.
	*
	* @return the action id of this social equity log
	*/
	public java.lang.String getActionId() {
		return _socialEquityLog.getActionId();
	}

	/**
	* Sets the action id of this social equity log.
	*
	* @param actionId the action id of this social equity log
	*/
	public void setActionId(java.lang.String actionId) {
		_socialEquityLog.setActionId(actionId);
	}

	/**
	* Gets the action date of this social equity log.
	*
	* @return the action date of this social equity log
	*/
	public int getActionDate() {
		return _socialEquityLog.getActionDate();
	}

	/**
	* Sets the action date of this social equity log.
	*
	* @param actionDate the action date of this social equity log
	*/
	public void setActionDate(int actionDate) {
		_socialEquityLog.setActionDate(actionDate);
	}

	/**
	* Gets the active of this social equity log.
	*
	* @return the active of this social equity log
	*/
	public boolean getActive() {
		return _socialEquityLog.getActive();
	}

	/**
	* Determines whether this social equity log is active.
	*
	* @return whether this social equity log is active
	*/
	public boolean isActive() {
		return _socialEquityLog.isActive();
	}

	/**
	* Sets whether this {$entity.humanName} is active.
	*
	* @param active the active of this social equity log
	*/
	public void setActive(boolean active) {
		_socialEquityLog.setActive(active);
	}

	/**
	* Gets the expiration of this social equity log.
	*
	* @return the expiration of this social equity log
	*/
	public int getExpiration() {
		return _socialEquityLog.getExpiration();
	}

	/**
	* Sets the expiration of this social equity log.
	*
	* @param expiration the expiration of this social equity log
	*/
	public void setExpiration(int expiration) {
		_socialEquityLog.setExpiration(expiration);
	}

	/**
	* Gets the type of this social equity log.
	*
	* @return the type of this social equity log
	*/
	public int getType() {
		return _socialEquityLog.getType();
	}

	/**
	* Sets the type of this social equity log.
	*
	* @param type the type of this social equity log
	*/
	public void setType(int type) {
		_socialEquityLog.setType(type);
	}

	/**
	* Gets the value of this social equity log.
	*
	* @return the value of this social equity log
	*/
	public int getValue() {
		return _socialEquityLog.getValue();
	}

	/**
	* Sets the value of this social equity log.
	*
	* @param value the value of this social equity log
	*/
	public void setValue(int value) {
		_socialEquityLog.setValue(value);
	}

	public boolean isNew() {
		return _socialEquityLog.isNew();
	}

	public void setNew(boolean n) {
		_socialEquityLog.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialEquityLog.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialEquityLog.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialEquityLog.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_socialEquityLog.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialEquityLog.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialEquityLog.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialEquityLog.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _socialEquityLog.clone();
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog) {
		return _socialEquityLog.compareTo(socialEquityLog);
	}

	public int hashCode() {
		return _socialEquityLog.hashCode();
	}

	public com.liferay.portlet.social.model.SocialEquityLog toEscapedModel() {
		return _socialEquityLog.toEscapedModel();
	}

	public java.lang.String toString() {
		return _socialEquityLog.toString();
	}

	public java.lang.String toXmlString() {
		return _socialEquityLog.toXmlString();
	}

	public int getLifespan() {
		return _socialEquityLog.getLifespan();
	}

	public SocialEquityLog getWrappedSocialEquityLog() {
		return _socialEquityLog;
	}

	private SocialEquityLog _socialEquityLog;
}