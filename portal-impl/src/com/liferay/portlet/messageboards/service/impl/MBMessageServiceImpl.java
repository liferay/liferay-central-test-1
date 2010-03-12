/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.messageboards.LockedThreadException;
import com.liferay.portlet.messageboards.NoSuchCategoryException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadConstants;
import com.liferay.portlet.messageboards.service.base.MBMessageServiceBaseImpl;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.messageboards.util.BBCodeUtil;
import com.liferay.portlet.messageboards.util.comparator.MessageCreateDateComparator;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBMessageServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Mika Koivisto
 */
public class MBMessageServiceImpl extends MBMessageServiceBaseImpl {

	public MBMessage addDiscussionMessage(
			String className, long classPK, String permissionClassName,
			long permissionClassPK, long threadId, long parentMessageId,
			String subject, String body, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = getUser();

		MBDiscussionPermission.check(
			getPermissionChecker(), user.getCompanyId(),
			serviceContext.getScopeGroupId(), permissionClassName,
			permissionClassPK, user.getUserId(), ActionKeys.ADD_DISCUSSION);

		return mbMessageLocalService.addDiscussionMessage(
			getUserId(), null, className, classPK, threadId,
			parentMessageId, subject, body, serviceContext);
	}

	public MBMessage addMessage(
			long groupId, long categoryId, String subject, String body,
			List<ObjectValuePair<String, byte[]>> files,
			boolean anonymous, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), groupId, categoryId,
			ActionKeys.ADD_MESSAGE);

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), groupId, categoryId,
				ActionKeys.ADD_FILE)) {

			files.clear();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), groupId, categoryId,
				ActionKeys.UPDATE_THREAD_PRIORITY)) {

			priority = MBThreadConstants.PRIORITY_NOT_GIVEN;
		}

		return mbMessageLocalService.addMessage(
			getGuestOrUserId(), null, groupId, categoryId, subject, body, files,
			anonymous, priority, allowPingbacks, serviceContext);
	}

	public MBMessage addMessage(
			long groupId, long categoryId, long threadId, long parentMessageId,
			String subject, String body,
			List<ObjectValuePair<String, byte[]>> files, boolean anonymous,
			double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		checkReplyToPermission(groupId, categoryId, parentMessageId);

		if (lockLocalService.isLocked(
				MBThread.class.getName(), threadId)) {

			throw new LockedThreadException();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), groupId, categoryId,
				ActionKeys.ADD_FILE)) {

			files.clear();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), groupId, categoryId,
				ActionKeys.UPDATE_THREAD_PRIORITY)) {

			priority = MBThreadConstants.PRIORITY_NOT_GIVEN;
		}

		return mbMessageLocalService.addMessage(
			getGuestOrUserId(), null, groupId, categoryId, threadId,
			parentMessageId, subject, body, files, anonymous, priority,
			allowPingbacks, serviceContext);
	}

	public void deleteDiscussionMessage(
			long groupId, String className, long classPK,
			String permissionClassName, long permissionClassPK, long messageId)
		throws PortalException, SystemException {

		User user = getUser();

		MBDiscussionPermission.check(
			getPermissionChecker(), user.getCompanyId(), groupId,
			permissionClassName, permissionClassPK, messageId, user.getUserId(),
			ActionKeys.DELETE_DISCUSSION);

		mbMessageLocalService.deleteDiscussionMessage(messageId);
	}

	public void deleteMessage(long messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.DELETE);

		mbMessageLocalService.deleteMessage(messageId);
	}

	public List<MBMessage> getCategoryMessages(
			long groupId, long categoryId, int status, int start, int end)
		throws PortalException, SystemException {

		List<MBMessage> messages = new ArrayList<MBMessage>();

		Iterator<MBMessage> itr = mbMessageLocalService.getCategoryMessages(
			groupId, categoryId, status, start, end).iterator();

		while (itr.hasNext()) {
			MBMessage message = itr.next();

			if (MBMessagePermission.contains(
					getPermissionChecker(), message, ActionKeys.VIEW)) {

				messages.add(message);
			}
		}

		return messages;
	}

	public int getCategoryMessagesCount(
			long groupId, long categoryId, int status)
		throws SystemException {

		return mbMessageLocalService.getCategoryMessagesCount(
			groupId, categoryId, status);
	}

	public String getCategoryMessagesRSS(
			long groupId, long categoryId, int status, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		String name = StringPool.BLANK;
		String description = StringPool.BLANK;

		try {
			MBCategory category = mbCategoryLocalService.getCategory(
				categoryId);

			name = category.getName();
			description = category.getDescription();
		}
		catch (NoSuchCategoryException nsce) {
			Group group = groupLocalService.getGroup(categoryId);

			name = group.getName();
			description = group.getDescription();
		}

		List<MBMessage> messages = new ArrayList<MBMessage>();

		int lastIntervalStart = 0;
		boolean listNotExhausted = true;
		MessageCreateDateComparator comparator =
			new MessageCreateDateComparator(false);

		while ((messages.size() < max) && listNotExhausted) {
			List<MBMessage> messageList =
				mbMessageLocalService.getCategoryMessages(
					groupId, categoryId, status, lastIntervalStart,
					lastIntervalStart + max, comparator);

			Iterator<MBMessage> itr = messageList.iterator();

			lastIntervalStart += max;
			listNotExhausted = (messageList.size() == max);

			while (itr.hasNext() && (messages.size() < max)) {
				MBMessage message = itr.next();

				if (MBMessagePermission.contains(
						getPermissionChecker(), message, ActionKeys.VIEW)) {

					messages.add(message);
				}
			}
		}

		return exportToRSS(
			name, description, type, version, displayStyle, feedURL, entryURL,
			messages, themeDisplay);
	}

	public String getCompanyMessagesRSS(
			long companyId, int status, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		String name = company.getName();
		String description = company.getName();

		List<MBMessage> messages = new ArrayList<MBMessage>();

		int lastIntervalStart = 0;
		boolean listNotExhausted = true;
		MessageCreateDateComparator comparator =
			new MessageCreateDateComparator(false);

		while ((messages.size() < max) && listNotExhausted) {
			List<MBMessage> messageList =
				mbMessageLocalService.getCompanyMessages(
					companyId, status, lastIntervalStart,
					lastIntervalStart + max, comparator);

			Iterator<MBMessage> itr = messageList.iterator();

			lastIntervalStart += max;
			listNotExhausted = (messageList.size() == max);

			while (itr.hasNext() && (messages.size() < max)) {
				MBMessage message = itr.next();

				if (MBMessagePermission.contains(
						getPermissionChecker(), message, ActionKeys.VIEW)) {

					messages.add(message);
				}
			}
		}

		return exportToRSS(
			name, description, type, version, displayStyle, feedURL, entryURL,
			messages, themeDisplay);
	}

	public String getGroupMessagesRSS(
			long groupId, int status, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		String name = StringPool.BLANK;
		String description = StringPool.BLANK;

		List<MBMessage> messages = new ArrayList<MBMessage>();

		int lastIntervalStart = 0;
		boolean listNotExhausted = true;
		MessageCreateDateComparator comparator =
			new MessageCreateDateComparator(false);

		while ((messages.size() < max) && listNotExhausted) {
			List<MBMessage> messageList =
				mbMessageLocalService.getGroupMessages(
					groupId, status, lastIntervalStart, lastIntervalStart + max,
					comparator);

			Iterator<MBMessage> itr = messageList.iterator();

			lastIntervalStart += max;
			listNotExhausted = (messageList.size() == max);

			while (itr.hasNext() && (messages.size() < max)) {
				MBMessage message = itr.next();

				if (MBMessagePermission.contains(
						getPermissionChecker(), message, ActionKeys.VIEW)) {

					messages.add(message);
				}
			}
		}

		if (messages.size() > 0) {
			MBMessage message = messages.get(messages.size() - 1);

			name = message.getSubject();
			description = message.getSubject();
		}

		return exportToRSS(
			name, description, type, version, displayStyle, feedURL, entryURL,
			messages, themeDisplay);
	}

	public String getGroupMessagesRSS(
			long groupId, long userId, int status, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		String name = StringPool.BLANK;
		String description = StringPool.BLANK;

		List<MBMessage> messages = new ArrayList<MBMessage>();

		int lastIntervalStart = 0;
		boolean listNotExhausted = true;
		MessageCreateDateComparator comparator =
			new MessageCreateDateComparator(false);

		while ((messages.size() < max) && listNotExhausted) {
			List<MBMessage> messageList =
				mbMessageLocalService.getGroupMessages(
					groupId, userId, status, lastIntervalStart,
					lastIntervalStart + max, comparator);

			Iterator<MBMessage> itr = messageList.iterator();

			lastIntervalStart += max;
			listNotExhausted = (messageList.size() == max);

			while (itr.hasNext() && (messages.size() < max)) {
				MBMessage message = itr.next();

				if (MBMessagePermission.contains(
						getPermissionChecker(), message, ActionKeys.VIEW)) {

					messages.add(message);
				}
			}
		}

		if (messages.size() > 0) {
			MBMessage message = messages.get(messages.size() - 1);

			name = message.getSubject();
			description = message.getSubject();
		}

		return exportToRSS(
			name, description, type, version, displayStyle, feedURL, entryURL,
			messages, themeDisplay);
	}

	public MBMessage getMessage(long messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.VIEW);

		return mbMessageLocalService.getMessage(messageId);
	}

	public MBMessageDisplay getMessageDisplay(
			long messageId, int status, String threadView)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.VIEW);

		return mbMessageLocalService.getMessageDisplay(
			messageId, status, threadView);
	}

	public String getThreadMessagesRSS(
			long threadId, int status, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		String name = StringPool.BLANK;
		String description = StringPool.BLANK;

		List<MBMessage> messages = new ArrayList<MBMessage>();

		MessageCreateDateComparator comparator =
			new MessageCreateDateComparator(false);

		Iterator<MBMessage> itr = mbMessageLocalService.getThreadMessages(
			threadId, status, comparator).iterator();

		while (itr.hasNext() && (messages.size() < max)) {
			MBMessage message = itr.next();

			if (MBMessagePermission.contains(
					getPermissionChecker(), message, ActionKeys.VIEW)) {

				messages.add(message);
			}
		}

		if (messages.size() > 0) {
			MBMessage message = messages.get(messages.size() - 1);

			name = message.getSubject();
			description = message.getSubject();
		}

		return exportToRSS(
			name, description, type, version, displayStyle, feedURL, entryURL,
			messages, themeDisplay);
	}

	public void subscribeMessage(long messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.SUBSCRIBE);

		mbMessageLocalService.subscribeMessage(getUserId(), messageId);
	}

	public void unsubscribeMessage(long messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.SUBSCRIBE);

		mbMessageLocalService.unsubscribeMessage(getUserId(), messageId);
	}

	public MBMessage updateDiscussionMessage(
			String className, long classPK, String permissionClassName,
			long permissionClassPK, long messageId, String subject, String body,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = getUser();

		MBDiscussionPermission.check(
			getPermissionChecker(), user.getCompanyId(),
			serviceContext.getScopeGroupId(), permissionClassName,
			permissionClassPK, messageId, user.getUserId(),
			ActionKeys.UPDATE_DISCUSSION);

		return mbMessageLocalService.updateDiscussionMessage(
			getUserId(), messageId, subject, body, serviceContext.getStatus());
	}

	public MBMessage updateMessage(
			long messageId, String subject, String body,
			List<ObjectValuePair<String, byte[]>> files,
			List<String> existingFiles, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		MBMessage message = mbMessageLocalService.getMessage(messageId);

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.UPDATE);

		if (lockLocalService.isLocked(
				MBThread.class.getName(), message.getThreadId())) {

			throw new LockedThreadException();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), message.getGroupId(),
				message.getCategoryId(), ActionKeys.ADD_FILE)) {

			files.clear();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), message.getGroupId(),
				message.getCategoryId(), ActionKeys.UPDATE_THREAD_PRIORITY)) {

			MBThread thread = mbThreadLocalService.getThread(
				message.getThreadId());

			priority = thread.getPriority();
		}

		return mbMessageLocalService.updateMessage(
			getUserId(), messageId, subject, body, files, existingFiles,
			priority, allowPingbacks, serviceContext);
	}

	protected void checkReplyToPermission(
			long groupId, long categoryId, long parentMessageId)
		throws PortalException, SystemException {

		if (parentMessageId > 0) {
			if (MBCategoryPermission.contains(
					getPermissionChecker(), groupId, categoryId,
					ActionKeys.ADD_MESSAGE)) {

				return;
			}

			MBMessage parentMessage = mbMessagePersistence.fetchByPrimaryKey(
				parentMessageId);

			if ((parentMessage == null) ||
				!MBCategoryPermission.contains(
					getPermissionChecker(), groupId, categoryId,
					ActionKeys.REPLY_TO_MESSAGE)) {

				throw new PrincipalException();
			}
		}
		else {
			MBCategoryPermission.check(
				getPermissionChecker(), groupId, categoryId,
				ActionKeys.ADD_MESSAGE);
		}
	}

	protected String exportToRSS(
			String name, String description, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			List<MBMessage> messages, ThemeDisplay themeDisplay)
		throws SystemException {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setFeedType(RSSUtil.getFeedType(type, version));
		syndFeed.setTitle(name);
		syndFeed.setLink(feedURL);
		syndFeed.setDescription(description);

		List<SyndEntry> entries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(entries);

		Iterator<MBMessage> itr = messages.iterator();

		while (itr.hasNext()) {
			MBMessage message = itr.next();

			String author = HtmlUtil.escape(
				PortalUtil.getUserName(
					message.getUserId(), message.getUserName()));

			String value = null;

			if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT)) {
				value = StringUtil.shorten(
					HtmlUtil.extractText(message.getBody()),
					_RSS_ABSTRACT_LENGTH, StringPool.BLANK);
			}
			else if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
				value = StringPool.BLANK;
			}
			else {
				value = BBCodeUtil.getHTML(message);

				value = StringUtil.replace(
					value,
					new String[] {
						"@theme_images_path@",
						"href=\"/",
						"src=\"/"
					},
					new String[] {
						themeDisplay.getURLPortal() +
							themeDisplay.getPathThemeImages(),
						"href=\"" + themeDisplay.getURLPortal() + "/",
						"src=\"" + themeDisplay.getURLPortal() + "/"
					});
			}

			SyndEntry syndEntry = new SyndEntryImpl();

			if (!message.isAnonymous()) {
				syndEntry.setAuthor(author);
			}

			syndEntry.setTitle(message.getSubject());
			syndEntry.setLink(
				entryURL + "&messageId=" + message.getMessageId());
			syndEntry.setUri(syndEntry.getLink());
			syndEntry.setPublishedDate(message.getCreateDate());
			syndEntry.setUpdatedDate(message.getModifiedDate());

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.DEFAULT_ENTRY_TYPE);
			syndContent.setValue(value);

			syndEntry.setDescription(syndContent);

			entries.add(syndEntry);
		}

		try {
			return RSSUtil.export(syndFeed);
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}
	}

	private static final int _RSS_ABSTRACT_LENGTH = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.MESSAGE_BOARDS_RSS_ABSTRACT_LENGTH));

}