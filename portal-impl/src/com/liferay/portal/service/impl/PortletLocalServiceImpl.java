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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.EventDefinition;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletCategory;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletFilter;
import com.liferay.portal.model.PortletInfo;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.impl.EventDefinitionImpl;
import com.liferay.portal.model.impl.PortletAppImpl;
import com.liferay.portal.model.impl.PortletFilterImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.model.impl.PortletURLListenerImpl;
import com.liferay.portal.model.impl.PublicRenderParameterImpl;
import com.liferay.portal.service.base.PortletLocalServiceBaseImpl;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.DocumentUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.QNameUtil;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.util.ListUtil;

import java.io.IOException;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletMode;
import javax.portlet.PreferencesValidator;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * <a href="PortletLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Aug�
 *
 */
public class PortletLocalServiceImpl extends PortletLocalServiceBaseImpl {

	public void destroyPortlet(Portlet portlet) {
		Map<String, Portlet> portletsPool = _getPortletsPool();

		portletsPool.remove(portlet.getRootPortletId());

		PortletApp portletApp = portlet.getPortletApp();

		_portletAppsPool.remove(portletApp.getServletContextName());

		_clearCaches();
	}

	public PortletCategory getEARDisplay(String xml) throws SystemException {
		try {
			return _readLiferayDisplayXML(xml);
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public PortletCategory getWARDisplay(String servletContextName, String xml)
		throws SystemException {

		try {
			return _readLiferayDisplayXML(servletContextName, xml);
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public List<FriendlyURLMapper> getFriendlyURLMappers() {
		return _getFriendlyURLMappers();
	}

	public Portlet getPortletById(long companyId, String portletId)
		throws SystemException {

		portletId = PortalUtil.getJsSafePortletId(portletId);

		Portlet portlet = null;

		Map<String, Portlet> companyPortletsPool = _getPortletsPool(companyId);

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		if (portletId.equals(rootPortletId)) {
			portlet = companyPortletsPool.get(portletId);
		}
		else {
			portlet = companyPortletsPool.get(rootPortletId);

			if (portlet != null) {
				portlet = portlet.getClonedInstance(portletId);
			}
		}

		if ((portlet == null) &&
			(!portletId.equals(PortletKeys.LIFERAY_PORTAL))) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Portlet not found for " + companyId + " " + portletId);
			}
		}

		return portlet;
	}

	public Portlet getPortletByStrutsPath(long companyId, String strutsPath)
		throws SystemException {

		return getPortletById(companyId, _getPortletId(strutsPath));
	}

	public List<Portlet> getPortlets() {
		Map<String, Portlet> portletsPool = _getPortletsPool();

		return ListUtil.fromCollection(portletsPool.values());
	}

	public List<Portlet> getPortlets(long companyId) throws SystemException {
		return getPortlets(companyId, true, true);
	}

	public List<Portlet> getPortlets(
			long companyId, boolean showSystem, boolean showPortal)
		throws SystemException {

		Map<String, Portlet> portletsPool = _getPortletsPool(companyId);

		List<Portlet> portlets = ListUtil.fromCollection(portletsPool.values());

		if (!showSystem || !showPortal) {
			Iterator<Portlet> itr = portlets.iterator();

			while (itr.hasNext()) {
				Portlet portlet = itr.next();

				if (showPortal &&
					portlet.getPortletId().equals(PortletKeys.PORTAL)) {

				}
				else if (!showPortal &&
						 portlet.getPortletId().equals(PortletKeys.PORTAL)) {

					itr.remove();
				}
				else if (!showSystem && portlet.isSystem()) {
					itr.remove();
				}
			}
		}

		return portlets;
	}

	public boolean hasPortlet(long companyId, String portletId)
		throws SystemException {

		portletId = PortalUtil.getJsSafePortletId(portletId);

		Portlet portlet = null;

		Map<String, Portlet> companyPortletsPool = _getPortletsPool(companyId);

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		if (portletId.equals(rootPortletId)) {
			portlet = companyPortletsPool.get(portletId);
		}
		else {
			portlet = companyPortletsPool.get(rootPortletId);
		}

		if (portlet == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public void initEAR(String[] xmls, PluginPackage pluginPackage) {

		// Clear pools every time initEAR is called. See LEP-5452.

		_portletAppsPool.clear();
		_portletsPool.clear();
		_companyPortletsPool.clear();
		_portletIdsByStrutsPath.clear();
		_friendlyURLMapperPortlets.clear();

		Map<String, Portlet> portletsPool = _getPortletsPool();

		try {
			List<String> servletURLPatterns = _readWebXML(xmls[4]);

			Set<String> portletIds = _readPortletXML(
				xmls[0], portletsPool, servletURLPatterns, pluginPackage);

			portletIds.addAll(_readPortletXML(
				xmls[1], portletsPool, servletURLPatterns, pluginPackage));

			Set<String> liferayPortletIds =
				_readLiferayPortletXML(xmls[2], portletsPool);

			liferayPortletIds.addAll(
				_readLiferayPortletXML(xmls[3], portletsPool));

			// Check for missing entries in liferay-portlet.xml

			Iterator<String> portletIdsItr = portletIds.iterator();

			while (portletIdsItr.hasNext()) {
				String portletId = portletIdsItr.next();

				if (_log.isWarnEnabled() &&
					!liferayPortletIds.contains(portletId)) {

					_log.warn(
						"Portlet with the name " + portletId +
							" is described in portlet.xml but does not " +
								"have a matching entry in liferay-portlet.xml");
				}
			}

			// Check for missing entries in portlet.xml

			Iterator<String> liferayPortletIdsItr =
				liferayPortletIds.iterator();

			while (liferayPortletIdsItr.hasNext()) {
				String portletId = liferayPortletIdsItr.next();

				if (_log.isWarnEnabled() && !portletIds.contains(portletId)) {
					_log.warn(
						"Portlet with the name " + portletId +
							" is described in liferay-portlet.xml but does " +
								"not have a matching entry in portlet.xml");
				}
			}

			// Remove portlets that should not be included

			Iterator<Map.Entry<String, Portlet>> portletPoolsItr =
				portletsPool.entrySet().iterator();

			while (portletPoolsItr.hasNext()) {
				Map.Entry<String, Portlet> entry = portletPoolsItr.next();

				Portlet portletModel = entry.getValue();

				if (!portletModel.getPortletId().equals(PortletKeys.ADMIN) &&
					!portletModel.getPortletId().equals(
						PortletKeys.MY_ACCOUNT) &&
					!portletModel.isInclude()) {

					portletPoolsItr.remove();
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public List<Portlet> initWAR(
		String servletContextName, String[] xmls, PluginPackage pluginPackage) {

		List<Portlet> portlets = new ArrayList<Portlet>();

		Map<String, Portlet> portletsPool = _getPortletsPool();

		try {
			List<String> servletURLPatterns = _readWebXML(xmls[3]);

			Set<String> portletIds = _readPortletXML(
				servletContextName, xmls[0], portletsPool, servletURLPatterns,
				pluginPackage);

			portletIds.addAll(_readPortletXML(
				servletContextName, xmls[1], portletsPool, servletURLPatterns,
				pluginPackage));

			Set<String> liferayPortletIds = _readLiferayPortletXML(
				servletContextName, xmls[2], portletsPool);

			// Check for missing entries in liferay-portlet.xml

			Iterator<String> itr = portletIds.iterator();

			while (itr.hasNext()) {
				String portletId = itr.next();

				if (_log.isWarnEnabled() &&
					!liferayPortletIds.contains(portletId)) {

					_log.warn(
						"Portlet with the name " + portletId +
							" is described in portlet.xml but does not " +
								"have a matching entry in liferay-portlet.xml");
				}
			}

			// Check for missing entries in portlet.xml

			itr = liferayPortletIds.iterator();

			while (itr.hasNext()) {
				String portletId = itr.next();

				if (_log.isWarnEnabled() && !portletIds.contains(portletId)) {
					_log.warn(
						"Portlet with the name " + portletId +
							" is described in liferay-portlet.xml but does " +
								"not have a matching entry in portlet.xml");
				}
			}

			// Return the new portlets

			itr = portletIds.iterator();

			while (itr.hasNext()) {
				String portletId = itr.next();

				Portlet portlet = _getPortletsPool().get(portletId);

				portlets.add(portlet);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		_clearCaches();

		return portlets;
	}

	public Portlet updatePortlet(
			long companyId, String portletId, String roles, boolean active)
		throws PortalException, SystemException {

		portletId = PortalUtil.getJsSafePortletId(portletId);

		Portlet portlet = portletPersistence.fetchByC_P(companyId, portletId);

		if (portlet == null) {
			long id = counterLocalService.increment();

			portlet = portletPersistence.create(id);

			portlet.setCompanyId(companyId);
			portlet.setPortletId(portletId);
		}

		portlet.setRoles(roles);
		portlet.setActive(active);

		portletPersistence.update(portlet, false);

		portlet = getPortletById(companyId, portletId);

		portlet.setRoles(roles);
		portlet.setActive(active);

		return portlet;
	}

	private void _clearCaches() {

		// Refresh security path to portlet id mapping for all portlets

		_portletIdsByStrutsPath.clear();

		// Refresh company portlets

		_companyPortletsPool.clear();
	}

	private List<FriendlyURLMapper> _getFriendlyURLMappers() {
		List<FriendlyURLMapper> friendlyURLMappers =
			new ArrayList<FriendlyURLMapper>(
							_friendlyURLMapperPortlets.size());

		Iterator<Map.Entry<String, Portlet>> itr =
			_friendlyURLMapperPortlets.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Portlet> entry = itr.next();

			Portlet portlet = entry.getValue();

			FriendlyURLMapper friendlyURLMapper =
				portlet.getFriendlyURLMapperInstance();

			if (friendlyURLMapper != null) {
				friendlyURLMappers.add(friendlyURLMapper);
			}
		}

		return friendlyURLMappers;
	}

	private PortletApp _getPortletApp(String servletContextName) {
		PortletApp portletApp = _portletAppsPool.get(servletContextName);

		if (portletApp == null) {
			portletApp = new PortletAppImpl(servletContextName);

			_portletAppsPool.put(servletContextName, portletApp);
		}

		return portletApp;
	}

	private String _getPortletId(String securityPath) throws SystemException {
		if (_portletIdsByStrutsPath.size() == 0) {
			Iterator<Portlet> itr = _getPortletsPool().values().iterator();

			while (itr.hasNext()) {
				Portlet portlet = itr.next();

				_portletIdsByStrutsPath.put(
					portlet.getStrutsPath(), portlet.getPortletId());
			}
		}

		String portletId = _portletIdsByStrutsPath.get(securityPath);

		if (Validator.isNull(portletId)) {
			_log.error(
				"Struts path " + securityPath + " is not mapped to a portlet " +
					"in liferay-portlet.xml");
		}

		return portletId;
	}

	private List<Portlet> _getPortletsByPortletName(
		String portletName, String servletContextName,
		Map<String, Portlet> portletsPool) {

		List<Portlet> portlets = null;

		int pos = portletName.indexOf(StringPool.STAR);

		if (pos == -1) {
			portlets = new ArrayList<Portlet>();

			String portletId = portletName;

			if (Validator.isNotNull(servletContextName)) {
				portletId =
					portletId + PortletConstants.WAR_SEPARATOR +
						servletContextName;
			}

			portletId = PortalUtil.getJsSafePortletId(portletId);

			Portlet portlet = portletsPool.get(portletId);

			if (portlet != null) {
				portlets.add(portlet);
			}

			return portlets;
		}

		String portletNamePrefix = portletName.substring(0, pos);

		portlets = _getPortletsByServletContextName(
			servletContextName, portletsPool);

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			if (!portlet.getPortletId().startsWith(portletNamePrefix)) {
				itr.remove();
			}
		}

		return portlets;
	}

	private List<Portlet> _getPortletsByServletContextName(
		String servletContextName, Map<String, Portlet> portletsPool) {

		List<Portlet> portlets = new ArrayList<Portlet>();

		Iterator<Map.Entry<String, Portlet>> itr =
			portletsPool.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Portlet> entry = itr.next();

			String portletId = entry.getKey();
			Portlet portlet = entry.getValue();

			if (Validator.isNotNull(servletContextName)) {
				if (portletId.endsWith(
						PortletConstants.WAR_SEPARATOR + servletContextName)) {

					portlets.add(portlet);
				}
			}
			else {
				if (portletId.indexOf(PortletConstants.WAR_SEPARATOR) == -1) {
					portlets.add(portlet);
				}
			}
		}

		return portlets;
	}

	private Map<String, Portlet> _getPortletsPool() {
		return _portletsPool;
	}

	private Map<String, Portlet> _getPortletsPool(long companyId)
		throws SystemException {

		Map<String, Portlet> portletsPool = _companyPortletsPool.get(companyId);

		if (portletsPool == null) {
			portletsPool = new ConcurrentHashMap<String, Portlet>();

			Map<String, Portlet> parentPortletsPool = _getPortletsPool();

			if (parentPortletsPool == null) {

				// The Upgrade scripts sometimes try to access portlet
				// preferences before the portal's been initialized. Return an
				// empty pool.

				return portletsPool;
			}

			Iterator<Portlet> itr = parentPortletsPool.values().iterator();

			while (itr.hasNext()) {
				Portlet portlet = itr.next();

				portlet = (Portlet)portlet.clone();

				portlet.setCompanyId(companyId);

				portletsPool.put(portlet.getPortletId(), portlet);
			}

			itr = portletPersistence.findByCompanyId(companyId).iterator();

			while (itr.hasNext()) {
				Portlet portlet = itr.next();

				Portlet portletModel = portletsPool.get(portlet.getPortletId());

				// Portlet may be null if it exists in the database but its
				// portlet WAR is not yet loaded

				if (portletModel != null) {
					portletModel.setPluginPackage(portlet.getPluginPackage());
					portletModel.setDefaultPluginSetting(
						portlet.getDefaultPluginSetting());
					portletModel.setRoles(portlet.getRoles());
					portletModel.setActive(portlet.getActive());
				}
			}

			_companyPortletsPool.put(companyId, portletsPool);
		}

		return portletsPool;
	}

	private void _readLiferayDisplay(
		String servletContextName, Element el, PortletCategory portletCategory,
		Set<String> portletIds) {

		Iterator<Element> itr1 = el.elements("category").iterator();

		while (itr1.hasNext()) {
			Element category = itr1.next();

			String name = category.attributeValue("name");

			PortletCategory curPortletCategory = new PortletCategory(name);

			portletCategory.addCategory(curPortletCategory);

			Set<String> curPortletIds = curPortletCategory.getPortletIds();

			Iterator<Element> itr2 = category.elements("portlet").iterator();

			while (itr2.hasNext()) {
				Element portlet = itr2.next();

				String portletId = portlet.attributeValue("id");

				if (Validator.isNotNull(servletContextName)) {
					portletId =
						portletId + PortletConstants.WAR_SEPARATOR +
							servletContextName;
				}

				portletId = PortalUtil.getJsSafePortletId(portletId);

				portletIds.add(portletId);
				curPortletIds.add(portletId);
			}

			_readLiferayDisplay(
				servletContextName, category, curPortletCategory, portletIds);
		}
	}

	private PortletCategory _readLiferayDisplayXML(String xml)
		throws DocumentException, IOException {

		return _readLiferayDisplayXML(null, xml);
	}

	private PortletCategory _readLiferayDisplayXML(
			String servletContextName, String xml)
		throws DocumentException, IOException {

		PortletCategory portletCategory = new PortletCategory();

		if (xml == null) {
			xml = ContentUtil.get(
				"com/liferay/portal/deploy/dependencies/liferay-display.xml");
		}

		Document doc = DocumentUtil.readDocumentFromXML(xml, true);

		Element root = doc.getRootElement();

		Set<String> portletIds = new HashSet<String>();

		_readLiferayDisplay(
			servletContextName, root, portletCategory, portletIds);

		// Portlets that do not belong to any categories should default to the
		// Undefined category

		Set<String> undefinedPortletIds = new HashSet<String>();

		Iterator<Portlet> itr = _getPortletsPool().values().iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			String portletId = portlet.getPortletId();

			PortletApp portletApp = portlet.getPortletApp();

			if ((servletContextName != null) && (portletApp.isWARFile()) &&
				(portletId.endsWith(
					PortletConstants.WAR_SEPARATOR +
						PortalUtil.getJsSafePortletId(servletContextName)) &&
				(!portletIds.contains(portletId)))) {

				undefinedPortletIds.add(portletId);
			}
			else if ((servletContextName == null) &&
					 (!portletApp.isWARFile()) &&
					 (portletId.indexOf(
						PortletConstants.WAR_SEPARATOR) == -1) &&
					 (!portletIds.contains(portletId))) {

				undefinedPortletIds.add(portletId);
			}
		}

		if (undefinedPortletIds.size() > 0) {
			PortletCategory undefinedCategory = new PortletCategory(
				"category.undefined");

			portletCategory.addCategory(undefinedCategory);

			undefinedCategory.getPortletIds().addAll(undefinedPortletIds);
		}

		return portletCategory;
	}

	private Set<String> _readLiferayPortletXML(
			String xml, Map<String, Portlet> portletsPool)
		throws DocumentException, IOException {

		return _readLiferayPortletXML(StringPool.BLANK, xml, portletsPool);
	}

	private Set<String> _readLiferayPortletXML(
			String servletContextName, String xml,
			Map<String, Portlet> portletsPool)
		throws DocumentException, IOException {

		Set<String> liferayPortletIds = new HashSet<String>();

		if (xml == null) {
			return liferayPortletIds;
		}

		Document doc = DocumentUtil.readDocumentFromXML(xml, true);

		Element root = doc.getRootElement();

		PortletApp portletApp = _getPortletApp(servletContextName);

		Map<String, String> roleMappers = new HashMap<String, String>();

		Iterator<Element> itr1 = root.elements("role-mapper").iterator();

		while (itr1.hasNext()) {
			Element roleMapper = itr1.next();

			String roleName = roleMapper.elementText("role-name");
			String roleLink = roleMapper.elementText("role-link");

			roleMappers.put(roleName, roleLink);
		}

		Map<String, String> customUserAttributes =
			portletApp.getCustomUserAttributes();

		itr1 = root.elements("custom-user-attribute").iterator();

		while (itr1.hasNext()) {
			Element customUserAttribute = itr1.next();

			String customClass = customUserAttribute.elementText(
				"custom-class");

			Iterator<Element> itr2 = customUserAttribute.elements(
				"name").iterator();

			while (itr2.hasNext()) {
				Element nameEl = itr2.next();

				String name = nameEl.getText();

				customUserAttributes.put(name, customClass);
			}
		}

		itr1 = root.elements("portlet").iterator();

		while (itr1.hasNext()) {
			Element portlet = itr1.next();

			String portletId = portlet.elementText("portlet-name");

			if (Validator.isNotNull(servletContextName)) {
				portletId =
					portletId + PortletConstants.WAR_SEPARATOR +
						servletContextName;
			}

			portletId = PortalUtil.getJsSafePortletId(portletId);

			if (_log.isDebugEnabled()) {
				_log.debug("Reading portlet extension " + portletId);
			}

			liferayPortletIds.add(portletId);

			Portlet portletModel = portletsPool.get(portletId);

			if (portletModel != null) {
				portletModel.setIcon(GetterUtil.getString(
					portlet.elementText("icon"), portletModel.getIcon()));
				portletModel.setVirtualPath(GetterUtil.getString(
					portlet.elementText("virtual-path"),
					portletModel.getVirtualPath()));
				portletModel.setStrutsPath(GetterUtil.getString(
					portlet.elementText("struts-path"),
					portletModel.getStrutsPath()));

				if (Validator.isNotNull(
						portlet.elementText("configuration-path"))) {

					_log.error(
						"The configuration-path element is no longer " +
							"supported. Use configuration-action-class " +
								"instead.");
				}

				portletModel.setConfigurationActionClass(GetterUtil.getString(
					portlet.elementText("configuration-action-class"),
					portletModel.getConfigurationActionClass()));
				portletModel.setIndexerClass(GetterUtil.getString(
					portlet.elementText("indexer-class"),
					portletModel.getIndexerClass()));
				portletModel.setOpenSearchClass(GetterUtil.getString(
					portlet.elementText("open-search-class"),
					portletModel.getOpenSearchClass()));
				portletModel.setSchedulerClass(GetterUtil.getString(
					portlet.elementText("scheduler-class"),
					portletModel.getSchedulerClass()));
				portletModel.setPortletURLClass(GetterUtil.getString(
					portlet.elementText("portlet-url-class"),
					portletModel.getPortletURLClass()));

				portletModel.setFriendlyURLMapperClass(GetterUtil.getString(
					portlet.elementText("friendly-url-mapper-class"),
					portletModel.getFriendlyURLMapperClass()));

				if (Validator.isNull(
						portletModel.getFriendlyURLMapperClass())) {

					_friendlyURLMapperPortlets.remove(portletId);
				}
				else {
					_friendlyURLMapperPortlets.put(portletId, portletModel);
				}

				portletModel.setURLEncoderClass(GetterUtil.getString(
					portlet.elementText("url-encoder-class"),
					portletModel.getURLEncoderClass()));
				portletModel.setPortletDataHandlerClass(GetterUtil.getString(
					portlet.elementText("portlet-data-handler-class"),
					portletModel.getPortletDataHandlerClass()));
				portletModel.setPortletLayoutListenerClass(GetterUtil.getString(
					portlet.elementText("portlet-layout-listener-class"),
					portletModel.getPortletLayoutListenerClass()));
				portletModel.setPopMessageListenerClass(GetterUtil.getString(
					portlet.elementText("pop-message-listener-class"),
					portletModel.getPopMessageListenerClass()));
				portletModel.setSocialActivityInterpreterClass(
					GetterUtil.getString(
						portlet.elementText(
							"social-activity-interpreter-class"),
							portletModel.getSocialActivityInterpreterClass()));
				portletModel.setSocialRequestInterpreterClass(
					GetterUtil.getString(
						portlet.elementText(
							"social-request-interpreter-class"),
							portletModel.getSocialRequestInterpreterClass()));
				portletModel.setPreferencesCompanyWide(GetterUtil.getBoolean(
					portlet.elementText("preferences-company-wide"),
					portletModel.isPreferencesCompanyWide()));
				portletModel.setPreferencesUniquePerLayout(
					GetterUtil.getBoolean(
						portlet.elementText("preferences-unique-per-layout"),
						portletModel.isPreferencesUniquePerLayout()));
				portletModel.setPreferencesOwnedByGroup(GetterUtil.getBoolean(
					portlet.elementText("preferences-owned-by-group"),
					portletModel.isPreferencesOwnedByGroup()));
				portletModel.setUseDefaultTemplate(GetterUtil.getBoolean(
					portlet.elementText("use-default-template"),
					portletModel.isUseDefaultTemplate()));
				portletModel.setShowPortletAccessDenied(GetterUtil.getBoolean(
					portlet.elementText("show-portlet-access-denied"),
					portletModel.isShowPortletAccessDenied()));
				portletModel.setShowPortletInactive(GetterUtil.getBoolean(
					portlet.elementText("show-portlet-inactive"),
					portletModel.isShowPortletInactive()));
				portletModel.setActionURLRedirect(GetterUtil.getBoolean(
					portlet.elementText("action-url-redirect"),
					portletModel.isActionURLRedirect()));
				portletModel.setRestoreCurrentView(GetterUtil.getBoolean(
					portlet.elementText("restore-current-view"),
					portletModel.isRestoreCurrentView()));
				portletModel.setMaximizeEdit(GetterUtil.getBoolean(
					portlet.elementText("maximize-edit"),
					portletModel.isMaximizeEdit()));
				portletModel.setMaximizeHelp(GetterUtil.getBoolean(
					portlet.elementText("maximize-help"),
					portletModel.isMaximizeHelp()));
				portletModel.setPopUpPrint(GetterUtil.getBoolean(
					portlet.elementText("pop-up-print"),
					portletModel.isPopUpPrint()));
				portletModel.setLayoutCacheable(GetterUtil.getBoolean(
					portlet.elementText("layout-cacheable"),
					portletModel.isLayoutCacheable()));
				portletModel.setInstanceable(GetterUtil.getBoolean(
					portlet.elementText("instanceable"),
					portletModel.isInstanceable()));
				portletModel.setUserPrincipalStrategy(GetterUtil.getString(
					portlet.elementText("user-principal-strategy"),
					portletModel.getUserPrincipalStrategy()));
				portletModel.setPrivateRequestAttributes(GetterUtil.getBoolean(
					portlet.elementText("private-request-attributes"),
					portletModel.isPrivateRequestAttributes()));
				portletModel.setPrivateSessionAttributes(GetterUtil.getBoolean(
					portlet.elementText("private-session-attributes"),
					portletModel.isPrivateSessionAttributes()));
				portletModel.setRenderWeight(GetterUtil.getInteger(
					portlet.elementText("render-weight"),
					portletModel.getRenderWeight()));
				portletModel.setAjaxable(GetterUtil.getBoolean(
					portlet.elementText("ajaxable"),
					portletModel.isAjaxable()));

				List<String> headerPortalCssList =
					portletModel.getHeaderPortalCss();

				Iterator<Element> itr2 = portlet.elements(
					"header-portal-css").iterator();

				while (itr2.hasNext()) {
					Element headerPortalCssEl = itr2.next();

					headerPortalCssList.add(headerPortalCssEl.getText());
				}

				List<String> headerPortletCssList =
					portletModel.getHeaderPortletCss();

				List<Element> list = new ArrayList<Element>();

				list.addAll(portlet.elements("header-css"));
				list.addAll(portlet.elements("header-portlet-css"));

				itr2 = list.iterator();

				while (itr2.hasNext()) {
					Element headerPortletCssEl = itr2.next();

					headerPortletCssList.add(headerPortletCssEl.getText());
				}

				List<String> headerPortalJavaScriptList =
					portletModel.getHeaderPortalJavaScript();

				itr2 = portlet.elements("header-portal-javascript").iterator();

				while (itr2.hasNext()) {
					Element headerPortalJavaScriptEl = itr2.next();

					headerPortalJavaScriptList.add(
						headerPortalJavaScriptEl.getText());
				}

				List<String> headerPortletJavaScriptList =
					portletModel.getHeaderPortletJavaScript();

				list.clear();

				list.addAll(portlet.elements("header-javascript"));
				list.addAll(portlet.elements("header-portlet-javascript"));

				itr2 = list.iterator();

				while (itr2.hasNext()) {
					Element headerPortletJavaScriptEl = itr2.next();

					headerPortletJavaScriptList.add(
						headerPortletJavaScriptEl.getText());
				}

				List<String> footerPortalCssList =
					portletModel.getFooterPortalCss();

				itr2 = portlet.elements("footer-portal-css").iterator();

				while (itr2.hasNext()) {
					Element footerPortalCssEl = itr2.next();

					footerPortalCssList.add(footerPortalCssEl.getText());
				}

				List<String> footerPortletCssList =
					portletModel.getFooterPortletCss();

				itr2 = portlet.elements("footer-portlet-css").iterator();

				while (itr2.hasNext()) {
					Element footerPortletCssEl = itr2.next();

					footerPortletCssList.add(footerPortletCssEl.getText());
				}

				List<String> footerPortalJavaScriptList =
					portletModel.getFooterPortalJavaScript();

				itr2 = portlet.elements("footer-portal-javascript").iterator();

				while (itr2.hasNext()) {
					Element footerPortalJavaScriptEl = itr2.next();

					footerPortalJavaScriptList.add(
						footerPortalJavaScriptEl.getText());
				}

				List<String> footerPortletJavaScriptList =
					portletModel.getFooterPortletJavaScript();

				itr2 = portlet.elements("footer-portlet-javascript").iterator();

				while (itr2.hasNext()) {
					Element footerPortletJavaScriptEl = itr2.next();

					footerPortletJavaScriptList.add(
						footerPortletJavaScriptEl.getText());
				}

				portletModel.setCssClassWrapper(GetterUtil.getString(
					portlet.elementText("css-class-wrapper"),
					portletModel.getCssClassWrapper()));
				portletModel.setAddDefaultResource(GetterUtil.getBoolean(
					portlet.elementText("add-default-resource"),
					portletModel.isAddDefaultResource()));
				portletModel.setSystem(GetterUtil.getBoolean(
					portlet.elementText("system"),
					portletModel.isSystem()));
				portletModel.setActive(GetterUtil.getBoolean(
					portlet.elementText("active"),
					portletModel.isActive()));
				portletModel.setInclude(GetterUtil.getBoolean(
					portlet.elementText("include"),
					portletModel.isInclude()));

				if (!portletModel.isAjaxable() &&
					(portletModel.getRenderWeight() < 1)) {

					portletModel.setRenderWeight(1);
				}

				portletModel.getRoleMappers().putAll(roleMappers);
				portletModel.linkRoles();
			}
		}

		return liferayPortletIds;
	}

	private Set<String> _readPortletXML(
			String xml, Map<String, Portlet> portletsPool,
			List<String> servletURLPatterns, PluginPackage pluginPackage)
		throws DocumentException, IOException {

		return _readPortletXML(
			StringPool.BLANK, xml, portletsPool, servletURLPatterns,
			pluginPackage);
	}

	private Set<String> _readPortletXML(
			String servletContextName, String xml,
			Map<String, Portlet> portletsPool, List<String> servletURLPatterns,
			PluginPackage pluginPackage)
		throws DocumentException, IOException {

		Set<String> portletIds = new HashSet<String>();

		if (xml == null) {
			return portletIds;
		}

		Document doc = DocumentUtil.readDocumentFromXML(xml);

		Element root = doc.getRootElement();

		PortletApp portletApp = _getPortletApp(servletContextName);

		portletApp.getServletURLPatterns().addAll(servletURLPatterns);

		Set<String> userAttributes = portletApp.getUserAttributes();

		Iterator<Element> itr1 = root.elements("user-attribute").iterator();

		while (itr1.hasNext()) {
			Element userAttribute = itr1.next();

			String name = userAttribute.elementText("name");

			userAttributes.add(name);
		}

		String defaultNamespace = root.elementText("default-namespace");

		if (Validator.isNotNull(defaultNamespace)) {
			portletApp.setDefaultNamespace(defaultNamespace);
		}

		itr1 = root.elements("event-definition").iterator();

		while (itr1.hasNext()) {
			Element eventDefinitionEl = itr1.next();

			Element qNameEl = eventDefinitionEl.element("qname");
			Element nameEl = eventDefinitionEl.element("name");
			String valueType = eventDefinitionEl.elementText("value-type");

			QName qName = QNameUtil.getQName(
				qNameEl, nameEl, portletApp.getDefaultNamespace());

			EventDefinition eventDefinition = new EventDefinitionImpl(
				qName, valueType, portletApp);

			portletApp.addEventDefinition(eventDefinition);
		}

		itr1 = root.elements("public-render-parameter").iterator();

		while (itr1.hasNext()) {
			Element publicRenderParameterEl = itr1.next();

			String identifier = publicRenderParameterEl.elementText(
				"identifier");
			Element qNameEl = publicRenderParameterEl.element("qname");
			Element nameEl = publicRenderParameterEl.element("name");

			QName qName = QNameUtil.getQName(
				qNameEl, nameEl, portletApp.getDefaultNamespace());

			PublicRenderParameter publicRenderParameter =
				new PublicRenderParameterImpl(identifier, qName, portletApp);

			portletApp.addPublicRenderParameter(publicRenderParameter);
		}

		itr1 = root.elements("container-runtime-option").iterator();

		while (itr1.hasNext()) {
			Element containerRuntimeOption = itr1.next();

			String name = containerRuntimeOption.elementText("name");

			List<String> values = new ArrayList<String>();

			for (Element value :
					(List<Element>)containerRuntimeOption.elements("value")) {

				values.add(value.getTextTrim());
			}

			portletApp.getContainerRuntimeOptions().put(
				name, values.toArray(new String[values.size()]));
		}

		itr1 = root.elements("portlet").iterator();

		while (itr1.hasNext()) {
			Element portlet = itr1.next();

			String portletId = portlet.elementText("portlet-name");

			if (Validator.isNotNull(servletContextName)) {
				portletId =
					portletId + PortletConstants.WAR_SEPARATOR +
						servletContextName;
			}

			portletId = PortalUtil.getJsSafePortletId(portletId);

			if (_log.isDebugEnabled()) {
				_log.debug("Reading portlet " + portletId);
			}

			portletIds.add(portletId);

			Portlet portletModel = portletsPool.get(portletId);

			if (portletModel == null) {
				portletModel = new PortletImpl(
					CompanyConstants.SYSTEM, portletId);

				portletsPool.put(portletId, portletModel);
			}

			portletModel.setPluginPackage(pluginPackage);
			portletModel.setPortletApp(portletApp);

			portletModel.setDisplayName(GetterUtil.getString(
				portlet.elementText("display-name"),
				portletModel.getDisplayName()));
			portletModel.setPortletClass(GetterUtil.getString(
				portlet.elementText("portlet-class")));

			Iterator<Element> itr2 = portlet.elements("init-param").iterator();

			while (itr2.hasNext()) {
				Element initParam = itr2.next();

				portletModel.getInitParams().put(
					initParam.elementText("name"),
					initParam.elementText("value"));
			}

			Element expirationCache = portlet.element("expiration-cache");

			if (expirationCache != null) {
				portletModel.setExpCache(new Integer(GetterUtil.getInteger(
					expirationCache.getText())));
			}

			itr2 = portlet.elements("supports").iterator();

			while (itr2.hasNext()) {
				Element supports = itr2.next();

				String mimeType = supports.elementText("mime-type");

				Set<String> mimeTypeModes =
					portletModel.getPortletModes().get(mimeType);

				if (mimeTypeModes == null) {
					mimeTypeModes = new HashSet<String>();

					portletModel.getPortletModes().put(mimeType, mimeTypeModes);
				}

				mimeTypeModes.add(PortletMode.VIEW.toString().toLowerCase());

				Iterator<Element> itr3 = supports.elements(
					"portlet-mode").iterator();

				while (itr3.hasNext()) {
					Element portletMode = itr3.next();

					mimeTypeModes.add(portletMode.getTextTrim().toLowerCase());
				}
			}

			Set<String> supportedLocales = portletModel.getSupportedLocales();

			//supportedLocales.add(
			//	LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

			itr2 = portlet.elements("supported-locale").iterator();

			while (itr2.hasNext()) {
				Element supportedLocaleEl = itr2.next();

				String supportedLocale = supportedLocaleEl.getText();

				supportedLocales.add(supportedLocale);
			}

			portletModel.setResourceBundle(
				portlet.elementText("resource-bundle"));

			Element portletInfo = portlet.element("portlet-info");

			String portletInfoTitle = null;
			String portletInfoShortTitle = null;
			String portletInfoKeyWords = null;

			if (portletInfo != null) {
				portletInfoTitle = portletInfo.elementText("title");
				portletInfoShortTitle = portletInfo.elementText("short-title");
				portletInfoKeyWords = portletInfo.elementText("keywords");
			}

			portletModel.setPortletInfo(new PortletInfo(
				portletInfoTitle, portletInfoShortTitle, portletInfoKeyWords));

			Element portletPreferences = portlet.element("portlet-preferences");

			String defaultPreferences = null;
			String prefsValidator = null;

			if (portletPreferences != null) {
				Element prefsValidatorEl =
					portletPreferences.element("preferences-validator");

				if (prefsValidatorEl != null) {
					prefsValidator = prefsValidatorEl.getText();

					portletPreferences.remove(prefsValidatorEl);
				}

				StringWriter sw = new StringWriter();

				XMLWriter writer = new XMLWriter(
					sw, OutputFormat.createCompactFormat());

				writer.write(portletPreferences);

				defaultPreferences = sw.toString();
			}

			portletModel.setDefaultPreferences(defaultPreferences);
			portletModel.setPreferencesValidator(prefsValidator);

			if (!portletApp.isWARFile() &&
				Validator.isNotNull(prefsValidator) &&
				PropsValues.PREFERENCE_VALIDATE_ON_STARTUP) {

				try {
					PreferencesValidator prefsValidatorObj =
						PortalUtil.getPreferencesValidator(portletModel);

					prefsValidatorObj.validate(
						PortletPreferencesSerializer.fromDefaultXML(
							defaultPreferences));
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Portlet with the name " + portletId +
								" does not have valid default preferences");
					}
				}
			}

			Set<String> unlikedRoles = portletModel.getUnlinkedRoles();

			itr2 = portlet.elements("security-role-ref").iterator();

			while (itr2.hasNext()) {
				Element role = itr2.next();

				unlikedRoles.add(role.elementText("role-name"));
			}

			itr2 = portlet.elements("supported-processing-event").iterator();

			while (itr2.hasNext()) {
				Element supportedProcessingEvent = itr2.next();

				Element qNameEl = supportedProcessingEvent.element("qname");
				Element nameEl = supportedProcessingEvent.element("name");

				QName qName = QNameUtil.getQName(
					qNameEl, nameEl, portletApp.getDefaultNamespace());

				portletModel.addProcessingEvent(qName);
			}

			itr2 = portlet.elements("supported-publishing-event").iterator();

			while (itr2.hasNext()) {
				Element supportedPublishingEvent = itr2.next();

				Element qNameEl = supportedPublishingEvent.element("qname");
				Element nameEl = supportedPublishingEvent.element("name");

				QName qName = QNameUtil.getQName(
					qNameEl, nameEl, portletApp.getDefaultNamespace());

				portletModel.addPublishingEvent(qName);
			}

			itr2 = portlet.elements(
				"supported-public-render-parameter").iterator();

			while (itr2.hasNext()) {
				Element supportedPublicRenderParameter = itr2.next();

				String identifier =
					supportedPublicRenderParameter.getTextTrim();

				PublicRenderParameter publicRenderParameter =
					portletApp.getPublicRenderParameter(identifier);

				if (publicRenderParameter == null) {
					_log.error(
						"Supported public render parameter references " +
							"unnknown identifier " + identifier);

					continue;
				}

				portletModel.addPublicRenderParameter(publicRenderParameter);
			}
		}

		itr1 = root.elements("filter").iterator();

		while (itr1.hasNext()) {
			Element filter = itr1.next();

			String filterName = filter.elementText("filter-name");
			String filterClass = filter.elementText("filter-class");

			Set<String> lifecycles = new LinkedHashSet<String>();

			Iterator<Element> itr2 = filter.elements("lifecycle").iterator();

			while (itr2.hasNext()) {
				Element lifecycle = itr2.next();

				lifecycles.add(lifecycle.getText());
			}

			Map<String, String> initParams = new HashMap<String, String>();

			itr2 = filter.elements("init-param").iterator();

			while (itr2.hasNext()) {
				Element initParam = itr2.next();

				initParams.put(
					initParam.elementText("name"),
					initParam.elementText("value"));
			}

			PortletFilter portletFilter = new PortletFilterImpl(
				filterName, filterClass, lifecycles, initParams, portletApp);

			portletApp.addPortletFilter(portletFilter);
		}

		itr1 = root.elements("filter-mapping").iterator();

		while (itr1.hasNext()) {
			Element filterMapping = itr1.next();

			String filterName = filterMapping.elementText("filter-name");

			Iterator<Element> itr2 = filterMapping.elements(
				"portlet-name").iterator();

			while (itr2.hasNext()) {
				Element portletNameEl = itr2.next();

				String portletName = portletNameEl.getTextTrim();

				PortletFilter portletFilter = portletApp.getPortletFilter(
					filterName);

				if (portletFilter == null) {
					_log.error(
						"Filter mapping references unnknown filter name " +
							filterName);

					continue;
				}

				List<Portlet> portletModels = _getPortletsByPortletName(
					portletName, servletContextName, portletsPool);

				if (portletModels.size() == 0) {
					_log.error(
						"Filter mapping with filter name " + filterName +
							" references unnknown portlet name " + portletName);
				}

				for (Portlet portletModel : portletModels) {
					portletModel.getPortletFilters().put(
						filterName, portletFilter);
				}
			}
		}

		itr1 = root.elements("listener").iterator();

		while (itr1.hasNext()) {
			Element listener = itr1.next();

			String listenerClass = listener.elementText("listener-class");

			PortletURLListener portletURLListener = new PortletURLListenerImpl(
				listenerClass, portletApp);

			portletApp.addPortletURLListener(portletURLListener);
		}

		return portletIds;
	}

	private List<String> _readWebXML(String xml)
		throws DocumentException, IOException {

		List<String> servletURLPatterns = new ArrayList<String>();

		if (xml == null) {
			return servletURLPatterns;
		}

		Document doc = DocumentUtil.readDocumentFromXML(xml);

		Element root = doc.getRootElement();

		Iterator<Element> itr = root.elements("servlet-mapping").iterator();

		while (itr.hasNext()) {
			Element servletMapping = itr.next();

			String urlPattern = servletMapping.elementText("url-pattern");

			servletURLPatterns.add(urlPattern);
		}

		return servletURLPatterns;

	}

	private static Log _log = LogFactory.getLog(PortletLocalServiceImpl.class);

	private static Map<String, PortletApp> _portletAppsPool =
		new ConcurrentHashMap<String, PortletApp>();
	private static Map<String, Portlet> _portletsPool =
		new ConcurrentHashMap<String, Portlet>();
	private static Map<Long, Map<String, Portlet>> _companyPortletsPool =
		new ConcurrentHashMap<Long, Map<String, Portlet>>();
	private static Map<String, String> _portletIdsByStrutsPath =
		new ConcurrentHashMap<String, String>();
	private static Map<String, Portlet> _friendlyURLMapperPortlets =
		new ConcurrentHashMap<String, Portlet>();

}