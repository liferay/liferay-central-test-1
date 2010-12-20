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
 * <p>
 * This class is a wrapper for {@link SCLicense}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCLicense
 * @generated
 */
public class SCLicenseWrapper implements SCLicense {
	public SCLicenseWrapper(SCLicense scLicense) {
		_scLicense = scLicense;
	}

	/**
	* Gets the primary key of this s c license.
	*
	* @return the primary key of this s c license
	*/
	public long getPrimaryKey() {
		return _scLicense.getPrimaryKey();
	}

	/**
	* Sets the primary key of this s c license
	*
	* @param pk the primary key of this s c license
	*/
	public void setPrimaryKey(long pk) {
		_scLicense.setPrimaryKey(pk);
	}

	/**
	* Gets the license ID of this s c license.
	*
	* @return the license ID of this s c license
	*/
	public long getLicenseId() {
		return _scLicense.getLicenseId();
	}

	/**
	* Sets the license ID of this s c license.
	*
	* @param licenseId the license ID of this s c license
	*/
	public void setLicenseId(long licenseId) {
		_scLicense.setLicenseId(licenseId);
	}

	/**
	* Gets the name of this s c license.
	*
	* @return the name of this s c license
	*/
	public java.lang.String getName() {
		return _scLicense.getName();
	}

	/**
	* Sets the name of this s c license.
	*
	* @param name the name of this s c license
	*/
	public void setName(java.lang.String name) {
		_scLicense.setName(name);
	}

	/**
	* Gets the url of this s c license.
	*
	* @return the url of this s c license
	*/
	public java.lang.String getUrl() {
		return _scLicense.getUrl();
	}

	/**
	* Sets the url of this s c license.
	*
	* @param url the url of this s c license
	*/
	public void setUrl(java.lang.String url) {
		_scLicense.setUrl(url);
	}

	/**
	* Gets the open source of this s c license.
	*
	* @return the open source of this s c license
	*/
	public boolean getOpenSource() {
		return _scLicense.getOpenSource();
	}

	/**
	* Determines if this s c license is open source.
	*
	* @return <code>true</code> if this s c license is open source; <code>false</code> otherwise
	*/
	public boolean isOpenSource() {
		return _scLicense.isOpenSource();
	}

	/**
	* Sets whether this s c license is open source.
	*
	* @param openSource the open source of this s c license
	*/
	public void setOpenSource(boolean openSource) {
		_scLicense.setOpenSource(openSource);
	}

	/**
	* Gets the active of this s c license.
	*
	* @return the active of this s c license
	*/
	public boolean getActive() {
		return _scLicense.getActive();
	}

	/**
	* Determines if this s c license is active.
	*
	* @return <code>true</code> if this s c license is active; <code>false</code> otherwise
	*/
	public boolean isActive() {
		return _scLicense.isActive();
	}

	/**
	* Sets whether this s c license is active.
	*
	* @param active the active of this s c license
	*/
	public void setActive(boolean active) {
		_scLicense.setActive(active);
	}

	/**
	* Gets the recommended of this s c license.
	*
	* @return the recommended of this s c license
	*/
	public boolean getRecommended() {
		return _scLicense.getRecommended();
	}

	/**
	* Determines if this s c license is recommended.
	*
	* @return <code>true</code> if this s c license is recommended; <code>false</code> otherwise
	*/
	public boolean isRecommended() {
		return _scLicense.isRecommended();
	}

	/**
	* Sets whether this s c license is recommended.
	*
	* @param recommended the recommended of this s c license
	*/
	public void setRecommended(boolean recommended) {
		_scLicense.setRecommended(recommended);
	}

	public boolean isNew() {
		return _scLicense.isNew();
	}

	public void setNew(boolean n) {
		_scLicense.setNew(n);
	}

	public boolean isCachedModel() {
		return _scLicense.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_scLicense.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _scLicense.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_scLicense.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _scLicense.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _scLicense.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_scLicense.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new SCLicenseWrapper((SCLicense)_scLicense.clone());
	}

	public int compareTo(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense) {
		return _scLicense.compareTo(scLicense);
	}

	public int hashCode() {
		return _scLicense.hashCode();
	}

	public com.liferay.portlet.softwarecatalog.model.SCLicense toEscapedModel() {
		return new SCLicenseWrapper(_scLicense.toEscapedModel());
	}

	public java.lang.String toString() {
		return _scLicense.toString();
	}

	public java.lang.String toXmlString() {
		return _scLicense.toXmlString();
	}

	public SCLicense getWrappedSCLicense() {
		return _scLicense;
	}

	private SCLicense _scLicense;
}