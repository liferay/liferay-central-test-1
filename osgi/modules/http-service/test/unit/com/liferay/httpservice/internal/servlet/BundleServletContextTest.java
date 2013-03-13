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

package com.liferay.httpservice.internal.servlet;

import com.liferay.httpservice.servlet.ResourceServlet;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;

import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionListener;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.NamespaceException;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockServletConfig;

/**
 * @author Miguel Pastor
 */
@PrepareForTest(FastDateFormatFactoryUtil.class)
@RunWith(PowerMockRunner.class)
public class BundleServletContextTest extends PowerMockito {

	@Before
	public void setUp() {
		mockStatic(FastDateFormatFactoryUtil.class);

		when(
			FastDateFormatFactoryUtil.getSimpleDateFormat(Mockito.anyString())
		).thenReturn(
			new SimpleDateFormat()
		);

		_bundleServletContext = new BundleServletContext(
			_bundle, "test", _servletContext);
	}

	@After
	public void tearDown() {
		verifyStatic();
	}

	@Test
	public void testGetClassloader() {
		mockBundleWiring();

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Assert.assertEquals(
			classLoader, _bundleServletContext.getClassLoader());

		verifyBundleWiring();
	}

	@Test
	public void testGetServletContextName() {
		Assert.assertEquals(
			"test",_bundleServletContext.getServletContextName());
	}

	@Test
	public void testRegisterHttpSessionActivationListener() {
		registerListener(_httpSessionActivationListener);
	}

	@Test
	public void testRegisterHttpSessionAttributeListener() {
		registerListener(_httpSessionAttributeListener);
	}

	@Test
	public void testRegisterHttpSessionBindingListener() {
		registerListener(_httpSessionBindingListener);
	}

	@Test
	public void testRegisterHttpSessionListener() {
		registerListener(_httpSessionListener);
	}

	@Test
	public void testRegisterNullFilter()
		throws NamespaceException, ServletException {

		try {
			_bundleServletContext.registerFilter(
				"Filter A", Arrays.asList("/a"), null, null, _httpContext);
		}
		catch (IllegalArgumentException iae) {
			return;
		}

		Assert.fail();
	}

	@Test
	public void testRegisterNullServlet()
		throws NamespaceException, ServletException {

		try {
			_bundleServletContext.registerServlet(
				"Servlet A", Arrays.asList("/a"), null, null, _httpContext);
		}
		catch (IllegalArgumentException iae) {
			return;
		}

		Assert.fail();
	}

	@Test
	public void testRegisterResource() throws NamespaceException {
		registerResource("JS Servlet", "/js");
	}

	@Test
	public void testRegisterResourceDuplicatedMapping()
		throws NamespaceException {

		registerResource("CSS Servlet", "/css");

		try {
			registerResource("New CSS Servlet", "/css");
		}
		catch (NamespaceException ne) {
			return;
		}

		Assert.fail();
	}

	@Test
	public void testRegisterResourceInvalidName() throws NamespaceException {
		try {
			registerResource("Bad Name/", "/js");
		}
		catch (IllegalArgumentException iae) {
			return;
		}

		Assert.fail();
	}

	@Test
	public void testRegisterServletContextAttributeListener() {
		registerListener(_servletContextAttributeListener);
	}

	@Test
	public void testRegisterServletContextListener() {
		registerListener(_servletContextListener);
	}

	@Test
	public void testRegisterServletMappingTwice()
		throws NamespaceException, ServletException {

		registerServlet("Original Servlet", "/a");

		try {
			registerServlet("New Servlet", "/a");
		}
		catch (NamespaceException ne) {
			return;
		}

		Assert.fail();
	}

	@Test
	public void testRegisterServletMultipleMapping()
		throws NamespaceException, ServletException {

		registerServlet("Servlet A", "/a", "/b", "/c");
	}

	@Test
	public void testRegisterServletRequestAttributeListener() {
		List<ServletRequestAttributeListener> servletRequestAttributeListeners =
			_bundleServletContext.getServletRequestAttributeListeners();

		int initialSize = servletRequestAttributeListeners.size();

		registerListener(_servletRequestAttributeListener);

		servletRequestAttributeListeners =
			_bundleServletContext.getServletRequestAttributeListeners();

		Assert.assertEquals(
			initialSize + 1, servletRequestAttributeListeners.size());
	}

	@Test
	public void testRegisterServletRequestListener() {
		List<ServletRequestListener> servletRequestListeners =
			_bundleServletContext.getServletRequestListeners();

		int initialSize = servletRequestListeners.size();

		registerListener(_servletRequestListener);

		servletRequestListeners =
			_bundleServletContext.getServletRequestListeners();

		Assert.assertEquals(initialSize + 1, servletRequestListeners.size());
	}

	@Test
	public void testRegisterServletSingleMapping()
		throws NamespaceException, ServletException {

		registerServlet("Servlet B", "/a");
	}

	@Test
	public void testUnegisterHttpSessionAttributeListener() {
		unregisterListener(_httpSessionAttributeListener);
	}

	@Test
	public void testUnegisterServletContextListener() {
		unregisterListener(_servletContextListener);
	}

	@Test
	public void testUnregisterExistingServlet()
		throws NamespaceException, ServletException {

		String servletName = "Servlet A";

		registerServlet(servletName, "/a");

		_bundleServletContext.unregisterServlet(servletName);

		Servlet servlet = _bundleServletContext.getServlet(servletName);

		Assert.assertNull(servlet);
	}

	@Test
	public void testUnregisterHttpSessionActivationListener() {
		unregisterListener(_httpSessionActivationListener);
	}

	@Test
	public void testUnregisterHttpSessionBindingListener() {

		unregisterListener(_httpSessionBindingListener);
	}

	@Test
	public void testUnregisterHttpSessionListener() {
		unregisterListener(_httpSessionListener);
	}

	@Test
	public void testUnregisterNonExistingServlet() {
		String servletName = "Nonexisting Servlet";

		_bundleServletContext.unregisterServlet(servletName);

		Servlet servlet = _bundleServletContext.getServlet(servletName);

		Assert.assertNull(servlet);
	}

	@Test
	public void testUnregisterServletContextAttributeListener() {
		registerListener(_servletContextAttributeListener);
	}

	@Test
	public void testUnregisterServletRequestAttributeListener() {
		List<ServletRequestAttributeListener> servletRequestAttributeListeners =
			_bundleServletContext.getServletRequestAttributeListeners();

		int initialSize = servletRequestAttributeListeners.size();

		registerListener(_servletRequestAttributeListener);

		unregisterListener(_servletRequestAttributeListener);

		servletRequestAttributeListeners =
			_bundleServletContext.getServletRequestAttributeListeners();

		Assert.assertEquals(
			initialSize, servletRequestAttributeListeners.size());
	}

	@Test
	public void testUnregisterServletRequestListener() {
		List<ServletRequestListener> servletRequestListeners =
			_bundleServletContext.getServletRequestListeners();

		int initialSize = servletRequestListeners.size();

		registerListener(_servletRequestListener);

		unregisterListener(_servletRequestListener);

		servletRequestListeners =
			_bundleServletContext.getServletRequestListeners();

		Assert.assertEquals(initialSize, servletRequestListeners.size());
	}

	protected void mockBundleWiring() {
		when(
			_bundle.adapt(BundleWiring.class)
		).thenReturn(
			_bundleWiring
		);

		Class<?> clazz = getClass();

		when(
			_bundleWiring.getClassLoader()
		).thenReturn(
			clazz.getClassLoader()
		);
	}

	protected void registerListener(Object listener) {
		mockBundleWiring();

		_bundleServletContext.registerListener(listener, null, _httpContext);

		verifyBundleWiring();
	}

	protected void registerResource(String servletName, String alias)
		throws NamespaceException {

		mockBundleWiring();

		_bundleServletContext.registerResources(
			alias, servletName, _httpContext);

		Servlet servlet = _bundleServletContext.getServlet(servletName);

		Assert.assertNotNull(servlet);

		Assert.assertTrue(servlet instanceof ResourceServlet);

		verifyBundleWiring();
	}

	protected void registerServlet(String servletName, String ... urlPatterns)
		throws NamespaceException, ServletException {

		mockBundleWiring();

		when(
			_servlet.getServletConfig()
		).thenReturn(
			new MockServletConfig(servletName)
		);

		_bundleServletContext.registerServlet(
			servletName, Arrays.asList(urlPatterns), _servlet, null,
			_httpContext);

		Servlet servlet = _bundleServletContext.getServlet(servletName);

		Assert.assertNotNull(servlet);

		Assert.assertEquals(
			servletName, servlet.getServletConfig().getServletName());

		Mockito.verify(_servlet).getServletConfig();

		verifyBundleWiring();
	}

	protected void unregisterListener(Object listener) {
		mockBundleWiring();

		_bundleServletContext.unregisterListener(listener);

		verifyBundleWiring();
	}

	protected void verifyBundleWiring() {
		Bundle bundle = Mockito.verify(_bundle);

		bundle.adapt(BundleWiring.class);

		BundleWiring bundleWiring = Mockito.verify(_bundleWiring);

		bundleWiring.getClassLoader();
	}

	@Mock
	private Bundle _bundle;

	private BundleServletContext _bundleServletContext;

	@Mock
	private BundleWiring _bundleWiring;

	@Mock
	private Filter _filter;

	@Mock
	private HttpContext _httpContext;

	@Mock
	private HttpSessionActivationListener _httpSessionActivationListener;

	@Mock
	private HttpSessionAttributeListener _httpSessionAttributeListener;

	@Mock
	private HttpSessionBindingListener _httpSessionBindingListener;

	@Mock
	private HttpSessionListener _httpSessionListener;

	@Mock
	private Servlet _servlet;

	@Mock
	private ServletContext _servletContext;

	@Mock
	private ServletContextAttributeListener _servletContextAttributeListener;

	@Mock
	private ServletContextListener _servletContextListener;

	@Mock
	private ServletRequestAttributeListener _servletRequestAttributeListener;

	@Mock
	private ServletRequestListener _servletRequestListener;

}