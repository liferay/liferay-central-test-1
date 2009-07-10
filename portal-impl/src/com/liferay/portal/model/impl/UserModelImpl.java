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

package com.liferay.portal.model.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserSoap;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="UserModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>User</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.model.User
 * @see com.liferay.portal.model.UserModel
 * @see com.liferay.portal.model.impl.UserImpl
 *
 */
public class UserModelImpl extends BaseModelImpl<User> {
	public static final String TABLE_NAME = "User_";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "defaultUser", new Integer(Types.BOOLEAN) },
			

			{ "contactId", new Integer(Types.BIGINT) },
			

			{ "password_", new Integer(Types.VARCHAR) },
			

			{ "passwordEncrypted", new Integer(Types.BOOLEAN) },
			

			{ "passwordReset", new Integer(Types.BOOLEAN) },
			

			{ "passwordModifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "reminderQueryQuestion", new Integer(Types.VARCHAR) },
			

			{ "reminderQueryAnswer", new Integer(Types.VARCHAR) },
			

			{ "graceLoginCount", new Integer(Types.INTEGER) },
			

			{ "screenName", new Integer(Types.VARCHAR) },
			

			{ "emailAddress", new Integer(Types.VARCHAR) },
			

			{ "openId", new Integer(Types.VARCHAR) },
			

			{ "portraitId", new Integer(Types.BIGINT) },
			

			{ "languageId", new Integer(Types.VARCHAR) },
			

			{ "timeZoneId", new Integer(Types.VARCHAR) },
			

			{ "greeting", new Integer(Types.VARCHAR) },
			

			{ "comments", new Integer(Types.VARCHAR) },
			

			{ "firstName", new Integer(Types.VARCHAR) },
			

			{ "middleName", new Integer(Types.VARCHAR) },
			

			{ "lastName", new Integer(Types.VARCHAR) },
			

			{ "jobTitle", new Integer(Types.VARCHAR) },
			

			{ "loginDate", new Integer(Types.TIMESTAMP) },
			

			{ "loginIP", new Integer(Types.VARCHAR) },
			

			{ "lastLoginDate", new Integer(Types.TIMESTAMP) },
			

			{ "lastLoginIP", new Integer(Types.VARCHAR) },
			

			{ "lastFailedLoginDate", new Integer(Types.TIMESTAMP) },
			

			{ "failedLoginAttempts", new Integer(Types.INTEGER) },
			

			{ "lockout", new Integer(Types.BOOLEAN) },
			

			{ "lockoutDate", new Integer(Types.TIMESTAMP) },
			

			{ "agreedToTermsOfUse", new Integer(Types.BOOLEAN) },
			

			{ "active_", new Integer(Types.BOOLEAN) }
		};
	public static final String TABLE_SQL_CREATE = "create table User_ (uuid_ VARCHAR(75) null,userId LONG not null primary key,companyId LONG,createDate DATE null,modifiedDate DATE null,defaultUser BOOLEAN,contactId LONG,password_ VARCHAR(75) null,passwordEncrypted BOOLEAN,passwordReset BOOLEAN,passwordModifiedDate DATE null,reminderQueryQuestion VARCHAR(75) null,reminderQueryAnswer VARCHAR(75) null,graceLoginCount INTEGER,screenName VARCHAR(75) null,emailAddress VARCHAR(75) null,openId VARCHAR(1024) null,portraitId LONG,languageId VARCHAR(75) null,timeZoneId VARCHAR(75) null,greeting VARCHAR(255) null,comments STRING null,firstName VARCHAR(75) null,middleName VARCHAR(75) null,lastName VARCHAR(75) null,jobTitle VARCHAR(100) null,loginDate DATE null,loginIP VARCHAR(75) null,lastLoginDate DATE null,lastLoginIP VARCHAR(75) null,lastFailedLoginDate DATE null,failedLoginAttempts INTEGER,lockout BOOLEAN,lockoutDate DATE null,agreedToTermsOfUse BOOLEAN,active_ BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table User_";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.User"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.User"),
			true);

	public static User toModel(UserSoap soapModel) {
		User model = new UserImpl();

		model.setUuid(soapModel.getUuid());
		model.setUserId(soapModel.getUserId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setDefaultUser(soapModel.getDefaultUser());
		model.setContactId(soapModel.getContactId());
		model.setPassword(soapModel.getPassword());
		model.setPasswordEncrypted(soapModel.getPasswordEncrypted());
		model.setPasswordReset(soapModel.getPasswordReset());
		model.setPasswordModifiedDate(soapModel.getPasswordModifiedDate());
		model.setReminderQueryQuestion(soapModel.getReminderQueryQuestion());
		model.setReminderQueryAnswer(soapModel.getReminderQueryAnswer());
		model.setGraceLoginCount(soapModel.getGraceLoginCount());
		model.setScreenName(soapModel.getScreenName());
		model.setEmailAddress(soapModel.getEmailAddress());
		model.setOpenId(soapModel.getOpenId());
		model.setPortraitId(soapModel.getPortraitId());
		model.setLanguageId(soapModel.getLanguageId());
		model.setTimeZoneId(soapModel.getTimeZoneId());
		model.setGreeting(soapModel.getGreeting());
		model.setComments(soapModel.getComments());
		model.setFirstName(soapModel.getFirstName());
		model.setMiddleName(soapModel.getMiddleName());
		model.setLastName(soapModel.getLastName());
		model.setJobTitle(soapModel.getJobTitle());
		model.setLoginDate(soapModel.getLoginDate());
		model.setLoginIP(soapModel.getLoginIP());
		model.setLastLoginDate(soapModel.getLastLoginDate());
		model.setLastLoginIP(soapModel.getLastLoginIP());
		model.setLastFailedLoginDate(soapModel.getLastFailedLoginDate());
		model.setFailedLoginAttempts(soapModel.getFailedLoginAttempts());
		model.setLockout(soapModel.getLockout());
		model.setLockoutDate(soapModel.getLockoutDate());
		model.setAgreedToTermsOfUse(soapModel.getAgreedToTermsOfUse());
		model.setActive(soapModel.getActive());

		return model;
	}

	public static List<User> toModels(UserSoap[] soapModels) {
		List<User> models = new ArrayList<User>(soapModels.length);

		for (UserSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final boolean FINDER_CACHE_ENABLED_USERS_GROUPS = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Users_Groups"), true);
	public static final boolean FINDER_CACHE_ENABLED_USERS_ORGS = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Users_Orgs"), true);
	public static final boolean FINDER_CACHE_ENABLED_USERS_PERMISSIONS = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Users_Permissions"), true);
	public static final boolean FINDER_CACHE_ENABLED_USERS_ROLES = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Users_Roles"), true);
	public static final boolean FINDER_CACHE_ENABLED_USERS_USERGROUPS = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Users_UserGroups"), true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.User"));

	public UserModelImpl() {
	}

	public long getPrimaryKey() {
		return _userId;
	}

	public void setPrimaryKey(long pk) {
		setUserId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_userId);
	}

	public String getUuid() {
		return GetterUtil.getString(_uuid);
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = userId;
		}
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = companyId;
		}
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public boolean getDefaultUser() {
		return _defaultUser;
	}

	public boolean isDefaultUser() {
		return _defaultUser;
	}

	public void setDefaultUser(boolean defaultUser) {
		_defaultUser = defaultUser;

		if (!_setOriginalDefaultUser) {
			_setOriginalDefaultUser = true;

			_originalDefaultUser = defaultUser;
		}
	}

	public boolean getOriginalDefaultUser() {
		return _originalDefaultUser;
	}

	public long getContactId() {
		return _contactId;
	}

	public void setContactId(long contactId) {
		_contactId = contactId;

		if (!_setOriginalContactId) {
			_setOriginalContactId = true;

			_originalContactId = contactId;
		}
	}

	public long getOriginalContactId() {
		return _originalContactId;
	}

	public String getPassword() {
		return GetterUtil.getString(_password);
	}

	public void setPassword(String password) {
		_password = password;
	}

	public boolean getPasswordEncrypted() {
		return _passwordEncrypted;
	}

	public boolean isPasswordEncrypted() {
		return _passwordEncrypted;
	}

	public void setPasswordEncrypted(boolean passwordEncrypted) {
		_passwordEncrypted = passwordEncrypted;
	}

	public boolean getPasswordReset() {
		return _passwordReset;
	}

	public boolean isPasswordReset() {
		return _passwordReset;
	}

	public void setPasswordReset(boolean passwordReset) {
		_passwordReset = passwordReset;
	}

	public Date getPasswordModifiedDate() {
		return _passwordModifiedDate;
	}

	public void setPasswordModifiedDate(Date passwordModifiedDate) {
		_passwordModifiedDate = passwordModifiedDate;
	}

	public String getReminderQueryQuestion() {
		return GetterUtil.getString(_reminderQueryQuestion);
	}

	public void setReminderQueryQuestion(String reminderQueryQuestion) {
		_reminderQueryQuestion = reminderQueryQuestion;
	}

	public String getReminderQueryAnswer() {
		return GetterUtil.getString(_reminderQueryAnswer);
	}

	public void setReminderQueryAnswer(String reminderQueryAnswer) {
		_reminderQueryAnswer = reminderQueryAnswer;
	}

	public int getGraceLoginCount() {
		return _graceLoginCount;
	}

	public void setGraceLoginCount(int graceLoginCount) {
		_graceLoginCount = graceLoginCount;
	}

	public String getScreenName() {
		return GetterUtil.getString(_screenName);
	}

	public void setScreenName(String screenName) {
		_screenName = screenName;

		if (_originalScreenName == null) {
			_originalScreenName = screenName;
		}
	}

	public String getOriginalScreenName() {
		return GetterUtil.getString(_originalScreenName);
	}

	public String getEmailAddress() {
		return GetterUtil.getString(_emailAddress);
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;

		if (_originalEmailAddress == null) {
			_originalEmailAddress = emailAddress;
		}
	}

	public String getOriginalEmailAddress() {
		return GetterUtil.getString(_originalEmailAddress);
	}

	public String getOpenId() {
		return GetterUtil.getString(_openId);
	}

	public void setOpenId(String openId) {
		_openId = openId;

		if (_originalOpenId == null) {
			_originalOpenId = openId;
		}
	}

	public String getOriginalOpenId() {
		return GetterUtil.getString(_originalOpenId);
	}

	public long getPortraitId() {
		return _portraitId;
	}

	public void setPortraitId(long portraitId) {
		_portraitId = portraitId;

		if (!_setOriginalPortraitId) {
			_setOriginalPortraitId = true;

			_originalPortraitId = portraitId;
		}
	}

	public long getOriginalPortraitId() {
		return _originalPortraitId;
	}

	public String getLanguageId() {
		return GetterUtil.getString(_languageId);
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public String getTimeZoneId() {
		return GetterUtil.getString(_timeZoneId);
	}

	public void setTimeZoneId(String timeZoneId) {
		_timeZoneId = timeZoneId;
	}

	public String getGreeting() {
		return GetterUtil.getString(_greeting);
	}

	public void setGreeting(String greeting) {
		_greeting = greeting;
	}

	public String getComments() {
		return GetterUtil.getString(_comments);
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public String getFirstName() {
		return GetterUtil.getString(_firstName);
	}

	public void setFirstName(String firstName) {
		_firstName = firstName;
	}

	public String getMiddleName() {
		return GetterUtil.getString(_middleName);
	}

	public void setMiddleName(String middleName) {
		_middleName = middleName;
	}

	public String getLastName() {
		return GetterUtil.getString(_lastName);
	}

	public void setLastName(String lastName) {
		_lastName = lastName;
	}

	public String getJobTitle() {
		return GetterUtil.getString(_jobTitle);
	}

	public void setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	public Date getLoginDate() {
		return _loginDate;
	}

	public void setLoginDate(Date loginDate) {
		_loginDate = loginDate;
	}

	public String getLoginIP() {
		return GetterUtil.getString(_loginIP);
	}

	public void setLoginIP(String loginIP) {
		_loginIP = loginIP;
	}

	public Date getLastLoginDate() {
		return _lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		_lastLoginDate = lastLoginDate;
	}

	public String getLastLoginIP() {
		return GetterUtil.getString(_lastLoginIP);
	}

	public void setLastLoginIP(String lastLoginIP) {
		_lastLoginIP = lastLoginIP;
	}

	public Date getLastFailedLoginDate() {
		return _lastFailedLoginDate;
	}

	public void setLastFailedLoginDate(Date lastFailedLoginDate) {
		_lastFailedLoginDate = lastFailedLoginDate;
	}

	public int getFailedLoginAttempts() {
		return _failedLoginAttempts;
	}

	public void setFailedLoginAttempts(int failedLoginAttempts) {
		_failedLoginAttempts = failedLoginAttempts;
	}

	public boolean getLockout() {
		return _lockout;
	}

	public boolean isLockout() {
		return _lockout;
	}

	public void setLockout(boolean lockout) {
		_lockout = lockout;
	}

	public Date getLockoutDate() {
		return _lockoutDate;
	}

	public void setLockoutDate(Date lockoutDate) {
		_lockoutDate = lockoutDate;
	}

	public boolean getAgreedToTermsOfUse() {
		return _agreedToTermsOfUse;
	}

	public boolean isAgreedToTermsOfUse() {
		return _agreedToTermsOfUse;
	}

	public void setAgreedToTermsOfUse(boolean agreedToTermsOfUse) {
		_agreedToTermsOfUse = agreedToTermsOfUse;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public User toEscapedModel() {
		if (isEscapedModel()) {
			return (User)this;
		}
		else {
			User model = new UserImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUuid(HtmlUtil.escape(getUuid()));
			model.setUserId(getUserId());
			model.setCompanyId(getCompanyId());
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setDefaultUser(getDefaultUser());
			model.setContactId(getContactId());
			model.setPassword(HtmlUtil.escape(getPassword()));
			model.setPasswordEncrypted(getPasswordEncrypted());
			model.setPasswordReset(getPasswordReset());
			model.setPasswordModifiedDate(getPasswordModifiedDate());
			model.setReminderQueryQuestion(HtmlUtil.escape(
					getReminderQueryQuestion()));
			model.setReminderQueryAnswer(HtmlUtil.escape(
					getReminderQueryAnswer()));
			model.setGraceLoginCount(getGraceLoginCount());
			model.setScreenName(HtmlUtil.escape(getScreenName()));
			model.setEmailAddress(HtmlUtil.escape(getEmailAddress()));
			model.setOpenId(HtmlUtil.escape(getOpenId()));
			model.setPortraitId(getPortraitId());
			model.setLanguageId(HtmlUtil.escape(getLanguageId()));
			model.setTimeZoneId(HtmlUtil.escape(getTimeZoneId()));
			model.setGreeting(HtmlUtil.escape(getGreeting()));
			model.setComments(HtmlUtil.escape(getComments()));
			model.setFirstName(HtmlUtil.escape(getFirstName()));
			model.setMiddleName(HtmlUtil.escape(getMiddleName()));
			model.setLastName(HtmlUtil.escape(getLastName()));
			model.setJobTitle(HtmlUtil.escape(getJobTitle()));
			model.setLoginDate(getLoginDate());
			model.setLoginIP(HtmlUtil.escape(getLoginIP()));
			model.setLastLoginDate(getLastLoginDate());
			model.setLastLoginIP(HtmlUtil.escape(getLastLoginIP()));
			model.setLastFailedLoginDate(getLastFailedLoginDate());
			model.setFailedLoginAttempts(getFailedLoginAttempts());
			model.setLockout(getLockout());
			model.setLockoutDate(getLockoutDate());
			model.setAgreedToTermsOfUse(getAgreedToTermsOfUse());
			model.setActive(getActive());

			model = (User)Proxy.newProxyInstance(User.class.getClassLoader(),
					new Class[] { User.class }, new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(User.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		UserImpl clone = new UserImpl();

		clone.setUuid(getUuid());
		clone.setUserId(getUserId());
		clone.setCompanyId(getCompanyId());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setDefaultUser(getDefaultUser());
		clone.setContactId(getContactId());
		clone.setPassword(getPassword());
		clone.setPasswordEncrypted(getPasswordEncrypted());
		clone.setPasswordReset(getPasswordReset());
		clone.setPasswordModifiedDate(getPasswordModifiedDate());
		clone.setReminderQueryQuestion(getReminderQueryQuestion());
		clone.setReminderQueryAnswer(getReminderQueryAnswer());
		clone.setGraceLoginCount(getGraceLoginCount());
		clone.setScreenName(getScreenName());
		clone.setEmailAddress(getEmailAddress());
		clone.setOpenId(getOpenId());
		clone.setPortraitId(getPortraitId());
		clone.setLanguageId(getLanguageId());
		clone.setTimeZoneId(getTimeZoneId());
		clone.setGreeting(getGreeting());
		clone.setComments(getComments());
		clone.setFirstName(getFirstName());
		clone.setMiddleName(getMiddleName());
		clone.setLastName(getLastName());
		clone.setJobTitle(getJobTitle());
		clone.setLoginDate(getLoginDate());
		clone.setLoginIP(getLoginIP());
		clone.setLastLoginDate(getLastLoginDate());
		clone.setLastLoginIP(getLastLoginIP());
		clone.setLastFailedLoginDate(getLastFailedLoginDate());
		clone.setFailedLoginAttempts(getFailedLoginAttempts());
		clone.setLockout(getLockout());
		clone.setLockoutDate(getLockoutDate());
		clone.setAgreedToTermsOfUse(getAgreedToTermsOfUse());
		clone.setActive(getActive());

		return clone;
	}

	public int compareTo(User user) {
		long pk = user.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		User user = null;

		try {
			user = (User)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = user.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", defaultUser=");
		sb.append(getDefaultUser());
		sb.append(", contactId=");
		sb.append(getContactId());
		sb.append(", password=");
		sb.append(getPassword());
		sb.append(", passwordEncrypted=");
		sb.append(getPasswordEncrypted());
		sb.append(", passwordReset=");
		sb.append(getPasswordReset());
		sb.append(", passwordModifiedDate=");
		sb.append(getPasswordModifiedDate());
		sb.append(", reminderQueryQuestion=");
		sb.append(getReminderQueryQuestion());
		sb.append(", reminderQueryAnswer=");
		sb.append(getReminderQueryAnswer());
		sb.append(", graceLoginCount=");
		sb.append(getGraceLoginCount());
		sb.append(", screenName=");
		sb.append(getScreenName());
		sb.append(", emailAddress=");
		sb.append(getEmailAddress());
		sb.append(", openId=");
		sb.append(getOpenId());
		sb.append(", portraitId=");
		sb.append(getPortraitId());
		sb.append(", languageId=");
		sb.append(getLanguageId());
		sb.append(", timeZoneId=");
		sb.append(getTimeZoneId());
		sb.append(", greeting=");
		sb.append(getGreeting());
		sb.append(", comments=");
		sb.append(getComments());
		sb.append(", firstName=");
		sb.append(getFirstName());
		sb.append(", middleName=");
		sb.append(getMiddleName());
		sb.append(", lastName=");
		sb.append(getLastName());
		sb.append(", jobTitle=");
		sb.append(getJobTitle());
		sb.append(", loginDate=");
		sb.append(getLoginDate());
		sb.append(", loginIP=");
		sb.append(getLoginIP());
		sb.append(", lastLoginDate=");
		sb.append(getLastLoginDate());
		sb.append(", lastLoginIP=");
		sb.append(getLastLoginIP());
		sb.append(", lastFailedLoginDate=");
		sb.append(getLastFailedLoginDate());
		sb.append(", failedLoginAttempts=");
		sb.append(getFailedLoginAttempts());
		sb.append(", lockout=");
		sb.append(getLockout());
		sb.append(", lockoutDate=");
		sb.append(getLockoutDate());
		sb.append(", agreedToTermsOfUse=");
		sb.append(getAgreedToTermsOfUse());
		sb.append(", active=");
		sb.append(getActive());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.User");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>defaultUser</column-name><column-value><![CDATA[");
		sb.append(getDefaultUser());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>contactId</column-name><column-value><![CDATA[");
		sb.append(getContactId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>password</column-name><column-value><![CDATA[");
		sb.append(getPassword());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>passwordEncrypted</column-name><column-value><![CDATA[");
		sb.append(getPasswordEncrypted());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>passwordReset</column-name><column-value><![CDATA[");
		sb.append(getPasswordReset());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>passwordModifiedDate</column-name><column-value><![CDATA[");
		sb.append(getPasswordModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>reminderQueryQuestion</column-name><column-value><![CDATA[");
		sb.append(getReminderQueryQuestion());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>reminderQueryAnswer</column-name><column-value><![CDATA[");
		sb.append(getReminderQueryAnswer());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>graceLoginCount</column-name><column-value><![CDATA[");
		sb.append(getGraceLoginCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>screenName</column-name><column-value><![CDATA[");
		sb.append(getScreenName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>emailAddress</column-name><column-value><![CDATA[");
		sb.append(getEmailAddress());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>openId</column-name><column-value><![CDATA[");
		sb.append(getOpenId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>portraitId</column-name><column-value><![CDATA[");
		sb.append(getPortraitId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>languageId</column-name><column-value><![CDATA[");
		sb.append(getLanguageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>timeZoneId</column-name><column-value><![CDATA[");
		sb.append(getTimeZoneId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>greeting</column-name><column-value><![CDATA[");
		sb.append(getGreeting());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>comments</column-name><column-value><![CDATA[");
		sb.append(getComments());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>firstName</column-name><column-value><![CDATA[");
		sb.append(getFirstName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>middleName</column-name><column-value><![CDATA[");
		sb.append(getMiddleName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastName</column-name><column-value><![CDATA[");
		sb.append(getLastName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>jobTitle</column-name><column-value><![CDATA[");
		sb.append(getJobTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>loginDate</column-name><column-value><![CDATA[");
		sb.append(getLoginDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>loginIP</column-name><column-value><![CDATA[");
		sb.append(getLoginIP());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastLoginDate</column-name><column-value><![CDATA[");
		sb.append(getLastLoginDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastLoginIP</column-name><column-value><![CDATA[");
		sb.append(getLastLoginIP());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastFailedLoginDate</column-name><column-value><![CDATA[");
		sb.append(getLastFailedLoginDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>failedLoginAttempts</column-name><column-value><![CDATA[");
		sb.append(getFailedLoginAttempts());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lockout</column-name><column-value><![CDATA[");
		sb.append(getLockout());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lockoutDate</column-name><column-value><![CDATA[");
		sb.append(getLockoutDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>agreedToTermsOfUse</column-name><column-value><![CDATA[");
		sb.append(getAgreedToTermsOfUse());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>active</column-name><column-value><![CDATA[");
		sb.append(getActive());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _defaultUser;
	private boolean _originalDefaultUser;
	private boolean _setOriginalDefaultUser;
	private long _contactId;
	private long _originalContactId;
	private boolean _setOriginalContactId;
	private String _password;
	private boolean _passwordEncrypted;
	private boolean _passwordReset;
	private Date _passwordModifiedDate;
	private String _reminderQueryQuestion;
	private String _reminderQueryAnswer;
	private int _graceLoginCount;
	private String _screenName;
	private String _originalScreenName;
	private String _emailAddress;
	private String _originalEmailAddress;
	private String _openId;
	private String _originalOpenId;
	private long _portraitId;
	private long _originalPortraitId;
	private boolean _setOriginalPortraitId;
	private String _languageId;
	private String _timeZoneId;
	private String _greeting;
	private String _comments;
	private String _firstName;
	private String _middleName;
	private String _lastName;
	private String _jobTitle;
	private Date _loginDate;
	private String _loginIP;
	private Date _lastLoginDate;
	private String _lastLoginIP;
	private Date _lastFailedLoginDate;
	private int _failedLoginAttempts;
	private boolean _lockout;
	private Date _lockoutDate;
	private boolean _agreedToTermsOfUse;
	private boolean _active;
	private transient ExpandoBridge _expandoBridge;
}