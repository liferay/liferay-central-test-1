/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.util.servlet.ServletResponseUtil;

import java.util.Iterator;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="PortletURLAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 *
 */
public class PortletURLAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			String portletURL = getPortletURL(request);

			ServletResponseUtil.write(response, portletURL);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);
		}

		return null;
	}

	protected String getPortletURL(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cacheability = ParamUtil.getString(request, "cacheability");
		boolean copyCurrentRenderParameters = ParamUtil.getBoolean(
			request, "copyCurrentRenderParameters");
		long doAsUserId = ParamUtil.getLong(request, "doAsUserId");
		String doAsUserLocale = ParamUtil.getString(request, "doAsUserLocale");
		boolean encrypt = ParamUtil.getBoolean(request, "encrypt");
		boolean escapeXml = ParamUtil.getBoolean(request, "escapeXml");
		String lifecycle = ParamUtil.getString(request, "lifecycle");
		String name = ParamUtil.getString(request, "name");
		boolean portletConfiguration = ParamUtil.getBoolean(
			request, "portletConfiguration");
		String portletId = ParamUtil.getString(request, "portletId");
		String portletMode = ParamUtil.getString(request, "portletMode");
		String resourceId = ParamUtil.getString(request, "resourceId");
		String returnToFullPageURL = ParamUtil.getString(
			request, "returnToFullPageURL");
		boolean secure = ParamUtil.getBoolean(request, "secure");
		String windowState = ParamUtil.getString(request, "windowState");

		PortletURLImpl portletURL = new PortletURLImpl(
			request, portletId, themeDisplay.getPlid(), lifecycle);

		if (Validator.isNotNull(cacheability)) {
			portletURL.setCacheability(cacheability);
		}

		portletURL.setCopyCurrentRenderParameters(copyCurrentRenderParameters);

		if (doAsUserId > 0) {
			portletURL.setDoAsUserId(doAsUserId);
		}

		if (Validator.isNotNull(doAsUserLocale)) {
			portletURL.setDoAsUserLocale(doAsUserLocale);
		}

		portletURL.setEncrypt(encrypt);
		portletURL.setEscapeXml(escapeXml);

		if (lifecycle.equals(PortletRequest.ACTION_PHASE) &&
			Validator.isNotNull(name)) {

			portletURL.setParameter(ActionRequest.ACTION_NAME, name);
		}

		portletURL.setPortletId(portletId);

		if (portletConfiguration) {
			String portletResource = ParamUtil.getString(
				request, "portletResource");
			String previewWidth = ParamUtil.getString(request, "previewWidth");

			portletURL.setParameter(
				"struts_action", "/portlet_configuration/edit_configuration");
			portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
			portletURL.setParameter("portletResource", portletResource);
			portletURL.setParameter("previewWidth", previewWidth);
		}

		if (Validator.isNotNull(portletMode)) {
			portletURL.setPortletMode(
				PortletModeFactory.getPortletMode(portletMode));
		}

		if (Validator.isNotNull(resourceId)) {
			portletURL.setResourceID(resourceId);
		}

		if (!themeDisplay.isStateMaximized()) {
			if (Validator.isNotNull(returnToFullPageURL)) {
				portletURL.setParameter(
					"returnToFullPageURL", returnToFullPageURL);
			}
		}

		portletURL.setSecure(secure);

		if (Validator.isNotNull(windowState)) {
			portletURL.setWindowState(
				WindowStateFactory.getWindowState(windowState));
		}

		String parameterMapString = ParamUtil.getString(
			request, "parameterMap");

		if (Validator.isNotNull(parameterMapString)) {
			Map<String, String> parameterMap =
				(Map<String, String>)JSONFactoryUtil.deserialize(
					parameterMapString);

			Iterator<String> itr = parameterMap.keySet().iterator();

			while (itr.hasNext()) {
				String paramName = itr.next();

				String paramValue = parameterMap.get(paramName);

				portletURL.setParameter(paramName, paramValue);
			}
		}

		return portletURL.toString();
	}

}