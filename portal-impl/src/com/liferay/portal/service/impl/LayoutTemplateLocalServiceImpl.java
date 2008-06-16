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

package com.liferay.portal.service.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.model.impl.LayoutTemplateImpl;
import com.liferay.portal.service.PluginSettingLocalServiceUtil;
import com.liferay.portal.service.base.LayoutTemplateLocalServiceBaseImpl;
import com.liferay.portal.util.DocumentUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.xml.ElementImpl;
import com.liferay.portlet.layoutconfiguration.util.velocity.InitColumnProcessor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * <a href="LayoutTemplateLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Ivica Cardic
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
*/
public class LayoutTemplateLocalServiceImpl
	extends LayoutTemplateLocalServiceBaseImpl {

	public String getContent(
			String layoutTemplateId, boolean standard, String themeId)
		throws SystemException {

		LayoutTemplate layoutTemplate = getLayoutTemplate(
			layoutTemplateId, standard, themeId);

		if (layoutTemplate == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Layout template " + layoutTemplateId + " does not exist");
			}

			layoutTemplate = getLayoutTemplate(
				PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID, standard, themeId);

			if (layoutTemplate == null) {
				_log.error(
					"Layout template " + layoutTemplateId +
						" and default layout template " +
							PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID +
								" do not exist");

				return StringPool.BLANK;
			}
		}

		if (PropsValues.LAYOUT_TEMPLATE_CACHE_ENABLED) {
			return layoutTemplate.getContent();
		}
		else {
			try {
				return layoutTemplate.getUncachedContent();
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}
	}

	public LayoutTemplate getLayoutTemplate(
		String layoutTemplateId, boolean standard, String themeId) {

		if (Validator.isNull(layoutTemplateId)) {
			return null;
		}

		LayoutTemplate layoutTemplate = null;

		if (themeId != null) {
			if (standard) {
				layoutTemplate = _getThemesStandard(themeId).get(
					layoutTemplateId);
			}
			else {
				layoutTemplate = _getThemesCustom(themeId).get(
					layoutTemplateId);
			}

			if (layoutTemplate != null) {
				return layoutTemplate;
			}
		}

		if (standard) {
			layoutTemplate = _warStandard.get(layoutTemplateId);

			if (layoutTemplate == null) {
				layoutTemplate = _portalStandard.get(layoutTemplateId);
			}
		}
		else {
			layoutTemplate = _warCustom.get(layoutTemplateId);

			if (layoutTemplate == null) {
				layoutTemplate = _portalCustom.get(layoutTemplateId);
			}
		}

		return layoutTemplate;
	}

	public List<LayoutTemplate> getLayoutTemplates() {
		List<LayoutTemplate> customLayoutTemplates =
			new ArrayList<LayoutTemplate>(
							_portalCustom.size() + _warCustom.size());

		customLayoutTemplates.addAll(
			ListUtil.fromCollection(_portalCustom.values()));

		customLayoutTemplates.addAll(
			ListUtil.fromCollection(_warCustom.values()));

		return customLayoutTemplates;
	}

	public List<LayoutTemplate> getLayoutTemplates(String themeId) {
		Map<String, LayoutTemplate> _themesCustom = _getThemesCustom(themeId);

		List<LayoutTemplate> customLayoutTemplates =
			new ArrayList<LayoutTemplate>(
				_portalCustom.size() + _warCustom.size() +
					_themesCustom.size());

		Iterator<Map.Entry<String, LayoutTemplate>> itr =
			_portalCustom.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, LayoutTemplate> entry = itr.next();

			String layoutTemplateId = entry.getKey();
			LayoutTemplate layoutTemplate = entry.getValue();

			if (_themesCustom.containsKey(layoutTemplateId)) {
				customLayoutTemplates.add(_themesCustom.get(layoutTemplateId));
			}
			else if (_warCustom.containsKey(layoutTemplateId)) {
				customLayoutTemplates.add(_warCustom.get(layoutTemplateId));
			}
			else {
				customLayoutTemplates.add(layoutTemplate);
			}
		}

		itr = _warCustom.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, LayoutTemplate> entry = itr.next();

			String layoutTemplateId = entry.getKey();

			if (!_portalCustom.containsKey(layoutTemplateId) &&
				!_themesCustom.containsKey(layoutTemplateId)) {

				customLayoutTemplates.add(_warCustom.get(layoutTemplateId));
			}
		}

		itr = _themesCustom.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, LayoutTemplate> entry = itr.next();

			String layoutTemplateId = entry.getKey();

			if (!_portalCustom.containsKey(layoutTemplateId) &&
				!_warCustom.containsKey(layoutTemplateId)) {

				customLayoutTemplates.add(_themesCustom.get(layoutTemplateId));
			}
		}

		return customLayoutTemplates;
	}

	public String getWapContent(
			String layoutTemplateId, boolean standard, String themeId)
		throws SystemException {

		LayoutTemplate layoutTemplate = getLayoutTemplate(
			layoutTemplateId, standard, themeId);

		if (layoutTemplate == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Layout template " + layoutTemplateId + " does not exist");
			}

			layoutTemplate = getLayoutTemplate(
				PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID, standard, themeId);

			if (layoutTemplate == null) {
				_log.error(
					"Layout template " + layoutTemplateId +
						" and default layout template " +
							PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID +
								" do not exist");

				return StringPool.BLANK;
			}
		}

		if (GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.LAYOUT_TEMPLATE_CACHE_ENABLED))) {

			return layoutTemplate.getWapContent();
		}
		else {
			try {
				return layoutTemplate.getUncachedWapContent();
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}
	}

	public List<ObjectValuePair<String, Boolean>> init(
		ServletContext ctx, String[] xmls, PluginPackage pluginPackage) {

		return init(null, ctx, xmls, pluginPackage);
	}

	public List<ObjectValuePair<String, Boolean>> init(
		String servletContextName, ServletContext ctx, String[] xmls,
		PluginPackage pluginPackage) {

		List<ObjectValuePair<String, Boolean>> layoutTemplateIds =
			new ArrayList<ObjectValuePair<String, Boolean>>();

		try {
			for (int i = 0; i < xmls.length; i++) {
				Set<ObjectValuePair<String, Boolean>> curLayoutTemplateIds =
					_readLayoutTemplates(
						servletContextName, ctx, xmls[i], pluginPackage);

				Iterator<ObjectValuePair<String, Boolean>> itr =
					curLayoutTemplateIds.iterator();

				while (itr.hasNext()) {
					ObjectValuePair<String, Boolean> ovp = itr.next();

					if (!layoutTemplateIds.contains(ovp)) {
						layoutTemplateIds.add(ovp);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return layoutTemplateIds;
	}

	public void readLayoutTemplate(
		String servletContextName, ServletContext ctx,
		Set<ObjectValuePair<String, Boolean>> layoutTemplateIds,
		com.liferay.portal.kernel.xml.Element el, boolean standard,
		String themeId, PluginPackage pluginPackage) {

		Map<String, LayoutTemplate> layoutTemplates = null;

		if (themeId != null) {
			if (standard) {
				layoutTemplates = _getThemesStandard(themeId);
			}
			else {
				layoutTemplates = _getThemesCustom(themeId);
			}
		}
		else if (servletContextName != null) {
			if (standard) {
				layoutTemplates = _warStandard;
			}
			else {
				layoutTemplates = _warCustom;
			}
		}
		else {
			if (standard) {
				layoutTemplates = _portalStandard;
			}
			else {
				layoutTemplates = _portalCustom;
			}
		}

		Iterator<com.liferay.portal.kernel.xml.Element> itr = el.elements(
			"layout-template").iterator();

		while (itr.hasNext()) {
			com.liferay.portal.kernel.xml.Element layoutTemplate = itr.next();

			String layoutTemplateId = layoutTemplate.attributeValue("id");

			if (layoutTemplateIds != null) {
				ObjectValuePair<String, Boolean> ovp =
					new ObjectValuePair<String, Boolean>(
						layoutTemplateId, standard);

				layoutTemplateIds.add(ovp);
			}

			LayoutTemplate layoutTemplateModel = layoutTemplates.get(
				layoutTemplateId);

			if (layoutTemplateModel == null) {
				layoutTemplateModel = new LayoutTemplateImpl(layoutTemplateId);

				layoutTemplates.put(layoutTemplateId, layoutTemplateModel);
			}

			PluginSetting pluginSetting =
				PluginSettingLocalServiceUtil.getDefaultPluginSetting();

			layoutTemplateModel.setPluginPackage(pluginPackage);
			layoutTemplateModel.setServletContext(ctx);

			if (servletContextName != null) {
				layoutTemplateModel.setServletContextName(servletContextName);
			}

			layoutTemplateModel.setStandard(standard);
			layoutTemplateModel.setName(GetterUtil.getString(
				layoutTemplate.attributeValue("name"),
				layoutTemplateModel.getName()));
			layoutTemplateModel.setTemplatePath(GetterUtil.getString(
				layoutTemplate.elementText("template-path"),
				layoutTemplateModel.getTemplatePath()));
			layoutTemplateModel.setWapTemplatePath(GetterUtil.getString(
				layoutTemplate.elementText("wap-template-path"),
				layoutTemplateModel.getWapTemplatePath()));
			layoutTemplateModel.setThumbnailPath(GetterUtil.getString(
				layoutTemplate.elementText("thumbnail-path"),
				layoutTemplateModel.getThumbnailPath()));

			String content = null;

			try {
				content = HttpUtil.URLtoString(ctx.getResource(
					layoutTemplateModel.getTemplatePath()));
			}
			catch (Exception e) {
				_log.error(
					"Unable to get content at template path " +
						layoutTemplateModel.getTemplatePath() + ": " +
							e.getMessage());
			}

			if (Validator.isNull(content)) {
				_log.error(
					"No content found at template path " +
						layoutTemplateModel.getTemplatePath());
			}
			else {
				layoutTemplateModel.setContent(content);
				layoutTemplateModel.setColumns(_getColumns(content));
			}

			if (Validator.isNull(layoutTemplateModel.getWapTemplatePath())) {
				_log.error(
					"The element wap-template-path is not defined for " +
						layoutTemplateId);
			}
			else {
				String wapContent = null;

				try {
					wapContent = HttpUtil.URLtoString(ctx.getResource(
						layoutTemplateModel.getWapTemplatePath()));
				}
				catch (Exception e) {
					_log.error(
						"Unable to get content at WAP template path " +
							layoutTemplateModel.getWapTemplatePath() + ": " +
								e.getMessage());
				}

				if (Validator.isNull(wapContent)) {
					_log.error(
						"No content found at WAP template path " +
							layoutTemplateModel.getWapTemplatePath());
				}
				else {
					layoutTemplateModel.setWapContent(wapContent);
				}
			}

			com.liferay.portal.kernel.xml.Element rolesEl =
				layoutTemplate.element("roles");

			if (rolesEl != null) {
				Iterator<com.liferay.portal.kernel.xml.Element> itr2 =
					rolesEl.elements("role-name").iterator();

				while (itr2.hasNext()) {
					com.liferay.portal.kernel.xml.Element roleNameEl =
						itr2.next();

					pluginSetting.addRole(roleNameEl.getText());
				}
			}

			layoutTemplateModel.setDefaultPluginSetting(pluginSetting);
		}
	}

	public void uninstallLayoutTemplate(
		String layoutTemplateId, boolean standard) {

		if (standard) {
			_warStandard.remove(layoutTemplateId);
		}
		else {
			_warCustom.remove(layoutTemplateId);
		}
	}

	public void uninstallLayoutTemplates(String themeId) {
		_getThemesStandard(themeId).clear();
		_getThemesCustom(themeId).clear();
	}

	private List<String> _getColumns(String content) {
		try {
			InitColumnProcessor processor = new InitColumnProcessor();

			VelocityContext context = new VelocityContext();

			context.put("processor", processor);

			Velocity.evaluate(
				context, new PrintWriter(new StringWriter()),
				LayoutTemplateLocalServiceImpl.class.getName(), content);

			List<String> columns = processor.getColumns();

			Collections.sort(columns);

			return columns;
		}
		catch (Exception e) {
			_log.error(e);

			return new ArrayList<String>();
		}
	}

	private Set<ObjectValuePair<String, Boolean>> _readLayoutTemplates(
			String servletContextName, ServletContext ctx, String xml,
			PluginPackage pluginPackage)
		throws DocumentException {

		Set<ObjectValuePair<String, Boolean>> layoutTemplateIds =
			new HashSet<ObjectValuePair<String, Boolean>>();

		if (xml == null) {
			return layoutTemplateIds;
		}

		Document doc = DocumentUtil.readDocumentFromXML(xml, true);

		Element root = doc.getRootElement();

		Element standardEl = root.element("standard");

		if (standardEl != null) {
			readLayoutTemplate(
				servletContextName, ctx, layoutTemplateIds,
				new ElementImpl(standardEl), true, null, pluginPackage);
		}

		Element customEl = root.element("custom");

		if (customEl != null) {
			readLayoutTemplate(
				servletContextName, ctx, layoutTemplateIds,
				new ElementImpl(customEl), false, null, pluginPackage);
		}

		return layoutTemplateIds;
	}

	private Map<String, LayoutTemplate> _getThemesCustom(String themeId) {
		String key = themeId + _CUSTOM_SEPARATOR;

		Map<String, LayoutTemplate> layoutTemplates = _themes.get(key);

		if (layoutTemplates == null) {
			layoutTemplates = new LinkedHashMap<String, LayoutTemplate>();

			_themes.put(key, layoutTemplates);
		}

		return layoutTemplates;
	}

	private Map<String, LayoutTemplate> _getThemesStandard(String themeId) {
		String key = themeId + _STANDARD_SEPARATOR;

		Map<String, LayoutTemplate> layoutTemplates = _themes.get(key);

		if (layoutTemplates == null) {
			layoutTemplates = new LinkedHashMap<String, LayoutTemplate>();

			_themes.put(key, layoutTemplates);
		}

		return layoutTemplates;
	}

	private static final String _STANDARD_SEPARATOR = "_STANDARD_";

	private static final String _CUSTOM_SEPARATOR = "_CUSTOM_";

	private static Log _log =
		LogFactory.getLog(LayoutTemplateLocalServiceImpl.class);

	private static Map<String, LayoutTemplate> _portalStandard =
		new LinkedHashMap<String, LayoutTemplate>();
	private static Map<String, LayoutTemplate> _portalCustom =
		new LinkedHashMap<String, LayoutTemplate>();

	private static Map<String, LayoutTemplate> _warStandard =
		new LinkedHashMap<String, LayoutTemplate>();
	private static Map<String, LayoutTemplate> _warCustom =
		new LinkedHashMap<String, LayoutTemplate>();

	private static Map<String, Map<String, LayoutTemplate>> _themes =
		new LinkedHashMap<String, Map<String, LayoutTemplate>>();

}