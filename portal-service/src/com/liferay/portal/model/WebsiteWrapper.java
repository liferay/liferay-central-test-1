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

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link Website}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Website
 * @generated
 */
public class WebsiteWrapper implements Website {
	public WebsiteWrapper(Website website) {
		_website = website;
	}

	/**
	* Gets the primary key of this website.
	*
	* @return the primary key of this website
	*/
	public long getPrimaryKey() {
		return _website.getPrimaryKey();
	}

	/**
	* Sets the primary key of this website
	*
	* @param pk the primary key of this website
	*/
	public void setPrimaryKey(long pk) {
		_website.setPrimaryKey(pk);
	}

	/**
	* Gets the website id of this website.
	*
	* @return the website id of this website
	*/
	public long getWebsiteId() {
		return _website.getWebsiteId();
	}

	/**
	* Sets the website id of this website.
	*
	* @param websiteId the website id of this website
	*/
	public void setWebsiteId(long websiteId) {
		_website.setWebsiteId(websiteId);
	}

	/**
	* Gets the company id of this website.
	*
	* @return the company id of this website
	*/
	public long getCompanyId() {
		return _website.getCompanyId();
	}

	/**
	* Sets the company id of this website.
	*
	* @param companyId the company id of this website
	*/
	public void setCompanyId(long companyId) {
		_website.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this website.
	*
	* @return the user id of this website
	*/
	public long getUserId() {
		return _website.getUserId();
	}

	/**
	* Sets the user id of this website.
	*
	* @param userId the user id of this website
	*/
	public void setUserId(long userId) {
		_website.setUserId(userId);
	}

	/**
	* Gets the user uuid of this website.
	*
	* @return the user uuid of this website
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _website.getUserUuid();
	}

	/**
	* Sets the user uuid of this website.
	*
	* @param userUuid the user uuid of this website
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_website.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this website.
	*
	* @return the user name of this website
	*/
	public java.lang.String getUserName() {
		return _website.getUserName();
	}

	/**
	* Sets the user name of this website.
	*
	* @param userName the user name of this website
	*/
	public void setUserName(java.lang.String userName) {
		_website.setUserName(userName);
	}

	/**
	* Gets the create date of this website.
	*
	* @return the create date of this website
	*/
	public java.util.Date getCreateDate() {
		return _website.getCreateDate();
	}

	/**
	* Sets the create date of this website.
	*
	* @param createDate the create date of this website
	*/
	public void setCreateDate(java.util.Date createDate) {
		_website.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this website.
	*
	* @return the modified date of this website
	*/
	public java.util.Date getModifiedDate() {
		return _website.getModifiedDate();
	}

	/**
	* Sets the modified date of this website.
	*
	* @param modifiedDate the modified date of this website
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_website.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the class name of the model instance this website is polymorphically associated with.
	*
	* @return the class name of the model instance this website is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _website.getClassName();
	}

	/**
	* Gets the class name id of this website.
	*
	* @return the class name id of this website
	*/
	public long getClassNameId() {
		return _website.getClassNameId();
	}

	/**
	* Sets the class name id of this website.
	*
	* @param classNameId the class name id of this website
	*/
	public void setClassNameId(long classNameId) {
		_website.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this website.
	*
	* @return the class p k of this website
	*/
	public long getClassPK() {
		return _website.getClassPK();
	}

	/**
	* Sets the class p k of this website.
	*
	* @param classPK the class p k of this website
	*/
	public void setClassPK(long classPK) {
		_website.setClassPK(classPK);
	}

	/**
	* Gets the url of this website.
	*
	* @return the url of this website
	*/
	public java.lang.String getUrl() {
		return _website.getUrl();
	}

	/**
	* Sets the url of this website.
	*
	* @param url the url of this website
	*/
	public void setUrl(java.lang.String url) {
		_website.setUrl(url);
	}

	/**
	* Gets the type id of this website.
	*
	* @return the type id of this website
	*/
	public int getTypeId() {
		return _website.getTypeId();
	}

	/**
	* Sets the type id of this website.
	*
	* @param typeId the type id of this website
	*/
	public void setTypeId(int typeId) {
		_website.setTypeId(typeId);
	}

	/**
	* Gets the primary of this website.
	*
	* @return the primary of this website
	*/
	public boolean getPrimary() {
		return _website.getPrimary();
	}

	/**
	* Determines if this website is primary.
	*
	* @return <code>true</code> if this website is primary; <code>false</code> otherwise
	*/
	public boolean isPrimary() {
		return _website.isPrimary();
	}

	/**
	* Sets whether this website is primary.
	*
	* @param primary the primary of this website
	*/
	public void setPrimary(boolean primary) {
		_website.setPrimary(primary);
	}

	public boolean isNew() {
		return _website.isNew();
	}

	public void setNew(boolean n) {
		_website.setNew(n);
	}

	public boolean isCachedModel() {
		return _website.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_website.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _website.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_website.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _website.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _website.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_website.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _website.clone();
	}

	public int compareTo(com.liferay.portal.model.Website website) {
		return _website.compareTo(website);
	}

	public int hashCode() {
		return _website.hashCode();
	}

	public com.liferay.portal.model.Website toEscapedModel() {
		return _website.toEscapedModel();
	}

	public java.lang.String toString() {
		return _website.toString();
	}

	public java.lang.String toXmlString() {
		return _website.toXmlString();
	}

	public com.liferay.portal.model.ListType getType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _website.getType();
	}

	public Website getWrappedWebsite() {
		return _website;
	}

	private Website _website;
}