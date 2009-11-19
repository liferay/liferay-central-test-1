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

package com.liferay.portal.service;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;

/**
 * <a href="UserGroupGroupRoleLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * {@link
 * com.liferay.portal.service.impl.UserGroupGroupRoleLocalServiceImpl}}.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupGroupRoleLocalServiceUtil
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface UserGroupGroupRoleLocalService {
	public com.liferay.portal.model.UserGroupGroupRole addUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole createUserGroupGroupRole(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK);

	public void deleteUserGroupGroupRole(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.UserGroupGroupRole getUserGroupGroupRole(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRoles(
		int start, int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserGroupGroupRolesCount()
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole updateUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole updateUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole,
		boolean merge) throws com.liferay.portal.SystemException;

	public void addUserGroupGroupRoles(long userGroupId, long groupId,
		long[] roleIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void addUserGroupGroupRoles(long[] userGroupIds, long groupId,
		long roleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteUserGroupGroupRoles(long userGroupId, long groupId,
		long[] roleIds) throws com.liferay.portal.SystemException;

	public void deleteUserGroupGroupRoles(long userGroupId, long[] groupIds)
		throws com.liferay.portal.SystemException;

	public void deleteUserGroupGroupRoles(long[] userGroupIds, long groupId)
		throws com.liferay.portal.SystemException;

	public void deleteUserGroupGroupRoles(long[] userGroupIds, long groupId,
		long roleId) throws com.liferay.portal.SystemException;

	public void deleteUserGroupGroupRolesByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void deleteUserGroupGroupRolesByRoleId(long roleId)
		throws com.liferay.portal.SystemException;

	public void deleteUserGroupGroupRolesByUserGroupId(long userGroupId)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRoles(
		long userGroupId) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRoles(
		long userGroupId, long groupId)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRolesByGroupAndRole(
		long groupId, long roleId) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserGroupGroupRole(long userGroupId, long groupId,
		long roleId) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserGroupGroupRole(long userGroupId, long groupId,
		java.lang.String roleName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;
}