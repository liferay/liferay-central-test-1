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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.templateparser.TemplateContext;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.template.TemplateContextHelper;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;

import freemarker.core.ParseException;

import freemarker.template.Configuration;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

/**
 * @author Tina Tian
 */
public class FreeMarkerTemplateTest extends TestCase {

	@Override
	public void setUp() throws Exception {
		_configuration = new Configuration();

		_configuration.setLocalizedLookup(false);

		_templateContextHelper = new MockTemplateContextHelper();

		_stringTemplateLoader = new StringTemplateLoader();

		MultiTemplateLoader multiTemplateLoader =
			new MultiTemplateLoader(
				new TemplateLoader[] {
					new ClassTemplateLoader(
						FreeMarkerTemplateTest.class, StringPool.SLASH),
					_stringTemplateLoader, new MockTemplateLoader()
				});

		_configuration.setTemplateLoader(multiTemplateLoader);
	}

	public void testGet() throws Exception {
		Template template = new FreeMarkerTemplate(
			_TEMPLATE_FILE_NAME, null, null, null, null, _configuration,
			_templateContextHelper, _stringTemplateLoader);

		template.put(_TEST_KEY, _TEST_VALUE);

		Object result = template.get(_TEST_KEY);

		assertNotNull(result);

		assertTrue(result instanceof String);

		String stringResult = (String)result;

		assertEquals(_TEST_VALUE, stringResult);
	}

	public void testPrepare() throws Exception {
		Template template = new FreeMarkerTemplate(
			_TEMPLATE_FILE_NAME, null, null, null, null, _configuration,
			_templateContextHelper, _stringTemplateLoader);

		template.put(_TEST_KEY, _TEST_VALUE);

		template.prepare(null);

		Object result = template.get(_TEST_VALUE);

		assertNotNull(result);

		assertTrue(result instanceof String);

		String stringResult = (String)result;

		assertEquals(_TEST_VALUE, stringResult);
	}

	public void testProcessTemplate1() throws Exception {
		Template template = new FreeMarkerTemplate(
			_TEMPLATE_FILE_NAME, null, null, null, null, _configuration,
			_templateContextHelper, _stringTemplateLoader);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	public void testProcessTemplate2() throws Exception {
		Template template = new FreeMarkerTemplate(
			_WRONG_TEMPLATE_ID, null, null, null, null, _configuration,
			_templateContextHelper, _stringTemplateLoader);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			template.processTemplate(unsyncStringWriter);

			fail();
		}
		catch (Exception e) {
			if (e instanceof TemplateException) {
				String message = e.getMessage();

				assertTrue(message.contains(_WRONG_TEMPLATE_ID));

				return;
			}

			fail();
		}
	}

	public void testProcessTemplate3() throws Exception {
		Template template = new FreeMarkerTemplate(
			_WRONG_TEMPLATE_ID, _TEST_TEMPLATE_CONTENT, null, null, null,
			_configuration, _templateContextHelper, _stringTemplateLoader);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	public void testProcessTemplate4() throws Exception {
		Template template = new FreeMarkerTemplate(
			_TEMPLATE_FILE_NAME, null, _WRONG_ERROR_TEMPLATE_ID, null, null,
			_configuration, _templateContextHelper, _stringTemplateLoader);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	public void testProcessTemplate5() throws Exception {
		Template template = new FreeMarkerTemplate(
			_WRONG_TEMPLATE_ID, null, _TEMPLATE_FILE_NAME, null, null,
			_configuration, _templateContextHelper, _stringTemplateLoader);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	public void testProcessTemplate6() throws Exception {
		Template template = new FreeMarkerTemplate(
			_WRONG_TEMPLATE_ID, null, _WRONG_ERROR_TEMPLATE_ID, null, null,
			_configuration, _templateContextHelper, _stringTemplateLoader);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			template.processTemplate(unsyncStringWriter);

			fail();
		}
		catch (Exception e) {
			if (e instanceof TemplateException) {
				String message = e.getMessage();

				assertTrue(message.contains(_WRONG_ERROR_TEMPLATE_ID));

				return;
			}

			fail();
		}
	}

	public void testProcessTemplate7() throws Exception {
		Template template = new FreeMarkerTemplate(
			_WRONG_TEMPLATE_ID, null, _WRONG_ERROR_TEMPLATE_ID,
			_TEST_TEMPLATE_CONTENT, null, _configuration,
			_templateContextHelper, _stringTemplateLoader);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	public void testProcessTemplate8() throws Exception {
		Map<String, Object> context = new HashMap<String, Object>();

		context.put(_TEST_KEY, _TEST_VALUE);

		Template template = new FreeMarkerTemplate(
			_TEMPLATE_FILE_NAME, null, null, null, context, _configuration,
			_templateContextHelper, _stringTemplateLoader);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	private static final String _TEMPLATE_FILE_NAME = "test.ftl";

	private static final String _TEST_KEY = "TEST_KEY";

	private static final String _TEST_TEMPLATE_CONTENT = "${" + _TEST_KEY + "}";

	private static final String _TEST_VALUE = "TEST_VALUE";

	private static final String _WRONG_ERROR_TEMPLATE_ID =
		"WRONG_ERROR_TEMPLATE_ID";

	private static final String _WRONG_TEMPLATE_ID = "WRONG_TEMPLATE_ID";

	private Configuration _configuration;
	private StringTemplateLoader _stringTemplateLoader;
	private TemplateContextHelper _templateContextHelper;

	private class MockTemplateContextHelper extends TemplateContextHelper {

		@Override
		public Map<String, Object> getHelperUtilities() {
			return Collections.emptyMap();
		}

		@Override
		public Map<String, Object> getRestrictedHelperUtilities() {
			return Collections.emptyMap();
		}

		@Override
		public Set<String> getRestrictedVariables() {
			return Collections.emptySet();
		}

		@Override
		public void prepare(
			TemplateContext templateContext, HttpServletRequest request) {

			String testValue = (String)templateContext.get(_TEST_KEY);

			templateContext.put(testValue, testValue);
		}

	}

	private class MockTemplateLoader implements TemplateLoader {

		public void closeTemplateSource(Object templateSource) {
		}

		public Object findTemplateSource(String name) throws IOException {
			if (_TEMPLATE_FILE_NAME.equals(name)) {
				return _TEMPLATE_FILE_NAME;
			}

			throw new ParseException(
				"Unable to find template source " + name, 0, 0);
		}

		public long getLastModified(Object templateSource) {
			return 0;
		}

		public Reader getReader(Object templateSource, String encoding)
			throws IOException {

			if (templateSource == _TEMPLATE_FILE_NAME) {
				return new StringReader(_TEST_TEMPLATE_CONTENT);
			}

			throw new ParseException(
				"Unable to get reader for template source " + templateSource, 0,
				0);
		}

	}

}