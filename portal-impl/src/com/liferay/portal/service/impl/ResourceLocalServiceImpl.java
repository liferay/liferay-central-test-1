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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.ResourceActionsException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.security.permission.PermissionsListFilter;
import com.liferay.portal.security.permission.PermissionsListFilterFactory;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.base.ResourceLocalServiceBaseImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.comparator.ResourceComparator;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="ResourceLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Wilson S. Man
 * @author Raymond Augé
 * @author Julio Camarero
 */
public class ResourceLocalServiceImpl extends ResourceLocalServiceBaseImpl {

	public void addModelResources(
			long companyId, long groupId, long userId, String name,
			long primKey, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		addModelResources(
			companyId, groupId, userId, name, String.valueOf(primKey),
			communityPermissions, guestPermissions);
	}

	public void addModelResources(
			long companyId, long groupId, long userId, String name,
			String primKey, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		if (!PermissionThreadLocal.isAddResource()) {
			return;
		}

		validate(name, false);

		// Company

		addResource(
			companyId, name, ResourceConstants.SCOPE_COMPANY,
			String.valueOf(companyId));

		// Guest

		Group guestGroup = groupLocalService.getGroup(
			companyId, GroupConstants.GUEST);

		addResource(
			companyId, name, ResourceConstants.SCOPE_GROUP,
			String.valueOf(guestGroup.getGroupId()));

		// Group

		if ((groupId > 0) && (guestGroup.getGroupId() != groupId)) {
			addResource(
				companyId, name, ResourceConstants.SCOPE_GROUP,
				String.valueOf(groupId));
		}

		if (primKey == null) {
			return;
		}

		// Individual

		Resource resource = addResource(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

		// Permissions

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			addModelResources_6(
				companyId, groupId, resource, communityPermissions,
				guestPermissions);
		}
		else {
			addModelResources_1to5(
				companyId, groupId, userId, resource, communityPermissions,
				guestPermissions);
		}
	}

	public Resource addResource(
			long companyId, String name, int scope, String primKey)
		throws SystemException {

		if (!PermissionThreadLocal.isAddResource()) {
			return null;
		}

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			return addResource_6(companyId, name, scope, primKey);
		}
		else {
			return addResource_1to5(companyId, name, scope, primKey);
		}
	}

	public void addResources(
			long companyId, long groupId, String name, boolean portletActions)
		throws PortalException, SystemException {

		addResources(
			companyId, groupId, 0, name, null, portletActions, false, false);
	}

	public void addResources(
			long companyId, long groupId, long userId, String name,
			long primKey, boolean portletActions,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		addResources(
			companyId, groupId, userId, name, String.valueOf(primKey),
			portletActions, addCommunityPermissions, addGuestPermissions);
	}

	public void addResources(
			long companyId, long groupId, long userId, String name,
			String primKey, boolean portletActions,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		if (!PermissionThreadLocal.isAddResource()) {
			return;
		}

		validate(name, portletActions);

		// Company

		addResource(
			companyId, name, ResourceConstants.SCOPE_COMPANY,
			String.valueOf(companyId));

		if (groupId > 0) {
			addResource(
				companyId, name, ResourceConstants.SCOPE_GROUP,
				String.valueOf(groupId));
		}

		if (primKey == null) {
			return;
		}

		// Individual

		Resource resource = addResource(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

		// Permissions

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			addResources_6(
				companyId, groupId, userId, resource, portletActions);
		}
		else {
			addResources_1to5(
				companyId, groupId, userId, resource, portletActions);
		}

		// Community permissions

		if ((groupId > 0) && addCommunityPermissions) {
			addCommunityPermissions(
				companyId, groupId, userId, name, resource, portletActions);
		}

		// Guest permissions

		if (addGuestPermissions) {

			// Don't add guest permissions when you've already added community
			// permissions and the given community is the guest community.

			addGuestPermissions(
				companyId, groupId, userId, name, resource, portletActions);
		}
	}

	public void deleteResource(long resourceId) throws SystemException {
		try {
			Resource resource = resourcePersistence.findByPrimaryKey(
				resourceId);

			deleteResource(resource);
		}
		catch (NoSuchResourceException nsre) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsre);
			}
		}
	}

	public void deleteResource(Resource resource) throws SystemException {

		// Permissions

		List<Permission> permissions = permissionPersistence.findByResourceId(
			resource.getResourceId());

		for (Permission permission : permissions) {
			orgGroupPermissionPersistence.removeByPermissionId(
				permission.getPermissionId());
		}

		permissionPersistence.removeByResourceId(resource.getResourceId());

		// Resource

		resourcePersistence.remove(resource);
	}

	public void deleteResource(
			long companyId, String name, int scope, long primKey)
		throws PortalException, SystemException {

		deleteResource(companyId, name, scope, String.valueOf(primKey));
	}

	public void deleteResource(
			long companyId, String name, int scope, String primKey)
		throws PortalException, SystemException {

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			return;
		}

		try {
			Resource resource = getResource(companyId, name, scope, primKey);

			deleteResource(resource.getResourceId());
		}
		catch (NoSuchResourceException nsre) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsre);
			}
		}
	}

	public void deleteResources(String name) throws SystemException {
		List<Resource> resources = resourceFinder.findByName(name);

		for (Resource resource : resources) {
			deleteResource(resource);
		}
	}

	public long getLatestResourceId() throws SystemException {
		List<Resource> resources = resourcePersistence.findAll(
			0, 1, new ResourceComparator());

		if (resources.size() == 0) {
			return 0;
		}
		else {
			Resource resource = resources.get(0);

			return resource.getResourceId();
		}
	}

	public Resource getResource(long resourceId)
		throws PortalException, SystemException {

		return resourcePersistence.findByPrimaryKey(resourceId);
	}

	public Resource getResource(
			long companyId, String name, int scope, String primKey)
		throws PortalException, SystemException {

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			return getResource_6(companyId, name, scope, primKey);
		}
		else {
			return getResource_1to5(companyId, name, scope, primKey);
		}
	}

	public List<Resource> getResources() throws SystemException {
		return resourcePersistence.findAll();
	}

	public Role getRole(long groupId)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		if (group.isLayout()) {
			Layout layout = layoutLocalService.getLayout(
				group.getClassPK());

			group = layout.getGroup();
		}

		Role role = null;

		if (group.isCommunity() || group.isLayoutPrototype() ||
			group.isLayoutSetPrototype()) {

			role = roleLocalService.getRole(
				group.getCompanyId(), RoleConstants.COMMUNITY_MEMBER);
		}
		else if (group.isCompany()) {
			role = roleLocalService.getRole(
				group.getCompanyId(), RoleConstants.ADMINISTRATOR);
		}
		else if (group.isOrganization()) {
			role = roleLocalService.getRole(
				group.getCompanyId(), RoleConstants.ORGANIZATION_MEMBER);
		}
		else if (group.isUser() || group.isUserGroup()) {
			role = roleLocalService.getRole(
				group.getCompanyId(), RoleConstants.POWER_USER);
		}

		return role;
	}

	public void updateResources(
			long companyId, long groupId, String name, long primKey,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		updateResources(
			companyId, groupId, name, String.valueOf(primKey),
			communityPermissions, guestPermissions);
	}

	public void updateResources(
			long companyId, long groupId, String name, String primKey,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		Resource resource = getResource(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

		if (communityPermissions == null) {
			communityPermissions = new String[0];
		}

		if (guestPermissions == null) {
			guestPermissions = new String[0];
		}

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			updateResources_6(
				companyId, groupId, resource, communityPermissions,
				guestPermissions);
		}
		else {
			updateResources_1to5(
				companyId, groupId, resource, communityPermissions,
				guestPermissions);
		}
	}

	protected void addCommunityPermissions(
			long companyId, long groupId, long userId, String name,
			Resource resource, boolean portletActions)
		throws PortalException, SystemException {

		List<String> actions = null;

		if (portletActions) {
			actions =
				ResourceActionsUtil.getPortletResourceCommunityDefaultActions(
					name);
		}
		else {
			actions =
				ResourceActionsUtil.getModelResourceCommunityDefaultActions(
					name);
		}

		String[] actionIds = actions.toArray(new String[actions.size()]);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			addCommunityPermissions_6(groupId, resource, actionIds);
		}
		else {
			addCommunityPermissions_1to5(
				companyId, groupId, userId, name, resource, portletActions,
				actionIds);
		}
	}

	protected void addCommunityPermissions_1to5(
			long companyId, long groupId, long userId, String name,
			Resource resource, boolean portletActions, String[] actionIds)
		throws PortalException, SystemException {

		long resourceId = resource.getResourceId();
		String primKey = resource.getPrimKey();

		List<Permission> communityPermissionsList =
			permissionLocalService.getPermissions(
				companyId, actionIds, resourceId);

		PermissionsListFilter permissionsListFilter =
			PermissionsListFilterFactory.getInstance();

		communityPermissionsList =
			permissionsListFilter.filterCommunityPermissions(
				companyId, groupId, userId, name, primKey, portletActions,
				communityPermissionsList);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			Role role = getRole(groupId);

			rolePersistence.addPermissions(
				role.getRoleId(), communityPermissionsList);
		}
		else {
			groupPersistence.addPermissions(groupId, communityPermissionsList);
		}
	}

	protected void addCommunityPermissions_6(
			long groupId, Resource resource, String[] actionIds)
		throws PortalException, SystemException {

		Role role = getRole(groupId);

		resourcePermissionLocalService.setResourcePermissions(
			resource.getCompanyId(), resource.getName(), resource.getScope(),
			resource.getPrimKey(), role.getRoleId(), actionIds);
	}

	protected void addGuestPermissions(
			long companyId, long groupId, long userId, String name,
			Resource resource, boolean portletActions)
		throws PortalException, SystemException {

		List<String> actions = null;

		if (portletActions) {
			actions = ResourceActionsUtil.getPortletResourceGuestDefaultActions(
				name);
		}
		else {
			actions = ResourceActionsUtil.getModelResourceGuestDefaultActions(
				name);
		}

		String[] actionIds = actions.toArray(new String[actions.size()]);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			addGuestPermissions_6(companyId, resource, actionIds);
		}
		else {
			addGuestPermissions_1to5(
				companyId, groupId, userId, name, resource, portletActions,
				actionIds);
		}
	}

	protected void addGuestPermissions_1to5(
			long companyId, long groupId, long userId, String name,
			Resource resource, boolean portletActions, String[] actionIds)
		throws PortalException, SystemException {

		List<Permission> guestPermissionsList =
			permissionLocalService.getPermissions(
				companyId, actionIds, resource.getResourceId());

		PermissionsListFilter permissionsListFilter =
			PermissionsListFilterFactory.getInstance();

		guestPermissionsList =
			permissionsListFilter.filterGuestPermissions(
				companyId, groupId, userId, name, resource.getPrimKey(),
				portletActions, guestPermissionsList);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			Role guestRole = roleLocalService.getRole(
				companyId, RoleConstants.GUEST);

			rolePersistence.addPermissions(
				guestRole.getRoleId(), guestPermissionsList);
		}
		else {
			long defaultUserId = userLocalService.getDefaultUserId(companyId);

			userPersistence.addPermissions(defaultUserId, guestPermissionsList);
		}
	}

	protected void addGuestPermissions_6(
			long companyId, Resource resource, String[] actionIds)
		throws PortalException, SystemException {

		Role guestRole = roleLocalService.getRole(
			companyId, RoleConstants.GUEST);

		resourcePermissionLocalService.setResourcePermissions(
			resource.getCompanyId(), resource.getName(), resource.getScope(),
			resource.getPrimKey(), guestRole.getRoleId(), actionIds);
	}

	protected void addModelResources_1to5(
			long companyId, long groupId, long userId, Resource resource,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		long defaultUserId = userLocalService.getDefaultUserId(companyId);

		PermissionsListFilter permissionsListFilter =
			PermissionsListFilterFactory.getInstance();

		List<Permission> permissionsList =
			permissionLocalService.addPermissions(
				companyId, resource.getName(), resource.getResourceId(), false);

		List<Permission> userPermissionsList =
			permissionsListFilter.filterUserPermissions(
				companyId, groupId, userId, resource.getName(),
				resource.getPrimKey(), false, permissionsList);

		filterOwnerPermissions(resource.getName(), userPermissionsList);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {

			// Owner permissions

			Role ownerRole = roleLocalService.getRole(
				companyId, RoleConstants.OWNER);

			rolePersistence.addPermissions(
				ownerRole.getRoleId(), userPermissionsList);
		}
		else {

			// User permissions

			if ((userId > 0) && (userId != defaultUserId)) {
				userPersistence.addPermissions(userId, userPermissionsList);
			}
		}

		// Community permissions

		if (groupId > 0) {
			if (communityPermissions == null) {
				communityPermissions = new String[0];
			}

			List<Permission> communityPermissionsList =
				permissionLocalService.getPermissions(
					companyId, communityPermissions, resource.getResourceId());

			communityPermissionsList =
				permissionsListFilter.filterCommunityPermissions(
					companyId, groupId, userId, resource.getName(),
					resource.getPrimKey(), false, communityPermissionsList);

			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
				Role role = getRole(groupId);

				rolePersistence.addPermissions(
					role.getRoleId(), communityPermissionsList);
			}
			else {
				groupPersistence.addPermissions(
					groupId, communityPermissionsList);
			}
		}

		// Guest permissions

		if (guestPermissions == null) {
			guestPermissions = new String[0];
		}

		List<Permission> guestPermissionsList =
			permissionLocalService.getPermissions(
				companyId, guestPermissions, resource.getResourceId());

		guestPermissionsList = permissionsListFilter.filterGuestPermissions(
			companyId, groupId, userId, resource.getName(),
			resource.getPrimKey(), false, guestPermissionsList);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			Role guestRole = roleLocalService.getRole(
				companyId, RoleConstants.GUEST);

			rolePersistence.addPermissions(
				guestRole.getRoleId(), guestPermissionsList);
		}
		else {
			userPersistence.addPermissions(defaultUserId, guestPermissionsList);
		}
	}

	protected void addModelResources_6(
			long companyId, long groupId, Resource resource,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// Owner permissions

		Role ownerRole = roleLocalService.getRole(
			companyId, RoleConstants.OWNER);

		List<String> actionIds = ResourceActionsUtil.getModelResourceActions(
			resource.getName());

		filterOwnerActions(resource.getName(), actionIds);

		resourcePermissionLocalService.setResourcePermissions(
			resource.getCompanyId(), resource.getName(), resource.getScope(),
			resource.getPrimKey(), ownerRole.getRoleId(),
			actionIds.toArray(new String[actionIds.size()]));

		// Community permissions

		if (groupId > 0) {
			Role role = getRole(groupId);

			if (communityPermissions == null) {
				communityPermissions = new String[0];
			}

			resourcePermissionLocalService.setResourcePermissions(
				resource.getCompanyId(), resource.getName(),
				resource.getScope(), resource.getPrimKey(), role.getRoleId(),
				communityPermissions);
		}

		// Guest permissions

		Role guestRole = roleLocalService.getRole(
			companyId, RoleConstants.GUEST);

		if (guestPermissions == null) {
			guestPermissions = new String[0];
		}

		resourcePermissionLocalService.setResourcePermissions(
			resource.getCompanyId(), resource.getName(), resource.getScope(),
			resource.getPrimKey(), guestRole.getRoleId(), guestPermissions);
	}

	protected Resource addResource_1to5(
			long companyId, String name, int scope, String primKey)
		throws SystemException {

		ResourceCode resourceCode = resourceCodeLocalService.getResourceCode(
			companyId, name, scope);

		long codeId = resourceCode.getCodeId();

		Resource resource = resourcePersistence.fetchByC_P(codeId, primKey);

		if (resource == null) {
			long resourceId = counterLocalService.increment(
				Resource.class.getName());

			resource = resourcePersistence.create(resourceId);

			resource.setCodeId(codeId);
			resource.setPrimKey(primKey);

			try {
				resourcePersistence.update(resource, false);
			}
			catch (SystemException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Add failed, fetch {codeId=" + codeId + ", primKey=" +
							primKey + "}");
				}

				resource = resourcePersistence.fetchByC_P(
					codeId, primKey, false);

				if (resource == null) {
					throw se;
				}
			}
		}

		return resource;
	}

	protected Resource addResource_6(
		long companyId, String name, int scope, String primKey) {

		Resource resource = new ResourceImpl();

		resource.setCompanyId(companyId);
		resource.setName(name);
		resource.setScope(scope);
		resource.setPrimKey(primKey);

		return resource;
	}

	protected void addResources_1to5(
			long companyId, long groupId, long userId, Resource resource,
			boolean portletActions)
		throws PortalException, SystemException {

		List<Permission> permissionsList =
			permissionLocalService.addPermissions(
				companyId, resource.getName(), resource.getResourceId(),
				portletActions);

		PermissionsListFilter permissionsListFilter =
			PermissionsListFilterFactory.getInstance();

		List<Permission> userPermissionsList =
			permissionsListFilter.filterUserPermissions(
				companyId, groupId, userId, resource.getName(),
				resource.getPrimKey(), portletActions, permissionsList);

		filterOwnerPermissions(resource.getName(), userPermissionsList);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {

			// Owner permissions

			Role ownerRole = roleLocalService.getRole(
				companyId, RoleConstants.OWNER);

			rolePersistence.addPermissions(
				ownerRole.getRoleId(), userPermissionsList);
		}
		else {

			// User permissions

			long defaultUserId = userLocalService.getDefaultUserId(companyId);

			if ((userId > 0) && (userId != defaultUserId)) {
				userPersistence.addPermissions(userId, userPermissionsList);
			}
		}
	}

	protected void addResources_6(
			long companyId, long groupId, long userId, Resource resource,
			boolean portletActions)
		throws PortalException, SystemException {

		List<String> actionIds = null;

		if (portletActions) {
			actionIds = ResourceActionsUtil.getPortletResourceActions(
				resource.getName());
		}
		else {
			actionIds = ResourceActionsUtil.getModelResourceActions(
				resource.getName());

			filterOwnerActions(resource.getName(), actionIds);
		}

		Role role = roleLocalService.getRole(companyId, RoleConstants.OWNER);

		resourcePermissionLocalService.setResourcePermissions(
			resource.getCompanyId(), resource.getName(), resource.getScope(),
			resource.getPrimKey(), role.getRoleId(),
			actionIds.toArray(new String[actionIds.size()]));
	}

	protected void filterOwnerActions(String name, List<String> actionIds) {
		List<String> defaultOwnerActions =
			ResourceActionsUtil.getModelResourceOwnerDefaultActions(name);

		if (defaultOwnerActions.isEmpty()) {
			return;
		}

		Iterator<String> itr = actionIds.iterator();

		while (itr.hasNext()) {
			String actionId = itr.next();

			if (!defaultOwnerActions.contains(actionId)) {
				itr.remove();
			}
		}
	}

	protected void filterOwnerPermissions(
		String name, List<Permission> permissions) {

		List<String> defaultOwnerActions =
			ResourceActionsUtil.getModelResourceOwnerDefaultActions(name);

		if (defaultOwnerActions.isEmpty()) {
			return;
		}

		Iterator<Permission> itr = permissions.iterator();

		while (itr.hasNext()) {
			Permission permission = itr.next();

			String actionId = permission.getActionId();

			if (!defaultOwnerActions.contains(actionId)) {
				itr.remove();
			}
		}
	}

	protected Resource getResource_1to5(
			long companyId, String name, int scope, String primKey)
		throws PortalException, SystemException {

		ResourceCode resourceCode = resourceCodeLocalService.getResourceCode(
			companyId, name, scope);

		return resourcePersistence.findByC_P(resourceCode.getCodeId(), primKey);
	}

	protected Resource getResource_6(
		long companyId, String name, int scope, String primKey) {

		Resource resource = new ResourceImpl();

		resource.setCompanyId(companyId);
		resource.setName(name);
		resource.setScope(scope);
		resource.setPrimKey(primKey);

		return resource;
	}

	protected void updateResources_1to5(
			long companyId, long groupId, Resource resource,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		Role role = getRole(groupId);

		permissionLocalService.setRolePermissions(
			role.getRoleId(), communityPermissions, resource.getResourceId());

		role = roleLocalService.getRole(companyId, RoleConstants.GUEST);

		permissionLocalService.setRolePermissions(
			role.getRoleId(), guestPermissions, resource.getResourceId());
	}

	protected void updateResources_6(
			long companyId, long groupId, Resource resource,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		Role role = getRole(groupId);

		resourcePermissionLocalService.setResourcePermissions(
			resource.getCompanyId(), resource.getName(), resource.getScope(),
			resource.getPrimKey(), role.getRoleId(), communityPermissions);

		role = roleLocalService.getRole(companyId, RoleConstants.GUEST);

		resourcePermissionLocalService.setResourcePermissions(
			resource.getCompanyId(), resource.getName(), resource.getScope(),
			resource.getPrimKey(), role.getRoleId(), guestPermissions);
	}

	protected void validate(String name, boolean portletActions)
		throws PortalException {

		List<String> actions = null;

		if (portletActions) {
			actions = ResourceActionsUtil.getPortletResourceActions(name);
		}
		else {
			actions = ResourceActionsUtil.getModelResourceActions(name);
		}

		if (actions.size() == 0) {
			throw new ResourceActionsException(
				"There are no actions associated with the resource " + name);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ResourceLocalServiceImpl.class);

}