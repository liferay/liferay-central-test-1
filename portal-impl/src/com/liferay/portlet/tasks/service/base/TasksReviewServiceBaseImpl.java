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

package com.liferay.portlet.tasks.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterLocalServiceFactory;
import com.liferay.counter.service.CounterService;
import com.liferay.counter.service.CounterServiceFactory;

import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceFactory;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.UserServiceFactory;
import com.liferay.portal.service.base.PrincipalBean;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserFinderUtil;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.UserUtil;

import com.liferay.portlet.social.service.SocialActivityLocalService;
import com.liferay.portlet.social.service.SocialActivityLocalServiceFactory;
import com.liferay.portlet.social.service.persistence.SocialActivityFinder;
import com.liferay.portlet.social.service.persistence.SocialActivityFinderUtil;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityUtil;
import com.liferay.portlet.tasks.service.TasksProposalLocalService;
import com.liferay.portlet.tasks.service.TasksProposalLocalServiceFactory;
import com.liferay.portlet.tasks.service.TasksProposalService;
import com.liferay.portlet.tasks.service.TasksProposalServiceFactory;
import com.liferay.portlet.tasks.service.TasksReviewLocalService;
import com.liferay.portlet.tasks.service.TasksReviewLocalServiceFactory;
import com.liferay.portlet.tasks.service.TasksReviewService;
import com.liferay.portlet.tasks.service.persistence.TasksProposalFinder;
import com.liferay.portlet.tasks.service.persistence.TasksProposalFinderUtil;
import com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence;
import com.liferay.portlet.tasks.service.persistence.TasksProposalUtil;
import com.liferay.portlet.tasks.service.persistence.TasksReviewPersistence;
import com.liferay.portlet.tasks.service.persistence.TasksReviewUtil;

import org.springframework.beans.factory.InitializingBean;

/**
 * <a href="TasksReviewServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class TasksReviewServiceBaseImpl extends PrincipalBean
	implements TasksReviewService, InitializingBean {
	public TasksReviewLocalService getTasksReviewLocalService() {
		return tasksReviewLocalService;
	}

	public void setTasksReviewLocalService(
		TasksReviewLocalService tasksReviewLocalService) {
		this.tasksReviewLocalService = tasksReviewLocalService;
	}

	public TasksReviewPersistence getTasksReviewPersistence() {
		return tasksReviewPersistence;
	}

	public void setTasksReviewPersistence(
		TasksReviewPersistence tasksReviewPersistence) {
		this.tasksReviewPersistence = tasksReviewPersistence;
	}

	public TasksProposalLocalService getTasksProposalLocalService() {
		return tasksProposalLocalService;
	}

	public void setTasksProposalLocalService(
		TasksProposalLocalService tasksProposalLocalService) {
		this.tasksProposalLocalService = tasksProposalLocalService;
	}

	public TasksProposalService getTasksProposalService() {
		return tasksProposalService;
	}

	public void setTasksProposalService(
		TasksProposalService tasksProposalService) {
		this.tasksProposalService = tasksProposalService;
	}

	public TasksProposalPersistence getTasksProposalPersistence() {
		return tasksProposalPersistence;
	}

	public void setTasksProposalPersistence(
		TasksProposalPersistence tasksProposalPersistence) {
		this.tasksProposalPersistence = tasksProposalPersistence;
	}

	public TasksProposalFinder getTasksProposalFinder() {
		return tasksProposalFinder;
	}

	public void setTasksProposalFinder(TasksProposalFinder tasksProposalFinder) {
		this.tasksProposalFinder = tasksProposalFinder;
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public CounterService getCounterService() {
		return counterService;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public UserFinder getUserFinder() {
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public SocialActivityLocalService getSocialActivityLocalService() {
		return socialActivityLocalService;
	}

	public void setSocialActivityLocalService(
		SocialActivityLocalService socialActivityLocalService) {
		this.socialActivityLocalService = socialActivityLocalService;
	}

	public SocialActivityPersistence getSocialActivityPersistence() {
		return socialActivityPersistence;
	}

	public void setSocialActivityPersistence(
		SocialActivityPersistence socialActivityPersistence) {
		this.socialActivityPersistence = socialActivityPersistence;
	}

	public SocialActivityFinder getSocialActivityFinder() {
		return socialActivityFinder;
	}

	public void setSocialActivityFinder(
		SocialActivityFinder socialActivityFinder) {
		this.socialActivityFinder = socialActivityFinder;
	}

	public void afterPropertiesSet() {
		if (tasksReviewLocalService == null) {
			tasksReviewLocalService = TasksReviewLocalServiceFactory.getImpl();
		}

		if (tasksReviewPersistence == null) {
			tasksReviewPersistence = TasksReviewUtil.getPersistence();
		}

		if (tasksProposalLocalService == null) {
			tasksProposalLocalService = TasksProposalLocalServiceFactory.getImpl();
		}

		if (tasksProposalService == null) {
			tasksProposalService = TasksProposalServiceFactory.getImpl();
		}

		if (tasksProposalPersistence == null) {
			tasksProposalPersistence = TasksProposalUtil.getPersistence();
		}

		if (tasksProposalFinder == null) {
			tasksProposalFinder = TasksProposalFinderUtil.getFinder();
		}

		if (counterLocalService == null) {
			counterLocalService = CounterLocalServiceFactory.getImpl();
		}

		if (counterService == null) {
			counterService = CounterServiceFactory.getImpl();
		}

		if (userLocalService == null) {
			userLocalService = UserLocalServiceFactory.getImpl();
		}

		if (userService == null) {
			userService = UserServiceFactory.getImpl();
		}

		if (userPersistence == null) {
			userPersistence = UserUtil.getPersistence();
		}

		if (userFinder == null) {
			userFinder = UserFinderUtil.getFinder();
		}

		if (socialActivityLocalService == null) {
			socialActivityLocalService = SocialActivityLocalServiceFactory.getImpl();
		}

		if (socialActivityPersistence == null) {
			socialActivityPersistence = SocialActivityUtil.getPersistence();
		}

		if (socialActivityFinder == null) {
			socialActivityFinder = SocialActivityFinderUtil.getFinder();
		}
	}

	protected TasksReviewLocalService tasksReviewLocalService;
	protected TasksReviewPersistence tasksReviewPersistence;
	protected TasksProposalLocalService tasksProposalLocalService;
	protected TasksProposalService tasksProposalService;
	protected TasksProposalPersistence tasksProposalPersistence;
	protected TasksProposalFinder tasksProposalFinder;
	protected CounterLocalService counterLocalService;
	protected CounterService counterService;
	protected UserLocalService userLocalService;
	protected UserService userService;
	protected UserPersistence userPersistence;
	protected UserFinder userFinder;
	protected SocialActivityLocalService socialActivityLocalService;
	protected SocialActivityPersistence socialActivityPersistence;
	protected SocialActivityFinder socialActivityFinder;
}