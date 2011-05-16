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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.cache.ThreadLocalCachable;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.asset.DuplicateTagException;
import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.service.base.AssetTagLocalServiceBaseImpl;
import com.liferay.portlet.asset.util.AssetUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alvaro del Castillo
 * @author Jorge Ferrer
 * @author Bruno Farache
 */
public class AssetTagLocalServiceImpl extends AssetTagLocalServiceBaseImpl {

	public AssetTag addTag(
			long userId, String name, String[] tagProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Tag

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();

		if (tagProperties == null) {
			tagProperties = new String[0];
		}

		Date now = new Date();

		long tagId = counterLocalService.increment();

		AssetTag tag = assetTagPersistence.create(tagId);

		tag.setGroupId(groupId);
		tag.setCompanyId(user.getCompanyId());
		tag.setUserId(user.getUserId());
		tag.setUserName(user.getFullName());
		tag.setCreateDate(now);
		tag.setModifiedDate(now);

		name = name.trim();
		name = name.toLowerCase();

		if (hasTag(groupId, name)) {
			throw new DuplicateTagException(
				"A tag with the name " + name + " already exists");
		}

		validate(name);

		tag.setName(name);

		assetTagPersistence.update(tag, false);

		// Resources

		if (serviceContext.getAddGroupPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addTagResources(
				tag, serviceContext.getAddGroupPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addTagResources(
				tag, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Properties

		for (int i = 0; i < tagProperties.length; i++) {
			String[] tagProperty = StringUtil.split(
				tagProperties[i], StringPool.COLON);

			String key = StringPool.BLANK;

			if (tagProperty.length > 0) {
				key = GetterUtil.getString(tagProperty[0]);
			}

			String value = StringPool.BLANK;

			if (tagProperty.length > 1) {
				value = GetterUtil.getString(tagProperty[1]);
			}

			if (Validator.isNotNull(key)) {
				assetTagPropertyLocalService.addTagProperty(
					userId, tagId, key, value);
			}
		}

		return tag;
	}

	public void addTagResources(
			AssetTag tag, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			tag.getCompanyId(), tag.getGroupId(), tag.getUserId(),
			AssetTag.class.getName(), tag.getTagId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addTagResources(
			AssetTag tag, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			tag.getCompanyId(), tag.getGroupId(), tag.getUserId(),
			AssetTag.class.getName(), tag.getTagId(), communityPermissions,
			guestPermissions);
	}

	public void checkTags(long userId, long groupId, String[] names)
		throws PortalException, SystemException {

		for (String name : names) {
			try {
				getTag(groupId, name);
			}
			catch (NoSuchTagException nste) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddGroupPermissions(true);
				serviceContext.setAddGuestPermissions(true);
				serviceContext.setScopeGroupId(groupId);

				addTag(
					userId, name, PropsValues.ASSET_TAG_PROPERTIES_DEFAULT,
					serviceContext);
			}
		}
	}

	public AssetTag decrementAssetCount(long tagId, long classNameId)
		throws PortalException, SystemException {

		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		tag.setAssetCount(Math.max(0, tag.getAssetCount() - 1));

		assetTagPersistence.update(tag, false);

		assetTagStatsLocalService.updateTagStats(tagId, classNameId);

		return tag;
	}

	public void deleteTag(AssetTag tag)
		throws PortalException, SystemException {

		// Entries

		List<AssetEntry> entries = assetTagPersistence.getAssetEntries(
			tag.getTagId());

		// Tag

		assetTagPersistence.remove(tag);

		// Resources

		resourceLocalService.deleteResource(
			tag.getCompanyId(), AssetTag.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, tag.getTagId());

		// Properties

		assetTagPropertyLocalService.deleteTagProperties(tag.getTagId());

		// Indexer

		reindex(entries);
	}

	public void deleteTag(long tagId) throws PortalException, SystemException {
		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		deleteTag(tag);
	}

	public List<AssetTag> getEntryTags(long entryId) throws SystemException {
		return assetTagFinder.findByEntryId(entryId);
	}

	public List<AssetTag> getGroupsTags(long[] groupIds)
		throws SystemException {

		List<AssetTag> groupsTags = new ArrayList<AssetTag>();

		for (long groupId : groupIds) {
			List<AssetTag> groupTags = getGroupTags(groupId);

			groupsTags.addAll(groupTags);
		}

		return groupsTags;
	}

	public List<AssetTag> getGroupTags(long groupId) throws SystemException {
		return assetTagPersistence.findByGroupId(groupId);
	}

	public List<AssetTag> getGroupTags(long groupId, int start, int end)
		throws SystemException {

		return assetTagPersistence.findByGroupId(groupId, start, end);
	}

	public AssetTag getTag(long tagId) throws PortalException, SystemException {
		return assetTagPersistence.findByPrimaryKey(tagId);
	}

	public AssetTag getTag(long groupId, String name)
		throws PortalException, SystemException {

		return assetTagFinder.findByG_N(groupId, name);
	}

	public long[] getTagIds(long groupId, String[] names)
		throws PortalException, SystemException {

		List<Long> tagIds = new ArrayList<Long>(names.length);

		for (String name : names) {
			try {
				AssetTag tag = getTag(groupId, name);

				tagIds.add(tag.getTagId());
			}
			catch (NoSuchTagException nste) {
			}
		}

		return ArrayUtil.toArray(tagIds.toArray(new Long[tagIds.size()]));
	}

	public long[] getTagIds(long[] groupIds, String[] names)
		throws PortalException, SystemException {

		long[] tagsIds = new long[0];

		for (long groupId : groupIds) {
			tagsIds = ArrayUtil.append(tagsIds, getTagIds(groupId, names));
		}

		return tagsIds;
	}

	public String[] getTagNames() throws SystemException {
		return getTagNames(getTags());
	}

	public String[] getTagNames(long classNameId, long classPK)
		throws SystemException {

		return getTagNames(getTags(classNameId, classPK));
	}

	public String[] getTagNames(String className, long classPK)
		throws SystemException {

		return getTagNames(getTags(className, classPK));
	}

	public List<AssetTag> getTags() throws SystemException {
		return assetTagPersistence.findAll();
	}

	public List<AssetTag> getTags(long classNameId, long classPK)
		throws SystemException {

		return assetTagFinder.findByC_C(classNameId, classPK);
	}

	public List<AssetTag> getTags(long groupId, long classNameId, String name)
		throws SystemException {

		return assetTagFinder.findByG_C_N(
			groupId, classNameId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	public List<AssetTag> getTags(
			long groupId, long classNameId, String name, int start, int end)
		throws SystemException {

		return assetTagFinder.findByG_C_N(
			groupId, classNameId, name, start, end, null);
	}

	@ThreadLocalCachable
	public List<AssetTag> getTags(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getTags(classNameId, classPK);
	}

	public int getTagsSize(long groupId, long classNameId, String name)
		throws SystemException {

		return assetTagFinder.countByG_C_N(groupId, classNameId, name);
	}

	public boolean hasTag(long groupId, String name)
		throws PortalException, SystemException {

		try {
			getTag(groupId, name);

			return true;
		}
		catch (NoSuchTagException nste) {
			return false;
		}
	}

	public AssetTag incrementAssetCount(long tagId, long classNameId)
		throws PortalException, SystemException {

		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		tag.setAssetCount(tag.getAssetCount() + 1);

		assetTagPersistence.update(tag, false);

		assetTagStatsLocalService.updateTagStats(tagId, classNameId);

		return tag;
	}

	public void mergeTags(long fromTagId, long toTagId)
		throws PortalException, SystemException {

		List<AssetEntry> entries = assetTagPersistence.getAssetEntries(
			fromTagId);

		assetTagPersistence.addAssetEntries(toTagId, entries);

		List<AssetTagProperty> tagProperties =
			assetTagPropertyPersistence.findByTagId(fromTagId);

		for (AssetTagProperty fromTagProperty : tagProperties) {
			AssetTagProperty toTagProperty =
				assetTagPropertyPersistence.fetchByT_K(
					toTagId, fromTagProperty.getKey());

			if (toTagProperty == null) {
				fromTagProperty.setTagId(toTagId);

				assetTagPropertyPersistence.update(fromTagProperty, false);
			}
		}

		deleteTag(fromTagId);
	}

	public List<AssetTag> search(
			long groupId, String name, String[] tagProperties, int start,
			int end)
		throws SystemException {

		return assetTagFinder.findByG_N_P(
			groupId, name, tagProperties, start, end, null);
	}

	public AssetTag updateTag(
			long userId, long tagId, String name, String[] tagProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Tag

		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		String oldName = tag.getName();

		tag.setModifiedDate(new Date());

		name = name.trim();
		name = name.toLowerCase();

		if (tagProperties == null) {
			tagProperties = new String[0];
		}

		if (!tag.getName().equals(name) &&
			hasTag(tag.getGroupId(), name)) {

			throw new DuplicateTagException(
				"A tag with the name " + name + " already exists");
		}

		if (!tag.getName().equals(name)) {
			try {
				AssetTag existingAssetTag = getTag(tag.getGroupId(), name);

				if (existingAssetTag.getTagId() != tagId) {
					throw new DuplicateTagException(
						"A tag with the name " + name + " already exists");
				}
			}
			catch (NoSuchTagException nste) {
			}
		}

		validate(name);

		tag.setName(name);

		assetTagPersistence.update(tag, false);

		// Properties

		List<AssetTagProperty> oldTagProperties =
			assetTagPropertyPersistence.findByTagId(tagId);

		for (AssetTagProperty tagProperty : oldTagProperties) {
			assetTagPropertyLocalService.deleteTagProperty(tagProperty);
		}

		for (int i = 0; i < tagProperties.length; i++) {
			String[] tagProperty = StringUtil.split(
				tagProperties[i], StringPool.COLON);

			String key = StringPool.BLANK;

			if (tagProperty.length > 0) {
				key = GetterUtil.getString(tagProperty[0]);
			}

			String value = StringPool.BLANK;

			if (tagProperty.length > 1) {
				value = GetterUtil.getString(tagProperty[1]);
			}

			if (Validator.isNotNull(key)) {
				assetTagPropertyLocalService.addTagProperty(
					userId, tagId, key, value);
			}
		}

		// Indexer

		if (!oldName.equals(name)) {
			List<AssetEntry> entries = assetTagPersistence.getAssetEntries(
				tag.getTagId());

			reindex(entries);
		}

		return tag;
	}

	protected String[] getTagNames(List <AssetTag>tags) {
		return StringUtil.split(ListUtil.toString(tags, "name"));
	}

	protected void reindex(List<AssetEntry> entries) throws PortalException {
		for (AssetEntry entry : entries) {
			String className = PortalUtil.getClassName(entry.getClassNameId());

			Indexer indexer = IndexerRegistryUtil.getIndexer(className);

			indexer.reindex(className, entry.getClassPK());
		}
	}

	protected void validate(String name) throws PortalException {
		if (!AssetUtil.isValidWord(name)) {
			throw new AssetTagException(AssetTagException.INVALID_CHARACTER);
		}
	}

}