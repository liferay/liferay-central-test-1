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

package com.liferay.portlet.pageratings.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 */
public class PageRatingsPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "page_ratings";

	public PageRatingsPortletDataHandler() {
		setExportControls(
			new PortletDataHandlerBoolean(NAMESPACE, "ratings", true, true));
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		RatingsStatsLocalServiceUtil.deleteStats(
			Layout.class.getName(), portletDataContext.getPlid());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addRatingsEntries(
			Layout.class, portletDataContext.getPlid());

		return String.valueOf(portletDataContext.getPlid());
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importRatingsEntries(
			Layout.class, GetterUtil.getLong(data),
			portletDataContext.getPlid());

		return null;
	}

}