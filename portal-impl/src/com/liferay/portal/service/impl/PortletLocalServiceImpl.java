/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.SpriteProcessorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.Route;
import com.liferay.portal.kernel.portlet.Router;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
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
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.impl.EventDefinitionImpl;
import com.liferay.portal.model.impl.PortletAppImpl;
import com.liferay.portal.model.impl.PortletFilterImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.model.impl.PortletURLListenerImpl;
import com.liferay.portal.model.impl.PublicRenderParameterImpl;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.base.PortletLocalServiceBaseImpl;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletInstanceFactoryUtil;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.portlet.PortletQNameUtil;
import com.liferay.portlet.expando.model.CustomAttributesDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletMode;
import javax.portlet.PreferencesValidator;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;

/**
 * <a href="PortletLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 * @author Wesley Gong
 */
public class PortletLocalServiceImpl extends PortletLocalServiceBaseImpl {

	public void checkPortlet(Portlet portlet)
		throws PortalException, SystemException {

		if (portlet.isSystem()) {
			return;
		}

		String[] roleNames = portlet.getRolesArray();

		if (roleNames.length == 0) {
			return;
		}

		long companyId = portlet.getCompanyId();
		String name = portlet.getPortletId();
		int scope = ResourceConstants.SCOPE_COMPANY;
		String primKey = String.valueOf(companyId);
		String actionId = ActionKeys.ADD_TO_PAGE;

		List<String> actionIds = ResourceActionsUtil.getPortletResourceActions(
			name);

		if (actionIds.contains(actionId)) {
			for (String roleName : roleNames) {
				Role role = roleLocalService.getRole(companyId, roleName);

				if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
					resourcePermissionLocalService.addResourcePermission(
						companyId, name, scope, primKey, role.getRoleId(),
						actionId);
				}
				else {
					permissionLocalService.setRolePermission(
						role.getRoleId(), companyId, name, scope, primKey,
						actionId);
				}
			}
		}

		updatePortlet(
			companyId, portlet.getPortletId(), StringPool.BLANK,
			portlet.isActive());
	}

	public void checkPortlets(long companyId)
		throws PortalException, SystemException {

		List<Portlet> portlets = getPortlets(companyId);

		for (Portlet portlet : portlets) {
			checkPortlet(portlet);
		}
	}

	public void clearCache() {

		// Refresh security path to portlet id mapping for all portlets

		_portletIdsByStrutsPath.clear();

		// Refresh company portlets

		_companyPortletsPool.removeAll();
	}

	public Portlet clonePortlet(long companyId, String portletId)
		throws SystemException {

		Portlet portlet = getPortletById(companyId, portletId);

		return (Portlet)portlet.clone();
	}

	public Portlet deployRemotePortlet(Portlet portlet, String categoryName)
		throws SystemException {

		Map<String, Portlet> portletsPool = _getPortletsPool();

		portletsPool.put(portlet.getPortletId(), portlet);

		clearCache();

		PortletCategory newPortletCategory = new PortletCategory();

		PortletCategory oldPortletCategory = new PortletCategory(categoryName);

		newPortletCategory.addCategory(oldPortletCategory);

		oldPortletCategory.getPortletIds().add(portlet.getPortletId());

		long companyId = portlet.getCompanyId();

		PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
			String.valueOf(companyId), WebKeys.PORTLET_CATEGORY);

		if (portletCategory != null) {
			portletCategory.merge(newPortletCategory);
		}
		else {
			_log.error(
				"Unable to register remote portlet for company " + companyId +
					" because it does not exist");
		}

		List<String> portletActions =
			ResourceActionsUtil.getPortletResourceActions(
				portlet.getPortletId());

		resourceActionLocalService.checkResourceActions(
			portlet.getPortletId(), portletActions);

		return portlet;
	}

	public void destroyPortlet(Portlet portlet) {
		Map<String, Portlet> portletsPool = _getPortletsPool();

		portletsPool.remove(portlet.getRootPortletId());

		PortletApp portletApp = portlet.getPortletApp();

		if (portletApp != null) {
			_portletAppsPool.remove(portletApp.getServletContextName());
		}

		clearCache();
	}

	public void destroyRemotePortlet(Portlet portlet) {
		Map<String, Portlet> portletsPool = _getPortletsPool();

		portletsPool.remove(portlet.getRootPortletId());

		PortletApp portletApp = portlet.getPortletApp();

		_portletAppsPool.remove(portletApp.getServletContextName());

		clearCache();
	}

	public List<CustomAttributesDisplay> getCustomAttributesDisplays() {
		List<CustomAttributesDisplay> customAttributesDisplays =
			new ArrayList<CustomAttributesDisplay>(
				_customAttributesDisplayPortlets.size());

		for (Map.Entry<String, Portlet> entry :
				_customAttributesDisplayPortlets.entrySet()) {

			Portlet portlet = entry.getValue();

			List<CustomAttributesDisplay> portletCustomAttributesDisplays =
				portlet.getCustomAttributesDisplayInstances();

			if ((portletCustomAttributesDisplays != null) &&
				(!portletCustomAttributesDisplays.isEmpty())) {

				customAttributesDisplays.addAll(
					portletCustomAttributesDisplays);
			}
		}

		return customAttributesDisplays;
	}

	public PortletCategory getEARDisplay(String xml) throws SystemException {
		try {
			return _readLiferayDisplayXML(xml);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public List<Portlet> getFriendlyURLMapperPortlets() {
		List<Portlet> portlets = new ArrayList<Portlet>(
			_friendlyURLMapperPortlets.size());

		for (Map.Entry<String, Portlet> entry :
				_friendlyURLMapperPortlets.entrySet()) {

			Portlet portlet = entry.getValue();

			FriendlyURLMapper friendlyURLMapper =
				portlet.getFriendlyURLMapperInstance();

			if (friendlyURLMapper != null) {
				portlets.add(portlet);
			}
		}

		return portlets;
	}

	public List<FriendlyURLMapper> getFriendlyURLMappers() {
		List<FriendlyURLMapper> friendlyURLMappers =
			new ArrayList<FriendlyURLMapper>(_friendlyURLMapperPortlets.size());

		for (Map.Entry<String, Portlet> entry :
				_friendlyURLMapperPortlets.entrySet()) {

			Portlet portlet = entry.getValue();

			FriendlyURLMapper friendlyURLMapper =
				portlet.getFriendlyURLMapperInstance();

			if (friendlyURLMapper != null) {
				friendlyURLMappers.add(friendlyURLMapper);
			}
		}

		return friendlyURLMappers;
	}

	public PortletApp getPortletApp(String servletContextName) {
		return _getPortletApp(servletContextName);
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

		if (portlet != null) {
			return portlet;
		}

		if (portletId.equals(PortletKeys.LIFERAY_PORTAL)) {
			return portlet;
		}
		
		if (_portletsPool.isEmpty()) {
			if (_log.isDebugEnabled()) {
				_log.debug("No portlets are installed");
			}
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Portlet not found for " + companyId + " " + portletId);
			}

			portlet = new PortletImpl(CompanyConstants.SYSTEM, portletId);

			portlet.setTimestamp(System.currentTimeMillis());

			PortletApp portletApp = _getPortletApp(StringPool.BLANK);

			portlet.setPortletApp(portletApp);

			portlet.setPortletName(portletId);
			portlet.setDisplayName(portletId);
			portlet.setPortletClass(MVCPortlet.class.getName());

			Map<String, String> initParams = portlet.getInitParams();

			initParams.put("view-jsp", "/html/portal/undeployed_portlet.jsp");

			Set<String> mimeTypePortletModes = new HashSet<String>();

			mimeTypePortletModes.add(PortletMode.VIEW.toString().toLowerCase());

			portlet.getPortletModes().put(
				ContentTypes.TEXT_HTML, mimeTypePortletModes);

			Set<String> mimeTypeWindowStates = new HashSet<String>();

			mimeTypeWindowStates.add(
				WindowState.NORMAL.toString().toLowerCase());

			portlet.getWindowStates().put(
				ContentTypes.TEXT_HTML, mimeTypeWindowStates);

			portlet.setPortletInfo(
				new PortletInfo(portletId, portletId, portletId, portletId));

			if (PortletConstants.getInstanceId(portletId) != null) {
				portlet.setInstanceable(true);
			}

			portlet.setActive(true);
			portlet.setUndeployedPortlet(true);
		}

		return portlet;
	}

	public Portlet getPortletById(String portletId) {
		Map<String, Portlet> portletsPool = _getPortletsPool();

		return portletsPool.get(portletId);
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

	public PortletCategory getWARDisplay(String servletContextName, String xml)
		throws SystemException {

		try {
			return _readLiferayDisplayXML(servletContextName, xml);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
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

	public void initEAR(
		ServletContext servletContext, String[] xmls,
		PluginPackage pluginPackage) {

		// Clear pools every time initEAR is called. See LEP-5452.

		_portletAppsPool.clear();
		_portletsPool.clear();
		_companyPortletsPool.removeAll();
		_portletIdsByStrutsPath.clear();
		_friendlyURLMapperPortlets.clear();

		Map<String, Portlet> portletsPool = _getPortletsPool();

		try {
			List<String> servletURLPatterns = _readWebXML(xmls[4]);

			Set<String> portletIds = _readPortletXML(
				servletContext, xmls[0], portletsPool, servletURLPatterns,
				pluginPackage);

			portletIds.addAll(
				_readPortletXML(
					servletContext, xmls[1], portletsPool, servletURLPatterns,
					pluginPackage));

			Set<String> liferayPortletIds =
				_readLiferayPortletXML(xmls[2], portletsPool);

			liferayPortletIds.addAll(
				_readLiferayPortletXML(xmls[3], portletsPool));

			// Check for missing entries in liferay-portlet.xml

			for (String portletId : portletIds) {
				if (_log.isWarnEnabled() &&
					!liferayPortletIds.contains(portletId)) {

					_log.warn(
						"Portlet with the name " + portletId +
							" is described in portlet.xml but does not " +
								"have a matching entry in liferay-portlet.xml");
				}
			}

			// Check for missing entries in portlet.xml

			for (String portletId : liferayPortletIds) {
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

			// Sprite images

			PortletApp portletApp = _getPortletApp(StringPool.BLANK);

			_setSpriteImages(servletContext, portletApp, "/html/icons/");
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public List<Portlet> initWAR(
		String servletContextName, ServletContext servletContext, String[] xmls,
		PluginPackage pluginPackage) {

		List<Portlet> portlets = new ArrayList<Portlet>();

		Map<String, Portlet> portletsPool = _getPortletsPool();

		try {
			List<String> servletURLPatterns = _readWebXML(xmls[3]);

			Set<String> portletIds = _readPortletXML(
				servletContextName, servletContext, xmls[0], portletsPool,
				servletURLPatterns, pluginPackage);

			portletIds.addAll(
				_readPortletXML(
					servletContextName, servletContext, xmls[1], portletsPool,
					servletURLPatterns, pluginPackage));

			Set<String> liferayPortletIds = _readLiferayPortletXML(
				servletContextName, xmls[2], portletsPool);

			// Check for missing entries in liferay-portlet.xml

			for (String portletId : portletIds) {
				if (_log.isWarnEnabled() &&
					!liferayPortletIds.contains(portletId)) {

					_log.warn(
						"Portlet with the name " + portletId +
							" is described in portlet.xml but does not " +
								"have a matching entry in liferay-portlet.xml");
				}
			}

			// Check for missing entries in portlet.xml

			for (String portletId : liferayPortletIds) {
				if (_log.isWarnEnabled() && !portletIds.contains(portletId)) {
					_log.warn(
						"Portlet with the name " + portletId +
							" is described in liferay-portlet.xml but does " +
								"not have a matching entry in portlet.xml");
				}
			}

			// Return the new portlets

			for (String portletId : portletIds) {
				Portlet portlet = _getPortletsPool().get(portletId);

				portlets.add(portlet);

				PortletInstanceFactoryUtil.clear(portlet);
			}

			// Sprite images

			PortletApp portletApp = _getPortletApp(servletContextName);

			_setSpriteImages(servletContext, portletApp, "/icons/");
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		clearCache();

		return portlets;
	}

	public Portlet updatePortlet(
			long companyId, String portletId, String roles, boolean active)
		throws SystemException {

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

		_updateCompanyPortletsPool(companyId);

		return portlet;
	}

	protected void setFriendlyURLRoutes(
		Portlet portlet, Element portletElement) {

		Element friendlyUrlRoutesElement = portletElement.element(
			"friendly-url-routes");

		if (friendlyUrlRoutesElement == null) {
			return;
		}

		Router router = new Router();

		for (Element routeElement :
				friendlyUrlRoutesElement.elements("route")) {

			Route route = new Route();

			String pattern = routeElement.elementText("route-pattern");

			route.setPattern(pattern);

			for (Element routeDefaultElement :
					routeElement.elements("route-default")) {

				String defaultParamater = routeDefaultElement.elementText(
					"route-default-parameter");
				String defaultValue = routeDefaultElement.elementText(
					"route-default-value");

				route.addDefaultValue(defaultParamater, defaultValue);
			}

			router.addRoute(route);
		}
	}

	private String _encodeKey(long companyId) {
		return _keyPrefix.concat(String.valueOf(companyId));
	}

	private PortletApp _getPortletApp(String servletContextName) {
		PortletApp portletApp = _portletAppsPool.get(servletContextName);

		if (portletApp == null) {
			portletApp = new PortletAppImpl(servletContextName);

			_portletAppsPool.put(servletContextName, portletApp);
		}

		return portletApp;
	}

	private String _getPortletId(String securityPath) {
		if (_portletIdsByStrutsPath.size() == 0) {
			for (Portlet portlet : _getPortletsPool().values()) {
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

			String portletId = portlet.getPortletId();

			if (!portletId.startsWith(portletNamePrefix)) {
				itr.remove();
			}
		}

		return portlets;
	}

	private List<Portlet> _getPortletsByServletContextName(
		String servletContextName, Map<String, Portlet> portletsPool) {

		List<Portlet> portlets = new ArrayList<Portlet>();

		for (Map.Entry<String, Portlet> entry : portletsPool.entrySet()) {
			String portletId = entry.getKey();
			Portlet portlet = entry.getValue();

			if (Validator.isNotNull(servletContextName)) {
				if (portletId.endsWith(
						PortletConstants.WAR_SEPARATOR + servletContextName)) {

					portlets.add(portlet);
				}
			}
			else {
				if (!portletId.contains(PortletConstants.WAR_SEPARATOR)) {
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

		String key = _encodeKey(companyId);

		Map<String, Portlet> portletsPool =
			(Map<String, Portlet>)_companyPortletsPool.get(key);

		if (portletsPool == null) {
			portletsPool = new ConcurrentHashMap<String, Portlet>();

			Map<String, Portlet> parentPortletsPool = _getPortletsPool();

			if (parentPortletsPool == null) {

				// The Upgrade scripts sometimes try to access portlet
				// preferences before the portal's been initialized. Return an
				// empty pool.

				return portletsPool;
			}

			for (Portlet portlet : parentPortletsPool.values()) {
				portlet = (Portlet)portlet.clone();

				portlet.setCompanyId(companyId);

				portletsPool.put(portlet.getPortletId(), portlet);
			}

			List<Portlet> portlets = portletPersistence.findByCompanyId(
				companyId);

			for (Portlet portlet : portlets) {
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

			_companyPortletsPool.put(key, portletsPool);
		}

		return portletsPool;
	}

	private void _readLiferayDisplay(
		String servletContextName, Element element,
		PortletCategory portletCategory, Set<String> portletIds) {

		for (Element categoryElement : element.elements("category")) {
			String name = categoryElement.attributeValue("name");

			PortletCategory curPortletCategory = new PortletCategory(name);

			portletCategory.addCategory(curPortletCategory);

			Set<String> curPortletIds = curPortletCategory.getPortletIds();

			for (Element portletElement : categoryElement.elements("portlet")) {
				String portletId = portletElement.attributeValue("id");

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
				servletContextName, categoryElement, curPortletCategory,
				portletIds);
		}
	}

	private PortletCategory _readLiferayDisplayXML(String xml)
		throws Exception {

		return _readLiferayDisplayXML(null, xml);
	}

	private PortletCategory _readLiferayDisplayXML(
			String servletContextName, String xml)
		throws Exception {

		PortletCategory portletCategory = new PortletCategory();

		if (xml == null) {
			xml = ContentUtil.get(
				"com/liferay/portal/deploy/dependencies/liferay-display.xml");
		}

		Document document = SAXReaderUtil.read(xml, true);

		Element rootElement = document.getRootElement();

		Set<String> portletIds = new HashSet<String>();

		_readLiferayDisplay(
			servletContextName, rootElement, portletCategory, portletIds);

		// Portlets that do not belong to any categories should default to the
		// Undefined category

		Set<String> undefinedPortletIds = new HashSet<String>();

		for (Portlet portlet : _getPortletsPool().values()) {
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

		if (!undefinedPortletIds.isEmpty()) {
			PortletCategory undefinedCategory = new PortletCategory(
				"category.undefined");

			portletCategory.addCategory(undefinedCategory);

			undefinedCategory.getPortletIds().addAll(undefinedPortletIds);
		}

		return portletCategory;
	}

	private Set<String> _readLiferayPortletXML(
			String xml, Map<String, Portlet> portletsPool)
		throws Exception {

		return _readLiferayPortletXML(StringPool.BLANK, xml, portletsPool);
	}

	private Set<String> _readLiferayPortletXML(
			String servletContextName, String xml,
			Map<String, Portlet> portletsPool)
		throws Exception {

		Set<String> liferayPortletIds = new HashSet<String>();

		if (xml == null) {
			return liferayPortletIds;
		}

		Document document = SAXReaderUtil.read(xml, true);

		Element rootElement = document.getRootElement();

		PortletApp portletApp = _getPortletApp(servletContextName);

		Map<String, String> roleMappers = new HashMap<String, String>();

		for (Element roleMapperElement : rootElement.elements("role-mapper")) {
			String roleName = roleMapperElement.elementText("role-name");
			String roleLink = roleMapperElement.elementText("role-link");

			roleMappers.put(roleName, roleLink);
		}

		Map<String, String> customUserAttributes =
			portletApp.getCustomUserAttributes();

		for (Element customUserAttributeElement :
				rootElement.elements("custom-user-attribute")) {

			String customClass = customUserAttributeElement.elementText(
				"custom-class");

			for (Element nameElement :
					customUserAttributeElement.elements("name")) {

				String name = nameElement.getText();

				customUserAttributes.put(name, customClass);
			}
		}

		for (Element portlet : rootElement.elements("portlet")) {
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

				Iterator<Element> itr2 = portlet.elementIterator(
					"scheduler-entry");

				while (itr2.hasNext()){
					Element schedulerEntryEl = itr2.next();

					SchedulerEntry schedulerEntry = new SchedulerEntryImpl();

					String schedulerDescription = schedulerEntryEl.elementText(
						"scheduler-description");

					schedulerEntry.setDescription(GetterUtil.getString(
						schedulerDescription));
					schedulerEntry.setEventListenerClass(GetterUtil.getString(
						schedulerEntryEl.elementText(
							"scheduler-event-listener-class"),
						schedulerEntry.getEventListenerClass()));

					Element triggerEl = schedulerEntryEl.element("trigger");

					Element cronEl = triggerEl.element("cron");
					Element simpleEl = triggerEl.element("simple");

					if (cronEl != null) {
						schedulerEntry.setTriggerType(TriggerType.CRON);

						Element propertyKeyEl = cronEl.element("property-key");

						if (propertyKeyEl != null) {
							schedulerEntry.setPropertyKey(
								propertyKeyEl.getTextTrim());
						}
						else {
							schedulerEntry.setTriggerValue(
								cronEl.elementText("cron-trigger-value"));
						}
					}
					else if (simpleEl != null) {
						schedulerEntry.setTriggerType(TriggerType.SIMPLE);

						Element propertyKeyEl = simpleEl.element(
							"property-key");

						if (propertyKeyEl != null) {
							schedulerEntry.setPropertyKey(
								propertyKeyEl.getTextTrim());
						}
						else {
							Element simpleTriggerValueEl = simpleEl.element(
								"simple-trigger-value");

							schedulerEntry.setTriggerValue(
								simpleTriggerValueEl.getTextTrim());
						}

						String timeUnit = GetterUtil.getString(
							simpleEl.elementText("time-unit"),
							TimeUnit.SECOND.getValue());

						schedulerEntry.setTimeUnit(
							TimeUnit.parse(timeUnit.toLowerCase()));
					}

					portletModel.addSchedulerEntry(schedulerEntry);
				}

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

				setFriendlyURLRoutes(portletModel, portlet);

				portletModel.setURLEncoderClass(GetterUtil.getString(
					portlet.elementText("url-encoder-class"),
					portletModel.getURLEncoderClass()));
				portletModel.setPortletDataHandlerClass(GetterUtil.getString(
					portlet.elementText("portlet-data-handler-class"),
					portletModel.getPortletDataHandlerClass()));
				portletModel.setPortletLayoutListenerClass(GetterUtil.getString(
					portlet.elementText("portlet-layout-listener-class"),
					portletModel.getPortletLayoutListenerClass()));
				portletModel.setPollerProcessorClass(GetterUtil.getString(
					portlet.elementText("poller-processor-class"),
					portletModel.getPollerProcessorClass()));
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
				portletModel.setWebDAVStorageToken(GetterUtil.getString(
					portlet.elementText("webdav-storage-token"),
					portletModel.getWebDAVStorageToken()));
				portletModel.setWebDAVStorageClass(GetterUtil.getString(
					portlet.elementText("webdav-storage-class"),
					portletModel.getWebDAVStorageClass()));
				portletModel.setXmlRpcMethodClass(GetterUtil.getString(
					portlet.elementText("xml-rpc-method-class"),
					portletModel.getXmlRpcMethodClass()));
				portletModel.setControlPanelEntryCategory(GetterUtil.getString(
					portlet.elementText("control-panel-entry-category"),
					portletModel.getControlPanelEntryCategory()));
				portletModel.setControlPanelEntryWeight(GetterUtil.getDouble(
					portlet.elementText("control-panel-entry-weight"),
					portletModel.getControlPanelEntryWeight()));
				portletModel.setControlPanelEntryClass(GetterUtil.getString(
					portlet.elementText("control-panel-entry-class"),
					portletModel.getControlPanelEntryClass()));

				List<String> assetRendererFactoryClasses =
					portletModel.getAssetRendererFactoryClasses();

				itr2 = portlet.elements("asset-renderer-factory").iterator();

				while (itr2.hasNext()) {
					Element assetRendererFactoryClassEl = itr2.next();

					assetRendererFactoryClasses.add(
						assetRendererFactoryClassEl.getText());
				}

				List<String> customAttributesDisplayClasses =
					portletModel.getCustomAttributesDisplayClasses();

				itr2 = portlet.elements("custom-attributes-display").iterator();

				while (itr2.hasNext()) {
					Element customAttributesDisplayClassEl = itr2.next();

					customAttributesDisplayClasses.add(
						customAttributesDisplayClassEl.getText());
				}

				if (portletModel.getCustomAttributesDisplayClasses().
					isEmpty()) {

					_customAttributesDisplayPortlets.remove(portletId);
				}
				else {
					_customAttributesDisplayPortlets.put(
						portletId, portletModel);
				}

				List<String> workflowHandlerClasses =
					portletModel.getWorkflowHandlerClasses();

				itr2 = portlet.elements("workflow-handler").iterator();

				while (itr2.hasNext()) {
					Element workflowHandlerClassEl = itr2.next();

					workflowHandlerClasses.add(
						workflowHandlerClassEl.getText());
				}

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
				portletModel.setScopeable(GetterUtil.getBoolean(
					portlet.elementText("scopeable"),
					portletModel.isScopeable()));
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

				itr2 = portlet.elements("header-portal-css").iterator();

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
				portletModel.setFacebookIntegration(GetterUtil.getString(
					portlet.elementText("facebook-integration"),
					portletModel.getFacebookIntegration()));
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
			ServletContext servletContext, String xml,
			Map<String, Portlet> portletsPool, List<String> servletURLPatterns,
			PluginPackage pluginPackage)
		throws Exception {

		return _readPortletXML(
			StringPool.BLANK, servletContext, xml, portletsPool,
			servletURLPatterns, pluginPackage);
	}

	private Set<String> _readPortletXML(
			String servletContextName, ServletContext servletContext,
			String xml, Map<String, Portlet> portletsPool,
			List<String> servletURLPatterns, PluginPackage pluginPackage)
		throws Exception {

		Set<String> portletIds = new HashSet<String>();

		if (xml == null) {
			return portletIds;
		}

		Document document = SAXReaderUtil.read(
			xml, PropsValues.PORTLET_XML_VALIDATE);

		Element rootElement = document.getRootElement();

		PortletApp portletApp = _getPortletApp(servletContextName);

		portletApp.getServletURLPatterns().addAll(servletURLPatterns);

		Set<String> userAttributes = portletApp.getUserAttributes();

		Iterator<Element> itr1 = rootElement.elements(
			"user-attribute").iterator();

		while (itr1.hasNext()) {
			Element userAttribute = itr1.next();

			String name = userAttribute.elementText("name");

			userAttributes.add(name);
		}

		String defaultNamespace = rootElement.elementText("default-namespace");

		if (Validator.isNotNull(defaultNamespace)) {
			portletApp.setDefaultNamespace(defaultNamespace);
		}

		itr1 = rootElement.elements("event-definition").iterator();

		while (itr1.hasNext()) {
			Element eventDefinitionEl = itr1.next();

			Element qNameEl = eventDefinitionEl.element("qname");
			Element nameEl = eventDefinitionEl.element("name");
			String valueType = eventDefinitionEl.elementText("value-type");

			QName qName = PortletQNameUtil.getQName(
				qNameEl, nameEl, portletApp.getDefaultNamespace());

			EventDefinition eventDefinition = new EventDefinitionImpl(
				qName, valueType, portletApp);

			portletApp.addEventDefinition(eventDefinition);
		}

		itr1 = rootElement.elements("public-render-parameter").iterator();

		while (itr1.hasNext()) {
			Element publicRenderParameterEl = itr1.next();

			String identifier = publicRenderParameterEl.elementText(
				"identifier");
			Element qNameEl = publicRenderParameterEl.element("qname");
			Element nameEl = publicRenderParameterEl.element("name");

			QName qName = PortletQNameUtil.getQName(
				qNameEl, nameEl, portletApp.getDefaultNamespace());

			PublicRenderParameter publicRenderParameter =
				new PublicRenderParameterImpl(identifier, qName, portletApp);

			portletApp.addPublicRenderParameter(publicRenderParameter);
		}

		itr1 = rootElement.elements("container-runtime-option").iterator();

		while (itr1.hasNext()) {
			Element containerRuntimeOption = itr1.next();

			String name = GetterUtil.getString(
				containerRuntimeOption.elementText("name"));

			List<String> values = new ArrayList<String>();

			for (Element value : containerRuntimeOption.elements("value")) {
				values.add(value.getTextTrim());
			}

			portletApp.getContainerRuntimeOptions().put(
				name, values.toArray(new String[values.size()]));

			if (name.equals(
					LiferayPortletConfig.RUNTIME_OPTION_PORTAL_CONTEXT) &&
				!values.isEmpty() && GetterUtil.getBoolean(values.get(0))) {

				portletApp.setWARFile(false);
			}
		}

		long timestamp = ServletContextUtil.getLastModified(servletContext);

		itr1 = rootElement.elements("portlet").iterator();

		while (itr1.hasNext()) {
			Element portlet = itr1.next();

			String portletName = portlet.elementText("portlet-name");

			String portletId = portletName;

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

			portletModel.setTimestamp(timestamp);

			portletModel.setPluginPackage(pluginPackage);
			portletModel.setPortletApp(portletApp);

			portletModel.setPortletName(portletName);
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

				Set<String> mimeTypePortletModes =
					portletModel.getPortletModes().get(mimeType);

				if (mimeTypePortletModes == null) {
					mimeTypePortletModes = new HashSet<String>();

					portletModel.getPortletModes().put(
						mimeType, mimeTypePortletModes);
				}

				mimeTypePortletModes.add(
					PortletMode.VIEW.toString().toLowerCase());

				Iterator<Element> itr3 = supports.elements(
					"portlet-mode").iterator();

				while (itr3.hasNext()) {
					Element portletMode = itr3.next();

					mimeTypePortletModes.add(
						portletMode.getTextTrim().toLowerCase());
				}

				Set<String> mimeTypeWindowStates =
					portletModel.getWindowStates().get(mimeType);

				if (mimeTypeWindowStates == null) {
					mimeTypeWindowStates = new HashSet<String>();

					portletModel.getWindowStates().put(
						mimeType, mimeTypeWindowStates);
				}

				mimeTypeWindowStates.add(
					WindowState.NORMAL.toString().toLowerCase());

				itr3 = supports.elements("window-state").iterator();

				if (!itr3.hasNext()) {
					mimeTypeWindowStates.add(
						WindowState.MAXIMIZED.toString().toLowerCase());
					mimeTypeWindowStates.add(
						WindowState.MINIMIZED.toString().toLowerCase());
					mimeTypeWindowStates.add(
						LiferayWindowState.EXCLUSIVE.toString().toLowerCase());
					mimeTypeWindowStates.add(
						LiferayWindowState.POP_UP.toString().toLowerCase());
				}

				while (itr3.hasNext()) {
					Element windowState = itr3.next();

					mimeTypeWindowStates.add(
						windowState.getTextTrim().toLowerCase());
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
			String portletInfoDescription = null;

			if (portletInfo != null) {
				portletInfoTitle = portletInfo.elementText("title");
				portletInfoShortTitle = portletInfo.elementText("short-title");
				portletInfoKeyWords = portletInfo.elementText("keywords");
			}

			portletModel.setPortletInfo(
				new PortletInfo(
					portletInfoTitle, portletInfoShortTitle,
					portletInfoKeyWords, portletInfoDescription));

			Element portletPreferences = portlet.element("portlet-preferences");

			String defaultPreferences = null;
			String preferencesValidator = null;

			if (portletPreferences != null) {
				Element preferencesValidatorEl =
					portletPreferences.element("preferences-validator");

				if (preferencesValidatorEl != null) {
					preferencesValidator = preferencesValidatorEl.getText();

					portletPreferences.remove(preferencesValidatorEl);
				}

				defaultPreferences = portletPreferences.asXML();
			}

			portletModel.setDefaultPreferences(defaultPreferences);
			portletModel.setPreferencesValidator(preferencesValidator);

			if (!portletApp.isWARFile() &&
				Validator.isNotNull(preferencesValidator) &&
				PropsValues.PREFERENCE_VALIDATE_ON_STARTUP) {

				try {
					PreferencesValidator preferencesValidatorObj =
						PortalUtil.getPreferencesValidator(portletModel);

					preferencesValidatorObj.validate(
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

				QName qName = PortletQNameUtil.getQName(
					qNameEl, nameEl, portletApp.getDefaultNamespace());

				portletModel.addProcessingEvent(qName);
			}

			itr2 = portlet.elements("supported-publishing-event").iterator();

			while (itr2.hasNext()) {
				Element supportedPublishingEvent = itr2.next();

				Element qNameEl = supportedPublishingEvent.element("qname");
				Element nameEl = supportedPublishingEvent.element("name");

				QName qName = PortletQNameUtil.getQName(
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

		itr1 = rootElement.elements("filter").iterator();

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

		itr1 = rootElement.elements("filter-mapping").iterator();

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

		itr1 = rootElement.elements("listener").iterator();

		while (itr1.hasNext()) {
			Element listener = itr1.next();

			String listenerClass = listener.elementText("listener-class");

			PortletURLListener portletURLListener = new PortletURLListenerImpl(
				listenerClass, portletApp);

			portletApp.addPortletURLListener(portletURLListener);
		}

		return portletIds;
	}

	private List<String> _readWebXML(String xml) throws Exception {
		List<String> servletURLPatterns = new ArrayList<String>();

		if (xml == null) {
			return servletURLPatterns;
		}

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		for (Element servletMappingElement :
				rootElement.elements("servlet-mapping")) {

			String urlPattern = servletMappingElement.elementText(
				"url-pattern");

			servletURLPatterns.add(urlPattern);
		}

		return servletURLPatterns;
	}

	private void _setSpriteImages(
			ServletContext servletContext, PortletApp portletApp,
			String resourcePath)
		throws Exception {

		Set<String> resourcePaths = servletContext.getResourcePaths(
			resourcePath);

		if (resourcePaths == null) {
			return;
		}

		List<File> images = new ArrayList<File>(resourcePaths.size());

		for (String curResourcePath : resourcePaths) {
			if (curResourcePath.endsWith(StringPool.SLASH)) {
				_setSpriteImages(servletContext, portletApp, curResourcePath);
			}
			else if (curResourcePath.endsWith(".png")) {
				String realPath = ServletContextUtil.getRealPath(
					servletContext, curResourcePath);

				if (realPath != null) {
					images.add(new File(realPath));
				}
				else {
					if (ServerDetector.isTomcat()) {
						if (_log.isInfoEnabled()) {
							_log.info(ServletContextUtil.LOG_INFO_SPRITES);
						}
					}
					else {
						_log.error(
							"Real path for " + curResourcePath + " is null");
					}
				}
			}
		}

		String spriteFileName = ".sprite.png";
		String spritePropertiesFileName = ".sprite.properties";
		String spritePropertiesRootPath = ServletContextUtil.getRealPath(
			servletContext, StringPool.SLASH);

		Properties spriteProperties = SpriteProcessorUtil.generate(
			images, spriteFileName, spritePropertiesFileName,
			spritePropertiesRootPath, 16, 16, 10240);

		if (spriteProperties == null) {
			return;
		}

		spriteFileName =
			resourcePath.substring(0, resourcePath.length()) + spriteFileName;

		portletApp.setSpriteImages(spriteFileName, spriteProperties);
	}

	private void _updateCompanyPortletsPool(long companyId) {
		String key = _encodeKey(companyId);

		Map<String, Portlet> portletsPool =
			(Map<String, Portlet>)_companyPortletsPool.get(key);

		_companyPortletsPool.put(key, portletsPool);
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletLocalServiceImpl.class);

	private static PortalCache _companyPortletsPool =
		MultiVMPoolUtil.getCache(Portlet.class.getName());
	private static Map<String, Portlet> _customAttributesDisplayPortlets =
		new ConcurrentHashMap<String, Portlet>();
	private static Map<String, Portlet> _friendlyURLMapperPortlets =
		new ConcurrentHashMap<String, Portlet>();
	private static String _keyPrefix = Portlet.class.getName().concat(
		StringPool.POUND);
	private static Map<String, PortletApp> _portletAppsPool =
		new ConcurrentHashMap<String, PortletApp>();
	private static Map<String, String> _portletIdsByStrutsPath =
		new ConcurrentHashMap<String, String>();
	private static Map<String, Portlet> _portletsPool =
		new ConcurrentHashMap<String, Portlet>();

}