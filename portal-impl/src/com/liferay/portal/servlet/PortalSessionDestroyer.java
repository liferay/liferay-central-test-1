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

package com.liferay.portal.servlet;

import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.servlet.PortalSessionContext;
import com.liferay.portal.kernel.servlet.PortletSessionTracker;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import org.apache.struts.Globals;

/**
 * @author Michael Young
 */
public class PortalSessionDestroyer extends BasePortalLifecycle {

	public PortalSessionDestroyer(HttpSessionEvent httpSessionEvent) {
		_httpSessionEvent = httpSessionEvent;

		registerPortalLifecycle(METHOD_INIT);
	}

	@Override
	protected void doPortalDestroy() {
	}

	@Override
	protected void doPortalInit() {
		if (PropsValues.SESSION_DISABLED) {
			return;
		}

		HttpSession session = _httpSessionEvent.getSession();

		PortalSessionContext.remove(session.getId());

		try {
			Long userIdObj = (Long)session.getAttribute(WebKeys.USER_ID);

			if (userIdObj == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("User id is not in the session");
				}
			}

			if (userIdObj == null) {
				return;
			}

			// Language

			session.removeAttribute(Globals.LOCALE_KEY);

			// Live users

			if (PropsValues.LIVE_USERS_ENABLED) {
				long userId = userIdObj.longValue();
				long companyId = CompanyLocalServiceUtil.getCompanyId(userId);
				String sessionId = session.getId();

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("command", "signOut");
				jsonObject.put("companyId", companyId);
				jsonObject.put("userId", userId);
				jsonObject.put("sessionId", sessionId);

				MessageBusUtil.sendMessage(
					DestinationNames.LIVE_USERS, jsonObject.toString());
			}
		}
		catch (IllegalStateException ise) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Please upgrade to a Servlet 2.4 compliant container");
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			PortletSessionTracker portletSessionTracker =
				(PortletSessionTracker)session.getAttribute(
					WebKeys.PORTLET_SESSION_TRACKER);

			if (portletSessionTracker != null) {
				PortletSessionTracker.invalidate(session);

				session.removeAttribute(WebKeys.PORTLET_SESSION_TRACKER);
			}
		}
		catch (IllegalStateException ise) {
			if (_log.isWarnEnabled()) {
				_log.warn(ise, ise);
			}
		}

		// Process session destroyed events

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SESSION_DESTROY_EVENTS,
				PropsValues.SERVLET_SESSION_DESTROY_EVENTS, session);
		}
		catch (ActionException ae) {
			_log.error(ae, ae);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalSessionDestroyer.class);

	private HttpSessionEvent _httpSessionEvent;

}