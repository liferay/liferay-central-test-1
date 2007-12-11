/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.permission;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerBag;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.PermissionServiceUtil;
import com.liferay.portal.service.ResourceServiceUtil;
import com.liferay.portal.service.RoleServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.rmi.RemoteException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="PermissionCheckerBagImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PermissionCheckerBagImpl implements PermissionCheckerBag {

	public PermissionCheckerBagImpl() {
	}

	public PermissionCheckerBagImpl(
		long userId, List userGroups, List userOrgs, List userOrgGroups,
		List userUserGroupGroups, List groups, List roles) {

		_userId = userId;
		_userGroups = userGroups;
		_userOrgs = userOrgs;
		_userOrgGroups = userOrgGroups;
		_userUserGroupGroups = userUserGroupGroups;
		_groups = groups;
		_roles = roles;
	}

	public List getUserGroups() {
		return _userGroups;
	}

	public List getUserOrgs() {
		return _userOrgs;
	}

	public List getUserOrgGroups() {
		return _userOrgGroups;
	}

	public List getUserUserGroupGroups() {
		return _userUserGroupGroups;
	}

	public List getGroups() {
		return _groups;
	}

	public List getRoles() {
		return _roles;
	}

	public boolean isCompanyAdmin(long companyId) throws Exception {
		String key = String.valueOf(companyId);

		Boolean value = (Boolean)_isCompanyAdmin.get(key);

		if (value == null) {
			boolean hasAdminRole = RoleServiceUtil.hasUserRole(
				_userId, companyId, RoleImpl.ADMINISTRATOR, true);

			value = Boolean.valueOf(hasAdminRole);

			_isCompanyAdmin.put(key, value);
		}

		return value.booleanValue();
	}

	public boolean isCommunityAdmin(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String name)
		throws Exception {

		String key =
			companyId + StringPool.PIPE + groupId + StringPool.PIPE + name;

		Boolean value = (Boolean)_isCommunityAdmin.get(key);

		if (value == null) {
			value = Boolean.valueOf(
				isCommunityAdminImpl(
					permissionChecker, companyId, groupId, name));

			_isCommunityAdmin.put(key, value);
		}

		return value.booleanValue();
	}

	protected boolean isCommunityAdminImpl(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String name)
		throws PortalException, RemoteException, SystemException {

		if (groupId <= 0) {
			return false;
		}

		try {
			Resource resource = ResourceServiceUtil.getResource(
				companyId, Group.class.getName(), ResourceImpl.SCOPE_INDIVIDUAL,
				String.valueOf(groupId));

			if (PermissionServiceUtil.hasUserPermission(
					_userId, ActionKeys.ADMINISTRATE,
					resource.getResourceId())) {

				return true;
			}

			if (permissionChecker.hasUserPermission(
					groupId, Group.class.getName(), String.valueOf(groupId),
					ActionKeys.ADMINISTRATE, false)) {

				return true;
			}
		}
		catch (NoSuchResourceException nsre) {
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (RemoteException re) {
			throw re;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		try {
			Group group = GroupServiceUtil.getGroup(groupId);

			long userClassNameId = PortalUtil.getClassNameId(User.class);

			if ((group.getClassNameId() == userClassNameId) &&
				(group.getClassPK() == _userId)) {

				return true;
			}
		}
		catch (NoSuchGroupException nsge) {
		}

		return false;
	}

	private long _userId;
	private List _userGroups;
	private List _userOrgs;
	private List _userOrgGroups;
	private List _userUserGroupGroups;
	private List _groups;
	private List _roles;
	private Map _isCompanyAdmin = new HashMap();
	private Map _isCommunityAdmin = new HashMap();

}