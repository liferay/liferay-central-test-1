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

package com.liferay.portal.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.servlet.NamespaceServletRequest;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.LayoutClone;
import com.liferay.portal.util.LayoutCloneFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.servlet.DynamicServletRequest;
import com.liferay.util.servlet.ServletResponseUtil;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="UpdateLayoutAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UpdateLayoutAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = themeDisplay.getUserId();

		Layout layout = themeDisplay.getLayout();
		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		String cmd = ParamUtil.getString(request, Constants.CMD);

		String portletId = ParamUtil.getString(request, "p_p_id");

		boolean updateLayout = true;
		boolean deletePortlet = false;

		if (cmd.equals(Constants.ADD)) {
			portletId = layoutTypePortlet.addPortletId(userId, portletId);

			String columnId = ParamUtil.getString(request, "p_p_col_id");
			int columnPos = ParamUtil.getInteger(request, "p_p_col_pos");

			if (Validator.isNotNull(columnId) &&
				Validator.isNotNull(portletId)) {

				layoutTypePortlet.movePortletId(
					userId, portletId, columnId, columnPos);
			}
		}
		else if (cmd.equals(Constants.DELETE)) {
			if (layoutTypePortlet.hasPortletId(portletId)) {
				deletePortlet = true;

				layoutTypePortlet.removePortletId(userId, portletId);
			}
		}
		else if (cmd.equals("drag")) {
			if (LayoutPermissionUtil.contains(
					permissionChecker, layout.getGroupId(),
					layout.isPrivateLayout(), layout.getLayoutId(),
					ActionKeys.UPDATE)) {

				String height = ParamUtil.getString(request, "height");
				String width = ParamUtil.getString(request, "width");
				String top = ParamUtil.getString(request, "top");
				String left = ParamUtil.getString(request, "left");

				PortletPreferences preferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						layout, portletId);

				StringBundler sb = new StringBundler(12);

				sb.append("height=");
				sb.append(height);
				sb.append("\n");
				sb.append("width=");
				sb.append(width);
				sb.append("\n");
				sb.append("top=");
				sb.append(top);
				sb.append("\n");
				sb.append("left=");
				sb.append(left);
				sb.append("\n");

				preferences.setValue("portlet-freeform-styles", sb.toString());

				preferences.store();
			}
		}
		else if (cmd.equals("minimize")) {
			boolean restore = ParamUtil.getBoolean(request, "p_p_restore");

			if (restore) {
				layoutTypePortlet.removeStateMinPortletId(portletId);
			}
			else {
				layoutTypePortlet.addStateMinPortletId(portletId);
			}

			updateLayout = false;
		}
		else if (cmd.equals("move")) {
			String columnId = ParamUtil.getString(request, "p_p_col_id");
			int columnPos = ParamUtil.getInteger(request, "p_p_col_pos");

			layoutTypePortlet.movePortletId(
				userId, portletId, columnId, columnPos);
		}
		else if (cmd.equals("template")) {
			String layoutTemplateId = ParamUtil.getString(
				request, "layoutTemplateId");

			layoutTypePortlet.setLayoutTemplateId(userId, layoutTemplateId);
		}

		if (updateLayout) {

			// LEP-3648

			layoutTypePortlet.resetStates();

			LayoutServiceUtil.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), layout.getTypeSettings());

			// See LEP-1411. Delay the delete of extraneous portlet resources
			// only after the user has proven that he has the valid permissions.

			if (deletePortlet) {
				String rootPortletId = PortletConstants.getRootPortletId(
					portletId);

				ResourceLocalServiceUtil.deleteResource(
					layout.getCompanyId(), rootPortletId,
					ResourceConstants.SCOPE_INDIVIDUAL,
					PortletPermissionUtil.getPrimaryKey(
						layout.getPlid(), portletId));
			}
		}
		else {
			LayoutClone layoutClone = LayoutCloneFactory.getInstance();

			if (layoutClone != null) {
				layoutClone.update(
					request, layout.getPlid(), layout.getTypeSettings());
			}
		}

		// The check for the refresh variable can be removed in the future. See
		// LEP-6910.

		if (ParamUtil.getBoolean(request, "refresh")) {
			return mapping.findForward(ActionConstants.COMMON_REFERER);
		}
		else {
			if (cmd.equals(Constants.ADD) && (portletId != null)) {
				addPortlet(mapping, form, request, response, portletId);
			}

			return null;
		}
	}

	protected void addPortlet(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String portletId)
		throws Exception {

		// Run the render portlet action to add a portlet without refreshing.

		Action renderPortletAction = (Action)InstancePool.get(
			RenderPortletAction.class.getName());

		// Pass in the portlet id because the portlet id may be the instance id.
		// Namespace the request if necessary. See LEP-4644.

		long companyId = PortalUtil.getCompanyId(request);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		DynamicServletRequest dynamicRequest = null;

		if (portlet.isPrivateRequestAttributes()) {
			String portletNamespace =
				PortalUtil.getPortletNamespace(portlet.getPortletId());

			dynamicRequest = new NamespaceServletRequest(
				request, portletNamespace, portletNamespace);
		}
		else {
			dynamicRequest = new DynamicServletRequest(request);
		}

		dynamicRequest.setParameter("p_p_id", portletId);

		String dataType = ParamUtil.getString(request, "dataType");

		if (dataType.equals("json")) {
			JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

			if (portlet.isAjaxable()) {
				StringServletResponse stringResponse =
					new StringServletResponse(response);

				renderPortletAction.execute(
					mapping, form, dynamicRequest, stringResponse);

				jsonObj.put("refresh", false);
				jsonObj.put("portletHTML", stringResponse.getString().trim());
			}
			else {
				jsonObj.put("refresh", true);
			}

			ServletResponseUtil.write(response, jsonObj.toString());
		}
		else {
			renderPortletAction.execute(
				mapping, form, dynamicRequest, response);
		}
	}

}