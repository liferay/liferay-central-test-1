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

public class OrganizationServiceUtil {
	public static void addGroupOrganizations(long groupId,
		long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().addGroupOrganizations(groupId, organizationIds);
	}

	public static void addPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addPasswordPolicyOrganizations(passwordPolicyId, organizationIds);
	}

	public static com.liferay.portal.model.Organization addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addOrganization(parentOrganizationId, name, type,
			recursable, regionId, countryId, statusId, comments, serviceContext);
	}

	public static com.liferay.portal.model.Organization addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.OrgLabor> orgLabors,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addOrganization(parentOrganizationId, name, type,
			recursable, regionId, countryId, statusId, comments, addresses,
			emailAddresses, orgLabors, phones, websites, serviceContext);
	}

	public static void deleteLogo(long organizationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteLogo(organizationId);
	}

	public static void deleteOrganization(long organizationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteOrganization(organizationId);
	}

	public static java.util.List<com.liferay.portal.model.Organization> getManageableOrganizations(
		java.lang.String actionId, int max)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getManageableOrganizations(actionId, max);
	}

	public static com.liferay.portal.model.Organization getOrganization(
		long organizationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getOrganization(organizationId);
	}

	public static long getOrganizationId(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getService().getOrganizationId(companyId, name);
	}

	public static java.util.List<com.liferay.portal.model.Organization> getUserOrganizations(
		long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getUserOrganizations(userId);
	}

	public static java.util.List<com.liferay.portal.model.Organization> getUserOrganizations(
		long userId, boolean inheritUserGroups)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getUserOrganizations(userId, inheritUserGroups);
	}

	public static void setGroupOrganizations(long groupId,
		long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().setGroupOrganizations(groupId, organizationIds);
	}

	public static void unsetGroupOrganizations(long groupId,
		long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().unsetGroupOrganizations(groupId, organizationIds);
	}

	public static void unsetPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.unsetPasswordPolicyOrganizations(passwordPolicyId, organizationIds);
	}

	public static com.liferay.portal.model.Organization updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateOrganization(organizationId, parentOrganizationId,
			name, type, recursable, regionId, countryId, statusId, comments,
			serviceContext);
	}

	public static com.liferay.portal.model.Organization updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.OrgLabor> orgLabors,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateOrganization(organizationId, parentOrganizationId,
			name, type, recursable, regionId, countryId, statusId, comments,
			addresses, emailAddresses, orgLabors, phones, websites,
			serviceContext);
	}

	public static OrganizationService getService() {
		if (_service == null) {
			throw new RuntimeException("OrganizationService is not set");
		}

		return _service;
	}

	public void setService(OrganizationService service) {
		_service = service;
	}

	private static OrganizationService _service;
}