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

package com.liferay.portlet.layoutsadmin.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.staging.LayoutStagingUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.SessionTreeJSClicks;
import com.liferay.portal.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Eduardo Lundgren
 */
public class GetLayoutsAction extends JSONAction {

	public String getJSON(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String treeId = ParamUtil.getString(request, "treeId");

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layouts = getLayouts(request);

		List<String> checkedLayoutsIds = getCheckedLayouts(request, treeId);

		List<String> openLayoutsIds = getOpenLayouts(request, treeId);

		for (Layout layout : layouts) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("contentDisplayPage", layout.isContentDisplayPage());
			jsonObject.put("hasChildren", layout.hasChildren());
			jsonObject.put("layoutId", layout.getLayoutId());
			jsonObject.put("name", layout.getName(themeDisplay.getLocale()));
			jsonObject.put("parentLayoutId", layout.getParentLayoutId());
			jsonObject.put("plid", layout.getPlid());
			jsonObject.put("priority", layout.getPriority());
			jsonObject.put("privateLayout", layout.isPrivateLayout());
			jsonObject.put("type", layout.getType());

			if (Validator.isNotNull(treeId)) {
				jsonObject.put(
					"checked", checkedLayoutsIds.contains(
						String.valueOf(layout.getLayoutId())));
				jsonObject.put(
					"expanded", openLayoutsIds.contains(
						String.valueOf(layout.getLayoutId())));
			}

			LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
				layout);

			if (layoutRevision != null) {
				long layoutSetBranchId = layoutRevision.getLayoutSetBranchId();

				LayoutSetBranch layoutSetBranch =
					LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(
						layoutSetBranchId);

				jsonObject.put("layoutSetBranchId", layoutSetBranchId);
				jsonObject.put(
					"layoutSetBranchName", layoutSetBranch.getName());
				jsonObject.put(
					"layoutRevisionId", layoutRevision.getLayoutRevisionId());
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray.toString();
	}

	protected List<String> getCheckedLayouts(
		HttpServletRequest request, String treeId) {

		String treeNamespace = _CHECK_NAMESPACE.concat(treeId);

		return ListUtil.toList(
			StringUtil.split(
				SessionTreeJSClicks.getAddedNodes(request, treeNamespace)));
	}

	protected List<Layout> getLayouts(HttpServletRequest request)
		throws Exception {

		String treeId = ParamUtil.getString(request, "treeId");
		long groupId = ParamUtil.getLong(request, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
		long layoutId = ParamUtil.getLong(request, "layoutId");
		int start = ParamUtil.getInteger(request, "start");
		int end = start + PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN;

		List<Layout> results = new ArrayList<Layout>();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, layoutId, start, end);

		results.addAll(layouts);

		List<String> openLayoutsIds = getOpenLayouts(request, treeId);

		if (!openLayoutsIds.isEmpty()) {
			for (Layout layout : layouts) {
				for (String openLayoutId : openLayoutsIds) {
					if (PortalUtil.isLayoutDescendant(
						layout, Long.valueOf(openLayoutId))) {

						List<Layout> openLayouts =
							LayoutLocalServiceUtil.getLayouts(
								groupId, privateLayout,
								Long.valueOf(openLayoutId), start, end);

						results.addAll(openLayouts);
					}
				}
			}
		}

		return results;
	}

	protected List<String> getOpenLayouts(
		HttpServletRequest request, String treeId) {

		String treeNamespace = _EXPAND_NAMESPACE.concat(treeId);

		return ListUtil.toList(
			StringUtil.split(
				SessionTreeJSClicks.getAddedNodes(request, treeNamespace)));
	}

	private final String _CHECK_NAMESPACE = "CHECK_NAMESPACE_";

	private final String _EXPAND_NAMESPACE = "EXPAND_NAMESPACE_";

}