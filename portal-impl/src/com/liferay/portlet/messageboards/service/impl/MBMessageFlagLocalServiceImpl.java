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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageFlag;
import com.liferay.portlet.messageboards.model.impl.MBMessageFlagImpl;
import com.liferay.portlet.messageboards.service.base.MBMessageFlagLocalServiceBaseImpl;

import java.util.List;

/**
 * <a href="MBMessageFlagLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBMessageFlagLocalServiceImpl
	extends MBMessageFlagLocalServiceBaseImpl {

	public void addReadFlags(long userId, List<MBMessage> messages)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (user.isDefaultUser()) {
			return;
		}

		for (MBMessage message : messages) {
			MBMessageFlag messageFlag = mbMessageFlagPersistence.fetchByU_M_F(
				userId, message.getMessageId(), MBMessageFlagImpl.READ_FLAG);

			if (messageFlag == null) {
				long messageFlagId = counterLocalService.increment();

				messageFlag = mbMessageFlagPersistence.create(messageFlagId);

				messageFlag.setUserId(userId);
				messageFlag.setMessageId(message.getMessageId());
				messageFlag.setFlag(MBMessageFlagImpl.READ_FLAG);

				mbMessageFlagPersistence.update(messageFlag, false);
			}
		}
	}

	public void addQuestionFlag(long messageId)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		if (!message.isRoot()) {
			return;
		}

		MBMessageFlag questionMessageFlag =
			mbMessageFlagPersistence.fetchByU_M_F(
				message.getUserId(), message.getMessageId(),
				MBMessageFlagImpl.QUESTION_FLAG);

		MBMessageFlag answerMessageFlag =
			mbMessageFlagPersistence.fetchByU_M_F(
				message.getUserId(), message.getMessageId(),
				MBMessageFlagImpl.ANSWER_FLAG);

		if ((questionMessageFlag == null) && (answerMessageFlag == null)) {
			long messageFlagId = counterLocalService.increment();

			questionMessageFlag = mbMessageFlagPersistence.create(
				messageFlagId);

			questionMessageFlag.setUserId(message.getUserId());
			questionMessageFlag.setMessageId(message.getMessageId());
			questionMessageFlag.setFlag(MBMessageFlagImpl.QUESTION_FLAG);

			mbMessageFlagPersistence.update(questionMessageFlag, false);
		}
	}

	public void deleteFlags(long userId) throws SystemException {
		mbMessageFlagPersistence.removeByUserId(userId);
	}

	public void deleteFlags(long messageId, int flag) throws SystemException {
		mbMessageFlagPersistence.removeByM_F(messageId, flag);
	}

	public void deleteQuestionAndAnswerFlags(long threadId)
		throws SystemException {

		List<MBMessage> messages = mbMessagePersistence.findByThreadId(
			threadId);

		for (MBMessage message : messages) {
			if (message.isRoot()) {
				mbMessageFlagPersistence.removeByM_F(
					message.getMessageId(), MBMessageFlagImpl.QUESTION_FLAG);
			}

			mbMessageFlagPersistence.removeByM_F(
				message.getMessageId(), MBMessageFlagImpl.ANSWER_FLAG);
		}
	}

	public boolean hasAnswerFlag(long messageId) throws SystemException {
		int count = mbMessageFlagPersistence.countByM_F(
			messageId, MBMessageFlagImpl.ANSWER_FLAG);

		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasQuestionFlag(long messageId) throws SystemException {
		int count = mbMessageFlagPersistence.countByM_F(
			messageId, MBMessageFlagImpl.QUESTION_FLAG);

		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasReadFlag(long userId, long messageId)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (user.isDefaultUser()) {
			return true;
		}

		MBMessageFlag messageFlag = mbMessageFlagPersistence.fetchByU_M_F(
			userId, messageId, MBMessageFlagImpl.READ_FLAG);

		if (messageFlag != null) {
			return true;
		}
		else {
			return false;
		}
	}

}