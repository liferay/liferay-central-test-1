/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.calendar.service;


/**
 * <a href="CalEventServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link CalEventService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CalEventService
 * @generated
 */
public class CalEventServiceUtil {
	public static com.liferay.portlet.calendar.model.CalEvent addEvent(
		java.lang.String title, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int durationHour, int durationMinute,
		boolean allDay, boolean timeZoneSensitive, java.lang.String type,
		boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addEvent(title, description, startDateMonth, startDateDay,
			startDateYear, startDateHour, startDateMinute, endDateMonth,
			endDateDay, endDateYear, durationHour, durationMinute, allDay,
			timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, serviceContext);
	}

	public static void deleteEvent(long eventId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteEvent(eventId);
	}

	public static java.io.File exportEvent(long eventId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().exportEvent(eventId);
	}

	public static java.io.File exportGroupEvents(long groupId,
		java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().exportGroupEvents(groupId, fileName);
	}

	public static com.liferay.portlet.calendar.model.CalEvent getEvent(
		long eventId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getEvent(eventId);
	}

	public static void importICal4j(long groupId, java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().importICal4j(groupId, file);
	}

	public static com.liferay.portlet.calendar.model.CalEvent updateEvent(
		long eventId, java.lang.String title, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int durationHour, int durationMinute,
		boolean allDay, boolean timeZoneSensitive, java.lang.String type,
		boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateEvent(eventId, title, description, startDateMonth,
			startDateDay, startDateYear, startDateHour, startDateMinute,
			endDateMonth, endDateDay, endDateYear, durationHour,
			durationMinute, allDay, timeZoneSensitive, type, repeating,
			recurrence, remindBy, firstReminder, secondReminder, serviceContext);
	}

	public static CalEventService getService() {
		if (_service == null) {
			throw new RuntimeException("CalEventService is not set");
		}

		return _service;
	}

	public void setService(CalEventService service) {
		_service = service;
	}

	private static CalEventService _service;
}