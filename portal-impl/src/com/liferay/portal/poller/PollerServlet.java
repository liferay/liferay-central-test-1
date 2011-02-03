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

package com.liferay.portal.poller;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class PollerServlet extends HttpServlet {

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			String content = getContent(request);

			if (content == null) {
				PortalUtil.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					new NoSuchLayoutException(), request, response);
			}
			else {
				response.setContentType(ContentTypes.TEXT_PLAIN_UTF8);

				ServletResponseUtil.write(
					response, content.getBytes(StringPool.UTF8));
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e, request,
				response);
		}
	}

	protected String getContent(HttpServletRequest request) throws Exception {
		String pollerRequestString = ParamUtil.getString(
			request, "pollerRequest");

		PollerResponseWriter pollerResponseWriter =
			new JSONPollerResponseWriter();

		SynchronousPollerRequestHandlerListener synchronousPollerRequestListener =
			new SynchronousPollerRequestHandlerListener();

		List<PollerRequestHandlerListener> listeners =
			new ArrayList<PollerRequestHandlerListener>(1);

		listeners.add(synchronousPollerRequestListener);

		PollerRequestHandler pollerRequestHandler = new PollerRequestHandler(
			request.getPathInfo(), pollerRequestString, pollerResponseWriter,
			listeners);

		if (!pollerRequestHandler.processRequest()) {
			return null;
		}

		synchronousPollerRequestListener.waitNotification(
			PropsValues.POLLER_REQUEST_TIMEOUT);

		pollerRequestHandler.shutdown();

		JSONArray jsonArray = pollerResponseWriter.getJSONArray();

		return jsonArray.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(PollerServlet.class);

}