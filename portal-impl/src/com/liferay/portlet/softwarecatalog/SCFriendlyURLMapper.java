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

package com.liferay.portlet.softwarecatalog;

import com.liferay.portal.kernel.portlet.BaseFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

/**
 * <a href="SCFriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class SCFriendlyURLMapper extends BaseFriendlyURLMapper {

	public String buildPath(LiferayPortletURL portletURL) {
		String friendlyURL = null;

		if (!portletURL.getLifecycle().equals(PortletRequest.RENDER_PHASE)) {
			 return friendlyURL;
		}

		String tabs1 = portletURL.getParameter("tabs1");

		String action = GetterUtil.getString(
			portletURL.getParameter("struts_action"));

		if (action.equals("/software_catalog/view")) {
			friendlyURL = "/software_catalog/" + tabs1;
		}
		else if (action.equals("/software_catalog/view_product_entry")) {
			String productEntryId = portletURL.getParameter("productEntryId");

			friendlyURL = "/software_catalog/products/" + productEntryId;

			portletURL.addParameterIncludedInPath("productEntryId");
		}
		else if (action.equals("/software_catalog/edit_product_entry")) {
			String productEntryId = portletURL.getParameter("productEntryId");

			if (Validator.isNotNull(productEntryId)) {
				friendlyURL = "/software_catalog/products/" +
					productEntryId + "/edit";

				portletURL.addParameterIncludedInPath("productEntryId");
			}
			else {
				friendlyURL = "/software_catalog/products/new";
			}
		}
		else if (action.equals("/software_catalog/edit_product_version")) {
			String productEntryId = portletURL.getParameter("productEntryId");
			String productVersionId = portletURL.getParameter(
				"productVersionId");

			if (Validator.isNotNull(productVersionId)) {
				friendlyURL = "/software_catalog/products/" +
					productEntryId + "/versions/" + productVersionId + "/edit";

				portletURL.addParameterIncludedInPath("productEntryId");
				portletURL.addParameterIncludedInPath("productVersionId");
			}
			else {
				friendlyURL = "/software_catalog/products/" +
					productEntryId + "/versions/new";
			}
		}
		else if (action.equals("/software_catalog/edit_framework_version")) {
			String frameworkVersionId = portletURL.getParameter(
				"frameworkVersionId");

			if (Validator.isNotNull(frameworkVersionId)) {
				friendlyURL = "/software_catalog/framework_versions/" +
					frameworkVersionId + "/edit";

				portletURL.addParameterIncludedInPath("frameworkVersionId");
			}
			else {
				friendlyURL = "/software_catalog/framework_versions/new";
			}
		}
		else if (action.equals("/software_catalog/edit_license")) {
			String licenseId = portletURL.getParameter("licenseId");

			if (Validator.isNotNull(licenseId)) {
				friendlyURL = "/software_catalog/licenses/" +
					licenseId + "/edit";

				portletURL.addParameterIncludedInPath("licenseId");
			}
			else {
				friendlyURL = "/software_catalog/licenses/new";
			}
		}
		else if (action.equals("/software_catalog/search")) {
			friendlyURL = "/software_catalog/search";
		}

		if (Validator.isNotNull(friendlyURL)) {
			WindowState windowState = portletURL.getWindowState();

			if (!windowState.equals(WindowState.NORMAL)) {
				friendlyURL += StringPool.SLASH + windowState;
			}

			portletURL.addParameterIncludedInPath("p_p_id");

			portletURL.addParameterIncludedInPath("struts_action");
			portletURL.addParameterIncludedInPath("tabs1");
		}

		return friendlyURL;
	}

	public String getMapping() {
		return _MAPPING;
	}

	public String getPortletId() {
		return _PORTLET_ID;
	}

	public void populateParams(
		String friendlyURLPath, Map<String, String[]> params,
		Map<String, Object> requestContext) {

		addParam(params, "p_p_id", _PORTLET_ID);

		if (!params.containsKey("p_p_lifecycle")) {
			addParam(params, "p_p_lifecycle", "0");
		}

		addParam(params, "p_p_mode", PortletMode.VIEW);

		int x = friendlyURLPath.indexOf(StringPool.SLASH, 1);

		String[] urlFragments = StringUtil.split(
			friendlyURLPath.substring(x + 1), StringPool.SLASH);

		if (friendlyURLPath.indexOf("maximized", x) != -1) {
			urlFragments = ArrayUtil.remove(urlFragments, "maximized");

			addParam(params, "p_p_state", WindowState.MAXIMIZED);
		}

		String resourceIdParam = getResourceIdParam(urlFragments[0]);

		if (urlFragments.length == 1) {
			addParam(params, "struts_action", "/software_catalog/view");
			addParam(params, "tabs1", urlFragments[0]);
		}
		else if (urlFragments.length == 2) {
			if (urlFragments[1].equals("new")) {
				addParam(
					params, "struts_action", getEditAction(urlFragments[0]));
				addParam(params, "tabs1", urlFragments[0]);
			}
			else if (urlFragments[0].equals("products")) {
				addParam(
					params,
					"struts_action", "/software_catalog/view_product_entry");
				addParam(params, "tabs1", urlFragments[0]);
				addParam(params, resourceIdParam, urlFragments[1]);
			}
		}
		else if (urlFragments.length == 3) {
			if (urlFragments[2].equals("edit")) {
				addParam(
					params, "struts_action", getEditAction(urlFragments[0]));
				addParam(params, "tabs1", urlFragments[0]);
				addParam(params, resourceIdParam, urlFragments[1]);
			}
		}
		else if (urlFragments.length == 4) {
			if (urlFragments[3].equals("new")) {
				addParam(
					params, "struts_action", getEditAction(urlFragments[2]));
				addParam(params, "tabs1", urlFragments[0]);
				addParam(params, resourceIdParam, urlFragments[1]);
			}
		}
		else if (urlFragments.length == 5) {
			if (urlFragments[0].equals("products") &&
				urlFragments[4].equals("edit")) {

				addParam(
					params, "struts_action", getEditAction(urlFragments[2]));
				addParam(params, "tabs1", urlFragments[0]);
				addParam(params, resourceIdParam, urlFragments[1]);
				addParam(
					params, getResourceIdParam(urlFragments[2]),
					urlFragments[3]);
			}
		}
	}

	protected String getEditAction(String resource) {
		String action = null;

		if (resource.equals("my_products") || resource.equals("products")) {
			action = "edit_product_entry";
		}
		else if (resource.equals("versions")) {
			action = "edit_product_version";
		}
		else if (resource.equals("framework_versions")) {
			action = "edit_framework_version";
		}
		else if (resource.equals("licenses")) {
			action = "edit_license";
		}
		else {
			return null;
		}

		return "/software_catalog/" + action;
	}

	protected String getResourceIdParam(String resource) {
		if (resource.equals("my_products") || resource.equals("products")) {
			return "productEntryId";
		}
		else if (resource.equals("versions")) {
			return "productVersionId";
		}
		else if (resource.equals("framework_versions")) {
			return "frameworkVersionId";
		}
		else if (resource.equals("licenses")) {
			return "licenseId";
		}
		else if (resource.equals("discussion")) {
			return "messageId";
		}
		else {
			return null;
		}
	}

	private static final String _MAPPING = "software_catalog";

	private static final String _PORTLET_ID = PortletKeys.SOFTWARE_CATALOG;

}