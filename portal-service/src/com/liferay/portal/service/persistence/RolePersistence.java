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
 * <a href="RolePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface RolePersistence extends BasePersistence {
	public void cacheResult(com.liferay.portal.model.Role role);

	public void cacheResult(java.util.List<com.liferay.portal.model.Role> roles);

	public void clearCache();

	public com.liferay.portal.model.Role create(long roleId);

	public com.liferay.portal.model.Role remove(long roleId)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role remove(
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(Role role, boolean merge)</code>.
	 */
	public com.liferay.portal.model.Role update(
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        role the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when role is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portal.model.Role update(
		com.liferay.portal.model.Role role, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role updateImpl(
		com.liferay.portal.model.Role role, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role findByPrimaryKey(long roleId)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role fetchByPrimaryKey(long roleId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role[] findByCompanyId_PrevAndNext(
		long roleId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role findBySubtype(java.lang.String subtype)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role fetchBySubtype(
		java.lang.String subtype) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role fetchBySubtype(
		java.lang.String subtype, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role fetchByC_N(long companyId,
		java.lang.String name) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role fetchByC_N(long companyId,
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> findByT_S(int type,
		java.lang.String subtype) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> findByT_S(int type,
		java.lang.String subtype, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> findByT_S(int type,
		java.lang.String subtype, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role findByT_S_First(int type,
		java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role findByT_S_Last(int type,
		java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role[] findByT_S_PrevAndNext(long roleId,
		int type, java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role findByC_C_C(long companyId,
		long classNameId, long classPK)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role fetchByC_C_C(long companyId,
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role fetchByC_C_C(long companyId,
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> findAll(int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeBySubtype(java.lang.String subtype)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.SystemException;

	public void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.SystemException;

	public void removeByT_S(int type, java.lang.String subtype)
		throws com.liferay.portal.SystemException;

	public void removeByC_C_C(long companyId, long classNameId, long classPK)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countBySubtype(java.lang.String subtype)
		throws com.liferay.portal.SystemException;

	public int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public int countByT_S(int type, java.lang.String subtype)
		throws com.liferay.portal.SystemException;

	public int countByC_C_C(long companyId, long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk,
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int getGroupsSize(long pk) throws com.liferay.portal.SystemException;

	public boolean containsGroup(long pk, long groupPK)
		throws com.liferay.portal.SystemException;

	public boolean containsGroups(long pk)
		throws com.liferay.portal.SystemException;

	public void addGroup(long pk, long groupPK)
		throws com.liferay.portal.SystemException;

	public void addGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.SystemException;

	public void addGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.SystemException;

	public void addGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.SystemException;

	public void clearGroups(long pk) throws com.liferay.portal.SystemException;

	public void removeGroup(long pk, long groupPK)
		throws com.liferay.portal.SystemException;

	public void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.SystemException;

	public void removeGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.SystemException;

	public void removeGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.SystemException;

	public void setGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.SystemException;

	public void setGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long pk) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long pk, int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int getPermissionsSize(long pk)
		throws com.liferay.portal.SystemException;

	public boolean containsPermission(long pk, long permissionPK)
		throws com.liferay.portal.SystemException;

	public boolean containsPermissions(long pk)
		throws com.liferay.portal.SystemException;

	public void addPermission(long pk, long permissionPK)
		throws com.liferay.portal.SystemException;

	public void addPermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.SystemException;

	public void addPermissions(long pk, long[] permissionPKs)
		throws com.liferay.portal.SystemException;

	public void addPermissions(long pk,
		java.util.List<com.liferay.portal.model.Permission> permissions)
		throws com.liferay.portal.SystemException;

	public void clearPermissions(long pk)
		throws com.liferay.portal.SystemException;

	public void removePermission(long pk, long permissionPK)
		throws com.liferay.portal.SystemException;

	public void removePermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.SystemException;

	public void removePermissions(long pk, long[] permissionPKs)
		throws com.liferay.portal.SystemException;

	public void removePermissions(long pk,
		java.util.List<com.liferay.portal.model.Permission> permissions)
		throws com.liferay.portal.SystemException;

	public void setPermissions(long pk, long[] permissionPKs)
		throws com.liferay.portal.SystemException;

	public void setPermissions(long pk,
		java.util.List<com.liferay.portal.model.Permission> permissions)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> getUsers(long pk)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int getUsersSize(long pk) throws com.liferay.portal.SystemException;

	public boolean containsUser(long pk, long userPK)
		throws com.liferay.portal.SystemException;

	public boolean containsUsers(long pk)
		throws com.liferay.portal.SystemException;

	public void addUser(long pk, long userPK)
		throws com.liferay.portal.SystemException;

	public void addUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException;

	public void addUsers(long pk, long[] userPKs)
		throws com.liferay.portal.SystemException;

	public void addUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.SystemException;

	public void clearUsers(long pk) throws com.liferay.portal.SystemException;

	public void removeUser(long pk, long userPK)
		throws com.liferay.portal.SystemException;

	public void removeUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException;

	public void removeUsers(long pk, long[] userPKs)
		throws com.liferay.portal.SystemException;

	public void removeUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.SystemException;

	public void setUsers(long pk, long[] userPKs)
		throws com.liferay.portal.SystemException;

	public void setUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.SystemException;
}