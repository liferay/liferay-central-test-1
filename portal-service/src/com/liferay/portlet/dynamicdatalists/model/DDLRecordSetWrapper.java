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

package com.liferay.portlet.dynamicdatalists.model;

/**
 * <p>
 * This class is a wrapper for {@link DDLRecordSet}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDLRecordSet
 * @generated
 */
public class DDLRecordSetWrapper implements DDLRecordSet {
	public DDLRecordSetWrapper(DDLRecordSet ddlRecordSet) {
		_ddlRecordSet = ddlRecordSet;
	}

	public Class<?> getModelClass() {
		return DDLRecordSet.class;
	}

	public String getModelClassName() {
		return DDLRecordSet.class.getName();
	}

	/**
	* Gets the primary key of this d d l record set.
	*
	* @return the primary key of this d d l record set
	*/
	public long getPrimaryKey() {
		return _ddlRecordSet.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d l record set
	*
	* @param pk the primary key of this d d l record set
	*/
	public void setPrimaryKey(long primaryKey) {
		_ddlRecordSet.setPrimaryKey(primaryKey);
	}

	/**
	* Gets the uuid of this d d l record set.
	*
	* @return the uuid of this d d l record set
	*/
	public java.lang.String getUuid() {
		return _ddlRecordSet.getUuid();
	}

	/**
	* Sets the uuid of this d d l record set.
	*
	* @param uuid the uuid of this d d l record set
	*/
	public void setUuid(java.lang.String uuid) {
		_ddlRecordSet.setUuid(uuid);
	}

	/**
	* Gets the record set ID of this d d l record set.
	*
	* @return the record set ID of this d d l record set
	*/
	public long getRecordSetId() {
		return _ddlRecordSet.getRecordSetId();
	}

	/**
	* Sets the record set ID of this d d l record set.
	*
	* @param recordSetId the record set ID of this d d l record set
	*/
	public void setRecordSetId(long recordSetId) {
		_ddlRecordSet.setRecordSetId(recordSetId);
	}

	/**
	* Gets the group ID of this d d l record set.
	*
	* @return the group ID of this d d l record set
	*/
	public long getGroupId() {
		return _ddlRecordSet.getGroupId();
	}

	/**
	* Sets the group ID of this d d l record set.
	*
	* @param groupId the group ID of this d d l record set
	*/
	public void setGroupId(long groupId) {
		_ddlRecordSet.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this d d l record set.
	*
	* @return the company ID of this d d l record set
	*/
	public long getCompanyId() {
		return _ddlRecordSet.getCompanyId();
	}

	/**
	* Sets the company ID of this d d l record set.
	*
	* @param companyId the company ID of this d d l record set
	*/
	public void setCompanyId(long companyId) {
		_ddlRecordSet.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this d d l record set.
	*
	* @return the user ID of this d d l record set
	*/
	public long getUserId() {
		return _ddlRecordSet.getUserId();
	}

	/**
	* Sets the user ID of this d d l record set.
	*
	* @param userId the user ID of this d d l record set
	*/
	public void setUserId(long userId) {
		_ddlRecordSet.setUserId(userId);
	}

	/**
	* Gets the user uuid of this d d l record set.
	*
	* @return the user uuid of this d d l record set
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSet.getUserUuid();
	}

	/**
	* Sets the user uuid of this d d l record set.
	*
	* @param userUuid the user uuid of this d d l record set
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_ddlRecordSet.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this d d l record set.
	*
	* @return the user name of this d d l record set
	*/
	public java.lang.String getUserName() {
		return _ddlRecordSet.getUserName();
	}

	/**
	* Sets the user name of this d d l record set.
	*
	* @param userName the user name of this d d l record set
	*/
	public void setUserName(java.lang.String userName) {
		_ddlRecordSet.setUserName(userName);
	}

	/**
	* Gets the create date of this d d l record set.
	*
	* @return the create date of this d d l record set
	*/
	public java.util.Date getCreateDate() {
		return _ddlRecordSet.getCreateDate();
	}

	/**
	* Sets the create date of this d d l record set.
	*
	* @param createDate the create date of this d d l record set
	*/
	public void setCreateDate(java.util.Date createDate) {
		_ddlRecordSet.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this d d l record set.
	*
	* @return the modified date of this d d l record set
	*/
	public java.util.Date getModifiedDate() {
		return _ddlRecordSet.getModifiedDate();
	}

	/**
	* Sets the modified date of this d d l record set.
	*
	* @param modifiedDate the modified date of this d d l record set
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_ddlRecordSet.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the d d m structure ID of this d d l record set.
	*
	* @return the d d m structure ID of this d d l record set
	*/
	public long getDDMStructureId() {
		return _ddlRecordSet.getDDMStructureId();
	}

	/**
	* Sets the d d m structure ID of this d d l record set.
	*
	* @param DDMStructureId the d d m structure ID of this d d l record set
	*/
	public void setDDMStructureId(long DDMStructureId) {
		_ddlRecordSet.setDDMStructureId(DDMStructureId);
	}

	/**
	* Gets the record set key of this d d l record set.
	*
	* @return the record set key of this d d l record set
	*/
	public java.lang.String getRecordSetKey() {
		return _ddlRecordSet.getRecordSetKey();
	}

	/**
	* Sets the record set key of this d d l record set.
	*
	* @param recordSetKey the record set key of this d d l record set
	*/
	public void setRecordSetKey(java.lang.String recordSetKey) {
		_ddlRecordSet.setRecordSetKey(recordSetKey);
	}

	/**
	* Gets the name of this d d l record set.
	*
	* @return the name of this d d l record set
	*/
	public java.lang.String getName() {
		return _ddlRecordSet.getName();
	}

	/**
	* Gets the localized name of this d d l record set. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized name for
	* @return the localized name of this d d l record set
	*/
	public java.lang.String getName(java.util.Locale locale) {
		return _ddlRecordSet.getName(locale);
	}

	/**
	* Gets the localized name of this d d l record set, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized name for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d l record set. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _ddlRecordSet.getName(locale, useDefault);
	}

	/**
	* Gets the localized name of this d d l record set. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized name for
	* @return the localized name of this d d l record set
	*/
	public java.lang.String getName(java.lang.String languageId) {
		return _ddlRecordSet.getName(languageId);
	}

	/**
	* Gets the localized name of this d d l record set, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized name for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d l record set
	*/
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _ddlRecordSet.getName(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized name of this d d l record set.
	*
	* @return the locales and localized name
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddlRecordSet.getNameMap();
	}

	/**
	* Sets the name of this d d l record set.
	*
	* @param name the name of this d d l record set
	*/
	public void setName(java.lang.String name) {
		_ddlRecordSet.setName(name);
	}

	/**
	* Sets the localized name of this d d l record set.
	*
	* @param name the localized name of this d d l record set
	* @param locale the locale to set the localized name for
	*/
	public void setName(java.lang.String name, java.util.Locale locale) {
		_ddlRecordSet.setName(name, locale);
	}

	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddlRecordSet.setName(name, locale, defaultLocale);
	}

	/**
	* Sets the localized names of this d d l record set from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this d d l record set
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_ddlRecordSet.setNameMap(nameMap);
	}

	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddlRecordSet.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Gets the description of this d d l record set.
	*
	* @return the description of this d d l record set
	*/
	public java.lang.String getDescription() {
		return _ddlRecordSet.getDescription();
	}

	/**
	* Sets the description of this d d l record set.
	*
	* @param description the description of this d d l record set
	*/
	public void setDescription(java.lang.String description) {
		_ddlRecordSet.setDescription(description);
	}

	/**
	* Gets the min display rows of this d d l record set.
	*
	* @return the min display rows of this d d l record set
	*/
	public int getMinDisplayRows() {
		return _ddlRecordSet.getMinDisplayRows();
	}

	/**
	* Sets the min display rows of this d d l record set.
	*
	* @param minDisplayRows the min display rows of this d d l record set
	*/
	public void setMinDisplayRows(int minDisplayRows) {
		_ddlRecordSet.setMinDisplayRows(minDisplayRows);
	}

	public boolean isNew() {
		return _ddlRecordSet.isNew();
	}

	public void setNew(boolean n) {
		_ddlRecordSet.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddlRecordSet.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddlRecordSet.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddlRecordSet.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ddlRecordSet.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddlRecordSet.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddlRecordSet.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddlRecordSet.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddlRecordSet.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new DDLRecordSetWrapper((DDLRecordSet)_ddlRecordSet.clone());
	}

	public int compareTo(
		com.liferay.portlet.dynamicdatalists.model.DDLRecordSet ddlRecordSet) {
		return _ddlRecordSet.compareTo(ddlRecordSet);
	}

	public int hashCode() {
		return _ddlRecordSet.hashCode();
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet toEscapedModel() {
		return new DDLRecordSetWrapper(_ddlRecordSet.toEscapedModel());
	}

	public java.lang.String toString() {
		return _ddlRecordSet.toString();
	}

	public java.lang.String toXmlString() {
		return _ddlRecordSet.toXmlString();
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getDDMStructure()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSet.getDDMStructure();
	}

	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getRecords()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSet.getRecords();
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.storage.Fields> getRecordsFieldsList()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSet.getRecordsFieldsList();
	}

	public DDLRecordSet getWrappedDDLRecordSet() {
		return _ddlRecordSet;
	}

	public void resetOriginalValues() {
		_ddlRecordSet.resetOriginalValues();
	}

	private DDLRecordSet _ddlRecordSet;
}