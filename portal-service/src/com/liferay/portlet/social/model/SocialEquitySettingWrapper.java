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
 * This class is a wrapper for {@link SocialEquitySetting}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquitySetting
 * @generated
 */
public class SocialEquitySettingWrapper implements SocialEquitySetting {
	public SocialEquitySettingWrapper(SocialEquitySetting socialEquitySetting) {
		_socialEquitySetting = socialEquitySetting;
	}

	/**
	* Gets the primary key of this social equity setting.
	*
	* @return the primary key of this social equity setting
	*/
	public long getPrimaryKey() {
		return _socialEquitySetting.getPrimaryKey();
	}

	/**
	* Sets the primary key of this social equity setting
	*
	* @param pk the primary key of this social equity setting
	*/
	public void setPrimaryKey(long pk) {
		_socialEquitySetting.setPrimaryKey(pk);
	}

	/**
	* Gets the equity setting id of this social equity setting.
	*
	* @return the equity setting id of this social equity setting
	*/
	public long getEquitySettingId() {
		return _socialEquitySetting.getEquitySettingId();
	}

	/**
	* Sets the equity setting id of this social equity setting.
	*
	* @param equitySettingId the equity setting id of this social equity setting
	*/
	public void setEquitySettingId(long equitySettingId) {
		_socialEquitySetting.setEquitySettingId(equitySettingId);
	}

	/**
	* Gets the group id of this social equity setting.
	*
	* @return the group id of this social equity setting
	*/
	public long getGroupId() {
		return _socialEquitySetting.getGroupId();
	}

	/**
	* Sets the group id of this social equity setting.
	*
	* @param groupId the group id of this social equity setting
	*/
	public void setGroupId(long groupId) {
		_socialEquitySetting.setGroupId(groupId);
	}

	/**
	* Gets the company id of this social equity setting.
	*
	* @return the company id of this social equity setting
	*/
	public long getCompanyId() {
		return _socialEquitySetting.getCompanyId();
	}

	/**
	* Sets the company id of this social equity setting.
	*
	* @param companyId the company id of this social equity setting
	*/
	public void setCompanyId(long companyId) {
		_socialEquitySetting.setCompanyId(companyId);
	}

	/**
	* Gets the class name of the model instance this social equity setting is polymorphically associated with.
	*
	* @return the class name of the model instance this social equity setting is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _socialEquitySetting.getClassName();
	}

	/**
	* Gets the class name id of this social equity setting.
	*
	* @return the class name id of this social equity setting
	*/
	public long getClassNameId() {
		return _socialEquitySetting.getClassNameId();
	}

	/**
	* Sets the class name id of this social equity setting.
	*
	* @param classNameId the class name id of this social equity setting
	*/
	public void setClassNameId(long classNameId) {
		_socialEquitySetting.setClassNameId(classNameId);
	}

	/**
	* Gets the action id of this social equity setting.
	*
	* @return the action id of this social equity setting
	*/
	public java.lang.String getActionId() {
		return _socialEquitySetting.getActionId();
	}

	/**
	* Sets the action id of this social equity setting.
	*
	* @param actionId the action id of this social equity setting
	*/
	public void setActionId(java.lang.String actionId) {
		_socialEquitySetting.setActionId(actionId);
	}

	/**
	* Gets the daily limit of this social equity setting.
	*
	* @return the daily limit of this social equity setting
	*/
	public int getDailyLimit() {
		return _socialEquitySetting.getDailyLimit();
	}

	/**
	* Sets the daily limit of this social equity setting.
	*
	* @param dailyLimit the daily limit of this social equity setting
	*/
	public void setDailyLimit(int dailyLimit) {
		_socialEquitySetting.setDailyLimit(dailyLimit);
	}

	/**
	* Gets the lifespan of this social equity setting.
	*
	* @return the lifespan of this social equity setting
	*/
	public int getLifespan() {
		return _socialEquitySetting.getLifespan();
	}

	/**
	* Sets the lifespan of this social equity setting.
	*
	* @param lifespan the lifespan of this social equity setting
	*/
	public void setLifespan(int lifespan) {
		_socialEquitySetting.setLifespan(lifespan);
	}

	/**
	* Gets the type of this social equity setting.
	*
	* @return the type of this social equity setting
	*/
	public int getType() {
		return _socialEquitySetting.getType();
	}

	/**
	* Sets the type of this social equity setting.
	*
	* @param type the type of this social equity setting
	*/
	public void setType(int type) {
		_socialEquitySetting.setType(type);
	}

	/**
	* Gets the unique entry of this social equity setting.
	*
	* @return the unique entry of this social equity setting
	*/
	public boolean getUniqueEntry() {
		return _socialEquitySetting.getUniqueEntry();
	}

	/**
	* Determines if this social equity setting is unique entry.
	*
	* @return <code>true</code> if this social equity setting is unique entry; <code>false</code> otherwise
	*/
	public boolean isUniqueEntry() {
		return _socialEquitySetting.isUniqueEntry();
	}

	/**
	* Sets whether this social equity setting is unique entry.
	*
	* @param uniqueEntry the unique entry of this social equity setting
	*/
	public void setUniqueEntry(boolean uniqueEntry) {
		_socialEquitySetting.setUniqueEntry(uniqueEntry);
	}

	/**
	* Gets the value of this social equity setting.
	*
	* @return the value of this social equity setting
	*/
	public int getValue() {
		return _socialEquitySetting.getValue();
	}

	/**
	* Sets the value of this social equity setting.
	*
	* @param value the value of this social equity setting
	*/
	public void setValue(int value) {
		_socialEquitySetting.setValue(value);
	}

	public boolean isNew() {
		return _socialEquitySetting.isNew();
	}

	public void setNew(boolean n) {
		_socialEquitySetting.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialEquitySetting.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialEquitySetting.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialEquitySetting.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_socialEquitySetting.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialEquitySetting.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialEquitySetting.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialEquitySetting.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _socialEquitySetting.clone();
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting) {
		return _socialEquitySetting.compareTo(socialEquitySetting);
	}

	public int hashCode() {
		return _socialEquitySetting.hashCode();
	}

	public com.liferay.portlet.social.model.SocialEquitySetting toEscapedModel() {
		return _socialEquitySetting.toEscapedModel();
	}

	public java.lang.String toString() {
		return _socialEquitySetting.toString();
	}

	public java.lang.String toXmlString() {
		return _socialEquitySetting.toXmlString();
	}

	public void update(
		com.liferay.portlet.social.model.SocialEquityActionMapping equityActionMapping) {
		_socialEquitySetting.update(equityActionMapping);
	}

	public SocialEquitySetting getWrappedSocialEquitySetting() {
		return _socialEquitySetting;
	}

	private SocialEquitySetting _socialEquitySetting;
}