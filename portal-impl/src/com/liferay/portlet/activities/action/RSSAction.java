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

package com.liferay.portlet.activities.action;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;

import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class RSSAction extends PortletAction {

	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		resourceResponse.setContentType(ContentTypes.TEXT_XML_UTF8);

		OutputStream outputStream = resourceResponse.getPortletOutputStream();

		try {
			outputStream.write(getRSS(resourceRequest));
		}
		finally {
			outputStream.close();
		}
	}

	protected byte[] getRSS(ResourceRequest resourceRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = GroupLocalServiceUtil.getGroup(
			themeDisplay.getScopeGroupId());

		int start = 0;
		int end = 10;

		List<SocialActivity> activities = null;

		if (group.isCommunity()) {
			activities = SocialActivityLocalServiceUtil.getGroupActivities(
				group.getGroupId(), start, end);
		}
		else if (group.isOrganization()) {
			activities =
				SocialActivityLocalServiceUtil.getOrganizationActivities(
					group.getOrganizationId(), start, end);
		}
		else if (group.isUser()) {
			activities = SocialActivityLocalServiceUtil.getUserActivities(
				group.getClassPK(), start, end);
		}

		String feedTitle = ParamUtil.getString(resourceRequest, "feedTitle");
		String feedLink =  PortalUtil.getLayoutFullURL(themeDisplay) +
			Portal.FRIENDLY_URL_SEPARATOR + "activities/rss";

		if (activities == null) {
			activities = SocialActivityLocalServiceUtil.getActivities(
				0, StringPool.BLANK, 0, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setFeedType(RSSUtil.FEED_TYPE_DEFAULT);
		syndFeed.setLink(feedLink);
		syndFeed.setTitle(feedTitle);
		syndFeed.setDescription(feedTitle);

		List<SyndEntry> entries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(entries);

		for (SocialActivity activity : activities) {
			SocialActivityFeedEntry activityFeedEntry =
				SocialActivityInterpreterLocalServiceUtil.interpret(
					activity, themeDisplay);

			if (activityFeedEntry == null) {
				continue;
			}

			SyndEntry syndEntry = new SyndEntryImpl();

			syndEntry.setTitle(HtmlUtil.extractText(
				activityFeedEntry.getTitle()));

			if (Validator.isNotNull(activityFeedEntry.getLink())) {
				syndEntry.setLink(activityFeedEntry.getLink());
			}

			syndEntry.setPublishedDate(new Date(activity.getCreateDate()));

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.FEED_TYPE_DEFAULT);
			syndContent.setValue(activityFeedEntry.getBody());

			syndEntry.setDescription(syndContent);

			entries.add(syndEntry);
		}

		String rss = StringPool.BLANK;

		try {
			rss = RSSUtil.export(syndFeed);
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}

		return rss.getBytes(StringPool.UTF8);
	}

	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}