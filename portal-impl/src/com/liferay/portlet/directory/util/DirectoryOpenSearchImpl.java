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

package com.liferay.portlet.directory.util;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.HitsOpenSearchImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.enterpriseadmin.util.UserIndexer;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Ryan Park
 */
public class DirectoryOpenSearchImpl extends HitsOpenSearchImpl {

	public static final String SEARCH_PATH = "/c/directory/open_search";

	public static final String TITLE = "Liferay Directory Search: ";

	public String getPortletId() {
		return UserIndexer.PORTLET_ID;
	}

	public String getSearchPath() {
		return SEARCH_PATH;
	}

	public Summary getSummary(
			Indexer indexer, Document document, Locale locale, String snippet,
			PortletURL portletURL)
		throws SearchException {

		Summary summary = super.getSummary(
			indexer, document, locale, snippet, portletURL);

		portletURL = summary.getPortletURL();

		portletURL.setParameter("struts_action", "/directory/view_user");

		return summary;
	}

	public String getTitle(String keywords) {
		return TITLE + keywords;
	}

	protected PortletURL getPortletURL(
			HttpServletRequest request, String portletId, long groupId)
		throws Exception {

		return super.getPortletURL(request, PortletKeys.DIRECTORY, groupId);
	}

	protected LinkedHashMap<String, Object> getUserParams(
		long companyId, String keywords) {

		LinkedHashMap<String, Object> userParams =
			new LinkedHashMap<String, Object>();

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, User.class.getName());

		Enumeration<String> enu = expandoBridge.getAttributeNames();

		while (enu.hasMoreElements()) {
			String attributeName = enu.nextElement();

			UnicodeProperties properties = expandoBridge.getAttributeProperties(
				attributeName);

			String indexType = properties.getProperty(
				ExpandoColumnConstants.INDEX_TYPE);

			if (!indexType.equals(ExpandoColumnConstants.INDEX_TYPE_NONE)) {
				userParams.put(attributeName, keywords);
			}
		}

		return userParams;
	}

}