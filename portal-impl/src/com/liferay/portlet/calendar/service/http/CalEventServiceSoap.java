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

package com.liferay.portlet.calendar.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.calendar.service.CalEventServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.calendar.service.CalEventServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.calendar.model.CalEventSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.calendar.model.CalEvent}, that is translated to a
 * {@link com.liferay.portlet.calendar.model.CalEventSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CalEventServiceHttp
 * @see       com.liferay.portlet.calendar.model.CalEventSoap
 * @see       com.liferay.portlet.calendar.service.CalEventServiceUtil
 * @generated
 */
public class CalEventServiceSoap {
	public static com.liferay.portlet.calendar.model.CalEventSoap addEvent(
		java.lang.String title, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int durationHour, int durationMinute,
		boolean allDay, boolean timeZoneSensitive, java.lang.String type,
		boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.calendar.model.CalEvent returnValue = CalEventServiceUtil.addEvent(title,
					description, startDateMonth, startDateDay, startDateYear,
					startDateHour, startDateMinute, endDateMonth, endDateDay,
					endDateYear, durationHour, durationMinute, allDay,
					timeZoneSensitive, type, repeating, recurrence, remindBy,
					firstReminder, secondReminder, serviceContext);

			return com.liferay.portlet.calendar.model.CalEventSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteEvent(long eventId) throws RemoteException {
		try {
			CalEventServiceUtil.deleteEvent(eventId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.calendar.model.CalEventSoap getEvent(
		long eventId) throws RemoteException {
		try {
			com.liferay.portlet.calendar.model.CalEvent returnValue = CalEventServiceUtil.getEvent(eventId);

			return com.liferay.portlet.calendar.model.CalEventSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.calendar.model.CalEventSoap updateEvent(
		long eventId, java.lang.String title, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int durationHour, int durationMinute,
		boolean allDay, boolean timeZoneSensitive, java.lang.String type,
		boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.calendar.model.CalEvent returnValue = CalEventServiceUtil.updateEvent(eventId,
					title, description, startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute,
					endDateMonth, endDateDay, endDateYear, durationHour,
					durationMinute, allDay, timeZoneSensitive, type, repeating,
					recurrence, remindBy, firstReminder, secondReminder,
					serviceContext);

			return com.liferay.portlet.calendar.model.CalEventSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CalEventServiceSoap.class);
}