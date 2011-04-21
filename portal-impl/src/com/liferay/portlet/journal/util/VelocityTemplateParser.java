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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.templateparser.BaseTemplateParser;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.templateparser.TransformException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.velocity.VelocityResourceListener;
import com.liferay.util.ContentUtil;
import com.liferay.util.PwdGenerator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.VelocityException;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class VelocityTemplateParser extends BaseTemplateParser {

	protected String doTransform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId,	String xml, String script)
		throws Exception {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		boolean load = false;

		try {
			VelocityContext velocityContext =
				VelocityEngineUtil.getWrappedRestrictedToolsContext();

			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			List<TemplateNode> nodes = extractDynamicContents(
				themeDisplay, root);

			for (TemplateNode node : nodes) {
				velocityContext.put(node.getName(), node);
			}

			velocityContext.put("xmlRequest", root.element("request").asXML());
			velocityContext.put(
				"request", insertRequestVariables(root.element("request")));

			long companyId = GetterUtil.getLong(tokens.get("company_id"));
			Company company = CompanyLocalServiceUtil.getCompanyById(companyId);
			long groupId = GetterUtil.getLong(tokens.get("group_id"));
			String templateId = tokens.get("template_id");
			String journalTemplatesPath =
				VelocityResourceListener.JOURNAL_SEPARATOR + StringPool.SLASH +
					companyId + StringPool.SLASH + groupId;
			String randomNamespace =
				PwdGenerator.getPassword(PwdGenerator.KEY3, 4) +
					StringPool.UNDERLINE;

			velocityContext.put("company", company);
			velocityContext.put("companyId", String.valueOf(companyId));
			velocityContext.put("groupId", String.valueOf(groupId));
			velocityContext.put("journalTemplatesPath", journalTemplatesPath);
			velocityContext.put("viewMode", viewMode);
			velocityContext.put(
				"locale", LocaleUtil.fromLanguageId(languageId));
			velocityContext.put(
				"permissionChecker",
				PermissionThreadLocal.getPermissionChecker());
			velocityContext.put("randomNamespace", randomNamespace);

			try {
				String velocityTemplateId = companyId + groupId + templateId;

				long companyGroupId = GetterUtil.getLong(
					tokens.get("company_group_id"));

				if (companyGroupId > 0) {
					velocityTemplateId =
						companyId + companyGroupId + templateId;
				}

				load = VelocityEngineUtil.mergeTemplate(
					velocityTemplateId, script, velocityContext,
					unsyncStringWriter);
			}
			catch (VelocityException ve) {
				velocityContext.put("exception", ve.getMessage());
				velocityContext.put("script", script);

				if (ve instanceof ParseErrorException) {
					ParseErrorException pe = (ParseErrorException)ve;

					velocityContext.put(
						"column", new Integer(pe.getColumnNumber()));
					velocityContext.put(
						"line", new Integer(pe.getLineNumber()));
				}

				String velocityTemplateId =
					PropsValues.JOURNAL_ERROR_TEMPLATE_VELOCITY;
				String velocityTemplateContent = ContentUtil.get(
					PropsValues.JOURNAL_ERROR_TEMPLATE_VELOCITY);

				load = VelocityEngineUtil.mergeTemplate(
					velocityTemplateId, velocityTemplateContent,
					velocityContext, unsyncStringWriter);
			}
		}
		catch (Exception e) {
			if (e instanceof DocumentException) {
				throw new TransformException("Unable to read XML document", e);
			}
			else if (e instanceof VelocityException) {
				VelocityException pex = (VelocityException)e;

				throw new TransformException(
					"Unable to parse velocity template: " +
						HtmlUtil.escape(pex.getMessage()),
					e);
			}
			else if (e instanceof IOException) {
				throw new TransformException(
					"Error reading velocity template", e);
			}
			else if (e instanceof TransformException) {
				throw (TransformException)e;
			}
			else {
				throw new TransformException("Unhandled exception", e);
			}
		}

		if (!load) {
			throw new TransformException(
				"Unable to dynamically load velocity transform script");
		}

		return unsyncStringWriter.toString();
	}

	protected List<TemplateNode> extractDynamicContents(
			ThemeDisplay themeDisplay, Element parent)
		throws TransformException {

		List<TemplateNode> nodes = new ArrayList<TemplateNode>();

		Map<String, TemplateNode> prototypeNodes =
			new HashMap<String, TemplateNode>();

		for (Element el : parent.elements("dynamic-element")) {
			Element content = el.element("dynamic-content");

			String data = StringPool.BLANK;

			if (content != null) {
				data = content.getText();
			}

			String name = el.attributeValue("name", "");

			if (name.length() == 0) {
				throw new TransformException(
					"Element missing \"name\" attribute");
			}

			String type = el.attributeValue("type", "");

			TemplateNode node = new TemplateNode(
				themeDisplay, name, stripCDATA(data), type);

			if (el.element("dynamic-element") != null) {
				node.appendChildren(extractDynamicContents(themeDisplay, el));
			}
			else if ((content != null) && (content.element("option") != null)) {
				for (Element option : content.elements("option")) {
					node.appendOption(stripCDATA(option.getText()));
				}
			}

			TemplateNode prototypeNode = prototypeNodes.get(name);

			if (prototypeNode == null) {
				prototypeNode = node;

				prototypeNodes.put(name, prototypeNode);

				nodes.add(node);
			}

			prototypeNode.appendSibling(node);
		}

		return nodes;
	}

	protected Map<String, Object> insertRequestVariables(Element parent) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (parent == null) {
			return map;
		}

		for (Element el : parent.elements()) {
			String name = el.getName();

			if (name.equals("attribute")) {
				map.put(el.elementText("name"), el.elementText("value"));
			}
			else if (name.equals("parameter")) {
				name = el.element("name").getText();

				List<Element> valueEls = el.elements("value");

				if (valueEls.size() == 1) {
					map.put(name, (valueEls.get(0)).getText());
				}
				else {
					List<String> values = new ArrayList<String>();

					for (Element valueEl : valueEls) {
						values.add(valueEl.getText());
					}

					map.put(name, values);
				}
			}
			else if (el.elements().size() > 0) {
				map.put(name, insertRequestVariables(el));
			}
			else {
				map.put(name, el.getText());
			}
		}

		return map;
	}

	protected String stripCDATA(String s) {
		if (s.startsWith(StringPool.CDATA_OPEN) &&
			s.endsWith(StringPool.CDATA_CLOSE)) {

			s = s.substring(
				StringPool.CDATA_OPEN.length(),
				s.length() - StringPool.CDATA_CLOSE.length());
		}

		return s;
	}

}