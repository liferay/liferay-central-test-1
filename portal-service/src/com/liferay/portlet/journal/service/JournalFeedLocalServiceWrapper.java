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

package com.liferay.portlet.journal.service;

/**
 * <p>
 * This class is a wrapper for {@link JournalFeedLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalFeedLocalService
 * @generated
 */
public class JournalFeedLocalServiceWrapper implements JournalFeedLocalService {
	public JournalFeedLocalServiceWrapper(
		JournalFeedLocalService journalFeedLocalService) {
		_journalFeedLocalService = journalFeedLocalService;
	}

	public com.liferay.portlet.journal.model.JournalFeed addJournalFeed(
		com.liferay.portlet.journal.model.JournalFeed journalFeed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.addJournalFeed(journalFeed);
	}

	public com.liferay.portlet.journal.model.JournalFeed createJournalFeed(
		long id) {
		return _journalFeedLocalService.createJournalFeed(id);
	}

	public void deleteJournalFeed(long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalFeedLocalService.deleteJournalFeed(id);
	}

	public void deleteJournalFeed(
		com.liferay.portlet.journal.model.JournalFeed journalFeed)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalFeedLocalService.deleteJournalFeed(journalFeed);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.journal.model.JournalFeed getJournalFeed(long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.getJournalFeed(id);
	}

	public com.liferay.portlet.journal.model.JournalFeed getJournalFeedByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.getJournalFeedByUuidAndGroupId(uuid,
			groupId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> getJournalFeeds(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.getJournalFeeds(start, end);
	}

	public int getJournalFeedsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.getJournalFeedsCount();
	}

	public com.liferay.portlet.journal.model.JournalFeed updateJournalFeed(
		com.liferay.portlet.journal.model.JournalFeed journalFeed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.updateJournalFeed(journalFeed);
	}

	public com.liferay.portlet.journal.model.JournalFeed updateJournalFeed(
		com.liferay.portlet.journal.model.JournalFeed journalFeed, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.updateJournalFeed(journalFeed, merge);
	}

	public com.liferay.portlet.journal.model.JournalFeed addFeed(long userId,
		long groupId, java.lang.String feedId, boolean autoFeedId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.lang.String rendererTemplateId,
		int delta, java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.addFeed(userId, groupId, feedId,
			autoFeedId, name, description, type, structureId, templateId,
			rendererTemplateId, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, serviceContext);
	}

	public void addFeedResources(long feedId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalFeedLocalService.addFeedResources(feedId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFeedResources(
		com.liferay.portlet.journal.model.JournalFeed feed,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalFeedLocalService.addFeedResources(feed,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFeedResources(long feedId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalFeedLocalService.addFeedResources(feedId, communityPermissions,
			guestPermissions);
	}

	public void addFeedResources(
		com.liferay.portlet.journal.model.JournalFeed feed,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalFeedLocalService.addFeedResources(feed, communityPermissions,
			guestPermissions);
	}

	public void deleteFeed(long feedId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalFeedLocalService.deleteFeed(feedId);
	}

	public void deleteFeed(long groupId, java.lang.String feedId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalFeedLocalService.deleteFeed(groupId, feedId);
	}

	public void deleteFeed(com.liferay.portlet.journal.model.JournalFeed feed)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalFeedLocalService.deleteFeed(feed);
	}

	public com.liferay.portlet.journal.model.JournalFeed getFeed(long feedId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.getFeed(feedId);
	}

	public com.liferay.portlet.journal.model.JournalFeed getFeed(long groupId,
		java.lang.String feedId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.getFeed(groupId, feedId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> getFeeds()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.getFeeds();
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> getFeeds(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.getFeeds(groupId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> getFeeds(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.getFeeds(groupId, start, end);
	}

	public int getFeedsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.getFeedsCount(groupId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> search(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.search(companyId, groupId, keywords,
			start, end, obc);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> search(
		long companyId, long groupId, java.lang.String feedId,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.search(companyId, groupId, feedId,
			name, description, andOperator, start, end, obc);
	}

	public int searchCount(long companyId, long groupId,
		java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.searchCount(companyId, groupId, keywords);
	}

	public int searchCount(long companyId, long groupId,
		java.lang.String feedId, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.searchCount(companyId, groupId, feedId,
			name, description, andOperator);
	}

	public com.liferay.portlet.journal.model.JournalFeed updateFeed(
		long groupId, java.lang.String feedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFeedLocalService.updateFeed(groupId, feedId, name,
			description, type, structureId, templateId, rendererTemplateId,
			delta, orderByCol, orderByType, targetLayoutFriendlyUrl,
			targetPortletId, contentField, feedType, feedVersion, serviceContext);
	}

	public JournalFeedLocalService getWrappedJournalFeedLocalService() {
		return _journalFeedLocalService;
	}

	private JournalFeedLocalService _journalFeedLocalService;
}