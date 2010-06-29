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
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Lock;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.LockedThreadException;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl;
import com.liferay.portlet.messageboards.service.base.MBThreadServiceBaseImpl;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="MBThreadServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Deepak Gothe
 * @author Mika Koivisto
 */
public class MBThreadServiceImpl extends MBThreadServiceBaseImpl {

	public void deleteThread(long threadId)
		throws PortalException, SystemException {

		if (lockLocalService.isLocked(
				MBThread.class.getName(), threadId)) {

			throw new LockedThreadException();
		}

		List<MBMessage> messages = mbMessagePersistence.findByThreadId(
			threadId);

		for (MBMessage message : messages) {
			MBMessagePermission.check(
				getPermissionChecker(), message.getMessageId(),
				ActionKeys.DELETE);
		}

		mbThreadLocalService.deleteThread(threadId);
	}

	public List<MBThread> getGroupThreads(
			long groupId, long userId, int status, boolean subscribed,
			boolean includeAnonymous, int start, int end)
		throws PortalException, SystemException {

		long[] categoryIds = mbCategoryService.getCategoryIds(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		if (categoryIds.length == 0) {
			return Collections.EMPTY_LIST;
		}

		if (userId <= 0) {
			if (status == WorkflowConstants.STATUS_ANY) {
				return mbThreadPersistence.findByG_C(
					groupId, categoryIds, start, end);
			}
			else {
				return mbThreadPersistence.findByG_C_S(
					groupId, categoryIds, status, start, end);
			}
		}
		else {
			if (subscribed) {
				return mbThreadFinder.findByS_G_U_S(
					groupId, userId, status, start, end);
			}
			else {
				List<Long> threadIds = null;

				if (includeAnonymous) {
					threadIds = mbMessageFinder.findByG_U_S(
						groupId, userId, status, start, end);
				}
				else {
					threadIds = mbMessageFinder.findByG_U_A_S(
						groupId, userId, false, status, start, end);
				}

				List<MBThread> threads = new ArrayList<MBThread>(
					threadIds.size());

				for (long threadId : threadIds) {
					MBThread thread = mbThreadPersistence.findByPrimaryKey(
						threadId);

					threads.add(thread);
				}

				return threads;
			}
		}
	}

	public int getGroupThreadsCount(
			long groupId, long userId, int status, boolean subscribed,
			boolean includeAnonymous)
		throws SystemException {

		long[] categoryIds = mbCategoryService.getCategoryIds(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		if (categoryIds.length == 0) {
			return 0;
		}

		if (userId <= 0) {
			if (status == WorkflowConstants.STATUS_ANY) {
				return mbThreadPersistence.countByG_C(
					groupId, categoryIds);
			}
			else {
				return mbThreadPersistence.countByG_C_S(
					groupId, categoryIds, status);
			}
		}
		else {
			if (subscribed) {
				return mbThreadFinder.countByS_G_U_S(groupId, userId, status);
			}
			else {
				if (includeAnonymous) {
					return mbMessageFinder.countByG_U_S(
						groupId, userId, status);
				}
				else {
					return mbMessageFinder.countByG_U_A_S(
						groupId, userId, false, status);
				}
			}
		}
	}

	public List<MBThread> getThreads(
			long groupId, long categoryId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbThreadFinder.filterFindByG_C(
				groupId, categoryId, start, end);
		}
		else {
			return mbThreadFinder.filterFindByG_C_S(
				groupId, categoryId, status, start, end);
		}
	}

	public int getThreadsCount(long groupId, long categoryId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbThreadFinder.filterCountByG_C(groupId, categoryId);
		}
		else {
			return mbThreadFinder.filterCountByG_C_S(
				groupId, categoryId, status);
		}
	}

	public Lock lockThread(long threadId)
		throws PortalException, SystemException {

		MBThread thread = mbThreadLocalService.getThread(threadId);

		MBCategoryPermission.check(
			getPermissionChecker(), thread.getGroupId(), thread.getCategoryId(),
			ActionKeys.LOCK_THREAD);

		return lockLocalService.lock(
			getUserId(), MBThread.class.getName(), threadId,
			String.valueOf(threadId), false,
			MBThreadModelImpl.LOCK_EXPIRATION_TIME);
	}

	public MBThread moveThread(long categoryId, long threadId)
		throws PortalException, SystemException {

		MBThread thread = mbThreadLocalService.getThread(threadId);

		MBCategoryPermission.check(
			getPermissionChecker(), thread.getGroupId(), thread.getCategoryId(),
			ActionKeys.MOVE_THREAD);

		MBCategoryPermission.check(
			getPermissionChecker(), thread.getGroupId(), categoryId,
			ActionKeys.MOVE_THREAD);

		return mbThreadLocalService.moveThread(
			thread.getGroupId(), categoryId, threadId);
	}

	public MBThread splitThread(long messageId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MBMessage message = mbMessageLocalService.getMessage(messageId);

		MBCategoryPermission.check(
			getPermissionChecker(), message.getGroupId(),
			message.getCategoryId(), ActionKeys.MOVE_THREAD);

		return mbThreadLocalService.splitThread(messageId, serviceContext);
	}

	public void unlockThread(long threadId)
		throws PortalException, SystemException {

		MBThread thread = mbThreadLocalService.getThread(threadId);

		MBCategoryPermission.check(
			getPermissionChecker(), thread.getGroupId(), thread.getCategoryId(),
			ActionKeys.LOCK_THREAD);

		lockLocalService.unlock(MBThread.class.getName(), threadId);
	}

}