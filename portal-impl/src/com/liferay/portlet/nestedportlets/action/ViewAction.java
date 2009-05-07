/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.nestedportlets.action;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTemplateConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ViewAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Berentey Zsolt
 * @author Jorge Ferrer
 * @author Raymond Augé
 * @author Jesper Weissglas
 *
 */
public class ViewAction extends PortletAction {

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Portlet portlet = (Portlet)renderRequest.getAttribute(
			WebKeys.RENDER_PORTLET);

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				renderRequest, portlet.getPortletId());

		String layoutTemplateId = preferences.getValue(
			"layout-template-id",
			PropsValues.NESTED_PORTLETS_LAYOUT_TEMPLATE_DEFAULT);

		String velocityTemplateId = StringPool.BLANK;
		String velocityTemplateContent = StringPool.BLANK;

		Map<String, String> columnIds = new HashMap<String, String>();

		if (Validator.isNotNull(layoutTemplateId)) {
			Theme theme = themeDisplay.getTheme();

			LayoutTemplate layoutTemplate =
				LayoutTemplateLocalServiceUtil.getLayoutTemplate(
					layoutTemplateId, false, theme.getThemeId());

			String content = layoutTemplate.getContent();

			Matcher processColumnMatcher = _processColumnPattern.matcher(
				content);

			while (processColumnMatcher.find()) {
				String columnId = processColumnMatcher.group(2);

				if (Validator.isNotNull(columnId)) {
					columnIds.put(
						columnId,
						portlet.getPortletId() + StringPool.UNDERLINE +
							columnId);
				}
			}

			processColumnMatcher.reset();

			content = processColumnMatcher.replaceAll("$1\\${$2}$3");

			velocityTemplateId =
				theme.getThemeId() + LayoutTemplateConstants.CUSTOM_SEPARATOR +
					layoutTemplateId;

			Matcher columnIdMatcher = _columnIdPattern.matcher(content);

			velocityTemplateContent = columnIdMatcher.replaceAll(
				"$1" + portlet.getPortletId() + "$2$3");
		}

		renderRequest.setAttribute(
			WebKeys.NESTED_PORTLET_VELOCITY_TEMPLATE_ID, velocityTemplateId);
		renderRequest.setAttribute(
			WebKeys.NESTED_PORTLET_VELOCITY_TEMPLATE_CONTENT,
			velocityTemplateContent);

		Map<String, Object> vmVariables =
			(Map<String, Object>)renderRequest.getAttribute(
				WebKeys.VM_VARIABLES);

		if (vmVariables != null) {
			vmVariables.putAll(columnIds);
		}
		else {
			renderRequest.setAttribute(WebKeys.VM_VARIABLES, columnIds);
		}

		return mapping.findForward("portlet.nested_portlets.view");
	}

	private static Pattern _columnIdPattern = Pattern.compile(
		"([<].*?id=[\"'])([^ ]*?)([\"'].*?[>])", Pattern.DOTALL);
	private static Pattern _processColumnPattern = Pattern.compile(
		"(processColumn[(]\")(.*?)(\"[)])", Pattern.DOTALL);

}