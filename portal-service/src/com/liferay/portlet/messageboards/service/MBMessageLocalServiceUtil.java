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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the message-boards message local service. This utility wraps {@link com.liferay.portlet.messageboards.service.impl.MBMessageLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.messageboards.service.impl.MBMessageLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageLocalService
 * @see com.liferay.portlet.messageboards.service.base.MBMessageLocalServiceBaseImpl
 * @see com.liferay.portlet.messageboards.service.impl.MBMessageLocalServiceImpl
 * @generated
 */
public class MBMessageLocalServiceUtil {
	/**
	* Adds the message-boards message to the database. Also notifies the appropriate model listeners.
	*
	* @param mbMessage the message-boards message to add
	* @return the message-boards message that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessage addMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addMBMessage(mbMessage);
	}

	/**
	* Creates a new message-boards message with the primary key. Does not add the message-boards message to the database.
	*
	* @param messageId the primary key for the new message-boards message
	* @return the new message-boards message
	*/
	public static com.liferay.portlet.messageboards.model.MBMessage createMBMessage(
		long messageId) {
		return getService().createMBMessage(messageId);
	}

	/**
	* Deletes the message-boards message with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param messageId the primary key of the message-boards message to delete
	* @throws PortalException if a message-boards message with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteMBMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMBMessage(messageId);
	}

	/**
	* Deletes the message-boards message from the database. Also notifies the appropriate model listeners.
	*
	* @param mbMessage the message-boards message to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMBMessage(mbMessage);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the message-boards message with the primary key.
	*
	* @param messageId the primary key of the message-boards message to get
	* @return the message-boards message
	* @throws PortalException if a message-boards message with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessage getMBMessage(
		long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBMessage(messageId);
	}

	/**
	* Gets the message-boards message with the UUID and group id.
	*
	* @param uuid the UUID of message-boards message to get
	* @param groupId the group id of the message-boards message to get
	* @return the message-boards message
	* @throws PortalException if a message-boards message with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessage getMBMessageByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBMessageByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Gets a range of all the message-boards messages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message-boards messages to return
	* @param end the upper bound of the range of message-boards messages to return (not inclusive)
	* @return the range of message-boards messages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getMBMessages(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBMessages(start, end);
	}

	/**
	* Gets the number of message-boards messages.
	*
	* @return the number of message-boards messages
	* @throws SystemException if a system exception occurred
	*/
	public static int getMBMessagesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBMessagesCount();
	}

	/**
	* Updates the message-boards message in the database. Also notifies the appropriate model listeners.
	*
	* @param mbMessage the message-boards message to update
	* @return the message-boards message that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessage updateMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateMBMessage(mbMessage);
	}

	/**
	* Updates the message-boards message in the database. Also notifies the appropriate model listeners.
	*
	* @param mbMessage the message-boards message to update
	* @param merge whether to merge the message-boards message with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the message-boards message that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBMessage updateMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateMBMessage(mbMessage, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		long userId, java.lang.String userName, long groupId,
		java.lang.String className, long classPK, int workflowAction)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addDiscussionMessage(userId, userName, groupId, className,
			classPK, workflowAction);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		long userId, java.lang.String userName, long groupId,
		java.lang.String className, long classPK, long threadId,
		long parentMessageId, java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addDiscussionMessage(userId, userName, groupId, className,
			classPK, threadId, parentMessageId, subject, body, serviceContext);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, java.lang.String userName, long groupId, long categoryId,
		long threadId, long parentMessageId, java.lang.String subject,
		java.lang.String body, java.lang.String format,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, byte[]>> files,
		boolean anonymous, double priority, boolean allowPingbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addMessage(userId, userName, groupId, categoryId, threadId,
			parentMessageId, subject, body, format, files, anonymous, priority,
			allowPingbacks, serviceContext);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, java.lang.String userName, long groupId, long categoryId,
		java.lang.String subject, java.lang.String body,
		java.lang.String format,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, byte[]>> files,
		boolean anonymous, double priority, boolean allowPingbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addMessage(userId, userName, groupId, categoryId, subject,
			body, format, files, anonymous, priority, allowPingbacks,
			serviceContext);
	}

	public static void addMessageResources(long messageId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addMessageResources(messageId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addMessageResources(long messageId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addMessageResources(messageId, communityPermissions,
			guestPermissions);
	}

	public static void addMessageResources(
		com.liferay.portlet.messageboards.model.MBMessage message,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addMessageResources(message, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addMessageResources(
		com.liferay.portlet.messageboards.model.MBMessage message,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addMessageResources(message, communityPermissions, guestPermissions);
	}

	public static void deleteDiscussionMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDiscussionMessage(messageId);
	}

	public static void deleteDiscussionMessages(java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDiscussionMessages(className, classPK);
	}

	public static void deleteMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMessage(messageId);
	}

	public static void deleteMessage(
		com.liferay.portlet.messageboards.model.MBMessage message)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMessage(message);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCategoryMessages(groupId, categoryId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCategoryMessages(groupId, categoryId, status, start,
			end, obc);
	}

	public static int getCategoryMessagesCount(long groupId, long categoryId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoryMessagesCount(groupId, categoryId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCompanyMessages(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyMessages(companyId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCompanyMessages(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCompanyMessages(companyId, status, start, end, obc);
	}

	public static int getCompanyMessagesCount(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyMessagesCount(companyId, status);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getDiscussionMessageDisplay(
		long userId, long groupId, java.lang.String className, long classPK,
		int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getDiscussionMessageDisplay(userId, groupId, className,
			classPK, status);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getDiscussionMessageDisplay(
		long userId, long groupId, java.lang.String className, long classPK,
		int status, java.lang.String threadView)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getDiscussionMessageDisplay(userId, groupId, className,
			classPK, status, threadView);
	}

	public static int getDiscussionMessagesCount(long classNameId,
		long classPK, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getDiscussionMessagesCount(classNameId, classPK, status);
	}

	public static int getDiscussionMessagesCount(java.lang.String className,
		long classPK, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getDiscussionMessagesCount(className, classPK, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> getDiscussions(
		java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDiscussions(className);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupMessages(groupId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupMessages(groupId, status, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupMessages(groupId, userId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getGroupMessages(groupId, userId, status, start, end, obc);
	}

	public static int getGroupMessagesCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupMessagesCount(groupId, status);
	}

	public static int getGroupMessagesCount(long groupId, long userId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupMessagesCount(groupId, userId, status);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage getMessage(
		long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMessage(messageId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		long messageId, int status, java.lang.String threadView,
		boolean includePrevAndNext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getMessageDisplay(messageId, status, threadView,
			includePrevAndNext);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		com.liferay.portlet.messageboards.model.MBMessage message, int status,
		java.lang.String threadView, boolean includePrevAndNext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getMessageDisplay(message, status, threadView,
			includePrevAndNext);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getMessages(
		java.lang.String className, long classPK, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMessages(className, classPK, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getNoAssetMessages()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getNoAssetMessages();
	}

	public static int getPositionInThread(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPositionInThread(messageId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadMessages(
		long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getThreadMessages(threadId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadMessages(
		long threadId, int status,
		java.util.Comparator<com.liferay.portlet.messageboards.model.MBMessage> comparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getThreadMessages(threadId, status, comparator);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadMessages(
		long threadId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getThreadMessages(threadId, status, start, end);
	}

	public static int getThreadMessagesCount(long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getThreadMessagesCount(threadId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadRepliesMessages(
		long threadId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getThreadRepliesMessages(threadId, status, start, end);
	}

	public static void subscribeMessage(long userId, long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().subscribeMessage(userId, messageId);
	}

	public static void unsubscribeMessage(long userId, long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsubscribeMessage(userId, messageId);
	}

	public static void updateAsset(long userId,
		com.liferay.portlet.messageboards.model.MBMessage message,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateAsset(userId, message, assetCategoryIds, assetTagNames);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		long userId, long messageId, java.lang.String className, long classPK,
		java.lang.String subject, java.lang.String body, int workflowAction)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateDiscussionMessage(userId, messageId, className,
			classPK, subject, body, workflowAction);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long userId, long messageId, java.lang.String subject,
		java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, byte[]>> files,
		java.util.List<java.lang.String> existingFiles, double priority,
		boolean allowPingbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateMessage(userId, messageId, subject, body, files,
			existingFiles, priority, allowPingbacks, serviceContext);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, java.lang.String body)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateMessage(messageId, body);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateStatus(
		long userId, long messageId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStatus(userId, messageId, status, serviceContext);
	}

	public static void updateUserName(long userId, java.lang.String userName)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().updateUserName(userId, userName);
	}

	public static MBMessageLocalService getService() {
		if (_service == null) {
			_service = (MBMessageLocalService)PortalBeanLocatorUtil.locate(MBMessageLocalService.class.getName());

			ReferenceRegistry.registerReference(MBMessageLocalServiceUtil.class,
				"_service");
			MethodCache.remove(MBMessageLocalService.class);
		}

		return _service;
	}

	public void setService(MBMessageLocalService service) {
		MethodCache.remove(MBMessageLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(MBMessageLocalServiceUtil.class,
			"_service");
		MethodCache.remove(MBMessageLocalService.class);
	}

	private static MBMessageLocalService _service;
}