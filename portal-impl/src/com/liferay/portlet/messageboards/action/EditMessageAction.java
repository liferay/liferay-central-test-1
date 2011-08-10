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

package com.liferay.portlet.messageboards.action;

import com.liferay.portal.kernel.captcha.CaptchaMaxChallengesException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.messageboards.LockedThreadException;
import com.liferay.portlet.messageboards.MessageBodyException;
import com.liferay.portlet.messageboards.MessageSubjectException;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.RequiredMessageException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.service.MBMessageFlagLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Sanz
 */
public class EditMessageAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			MBMessage message = null;

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				message = updateMessage(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteMessage(actionRequest);
			}
			else if (cmd.equals(Constants.LOCK)) {
				lockThreads(actionRequest);
			}
			else if (cmd.equals(Constants.PREVIEW)) {
				long messageId = ParamUtil.getLong(actionRequest, "messageId");

				if (messageId > 0) {
					message = MBMessageLocalServiceUtil.getMBMessage(messageId);
				}
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribeMessage(actionRequest);
			}
			else if (cmd.equals(Constants.UNLOCK)) {
				unlockThreads(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribeMessage(actionRequest);
			}

			if (Validator.isNotNull(cmd)) {
				String redirect = getRedirect(
					actionRequest, actionResponse, message);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchMessageException ||
				e instanceof PrincipalException ||
				e instanceof RequiredMessageException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.message_boards.error");
			}
			else if (e instanceof CaptchaMaxChallengesException ||
					 e instanceof CaptchaTextException ||
					 e instanceof FileExtensionException ||
					 e instanceof FileNameException ||
					 e instanceof FileSizeException ||
					 e instanceof LockedThreadException ||
					 e instanceof MessageBodyException ||
					 e instanceof MessageSubjectException) {

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else if (e instanceof AssetCategoryException ||
					 e instanceof AssetTagException) {

				SessionErrors.add(actionRequest, e.getClass().getName(), e);
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getMessage(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchMessageException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.message_boards.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.message_boards.edit_message"));
	}

	protected void deleteMessage(ActionRequest actionRequest) throws Exception {
		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		MBMessageServiceUtil.deleteMessage(messageId);
	}

	protected String getRedirect(
		ActionRequest actionRequest, ActionResponse actionResponse,
		MBMessage message) {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		int workflowAction = ParamUtil.getInteger(
			actionRequest, "workflowAction", WorkflowConstants.ACTION_PUBLISH);

		if (((message == null) && cmd.equals(Constants.PREVIEW)) ||
			(workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT)) {

			return getSaveAndContinueRedirect(
				actionRequest, actionResponse, message);
		}
		else if (message == null) {
			return ParamUtil.getString(actionRequest, "redirect");
		}

		ActionResponseImpl actionResponseImpl =
			(ActionResponseImpl)actionResponse;

		PortletURL portletURL = actionResponseImpl.createRenderURL();

		portletURL.setParameter(
			"struts_action", "/message_boards/view_message");
		portletURL.setParameter(
			"messageId", String.valueOf(message.getMessageId()));

		return portletURL.toString();
	}

	protected String getSaveAndContinueRedirect(
		ActionRequest actionRequest, ActionResponse actionResponse,
		MBMessage message) {

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		String subject = ParamUtil.getString(actionRequest, "subject");
		String body = ParamUtil.getString(actionRequest, "body");

		boolean preview = ParamUtil.getBoolean(actionRequest, "preview");

		PortletURL portletURL =
			((ActionResponseImpl)actionResponse).createRenderURL();

		portletURL.setParameter(
			"struts_action", "/message_boards/edit_message");
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter("preview", String.valueOf(preview));

		if (message != null) {
			portletURL.setParameter(
				"messageId", String.valueOf(message.getMessageId()));
		}
		else {
			portletURL.setParameter("body", body);
			portletURL.setParameter("subject", subject);
		}

		return portletURL.toString();
	}

	protected void lockThreads(ActionRequest actionRequest) throws Exception {
		long threadId = ParamUtil.getLong(actionRequest, "threadId");

		if (threadId > 0) {
			MBThreadServiceUtil.lockThread(threadId);
		}
		else {
			long[] threadIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "threadIds"), 0L);

			for (int i = 0; i < threadIds.length; i++) {
				MBThreadServiceUtil.lockThread(threadIds[i]);
			}
		}
	}

	protected void subscribeMessage(ActionRequest actionRequest)
		throws Exception {

		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		MBMessageServiceUtil.subscribeMessage(messageId);
	}

	protected void unlockThreads(ActionRequest actionRequest) throws Exception {
		long threadId = ParamUtil.getLong(actionRequest, "threadId");

		if (threadId > 0) {
			MBThreadServiceUtil.unlockThread(threadId);
		}
		else {
			long[] threadIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "threadIds"), 0L);

			for (int i = 0; i < threadIds.length; i++) {
				MBThreadServiceUtil.unlockThread(threadIds[i]);
			}
		}
	}

	protected void unsubscribeMessage(ActionRequest actionRequest)
		throws Exception {

		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		MBMessageServiceUtil.unsubscribeMessage(messageId);
	}

	protected MBMessage updateMessage(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		PortletPreferences preferences = actionRequest.getPreferences();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		long groupId = themeDisplay.getScopeGroupId();
		long categoryId = ParamUtil.getLong(actionRequest, "mbCategoryId");
		long threadId = ParamUtil.getLong(actionRequest, "threadId");
		long parentMessageId = ParamUtil.getLong(
			actionRequest, "parentMessageId");
		String subject = ParamUtil.getString(actionRequest, "subject");
		String body = ParamUtil.getString(actionRequest, "body");

		String format = GetterUtil.getString(
			preferences.getValue("messageFormat", null),
			MBMessageConstants.DEFAULT_FORMAT);

		boolean attachments = ParamUtil.getBoolean(
			actionRequest, "attachments");

		List<ObjectValuePair<String, File>> files =
			new ArrayList<ObjectValuePair<String, File>>();

		if (attachments) {
			UploadPortletRequest uploadRequest =
				PortalUtil.getUploadPortletRequest(actionRequest);

			for (int i = 1; i <= 5; i++) {
				File file = uploadRequest.getFile("msgFile" + i);
				String fileName = uploadRequest.getFileName("msgFile" + i);

				if ((file != null) && file.exists()) {
					ObjectValuePair<String, File> ovp =
						new ObjectValuePair<String, File>(fileName, file);

					files.add(ovp);
				}
			}
		}

		boolean question = ParamUtil.getBoolean(actionRequest, "question");
		boolean anonymous = ParamUtil.getBoolean(actionRequest, "anonymous");
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		boolean allowPingbacks = ParamUtil.getBoolean(
			actionRequest, "allowPingbacks");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			MBMessage.class.getName(), actionRequest);

		boolean preview = ParamUtil.getBoolean(actionRequest, "preview");

		serviceContext.setAttribute("preview", preview);

		MBMessage message = null;

		if (messageId <= 0) {
			if (PropsValues.CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_MESSAGE) {
				CaptchaUtil.check(actionRequest);
			}

			if (threadId <= 0) {

				// Post new thread

				message = MBMessageServiceUtil.addMessage(
					groupId, categoryId, subject, body, format, files,
					anonymous, priority, allowPingbacks, serviceContext);

				if (question) {
					MBMessageFlagLocalServiceUtil.addQuestionFlag(
						message.getMessageId());
				}
			}
			else {

				// Post reply

				message = MBMessageServiceUtil.addMessage(
					groupId, categoryId, threadId, parentMessageId, subject,
					body, format, files, anonymous, priority, allowPingbacks,
					serviceContext);
			}
		}
		else {
			List<String> existingFiles = new ArrayList<String>();

			for (int i = 1; i <= 5; i++) {
				String path = ParamUtil.getString(
					actionRequest, "existingPath" + i);

				if (Validator.isNotNull(path)) {
					existingFiles.add(path);
				}
			}

			// Update message

			message = MBMessageServiceUtil.updateMessage(
				messageId, subject, body, files, existingFiles, priority,
				allowPingbacks, serviceContext);

			if (message.isRoot()) {
				if (question) {
					MBMessageFlagLocalServiceUtil.addQuestionFlag(messageId);
				}
				else {
					MBMessageFlagLocalServiceUtil.deleteQuestionAndAnswerFlags(
						message.getThreadId());
				}
			}
		}

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		boolean subscribe = ParamUtil.getBoolean(
			actionRequest, "subscribe");

		if (subscribe &&
			MBMessagePermission.contains(
				permissionChecker, message,	ActionKeys.SUBSCRIBE)) {

			MBMessageServiceUtil.subscribeMessage(message.getMessageId());
		}

		return message;
	}

}