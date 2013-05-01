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

package com.liferay.portlet.portletdisplaytemplate.ddm;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.BaseDDMDisplay;

import java.util.Locale;
import java.util.Set;

/**
 * @author Eduardo Garcia
 */
public class PortletDisplayTemplateDDMDisplay extends BaseDDMDisplay {

	@Override
	public String getPortletId() {
		return PortletKeys.PORTLET_DISPLAY_TEMPLATES;
	}

	@Override
	public String getTemplateType(DDMTemplate template, Locale locale) {
		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(
				template.getClassNameId());

		return templateHandler.getName(locale);
	}

	@Override
	public Set<String> getViewTemplatesExcludedColumns() {
		return _viewTemplateExcludedColumns;
	}

	@Override
	public String getViewTemplatesTitle(
		DDMStructure structure, boolean isControlPanel, Locale locale) {

		if (isControlPanel) {
			return StringPool.BLANK;
		}

		return super.getViewTemplatesTitle(structure, isControlPanel, locale);
	}

	@Override
	protected String getDefaultEditTemplateTitle(Locale locale) {
		return LanguageUtil.get(locale, "new-application-display-template");
	}

	@Override
	protected String getDefaultViewTemplateTitle(Locale locale) {
		return LanguageUtil.get(locale, "application-display-templates");
	}

	private static Set<String> _viewTemplateExcludedColumns = SetUtil.fromArray(
		new String[] {"structure", "mode", "language"});

}