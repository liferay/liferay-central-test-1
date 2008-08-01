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

package com.liferay.portlet.portletconfiguration.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditSharingAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class EditSharingAction extends EditConfigurationAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(actionRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				actionRequest, PrincipalException.class.getName());

			setForward(actionRequest, "portlet.portlet_configuration.error");
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PortletPreferences prefs =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portlet.getPortletId());

		String tabs2 = ParamUtil.getString(actionRequest, "tabs2");

		if (tabs2.equals("any-website")) {
			updateAnyWebsite(actionRequest, prefs);
		}
		else if (tabs2.equals("facebook")) {
			updateFacebook(actionRequest, prefs);
		} else if (tabs2.equals("request-share-tab-name")) {
			updateWidgetShareWithFriends(actionRequest, prefs);
		}

		prefs.store();

		sendRedirect(actionRequest, actionResponse);
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(renderRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			return mapping.findForward("portlet.portlet_configuration.error");
		}

		renderResponse.setTitle(getTitle(portlet, renderRequest));

		return mapping.findForward(getForward(
			renderRequest, "portlet.portlet_configuration.edit_sharing"));
	}

	protected void updateAnyWebsite(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		boolean widgetShowAddAppLink = ParamUtil.getBoolean(
			actionRequest, "widgetShowAddAppLink");

		prefs.setValue(
			"lfr-widget-show-add-app-link",
			String.valueOf(widgetShowAddAppLink));
	}

	protected void updateWidgetShareWithFriends(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		boolean widgetShareWithFriends = ParamUtil.getBoolean(
			actionRequest, "widgetShareWithFriends");

		prefs.setValue(
			"lfr-share-widget-with-friends",
			String.valueOf(widgetShareWithFriends));
		}

	protected void updateFacebook(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		String facebookAPIKey = ParamUtil.getString(
			actionRequest, "facebookAPIKey");
		String facebookCanvasPageURL = ParamUtil.getString(
			actionRequest, "facebookCanvasPageURL");
		boolean facebookShowAddAppLink = ParamUtil.getBoolean(
			actionRequest, "facebookShowAddAppLink");

		prefs.setValue("lfr-facebook-api-key", facebookAPIKey);
		prefs.setValue("lfr-facebook-canvas-page-url", facebookCanvasPageURL);
		prefs.setValue(
			"lfr-facebook-show-add-app-link",
			String.valueOf(facebookShowAddAppLink));
	}

}