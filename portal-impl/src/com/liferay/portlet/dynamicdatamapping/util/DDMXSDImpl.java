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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.freemarker.FreeMarkerContext;
import com.liferay.portal.kernel.freemarker.FreeMarkerEngineUtil;
import com.liferay.portal.kernel.freemarker.FreeMarkerVariablesUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.ServletContextHashModel;

import freemarker.template.ObjectWrapper;

import java.io.Writer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.GenericServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Bruno Basto
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 */
public class DDMXSDImpl implements DDMXSD {

	public String getHTML(PageContext pageContext, Document document)
		throws Exception {

		return getHTML(pageContext, document.getRootElement());
	}

	public String getHTML(
			PageContext pageContext, Document document, Fields fields)
		throws Exception {

		return getHTML(pageContext, document.getRootElement(), fields);
	}

	public String getHTML(PageContext pageContext, Element element)
		throws Exception {

		return getHTML(pageContext, element, null);
	}

	public String getHTML(
			PageContext pageContext, Element element, Fields fields)
		throws Exception {

		StringBundler sb = new StringBundler();

		List<Element> dynamicElementElements = element.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			FreeMarkerContext freeMarkerContext = getFreeMarkerContext(
				dynamicElementElement);

			if (fields != null) {
				freeMarkerContext.put("fields", fields);
			}

			Map<String, Object> field =
				(Map<String, Object>)freeMarkerContext.get("field");

			String childrenHTML = getHTML(
				pageContext, dynamicElementElement, fields);

			field.put("children", childrenHTML);

			String fieldNamespace = dynamicElementElement.attributeValue(
				"fieldNamespace", _DEFAULT_NAMESPACE);
			String type = dynamicElementElement.attributeValue("type");

			String templateName = StringUtil.replaceFirst(
				type, fieldNamespace.concat(StringPool.DASH), StringPool.BLANK);

			StringBundler resourcePath = new StringBundler(5);

			resourcePath.append(_TPL_PATH);
			resourcePath.append(fieldNamespace.toLowerCase());
			resourcePath.append(CharPool.SLASH);
			resourcePath.append(templateName);
			resourcePath.append(_TPL_EXT);

			sb.append(
				processFTL(
					pageContext, freeMarkerContext, resourcePath.toString()));
		}

		return sb.toString();
	}

	public String getHTML(PageContext pageContext, String xml)
		throws Exception {

		return getHTML(pageContext, xml, null);
	}

	public String getHTML(PageContext pageContext, String xml, Fields fields)
		throws Exception {

		return getHTML(pageContext, SAXReaderUtil.read(xml), fields);
	}

	public JSONArray getJSONArray(Document document) throws JSONException {
		return getJSONArray(document.getRootElement());
	}

	public JSONArray getJSONArray(Element element) throws JSONException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Element> dynamicElementElements = element.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			Element metadataElement = dynamicElementElement.element(
				"meta-data");

			if (metadataElement != null) {
				for (Element metadataEntry : metadataElement.elements()) {
					jsonObject.put(
						metadataEntry.attributeValue("name"),
						metadataEntry.getText());
				}
			}

			for (Attribute attribute : dynamicElementElement.attributes()) {
				jsonObject.put(attribute.getName(), attribute.getValue());
			}

			jsonObject.put(
				"key", dynamicElementElement.attributeValue("name"));

			String type = jsonObject.getString("type");

			String key = "fields";

			if (type.equals(_TYPE_RADIO) || type.equals(_TYPE_SELECT)) {
				key = "options";
			}

			jsonObject.put(key, getJSONArray(dynamicElementElement));

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public JSONArray getJSONArray(String xml)
		throws DocumentException, JSONException {

		return getJSONArray(SAXReaderUtil.read(xml));
	}

	protected Map<String, Object> getFieldContext(
		Element dynamicElementElement) {

		Map<String, Object> field = new HashMap<String, Object>();

		Element metadataElement = dynamicElementElement.element("meta-data");

		if (metadataElement != null) {
			for (Element metadataEntry : metadataElement.elements()) {
				field.put(
					metadataEntry.attributeValue("name"),
					metadataEntry.getText());
			}
		}

		for (Attribute attribute : dynamicElementElement.attributes()) {
			field.put(attribute.getName(), attribute.getValue());
		}

		return field;
	}

	protected FreeMarkerContext getFreeMarkerContext(
		Element dynamicElementElement) {

		FreeMarkerContext freeMarkerContext =
			FreeMarkerEngineUtil.getWrappedRestrictedToolsContext();

		Map<String, Object> fieldContext = getFieldContext(
			dynamicElementElement);

		Map<String, Object> parentFieldContext = new HashMap<String, Object>();

		Element parentElement = dynamicElementElement.getParent();

		if (parentElement != null) {
			parentFieldContext = getFieldContext(parentElement);
		}

		freeMarkerContext.put("field", fieldContext);
		freeMarkerContext.put("parentField", parentFieldContext);

		return freeMarkerContext;
	}

	/**
	 * @see com.liferay.taglib.util.ThemeUtil#includeFTL
	 */
	protected String processFTL(
			PageContext pageContext, FreeMarkerContext freeMarkerContext,
			String resourcePath)
		throws Exception {

		if (!FreeMarkerEngineUtil.resourceExists(resourcePath)) {
			resourcePath = _TPL_DEFAULT_PATH;
		}

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		// FreeMarker variables

		FreeMarkerVariablesUtil.insertVariables(
			freeMarkerContext, request);

		// Tag libraries

		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		Writer writer = new UnsyncStringWriter();

		// Portal JSP tag library factory

		TaglibFactory portalTaglib = new TaglibFactory(
			pageContext.getServletContext());

		freeMarkerContext.put("PortalJspTagLibs", portalTaglib);

		// FreeMarker JSP tag library support

		ServletContextHashModel servletContextHashModel =
			new ServletContextHashModel(
				(GenericServlet)pageContext.getPage(),
				ObjectWrapper.DEFAULT_WRAPPER);

		freeMarkerContext.put("Application", servletContextHashModel);

		HttpRequestHashModel httpRequestHashModel = new HttpRequestHashModel(
			request, response, ObjectWrapper.DEFAULT_WRAPPER);

		freeMarkerContext.put("Request", httpRequestHashModel);

		// Merge templates

		FreeMarkerEngineUtil.mergeTemplate(
			resourcePath, freeMarkerContext, writer);

		return ((UnsyncStringWriter)writer).toString();
	}

	private static final String _DEFAULT_NAMESPACE = "alloy";

	private static final String _TPL_DEFAULT_PATH =
		"com/liferay/portlet/dynamicdatamapping/dependencies/alloy/text.ftl";

	private static final String _TPL_EXT = ".ftl";

	private static final String _TPL_PATH =
		"com/liferay/portlet/dynamicdatamapping/dependencies/";

	private static final String _TYPE_RADIO = "radio";

	private static final String _TYPE_SELECT = "select";

}