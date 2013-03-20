/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.bean;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.persistence.ResourcePersistence;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;

/**
 * @author Brian Wing Shun Chan
 * @author Miguel Pastor
 */
@DoPrivileged
@SuppressWarnings("deprecation")
public class BeanLocatorImpl implements BeanLocator {

	public static final String VELOCITY_SUFFIX = ".velocity";

	public BeanLocatorImpl(
		ClassLoader classLoader, ApplicationContext applicationContext) {

		_classLoader = classLoader;
		_applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return _applicationContext;
	}

	public ClassLoader getClassLoader() {
		PortalRuntimePermission.checkGetClassLoader(_paclServletContextName);

		return _classLoader;
	}

	public String[] getNames() {
		return _applicationContext.getBeanDefinitionNames();
	}

	public Class<?> getType(String name) {
		try {
			return _applicationContext.getType(name);
		}
		catch (Exception e) {
			throw new BeanLocatorException(e);
		}
	}

	public <T> Map<String, T> locate(Class<T> clazz)
		throws BeanLocatorException {

		try {
			return doLocate(clazz);
		}
		catch (SecurityException se) {
			throw se;
		}
		catch (Exception e) {
			throw new BeanLocatorException(e);
		}
	}

	public Object locate(String name) throws BeanLocatorException {
		try {
			return doLocate(name);
		}
		catch (SecurityException se) {
			throw se;
		}
		catch (Exception e) {
			Object bean = _deprecatedBeans.get(name);

			if (bean != null) {
				return bean;
			}

			if (name.equals(ResourcePersistence.class.getName())) {
				bean = new ResourcePersistence() {};

				_deprecatedBeans.put(name, bean);

				return bean;
			}
			else if (name.equals(ResourceService.class.getName())) {
				bean = new ResourceService() {};

				_deprecatedBeans.put(name, bean);

				return bean;
			}

			throw new BeanLocatorException(e);
		}
	}

	public void setPACLServletContextName(String paclServletContextName) {
		_paclServletContextName = paclServletContextName;
	}

	/**
	 * This method ensures the calls stack is the proper length.
	 */
	protected <T> Map<String, T> doLocate(Class<T> clazz) throws Exception {
		PortalRuntimePermission.checkGetBeanProperty(
			_paclServletContextName, clazz);

		return _applicationContext.getBeansOfType(clazz);
	}

	protected Object doLocate(String name) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Locating " + name);
		}

		if (name.equals("portletClassLoader")) {
			PortalRuntimePermission.checkGetClassLoader(
				_paclServletContextName);
		}

		Object bean = null;

		if (name.endsWith(VELOCITY_SUFFIX)) {
			Object velocityBean = _velocityBeans.get(name);

			if (velocityBean == null) {
				String originalName = name.substring(
					0, name.length() - VELOCITY_SUFFIX.length());

				Object curBean = _applicationContext.getBean(originalName);

				velocityBean = ProxyUtil.newProxyInstance(
					_classLoader, getInterfaces(curBean),
					_pacl.getInvocationHandler(curBean, _classLoader));

				_velocityBeans.put(name, velocityBean);
			}

			bean = velocityBean;
		}
		else {
			bean = _applicationContext.getBean(name);
		}

		if (bean == null) {
			return bean;
		}

		PortalRuntimePermission.checkGetBeanProperty(
			_paclServletContextName, bean.getClass());

		return bean;
	}

	/**
	 * @see {@link
	 *      com.liferay.portal.security.lang.DoPrivilegedFactory#_getInterfaces(
	 *      List, Class)}
	 */
	protected void getInterfaces(
		List<Class<?>> interfaceClasses, Class<?> clazz) {

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			try {
				interfaceClasses.add(
					_classLoader.loadClass(interfaceClass.getName()));
			}
			catch (ClassNotFoundException cnfe) {
			}
		}
	}

	/**
	 * @see {@link
	 *      com.liferay.portal.security.lang.DoPrivilegedFactory#_getInterfaces(
	 *      Object)}
	 */
	protected Class<?>[] getInterfaces(Object object) {
		List<Class<?>> interfaceClasses = new ArrayList<Class<?>>();

		Class<?> clazz = object.getClass();

		getInterfaces(interfaceClasses, clazz);

		Class<?> superClass = clazz.getSuperclass();

		while (superClass != null) {
			getInterfaces(interfaceClasses, superClass);

			superClass = superClass.getSuperclass();
		}

		return interfaceClasses.toArray(new Class<?>[interfaceClasses.size()]);
	}

	private static Log _log = LogFactoryUtil.getLog(BeanLocatorImpl.class);

	private static PACL _pacl = new NoPACL();

	private ApplicationContext _applicationContext;
	private ClassLoader _classLoader;
	private Map<String, Object> _deprecatedBeans =
		new ConcurrentHashMap<String, Object>();
	private String _paclServletContextName;
	private Map<String, Object> _velocityBeans =
		new ConcurrentHashMap<String, Object>();

	private static class NoPACL implements PACL {

		public InvocationHandler getInvocationHandler(
			Object bean, ClassLoader classLoader) {

			return new VelocityBeanHandler(bean, classLoader);
		}

	}

	public static interface PACL {

		public InvocationHandler getInvocationHandler(
			Object bean, ClassLoader classLoader);

	}

}