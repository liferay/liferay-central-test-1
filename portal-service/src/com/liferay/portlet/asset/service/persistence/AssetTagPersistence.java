/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

public interface AssetTagPersistence extends BasePersistence {
	public void cacheResult(com.liferay.portlet.asset.model.AssetTag assetTag);

	public void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags);

	public void clearCache();

	public com.liferay.portlet.asset.model.AssetTag create(long tagId);

	public com.liferay.portlet.asset.model.AssetTag remove(long tagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagException;

	public com.liferay.portlet.asset.model.AssetTag remove(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(AssetTag assetTag, boolean merge)</code>.
	 */
	public com.liferay.portlet.asset.model.AssetTag update(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        assetTag the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when assetTag is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portlet.asset.model.AssetTag update(
		com.liferay.portlet.asset.model.AssetTag assetTag, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetTag updateImpl(
		com.liferay.portlet.asset.model.AssetTag assetTag, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetTag findByPrimaryKey(long tagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagException;

	public com.liferay.portlet.asset.model.AssetTag fetchByPrimaryKey(
		long tagId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetTag findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagException;

	public com.liferay.portlet.asset.model.AssetTag findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagException;

	public com.liferay.portlet.asset.model.AssetTag[] findByGroupId_PrevAndNext(
		long tagId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int getAssetEntriesSize(long pk)
		throws com.liferay.portal.SystemException;

	public boolean containsAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.SystemException;

	public boolean containsAssetEntries(long pk)
		throws com.liferay.portal.SystemException;

	public void addAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.SystemException;

	public void addAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.SystemException;

	public void addAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.SystemException;

	public void addAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.SystemException;

	public void clearAssetEntries(long pk)
		throws com.liferay.portal.SystemException;

	public void removeAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.SystemException;

	public void removeAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.SystemException;

	public void removeAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.SystemException;

	public void removeAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.SystemException;

	public void setAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.SystemException;

	public void setAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.SystemException;
}