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

public class UserUtil {
	public static void cacheResult(com.liferay.portal.model.User user) {
		getPersistence().cacheResult(user);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.User> users) {
		getPersistence().cacheResult(users);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portal.model.User create(long userId) {
		return getPersistence().create(userId);
	}

	public static com.liferay.portal.model.User remove(long userId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(userId);
	}

	public static com.liferay.portal.model.User remove(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(user);
	}

	public static com.liferay.portal.model.User update(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(user);
	}

	public static com.liferay.portal.model.User update(
		com.liferay.portal.model.User user, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(user, merge);
	}

	public static com.liferay.portal.model.User updateImpl(
		com.liferay.portal.model.User user, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(user, merge);
	}

	public static com.liferay.portal.model.User findByPrimaryKey(long userId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(userId);
	}

	public static com.liferay.portal.model.User fetchByPrimaryKey(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(userId);
	}

	public static java.util.List<com.liferay.portal.model.User> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portal.model.User> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portal.model.User> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portal.model.User findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portal.model.User findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portal.model.User[] findByUuid_PrevAndNext(
		long userId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUuid_PrevAndNext(userId, uuid, obc);
	}

	public static java.util.List<com.liferay.portal.model.User> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.User> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.User> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portal.model.User findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.User findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.User[] findByCompanyId_PrevAndNext(
		long userId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(userId, companyId, obc);
	}

	public static com.liferay.portal.model.User findByContactId(long contactId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence().findByContactId(contactId);
	}

	public static com.liferay.portal.model.User fetchByContactId(long contactId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByContactId(contactId);
	}

	public static com.liferay.portal.model.User fetchByContactId(
		long contactId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByContactId(contactId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.User> findByEmailAddress(
		java.lang.String emailAddress)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByEmailAddress(emailAddress);
	}

	public static java.util.List<com.liferay.portal.model.User> findByEmailAddress(
		java.lang.String emailAddress, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByEmailAddress(emailAddress, start, end);
	}

	public static java.util.List<com.liferay.portal.model.User> findByEmailAddress(
		java.lang.String emailAddress, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByEmailAddress(emailAddress, start, end, obc);
	}

	public static com.liferay.portal.model.User findByEmailAddress_First(
		java.lang.String emailAddress,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence().findByEmailAddress_First(emailAddress, obc);
	}

	public static com.liferay.portal.model.User findByEmailAddress_Last(
		java.lang.String emailAddress,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence().findByEmailAddress_Last(emailAddress, obc);
	}

	public static com.liferay.portal.model.User[] findByEmailAddress_PrevAndNext(
		long userId, java.lang.String emailAddress,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByEmailAddress_PrevAndNext(userId, emailAddress, obc);
	}

	public static com.liferay.portal.model.User findByOpenId(
		java.lang.String openId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence().findByOpenId(openId);
	}

	public static com.liferay.portal.model.User fetchByOpenId(
		java.lang.String openId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByOpenId(openId);
	}

	public static com.liferay.portal.model.User fetchByOpenId(
		java.lang.String openId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByOpenId(openId, retrieveFromCache);
	}

	public static com.liferay.portal.model.User findByPortraitId(
		long portraitId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPortraitId(portraitId);
	}

	public static com.liferay.portal.model.User fetchByPortraitId(
		long portraitId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPortraitId(portraitId);
	}

	public static com.liferay.portal.model.User fetchByPortraitId(
		long portraitId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPortraitId(portraitId, retrieveFromCache);
	}

	public static com.liferay.portal.model.User findByC_U(long companyId,
		long userId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_U(companyId, userId);
	}

	public static com.liferay.portal.model.User fetchByC_U(long companyId,
		long userId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_U(companyId, userId);
	}

	public static com.liferay.portal.model.User fetchByC_U(long companyId,
		long userId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_U(companyId, userId, retrieveFromCache);
	}

	public static com.liferay.portal.model.User findByC_DU(long companyId,
		boolean defaultUser)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_DU(companyId, defaultUser);
	}

	public static com.liferay.portal.model.User fetchByC_DU(long companyId,
		boolean defaultUser) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_DU(companyId, defaultUser);
	}

	public static com.liferay.portal.model.User fetchByC_DU(long companyId,
		boolean defaultUser, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByC_DU(companyId, defaultUser, retrieveFromCache);
	}

	public static com.liferay.portal.model.User findByC_SN(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_SN(companyId, screenName);
	}

	public static com.liferay.portal.model.User fetchByC_SN(long companyId,
		java.lang.String screenName) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_SN(companyId, screenName);
	}

	public static com.liferay.portal.model.User fetchByC_SN(long companyId,
		java.lang.String screenName, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByC_SN(companyId, screenName, retrieveFromCache);
	}

	public static com.liferay.portal.model.User findByC_EA(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_EA(companyId, emailAddress);
	}

	public static com.liferay.portal.model.User fetchByC_EA(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_EA(companyId, emailAddress);
	}

	public static com.liferay.portal.model.User fetchByC_EA(long companyId,
		java.lang.String emailAddress, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByC_EA(companyId, emailAddress, retrieveFromCache);
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

	public static java.util.List<com.liferay.portal.model.User> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.User> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.User> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByContactId(long contactId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		getPersistence().removeByContactId(contactId);
	}

	public static void removeByEmailAddress(java.lang.String emailAddress)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByEmailAddress(emailAddress);
	}

	public static void removeByOpenId(java.lang.String openId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		getPersistence().removeByOpenId(openId);
	}

	public static void removeByPortraitId(long portraitId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		getPersistence().removeByPortraitId(portraitId);
	}

	public static void removeByC_U(long companyId, long userId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		getPersistence().removeByC_U(companyId, userId);
	}

	public static void removeByC_DU(long companyId, boolean defaultUser)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		getPersistence().removeByC_DU(companyId, defaultUser);
	}

	public static void removeByC_SN(long companyId, java.lang.String screenName)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		getPersistence().removeByC_SN(companyId, screenName);
	}

	public static void removeByC_EA(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.SystemException {
		getPersistence().removeByC_EA(companyId, emailAddress);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByContactId(long contactId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByContactId(contactId);
	}

	public static int countByEmailAddress(java.lang.String emailAddress)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByEmailAddress(emailAddress);
	}

	public static int countByOpenId(java.lang.String openId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByOpenId(openId);
	}

	public static int countByPortraitId(long portraitId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByPortraitId(portraitId);
	}

	public static int countByC_U(long companyId, long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_U(companyId, userId);
	}

	public static int countByC_DU(long companyId, boolean defaultUser)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_DU(companyId, defaultUser);
	}

	public static int countByC_SN(long companyId, java.lang.String screenName)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_SN(companyId, screenName);
	}

	public static int countByC_EA(long companyId, java.lang.String emailAddress)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_EA(companyId, emailAddress);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getGroups(pk);
	}

	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getGroups(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getGroups(pk, start, end, obc);
	}

	public static int getGroupsSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getGroupsSize(pk);
	}

	public static boolean containsGroup(long pk, long groupPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsGroup(pk, groupPK);
	}

	public static boolean containsGroups(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsGroups(pk);
	}

	public static void addGroup(long pk, long groupPK)
		throws com.liferay.portal.SystemException {
		getPersistence().addGroup(pk, groupPK);
	}

	public static void addGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.SystemException {
		getPersistence().addGroup(pk, group);
	}

	public static void addGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().addGroups(pk, groupPKs);
	}

	public static void addGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.SystemException {
		getPersistence().addGroups(pk, groups);
	}

	public static void clearGroups(long pk)
		throws com.liferay.portal.SystemException {
		getPersistence().clearGroups(pk);
	}

	public static void removeGroup(long pk, long groupPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeGroup(pk, groupPK);
	}

	public static void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.SystemException {
		getPersistence().removeGroup(pk, group);
	}

	public static void removeGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().removeGroups(pk, groupPKs);
	}

	public static void removeGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.SystemException {
		getPersistence().removeGroups(pk, groups);
	}

	public static void setGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().setGroups(pk, groupPKs);
	}

	public static void setGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.SystemException {
		getPersistence().setGroups(pk, groups);
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

	public static UserPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(UserPersistence persistence) {
		_persistence = persistence;
	}

	private static UserPersistence _persistence;
}