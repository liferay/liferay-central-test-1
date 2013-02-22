/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateContextType;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.templateparser.BaseTransformer;
import com.liferay.portal.util.PropsUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class JournalTransformer extends BaseTransformer {

	public JournalTransformer() {
		_transformerListenerClassNames = Collections.unmodifiableSet(
			SetUtil.fromArray(
				PropsUtil.getArray(PropsKeys.JOURNAL_TRANSFORMER_LISTENER)));

		Set<String> langTypes = TemplateManagerUtil.getSupportedLanguageTypes(
			PropsKeys.JOURNAL_ERROR_TEMPLATE);

		for (String langType : langTypes) {
			String errorTemplateId = PropsUtil.get(
				PropsKeys.JOURNAL_ERROR_TEMPLATE, new Filter(langType));

			if (Validator.isNotNull(errorTemplateId)) {
				_errorTemplateIds.put(langType, errorTemplateId);
			}
		}
	}

	@Override
	protected String getErrorTemplateId(String langType) {
		return _errorTemplateIds.get(langType);
	}

	@Override
	protected TemplateContextType getTemplateContextType(String langType) {
		if (langType.equals(TemplateConstants.LANG_TYPE_XSL)) {
			return TemplateContextType.EMPTY;
		}
		else {
			return TemplateContextType.RESTRICTED;
		}
	}

	@Override
	protected Set<String> getTransformerListenersClassNames() {
		return _transformerListenerClassNames;
	}

	private Map<String, String> _errorTemplateIds =
		new HashMap<String, String>();
	private Set<String> _transformerListenerClassNames;

}