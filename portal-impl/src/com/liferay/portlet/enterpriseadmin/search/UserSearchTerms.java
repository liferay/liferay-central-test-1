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

package com.liferay.portlet.enterpriseadmin.search;

import com.liferay.portal.kernel.dao.search.DAOParamUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletRequest;

/**
 * <a href="UserSearchTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UserSearchTerms extends UserDisplayTerms {

	public UserSearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		firstName = DAOParamUtil.getLike(portletRequest, FIRST_NAME);
		middleName = DAOParamUtil.getLike(portletRequest, MIDDLE_NAME);
		lastName = DAOParamUtil.getLike(portletRequest, LAST_NAME);
		screenName = DAOParamUtil.getLike(portletRequest, SCREEN_NAME);
		emailAddress = DAOParamUtil.getLike(portletRequest, EMAIL_ADDRESS);

		if (Validator.isNotNull(ParamUtil.getString(portletRequest, ACTIVE))) {
			active = ParamUtil.getBoolean(portletRequest, ACTIVE, true);
		}

		organizationId = ParamUtil.getLong(portletRequest, ORGANIZATION_ID);
		roleId = ParamUtil.getLong(portletRequest, ROLE_ID);
		userGroupId = ParamUtil.getLong(portletRequest, USER_GROUP_ID);
	}

}