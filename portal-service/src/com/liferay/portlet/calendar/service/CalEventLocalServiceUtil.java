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
 * <a href="CalEventLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.calendar.service.CalEventLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.calendar.service.CalEventLocalService
 *
 */
public class CalEventLocalServiceUtil {
	public static com.liferay.portlet.calendar.model.CalEvent addCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws com.liferay.portal.SystemException {
		return getService().addCalEvent(calEvent);
	}

	public static com.liferay.portlet.calendar.model.CalEvent createCalEvent(
		long eventId) {
		return getService().createCalEvent(eventId);
	}

	public static void deleteCalEvent(long eventId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteCalEvent(eventId);
	}

	public static void deleteCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws com.liferay.portal.SystemException {
		getService().deleteCalEvent(calEvent);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.calendar.model.CalEvent getCalEvent(
		long eventId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getCalEvent(eventId);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> getCalEvents(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getCalEvents(start, end);
	}

	public static int getCalEventsCount()
		throws com.liferay.portal.SystemException {
		return getService().getCalEventsCount();
	}

	public static com.liferay.portlet.calendar.model.CalEvent updateCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws com.liferay.portal.SystemException {
		return getService().updateCalEvent(calEvent);
	}

	public static com.liferay.portlet.calendar.model.CalEvent addEvent(
		long userId, java.lang.String title, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int durationHour, int durationMinute,
		boolean allDay, boolean timeZoneSensitive, java.lang.String type,
		boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence,
		java.lang.String remindBy, int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addEvent(userId, title, description, startDateMonth,
			startDateDay, startDateYear, startDateHour, startDateMinute,
			endDateMonth, endDateDay, endDateYear, durationHour,
			durationMinute, allDay, timeZoneSensitive, type, repeating,
			recurrence, remindBy, firstReminder, secondReminder, serviceContext);
	}

	public static com.liferay.portlet.calendar.model.CalEvent addEvent(
		java.lang.String uuid, long userId, java.lang.String title,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence,
		java.lang.String remindBy, int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addEvent(uuid, userId, title, description, startDateMonth,
			startDateDay, startDateYear, startDateHour, startDateMinute,
			endDateMonth, endDateDay, endDateYear, durationHour,
			durationMinute, allDay, timeZoneSensitive, type, repeating,
			recurrence, remindBy, firstReminder, secondReminder, serviceContext);
	}

	public static void addEventResources(long eventId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addEventResources(eventId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addEventResources(
		com.liferay.portlet.calendar.model.CalEvent event,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addEventResources(event, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addEventResources(long eventId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addEventResources(eventId, communityPermissions, guestPermissions);
	}

	public static void addEventResources(
		com.liferay.portlet.calendar.model.CalEvent event,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addEventResources(event, communityPermissions, guestPermissions);
	}

	public static void checkEvents()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().checkEvents();
	}

	public static void deleteEvent(long eventId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteEvent(eventId);
	}

	public static void deleteEvent(
		com.liferay.portlet.calendar.model.CalEvent event)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteEvent(event);
	}

	public static void deleteEvents(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteEvents(groupId);
	}

	public static java.io.File exportEvent(long userId, long eventId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().exportEvent(userId, eventId);
	}

	public static java.io.File exportGroupEvents(long userId, long plid,
		java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().exportGroupEvents(userId, plid, fileName);
	}

	public static com.liferay.portlet.calendar.model.CalEvent getEvent(
		long eventId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getEvent(eventId);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.lang.String type, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getEvents(groupId, type, start, end);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.util.Calendar cal)
		throws com.liferay.portal.SystemException {
		return getService().getEvents(groupId, cal);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> getEvents(
		long groupId, java.util.Calendar cal, java.lang.String type)
		throws com.liferay.portal.SystemException {
		return getService().getEvents(groupId, cal, type);
	}

	public static int getEventsCount(long groupId, java.lang.String type)
		throws com.liferay.portal.SystemException {
		return getService().getEventsCount(groupId, type);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> getRepeatingEvents(
		long groupId) throws com.liferay.portal.SystemException {
		return getService().getRepeatingEvents(groupId);
	}

	public static boolean hasEvents(long groupId, java.util.Calendar cal)
		throws com.liferay.portal.SystemException {
		return getService().hasEvents(groupId, cal);
	}

	public static boolean hasEvents(long groupId, java.util.Calendar cal,
		java.lang.String type) throws com.liferay.portal.SystemException {
		return getService().hasEvents(groupId, cal, type);
	}

	public static void importICal4j(long userId, long plid, java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().importICal4j(userId, plid, file);
	}

	public static com.liferay.portlet.calendar.model.CalEvent updateEvent(
		long userId, long eventId, java.lang.String title,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence,
		java.lang.String remindBy, int firstReminder, int secondReminder)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateEvent(userId, eventId, title, description,
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, endDateMonth, endDateDay, endDateYear,
			durationHour, durationMinute, allDay, timeZoneSensitive, type,
			repeating, recurrence, remindBy, firstReminder, secondReminder);
	}

	public static CalEventLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("CalEventLocalService is not set");
		}

		return _service;
	}

	public void setService(CalEventLocalService service) {
		_service = service;
	}

	private static CalEventLocalService _service;
}