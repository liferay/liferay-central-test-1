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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.util.Locale;

/**
 * @author Bruno Basto
 * @author Brian Wing Shun Chan
 */
public class DDMXMLUtil {

	public static String formatXML(Document document) throws SystemException {
		return getDDMXML().formatXML(document);
	}

	public static String formatXML(String xml) throws SystemException {
		return getDDMXML().formatXML(xml);
	}

	public static DDMXML getDDMXML() {
		PortalRuntimePermission.checkGetBeanProperty(DDMXMLUtil.class);

		return _ddmXML;
	}

	public static Fields getFields(long ddmStructureId, String xml)
		throws SystemException, PortalException {

		return getDDMXML().getFields(ddmStructureId, xml);
	}

	public static Fields getFields(
			long ddmStructureId, XPath conditionXPath, String xml)
		throws PortalException, SystemException {

		return getDDMXML().getFields(ddmStructureId, conditionXPath, xml);
	}

	public static String getXML(Fields fields)
		throws PortalException, SystemException {

		return getDDMXML().getXML(fields);
	}

	public static String getXML(
			long ddmContentId, Fields fields, boolean mergeFields)
		throws PortalException, SystemException {

		return getDDMXML().getXML(ddmContentId, fields, mergeFields);
	}

	public static String updateXMLDefaultLocale(
			String xml, Locale contentDefaultLocale,
			Locale contentNewDefaultLocale)
		throws SystemException {

		return getDDMXML().updateXMLDefaultLocale(
			xml, contentDefaultLocale, contentNewDefaultLocale);
	}

	public void setDDMXML(DDMXML ddmXML) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ddmXML = ddmXML;
	}

	private static DDMXML _ddmXML;

}