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

package com.liferay.portal.search;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.SystemException;
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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.ActionKeys;
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

	protected void doAddPermissionFields_5(
			long companyId, long groupId, String className, String classPK,
			Document doc)
		throws Exception {

		Resource resource = ResourceLocalServiceUtil.getResource(
			companyId, className, ResourceConstants.SCOPE_INDIVIDUAL,
			classPK);

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		List<Role> roles = ResourceActionsUtil.getRoles(group, className);

		List<Long> roleIds = new ArrayList<Long>();

		for (Role role : roles) {
			long roleId = role.getRoleId();

			if (hasPermission(roleId, resource.getResourceId())) {
				roleIds.add(roleId);
			}
		}

		doc.addKeyword(
			Field.ROLE_ID, roleIds.toArray(new Long[roleIds.size()]));
	}

	protected void doAddPermissionFields_6(
			long companyId, long groupId, String className, String classPK,
			Document doc)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		List<Role> roles = ResourceActionsUtil.getRoles(group, className);

		List<Long> roleIds = new ArrayList<Long>();

		for (Role role : roles) {
			long roleId = role.getRoleId();

			if (ResourcePermissionLocalServiceUtil.hasResourcePermission(
					companyId, className, ResourceConstants.SCOPE_INDIVIDUAL,
					classPK, roleId, ActionKeys.VIEW)) {

				roleIds.add(roleId);
			}
		}

		doc.addKeyword(
			Field.ROLE_ID, roleIds.toArray(new Long[roleIds.size()]));
	}

	protected Query doGetPermissionQuery_5(
			long companyId, long groupId, long userId, String className,
			Query query)
		throws Exception {

		BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

		BooleanQuery permissionQuery = BooleanQueryFactoryUtil.create();

		List<Role> roles = RoleLocalServiceUtil.getUserRoles(userId);

		roles = ListUtil.copy(roles);

		if (groupId == 0) {
			List<UserGroupRole> userGroupRoles =
				UserGroupRoleLocalServiceUtil.getUserGroupRoles(userId);

			for (UserGroupRole userGroupRole : userGroupRoles) {
				roles.add(userGroupRole.getRole());
			}
		}
		else {
			roles.addAll(
				RoleLocalServiceUtil.getUserGroupRoles(userId, groupId));
			roles.addAll(
				RoleLocalServiceUtil.getUserGroupGroupRoles(userId, groupId));
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

		fullQuery.add(query, BooleanClauseOccur.MUST);
		fullQuery.add(permissionQuery, BooleanClauseOccur.MUST);

		return fullQuery;
	}

	protected Query doGetPermissionQuery_6(
			long companyId, long groupId, long userId, String className,
			Query query)
		throws Exception {

		BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

		BooleanQuery permissionQuery = BooleanQueryFactoryUtil.create();

		List<Role> roles = RoleLocalServiceUtil.getUserRoles(userId);

		roles = ListUtil.copy(roles);

		if (groupId == 0) {
			List<UserGroupRole> userGroupRoles =
				UserGroupRoleLocalServiceUtil.getUserGroupRoles(userId);

			for (UserGroupRole userGroupRole : userGroupRoles) {
				roles.add(userGroupRole.getRole());
			}
		}
		else {
			roles.addAll(
				RoleLocalServiceUtil.getUserGroupRoles(userId, groupId));
			roles.addAll(
				RoleLocalServiceUtil.getUserGroupGroupRoles(userId, groupId));
		}

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		if (defaultUserId != userId) {
			roles.add(
				RoleLocalServiceUtil.getRole(companyId, RoleConstants.GUEST));
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

		fullQuery.add(query, BooleanClauseOccur.MUST);
		fullQuery.add(permissionQuery, BooleanClauseOccur.MUST);

		return fullQuery;
	}

	protected void doUpdatePermissionFields_5(long resourceId)
		throws Exception {

		Resource resource = ResourceLocalServiceUtil.getResource(resourceId);

		Indexer indexer = IndexerRegistryUtil.getIndexer(resource.getName());

		if (indexer != null) {
			indexer.reIndex(
				resource.getName(), GetterUtil.getLong(resource.getPrimKey()));
		}
	}

	protected void doUpdatePermissionFields_6(
			String resourceName, String resourceClassPK)
		throws Exception {

		Indexer indexer = IndexerRegistryUtil.getIndexer(resourceName);

		if (indexer != null) {
			indexer.reIndex(resourceName, GetterUtil.getLong(resourceClassPK));
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

	private static Log _log =
		LogFactoryUtil.getLog(SearchPermissionCheckerImpl.class);

}