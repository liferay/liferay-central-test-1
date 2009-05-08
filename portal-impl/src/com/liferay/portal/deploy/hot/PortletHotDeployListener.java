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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.apache.bridges.struts.LiferayServletContextProvider;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.job.Scheduler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.InvokerIndexer;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.servlet.ServletContextProvider;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.lar.InvokerPortletDataHandler;
import com.liferay.portal.lar.PortletDataHandler;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletCategory;
import com.liferay.portal.model.PortletFilter;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.poller.PollerProcessorUtil;
import com.liferay.portal.pop.POPServerUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.webdav.WebDAVStorage;
import com.liferay.portal.webdav.WebDAVUtil;
import com.liferay.portlet.ControlPanelEntry;
import com.liferay.portlet.CustomUserAttributes;
import com.liferay.portlet.PortletBagImpl;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.portlet.PortletContextBag;
import com.liferay.portlet.PortletContextBagPool;
import com.liferay.portlet.PortletFilterFactory;
import com.liferay.portlet.PortletInstanceFactoryUtil;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.portlet.PortletResourceBundles;
import com.liferay.portlet.PortletURLListenerFactory;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialRequestInterpreter;
import com.liferay.portlet.social.model.impl.SocialActivityInterpreterImpl;
import com.liferay.portlet.social.model.impl.SocialRequestInterpreterImpl;
import com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil;
import com.liferay.portlet.social.service.SocialRequestInterpreterLocalServiceUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletURLGenerationListener;
import javax.portlet.PreferencesValidator;

import javax.servlet.ServletContext;

import org.apache.portals.bridges.struts.StrutsPortlet;

/**
 * <a href="PortletHotDeployListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Ivica Cardic
 * @author Raymond Augé
 *
 */
public class PortletHotDeployListener extends BaseHotDeployListener {

	public void invokeDeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeDeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(
				event, "Error registering portlets for ", t);
		}
	}

	public void invokeUndeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeUndeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(
				event, "Error unregistering portlets for ", t);
		}
	}

	protected void destroyPortlet(Portlet portlet, Set<String> portletIds)
		throws Exception {

		PortletApp portletApp = portlet.getPortletApp();

		Set<PortletFilter> portletFilters = portletApp.getPortletFilters();

		for (PortletFilter portletFilter : portletFilters) {
			PortletFilterFactory.destroy(portletFilter);
		}

		Set<PortletURLListener> portletURLListeners =
			portletApp.getPortletURLListeners();

		for (PortletURLListener portletURLListener : portletURLListeners) {
			PortletURLListenerFactory.destroy(portletURLListener);
		}

		Indexer indexer = portlet.getIndexerInstance();

		if (indexer != null) {
			for (String className : indexer.getClassNames()) {
				IndexerRegistryUtil.unregister(className);
			}
		}

		Scheduler scheduler = portlet.getSchedulerInstance();

		if (scheduler != null) {
			scheduler.unschedule();
		}

		PollerProcessorUtil.deletePollerProcessor(portlet.getPortletId());

		POPServerUtil.deleteListener(portlet.getPopMessageListenerInstance());

		SocialActivityInterpreterLocalServiceUtil.deleteActivityInterpreter(
			portlet.getSocialActivityInterpreterInstance());

		SocialRequestInterpreterLocalServiceUtil.deleteRequestInterpreter(
			portlet.getSocialRequestInterpreterInstance());

		WebDAVUtil.deleteStorage(portlet.getWebDAVStorageInstance());

		PortletInstanceFactoryUtil.destroy(portlet);

		portletIds.add(portlet.getPortletId());
	}

	protected void doInvokeDeploy(HotDeployEvent event) throws Exception {

		// Servlet context

		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		// Company ids

		long[] companyIds = PortalInstances.getCompanyIds();

		// Initialize portlets

		String[] xmls = new String[] {
			HttpUtil.URLtoString(servletContext.getResource(
				"/WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_STANDARD)),
			HttpUtil.URLtoString(servletContext.getResource(
				"/WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_CUSTOM)),
			HttpUtil.URLtoString(servletContext.getResource(
				"/WEB-INF/liferay-portlet.xml")),
			HttpUtil.URLtoString(servletContext.getResource("/WEB-INF/web.xml"))
		};

		if (xmls[0] == null) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Registering portlets for " + servletContextName);
		}

		List<Portlet> portlets = PortletLocalServiceUtil.initWAR(
			servletContextName, servletContext, xmls, event.getPluginPackage());

		// Class loader

		ClassLoader portletClassLoader = event.getContextClassLoader();

		servletContext.setAttribute(
			PortletServlet.PORTLET_CLASS_LOADER, portletClassLoader);

		// Portlet context wrapper

		_portletAppInitialized = false;
		_strutsBridges = false;

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			initPortlet(portlet, servletContext, portletClassLoader, itr);
		}

		// Struts bridges

		if (!_strutsBridges) {
			_strutsBridges = GetterUtil.getBoolean(
				servletContext.getInitParameter(
					"struts-bridges-context-provider"));
		}

		if (_strutsBridges) {
			servletContext.setAttribute(
				ServletContextProvider.STRUTS_BRIDGES_CONTEXT_PROVIDER,
				new LiferayServletContextProvider());
		}

		// Portlet display

		String xml = HttpUtil.URLtoString(servletContext.getResource(
			"/WEB-INF/liferay-display.xml"));

		PortletCategory newPortletCategory =
			PortletLocalServiceUtil.getWARDisplay(servletContextName, xml);

		for (int i = 0; i < companyIds.length; i++) {
			long companyId = companyIds[i];

			PortletCategory portletCategory =
				(PortletCategory)WebAppPool.get(
					String.valueOf(companyId), WebKeys.PORTLET_CATEGORY);

			if (portletCategory != null) {
				portletCategory.merge(newPortletCategory);
			}
			else {
				_log.error(
					"Unable to register portlet for company " + companyId +
						" because it does not exist");
			}
		}

		// Portlet properties

		processPortletProperties(servletContextName, portletClassLoader);

		// Resource actions and codes

		itr = portlets.iterator();

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

			List<String> portletActions =
				ResourceActionsUtil.getPortletResourceActions(
					portlet.getPortletId());

			ResourceActionLocalServiceUtil.checkResourceActions(
				portlet.getPortletId(), portletActions);

			for (String modelName : modelNames) {
				List<String> modelActions =
					ResourceActionsUtil.getModelResourceActions(modelName);

				ResourceActionLocalServiceUtil.checkResourceActions(
					modelName, modelActions);
			}
		}

		// ClpMessageListener

		registerClpMessageListeners(servletContext, portletClassLoader);

		// Variables

		_vars.put(
			servletContextName,
			new ObjectValuePair<long[], List<Portlet>>(
				companyIds, portlets));

		if (_log.isInfoEnabled()) {
			if (portlets.size() == 1) {
				_log.info(
					"1 portlet for " + servletContextName +
						" is available for use");
			}
			else {
				_log.info(
					portlets.size() + " portlets for " + servletContextName +
						" are available for use");
			}
		}
	}

	protected void doInvokeUndeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking undeploy for " + servletContextName);
		}

		ObjectValuePair<long[], List<Portlet>> ovp =
			_vars.remove(servletContextName);

		if (ovp == null) {
			return;
		}

		long[] companyIds = ovp.getKey();
		List<Portlet> portlets = ovp.getValue();

		Set<String> portletIds = new HashSet<String>();

		if (portlets != null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unregistering portlets for " + servletContextName);
			}

			Iterator<Portlet> itr = portlets.iterator();

			while (itr.hasNext()) {
				Portlet portlet = itr.next();

				destroyPortlet(portlet, portletIds);
			}
		}

		if (portletIds.size() > 0) {
			for (int i = 0; i < companyIds.length; i++) {
				long companyId = companyIds[i];

				PortletCategory portletCategory =
					(PortletCategory)WebAppPool.get(
						String.valueOf(companyId), WebKeys.PORTLET_CATEGORY);

				portletCategory.separate(portletIds);
			}
		}

		PortletResourceBundles.remove(servletContextName);

		unregisterClpMessageListeners(servletContext);

		if (_log.isInfoEnabled()) {
			if (portlets.size() == 1) {
				_log.info(
					"1 portlet for " + servletContextName +
						" was unregistered");
			}
			else {
				_log.info(
					portlets.size() + " portlets for " + servletContextName +
						" was unregistered");
			}
		}
	}

	protected void initPortlet(
			Portlet portlet, ServletContext servletContext,
			ClassLoader portletClassLoader, Iterator<Portlet> portletsItr)
		throws Exception {

		Class<?> portletClass = null;

		try {
			portletClass = portletClassLoader.loadClass(
				portlet.getPortletClass());
		}
		catch (Throwable e) {
			_log.error(e, e);

			portletsItr.remove();

			PortletLocalServiceUtil.destroyPortlet(portlet);

			return;
		}

		javax.portlet.Portlet portletInstance =
			(javax.portlet.Portlet)portletClass.newInstance();

		if (ClassUtil.isSubclass(portletClass, StrutsPortlet.class.getName())) {
			_strutsBridges = true;
		}

		ConfigurationAction configurationActionInstance = null;

		if (Validator.isNotNull(portlet.getConfigurationActionClass())) {
			configurationActionInstance =
				(ConfigurationAction)portletClassLoader.loadClass(
					portlet.getConfigurationActionClass()).newInstance();
		}

		Indexer indexerInstance = null;

		if (Validator.isNotNull(portlet.getIndexerClass())) {
			indexerInstance = (Indexer)portletClassLoader.loadClass(
				portlet.getIndexerClass()).newInstance();

			indexerInstance = new InvokerIndexer(
				indexerInstance, portletClassLoader);

			for (String className : indexerInstance.getClassNames()) {
				IndexerRegistryUtil.register(className, indexerInstance);
			}
		}

		OpenSearch openSearchInstance = null;

		if (Validator.isNotNull(portlet.getOpenSearchClass())) {
			openSearchInstance = (OpenSearch)portletClassLoader.loadClass(
				portlet.getOpenSearchClass()).newInstance();
		}

		Scheduler schedulerInstance = null;

		if (PropsValues.SCHEDULER_ENABLED &&
			Validator.isNotNull(portlet.getSchedulerClass())) {

			schedulerInstance = (Scheduler)portletClassLoader.loadClass(
				portlet.getSchedulerClass()).newInstance();

			schedulerInstance.schedule();
		}

		FriendlyURLMapper friendlyURLMapperInstance = null;

		if (Validator.isNotNull(portlet.getFriendlyURLMapperClass())) {
			friendlyURLMapperInstance =
				(FriendlyURLMapper)portletClassLoader.loadClass(
					portlet.getFriendlyURLMapperClass()).newInstance();
		}

		URLEncoder urlEncoderInstance = null;

		if (Validator.isNotNull(portlet.getURLEncoderClass())) {
			urlEncoderInstance = (URLEncoder)portletClassLoader.loadClass(
				portlet.getURLEncoderClass()).newInstance();
		}

		PortletDataHandler portletDataHandlerInstance = null;

		if (Validator.isNotNull(portlet.getPortletDataHandlerClass())) {
			portletDataHandlerInstance =
				(PortletDataHandler)portletClassLoader.loadClass(
					portlet.getPortletDataHandlerClass()).newInstance();

			portletDataHandlerInstance = new InvokerPortletDataHandler(
				portletDataHandlerInstance, portletClassLoader);
		}

		PortletLayoutListener portletLayoutListenerInstance = null;

		if (Validator.isNotNull(portlet.getPortletLayoutListenerClass())) {
			portletLayoutListenerInstance =
				(PortletLayoutListener)portletClassLoader.loadClass(
					portlet.getPortletLayoutListenerClass()).newInstance();
		}

		PollerProcessor pollerProcessorInstance = null;

		if (Validator.isNotNull(portlet.getPollerProcessorClass())) {
			pollerProcessorInstance =
				(PollerProcessor)portletClassLoader.loadClass(
					portlet.getPollerProcessorClass()).newInstance();

			PollerProcessorUtil.addPollerProcessor(
				portlet.getPortletId(), pollerProcessorInstance);
		}

		MessageListener popMessageListenerInstance = null;

		if (Validator.isNotNull(portlet.getPopMessageListenerClass())) {
			popMessageListenerInstance =
				(MessageListener)portletClassLoader.loadClass(
					portlet.getPopMessageListenerClass()).newInstance();

			POPServerUtil.addListener(popMessageListenerInstance);
		}

		SocialActivityInterpreter socialActivityInterpreterInstance = null;

		if (Validator.isNotNull(portlet.getSocialActivityInterpreterClass())) {
			socialActivityInterpreterInstance =
				(SocialActivityInterpreter)portletClassLoader.loadClass(
					portlet.getSocialActivityInterpreterClass()).newInstance();

			socialActivityInterpreterInstance =
				new SocialActivityInterpreterImpl(
					portlet.getPortletId(), socialActivityInterpreterInstance);

			SocialActivityInterpreterLocalServiceUtil.addActivityInterpreter(
				socialActivityInterpreterInstance);
		}

		SocialRequestInterpreter socialRequestInterpreterInstance = null;

		if (Validator.isNotNull(portlet.getSocialRequestInterpreterClass())) {
			socialRequestInterpreterInstance =
				(SocialRequestInterpreter)portletClassLoader.loadClass(
					portlet.getSocialRequestInterpreterClass()).newInstance();

			socialRequestInterpreterInstance = new SocialRequestInterpreterImpl(
				portlet.getPortletId(), socialRequestInterpreterInstance);

			SocialRequestInterpreterLocalServiceUtil.addRequestInterpreter(
				socialRequestInterpreterInstance);
		}

		WebDAVStorage webDAVStorageInstance = null;

		if (Validator.isNotNull(portlet.getWebDAVStorageClass())) {
			webDAVStorageInstance = (WebDAVStorage)portletClassLoader.loadClass(
				portlet.getWebDAVStorageClass()).newInstance();

			webDAVStorageInstance.setToken(portlet.getWebDAVStorageToken());

			WebDAVUtil.addStorage(webDAVStorageInstance);
		}

		ControlPanelEntry controlPanelEntryInstance = null;

		if (Validator.isNotNull(portlet.getControlPanelEntryClass())) {
			controlPanelEntryInstance =
				(ControlPanelEntry)portletClassLoader.loadClass(
					portlet.getControlPanelEntryClass()).newInstance();
		}

		PreferencesValidator preferencesValidatorInstance = null;

		if (Validator.isNotNull(portlet.getPreferencesValidator())) {
			preferencesValidatorInstance =
				(PreferencesValidator)portletClassLoader.loadClass(
					portlet.getPreferencesValidator()).newInstance();

			try {
				if (PropsValues.PREFERENCE_VALIDATE_ON_STARTUP) {
					preferencesValidatorInstance.validate(
						PortletPreferencesSerializer.fromDefaultXML(
							portlet.getDefaultPreferences()));
				}
			}
			catch (Exception e) {
				_log.warn(
					"Portlet with the name " + portlet.getPortletId() +
						" does not have valid default preferences");
			}
		}

		Map<String, ResourceBundle> resourceBundles = null;

		if (Validator.isNotNull(portlet.getResourceBundle())) {
			resourceBundles = new HashMap<String, ResourceBundle>();

			initResourceBundle(
				resourceBundles, portlet, portletClassLoader,
				LocaleUtil.getDefault());

			Iterator<String> supportLocalesItr =
				portlet.getSupportedLocales().iterator();

			while (supportLocalesItr.hasNext()) {
				String supportedLocale = supportLocalesItr.next();

				Locale locale = LocaleUtil.fromLanguageId(supportedLocale);

				initResourceBundle(
					resourceBundles, portlet, portletClassLoader, locale);
			}
		}

		PortletBag portletBag = new PortletBagImpl(
			portlet.getPortletId(), servletContext, portletInstance,
			configurationActionInstance, indexerInstance, openSearchInstance,
			schedulerInstance, friendlyURLMapperInstance, urlEncoderInstance,
			portletDataHandlerInstance, portletLayoutListenerInstance,
			pollerProcessorInstance, popMessageListenerInstance,
			socialActivityInterpreterInstance, socialRequestInterpreterInstance,
			webDAVStorageInstance, controlPanelEntryInstance,
			preferencesValidatorInstance, resourceBundles);

		PortletBagPool.put(portlet.getPortletId(), portletBag);

		if (!_portletAppInitialized) {
			initPortletApp(portlet, servletContext, portletClassLoader);

			_portletAppInitialized = true;
		}

		try {
			PortletInstanceFactoryUtil.create(portlet, servletContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void initPortletApp(
			Portlet portlet, ServletContext servletContext,
			ClassLoader portletClassLoader)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		PortletConfig portletConfig = PortletConfigFactory.create(
			portlet, servletContext);

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletContextBag portletContextBag = new PortletContextBag(
			servletContextName);

		PortletContextBagPool.put(servletContextName, portletContextBag);

		PortletApp portletApp = portlet.getPortletApp();

		Map<String, String> customUserAttributes =
			portletApp.getCustomUserAttributes();

		for (Map.Entry<String, String> entry :
				customUserAttributes.entrySet()) {

			String attrCustomClass = entry.getValue();

			CustomUserAttributes customUserAttributesInstance =
				(CustomUserAttributes)portletClassLoader.loadClass(
					attrCustomClass).newInstance();

			portletContextBag.getCustomUserAttributes().put(
				attrCustomClass, customUserAttributesInstance);
		}

		Set<PortletFilter> portletFilters = portletApp.getPortletFilters();

		for (PortletFilter portletFilter : portletFilters) {
			javax.portlet.filter.PortletFilter portletFilterInstance =
				(javax.portlet.filter.PortletFilter)
					portletClassLoader.loadClass(
						portletFilter.getFilterClass()).newInstance();

			portletContextBag.getPortletFilters().put(
				portletFilter.getFilterName(), portletFilterInstance);

			PortletFilterFactory.create(portletFilter, portletContext);
		}

		Set<PortletURLListener> portletURLListeners =
			portletApp.getPortletURLListeners();

		for (PortletURLListener portletURLListener : portletURLListeners) {
			PortletURLGenerationListener portletURLListenerInstance =
				(PortletURLGenerationListener)portletClassLoader.loadClass(
					portletURLListener.getListenerClass()).newInstance();

			portletContextBag.getPortletURLListeners().put(
				portletURLListener.getListenerClass(),
				portletURLListenerInstance);

			PortletURLListenerFactory.create(portletURLListener);
		}
	}

	protected void initResourceBundle(
		Map<String, ResourceBundle> resourceBundles, Portlet portlet,
		ClassLoader portletClassLoader, Locale locale) {

		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(
				portlet.getResourceBundle(), locale, portletClassLoader);

			resourceBundles.put(
				LocaleUtil.toLanguageId(locale), resourceBundle);
		}
		catch (MissingResourceException mre) {
			_log.warn(mre.getMessage());
		}
	}

	protected void processPortletProperties(
			String servletContextName, ClassLoader portletClassLoader)
		throws Exception {

		Configuration portletPropertiesConfiguration = null;

		try {
			portletPropertiesConfiguration =
				ConfigurationFactoryUtil.getConfiguration(
					portletClassLoader, "portlet");
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read portlet.properties");
			}

			return;
		}

		Properties portletProperties =
			portletPropertiesConfiguration.getProperties();

		if (portletProperties.size() == 0) {
			return;
		}

		String languageBundleName = portletProperties.getProperty(
			"language.bundle");

		if (Validator.isNotNull(languageBundleName)) {
			Locale[] locales = LanguageUtil.getAvailableLocales();

			for (int i = 0; i < locales.length; i++) {
				ResourceBundle bundle = ResourceBundle.getBundle(
					languageBundleName, locales[i], portletClassLoader);

				PortletResourceBundles.put(
					servletContextName, LocaleUtil.toLanguageId(locales[i]),
					bundle);
			}
		}

		String[] resourceActionConfigs = StringUtil.split(
			portletProperties.getProperty("resource.actions.configs"));

		for (int i = 0; i < resourceActionConfigs.length; i++) {
			ResourceActionsUtil.read(
				servletContextName, portletClassLoader,
				resourceActionConfigs[i]);
		}
	}

	private static Log _log =
		 LogFactoryUtil.getLog(PortletHotDeployListener.class);

	private static Map<String, ObjectValuePair<long[], List<Portlet>>> _vars =
		new HashMap<String, ObjectValuePair<long[], List<Portlet>>>();

	private boolean _portletAppInitialized;
	private boolean _strutsBridges;

}