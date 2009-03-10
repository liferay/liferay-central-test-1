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

package com.liferay.portal.service.impl;

import com.liferay.portal.AccountNameException;
import com.liferay.portal.CompanyMxException;
import com.liferay.portal.CompanyVirtualHostException;
import com.liferay.portal.CompanyWebIdException;
import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.search.lucene.LuceneUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.CompanyLocalServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

/**
 * <a href="CompanyLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 *
 */
public class CompanyLocalServiceImpl extends CompanyLocalServiceBaseImpl {

	public Company addCompany(String webId, String virtualHost, String mx)
		throws PortalException, SystemException {

		// Company

		virtualHost = virtualHost.trim().toLowerCase();

		if ((Validator.isNull(webId)) ||
			(webId.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) ||
			(companyPersistence.fetchByWebId(webId) != null)) {

			throw new CompanyWebIdException();
		}

		validate(webId, virtualHost, mx);

		Company company = checkCompany(webId, mx);

		company.setVirtualHost(virtualHost);
		company.setMx(mx);

		companyPersistence.update(company, false);

		return company;
	}

	public Company checkCompany(String webId)
		throws PortalException, SystemException {

		String mx = webId;

		return checkCompany(webId, mx);
	}

	public Company checkCompany(String webId, String mx)
		throws PortalException, SystemException {

		// Company

		Date now = new Date();

		Company company = companyPersistence.fetchByWebId(webId);

		if (company == null) {
			String virtualHost = webId;

			if (webId.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {
				virtualHost = _DEFAULT_VIRTUAL_HOST;
			}

			String homeURL = null;
			String name = webId;
			String legalName = null;
			String legalId = null;
			String legalType = null;
			String sicCode = null;
			String tickerSymbol = null;
			String industry = null;
			String type = null;
			String size = null;

			long companyId = counterLocalService.increment();

			company = companyPersistence.create(companyId);

			try {
				company.setKeyObj(Encryptor.generateKey());
			}
			catch (EncryptorException ee) {
				throw new SystemException(ee);
			}

			company.setWebId(webId);
			company.setVirtualHost(virtualHost);
			company.setMx(mx);

			companyPersistence.update(company, false);

			updateCompany(
				companyId, virtualHost, mx, homeURL, name, legalName, legalId,
				legalType, sicCode, tickerSymbol, industry, type, size);

			// Lucene

			LuceneUtil.checkLuceneDir(company.getCompanyId());

			// Demo settings

			if (webId.equals("liferay.net")) {
				company = companyPersistence.findByWebId(webId);

				company.setVirtualHost("demo.liferay.net");

				companyPersistence.update(company, false);

				updateSecurity(
					companyId, CompanyConstants.AUTH_TYPE_EA, true, true, true,
					true, false, true);

				PortletPreferences preferences = PrefsPropsUtil.getPreferences(
					companyId);

				try {
					preferences.setValue(
						PropsKeys.ADMIN_EMAIL_FROM_NAME, "Liferay Demo");
					preferences.setValue(
						PropsKeys.ADMIN_EMAIL_FROM_ADDRESS, "test@liferay.net");

					preferences.store();
				}
				catch (IOException ioe) {
					throw new SystemException(ioe);
				}
				catch (PortletException pe) {
					throw new SystemException(pe);
				}
			}
		}
		else {

			// Lucene

			LuceneUtil.checkLuceneDir(company.getCompanyId());
		}

		long companyId = company.getCompanyId();

		// Key

		checkCompanyKey(companyId);

		// Default user

		User defaultUser = null;

		try {
			defaultUser = userLocalService.getDefaultUser(companyId);

			if (!defaultUser.isAgreedToTermsOfUse()) {
				defaultUser.setAgreedToTermsOfUse(true);

				userPersistence.update(defaultUser, false);
			}
		}
		catch (NoSuchUserException nsue) {
			long userId = counterLocalService.increment();

			defaultUser = userPersistence.create(userId);

			defaultUser.setCompanyId(companyId);
			defaultUser.setCreateDate(now);
			defaultUser.setModifiedDate(now);
			defaultUser.setDefaultUser(true);
			defaultUser.setContactId(counterLocalService.increment());
			defaultUser.setPassword("password");
			defaultUser.setScreenName(String.valueOf(defaultUser.getUserId()));
			defaultUser.setEmailAddress("default@" + company.getMx());
			defaultUser.setLanguageId(LocaleUtil.getDefault().toString());
			defaultUser.setTimeZoneId(TimeZoneUtil.getDefault().getID());
			defaultUser.setGreeting(
				LanguageUtil.format(
					companyId, defaultUser.getLocale(), "welcome-x",
					StringPool.BLANK, false));
			defaultUser.setLoginDate(now);
			defaultUser.setFailedLoginAttempts(0);
			defaultUser.setAgreedToTermsOfUse(true);
			defaultUser.setActive(true);

			userPersistence.update(defaultUser, false);

			// Contact

			Contact defaultContact = contactPersistence.create(
				defaultUser.getContactId());

			defaultContact.setCompanyId(defaultUser.getCompanyId());
			defaultContact.setUserId(defaultUser.getUserId());
			defaultContact.setUserName(StringPool.BLANK);
			defaultContact.setCreateDate(now);
			defaultContact.setModifiedDate(now);
			defaultContact.setAccountId(company.getAccountId());
			defaultContact.setParentContactId(
				ContactConstants.DEFAULT_PARENT_CONTACT_ID);
			defaultContact.setFirstName(StringPool.BLANK);
			defaultContact.setMiddleName(StringPool.BLANK);
			defaultContact.setLastName(StringPool.BLANK);
			defaultContact.setMale(true);
			defaultContact.setBirthday(now);

			contactPersistence.update(defaultContact, false);
		}

		// System roles

		roleLocalService.checkSystemRoles(companyId);

		// System groups

		groupLocalService.checkSystemGroups(companyId);

		// Default password policy

		passwordPolicyLocalService.checkDefaultPasswordPolicy(companyId);

		// Default user must have the Guest role

		Role guestRole = roleLocalService.getRole(
			companyId, RoleConstants.GUEST);

		roleLocalService.setUserRoles(
			defaultUser.getUserId(), new long[] {guestRole.getRoleId()});

		// Default admin

		if (userPersistence.countByCompanyId(companyId) == 1) {
			long creatorUserId = 0;
			boolean autoPassword = false;
			String password1 = PropsValues.DEFAULT_ADMIN_PASSWORD;
			String password2 = password1;
			boolean autoScreenName = false;
			String screenName = PropsValues.DEFAULT_ADMIN_SCREEN_NAME;
			String emailAddress =
				PropsValues.DEFAULT_ADMIN_EMAIL_ADDRESS_PREFIX + "@" + mx;
			String openId = StringPool.BLANK;
			Locale locale = defaultUser.getLocale();
			String firstName = PropsValues.DEFAULT_ADMIN_FIRST_NAME;
			String middleName = PropsValues.DEFAULT_ADMIN_MIDDLE_NAME;
			String lastName = PropsValues.DEFAULT_ADMIN_LAST_NAME;
			int prefixId = 0;
			int suffixId = 0;
			boolean male = true;
			int birthdayMonth = Calendar.JANUARY;
			int birthdayDay = 1;
			int birthdayYear = 1970;
			String jobTitle = StringPool.BLANK;

			Group guestGroup = groupLocalService.getGroup(
				companyId, GroupConstants.GUEST);

			long[] groupIds = new long[] {guestGroup.getGroupId()};

			long[] organizationIds = null;

			Role adminRole = roleLocalService.getRole(
				companyId, RoleConstants.ADMINISTRATOR);

			Role powerUserRole = roleLocalService.getRole(
				companyId, RoleConstants.POWER_USER);

			long[] roleIds = new long[] {
				adminRole.getRoleId(), powerUserRole.getRoleId()
			};

			long[] userGroupIds = null;
			boolean sendEmail = false;
			ServiceContext serviceContext = new ServiceContext();

			userLocalService.addUser(
				creatorUserId, companyId, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, openId, locale,
				firstName, middleName, lastName, prefixId, suffixId, male,
				birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
				organizationIds, roleIds, userGroupIds, sendEmail,
				serviceContext);
		}

		return company;
	}

	public void checkCompanyKey(long companyId)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		if (company.getKeyObj() == null) {
			try {
				company.setKeyObj(Encryptor.generateKey());
			}
			catch (EncryptorException ee) {
				throw new SystemException(ee);
			}
		}

		companyPersistence.update(company, false);
	}

	public List<Company> getCompanies() throws SystemException {
		return companyPersistence.findAll();
	}

	public Company getCompanyById(long companyId)
		throws PortalException, SystemException {

		return companyPersistence.findByPrimaryKey(companyId);
	}

	public Company getCompanyByLogoId(long logoId)
		throws PortalException, SystemException {

		return companyPersistence.findByLogoId(logoId);
	}

	public Company getCompanyByMx(String mx)
		throws PortalException, SystemException {

		return companyPersistence.findByMx(mx);
	}

	public Company getCompanyByVirtualHost(String virtualHost)
		throws PortalException, SystemException {

		virtualHost = virtualHost.trim().toLowerCase();

		return companyPersistence.findByVirtualHost(virtualHost);
	}

	public Company getCompanyByWebId(String webId)
		throws PortalException, SystemException {

		return companyPersistence.findByWebId(webId);
	}

	public Hits search(
			long companyId, long userId, String keywords, int start, int end)
		throws SystemException {

		return search(companyId, userId, null, 0, null, keywords, start, end);
	}

	public Hits search(
			long companyId, long userId, String portletId, long groupId,
			String type, String keywords, int start, int end)
		throws SystemException {

		try {
			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create();

			contextQuery.addRequiredTerm(Field.COMPANY_ID, companyId);

			if (Validator.isNotNull(portletId)) {
				contextQuery.addRequiredTerm(Field.PORTLET_ID, portletId);
			}

			if (groupId > 0) {
				contextQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			}

			if (Validator.isNotNull(type)) {
				contextQuery.addRequiredTerm(Field.TYPE, type);
			}

			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			if (Validator.isNotNull(keywords)) {
				searchQuery.addTerm(Field.TITLE, keywords);
				searchQuery.addTerm(Field.CONTENT, keywords);
				searchQuery.addTerm(Field.DESCRIPTION, keywords);
				searchQuery.addTerm(Field.PROPERTIES, keywords);
				searchQuery.addTerm(Field.TAGS_CATEGORIES, keywords);
				searchQuery.addTerm(Field.TAGS_ENTRIES, keywords);
			}

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			if (searchQuery.clauses().size() > 0) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			return SearchEngineUtil.search(
				companyId, groupId, userId, null, fullQuery, start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public Company updateCompany(long companyId, String virtualHost, String mx)
		throws PortalException, SystemException {

		virtualHost = virtualHost.trim().toLowerCase();

		Company company = companyPersistence.findByPrimaryKey(companyId);

		validate(company.getWebId(), virtualHost, mx);

		company.setVirtualHost(virtualHost);

		if (PropsValues.MAIL_MX_UPDATE) {
			company.setMx(mx);
		}

		companyPersistence.update(company, false);

		return company;
	}

	public Company updateCompany(
			long companyId, String virtualHost, String mx, String homeURL,
			String name, String legalName, String legalId, String legalType,
			String sicCode, String tickerSymbol, String industry, String type,
			String size)
		throws PortalException, SystemException {

		// Company

		virtualHost = virtualHost.trim().toLowerCase();
		Date now = new Date();

		Company company = companyPersistence.findByPrimaryKey(companyId);

		validate(company.getWebId(), virtualHost, mx);
		validate(name);

		company.setVirtualHost(virtualHost);

		if (PropsValues.MAIL_MX_UPDATE) {
			company.setMx(mx);
		}

		company.setHomeURL(homeURL);

		companyPersistence.update(company, false);

		// Account

		Account account = accountPersistence.fetchByPrimaryKey(
			company.getAccountId());

		if (account == null) {
			long accountId = counterLocalService.increment();

			account = accountPersistence.create(accountId);

			account.setCreateDate(now);
			account.setCompanyId(companyId);
			account.setUserId(0);
			account.setUserName(StringPool.BLANK);

			company.setAccountId(accountId);

			companyPersistence.update(company, false);
		}

		account.setModifiedDate(now);
		account.setName(name);
		account.setLegalName(legalName);
		account.setLegalId(legalId);
		account.setLegalType(legalType);
		account.setSicCode(sicCode);
		account.setTickerSymbol(tickerSymbol);
		account.setIndustry(industry);
		account.setType(type);
		account.setSize(size);

		accountPersistence.update(account, false);

		return company;
	}

	public void updateDisplay(
			long companyId, String languageId, String timeZoneId)
		throws PortalException, SystemException {

		User user = userLocalService.getDefaultUser(companyId);

		user.setLanguageId(languageId);
		user.setTimeZoneId(timeZoneId);

		userPersistence.update(user, false);
	}

	public void updateLogo(long companyId, byte[] bytes)
		throws PortalException, SystemException {

		long logoId = getLogoId(companyId);

		imageLocalService.updateImage(logoId, bytes);
	}

	public void updateLogo(long companyId, File file)
		throws PortalException, SystemException {

		long logoId = getLogoId(companyId);

		imageLocalService.updateImage(logoId, file);
	}

	public void updateLogo(long companyId, InputStream is)
		throws PortalException, SystemException {

		long logoId = getLogoId(companyId);

		imageLocalService.updateImage(logoId, is);
	}

	public void updatePreferences(long companyId, UnicodeProperties properties)
		throws SystemException {

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			companyId);

		try {
			for (String key : properties.keySet()) {
				String value = properties.getProperty(key);

				preferences.setValue(key, value);
			}

			preferences.store();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public void updateSecurity(
			long companyId, String authType, boolean autoLogin,
			boolean sendPassword, boolean strangers, boolean strangersWithMx,
			boolean strangersVerify, boolean communityLogo)
		throws SystemException {

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			companyId);

		try {
			preferences.setValue(
				PropsKeys.COMPANY_SECURITY_AUTH_TYPE, authType);
			preferences.setValue(
				PropsKeys.COMPANY_SECURITY_AUTO_LOGIN,
				String.valueOf(autoLogin));
			preferences.setValue(
				PropsKeys.COMPANY_SECURITY_SEND_PASSWORD,
				String.valueOf(sendPassword));
			preferences.setValue(
				PropsKeys.COMPANY_SECURITY_STRANGERS,
				String.valueOf(strangers));
			preferences.setValue(
				PropsKeys.COMPANY_SECURITY_STRANGERS_WITH_MX,
				String.valueOf(strangersWithMx));
			preferences.setValue(
				PropsKeys.COMPANY_SECURITY_STRANGERS_VERIFY,
				String.valueOf(strangersVerify));
			preferences.setValue(
				PropsKeys.COMPANY_SECURITY_COMMUNITY_LOGO,
				String.valueOf(communityLogo));

			preferences.store();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (PortletException pe) {
			throw new SystemException(pe);
		}
	}

	protected long getLogoId(long companyId)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		long logoId = company.getLogoId();

		if (logoId <= 0) {
			logoId = counterLocalService.increment();

			company.setLogoId(logoId);

			companyPersistence.update(company, false);
		}

		return logoId;
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new AccountNameException();
		}
	}

	protected void validate(String webId, String virtualHost, String mx)
		throws PortalException, SystemException {

		if (Validator.isNull(virtualHost)) {
			throw new CompanyVirtualHostException();
		}
		else if (virtualHost.equals(_DEFAULT_VIRTUAL_HOST) &&
				 !webId.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {

			throw new CompanyVirtualHostException();
		}
		else if (!Validator.isDomain(virtualHost)) {
			throw new CompanyVirtualHostException();
		}
		else {
			try {
				Company virtualHostCompany = getCompanyByVirtualHost(
					virtualHost);

				if ((virtualHostCompany != null) &&
					(!virtualHostCompany.getWebId().equals(webId))) {

					throw new CompanyVirtualHostException();
				}
			}
			catch (NoSuchCompanyException nsce) {
			}

			try {
				layoutSetLocalService.getLayoutSet(virtualHost);

				throw new CompanyVirtualHostException();
			}
			catch (NoSuchLayoutSetException nslse) {
			}
		}

		if (Validator.isNull(mx)) {
			throw new CompanyMxException();
		}
		else if (!Validator.isDomain(mx)) {
			throw new CompanyMxException();
		}
	}

	private static final String _DEFAULT_VIRTUAL_HOST = "localhost";

}