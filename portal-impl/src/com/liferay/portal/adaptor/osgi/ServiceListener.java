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

package com.liferay.portal.adaptor.osgi;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.target.SingletonTargetSource;

/**
 * @author Raymond Augé
 */
public class ServiceListener implements org.osgi.framework.ServiceListener {

	public ServiceListener(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	public void serviceChanged(ServiceEvent serviceEvent) {
		try {
			if (serviceEvent.getType() == ServiceEvent.REGISTERED) {
				eventRegistered(serviceEvent);
			}
			else if (serviceEvent.getType() == ServiceEvent.UNREGISTERING) {
				eventUnregistering(serviceEvent);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void eventRegistered(ServiceEvent event) throws Exception {
		ServiceReference serviceReference = event.getServiceReference();

		Boolean portalServiceWrapper = (Boolean)serviceReference.getProperty(
			OSGiConstants.PORTAL_SERVICE_WRAPPER);

		if ((portalServiceWrapper != null) && portalServiceWrapper) {
			registerServiceWrapper(serviceReference);
		}
	}

	protected void eventUnregistering(ServiceEvent event) throws Exception {
		ServiceReference serviceReference = event.getServiceReference();

		Boolean portalServiceWrapper = (Boolean)serviceReference.getProperty(
			OSGiConstants.PORTAL_SERVICE_WRAPPER);

		if ((portalServiceWrapper != null) && portalServiceWrapper) {
			unregisterServiceWrapper(serviceReference);
		}
	}

	protected void registerServiceWrapper(ServiceReference serviceReference)
		throws Exception {

		Bundle bundle = serviceReference.getBundle();

		String serviceType = (String)serviceReference.getProperty(
			OSGiConstants.PORTAL_SERVICE_TYPE);

		Class<?> serviceTypeClass = bundle.loadClass(serviceType);

		Object nextService = _bundleContext.getService(serviceReference);

		Class<?> serviceImplClass = nextService.getClass();

		Method serviceImplWrapperMethod = null;

		try {
			serviceImplWrapperMethod = serviceImplClass.getMethod(
				"setWrapped".concat(serviceTypeClass.getSimpleName()),
				serviceTypeClass);
		}
		catch (NoSuchMethodException nsme) {
			throw new IllegalArgumentException(
				"Implementation must extend " + serviceTypeClass.getName() +
					"Wrapper",
				nsme);
		}

		Object serviceProxy = PortalBeanLocatorUtil.locate(serviceType);

		if (!(serviceProxy instanceof Advised)) {
			throw new IllegalArgumentException(
				"Service must be an instance of Advised");
		}

		if (!Proxy.isProxyClass(serviceProxy.getClass())) {
			return;
		}

		AdvisedSupport advisedSupport = ServiceWrapperUtil.getAdvisedSupport(
			serviceProxy);

		Object previousService = ServiceWrapperUtil.getTarget(advisedSupport);

		serviceImplWrapperMethod.invoke(nextService, previousService);

		ClassLoader classLoader = serviceTypeClass.getClassLoader();

		Object nextTarget = Proxy.newProxyInstance(
			classLoader, new Class<?>[] {serviceTypeClass},
			new ClassLoaderBeanHandler(nextService, classLoader));

		TargetSource nextTargetSource = new SingletonTargetSource(nextTarget);

		advisedSupport.setTargetSource(nextTargetSource);

		Hashtable<String, Object> properties = new Hashtable<String, Object>();

		properties.put(Constants.SERVICE_RANKING, -1);

		properties.put(OSGiConstants.PORTAL_SERVICE, Boolean.TRUE);
		properties.put(OSGiConstants.PORTAL_SERVICE_PREVIOUS, Boolean.TRUE);

		ServiceRegistration serviceRegistration =
			_bundleContext.registerService(
				serviceType, previousService, properties);

		_serviceRegistrations.put(
			serviceRegistration.getReference(), serviceRegistration);
	}

	protected void unregisterServiceWrapper(ServiceReference serviceReference)
		throws Exception {

		String serviceType = (String)serviceReference.getProperty(
			OSGiConstants.PORTAL_SERVICE_TYPE);

		ServiceReference[] previousServiceReferences =
			_bundleContext.getServiceReferences(
				serviceType, _PREVIOUS_SERVICE_FILTER);

		if ((previousServiceReferences == null) ||
			(previousServiceReferences.length == 0)) {

			return;
		}

		ServiceReference previousServiceReference =
			previousServiceReferences[0];

		Object previousService = _bundleContext.getService(
			previousServiceReference);

		Object serviceProxy = PortalBeanLocatorUtil.locate(serviceType);

		AdvisedSupport advisedSupport = ServiceWrapperUtil.getAdvisedSupport(
			serviceProxy);

		TargetSource previousTargetSource = new SingletonTargetSource(
			previousService);

		advisedSupport.setTargetSource(previousTargetSource);

		ServiceRegistration serviceRegistration = _serviceRegistrations.get(
			previousServiceReference);

		serviceRegistration.unregister();

		_serviceRegistrations.remove(previousServiceReference);
	}

	private static final String _PREVIOUS_SERVICE_FILTER =
		"(&(portal.service=*)(portal.service.previous=*)(service.ranking=-1))";

	private static Log _log = LogFactoryUtil.getLog(ServiceListener.class);

	private BundleContext _bundleContext;
	private Map<ServiceReference, ServiceRegistration> _serviceRegistrations =
		new HashMap<ServiceReference, ServiceRegistration>();

}