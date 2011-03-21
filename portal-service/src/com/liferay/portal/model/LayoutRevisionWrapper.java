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

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link LayoutRevision}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutRevision
 * @generated
 */
public class LayoutRevisionWrapper implements LayoutRevision {
	public LayoutRevisionWrapper(LayoutRevision layoutRevision) {
		_layoutRevision = layoutRevision;
	}

	public Class<?> getModelClass() {
		return LayoutRevision.class;
	}

	public String getModelClassName() {
		return LayoutRevision.class.getName();
	}

	/**
	* Gets the primary key of this layout revision.
	*
	* @return the primary key of this layout revision
	*/
	public long getPrimaryKey() {
		return _layoutRevision.getPrimaryKey();
	}

	/**
	* Sets the primary key of this layout revision
	*
	* @param pk the primary key of this layout revision
	*/
	public void setPrimaryKey(long pk) {
		_layoutRevision.setPrimaryKey(pk);
	}

	/**
	* Gets the layout revision ID of this layout revision.
	*
	* @return the layout revision ID of this layout revision
	*/
	public long getLayoutRevisionId() {
		return _layoutRevision.getLayoutRevisionId();
	}

	/**
	* Sets the layout revision ID of this layout revision.
	*
	* @param layoutRevisionId the layout revision ID of this layout revision
	*/
	public void setLayoutRevisionId(long layoutRevisionId) {
		_layoutRevision.setLayoutRevisionId(layoutRevisionId);
	}

	/**
	* Gets the group ID of this layout revision.
	*
	* @return the group ID of this layout revision
	*/
	public long getGroupId() {
		return _layoutRevision.getGroupId();
	}

	/**
	* Sets the group ID of this layout revision.
	*
	* @param groupId the group ID of this layout revision
	*/
	public void setGroupId(long groupId) {
		_layoutRevision.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this layout revision.
	*
	* @return the company ID of this layout revision
	*/
	public long getCompanyId() {
		return _layoutRevision.getCompanyId();
	}

	/**
	* Sets the company ID of this layout revision.
	*
	* @param companyId the company ID of this layout revision
	*/
	public void setCompanyId(long companyId) {
		_layoutRevision.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this layout revision.
	*
	* @return the user ID of this layout revision
	*/
	public long getUserId() {
		return _layoutRevision.getUserId();
	}

	/**
	* Sets the user ID of this layout revision.
	*
	* @param userId the user ID of this layout revision
	*/
	public void setUserId(long userId) {
		_layoutRevision.setUserId(userId);
	}

	/**
	* Gets the user uuid of this layout revision.
	*
	* @return the user uuid of this layout revision
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getUserUuid();
	}

	/**
	* Sets the user uuid of this layout revision.
	*
	* @param userUuid the user uuid of this layout revision
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_layoutRevision.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this layout revision.
	*
	* @return the user name of this layout revision
	*/
	public java.lang.String getUserName() {
		return _layoutRevision.getUserName();
	}

	/**
	* Sets the user name of this layout revision.
	*
	* @param userName the user name of this layout revision
	*/
	public void setUserName(java.lang.String userName) {
		_layoutRevision.setUserName(userName);
	}

	/**
	* Gets the create date of this layout revision.
	*
	* @return the create date of this layout revision
	*/
	public java.util.Date getCreateDate() {
		return _layoutRevision.getCreateDate();
	}

	/**
	* Sets the create date of this layout revision.
	*
	* @param createDate the create date of this layout revision
	*/
	public void setCreateDate(java.util.Date createDate) {
		_layoutRevision.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this layout revision.
	*
	* @return the modified date of this layout revision
	*/
	public java.util.Date getModifiedDate() {
		return _layoutRevision.getModifiedDate();
	}

	/**
	* Sets the modified date of this layout revision.
	*
	* @param modifiedDate the modified date of this layout revision
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_layoutRevision.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the layout set branch ID of this layout revision.
	*
	* @return the layout set branch ID of this layout revision
	*/
	public long getLayoutSetBranchId() {
		return _layoutRevision.getLayoutSetBranchId();
	}

	/**
	* Sets the layout set branch ID of this layout revision.
	*
	* @param layoutSetBranchId the layout set branch ID of this layout revision
	*/
	public void setLayoutSetBranchId(long layoutSetBranchId) {
		_layoutRevision.setLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Gets the parent layout revision ID of this layout revision.
	*
	* @return the parent layout revision ID of this layout revision
	*/
	public long getParentLayoutRevisionId() {
		return _layoutRevision.getParentLayoutRevisionId();
	}

	/**
	* Sets the parent layout revision ID of this layout revision.
	*
	* @param parentLayoutRevisionId the parent layout revision ID of this layout revision
	*/
	public void setParentLayoutRevisionId(long parentLayoutRevisionId) {
		_layoutRevision.setParentLayoutRevisionId(parentLayoutRevisionId);
	}

	/**
	* Gets the head of this layout revision.
	*
	* @return the head of this layout revision
	*/
	public boolean getHead() {
		return _layoutRevision.getHead();
	}

	/**
	* Determines if this layout revision is head.
	*
	* @return <code>true</code> if this layout revision is head; <code>false</code> otherwise
	*/
	public boolean isHead() {
		return _layoutRevision.isHead();
	}

	/**
	* Sets whether this layout revision is head.
	*
	* @param head the head of this layout revision
	*/
	public void setHead(boolean head) {
		_layoutRevision.setHead(head);
	}

	/**
	* Gets the major of this layout revision.
	*
	* @return the major of this layout revision
	*/
	public boolean getMajor() {
		return _layoutRevision.getMajor();
	}

	/**
	* Determines if this layout revision is major.
	*
	* @return <code>true</code> if this layout revision is major; <code>false</code> otherwise
	*/
	public boolean isMajor() {
		return _layoutRevision.isMajor();
	}

	/**
	* Sets whether this layout revision is major.
	*
	* @param major the major of this layout revision
	*/
	public void setMajor(boolean major) {
		_layoutRevision.setMajor(major);
	}

	/**
	* Gets the plid of this layout revision.
	*
	* @return the plid of this layout revision
	*/
	public long getPlid() {
		return _layoutRevision.getPlid();
	}

	/**
	* Sets the plid of this layout revision.
	*
	* @param plid the plid of this layout revision
	*/
	public void setPlid(long plid) {
		_layoutRevision.setPlid(plid);
	}

	/**
	* Gets the private layout of this layout revision.
	*
	* @return the private layout of this layout revision
	*/
	public boolean getPrivateLayout() {
		return _layoutRevision.getPrivateLayout();
	}

	/**
	* Determines if this layout revision is private layout.
	*
	* @return <code>true</code> if this layout revision is private layout; <code>false</code> otherwise
	*/
	public boolean isPrivateLayout() {
		return _layoutRevision.isPrivateLayout();
	}

	/**
	* Sets whether this layout revision is private layout.
	*
	* @param privateLayout the private layout of this layout revision
	*/
	public void setPrivateLayout(boolean privateLayout) {
		_layoutRevision.setPrivateLayout(privateLayout);
	}

	/**
	* Gets the name of this layout revision.
	*
	* @return the name of this layout revision
	*/
	public java.lang.String getName() {
		return _layoutRevision.getName();
	}

	/**
	* Gets the localized name of this layout revision. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized name for
	* @return the localized name of this layout revision
	*/
	public java.lang.String getName(java.util.Locale locale) {
		return _layoutRevision.getName(locale);
	}

	/**
	* Gets the localized name of this layout revision, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized name for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this layout revision. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _layoutRevision.getName(locale, useDefault);
	}

	/**
	* Gets the localized name of this layout revision. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized name for
	* @return the localized name of this layout revision
	*/
	public java.lang.String getName(java.lang.String languageId) {
		return _layoutRevision.getName(languageId);
	}

	/**
	* Gets the localized name of this layout revision, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized name for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this layout revision
	*/
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _layoutRevision.getName(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized name of this layout revision.
	*
	* @return the locales and localized name
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _layoutRevision.getNameMap();
	}

	/**
	* Sets the name of this layout revision.
	*
	* @param name the name of this layout revision
	*/
	public void setName(java.lang.String name) {
		_layoutRevision.setName(name);
	}

	/**
	* Sets the localized name of this layout revision.
	*
	* @param name the localized name of this layout revision
	* @param locale the locale to set the localized name for
	*/
	public void setName(java.lang.String name, java.util.Locale locale) {
		_layoutRevision.setName(name, locale);
	}

	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layoutRevision.setName(name, locale, defaultLocale);
	}

	/**
	* Sets the localized names of this layout revision from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this layout revision
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_layoutRevision.setNameMap(nameMap);
	}

	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_layoutRevision.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Gets the title of this layout revision.
	*
	* @return the title of this layout revision
	*/
	public java.lang.String getTitle() {
		return _layoutRevision.getTitle();
	}

	/**
	* Gets the localized title of this layout revision. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized title for
	* @return the localized title of this layout revision
	*/
	public java.lang.String getTitle(java.util.Locale locale) {
		return _layoutRevision.getTitle(locale);
	}

	/**
	* Gets the localized title of this layout revision, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized title for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this layout revision. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _layoutRevision.getTitle(locale, useDefault);
	}

	/**
	* Gets the localized title of this layout revision. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized title for
	* @return the localized title of this layout revision
	*/
	public java.lang.String getTitle(java.lang.String languageId) {
		return _layoutRevision.getTitle(languageId);
	}

	/**
	* Gets the localized title of this layout revision, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized title for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this layout revision
	*/
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _layoutRevision.getTitle(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized title of this layout revision.
	*
	* @return the locales and localized title
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _layoutRevision.getTitleMap();
	}

	/**
	* Sets the title of this layout revision.
	*
	* @param title the title of this layout revision
	*/
	public void setTitle(java.lang.String title) {
		_layoutRevision.setTitle(title);
	}

	/**
	* Sets the localized title of this layout revision.
	*
	* @param title the localized title of this layout revision
	* @param locale the locale to set the localized title for
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_layoutRevision.setTitle(title, locale);
	}

	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layoutRevision.setTitle(title, locale, defaultLocale);
	}

	/**
	* Sets the localized titles of this layout revision from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this layout revision
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_layoutRevision.setTitleMap(titleMap);
	}

	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_layoutRevision.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Gets the description of this layout revision.
	*
	* @return the description of this layout revision
	*/
	public java.lang.String getDescription() {
		return _layoutRevision.getDescription();
	}

	/**
	* Gets the localized description of this layout revision. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized description for
	* @return the localized description of this layout revision
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _layoutRevision.getDescription(locale);
	}

	/**
	* Gets the localized description of this layout revision, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized description for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this layout revision. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _layoutRevision.getDescription(locale, useDefault);
	}

	/**
	* Gets the localized description of this layout revision. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized description for
	* @return the localized description of this layout revision
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _layoutRevision.getDescription(languageId);
	}

	/**
	* Gets the localized description of this layout revision, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized description for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this layout revision
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _layoutRevision.getDescription(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized description of this layout revision.
	*
	* @return the locales and localized description
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _layoutRevision.getDescriptionMap();
	}

	/**
	* Sets the description of this layout revision.
	*
	* @param description the description of this layout revision
	*/
	public void setDescription(java.lang.String description) {
		_layoutRevision.setDescription(description);
	}

	/**
	* Sets the localized description of this layout revision.
	*
	* @param description the localized description of this layout revision
	* @param locale the locale to set the localized description for
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_layoutRevision.setDescription(description, locale);
	}

	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_layoutRevision.setDescription(description, locale, defaultLocale);
	}

	/**
	* Sets the localized descriptions of this layout revision from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this layout revision
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_layoutRevision.setDescriptionMap(descriptionMap);
	}

	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_layoutRevision.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Gets the keywords of this layout revision.
	*
	* @return the keywords of this layout revision
	*/
	public java.lang.String getKeywords() {
		return _layoutRevision.getKeywords();
	}

	/**
	* Gets the localized keywords of this layout revision. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized keywords for
	* @return the localized keywords of this layout revision
	*/
	public java.lang.String getKeywords(java.util.Locale locale) {
		return _layoutRevision.getKeywords(locale);
	}

	/**
	* Gets the localized keywords of this layout revision, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized keywords for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized keywords of this layout revision. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getKeywords(java.util.Locale locale,
		boolean useDefault) {
		return _layoutRevision.getKeywords(locale, useDefault);
	}

	/**
	* Gets the localized keywords of this layout revision. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized keywords for
	* @return the localized keywords of this layout revision
	*/
	public java.lang.String getKeywords(java.lang.String languageId) {
		return _layoutRevision.getKeywords(languageId);
	}

	/**
	* Gets the localized keywords of this layout revision, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized keywords for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized keywords of this layout revision
	*/
	public java.lang.String getKeywords(java.lang.String languageId,
		boolean useDefault) {
		return _layoutRevision.getKeywords(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized keywords of this layout revision.
	*
	* @return the locales and localized keywords
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getKeywordsMap() {
		return _layoutRevision.getKeywordsMap();
	}

	/**
	* Sets the keywords of this layout revision.
	*
	* @param keywords the keywords of this layout revision
	*/
	public void setKeywords(java.lang.String keywords) {
		_layoutRevision.setKeywords(keywords);
	}

	/**
	* Sets the localized keywords of this layout revision.
	*
	* @param keywords the localized keywords of this layout revision
	* @param locale the locale to set the localized keywords for
	*/
	public void setKeywords(java.lang.String keywords, java.util.Locale locale) {
		_layoutRevision.setKeywords(keywords, locale);
	}

	public void setKeywords(java.lang.String keywords, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layoutRevision.setKeywords(keywords, locale, defaultLocale);
	}

	/**
	* Sets the localized keywordses of this layout revision from the map of locales and localized keywordses.
	*
	* @param keywordsMap the locales and localized keywordses of this layout revision
	*/
	public void setKeywordsMap(
		java.util.Map<java.util.Locale, java.lang.String> keywordsMap) {
		_layoutRevision.setKeywordsMap(keywordsMap);
	}

	public void setKeywordsMap(
		java.util.Map<java.util.Locale, java.lang.String> keywordsMap,
		java.util.Locale defaultLocale) {
		_layoutRevision.setKeywordsMap(keywordsMap, defaultLocale);
	}

	/**
	* Gets the robots of this layout revision.
	*
	* @return the robots of this layout revision
	*/
	public java.lang.String getRobots() {
		return _layoutRevision.getRobots();
	}

	/**
	* Gets the localized robots of this layout revision. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized robots for
	* @return the localized robots of this layout revision
	*/
	public java.lang.String getRobots(java.util.Locale locale) {
		return _layoutRevision.getRobots(locale);
	}

	/**
	* Gets the localized robots of this layout revision, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized robots for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized robots of this layout revision. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getRobots(java.util.Locale locale,
		boolean useDefault) {
		return _layoutRevision.getRobots(locale, useDefault);
	}

	/**
	* Gets the localized robots of this layout revision. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized robots for
	* @return the localized robots of this layout revision
	*/
	public java.lang.String getRobots(java.lang.String languageId) {
		return _layoutRevision.getRobots(languageId);
	}

	/**
	* Gets the localized robots of this layout revision, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized robots for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized robots of this layout revision
	*/
	public java.lang.String getRobots(java.lang.String languageId,
		boolean useDefault) {
		return _layoutRevision.getRobots(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized robots of this layout revision.
	*
	* @return the locales and localized robots
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getRobotsMap() {
		return _layoutRevision.getRobotsMap();
	}

	/**
	* Sets the robots of this layout revision.
	*
	* @param robots the robots of this layout revision
	*/
	public void setRobots(java.lang.String robots) {
		_layoutRevision.setRobots(robots);
	}

	/**
	* Sets the localized robots of this layout revision.
	*
	* @param robots the localized robots of this layout revision
	* @param locale the locale to set the localized robots for
	*/
	public void setRobots(java.lang.String robots, java.util.Locale locale) {
		_layoutRevision.setRobots(robots, locale);
	}

	public void setRobots(java.lang.String robots, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layoutRevision.setRobots(robots, locale, defaultLocale);
	}

	/**
	* Sets the localized robotses of this layout revision from the map of locales and localized robotses.
	*
	* @param robotsMap the locales and localized robotses of this layout revision
	*/
	public void setRobotsMap(
		java.util.Map<java.util.Locale, java.lang.String> robotsMap) {
		_layoutRevision.setRobotsMap(robotsMap);
	}

	public void setRobotsMap(
		java.util.Map<java.util.Locale, java.lang.String> robotsMap,
		java.util.Locale defaultLocale) {
		_layoutRevision.setRobotsMap(robotsMap, defaultLocale);
	}

	/**
	* Gets the type settings of this layout revision.
	*
	* @return the type settings of this layout revision
	*/
	public java.lang.String getTypeSettings() {
		return _layoutRevision.getTypeSettings();
	}

	/**
	* Sets the type settings of this layout revision.
	*
	* @param typeSettings the type settings of this layout revision
	*/
	public void setTypeSettings(java.lang.String typeSettings) {
		_layoutRevision.setTypeSettings(typeSettings);
	}

	/**
	* Gets the icon image of this layout revision.
	*
	* @return the icon image of this layout revision
	*/
	public boolean getIconImage() {
		return _layoutRevision.getIconImage();
	}

	/**
	* Determines if this layout revision is icon image.
	*
	* @return <code>true</code> if this layout revision is icon image; <code>false</code> otherwise
	*/
	public boolean isIconImage() {
		return _layoutRevision.isIconImage();
	}

	/**
	* Sets whether this layout revision is icon image.
	*
	* @param iconImage the icon image of this layout revision
	*/
	public void setIconImage(boolean iconImage) {
		_layoutRevision.setIconImage(iconImage);
	}

	/**
	* Gets the icon image ID of this layout revision.
	*
	* @return the icon image ID of this layout revision
	*/
	public long getIconImageId() {
		return _layoutRevision.getIconImageId();
	}

	/**
	* Sets the icon image ID of this layout revision.
	*
	* @param iconImageId the icon image ID of this layout revision
	*/
	public void setIconImageId(long iconImageId) {
		_layoutRevision.setIconImageId(iconImageId);
	}

	/**
	* Gets the theme ID of this layout revision.
	*
	* @return the theme ID of this layout revision
	*/
	public java.lang.String getThemeId() {
		return _layoutRevision.getThemeId();
	}

	/**
	* Sets the theme ID of this layout revision.
	*
	* @param themeId the theme ID of this layout revision
	*/
	public void setThemeId(java.lang.String themeId) {
		_layoutRevision.setThemeId(themeId);
	}

	/**
	* Gets the color scheme ID of this layout revision.
	*
	* @return the color scheme ID of this layout revision
	*/
	public java.lang.String getColorSchemeId() {
		return _layoutRevision.getColorSchemeId();
	}

	/**
	* Sets the color scheme ID of this layout revision.
	*
	* @param colorSchemeId the color scheme ID of this layout revision
	*/
	public void setColorSchemeId(java.lang.String colorSchemeId) {
		_layoutRevision.setColorSchemeId(colorSchemeId);
	}

	/**
	* Gets the wap theme ID of this layout revision.
	*
	* @return the wap theme ID of this layout revision
	*/
	public java.lang.String getWapThemeId() {
		return _layoutRevision.getWapThemeId();
	}

	/**
	* Sets the wap theme ID of this layout revision.
	*
	* @param wapThemeId the wap theme ID of this layout revision
	*/
	public void setWapThemeId(java.lang.String wapThemeId) {
		_layoutRevision.setWapThemeId(wapThemeId);
	}

	/**
	* Gets the wap color scheme ID of this layout revision.
	*
	* @return the wap color scheme ID of this layout revision
	*/
	public java.lang.String getWapColorSchemeId() {
		return _layoutRevision.getWapColorSchemeId();
	}

	/**
	* Sets the wap color scheme ID of this layout revision.
	*
	* @param wapColorSchemeId the wap color scheme ID of this layout revision
	*/
	public void setWapColorSchemeId(java.lang.String wapColorSchemeId) {
		_layoutRevision.setWapColorSchemeId(wapColorSchemeId);
	}

	/**
	* Gets the css of this layout revision.
	*
	* @return the css of this layout revision
	*/
	public java.lang.String getCss() {
		return _layoutRevision.getCss();
	}

	/**
	* Sets the css of this layout revision.
	*
	* @param css the css of this layout revision
	*/
	public void setCss(java.lang.String css) {
		_layoutRevision.setCss(css);
	}

	/**
	* Gets the status of this layout revision.
	*
	* @return the status of this layout revision
	*/
	public int getStatus() {
		return _layoutRevision.getStatus();
	}

	/**
	* Sets the status of this layout revision.
	*
	* @param status the status of this layout revision
	*/
	public void setStatus(int status) {
		_layoutRevision.setStatus(status);
	}

	/**
	* Gets the status by user ID of this layout revision.
	*
	* @return the status by user ID of this layout revision
	*/
	public long getStatusByUserId() {
		return _layoutRevision.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this layout revision.
	*
	* @param statusByUserId the status by user ID of this layout revision
	*/
	public void setStatusByUserId(long statusByUserId) {
		_layoutRevision.setStatusByUserId(statusByUserId);
	}

	/**
	* Gets the status by user uuid of this layout revision.
	*
	* @return the status by user uuid of this layout revision
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this layout revision.
	*
	* @param statusByUserUuid the status by user uuid of this layout revision
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_layoutRevision.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Gets the status by user name of this layout revision.
	*
	* @return the status by user name of this layout revision
	*/
	public java.lang.String getStatusByUserName() {
		return _layoutRevision.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this layout revision.
	*
	* @param statusByUserName the status by user name of this layout revision
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_layoutRevision.setStatusByUserName(statusByUserName);
	}

	/**
	* Gets the status date of this layout revision.
	*
	* @return the status date of this layout revision
	*/
	public java.util.Date getStatusDate() {
		return _layoutRevision.getStatusDate();
	}

	/**
	* Sets the status date of this layout revision.
	*
	* @param statusDate the status date of this layout revision
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_layoutRevision.setStatusDate(statusDate);
	}

	/**
	* @deprecated {@link #isApproved}
	*/
	public boolean getApproved() {
		return _layoutRevision.getApproved();
	}

	/**
	* Determines if this layout revision is approved.
	*
	* @return <code>true</code> if this layout revision is approved; <code>false</code> otherwise
	*/
	public boolean isApproved() {
		return _layoutRevision.isApproved();
	}

	/**
	* Determines if this layout revision is a draft.
	*
	* @return <code>true</code> if this layout revision is a draft; <code>false</code> otherwise
	*/
	public boolean isDraft() {
		return _layoutRevision.isDraft();
	}

	/**
	* Determines if this layout revision is expired.
	*
	* @return <code>true</code> if this layout revision is expired; <code>false</code> otherwise
	*/
	public boolean isExpired() {
		return _layoutRevision.isExpired();
	}

	/**
	* Determines if this layout revision is pending.
	*
	* @return <code>true</code> if this layout revision is pending; <code>false</code> otherwise
	*/
	public boolean isPending() {
		return _layoutRevision.isPending();
	}

	public boolean isNew() {
		return _layoutRevision.isNew();
	}

	public void setNew(boolean n) {
		_layoutRevision.setNew(n);
	}

	public boolean isCachedModel() {
		return _layoutRevision.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_layoutRevision.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _layoutRevision.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_layoutRevision.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutRevision.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutRevision.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layoutRevision.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new LayoutRevisionWrapper((LayoutRevision)_layoutRevision.clone());
	}

	public int compareTo(com.liferay.portal.model.LayoutRevision layoutRevision) {
		return _layoutRevision.compareTo(layoutRevision);
	}

	public int hashCode() {
		return _layoutRevision.hashCode();
	}

	public com.liferay.portal.model.LayoutRevision toEscapedModel() {
		return new LayoutRevisionWrapper(_layoutRevision.toEscapedModel());
	}

	public java.lang.String toString() {
		return _layoutRevision.toString();
	}

	public java.lang.String toXmlString() {
		return _layoutRevision.toXmlString();
	}

	public java.util.List<com.liferay.portal.model.LayoutRevision> getChildren()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getChildren();
	}

	public com.liferay.portal.model.ColorScheme getColorScheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getColorScheme();
	}

	public java.lang.String getCssText()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getCssText();
	}

	public java.lang.String getHTMLTitle(java.util.Locale locale) {
		return _layoutRevision.getHTMLTitle(locale);
	}

	public java.lang.String getHTMLTitle(java.lang.String localeLanguageId) {
		return _layoutRevision.getHTMLTitle(localeLanguageId);
	}

	public com.liferay.portal.model.LayoutSet getLayoutSet()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getLayoutSet();
	}

	public com.liferay.portal.model.Theme getTheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getTheme();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _layoutRevision.getTypeSettingsProperties();
	}

	public com.liferay.portal.model.ColorScheme getWapColorScheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getWapColorScheme();
	}

	public com.liferay.portal.model.Theme getWapTheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getWapTheme();
	}

	public boolean hasChildren()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.hasChildren();
	}

	public boolean isInheritLookAndFeel() {
		return _layoutRevision.isInheritLookAndFeel();
	}

	public boolean isInheritWapLookAndFeel() {
		return _layoutRevision.isInheritWapLookAndFeel();
	}

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_layoutRevision.setTypeSettingsProperties(typeSettingsProperties);
	}

	public LayoutRevision getWrappedLayoutRevision() {
		return _layoutRevision;
	}

	private LayoutRevision _layoutRevision;
}