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

package com.liferay.portlet.blogs.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.blogs.EntryContentException;
import com.liferay.portlet.blogs.EntryDisplayDateException;
import com.liferay.portlet.blogs.EntryTitleException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.base.BlogsEntryLocalServiceBaseImpl;
import com.liferay.portlet.blogs.social.BlogsActivityKeys;
import com.liferay.portlet.blogs.util.Indexer;
import com.liferay.util.Normalizer;
import com.liferay.util.SetUtil;

import java.io.IOException;
import java.io.StringReader;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="BlogsEntryLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Wilson S. Man
 * @author Raymond Augé
 *
 */
public class BlogsEntryLocalServiceImpl extends BlogsEntryLocalServiceBaseImpl {

	public BlogsEntry addEntry(
			String title, String content, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, boolean draft, boolean allowTrackbacks,
			String[] trackbacks, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addEntry(
			null, title, content, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute, draft,
			allowTrackbacks, trackbacks, serviceContext);
	}

	public BlogsEntry addEntry(
			String uuid, String title, String content, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, boolean draft, boolean allowTrackbacks,
			String[] trackbacks, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		long userId = serviceContext.getUserId();
		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			new EntryDisplayDateException());

		Date now = new Date();

		validate(title, content);

		long entryId = counterLocalService.increment();

		BlogsEntry entry = blogsEntryPersistence.create(entryId);

		entry.setUuid(uuid);
		entry.setGroupId(groupId);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(now);
		entry.setModifiedDate(now);
		entry.setTitle(title);
		entry.setUrlTitle(getUniqueUrlTitle(entryId, groupId, title));
		entry.setContent(content);
		entry.setDisplayDate(displayDate);
		entry.setDraft(draft);
		entry.setAllowTrackbacks(allowTrackbacks);

		blogsEntryPersistence.update(entry, false);

		// Resources

		if ((serviceContext.getAddCommunityPermissions() != null) &&
			(serviceContext.getAddGuestPermissions() != null)) {

			addEntryResources(
				entry, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addEntryResources(
				entry, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Statistics

		if (!draft) {
			blogsStatsUserLocalService.updateStatsUser(groupId, userId, now);
		}

		// Social

		if (!draft) {
			socialActivityLocalService.addActivity(
				userId, groupId, BlogsEntry.class.getName(), entryId,
				BlogsActivityKeys.ADD_ENTRY, StringPool.BLANK, 0);
		}

		// Tags

		updateTagsAsset(userId, entry, serviceContext.getTagsEntries());

		// Indexer

		try {
			if (!draft) {
				Indexer.addEntry(
					entry.getCompanyId(), entry.getGroupId(), userId,
					entry.getUserName(), entryId, title, content,
					serviceContext.getTagsEntries(), entry.getExpandoBridge());
			}
		}
		catch (SearchException se) {
			_log.error("Indexing " + entryId, se);
		}

		// Ping

		if (!draft) {
			pingGoogle(entry, serviceContext);
			pingTrackbacks(entry, trackbacks, false, serviceContext);
		}

		return entry;
	}

	public void addEntryResources(
			long entryId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		addEntryResources(entry, addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			BlogsEntry entry, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			BlogsEntry.class.getName(), entry.getEntryId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			long entryId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		addEntryResources(entry, communityPermissions, guestPermissions);
	}

	public void addEntryResources(
			BlogsEntry entry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			BlogsEntry.class.getName(), entry.getEntryId(),
			communityPermissions, guestPermissions);
	}

	public void deleteEntries(long groupId)
		throws PortalException, SystemException {

		for (BlogsEntry entry : blogsEntryPersistence.findByGroupId(groupId)) {
			deleteEntry(entry);
		}
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	public void deleteEntry(BlogsEntry entry)
		throws PortalException, SystemException {

		// Indexer

		try {
			Indexer.deleteEntry(entry.getCompanyId(), entry.getEntryId());
		}
		catch (SearchException se) {
			_log.error("Deleting index " + entry.getEntryId(), se);
		}

		// Tags

		tagsAssetLocalService.deleteAsset(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Social

		socialActivityLocalService.deleteActivities(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Resources

		resourceLocalService.deleteResource(
			entry.getCompanyId(), BlogsEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, entry.getEntryId());

		// Entry

		blogsEntryPersistence.remove(entry);

		// Statistics

		blogsStatsUserLocalService.updateStatsUser(
			entry.getGroupId(), entry.getUserId());
	}

	public List<BlogsEntry> getCompanyEntries(
			long companyId, int start, int end)
		throws SystemException {

		return blogsEntryPersistence.findByCompanyId(companyId, start, end);
	}

	public List<BlogsEntry> getCompanyEntries(
			long companyId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return blogsEntryPersistence.findByCompanyId(
			companyId, start, end, obc);
	}

	public List<BlogsEntry> getCompanyEntries(
			long companyId, boolean draft, int start, int end)
		throws SystemException {

		return blogsEntryPersistence.findByC_D_D(
			companyId, new Date(), draft, start, end);
	}

	public List<BlogsEntry> getCompanyEntries(
			long companyId, boolean draft, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return blogsEntryPersistence.findByC_D_D(
			companyId, new Date(), draft, start, end, obc);
	}

	public int getCompanyEntriesCount(long companyId) throws SystemException {
		return blogsEntryPersistence.countByCompanyId(companyId);
	}

	public int getCompanyEntriesCount(long companyId, boolean draft)
		throws SystemException {

		return blogsEntryPersistence.countByC_D_D(companyId, new Date(), draft);
	}

	public BlogsEntry[] getEntriesPrevAndNext(long entryId)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		return blogsEntryPersistence.findByGroupId_PrevAndNext(
			entry.getEntryId(), entry.getGroupId(), null);
	}

	public BlogsEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return blogsEntryPersistence.findByPrimaryKey(entryId);
	}

	public BlogsEntry getEntry(long groupId, String urlTitle)
		throws PortalException, SystemException {

		return blogsEntryPersistence.findByG_UT(groupId, urlTitle);
	}

	public List<BlogsEntry> getGroupEntries(long groupId, int start, int end)
		throws SystemException {

		return blogsEntryPersistence.findByGroupId(groupId, start, end);
	}

	public List<BlogsEntry> getGroupEntries(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return blogsEntryPersistence.findByGroupId(groupId, start, end, obc);
	}

	public List<BlogsEntry> getGroupEntries(
			long groupId, boolean draft, int start, int end)
		throws SystemException {

		return blogsEntryPersistence.findByG_D_D(
			groupId, new Date(), draft, start, end);
	}

	public List<BlogsEntry> getGroupEntries(
			long groupId, boolean draft, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return blogsEntryPersistence.findByG_D_D(
			groupId, new Date(), draft, start, end, obc);
	}

	public int getGroupEntriesCount(long groupId) throws SystemException {
		return blogsEntryPersistence.countByGroupId(groupId);
	}

	public int getGroupEntriesCount(long groupId, boolean draft)
		throws SystemException {

		return blogsEntryPersistence.countByG_D_D(groupId, new Date(), draft);
	}

	public List<BlogsEntry> getGroupUserEntries(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return blogsEntryPersistence.findByG_U(groupId, userId, start, end);
	}

	public List<BlogsEntry> getGroupUserEntries(
			long groupId, long userId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return blogsEntryPersistence.findByG_U(
			groupId, userId, start, end, obc);
	}

	public List<BlogsEntry> getGroupUserEntries(
			long groupId, long userId, boolean draft, int start, int end)
		throws SystemException {

		return blogsEntryPersistence.findByG_U_D_D(
			groupId, userId, new Date(), draft, start, end);
	}

	public List<BlogsEntry> getGroupUserEntries(
			long groupId, long userId, boolean draft, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return blogsEntryPersistence.findByG_U_D_D(
			groupId, userId, new Date(), draft, start, end, obc);
	}

	public int getGroupUserEntriesCount(long groupId, long userId)
		throws SystemException {

		return blogsEntryPersistence.countByG_U(groupId, userId);
	}

	public int getGroupUserEntriesCount(
			long groupId, long userId, boolean draft)
		throws SystemException {

		return blogsEntryPersistence.countByG_U_D_D(
			groupId, userId, new Date(), draft);
	}

	public List<BlogsEntry> getNoAssetEntries() throws SystemException {
		return blogsEntryFinder.findByNoAssets();
	}

	public List<BlogsEntry> getOrganizationEntries(
			long organizationId, boolean draft, int start, int end)
		throws SystemException {

		return blogsEntryFinder.findByOrganizationId(
			organizationId, new Date(), draft, start, end);
	}

	public int getOrganizationEntriesCount(long organizationId, boolean draft)
		throws SystemException {

		return blogsEntryFinder.countByOrganizationId(
			organizationId, new Date(), draft);
	}

	public String getUrlTitle(long entryId, String title) {
		String urlTitle = String.valueOf(entryId);

		title = title.trim().toLowerCase();

		if (Validator.isNull(title) || Validator.isNumber(title) ||
			title.equals("rss")) {

			return urlTitle;
		}

		title = Normalizer.normalizeToAscii(title);

		char[] urlTitleCharArray = title.toCharArray();

		for (int i = 0; i < urlTitleCharArray.length; i++) {
			char oldChar = urlTitleCharArray[i];

			char newChar = oldChar;

			if ((oldChar == CharPool.DASH) ||
				(Validator.isChar(oldChar)) || (Validator.isDigit(oldChar))) {

			}
			else if (ArrayUtil.contains(_URL_TITLE_REPLACE_CHARS, oldChar)) {
				newChar = CharPool.DASH;
			}
			else {
				return urlTitle;
			}

			if (oldChar != newChar) {
				urlTitleCharArray[i] = newChar;
			}
		}

		urlTitle = new String(urlTitleCharArray);

		return urlTitle;
	}

	public void reIndex(long entryId) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		BlogsEntry entry = blogsEntryPersistence.fetchByPrimaryKey(entryId);

		if (entry == null) {
			return;
		}

		long companyId = entry.getCompanyId();
		long groupId = entry.getGroupId();
		long userId = entry.getUserId();
		String userName = entry.getUserName();
		String title = entry.getTitle();
		String content = entry.getContent();

		String[] tagsEntries = tagsEntryLocalService.getEntryNames(
			BlogsEntry.class.getName(), entryId);

		try {
			Indexer.updateEntry(
				companyId, groupId, userId, userName, entryId, title, content,
				tagsEntries, entry.getExpandoBridge());
		}
		catch (SearchException se) {
			_log.error("Reindexing " + entryId, se);
		}
	}

	public void reIndex(String[] ids) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		long companyId = GetterUtil.getLong(ids[0]);

		try {
			for (BlogsEntry entry :
					blogsEntryPersistence.findByCompanyId(companyId)) {

				long groupId = entry.getGroupId();
				long userId = entry.getUserId();
				String userName = entry.getUserName();
				long entryId = entry.getEntryId();
				String title = entry.getTitle();
				String content = entry.getContent();

				String[] tagsEntries = tagsEntryLocalService.getEntryNames(
					BlogsEntry.class.getName(), entryId);

				try {
					Indexer.updateEntry(
						companyId, groupId, userId, userName, entryId, title,
						content, tagsEntries, entry.getExpandoBridge());
				}
				catch (SearchException se) {
					_log.error("Reindexing " + entryId, se);
				}
			}
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
				searchQuery.addTerm(Field.CONTENT, keywords);
				searchQuery.addTerm(Field.TAGS_ENTRIES, keywords);
			}

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			if (searchQuery.clauses().size() > 0) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			return SearchEngineUtil.search(
				companyId, groupId, userId, BlogsEntry.class.getName(),
				fullQuery, start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public BlogsEntry updateEntry(
			long entryId, String title, String content, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, boolean draft, boolean allowTrackbacks,
			String[] trackbacks, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		long userId = serviceContext.getUserId();
		User user = userPersistence.findByPrimaryKey(userId);

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			new EntryDisplayDateException());

		validate(title, content);

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		String oldUrlTitle = entry.getUrlTitle();
		boolean oldDraft = entry.isDraft();

		entry.setModifiedDate(new Date());
		entry.setTitle(title);
		entry.setUrlTitle(
			getUniqueUrlTitle(entryId, entry.getGroupId(), title));
		entry.setContent(content);
		entry.setDisplayDate(displayDate);
		entry.setDraft(draft);
		entry.setAllowTrackbacks(allowTrackbacks);

		blogsEntryPersistence.update(entry, false);

		// Statistics

		if (!draft) {
			blogsStatsUserLocalService.updateStatsUser(
				entry.getGroupId(), entry.getUserId(), displayDate);
		}

		// Social

		if (oldDraft && !draft) {
			socialActivityLocalService.addActivity(
				userId, entry.getGroupId(), BlogsEntry.class.getName(), entryId,
				BlogsActivityKeys.ADD_ENTRY, StringPool.BLANK, 0);
		}

		// Tags

		updateTagsAsset(userId, entry, serviceContext.getTagsEntries());

		// Indexer

		try {
			if (!draft) {
				Indexer.updateEntry(
					entry.getCompanyId(), entry.getGroupId(), userId,
					entry.getUserName(), entryId, title, content,
					serviceContext.getTagsEntries(), entry.getExpandoBridge());
			}
		}
		catch (SearchException se) {
			_log.error("Indexing " + entryId, se);
		}

		// Ping

		if (!draft) {
			pingGoogle(entry, serviceContext);

			String urlTitle = entry.getUrlTitle();

			if (!oldDraft && !oldUrlTitle.equals(urlTitle)) {
				pingTrackbacks(entry, trackbacks, true, serviceContext);
			}
			else {
				pingTrackbacks(entry, trackbacks, false, serviceContext);
			}
		}

		return entry;
	}

	public void updateTagsAsset(
			long userId, BlogsEntry entry, String[] tagsEntries)
		throws PortalException, SystemException {

		tagsAssetLocalService.updateAsset(
			userId, entry.getGroupId(), BlogsEntry.class.getName(),
			entry.getEntryId(), null, tagsEntries, null, null, null, null,
			ContentTypes.TEXT_HTML, entry.getTitle(), null, null, null, 0, 0,
			null, false);
	}

	protected String getUniqueUrlTitle(
			long entryId, long groupId, String title)
		throws SystemException {

		String urlTitle = getUrlTitle(entryId, title);

		String newUrlTitle = new String(urlTitle);

		for (int i = 1;; i++) {
			BlogsEntry entry = blogsEntryPersistence.fetchByG_UT(
				groupId, newUrlTitle);

			if ((entry == null) || (entry.getEntryId() == entryId)) {
				break;
			}
			else {
				newUrlTitle = urlTitle + StringPool.DASH + i;
			}
		}

		return newUrlTitle;
	}

	protected void pingGoogle(BlogsEntry entry, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (Validator.isNull(serviceContext.getPortalURL()) &&
			Validator.isNull(serviceContext.getLayoutURL())) {

			return;
		}

		Group group = groupPersistence.findByPrimaryKey(entry.getGroupId());

		String portalURL = serviceContext.getPortalURL();

		if ((portalURL.indexOf("://localhost") != -1) ||
			(portalURL.indexOf("://127.0.0.1") != -1)) {

			return;
		}

		String layoutURL = serviceContext.getLayoutURL();

		StringBuilder sb = new StringBuilder();

		String name = group.getDescriptiveName();
		String url = portalURL + layoutURL + "/-/blogs";
		String changesURL = portalURL + layoutURL + "/-/blogs/rss";

		sb.append("http://blogsearch.google.com/ping?name=");
		sb.append(HttpUtil.encodeURL(name));
		sb.append("&url=");
		sb.append(HttpUtil.encodeURL(url));
		sb.append("&changesURL=");
		sb.append(HttpUtil.encodeURL(changesURL));

		String location = sb.toString();

		if (_log.isInfoEnabled()) {
			_log.info("Pinging Google at " + location);
		}

		try {
			String response = HttpUtil.URLtoString(sb.toString());

			if (_log.isInfoEnabled()) {
				_log.info("Google ping response: " + response);
			}
		}
		catch (IOException ioe) {
			_log.error("Unable to ping Google at " + location, ioe);
		}
	}

	protected boolean pingTrackback(String trackback, Map<String, String> parts)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Pinging trackback " + trackback);
		}

		String xml = HttpUtil.URLtoString(trackback, null, parts, true);

		if (_log.isDebugEnabled()) {
			_log.debug(xml);
		}

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();

		XMLStreamReader reader = inputFactory.createXMLStreamReader(
			new StringReader(xml));

		String error = xml;

		try {
			reader.nextTag();
			reader.nextTag();

			String name = reader.getLocalName();

			if (name.equals("error")) {
				int status = GetterUtil.getInteger(reader.getElementText(), 1);

				if (status == 0) {
					return true;
				}

				reader.nextTag();

				name = reader.getLocalName();

				if (name.equals("message")) {
					error = reader.getElementText();
				}
			}
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				}
				catch (Exception e) {
				}
			}
		}

		_log.error(
			"Error while pinging trackback at " + trackback + ": " + error);

		return false;
	}

	protected void pingTrackbacks(
			BlogsEntry entry, String[] trackbacks, boolean pingOldTrackbacks,
			ServiceContext serviceContext)
		throws SystemException {

		if (Validator.isNull(serviceContext.getPortalURL()) &&
			Validator.isNull(serviceContext.getLayoutURL())) {

			return;
		}

		Map<String, String> parts = new HashMap<String, String>();

		String excerpt = StringUtil.shorten(
			HtmlUtil.extractText(entry.getContent()),
			PropsValues.BLOGS_TRACKBACK_EXCERPT_LENGTH);
		String url =
			serviceContext.getPortalURL() + serviceContext.getLayoutURL()
				+ "/-/blogs/" + entry.getUrlTitle();

		parts.put("title", entry.getTitle());
		parts.put("excerpt", excerpt);
		parts.put("url", url);
		parts.put("blog_name", entry.getUserName());

		Set<String> trackbacksSet = null;

		if (Validator.isNotNull(trackbacks)) {
			trackbacksSet = SetUtil.fromArray(trackbacks);
		}
		else {
			trackbacksSet = new HashSet<String>();
		}

		if (pingOldTrackbacks) {
			trackbacksSet.addAll(
				SetUtil.fromArray(StringUtil.split(entry.getTrackbacks())));

			entry.setTrackbacks(StringPool.BLANK);

			blogsEntryPersistence.update(entry, false);
		}

		Set<String> oldTrackbacks = SetUtil.fromArray(
			StringUtil.split(entry.getTrackbacks()));

		Set<String> validTrackbacks = new HashSet<String>();

		for (String trackback : trackbacksSet) {
			if (oldTrackbacks.contains(trackback)) {
				continue;
			}

			try {
				if (pingTrackback(trackback, parts)) {
					validTrackbacks.add(trackback);
				}
			}
			catch (Exception e) {
				_log.error("Error while pinging trackback at " + trackback, e);
			}
		}

		if (!validTrackbacks.isEmpty()) {
			String newTrackbacks = StringUtil.merge(validTrackbacks);

			if (Validator.isNotNull(entry.getTrackbacks())) {
				newTrackbacks += StringPool.COMMA + entry.getTrackbacks();
			}

			entry.setTrackbacks(newTrackbacks);

			blogsEntryPersistence.update(entry, false);
		}
	}

	protected void validate(String title, String content)
		throws PortalException {

		if (Validator.isNull(title)) {
			throw new EntryTitleException();
		}
		else if (Validator.isNull(content)) {
			throw new EntryContentException();
		}
	}

	private static final char[] _URL_TITLE_REPLACE_CHARS = new char[] {
		' ', '.', ',', '/', '\\', '\'', '\"'
	};

	private static Log _log =
		LogFactory.getLog(BlogsEntryLocalServiceImpl.class);

}