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

package com.liferay.portlet.rolesadmin.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelPathUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Role;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

/**
 * @author David Mendez Gonzalez
 */
public class RoleStagedModelDataHandler
	extends BaseStagedModelDataHandler<Role> {

	public static final String NAMESPACE = "role";

	@Override
	public String getClassName() {
		return Role.class.getName();
	}

	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Role role)
		throws Exception {

		Element roleElement =
			portletDataContext.getExportDataStagedModelElement(role);

		portletDataContext.addClassedModel(
			roleElement, StagedModelPathUtil.getPath(role), role, NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Role role)
		throws Exception {

		long userId = portletDataContext.getUserId(role.getUserUuid());

		long companyId = portletDataContext.getCompanyId();

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			role, NAMESPACE);

		Role importedRole = null;

		if (portletDataContext.isDataStrategyMirror()) {
			Role existingRole =
				RoleLocalServiceUtil.fetchRoleByUuidAndCompanyId(
					role.getUuid(), companyId);

			if (existingRole == null) {
				serviceContext.setUuid(role.getUuid());

				importedRole = RoleLocalServiceUtil.addRole(
					userId, null, 0, role.getName(), role.getTitleMap(),
					role.getDescriptionMap(), role.getType(), role.getSubtype(),
					serviceContext);
			}
			else {
				importedRole = RoleLocalServiceUtil.updateRole(
					existingRole.getRoleId(), role.getName(),
					role.getTitleMap(), role.getDescriptionMap(),
					role.getSubtype(), serviceContext);
			}
		}
		else {
			importedRole = RoleLocalServiceUtil.addRole(
				userId, null, 0, role.getName(), role.getTitleMap(),
				role.getDescriptionMap(), role.getType(), role.getSubtype(),
				serviceContext);
		}

		portletDataContext.importClassedModel(role, importedRole, NAMESPACE);
	}

}