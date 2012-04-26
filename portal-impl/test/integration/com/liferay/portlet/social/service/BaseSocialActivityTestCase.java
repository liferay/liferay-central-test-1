/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityCounter;
import com.liferay.portlet.social.model.SocialActivityCounterConstants;
import com.liferay.portlet.social.model.SocialActivityLimit;
import com.liferay.portlet.social.model.impl.SocialActivityImpl;
import com.liferay.portlet.social.util.SocialConfigurationUtil;

import java.io.InputStream;

/**
 * @author Zsolt Berentey
 */
public class BaseSocialActivityTestCase {

	public static void setUp() throws Exception {
		_userClassNameId = PortalUtil.getClassNameId(User.class.getName());

		Class<?> clazz = SocialActivitySettingLocalServiceTest.class;

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/liferay-social.xml");

		String xml = new String(FileUtil.getBytes(inputStream));

		SocialConfigurationUtil.read(clazz.getClassLoader(), new String[]{xml});
	}

	public static void tearDown() throws Exception {
		if (_actorUser != null) {
			UserLocalServiceUtil.deleteUser(_actorUser);

			_actorUser = null;
		}

		if (_assetEntry != null) {
			AssetEntryLocalServiceUtil.deleteEntry(_assetEntry);

			_assetEntry = null;
		}

		if (_creatorUser != null) {
			UserLocalServiceUtil.deleteUser(_creatorUser);

			_creatorUser = null;
		}

		if (_group != null) {
			GroupLocalServiceUtil.deleteGroup(_group);

			_group = null;
		}
	}

	protected static void addAsset() throws Exception {
		if (_assetEntry != null) {
			AssetEntryLocalServiceUtil.deleteEntry(_assetEntry);
		}

		_assetEntry = AssetEntryLocalServiceUtil.updateEntry(
			_creatorUser.getUserId(), _group.getGroupId(), TEST_MODEL, 1, null,
			null);
	}

	protected static void addGroup(String name) throws Exception {
		_group = ServiceTestUtil.addGroup(name);
	}

	protected static void addUsers() throws Exception {
		if (_actorUser != null) {
			UserLocalServiceUtil.deleteUser(_actorUser);
		}

		_actorUser = ServiceTestUtil.addUser(
			"actor", false, new long[] {_group.getGroupId()});

		if (_creatorUser != null) {
			UserLocalServiceUtil.deleteUser(_creatorUser);
		}

		_creatorUser = ServiceTestUtil.addUser(
			"creator", false, new long[] {_group.getGroupId()});
	}

	protected SocialActivity addActivity(User user, int type) {
		SocialActivity activity = new SocialActivityImpl();

		activity.setAssetEntry(_assetEntry);
		activity.setClassNameId(_assetEntry.getClassNameId());
		activity.setClassPK(_assetEntry.getClassPK());
		activity.setCompanyId(_group.getCompanyId());
		activity.setGroupId(_group.getGroupId());
		activity.setType(type);
		activity.setUserId(user.getUserId());
		activity.setUserUuid(user.getUuid());

		return activity;
	}

	protected SocialActivityCounter getActivityCounter(
			String name, Object owner)
		throws Exception {

		long classNameId = 0;
		long classPk = 0;
		int ownerType = SocialActivityCounterConstants.TYPE_ACTOR;

		if (owner instanceof User) {
			classNameId = _userClassNameId;
			classPk = ((User)owner).getUserId();
		}
		else if (owner instanceof AssetEntry) {
			classNameId = ((AssetEntry)owner).getClassNameId();
			classPk = ((AssetEntry)owner).getClassPK();
			ownerType = SocialActivityCounterConstants.TYPE_ASSET;
		}

		if (name.equals(SocialActivityCounterConstants.NAME_CONTRIBUTION)) {
			ownerType = SocialActivityCounterConstants.TYPE_CREATOR;
		}

		return
			SocialActivityCounterLocalServiceUtil.fetchLatestActivityCounter(
				_group.getGroupId(), classNameId, classPk, name, ownerType);
	}

	protected SocialActivityLimit getActivityLimit(
			User user, AssetEntry assetEntry, int activityType,
			String activityCounterName)
		throws Exception {

		long classPK = assetEntry.getClassPK();

		if (activityCounterName.equals(
				SocialActivityCounterConstants.NAME_PARTICIPATION)) {

			classPK = 0;
		}

		return SocialActivityLimitLocalServiceUtil.fetchActivityLimit(
			_group.getGroupId(), user.getUserId(), _assetEntry.getClassNameId(),
			classPK, activityType, activityCounterName);
	}

	protected static final String TEST_GROUP = "test-group";

	protected static final String TEST_MODEL = "test-model";

	protected static User _actorUser;
	protected static AssetEntry _assetEntry;
	protected static User _creatorUser;
	protected static Group _group;
	protected static long _userClassNameId;

}