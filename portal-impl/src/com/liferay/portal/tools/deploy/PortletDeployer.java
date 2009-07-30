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

package com.liferay.portal.tools.deploy;

import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.xml.DocumentImpl;
import com.liferay.util.TextFormatter;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.liferay.util.xml.XMLMerger;
import com.liferay.util.xml.descriptor.FacesXMLDescriptor;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <a href="PortletDeployer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 */
public class PortletDeployer extends BaseDeployer {

	public static final String JSF_MYFACES =
		"org.apache.myfaces.portlet.MyFacesGenericPortlet";

	public static final String JSF_STANDARD =
		"javax.portlet.faces.GenericFacesPortlet";

	public static final String JSF_SUN =
		"com.sun.faces.portlet.FacesPortlet";

	public static final String LIFERAY_RENDER_KIT_FACTORY =
		"com.liferay.util.jsf.sun.faces.renderkit.LiferayRenderKitFactoryImpl";

	public static final String MYFACES_CONTEXT_FACTORY =
		"com.liferay.util.bridges.jsf.myfaces.MyFacesContextFactoryImpl";

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		List<String> wars = new ArrayList<String>();
		List<String> jars = new ArrayList<String>();

		for (String arg : args) {
			if (arg.endsWith(".war")) {
				wars.add(arg);
			}
			else if (arg.endsWith(".jar")) {
				jars.add(arg);
			}
		}

		new PortletDeployer(wars, jars);
	}

	protected PortletDeployer() {
	}

	protected PortletDeployer(List<String> wars, List<String> jars) {
		super(wars, jars);
	}

	protected void checkArguments() {
		super.checkArguments();

		if (Validator.isNull(portletTaglibDTD)) {
			throw new IllegalArgumentException(
				"The system property deployer.portlet.taglib.dtd is not set");
		}
	}

	protected void copyXmls(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		super.copyXmls(srcFile, displayName, pluginPackage);

		if (appServerType.equals(ServerDetector.TOMCAT_ID)) {
			copyDependencyXml("context.xml", srcFile + "/META-INF");
		}
	}

	protected String getExtraContent(
			double webXmlVersion, File srcFile, String displayName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		String extraContent = super.getExtraContent(
			webXmlVersion, srcFile, displayName);

		sb.append(extraContent);

		if (ServerDetector.isWebSphere()) {
			sb.append("<context-param>");
			sb.append("<param-name>");
			sb.append("com.ibm.websphere.portletcontainer.");
			sb.append("PortletDeploymentEnabled");
			sb.append("</param-name>");
			sb.append("<param-value>false</param-value>");
			sb.append("</context-param>");
		}

		File facesXML = new File(srcFile + "/WEB-INF/faces-config.xml");
		File portletXML = new File(
			srcFile + "/WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_STANDARD);
		File webXML = new File(srcFile + "/WEB-INF/web.xml");

		updatePortletXML(portletXML);

		sb.append(getServletContent(portletXML, webXML));

		setupJSF(facesXML, portletXML);

		if (_sunFacesPortlet) {

			// LiferayConfigureListener

			sb.append("<listener>");
			sb.append("<listener-class>");
			sb.append("com.liferay.util.bridges.jsf.sun.");
			sb.append("LiferayConfigureListener");
			sb.append("</listener-class>");
			sb.append("</listener>");
		}

		// PortletContextListener

		sb.append("<listener>");
		sb.append("<listener-class>");
		sb.append("com.liferay.portal.kernel.servlet.PortletContextListener");
		sb.append("</listener-class>");
		sb.append("</listener>");

		// Speed filters

		sb.append(getSpeedFiltersContent(srcFile));

		return sb.toString();
	}

	protected String getServletContent(File portletXML, File webXML)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		// Add wrappers for portlets

		Document doc = SAXReaderUtil.read(portletXML);

		Element root = doc.getRootElement();

		Iterator<Element> itr1 = root.elements("portlet").iterator();

		while (itr1.hasNext()) {
			Element portlet = itr1.next();

			String portletName = PortalUtil.getJsSafePortletId(
				portlet.elementText("portlet-name"));
			String portletClass = portlet.elementText("portlet-class");

			sb.append("<servlet>");
			sb.append("<servlet-name>");
			sb.append(portletName);
			sb.append("</servlet-name>");
			sb.append("<servlet-class>");
			sb.append("com.liferay.portal.kernel.servlet.PortletServlet");
			sb.append("</servlet-class>");
			sb.append("<init-param>");
			sb.append("<param-name>portlet-class</param-name>");
			sb.append("<param-value>");
			sb.append(portletClass);
			sb.append("</param-value>");
			sb.append("</init-param>");
			sb.append("<load-on-startup>0</load-on-startup>");
			sb.append("</servlet>");

			sb.append("<servlet-mapping>");
			sb.append("<servlet-name>");
			sb.append(portletName);
			sb.append("</servlet-name>");
			sb.append("<url-pattern>/");
			sb.append(portletName);
			sb.append("/*</url-pattern>");
			sb.append("</servlet-mapping>");
		}

		// Make sure there is a company id specified

		doc = SAXReaderUtil.read(webXML);

		root = doc.getRootElement();

		// Remove deprecated references to SharedServletWrapper

		itr1 = root.elements("servlet").iterator();

		while (itr1.hasNext()) {
			Element servlet = itr1.next();

			String icon = servlet.elementText("icon");
			String servletName = servlet.elementText("servlet-name");
			String displayName = servlet.elementText("display-name");
			String description = servlet.elementText("description");
			String servletClass = servlet.elementText("servlet-class");
			List<Element> initParams = servlet.elements("init-param");
			String loadOnStartup = servlet.elementText("load-on-startup");
			String runAs = servlet.elementText("run-as");
			List<Element> securityRoleRefs = servlet.elements(
				"security-role-ref");

			if ((servletClass != null) &&
				(servletClass.equals(
					"com.liferay.portal.servlet.SharedServletWrapper"))) {

				sb.append("<servlet>");

				if (icon != null) {
					sb.append("<icon>");
					sb.append(icon);
					sb.append("</icon>");
				}

				if (servletName != null) {
					sb.append("<servlet-name>");
					sb.append(servletName);
					sb.append("</servlet-name>");
				}

				if (displayName != null) {
					sb.append("<display-name>");
					sb.append(displayName);
					sb.append("</display-name>");
				}

				if (description != null) {
					sb.append("<description>");
					sb.append(description);
					sb.append("</description>");
				}

				Iterator<Element> itr2 = initParams.iterator();

				while (itr2.hasNext()) {
					Element initParam = itr2.next();

					String paramName = initParam.elementText("param-name");
					String paramValue = initParam.elementText("param-value");

					if ((paramName != null) &&
						(paramName.equals("servlet-class"))) {

						sb.append("<servlet-class>");
						sb.append(paramValue);
						sb.append("</servlet-class>");
					}
				}

				itr2 = initParams.iterator();

				while (itr2.hasNext()) {
					Element initParam = itr2.next();

					String paramName = initParam.elementText("param-name");
					String paramValue = initParam.elementText("param-value");
					String paramDesc = initParam.elementText("description");

					if ((paramName != null) &&
						(!paramName.equals("servlet-class"))) {

						sb.append("<init-param>");
						sb.append("<param-name>");
						sb.append(paramName);
						sb.append("</param-name>");

						if (paramValue != null) {
							sb.append("<param-value>");
							sb.append(paramValue);
							sb.append("</param-value>");
						}

						if (paramDesc != null) {
							sb.append("<description>");
							sb.append(paramDesc);
							sb.append("</description>");
						}

						sb.append("</init-param>");
					}
				}

				if (loadOnStartup != null) {
					sb.append("<load-on-startup>");
					sb.append(loadOnStartup);
					sb.append("</load-on-startup>");
				}

				if (runAs != null) {
					sb.append("<run-as>");
					sb.append(runAs);
					sb.append("</run-as>");
				}

				itr2 = securityRoleRefs.iterator();

				while (itr2.hasNext()) {
					Element roleRef = itr2.next();

					String roleDesc = roleRef.elementText("description");
					String roleName = roleRef.elementText("role-name");
					String roleLink = roleRef.elementText("role-link");

					sb.append("<security-role-ref>");

					if (roleDesc != null) {
						sb.append("<description>");
						sb.append(roleDesc);
						sb.append("</description>");
					}

					if (roleName != null) {
						sb.append("<role-name>");
						sb.append(roleName);
						sb.append("</role-name>");
					}

					if (roleLink != null) {
						sb.append("<role-link>");
						sb.append(roleLink);
						sb.append("</role-link>");
					}

					sb.append("</security-role-ref>");
				}

				sb.append("</servlet>");
			}
		}

		return sb.toString();
	}

	protected void processPluginPackageProperties(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		if (pluginPackage == null) {
			return;
		}

		Properties properties = getPluginPackageProperties(srcFile);

		if ((properties == null) || (properties.size() == 0)) {
			return;
		}

		String moduleGroupId = pluginPackage.getGroupId();
		String moduleArtifactId = pluginPackage.getArtifactId();
		String moduleVersion = pluginPackage.getVersion();

		String pluginName = pluginPackage.getName();
		String pluginType = pluginPackage.getTypes().get(0);
		String pluginTypeName = TextFormatter.format(
			pluginType, TextFormatter.J);

		if (!pluginType.equals(Plugin.TYPE_PORTLET)) {
			return;
		}

		String tags = getPluginPackageTagsXml(pluginPackage.getTags());
		String shortDescription = pluginPackage.getShortDescription();
		String longDescription = pluginPackage.getLongDescription();
		String changeLog = pluginPackage.getChangeLog();
		String pageURL = pluginPackage.getPageURL();
		String author = pluginPackage.getAuthor();
		String licenses = getPluginPackageLicensesXml(
			pluginPackage.getLicenses());
		String liferayVersions = getPluginPackageLiferayVersionsXml(
			pluginPackage.getLiferayVersions());

		Map<String, String> filterMap = new HashMap<String, String>();

		filterMap.put("module_group_id", moduleGroupId);
		filterMap.put("module_artifact_id", moduleArtifactId);
		filterMap.put("module_version", moduleVersion);

		filterMap.put("plugin_name", pluginName);
		filterMap.put("plugin_type", pluginType);
		filterMap.put("plugin_type_name", pluginTypeName);

		filterMap.put("tags", tags);
		filterMap.put("short_description", shortDescription);
		filterMap.put("long_description", longDescription);
		filterMap.put("change_log", changeLog);
		filterMap.put("page_url", pageURL);
		filterMap.put("author", author);
		filterMap.put("licenses", licenses);
		filterMap.put("liferay_versions", liferayVersions);

		copyDependencyXml(
			"liferay-plugin-package.xml", srcFile + "/WEB-INF", filterMap,
			true);
	}

	protected void setupJSF(File facesXML, File portletXML) throws Exception {
		_myFacesPortlet = false;
		_sunFacesPortlet = false;

		if (!facesXML.exists()) {
			return;
		}

		// portlet.xml

		Document doc = SAXReaderUtil.read(portletXML, true);

		Element root = doc.getRootElement();

		List<Element> elements = root.elements("portlet");

		Iterator<Element> itr = elements.iterator();

		while (itr.hasNext()) {
			Element portlet = itr.next();

			String portletClass = portlet.elementText("portlet-class");

			if (portletClass.equals(JSF_MYFACES)) {
				_myFacesPortlet = true;

				break;
			}
			else if (portletClass.equals(JSF_SUN)) {
				_sunFacesPortlet = true;

				break;
			}
		}

		// faces-config.xml

		doc = SAXReaderUtil.read(facesXML, true);

		root = doc.getRootElement();

		Element factoryEl = root.element("factory");

		Element renderKitFactoryEl = null;
		Element facesContextFactoryEl = null;

		if (factoryEl == null) {
			factoryEl = root.addElement("factory");
		}

		renderKitFactoryEl = factoryEl.element("render-kit-factory");
		facesContextFactoryEl = factoryEl.element("faces-context-factory");

		if ((appServerType.equals("orion") && (_sunFacesPortlet) &&
			(renderKitFactoryEl == null))) {

			renderKitFactoryEl = factoryEl.addElement("render-kit-factory");

			renderKitFactoryEl.addText(LIFERAY_RENDER_KIT_FACTORY);
		}
		else if (_myFacesPortlet && (facesContextFactoryEl == null)) {
			facesContextFactoryEl =
				factoryEl.addElement("faces-context-factory");

			facesContextFactoryEl.addText(MYFACES_CONTEXT_FACTORY);
		}

		if (!appServerType.equals("orion") && (_sunFacesPortlet)) {
			factoryEl.detach();
		}

		XMLMerger merger = new XMLMerger(new FacesXMLDescriptor());

		DocumentImpl docImpl = (DocumentImpl)doc;

		merger.organizeXML(docImpl.getWrappedDocument());

		FileUtil.write(facesXML, doc.formattedString(), true);
	}

	protected void updateDeployDirectory(File srcFile) throws Exception {
		try {
			if (!PrefsPropsUtil.getBoolean(
					PropsKeys.AUTO_DEPLOY_CUSTOM_PORTLET_XML,
					PropsValues.AUTO_DEPLOY_CUSTOM_PORTLET_XML)) {

				return;
			}
		}
		catch (Exception e) {

			// This will only happen when running the deploy tool in Ant in the
			// classical way where the WAR file is actually massaged and
			// packaged.

			if (!PropsValues.AUTO_DEPLOY_CUSTOM_PORTLET_XML) {
				return;
			}
		}

		File portletXML = new File(
			srcFile + "/WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_STANDARD);

		if (portletXML.exists()) {
			File portletCustomXML = new File(
				srcFile + "/WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_CUSTOM);

			if (portletCustomXML.exists()) {
				portletCustomXML.delete();
			}

			portletXML.renameTo(portletCustomXML);
		}
	}

	protected void updatePortletXML(File portletXML) throws Exception {
		if (!portletXML.exists()) {
			return;
		}

		String content = FileUtil.read(portletXML);

		content = StringUtil.replace(
			content, "com.liferay.util.bridges.jsp.JSPPortlet",
			MVCPortlet.class.getName());

		FileUtil.write(portletXML, content);
	}

	private boolean _myFacesPortlet;
	private boolean _sunFacesPortlet;

}