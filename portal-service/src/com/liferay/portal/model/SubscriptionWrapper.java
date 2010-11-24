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
 * This class is a wrapper for {@link Subscription}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Subscription
 * @generated
 */
public class SubscriptionWrapper implements Subscription {
	public SubscriptionWrapper(Subscription subscription) {
		_subscription = subscription;
	}

	/**
	* Gets the primary key of this subscription.
	*
	* @return the primary key of this subscription
	*/
	public long getPrimaryKey() {
		return _subscription.getPrimaryKey();
	}

	/**
	* Sets the primary key of this subscription
	*
	* @param pk the primary key of this subscription
	*/
	public void setPrimaryKey(long pk) {
		_subscription.setPrimaryKey(pk);
	}

	/**
	* Gets the subscription id of this subscription.
	*
	* @return the subscription id of this subscription
	*/
	public long getSubscriptionId() {
		return _subscription.getSubscriptionId();
	}

	/**
	* Sets the subscription id of this subscription.
	*
	* @param subscriptionId the subscription id of this subscription
	*/
	public void setSubscriptionId(long subscriptionId) {
		_subscription.setSubscriptionId(subscriptionId);
	}

	/**
	* Gets the company id of this subscription.
	*
	* @return the company id of this subscription
	*/
	public long getCompanyId() {
		return _subscription.getCompanyId();
	}

	/**
	* Sets the company id of this subscription.
	*
	* @param companyId the company id of this subscription
	*/
	public void setCompanyId(long companyId) {
		_subscription.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this subscription.
	*
	* @return the user id of this subscription
	*/
	public long getUserId() {
		return _subscription.getUserId();
	}

	/**
	* Sets the user id of this subscription.
	*
	* @param userId the user id of this subscription
	*/
	public void setUserId(long userId) {
		_subscription.setUserId(userId);
	}

	/**
	* Gets the user uuid of this subscription.
	*
	* @return the user uuid of this subscription
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _subscription.getUserUuid();
	}

	/**
	* Sets the user uuid of this subscription.
	*
	* @param userUuid the user uuid of this subscription
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_subscription.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this subscription.
	*
	* @return the user name of this subscription
	*/
	public java.lang.String getUserName() {
		return _subscription.getUserName();
	}

	/**
	* Sets the user name of this subscription.
	*
	* @param userName the user name of this subscription
	*/
	public void setUserName(java.lang.String userName) {
		_subscription.setUserName(userName);
	}

	/**
	* Gets the create date of this subscription.
	*
	* @return the create date of this subscription
	*/
	public java.util.Date getCreateDate() {
		return _subscription.getCreateDate();
	}

	/**
	* Sets the create date of this subscription.
	*
	* @param createDate the create date of this subscription
	*/
	public void setCreateDate(java.util.Date createDate) {
		_subscription.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this subscription.
	*
	* @return the modified date of this subscription
	*/
	public java.util.Date getModifiedDate() {
		return _subscription.getModifiedDate();
	}

	/**
	* Sets the modified date of this subscription.
	*
	* @param modifiedDate the modified date of this subscription
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_subscription.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the class name of the model instance this subscription is polymorphically associated with.
	*
	* @return the class name of the model instance this subscription is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _subscription.getClassName();
	}

	/**
	* Gets the class name id of this subscription.
	*
	* @return the class name id of this subscription
	*/
	public long getClassNameId() {
		return _subscription.getClassNameId();
	}

	/**
	* Sets the class name id of this subscription.
	*
	* @param classNameId the class name id of this subscription
	*/
	public void setClassNameId(long classNameId) {
		_subscription.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this subscription.
	*
	* @return the class p k of this subscription
	*/
	public long getClassPK() {
		return _subscription.getClassPK();
	}

	/**
	* Sets the class p k of this subscription.
	*
	* @param classPK the class p k of this subscription
	*/
	public void setClassPK(long classPK) {
		_subscription.setClassPK(classPK);
	}

	/**
	* Gets the frequency of this subscription.
	*
	* @return the frequency of this subscription
	*/
	public java.lang.String getFrequency() {
		return _subscription.getFrequency();
	}

	/**
	* Sets the frequency of this subscription.
	*
	* @param frequency the frequency of this subscription
	*/
	public void setFrequency(java.lang.String frequency) {
		_subscription.setFrequency(frequency);
	}

	public boolean isNew() {
		return _subscription.isNew();
	}

	public void setNew(boolean n) {
		_subscription.setNew(n);
	}

	public boolean isCachedModel() {
		return _subscription.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_subscription.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _subscription.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_subscription.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _subscription.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _subscription.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_subscription.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new SubscriptionWrapper((Subscription)_subscription.clone());
	}

	public int compareTo(com.liferay.portal.model.Subscription subscription) {
		return _subscription.compareTo(subscription);
	}

	public int hashCode() {
		return _subscription.hashCode();
	}

	public com.liferay.portal.model.Subscription toEscapedModel() {
		return new SubscriptionWrapper(_subscription.toEscapedModel());
	}

	public java.lang.String toString() {
		return _subscription.toString();
	}

	public java.lang.String toXmlString() {
		return _subscription.toXmlString();
	}

	public Subscription getWrappedSubscription() {
		return _subscription;
	}

	private Subscription _subscription;
}