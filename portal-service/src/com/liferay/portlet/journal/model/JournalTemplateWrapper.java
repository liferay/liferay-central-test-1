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

package com.liferay.portlet.journal.model;

/**
 * <p>
 * This class is a wrapper for {@link JournalTemplate}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalTemplate
 * @generated
 */
public class JournalTemplateWrapper implements JournalTemplate {
	public JournalTemplateWrapper(JournalTemplate journalTemplate) {
		_journalTemplate = journalTemplate;
	}

	/**
	* Gets the primary key of this journal template.
	*
	* @return the primary key of this journal template
	*/
	public long getPrimaryKey() {
		return _journalTemplate.getPrimaryKey();
	}

	/**
	* Sets the primary key of this journal template
	*
	* @param pk the primary key of this journal template
	*/
	public void setPrimaryKey(long pk) {
		_journalTemplate.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this journal template.
	*
	* @return the uuid of this journal template
	*/
	public java.lang.String getUuid() {
		return _journalTemplate.getUuid();
	}

	/**
	* Sets the uuid of this journal template.
	*
	* @param uuid the uuid of this journal template
	*/
	public void setUuid(java.lang.String uuid) {
		_journalTemplate.setUuid(uuid);
	}

	/**
	* Gets the id of this journal template.
	*
	* @return the id of this journal template
	*/
	public long getId() {
		return _journalTemplate.getId();
	}

	/**
	* Sets the id of this journal template.
	*
	* @param id the id of this journal template
	*/
	public void setId(long id) {
		_journalTemplate.setId(id);
	}

	/**
	* Gets the group id of this journal template.
	*
	* @return the group id of this journal template
	*/
	public long getGroupId() {
		return _journalTemplate.getGroupId();
	}

	/**
	* Sets the group id of this journal template.
	*
	* @param groupId the group id of this journal template
	*/
	public void setGroupId(long groupId) {
		_journalTemplate.setGroupId(groupId);
	}

	/**
	* Gets the company id of this journal template.
	*
	* @return the company id of this journal template
	*/
	public long getCompanyId() {
		return _journalTemplate.getCompanyId();
	}

	/**
	* Sets the company id of this journal template.
	*
	* @param companyId the company id of this journal template
	*/
	public void setCompanyId(long companyId) {
		_journalTemplate.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this journal template.
	*
	* @return the user id of this journal template
	*/
	public long getUserId() {
		return _journalTemplate.getUserId();
	}

	/**
	* Sets the user id of this journal template.
	*
	* @param userId the user id of this journal template
	*/
	public void setUserId(long userId) {
		_journalTemplate.setUserId(userId);
	}

	/**
	* Gets the user uuid of this journal template.
	*
	* @return the user uuid of this journal template
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplate.getUserUuid();
	}

	/**
	* Sets the user uuid of this journal template.
	*
	* @param userUuid the user uuid of this journal template
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_journalTemplate.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this journal template.
	*
	* @return the user name of this journal template
	*/
	public java.lang.String getUserName() {
		return _journalTemplate.getUserName();
	}

	/**
	* Sets the user name of this journal template.
	*
	* @param userName the user name of this journal template
	*/
	public void setUserName(java.lang.String userName) {
		_journalTemplate.setUserName(userName);
	}

	/**
	* Gets the create date of this journal template.
	*
	* @return the create date of this journal template
	*/
	public java.util.Date getCreateDate() {
		return _journalTemplate.getCreateDate();
	}

	/**
	* Sets the create date of this journal template.
	*
	* @param createDate the create date of this journal template
	*/
	public void setCreateDate(java.util.Date createDate) {
		_journalTemplate.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this journal template.
	*
	* @return the modified date of this journal template
	*/
	public java.util.Date getModifiedDate() {
		return _journalTemplate.getModifiedDate();
	}

	/**
	* Sets the modified date of this journal template.
	*
	* @param modifiedDate the modified date of this journal template
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_journalTemplate.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the template id of this journal template.
	*
	* @return the template id of this journal template
	*/
	public java.lang.String getTemplateId() {
		return _journalTemplate.getTemplateId();
	}

	/**
	* Sets the template id of this journal template.
	*
	* @param templateId the template id of this journal template
	*/
	public void setTemplateId(java.lang.String templateId) {
		_journalTemplate.setTemplateId(templateId);
	}

	/**
	* Gets the structure id of this journal template.
	*
	* @return the structure id of this journal template
	*/
	public java.lang.String getStructureId() {
		return _journalTemplate.getStructureId();
	}

	/**
	* Sets the structure id of this journal template.
	*
	* @param structureId the structure id of this journal template
	*/
	public void setStructureId(java.lang.String structureId) {
		_journalTemplate.setStructureId(structureId);
	}

	/**
	* Gets the name of this journal template.
	*
	* @return the name of this journal template
	*/
	public java.lang.String getName() {
		return _journalTemplate.getName();
	}

	/**
	* Sets the name of this journal template.
	*
	* @param name the name of this journal template
	*/
	public void setName(java.lang.String name) {
		_journalTemplate.setName(name);
	}

	/**
	* Gets the description of this journal template.
	*
	* @return the description of this journal template
	*/
	public java.lang.String getDescription() {
		return _journalTemplate.getDescription();
	}

	/**
	* Sets the description of this journal template.
	*
	* @param description the description of this journal template
	*/
	public void setDescription(java.lang.String description) {
		_journalTemplate.setDescription(description);
	}

	/**
	* Gets the xsl of this journal template.
	*
	* @return the xsl of this journal template
	*/
	public java.lang.String getXsl() {
		return _journalTemplate.getXsl();
	}

	/**
	* Sets the xsl of this journal template.
	*
	* @param xsl the xsl of this journal template
	*/
	public void setXsl(java.lang.String xsl) {
		_journalTemplate.setXsl(xsl);
	}

	/**
	* Gets the lang type of this journal template.
	*
	* @return the lang type of this journal template
	*/
	public java.lang.String getLangType() {
		return _journalTemplate.getLangType();
	}

	/**
	* Sets the lang type of this journal template.
	*
	* @param langType the lang type of this journal template
	*/
	public void setLangType(java.lang.String langType) {
		_journalTemplate.setLangType(langType);
	}

	/**
	* Gets the cacheable of this journal template.
	*
	* @return the cacheable of this journal template
	*/
	public boolean getCacheable() {
		return _journalTemplate.getCacheable();
	}

	/**
	* Determines whether this journal template is cacheable.
	*
	* @return whether this journal template is cacheable
	*/
	public boolean isCacheable() {
		return _journalTemplate.isCacheable();
	}

	/**
	* Sets whether this {$entity.humanName} is cacheable.
	*
	* @param cacheable the cacheable of this journal template
	*/
	public void setCacheable(boolean cacheable) {
		_journalTemplate.setCacheable(cacheable);
	}

	/**
	* Gets the small image of this journal template.
	*
	* @return the small image of this journal template
	*/
	public boolean getSmallImage() {
		return _journalTemplate.getSmallImage();
	}

	/**
	* Determines whether this journal template is small image.
	*
	* @return whether this journal template is small image
	*/
	public boolean isSmallImage() {
		return _journalTemplate.isSmallImage();
	}

	/**
	* Sets whether this {$entity.humanName} is small image.
	*
	* @param smallImage the small image of this journal template
	*/
	public void setSmallImage(boolean smallImage) {
		_journalTemplate.setSmallImage(smallImage);
	}

	/**
	* Gets the small image id of this journal template.
	*
	* @return the small image id of this journal template
	*/
	public long getSmallImageId() {
		return _journalTemplate.getSmallImageId();
	}

	/**
	* Sets the small image id of this journal template.
	*
	* @param smallImageId the small image id of this journal template
	*/
	public void setSmallImageId(long smallImageId) {
		_journalTemplate.setSmallImageId(smallImageId);
	}

	/**
	* Gets the small image u r l of this journal template.
	*
	* @return the small image u r l of this journal template
	*/
	public java.lang.String getSmallImageURL() {
		return _journalTemplate.getSmallImageURL();
	}

	/**
	* Sets the small image u r l of this journal template.
	*
	* @param smallImageURL the small image u r l of this journal template
	*/
	public void setSmallImageURL(java.lang.String smallImageURL) {
		_journalTemplate.setSmallImageURL(smallImageURL);
	}

	public boolean isNew() {
		return _journalTemplate.isNew();
	}

	public void setNew(boolean n) {
		_journalTemplate.setNew(n);
	}

	public boolean isCachedModel() {
		return _journalTemplate.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_journalTemplate.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _journalTemplate.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_journalTemplate.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _journalTemplate.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalTemplate.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalTemplate.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _journalTemplate.clone();
	}

	public int compareTo(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate) {
		return _journalTemplate.compareTo(journalTemplate);
	}

	public int hashCode() {
		return _journalTemplate.hashCode();
	}

	public com.liferay.portlet.journal.model.JournalTemplate toEscapedModel() {
		return _journalTemplate.toEscapedModel();
	}

	public java.lang.String toString() {
		return _journalTemplate.toString();
	}

	public java.lang.String toXmlString() {
		return _journalTemplate.toXmlString();
	}

	public java.lang.String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplate.getSmallImageType();
	}

	public void setSmallImageType(java.lang.String smallImageType) {
		_journalTemplate.setSmallImageType(smallImageType);
	}

	public JournalTemplate getWrappedJournalTemplate() {
		return _journalTemplate;
	}

	private JournalTemplate _journalTemplate;
}