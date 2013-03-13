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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateContextType;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.template.RestrictedTemplate;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.util.PropsValues;

import freemarker.cache.TemplateCache;

import freemarker.template.Configuration;

import java.lang.reflect.Field;

import java.util.Map;

/**
 * @author Mika Koivisto
 * @author Tina Tina
 */
@DoPrivileged
public class FreeMarkerManager implements TemplateManager {

	public void destroy() {
		if (_configuration == null) {
			return;
		}

		_configuration.clearEncodingMap();
		_configuration.clearSharedVariables();
		_configuration.clearTemplateCache();

		_configuration = null;

		_templateContextHelper.removeAllHelperUtilities();

		_templateContextHelper = null;
	}

	public void destroy(ClassLoader classLoader) {
		_templateContextHelper.removeHelperUtilities(classLoader);
	}

	public String getName() {
		return TemplateConstants.LANG_TYPE_FTL;
	}

	public Template getTemplate(
		TemplateResource templateResource,
		TemplateContextType templateContextType) {

		return getTemplate(templateResource, null, templateContextType);
	}

	public Template getTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource,
		TemplateContextType templateContextType) {

		Template template = null;

		Map<String, Object> context = _templateContextHelper.getHelperUtilities(
			templateContextType);

		if (templateContextType.equals(TemplateContextType.EMPTY)) {
			template = new FreeMarkerTemplate(
				templateResource, errorTemplateResource, null, _configuration,
				_templateContextHelper);
		}
		else if (templateContextType.equals(TemplateContextType.RESTRICTED)) {
			template = new RestrictedTemplate(
				new FreeMarkerTemplate(
					templateResource, errorTemplateResource, context,
					_configuration, _templateContextHelper),
				_templateContextHelper.getRestrictedVariables());
		}
		else if (templateContextType.equals(TemplateContextType.STANDARD)) {
			template = new FreeMarkerTemplate(
				templateResource, errorTemplateResource, context,
				_configuration, _templateContextHelper);
		}

		return template;
	}

	public void init() throws TemplateException {
		if (_configuration != null) {
			return;
		}

		_configuration = new Configuration();

		try {
			Field field = ReflectionUtil.getDeclaredField(
				Configuration.class, "cache");

			TemplateCache templateCache = new LiferayTemplateCache(
				_configuration);

			field.set(_configuration, templateCache);
		}
		catch (Exception e) {
			throw new TemplateException(
				"Unable to Initialize Freemarker manager");
		}

		_configuration.setDefaultEncoding(StringPool.UTF8);
		_configuration.setLocalizedLookup(
			PropsValues.FREEMARKER_ENGINE_LOCALIZED_LOOKUP);
		_configuration.setNewBuiltinClassResolver(
			new LiferayTemplateClassResolver());
		_configuration.setObjectWrapper(new LiferayObjectWrapper());

		try {
			_configuration.setSetting(
				"auto_import", PropsValues.FREEMARKER_ENGINE_MACRO_LIBRARY);
			_configuration.setSetting(
				"template_exception_handler",
				PropsValues.FREEMARKER_ENGINE_TEMPLATE_EXCEPTION_HANDLER);
		}
		catch (Exception e) {
			throw new TemplateException("Unable to init freemarker manager", e);
		}
	}

	public void setTemplateContextHelper(
		TemplateContextHelper templateContextHelper) {

		_templateContextHelper = templateContextHelper;
	}

	private Configuration _configuration;
	private TemplateContextHelper _templateContextHelper;

}