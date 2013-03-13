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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateContextType;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juan Fernández
 */
public class DDMTemplateHelperImpl implements DDMTemplateHelper {

	public DDMStructure fetchStructure(DDMTemplate template) {
		try {
			long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

			if (template.getClassNameId() == classNameId) {
				return DDMStructureLocalServiceUtil.fetchDDMStructure(
					template.getClassPK());
			}
		}
		catch (Exception e) {
		}

		return null;
	}

	public String getAutocompleteJSON(HttpServletRequest request)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		TemplateResource templateResource = new StringTemplateResource(
			_TEMPLATE_ID, _TEMPLATE_CONTENT);

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, templateResource,
			TemplateContextType.STANDARD);

		template.prepare(request);

		for (String key : template.getKeys()) {
			JSONObject valueJSONObject = JSONFactoryUtil.createJSONObject();

			Object value = template.get(key);

			if (value == null) {
				continue;
			}

			Class<?> clazz = value.getClass();

			for (Field field : clazz.getFields()) {
				valueJSONObject.put(
					field.getName(),
					JSONFactoryUtil.getUnmodifiableJSONObject());
			}

			for (Method method : clazz.getMethods()) {
				Class<?>[] parameterTypes = method.getParameterTypes();

				StringBundler sb = new StringBundler(
					3 + ((parameterTypes.length * 2) - 1));

				sb.append(method.getName());
				sb.append(StringPool.OPEN_PARENTHESIS);

				for (Class<?> parameterType : parameterTypes) {
					sb.append(parameterType.getSimpleName());
					sb.append(StringPool.COMMA_AND_SPACE);
				}

				if (parameterTypes.length > 0) {
					sb.setIndex(sb.index() - 1);
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);

				valueJSONObject.put(
					sb.toString(), JSONFactoryUtil.getUnmodifiableJSONObject());
			}

			jsonObject.put(key, valueJSONObject);
		}

		return jsonObject.toString();
	}

	private static final String _TEMPLATE_CONTENT = "# Placeholder";

	private static final String _TEMPLATE_ID = "0";

}