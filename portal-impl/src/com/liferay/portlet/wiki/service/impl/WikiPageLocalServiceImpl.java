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

package com.liferay.portlet.wiki.service.impl;

import com.liferay.documentlibrary.DuplicateDirectoryException;
import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.util.NotificationThreadLocal;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.wiki.DuplicatePageException;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.NoSuchPageResourceException;
import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.PageTitleException;
import com.liferay.portlet.wiki.PageVersionException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageDisplay;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.model.impl.WikiPageDisplayImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;
import com.liferay.portlet.wiki.service.base.WikiPageLocalServiceBaseImpl;
import com.liferay.portlet.wiki.social.WikiActivityKeys;
import com.liferay.portlet.wiki.util.Indexer;
import com.liferay.portlet.wiki.util.WikiCacheThreadLocal;
import com.liferay.portlet.wiki.util.WikiCacheUtil;
import com.liferay.portlet.wiki.util.WikiUtil;
import com.liferay.portlet.wiki.util.comparator.PageCreateDateComparator;
import com.liferay.util.UniqueList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * <a href="WikiPageLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Raymond Aug�
 * @author Bruno Farache
 * @author Julio Camarero
 */
public class WikiPageLocalServiceImpl extends WikiPageLocalServiceBaseImpl {

	public WikiPage addPage(
			long userId, long nodeId, String title, String content,
			String summary, boolean minorEdit, ServiceContext serviceContext)
		throws PortalException, SystemException {

		String uuid = null;
		double version = WikiPageImpl.DEFAULT_VERSION;
		String format = WikiPageImpl.DEFAULT_FORMAT;
		boolean head = true;
		String parentTitle = null;
		String redirectTitle = null;

		return addPage(
			uuid, userId, nodeId, title, version, content, summary, minorEdit,
			format, head, parentTitle, redirectTitle, serviceContext);
	}

	public WikiPage addPage(
			String uuid, long userId, long nodeId, String title, double version,
			String content, String summary, boolean minorEdit, String format,
			boolean head, String parentTitle, String redirectTitle,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Page

		User user = userPersistence.findByPrimaryKey(userId);
		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		Date now = new Date();

		validate(title, nodeId, content, format);

		long pageId = counterLocalService.increment();

		long resourcePrimKey =
			wikiPageResourceLocalService.getPageResourcePrimKey(nodeId, title);

		WikiPage page = wikiPagePersistence.create(pageId);

		page.setUuid(uuid);
		page.setResourcePrimKey(resourcePrimKey);
		page.setGroupId(node.getGroupId());
		page.setCompanyId(user.getCompanyId());
		page.setUserId(user.getUserId());
		page.setUserName(user.getFullName());
		page.setCreateDate(now);
		page.setModifiedDate(now);
		page.setNodeId(nodeId);
		page.setTitle(title);
		page.setVersion(version);
		page.setMinorEdit(minorEdit);
		page.setContent(content);
		page.setSummary(summary);
		page.setFormat(format);
		page.setHead(head);
		page.setParentTitle(parentTitle);
		page.setRedirectTitle(redirectTitle);

		wikiPagePersistence.update(page, false);

		// Resources

		addPageResources(page, true, true);

		// Node

		node.setLastPostDate(now);

		wikiNodePersistence.update(node, false);

		// Asset

		updateAsset(
			userId, page, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Expando

		ExpandoBridge expandoBridge = page.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Message boards

		if (PropsValues.WIKI_PAGE_COMMENTS_ENABLED) {
			mbMessageLocalService.addDiscussionMessage(
				userId, page.getUserName(), WikiPage.class.getName(),
				resourcePrimKey);
		}

		// Social

		socialActivityLocalService.addActivity(
			userId, page.getGroupId(), WikiPage.class.getName(),
			resourcePrimKey, WikiActivityKeys.ADD_PAGE, StringPool.BLANK, 0);

		// Subscriptions

		if (!minorEdit && NotificationThreadLocal.isNotificationEnabled()) {
			notifySubscribers(node, page, serviceContext, false);
		}

		// Indexer

		reIndex(page);

		// Cache

		clearPageCache(page);
		clearReferralsCache(page);

		return page;
	}

	public void addPageAttachments(
			long nodeId, String title,
			List<ObjectValuePair<String, byte[]>> files)
		throws PortalException, SystemException {

		if (files.size() == 0) {
			return;
		}

		WikiPage page = getPage(nodeId, title);

		long companyId = page.getCompanyId();
		String portletId = CompanyConstants.SYSTEM_STRING;
		long groupId = GroupConstants.DEFAULT_PARENT_GROUP_ID;
		long repositoryId = CompanyConstants.SYSTEM;
		String dirName = page.getAttachmentsDir();

		try {
			dlService.addDirectory(companyId, repositoryId, dirName);
		}
		catch (DuplicateDirectoryException dde) {
		}

		for (int i = 0; i < files.size(); i++) {
			ObjectValuePair<String, byte[]> ovp = files.get(i);

			String fileName = ovp.getKey();
			byte[] bytes = ovp.getValue();

			if (Validator.isNull(fileName)) {
				continue;
			}

			try {
				dlService.addFile(
					companyId, portletId, groupId, repositoryId,
					dirName + "/" + fileName, 0, StringPool.BLANK,
					page.getModifiedDate(), new ServiceContext(), bytes);
			}
			catch (DuplicateFileException dfe) {
			}
		}
	}

	public void addPageResources(
			long nodeId, String title, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		addPageResources(page, addCommunityPermissions, addGuestPermissions);
	}

	public void addPageResources(
			WikiPage page, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			page.getCompanyId(), page.getGroupId(),	page.getUserId(),
			WikiPage.class.getName(), page.getResourcePrimKey(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addPageResources(
			long nodeId, String title, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		addPageResources(page, communityPermissions, guestPermissions);
	}

	public void addPageResources(
			WikiPage page, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			page.getCompanyId(), page.getGroupId(),	page.getUserId(),
			WikiPage.class.getName(), page.getResourcePrimKey(),
			communityPermissions, guestPermissions);
	}

	public void changeParent(
			long userId, long nodeId, String title, String newParentTitle,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiPage parentPage = getPage(nodeId, newParentTitle);

		if (Validator.isNotNull(parentPage.getRedirectTitle())) {
			newParentTitle = parentPage.getRedirectTitle();
		}

		WikiPage page = getPage(nodeId, title);

		String originalParentTitle = page.getParentTitle();

		double version = page.getVersion();
		String content = page.getContent();
		String summary = LanguageUtil.format(
			page.getCompanyId(), ServiceContextUtil.getLocale(serviceContext),
			"changed-parent-from-x", originalParentTitle);
		boolean minorEdit = false;
		String format = page.getFormat();
		String redirectTitle = page.getRedirectTitle();

		updatePage(
			userId, nodeId, title, version, content, summary, minorEdit,
			format, newParentTitle, redirectTitle, serviceContext);

		List<WikiPage> oldPages = wikiPagePersistence.findByN_T_H(
			nodeId, title, false);

		for (WikiPage oldPage : oldPages) {
			oldPage.setParentTitle(originalParentTitle);

			wikiPagePersistence.update(oldPage, false);
		}
	}

	public void deletePage(long nodeId, String title)
		throws PortalException, SystemException {

		List<WikiPage> pages = wikiPagePersistence.findByN_T_H(
			nodeId, title, true, 0, 1);

		if (pages.size() > 0) {
			WikiPage page = pages.iterator().next();

			deletePage(page);
		}
	}

	public void deletePage(WikiPage page)
		throws PortalException, SystemException {

		// Children

		List<WikiPage> children = wikiPagePersistence.findByN_H_P(
			page.getNodeId(), true, page.getTitle());

		for (WikiPage curPage : children) {
			deletePage(curPage);
		}

		// Indexer

		try {
			Indexer.deletePage(
				page.getCompanyId(), page.getNodeId(), page.getTitle());
		}
		catch (SearchException se) {
			_log.error("Deleting index " + page.getPrimaryKey(), se);
		}

		// Attachments

		long companyId = page.getCompanyId();
		String portletId = CompanyConstants.SYSTEM_STRING;
		long repositoryId = CompanyConstants.SYSTEM;
		String dirName = page.getAttachmentsDir();

		try {
			dlService.deleteDirectory(
				companyId, portletId, repositoryId, dirName);
		}
		catch (NoSuchDirectoryException nsde) {
		}

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			page.getCompanyId(), WikiPage.class.getName(), page.getPageId());

		// Social

		socialActivityLocalService.deleteActivities(
			WikiPage.class.getName(), page.getResourcePrimKey());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			WikiPage.class.getName(), page.getResourcePrimKey());

		// Expando

		expandoValueLocalService.deleteValues(
			WikiPage.class.getName(), page.getResourcePrimKey());

		// Asset

		assetEntryLocalService.deleteEntry(
			WikiPage.class.getName(), page.getResourcePrimKey());

		// Resources

		resourceLocalService.deleteResource(
			page.getCompanyId(), WikiPage.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, page.getResourcePrimKey());

		// Resource

		try {
			wikiPageResourceLocalService.deletePageResource(
				page.getNodeId(), page.getTitle());
		}
		catch (NoSuchPageResourceException nspre) {
		}

		// All versions

		wikiPagePersistence.removeByN_T(page.getNodeId(), page.getTitle());

		// All referrals

		wikiPagePersistence.removeByN_R(page.getNodeId(), page.getTitle());

		// Cache

		clearPageCache(page);
		clearReferralsCache(page);
	}

	public void deletePageAttachment(long nodeId, String title, String fileName)
		throws PortalException, SystemException {

		if (Validator.isNull(fileName)) {
			return;
		}

		WikiPage page = getPage(nodeId, title);

		long companyId = page.getCompanyId();
		String portletId = CompanyConstants.SYSTEM_STRING;
		long repositoryId = CompanyConstants.SYSTEM;

		try {
			dlService.deleteFile(companyId, portletId, repositoryId, fileName);
		}
		catch (NoSuchFileException nsfe) {
		}
	}

	public void deletePages(long nodeId)
		throws PortalException, SystemException {

		Iterator<WikiPage> itr = wikiPagePersistence.findByN_H(
			nodeId, true).iterator();

		while (itr.hasNext()) {
			WikiPage page = itr.next();

			deletePage(page);
		}
	}

	public List<WikiPage> getChildren(
			long nodeId, boolean head, String parentTitle)
		throws SystemException {

		return wikiPagePersistence.findByN_H_P(nodeId, head, parentTitle);
	}

	public List<WikiPage> getIncomingLinks(long nodeId, String title)
		throws PortalException, SystemException {

		List<WikiPage> links = new UniqueList<WikiPage>();

		List<WikiPage> pages = wikiPagePersistence.findByN_H(nodeId, true);

		for (WikiPage page : pages) {
			if (isLinkedTo(page, title)) {
				links.add(page);
			}
		}

		List<WikiPage> referrals = wikiPagePersistence.findByN_R(nodeId, title);

		for (WikiPage referral : referrals) {
			for (WikiPage page : pages) {
				if (isLinkedTo(page, referral.getTitle())) {
					links.add(page);
				}
			}
		}

		return ListUtil.sort(links);
	}

	public List<WikiPage> getNoAssetPages() throws SystemException {
		return wikiPageFinder.findByNoAssets();
	}

	public List<WikiPage> getOrphans(long nodeId)
		throws PortalException, SystemException {

		List<Map<String, Boolean>> pageTitles =
			new ArrayList<Map<String, Boolean>>();

		List<WikiPage> pages = wikiPagePersistence.findByN_H(nodeId, true);

		for (WikiPage page : pages) {
			pageTitles.add(WikiCacheUtil.getOutgoingLinks(page));
		}

		Set<WikiPage> notOrphans = new HashSet<WikiPage>();

		for (WikiPage page : pages) {
			for (Map<String, Boolean> pageTitle : pageTitles) {
				if (pageTitle.get(page.getTitle()) != null) {
					notOrphans.add(page);

					break;
				}
			}
		}

		List<WikiPage> orphans = new ArrayList<WikiPage>();

		for (WikiPage page : pages) {
			if (!notOrphans.contains(page)) {
				orphans.add(page);
			}
		}

		orphans = ListUtil.sort(orphans);

		return orphans;
	}

	public List<WikiPage> getOutgoingLinks(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		Map<String, WikiPage> pages = new LinkedHashMap<String, WikiPage>();

		Map<String, Boolean> links = WikiCacheUtil.getOutgoingLinks(page);

		for (String curTitle : links.keySet()) {
			Boolean exists = links.get(curTitle);

			if (exists) {
				if (!pages.containsKey(curTitle)) {
					pages.put(curTitle, getPage(nodeId, curTitle));
				}
			}
			else {
				WikiPageImpl newPage = new WikiPageImpl();

				newPage.setNew(true);
				newPage.setNodeId(nodeId);
				newPage.setTitle(curTitle);

				if (!pages.containsKey(curTitle)) {
					pages.put(curTitle, newPage);
				}
			}
		}

		return ListUtil.fromCollection(pages.values());
	}

	public WikiPage getPage(long resourcePrimKey)
		throws PortalException, SystemException {

		WikiPageResource wikiPageResource =
			wikiPageResourceLocalService.getPageResource(resourcePrimKey);

		return getPage(
			wikiPageResource.getNodeId(), wikiPageResource.getTitle());
	}

	public WikiPage getPage(long nodeId, String title)
		throws PortalException, SystemException {

		List<WikiPage> pages = wikiPagePersistence.findByN_T_H(
			nodeId, title, true, 0, 1);

		if (pages.size() > 0) {
			return pages.get(0);
		}
		else {
			throw new NoSuchPageException();
		}
	}

	public WikiPage getPage(long nodeId, String title, double version)
		throws PortalException, SystemException {

		WikiPage page = null;

		if (version == 0) {
			page = getPage(nodeId, title);
		}
		else {
			page = wikiPagePersistence.findByN_T_V(nodeId, title, version);
		}

		return page;
	}

	public WikiPageDisplay getPageDisplay(
			long nodeId, String title, PortletURL viewPageURL,
			PortletURL editPageURL, String attachmentURLPrefix)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		String formattedContent = WikiUtil.convert(
			page, viewPageURL, editPageURL, attachmentURLPrefix);

		return new WikiPageDisplayImpl(
			page.getUserId(), page.getNodeId(), page.getTitle(),
			page.getVersion(), page.getContent(), formattedContent,
			page.getFormat(), page.getHead(), page.getAttachmentsFiles());
	}

	public List<WikiPage> getPages(long nodeId, int start, int end)
		throws SystemException {

		return wikiPagePersistence.findByNodeId(
			nodeId, start, end, new PageCreateDateComparator(false));
	}

	public List<WikiPage> getPages(String format) throws SystemException {
		return wikiPagePersistence.findByFormat(format);
	}

	public List<WikiPage> getPages(
			long nodeId, String title, int start, int end)
		throws SystemException {

		return wikiPagePersistence.findByN_T(
			nodeId, title, start, end, new PageCreateDateComparator(false));
	}

	public List<WikiPage> getPages(
			long nodeId, String title, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return wikiPagePersistence.findByN_T(nodeId, title, start, end, obc);
	}

	public List<WikiPage> getPages(
			long nodeId, boolean head, int start, int end)
		throws SystemException {

		return wikiPagePersistence.findByN_H(
			nodeId, head, start, end, new PageCreateDateComparator(false));
	}

	public List<WikiPage> getPages(
			long nodeId, String title, boolean head, int start, int end)
		throws SystemException {

		return wikiPagePersistence.findByN_T_H(
			nodeId, title, head, start, end,
			new PageCreateDateComparator(false));
	}

	public int getPagesCount(long nodeId) throws SystemException {
		return wikiPagePersistence.countByNodeId(nodeId);
	}

	public int getPagesCount(long nodeId, String title)
		throws SystemException {

		return wikiPagePersistence.countByN_T(nodeId, title);
	}

	public int getPagesCount(long nodeId, boolean head)
		throws SystemException {

		return wikiPagePersistence.countByN_H(nodeId, head);
	}

	public int getPagesCount(long nodeId, String title, boolean head)
		throws SystemException {

		return wikiPagePersistence.countByN_T_H(nodeId, title, head);
	}

	public int getPagesCount(String format) throws SystemException {
		return wikiPagePersistence.countByFormat(format);
	}

	public List<WikiPage> getRecentChanges(long nodeId, int start, int end)
		throws SystemException {

		Calendar cal = CalendarFactoryUtil.getCalendar();

		cal.add(Calendar.WEEK_OF_YEAR, -1);

		return wikiPageFinder.findByCreateDate(
			nodeId, cal.getTime(), false, start, end);
	}

	public int getRecentChangesCount(long nodeId) throws SystemException {
		Calendar cal = CalendarFactoryUtil.getCalendar();

		cal.add(Calendar.WEEK_OF_YEAR, -1);

		return wikiPageFinder.countByCreateDate(nodeId, cal.getTime(), false);
	}

	public void movePage(
			long userId, long nodeId, String title, String newTitle,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		movePage(userId, nodeId, title, newTitle, true, serviceContext);
	}

	public void movePage(
			long userId, long nodeId, String title, String newTitle,
			boolean strict, ServiceContext serviceContext)
		throws PortalException, SystemException {

		validateTitle(newTitle);

		// Check if the new title already exists

		if (isUsedTitle(nodeId, newTitle)) {
			WikiPage page = getPage(nodeId, newTitle);

			// Support moving back to a previously moved title

			if (((page.getVersion() == WikiPageImpl.DEFAULT_VERSION) &&
				 (page.getContent().length() < 200)) ||
				!strict) {

				deletePage(nodeId, newTitle);
			}
			else {
				throw new DuplicatePageException(newTitle);
			}
		}

		// All versions

		List<WikiPage> pageVersions = wikiPagePersistence.findByN_T(
			nodeId, title);

		if (pageVersions.size() == 0) {
			return;
		}

		for (WikiPage page : pageVersions) {
			page.setTitle(newTitle);

			wikiPagePersistence.update(page, false);
		}

		// Children

		List<WikiPage> children = wikiPagePersistence.findByN_P(nodeId, title);

		for (WikiPage page : children) {
			page.setParentTitle(newTitle);

			wikiPagePersistence.update(page, false);
		}

		WikiPage page = pageVersions.get(pageVersions.size() - 1);

		long resourcePrimKey = page.getResourcePrimKey();

		// Page resource

		WikiPageResource wikiPageResource =
			wikiPageResourcePersistence.findByPrimaryKey(resourcePrimKey);

		wikiPageResource.setTitle(newTitle);

		wikiPageResourcePersistence.update(wikiPageResource, false);

		// Create stub page at the old location

		String uuid = null;
		double version = WikiPageImpl.DEFAULT_VERSION;
		String summary = WikiPageImpl.MOVED + " to " + title;
		String format = page.getFormat();
		boolean head = true;
		String parentTitle = page.getParentTitle();
		String redirectTitle = page.getTitle();
		String content =
			StringPool.DOUBLE_OPEN_BRACKET + redirectTitle +
				StringPool.DOUBLE_CLOSE_BRACKET;

		addPage(
			uuid, userId, nodeId, title, version, content, summary, false,
			format, head, parentTitle, redirectTitle, serviceContext);

		// Move redirects to point to the page with the new title

		List<WikiPage> redirectedPages = wikiPagePersistence.findByN_R(
			nodeId, title);

		for (WikiPage redirectedPage : redirectedPages) {
			redirectedPage.setRedirectTitle(newTitle);

			wikiPagePersistence.update(redirectedPage, false);
		}

		// Asset

		updateAsset(userId, page, null, null);

		// Indexer

		try {
			Indexer.deletePage(page.getCompanyId(), page.getGroupId(), title);
		}
		catch (SearchException se) {
			_log.error("Indexing " + newTitle, se);
		}

		reIndex(page);
	}

	public void reIndex(long resourcePrimKey) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		WikiPage page = null;

		try {
			page = wikiPageFinder.findByResourcePrimKey(resourcePrimKey);
		}
		catch (NoSuchPageException nspe) {
			return;
		}

		reIndex(page);
	}

	public void reIndex(WikiPage page) throws SystemException {
		if (Validator.isNotNull(page.getRedirectTitle())) {
			return;
		}

		long companyId = page.getCompanyId();
		long groupId = page.getGroupId();
		long resourcePrimKey = page.getResourcePrimKey();
		long nodeId = page.getNodeId();
		String title = page.getTitle();
		String content = page.getContent();
		Date modifiedDate = page.getModifiedDate();

		long[] assetCategoryIds = assetCategoryLocalService.getCategoryIds(
			WikiPage.class.getName(), resourcePrimKey);
		String[] assetTagNames = assetTagLocalService.getTagNames(
			WikiPage.class.getName(), resourcePrimKey);

		ExpandoBridge expandoBridge = page.getExpandoBridge();

		try {
			Indexer.updatePage(
				companyId, groupId, resourcePrimKey, nodeId, title, content,
				modifiedDate, assetCategoryIds, assetTagNames, expandoBridge);
		}
		catch (SearchException se) {
			_log.error("Reindexing " + page.getPrimaryKey(), se);
		}
	}

	public WikiPage revertPage(
			long userId, long nodeId, String title, double version,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiPage oldPage = getPage(nodeId, title, version);

		return updatePage(
			userId, nodeId, title, 0, oldPage.getContent(),
			WikiPageImpl.REVERTED + " to " + version, false,
			oldPage.getFormat(), null, oldPage.getRedirectTitle(),
			serviceContext);
	}

	public void subscribePage(long userId, long nodeId, String title)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		subscriptionLocalService.addSubscription(
			userId, WikiPage.class.getName(), page.getResourcePrimKey());
	}

	public void unsubscribePage(long userId, long nodeId, String title)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		subscriptionLocalService.deleteSubscription(
			userId, WikiPage.class.getName(), page.getResourcePrimKey());
	}

	public void updateAsset(
			long userId, WikiPage page, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException, SystemException {

		assetEntryLocalService.updateEntry(
			userId, page.getGroupId(), WikiPage.class.getName(),
			page.getResourcePrimKey(), assetCategoryIds, assetTagNames, true,
			null, null, null, null, ContentTypes.TEXT_HTML, page.getTitle(),
			null, null, null, 0, 0, null, false);
	}

	public WikiPage updatePage(
			long userId, long nodeId, String title, double version,
			String content, String summary, boolean minorEdit, String format,
			String parentTitle, String redirectTitle,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Page

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(nodeId, content, format);

		WikiPage page = null;

		try {
			page = getPage(nodeId, title);
		}
		catch (NoSuchPageException nspe) {
			return addPage(
				null, userId, nodeId, title, WikiPageImpl.DEFAULT_VERSION,
				content, summary, minorEdit, format, true, parentTitle,
				redirectTitle, serviceContext);
		}

		double oldVersion = page.getVersion();

		if ((version > 0) && (version != oldVersion)) {
			throw new PageVersionException();
		}

		long resourcePrimKey = page.getResourcePrimKey();
		long groupId = page.getGroupId();

		page.setHead(false);
		page.setModifiedDate(now);

		wikiPagePersistence.update(page, false);

		double newVersion = MathUtil.format(oldVersion + 0.1, 1, 1);

		long pageId = counterLocalService.increment();

		page = wikiPagePersistence.create(pageId);

		page.setResourcePrimKey(resourcePrimKey);
		page.setGroupId(groupId);
		page.setCompanyId(user.getCompanyId());
		page.setUserId(user.getUserId());
		page.setUserName(user.getFullName());
		page.setCreateDate(now);
		page.setModifiedDate(now);
		page.setNodeId(nodeId);
		page.setTitle(title);
		page.setVersion(newVersion);
		page.setMinorEdit(minorEdit);
		page.setContent(content);
		page.setSummary(summary);
		page.setFormat(format);
		page.setHead(true);

		if (Validator.isNotNull(parentTitle)) {
			page.setParentTitle(parentTitle);
		}

		if (Validator.isNotNull(redirectTitle)) {
			page.setRedirectTitle(redirectTitle);
		}

		wikiPagePersistence.update(page, false);

		// Expando

		ExpandoBridge expandoBridge = page.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Node

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		node.setLastPostDate(now);

		wikiNodePersistence.update(node, false);

		// Asset

		updateAsset(
			userId, page, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Social

		socialActivityLocalService.addActivity(
			userId, page.getGroupId(), WikiPage.class.getName(),
			page.getResourcePrimKey(), WikiActivityKeys.UPDATE_PAGE,
			StringPool.BLANK, 0);

		// Subscriptions

		if (!minorEdit && NotificationThreadLocal.isNotificationEnabled()) {
			notifySubscribers(node, page, serviceContext, true);
		}

		// Indexer

		reIndex(page);

		// Cache

		clearPageCache(page);

		return page;
	}

	public void validateTitle(String title) throws PortalException {
		if (title.equals("all_pages") || title.equals("orphan_pages") ||
			title.equals("recent_changes")) {

			throw new PageTitleException(title + " is reserved");
		}

		if (Validator.isNotNull(PropsValues.WIKI_PAGE_TITLES_REGEXP)) {
			Pattern pattern = Pattern.compile(
				PropsValues.WIKI_PAGE_TITLES_REGEXP);

			Matcher matcher = pattern.matcher(title);

			if (!matcher.matches()) {
				throw new PageTitleException();
			}
		}
	}

	protected void clearPageCache(WikiPage page) {
		if (!WikiCacheThreadLocal.isClearCache()) {
			return;
		}

		WikiCacheUtil.clearCache(page.getNodeId(), page.getTitle());
	}

	protected void clearReferralsCache(WikiPage page)
		throws PortalException, SystemException {

		if (!WikiCacheThreadLocal.isClearCache()) {
			return;
		}

		List<WikiPage> links = getIncomingLinks(
			page.getNodeId(), page.getTitle());

		for (WikiPage curPage : links) {
			WikiCacheUtil.clearCache(curPage.getNodeId(), curPage.getTitle());
		}
	}

	protected WikiPage getPreviousVersionPage(WikiPage page)
		throws PortalException, SystemException {

		double previousVersion = MathUtil.format(page.getVersion() - 0.1, 1, 1);

		if (previousVersion < 1) {
			return null;
		}

		return getPage(page.getNodeId(), page.getTitle(), previousVersion);
	}

	protected boolean isLinkedTo(WikiPage page, String targetTitle)
		throws PortalException {

		Map<String, Boolean> links = WikiCacheUtil.getOutgoingLinks(page);

		Boolean link = links.get(targetTitle);

		if (link != null) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isUsedTitle(long nodeId, String title)
		throws SystemException {

		if (getPagesCount(nodeId, title, true) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void notifySubscribers(
			WikiNode node, WikiPage page, ServiceContext serviceContext,
			boolean update)
		throws PortalException, SystemException {

		PortletPreferences preferences =
			ServiceContextUtil.getPortletPreferences(serviceContext);

		if (preferences == null) {
			long ownerId = node.getGroupId();
			int ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
			long plid = PortletKeys.PREFS_PLID_SHARED;
			String portletId = PortletKeys.WIKI;
			String defaultPreferences = null;

			preferences = portletPreferencesLocalService.getPreferences(
				node.getCompanyId(), ownerId, ownerType, plid, portletId,
				defaultPreferences);
		}

		if (!update && WikiUtil.getEmailPageAddedEnabled(preferences)) {
		}
		else if (update && WikiUtil.getEmailPageUpdatedEnabled(preferences)) {
		}
		else {
			return;
		}

		Company company = companyPersistence.findByPrimaryKey(
			page.getCompanyId());

		Group group = groupPersistence.findByPrimaryKey(node.getGroupId());

		User user = userPersistence.findByPrimaryKey(page.getUserId());

		String portalURL = serviceContext.getPortalURL();
		String layoutFullURL = serviceContext.getLayoutFullURL();

		WikiPage previousVersionPage = getPreviousVersionPage(page);

		String attachmentURLPrefix =
			portalURL + serviceContext.getPathMain() +
				"/wiki/get_page_attachment?p_l_id=" + serviceContext.getPlid() +
					"&nodeId=" + page.getNodeId() + "&title=" +
						HttpUtil.encodeURL(page.getTitle()) + "&fileName=";

		String pageDiffs = StringPool.BLANK;

		try {
			pageDiffs = WikiUtil.diffHtml(
				previousVersionPage, page, null, null, attachmentURLPrefix);
		}
		catch (Exception e) {
		}

		String pageContent = null;

		if (Validator.equals(page.getFormat(), "creole")) {
			pageContent = WikiUtil.convert(
				page, null, null, attachmentURLPrefix);
		}
		else {
			pageContent = page.getContent();
			pageContent = WikiUtil.processContent(pageContent);
		}

		String pageURL = StringPool.BLANK;
		String diffsURL = StringPool.BLANK;

		if (Validator.isNotNull(layoutFullURL)) {
			pageURL =
				layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR + "wiki/" +
					node.getNodeId() + StringPool.SLASH +
						HttpUtil.encodeURL(page.getTitle());

			if (previousVersionPage != null) {
				StringBuilder sb = new StringBuilder();

				sb.append(layoutFullURL);
				sb.append("?p_p_id=");
				sb.append(PortletKeys.WIKI);
				sb.append("&p_p_state=");
				sb.append(WindowState.MAXIMIZED);
				sb.append("&struts_action=");
				sb.append(HttpUtil.encodeURL("/wiki/compare_versions"));
				sb.append("&nodeId=");
				sb.append(node.getNodeId());
				sb.append("&title=");
				sb.append(HttpUtil.encodeURL(page.getTitle()));
				sb.append("&sourceVersion=");
				sb.append(previousVersionPage.getVersion());
				sb.append("&targetVersion=");
				sb.append(page.getVersion());
				sb.append("&type=html");

				diffsURL = sb.toString();
			}
		}

		String portletName = PortalUtil.getPortletTitle(PortletKeys.WIKI, user);

		String fromName = WikiUtil.getEmailFromName(preferences);
		String fromAddress = WikiUtil.getEmailFromAddress(preferences);

		String replyToAddress = fromAddress;
		String mailId = WikiUtil.getMailId(
			company.getMx(), page.getNodeId(), page.getPageId());

		fromName = StringUtil.replace(
			fromName,
			new String[] {
				"[$COMPANY_ID$]",
				"[$COMPANY_MX$]",
				"[$COMPANY_NAME$]",
				"[$COMMUNITY_NAME$]",
				"[$PAGE_USER_ADDRESS$]",
				"[$PAGE_USER_NAME$]",
				"[$PORTLET_NAME$]"
			},
			new String[] {
				String.valueOf(company.getCompanyId()),
				company.getMx(),
				company.getName(),
				group.getName(),
				user.getEmailAddress(),
				user.getFullName(),
				portletName
			});

		fromAddress = StringUtil.replace(
			fromAddress,
			new String[] {
				"[$COMPANY_ID$]",
				"[$COMPANY_MX$]",
				"[$COMPANY_NAME$]",
				"[$COMMUNITY_NAME$]",
				"[$PAGE_USER_ADDRESS$]",
				"[$PAGE_USER_NAME$]",
				"[$PORTLET_NAME$]"
			},
			new String[] {
				String.valueOf(company.getCompanyId()),
				company.getMx(),
				company.getName(),
				group.getName(),
				user.getEmailAddress(),
				user.getFullName(),
				portletName
			});

		String subjectPrefix = null;
		String body = null;
		String signature = null;

		if (update) {
			subjectPrefix = WikiUtil.getEmailPageUpdatedSubjectPrefix(
				preferences);
			body = WikiUtil.getEmailPageUpdatedBody(preferences);
			signature = WikiUtil.getEmailPageUpdatedSignature(preferences);
		}
		else {
			subjectPrefix = WikiUtil.getEmailPageAddedSubjectPrefix(
				preferences);
			body = WikiUtil.getEmailPageAddedBody(preferences);
			signature = WikiUtil.getEmailPageAddedSignature(preferences);
		}

		if (Validator.isNotNull(signature)) {
			body +=  "\n" + signature;
		}

		subjectPrefix = StringUtil.replace(
			subjectPrefix,
			new String[] {
				"[$COMPANY_ID$]",
				"[$COMPANY_MX$]",
				"[$COMPANY_NAME$]",
				"[$COMMUNITY_NAME$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$NODE_NAME$]",
				"[$PAGE_CONTENT$]",
				"[$PAGE_ID$]",
				"[$PAGE_TITLE$]",
				"[$PAGE_USER_ADDRESS$]",
				"[$PAGE_USER_NAME$]",
				"[$PORTAL_URL$]",
				"[$PORTLET_NAME$]"
			},
			new String[] {
				String.valueOf(company.getCompanyId()),
				company.getMx(),
				company.getName(),
				group.getName(),
				fromAddress,
				fromName,
				node.getName(),
				pageContent,
				String.valueOf(page.getPageId()),
				page.getTitle(),
				user.getEmailAddress(),
				user.getFullName(),
				company.getVirtualHost(),
				portletName
			});

		body = StringUtil.replace(
			body,
			new String[] {
				"[$COMPANY_ID$]",
				"[$COMPANY_MX$]",
				"[$COMPANY_NAME$]",
				"[$COMMUNITY_NAME$]",
				"[$DIFFS_URL$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$NODE_NAME$]",
				"[$PAGE_CONTENT$]",
				"[$PAGE_DATE_UPDATE$]",
				"[$PAGE_DIFFS$]",
				"[$PAGE_ID$]",
				"[$PAGE_SUMMARY$]",
				"[$PAGE_TITLE$]",
				"[$PAGE_URL$]",
				"[$PAGE_USER_ADDRESS$]",
				"[$PAGE_USER_NAME$]",
				"[$PORTAL_URL$]",
				"[$PORTLET_NAME$]"
			},
			new String[] {
				String.valueOf(company.getCompanyId()),
				company.getMx(),
				company.getName(),
				group.getName(),
				diffsURL,
				fromAddress,
				fromName,
				node.getName(),
				pageContent,
				String.valueOf(page.getModifiedDate()),
				replaceStyles(pageDiffs),
				String.valueOf(page.getPageId()),
				page.getSummary(),
				page.getTitle(),
				pageURL,
				user.getEmailAddress(),
				user.getFullName(),
				company.getVirtualHost(),
				portletName
			});

		String subject = page.getTitle();

		if (subject.indexOf(subjectPrefix) == -1) {
			subject = subjectPrefix + StringPool.SPACE + subject;
		}

		Message message = new Message();

		message.put("companyId", node.getCompanyId());
		message.put("userId", node.getUserId());
		message.put("nodeId", node.getNodeId());
		message.put("pageResourcePrimKey", page.getResourcePrimKey());
		message.put("fromName", fromName);
		message.put("fromAddress", fromAddress);
		message.put("subject", subject);
		message.put("body", body);
		message.put("replyToAddress", replyToAddress);
		message.put("mailId", mailId);
		message.put("htmlFormat", Boolean.TRUE);

		MessageBusUtil.sendMessage(DestinationNames.WIKI, message);
	}

	protected String replaceStyles(String html) {
		return StringUtil.replace(
			html,
			new String[] {
				"class=\"diff-html-added\"",
				"class=\"diff-html-removed\"",
				"class=\"diff-html-changed\"",
				"changeType=\"diff-added-image\"",
				"changeType=\"diff-removed-image\"",
				"changeType=\"diff-changed-image\""
			},
			new String[] {
				"style=\"background-color: #CFC;\"",
				"style=\"background-color: #FDC6C6; text-decoration: " +
					"line-through;\"",
				"style=\"border-bottom: 2px dotted blue;\"",
				"style=\"border: 10px solid #CFC;\"",
				"style=\"border: 10px solid #FDC6C6;\"",
				"style=\"border: 10px solid blue;\""
			}
		);
	}

	protected void validate(long nodeId, String content, String format)
		throws PortalException {

		if (!WikiUtil.validate(nodeId, content, format)) {
			throw new PageContentException();
		}
	}

	protected void validate(
			String title, long nodeId, String content, String format)
		throws PortalException, SystemException {

		if (Validator.isNull(title)) {
			throw new PageTitleException();
		}

		if (isUsedTitle(nodeId, title)) {
			throw new DuplicatePageException();
		}

		validateTitle(title);

		validate(nodeId, content, format);
	}

	private static Log _log =
		LogFactoryUtil.getLog(WikiPageLocalServiceImpl.class);

}