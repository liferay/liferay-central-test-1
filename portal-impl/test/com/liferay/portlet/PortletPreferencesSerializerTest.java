/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.SystemException;
import com.liferay.portal.util.BaseTestCase;

import java.util.Map;

/**
 * <a href="PortletPreferencesSerializerTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class PortletPreferencesSerializerTest extends BaseTestCase {

	public void testBlankPreference() throws Exception {
		String expectedXML =
			"<portlet-preferences><preference><name>name</name><value>" +
				"</value></preference></portlet-preferences>";

		PortletPreferencesImpl portletPreferences =
			new PortletPreferencesImpl();

		portletPreferences.setValue("name", "");

		String actualXML = PortletPreferencesSerializer.toXML(
			portletPreferences);

		assertEquals(expectedXML, actualXML);

		portletPreferences = deserialize(expectedXML);

		Map<String, Preference> preferenceMap =
			portletPreferences.getPreferences();

		assertEquals(1, preferenceMap.size());

		Preference preference = preferenceMap.values().iterator().next();

		assertEquals("name", preference.getName());

		String[] values = preference.getValues();

		assertEquals(1, values.length);
		assertEquals("", values[0]);
	}

	public void testComplexPortletPreferences() throws Exception {
		PortletPreferencesImpl portletPreferences =
			new PortletPreferencesImpl();

		// Blank

		portletPreferences.setValue("", "");

		// Empty

		portletPreferences.setValues("name1", new String[0]);

		// Multiple

		portletPreferences.setValues("name2", new String[] {"", "value1"});

		// Read only

		Preference preference = new Preference(
			"name3", new String[] {"value2", "value3"}, true);

		portletPreferences.getPreferences().put("name3", preference);

		String actualXML = PortletPreferencesSerializer.toXML(
			portletPreferences);

		portletPreferences = deserialize(actualXML);

		Map<String, Preference> preferenceMap =
			portletPreferences.getPreferences();

		assertEquals(4, preferenceMap.size());

		// Blank

		preference = preferenceMap.get("");

		assertNotNull(preference);
		assertEquals("", preference.getName());

		String[] values = preference.getValues();

		assertEquals(1, values.length);
		assertEquals("", values[0]);
		assertFalse(preference.isReadOnly());

		// Empty

		preference = preferenceMap.get("name1");

		assertNotNull(preference);
		assertEquals("name1", preference.getName());
		values = preference.getValues();
		assertEquals(0, values.length);
		assertFalse(preference.isReadOnly());

		// Multiple

		preference = preferenceMap.get("name2");

		assertNotNull(preference);
		assertEquals("name2", preference.getName());

		values = preference.getValues();

		assertEquals(2, values.length);
		assertEquals("", values[0]);
		assertEquals("value1", values[1]);
		assertFalse(preference.isReadOnly());

		// Read only

		preference = preferenceMap.get("name3");

		assertNotNull(preference);
		assertEquals("name3", preference.getName());

		values = preference.getValues();

		assertEquals(2, values.length);
		assertEquals("value2", values[0]);
		assertEquals("value3", values[1]);
		assertTrue(preference.isReadOnly());

	}

	public void testEmptyPortletPreferences() throws SystemException{
		String expectedXML = "<portlet-preferences></portlet-preferences>";

		PortletPreferencesImpl portletPreferences =
			new PortletPreferencesImpl();

		String actualXML = PortletPreferencesSerializer.toXML(
			portletPreferences);

		assertEquals(expectedXML, actualXML);

		portletPreferences =
			(PortletPreferencesImpl)PortletPreferencesSerializer.fromDefaultXML(
				expectedXML);

		assertEquals(0, portletPreferences.getPreferences().size());
	}

	public void testEmptyPreference() throws Exception {
		String expectedXML =
			"<portlet-preferences><preference><name>name</name></preference>" +
				"</portlet-preferences>";

		PortletPreferencesImpl portletPreferences =
			new PortletPreferencesImpl();

		portletPreferences.setValues("name", new String[0]);

		String actualXML = PortletPreferencesSerializer.toXML(
			portletPreferences);

		assertEquals(expectedXML, actualXML);

		portletPreferences = deserialize(expectedXML);

		Map<String, Preference> preferenceMap =
			portletPreferences.getPreferences();

		assertEquals(1, preferenceMap.size());

		Preference preference = preferenceMap.values().iterator().next();

		assertEquals("name", preference.getName());
		assertEquals(0, preference.getValues().length);
		assertFalse(preference.isReadOnly());
	}

	public void testMultiplePreferences() throws Exception {
		String expectedXML =
			"<portlet-preferences><preference><name>name</name><value>value1" +
				"</value><value>value2</value></preference>" +
					"</portlet-preferences>";

		PortletPreferencesImpl portletPreferences =
			new PortletPreferencesImpl();

		String[] values = {"value1", "value2"};

		portletPreferences.setValues("name", values);

		String actualXML = PortletPreferencesSerializer.toXML(
			portletPreferences);

		assertEquals(expectedXML, actualXML);

		portletPreferences = deserialize(expectedXML);

		Map<String, Preference> preferenceMap =
			portletPreferences.getPreferences();

		assertEquals(1, preferenceMap.size());

		Preference preference = preferenceMap.values().iterator().next();

		assertEquals("name", preference.getName());

		values = preference.getValues();

		assertEquals(2, values.length);
		assertEquals("value1", values[0]);
		assertEquals("value2", values[1]);
	}

	public void testSinglePreference() throws Exception {
		String expectedXML =
			"<portlet-preferences><preference><name>name</name><value>value" +
				"</value></preference></portlet-preferences>";

		PortletPreferencesImpl portletPreferences =
			new PortletPreferencesImpl();

		portletPreferences.setValue("name", "value");

		String actualXML = PortletPreferencesSerializer.toXML(
			portletPreferences);

		assertEquals(expectedXML, actualXML);

		portletPreferences = deserialize(expectedXML);

		Map<String, Preference> preferenceMap =
			portletPreferences.getPreferences();

		assertEquals(1, preferenceMap.size());

		Preference preference = preferenceMap.values().iterator().next();

		assertEquals("name", preference.getName());

		String[] values = preference.getValues();

		assertEquals(1, values.length);
		assertEquals("value", values[0]);
	}

	protected PortletPreferencesImpl deserialize(String xml) throws Exception {
		PortletPreferencesImpl portletPreferences =
			(PortletPreferencesImpl)PortletPreferencesSerializer.fromDefaultXML(
				xml);

		return portletPreferences;
	}

}