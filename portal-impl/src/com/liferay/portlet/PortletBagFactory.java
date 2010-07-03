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

package com.liferay.portlet;

import com.liferay.portal.dao.shard.ShardPollerProcessorWrapper;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.Route;
import com.liferay.portal.kernel.portlet.Router;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.poller.PollerProcessorUtil;
import com.liferay.portal.pop.POPServerUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.xmlrpc.XmlRpcServlet;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.expando.model.CustomAttributesDisplay;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialRequestInterpreter;
import com.liferay.portlet.social.model.impl.SocialActivityInterpreterImpl;
import com.liferay.portlet.social.model.impl.SocialRequestInterpreterImpl;
import com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil;
import com.liferay.portlet.social.service.SocialRequestInterpreterLocalServiceUtil;
import com.liferay.util.portlet.PortletProps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PreferencesValidator;

import javax.servlet.ServletContext;

/**
 * <a href="PortletBagFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Ivica Cardic
 * @author Raymond Augé
 */
public class PortletBagFactory {

	public PortletBag create(Portlet portlet) throws Exception {
		PortletApp portletApp = portlet.getPortletApp();

		if (!portletApp.isWARFile() && _warFile) {
			String contextPath = PortalUtil.getPathContext();

			_servletContext = ServletContextPool.get(contextPath);

			_classLoader = PortalClassLoaderUtil.getClassLoader();
		}

		Class<?> portletClass = null;

		try {
			portletClass = _classLoader.loadClass(portlet.getPortletClass());
		}
		catch (Throwable e) {
			_log.error(e, e);

			PortletLocalServiceUtil.destroyPortlet(portlet);

			return null;
		}

		javax.portlet.Portlet portletInstance =
			(javax.portlet.Portlet)portletClass.newInstance();

		ConfigurationAction configurationActionInstance =
			newConfigurationAction(portlet);

		Indexer indexerInstance = newIndexer(portlet);

		OpenSearch openSearchInstance = newOpenSearch(portlet);

		initSchedulers(portlet);

		FriendlyURLMapper friendlyURLMapperInstance = newFriendlyURLMapper(
			portlet);

		URLEncoder urlEncoderInstance = newURLEncoder(portlet);

		PortletDataHandler portletDataHandlerInstance = newPortletDataHandler(
			portlet);

		PortletLayoutListener portletLayoutListenerInstance =
			newPortletLayoutListener(portlet);

		PollerProcessor pollerProcessorInstance = newPollerProcessor(portlet);

		MessageListener popMessageListenerInstance = newPOPMessageListener(
			portlet);

		SocialActivityInterpreter socialActivityInterpreterInstance =
			initSocialActivityInterpreterInstance(portlet);

		SocialRequestInterpreter socialRequestInterpreterInstance = null;

		if (Validator.isNotNull(portlet.getSocialRequestInterpreterClass())) {
			socialRequestInterpreterInstance =
				(SocialRequestInterpreter)newInstance(
					SocialRequestInterpreter.class,
					portlet.getSocialRequestInterpreterClass());

			socialRequestInterpreterInstance = new SocialRequestInterpreterImpl(
				portlet.getPortletId(), socialRequestInterpreterInstance);

			SocialRequestInterpreterLocalServiceUtil.addRequestInterpreter(
				socialRequestInterpreterInstance);
		}

		WebDAVStorage webDAVStorageInstance = null;

		if (Validator.isNotNull(portlet.getWebDAVStorageClass())) {
			webDAVStorageInstance = (WebDAVStorage)newInstance(
				WebDAVStorage.class, portlet.getWebDAVStorageClass());

			webDAVStorageInstance.setToken(portlet.getWebDAVStorageToken());

			WebDAVUtil.addStorage(webDAVStorageInstance);
		}

		Method xmlRpcMethodInstance = null;

		if (Validator.isNotNull(portlet.getXmlRpcMethodClass())) {
			xmlRpcMethodInstance = (Method)newInstance(
				Method.class, portlet.getXmlRpcMethodClass());

			XmlRpcServlet.registerMethod(xmlRpcMethodInstance);
		}

		ControlPanelEntry controlPanelEntryInstance = null;

		if (Validator.isNotNull(portlet.getControlPanelEntryClass())) {
			controlPanelEntryInstance = (ControlPanelEntry)newInstance(
				ControlPanelEntry.class, portlet.getControlPanelEntryClass());
		}

		List<AssetRendererFactory> assetRendererFactoryInstances =
			newAssetRendererFactoryInstances(portlet);

		List<CustomAttributesDisplay> customAttributesDisplayInstances =
			new ArrayList<CustomAttributesDisplay>();

		for (String customAttributesDisplayClass :
				portlet.getCustomAttributesDisplayClasses()) {

			CustomAttributesDisplay customAttributesDisplayInstance =
				(CustomAttributesDisplay)newInstance(
					CustomAttributesDisplay.class,
					customAttributesDisplayClass);

			customAttributesDisplayInstance.setClassNameId(
				PortalUtil.getClassNameId(
					customAttributesDisplayInstance.getClassName()));
			customAttributesDisplayInstance.setPortletId(
				portlet.getPortletId());

			customAttributesDisplayInstances.add(
				customAttributesDisplayInstance);
		}

		List<WorkflowHandler> workflowHandlerInstances =
			new ArrayList<WorkflowHandler>();

		for (String workflowHandlerClass :
				portlet.getWorkflowHandlerClasses()) {

			WorkflowHandler workflowHandlerInstance =
				(WorkflowHandler)newInstance(
					WorkflowHandler.class, workflowHandlerClass);

			workflowHandlerInstances.add(workflowHandlerInstance);

			WorkflowHandlerRegistryUtil.register(workflowHandlerInstance);
		}

		PreferencesValidator preferencesValidatorInstance = null;

		if (Validator.isNotNull(portlet.getPreferencesValidator())) {
			preferencesValidatorInstance = (PreferencesValidator)newInstance(
				PreferencesValidator.class, portlet.getPreferencesValidator());

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
				resourceBundles, portlet, LocaleUtil.getDefault());

			Set<String> supportedLocales = portlet.getSupportedLocales();

			if (supportedLocales.isEmpty()) {
				supportedLocales = SetUtil.fromArray(PropsValues.LOCALES);
			}

			for (String supportedLocale : supportedLocales) {
				Locale locale = LocaleUtil.fromLanguageId(supportedLocale);

				initResourceBundle(resourceBundles, portlet, locale);
			}
		}

		PortletBag portletBag = new PortletBagImpl(
			portlet.getPortletId(), _servletContext, portletInstance,
			configurationActionInstance, indexerInstance, openSearchInstance,
			friendlyURLMapperInstance, urlEncoderInstance,
			portletDataHandlerInstance, portletLayoutListenerInstance,
			pollerProcessorInstance, popMessageListenerInstance,
			socialActivityInterpreterInstance, socialRequestInterpreterInstance,
			webDAVStorageInstance, xmlRpcMethodInstance,
			controlPanelEntryInstance, assetRendererFactoryInstances,
			customAttributesDisplayInstances, workflowHandlerInstances,
			preferencesValidatorInstance, resourceBundles);

		PortletBagPool.put(portlet.getPortletId(), portletBag);

		try {
			PortletInstanceFactoryUtil.create(portlet, _servletContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return portletBag;
	}

	public void setClassLoader(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	public void setWARFile(boolean warFile) {
		_warFile = warFile;
	}

	protected void initResourceBundle(
		Map<String, ResourceBundle> resourceBundles, Portlet portlet,
		Locale locale) {

		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(
				portlet.getResourceBundle(), locale, _classLoader);

			resourceBundles.put(
				LocaleUtil.toLanguageId(locale), resourceBundle);
		}
		catch (MissingResourceException mre) {
			_log.warn(mre.getMessage());
		}
	}

	protected void initScheduler(SchedulerEntry schedulerEntry)
		throws Exception {

		String propertyKey = schedulerEntry.getPropertyKey();

		if (Validator.isNotNull(propertyKey)) {
			String triggerValue = null;

			if (_warFile) {
				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader =
					currentThread.getContextClassLoader();

				try {
					currentThread.setContextClassLoader(_classLoader);

					MethodWrapper methodWrapper = new MethodWrapper(
						PortletProps.class.getName(), "get", propertyKey);

					triggerValue = (String)MethodInvoker.invoke(
						methodWrapper, false);
				}
				finally {
					currentThread.setContextClassLoader(contextClassLoader);
				}
			}
			else {
				triggerValue = PrefsPropsUtil.getString(propertyKey);
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Scheduler property key " + propertyKey +
						" has trigger value " + triggerValue);
			}

			if (Validator.isNull(triggerValue)) {
				throw new SchedulerException(
					"Property key " + propertyKey + " requires a value");
			}

			schedulerEntry.setTriggerValue(triggerValue);
		}

		SchedulerEngineUtil.schedule(schedulerEntry, _classLoader);
	}

	protected void initSchedulers(Portlet portlet) throws Exception {
		if (!PropsValues.SCHEDULER_ENABLED){
			return;
		}

		List<SchedulerEntry> schedulerEntries = portlet.getSchedulerEntries();

		if ((schedulerEntries == null) || schedulerEntries.isEmpty()) {
			return;
		}

		for (SchedulerEntry schedulerEntry : schedulerEntries) {
			initScheduler(schedulerEntry);
		}
	}

	protected SocialActivityInterpreter initSocialActivityInterpreterInstance(
			Portlet portlet)
		throws Exception {

		if (Validator.isNull(portlet.getSocialActivityInterpreterClass())) {
			return null;
		}

		SocialActivityInterpreter socialActivityInterpreterInstance =
			(SocialActivityInterpreter)newInstance(
				SocialActivityInterpreter.class,
				portlet.getSocialActivityInterpreterClass());

		socialActivityInterpreterInstance =
			new SocialActivityInterpreterImpl(
				portlet.getPortletId(), socialActivityInterpreterInstance);

		SocialActivityInterpreterLocalServiceUtil.addActivityInterpreter(
			socialActivityInterpreterInstance);

		return socialActivityInterpreterInstance;
	}

	protected AssetRendererFactory newAssetRendererFactoryInstance(
			Portlet portlet, String assetRendererFactoryClass)
		throws Exception {

		AssetRendererFactory assetRendererFactoryInstance =
			(AssetRendererFactory)newInstance(
				AssetRendererFactory.class, assetRendererFactoryClass);

		assetRendererFactoryInstance.setClassNameId(
			PortalUtil.getClassNameId(
				assetRendererFactoryInstance.getClassName()));
		assetRendererFactoryInstance.setPortletId(portlet.getPortletId());

		AssetRendererFactoryRegistryUtil.register(assetRendererFactoryInstance);

		return assetRendererFactoryInstance;
	}

	protected List<AssetRendererFactory> newAssetRendererFactoryInstances(
			Portlet portlet)
		throws Exception {

		List<AssetRendererFactory> assetRendererFactoryInstances =
			new ArrayList<AssetRendererFactory>();

		for (String assetRendererFactoryClass :
				portlet.getAssetRendererFactoryClasses()) {

			AssetRendererFactory assetRendererFactoryInstance =
				newAssetRendererFactoryInstance(
					portlet, assetRendererFactoryClass);

			assetRendererFactoryInstances.add(assetRendererFactoryInstance);
		}

		return assetRendererFactoryInstances;
	}

	protected ConfigurationAction newConfigurationAction(Portlet portlet)
		throws Exception {

		if (Validator.isNull(portlet.getConfigurationActionClass())) {
			return null;
		}

		return (ConfigurationAction)newInstance(
			ConfigurationAction.class, portlet.getConfigurationActionClass());
	}

	protected FriendlyURLMapper newFriendlyURLMapper(Portlet portlet)
		throws Exception {

		if (Validator.isNull(portlet.getFriendlyURLMapperClass())) {
			return null;
		}

		FriendlyURLMapper friendlyURLMapper = (FriendlyURLMapper)newInstance(
			FriendlyURLMapper.class, portlet.getFriendlyURLMapperClass());

		friendlyURLMapper.setMapping(portlet.getFriendlyURLMapping());
		friendlyURLMapper.setPortletId(portlet.getPortletId());
		friendlyURLMapper.setPortletInstanceable(portlet.isInstanceable());

		Router router = newFriendlyURLRouter(portlet);

		friendlyURLMapper.setRouter(router);

		return friendlyURLMapper;
	}

	protected Router newFriendlyURLRouter(Portlet portlet) throws Exception {
		if (Validator.isNull(portlet.getFriendlyURLRoutes())) {
			return null;
		}

		Router router = new RouterImpl();

		String xml = StringUtil.read(
			_classLoader, portlet.getFriendlyURLRoutes());

		Document document = SAXReaderUtil.read(xml, true);

		Element rootElement = document.getRootElement();

		for (Element routeElement : rootElement.elements("route")) {
			String pattern = routeElement.elementText("pattern");

			Route route = router.addRoute(pattern);

			for (Element defaultParameterElement :
					routeElement.elements("default-parameter")) {

				String name = defaultParameterElement.attributeValue("name");
				String value = defaultParameterElement.getText();

				route.addDefaultParameter(name, value);
			}

			for (Element generatedParameterElement :
					routeElement.elements("generated-parameter")) {

				String name = generatedParameterElement.attributeValue("name");
				String value = generatedParameterElement.getText();

				route.addGeneratedParameter(name, value);
			}

			for (Element ignoredParameterElement :
					routeElement.elements("ignored-parameter")) {

				String name = ignoredParameterElement.attributeValue("name");

				route.addIgnoredParameter(name);
			}

			for (Element overriddenParameterElement :
					routeElement.elements("overridden-parameter")) {

				String name = overriddenParameterElement.attributeValue("name");
				String value = overriddenParameterElement.getText();

				route.addOverriddenParameter(name, value);
			}
		}

		return router;
	}

	protected Indexer newIndexer(Portlet portlet) throws Exception {
		if (Validator.isNull(portlet.getIndexerClass())) {
			return null;
		}

		Indexer indexerInstance = (Indexer)newInstance(
			Indexer.class, portlet.getIndexerClass());

		IndexerRegistryUtil.register(indexerInstance);

		return indexerInstance;
	}

	protected Object newInstance(Class<?> interfaceClass, String implClassName)
		throws Exception {

		return newInstance(new Class[] {interfaceClass}, implClassName);
	}

	protected Object newInstance(
			Class<?>[] interfaceClasses, String implClassName)
		throws Exception {

		if (_warFile) {
			return ProxyFactory.newInstance(
				_classLoader, interfaceClasses, implClassName);
		}
		else {
			Class<?> classObj = _classLoader.loadClass(implClassName);

			return classObj.newInstance();
		}
	}

	protected OpenSearch newOpenSearch(Portlet portlet) throws Exception {
		if (Validator.isNull(portlet.getOpenSearchClass())) {
			return null;
		}

		return (OpenSearch)newInstance(
			OpenSearch.class, portlet.getOpenSearchClass());
	}

	protected PollerProcessor newPollerProcessor(Portlet portlet)
		throws Exception {

		if (Validator.isNull(portlet.getPollerProcessorClass())) {
			return null;
		}

		PollerProcessor pollerProcessorInstance = (PollerProcessor)newInstance(
			PollerProcessor.class, portlet.getPollerProcessorClass());

		PollerProcessorUtil.addPollerProcessor(
			portlet.getPortletId(),
			new ShardPollerProcessorWrapper(pollerProcessorInstance));

		return pollerProcessorInstance;
	}

	protected MessageListener newPOPMessageListener(Portlet portlet)
		throws Exception {

		if (Validator.isNull(portlet.getPopMessageListenerClass())) {
			return null;
		}

		MessageListener popMessageListenerInstance =
			(MessageListener)newInstance(
				MessageListener.class, portlet.getPopMessageListenerClass());

		POPServerUtil.addListener(popMessageListenerInstance);

		return popMessageListenerInstance;
	}

	protected PortletDataHandler newPortletDataHandler(Portlet portlet)
		throws Exception {

		if (Validator.isNull(portlet.getPortletDataHandlerClass())) {
			return null;
		}

		return (PortletDataHandler)newInstance(
			PortletDataHandler.class, portlet.getPortletDataHandlerClass());
	}

	protected PortletLayoutListener newPortletLayoutListener(Portlet portlet)
		throws Exception {

		if (Validator.isNull(portlet.getPortletLayoutListenerClass())) {
			return null;
		}

		return (PortletLayoutListener)newInstance(
			PortletLayoutListener.class,
			portlet.getPortletLayoutListenerClass());
	}

	protected URLEncoder newURLEncoder(Portlet portlet) throws Exception {
		if (Validator.isNull(portlet.getURLEncoderClass())) {
			return null;
		}

		return (URLEncoder)newInstance(
			URLEncoder.class, portlet.getURLEncoderClass());
	}

	private static Log _log = LogFactoryUtil.getLog(PortletBagFactory.class);

	private ClassLoader _classLoader;
	private ServletContext _servletContext;
	private boolean _warFile;

}