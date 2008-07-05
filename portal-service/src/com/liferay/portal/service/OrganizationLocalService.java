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
 * <a href="OrganizationLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.portal.service.impl.OrganizationLocalServiceImpl</code>.
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
 * @see com.liferay.portal.service.OrganizationLocalServiceFactory
 * @see com.liferay.portal.service.OrganizationLocalServiceUtil
 *
 */
public interface OrganizationLocalService {
	public com.liferay.portal.model.Organization addOrganization(
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.SystemException;

	public void deleteOrganization(long organizationId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteOrganization(
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Organization getOrganization(
		long organizationId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Organization updateOrganization(
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.SystemException;

	public void init();

	public void addGroupOrganizations(long groupId, long[] organizationIds)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Organization addOrganization(long userId,
		long parentOrganizationId, java.lang.String name, int type,
		boolean recursable, long regionId, long countryId, int statusId,
		java.lang.String comments)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void addOrganizationResources(long userId,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void addPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> getGroupOrganizations(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> getManageableOrganizations(
		long userId) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Organization getOrganization(
		long companyId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public long getOrganizationId(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> getParentOrganizations(
		long organizationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> getSuborganizations(
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> getSubsetOrganizations(
		java.util.List<com.liferay.portal.model.Organization> allOrganizations,
		java.util.List<com.liferay.portal.model.Organization> availableOrganizations);

	public java.util.List<com.liferay.portal.model.Organization> getUserOrganizations(
		long userId) throws com.liferay.portal.SystemException;

	public int getUserOrganizationsCount(long userId)
		throws com.liferay.portal.SystemException;

	public boolean hasGroupOrganization(long groupId, long organizationId)
		throws com.liferay.portal.SystemException;

	public boolean hasUserOrganization(long userId, long organizationId)
		throws com.liferay.portal.SystemException;

	public boolean hasPasswordPolicyOrganization(long passwordPolicyId,
		long organizationId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> search(
		long companyId, long parentOrganizationId, java.lang.String keywords,
		int type, java.lang.Long regionId, java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> search(
		long companyId, long parentOrganizationId, java.lang.String keywords,
		int type, java.lang.Long regionId, java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> search(
		long companyId, long parentOrganizationId, java.lang.String name,
		int type, java.lang.String street, java.lang.String city,
		java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> search(
		long companyId, long parentOrganizationId, java.lang.String name,
		int type, java.lang.String street, java.lang.String city,
		java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int searchCount(long companyId, long parentOrganizationId,
		java.lang.String keywords, int type, java.lang.Long regionId,
		java.lang.Long countryId, java.util.LinkedHashMap<String, Object> params)
		throws com.liferay.portal.SystemException;

	public int searchCount(long companyId, long parentOrganizationId,
		java.lang.String name, int type, java.lang.String street,
		java.lang.String city, java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator)
		throws com.liferay.portal.SystemException;

	public void setGroupOrganizations(long groupId, long[] organizationIds)
		throws com.liferay.portal.SystemException;

	public void unsetGroupOrganizations(long groupId, long[] organizationIds)
		throws com.liferay.portal.SystemException;

	public void unsetPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Organization updateOrganization(
		long companyId, long organizationId, long parentOrganizationId,
		java.lang.String name, int type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;
}