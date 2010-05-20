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

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.deploy.hot.PluginPackageHotDeployListener;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.events.StartupAction;
import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.servlet.PortletSessionTracker;
import com.liferay.portal.kernel.servlet.ProtectedServletRequest;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalInitableUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletFilter;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.model.User;
import com.liferay.portal.plugin.PluginPackageIndexer;
import com.liferay.portal.poller.PollerProcessorUtil;
import com.liferay.portal.pop.POPServerUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.servlet.filters.i18n.I18nFilter;
import com.liferay.portal.struts.MultiMessageResources;
import com.liferay.portal.struts.PortletRequestProcessor;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.ExtRegistry;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.ShutdownUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.xmlrpc.XmlRpcServlet;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.portlet.PortletFilterFactory;
import com.liferay.portlet.PortletInstanceFactoryUtil;
import com.liferay.portlet.PortletURLListenerFactory;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.social.messaging.CheckEquityLogMessageListener;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialRequestInterpreter;
import com.liferay.portlet.social.model.impl.SocialActivityInterpreterImpl;
import com.liferay.portlet.social.model.impl.SocialRequestInterpreterImpl;
import com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil;
import com.liferay.portlet.social.service.SocialRequestInterpreterLocalServiceUtil;
import com.liferay.util.servlet.DynamicServletRequest;
import com.liferay.util.servlet.EncryptedServletRequest;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.config.ControllerConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.tiles.TilesUtilImpl;

/**
 * <a href="MainServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Brian Myunghun Kim
 */
public class MainServlet extends ActionServlet {

	public void destroy() {
		List<Portlet> portlets = PortletLocalServiceUtil.getPortlets();

		if (_log.isDebugEnabled()) {
			_log.debug("Destroy schedulers");
		}

		try {
			destroySchedulers(portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Destroy portlets");
		}

		try {
			destroyPortlets(portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Destroy companies");
		}

		try {
			destroyCompanies();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Process global shutdown events");
		}

		try {
			processGlobalShutdownEvents();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Destroy");
		}

		callParentDestroy();
	}

	public void init() throws ServletException {
		if (_log.isDebugEnabled()) {
			_log.debug("Initialize");
		}

		callParentInit();

		if (_log.isDebugEnabled()) {
			_log.debug("Process startup events");
		}

		try {
			processStartupEvents();
		}
		catch (Exception e) {
			_log.error(e, e);

			System.out.println(
				"Stopping the server due to unexpected startup errors");

			System.exit(0);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize servlet context pool");
		}

		try {
			initSevletContextPool();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize plugin package");
		}

		PluginPackage pluginPackage = null;

		try {
			pluginPackage = initPluginPackage();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize portlets");
		}

		List<Portlet> portlets = null;

		try {
			portlets = initPortlets(pluginPackage);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize layout templates");
		}

		try {
			initLayoutTemplates(pluginPackage, portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize themes");
		}

		try {
			initThemes(pluginPackage, portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize indexers");
		}

		try {
			initIndexers(portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize schedulers");
		}

		try {
			initSchedulers(portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize poller processors");
		}

		try {
			initPollerProcessors(portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize POP message listeners");
		}

		try {
			initPOPMessageListeners(portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize social activity interpreters");
		}

		try {
			initSocialActivityInterpreters(portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize social request interpreters");
		}

		try {
			initSocialEquityLogScheduler();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			initSocialRequestInterpreters(portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize WebDAV storages");
		}

		try {
			initWebDAVStorages(portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize XML-RPC methods");
		}

		try {
			initXmlRpcMethods(portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize asset renderer factories");
		}

		try {
			initAssetRendererFactories(portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize workflow handlers");
		}

		try {
			initWorkflowHandlers(portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize web settings");
		}

		try {
			initWebSettings();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize extension environment");
		}

		try {
			initExt();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Process global startup events");
		}

		try {
			processGlobalStartupEvents();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize resource actions");
		}

		try {
			initResourceActions(portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize resource codes");
		}

		try {
			initResourceCodes(portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize companies");
		}

		try {
			initCompanies();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize message resources");
		}

		try {
			initMessageResources();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize plugins");
		}

		try {
			initPlugins();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		if (_log.isDebugEnabled()) {
			_log.debug("Process service request");
		}

		if (processShutdownRequest(request, response)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processed shutdown request");
			}

			return;
		}

		if (processMaintenanceRequest(request, response)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processed maintenance request");
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Get company id");
		}

		long companyId = getCompanyId(request);

		if (_log.isDebugEnabled()) {
			_log.debug("Set portal port");
		}

		setPortalPort(request);

		if (_log.isDebugEnabled()) {
			_log.debug("Check variables");
		}

		checkServletContext(request);
		checkPortletSessionTracker(request);
		checkPortletRequestProcessor(request);
		checkTilesDefinitionsFactory();

		if (_log.isDebugEnabled()) {
			_log.debug("Encrypt request");
		}

		request = encryptRequest(request, companyId);

		long userId = getUserId(request);
		String remoteUser = getRemoteUser(request, userId);

		if (_log.isDebugEnabled()) {
			_log.debug("Protect request");
		}

		request = protectRequest(request, remoteUser);

		if (_log.isDebugEnabled()) {
			_log.debug("Set principal");
		}

		setPrincipalName(userId, remoteUser);

		try {
			if (_log.isDebugEnabled()) {
				_log.debug(
				"Authenticate user id " + userId + " and remote user " +
					remoteUser);
			}

			userId = loginUser(request, response, userId, remoteUser);

			if (_log.isDebugEnabled()) {
				_log.debug("Authenticated user id " + userId);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Process service pre events");
		}

		if (processServicePre(request, response, userId)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processing service pre events has errors");
			}

			return;
		}

		if (hasAbsoluteRedirect(request)) {
			if (_log.isDebugEnabled()) {
				String currentURL = PortalUtil.getCurrentURL(request);

				_log.debug(
					"Current URL " + currentURL + " has absolute redirect");
			}

			return;
		}

		if (!hasThemeDisplay(request)) {
			if (_log.isDebugEnabled()) {
				String currentURL = PortalUtil.getCurrentURL(request);

				_log.debug(
					"Current URL " + currentURL +
						" does not have a theme display");
			}

			return;
		}

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Call parent service");
			}

			callParentService(request, response);
		}
		finally {
			if (_log.isDebugEnabled()) {
				_log.debug("Process service post events");
			}

			processServicePost(request, response);
		}
	}

	protected void callParentDestroy() {
		super.destroy();
	}

	protected void callParentInit() throws ServletException {
		super.init();
	}

	protected void callParentService(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		super.service(request, response);
	}

	protected void checkPortletRequestProcessor(HttpServletRequest request)
		throws ServletException {

		ServletContext servletContext = getServletContext();

		PortletRequestProcessor portletReqProcessor =
			(PortletRequestProcessor)servletContext.getAttribute(
				WebKeys.PORTLET_STRUTS_PROCESSOR);

		if (portletReqProcessor == null) {
			ModuleConfig moduleConfig = getModuleConfig(request);

			portletReqProcessor =
				PortletRequestProcessor.getInstance(this, moduleConfig);

			servletContext.setAttribute(
				WebKeys.PORTLET_STRUTS_PROCESSOR, portletReqProcessor);
		}
	}

	protected void checkPortletSessionTracker(HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session.getAttribute(WebKeys.PORTLET_SESSION_TRACKER) != null) {
			return;
		}

		session.setAttribute(
			WebKeys.PORTLET_SESSION_TRACKER,
			PortletSessionTracker.getInstance());
	}

	protected void checkServletContext(HttpServletRequest request) {
		ServletContext servletContext = getServletContext();

		request.setAttribute(WebKeys.CTX, servletContext);
	}

	protected void checkTilesDefinitionsFactory() {
		ServletContext servletContext = getServletContext();

		if (servletContext.getAttribute(
				TilesUtilImpl.DEFINITIONS_FACTORY) != null) {

			return;
		}

		servletContext.setAttribute(
			TilesUtilImpl.DEFINITIONS_FACTORY,
			servletContext.getAttribute(TilesUtilImpl.DEFINITIONS_FACTORY));
	}

	protected void checkWebSettings(String xml) throws DocumentException {
		Document doc = SAXReaderUtil.read(xml);

		Element root = doc.getRootElement();

		int timeout = PropsValues.SESSION_TIMEOUT;

		Element sessionConfig = root.element("session-config");

		if (sessionConfig != null) {
			String sessionTimeout = sessionConfig.elementText(
				"session-timeout");

			timeout = GetterUtil.getInteger(sessionTimeout, timeout);
		}

		PropsUtil.set(PropsKeys.SESSION_TIMEOUT, String.valueOf(timeout));

		PropsValues.SESSION_TIMEOUT = timeout;

		I18nServlet.setLanguageIds(root);
		I18nFilter.setLanguageIds(I18nServlet.getLanguageIds());
	}

	protected void destroyCompanies() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIds();

		for (int i = 0; i < companyIds.length; i++) {
			destroyCompany(companyIds[i]);
		}
	}

	protected void destroyCompany(long companyId) {
		if (_log.isDebugEnabled()) {
			_log.debug("Process shutdown events");
		}

		try {
			EventsProcessorUtil.process(
				PropsKeys.APPLICATION_SHUTDOWN_EVENTS,
				PropsValues.APPLICATION_SHUTDOWN_EVENTS,
				new String[] {String.valueOf(companyId)});
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void destroyPortlets(List<Portlet> portlets) throws Exception {
		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			PortletInstanceFactoryUtil.destroy(portlet);
		}
	}

	protected void destroySchedulers(List<Portlet> portlets) throws Exception {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		for (Portlet portlet : portlets) {
			if (!portlet.isActive()) {
				continue;
			}

			List<SchedulerEntry> schedulerEntries =
				portlet.getSchedulerEntries();

			if ((schedulerEntries == null) || schedulerEntries.isEmpty()) {
				continue;
			}

			for (SchedulerEntry schedulerEntry : schedulerEntries) {
				SchedulerEngineUtil.unschedule(schedulerEntry);
			}
		}
	}

	protected HttpServletRequest encryptRequest(
		HttpServletRequest request, long companyId) {

		boolean encryptRequest = ParamUtil.getBoolean(request, WebKeys.ENCRYPT);

		if (!encryptRequest) {
			return request;
		}

		try {
			Company company = CompanyLocalServiceUtil.getCompanyById(
				companyId);

			request = new EncryptedServletRequest(
				request, company.getKeyObj());
		}
		catch (Exception e) {
		}

		return request;
	}

	protected long getCompanyId(HttpServletRequest request) {
		return PortalInstances.getCompanyId(request);
	}

	protected String getRemoteUser(
		HttpServletRequest request, long userId) {

		String remoteUser = request.getRemoteUser();

		if (!PropsValues.PORTAL_JAAS_ENABLE) {
			HttpSession session = request.getSession();

			String jRemoteUser = (String)session.getAttribute("j_remoteuser");

			if (jRemoteUser != null) {
				remoteUser = jRemoteUser;

				session.removeAttribute("j_remoteuser");
			}
		}

		if ((userId > 0) && (remoteUser == null)) {
			remoteUser = String.valueOf(userId);
		}

		return remoteUser;
	}

	protected synchronized RequestProcessor getRequestProcessor(
			ModuleConfig moduleConfig)
		throws ServletException {

		ServletContext servletContext = getServletContext();

		String key = Globals.REQUEST_PROCESSOR_KEY + moduleConfig.getPrefix();

		RequestProcessor processor =
			(RequestProcessor)servletContext.getAttribute(key);

		if (processor == null) {
			ControllerConfig controllerConfig =
				moduleConfig.getControllerConfig();

			String processorClass = controllerConfig.getProcessorClass();

			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			try {
				processor = (RequestProcessor)classLoader.loadClass(
					processorClass).newInstance();
			}
			catch (Exception e) {
				throw new ServletException(e);
			}

			processor.init(this, moduleConfig);

			servletContext.setAttribute(key, processor);
		}

		return processor;
	}

	protected long getUserId(HttpServletRequest request) {
		return PortalUtil.getUserId(request);
	}

	protected boolean hasAbsoluteRedirect(HttpServletRequest request) {
		if (request.getAttribute(
				AbsoluteRedirectsResponse.class.getName()) == null) {

			return false;
		}
		else {
			return true;
		}
	}

	protected boolean hasThemeDisplay(HttpServletRequest request) {
		if (request.getAttribute(WebKeys.THEME_DISPLAY) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	protected void initAssetRendererFactories(List<Portlet> portlets)
		throws Exception {

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			List<AssetRendererFactory> assetRendererFactories =
				portlet.getAssetRendererFactoryInstances();

			if (assetRendererFactories == null) {
				continue;
			}

			for (AssetRendererFactory assetRendererFactory :
					assetRendererFactories) {

				String assetRendererEnabledKey =
					PropsKeys.ASSET_RENDERER_ENABLED +
						assetRendererFactory.getClass().getName();

				boolean assetRendererEnabledValue = GetterUtil.getBoolean(
					PropsUtil.get(assetRendererEnabledKey), true);

				if (assetRendererEnabledValue) {
					AssetRendererFactoryRegistryUtil.register(
						assetRendererFactory);
				}
			}
		}
	}

	protected void initCompanies() throws Exception {
		ServletContext servletContext = getServletContext();

		String[] webIds = PortalInstances.getWebIds();

		for (int i = 0; i < webIds.length; i++) {
			PortalInstances.initCompany(servletContext, webIds[i]);
		}
	}

	protected void initExt() throws Exception {
		ServletContext servletContext = getServletContext();

		ExtRegistry.registerPortal(servletContext);
	}

	protected void initIndexers(List<Portlet> portlets) throws Exception {
		IndexerRegistryUtil.register(new PluginPackageIndexer());

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			String indexerClass = portlet.getIndexerClass();

			if (!portlet.isActive() || Validator.isNull(indexerClass)) {
				continue;
			}

			Indexer indexer = (Indexer)InstancePool.get(indexerClass);

			IndexerRegistryUtil.register(indexer);
		}
	}

	protected void initLayoutTemplates(
			PluginPackage pluginPackage, List<Portlet> portlets)
		throws Exception {

		ServletContext servletContext = getServletContext();

		String[] xmls = new String[] {
			HttpUtil.URLtoString(
				servletContext.getResource(
					"/WEB-INF/liferay-layout-templates.xml")),
			HttpUtil.URLtoString(
				servletContext.getResource(
					"/WEB-INF/liferay-layout-templates-ext.xml"))
		};

		LayoutTemplateLocalServiceUtil.init(
			servletContext, xmls, pluginPackage);
	}

	protected void initMessageResources() throws Exception {
		ServletContext servletContext = getServletContext();

		MultiMessageResources messageResources =
			(MultiMessageResources)servletContext.getAttribute(
				Globals.MESSAGES_KEY);

		LanguageResources.init(messageResources);
	}

	protected PluginPackage initPluginPackage() throws Exception {
		ServletContext servletContext = getServletContext();

		return PluginPackageHotDeployListener.readPluginPackage(servletContext);
	}

	protected void initPlugins() throws Exception {

		// See LEP-2885. Don't flush hot deploy events until after the portal
		// has initialized.

		PortalInitableUtil.flushInitables();
		HotDeployUtil.flushPrematureEvents();
	}

	protected void initPollerProcessors(List<Portlet> portlets)
		throws Exception {

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			PollerProcessor pollerProcessor =
				portlet.getPollerProcessorInstance();

			if (!portlet.isActive() || (pollerProcessor == null)) {
				continue;
			}

			PollerProcessorUtil.addPollerProcessor(
				portlet.getPortletId(), pollerProcessor);
		}
	}

	protected void initPOPMessageListeners(List<Portlet> portlets)
		throws Exception {

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			com.liferay.portal.kernel.pop.MessageListener popMessageListener =
				portlet.getPopMessageListenerInstance();

			if (!portlet.isActive() || (popMessageListener == null)) {
				continue;
			}

			POPServerUtil.addListener(popMessageListener);
		}
	}

	protected void initPortletApp(
			Portlet portlet, ServletContext servletContext)
		throws PortletException {

		PortletApp portletApp = portlet.getPortletApp();

		PortletConfig portletConfig = PortletConfigFactory.create(
			portlet, servletContext);

		PortletContext portletContext = portletConfig.getPortletContext();

		Set<PortletFilter> portletFilters = portletApp.getPortletFilters();

		for (PortletFilter portletFilter : portletFilters) {
			PortletFilterFactory.create(portletFilter, portletContext);
		}

		Set<PortletURLListener> portletURLListeners =
			portletApp.getPortletURLListeners();

		for (PortletURLListener portletURLListener : portletURLListeners) {
			PortletURLListenerFactory.create(portletURLListener);
		}
	}

	protected List<Portlet> initPortlets(PluginPackage pluginPackage)
		throws Exception {

		ServletContext servletContext = getServletContext();

		String[] xmls = new String[] {
			HttpUtil.URLtoString(
				servletContext.getResource(
					"/WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_CUSTOM)),
			HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/portlet-ext.xml")),
			HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/liferay-portlet.xml")),
			HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/liferay-portlet-ext.xml")),
			HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/web.xml"))
		};

		PortletLocalServiceUtil.initEAR(servletContext, xmls, pluginPackage);

		List<Portlet> portlets = PortletLocalServiceUtil.getPortlets();

		for (int i = 0; i < portlets.size(); i++) {
			Portlet portlet = portlets.get(i);

			if (i == 0) {
				initPortletApp(portlet, servletContext);
			}

			PortletInstanceFactoryUtil.create(portlet, servletContext);
		}

		return portlets;
	}

	protected void initResourceActions(List<Portlet> portlets)
		throws Exception {

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			List<String> portletActions =
				ResourceActionsUtil.getPortletResourceActions(
					portlet.getPortletId());

			ResourceActionLocalServiceUtil.checkResourceActions(
				portlet.getPortletId(), portletActions);

			List<String> modelNames =
				ResourceActionsUtil.getPortletModelResources(
					portlet.getPortletId());

			for (String modelName : modelNames) {
				List<String> modelActions =
					ResourceActionsUtil.getModelResourceActions(modelName);

				ResourceActionLocalServiceUtil.checkResourceActions(
					modelName, modelActions);
			}
		}
	}

	protected void initResourceCodes(List<Portlet> portlets) throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			List<String> modelNames =
				ResourceActionsUtil.getPortletModelResources(
					portlet.getPortletId());

			for (long companyId : companyIds) {
				ResourceCodeLocalServiceUtil.checkResourceCodes(
					companyId, portlet.getPortletId());

				for (String modelName : modelNames) {
					ResourceCodeLocalServiceUtil.checkResourceCodes(
						companyId, modelName);
				}
			}
		}
	}

	protected void initSchedulers(List<Portlet> portlets) throws Exception {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		for (Portlet portlet : portlets) {
			if (!portlet.isActive()) {
				continue;
			}

			List<SchedulerEntry> schedulerEntries =
				portlet.getSchedulerEntries();

			if ((schedulerEntries == null) || schedulerEntries.isEmpty()) {
				continue;
			}

			for (SchedulerEntry schedulerEntry : schedulerEntries) {
				String propertyKey = schedulerEntry.getPropertyKey();

				if (Validator.isNotNull(propertyKey)) {
					String triggerValue = PrefsPropsUtil.getString(propertyKey);

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Scheduler property key " + propertyKey +
								" has trigger value " + triggerValue);
					}

					if (Validator.isNull(triggerValue)) {
						throw new SchedulerException(
							"Property key " + propertyKey +
								" requires a value");
					}

					schedulerEntry.setTriggerValue(triggerValue);
				}

				SchedulerEngineUtil.schedule(
					schedulerEntry, PortalClassLoaderUtil.getClassLoader());
			}
		}
	}

	protected void initSevletContextPool() throws Exception {
		ServletContext servletContext = getServletContext();

		String contextPath = PortalUtil.getPathContext();

		ServletContextPool.put(contextPath, servletContext);
	}

	protected void initSocialActivityInterpreters(List<Portlet> portlets)
		throws Exception {

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			SocialActivityInterpreter socialActivityInterpreter =
				portlet.getSocialActivityInterpreterInstance();

			if (!portlet.isActive() ||
				(socialActivityInterpreter == null)) {

				continue;
			}

			socialActivityInterpreter = new SocialActivityInterpreterImpl(
				portlet.getPortletId(), socialActivityInterpreter);

			SocialActivityInterpreterLocalServiceUtil.addActivityInterpreter(
				socialActivityInterpreter);
		}
	}

	protected void initSocialEquityLogScheduler() throws Exception {
		SchedulerEntry schedulerEntry = new SchedulerEntryImpl();

		schedulerEntry.setEventListenerClass(
			CheckEquityLogMessageListener.class.getName());
		schedulerEntry.setTimeUnit(TimeUnit.MINUTE);
		schedulerEntry.setTriggerType(TriggerType.SIMPLE);
		schedulerEntry.setTriggerValue(
			PropsValues.SOCIAL_EQUITY_EQUITY_LOG_CHECK_INTERVAL);

		SchedulerEngineUtil.schedule(
			schedulerEntry, PortalClassLoaderUtil.getClassLoader());
	}

	protected void initSocialRequestInterpreters(List<Portlet> portlets)
		throws Exception {

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			SocialRequestInterpreter socialRequestInterpreter =
				portlet.getSocialRequestInterpreterInstance();

			if (!portlet.isActive() || (socialRequestInterpreter == null)) {
				continue;
			}

			socialRequestInterpreter = new SocialRequestInterpreterImpl(
				portlet.getPortletId(), socialRequestInterpreter);

			SocialRequestInterpreterLocalServiceUtil.addRequestInterpreter(
				socialRequestInterpreter);
		}
	}

	protected void initThemes(
			PluginPackage pluginPackage, List<Portlet> portlets)
		throws Exception {

		ServletContext servletContext = getServletContext();

		String[] xmls = new String[] {
			HttpUtil.URLtoString(
				servletContext.getResource(
					"/WEB-INF/liferay-look-and-feel.xml")),
			HttpUtil.URLtoString(
				servletContext.getResource(
					"/WEB-INF/liferay-look-and-feel-ext.xml"))
		};

		ThemeLocalServiceUtil.init(
			servletContext, null, true, xmls, pluginPackage);
	}

	protected void initWebDAVStorages(List<Portlet> portlets) throws Exception {
		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			WebDAVStorage webDAVStorage = portlet.getWebDAVStorageInstance();

			if (!portlet.isActive() || (webDAVStorage == null)) {
				continue;
			}

			webDAVStorage.setToken(portlet.getWebDAVStorageToken());

			WebDAVUtil.addStorage(webDAVStorage);
		}
	}

	protected void initWebSettings() throws Exception {
		ServletContext servletContext = getServletContext();

		String xml = HttpUtil.URLtoString(
			servletContext.getResource("/WEB-INF/web.xml"));

		checkWebSettings(xml);
	}

	protected void initWorkflowHandlers(List<Portlet> portlets)
		throws Exception {

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			List<WorkflowHandler> workflowHandlers =
				portlet.getWorkflowHandlerInstances();

			if (workflowHandlers == null) {
				continue;
			}

			WorkflowHandlerRegistryUtil.register(workflowHandlers);
		}
	}

	protected void initXmlRpcMethods(List<Portlet> portlets) throws Exception {
		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			Method method = portlet.getXmlRpcMethodInstance();

			if (!portlet.isActive() || (method == null)) {
				continue;
			}

			XmlRpcServlet.registerMethod(method);
		}
	}

	protected long loginUser(
			HttpServletRequest request, HttpServletResponse response,
			long userId, String remoteUser)
		throws PortalException, SystemException {

		if ((userId > 0) || (remoteUser == null)) {
			return userId;
		}

		userId = GetterUtil.getLong(remoteUser);

		EventsProcessorUtil.process(
			PropsKeys.LOGIN_EVENTS_PRE, PropsValues.LOGIN_EVENTS_PRE, request,
			response);

		User user = UserLocalServiceUtil.getUserById(userId);

		if (PropsValues.USERS_UPDATE_LAST_LOGIN) {
			UserLocalServiceUtil.updateLastLogin(
				userId, request.getRemoteAddr());
		}

		HttpSession session = request.getSession();

		session.setAttribute(WebKeys.USER_ID, new Long(userId));
		session.setAttribute(Globals.LOCALE_KEY, user.getLocale());

		EventsProcessorUtil.process(
			PropsKeys.LOGIN_EVENTS_POST, PropsValues.LOGIN_EVENTS_POST,
			request, response);

		return userId;
	}

	protected void processGlobalShutdownEvents() throws Exception {
		EventsProcessorUtil.process(
			PropsKeys.GLOBAL_SHUTDOWN_EVENTS,
			PropsValues.GLOBAL_SHUTDOWN_EVENTS);

		super.destroy();
	}

	protected void processGlobalStartupEvents() throws Exception {
		EventsProcessorUtil.process(
			PropsKeys.GLOBAL_STARTUP_EVENTS, PropsValues.GLOBAL_STARTUP_EVENTS);
	}

	protected boolean processMaintenanceRequest(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		if (!MaintenanceUtil.isMaintaining()) {
			return false;
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(
			"/html/portal/maintenance.jsp");

		requestDispatcher.include(request, response);

		return true;
	}

	protected void processServicePost(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_POST,
				PropsValues.SERVLET_SERVICE_EVENTS_POST, request, response);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		response.addHeader(
			_LIFERAY_PORTAL_REQUEST_HEADER, ReleaseInfo.getReleaseInfo());

		ThreadLocalCacheManager.clearAll(Lifecycle.REQUEST);
	}

	protected boolean processServicePre(
			HttpServletRequest request, HttpServletResponse response,
			long userId)
		throws IOException, ServletException {

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
				PropsValues.SERVLET_SERVICE_EVENTS_PRE, request, response);
		}
		catch (Exception e) {
			Throwable cause = e.getCause();

			if (cause instanceof NoSuchLayoutException) {
				sendError(
					HttpServletResponse.SC_NOT_FOUND, cause, request, response);

				return true;
			}
			else if (cause instanceof PrincipalException) {
				processServicePrePrincipalException(
					cause, userId, request, response);

				return true;
			}

			_log.error(e, e);

			request.setAttribute(PageContext.EXCEPTION, e);

			ServletContext servletContext = getServletContext();

			StrutsUtil.forward(
				PropsValues.SERVLET_SERVICE_EVENTS_PRE_ERROR_PAGE,
				servletContext, request, response);

			return true;
		}

		return false;
	}

	protected void processServicePrePrincipalException(
			Throwable t, long userId, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		if (userId > 0) {
			sendError(
				HttpServletResponse.SC_UNAUTHORIZED, t, request, response);

			return;
		}

		String redirect =
			request.getContextPath() + Portal.PATH_MAIN + "/portal/login";

		String currentURL = PortalUtil.getCurrentURL(request);

		redirect = HttpUtil.addParameter(redirect, "redirect", currentURL);

		long plid = ParamUtil.getLong(request, "p_l_id");

		if (plid > 0) {
			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				if (layout.getGroup().isStagingGroup()) {
					Group group = GroupLocalServiceUtil.getGroup(
						layout.getCompanyId(), GroupConstants.GUEST);

					plid = group.getDefaultPublicPlid();
				}
				else if (layout.isPrivateLayout()) {
					plid = LayoutLocalServiceUtil.getDefaultPlid(
						layout.getGroupId(), false);
				}

				redirect = HttpUtil.addParameter(redirect, "p_l_id", plid);
			}
			catch (Exception e) {
			}
		}

		response.sendRedirect(redirect);
	}

	protected boolean processShutdownRequest(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		if (!ShutdownUtil.isShutdown()) {
			return false;
		}

		response.setContentType(ContentTypes.TEXT_HTML_UTF8);

		String html = ContentUtil.get(
			"com/liferay/portal/dependencies/shutdown.html");

		response.getOutputStream().print(html);

		return true;
	}

	protected void processStartupEvents() throws Exception {
		StartupAction startupAction = new StartupAction();

		startupAction.run(null);
	}

	protected HttpServletRequest protectRequest(
		HttpServletRequest request, String remoteUser) {

		// WebSphere will not return the remote user unless you are
		// authenticated AND accessing a protected path. Other servers will
		// return the remote user for all threads associated with an
		// authenticated user. We use ProtectedServletRequest to ensure we get
		// similar behavior across all servers.

		return new ProtectedServletRequest(request, remoteUser);
	}

	protected void sendError(
			int status, Throwable t, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		DynamicServletRequest dynamicRequest = new DynamicServletRequest(
			request);

		// Reset p_l_id or there will be an infinite loop

		dynamicRequest.setParameter("p_l_id", StringPool.BLANK);

		PortalUtil.sendError(status, (Exception)t, dynamicRequest, response);
	}

	protected void setPortalPort(HttpServletRequest request) {
		PortalUtil.setPortalPort(request);
	}

	protected void setPrincipalName(long userId, String remoteUser) {
		if ((userId == 0) && (remoteUser == null)) {
			return;
		}

		String name = String.valueOf(userId);

		if (remoteUser != null) {
			name = remoteUser;
		}

		PrincipalThreadLocal.setName(name);
	}

	private static final String _LIFERAY_PORTAL_REQUEST_HEADER =
		"Liferay-Portal";

	private static Log _log = LogFactoryUtil.getLog(MainServlet.class);

}