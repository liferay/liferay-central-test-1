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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the company local service. This utility wraps {@link com.liferay.portal.service.impl.CompanyLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.CompanyLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CompanyLocalService
 * @see com.liferay.portal.service.base.CompanyLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.CompanyLocalServiceImpl
 * @generated
 */
public class CompanyLocalServiceUtil {
	/**
	* Adds the company to the database. Also notifies the appropriate model listeners.
	*
	* @param company the company to add
	* @return the company that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Company addCompany(
		com.liferay.portal.model.Company company)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addCompany(company);
	}

	/**
	* Creates a new company with the primary key. Does not add the company to the database.
	*
	* @param companyId the primary key for the new company
	* @return the new company
	*/
	public static com.liferay.portal.model.Company createCompany(long companyId) {
		return getService().createCompany(companyId);
	}

	/**
	* Deletes the company with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param companyId the primary key of the company to delete
	* @throws PortalException if a company with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteCompany(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCompany(companyId);
	}

	/**
	* Deletes the company from the database. Also notifies the appropriate model listeners.
	*
	* @param company the company to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteCompany(com.liferay.portal.model.Company company)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCompany(company);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the company with the primary key.
	*
	* @param companyId the primary key of the company to get
	* @return the company
	* @throws PortalException if a company with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Company getCompany(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompany(companyId);
	}

	/**
	* Gets a range of all the companies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of companies to return
	* @param end the upper bound of the range of companies to return (not inclusive)
	* @return the range of companies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Company> getCompanies(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanies(start, end);
	}

	/**
	* Gets the number of companies.
	*
	* @return the number of companies
	* @throws SystemException if a system exception occurred
	*/
	public static int getCompaniesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompaniesCount();
	}

	/**
	* Updates the company in the database. Also notifies the appropriate model listeners.
	*
	* @param company the company to update
	* @return the company that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Company updateCompany(
		com.liferay.portal.model.Company company)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCompany(company);
	}

	/**
	* Updates the company in the database. Also notifies the appropriate model listeners.
	*
	* @param company the company to update
	* @param merge whether to merge the company with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the company that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Company updateCompany(
		com.liferay.portal.model.Company company, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCompany(company, merge);
	}

	public static com.liferay.portal.model.Company addCompany(
		java.lang.String webId, java.lang.String virtualHost,
		java.lang.String mx, java.lang.String shardName, boolean system,
		int maxUsers)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addCompany(webId, virtualHost, mx, shardName, system,
			maxUsers);
	}

	public static com.liferay.portal.model.Company checkCompany(
		java.lang.String webId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().checkCompany(webId);
	}

	public static com.liferay.portal.model.Company checkCompany(
		java.lang.String webId, java.lang.String mx, java.lang.String shardName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().checkCompany(webId, mx, shardName);
	}

	public static void checkCompanyKey(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkCompanyKey(companyId);
	}

	public static void deleteLogo(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLogo(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Company> getCompanies()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanies();
	}

	public static java.util.List<com.liferay.portal.model.Company> getCompanies(
		boolean system)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanies(system);
	}

	public static int getCompaniesCount(boolean system)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompaniesCount(system);
	}

	public static com.liferay.portal.model.Company getCompanyById(
		long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyById(companyId);
	}

	public static com.liferay.portal.model.Company getCompanyByLogoId(
		long logoId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyByLogoId(logoId);
	}

	public static com.liferay.portal.model.Company getCompanyByMx(
		java.lang.String mx)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyByMx(mx);
	}

	public static com.liferay.portal.model.Company getCompanyByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyByVirtualHost(virtualHost);
	}

	public static com.liferay.portal.model.Company getCompanyByWebId(
		java.lang.String webId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyByWebId(webId);
	}

	public static void removePreferences(long companyId, java.lang.String[] keys)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().removePreferences(companyId, keys);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long userId, java.lang.String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().search(companyId, userId, keywords, start, end);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long userId, java.lang.String portletId, long groupId,
		java.lang.String type, java.lang.String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, userId, portletId, groupId, type,
			keywords, start, end);
	}

	public static com.liferay.portal.model.Company updateCompany(
		long companyId, java.lang.String virtualHost, java.lang.String mx,
		int maxUsers)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCompany(companyId, virtualHost, mx, maxUsers);
	}

	public static com.liferay.portal.model.Company updateCompany(
		long companyId, java.lang.String virtualHost, java.lang.String mx,
		java.lang.String homeURL, java.lang.String name,
		java.lang.String legalName, java.lang.String legalId,
		java.lang.String legalType, java.lang.String sicCode,
		java.lang.String tickerSymbol, java.lang.String industry,
		java.lang.String type, java.lang.String size)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateCompany(companyId, virtualHost, mx, homeURL, name,
			legalName, legalId, legalType, sicCode, tickerSymbol, industry,
			type, size);
	}

	public static void updateDisplay(long companyId,
		java.lang.String languageId, java.lang.String timeZoneId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateDisplay(companyId, languageId, timeZoneId);
	}

	public static void updateLogo(long companyId, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateLogo(companyId, bytes);
	}

	public static void updateLogo(long companyId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateLogo(companyId, file);
	}

	public static void updateLogo(long companyId, java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateLogo(companyId, is);
	}

	public static void updatePreferences(long companyId,
		com.liferay.portal.kernel.util.UnicodeProperties properties)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().updatePreferences(companyId, properties);
	}

	public static void updateSecurity(long companyId,
		java.lang.String authType, boolean autoLogin, boolean sendPassword,
		boolean strangers, boolean strangersWithMx, boolean strangersVerify,
		boolean communityLogo)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateSecurity(companyId, authType, autoLogin, sendPassword,
			strangers, strangersWithMx, strangersVerify, communityLogo);
	}

	public static CompanyLocalService getService() {
		if (_service == null) {
			_service = (CompanyLocalService)PortalBeanLocatorUtil.locate(CompanyLocalService.class.getName());

			ReferenceRegistry.registerReference(CompanyLocalServiceUtil.class,
				"_service");
			MethodCache.remove(CompanyLocalService.class);
		}

		return _service;
	}

	public void setService(CompanyLocalService service) {
		MethodCache.remove(CompanyLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(CompanyLocalServiceUtil.class,
			"_service");
		MethodCache.remove(CompanyLocalService.class);
	}

	private static CompanyLocalService _service;
}