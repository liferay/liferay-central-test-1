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

package com.liferay.portal.service;


/**
 * <a href="UserLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link UserLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserLocalService
 * @generated
 */
public class UserLocalServiceWrapper implements UserLocalService {
	public UserLocalServiceWrapper(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	public com.liferay.portal.model.User addUser(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.addUser(user);
	}

	public com.liferay.portal.model.User createUser(long userId) {
		return _userLocalService.createUser(userId);
	}

	public void deleteUser(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteUser(userId);
	}

	public void deleteUser(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteUser(user);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.User getUser(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUser(userId);
	}

	public java.util.List<com.liferay.portal.model.User> getUsers(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUsers(start, end);
	}

	public int getUsersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUsersCount();
	}

	public com.liferay.portal.model.User updateUser(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateUser(user);
	}

	public com.liferay.portal.model.User updateUser(
		com.liferay.portal.model.User user, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateUser(user, merge);
	}

	public void addDefaultGroups(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addDefaultGroups(userId);
	}

	public void addDefaultRoles(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addDefaultRoles(userId);
	}

	public void addDefaultUserGroups(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addDefaultUserGroups(userId);
	}

	public void addGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addGroupUsers(groupId, userIds);
	}

	public void addOrganizationUsers(long organizationId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addOrganizationUsers(organizationId, userIds);
	}

	public void addPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public void addRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addRoleUsers(roleId, userIds);
	}

	public void addTeamUsers(long teamId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addTeamUsers(teamId, userIds);
	}

	public com.liferay.portal.model.User addUser(long creatorUserId,
		long companyId, boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.String openId, java.util.Locale locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds, long[] userGroupIds, boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.addUser(creatorUserId, companyId,
			autoPassword, password1, password2, autoScreenName, screenName,
			emailAddress, openId, locale, firstName, middleName, lastName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
			sendEmail, serviceContext);
	}

	public void addUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.addUserGroupUsers(userGroupId, userIds);
	}

	public int authenticateByEmailAddress(long companyId,
		java.lang.String emailAddress, java.lang.String password,
		java.util.Map<String, String[]> headerMap,
		java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.authenticateByEmailAddress(companyId,
			emailAddress, password, headerMap, parameterMap);
	}

	public int authenticateByScreenName(long companyId,
		java.lang.String screenName, java.lang.String password,
		java.util.Map<String, String[]> headerMap,
		java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.authenticateByScreenName(companyId,
			screenName, password, headerMap, parameterMap);
	}

	public int authenticateByUserId(long companyId, long userId,
		java.lang.String password, java.util.Map<String, String[]> headerMap,
		java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.authenticateByUserId(companyId, userId,
			password, headerMap, parameterMap);
	}

	public long authenticateForBasic(long companyId, java.lang.String authType,
		java.lang.String login, java.lang.String password)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.authenticateForBasic(companyId, authType,
			login, password);
	}

	public boolean authenticateForJAAS(long userId, java.lang.String encPassword) {
		return _userLocalService.authenticateForJAAS(userId, encPassword);
	}

	public void checkLockout(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.checkLockout(user);
	}

	public void checkLoginFailure(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.checkLoginFailure(user);
	}

	public void checkLoginFailureByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.checkLoginFailureByEmailAddress(companyId,
			emailAddress);
	}

	public void checkLoginFailureById(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.checkLoginFailureById(userId);
	}

	public void checkLoginFailureByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.checkLoginFailureByScreenName(companyId, screenName);
	}

	public void checkPasswordExpired(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.checkPasswordExpired(user);
	}

	public com.liferay.portal.kernel.util.KeyValuePair decryptUserId(
		long companyId, java.lang.String name, java.lang.String password)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.decryptUserId(companyId, name, password);
	}

	public void deletePortrait(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deletePortrait(userId);
	}

	public void deleteRoleUser(long roleId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.deleteRoleUser(roleId, userId);
	}

	public java.lang.String encryptUserId(java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.encryptUserId(name);
	}

	public java.util.List<com.liferay.portal.model.User> getCompanyUsers(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getCompanyUsers(companyId, start, end);
	}

	public int getCompanyUsersCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getCompanyUsersCount(companyId);
	}

	public com.liferay.portal.model.User getDefaultUser(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getDefaultUser(companyId);
	}

	public long getDefaultUserId(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getDefaultUserId(companyId);
	}

	public long[] getGroupUserIds(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getGroupUserIds(groupId);
	}

	public java.util.List<com.liferay.portal.model.User> getGroupUsers(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getGroupUsers(groupId);
	}

	public int getGroupUsersCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getGroupUsersCount(groupId);
	}

	public int getGroupUsersCount(long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getGroupUsersCount(groupId, active);
	}

	public java.util.List<com.liferay.portal.model.User> getNoAnnouncementsDeliveries(
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getNoAnnouncementsDeliveries(type);
	}

	public java.util.List<com.liferay.portal.model.User> getNoContacts()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getNoContacts();
	}

	public java.util.List<com.liferay.portal.model.User> getNoGroups()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getNoGroups();
	}

	public long[] getOrganizationUserIds(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getOrganizationUserIds(organizationId);
	}

	public java.util.List<com.liferay.portal.model.User> getOrganizationUsers(
		long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getOrganizationUsers(organizationId);
	}

	public int getOrganizationUsersCount(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getOrganizationUsersCount(organizationId);
	}

	public int getOrganizationUsersCount(long organizationId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getOrganizationUsersCount(organizationId,
			active);
	}

	public long[] getRoleUserIds(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getRoleUserIds(roleId);
	}

	public java.util.List<com.liferay.portal.model.User> getRoleUsers(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getRoleUsers(roleId);
	}

	public java.util.List<com.liferay.portal.model.User> getRoleUsers(
		long roleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getRoleUsers(roleId, start, end);
	}

	public int getRoleUsersCount(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getRoleUsersCount(roleId);
	}

	public int getRoleUsersCount(long roleId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getRoleUsersCount(roleId, active);
	}

	public java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getSocialUsers(userId, type, start, end, obc);
	}

	public java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getSocialUsers(userId, start, end, obc);
	}

	public java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId1, long userId2, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getSocialUsers(userId1, userId2, type, start,
			end, obc);
	}

	public java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId1, long userId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getSocialUsers(userId1, userId2, start, end,
			obc);
	}

	public int getSocialUsersCount(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getSocialUsersCount(userId);
	}

	public int getSocialUsersCount(long userId, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getSocialUsersCount(userId, type);
	}

	public int getSocialUsersCount(long userId1, long userId2)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getSocialUsersCount(userId1, userId2);
	}

	public int getSocialUsersCount(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getSocialUsersCount(userId1, userId2, type);
	}

	public com.liferay.portal.model.User getUserByContactId(long contactId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserByContactId(contactId);
	}

	public com.liferay.portal.model.User getUserByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserByEmailAddress(companyId, emailAddress);
	}

	public com.liferay.portal.model.User getUserById(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserById(userId);
	}

	public com.liferay.portal.model.User getUserById(long companyId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserById(companyId, userId);
	}

	public com.liferay.portal.model.User getUserByOpenId(
		java.lang.String openId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserByOpenId(openId);
	}

	public com.liferay.portal.model.User getUserByPortraitId(long portraitId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserByPortraitId(portraitId);
	}

	public com.liferay.portal.model.User getUserByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserByScreenName(companyId, screenName);
	}

	public com.liferay.portal.model.User getUserByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserByUuid(uuid);
	}

	public java.util.List<com.liferay.portal.model.User> getUserGroupUsers(
		long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserGroupUsers(userGroupId);
	}

	public int getUserGroupUsersCount(long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserGroupUsersCount(userGroupId);
	}

	public int getUserGroupUsersCount(long userGroupId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserGroupUsersCount(userGroupId, active);
	}

	public long getUserIdByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserIdByEmailAddress(companyId, emailAddress);
	}

	public long getUserIdByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.getUserIdByScreenName(companyId, screenName);
	}

	public boolean hasGroupUser(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasGroupUser(groupId, userId);
	}

	public boolean hasOrganizationUser(long organizationId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasOrganizationUser(organizationId, userId);
	}

	public boolean hasPasswordPolicyUser(long passwordPolicyId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasPasswordPolicyUser(passwordPolicyId, userId);
	}

	public boolean hasRoleUser(long roleId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasRoleUser(roleId, userId);
	}

	public boolean hasRoleUser(long companyId, java.lang.String name,
		long userId, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasRoleUser(companyId, name, userId, inherited);
	}

	public boolean hasTeamUser(long teamId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasTeamUser(teamId, userId);
	}

	public boolean hasUserGroupUser(long userGroupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.hasUserGroupUser(userGroupId, userId);
	}

	public boolean isPasswordExpired(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.isPasswordExpired(user);
	}

	public boolean isPasswordExpiringSoon(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.isPasswordExpiringSoon(user);
	}

	public java.util.List<com.liferay.portal.model.User> search(
		long companyId, java.lang.String keywords, java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.search(companyId, keywords, active, params,
			start, end, obc);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String keywords, java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.search(companyId, keywords, active, params,
			start, end, sort);
	}

	public java.util.List<com.liferay.portal.model.User> search(
		long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, boolean andSearch,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.search(companyId, firstName, middleName,
			lastName, screenName, emailAddress, active, params, andSearch,
			start, end, obc);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, boolean andSearch,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.search(companyId, firstName, middleName,
			lastName, screenName, emailAddress, active, params, andSearch,
			start, end, sort);
	}

	public int searchCount(long companyId, java.lang.String keywords,
		java.lang.Boolean active, java.util.LinkedHashMap<String, Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.searchCount(companyId, keywords, active, params);
	}

	public int searchCount(long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, boolean andSearch)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.searchCount(companyId, firstName, middleName,
			lastName, screenName, emailAddress, active, params, andSearch);
	}

	public void sendPassword(long companyId, java.lang.String emailAddress,
		java.lang.String remoteAddr, java.lang.String remoteHost,
		java.lang.String userAgent, java.lang.String fromName,
		java.lang.String fromAddress, java.lang.String subject,
		java.lang.String body)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.sendPassword(companyId, emailAddress, remoteAddr,
			remoteHost, userAgent, fromName, fromAddress, subject, body);
	}

	public void setRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.setRoleUsers(roleId, userIds);
	}

	public void setUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.setUserGroupUsers(userGroupId, userIds);
	}

	public void unsetGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.unsetGroupUsers(groupId, userIds);
	}

	public void unsetOrganizationUsers(long organizationId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.unsetOrganizationUsers(organizationId, userIds);
	}

	public void unsetPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.unsetPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public void unsetRoleUsers(long roleId,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.unsetRoleUsers(roleId, users);
	}

	public void unsetRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.unsetRoleUsers(roleId, userIds);
	}

	public void unsetTeamUsers(long teamId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.unsetTeamUsers(teamId, userIds);
	}

	public void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.unsetUserGroupUsers(userGroupId, userIds);
	}

	public com.liferay.portal.model.User updateActive(long userId,
		boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateActive(userId, active);
	}

	public com.liferay.portal.model.User updateAgreedToTermsOfUse(long userId,
		boolean agreedToTermsOfUse)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateAgreedToTermsOfUse(userId,
			agreedToTermsOfUse);
	}

	public void updateAsset(long userId, com.liferay.portal.model.User user,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.updateAsset(userId, user, assetCategoryIds,
			assetTagNames);
	}

	public com.liferay.portal.model.User updateCreateDate(long userId,
		java.util.Date createDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateCreateDate(userId, createDate);
	}

	public com.liferay.portal.model.User updateEmailAddress(long userId,
		java.lang.String password, java.lang.String emailAddress1,
		java.lang.String emailAddress2)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateEmailAddress(userId, password,
			emailAddress1, emailAddress2);
	}

	public void updateGroups(long userId, long[] newGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.updateGroups(userId, newGroupIds);
	}

	public com.liferay.portal.model.User updateLastLogin(long userId,
		java.lang.String loginIP)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateLastLogin(userId, loginIP);
	}

	public com.liferay.portal.model.User updateLockout(
		com.liferay.portal.model.User user, boolean lockout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateLockout(user, lockout);
	}

	public com.liferay.portal.model.User updateLockoutByEmailAddress(
		long companyId, java.lang.String emailAddress, boolean lockout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateLockoutByEmailAddress(companyId,
			emailAddress, lockout);
	}

	public com.liferay.portal.model.User updateLockoutById(long userId,
		boolean lockout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateLockoutById(userId, lockout);
	}

	public com.liferay.portal.model.User updateLockoutByScreenName(
		long companyId, java.lang.String screenName, boolean lockout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateLockoutByScreenName(companyId,
			screenName, lockout);
	}

	public com.liferay.portal.model.User updateModifiedDate(long userId,
		java.util.Date modifiedDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateModifiedDate(userId, modifiedDate);
	}

	public void updateOpenId(long userId, java.lang.String openId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.updateOpenId(userId, openId);
	}

	public void updateOrganizations(long userId, long[] newOrganizationIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.updateOrganizations(userId, newOrganizationIds);
	}

	public com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updatePassword(userId, password1, password2,
			passwordReset);
	}

	public com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, boolean silentUpdate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updatePassword(userId, password1, password2,
			passwordReset, silentUpdate);
	}

	public com.liferay.portal.model.User updatePasswordManually(long userId,
		java.lang.String password, boolean passwordEncrypted,
		boolean passwordReset, java.util.Date passwordModifiedDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updatePasswordManually(userId, password,
			passwordEncrypted, passwordReset, passwordModifiedDate);
	}

	public void updatePasswordReset(long userId, boolean passwordReset)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.updatePasswordReset(userId, passwordReset);
	}

	public void updatePortrait(long userId, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.updatePortrait(userId, bytes);
	}

	public void updateReminderQuery(long userId, java.lang.String question,
		java.lang.String answer)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.updateReminderQuery(userId, question, answer);
	}

	public void updateScreenName(long userId, java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userLocalService.updateScreenName(userId, screenName);
	}

	public com.liferay.portal.model.User updateUser(long userId,
		java.lang.String oldPassword, java.lang.String newPassword1,
		java.lang.String newPassword2, boolean passwordReset,
		java.lang.String reminderQueryQuestion,
		java.lang.String reminderQueryAnswer, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.String openId,
		java.lang.String languageId, java.lang.String timeZoneId,
		java.lang.String greeting, java.lang.String comments,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String facebookSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String mySpaceSn, java.lang.String skypeSn,
		java.lang.String twitterSn, java.lang.String ymSn,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds,
		java.util.List<com.liferay.portal.model.UserGroupRole> userGroupRoles,
		long[] userGroupIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userLocalService.updateUser(userId, oldPassword, newPassword1,
			newPassword2, passwordReset, reminderQueryQuestion,
			reminderQueryAnswer, screenName, emailAddress, openId, languageId,
			timeZoneId, greeting, comments, firstName, middleName, lastName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			smsSn, aimSn, facebookSn, icqSn, jabberSn, msnSn, mySpaceSn,
			skypeSn, twitterSn, ymSn, jobTitle, groupIds, organizationIds,
			roleIds, userGroupRoles, userGroupIds, serviceContext);
	}

	public UserLocalService getWrappedUserLocalService() {
		return _userLocalService;
	}

	private UserLocalService _userLocalService;
}