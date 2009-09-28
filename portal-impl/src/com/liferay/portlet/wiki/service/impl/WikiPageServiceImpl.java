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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Diff;
import com.liferay.portal.kernel.util.DiffResult;
import com.liferay.portal.kernel.util.DiffUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;
import com.liferay.portlet.wiki.service.base.WikiPageServiceBaseImpl;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;
import com.liferay.portlet.wiki.util.WikiUtil;
import com.liferay.portlet.wiki.util.comparator.PageCreateDateComparator;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;

import java.io.StringReader;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * <a href="WikiPageServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Raymond Augé
 */
public class WikiPageServiceImpl extends WikiPageServiceBaseImpl {

	public WikiPage addPage(
			long nodeId, String title, String content, String summary,
			boolean minorEdit, ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		return wikiPageLocalService.addPage(
			getUserId(), nodeId, title, content, summary, minorEdit,
			serviceContext);
	}

	public WikiPage addPage(
			long nodeId, String title, String content, String summary,
			boolean minorEdit, String format, String parentTitle,
			String redirectTitle, ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		return wikiPageLocalService.addPage(
			null, getUserId(), nodeId, title, WikiPageConstants.DEFAULT_VERSION,
			content, summary, minorEdit, format, true, parentTitle,
			redirectTitle, serviceContext);
	}

	public void addPageAttachments(
			long nodeId, String title,
			List<ObjectValuePair<String, byte[]>> files)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		wikiPageLocalService.addPageAttachments(nodeId, title, files);
	}

	public void changeParent(
			long nodeId, String title, String newParentTitle,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		wikiPageLocalService.changeParent(
			getUserId(), nodeId, title, newParentTitle, serviceContext);
	}

	public void deletePage(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		wikiPageLocalService.deletePage(nodeId, title);
	}

	public void deletePageAttachment(long nodeId, String title, String fileName)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		wikiPageLocalService.deletePageAttachment(nodeId, title, fileName);
	}

	public List<WikiPage> getNodePages(long nodeId, int max)
		throws PortalException, SystemException {

		List<WikiPage> pages = new ArrayList<WikiPage>();

		int lastIntervalStart = 0;
		boolean listNotExhausted = true;

		while ((pages.size() < max) && listNotExhausted) {
			List<WikiPage> pageList = wikiPageLocalService.getPages(
				nodeId, true, lastIntervalStart, lastIntervalStart + max);

			Iterator<WikiPage> itr = pageList.iterator();

			lastIntervalStart += max;
			listNotExhausted = (pageList.size() == max);

			while (itr.hasNext() && (pages.size() < max)) {
				WikiPage page = itr.next();

				if (WikiPagePermission.contains(getPermissionChecker(), page,
						ActionKeys.VIEW)) {

					pages.add(page);
				}
			}
		}

		return pages;
	}

	public String getNodePagesRSS(
			long nodeId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		long companyId = node.getCompanyId();
		String name = node.getName();
		String description = node.getDescription();
		List<WikiPage> pages = getNodePages(nodeId, max);
		boolean diff = false;
		Locale locale = null;

		return exportToRSS(
			companyId, name, description, type, version, displayStyle,
			feedURL, entryURL, pages, diff, locale);
	}

	public WikiPage getPage(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		return wikiPageLocalService.getPage(nodeId, title);
	}

	public WikiPage getPage(long nodeId, String title, double version)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		return wikiPageLocalService.getPage(nodeId, title, version);
	}

	public String getPagesRSS(
			long companyId, long nodeId, String title, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, Locale locale)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		String description = title;
		List<WikiPage> pages = wikiPageLocalService.getPages(
			nodeId, title, 0, max, new PageCreateDateComparator(true));
		boolean diff = true;

		return exportToRSS(
			companyId, title, description, type, version, displayStyle, feedURL,
			entryURL, pages, diff, locale);
	}

	public void movePage(
			long nodeId, String title, String newTitle,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		wikiPageLocalService.movePage(
			getUserId(), nodeId, title, newTitle, serviceContext);
	}

	public WikiPage revertPage(
			long nodeId, String title, double version,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.UPDATE);

		return wikiPageLocalService.revertPage(
			getUserId(), nodeId, title, version, serviceContext);
	}

	public void subscribePage(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.SUBSCRIBE);

		wikiPageLocalService.subscribePage(getUserId(), nodeId, title);
	}

	public void unsubscribePage(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.SUBSCRIBE);

		wikiPageLocalService.unsubscribePage(getUserId(), nodeId, title);
	}

	public WikiPage updatePage(
			long nodeId, String title, double version, String content,
			String summary, boolean minorEdit, String format,
			String parentTitle, String redirectTitle,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.UPDATE);

		return wikiPageLocalService.updatePage(
			getUserId(), nodeId, title, version, content, summary, minorEdit,
			format, parentTitle, redirectTitle, serviceContext);
	}

	protected String exportToRSS(
			long companyId, String name, String description, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, List<WikiPage> pages, boolean diff, Locale locale)
		throws SystemException {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setFeedType(RSSUtil.getFeedType(type, version));
		syndFeed.setTitle(name);
		syndFeed.setLink(feedURL);
		syndFeed.setDescription(description);

		List<SyndEntry> entries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(entries);

		WikiPage latestPage = null;

		for (WikiPage page : pages) {
			String author = PortalUtil.getUserName(
				page.getUserId(), page.getUserName());

			String title =
				page.getTitle() + StringPool.SPACE + page.getVersion();

			if (page.isMinorEdit()) {
				title +=
					StringPool.SPACE + StringPool.OPEN_PARENTHESIS +
						LanguageUtil.get(locale, "minor-edit") +
							StringPool.CLOSE_PARENTHESIS;
			}

			StringBuilder link = new StringBuilder();

			link.append(entryURL);
			link.append(StringPool.AMPERSAND);
			link.append(HttpUtil.encodeURL(page.getTitle()));

			SyndEntry syndEntry = new SyndEntryImpl();

			syndEntry.setAuthor(author);
			syndEntry.setTitle(title);
			syndEntry.setPublishedDate(page.getCreateDate());
			syndEntry.setUpdatedDate(page.getModifiedDate());

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.DEFAULT_ENTRY_TYPE);

			if (diff) {
				if (latestPage != null) {
					link.append(StringPool.QUESTION);
					link.append(
						PortalUtil.getPortletNamespace(PortletKeys.WIKI));
					link.append("version=");
					link.append(page.getVersion());

					String value = getPageDiff(
						companyId, latestPage, page, locale);

					syndContent.setValue(value);

					syndEntry.setDescription(syndContent);

					entries.add(syndEntry);
				}
			}
			else {
				String value = null;

				if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT)) {
					value = StringUtil.shorten(
						HtmlUtil.extractText(page.getContent()),
						_RSS_ABSTRACT_LENGTH, StringPool.BLANK);
				}
				else if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
					value = StringPool.BLANK;
				}
				else {
					value = page.getContent();
				}

				syndContent.setValue(value);

				syndEntry.setDescription(syndContent);

				entries.add(syndEntry);
			}

			syndEntry.setLink(link.toString());
			syndEntry.setUri(syndEntry.getLink());

			latestPage = page;
		}

		try {
			return RSSUtil.export(syndFeed);
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}
	}

	protected String getPageDiff(
			long companyId, WikiPage latestPage, WikiPage page,
			Locale locale)
		throws SystemException {

		String sourceContent = WikiUtil.processContent(latestPage.getContent());
		String targetContent = WikiUtil.processContent(page.getContent());

		sourceContent = HtmlUtil.escape(sourceContent);
		targetContent = HtmlUtil.escape(targetContent);

		List<DiffResult>[] diffResults = DiffUtil.diff(
			new StringReader(sourceContent), new StringReader(targetContent));

		String velocityTemplateId =
			"com/liferay/portlet/wiki/dependencies/rss.vm";
		String velocityTemplateContent = ContentUtil.get(velocityTemplateId);

		VelocityContext velocityContext =
			VelocityEngineUtil.getWrappedStandardToolsContext();

		velocityContext.put("companyId", companyId);
		velocityContext.put("contextLine", Diff.CONTEXT_LINE);
		velocityContext.put("diffUtil", new DiffUtil());
		velocityContext.put("languageUtil", LanguageUtil.getLanguage());
		velocityContext.put("locale", locale);
		velocityContext.put("sourceResults", diffResults[0]);
		velocityContext.put("targetResults", diffResults[1]);

		try {
			StringWriter stringWriter = new StringWriter();

			VelocityEngineUtil.mergeTemplate(
				velocityTemplateId, velocityTemplateContent, velocityContext,
				stringWriter);

			return stringWriter.toString();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private static final int _RSS_ABSTRACT_LENGTH = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.WIKI_RSS_ABSTRACT_LENGTH));

}