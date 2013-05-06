/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.usergroupsadmin.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.persistence.UserGroupActionableDynamicQuery;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 * @author David Mendez Gonzalez
 */
public class UserGroupsAdminPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "user_groups_admin";

	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		List<UserGroup> userGroups = UserGroupLocalServiceUtil.getUserGroups(
			portletDataContext.getCompanyId());

		for (UserGroup userGroup : userGroups) {
			UserGroupLocalServiceUtil.deleteUserGroup(userGroup);
		}

		return portletPreferences;
	}

	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.usergroupsadmin",
			portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery userGroupActionableDynamicQuery =
			new UserGroupActionableDynamicQuery() {

				@Override
				protected void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

				@Override
				protected void performAction(Object object)
					throws PortalException {

					UserGroup userGroup = (UserGroup)object;

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, userGroup);
				}

			};

		userGroupActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		userGroupActionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.usergroupsadmin",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Element userGroupsElement =
			portletDataContext.getImportDataGroupElement(UserGroup.class);

		List<Element> userGroupElements = userGroupsElement.elements();

		for (Element userGroupElement : userGroupElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, userGroupElement);
		}

		return null;
	}

}