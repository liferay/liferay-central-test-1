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

package com.liferay.portlet.asset.model;

/**
 * <p>
 * This class is a wrapper for {@link AssetTagStats}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagStats
 * @generated
 */
public class AssetTagStatsWrapper implements AssetTagStats {
	public AssetTagStatsWrapper(AssetTagStats assetTagStats) {
		_assetTagStats = assetTagStats;
	}

	public Class<?> getModelClass() {
		return AssetTagStats.class;
	}

	public String getModelClassName() {
		return AssetTagStats.class.getName();
	}

	/**
	* Gets the primary key of this asset tag stats.
	*
	* @return the primary key of this asset tag stats
	*/
	public long getPrimaryKey() {
		return _assetTagStats.getPrimaryKey();
	}

	/**
	* Sets the primary key of this asset tag stats
	*
	* @param pk the primary key of this asset tag stats
	*/
	public void setPrimaryKey(long pk) {
		_assetTagStats.setPrimaryKey(pk);
	}

	/**
	* Gets the tag stats ID of this asset tag stats.
	*
	* @return the tag stats ID of this asset tag stats
	*/
	public long getTagStatsId() {
		return _assetTagStats.getTagStatsId();
	}

	/**
	* Sets the tag stats ID of this asset tag stats.
	*
	* @param tagStatsId the tag stats ID of this asset tag stats
	*/
	public void setTagStatsId(long tagStatsId) {
		_assetTagStats.setTagStatsId(tagStatsId);
	}

	/**
	* Gets the tag ID of this asset tag stats.
	*
	* @return the tag ID of this asset tag stats
	*/
	public long getTagId() {
		return _assetTagStats.getTagId();
	}

	/**
	* Sets the tag ID of this asset tag stats.
	*
	* @param tagId the tag ID of this asset tag stats
	*/
	public void setTagId(long tagId) {
		_assetTagStats.setTagId(tagId);
	}

	/**
	* Gets the class name of the model instance this asset tag stats is polymorphically associated with.
	*
	* @return the class name of the model instance this asset tag stats is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _assetTagStats.getClassName();
	}

	/**
	* Gets the class name ID of this asset tag stats.
	*
	* @return the class name ID of this asset tag stats
	*/
	public long getClassNameId() {
		return _assetTagStats.getClassNameId();
	}

	/**
	* Sets the class name ID of this asset tag stats.
	*
	* @param classNameId the class name ID of this asset tag stats
	*/
	public void setClassNameId(long classNameId) {
		_assetTagStats.setClassNameId(classNameId);
	}

	/**
	* Gets the asset count of this asset tag stats.
	*
	* @return the asset count of this asset tag stats
	*/
	public int getAssetCount() {
		return _assetTagStats.getAssetCount();
	}

	/**
	* Sets the asset count of this asset tag stats.
	*
	* @param assetCount the asset count of this asset tag stats
	*/
	public void setAssetCount(int assetCount) {
		_assetTagStats.setAssetCount(assetCount);
	}

	public boolean isNew() {
		return _assetTagStats.isNew();
	}

	public void setNew(boolean n) {
		_assetTagStats.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetTagStats.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetTagStats.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetTagStats.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_assetTagStats.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetTagStats.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetTagStats.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetTagStats.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new AssetTagStatsWrapper((AssetTagStats)_assetTagStats.clone());
	}

	public int compareTo(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats) {
		return _assetTagStats.compareTo(assetTagStats);
	}

	public int hashCode() {
		return _assetTagStats.hashCode();
	}

	public com.liferay.portlet.asset.model.AssetTagStats toEscapedModel() {
		return new AssetTagStatsWrapper(_assetTagStats.toEscapedModel());
	}

	public java.lang.String toString() {
		return _assetTagStats.toString();
	}

	public java.lang.String toXmlString() {
		return _assetTagStats.toXmlString();
	}

	public AssetTagStats getWrappedAssetTagStats() {
		return _assetTagStats;
	}

	public void resetOriginalValues() {
		_assetTagStats.resetOriginalValues();
	}

	private AssetTagStats _assetTagStats;
}