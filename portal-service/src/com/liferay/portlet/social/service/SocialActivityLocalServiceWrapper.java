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

package com.liferay.portlet.social.service;


/**
 * <a href="SocialActivityLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SocialActivityLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialActivityLocalService
 * @generated
 */
public class SocialActivityLocalServiceWrapper
	implements SocialActivityLocalService {
	public SocialActivityLocalServiceWrapper(
		SocialActivityLocalService socialActivityLocalService) {
		_socialActivityLocalService = socialActivityLocalService;
	}

	public com.liferay.portlet.social.model.SocialActivity addSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.addSocialActivity(socialActivity);
	}

	public com.liferay.portlet.social.model.SocialActivity createSocialActivity(
		long activityId) {
		return _socialActivityLocalService.createSocialActivity(activityId);
	}

	public void deleteSocialActivity(long activityId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_socialActivityLocalService.deleteSocialActivity(activityId);
	}

	public void deleteSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.SystemException {
		_socialActivityLocalService.deleteSocialActivity(socialActivity);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.social.model.SocialActivity getSocialActivity(
		long activityId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _socialActivityLocalService.getSocialActivity(activityId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getSocialActivities(
		int start, int end) throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getSocialActivities(start, end);
	}

	public int getSocialActivitiesCount()
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getSocialActivitiesCount();
	}

	public com.liferay.portlet.social.model.SocialActivity updateSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.updateSocialActivity(socialActivity);
	}

	public com.liferay.portlet.social.model.SocialActivity updateSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity,
		boolean merge) throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.updateSocialActivity(socialActivity,
			merge);
	}

	public com.liferay.portlet.social.model.SocialActivity addActivity(
		long userId, long groupId, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _socialActivityLocalService.addActivity(userId, groupId,
			className, classPK, type, extraData, receiverUserId);
	}

	public com.liferay.portlet.social.model.SocialActivity addActivity(
		long userId, long groupId, java.util.Date createDate,
		java.lang.String className, long classPK, int type,
		java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _socialActivityLocalService.addActivity(userId, groupId,
			createDate, className, classPK, type, extraData, receiverUserId);
	}

	public com.liferay.portlet.social.model.SocialActivity addUniqueActivity(
		long userId, long groupId, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _socialActivityLocalService.addUniqueActivity(userId, groupId,
			className, classPK, type, extraData, receiverUserId);
	}

	public com.liferay.portlet.social.model.SocialActivity addUniqueActivity(
		long userId, long groupId, java.util.Date createDate,
		java.lang.String className, long classPK, int type,
		java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _socialActivityLocalService.addUniqueActivity(userId, groupId,
			createDate, className, classPK, type, extraData, receiverUserId);
	}

	public void deleteActivities(java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		_socialActivityLocalService.deleteActivities(className, classPK);
	}

	public void deleteActivities(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		_socialActivityLocalService.deleteActivities(classNameId, classPK);
	}

	public void deleteActivity(long activityId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_socialActivityLocalService.deleteActivity(activityId);
	}

	public void deleteUserActivities(long userId)
		throws com.liferay.portal.SystemException {
		_socialActivityLocalService.deleteUserActivities(userId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		java.lang.String className, int start, int end)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getActivities(className, start, end);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long classNameId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getActivities(classNameId, start, end);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long mirrorActivityId, java.lang.String className, long classPK,
		int start, int end) throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getActivities(mirrorActivityId,
			className, classPK, start, end);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long mirrorActivityId, long classNameId, long classPK, int start,
		int end) throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getActivities(mirrorActivityId,
			classNameId, classPK, start, end);
	}

	public int getActivitiesCount(java.lang.String className)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getActivitiesCount(className);
	}

	public int getActivitiesCount(long classNameId)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getActivitiesCount(classNameId);
	}

	public int getActivitiesCount(long mirrorActivityId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getActivitiesCount(mirrorActivityId,
			className, classPK);
	}

	public int getActivitiesCount(long mirrorActivityId, long classNameId,
		long classPK) throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getActivitiesCount(mirrorActivityId,
			classNameId, classPK);
	}

	public com.liferay.portlet.social.model.SocialActivity getActivity(
		long activityId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _socialActivityLocalService.getActivity(activityId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getGroupActivities(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getGroupActivities(groupId, start,
			end);
	}

	public int getGroupActivitiesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getGroupActivitiesCount(groupId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getGroupUsersActivities(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getGroupUsersActivities(groupId,
			start, end);
	}

	public int getGroupUsersActivitiesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getGroupUsersActivitiesCount(groupId);
	}

	public com.liferay.portlet.social.model.SocialActivity getMirrorActivity(
		long mirrorActivityId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _socialActivityLocalService.getMirrorActivity(mirrorActivityId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getOrganizationActivities(
		long organizationId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getOrganizationActivities(organizationId,
			start, end);
	}

	public int getOrganizationActivitiesCount(long organizationId)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getOrganizationActivitiesCount(organizationId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getOrganizationUsersActivities(
		long organizationId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getOrganizationUsersActivities(organizationId,
			start, end);
	}

	public int getOrganizationUsersActivitiesCount(long organizationId)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getOrganizationUsersActivitiesCount(organizationId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getRelationActivities(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getRelationActivities(userId, start,
			end);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getRelationActivities(
		long userId, int type, int start, int end)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getRelationActivities(userId, type,
			start, end);
	}

	public int getRelationActivitiesCount(long userId)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getRelationActivitiesCount(userId);
	}

	public int getRelationActivitiesCount(long userId, int type)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getRelationActivitiesCount(userId,
			type);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserActivities(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getUserActivities(userId, start, end);
	}

	public int getUserActivitiesCount(long userId)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getUserActivitiesCount(userId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserGroupsActivities(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getUserGroupsActivities(userId,
			start, end);
	}

	public int getUserGroupsActivitiesCount(long userId)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getUserGroupsActivitiesCount(userId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserGroupsAndOrganizationsActivities(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getUserGroupsAndOrganizationsActivities(userId,
			start, end);
	}

	public int getUserGroupsAndOrganizationsActivitiesCount(long userId)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getUserGroupsAndOrganizationsActivitiesCount(userId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserOrganizationsActivities(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getUserOrganizationsActivities(userId,
			start, end);
	}

	public int getUserOrganizationsActivitiesCount(long userId)
		throws com.liferay.portal.SystemException {
		return _socialActivityLocalService.getUserOrganizationsActivitiesCount(userId);
	}

	public SocialActivityLocalService getWrappedSocialActivityLocalService() {
		return _socialActivityLocalService;
	}

	private SocialActivityLocalService _socialActivityLocalService;
}