/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.httpservice.internal.http;

import com.liferay.httpservice.internal.definition.FilterDefinition;
import com.liferay.httpservice.internal.definition.ListenerDefinition;
import com.liferay.httpservice.internal.definition.ServletDefinition;
import com.liferay.httpservice.internal.definition.WebXMLDefinition;
import com.liferay.httpservice.internal.definition.WebXMLDefinitionLoader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xml.DocumentException;

import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class HttpServiceTracker
	extends ServiceTracker<HttpService, HttpService> {

	public HttpServiceTracker(BundleContext bundleContext, Bundle bundle)
		throws DocumentException {

		super(bundleContext, HttpService.class, null);

		_bundleContext = bundleContext;
		_bundle = bundle;

		_webXMLDefinitionLoader = new WebXMLDefinitionLoader();
	}

	@Override
	public HttpService addingService(
		ServiceReference<HttpService> serviceReference) {

		_serviceReference = serviceReference;

		HttpService httpService = _bundleContext.getService(_serviceReference);

		if (httpService == null) {
			return httpService;
		}

		HttpContext httpContext = httpService.createDefaultHttpContext();

		readConfiguration(_bundle);

		initListeners((ExtendedHttpService)httpService, httpContext);

		initServlets(httpService, httpContext);

		initFilters((ExtendedHttpService)httpService, httpContext);

		return httpService;
	}

	@Override
	public void removedService(
		ServiceReference<HttpService> serviceReference,
		HttpService httpService) {

		if (_webXML == null) {
			return;
		}

		destroyFilters((ExtendedHttpService)httpService);

		destroyServlets(httpService);

		destroyListeners((ExtendedHttpService)httpService);

		_webXML = null;
	}

	protected void destroyFilters(ExtendedHttpService extendedHttpService) {
		Map<String, FilterDefinition> filterDefinitions =
			_webXML.getFilterDefinitions();

		for (String name : filterDefinitions.keySet()) {
			try {
				extendedHttpService.unregisterFilter(name);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected void destroyListeners(ExtendedHttpService extendedHttpService) {
		List<ListenerDefinition> listenerDefinitions =
			_webXML.getListenerDefinitions();

		for (ListenerDefinition listenerDefinition : listenerDefinitions) {
			try {
				extendedHttpService.unregisterListener(
					listenerDefinition.getListener());
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected void destroyServlets(HttpService httpService) {
		Map<String, ServletDefinition> servlets =
			_webXML.getServletDefinitions();

		for (String name : servlets.keySet()) {
			httpService.unregister(name);
		}
	}

	protected void initFilters(
		ExtendedHttpService extendedHttpService, HttpContext httpContext) {

		Map<String, FilterDefinition> filterDefinitions =
			_webXML.getFilterDefinitions();

		for (Map.Entry<String, FilterDefinition> entry :
				filterDefinitions.entrySet()) {

			FilterDefinition filterDefinition = entry.getValue();

			List<String> urlPatterns = filterDefinition.getURLPatterns();

			for (String urlPattern : urlPatterns) {
				try {
					extendedHttpService.registerFilter(
						urlPattern, filterDefinition.getFilter(),
						filterDefinition.getInitParameters(), httpContext);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}
	}

	protected void initListeners(
		ExtendedHttpService extendedHttpService, HttpContext httpContext) {

		List<ListenerDefinition> listenerDefinitions =
			_webXML.getListenerDefinitions();

		for (ListenerDefinition listenerDefinition : listenerDefinitions) {
			try {
				extendedHttpService.registerListener(
					listenerDefinition.getListener(),
					_webXML.getContextParameters(), httpContext);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected void initServlets(
		HttpService httpService, HttpContext httpContext) {

		Map<String, ServletDefinition> servletDefinitions =
			_webXML.getServletDefinitions();

		for (Map.Entry<String, ServletDefinition> entry :
				servletDefinitions.entrySet()) {

			ServletDefinition servletDefinition = entry.getValue();

			List<String> urlPatterns = servletDefinition.getURLPatterns();

			for (String urlPattern : urlPatterns) {
				try {
					httpService.registerServlet(
						urlPattern, servletDefinition.getServlet(),
						servletDefinition.getInitParameters(), httpContext);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}
	}

	protected void readConfiguration(Bundle bundle) {
		try {
			_webXML = _webXMLDefinitionLoader.loadWebXML(bundle);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(HttpServiceTracker.class);

	private Bundle _bundle;
	private BundleContext _bundleContext;
	private ServiceReference<HttpService> _serviceReference;
	private WebXMLDefinition _webXML;
	private WebXMLDefinitionLoader _webXMLDefinitionLoader;

}