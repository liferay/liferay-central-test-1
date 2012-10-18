/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.documentlibrary.NoSuchDirectoryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.Date;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * Represents the trash handler for message boards threads.
 *
 * @author Zsolt Berentey
 */
public class MBThreadTrashHandler extends BaseTrashHandler {

	public static final String CLASS_NAME = MBThread.class.getName();

	/**
	 * Deletes trash attachments from all the message boards messages from a
	 * group that were deleted after a given date.
	 *
	 * @param  group the group
	 * @param  date the date from which attachments will be deleted
	 * @throws PortalException if the attachments could not be found or deleted
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteTrashAttachments(Group group, Date date)
		throws PortalException, SystemException {

		long repositoryId = CompanyConstants.SYSTEM;

		String[] threadFileNames = null;

		try {
			threadFileNames = DLStoreUtil.getFileNames(
				group.getCompanyId(), repositoryId, "messageboards");
		}
		catch (NoSuchDirectoryException nsde) {
			return;
		}

		for (String threadFileName : threadFileNames) {
			String[] messageFileNames = null;

			try {
				messageFileNames = DLStoreUtil.getFileNames(
					group.getCompanyId(), repositoryId, threadFileName);
			}
			catch (NoSuchDirectoryException nsde) {
				continue;
			}

			for (String messageFileName : messageFileNames) {
				String fileTitle = StringUtil.extractLast(
					messageFileName, StringPool.FORWARD_SLASH);

				if (fileTitle.startsWith(TrashUtil.TRASH_ATTACHMENTS_DIR)) {
					String[] attachmentFileNames = DLStoreUtil.getFileNames(
						group.getCompanyId(), repositoryId,
						threadFileName + StringPool.FORWARD_SLASH + fileTitle);

					TrashUtil.deleteEntriesAttachments(
						group.getCompanyId(), repositoryId, date,
						attachmentFileNames);
				}
			}
		}
	}

	/**
	 * Deletes all message boards threads with the matching primary keys.
	 *
	 * @param  classPKs the primary keys of the message boards threads to be
	 *         deleted
	 * @param  checkPermission whether to check for necessary permissions
	 * @throws PortalException if any one of the message boards threads could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTrashEntries(long[] classPKs, boolean checkPermission)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			if (checkPermission) {
				MBThreadServiceUtil.deleteThread(classPK);
			}
			else {
				MBThreadLocalServiceUtil.deleteThread(classPK);
			}
		}
	}

	/**
	 * Returns the message boards thread's class name
	 *
	 * @return the message boards thread's class name
	 */
	public String getClassName() {
		return CLASS_NAME;
	}

	/**
	 * Returns the link to the restored message boards thread
	 *
	 * @param  portletRequest the portlet request
	 * @param  classPK the primary key of the message boards thread
	 * @return Returns the link to the restored message boards thread
	 * @throws PortalException if the message boards thread could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public String getRestoreLink(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		MBThread thread = MBThreadLocalServiceUtil.getThread(classPK);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, PortletKeys.MESSAGE_BOARDS_ADMIN,
			PortalUtil.getControlPanelPlid(themeDisplay.getCompanyId()),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/message_boards_admin/view");

		if (thread.getCategoryId() !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			portletURL.setParameter(
				"mbCategoryId", String.valueOf(thread.getCategoryId()));
		}

		return portletURL.toString();
	}

	/**
	 * Returns the path of the restored message boards thread
	 *
	 * The returned string is a path of categories the restored message boards
	 * thread belongs to.
	 *
	 * @param  portletRequest the portlet request
	 * @param  classPK the primary key of the message boards thread
	 * @return Returns the path of the restored message boards thread
	 * @throws PortalException if the message boards thread could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String restorePath = StringPool.SLASH + themeDisplay.translate("home");

		MBThread thread = MBThreadLocalServiceUtil.getThread(classPK);

		if (thread.getCategoryId() ==
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			return restorePath;
		}

		MBCategory category = MBCategoryLocalServiceUtil.fetchMBCategory(
			thread.getCategoryId());

		return restorePath + category.getAbsolutePath();
	}

	/**
	 * Returns the trash renderer for the message boards thread with the
	 * primary key.
	 *
	 * @param  classPK the primary key of the message boards thread
	 * @return Returns the trash renderer
	 * @throws PortalException if the message boards thread could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		MBThread thread = MBThreadLocalServiceUtil.getThread(classPK);

		return new MBThreadTrashRenderer(thread);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException {

		MBThread thread = MBThreadLocalServiceUtil.getThread(classPK);

		return MBMessagePermission.contains(
			permissionChecker, thread.getRootMessageId(), actionId);
	}

	public boolean isInTrash(long classPK) {
		return false;
	}

	/**
	 * Restores all message boards threads with the matching primary keys.
	 *
	 * @param  classPKs the primary key of the message boards threads to be
	 *         restored
	 * @throws PortalException if any one of the message boards threads could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void restoreTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			MBThreadServiceUtil.restoreThreadFromTrash(classPK);
		}
	}

}