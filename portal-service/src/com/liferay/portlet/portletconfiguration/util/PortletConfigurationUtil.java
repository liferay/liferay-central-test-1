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

package com.liferay.portlet.portletconfiguration.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletSetupUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletConfigurationUtil {

	public static String getPortletCustomCSSClassName(
			PortletPreferences portletSetup)
		throws Exception {

		String customCSSClassName = StringPool.BLANK;

		String css = portletSetup.getValue(
			"portlet-setup-css", StringPool.BLANK);

		if (Validator.isNotNull(css)) {
			JSONObject cssJSON = PortletSetupUtil.cssToJSON(portletSetup, css);

			JSONObject advancedDataJSON = cssJSON.getJSONObject("advancedData");

			if (advancedDataJSON != null) {
				customCSSClassName = advancedDataJSON.getString(
					"customCSSClassName");
			}
		}

		return customCSSClassName;
	}

	public static String getPortletTitle(
		PortletPreferences portletSetup, String languageId) {

		String useCustomTitle = GetterUtil.getString(portletSetup.getValue(
			"portlet-setup-use-custom-title", StringPool.BLANK));

		if (useCustomTitle.equals("false")) {
			return StringPool.BLANK;
		}

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		String defaultPortletTitle = portletSetup.getValue(
			"portlet-setup-title-" + defaultLanguageId, StringPool.BLANK);

		String portletTitle = portletSetup.getValue(
			"portlet-setup-title-" + languageId, defaultPortletTitle);

		if (Validator.isNull(portletTitle)) {

			// For backwards compatibility

			String oldPortletTitle = portletSetup.getValue(
				"portlet-setup-title", null);

			if (Validator.isNull(useCustomTitle) &&
				Validator.isNotNull(oldPortletTitle)) {

				portletTitle = oldPortletTitle;

				try {
					portletSetup.setValue(
						"portlet-setup-title-" + defaultLanguageId,
						portletTitle);
					portletSetup.setValue(
						"portlet-setup-use-custom-title", "true");

					portletSetup.store();
				}
				catch (Exception e) {
					_log.error(e);
				}
			}
		}

		return portletTitle;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletConfigurationUtil.class);

}