/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.googleapps;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;

import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class GAuthenticator {

	public GAuthenticator(long companyId) {
		_companyId = companyId;

		init(true);
	}

	public String getAuthenticationToken() {
		init(false);

		return _authenticationToken;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getDomain() {
		return _domain;
	}

	public void init(boolean manual) {
		if (manual || isStale()) {
			try {
				doInit();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected void doInit() throws Exception {
		Company company = CompanyLocalServiceUtil.getCompany(_companyId);

		_domain = company.getMx();
		_userName = PrefsPropsUtil.getString(
			_companyId, PropsKeys.GOOGLE_APPS_USERNAME);
		_password = PrefsPropsUtil.getString(
			_companyId, PropsKeys.GOOGLE_APPS_PASSWORD);

		if (!_userName.contains(StringPool.AT)) {
			_userName += StringPool.AT + _domain;
		}

		Http.Options options = new Http.Options();

		options.addPart("Email", _userName);
		options.addPart("Passwd", _password);
		options.addPart("accountType", "HOSTED");
		options.addPart("service", "apps");
		options.setLocation("https://www.google.com/accounts/ClientLogin");
		options.setPost(true);

		String content = HttpUtil.URLtoString(options);

		Properties properties = PropertiesUtil.load(content);

		_authenticationToken = properties.getProperty("Auth");

		_initTime = System.currentTimeMillis();
	}

	private boolean isStale() {
		if ((_initTime + (Time.HOUR * 23)) > System.currentTimeMillis()) {
			return false;
		}
		else {
			return true;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(GAuthenticator.class);

	private String _authenticationToken;
	private long _companyId;
	private String _domain;
	private long _initTime;
	private String _password;
	private String _userName;

}