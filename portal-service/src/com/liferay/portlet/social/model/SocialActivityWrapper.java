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
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SocialActivity}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialActivity
 * @generated
 */
public class SocialActivityWrapper implements SocialActivity {
	public SocialActivityWrapper(SocialActivity socialActivity) {
		_socialActivity = socialActivity;
	}

	public long getPrimaryKey() {
		return _socialActivity.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_socialActivity.setPrimaryKey(pk);
	}

	public long getActivityId() {
		return _socialActivity.getActivityId();
	}

	public void setActivityId(long activityId) {
		_socialActivity.setActivityId(activityId);
	}

	public long getGroupId() {
		return _socialActivity.getGroupId();
	}

	public void setGroupId(long groupId) {
		_socialActivity.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _socialActivity.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_socialActivity.setCompanyId(companyId);
	}

	public long getUserId() {
		return _socialActivity.getUserId();
	}

	public void setUserId(long userId) {
		_socialActivity.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivity.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_socialActivity.setUserUuid(userUuid);
	}

	public long getCreateDate() {
		return _socialActivity.getCreateDate();
	}

	public void setCreateDate(long createDate) {
		_socialActivity.setCreateDate(createDate);
	}

	public long getMirrorActivityId() {
		return _socialActivity.getMirrorActivityId();
	}

	public void setMirrorActivityId(long mirrorActivityId) {
		_socialActivity.setMirrorActivityId(mirrorActivityId);
	}

	public java.lang.String getClassName() {
		return _socialActivity.getClassName();
	}

	public long getClassNameId() {
		return _socialActivity.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_socialActivity.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _socialActivity.getClassPK();
	}

	public void setClassPK(long classPK) {
		_socialActivity.setClassPK(classPK);
	}

	public int getType() {
		return _socialActivity.getType();
	}

	public void setType(int type) {
		_socialActivity.setType(type);
	}

	public java.lang.String getExtraData() {
		return _socialActivity.getExtraData();
	}

	public void setExtraData(java.lang.String extraData) {
		_socialActivity.setExtraData(extraData);
	}

	public long getReceiverUserId() {
		return _socialActivity.getReceiverUserId();
	}

	public void setReceiverUserId(long receiverUserId) {
		_socialActivity.setReceiverUserId(receiverUserId);
	}

	public java.lang.String getReceiverUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivity.getReceiverUserUuid();
	}

	public void setReceiverUserUuid(java.lang.String receiverUserUuid) {
		_socialActivity.setReceiverUserUuid(receiverUserUuid);
	}

	public com.liferay.portlet.social.model.SocialActivity toEscapedModel() {
		return _socialActivity.toEscapedModel();
	}

	public boolean isNew() {
		return _socialActivity.isNew();
	}

	public void setNew(boolean n) {
		_socialActivity.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialActivity.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialActivity.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialActivity.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_socialActivity.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialActivity.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialActivity.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialActivity.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _socialActivity.clone();
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialActivity socialActivity) {
		return _socialActivity.compareTo(socialActivity);
	}

	public int hashCode() {
		return _socialActivity.hashCode();
	}

	public java.lang.String toString() {
		return _socialActivity.toString();
	}

	public java.lang.String toXmlString() {
		return _socialActivity.toXmlString();
	}

	public SocialActivity getWrappedSocialActivity() {
		return _socialActivity;
	}

	private SocialActivity _socialActivity;
}