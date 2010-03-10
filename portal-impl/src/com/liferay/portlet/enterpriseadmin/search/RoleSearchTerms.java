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
import com.liferay.portal.model.RoleConstants;

import javax.portlet.PortletRequest;

/**
 * <a href="RoleSearchTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class RoleSearchTerms extends RoleDisplayTerms {

	public RoleSearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		name = DAOParamUtil.getLike(portletRequest, NAME);
		description = DAOParamUtil.getLike(portletRequest, DESCRIPTION);
		type = ParamUtil.getInteger(portletRequest, TYPE);
	}

	public Integer[] getTypesObj() {
		if ((type == RoleConstants.TYPE_COMMUNITY) ||
			(type == RoleConstants.TYPE_ORGANIZATION) ||
			(type == RoleConstants.TYPE_REGULAR)) {

			return new Integer[] {type};
		}
		else {
			return new Integer[0];
		}
	}

}