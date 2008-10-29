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
 * <a href="UserLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.portal.service.impl.UserLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.UserLocalServiceUtil
 *
 */
public interface UserLocalService {
	public com.liferay.portal.model.User addUser(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.User createUser(long userId);

	public void deleteUser(long userId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteUser(com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.User getUser(long userId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portal.model.User> getUsers(int start,
		int end) throws com.liferay.portal.SystemException;

	public int getUsersCount() throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.User updateUser(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException;

	public void addGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void addOrganizationUsers(long organizationId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void addPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.SystemException;

	public void addRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.SystemException;

	public void addUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

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
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public int authenticateByEmailAddress(long companyId,
		java.lang.String emailAddress, java.lang.String password,
		java.util.Map<String, String[]> headerMap,
		java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public int authenticateByScreenName(long companyId,
		java.lang.String screenName, java.lang.String password,
		java.util.Map<String, String[]> headerMap,
		java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public int authenticateByUserId(long companyId, long userId,
		java.lang.String password, java.util.Map<String, String[]> headerMap,
		java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public long authenticateForBasic(long companyId, java.lang.String authType,
		java.lang.String login, java.lang.String password)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public boolean authenticateForJAAS(long userId, java.lang.String encPassword);

	public void checkLockout(com.liferay.portal.model.User user)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void checkLoginFailure(com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException;

	public void checkLoginFailureByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void checkLoginFailureById(long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void checkLoginFailureByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void checkPasswordExpired(com.liferay.portal.model.User user)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void clearOrganizationUsers(long organizationId)
		throws com.liferay.portal.SystemException;

	public void clearUserGroupUsers(long userGroupId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.kernel.util.KeyValuePair decryptUserId(
		long companyId, java.lang.String name, java.lang.String password)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deletePasswordPolicyUser(long passwordPolicyId, long userId)
		throws com.liferay.portal.SystemException;

	public void deleteRoleUser(long roleId, long userId)
		throws com.liferay.portal.SystemException;

	public java.lang.String encryptUserId(java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User getDefaultUser(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public long getDefaultUserId(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> getGroupUsers(
		long groupId) throws com.liferay.portal.SystemException;

	public int getGroupUsersCount(long groupId)
		throws com.liferay.portal.SystemException;

	public int getGroupUsersCount(long groupId, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> getNoAnnouncementsDeliveries(
		java.lang.String type) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> getOrganizationUsers(
		long organizationId) throws com.liferay.portal.SystemException;

	public int getOrganizationUsersCount(long organizationId)
		throws com.liferay.portal.SystemException;

	public int getOrganizationUsersCount(long organizationId, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> getPermissionUsers(
		long companyId, long groupId, java.lang.String name,
		java.lang.String primKey, java.lang.String actionId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String emailAddress,
		boolean andOperator, int start, int end)
		throws com.liferay.portal.SystemException;

	public int getPermissionUsersCount(long companyId, long groupId,
		java.lang.String name, java.lang.String primKey,
		java.lang.String actionId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String emailAddress, boolean andOperator)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> getRoleUsers(
		long roleId) throws com.liferay.portal.SystemException;

	public int getRoleUsersCount(long roleId)
		throws com.liferay.portal.SystemException;

	public int getRoleUsersCount(long roleId, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId1, long userId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId1, long userId2, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public int getSocialUsersCount(long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public int getSocialUsersCount(long userId, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public int getSocialUsersCount(long userId1, long userId2)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public int getSocialUsersCount(long userId1, long userId2, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> getUserGroupUsers(
		long userGroupId) throws com.liferay.portal.SystemException;

	public int getUserGroupUsersCount(long userGroupId)
		throws com.liferay.portal.SystemException;

	public int getUserGroupUsersCount(long userGroupId, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User getUserByContactId(long contactId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User getUserByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User getUserById(long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User getUserById(long companyId, long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User getUserByOpenId(
		java.lang.String openId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User getUserByPortraitId(long portraitId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User getUserByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public long getUserIdByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public long getUserIdByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public boolean hasGroupUser(long groupId, long userId)
		throws com.liferay.portal.SystemException;

	public boolean hasOrganizationUser(long organizationId, long userId)
		throws com.liferay.portal.SystemException;

	public boolean hasPasswordPolicyUser(long passwordPolicyId, long userId)
		throws com.liferay.portal.SystemException;

	public boolean hasRoleUser(long roleId, long userId)
		throws com.liferay.portal.SystemException;

	public boolean hasUserGroupUser(long userGroupId, long userId)
		throws com.liferay.portal.SystemException;

	public boolean isPasswordExpired(com.liferay.portal.model.User user)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public boolean isPasswordExpiringSoon(com.liferay.portal.model.User user)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String keywords, java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, boolean andSearch,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> search(
		long companyId, java.lang.String keywords, java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> search(
		long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, boolean andSearch,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int searchCount(long companyId, java.lang.String keywords,
		java.lang.Boolean active, java.util.LinkedHashMap<String, Object> params)
		throws com.liferay.portal.SystemException;

	public int searchCount(long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, boolean andSearch)
		throws com.liferay.portal.SystemException;

	public void sendPassword(long companyId, java.lang.String emailAddress,
		java.lang.String remoteAddr, java.lang.String remoteHost,
		java.lang.String userAgent)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void setRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.SystemException;

	public void setUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void unsetGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.SystemException;

	public void unsetOrganizationUsers(long organizationId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void unsetPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.SystemException;

	public void unsetRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.SystemException;

	public void unsetRoleUsers(long roleId,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.SystemException;

	public void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.User updateActive(long userId,
		boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User updateAgreedToTermsOfUse(long userId,
		boolean agreedToTermsOfUse)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User updateCreateDate(long userId,
		java.util.Date createDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void updateGroups(long userId, long[] newGroupIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User updateLastLogin(long userId,
		java.lang.String loginIP)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User updateLockout(
		com.liferay.portal.model.User user, boolean lockout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User updateLockoutByEmailAddress(
		long companyId, java.lang.String emailAddress, boolean lockout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User updateLockoutById(long userId,
		boolean lockout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User updateLockoutByScreenName(
		long companyId, java.lang.String screenName, boolean lockout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User updateModifiedDate(long userId,
		java.util.Date modifiedDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void updateOpenId(long userId, java.lang.String openId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void updateOrganizations(long userId, long[] newOrganizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, boolean silentUpdate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.User updatePasswordManually(long userId,
		java.lang.String password, boolean passwordEncrypted,
		boolean passwordReset, java.util.Date passwordModifiedDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void updatePasswordReset(long userId, boolean passwordReset)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void updatePortrait(long userId, byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void updateReminderQuery(long userId, java.lang.String question,
		java.lang.String answer)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void updateScreenName(long userId, java.lang.String screenName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

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
		long[] roleIds, long[] userGroupIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;
}