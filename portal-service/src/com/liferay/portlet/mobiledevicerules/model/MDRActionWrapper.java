/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.model;

/**
 * <p>
 * This class is a wrapper for {@link MDRAction}.
 * </p>
 *
 * @author    Edward C. Han
 * @see       MDRAction
 * @generated
 */
public class MDRActionWrapper implements MDRAction {
	public MDRActionWrapper(MDRAction mdrAction) {
		_mdrAction = mdrAction;
	}

	public Class<?> getModelClass() {
		return MDRAction.class;
	}

	public String getModelClassName() {
		return MDRAction.class.getName();
	}

	/**
	* Returns the primary key of this m d r action.
	*
	* @return the primary key of this m d r action
	*/
	public long getPrimaryKey() {
		return _mdrAction.getPrimaryKey();
	}

	/**
	* Sets the primary key of this m d r action.
	*
	* @param primaryKey the primary key of this m d r action
	*/
	public void setPrimaryKey(long primaryKey) {
		_mdrAction.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this m d r action.
	*
	* @return the uuid of this m d r action
	*/
	public java.lang.String getUuid() {
		return _mdrAction.getUuid();
	}

	/**
	* Sets the uuid of this m d r action.
	*
	* @param uuid the uuid of this m d r action
	*/
	public void setUuid(java.lang.String uuid) {
		_mdrAction.setUuid(uuid);
	}

	/**
	* Returns the action ID of this m d r action.
	*
	* @return the action ID of this m d r action
	*/
	public long getActionId() {
		return _mdrAction.getActionId();
	}

	/**
	* Sets the action ID of this m d r action.
	*
	* @param actionId the action ID of this m d r action
	*/
	public void setActionId(long actionId) {
		_mdrAction.setActionId(actionId);
	}

	/**
	* Returns the group ID of this m d r action.
	*
	* @return the group ID of this m d r action
	*/
	public long getGroupId() {
		return _mdrAction.getGroupId();
	}

	/**
	* Sets the group ID of this m d r action.
	*
	* @param groupId the group ID of this m d r action
	*/
	public void setGroupId(long groupId) {
		_mdrAction.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this m d r action.
	*
	* @return the company ID of this m d r action
	*/
	public long getCompanyId() {
		return _mdrAction.getCompanyId();
	}

	/**
	* Sets the company ID of this m d r action.
	*
	* @param companyId the company ID of this m d r action
	*/
	public void setCompanyId(long companyId) {
		_mdrAction.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this m d r action.
	*
	* @return the user ID of this m d r action
	*/
	public long getUserId() {
		return _mdrAction.getUserId();
	}

	/**
	* Sets the user ID of this m d r action.
	*
	* @param userId the user ID of this m d r action
	*/
	public void setUserId(long userId) {
		_mdrAction.setUserId(userId);
	}

	/**
	* Returns the user uuid of this m d r action.
	*
	* @return the user uuid of this m d r action
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrAction.getUserUuid();
	}

	/**
	* Sets the user uuid of this m d r action.
	*
	* @param userUuid the user uuid of this m d r action
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_mdrAction.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this m d r action.
	*
	* @return the user name of this m d r action
	*/
	public java.lang.String getUserName() {
		return _mdrAction.getUserName();
	}

	/**
	* Sets the user name of this m d r action.
	*
	* @param userName the user name of this m d r action
	*/
	public void setUserName(java.lang.String userName) {
		_mdrAction.setUserName(userName);
	}

	/**
	* Returns the create date of this m d r action.
	*
	* @return the create date of this m d r action
	*/
	public java.util.Date getCreateDate() {
		return _mdrAction.getCreateDate();
	}

	/**
	* Sets the create date of this m d r action.
	*
	* @param createDate the create date of this m d r action
	*/
	public void setCreateDate(java.util.Date createDate) {
		_mdrAction.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this m d r action.
	*
	* @return the modified date of this m d r action
	*/
	public java.util.Date getModifiedDate() {
		return _mdrAction.getModifiedDate();
	}

	/**
	* Sets the modified date of this m d r action.
	*
	* @param modifiedDate the modified date of this m d r action
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_mdrAction.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the rule group ID of this m d r action.
	*
	* @return the rule group ID of this m d r action
	*/
	public long getRuleGroupId() {
		return _mdrAction.getRuleGroupId();
	}

	/**
	* Sets the rule group ID of this m d r action.
	*
	* @param ruleGroupId the rule group ID of this m d r action
	*/
	public void setRuleGroupId(long ruleGroupId) {
		_mdrAction.setRuleGroupId(ruleGroupId);
	}

	/**
	* Returns the rule ID of this m d r action.
	*
	* @return the rule ID of this m d r action
	*/
	public long getRuleId() {
		return _mdrAction.getRuleId();
	}

	/**
	* Sets the rule ID of this m d r action.
	*
	* @param ruleId the rule ID of this m d r action
	*/
	public void setRuleId(long ruleId) {
		_mdrAction.setRuleId(ruleId);
	}

	/**
	* Returns the name of this m d r action.
	*
	* @return the name of this m d r action
	*/
	public java.lang.String getName() {
		return _mdrAction.getName();
	}

	/**
	* Returns the localized name of this m d r action in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this m d r action
	*/
	public java.lang.String getName(java.util.Locale locale) {
		return _mdrAction.getName(locale);
	}

	/**
	* Returns the localized name of this m d r action in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this m d r action. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _mdrAction.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this m d r action in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this m d r action
	*/
	public java.lang.String getName(java.lang.String languageId) {
		return _mdrAction.getName(languageId);
	}

	/**
	* Returns the localized name of this m d r action in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this m d r action
	*/
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _mdrAction.getName(languageId, useDefault);
	}

	/**
	* Returns a map of the locales and localized names of this m d r action.
	*
	* @return the locales and localized names of this m d r action
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _mdrAction.getNameMap();
	}

	/**
	* Sets the name of this m d r action.
	*
	* @param name the name of this m d r action
	*/
	public void setName(java.lang.String name) {
		_mdrAction.setName(name);
	}

	/**
	* Sets the localized name of this m d r action in the language.
	*
	* @param name the localized name of this m d r action
	* @param locale the locale of the language
	*/
	public void setName(java.lang.String name, java.util.Locale locale) {
		_mdrAction.setName(name, locale);
	}

	/**
	* Sets the localized name of this m d r action in the language, and sets the default locale.
	*
	* @param name the localized name of this m d r action
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_mdrAction.setName(name, locale, defaultLocale);
	}

	/**
	* Sets the localized names of this m d r action from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this m d r action
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_mdrAction.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this m d r action from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this m d r action
	* @param defaultLocale the default locale
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_mdrAction.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Returns the description of this m d r action.
	*
	* @return the description of this m d r action
	*/
	public java.lang.String getDescription() {
		return _mdrAction.getDescription();
	}

	/**
	* Returns the localized description of this m d r action in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this m d r action
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _mdrAction.getDescription(locale);
	}

	/**
	* Returns the localized description of this m d r action in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this m d r action. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _mdrAction.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this m d r action in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this m d r action
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _mdrAction.getDescription(languageId);
	}

	/**
	* Returns the localized description of this m d r action in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this m d r action
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _mdrAction.getDescription(languageId, useDefault);
	}

	/**
	* Returns a map of the locales and localized descriptions of this m d r action.
	*
	* @return the locales and localized descriptions of this m d r action
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _mdrAction.getDescriptionMap();
	}

	/**
	* Sets the description of this m d r action.
	*
	* @param description the description of this m d r action
	*/
	public void setDescription(java.lang.String description) {
		_mdrAction.setDescription(description);
	}

	/**
	* Sets the localized description of this m d r action in the language.
	*
	* @param description the localized description of this m d r action
	* @param locale the locale of the language
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_mdrAction.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this m d r action in the language, and sets the default locale.
	*
	* @param description the localized description of this m d r action
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_mdrAction.setDescription(description, locale, defaultLocale);
	}

	/**
	* Sets the localized descriptions of this m d r action from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this m d r action
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_mdrAction.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this m d r action from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this m d r action
	* @param defaultLocale the default locale
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_mdrAction.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the type of this m d r action.
	*
	* @return the type of this m d r action
	*/
	public java.lang.String getType() {
		return _mdrAction.getType();
	}

	/**
	* Sets the type of this m d r action.
	*
	* @param type the type of this m d r action
	*/
	public void setType(java.lang.String type) {
		_mdrAction.setType(type);
	}

	/**
	* Returns the type settings of this m d r action.
	*
	* @return the type settings of this m d r action
	*/
	public java.lang.String getTypeSettings() {
		return _mdrAction.getTypeSettings();
	}

	/**
	* Sets the type settings of this m d r action.
	*
	* @param typeSettings the type settings of this m d r action
	*/
	public void setTypeSettings(java.lang.String typeSettings) {
		_mdrAction.setTypeSettings(typeSettings);
	}

	public boolean isNew() {
		return _mdrAction.isNew();
	}

	public void setNew(boolean n) {
		_mdrAction.setNew(n);
	}

	public boolean isCachedModel() {
		return _mdrAction.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mdrAction.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mdrAction.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_mdrAction.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mdrAction.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mdrAction.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mdrAction.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mdrAction.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MDRActionWrapper((MDRAction)_mdrAction.clone());
	}

	public int compareTo(MDRAction mdrAction) {
		return _mdrAction.compareTo(mdrAction);
	}

	@Override
	public int hashCode() {
		return _mdrAction.hashCode();
	}

	public com.liferay.portal.model.CacheModel<MDRAction> toCacheModel() {
		return _mdrAction.toCacheModel();
	}

	public MDRAction toEscapedModel() {
		return new MDRActionWrapper(_mdrAction.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mdrAction.toString();
	}

	public java.lang.String toXmlString() {
		return _mdrAction.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mdrAction.persist();
	}

	public MDRAction getWrappedMDRAction() {
		return _mdrAction;
	}

	public void resetOriginalValues() {
		_mdrAction.resetOriginalValues();
	}

	private MDRAction _mdrAction;
}