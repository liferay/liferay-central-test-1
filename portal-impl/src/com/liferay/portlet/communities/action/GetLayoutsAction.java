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

package com.liferay.portlet.communities.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.struts.JSONAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="GetLayoutsAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 */
public class GetLayoutsAction extends JSONAction {

	public String getJSON(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layouts = getLayouts(request);

		for (Layout layout : layouts) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("hasChildren", layout.hasChildren());
			jsonObject.put("layoutId", layout.getLayoutId());
			jsonObject.put("name", layout.getName());
			jsonObject.put("parentLayoutId", layout.getParentLayoutId());
			jsonObject.put("plid", layout.getPlid());
			jsonObject.put("priority", layout.getPriority());
			jsonObject.put("privateLayout", layout.getPrivateLayout());
			jsonObject.put("type", layout.getType());

			jsonArray.put(jsonObject);
		}

		return jsonArray.toString();
	}

	protected List<Layout> getLayouts(HttpServletRequest request)
		throws Exception {

		long groupId = ParamUtil.getLong(request, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
		long parentLayoutId = ParamUtil.getLong(request, "parentLayoutId");

		return LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, parentLayoutId);
	}

}