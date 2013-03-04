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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class DDMStructureTestUtil {

	public static DDMStructure addDDMStructure(long groupId, String className)
		throws Exception {

		return addDDMStructure(
			groupId, className, getSampleStructureXSD(),
			LocaleUtil.getDefault());
	}

	public static DDMStructure addDDMStructure(
			long groupId, String className, Locale defaultLocale)
		throws Exception {

		return addDDMStructure(
			groupId, className, getSampleStructureXSD(), defaultLocale);
	}

	public static DDMStructure addDDMStructure(
			long groupId, String className, String xsd)
		throws Exception {

		return addDDMStructure(
			groupId, className, xsd, LocaleUtil.getDefault());
	}

	public static DDMStructure addDDMStructure(
			long groupId, String className, String xsd, Locale defaultLocale)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(defaultLocale, "Test Structure");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMStructureLocalServiceUtil.addStructure(
			TestPropsValues.getUserId(), groupId,
			PortalUtil.getClassNameId(className), nameMap, null, xsd,
			serviceContext);
	}

	public static DDMStructure addDDMStructure(String className)
		throws Exception {

		return addDDMStructure(
			TestPropsValues.getGroupId(), className, getSampleStructureXSD(),
			LocaleUtil.getDefault());
	}

	public static DDMStructure addDDMStructure(
			String className, Locale defaultLocale)
		throws Exception {

		return addDDMStructure(
			TestPropsValues.getGroupId(), className, getSampleStructureXSD(),
			defaultLocale);
	}

	public static DDMStructure addDDMStructure(String className, String xsd)
		throws Exception {

		return addDDMStructure(
			TestPropsValues.getGroupId(), className, xsd,
			LocaleUtil.getDefault());
	}

	public static DDMStructure addDDMStructure(
			String className, String xsd, Locale defaultLocale)
		throws Exception {

		return addDDMStructure(
			TestPropsValues.getGroupId(), className, xsd, defaultLocale);
	}

	public static String getSampleStructureXSD() {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		JournalTestUtil.addDynamicElementElement(rootElement, "text", "name");
		JournalTestUtil.addDynamicElementElement(rootElement, "text", "link");

		return document.asXML();
	}

	public static String getSampleStructuredContent() {
		Document document = JournalTestUtil.createDocument("en_US", "en_US");

		Element dynamicElementElement =
			JournalTestUtil.addDynamicElementElement(
				document.getRootElement(), "text", "name");

		JournalTestUtil.addDynamicContentElement(
			dynamicElementElement, "en_US", "Joe Bloggs");

		return document.asXML();
	}

}