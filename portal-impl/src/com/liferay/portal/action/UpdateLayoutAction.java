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

package com.liferay.portal.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
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
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.LayoutClone;
import com.liferay.portal.util.LayoutCloneFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.servlet.DynamicServletRequest;
import com.liferay.util.servlet.ServletResponseUtil;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class UpdateLayoutAction extends JSONAction {

	public String getJSON(
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

		String json = StringPool.BLANK;

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
		else if (cmd.equals("select_layout_revision")) {
			long layoutRevisionId = ParamUtil.getLong(
				request, "layoutRevisionId");
			long layoutSetBranchId = ParamUtil.getLong(
				request, "layoutSetBranchId");

			StagingUtil.setRecentLayoutRevisionId(
				request, layoutSetBranchId, layout.getPlid(), layoutRevisionId);

			updateLayout = false;
		}

		if (updateLayout) {

			// LEP-3648

			layoutTypePortlet.resetModes();
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

		if (cmd.equals(Constants.ADD) && (portletId != null)) {
			addPortlet(mapping, form, request, response, portletId);
		}

		return json;
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
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			if (portlet.isAjaxable()) {
				StringServletResponse stringResponse =
					new StringServletResponse(response);

				renderPortletAction.execute(
					mapping, form, dynamicRequest, stringResponse);

				populatePortletJSONObject(
					request, stringResponse, portlet, jsonObject);
			}
			else {
				jsonObject.put("refresh", true);
			}

			response.setContentType(ContentTypes.TEXT_JAVASCRIPT);

			ServletResponseUtil.write(response, jsonObject.toString());
		}
		else {
			renderPortletAction.execute(
				mapping, form, dynamicRequest, response);
		}
	}

	protected void populatePortletJSONObject(
			HttpServletRequest request, StringServletResponse stringResponse,
			Portlet portlet, JSONObject jsonObject)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		jsonObject.put("refresh", false);
		jsonObject.put("portletHTML", stringResponse.getString().trim());

		Set<String> footerCssSet = new LinkedHashSet<String>();
		Set<String> footerJavaScriptSet = new LinkedHashSet<String>();
		Set<String> headerCssSet = new LinkedHashSet<String>();
		Set<String> headerJavaScriptSet = new LinkedHashSet<String>();

		boolean portletOnLayout = false;

		String rootPortletId = portlet.getRootPortlet().getPortletId();

		for (Portlet layoutPortlet : layoutTypePortlet.getAllPortlets()) {
			String layoutRootPortletId =
				layoutPortlet.getRootPortlet().getPortletId();

			// Check to see if an instance of this portlet is already in layout,
			// but ignore the portlet that was just added

			if (layoutRootPortletId.equals(rootPortletId) &&
				(layoutPortlet.getPortletId() != portlet.getPortletId())) {

				portletOnLayout = true;

				break;
			}
		}

		if (!portletOnLayout) {
			Portlet rootPortlet = portlet.getRootPortlet();

			for (String footerPortalCss : portlet.getFooterPortalCss()) {
				if (!HttpUtil.hasProtocol(footerPortalCss)) {
					footerPortalCss =
						PortalUtil.getPathContext() + footerPortalCss;

					footerPortalCss = PortalUtil.getStaticResourceURL(
						request, footerPortalCss, rootPortlet.getTimestamp());
				}

				footerCssSet.add(footerPortalCss);
			}

			for (String footerPortalJavaScript :
					portlet.getFooterPortalJavaScript()) {

				if (!HttpUtil.hasProtocol(footerPortalJavaScript)) {
					footerPortalJavaScript =
						PortalUtil.getPathContext() + footerPortalJavaScript;

					footerPortalJavaScript = PortalUtil.getStaticResourceURL(
						request, footerPortalJavaScript,
						rootPortlet.getTimestamp());
				}

				footerJavaScriptSet.add(footerPortalJavaScript);
			}

			for (String footerPortletCss : portlet.getFooterPortletCss()) {
				if (!HttpUtil.hasProtocol(footerPortletCss)) {
					footerPortletCss =
						PortalUtil.getPathContext() + footerPortletCss;

					footerPortletCss = PortalUtil.getStaticResourceURL(
						request, footerPortletCss, rootPortlet.getTimestamp());
				}

				footerCssSet.add(footerPortletCss);
			}

			for (String footerPortletJavaScript :
					portlet.getFooterPortletJavaScript()) {

				if (!HttpUtil.hasProtocol(footerPortletJavaScript)) {
					footerPortletJavaScript =
						PortalUtil.getPathContext() + footerPortletJavaScript;

					footerPortletJavaScript = PortalUtil.getStaticResourceURL(
						request, footerPortletJavaScript,
						rootPortlet.getTimestamp());
				}

				footerJavaScriptSet.add(footerPortletJavaScript);
			}

			for (String headerPortalCss : portlet.getHeaderPortalCss()) {
				if (!HttpUtil.hasProtocol(headerPortalCss)) {
					headerPortalCss =
						PortalUtil.getPathContext() + headerPortalCss;

					headerPortalCss = PortalUtil.getStaticResourceURL(
						request, headerPortalCss, rootPortlet.getTimestamp());
				}

				headerCssSet.add(headerPortalCss);
			}

			for (String headerPortalJavaScript :
					portlet.getHeaderPortalJavaScript()) {

				if (!HttpUtil.hasProtocol(headerPortalJavaScript)) {
					headerPortalJavaScript =
						PortalUtil.getPathContext() + headerPortalJavaScript;

					headerPortalJavaScript = PortalUtil.getStaticResourceURL(
						request, headerPortalJavaScript,
						rootPortlet.getTimestamp());
				}

				headerJavaScriptSet.add(headerPortalJavaScript);
			}

			for (String headerPortletCss : portlet.getHeaderPortletCss()) {
				if (!HttpUtil.hasProtocol(headerPortletCss)) {
					headerPortletCss =
						PortalUtil.getPathContext() + headerPortletCss;

					headerPortletCss = PortalUtil.getStaticResourceURL(
						request, headerPortletCss, rootPortlet.getTimestamp());
				}

				headerCssSet.add(headerPortletCss);
			}

			for (String headerPortletJavaScript :
					portlet.getHeaderPortletJavaScript()) {

				if (!HttpUtil.hasProtocol(headerPortletJavaScript)) {
					headerPortletJavaScript =
						PortalUtil.getPathContext() + headerPortletJavaScript;

					headerPortletJavaScript = PortalUtil.getStaticResourceURL(
						request, headerPortletJavaScript,
						rootPortlet.getTimestamp());
				}

				headerJavaScriptSet.add(headerPortletJavaScript);
			}
		}

		String footerCssPaths = JSONFactoryUtil.serialize(
			footerCssSet.toArray(new String[footerCssSet.size()]));

		jsonObject.put(
			"footerCssPaths", JSONFactoryUtil.createJSONArray(footerCssPaths));

		String footerJavaScriptPaths = JSONFactoryUtil.serialize(
			footerJavaScriptSet.toArray(
				new String[footerJavaScriptSet.size()]));

		jsonObject.put(
			"footerJavaScriptPaths",
			JSONFactoryUtil.createJSONArray(footerJavaScriptPaths));

		String headerCssPaths = JSONFactoryUtil.serialize(
			headerCssSet.toArray(new String[headerCssSet.size()]));

		jsonObject.put(
			"headerCssPaths", JSONFactoryUtil.createJSONArray(headerCssPaths));

		String headerJavaScriptPaths = JSONFactoryUtil.serialize(
			headerJavaScriptSet.toArray(
				new String[headerJavaScriptSet.size()]));

		jsonObject.put(
			"headerJavaScriptPaths",
			JSONFactoryUtil.createJSONArray(headerJavaScriptPaths));
	}

}