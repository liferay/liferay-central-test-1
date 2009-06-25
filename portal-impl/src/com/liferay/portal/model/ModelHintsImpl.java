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

package com.liferay.portal.model;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * <a href="ModelHintsImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ModelHintsImpl implements ModelHints {

	public void afterPropertiesSet() {
		_hintCollections = new HashMap<String, Map<String, String>>();
		_defaultHints = new HashMap<String, Map<String, String>>();
		_modelFields = new HashMap<String, Object>();
		_models = new TreeSet<String>();

		try {
			ClassLoader classLoader = getClass().getClassLoader();

			String[] configs = StringUtil.split(
				PropsUtil.get(PropsKeys.MODEL_HINTS_CONFIGS));

			for (int i = 0; i < configs.length; i++) {
				read(classLoader, configs[i]);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public Map<String, String> getDefaultHints(String model) {
		return _defaultHints.get(model);
	}

	public com.liferay.portal.kernel.xml.Element getFieldsEl(
		String model, String field) {

		Map<String, Object> fields =
			(Map<String, Object>)_modelFields.get(model);

		if (fields == null) {
			return null;
		}
		else {
			Element fieldsEl = (Element)fields.get(field + _ELEMENTS_SUFFIX);

			if (fieldsEl == null) {
				return null;
			}
			else {
				return fieldsEl;
			}
		}
	}

	public List<String> getModels() {
		return ListUtil.fromCollection(_models);
	}

	public String getType(String model, String field) {
		Map<String, Object> fields =
			(Map<String, Object>)_modelFields.get(model);

		if (fields == null) {
			return null;
		}
		else {
			return (String)fields.get(field + _TYPE_SUFFIX);
		}
	}

	public Map<String, String> getHints(String model, String field) {
		Map<String, Object> fields =
			(Map<String, Object>)_modelFields.get(model);

		if (fields == null) {
			return null;
		}
		else {
			return (Map<String, String>)fields.get(field + _HINTS_SUFFIX);
		}
	}

	public boolean isLocalized (String model, String field) {
		Map<String, Object> fields = (Map<String, Object>)_modelFields.get(
			model);

		if (fields == null) {
			return false;
		}
		else {
			return (Boolean)fields.get(field + _LOCALIZATION_SUFFIX);
		}
	}

	public void read(ClassLoader classLoader, String source) throws Exception {
		String xml = null;

		try {
			xml = StringUtil.read(classLoader, source);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Cannot load " + source);
			}
		}

		if (xml == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Loading " + source);
		}

		Document doc = _saxReader.read(xml);

		Element root = doc.getRootElement();

		Iterator<Element> itr1 = root.elements("hint-collection").iterator();

		while (itr1.hasNext()) {
			Element hintCollection = itr1.next();

			String name = hintCollection.attributeValue("name");

			Map<String, String> hints = _hintCollections.get(name);

			if (hints == null) {
				hints = new HashMap<String, String>();

				_hintCollections.put(name, hints);
			}

			Iterator<Element> itr2 = hintCollection.elements("hint").iterator();

			while (itr2.hasNext()) {
				Element hint = itr2.next();

				String hintName = hint.attributeValue("name");
				String hintValue = hint.getText();

				hints.put(hintName, hintValue);
			}
		}

		itr1 = root.elements("model").iterator();

		while (itr1.hasNext()) {
			Element model = itr1.next();

			String name = model.attributeValue("name");

			if (classLoader != ModelHintsImpl.class.getClassLoader()) {
				ClassNameLocalServiceUtil.getClassName(name);
			}

			Map<String, String> defaultHints = new HashMap<String, String>();

			_defaultHints.put(name, defaultHints);

			Element defaultHintsEl = model.element("default-hints");

			if (defaultHintsEl != null) {
				Iterator<Element> itr2 = defaultHintsEl.elements(
					"hint").iterator();

				while (itr2.hasNext()) {
					Element hint = itr2.next();

					String hintName = hint.attributeValue("name");
					String hintValue = hint.getText();

					defaultHints.put(hintName, hintValue);
				}
			}

			Map<String, Object> fields =
				(Map<String, Object>)_modelFields.get(name);

			if (fields == null) {
				fields = new HashMap<String, Object>();

				_modelFields.put(name, fields);
			}

			_models.add(name);

			Iterator<Element> itr2 = model.elements("field").iterator();

			while (itr2.hasNext()) {
				Element field = itr2.next();

				String fieldName = field.attributeValue("name");
				String fieldType = field.attributeValue("type");
				boolean fieldLocalized = GetterUtil.getBoolean(
					field.attributeValue("localized"));

				Map<String, String> fieldHints = new HashMap<String, String>();

				fieldHints.putAll(defaultHints);

				Iterator<Element> itr3 = field.elements(
					"hint-collection").iterator();

				while (itr3.hasNext()) {
					Element hintCollection = itr3.next();

					Map<String, String> hints = _hintCollections.get(
						hintCollection.attributeValue("name"));

					fieldHints.putAll(hints);
				}

				itr3 = field.elements("hint").iterator();

				while (itr3.hasNext()) {
					Element hint = itr3.next();

					String hintName = hint.attributeValue("name");
					String hintValue = hint.getText();

					fieldHints.put(hintName, hintValue);
				}

				fields.put(fieldName + _ELEMENTS_SUFFIX, field);
				fields.put(fieldName + _TYPE_SUFFIX, fieldType);
				fields.put(fieldName + _LOCALIZATION_SUFFIX, fieldLocalized);
				fields.put(fieldName + _HINTS_SUFFIX, fieldHints);
			}
		}
	}

	public void setSAXReader(SAXReader saxReader) {
		_saxReader = saxReader;
	}

	public String trimString(String model, String field, String value) {
		if (value == null) {
			return value;
		}

		Map<String, String> hints = getHints(model, field);

		if (hints == null) {
			return value;
		}

		int maxLength = GetterUtil.getInteger(
			ModelHintsConstants.TEXT_MAX_LENGTH);

		maxLength = GetterUtil.getInteger(hints.get("max-length"), maxLength);

		if (value.length() > maxLength) {
			return value.substring(0, maxLength);
		}
		else {
			return value;
		}
	}

	private static final String _ELEMENTS_SUFFIX = "_ELEMENTS";

	private static final String _TYPE_SUFFIX = "_TYPE";

	private static final String _LOCALIZATION_SUFFIX = "_LOCALIZATION";

	private static final String _HINTS_SUFFIX = "_HINTS";

	private static Log _log = LogFactoryUtil.getLog(ModelHintsImpl.class);

	private Map<String, Map<String, String>> _hintCollections;
	private Map<String, Map<String, String>> _defaultHints;
	private Map<String, Object> _modelFields;
	private Set<String> _models;
	private SAXReader _saxReader;

}