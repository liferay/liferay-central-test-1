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
 * This class is a wrapper for {@link EmailAddress}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       EmailAddress
 * @generated
 */
public class EmailAddressWrapper implements EmailAddress {
	public EmailAddressWrapper(EmailAddress emailAddress) {
		_emailAddress = emailAddress;
	}

	/**
	* Gets the primary key of this email address.
	*
	* @return the primary key of this email address
	*/
	public long getPrimaryKey() {
		return _emailAddress.getPrimaryKey();
	}

	/**
	* Sets the primary key of this email address
	*
	* @param pk the primary key of this email address
	*/
	public void setPrimaryKey(long pk) {
		_emailAddress.setPrimaryKey(pk);
	}

	/**
	* Gets the email address id of this email address.
	*
	* @return the email address id of this email address
	*/
	public long getEmailAddressId() {
		return _emailAddress.getEmailAddressId();
	}

	/**
	* Sets the email address id of this email address.
	*
	* @param emailAddressId the email address id of this email address
	*/
	public void setEmailAddressId(long emailAddressId) {
		_emailAddress.setEmailAddressId(emailAddressId);
	}

	/**
	* Gets the company id of this email address.
	*
	* @return the company id of this email address
	*/
	public long getCompanyId() {
		return _emailAddress.getCompanyId();
	}

	/**
	* Sets the company id of this email address.
	*
	* @param companyId the company id of this email address
	*/
	public void setCompanyId(long companyId) {
		_emailAddress.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this email address.
	*
	* @return the user id of this email address
	*/
	public long getUserId() {
		return _emailAddress.getUserId();
	}

	/**
	* Sets the user id of this email address.
	*
	* @param userId the user id of this email address
	*/
	public void setUserId(long userId) {
		_emailAddress.setUserId(userId);
	}

	/**
	* Gets the user uuid of this email address.
	*
	* @return the user uuid of this email address
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _emailAddress.getUserUuid();
	}

	/**
	* Sets the user uuid of this email address.
	*
	* @param userUuid the user uuid of this email address
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_emailAddress.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this email address.
	*
	* @return the user name of this email address
	*/
	public java.lang.String getUserName() {
		return _emailAddress.getUserName();
	}

	/**
	* Sets the user name of this email address.
	*
	* @param userName the user name of this email address
	*/
	public void setUserName(java.lang.String userName) {
		_emailAddress.setUserName(userName);
	}

	/**
	* Gets the create date of this email address.
	*
	* @return the create date of this email address
	*/
	public java.util.Date getCreateDate() {
		return _emailAddress.getCreateDate();
	}

	/**
	* Sets the create date of this email address.
	*
	* @param createDate the create date of this email address
	*/
	public void setCreateDate(java.util.Date createDate) {
		_emailAddress.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this email address.
	*
	* @return the modified date of this email address
	*/
	public java.util.Date getModifiedDate() {
		return _emailAddress.getModifiedDate();
	}

	/**
	* Sets the modified date of this email address.
	*
	* @param modifiedDate the modified date of this email address
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_emailAddress.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the class name of the model instance this email address is polymorphically associated with.
	*
	* @return the class name of the model instance this email address is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _emailAddress.getClassName();
	}

	/**
	* Gets the class name id of this email address.
	*
	* @return the class name id of this email address
	*/
	public long getClassNameId() {
		return _emailAddress.getClassNameId();
	}

	/**
	* Sets the class name id of this email address.
	*
	* @param classNameId the class name id of this email address
	*/
	public void setClassNameId(long classNameId) {
		_emailAddress.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this email address.
	*
	* @return the class p k of this email address
	*/
	public long getClassPK() {
		return _emailAddress.getClassPK();
	}

	/**
	* Sets the class p k of this email address.
	*
	* @param classPK the class p k of this email address
	*/
	public void setClassPK(long classPK) {
		_emailAddress.setClassPK(classPK);
	}

	/**
	* Gets the address of this email address.
	*
	* @return the address of this email address
	*/
	public java.lang.String getAddress() {
		return _emailAddress.getAddress();
	}

	/**
	* Sets the address of this email address.
	*
	* @param address the address of this email address
	*/
	public void setAddress(java.lang.String address) {
		_emailAddress.setAddress(address);
	}

	/**
	* Gets the type id of this email address.
	*
	* @return the type id of this email address
	*/
	public int getTypeId() {
		return _emailAddress.getTypeId();
	}

	/**
	* Sets the type id of this email address.
	*
	* @param typeId the type id of this email address
	*/
	public void setTypeId(int typeId) {
		_emailAddress.setTypeId(typeId);
	}

	/**
	* Gets the primary of this email address.
	*
	* @return the primary of this email address
	*/
	public boolean getPrimary() {
		return _emailAddress.getPrimary();
	}

	/**
	* Determines if this email address is primary.
	*
	* @return <code>true</code> if this email address is primary; <code>false</code> otherwise
	*/
	public boolean isPrimary() {
		return _emailAddress.isPrimary();
	}

	/**
	* Sets whether this email address is primary.
	*
	* @param primary the primary of this email address
	*/
	public void setPrimary(boolean primary) {
		_emailAddress.setPrimary(primary);
	}

	public boolean isNew() {
		return _emailAddress.isNew();
	}

	public void setNew(boolean n) {
		_emailAddress.setNew(n);
	}

	public boolean isCachedModel() {
		return _emailAddress.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_emailAddress.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _emailAddress.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_emailAddress.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _emailAddress.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _emailAddress.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_emailAddress.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _emailAddress.clone();
	}

	public int compareTo(com.liferay.portal.model.EmailAddress emailAddress) {
		return _emailAddress.compareTo(emailAddress);
	}

	public int hashCode() {
		return _emailAddress.hashCode();
	}

	public com.liferay.portal.model.EmailAddress toEscapedModel() {
		return _emailAddress.toEscapedModel();
	}

	public java.lang.String toString() {
		return _emailAddress.toString();
	}

	public java.lang.String toXmlString() {
		return _emailAddress.toXmlString();
	}

	public com.liferay.portal.model.ListType getType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _emailAddress.getType();
	}

	public EmailAddress getWrappedEmailAddress() {
		return _emailAddress;
	}

	private EmailAddress _emailAddress;
}