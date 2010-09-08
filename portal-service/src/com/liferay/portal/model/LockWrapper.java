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
 * This class is a wrapper for {@link Lock}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Lock
 * @generated
 */
public class LockWrapper implements Lock {
	public LockWrapper(Lock lock) {
		_lock = lock;
	}

	/**
	* Gets the primary key of this lock.
	*
	* @return the primary key of this lock
	*/
	public long getPrimaryKey() {
		return _lock.getPrimaryKey();
	}

	/**
	* Sets the primary key of this lock
	*
	* @param pk the primary key of this lock
	*/
	public void setPrimaryKey(long pk) {
		_lock.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this lock.
	*
	* @return the uuid of this lock
	*/
	public java.lang.String getUuid() {
		return _lock.getUuid();
	}

	/**
	* Sets the uuid of this lock.
	*
	* @param uuid the uuid of this lock
	*/
	public void setUuid(java.lang.String uuid) {
		_lock.setUuid(uuid);
	}

	/**
	* Gets the lock id of this lock.
	*
	* @return the lock id of this lock
	*/
	public long getLockId() {
		return _lock.getLockId();
	}

	/**
	* Sets the lock id of this lock.
	*
	* @param lockId the lock id of this lock
	*/
	public void setLockId(long lockId) {
		_lock.setLockId(lockId);
	}

	/**
	* Gets the company id of this lock.
	*
	* @return the company id of this lock
	*/
	public long getCompanyId() {
		return _lock.getCompanyId();
	}

	/**
	* Sets the company id of this lock.
	*
	* @param companyId the company id of this lock
	*/
	public void setCompanyId(long companyId) {
		_lock.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this lock.
	*
	* @return the user id of this lock
	*/
	public long getUserId() {
		return _lock.getUserId();
	}

	/**
	* Sets the user id of this lock.
	*
	* @param userId the user id of this lock
	*/
	public void setUserId(long userId) {
		_lock.setUserId(userId);
	}

	/**
	* Gets the user uuid of this lock.
	*
	* @return the user uuid of this lock
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _lock.getUserUuid();
	}

	/**
	* Sets the user uuid of this lock.
	*
	* @param userUuid the user uuid of this lock
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_lock.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this lock.
	*
	* @return the user name of this lock
	*/
	public java.lang.String getUserName() {
		return _lock.getUserName();
	}

	/**
	* Sets the user name of this lock.
	*
	* @param userName the user name of this lock
	*/
	public void setUserName(java.lang.String userName) {
		_lock.setUserName(userName);
	}

	/**
	* Gets the create date of this lock.
	*
	* @return the create date of this lock
	*/
	public java.util.Date getCreateDate() {
		return _lock.getCreateDate();
	}

	/**
	* Sets the create date of this lock.
	*
	* @param createDate the create date of this lock
	*/
	public void setCreateDate(java.util.Date createDate) {
		_lock.setCreateDate(createDate);
	}

	/**
	* Gets the class name of this lock.
	*
	* @return the class name of this lock
	*/
	public java.lang.String getClassName() {
		return _lock.getClassName();
	}

	/**
	* Sets the class name of this lock.
	*
	* @param className the class name of this lock
	*/
	public void setClassName(java.lang.String className) {
		_lock.setClassName(className);
	}

	/**
	* Gets the key of this lock.
	*
	* @return the key of this lock
	*/
	public java.lang.String getKey() {
		return _lock.getKey();
	}

	/**
	* Sets the key of this lock.
	*
	* @param key the key of this lock
	*/
	public void setKey(java.lang.String key) {
		_lock.setKey(key);
	}

	/**
	* Gets the owner of this lock.
	*
	* @return the owner of this lock
	*/
	public java.lang.String getOwner() {
		return _lock.getOwner();
	}

	/**
	* Sets the owner of this lock.
	*
	* @param owner the owner of this lock
	*/
	public void setOwner(java.lang.String owner) {
		_lock.setOwner(owner);
	}

	/**
	* Gets the inheritable of this lock.
	*
	* @return the inheritable of this lock
	*/
	public boolean getInheritable() {
		return _lock.getInheritable();
	}

	/**
	* Determines if this lock is inheritable.
	*
	* @return <code>true</code> if this lock is inheritable; <code>false</code> otherwise
	*/
	public boolean isInheritable() {
		return _lock.isInheritable();
	}

	/**
	* Sets whether this {$entity.humanName} is inheritable.
	*
	* @param inheritable the inheritable of this lock
	*/
	public void setInheritable(boolean inheritable) {
		_lock.setInheritable(inheritable);
	}

	/**
	* Gets the expiration date of this lock.
	*
	* @return the expiration date of this lock
	*/
	public java.util.Date getExpirationDate() {
		return _lock.getExpirationDate();
	}

	/**
	* Sets the expiration date of this lock.
	*
	* @param expirationDate the expiration date of this lock
	*/
	public void setExpirationDate(java.util.Date expirationDate) {
		_lock.setExpirationDate(expirationDate);
	}

	public boolean isNew() {
		return _lock.isNew();
	}

	public void setNew(boolean n) {
		_lock.setNew(n);
	}

	public boolean isCachedModel() {
		return _lock.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_lock.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _lock.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_lock.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _lock.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _lock.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_lock.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _lock.clone();
	}

	public int compareTo(com.liferay.portal.model.Lock lock) {
		return _lock.compareTo(lock);
	}

	public int hashCode() {
		return _lock.hashCode();
	}

	public com.liferay.portal.model.Lock toEscapedModel() {
		return _lock.toEscapedModel();
	}

	public java.lang.String toString() {
		return _lock.toString();
	}

	public java.lang.String toXmlString() {
		return _lock.toXmlString();
	}

	public long getExpirationTime() {
		return _lock.getExpirationTime();
	}

	public boolean isExpired() {
		return _lock.isExpired();
	}

	public boolean isNeverExpires() {
		return _lock.isNeverExpires();
	}

	public Lock getWrappedLock() {
		return _lock;
	}

	private Lock _lock;
}