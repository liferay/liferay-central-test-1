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

package com.liferay.portlet.nestedportlets.action;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.UniqueList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="ConfigurationActionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class ConfigurationActionImpl implements ConfigurationAction {

	public void processAction(
			PortletConfig config, ActionRequest req, ActionResponse res)
		throws Exception {

		String layoutTemplateId = ParamUtil.getString(req, "layoutTemplateId");
		String portletSetupShowBorders = ParamUtil.getString(
			req, "portletSetupShowBorders");

		String portletResource = ParamUtil.getString(req, "portletResource");

		PortletPreferences prefs =
			PortletPreferencesFactoryUtil.getPortletSetup(req, portletResource);

		String oldLayoutTemplateId = prefs.getValue(
			"layout-template-id",
			PropsValues.NESTED_PORTLETS_LAYOUT_TEMPLATE_DEFAULT);

		if (!oldLayoutTemplateId.equals(layoutTemplateId)) {
			reorganizeNestedColumns(
				req, portletResource, layoutTemplateId, oldLayoutTemplateId);
		}

		prefs.setValue("layout-template-id", layoutTemplateId);
		prefs.setValue("portlet-setup-show-borders", portletSetupShowBorders);

		prefs.store();

		SessionMessages.add(req, config.getPortletName() + ".doConfigure");
	}

	public String render(
			PortletConfig config, RenderRequest req, RenderResponse res)
		throws Exception {

		return "/html/portlet/nested_portlets/configuration.jsp";
	}

	protected List<String> getColumnNames(String content, String portletId) {
		Matcher matcher = _searchColumnsPattern.matcher(content);

		Set<String> columnIds = new HashSet<String>();

		while (matcher.find()) {
			if (Validator.isNotNull(matcher.group(1))) {
				columnIds.add(matcher.group(1));
			}
		}

		List<String> columnNames = new UniqueList<String>();

		for (String columnId : columnIds) {
			if (columnId.indexOf(portletId) == -1) {
				columnNames.add(portletId + StringPool.UNDERLINE + columnId);
			}
		}

		return columnNames;
	}

	protected void reorganizeNestedColumns(
			ActionRequest req, String portletResource,
			String newLayoutTemplateId, String oldLayoutTemplateId)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();
		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();
		Theme theme = themeDisplay.getTheme();

		LayoutTemplate newLayoutTemplate =
			LayoutTemplateLocalServiceUtil.getLayoutTemplate(
				newLayoutTemplateId, false, theme.getThemeId());

		List<String> newColumns = getColumnNames(
			newLayoutTemplate.getContent(), portletResource);

		LayoutTemplate oldLayoutTemplate =
			LayoutTemplateLocalServiceUtil.getLayoutTemplate(
				oldLayoutTemplateId, false, theme.getThemeId());

		List<String> oldColumns = getColumnNames(
			oldLayoutTemplate.getContent(), portletResource);

		layoutTypePortlet.reorganizeNestedColumns(
			portletResource, newColumns, oldColumns);

		layoutTypePortlet.setStateMax(StringPool.BLANK);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getLayoutId(), layout.getTypeSettings());
	}

	private static final Pattern _searchColumnsPattern = Pattern.compile(
		"processColumn[(]\"(.*?)\"[)]", Pattern.DOTALL);

}