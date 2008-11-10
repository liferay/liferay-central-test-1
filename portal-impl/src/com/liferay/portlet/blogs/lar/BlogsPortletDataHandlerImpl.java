/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.lar;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.lar.PortletDataContext;
import com.liferay.portal.lar.PortletDataException;
import com.liferay.portal.lar.PortletDataHandler;
import com.liferay.portal.lar.PortletDataHandlerBoolean;
import com.liferay.portal.lar.PortletDataHandlerControl;
import com.liferay.portal.lar.PortletDataHandlerKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryUtil;

import java.util.Calendar;
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * <a href="BlogsPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Raymond Augé
 *
 */
public class BlogsPortletDataHandlerImpl implements PortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			if (!context.addPrimaryKey(
					BlogsPortletDataHandlerImpl.class, "deleteData")) {

				BlogsEntryLocalServiceUtil.deleteEntries(context.getGroupId());
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("blogs-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			List<BlogsEntry> entries = BlogsEntryUtil.findByGroupId(
				context.getGroupId());

			for (BlogsEntry entry : entries) {
				exportEntry(context, root, entry);
			}

			return doc.formattedString();
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_entries, _comments, _ratings, _tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_entries, _comments, _ratings, _tags, _wordpress
		};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws PortletDataException {

		try {
			Document doc = SAXReaderUtil.read(data);

			Element root = doc.getRootElement();

			List<Element> entryEls = root.elements("entry");

			for (Element entryEl : entryEls) {
				String path = entryEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				BlogsEntry entry = (BlogsEntry)context.getZipEntryAsObject(
					path);

				importEntry(context, entry);
			}

			if (context.getBooleanParameter(_NAMESPACE, "wordpress")) {
				WordPressImporter.importData(context);
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public boolean isPublishToLiveByDefault() {
		return false;
	}

	protected void exportEntry(
			PortletDataContext context, Element root, BlogsEntry entry)
		throws PortalException, SystemException {

		if (!context.isWithinDateRange(entry.getModifiedDate())) {
			return;
		}

		String path = getEntryPath(context, entry);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element entryEl = root.addElement("entry");

		entryEl.addAttribute("path", path);

		if (context.getBooleanParameter(_NAMESPACE, "comments")) {
			context.addComments(BlogsEntry.class, entry.getEntryId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.addRatingsEntries(BlogsEntry.class, entry.getEntryId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			context.addTagsEntries(BlogsEntry.class, entry.getEntryId());
		}

		entry.setUserUuid(entry.getUserUuid());

		context.addZipEntry(path, entry);
	}

	protected String getEntryPath(
		PortletDataContext context, BlogsEntry entry) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.BLOGS));
		sb.append("/entries/");
		sb.append(entry.getEntryId());
		sb.append(".xml");

		return sb.toString();
	}

	protected void importEntry(PortletDataContext context, BlogsEntry entry)
		throws Exception {

		long userId = context.getUserId(entry.getUserUuid());

		Calendar displayDateCal = CalendarFactoryUtil.getCalendar();

		displayDateCal.setTime(entry.getDisplayDate());

		int displayDateMonth = displayDateCal.get(Calendar.MONTH);
		int displayDateDay = displayDateCal.get(Calendar.DATE);
		int displayDateYear = displayDateCal.get(Calendar.YEAR);
		int displayDateHour = displayDateCal.get(Calendar.HOUR);
		int displayDateMinute = displayDateCal.get(Calendar.MINUTE);

		if (displayDateCal.get(Calendar.AM_PM) == Calendar.PM) {
			displayDateHour += 12;
		}

		boolean draft = entry.isDraft();
		boolean allowTrackbacks = entry.isAllowTrackbacks();
		String[] trackbacks = StringUtil.split(entry.getTrackbacks());

		String[] tagsEntries = null;

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			tagsEntries = context.getTagsEntries(
				BlogsEntry.class, entry.getEntryId());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setPlid(context.getPlid());
		serviceContext.setTagsEntries(tagsEntries);

		BlogsEntry existingEntry = null;

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

			existingEntry = BlogsEntryUtil.fetchByUUID_G(
				entry.getUuid(), context.getGroupId());

			if (existingEntry == null) {
				existingEntry = BlogsEntryLocalServiceUtil.addEntry(
					entry.getUuid(), userId, entry.getTitle(),
					entry.getContent(), displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					draft, allowTrackbacks, trackbacks, serviceContext);
			}
			else {
				existingEntry = BlogsEntryLocalServiceUtil.updateEntry(
					userId, existingEntry.getEntryId(), entry.getTitle(),
					entry.getContent(), displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					draft, allowTrackbacks, trackbacks, serviceContext);
			}
		}
		else {
			existingEntry = BlogsEntryLocalServiceUtil.addEntry(
				userId, entry.getTitle(), entry.getContent(),
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, draft, allowTrackbacks,
				trackbacks, serviceContext);
		}

		if (context.getBooleanParameter(_NAMESPACE, "comments")) {
			context.importComments(
				BlogsEntry.class, entry.getEntryId(),
				existingEntry.getEntryId(), context.getGroupId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.importRatingsEntries(
				BlogsEntry.class, entry.getEntryId(),
				existingEntry.getEntryId());
		}
	}

	private static final String _NAMESPACE = "blogs";

	private static final PortletDataHandlerBoolean _entries =
		new PortletDataHandlerBoolean(_NAMESPACE, "entries", true, true);

	private static final PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static final PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static final PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static final PortletDataHandlerBoolean _wordpress =
		new PortletDataHandlerBoolean(_NAMESPACE, "wordpress");

}