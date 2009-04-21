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

package com.liferay.portlet.calendar.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.im.AIMConnector;
import com.liferay.portal.im.ICQConnector;
import com.liferay.portal.im.MSNConnector;
import com.liferay.portal.im.YMConnector;
import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.cal.TZSRecurrence;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.DateFormats;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.calendar.EventDurationException;
import com.liferay.portlet.calendar.EventEndDateException;
import com.liferay.portlet.calendar.EventStartDateException;
import com.liferay.portlet.calendar.EventTitleException;
import com.liferay.portlet.calendar.job.CheckEventJob;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.model.impl.CalEventImpl;
import com.liferay.portlet.calendar.service.base.CalEventLocalServiceBaseImpl;
import com.liferay.portlet.calendar.social.CalendarActivityKeys;
import com.liferay.portlet.calendar.util.CalUtil;
import com.liferay.portlet.calendar.util.Indexer;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.util.TimeZoneSensitive;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.mail.internet.InternetAddress;

import javax.portlet.PortletPreferences;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Comment;
import net.fortuna.ical4j.model.property.DateProperty;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;

/**
 * <a href="CalEventLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Samuel Kong
 * @author Ganesh Ram
 * @author Brett Swaim
 *
 */
public class CalEventLocalServiceImpl extends CalEventLocalServiceBaseImpl {

	public CalEvent addEvent(
			long userId, String title, String description, int startDateMonth,
			int startDateDay, int startDateYear, int startDateHour,
			int startDateMinute, int endDateMonth, int endDateDay,
			int endDateYear, int durationHour, int durationMinute,
			boolean allDay, boolean timeZoneSensitive, String type,
			boolean repeating, TZSRecurrence recurrence, String remindBy,
			int firstReminder, int secondReminder,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addEvent(
			null, userId, title, description, startDateMonth, startDateDay,
			startDateYear, startDateHour, startDateMinute, endDateMonth,
			endDateDay, endDateYear, durationHour, durationMinute, allDay,
			timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, serviceContext);
	}

	public CalEvent addEvent(
			String uuid, long userId, String title, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int durationHour,
			int durationMinute, boolean allDay, boolean timeZoneSensitive,
			String type, boolean repeating, TZSRecurrence recurrence,
			String remindBy, int firstReminder, int secondReminder,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Event

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		Date now = new Date();

		Locale locale = null;
		TimeZone timeZone = null;

		if (timeZoneSensitive) {
			locale = user.getLocale();
			timeZone = user.getTimeZone();
		}
		else {
			locale = LocaleUtil.getDefault();
			timeZone = TimeZoneUtil.getDefault();
		}

		Calendar startDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

		startDate.set(Calendar.MONTH, startDateMonth);
		startDate.set(Calendar.DATE, startDateDay);
		startDate.set(Calendar.YEAR, startDateYear);
		startDate.set(Calendar.HOUR_OF_DAY, startDateHour);
		startDate.set(Calendar.MINUTE, startDateMinute);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);

		Calendar endDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

		endDate.set(Calendar.MONTH, endDateMonth);
		endDate.set(Calendar.DATE, endDateDay);
		endDate.set(Calendar.YEAR, endDateYear);
		endDate.set(Calendar.HOUR_OF_DAY, 23);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		endDate.set(Calendar.MILLISECOND, 999);

		if (allDay) {
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);

			durationHour = 24;
			durationMinute = 0;
		}

		validate(
			title, startDateMonth, startDateDay, startDateYear, endDateMonth,
			endDateDay, endDateYear, durationHour, durationMinute, allDay,
			repeating);

		long eventId = counterLocalService.increment();

		CalEvent event = calEventPersistence.create(eventId);

		event.setUuid(uuid);
		event.setGroupId(groupId);
		event.setCompanyId(user.getCompanyId());
		event.setUserId(user.getUserId());
		event.setUserName(user.getFullName());
		event.setCreateDate(now);
		event.setModifiedDate(now);
		event.setTitle(title);
		event.setDescription(description);
		event.setStartDate(startDate.getTime());
		event.setEndDate(endDate.getTime());
		event.setDurationHour(durationHour);
		event.setDurationMinute(durationMinute);
		event.setAllDay(allDay);
		event.setTimeZoneSensitive(timeZoneSensitive);
		event.setType(type);
		event.setRepeating(repeating);
		event.setRecurrenceObj(recurrence);
		event.setRemindBy(remindBy);
		event.setFirstReminder(firstReminder);
		event.setSecondReminder(secondReminder);

		calEventPersistence.update(event, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addEventResources(
				event, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addEventResources(
				event, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Expando

		ExpandoBridge expandoBridge = event.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Social

		socialActivityLocalService.addActivity(
			userId, groupId, CalEvent.class.getName(), eventId,
			CalendarActivityKeys.ADD_EVENT, StringPool.BLANK, 0);

		// Indexer

		try {
			com.liferay.portlet.calendar.util.Indexer.addEvent(
				event.getCompanyId(), event.getGroupId(), userId,
				event.getUserName(), event.getEventId(), title, description,
				event.getModifiedDate(), serviceContext.getTagsEntries(),
				event.getExpandoBridge());
		}
		catch (SearchException se) {
			_log.error("Indexing " + eventId, se);
		}

		// Pool

		CalEventLocalUtil.clearEventsPool(event.getGroupId());

		return event;
	}

	public void addEventResources(
			long eventId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		CalEvent event = calEventPersistence.findByPrimaryKey(eventId);

		addEventResources(
			event, addCommunityPermissions, addGuestPermissions);
	}

	public void addEventResources(
			CalEvent event, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			event.getCompanyId(), event.getGroupId(), event.getUserId(),
			CalEvent.class.getName(), event.getEventId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEventResources(
			long eventId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		CalEvent event = calEventPersistence.findByPrimaryKey(eventId);

		addEventResources(event, communityPermissions, guestPermissions);
	}

	public void addEventResources(
			CalEvent event, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			event.getCompanyId(), event.getGroupId(), event.getUserId(),
			CalEvent.class.getName(), event.getEventId(), communityPermissions,
			guestPermissions);
	}

	public void checkEvents() throws PortalException, SystemException {
		Iterator<CalEvent> itr = calEventFinder.findByRemindBy().iterator();

		while (itr.hasNext()) {
			CalEvent event = itr.next();

			User user = userPersistence.fetchByPrimaryKey(event.getUserId());

			if (user == null) {
				deleteEvent(event);

				continue;
			}

			Calendar now = CalendarFactoryUtil.getCalendar(
				user.getTimeZone(), user.getLocale());

			if (!event.isTimeZoneSensitive()) {
				Calendar temp = CalendarFactoryUtil.getCalendar();

				temp.setTime(Time.getDate(now));

				now = temp;
			}

			Calendar startDate = null;

			if (event.isTimeZoneSensitive()) {
				startDate = CalendarFactoryUtil.getCalendar(
					user.getTimeZone(), user.getLocale());
			}
			else {
				startDate = CalendarFactoryUtil.getCalendar();
			}

			if (event.isRepeating()) {
				double daysToCheck = Math.ceil(
					CalEventImpl.REMINDERS[CalEventImpl.REMINDERS.length - 1] /
					Time.DAY);

				Calendar cal = (Calendar)now.clone();

				for (int i = 0; i <= daysToCheck; i++) {
					Recurrence recurrence = event.getRecurrenceObj();

					Calendar tzICal = CalendarFactoryUtil.getCalendar(
						cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
						cal.get(Calendar.DATE));

					Calendar recurrenceCal = getRecurrenceCal(
						cal, tzICal, event);

					if (recurrence.isInRecurrence(recurrenceCal)) {
						remindUser(event, user, recurrenceCal, now);
					}

					cal.add(Calendar.DAY_OF_YEAR, 1);
				}
			}
			else {
				startDate.setTime(event.getStartDate());

				remindUser(event, user, startDate, now);
			}
		}
	}

	public void deleteEvent(long eventId)
		throws PortalException, SystemException {

		CalEvent event = calEventPersistence.findByPrimaryKey(eventId);

		deleteEvent(event);
	}

	public void deleteEvent(CalEvent event)
		throws PortalException, SystemException {

		// Pool

		CalEventLocalUtil.clearEventsPool(event.getGroupId());

		// Social

		socialActivityLocalService.deleteActivities(
			CalEvent.class.getName(), event.getEventId());

		// Expando

		expandoValueLocalService.deleteValues(
			CalEvent.class.getName(), event.getEventId());

		// Resources

		resourceLocalService.deleteResource(
			event.getCompanyId(), CalEvent.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, event.getEventId());

		// Event

		calEventPersistence.remove(event);
	}

	public void deleteEvents(long groupId)
		throws PortalException, SystemException {

		Iterator<CalEvent> itr = calEventPersistence.findByGroupId(
			groupId).iterator();

		while (itr.hasNext()) {
			CalEvent event = itr.next();

			deleteEvent(event);
		}
	}

	public File exportEvent(long userId, long eventId)
		throws PortalException, SystemException {

		List<CalEvent> events = new ArrayList<CalEvent>();

		CalEvent event = calEventPersistence.findByPrimaryKey(eventId);

		events.add(event);

		return exportICal4j(toICalCalendar(userId, events), null);
	}

	public File exportGroupEvents(long userId, long groupId, String fileName)
		throws PortalException, SystemException {

		List<CalEvent> events = calEventPersistence.findByGroupId(groupId);

		return exportICal4j(toICalCalendar(userId, events), fileName);
	}

	public CalEvent getEvent(long eventId)
		throws PortalException, SystemException {

		return calEventPersistence.findByPrimaryKey(eventId);
	}

	public List<CalEvent> getEvents(
			long groupId, String type, int start, int end)
		throws SystemException {

		if (Validator.isNull(type)) {
			return calEventPersistence.findByGroupId(groupId, start, end);
		}
		else {
			return calEventPersistence.findByG_T(groupId, type, start, end);
		}
	}

	public List<CalEvent> getEvents(long groupId, Calendar cal)
		throws SystemException {

		Map<String, List<CalEvent>> eventsPool =
			CalEventLocalUtil.getEventsPool(groupId);

		String key = CalUtil.toString(cal);

		List<CalEvent> events = eventsPool.get(key);

		if (events == null) {

			// Time zone sensitive

			List<CalEvent> events1 = calEventFinder.findByG_SD(
				groupId, CalendarUtil.getGTDate(cal),
				CalendarUtil.getLTDate(cal), true);

			// Time zone insensitive

			Calendar tzICal = CalendarFactoryUtil.getCalendar(
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE));

			List<CalEvent> events2 = calEventFinder.findByG_SD(
				groupId, CalendarUtil.getGTDate(tzICal),
				CalendarUtil.getLTDate(tzICal), false);

			// Create new list

			events = new ArrayList<CalEvent>();

			events.addAll(events1);
			events.addAll(events2);

			// Add repeating events

			Iterator<CalEvent> itr = getRepeatingEvents(groupId).iterator();

			while (itr.hasNext()) {
				CalEvent event = itr.next();

				TZSRecurrence recurrence = event.getRecurrenceObj();

				try {

					// LEP-3468

					if ((recurrence.getFrequency() !=
							Recurrence.NO_RECURRENCE) &&
						(recurrence.getInterval() <= 0)) {

						recurrence.setInterval(1);

						event.setRecurrenceObj(recurrence);

						event = calEventPersistence.update(event, false);

						recurrence = event.getRecurrenceObj();
					}

					if (recurrence.isInRecurrence(
							getRecurrenceCal(cal, tzICal, event))) {

						events.add(event);
					}
				}
				catch (Exception e) {
					_log.error(e.getMessage());
				}
			}

			events = new UnmodifiableList<CalEvent>(events);

			eventsPool.put(key, events);
		}

		return events;
	}

	public List<CalEvent> getEvents(long groupId, Calendar cal, String type)
		throws SystemException {

		List<CalEvent> events = getEvents(groupId, cal);

		if (Validator.isNull(type)) {
			return events;
		}
		else {
			events = new ArrayList<CalEvent>(events);

			Iterator<CalEvent> itr = events.iterator();

			while (itr.hasNext()) {
				CalEvent event = itr.next();

				if (!event.getType().equals(type)) {
					itr.remove();
				}
			}

			return events;
		}
	}

	public int getEventsCount(long groupId, String type)
		throws SystemException {

		if (Validator.isNull(type)) {
			return calEventPersistence.countByGroupId(groupId);
		}
		else {
			return calEventPersistence.countByG_T(groupId, type);
		}
	}

	public List<CalEvent> getRepeatingEvents(long groupId)
		throws SystemException {

		Map<String, List<CalEvent>> eventsPool =
			CalEventLocalUtil.getEventsPool(groupId);

		String key = "recurrence";

		List<CalEvent> events = eventsPool.get(key);

		if (events == null) {
			events = calEventPersistence.findByG_R(groupId, true);

			events = new UnmodifiableList<CalEvent>(events);

			eventsPool.put(key, events);
		}

		return events;
	}

	public boolean hasEvents(long groupId, Calendar cal)
		throws SystemException {

		return hasEvents(groupId, cal, null);
	}

	public boolean hasEvents(long groupId, Calendar cal, String type)
		throws SystemException {

		if (getEvents(groupId, cal, type).size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void importICal4j(long userId, long groupId, File file)
		throws PortalException, SystemException {

		try {
			CalendarBuilder builder = new CalendarBuilder();

			net.fortuna.ical4j.model.Calendar calendar = builder.build(
				new FileReader(file));

			Iterator<VEvent> itr = calendar.getComponents(
				Component.VEVENT).iterator();

			while (itr.hasNext()) {
				VEvent vEvent = itr.next();

				importICal4j(userId, groupId, vEvent);
			}
		}
		catch (IOException ioe) {
			throw new SystemException(ioe.getMessage());
		}
		catch (ParserException pe) {
			throw new SystemException(pe.getMessage());
		}
	}

	public void reIndex(long eventId) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		CalEvent event = calEventPersistence.fetchByPrimaryKey(eventId);

		if (event == null) {
			return;
		}

		reIndex(event);
	}

	public void reIndex(CalEvent event) throws SystemException {
		long companyId = event.getCompanyId();
		long groupId = event.getGroupId();
		long userId = event.getUserId();
		long eventId = event.getEventId();
		String userName = event.getUserName();
		String title = event.getTitle();
		String description = event.getDescription();
		Date modifiedDate = event.getModifiedDate();

		String[] tagsEntries = tagsEntryLocalService.getEntryNames(
			CalEvent.class.getName(), eventId);

		try {
			Indexer.updateEvent(
				companyId, groupId, userId, userName, eventId, title,
				description, modifiedDate, tagsEntries,
				event.getExpandoBridge());
		}
		catch (SearchException se) {
			_log.error("Reindexing " + eventId, se);
		}
	}

	public void reIndex(String[] ids) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		long companyId = GetterUtil.getLong(ids[0]);

		try {
			reIndexEvents(companyId);
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public Hits search(
			long companyId, long groupId, long userId, long ownerUserId,
			String keywords, int start, int end)
		throws SystemException {

		try {
			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create();

			contextQuery.addRequiredTerm(Field.PORTLET_ID, Indexer.PORTLET_ID);

			if (groupId > 0) {
				contextQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			}

			if (ownerUserId > 0) {
				contextQuery.addRequiredTerm(Field.USER_ID, ownerUserId);
			}

			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			if (Validator.isNotNull(keywords)) {
				searchQuery.addTerm(Field.USER_NAME, keywords);
				searchQuery.addTerm(Field.TITLE, keywords);
				searchQuery.addTerm(Field.DESCRIPTION, keywords);
				searchQuery.addTerm(Field.TAGS_ENTRIES, keywords);
			}

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			if (searchQuery.clauses().size() > 0) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			return SearchEngineUtil.search(
				companyId, groupId, userId, CalEvent.class.getName(), fullQuery,
				start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public CalEvent updateEvent(
			long userId, long eventId, String title, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int durationHour,
			int durationMinute, boolean allDay, boolean timeZoneSensitive,
			String type, boolean repeating, TZSRecurrence recurrence,
			String remindBy, int firstReminder, int secondReminder,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Event

		User user = userPersistence.findByPrimaryKey(userId);

		Locale locale = null;
		TimeZone timeZone = null;

		if (timeZoneSensitive) {
			locale = user.getLocale();
			timeZone = user.getTimeZone();
		}
		else {
			locale = LocaleUtil.getDefault();
			timeZone = TimeZoneUtil.getDefault();
		}

		Calendar startDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

		startDate.set(Calendar.MONTH, startDateMonth);
		startDate.set(Calendar.DATE, startDateDay);
		startDate.set(Calendar.YEAR, startDateYear);
		startDate.set(Calendar.HOUR_OF_DAY, startDateHour);
		startDate.set(Calendar.MINUTE, startDateMinute);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);

		Calendar endDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

		endDate.set(Calendar.MONTH, endDateMonth);
		endDate.set(Calendar.DATE, endDateDay);
		endDate.set(Calendar.YEAR, endDateYear);
		endDate.set(Calendar.HOUR_OF_DAY, 23);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		endDate.set(Calendar.MILLISECOND, 999);

		if (allDay) {
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);

			durationHour = 24;
			durationMinute = 0;
		}

		validate(
			title, startDateMonth, startDateDay, startDateYear, endDateMonth,
			endDateDay, endDateYear, durationHour, durationMinute, allDay,
			repeating);

		CalEvent event = calEventPersistence.findByPrimaryKey(eventId);

		event.setModifiedDate(new Date());
		event.setTitle(title);
		event.setDescription(description);
		event.setStartDate(startDate.getTime());
		event.setEndDate(endDate.getTime());
		event.setDurationHour(durationHour);
		event.setDurationMinute(durationMinute);
		event.setAllDay(allDay);
		event.setTimeZoneSensitive(timeZoneSensitive);
		event.setType(type);
		event.setRepeating(repeating);
		event.setRecurrenceObj(recurrence);
		event.setRemindBy(remindBy);
		event.setFirstReminder(firstReminder);
		event.setSecondReminder(secondReminder);

		calEventPersistence.update(event, false);

		// Expando

		ExpandoBridge expandoBridge = event.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Social

		socialActivityLocalService.addActivity(
			userId, event.getGroupId(), CalEvent.class.getName(), eventId,
			CalendarActivityKeys.UPDATE_EVENT, StringPool.BLANK, 0);

		// Indexer

		try {
			com.liferay.portlet.calendar.util.Indexer.updateEvent(
				event.getCompanyId(), event.getGroupId(), userId,
				event.getUserName(), eventId, title, description,
				event.getModifiedDate(), serviceContext.getTagsEntries(),
				event.getExpandoBridge());
		}
		catch (SearchException se) {
			_log.error("Indexing " + eventId, se);
		}

		// Pool

		CalEventLocalUtil.clearEventsPool(event.getGroupId());

		return event;
	}

	protected File exportICal4j(
			net.fortuna.ical4j.model.Calendar cal, String fileName)
		throws SystemException {

		OutputStream os = null;

		try {
			String extension = ".ics";

			if (Validator.isNull(fileName)) {
				fileName = "liferay.";
			}
			else {
				int pos = fileName.lastIndexOf(StringPool.PERIOD);

				if (pos != -1) {
					extension = fileName.substring(pos);
					fileName = fileName.substring(0, pos);
				}
			}

			File file = File.createTempFile(fileName, extension);

			os = new BufferedOutputStream(new FileOutputStream(file.getPath()));

			CalendarOutputter calOutput = new CalendarOutputter();

			if (cal.getComponents().isEmpty()) {
				calOutput.setValidating(false);
			}

			calOutput.output(cal, os);

			return file;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new SystemException(e);
		}
		finally {
			ServletResponseUtil.cleanUp(os);
		}
	}

	protected Calendar getRecurrenceCal(
		Calendar cal, Calendar tzICal, CalEvent event) {

		Calendar eventCal = CalendarFactoryUtil.getCalendar();
		eventCal.setTime(event.getStartDate());

		Calendar recurrenceCal = (Calendar)tzICal.clone();
		recurrenceCal.set(
			Calendar.HOUR_OF_DAY, eventCal.get(Calendar.HOUR_OF_DAY));
		recurrenceCal.set(
			Calendar.MINUTE, eventCal.get(Calendar.MINUTE));
		recurrenceCal.set(Calendar.SECOND, 0);
		recurrenceCal.set(Calendar.MILLISECOND, 0);

		if (event.isTimeZoneSensitive()) {
			int gmtDate = eventCal.get(Calendar.DATE);
			long gmtMills = eventCal.getTimeInMillis();

			eventCal.setTimeZone(cal.getTimeZone());

			int tziDate = eventCal.get(Calendar.DATE);
			long tziMills = Time.getDate(eventCal).getTime();

			if (gmtDate != tziDate) {
				int diffDate = 0;

				if (gmtMills > tziMills) {
					diffDate = (int)Math.ceil(
						(double)(gmtMills - tziMills) / Time.DAY);
				}
				else {
					diffDate = (int)Math.floor(
						(double)(gmtMills - tziMills) / Time.DAY);
				}

				recurrenceCal.add(Calendar.DATE, diffDate);
			}
		}

		return recurrenceCal;
	}

	protected void importICal4j(
			long userId, long groupId, VEvent event)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		TimeZone timeZone = user.getTimeZone();

		// X iCal property

		Property timeZoneXProperty = event.getProperty(
			TimeZoneSensitive.PROPERTY_NAME);

		boolean timeZoneXPropertyValue = true;

		if (Validator.isNotNull(timeZoneXProperty) &&
			timeZoneXProperty.getValue().equals("FALSE")) {

			timeZoneXPropertyValue = false;
		}

		// Title

		String title = StringPool.BLANK;

		if (event.getSummary() != null) {
			title = ModelHintsUtil.trimString(
				CalEvent.class.getName(), "title",
				event.getSummary().getValue());
		}

		// Description

		String description = StringPool.BLANK;

		if (event.getDescription() != null) {
			description = event.getDescription().getValue();
		}

		// Start date

		DtStart dtStart = event.getStartDate();

		Calendar startDate = toCalendar(
			dtStart, timeZone, timeZoneXPropertyValue);

		startDate.setTime(dtStart.getDate());

		// End date

		Calendar endDate = null;

		DtEnd dtEnd = event.getEndDate(true);

		RRule rrule = (RRule)event.getProperty(Property.RRULE);

		if (Validator.isNotNull(dtEnd)) {
			endDate = toCalendar(dtEnd, timeZone, timeZoneXPropertyValue);

			endDate.setTime(dtEnd.getDate());
		}
		else {
			endDate = (Calendar)startDate.clone();
			endDate.add(Calendar.DATE, 1);
		}

		// Duration

		long diffMillis = 0;
		long durationHours = 24;
		long durationMins = 0;
		boolean multiDayEvent = false;

		if (Validator.isNotNull(dtEnd)) {
			diffMillis =
				dtEnd.getDate().getTime() - startDate.getTimeInMillis();
			durationHours = diffMillis / Time.HOUR;
			durationMins = (diffMillis / Time.MINUTE) - (durationHours * 60);

			if ((durationHours > 24) ||
				((durationHours == 24) && (durationMins > 0))) {

				durationHours = 24;
				durationMins = 0;
				multiDayEvent = true;
			}
		}

		// All day

		boolean allDay = false;

		if (isICal4jDateOnly(event.getStartDate()) || multiDayEvent) {
			allDay = true;
		}

		// Time zone sensitive

		boolean timeZoneSensitive = true;

		if (allDay || !timeZoneXPropertyValue) {
			timeZoneSensitive = false;
		}

		// Type

		String type = StringPool.BLANK;

		Property comment = event.getProperty(Property.COMMENT);

		if (Validator.isNotNull(comment) &&
			ArrayUtil.contains(CalEventImpl.TYPES, comment.getValue())) {

			type = comment.getValue();
		}

		// Recurrence

		boolean repeating = false;
		TZSRecurrence recurrence = null;

		if (multiDayEvent) {
			repeating = true;

			Calendar recStartCal = CalendarFactoryUtil.getCalendar(timeZone);

			recStartCal.setTime(startDate.getTime());

			com.liferay.portal.kernel.cal.Duration duration =
				new com.liferay.portal.kernel.cal.Duration(1, 0, 0, 0);

			recurrence = new TZSRecurrence(
				recStartCal, duration, Recurrence.DAILY);

			Calendar until = (Calendar) startDate.clone();

			until.setTimeInMillis(until.getTimeInMillis() + diffMillis);

			recurrence.setUntil(until);

			endDate = recurrence.getUntil();
		}
		else if (rrule != null) {
			repeating = true;
			recurrence = toRecurrence(rrule, timeZone, startDate);

			if (recurrence.getUntil() != null) {
				endDate = recurrence.getUntil();
			}
		}

		// Reminder

		String remindBy = "none";
		int firstReminder = 300000;
		int secondReminder = 300000;

		// Permissions

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		addEvent(
			userId, title, description, startDate.get(Calendar.MONTH),
			startDate.get(Calendar.DAY_OF_MONTH), startDate.get(Calendar.YEAR),
			startDate.get(Calendar.HOUR_OF_DAY),
			startDate.get(Calendar.MINUTE), endDate.get(Calendar.MONTH),
			endDate.get(Calendar.DAY_OF_MONTH), endDate.get(Calendar.YEAR),
			(int)durationHours, (int)durationMins, allDay,
			timeZoneSensitive, type, repeating, recurrence, remindBy,
			firstReminder, secondReminder, serviceContext);

	}

	protected boolean isICal4jDateOnly(DateProperty date) {
		if (Validator.isNotNull(date.getParameter(Parameter.VALUE)) &&
			date.getParameter(Parameter.VALUE).getValue().equals("DATE")) {

			return true;
		}

		return false;
	}

	protected void reIndexEvents(long companyId) throws SystemException {
		int count = calEventPersistence.countByCompanyId(companyId);

		int pages = count / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			reIndexEvents(companyId, start, end);
		}
	}

	protected void reIndexEvents(long companyId, int start, int end)
		throws SystemException {

		List<CalEvent> events = calEventPersistence.findByCompanyId(
			companyId, start, end);

		for (CalEvent event : events) {
			long groupId = event.getGroupId();
			long userId = event.getUserId();
			String userName = event.getUserName();
			long eventId = event.getEventId();
			String title = event.getTitle();
			String description = event.getDescription();
			Date modifiedDate = event.getModifiedDate();

			String[] tagsEntries = tagsEntryLocalService.getEntryNames(
				CalEvent.class.getName(), eventId);

			try {
				Indexer.updateEvent(
					companyId, groupId, userId, userName, eventId, title,
					description, modifiedDate, tagsEntries,
					event.getExpandoBridge());
			}
			catch (SearchException se) {
				_log.error("Reindexing " + eventId, se);
			}
		}
	}

	protected void remindUser(CalEvent event, User user, Calendar startDate) {
		String remindBy = event.getRemindBy();

		if (remindBy.equals(CalEventImpl.REMIND_BY_NONE)) {
			return;
		}

		try {
			long ownerId = event.getGroupId();
			int ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
			long plid = PortletKeys.PREFS_PLID_SHARED;
			String portletId = PortletKeys.CALENDAR;

			PortletPreferences preferences =
				portletPreferencesLocalService.getPreferences(
					event.getCompanyId(), ownerId, ownerType, plid, portletId);

			Company company = companyPersistence.findByPrimaryKey(
				user.getCompanyId());

			Contact contact = user.getContact();

			String portletName = PortalUtil.getPortletTitle(
				PortletKeys.CALENDAR, user);

			String fromName = CalUtil.getEmailFromName(preferences);
			String fromAddress = CalUtil.getEmailFromAddress(preferences);

			String toName = user.getFullName();
			String toAddress = user.getEmailAddress();

			if (remindBy.equals(CalEventImpl.REMIND_BY_SMS)) {
				toAddress = contact.getSmsSn();
			}

			String subject = CalUtil.getEmailEventReminderSubject(preferences);
			String body = CalUtil.getEmailEventReminderBody(preferences);

			DateFormat dateFormatDateTime = DateFormats.getDateTime(
				user.getLocale(), user.getTimeZone());

			subject = StringUtil.replace(
				subject,
				new String[] {
					"[$EVENT_START_DATE$]",
					"[$EVENT_TITLE$]",
					"[$FROM_ADDRESS$]",
					"[$FROM_NAME$]",
					"[$PORTAL_URL$]",
					"[$PORTLET_NAME$]",
					"[$TO_ADDRESS$]",
					"[$TO_NAME$]"
				},
				new String[] {
					dateFormatDateTime.format(startDate.getTime()),
					event.getTitle(),
					fromAddress,
					fromName,
					company.getVirtualHost(),
					portletName,
					toAddress,
					toName,
				});

			body = StringUtil.replace(
				body,
				new String[] {
					"[$EVENT_START_DATE$]",
					"[$EVENT_TITLE$]",
					"[$FROM_ADDRESS$]",
					"[$FROM_NAME$]",
					"[$PORTAL_URL$]",
					"[$PORTLET_NAME$]",
					"[$TO_ADDRESS$]",
					"[$TO_NAME$]"
				},
				new String[] {
					dateFormatDateTime.format(startDate.getTime()),
					event.getTitle(),
					fromAddress,
					fromName,
					company.getVirtualHost(),
					portletName,
					toAddress,
					toName,
				});

			if (remindBy.equals(CalEventImpl.REMIND_BY_EMAIL) ||
				remindBy.equals(CalEventImpl.REMIND_BY_SMS)) {

				InternetAddress from = new InternetAddress(
					fromAddress, fromName);

				InternetAddress to = new InternetAddress(toAddress, toName);

				MailMessage message = new MailMessage(
					from, to, subject, body, true);

				mailService.sendEmail(message);
			}
			else if (remindBy.equals(CalEventImpl.REMIND_BY_AIM) &&
					 Validator.isNotNull(contact.getAimSn())) {

				AIMConnector.send(contact.getAimSn(), body);
			}
			else if (remindBy.equals(CalEventImpl.REMIND_BY_ICQ) &&
					 Validator.isNotNull(contact.getIcqSn())) {

				ICQConnector.send(contact.getIcqSn(), body);
			}
			else if (remindBy.equals(CalEventImpl.REMIND_BY_MSN) &&
					 Validator.isNotNull(contact.getMsnSn())) {

				MSNConnector.send(contact.getMsnSn(), body);
			}
			else if (remindBy.equals(CalEventImpl.REMIND_BY_YM) &&
					 Validator.isNotNull(contact.getYmSn())) {

				YMConnector.send(contact.getYmSn(), body);
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	protected void remindUser(
		CalEvent event, User user, Calendar startDate, Calendar now) {

		long diff =
			(startDate.getTime().getTime() - now.getTime().getTime()) /
			CheckEventJob.INTERVAL;

		if ((diff == (event.getFirstReminder() / CheckEventJob.INTERVAL)) ||
			(diff ==
				(event.getSecondReminder() / CheckEventJob.INTERVAL))) {

			remindUser(event, user, startDate);
		}
	}

	protected Calendar toCalendar(
		DateProperty date, TimeZone timeZone, boolean timeZoneSensitive) {

		Calendar cal = null;

		if (isICal4jDateOnly(date)) {
			cal = Calendar.getInstance();
		}
		else if (!timeZoneSensitive) {
			cal = Calendar.getInstance(TimeZone.getTimeZone(StringPool.UTC));
		}
		else {
			cal = Calendar.getInstance(timeZone);
		}

		return cal;
	}

	protected int toCalendarWeekDay(WeekDay weekDay) {
		int dayOfWeeek = 0;

		if (weekDay.getDay().equals(WeekDay.SU.getDay())) {
			dayOfWeeek = Calendar.SUNDAY;
		}
		else if (weekDay.getDay().equals(WeekDay.MO.getDay())) {
			dayOfWeeek = Calendar.MONDAY;
		}
		else if (weekDay.getDay().equals(WeekDay.TU.getDay())) {
			dayOfWeeek = Calendar.TUESDAY;
		}
		else if (weekDay.getDay().equals(WeekDay.WE.getDay())) {
			dayOfWeeek = Calendar.WEDNESDAY;
		}
		else if (weekDay.getDay().equals(WeekDay.TH.getDay())) {
			dayOfWeeek = Calendar.THURSDAY;
		}
		else if (weekDay.getDay().equals(WeekDay.FR.getDay())) {
			dayOfWeeek = Calendar.FRIDAY;
		}
		else if (weekDay.getDay().equals(WeekDay.SA.getDay())) {
			dayOfWeeek = Calendar.SATURDAY;
		}

		return dayOfWeeek;
	}

	protected net.fortuna.ical4j.model.Calendar toICalCalendar(
		long userId, List<CalEvent> events)
		throws PortalException, SystemException {

		net.fortuna.ical4j.model.Calendar iCal =
			new net.fortuna.ical4j.model.Calendar();

		ProdId prodId = new ProdId(
			"-//Liferay Inc//Liferay Portal " + ReleaseInfo.getVersion() +
			"//EN");

		PropertyList props = iCal.getProperties();

		props.add(prodId);
		props.add(Version.VERSION_2_0);
		props.add(CalScale.GREGORIAN);

		User user = userPersistence.findByPrimaryKey(userId);
		TimeZone timeZone = user.getTimeZone();

		List<VEvent> components = iCal.getComponents();

		Iterator<CalEvent> itr = events.iterator();

		while (itr.hasNext()) {
			CalEvent event = itr.next();

			components.add(toICalVEvent(event, timeZone));
		}

		return iCal;
	}

	protected Recur toICalRecurrence(TZSRecurrence recurrence) {
		Recur recur = null;

		int recurrenceType = recurrence.getFrequency();

		int interval = recurrence.getInterval();

		if (recurrenceType == Recurrence.DAILY) {
			recur = new Recur(Recur.DAILY, -1);

			if (interval >= 1) {
				recur.setInterval(interval);
			}

			DayAndPosition[] byDay = recurrence.getByDay();

			if (byDay != null) {
				for (int i = 0; i < byDay.length; i++) {
					WeekDay weekDay = toICalWeekDay(byDay[i].getDayOfWeek());

					recur.getDayList().add(weekDay);
				}
			}

		}
		else if (recurrenceType == Recurrence.WEEKLY) {
			recur = new Recur(Recur.WEEKLY, -1);

			recur.setInterval(interval);

			DayAndPosition[] byDay = recurrence.getByDay();

			if (byDay != null) {
				for (int i = 0; i < byDay.length; i++) {
					WeekDay weekDay = toICalWeekDay(byDay[i].getDayOfWeek());

					recur.getDayList().add(weekDay);
				}
			}
		}
		else if (recurrenceType == Recurrence.MONTHLY) {
			recur = new Recur(Recur.MONTHLY, -1);

			recur.setInterval(interval);

			int[] byMonthDay = recurrence.getByMonthDay();

			if (byMonthDay != null) {
				Integer monthDay = new Integer(byMonthDay[0]);

				recur.getMonthDayList().add(monthDay);
			}
			else if (recurrence.getByDay() != null) {
				DayAndPosition[] byDay = recurrence.getByDay();

				WeekDay weekDay = toICalWeekDay(byDay[0].getDayOfWeek());

				recur.getDayList().add(weekDay);

				Integer position = new Integer(byDay[0].getDayPosition());

				recur.getSetPosList().add(position);
			}
		}
		else if (recurrenceType == Recurrence.YEARLY) {
			recur = new Recur(Recur.YEARLY, -1);

			recur.setInterval(interval);
		}

		Calendar until = recurrence.getUntil();

		if (until != null) {
			DateTime dateTime = new DateTime(until.getTime());

			recur.setUntil(dateTime);
		}

		return recur;
	}

	protected VEvent toICalVEvent(CalEvent event, TimeZone timeZone) {
		VEvent vEvent = new VEvent();

		PropertyList eventProps = vEvent.getProperties();

		// UID

		Uid uid = new Uid(PortalUUIDUtil.generate());

		eventProps.add(uid);

		if (event.isAllDay()) {

			// Start date

			DtStart dtStart = new DtStart(
				new net.fortuna.ical4j.model.Date(event.getStartDate()));

			eventProps.add(dtStart);

			Property dtStartProperty = eventProps.getProperty(Property.DTSTART);

			dtStartProperty.getParameters().add(Value.DATE);
		}
		else {

			// Start date

			DtStart dtStart = new DtStart(new DateTime(event.getStartDate()));

			eventProps.add(dtStart);

			// Duration

			Duration duration = new Duration(
				new Dur(
					0, event.getDurationHour(), event.getDurationMinute(), 0));

			eventProps.add(duration);
		}

		// Summary

		Summary summary = new Summary(event.getTitle());

		eventProps.add(summary);

		// Description

		Description description = new Description(event.getDescription());

		eventProps.add(description);

		// Comment

		Comment comment = new Comment(event.getType());

		eventProps.add(comment);

		// Recurrence rule

		if (event.isRepeating()) {
			Recur recur = toICalRecurrence(event.getRecurrenceObj());

			RRule rRule = new RRule(recur);

			eventProps.add(rRule);
		}

		// Time zone sensitive

		if (!event.getTimeZoneSensitive()) {
			eventProps.add(new TimeZoneSensitive("FALSE"));
		}

		return vEvent;
	}

	protected WeekDay toICalWeekDay(int dayOfWeek) {
		WeekDay weekDay = null;

		if (dayOfWeek == Calendar.SUNDAY) {
			weekDay = WeekDay.SU;
		}
		else if (dayOfWeek == Calendar.MONDAY) {
			weekDay = WeekDay.MO;
		}
		else if (dayOfWeek == Calendar.TUESDAY) {
			weekDay = WeekDay.TU;
		}
		else if (dayOfWeek == Calendar.WEDNESDAY) {
			weekDay = WeekDay.WE;
		}
		else if (dayOfWeek == Calendar.THURSDAY) {
			weekDay = WeekDay.TH;
		}
		else if (dayOfWeek == Calendar.FRIDAY) {
			weekDay = WeekDay.FR;
		}
		else if (dayOfWeek == Calendar.SATURDAY) {
			weekDay = WeekDay.SA;
		}

		return weekDay;
	}

	protected TZSRecurrence toRecurrence(
		RRule rRule, TimeZone timeZone, Calendar startDate) {

		Recur recur = rRule.getRecur();

		Calendar recStartCal = CalendarFactoryUtil.getCalendar(timeZone);

		recStartCal.setTime(startDate.getTime());

		TZSRecurrence recurrence = new TZSRecurrence(
			recStartCal,
			new com.liferay.portal.kernel.cal.Duration(1, 0, 0, 0));

		recurrence.setWeekStart(Calendar.SUNDAY);

		if (recur.getInterval() > 1) {
			recurrence.setInterval(recur.getInterval());
		}

		Calendar until = Calendar.getInstance(timeZone);

		String frequency = recur.getFrequency();

		if (Validator.isNotNull(recur.getUntil())) {
			until.setTime(recur.getUntil());

			recurrence.setUntil(until);
		}
		else if (rRule.getValue().indexOf("COUNT") >= 0) {
			until.setTimeInMillis(startDate.getTimeInMillis());

			int addField = 0;

			if (Recur.DAILY.equals(frequency)) {
				addField = Calendar.DAY_OF_YEAR;
			}
			else if (Recur.WEEKLY.equals(frequency)) {
				addField = Calendar.WEEK_OF_YEAR;
			}
			else if (Recur.MONTHLY.equals(frequency)) {
				addField = Calendar.MONTH;
			}
			else if (Recur.YEARLY.equals(frequency)) {
				addField = Calendar.YEAR;
			}

			int addAmount = recurrence.getInterval() * recur.getCount();

			until.add(addField, addAmount);
			until.add(Calendar.DAY_OF_YEAR, -1);

			recurrence.setUntil(until);
		}

		if (Recur.DAILY.equals(frequency)) {
			recurrence.setFrequency(Recurrence.DAILY);

			List<DayAndPosition> dayPosList = new ArrayList<DayAndPosition>();

			Iterator<WeekDay> itr = recur.getDayList().iterator();

			while (itr.hasNext()) {
				WeekDay weekDay = itr.next();

				dayPosList.add(
					new DayAndPosition(toCalendarWeekDay(weekDay), 0));
			}

			if (!dayPosList.isEmpty()) {
				recurrence.setByDay(
					dayPosList.toArray(new DayAndPosition[dayPosList.size()]));
			}
		}
		else if (Recur.WEEKLY.equals(frequency)) {
			recurrence.setFrequency(Recurrence.WEEKLY);

			List<DayAndPosition> dayPosList = new ArrayList<DayAndPosition>();

			Iterator<WeekDay> itr = recur.getDayList().iterator();

			while (itr.hasNext()) {
				WeekDay weekDay = itr.next();

				dayPosList.add(
					new DayAndPosition(toCalendarWeekDay(weekDay), 0));
			}

			if (!dayPosList.isEmpty()) {
				recurrence.setByDay(
					dayPosList.toArray(new DayAndPosition[dayPosList.size()]));
			}
		}
		else if (Recur.MONTHLY.equals(frequency)) {
			recurrence.setFrequency(Recurrence.MONTHLY);

			Iterator<Integer> monthDayListItr =
				recur.getMonthDayList().iterator();

			if (monthDayListItr.hasNext()) {
				Integer monthDay = monthDayListItr.next();

				recurrence.setByMonthDay(new int[] {monthDay.intValue()});
			}

			Iterator<WeekDay> dayListItr = recur.getDayList().iterator();

			if (dayListItr.hasNext()) {
				WeekDay weekDay = dayListItr.next();

				DayAndPosition[] dayPos = {
					new DayAndPosition(toCalendarWeekDay(weekDay),
					weekDay.getOffset())
				};

				recurrence.setByDay(dayPos);
			}
		}
		else if (Recur.YEARLY.equals(frequency)) {
			recurrence.setFrequency(Recurrence.YEARLY);
		}

		return recurrence;
	}

	protected void validate(
			String title, int startDateMonth, int startDateDay,
			int startDateYear, int endDateMonth, int endDateDay,
			int endDateYear, int durationHour, int durationMinute,
			boolean allDay, boolean repeating)
		throws PortalException {

		if (Validator.isNull(title)) {
			throw new EventTitleException();
		}
		else if (!Validator.isDate(
				startDateMonth, startDateDay, startDateYear)) {

			throw new EventStartDateException();
		}
		else if (!Validator.isDate(endDateMonth, endDateDay, endDateYear)) {
			throw new EventEndDateException();
		}

		if (!allDay && durationHour <= 0 && durationMinute <= 0) {
			throw new EventDurationException();
		}

		Calendar startDate = CalendarFactoryUtil.getCalendar(
			startDateYear, startDateMonth, startDateDay);

		Calendar endDate = CalendarFactoryUtil.getCalendar(
			endDateYear, endDateMonth, endDateDay);

		if (repeating && startDate.after(endDate)) {
			throw new EventEndDateException();
		}
	}

	private static Log _log =
		 LogFactoryUtil.getLog(CalEventLocalServiceImpl.class);

}