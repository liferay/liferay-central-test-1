/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.staging;

import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * <a href="StagingUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class StagingUtil {

	public static void copyFromLive(
			PortletRequest PortletRequest, Portlet portlet)
		throws Exception {

		getStaging().copyFromLive(PortletRequest, portlet);
	}

	public static void copyFromLive(PortletRequest PortletRequest)
		throws Exception {

		getStaging().copyFromLive(PortletRequest);
	}

	public static void copyPortlet(
			PortletRequest PortletRequest, long sourceGroupId,
			long targetGroupId, long sourcePlid, long targetPlid,
			String portletId)
		throws Exception {

		getStaging().copyPortlet(
			PortletRequest, sourceGroupId, targetGroupId, sourcePlid,
			targetPlid, portletId);
	}

	public static void copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap,
			Map<String, String[]> exportParameterMap, String remoteAddress,
			int remotePort, boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout,
			Map<String, String[]> importParameterMap, Date startDate,
			Date endDate)
		throws Exception {

		getStaging().copyRemoteLayouts(
			sourceGroupId, privateLayout, layoutIdMap, exportParameterMap,
			remoteAddress, remotePort, secureConnection, remoteGroupId,
			remotePrivateLayout, importParameterMap, startDate, endDate);
	}

	public static List<Layout> getMissingParents(
			Layout layout, long liveGroupId)
		throws Exception {

		return getStaging().getMissingParents(layout, liveGroupId);
	}

	public static String getSchedulerGroupName(
		String destinationName, long groupId) {

		return getStaging().getSchedulerGroupName(destinationName, groupId);
	}

	public static Map<String, String[]> getStagingParameters() {
		return getStaging().getStagingParameters();
	}

	public static Map<String, String[]> getStagingParameters(
		PortletRequest PortletRequest) {

		return getStaging().getStagingParameters(PortletRequest);
	}

	public static void publishLayout(
			long plid, long liveGroupId, boolean includeChildren)
		throws Exception {

		getStaging().publishLayout(plid, liveGroupId, includeChildren);
	}

	public static void publishLayouts(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			long[] layoutIds, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws Exception {

		getStaging().publishLayouts(
			sourceGroupId, targetGroupId, privateLayout, layoutIds,
			parameterMap, startDate, endDate);
	}

	public static void publishLayouts(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws Exception {

		getStaging().publishLayouts(
			sourceGroupId, targetGroupId, privateLayout, layoutIdMap,
			parameterMap, startDate, endDate);
	}

	public static void publishLayouts(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception {

		getStaging().publishLayouts(
			sourceGroupId, targetGroupId, privateLayout, parameterMap,
			startDate, endDate);
	}

	public static void publishToLive(
			PortletRequest PortletRequest, Portlet portlet)
		throws Exception {

		getStaging().publishToLive(PortletRequest, portlet);
	}

	public static void publishToLive(PortletRequest PortletRequest)
		throws Exception {

		getStaging().publishToLive(PortletRequest);
	}

	public static void publishToRemote(PortletRequest PortletRequest)
		throws Exception {

		getStaging().publishToRemote(PortletRequest);
	}

	public static void scheduleCopyFromLive(PortletRequest PortletRequest)
		throws Exception {

		getStaging().scheduleCopyFromLive(PortletRequest);
	}

	public static void schedulePublishToLive(PortletRequest PortletRequest)
		throws Exception {

		getStaging().schedulePublishToLive(PortletRequest);
	}

	public static void schedulePublishToRemote(PortletRequest PortletRequest)
		throws Exception {

		getStaging().schedulePublishToRemote(PortletRequest);
	}

	public static void unscheduleCopyFromLive(PortletRequest PortletRequest)
		throws Exception {

		getStaging().unscheduleCopyFromLive(PortletRequest);
	}

	public static void unschedulePublishToLive(PortletRequest PortletRequest)
		throws Exception {

		getStaging().unschedulePublishToLive(PortletRequest);
	}

	public static void unschedulePublishToRemote(PortletRequest PortletRequest)
		throws Exception {

		getStaging().unschedulePublishToRemote(PortletRequest);
	}

	public static void updateStaging(PortletRequest PortletRequest)
		throws Exception {

		getStaging().updateStaging(PortletRequest);
	}

	public static Staging getStaging() {
		return _staging;
	}

	public void setStaging(Staging staging) {
		_staging = staging;
	}

	private static Staging _staging;

}