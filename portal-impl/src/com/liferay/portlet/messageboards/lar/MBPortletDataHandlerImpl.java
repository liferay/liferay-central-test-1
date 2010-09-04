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

package com.liferay.portlet.messageboards.lar;

import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageFlag;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageFlagLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBBanUtil;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFlagUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageUtil;
import com.liferay.portlet.messageboards.util.MBUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class MBPortletDataHandlerImpl extends BasePortletDataHandler {

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_categoriesAndMessages, _attachments, _messageFlags, _userBans,
			_ratings, _tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_categoriesAndMessages, _attachments, _messageFlags, _userBans,
			_ratings, _tags
		};
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		if (!context.addPrimaryKey(
				MBPortletDataHandlerImpl.class, "deleteData")) {

			MBCategoryLocalServiceUtil.deleteCategories(
				context.getScopeGroupId());

			MBThreadLocalServiceUtil.deleteThreads(
				context.getScopeGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
		}

		return null;
	}

	protected String doExportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		context.addPermissions(
			"com.liferay.portlet.messageboards", context.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("message-boards-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(context.getScopeGroupId()));

		Element categoriesElement = rootElement.addElement("categories");
		Element messagesElement = rootElement.addElement("messages");
		Element messageFlagsElement = rootElement.addElement("message-flags");
		Element userBansElement = rootElement.addElement("user-bans");

		List<MBCategory> categories = MBCategoryUtil.findByGroupId(
			context.getScopeGroupId());

		for (MBCategory category : categories) {
			exportCategory(
				context, categoriesElement, messagesElement,
				messageFlagsElement, category);
		}

		List<MBMessage> messages = MBMessageUtil.findByG_C(
			context.getScopeGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		for (MBMessage message : messages) {
			exportMessage(
				context, categoriesElement, messagesElement,
				messageFlagsElement, message);
		}

		if (context.getBooleanParameter(_NAMESPACE, "user-bans")) {
			List<MBBan> bans = MBBanUtil.findByGroupId(
				context.getScopeGroupId());

			for (MBBan ban : bans) {
				exportBan(context, userBansElement, ban);
			}
		}

		return document.formattedString();
	}

	protected PortletPreferences doImportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws Exception {

		context.importPermissions(
			"com.liferay.portlet.messageboards", context.getSourceGroupId(),
			context.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element categoriesElement = rootElement.element("categories");

		for (Element categoryElement : categoriesElement.elements("category")) {
			String path = categoryElement.attributeValue("path");

			if (!context.isPathNotProcessed(path)) {
				continue;
			}

			MBCategory category = (MBCategory)context.getZipEntryAsObject(path);

			importCategory(context, category);
		}

		Element messagesElement = rootElement.element("messages");

		for (Element messageElement : messagesElement.elements("message")) {
			String path = messageElement.attributeValue("path");

			if (!context.isPathNotProcessed(path)) {
				continue;
			}

			MBMessage message = (MBMessage)context.getZipEntryAsObject(
				path);

			importMessage(context, messageElement, message);
		}

		if (context.getBooleanParameter(_NAMESPACE, "message-flags")) {
			Element messageFlagsElement = rootElement.element("message-flags");

			for (Element messageFlagElement :
					messageFlagsElement.elements("message-flag")) {

				String path = messageFlagElement.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				MBMessageFlag flag = (MBMessageFlag)context.getZipEntryAsObject(
					path);

				importMessageFlag(context, flag);
			}
		}

		if (context.getBooleanParameter(_NAMESPACE, "user-bans")) {
			Element userBansElement = rootElement.element("user-bans");

			for (Element userBanElement :
					userBansElement.elements("user-ban")) {

				String path = userBanElement.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				MBBan ban = (MBBan)context.getZipEntryAsObject(path);

				importBan(context, ban);
			}
		}

		return null;
	}

	protected void exportBan(
			PortletDataContext context, Element userBansElement, MBBan ban)
		throws Exception {

		if (!context.isWithinDateRange(ban.getModifiedDate())) {
			return;
		}

		String path = getUserBanPath(context, ban);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element userBanElement = userBansElement.addElement("user-ban");

		userBanElement.addAttribute("path", path);

		ban.setBanUserUuid(ban.getBanUserUuid());
		ban.setUserUuid(ban.getUserUuid());

		context.addZipEntry(path, ban);
	}

	protected void exportCategory(
			PortletDataContext context, Element categoriesElement,
			Element messagesElement, Element messageFlagsElement,
			MBCategory category)
		throws Exception {

		if (context.isWithinDateRange(category.getModifiedDate())) {
			exportParentCategory(
				context, categoriesElement, category.getParentCategoryId());

			String path = getCategoryPath(context, category);

			if (context.isPathNotProcessed(path)) {
				Element categoryElement = categoriesElement.addElement(
					"category");

				categoryElement.addAttribute("path", path);

				category.setUserUuid(category.getUserUuid());

				context.addPermissions(
					MBCategory.class, category.getCategoryId());

				context.addZipEntry(path, category);
			}
		}

		List<MBMessage> messages = MBMessageUtil.findByG_C(
			category.getGroupId(), category.getCategoryId());

		for (MBMessage message : messages) {
			exportMessage(
				context, categoriesElement, messagesElement,
				messageFlagsElement, message);
		}
	}

	protected void exportMessage(
			PortletDataContext context, Element categoriesElement,
			Element messagesElement, Element messageFlagsElement,
			MBMessage message)
		throws Exception {

		if (!context.isWithinDateRange(message.getModifiedDate())) {
			return;
		}

		if (message.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			return;
		}

		exportParentCategory(
			context, categoriesElement, message.getCategoryId());

		String path = getMessagePath(context, message);

		if (context.isPathNotProcessed(path)) {
			Element messageElement = messagesElement.addElement("message");

			messageElement.addAttribute("path", path);

			message.setUserUuid(message.getUserUuid());
			message.setPriority(message.getPriority());

			context.addPermissions(MBMessage.class, message.getMessageId());

			context.addLocks(
				MBThread.class, String.valueOf(message.getThreadId()));

			if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
				context.addRatingsEntries(
					MBMessage.class, message.getMessageId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "tags")) {
				context.addAssetTags(MBMessage.class, message.getMessageId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "attachments") &&
				message.isAttachments()) {

				for (String attachment : message.getAttachmentsFiles()) {
					int pos = attachment.lastIndexOf(CharPool.FORWARD_SLASH);

					String name = attachment.substring(pos + 1);
					String binPath = getMessageAttachementBinPath(
						context, message, name);

					Element attachmentElement = messageElement.addElement(
						"attachment");

					attachmentElement.addAttribute("name", name);
					attachmentElement.addAttribute("bin-path", binPath);

					byte[] bytes = DLServiceUtil.getFile(
						context.getCompanyId(), CompanyConstants.SYSTEM,
						attachment);

					context.addZipEntry(binPath, bytes);
				}

				message.setAttachmentsDir(message.getAttachmentsDir());
			}

			if (context.getBooleanParameter(_NAMESPACE, "message-flags")) {
				List<MBMessageFlag> messageFlags =
					MBMessageFlagUtil.findByMessageId(message.getMessageId());

				for (MBMessageFlag messageFlag : messageFlags) {
					exportMessageFlag(
						context, messageFlagsElement, messageFlag);
				}
			}

			context.addZipEntry(path, message);
		}
	}

	protected void exportMessageFlag(
			PortletDataContext context, Element messageFlagsElement,
			MBMessageFlag messageFlag)
		throws Exception {

		String path = getMessageFlagPath(context, messageFlag);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element messageFlagElement = messageFlagsElement.addElement(
			"message-flag");

		messageFlagElement.addAttribute("path", path);

		messageFlag.setUserUuid(messageFlag.getUserUuid());

		context.addZipEntry(path, messageFlag);
	}

	protected void exportParentCategory(
			PortletDataContext context, Element categoriesElement,
			long categoryId)
		throws Exception {

		if ((!context.hasDateRange()) ||
			MBUtil.isDefaultParentCategoryId(categoryId) ||
			MBUtil.isDiscussionCategoryId(categoryId)) {

			return;
		}

		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);

		exportParentCategory(
			context, categoriesElement, category.getParentCategoryId());

		String path = getCategoryPath(context, category);

		if (context.isPathNotProcessed(path)) {
			Element categoryElement = categoriesElement.addElement("category");

			categoryElement.addAttribute("path", path);

			category.setUserUuid(category.getUserUuid());

			context.addPermissions(MBCategory.class, category.getCategoryId());

			context.addZipEntry(path, category);
		}
	}

	protected String getCategoryPath(
		PortletDataContext context, MBCategory category) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/categories/");
		sb.append(category.getCategoryId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getImportCategoryPath(
		PortletDataContext context, long categoryId) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getSourcePortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/categories/");
		sb.append(categoryId);
		sb.append(".xml");

		return sb.toString();
	}

	protected String getMessageAttachementBinPath(
		PortletDataContext context, MBMessage message, String attachment) {

		StringBundler sb = new StringBundler(5);

		sb.append(context.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/bin/");
		sb.append(message.getMessageId());
		sb.append(StringPool.SLASH);
		sb.append(PortalUUIDUtil.generate());

		return sb.toString();
	}

	protected String getMessageFlagPath(
		PortletDataContext context, MBMessageFlag messageFlag) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/message-flags/");
		sb.append(messageFlag.getMessageFlagId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getMessagePath(
		PortletDataContext context, MBMessage message) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/messages/");
		sb.append(message.getMessageId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getUserBanPath(PortletDataContext context, MBBan ban) {
		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/user-bans/");
		sb.append(ban.getBanId());
		sb.append(".xml");

		return sb.toString();
	}

	protected void importBan(PortletDataContext context, MBBan ban)
		throws Exception {

		long userId = context.getUserId(ban.getUserUuid());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCreateDate(ban.getCreateDate());
		serviceContext.setModifiedDate(ban.getModifiedDate());
		serviceContext.setScopeGroupId(context.getScopeGroupId());

		List<User> users = UserUtil.findByUuid(ban.getBanUserUuid());

		Iterator<User> itr = users.iterator();

		if (itr.hasNext()) {
			User user = itr.next();

			MBBanLocalServiceUtil.addBan(
				userId, user.getUserId(), serviceContext);
		}
		else {
			_log.error(
				"Could not find banned user with uuid " + ban.getBanUserUuid());
		}
	}

	protected void importCategory(
			PortletDataContext context, MBCategory category)
		throws Exception {

		long userId = context.getUserId(category.getUserUuid());

		Map<Long, Long> categoryPKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(MBCategory.class);

		long parentCategoryId = MapUtil.getLong(
			categoryPKs, category.getParentCategoryId(),
			category.getParentCategoryId());

		String emailAddress = null;
		String inProtocol = null;
		String inServerName = null;
		int inServerPort = 0;
		boolean inUseSSL = false;
		String inUserName = null;
		String inPassword = null;
		int inReadInterval = 0;
		String outEmailAddress = null;
		boolean outCustom = false;
		String outServerName = null;
		int outServerPort = 0;
		boolean outUseSSL = false;
		String outUserName = null;
		String outPassword = null;
		boolean mailingListActive = false;

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(category.getCreateDate());
		serviceContext.setModifiedDate(category.getModifiedDate());
		serviceContext.setScopeGroupId(context.getScopeGroupId());

		if (!MBUtil.isDefaultParentCategoryId(parentCategoryId) &&
			!MBUtil.isDiscussionCategoryId(parentCategoryId) &&
			(parentCategoryId == category.getParentCategoryId())) {

			String path = getImportCategoryPath(context, parentCategoryId);

			MBCategory parentCategory =
				(MBCategory)context.getZipEntryAsObject(path);

			importCategory(context, parentCategory);

			parentCategoryId = MapUtil.getLong(
				categoryPKs, category.getParentCategoryId(),
				category.getParentCategoryId());
		}

		MBCategory importedCategory = null;

		if (context.isDataStrategyMirror()) {
			MBCategory existingCategory = MBCategoryUtil.fetchByUUID_G(
				category.getUuid(), context.getScopeGroupId());

			if (existingCategory == null) {
				serviceContext.setUuid(category.getUuid());

				importedCategory = MBCategoryLocalServiceUtil.addCategory(
					userId, parentCategoryId, category.getName(),
					category.getDescription(), emailAddress, inProtocol,
					inServerName, inServerPort, inUseSSL, inUserName,
					inPassword, inReadInterval, outEmailAddress, outCustom,
					outServerName, outServerPort, outUseSSL, outUserName,
					outPassword, mailingListActive, serviceContext);
			}
			else {
				importedCategory = MBCategoryLocalServiceUtil.updateCategory(
					existingCategory.getCategoryId(), parentCategoryId,
					category.getName(), category.getDescription(), emailAddress,
					inProtocol, inServerName, inServerPort, inUseSSL,
					inUserName, inPassword, inReadInterval, outEmailAddress,
					outCustom, outServerName, outServerPort, outUseSSL,
					outUserName, outPassword, mailingListActive, false,
					serviceContext);
			}
		}
		else {
			importedCategory = MBCategoryLocalServiceUtil.addCategory(
				userId, parentCategoryId, category.getName(),
				category.getDescription(), emailAddress, inProtocol,
				inServerName, inServerPort, inUseSSL, inUserName, inPassword,
				inReadInterval, outEmailAddress, outCustom, outServerName,
				outServerPort, outUseSSL, outUserName, outPassword,
				mailingListActive, serviceContext);
		}

		categoryPKs.put(
			category.getCategoryId(), importedCategory.getCategoryId());

		context.importPermissions(
			MBCategory.class, category.getCategoryId(),
			importedCategory.getCategoryId());
	}

	protected void importMessage(
			PortletDataContext context, Element messageElement,
			MBMessage message)
		throws Exception {

		long userId = context.getUserId(message.getUserUuid());
		String userName = message.getUserName();

		Map<Long, Long> categoryPKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(MBCategory.class);

		long categoryId = MapUtil.getLong(
			categoryPKs, message.getCategoryId(), message.getCategoryId());

		Map<Long, Long> threadPKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(MBThread.class);

		long threadId = MapUtil.getLong(
			threadPKs, message.getThreadId(), message.getThreadId());

		Map<Long, Long> messagePKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(MBMessage.class);

		long parentMessageId = MapUtil.getLong(
			messagePKs, message.getParentMessageId(),
			message.getParentMessageId());

		List<ObjectValuePair<String, byte[]>> files =
			new ArrayList<ObjectValuePair<String, byte[]>>();
		List<String> existingFiles = new ArrayList<String>();

		if (context.getBooleanParameter(_NAMESPACE, "attachments") &&
			message.isAttachments()) {

			List<Element> attachmentElements = messageElement.elements(
				"attachment");

			for (Element attachmentElement : attachmentElements) {
				String name = attachmentElement.attributeValue("name");
				String binPath = attachmentElement.attributeValue("bin-path");

				byte[] bytes = context.getZipEntryAsByteArray(binPath);

				files.add(new ObjectValuePair<String, byte[]>(name, bytes));
			}

			if (files.size() <= 0) {
				_log.error(
					"Could not find attachments for message " +
						message.getMessageId());
			}
		}

		String[] assetTagNames = null;

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = context.getAssetTagNames(
				MBMessage.class, message.getMessageId());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setCreateDate(message.getCreateDate());
		serviceContext.setModifiedDate(message.getModifiedDate());
		serviceContext.setScopeGroupId(context.getScopeGroupId());

		if (message.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		if (!MBUtil.isDefaultParentCategoryId(categoryId) &&
			!MBUtil.isDiscussionCategoryId(categoryId) &&
			(categoryId == message.getCategoryId())) {

			String path = getImportCategoryPath(context, categoryId);

			MBCategory category = (MBCategory)context.getZipEntryAsObject(path);

			importCategory(context, category);

			categoryId = MapUtil.getLong(
				categoryPKs, message.getCategoryId(), message.getCategoryId());
		}

		MBMessage importedMessage = null;

		if (context.isDataStrategyMirror()) {
			MBMessage existingMessage = MBMessageUtil.fetchByUUID_G(
				message.getUuid(), context.getScopeGroupId());

			if (existingMessage == null) {
				serviceContext.setUuid(message.getUuid());

				importedMessage = MBMessageLocalServiceUtil.addMessage(
					userId, userName, context.getScopeGroupId(), categoryId,
					threadId, parentMessageId, message.getSubject(),
					message.getBody(), files, message.getAnonymous(),
					message.getPriority(), message.getAllowPingbacks(),
					serviceContext);
			}
			else {
				importedMessage = MBMessageLocalServiceUtil.updateMessage(
					userId, existingMessage.getMessageId(),
					message.getSubject(), message.getBody(), files,
					existingFiles, message.getPriority(),
					message.getAllowPingbacks(), serviceContext);
			}
		}
		else {
			importedMessage = MBMessageLocalServiceUtil.addMessage(
				userId, userName, context.getScopeGroupId(), categoryId,
				threadId, parentMessageId, message.getSubject(),
				message.getBody(), files, message.getAnonymous(),
				message.getPriority(), message.getAllowPingbacks(),
				serviceContext);
		}

		threadPKs.put(message.getThreadId(), importedMessage.getThreadId());
		messagePKs.put(message.getMessageId(), importedMessage.getMessageId());

		context.importLocks(
			MBThread.class, String.valueOf(message.getThreadId()),
			String.valueOf(importedMessage.getThreadId()));

		context.importPermissions(
			MBMessage.class, message.getMessageId(),
			importedMessage.getMessageId());

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.importRatingsEntries(
				MBMessage.class, message.getMessageId(),
				importedMessage.getMessageId());
		}
	}

	protected void importMessageFlag(
			PortletDataContext context, MBMessageFlag flag)
		throws Exception {

		long userId = context.getUserId(flag.getUserUuid());

		Map<Long, Long> messagePKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(MBMessage.class);

		long messageId = MapUtil.getLong(
			messagePKs, flag.getMessageId(), flag.getMessageId());

		MBMessage message = MBMessageUtil.findByPrimaryKey(messageId);

		MBThread thread = message.getThread();

		MBMessageFlagLocalServiceUtil.addReadFlags(userId, thread);
	}

	private static final String _NAMESPACE = "message_board";

	private static Log _log = LogFactoryUtil.getLog(
		MBPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _attachments =
		new PortletDataHandlerBoolean(_NAMESPACE, "attachments");

	private static PortletDataHandlerBoolean _categoriesAndMessages =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "categories-and-messages", true, true);

	private static PortletDataHandlerBoolean _messageFlags =
		new PortletDataHandlerBoolean(_NAMESPACE, "message-flags");

	private static PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static PortletDataHandlerBoolean _userBans =
		new PortletDataHandlerBoolean(_NAMESPACE, "user-bans");

}