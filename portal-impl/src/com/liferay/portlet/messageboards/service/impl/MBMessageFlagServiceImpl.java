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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portlet.messageboards.NoSuchMessageFlagException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageFlag;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBMessageFlagImpl;
import com.liferay.portlet.messageboards.service.base.MBMessageFlagServiceBaseImpl;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;

/**
 * <a href="MBMessageFlagServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBMessageFlagServiceImpl extends MBMessageFlagServiceBaseImpl {

	public void addAnswerFlag(long messageId)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		if (message.isRoot()) {
			return;
		}

		MBThread thread = mbThreadPersistence.findByPrimaryKey(
			message.getThreadId());

		MBMessage rootMessage = mbMessagePersistence.findByPrimaryKey(
			thread.getRootMessageId());

		MBMessagePermission.check(
			getPermissionChecker(), rootMessage.getMessageId(),
			ActionKeys.UPDATE);

		MBMessageFlag questionMessageFlag =
			mbMessageFlagPersistence.fetchByU_M_F(
				rootMessage.getUserId(), rootMessage.getMessageId(),
				MBMessageFlagImpl.QUESTION_FLAG);

		MBMessageFlag answerMessageFlag =
			mbMessageFlagPersistence.fetchByU_M_F(
				rootMessage.getUserId(), rootMessage.getMessageId(),
				MBMessageFlagImpl.ANSWER_FLAG);

		if ((questionMessageFlag != null) && (answerMessageFlag == null)) {
			questionMessageFlag.setFlag(MBMessageFlagImpl.ANSWER_FLAG);

			mbMessageFlagPersistence.update(questionMessageFlag, false);
		}

		MBMessageFlag messageFlag = mbMessageFlagPersistence.fetchByU_M_F(
			message.getUserId(), message.getMessageId(),
			MBMessageFlagImpl.ANSWER_FLAG);

		if (messageFlag == null) {
			long messageFlagId = counterLocalService.increment();

			messageFlag = mbMessageFlagPersistence.create(messageFlagId);

			messageFlag.setUserId(message.getUserId());
			messageFlag.setMessageId(message.getMessageId());
			messageFlag.setFlag(MBMessageFlagImpl.ANSWER_FLAG);

			mbMessageFlagPersistence.update(messageFlag, false);
		}
	}

	public void deleteAnswerFlag(long messageId)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		if (message.isRoot()) {
			return;
		}

		MBThread thread = mbThreadPersistence.findByPrimaryKey(
			message.getThreadId());

		MBMessage rootMessage = mbMessagePersistence.findByPrimaryKey(
			thread.getRootMessageId());

		MBMessagePermission.check(
			getPermissionChecker(), rootMessage.getMessageId(),
			ActionKeys.UPDATE);

		try {
			mbMessageFlagPersistence.removeByU_M_F(
				message.getUserId(), message.getMessageId(),
				MBMessageFlagImpl.ANSWER_FLAG);
		}
		catch (NoSuchMessageFlagException nsmfe) {
		}

		MBMessageFlag answerMessageFlag =
			mbMessageFlagPersistence.fetchByU_M_F(
				rootMessage.getUserId(), rootMessage.getMessageId(),
				MBMessageFlagImpl.ANSWER_FLAG);

		if (answerMessageFlag == null) {
			return;
		}

		int answerFlagsCount = mbMessageFlagFinder.countByT_F(
			message.getThreadId(), MBMessageFlagImpl.ANSWER_FLAG);

		if (answerFlagsCount == 0) {
			answerMessageFlag.setFlag(MBMessageFlagImpl.QUESTION_FLAG);

			mbMessageFlagPersistence.update(answerMessageFlag, false);
		}
	}

}