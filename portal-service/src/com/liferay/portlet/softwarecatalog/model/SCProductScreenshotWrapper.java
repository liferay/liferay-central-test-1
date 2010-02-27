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

package com.liferay.portlet.softwarecatalog.model;


/**
 * <a href="SCProductScreenshotSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SCProductScreenshot}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductScreenshot
 * @generated
 */
public class SCProductScreenshotWrapper implements SCProductScreenshot {
	public SCProductScreenshotWrapper(SCProductScreenshot scProductScreenshot) {
		_scProductScreenshot = scProductScreenshot;
	}

	public long getPrimaryKey() {
		return _scProductScreenshot.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_scProductScreenshot.setPrimaryKey(pk);
	}

	public long getProductScreenshotId() {
		return _scProductScreenshot.getProductScreenshotId();
	}

	public void setProductScreenshotId(long productScreenshotId) {
		_scProductScreenshot.setProductScreenshotId(productScreenshotId);
	}

	public long getCompanyId() {
		return _scProductScreenshot.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_scProductScreenshot.setCompanyId(companyId);
	}

	public long getGroupId() {
		return _scProductScreenshot.getGroupId();
	}

	public void setGroupId(long groupId) {
		_scProductScreenshot.setGroupId(groupId);
	}

	public long getProductEntryId() {
		return _scProductScreenshot.getProductEntryId();
	}

	public void setProductEntryId(long productEntryId) {
		_scProductScreenshot.setProductEntryId(productEntryId);
	}

	public long getThumbnailId() {
		return _scProductScreenshot.getThumbnailId();
	}

	public void setThumbnailId(long thumbnailId) {
		_scProductScreenshot.setThumbnailId(thumbnailId);
	}

	public long getFullImageId() {
		return _scProductScreenshot.getFullImageId();
	}

	public void setFullImageId(long fullImageId) {
		_scProductScreenshot.setFullImageId(fullImageId);
	}

	public int getPriority() {
		return _scProductScreenshot.getPriority();
	}

	public void setPriority(int priority) {
		_scProductScreenshot.setPriority(priority);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductScreenshot toEscapedModel() {
		return _scProductScreenshot.toEscapedModel();
	}

	public boolean isNew() {
		return _scProductScreenshot.isNew();
	}

	public boolean setNew(boolean n) {
		return _scProductScreenshot.setNew(n);
	}

	public boolean isCachedModel() {
		return _scProductScreenshot.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_scProductScreenshot.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _scProductScreenshot.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_scProductScreenshot.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _scProductScreenshot.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _scProductScreenshot.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_scProductScreenshot.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _scProductScreenshot.clone();
	}

	public int compareTo(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot) {
		return _scProductScreenshot.compareTo(scProductScreenshot);
	}

	public int hashCode() {
		return _scProductScreenshot.hashCode();
	}

	public java.lang.String toString() {
		return _scProductScreenshot.toString();
	}

	public java.lang.String toXmlString() {
		return _scProductScreenshot.toXmlString();
	}

	public SCProductScreenshot getWrappedSCProductScreenshot() {
		return _scProductScreenshot;
	}

	private SCProductScreenshot _scProductScreenshot;
}