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

package com.liferay.portal.kernel.jsonwebservice;

import java.lang.reflect.Method;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceActionsManagerUtil {

	public static List<String[]> dumpMappings() {
		return _restActionsManager.dumpMappings();
	}

	public static JSONWebServiceActionsManager getRESTActionsManager() {
		return _restActionsManager;
	}

	public static JSONWebServiceAction lookup(HttpServletRequest request) {
		return getRESTActionsManager().lookup(request);
	}

	public static void registerRESTAction(
		Class<?> actionClass, Method actionMethod, String path, String method) {

		getRESTActionsManager().registerRESTAction(
			actionClass, actionMethod, path, method);
	}

	public void setRESTActionsManager(JSONWebServiceActionsManager restActionsManager) {
		_restActionsManager = restActionsManager;
	}

	private static JSONWebServiceActionsManager _restActionsManager;

}