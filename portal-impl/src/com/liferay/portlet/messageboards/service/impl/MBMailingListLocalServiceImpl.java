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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.TriggerExpression;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portlet.messageboards.MailingListEmailAddressException;
import com.liferay.portlet.messageboards.MailingListInServerNameException;
import com.liferay.portlet.messageboards.MailingListInUserNameException;
import com.liferay.portlet.messageboards.MailingListOutEmailAddressException;
import com.liferay.portlet.messageboards.MailingListOutServerNameException;
import com.liferay.portlet.messageboards.MailingListOutUserNameException;
import com.liferay.portlet.messageboards.messaging.MailingListRequest;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.service.base.MBMailingListLocalServiceBaseImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <a href="MBMailingListLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Thiago Moreira
 *
 */
public class MBMailingListLocalServiceImpl
	extends MBMailingListLocalServiceBaseImpl {

	public MBMailingList addMailingList(
			String uuid, long userId, long categoryId, String emailAddress,
			String inProtocol, String inServerName, int inServerPort,
			boolean inUseSSL, String inUserName, String inPassword,
			int inReadInterval, String outEmailAddress, boolean outCustom,
			String outServerName, int outServerPort, boolean outUseSSL,
			String outUserName, String outPassword, boolean active)
		throws PortalException, SystemException {

		// Mailing list

		User user = userPersistence.findByPrimaryKey(userId);
		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);
		Date now = new Date();

		validate(
			emailAddress, inServerName, inUserName, outEmailAddress, outCustom,
			outServerName, outUserName, active);

		long mailingListId = counterLocalService.increment();

		MBMailingList mailingList = mbMailingListPersistence.create(
			mailingListId);

		mailingList.setUuid(uuid);
		mailingList.setGroupId(category.getGroupId());
		mailingList.setCompanyId(user.getCompanyId());
		mailingList.setUserId(user.getUserId());
		mailingList.setUserName(user.getFullName());
		mailingList.setCreateDate(now);
		mailingList.setModifiedDate(now);
		mailingList.setCategoryId(categoryId);
		mailingList.setEmailAddress(emailAddress);
		mailingList.setInProtocol(inProtocol);
		mailingList.setInServerName(inServerName);
		mailingList.setInServerPort(inServerPort);
		mailingList.setInUseSSL(inUseSSL);
		mailingList.setInUserName(inUserName);
		mailingList.setInPassword(inPassword);
		mailingList.setInReadInterval(inReadInterval);
		mailingList.setOutEmailAddress(outEmailAddress);
		mailingList.setOutCustom(outCustom);
		mailingList.setOutServerName(outServerName);
		mailingList.setOutServerPort(outServerPort);
		mailingList.setOutUseSSL(outUseSSL);
		mailingList.setOutUserName(outUserName);
		mailingList.setOutPassword(outPassword);
		mailingList.setActive(active);

		mbMailingListPersistence.update(mailingList, false);

		// Scheduler

		if (active) {
			scheduleMailingList(mailingList);
		}

		return mailingList;
	}

	public void deleteCategoryMailingList(long categoryId)
		throws PortalException, SystemException {

		MBMailingList mailingList = mbMailingListPersistence.findByCategoryId(
			categoryId);

		deleteMailingList(mailingList);
	}

	public void deleteMailingList(long mailingListId)
		throws PortalException, SystemException {

		MBMailingList mailingList = mbMailingListPersistence.findByPrimaryKey(
			mailingListId);

		deleteMailingList(mailingList);
	}

	public void deleteMailingList(MBMailingList mailingList)
		throws PortalException, SystemException {

		unscheduleMailingList(mailingList);

		mbMailingListPersistence.remove(mailingList);
	}

	public MBMailingList getCategoryMailingList(long categoryId)
		throws PortalException, SystemException {

		return mbMailingListPersistence.findByCategoryId(categoryId);
	}

	public MBMailingList updateMailingList(
			long mailingListId, String emailAddress, String inProtocol,
			String inServerName, int inServerPort, boolean inUseSSL,
			String inUserName, String inPassword, int inReadInterval,
			String outEmailAddress, boolean outCustom, String outServerName,
			int outServerPort, boolean outUseSSL, String outUserName,
			String outPassword, boolean active)
		throws PortalException, SystemException {

		// Mailing list

		MBMailingList mailingList = mbMailingListPersistence.findByPrimaryKey(
			mailingListId);

		mailingList.setModifiedDate(new Date());
		mailingList.setEmailAddress(emailAddress);
		mailingList.setInProtocol(inProtocol);
		mailingList.setInServerName(inServerName);
		mailingList.setInServerPort(inServerPort);
		mailingList.setInUseSSL(inUseSSL);
		mailingList.setInUserName(inUserName);
		mailingList.setInPassword(inPassword);
		mailingList.setInReadInterval(inReadInterval);
		mailingList.setOutEmailAddress(outEmailAddress);
		mailingList.setOutCustom(outCustom);
		mailingList.setOutServerName(outServerName);
		mailingList.setOutServerPort(outServerPort);
		mailingList.setOutUseSSL(outUseSSL);
		mailingList.setOutUserName(outUserName);
		mailingList.setOutPassword(outPassword);
		mailingList.setActive(active);

		mbMailingListPersistence.update(mailingList, false);

		// Scheduler

		if (active) {
			scheduleMailingList(mailingList);
		}
		else {
			unscheduleMailingList(mailingList);
		}

		return mailingList;
	}

	protected String getSchedulerGroupName(MBMailingList mailingList) {
		StringBuilder sb = new StringBuilder();

		sb.append(DestinationNames.MESSAGE_BOARDS_MAILING_LIST);
		sb.append(StringPool.SLASH);
		sb.append(mailingList.getMailingListId());

		return sb.toString();
	}

	protected void scheduleMailingList(MBMailingList mailingList)
		throws PortalException {

		unscheduleMailingList(mailingList);

		String groupName = getSchedulerGroupName(mailingList);

		Calendar startDate = CalendarFactoryUtil.getCalendar();

		TriggerExpression triggerExpression = new TriggerExpression(
			startDate, TriggerExpression.MINUTELY_FREQUENCY,
			mailingList.getInReadInterval());

		String cronText = triggerExpression.toCronText();

		MailingListRequest mailingListRequest = new MailingListRequest();

		mailingListRequest.setCompanyId(mailingList.getCompanyId());
		mailingListRequest.setUserId(mailingList.getUserId());
		mailingListRequest.setCategoryId(mailingList.getCategoryId());
		mailingListRequest.setInProtocol(mailingList.getInProtocol());
		mailingListRequest.setInServerName(mailingList.getInServerName());
		mailingListRequest.setInServerPort(mailingList.getInServerPort());
		mailingListRequest.setInUseSSL(mailingList.getInUseSSL());
		mailingListRequest.setInUserName(mailingList.getInUserName());
		mailingListRequest.setInPassword(mailingList.getInPassword());

		SchedulerEngineUtil.schedule(
			groupName, cronText, startDate.getTime(), null, null,
			DestinationNames.MESSAGE_BOARDS_MAILING_LIST,
			JSONFactoryUtil.serialize(mailingListRequest));
	}

	protected void unscheduleMailingList(MBMailingList mailingList)
		throws PortalException {

		String groupName = getSchedulerGroupName(mailingList);

		List<SchedulerRequest> schedulerRequests =
			SchedulerEngineUtil.getScheduledJobs(groupName);

		for (SchedulerRequest schedulerRequest : schedulerRequests) {
			SchedulerEngineUtil.unschedule(
				schedulerRequest.getJobName(), schedulerRequest.getGroupName());
		}
	}

	protected void validate(
			String emailAddress, String inServerName, String inUserName,
			String outEmailAddress, boolean outCustom, String outServerName,
			String outUserName, boolean active)
		throws PortalException {

		if (!active) {
			return;
		}

		if (!Validator.isEmailAddress(emailAddress)) {
			throw new MailingListEmailAddressException();
		}
		else if (Validator.isNull(inServerName)) {
			throw new MailingListInServerNameException();
		}
		else if (Validator.isNull(inUserName)) {
			throw new MailingListInUserNameException();
		}
		else if (Validator.isNull(outEmailAddress)) {
			throw new MailingListOutEmailAddressException();
		}
		else if (outCustom) {
			if (Validator.isNull(outServerName)) {
				throw new MailingListOutServerNameException();
			}
			else if (Validator.isNull(outUserName)) {
				throw new MailingListOutUserNameException();
			}
		}
	}

}