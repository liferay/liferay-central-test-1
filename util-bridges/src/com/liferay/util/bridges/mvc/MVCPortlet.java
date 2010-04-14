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

package com.liferay.util.bridges.mvc;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * <a href="MVCPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MVCPortlet extends LiferayPortlet {

	public void doAbout(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(aboutJSP, renderRequest, renderResponse);
	}

	public void doConfig(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(configJSP, renderRequest, renderResponse);
	}

	public void doEdit(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			include(editJSP, renderRequest, renderResponse);
		}
	}

	public void doEditDefaults(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			include(editDefaultsJSP, renderRequest, renderResponse);
		}
	}

	public void doEditGuest(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			include(editGuestJSP, renderRequest, renderResponse);
		}
	}

	public void doHelp(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(helpJSP, renderRequest, renderResponse);
	}

	public void doPreview(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(previewJSP, renderRequest, renderResponse);
	}

	public void doPrint(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(printJSP, renderRequest, renderResponse);
	}

	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(viewJSP, renderRequest, renderResponse);
	}

	public void init() throws PortletException {
		super.init();

		jspPath = getInitParameter("jsp-path");

		if (Validator.isNull(jspPath)) {
			jspPath = StringPool.SLASH;
		}
		else if (jspPath.contains(StringPool.BACK_SLASH) ||
				 jspPath.contains(StringPool.DOUBLE_SLASH) ||
				 jspPath.contains(StringPool.PERIOD) ||
				 jspPath.contains(StringPool.SPACE)) {

			throw new PortletException(
				"jsp-path " + jspPath + " has invalid characters");
		}
		else if (jspPath.startsWith(StringPool.SLASH) &&
				 jspPath.endsWith(StringPool.SLASH)) {

			throw new PortletException(
				"jsp-path " + jspPath + " must start and end with a /");
		}

		aboutJSP = getInitParameter("about-jsp");
		configJSP = getInitParameter("config-jsp");
		editJSP = getInitParameter("edit-jsp");
		editDefaultsJSP = getInitParameter("edit-defaults-jsp");
		editGuestJSP = getInitParameter("edit-guest-jsp");
		helpJSP = getInitParameter("help-jsp");
		previewJSP = getInitParameter("preview-jsp");
		printJSP = getInitParameter("print-jsp");
		viewJSP = getInitParameter("view-jsp");

		clearRequestParameters = GetterUtil.getBoolean(
			getInitParameter("clear-request-parameters"));
		copyRequestParameters = GetterUtil.getBoolean(
			getInitParameter("copy-request-parameters"));

		String packagePrefix = getInitParameter(
			ActionCommandCache.ACTION_PACKAGE_NAME);

		if (Validator.isNotNull(packagePrefix)) {
			_actionCommandCache = new ActionCommandCache(packagePrefix);
		}
	}

	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		super.processAction(actionRequest, actionResponse);

		if (copyRequestParameters) {
			PortalUtil.copyRequestParameters(actionRequest, actionResponse);
		}
	}

	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String jspPage = resourceRequest.getParameter("jspPage");

		if (jspPage != null) {
			include(
				jspPage, resourceRequest, resourceResponse,
				PortletRequest.RESOURCE_PHASE);
		}
		else {
			super.serveResource(resourceRequest, resourceResponse);
		}
	}

	protected boolean callActionMethod(
			ActionRequest request, ActionResponse response)
		throws PortletException {

		if ((_actionCommandCache == null) || _actionCommandCache.isEmpty()) {
			return super.callActionMethod(request, response);
		}

		String actionName = ParamUtil.getString(
			request, ActionRequest.ACTION_NAME);

		if (!actionName.contains(StringPool.COMMA)) {
			ActionCommand actionCommand = _actionCommandCache.getActionCommand(
				actionName);

			if (actionCommand != ActionCommandCache.EMPTY) {
				return actionCommand.processCommand(request, response);
			}
		}
		else {
			List<ActionCommand> actionCommands =
				_actionCommandCache.getActionCommandChain(actionName);

			if (actionCommands.isEmpty()) {
				return false;
			}

			for (ActionCommand actionCommand : actionCommands) {
				if (!actionCommand.processCommand(request, response)) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected void checkJSPPath(String path) throws PortletException {
		if (!path.startsWith(jspPath) ||
			path.contains(StringPool.DOUBLE_PERIOD)) {

			throw new PortletException(
				"Path " + path + " is not accessible by this portlet");
		}
	}

	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		String jspPage = renderRequest.getParameter("jspPage");

		if (jspPage != null) {
			include(jspPage, renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected void include(
			String path, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws IOException, PortletException {

		include(
			path, portletRequest, portletResponse, PortletRequest.RENDER_PHASE);
	}

	protected void include(
			String path, PortletRequest portletRequest,
			PortletResponse portletResponse, String lifecycle)
		throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher =
			getPortletContext().getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
			_log.error(path + " is not a valid include");
		}
		else {
			checkJSPPath(path);

			portletRequestDispatcher.include(portletRequest, portletResponse);
		}

		if (clearRequestParameters) {
			if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
				portletResponse.setProperty("clear-request-parameters", "true");
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MVCPortlet.class);

	protected ActionCommandCache _actionCommandCache;

	protected String aboutJSP;
	protected boolean clearRequestParameters;
	protected String configJSP;
	protected boolean copyRequestParameters;
	protected String editDefaultsJSP;
	protected String editGuestJSP;
	protected String editJSP;
	protected String helpJSP;
	protected String jspPath;
	protected String previewJSP;
	protected String printJSP;
	protected String viewJSP;

}