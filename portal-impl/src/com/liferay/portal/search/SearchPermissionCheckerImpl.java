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

package com.liferay.portal.search;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.AdvancedPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerBag;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="SearchPermissionCheckerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Allen Chiang
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Amos Fong
 */
public class SearchPermissionCheckerImpl implements SearchPermissionChecker {

	public void addPermissionFields(long companyId, Document doc) {
		try {
			long groupId = GetterUtil.getLong(doc.get(Field.GROUP_ID));
			String className = doc.get(Field.ENTRY_CLASS_NAME);

			String classPK = doc.get(Field.ROOT_ENTRY_CLASS_PK);

			if (Validator.isNull(classPK)) {
				classPK = doc.get(Field.ENTRY_CLASS_PK);
			}

			if (Validator.isNotNull(className) &&
				Validator.isNotNull(classPK)) {

				if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
					doAddPermissionFields_5(
						companyId, groupId, className, classPK, doc);
				}
				else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
					doAddPermissionFields_6(
						companyId, groupId, className, classPK, doc);
				}
			}
		}
		catch (NoSuchResourceException nsre) {
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public Query getPermissionQuery(
		long companyId, long groupId, long userId, String className,
		Query query) {

		try {
			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
				return doGetPermissionQuery_5(
					companyId, groupId, userId, className, query);
			}
			else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				return doGetPermissionQuery_6(
					companyId, groupId, userId, className, query);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return query;
	}

	public void updatePermissionFields(long resourceId) {
		try {
			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
				doUpdatePermissionFields_5(resourceId);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void updatePermissionFields(
		String resourceName, String resourceClassPK) {

		try {
			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				doUpdatePermissionFields_6(resourceName, resourceClassPK);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void addRequiredMemberRole(
			Group group, BooleanQuery permissionQuery)
		throws Exception {

		if (group.isCommunity()) {
			Role communityMemberRole = RoleLocalServiceUtil.getRole(
				group.getCompanyId(), RoleConstants.COMMUNITY_MEMBER);

			permissionQuery.addTerm(
				Field.GROUP_ROLE_ID,
				group.getGroupId() + StringPool.DASH +
					communityMemberRole.getRoleId());
		}
		else if (group.isOrganization()) {
			Role organizationMemberRole = RoleLocalServiceUtil.getRole(
				group.getCompanyId(), RoleConstants.ORGANIZATION_MEMBER);

			permissionQuery.addTerm(
				Field.GROUP_ROLE_ID,
				group.getGroupId() + StringPool.DASH +
					organizationMemberRole.getRoleId());
		}
	}

	protected void doAddPermissionFields_5(
			long companyId, long groupId, String className, String classPK,
			Document doc)
		throws Exception {

		Resource resource = ResourceLocalServiceUtil.getResource(
			companyId, className, ResourceConstants.SCOPE_INDIVIDUAL,
			classPK);

		Group group = null;

		if (groupId > 0) {
			group = GroupLocalServiceUtil.getGroup(groupId);
		}

		List<Role> roles = ResourceActionsUtil.getRoles(
			companyId, group, className);

		List<Long> roleIds = new ArrayList<Long>();
		List<String> groupRoleIds = new ArrayList<String>();

		for (Role role : roles) {
			long roleId = role.getRoleId();

			if (hasPermission(roleId, resource.getResourceId())) {
				if ((role.getType() == RoleConstants.TYPE_COMMUNITY) ||
					(role.getType() == RoleConstants.TYPE_ORGANIZATION)) {

					groupRoleIds.add(groupId + StringPool.DASH + roleId);
				}
				else {
					roleIds.add(roleId);
				}
			}
		}

		doc.addKeyword(
			Field.ROLE_ID, roleIds.toArray(new Long[roleIds.size()]));
		doc.addKeyword(
			Field.GROUP_ROLE_ID,
			groupRoleIds.toArray(new String[groupRoleIds.size()]));
	}

	protected void doAddPermissionFields_6(
			long companyId, long groupId, String className, String classPK,
			Document doc)
		throws Exception {

		Group group = null;

		if (groupId > 0) {
			group = GroupLocalServiceUtil.getGroup(groupId);
		}

		List<Role> roles = ResourceActionsUtil.getRoles(
			companyId, group, className);

		List<Long> roleIds = new ArrayList<Long>();
		List<String> groupRoleIds = new ArrayList<String>();

		for (Role role : roles) {
			long roleId = role.getRoleId();

			if (ResourcePermissionLocalServiceUtil.hasResourcePermission(
					companyId, className, ResourceConstants.SCOPE_INDIVIDUAL,
					classPK, roleId, ActionKeys.VIEW)) {

				if ((role.getType() == RoleConstants.TYPE_COMMUNITY) ||
					(role.getType() == RoleConstants.TYPE_ORGANIZATION)) {

					groupRoleIds.add(groupId + StringPool.DASH + roleId);
				}
				else {
					roleIds.add(roleId);
				}
			}
		}

		doc.addKeyword(
			Field.ROLE_ID, roleIds.toArray(new Long[roleIds.size()]));
		doc.addKeyword(
			Field.GROUP_ROLE_ID,
			groupRoleIds.toArray(new String[groupRoleIds.size()]));
	}

	protected Query doGetPermissionQuery_5(
			long companyId, long groupId, long userId, String className,
			Query query)
		throws Exception {

		PermissionCheckerBag bag = getUserBag(userId);

		List<Group> groups = new ArrayList<Group>();
		List<Role> roles = bag.getRoles();
		List<UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();

		if (groupId == 0) {
			groups.addAll(
				GroupLocalServiceUtil.getUserGroups(userId, true));
			groups.addAll(bag.getGroups());

			userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(
				userId);
		}
		else {
			if (GroupLocalServiceUtil.hasUserGroup(userId, groupId)) {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				groups.add(group);
			}

			userGroupRoles.addAll(
				UserGroupRoleLocalServiceUtil.getUserGroupRoles(
					userId, groupId));
			userGroupRoles.addAll(
				UserGroupRoleLocalServiceUtil.
					getUserGroupRolesByUserUserGroupAndGroup(userId, groupId));
		}

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		if (defaultUserId != userId) {
			roles.add(
				RoleLocalServiceUtil.getRole(companyId, RoleConstants.GUEST));
		}

		long companyResourceId = 0;

		try {
			Resource companyResource = ResourceLocalServiceUtil.getResource(
				companyId, className, ResourceConstants.SCOPE_COMPANY,
				String.valueOf(companyId));

			companyResourceId = companyResource.getResourceId();
		}
		catch (NoSuchResourceException nsre) {
		}

		long groupResourceId = 0;

		try {
			Resource groupResource = ResourceLocalServiceUtil.getResource(
				companyId, className, ResourceConstants.SCOPE_GROUP,
				String.valueOf(groupId));

			groupResourceId = groupResource.getResourceId();
		}
		catch (NoSuchResourceException nsre) {
		}

		BooleanQuery permissionQuery = BooleanQueryFactoryUtil.create();

		if (userId > 0) {
			permissionQuery.addTerm(Field.USER_ID, userId);
		}

		for (Role role : roles) {
			if (role.getName().equals(RoleConstants.ADMINISTRATOR)) {
				return query;
			}

			long roleId = role.getRoleId();

			if (hasPermission(roleId, companyResourceId) ||
				hasPermission(roleId, groupResourceId)) {

				return query;
			}

			permissionQuery.addTerm(Field.ROLE_ID, role.getRoleId());
		}

		for (Group group : groups) {
			addRequiredMemberRole(group, permissionQuery);
		}

		for (UserGroupRole userGroupRole : userGroupRoles) {
			permissionQuery.addTerm(
				Field.GROUP_ROLE_ID,
				userGroupRole.getGroupId() + StringPool.DASH +
					userGroupRole.getRoleId());
		}

		BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

		fullQuery.add(query, BooleanClauseOccur.MUST);
		fullQuery.add(permissionQuery, BooleanClauseOccur.MUST);

		return fullQuery;
	}

	protected Query doGetPermissionQuery_6(
			long companyId, long groupId, long userId, String className,
			Query query)
		throws Exception {

		PermissionCheckerBag bag = getUserBag(userId);

		if (bag == null) {
			return query;
		}

		List<Group> groups = new ArrayList<Group>();
		List<Role> roles = bag.getRoles();
		List<UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();

		if (groupId == 0) {
			groups.addAll(GroupLocalServiceUtil.getUserGroups(userId, true));
			groups.addAll(bag.getGroups());

			userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(
				userId);
		}
		else {
			if (GroupLocalServiceUtil.hasUserGroup(userId, groupId)) {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				groups.add(group);
			}

			userGroupRoles.addAll(
				UserGroupRoleLocalServiceUtil.getUserGroupRoles(
					userId, groupId));
			userGroupRoles.addAll(
				UserGroupRoleLocalServiceUtil.
					getUserGroupRolesByUserUserGroupAndGroup(userId, groupId));
		}

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		if (defaultUserId != userId) {
			roles.add(
				RoleLocalServiceUtil.getRole(companyId, RoleConstants.GUEST));
		}

		BooleanQuery permissionQuery = BooleanQueryFactoryUtil.create();

		if (userId > 0) {
			permissionQuery.addTerm(Field.USER_ID, userId);
		}

		for (Role role : roles) {
			if (role.getName().equals(RoleConstants.ADMINISTRATOR)) {
				return query;
			}

			long roleId = role.getRoleId();

			if (ResourcePermissionLocalServiceUtil.hasResourcePermission(
					companyId, className, ResourceConstants.SCOPE_COMPANY,
					String.valueOf(companyId), roleId, ActionKeys.VIEW) ||
				ResourcePermissionLocalServiceUtil.hasResourcePermission(
					companyId, className, ResourceConstants.SCOPE_GROUP,
					String.valueOf(groupId), roleId, ActionKeys.VIEW)) {

				return query;
			}

			permissionQuery.addTerm(Field.ROLE_ID, roleId);
		}

		for (Group group : groups) {
			addRequiredMemberRole(group, permissionQuery);
		}

		for (UserGroupRole userGroupRole : userGroupRoles) {
			permissionQuery.addTerm(
				Field.GROUP_ROLE_ID,
				userGroupRole.getGroupId() + StringPool.DASH +
					userGroupRole.getRoleId());
		}

		BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

		fullQuery.add(query, BooleanClauseOccur.MUST);
		fullQuery.add(permissionQuery, BooleanClauseOccur.MUST);

		return fullQuery;
	}

	protected void doUpdatePermissionFields_5(long resourceId)
		throws Exception {

		Resource resource = ResourceLocalServiceUtil.getResource(resourceId);

		Indexer indexer = IndexerRegistryUtil.getIndexer(resource.getName());

		if (indexer != null) {
			indexer.reindex(
				resource.getName(), GetterUtil.getLong(resource.getPrimKey()));
		}
	}

	protected void doUpdatePermissionFields_6(
			String resourceName, String resourceClassPK)
		throws Exception {

		Indexer indexer = IndexerRegistryUtil.getIndexer(resourceName);

		if (indexer != null) {
			indexer.reindex(resourceName, GetterUtil.getLong(resourceClassPK));
		}
	}

	protected PermissionCheckerBag getUserBag(long userId) throws Exception {
		User user = UserLocalServiceUtil.getUser(userId);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user, true);

		if (permissionChecker instanceof AdvancedPermissionChecker) {
			AdvancedPermissionChecker advancedPermissionChecker =
				(AdvancedPermissionChecker)permissionChecker;

			return advancedPermissionChecker.getUserBag(userId, 0);
		}
		else {
			return null;
		}
	}

	protected boolean hasPermission(long roleId, long resourceId)
		throws SystemException {

		if (resourceId == 0) {
			return false;
		}

		List<Permission> permissions =
			PermissionLocalServiceUtil.getRolePermissions(roleId, resourceId);

		List<String> actions = ResourceActionsUtil.getActions(permissions);

		if (actions.contains(ActionKeys.VIEW)) {
			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SearchPermissionCheckerImpl.class);

}