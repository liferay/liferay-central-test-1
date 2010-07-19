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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.captcha.CaptchaWrapper;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.deploy.auto.AutoDeployDir;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.AutoDeployUtil;
import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.deploy.hot.HotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.InvokerAction;
import com.liferay.portal.kernel.events.InvokerSessionAction;
import com.liferay.portal.kernel.events.InvokerSimpleAction;
import com.liferay.portal.kernel.events.SessionAction;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.sanitizer.SanitizerWrapper;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Release;
import com.liferay.portal.security.auth.AuthFailure;
import com.liferay.portal.security.auth.AuthPipeline;
import com.liferay.portal.security.auth.AuthToken;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.security.auth.AuthTokenWrapper;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.security.auth.AutoLogin;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.EmailAddressGenerator;
import com.liferay.portal.security.auth.EmailAddressGeneratorFactory;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.security.auth.FullNameValidator;
import com.liferay.portal.security.auth.FullNameValidatorFactory;
import com.liferay.portal.security.auth.ScreenNameGenerator;
import com.liferay.portal.security.auth.ScreenNameGeneratorFactory;
import com.liferay.portal.security.auth.ScreenNameValidator;
import com.liferay.portal.security.auth.ScreenNameValidatorFactory;
import com.liferay.portal.security.ldap.AttributesTransformer;
import com.liferay.portal.security.ldap.AttributesTransformerFactory;
import com.liferay.portal.service.ReleaseLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.servlet.filters.autologin.AutoLoginFilter;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.upgrade.UpgradeProcessUtil;
import com.liferay.portal.util.JavaScriptBundleUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.ControlPanelEntry;
import com.liferay.portlet.DefaultControlPanelEntryFactory;
import com.liferay.util.UniqueList;
import com.liferay.util.log4j.Log4JUtil;

import java.io.File;
import java.io.InputStream;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.target.SingletonTargetSource;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Wesley Gong
 */
public class HookHotDeployListener
	extends BaseHotDeployListener implements PropsKeys {

	public static String[] SUPPORTED_PROPERTIES = {
		"admin.default.group.names",
		"admin.default.role.names",
		"admin.default.user.group.names",
		"auth.forward.by.last.path",
		"auto.deploy.listeners",
		"application.startup.events",
		"auth.failure",
		"auth.max.failures",
		"auth.token.impl",
		"auth.pipeline.post",
		"auth.pipeline.pre",
		"auto.login.hooks",
		"captcha.check.portal.create_account",
		"captcha.engine.impl",
		"control.panel.entry.class.default",
		"convert.processes",
		"default.landing.page.path",
		"dl.hook.impl",
		"dl.webdav.hold.lock",
		"dl.webdav.save.to.single.version",
		"field.enable.com.liferay.portal.model.Contact.birthday",
		"field.enable.com.liferay.portal.model.Contact.male",
		"field.enable.com.liferay.portal.model.Organization.status",
		"hot.deploy.listeners",
		"image.hook.impl",
		"javascript.fast.load",
		"layout.static.portlets.all",
		"layout.template.cache.enabled",
		"layout.types",
		"layout.user.private.layouts.auto.create",
		"layout.user.private.layouts.enabled",
		"layout.user.private.layouts.modifiable",
		"layout.user.public.layouts.auto.create",
		"layout.user.public.layouts.enabled",
		"layout.user.public.layouts.modifiable",
		"ldap.attrs.transformer.impl",
		"login.create.account.allow.custom.password",
		"login.events.post",
		"login.events.pre",
		"logout.events.post",
		"logout.events.pre",
		"mail.hook.impl",
		"my.places.show.community.private.sites.with.no.layouts",
		"my.places.show.community.public.sites.with.no.layouts",
		"my.places.show.organization.private.sites.with.no.layouts",
		"my.places.show.organization.public.sites.with.no.layouts",
		"my.places.show.user.private.sites.with.no.layouts",
		"my.places.show.user.public.sites.with.no.layouts",
		"passwords.passwordpolicytoolkit.generator",
		"passwords.passwordpolicytoolkit.static",
		"portlet.add.default.resource.check.enabled",
		"sanitizer.impl",
		"servlet.session.create.events",
		"servlet.session.destroy.events",
		"servlet.service.events.post",
		"servlet.service.events.pre",
		"session.phishing.protected.attributes",
		"terms.of.use.required",
		"theme.css.fast.load",
		"theme.images.fast.load",
		"theme.loader.new.theme.id.on.import",
		"theme.portlet.decorate.default",
		"theme.portlet.sharing.default",
		"theme.shortcut.icon",
		"upgrade.processes",
		"users.email.address.generator",
		"users.email.address.required",
		"users.full.name.generator",
		"users.full.name.validator",
		"users.screen.name.always.autogenerate",
		"users.screen.name.generator",
		"users.screen.name.validator",
		"value.object.listener.*"
	};

	public HookHotDeployListener() {
		for (String key : _PROPS_VALUES_STRING_ARRAY) {
			_stringArraysContainerMap.put(key, new StringArraysContainer(key));
		}
	}

	public void invokeDeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeDeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(event, "Error registering hook for ", t);
		}
	}

	public void invokeUndeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeUndeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(event, "Error unregistering hook for ", t);
		}
	}

	protected boolean containsKey(Properties portalProperties, String key) {
		if (_log.isDebugEnabled()) {
			return true;
		}
		else {
			return portalProperties.containsKey(key);
		}
	}

	protected void destroyCustomJspBag(CustomJspBag customJspBag) {
		String customJspDir = customJspBag.getCustomJspDir();
		List<String> customJsps = customJspBag.getCustomJsps();
		//String timestamp = customJspBag.getTimestamp();

		String portalWebDir = PortalUtil.getPortalWebDir();

		for (String customJsp : customJsps) {
			int pos = customJsp.indexOf(customJspDir);

			String portalJsp = customJsp.substring(
				pos + customJspDir.length(), customJsp.length());

			File portalJspFile = new File(portalWebDir + portalJsp);
			File portalJspBackupFile = getPortalJspBackupFile(portalJspFile);

			if (portalJspBackupFile.exists()) {
				FileUtil.copyFile(portalJspBackupFile, portalJspFile);

				portalJspBackupFile.delete();
			}
			else if (portalJspFile.exists()) {
				portalJspFile.delete();
			}
		}
	}

	protected void destroyPortalProperties(
			String servletContextName, Properties portalProperties)
		throws Exception {

		PropsUtil.removeProperties(portalProperties);

		if (_log.isDebugEnabled() && portalProperties.containsKey(LOCALES)) {
			_log.debug(
				"Portlet locales " + portalProperties.getProperty(LOCALES));
			_log.debug("Original locales " + PropsUtil.get(LOCALES));
			_log.debug(
				"Original locales array length " +
					PropsUtil.getArray(LOCALES).length);
		}

		resetPortalProperties(servletContextName, portalProperties, false);

		if (portalProperties.containsKey(PropsKeys.AUTH_TOKEN_IMPL)) {
			AuthTokenWrapper authTokenWrapper =
				(AuthTokenWrapper)AuthTokenUtil.getAuthToken();

			authTokenWrapper.setAuthToken(null);
		}

		if (portalProperties.containsKey(PropsKeys.CAPTCHA_ENGINE_IMPL)) {
			CaptchaWrapper captchaWrapper =
				(CaptchaWrapper)CaptchaUtil.getCaptcha();

			captchaWrapper.setCaptcha(null);
		}

		if (portalProperties.containsKey(
				PropsKeys.CONTROL_PANEL_DEFAULT_ENTRY_CLASS)) {

			DefaultControlPanelEntryFactory.setInstance(null);
		}

		if (portalProperties.containsKey(PropsKeys.DL_HOOK_IMPL)) {
			com.liferay.documentlibrary.util.HookFactory.setInstance(null);
		}

		if (portalProperties.containsKey(PropsKeys.IMAGE_HOOK_IMPL)) {
			com.liferay.portal.image.HookFactory.setInstance(null);
		}

		if (portalProperties.containsKey(
				PropsKeys.LDAP_ATTRS_TRANSFORMER_IMPL)) {

			AttributesTransformerFactory.setInstance(null);
		}

		if (portalProperties.containsKey(PropsKeys.MAIL_HOOK_IMPL)) {
			com.liferay.mail.util.HookFactory.setInstance(null);
		}

		if (portalProperties.containsKey(PropsKeys.SANITIZER_IMPL)) {
			SanitizerWrapper sanitizerWrapper =
				(SanitizerWrapper)SanitizerUtil.getSanitizer();

			sanitizerWrapper.setSanitizer(null);
		}

		if (portalProperties.containsKey(
				PropsKeys.USERS_EMAIL_ADDRESS_GENERATOR)) {

			EmailAddressGeneratorFactory.setInstance(null);
		}

		if (portalProperties.containsKey(PropsKeys.USERS_FULL_NAME_GENERATOR)) {
			FullNameGeneratorFactory.setInstance(null);
		}

		if (portalProperties.containsKey(PropsKeys.USERS_FULL_NAME_VALIDATOR)) {
			FullNameValidatorFactory.setInstance(null);
		}

		if (portalProperties.containsKey(
				PropsKeys.USERS_SCREEN_NAME_GENERATOR)) {

			ScreenNameGeneratorFactory.setInstance(null);
		}

		if (portalProperties.containsKey(
				PropsKeys.USERS_SCREEN_NAME_VALIDATOR)) {

			ScreenNameValidatorFactory.setInstance(null);
		}
	}

	protected void destroyServices(String servletContextName) throws Exception {
		List<ServiceBag> serviceBags =
			_servicesContainer.findByServletContextName(servletContextName);

		for (ServiceBag serviceBag : serviceBags) {
			Object serviceProxy = PortalBeanLocatorUtil.locate(
				serviceBag.getServiceType());

			AdvisedSupport advisedSupport = getAdvisedSupport(serviceProxy);

			TargetSource originalTargetSource = new SingletonTargetSource(
				serviceBag.getOriginalService());

			advisedSupport.setTargetSource(originalTargetSource);
		}

		_servicesContainer.removeByServletContextName(servletContextName);
	}

	protected void doInvokeDeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		String xml = HttpUtil.URLtoString(
			servletContext.getResource("/WEB-INF/liferay-hook.xml"));

		if (xml == null) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Registering hook for " + servletContextName);
		}

		_servletContextNames.add(servletContextName);

		ClassLoader portletClassLoader = event.getContextClassLoader();

		initLogger(portletClassLoader);

		Document doc = SAXReaderUtil.read(xml, true);

		Element root = doc.getRootElement();

		String portalPropertiesLocation = root.elementText("portal-properties");

		if (Validator.isNotNull(portalPropertiesLocation)) {
			Configuration portalPropertiesConfiguration = null;

			try {
				String name = portalPropertiesLocation;

				int pos = name.lastIndexOf(".properties");

				if (pos != -1) {
					name = name.substring(0, pos);
				}

				portalPropertiesConfiguration =
					ConfigurationFactoryUtil.getConfiguration(
						portletClassLoader, name);
			}
			catch (Exception e) {
				_log.error("Unable to read " + portalPropertiesLocation, e);
			}

			if (portalPropertiesConfiguration != null) {
				Properties portalProperties =
					portalPropertiesConfiguration.getProperties();

				if (portalProperties.size() > 0) {
					_portalPropertiesMap.put(
						servletContextName, portalProperties);

					// Initialize properties, auto logins, model listeners, and
					// events in that specific order. Events have to be loaded
					// last because they may require model listeners to have
					// been registered.

					initPortalProperties(
						servletContextName, portletClassLoader,
						portalProperties);
					initAuthFailures(
						servletContextName, portletClassLoader,
						portalProperties);
					initAutoDeployListeners(
						servletContextName, portletClassLoader,
						portalProperties);
					initAutoLogins(
						servletContextName, portletClassLoader,
						portalProperties);
					initAuthenticators(
						servletContextName, portletClassLoader,
						portalProperties);
					initHotDeployListeners(
						servletContextName, portletClassLoader,
						portalProperties);
					initModelListeners(
						servletContextName, portletClassLoader,
						portalProperties);
					initEvents(
						servletContextName, portletClassLoader,
						portalProperties);
				}
			}
		}

		LanguagesContainer languagesContainer = new LanguagesContainer();

		_languagesContainerMap.put(servletContextName, languagesContainer);

		List<Element> languagePropertiesEls = root.elements(
			"language-properties");

		for (Element languagePropertiesEl : languagePropertiesEls) {
			String languagePropertiesLocation = languagePropertiesEl.getText();

			try {
				URL url = portletClassLoader.getResource(
					languagePropertiesLocation);

				if (url == null) {
					continue;
				}

				InputStream is = url.openStream();

				Properties properties = new Properties();

				properties.load(is);

				is.close();

				Map<String, String> languageMap = new HashMap<String, String>();

				for (Map.Entry<Object, Object> entry : properties.entrySet()) {
					String key = (String)entry.getKey();
					String value = (String)entry.getValue();

					languageMap.put(key, value);
				}

				Locale locale = getLocale(languagePropertiesLocation);

				if (locale != null) {
					languagesContainer.addLanguage(locale, languageMap);
				}
			}
			catch (Exception e) {
				_log.error("Unable to read " + languagePropertiesLocation, e);
			}
		}

		String customJspDir = root.elementText("custom-jsp-dir");

		if (Validator.isNotNull(customJspDir)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Custom JSP directory: " + customJspDir);
			}

			List<String> customJsps = new ArrayList<String>();

			String webDir = servletContext.getRealPath(StringPool.SLASH);

			getCustomJsps(servletContext, webDir, customJspDir, customJsps);

			if (customJsps.size() > 0) {
				CustomJspBag customJspBag = new CustomJspBag(
					customJspDir, customJsps);

				if (_log.isDebugEnabled()) {
					StringBundler sb = new StringBundler(customJsps.size() * 2);

					sb.append("Custom JSP files:\n");

					Iterator<String> itr = customJsps.iterator();

					while (itr.hasNext()) {
						String customJsp = itr.next();

						sb.append(customJsp);

						if (itr.hasNext()) {
							sb.append(StringPool.NEW_LINE);
						}
					}

					_log.debug(sb.toString());
				}

				_customJspBagsMap.put(servletContextName, customJspBag);

				initCustomJspBag(customJspBag);
			}
		}

		List<Element> serviceEls = root.elements("service");

		for (Element serviceEl : serviceEls) {
			String serviceType = serviceEl.elementText("service-type");
			String serviceImpl = serviceEl.elementText("service-impl");

			Class<?> serviceTypeClass = portletClassLoader.loadClass(
				serviceType);
			Class<?> serviceImplClass = portletClassLoader.loadClass(
				serviceImpl);

			Constructor<?> serviceImplConstructor =
				serviceImplClass.getConstructor(
					new Class<?>[] {serviceTypeClass});

			Object serviceProxy = PortalBeanLocatorUtil.locate(serviceType);

			if (Proxy.isProxyClass(serviceProxy.getClass())) {
				initServices(
					servletContextName, portletClassLoader, serviceType,
					serviceTypeClass, serviceImplConstructor, serviceProxy);
			}
			else {
				_log.error(
					"Service hooks require Spring to be configured to use " +
						"JdkDynamicProxy and will not work with CGLIB");
			}
		}

		// Begin backwards compatibility for 5.1.0

		ModelListenersContainer modelListenersContainer =
			_modelListenersContainerMap.get(servletContextName);

		if (modelListenersContainer == null) {
			modelListenersContainer = new ModelListenersContainer();

			_modelListenersContainerMap.put(
				servletContextName, modelListenersContainer);
		}

		List<Element> modelListenerEls = root.elements("model-listener");

		for (Element modelListenerEl : modelListenerEls) {
			String modelName = modelListenerEl.elementText("model-name");
			String modelListenerClassName = modelListenerEl.elementText(
				"model-listener-class");

			ModelListener<BaseModel<?>> modelListener = initModelListener(
				modelName, modelListenerClassName, portletClassLoader);

			if (modelListener != null) {
				modelListenersContainer.registerModelListener(
					modelName, modelListener);
			}
		}

		EventsContainer eventsContainer = _eventsContainerMap.get(
			servletContextName);

		if (eventsContainer == null) {
			eventsContainer = new EventsContainer();

			_eventsContainerMap.put(servletContextName, eventsContainer);
		}

		List<Element> eventEls = root.elements("event");

		for (Element eventEl : eventEls) {
			String eventName = eventEl.elementText("event-type");
			String eventClassName = eventEl.elementText("event-class");

			Object obj = initEvent(
				eventName, eventClassName, portletClassLoader);

			if (obj != null) {
				eventsContainer.registerEvent(eventName, obj);
			}
		}

		// End backwards compatibility for 5.1.0

		registerClpMessageListeners(servletContext, portletClassLoader);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Hook for " + servletContextName + " is available for use");
		}
	}

	protected void doInvokeUndeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking undeploy for " + servletContextName);
		}

		if (!_servletContextNames.remove(servletContextName)) {
			return;
		}

		AuthenticatorsContainer authenticatorsContainer =
			_authenticatorsContainerMap.remove(servletContextName);

		if (authenticatorsContainer != null) {
			authenticatorsContainer.unregisterAuthenticators();
		}

		AuthFailuresContainer authFailuresContainer =
			_authFailuresContainerMap.remove(servletContextName);

		if (authFailuresContainer != null) {
			authFailuresContainer.unregisterAuthFailures();
		}

		AutoDeployListenersContainer autoDeployListenersContainer =
			_autoDeployListenersContainerMap.remove(servletContextName);

		if (autoDeployListenersContainer != null) {
			autoDeployListenersContainer.unregisterAutoDeployListeners();
		}

		AutoLoginsContainer autoLoginsContainer =
			_autoLoginsContainerMap.remove(servletContextName);

		if (autoLoginsContainer != null) {
			autoLoginsContainer.unregisterAutoLogins();
		}

		CustomJspBag customJspBag = _customJspBagsMap.remove(
			servletContextName);

		if (customJspBag != null) {
			destroyCustomJspBag(customJspBag);
		}

		EventsContainer eventsContainer = _eventsContainerMap.remove(
			servletContextName);

		if (eventsContainer != null) {
			eventsContainer.unregisterEvents();
		}

		HotDeployListenersContainer hotDeployListenersContainer =
			_hotDeployListenersContainerMap.remove(servletContextName);

		if (hotDeployListenersContainer != null) {
			hotDeployListenersContainer.unregisterHotDeployListeners();
		}

		LanguagesContainer languagesContainer = _languagesContainerMap.remove(
			servletContextName);

		if (languagesContainer != null) {
			languagesContainer.unregisterLanguages();
		}

		ModelListenersContainer modelListenersContainer =
			_modelListenersContainerMap.remove(servletContextName);

		if (modelListenersContainer != null) {
			modelListenersContainer.unregisterModelListeners();
		}

		Properties portalProperties = _portalPropertiesMap.remove(
			servletContextName);

		if (portalProperties != null) {
			destroyPortalProperties(servletContextName, portalProperties);
		}

		destroyServices(servletContextName);

		unregisterClpMessageListeners(servletContext);

		if (_log.isInfoEnabled()) {
			_log.info("Hook for " + servletContextName + " was unregistered");
		}
	}

	protected AdvisedSupport getAdvisedSupport(Object serviceProxy)
		throws Exception {

		InvocationHandler invocationHandler = Proxy.getInvocationHandler(
			serviceProxy);

		Class<?> invocationHandlerClass = invocationHandler.getClass();

		Field advisedField = invocationHandlerClass.getDeclaredField("advised");

		advisedField.setAccessible(true);

		return (AdvisedSupport)advisedField.get(invocationHandler);
	}

	protected void getCustomJsps(
		ServletContext servletContext, String webDir, String resourcePath,
		List<String> customJsps) {

		Set<String> resourcePaths = servletContext.getResourcePaths(
			resourcePath);

		for (String curResourcePath : resourcePaths) {
			if (curResourcePath.endsWith(StringPool.SLASH)) {
				getCustomJsps(
					servletContext, webDir, curResourcePath, customJsps);
			}
			else {
				String customJsp = webDir + curResourcePath;

				customJsp = StringUtil.replace(
					customJsp, StringPool.DOUBLE_SLASH, StringPool.SLASH);

				customJsps.add(customJsp);
			}
		}
	}

	protected Locale getLocale(String languagePropertiesLocation) {
		int x = languagePropertiesLocation.indexOf(StringPool.UNDERLINE);
		int y = languagePropertiesLocation.indexOf(".properties");

		Locale locale = null;

		if ((x != -1) && (y != 1)) {
			String localeKey = languagePropertiesLocation.substring(x + 1, y);

			locale = LocaleUtil.fromLanguageId(localeKey);

		}

		return locale;
	}

	protected BasePersistence<?> getPersistence(String modelName) {
		int pos = modelName.lastIndexOf(StringPool.PERIOD);

		String entityName = modelName.substring(pos + 1);

		pos = modelName.lastIndexOf(".model.");

		String packagePath = modelName.substring(0, pos);

		return (BasePersistence<?>)PortalBeanLocatorUtil.locate(
			packagePath + ".service.persistence." + entityName + "Persistence");
	}

	protected File getPortalJspBackupFile(File portalJspFile) {
		String fileName = portalJspFile.getName();
		String filePath = portalJspFile.toString();

		int fileNameIndex = fileName.lastIndexOf(StringPool.PERIOD);

		if (fileNameIndex > 0) {
			int filePathIndex = filePath.lastIndexOf(fileName);

			fileName =
				fileName.substring(0, fileNameIndex) + ".portal" +
					fileName.substring(fileNameIndex);

			filePath = filePath.substring(0, filePathIndex) + fileName;
		}
		else {
			filePath += ".portal";
		}

		return new File(filePath);
	}

	protected void initAuthenticators(
			ClassLoader portletClassLoader, Properties portalProperties,
			String key, AuthenticatorsContainer authenticatorsContainer)
		throws Exception {

		String[] authenticatorClassNames = StringUtil.split(
			portalProperties.getProperty(key));

		for (String authenticatorClassName : authenticatorClassNames) {
			Authenticator authenticator = (Authenticator)newInstance(
				portletClassLoader, Authenticator.class,
				authenticatorClassName);

			authenticatorsContainer.registerAuthenticator(
				key, authenticator);
		}
	}

	protected void initAuthenticators(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		AuthenticatorsContainer authenticatorsContainer =
			new AuthenticatorsContainer();

		_authenticatorsContainerMap.put(
			servletContextName, authenticatorsContainer);

		initAuthenticators(
			portletClassLoader, portalProperties, AUTH_PIPELINE_PRE,
			authenticatorsContainer);
		initAuthenticators(
			portletClassLoader, portalProperties, AUTH_PIPELINE_POST,
			authenticatorsContainer);
	}

	protected void initAuthFailures(
			ClassLoader portletClassLoader, Properties portalProperties,
			String key, AuthFailuresContainer authFailuresContainer)
		throws Exception {

		String[] authFailureClassNames = StringUtil.split(
			portalProperties.getProperty(key));

		for (String authFailureClassName : authFailureClassNames) {
			AuthFailure authFailure = (AuthFailure)newInstance(
				portletClassLoader, AuthFailure.class, authFailureClassName);

			authFailuresContainer.registerAuthFailure(key, authFailure);
		}
	}

	protected void initAuthFailures(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		AuthFailuresContainer authFailuresContainer =
			new AuthFailuresContainer();

		_authFailuresContainerMap.put(
			servletContextName, authFailuresContainer);

		initAuthFailures(
			portletClassLoader, portalProperties, AUTH_FAILURE,
			authFailuresContainer);
		initAuthFailures(
			portletClassLoader, portalProperties, AUTH_MAX_FAILURES,
			authFailuresContainer);
	}

	protected void initAutoDeployListeners(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		String[] autoDeployListenerClassNames = StringUtil.split(
			portalProperties.getProperty(PropsKeys.AUTO_DEPLOY_LISTENERS));

		if (autoDeployListenerClassNames.length == 0) {
			return;
		}

		AutoDeployListenersContainer autoDeployListenersContainer =
			new AutoDeployListenersContainer();

		_autoDeployListenersContainerMap.put(
			servletContextName, autoDeployListenersContainer);

		for (String autoDeployListenerClassName :
				autoDeployListenerClassNames) {

			AutoDeployListener autoDeployListener =
				(AutoDeployListener)newInstance(
					portletClassLoader, AutoDeployListener.class,
					autoDeployListenerClassName);

			autoDeployListenersContainer.registerAutoDeployListener(
				autoDeployListener);
		}
	}

	protected void initAutoLogins(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		AutoLoginsContainer autoLoginsContainer = new AutoLoginsContainer();

		_autoLoginsContainerMap.put(servletContextName, autoLoginsContainer);

		String[] autoLoginClassNames = StringUtil.split(
			portalProperties.getProperty(AUTO_LOGIN_HOOKS));

		for (String autoLoginClassName : autoLoginClassNames) {
			AutoLogin autoLogin = (AutoLogin)newInstance(
				portletClassLoader, AutoLogin.class, autoLoginClassName);

			autoLoginsContainer.registerAutoLogin(autoLogin);
		}
	}

	protected void initCustomJspBag(CustomJspBag customJspBag)
		throws Exception {

		String customJspDir = customJspBag.getCustomJspDir();
		List<String> customJsps = customJspBag.getCustomJsps();
		//String timestamp = customJspBag.getTimestamp();

		String portalWebDir = PortalUtil.getPortalWebDir();

		for (String customJsp : customJsps) {
			int pos = customJsp.indexOf(customJspDir);

			String portalJsp = customJsp.substring(
				pos + customJspDir.length(), customJsp.length());

			File portalJspFile = new File(portalWebDir + portalJsp);
			File portalJspBackupFile = getPortalJspBackupFile(portalJspFile);

			if (portalJspFile.exists() && !portalJspBackupFile.exists()) {
				FileUtil.copyFile(portalJspFile, portalJspBackupFile);
			}

			FileUtil.copyFile(customJsp, portalWebDir + portalJsp);
		}
	}

	protected Object initEvent(
			String eventName, String eventClassName,
			ClassLoader portletClassLoader)
		throws Exception {

		if (eventName.equals(APPLICATION_STARTUP_EVENTS)) {
			SimpleAction simpleAction =
				(SimpleAction)portletClassLoader.loadClass(
					eventClassName).newInstance();

			simpleAction = new InvokerSimpleAction(
				simpleAction, portletClassLoader);

			long companyId = CompanyThreadLocal.getCompanyId();

			long[] companyIds = PortalInstances.getCompanyIds();

			for (long curCompanyId : companyIds) {
				CompanyThreadLocal.setCompanyId(curCompanyId);

				simpleAction.run(new String[] {String.valueOf(curCompanyId)});
			}

			CompanyThreadLocal.setCompanyId(companyId);

			return null;
		}

		if (ArrayUtil.contains(_PROPS_KEYS_EVENTS, eventName)) {
			Action action = (Action)portletClassLoader.loadClass(
				eventClassName).newInstance();

			action = new InvokerAction(action, portletClassLoader);

			EventsProcessorUtil.registerEvent(eventName, action);

			return action;
		}

		if (ArrayUtil.contains(_PROPS_KEYS_SESSION_EVENTS, eventName)) {
			SessionAction sessionAction =
				(SessionAction)portletClassLoader.loadClass(
					eventClassName).newInstance();

			sessionAction = new InvokerSessionAction(
				sessionAction, portletClassLoader);

			EventsProcessorUtil.registerEvent(eventName, sessionAction);

			return sessionAction;
		}

		return null;
	}

	protected void initEvents(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		EventsContainer eventsContainer = new EventsContainer();

		_eventsContainerMap.put(servletContextName, eventsContainer);

		for (Map.Entry<Object, Object> entry : portalProperties.entrySet()) {
			String key = (String) entry.getKey();

			if (!key.equals(APPLICATION_STARTUP_EVENTS) &&
				!ArrayUtil.contains(_PROPS_KEYS_EVENTS, key) &&
				!ArrayUtil.contains(_PROPS_KEYS_SESSION_EVENTS, key)) {

				continue;
			}

			String eventName = key;
			String[] eventClassNames = StringUtil.split(
				(String)entry.getValue());

			for (String eventClassName : eventClassNames) {
				Object obj = initEvent(
					eventName, eventClassName, portletClassLoader);

				if (obj == null) {
					continue;
				}

				eventsContainer.registerEvent(eventName, obj);
			}
		}
	}

	protected void initHotDeployListeners(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		String[] hotDeployListenerClassNames = StringUtil.split(
			portalProperties.getProperty(PropsKeys.HOT_DEPLOY_LISTENERS));

		if (hotDeployListenerClassNames.length == 0) {
			return;
		}

		HotDeployListenersContainer hotDeployListenersContainer =
			new HotDeployListenersContainer();

		_hotDeployListenersContainerMap.put(
			servletContextName, hotDeployListenersContainer);

		for (String hotDeployListenerClassName : hotDeployListenerClassNames) {
			HotDeployListener hotDeployListener =
				(HotDeployListener)newInstance(
					portletClassLoader, HotDeployListener.class,
					hotDeployListenerClassName);

			hotDeployListenersContainer.registerHotDeployListener(
				hotDeployListener);
		}
	}

	protected void initLogger(ClassLoader portletClassLoader) {
		Log4JUtil.configureLog4J(
			portletClassLoader.getResource("META-INF/portal-log4j.xml"));
	}

	@SuppressWarnings("unchecked")
	protected ModelListener<BaseModel<?>> initModelListener(
			String modelName, String modelListenerClassName,
			ClassLoader portletClassLoader)
		throws Exception {

		ModelListener<BaseModel<?>> modelListener =
			(ModelListener<BaseModel<?>>)newInstance(
				portletClassLoader, ModelListener.class,
				modelListenerClassName);

		BasePersistence persistence = getPersistence(modelName);

		persistence.registerListener(modelListener);

		return modelListener;
	}

	protected void initModelListeners(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		ModelListenersContainer modelListenersContainer =
			new ModelListenersContainer();

		_modelListenersContainerMap.put(
			servletContextName, modelListenersContainer);

		for (Map.Entry<Object, Object> entry : portalProperties.entrySet()) {
			String key = (String) entry.getKey();

			if (!key.startsWith(VALUE_OBJECT_LISTENER)) {
				continue;
			}

			String modelName = key.substring(VALUE_OBJECT_LISTENER.length());

			String[] modelListenerClassNames = StringUtil.split(
				(String)entry.getValue());

			for (String modelListenerClassName : modelListenerClassNames) {
				ModelListener<BaseModel<?>> modelListener = initModelListener(
					modelName, modelListenerClassName, portletClassLoader);

				if (modelListener != null) {
					modelListenersContainer.registerModelListener(
						modelName, modelListener);
				}
			}
		}
	}

	protected void initPortalProperties(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		PropsUtil.addProperties(portalProperties);

		if (_log.isDebugEnabled() && portalProperties.containsKey(LOCALES)) {
			_log.debug(
				"Portlet locales " + portalProperties.getProperty(LOCALES));
			_log.debug("Merged locales " + PropsUtil.get(LOCALES));
			_log.debug(
				"Merged locales array length " +
					PropsUtil.getArray(LOCALES).length);
		}

		resetPortalProperties(servletContextName, portalProperties, true);

		if (portalProperties.containsKey(PropsKeys.AUTH_TOKEN_IMPL)) {
			String authTokenClassName = portalProperties.getProperty(
				PropsKeys.AUTH_TOKEN_IMPL);

			AuthToken authToken = (AuthToken)newInstance(
				portletClassLoader, AuthToken.class, authTokenClassName);

			AuthTokenWrapper authTokenWrapper =
				(AuthTokenWrapper)AuthTokenUtil.getAuthToken();

			authTokenWrapper.setAuthToken(authToken);
		}

		if (portalProperties.containsKey(PropsKeys.CAPTCHA_ENGINE_IMPL)) {
			String captchaClassName = portalProperties.getProperty(
				PropsKeys.CAPTCHA_ENGINE_IMPL);

			Captcha captcha = (Captcha)newInstance(
				portletClassLoader, Captcha.class, captchaClassName);

			CaptchaWrapper captchaWrapper =
				(CaptchaWrapper)CaptchaUtil.getCaptcha();

			captchaWrapper.setCaptcha(captcha);
		}

		if (portalProperties.containsKey(
				PropsKeys.CONTROL_PANEL_DEFAULT_ENTRY_CLASS)) {

			String controlPanelEntryClassName = portalProperties.getProperty(
				PropsKeys.CONTROL_PANEL_DEFAULT_ENTRY_CLASS);

			ControlPanelEntry controlPanelEntry =
				(ControlPanelEntry)newInstance(
					portletClassLoader, ControlPanelEntry.class,
					controlPanelEntryClassName);

			DefaultControlPanelEntryFactory.setInstance(controlPanelEntry);
		}

		if (portalProperties.containsKey(PropsKeys.DL_HOOK_IMPL)) {
			String dlHookClassName = portalProperties.getProperty(
				PropsKeys.DL_HOOK_IMPL);

			com.liferay.documentlibrary.util.Hook dlHook =
				(com.liferay.documentlibrary.util.Hook)newInstance(
					portletClassLoader,
					com.liferay.documentlibrary.util.Hook.class,
					dlHookClassName);

			com.liferay.documentlibrary.util.HookFactory.setInstance(dlHook);
		}

		if (portalProperties.containsKey(PropsKeys.IMAGE_HOOK_IMPL)) {
			String imageHookClassName = portalProperties.getProperty(
				PropsKeys.IMAGE_HOOK_IMPL);

			com.liferay.portal.kernel.image.Hook imageHook =
				(com.liferay.portal.kernel.image.Hook)newInstance(
					portletClassLoader,
					com.liferay.portal.kernel.image.Hook.class,
					imageHookClassName);

			com.liferay.portal.image.HookFactory.setInstance(imageHook);
		}

		if (portalProperties.containsKey(
				PropsKeys.LDAP_ATTRS_TRANSFORMER_IMPL)) {

			String attributesTransformerClassName =
				portalProperties.getProperty(
					PropsKeys.LDAP_ATTRS_TRANSFORMER_IMPL);

			AttributesTransformer attributesTransformer =
				(AttributesTransformer)newInstance(
					portletClassLoader, AttributesTransformer.class,
					attributesTransformerClassName);

			AttributesTransformerFactory.setInstance(attributesTransformer);
		}

		if (portalProperties.containsKey(PropsKeys.MAIL_HOOK_IMPL)) {
			String mailHookClassName = portalProperties.getProperty(
				PropsKeys.MAIL_HOOK_IMPL);

			com.liferay.mail.util.Hook mailHook =
				(com.liferay.mail.util.Hook)newInstance(
					portletClassLoader, com.liferay.mail.util.Hook.class,
					mailHookClassName);

			com.liferay.mail.util.HookFactory.setInstance(mailHook);
		}

		if (portalProperties.containsKey(PropsKeys.SANITIZER_IMPL)) {
			String sanitizerClassName = portalProperties.getProperty(
				PropsKeys.SANITIZER_IMPL);

			Sanitizer sanitizer = (Sanitizer)newInstance(
				portletClassLoader, Sanitizer.class, sanitizerClassName);

			SanitizerWrapper sanitizerWrapper =
				(SanitizerWrapper)SanitizerUtil.getSanitizer();

			sanitizerWrapper.setSanitizer(sanitizer);
		}

		if (portalProperties.containsKey(
				PropsKeys.USERS_EMAIL_ADDRESS_GENERATOR)) {

			String emailAddressGeneratorClassName =
				portalProperties.getProperty(
					PropsKeys.USERS_EMAIL_ADDRESS_GENERATOR);

			EmailAddressGenerator emailAddressGenerator =
				(EmailAddressGenerator)newInstance(
					portletClassLoader, EmailAddressGenerator.class,
					emailAddressGeneratorClassName);

			EmailAddressGeneratorFactory.setInstance(emailAddressGenerator);
		}

		if (portalProperties.containsKey(PropsKeys.USERS_FULL_NAME_GENERATOR)) {
			String fullNameGeneratorClassName = portalProperties.getProperty(
				PropsKeys.USERS_FULL_NAME_GENERATOR);

			FullNameGenerator fullNameGenerator =
				(FullNameGenerator)newInstance(
					portletClassLoader, FullNameGenerator.class,
					fullNameGeneratorClassName);

			FullNameGeneratorFactory.setInstance(fullNameGenerator);
		}

		if (portalProperties.containsKey(PropsKeys.USERS_FULL_NAME_VALIDATOR)) {
			String fullNameValidatorClassName = portalProperties.getProperty(
				PropsKeys.USERS_FULL_NAME_VALIDATOR);

			FullNameValidator fullNameValidator =
				(FullNameValidator)newInstance(
					portletClassLoader, FullNameValidator.class,
					fullNameValidatorClassName);

			FullNameValidatorFactory.setInstance(fullNameValidator);
		}

		if (portalProperties.containsKey(
				PropsKeys.USERS_SCREEN_NAME_GENERATOR)) {

			String screenNameGeneratorClassName = portalProperties.getProperty(
				PropsKeys.USERS_SCREEN_NAME_GENERATOR);

			ScreenNameGenerator screenNameGenerator =
				(ScreenNameGenerator)newInstance(
					portletClassLoader, ScreenNameGenerator.class,
					screenNameGeneratorClassName);

			ScreenNameGeneratorFactory.setInstance(screenNameGenerator);
		}

		if (portalProperties.containsKey(
				PropsKeys.USERS_SCREEN_NAME_VALIDATOR)) {

			String screenNameValidatorClassName = portalProperties.getProperty(
				PropsKeys.USERS_SCREEN_NAME_VALIDATOR);

			ScreenNameValidator screenNameValidator =
				(ScreenNameValidator)newInstance(
					portletClassLoader, ScreenNameValidator.class,
					screenNameValidatorClassName);

			ScreenNameValidatorFactory.setInstance(screenNameValidator);
		}

		if (portalProperties.containsKey(PropsKeys.RELEASE_INFO_BUILD_NUMBER) ||
			portalProperties.containsKey(PropsKeys.UPGRADE_PROCESSES)) {

			updateRelease(
				servletContextName, portletClassLoader, portalProperties);
		}
	}

	protected void initServices(
			String servletContextName, ClassLoader portletClassLoader,
			String serviceType, Class<?> serviceTypeClass,
			Constructor<?> serviceImplConstructor, Object serviceProxy)
		throws Exception {

		ServiceBag serviceBag = _servicesContainer.findByServiceType(
			serviceType);

		if (serviceBag != null) {
			throw new IllegalStateException(
				serviceType + " is already overridden by " +
					serviceBag.getServletContextName());
		}

		AdvisedSupport advisedSupport = getAdvisedSupport(serviceProxy);

		TargetSource targetSource = advisedSupport.getTargetSource();

		Object originalService = targetSource.getTarget();

		if (Proxy.isProxyClass(originalService.getClass())) {
			InvocationHandler invocationHandler =
				Proxy.getInvocationHandler(originalService);

			if (invocationHandler instanceof ClassLoaderBeanHandler) {
				ClassLoaderBeanHandler classLoaderBeanHandler =
					(ClassLoaderBeanHandler)invocationHandler;

				originalService =  classLoaderBeanHandler.getBean();
			}
		}

		Object customService = serviceImplConstructor.newInstance(
			originalService);

		Object customTarget = Proxy.newProxyInstance(
			portletClassLoader, new Class<?>[] {serviceTypeClass},
			new ClassLoaderBeanHandler(customService, portletClassLoader));

		TargetSource customTargetSource = new SingletonTargetSource(
			customTarget);

		advisedSupport.setTargetSource(customTargetSource);

		_servicesContainer.addServiceBag(
			servletContextName, serviceType, originalService);
	}

	protected void resetPortalProperties(
			String servletContextName, Properties portalProperties,
			boolean initPhase)
		throws Exception {

		for (String key : _PROPS_VALUES_BOOLEAN) {
			String fieldName = StringUtil.replace(
				key.toUpperCase(), StringPool.PERIOD,  StringPool.UNDERLINE);

			if (!containsKey(portalProperties, key)) {
				continue;
			}

			try {
				Field field = PropsValues.class.getField(fieldName);

				Boolean value = Boolean.valueOf(GetterUtil.getBoolean(
					PropsUtil.get(key)));

				field.setBoolean(null, value);
			}
			catch (Exception e) {
				_log.error(
					"Error setting field " + fieldName + ": " + e.getMessage());
			}
		}

		for (String key : _PROPS_VALUES_INTEGER) {
			String fieldName = StringUtil.replace(
				key.toUpperCase(), StringPool.PERIOD,  StringPool.UNDERLINE);

			if (!containsKey(portalProperties, key)) {
				continue;
			}

			try {
				Field field = PropsValues.class.getField(fieldName);

				Integer value = Integer.valueOf(GetterUtil.getInteger(
					PropsUtil.get(key)));

				field.setInt(null, value);
			}
			catch (Exception e) {
				_log.error(
					"Error setting field " + fieldName + ": " + e.getMessage());
			}
		}

		for (String key : _PROPS_VALUES_LONG) {
			String fieldName = StringUtil.replace(
				key.toUpperCase(), StringPool.PERIOD,  StringPool.UNDERLINE);

			if (!containsKey(portalProperties, key)) {
				continue;
			}

			try {
				Field field = PropsValues.class.getField(fieldName);

				Long value = Long.valueOf(GetterUtil.getLong(
					PropsUtil.get(key)));

				field.setLong(null, value);
			}
			catch (Exception e) {
				_log.error(
					"Error setting field " + fieldName + ": " + e.getMessage());
			}
		}

		for (String key : _PROPS_VALUES_STRING) {
			String fieldName = StringUtil.replace(
				key.toUpperCase(), StringPool.PERIOD,  StringPool.UNDERLINE);

			if (!containsKey(portalProperties, key)) {
				continue;
			}

			try {
				Field field = PropsValues.class.getField(fieldName);

				String value = GetterUtil.getString(PropsUtil.get(key));

				field.set(null, value);
			}
			catch (Exception e) {
				_log.error(
					"Error setting field " + fieldName + ": " + e.getMessage());
			}
		}

		for (String key : _PROPS_VALUES_STRING_ARRAY) {
			String fieldName = StringUtil.replace(
				key.toUpperCase(), StringPool.PERIOD,  StringPool.UNDERLINE);

			if (!containsKey(portalProperties, key)) {
				continue;
			}

			try {
				Field field = PropsValues.class.getField(fieldName);

				StringArraysContainer stringArraysContainer =
					_stringArraysContainerMap.get(key);

				String[] value = null;

				if (initPhase) {
					value = PropsUtil.getArray(key);
				}

				stringArraysContainer.setPluginStringArray(
					servletContextName, value);

				value = stringArraysContainer.getMergedStringArray();

				field.set(null, value);
			}
			catch (Exception e) {
				_log.error(
					"Error setting field " + fieldName + ": " + e.getMessage());
			}
		}

		if (containsKey(portalProperties, LOCALES)) {
			PropsValues.LOCALES = PropsUtil.getArray(LOCALES);

			LanguageUtil.init();
		}

		CacheUtil.clearCache();

		JavaScriptBundleUtil.clearCache();
	}

	protected void updateRelease(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		int buildNumber = GetterUtil.getInteger(
			portalProperties.getProperty(PropsKeys.RELEASE_INFO_BUILD_NUMBER));

		if (buildNumber <= 0) {
			_log.error(
				"Skipping upgrade processes for " + servletContextName +
					" because \"release.info.build.number\" is not specified");

			return;
		}

		Release release = null;

		try {
			release = ReleaseLocalServiceUtil.getRelease(
				servletContextName, buildNumber);
		}
		catch (PortalException pe) {
			int previousBuildNumber = GetterUtil.getInteger(
				portalProperties.getProperty(
					PropsKeys.RELEASE_INFO_PREVIOUS_BUILD_NUMBER),
				buildNumber);

			release = ReleaseLocalServiceUtil.addRelease(
				servletContextName, previousBuildNumber);
		}

		if (buildNumber == release.getBuildNumber()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping upgrade processes for " + servletContextName +
						" because it is already up to date");
			}
		}
		else if (buildNumber < release.getBuildNumber()) {
			throw new UpgradeException(
				"Skipping upgrade processes for " + servletContextName +
					" because you are trying to upgrade with an older version");
		}
		else {
			String[] upgradeProcessClassNames = StringUtil.split(
				portalProperties.getProperty(PropsKeys.UPGRADE_PROCESSES));

			UpgradeProcessUtil.upgradeProcess(
				release.getBuildNumber(), upgradeProcessClassNames,
				portletClassLoader);
		}

		ReleaseLocalServiceUtil.updateRelease(
			release.getReleaseId(), buildNumber, null, true);
	}

	private static final String[] _PROPS_KEYS_EVENTS = new String[] {
		LOGIN_EVENTS_POST,
		LOGIN_EVENTS_PRE,
		LOGOUT_EVENTS_POST,
		LOGOUT_EVENTS_PRE,
		SERVLET_SERVICE_EVENTS_POST,
		SERVLET_SERVICE_EVENTS_PRE
	};

	private static final String[] _PROPS_KEYS_SESSION_EVENTS = new String[] {
		SERVLET_SESSION_CREATE_EVENTS,
		SERVLET_SESSION_DESTROY_EVENTS
	};

	private static final String[] _PROPS_VALUES_BOOLEAN = new String[] {
		"auth.forward.by.last.path",
		"captcha.check.portal.create_account",
		"dl.webdav.hold.lock",
		"dl.webdav.save.to.single.version",
		"field.enable.com.liferay.portal.model.Contact.birthday",
		"field.enable.com.liferay.portal.model.Contact.male",
		"field.enable.com.liferay.portal.model.Organization.status",
		"javascript.fast.load",
		"layout.template.cache.enabled",
		"layout.user.private.layouts.auto.create",
		"layout.user.private.layouts.enabled",
		"layout.user.private.layouts.modifiable",
		"layout.user.public.layouts.auto.create",
		"layout.user.public.layouts.enabled",
		"layout.user.public.layouts.modifiable",
		"login.create.account.allow.custom.password",
		"my.places.show.community.private.sites.with.no.layouts",
		"my.places.show.community.public.sites.with.no.layouts",
		"my.places.show.organization.private.sites.with.no.layouts",
		"my.places.show.organization.public.sites.with.no.layouts",
		"my.places.show.user.private.sites.with.no.layouts",
		"my.places.show.user.public.sites.with.no.layouts",
		"portlet.add.default.resource.check.enabled",
		"terms.of.use.required",
		"theme.css.fast.load",
		"theme.images.fast.load",
		"theme.loader.new.theme.id.on.import",
		"theme.portlet.decorate.default",
		"theme.portlet.sharing.default",
		"users.email.address.required",
		"users.screen.name.always.autogenerate"
	};

	private static final String[] _PROPS_VALUES_INTEGER = new String[] {
	};

	private static final String[] _PROPS_VALUES_LONG = new String[] {
	};

	private static final String[] _PROPS_VALUES_STRING = new String[] {
		"default.landing.page.path",
		"passwords.passwordpolicytoolkit.generator",
		"passwords.passwordpolicytoolkit.static",
		"theme.shortcut.icon"
	};

	private static final String[] _PROPS_VALUES_STRING_ARRAY = new String[] {
		"admin.default.group.names",
		"admin.default.role.names",
		"admin.default.user.group.names",
		"convert.processes",
		"layout.static.portlets.all",
		"layout.types",
		"session.phishing.protected.attributes"
	};

	private static Log _log = LogFactoryUtil.getLog(
		HookHotDeployListener.class);

	private Map<String, AuthenticatorsContainer> _authenticatorsContainerMap =
		new HashMap<String, AuthenticatorsContainer>();
	private Map<String, AuthFailuresContainer> _authFailuresContainerMap =
		new HashMap<String, AuthFailuresContainer>();
	private Map<String, AutoDeployListenersContainer>
		_autoDeployListenersContainerMap =
			new HashMap<String, AutoDeployListenersContainer>();
	private Map<String, AutoLoginsContainer> _autoLoginsContainerMap =
		new HashMap<String, AutoLoginsContainer>();
	private Map<String, CustomJspBag> _customJspBagsMap =
		new HashMap<String, CustomJspBag>();
	private Map<String, EventsContainer> _eventsContainerMap =
		new HashMap<String, EventsContainer>();
	private Map<String, HotDeployListenersContainer>
		_hotDeployListenersContainerMap =
			new HashMap<String, HotDeployListenersContainer>();
	private Map<String, LanguagesContainer> _languagesContainerMap =
		new HashMap<String, LanguagesContainer>();
	private Map<String, ModelListenersContainer> _modelListenersContainerMap =
		new HashMap<String, ModelListenersContainer>();
	private Map<String, Properties> _portalPropertiesMap =
		new HashMap<String, Properties>();
	private ServicesContainer _servicesContainer = new ServicesContainer();
	private Set<String> _servletContextNames = new HashSet<String>();
	private Map<String, StringArraysContainer> _stringArraysContainerMap =
		new HashMap<String, StringArraysContainer>();

	private class AuthenticatorsContainer {

		public void registerAuthenticator(
			String key, Authenticator authenticator) {

			List<Authenticator> authenticators = _authenticators.get(key);

			if (authenticators == null) {
				authenticators = new ArrayList<Authenticator>();

				_authenticators.put(key, authenticators);
			}

			AuthPipeline.registerAuthenticator(key, authenticator);

			authenticators.add(authenticator);
		}

		public void unregisterAuthenticators() {
			for (Map.Entry<String, List<Authenticator>> entry :
					_authenticators.entrySet()) {

				String key = entry.getKey();
				List<Authenticator> authenticators = entry.getValue();

				for (Authenticator authenticator : authenticators) {
					AuthPipeline.unregisterAuthenticator(key, authenticator);
				}
			}
		}

		Map<String, List<Authenticator>> _authenticators =
			new HashMap<String, List<Authenticator>>();

	}

	private class AuthFailuresContainer {

		public void registerAuthFailure(String key, AuthFailure authFailure) {
			List<AuthFailure> authFailures = _authFailures.get(key);

			if (authFailures == null) {
				authFailures = new ArrayList<AuthFailure>();

				_authFailures.put(key, authFailures);
			}

			AuthPipeline.registerAuthFailure(key, authFailure);

			authFailures.add(authFailure);
		}

		public void unregisterAuthFailures() {
			for (Map.Entry<String, List<AuthFailure>> entry :
					_authFailures.entrySet()) {

				String key = entry.getKey();
				List<AuthFailure> authFailures = entry.getValue();

				for (AuthFailure authFailure : authFailures) {
					AuthPipeline.unregisterAuthFailure(key, authFailure);
				}
			}
		}

		Map<String, List<AuthFailure>> _authFailures =
			new HashMap<String, List<AuthFailure>>();

	}

	private class AutoDeployListenersContainer {

		public void registerAutoDeployListener(
			AutoDeployListener autoDeployListener) {

			AutoDeployDir autoDeployDir = AutoDeployUtil.getDir(
				AutoDeployDir.DEFAULT_NAME);

			if (autoDeployDir == null) {
				return;
			}

			autoDeployDir.registerListener(autoDeployListener);

			_autoDeployListeners.add(autoDeployListener);
		}

		public void unregisterAutoDeployListeners() {
			AutoDeployDir autoDeployDir = AutoDeployUtil.getDir(
				AutoDeployDir.DEFAULT_NAME);

			if (autoDeployDir == null) {
				return;
			}

			for (AutoDeployListener autoDeployListener : _autoDeployListeners) {
				autoDeployDir.unregisterListener(autoDeployListener);
			}
		}

		private List<AutoDeployListener> _autoDeployListeners =
			new ArrayList<AutoDeployListener>();

	}

	private class AutoLoginsContainer {

		public void registerAutoLogin(AutoLogin autoLogin) {
			AutoLoginFilter.registerAutoLogin(autoLogin);

			_autoLogins.add(autoLogin);
		}

		public void unregisterAutoLogins() {
			for (AutoLogin autoLogin : _autoLogins) {
				AutoLoginFilter.unregisterAutoLogin(autoLogin);
			}
		}

		List<AutoLogin> _autoLogins = new ArrayList<AutoLogin>();

	}

	private class CustomJspBag {

		public CustomJspBag(String customJspDir, List<String> customJsps) {
			_customJspDir = customJspDir;
			_customJsps = customJsps;
		}

		public String getCustomJspDir() {
			return _customJspDir;
		}

		public List<String> getCustomJsps() {
			return _customJsps;
		}

		private String _customJspDir;
		private List<String> _customJsps;

	}

	private class EventsContainer {

		public void registerEvent(String eventName, Object event) {
			List<Object> events = _eventsMap.get(eventName);

			if (events == null) {
				events = new ArrayList<Object>();

				_eventsMap.put(eventName, events);
			}

			events.add(event);
		}

		public void unregisterEvents() {
			for (Map.Entry<String, List<Object>> entry :
					_eventsMap.entrySet()) {

				String eventName = entry.getKey();
				List<Object> events = entry.getValue();

				for (Object event : events) {
					EventsProcessorUtil.unregisterEvent(eventName, event);
				}
			}
		}

		private Map<String, List<Object>> _eventsMap =
			new HashMap<String, List<Object>>();

	}

	private class HotDeployListenersContainer {

		public void registerHotDeployListener(
			HotDeployListener hotDeployListener) {

			HotDeployUtil.registerListener(hotDeployListener);

			_hotDeployListeners.add(hotDeployListener);
		}

		public void unregisterHotDeployListeners() {
			for (HotDeployListener hotDeployListener : _hotDeployListeners) {
				HotDeployUtil.unregisterListener(hotDeployListener);
			}
		}

		private List<HotDeployListener> _hotDeployListeners =
			new ArrayList<HotDeployListener>();

	}

	private class LanguagesContainer {

		public void addLanguage(
			Locale locale, Map<String, String> languageMap) {

			Map<String, String> oldLanguageMap =
				LanguageResources.putLanguageMap(locale, languageMap);

			_languagesMap.put(locale, oldLanguageMap);
		}

		public void unregisterLanguages() {
			for (Map.Entry<Locale, Map<String, String>> entry :
					_languagesMap.entrySet()) {

				Locale locale = entry.getKey();
				Map<String, String> languageMap = entry.getValue();

				LanguageResources.putLanguageMap(locale, languageMap);
			}
		}

		private Map<Locale, Map<String, String>> _languagesMap =
			new HashMap<Locale, Map<String, String>>();

	}

	private class ModelListenersContainer {

		public void registerModelListener(
			String modelName, ModelListener<BaseModel<?>> modelListener) {

			List<ModelListener<BaseModel<?>>> modelListeners =
				_modelListenersMap.get(modelName);

			if (modelListeners == null) {
				modelListeners = new ArrayList<ModelListener<BaseModel<?>>>();

				_modelListenersMap.put(modelName, modelListeners);
			}

			modelListeners.add(modelListener);
		}

		@SuppressWarnings("unchecked")
		public void unregisterModelListeners() {
			for (Map.Entry<String, List<ModelListener<BaseModel<?>>>> entry :
					_modelListenersMap.entrySet()) {

				String modelName = entry.getKey();
				List<ModelListener<BaseModel<?>>> modelListeners =
					entry.getValue();

				BasePersistence persistence = getPersistence(modelName);

				for (ModelListener<BaseModel<?>> modelListener :
						modelListeners) {

					persistence.unregisterListener(modelListener);
				}
			}
		}

		private Map<String, List<ModelListener<BaseModel<?>>>>
			_modelListenersMap =
				new HashMap<String, List<ModelListener<BaseModel<?>>>>();

	}

	private class ServiceBag {

		public ServiceBag(
			String servletContextName, String serviceType,
			Object originalService) {

			_servletContextName = servletContextName;
			_serviceType = serviceType;
			_originalService = originalService;
		}

		public Object getOriginalService() {
			return _originalService;
		}

		public String getServiceType() {
			return _serviceType;
		}

		public String getServletContextName() {
			return _servletContextName;
		}

		private Object _originalService;
		private String _serviceType;
		private String _servletContextName;

	}

	private class ServicesContainer {

		public void addServiceBag(
			String servletContextName, String serviceType,
			Object originalService) {

			ServiceBag serviceBag = new ServiceBag(
				servletContextName, serviceType, originalService);

			_serviceBags.add(serviceBag);
		}

		public ServiceBag findByServiceType(String serviceType) {
			for (ServiceBag serviceBag : _serviceBags) {
				if (serviceBag.getServiceType().equals(serviceType)) {
					return serviceBag;
				}
			}

			return null;
		}

		public List<ServiceBag> findByServletContextName(
			String servletContextName) {

			List<ServiceBag> serviceBags = new ArrayList<ServiceBag>();

			for (ServiceBag serviceBag : _serviceBags) {
				if (serviceBag.getServletContextName().equals(
						servletContextName)) {

					serviceBags.add(serviceBag);
				}
			}

			return serviceBags;
		}

		public void removeByServletContextName(
			String servletContextName) {

			Iterator<ServiceBag> itr = _serviceBags.iterator();

			while (itr.hasNext()) {
				ServiceBag serviceBag = itr.next();

				if (serviceBag.getServletContextName().equals(
						servletContextName)) {

					itr.remove();
				}
			}
		}

		private List<ServiceBag> _serviceBags = new ArrayList<ServiceBag>();

	}

	private class StringArraysContainer {

		private StringArraysContainer(String key) {
			_portalStringArray = PropsUtil.getArray(key);
		}

		public String[] getMergedStringArray() {
			List<String> mergedStringList = new UniqueList<String>();

			mergedStringList.addAll(ListUtil.fromArray(_portalStringArray));

			for (Map.Entry<String, String[]> entry :
					_pluginStringArrayMap.entrySet()) {

				String[] pluginStringArray = entry.getValue();

				mergedStringList.addAll(ListUtil.fromArray(pluginStringArray));
			}

			return mergedStringList.toArray(
				new String[mergedStringList.size()]);
		}

		public void setPluginStringArray(
			String servletContextName, String[] pluginStringArray) {

			if (pluginStringArray != null) {
				_pluginStringArrayMap.put(
					servletContextName, pluginStringArray);
			}
			else {
				_pluginStringArrayMap.remove(servletContextName);
			}
		}

		private String[] _portalStringArray;
		private Map<String, String[]> _pluginStringArrayMap =
			new HashMap<String, String[]>();

	}

}