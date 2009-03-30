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

package com.liferay.portal.service.persistence;

/**
 * <a href="GroupUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class GroupUtil {
	public static void cacheResult(com.liferay.portal.model.Group group) {
		getPersistence().cacheResult(group);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Group> groups) {
		getPersistence().cacheResult(groups);
	}

	public static com.liferay.portal.model.Group create(long groupId) {
		return getPersistence().create(groupId);
	}

	public static com.liferay.portal.model.Group remove(long groupId)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(groupId);
	}

	public static com.liferay.portal.model.Group remove(
		com.liferay.portal.model.Group group)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(group);
	}

	/**
	 * @deprecated Use <code>update(Group group, boolean merge)</code>.
	 */
	public static com.liferay.portal.model.Group update(
		com.liferay.portal.model.Group group)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(group);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        group the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when group is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portal.model.Group update(
		com.liferay.portal.model.Group group, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(group, merge);
	}

	public static com.liferay.portal.model.Group updateImpl(
		com.liferay.portal.model.Group group, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(group, merge);
	}

	public static com.liferay.portal.model.Group findByPrimaryKey(long groupId)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(groupId);
	}

	public static com.liferay.portal.model.Group fetchByPrimaryKey(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(groupId);
	}

	public static com.liferay.portal.model.Group findByLiveGroupId(
		long liveGroupId)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.SystemException {
		return getPersistence().findByLiveGroupId(liveGroupId);
	}

	public static com.liferay.portal.model.Group fetchByLiveGroupId(
		long liveGroupId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByLiveGroupId(liveGroupId);
	}

	public static com.liferay.portal.model.Group findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_N(companyId, name);
	}

	public static com.liferay.portal.model.Group fetchByC_N(long companyId,
		java.lang.String name) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_N(companyId, name);
	}

	public static com.liferay.portal.model.Group findByC_F(long companyId,
		java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_F(companyId, friendlyURL);
	}

	public static com.liferay.portal.model.Group fetchByC_F(long companyId,
		java.lang.String friendlyURL) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_F(companyId, friendlyURL);
	}

	public static java.util.List<com.liferay.portal.model.Group> findByT_A(
		int type, boolean active) throws com.liferay.portal.SystemException {
		return getPersistence().findByT_A(type, active);
	}

	public static java.util.List<com.liferay.portal.model.Group> findByT_A(
		int type, boolean active, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByT_A(type, active, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Group> findByT_A(
		int type, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByT_A(type, active, start, end, obc);
	}

	public static com.liferay.portal.model.Group findByT_A_First(int type,
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.SystemException {
		return getPersistence().findByT_A_First(type, active, obc);
	}

	public static com.liferay.portal.model.Group findByT_A_Last(int type,
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.SystemException {
		return getPersistence().findByT_A_Last(type, active, obc);
	}

	public static com.liferay.portal.model.Group[] findByT_A_PrevAndNext(
		long groupId, int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.SystemException {
		return getPersistence().findByT_A_PrevAndNext(groupId, type, active, obc);
	}

	public static com.liferay.portal.model.Group findByC_C_C(long companyId,
		long classNameId, long classPK)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, classNameId, classPK);
	}

	public static com.liferay.portal.model.Group fetchByC_C_C(long companyId,
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_C_C(companyId, classNameId, classPK);
	}

	public static com.liferay.portal.model.Group findByC_L_N(long companyId,
		long liveGroupId, java.lang.String name)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_L_N(companyId, liveGroupId, name);
	}

	public static com.liferay.portal.model.Group fetchByC_L_N(long companyId,
		long liveGroupId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_L_N(companyId, liveGroupId, name);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Group> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Group> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Group> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByLiveGroupId(long liveGroupId)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.SystemException {
		getPersistence().removeByLiveGroupId(liveGroupId);
	}

	public static void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.SystemException {
		getPersistence().removeByC_N(companyId, name);
	}

	public static void removeByC_F(long companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.SystemException {
		getPersistence().removeByC_F(companyId, friendlyURL);
	}

	public static void removeByT_A(int type, boolean active)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByT_A(type, active);
	}

	public static void removeByC_C_C(long companyId, long classNameId,
		long classPK)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.SystemException {
		getPersistence().removeByC_C_C(companyId, classNameId, classPK);
	}

	public static void removeByC_L_N(long companyId, long liveGroupId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.SystemException {
		getPersistence().removeByC_L_N(companyId, liveGroupId, name);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByLiveGroupId(long liveGroupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByLiveGroupId(liveGroupId);
	}

	public static int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_N(companyId, name);
	}

	public static int countByC_F(long companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_F(companyId, friendlyURL);
	}

	public static int countByT_A(int type, boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByT_A(type, active);
	}

	public static int countByC_C_C(long companyId, long classNameId,
		long classPK) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C_C(companyId, classNameId, classPK);
	}

	public static int countByC_L_N(long companyId, long liveGroupId,
		java.lang.String name) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_L_N(companyId, liveGroupId, name);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getOrganizations(pk);
	}

	public static java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getOrganizations(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getOrganizations(pk, start, end, obc);
	}

	public static int getOrganizationsSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getOrganizationsSize(pk);
	}

	public static boolean containsOrganization(long pk, long organizationPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsOrganization(pk, organizationPK);
	}

	public static boolean containsOrganizations(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsOrganizations(pk);
	}

	public static void addOrganization(long pk, long organizationPK)
		throws com.liferay.portal.SystemException {
		getPersistence().addOrganization(pk, organizationPK);
	}

	public static void addOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.SystemException {
		getPersistence().addOrganization(pk, organization);
	}

	public static void addOrganizations(long pk, long[] organizationPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().addOrganizations(pk, organizationPKs);
	}

	public static void addOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.SystemException {
		getPersistence().addOrganizations(pk, organizations);
	}

	public static void clearOrganizations(long pk)
		throws com.liferay.portal.SystemException {
		getPersistence().clearOrganizations(pk);
	}

	public static void removeOrganization(long pk, long organizationPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeOrganization(pk, organizationPK);
	}

	public static void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.SystemException {
		getPersistence().removeOrganization(pk, organization);
	}

	public static void removeOrganizations(long pk, long[] organizationPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().removeOrganizations(pk, organizationPKs);
	}

	public static void removeOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.SystemException {
		getPersistence().removeOrganizations(pk, organizations);
	}

	public static void setOrganizations(long pk, long[] organizationPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().setOrganizations(pk, organizationPKs);
	}

	public static void setOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.SystemException {
		getPersistence().setOrganizations(pk, organizations);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getPermissions(pk);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getPermissions(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getPermissions(pk, start, end, obc);
	}

	public static int getPermissionsSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getPermissionsSize(pk);
	}

	public static boolean containsPermission(long pk, long permissionPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsPermission(pk, permissionPK);
	}

	public static boolean containsPermissions(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsPermissions(pk);
	}

	public static void addPermission(long pk, long permissionPK)
		throws com.liferay.portal.SystemException {
		getPersistence().addPermission(pk, permissionPK);
	}

	public static void addPermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.SystemException {
		getPersistence().addPermission(pk, permission);
	}

	public static void addPermissions(long pk, long[] permissionPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().addPermissions(pk, permissionPKs);
	}

	public static void addPermissions(long pk,
		java.util.List<com.liferay.portal.model.Permission> permissions)
		throws com.liferay.portal.SystemException {
		getPersistence().addPermissions(pk, permissions);
	}

	public static void clearPermissions(long pk)
		throws com.liferay.portal.SystemException {
		getPersistence().clearPermissions(pk);
	}

	public static void removePermission(long pk, long permissionPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removePermission(pk, permissionPK);
	}

	public static void removePermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.SystemException {
		getPersistence().removePermission(pk, permission);
	}

	public static void removePermissions(long pk, long[] permissionPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().removePermissions(pk, permissionPKs);
	}

	public static void removePermissions(long pk,
		java.util.List<com.liferay.portal.model.Permission> permissions)
		throws com.liferay.portal.SystemException {
		getPersistence().removePermissions(pk, permissions);
	}

	public static void setPermissions(long pk, long[] permissionPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().setPermissions(pk, permissionPKs);
	}

	public static void setPermissions(long pk,
		java.util.List<com.liferay.portal.model.Permission> permissions)
		throws com.liferay.portal.SystemException {
		getPersistence().setPermissions(pk, permissions);
	}

	public static java.util.List<com.liferay.portal.model.Role> getRoles(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getRoles(pk);
	}

	public static java.util.List<com.liferay.portal.model.Role> getRoles(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getRoles(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Role> getRoles(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getRoles(pk, start, end, obc);
	}

	public static int getRolesSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getRolesSize(pk);
	}

	public static boolean containsRole(long pk, long rolePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsRole(pk, rolePK);
	}

	public static boolean containsRoles(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsRoles(pk);
	}

	public static void addRole(long pk, long rolePK)
		throws com.liferay.portal.SystemException {
		getPersistence().addRole(pk, rolePK);
	}

	public static void addRole(long pk, com.liferay.portal.model.Role role)
		throws com.liferay.portal.SystemException {
		getPersistence().addRole(pk, role);
	}

	public static void addRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.SystemException {
		getPersistence().addRoles(pk, rolePKs);
	}

	public static void addRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.SystemException {
		getPersistence().addRoles(pk, roles);
	}

	public static void clearRoles(long pk)
		throws com.liferay.portal.SystemException {
		getPersistence().clearRoles(pk);
	}

	public static void removeRole(long pk, long rolePK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeRole(pk, rolePK);
	}

	public static void removeRole(long pk, com.liferay.portal.model.Role role)
		throws com.liferay.portal.SystemException {
		getPersistence().removeRole(pk, role);
	}

	public static void removeRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.SystemException {
		getPersistence().removeRoles(pk, rolePKs);
	}

	public static void removeRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.SystemException {
		getPersistence().removeRoles(pk, roles);
	}

	public static void setRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.SystemException {
		getPersistence().setRoles(pk, rolePKs);
	}

	public static void setRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.SystemException {
		getPersistence().setRoles(pk, roles);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getUserGroups(pk);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getUserGroups(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getUserGroups(pk, start, end, obc);
	}

	public static int getUserGroupsSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getUserGroupsSize(pk);
	}

	public static boolean containsUserGroup(long pk, long userGroupPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsUserGroup(pk, userGroupPK);
	}

	public static boolean containsUserGroups(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsUserGroups(pk);
	}

	public static void addUserGroup(long pk, long userGroupPK)
		throws com.liferay.portal.SystemException {
		getPersistence().addUserGroup(pk, userGroupPK);
	}

	public static void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.SystemException {
		getPersistence().addUserGroup(pk, userGroup);
	}

	public static void addUserGroups(long pk, long[] userGroupPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().addUserGroups(pk, userGroupPKs);
	}

	public static void addUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.SystemException {
		getPersistence().addUserGroups(pk, userGroups);
	}

	public static void clearUserGroups(long pk)
		throws com.liferay.portal.SystemException {
		getPersistence().clearUserGroups(pk);
	}

	public static void removeUserGroup(long pk, long userGroupPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeUserGroup(pk, userGroupPK);
	}

	public static void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.SystemException {
		getPersistence().removeUserGroup(pk, userGroup);
	}

	public static void removeUserGroups(long pk, long[] userGroupPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().removeUserGroups(pk, userGroupPKs);
	}

	public static void removeUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.SystemException {
		getPersistence().removeUserGroups(pk, userGroups);
	}

	public static void setUserGroups(long pk, long[] userGroupPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().setUserGroups(pk, userGroupPKs);
	}

	public static void setUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.SystemException {
		getPersistence().setUserGroups(pk, userGroups);
	}

	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk);
	}

	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk, start, end, obc);
	}

	public static int getUsersSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getUsersSize(pk);
	}

	public static boolean containsUser(long pk, long userPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsUser(pk, userPK);
	}

	public static boolean containsUsers(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsUsers(pk);
	}

	public static void addUser(long pk, long userPK)
		throws com.liferay.portal.SystemException {
		getPersistence().addUser(pk, userPK);
	}

	public static void addUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException {
		getPersistence().addUser(pk, user);
	}

	public static void addUsers(long pk, long[] userPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().addUsers(pk, userPKs);
	}

	public static void addUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.SystemException {
		getPersistence().addUsers(pk, users);
	}

	public static void clearUsers(long pk)
		throws com.liferay.portal.SystemException {
		getPersistence().clearUsers(pk);
	}

	public static void removeUser(long pk, long userPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeUser(pk, userPK);
	}

	public static void removeUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException {
		getPersistence().removeUser(pk, user);
	}

	public static void removeUsers(long pk, long[] userPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().removeUsers(pk, userPKs);
	}

	public static void removeUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.SystemException {
		getPersistence().removeUsers(pk, users);
	}

	public static void setUsers(long pk, long[] userPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().setUsers(pk, userPKs);
	}

	public static void setUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.SystemException {
		getPersistence().setUsers(pk, users);
	}

	public static GroupPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(GroupPersistence persistence) {
		_persistence = persistence;
	}

	private static GroupPersistence _persistence;
}