/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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


/**
 * <a href="UserLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.UserLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.UserLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.UserLocalService
 * @see com.liferay.portal.service.UserLocalServiceFactory
 *
 */
public class UserLocalServiceUtil {
	public static com.liferay.portal.model.User addUser(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.addUser(user);
	}

	public static void deleteUser(long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.deleteUser(userId);
	}

	public static void deleteUser(com.liferay.portal.model.User user)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.deleteUser(user);
	}

	public static java.util.List<com.liferay.portal.model.User> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.User> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portal.model.User updateUser(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateUser(user);
	}

	public static void addGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.addGroupUsers(groupId, userIds);
	}

	public static void addOrganizationUsers(long organizationId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.addOrganizationUsers(organizationId, userIds);
	}

	public static void addPasswordPolicyUsers(long passwordPolicyId,
		long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.addPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public static void addRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.addRoleUsers(roleId, userIds);
	}

	public static void addUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.addUserGroupUsers(userGroupId, userIds);
	}

	public static com.liferay.portal.model.User addUser(long creatorUserId,
		long companyId, boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.util.Locale locale, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String jobTitle, long[] organizationIds,
		boolean sendEmail)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.addUser(creatorUserId, companyId, autoPassword,
			password1, password2, autoScreenName, screenName, emailAddress,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			organizationIds, sendEmail);
	}

	public static int authenticateByEmailAddress(long companyId,
		java.lang.String emailAddress, java.lang.String password,
		java.util.Map<String, String[]> headerMap,
		java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.authenticateByEmailAddress(companyId,
			emailAddress, password, headerMap, parameterMap);
	}

	public static int authenticateByScreenName(long companyId,
		java.lang.String screenName, java.lang.String password,
		java.util.Map<String, String[]> headerMap,
		java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.authenticateByScreenName(companyId, screenName,
			password, headerMap, parameterMap);
	}

	public static int authenticateByUserId(long companyId, long userId,
		java.lang.String password, java.util.Map<String, String[]> headerMap,
		java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.authenticateByUserId(companyId, userId,
			password, headerMap, parameterMap);
	}

	public static long authenticateForBasic(long companyId,
		java.lang.String authType, java.lang.String login,
		java.lang.String password)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.authenticateForBasic(companyId, authType,
			login, password);
	}

	public static boolean authenticateForJAAS(long userId,
		java.lang.String encPassword)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.authenticateForJAAS(userId, encPassword);
	}

	public static void checkLockout(com.liferay.portal.model.User user)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.checkLockout(user);
	}

	public static void checkLoginFailure(com.liferay.portal.model.User user)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.checkLoginFailure(user);
	}

	public static void checkLoginFailureByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.checkLoginFailureByEmailAddress(companyId, emailAddress);
	}

	public static void checkLoginFailureById(long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.checkLoginFailureById(userId);
	}

	public static void checkLoginFailureByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.checkLoginFailureByScreenName(companyId, screenName);
	}

	public static void checkPasswordExpired(com.liferay.portal.model.User user)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.checkPasswordExpired(user);
	}

	public static void clearOrganizationUsers(long organizationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.clearOrganizationUsers(organizationId);
	}

	public static void clearUserGroupUsers(long userGroupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.clearUserGroupUsers(userGroupId);
	}

	public static com.liferay.portal.kernel.util.KeyValuePair decryptUserId(
		long companyId, java.lang.String name, java.lang.String password)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.decryptUserId(companyId, name, password);
	}

	public static void deletePasswordPolicyUser(long passwordPolicyId,
		long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.deletePasswordPolicyUser(passwordPolicyId, userId);
	}

	public static void deleteRoleUser(long roleId, long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.deleteRoleUser(roleId, userId);
	}

	public static java.lang.String encryptUserId(java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.encryptUserId(name);
	}

	public static com.liferay.portal.model.User getDefaultUser(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getDefaultUser(companyId);
	}

	public static long getDefaultUserId(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getDefaultUserId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.User> getGroupUsers(
		long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getGroupUsers(groupId);
	}

	public static int getGroupUsersCount(long groupId)
		throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getGroupUsersCount(groupId);
	}

	public static int getGroupUsersCount(long groupId, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getGroupUsersCount(groupId, active);
	}

	public static java.util.List<com.liferay.portal.model.User> getOrganizationUsers(
		long organizationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getOrganizationUsers(organizationId);
	}

	public static int getOrganizationUsersCount(long organizationId)
		throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getOrganizationUsersCount(organizationId);
	}

	public static int getOrganizationUsersCount(long organizationId,
		boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getOrganizationUsersCount(organizationId, active);
	}

	public static java.util.List<com.liferay.portal.model.User> getPermissionUsers(
		long companyId, long groupId, java.lang.String name,
		java.lang.String primKey, java.lang.String actionId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String emailAddress,
		boolean andOperator, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getPermissionUsers(companyId, groupId, name,
			primKey, actionId, firstName, middleName, lastName, emailAddress,
			andOperator, begin, end);
	}

	public static int getPermissionUsersCount(long companyId, long groupId,
		java.lang.String name, java.lang.String primKey,
		java.lang.String actionId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String emailAddress, boolean andOperator)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getPermissionUsersCount(companyId, groupId,
			name, primKey, actionId, firstName, middleName, lastName,
			emailAddress, andOperator);
	}

	public static java.util.List<com.liferay.portal.model.User> getRoleUsers(
		long roleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getRoleUsers(roleId);
	}

	public static int getRoleUsersCount(long roleId)
		throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getRoleUsersCount(roleId);
	}

	public static int getRoleUsersCount(long roleId, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getRoleUsersCount(roleId, active);
	}

	public static java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId, int type, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getSocialUsers(userId, type, begin, end);
	}

	public static int getSocialUsersCount(long userId, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getSocialUsersCount(userId, type);
	}

	public static java.util.List<com.liferay.portal.model.User> getUserGroupUsers(
		long userGroupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserGroupUsers(userGroupId);
	}

	public static int getUserGroupUsersCount(long userGroupId)
		throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserGroupUsersCount(userGroupId);
	}

	public static int getUserGroupUsersCount(long userGroupId, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserGroupUsersCount(userGroupId, active);
	}

	public static com.liferay.portal.model.User getUserByContactId(
		long contactId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserByContactId(contactId);
	}

	public static com.liferay.portal.model.User getUserByEmailAddress(
		long companyId, java.lang.String emailAddress)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserByEmailAddress(companyId, emailAddress);
	}

	public static com.liferay.portal.model.User getUserById(long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserById(userId);
	}

	public static com.liferay.portal.model.User getUserById(long companyId,
		long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserById(companyId, userId);
	}

	public static com.liferay.portal.model.User getUserByPortraitId(
		long portraitId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserByPortraitId(portraitId);
	}

	public static com.liferay.portal.model.User getUserByScreenName(
		long companyId, java.lang.String screenName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserByScreenName(companyId, screenName);
	}

	public static long getUserIdByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserIdByEmailAddress(companyId, emailAddress);
	}

	public static long getUserIdByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserIdByScreenName(companyId, screenName);
	}

	public static boolean hasGroupUser(long groupId, long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.hasGroupUser(groupId, userId);
	}

	public static boolean hasOrganizationUser(long organizationId, long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.hasOrganizationUser(organizationId, userId);
	}

	public static boolean hasPasswordPolicyUser(long passwordPolicyId,
		long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.hasPasswordPolicyUser(passwordPolicyId, userId);
	}

	public static boolean hasRoleUser(long roleId, long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.hasRoleUser(roleId, userId);
	}

	public static boolean hasUserGroupUser(long userGroupId, long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.hasUserGroupUser(userGroupId, userId);
	}

	public static boolean isPasswordExpired(com.liferay.portal.model.User user)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.isPasswordExpired(user);
	}

	public static boolean isPasswordExpiringSoon(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.isPasswordExpiringSoon(user);
	}

	public static java.util.List<com.liferay.portal.model.User> search(
		long companyId, java.lang.String keywords, java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.search(companyId, keywords, active, params,
			begin, end, obc);
	}

	public static java.util.List<com.liferay.portal.model.User> search(
		long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, boolean andSearch,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.search(companyId, firstName, middleName,
			lastName, screenName, emailAddress, active, params, andSearch,
			begin, end, obc);
	}

	public static int searchCount(long companyId, java.lang.String keywords,
		java.lang.Boolean active, java.util.LinkedHashMap<String, Object> params)
		throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.searchCount(companyId, keywords, active, params);
	}

	public static int searchCount(long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, boolean andSearch)
		throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.searchCount(companyId, firstName, middleName,
			lastName, screenName, emailAddress, active, params, andSearch);
	}

	public static void sendPassword(long companyId,
		java.lang.String emailAddress, java.lang.String remoteAddr,
		java.lang.String remoteHost, java.lang.String userAgent)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.sendPassword(companyId, emailAddress, remoteAddr,
			remoteHost, userAgent);
	}

	public static void setRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.setRoleUsers(roleId, userIds);
	}

	public static void setUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.setUserGroupUsers(userGroupId, userIds);
	}

	public static void unsetGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.unsetGroupUsers(groupId, userIds);
	}

	public static void unsetOrganizationUsers(long organizationId,
		long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.unsetOrganizationUsers(organizationId, userIds);
	}

	public static void unsetPasswordPolicyUsers(long passwordPolicyId,
		long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.unsetPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public static void unsetRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.unsetRoleUsers(roleId, userIds);
	}

	public static void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.unsetUserGroupUsers(userGroupId, userIds);
	}

	public static com.liferay.portal.model.User updateActive(long userId,
		boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateActive(userId, active);
	}

	public static com.liferay.portal.model.User updateAgreedToTermsOfUse(
		long userId, boolean agreedToTermsOfUse)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateAgreedToTermsOfUse(userId,
			agreedToTermsOfUse);
	}

	public static com.liferay.portal.model.User updateCreateDate(long userId,
		java.util.Date createDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateCreateDate(userId, createDate);
	}

	public static com.liferay.portal.model.User updateLastLogin(long userId,
		java.lang.String loginIP)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateLastLogin(userId, loginIP);
	}

	public static com.liferay.portal.model.User updateLockout(
		com.liferay.portal.model.User user, boolean lockout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateLockout(user, lockout);
	}

	public static com.liferay.portal.model.User updateLockoutByEmailAddress(
		long companyId, java.lang.String emailAddress, boolean lockout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateLockoutByEmailAddress(companyId,
			emailAddress, lockout);
	}

	public static com.liferay.portal.model.User updateLockoutById(long userId,
		boolean lockout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateLockoutById(userId, lockout);
	}

	public static com.liferay.portal.model.User updateLockoutByScreenName(
		long companyId, java.lang.String screenName, boolean lockout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateLockoutByScreenName(companyId,
			screenName, lockout);
	}

	public static com.liferay.portal.model.User updateModifiedDate(
		long userId, java.util.Date modifiedDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateModifiedDate(userId, modifiedDate);
	}

	public static void updateOrganizations(long userId,
		long[] newOrganizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.updateOrganizations(userId, newOrganizationIds);
	}

	public static com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updatePassword(userId, password1, password2,
			passwordReset);
	}

	public static com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, boolean silentUpdate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updatePassword(userId, password1, password2,
			passwordReset, silentUpdate);
	}

	public static com.liferay.portal.model.User updatePasswordManually(
		long userId, java.lang.String password, boolean passwordEncrypted,
		boolean passwordReset, java.util.Date passwordModifiedDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updatePasswordManually(userId, password,
			passwordEncrypted, passwordReset, passwordModifiedDate);
	}

	public static void updatePasswordReset(long userId, boolean passwordReset)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.updatePasswordReset(userId, passwordReset);
	}

	public static void updatePortrait(long userId, byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.updatePortrait(userId, bytes);
	}

	public static void updateScreenName(long userId, java.lang.String screenName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		userLocalService.updateScreenName(userId, screenName);
	}

	public static com.liferay.portal.model.User updateUser(long userId,
		java.lang.String oldPassword, boolean passwordReset,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.String languageId, java.lang.String timeZoneId,
		java.lang.String greeting, java.lang.String comments,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String smsSn, java.lang.String aimSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String skypeSn, java.lang.String ymSn,
		java.lang.String jobTitle, long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateUser(userId, oldPassword, passwordReset,
			screenName, emailAddress, languageId, timeZoneId, greeting,
			comments, firstName, middleName, lastName, prefixId, suffixId,
			male, birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn,
			icqSn, jabberSn, msnSn, skypeSn, ymSn, jobTitle, organizationIds);
	}

	public static com.liferay.portal.model.User updateUser(long userId,
		java.lang.String oldPassword, java.lang.String newPassword1,
		java.lang.String newPassword2, boolean passwordReset,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.String languageId, java.lang.String timeZoneId,
		java.lang.String greeting, java.lang.String comments,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String smsSn, java.lang.String aimSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String skypeSn, java.lang.String ymSn,
		java.lang.String jobTitle, long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateUser(userId, oldPassword, newPassword1,
			newPassword2, passwordReset, screenName, emailAddress, languageId,
			timeZoneId, greeting, comments, firstName, middleName, lastName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			smsSn, aimSn, icqSn, jabberSn, msnSn, skypeSn, ymSn, jobTitle,
			organizationIds);
	}
}