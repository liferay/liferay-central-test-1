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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.servlet.ImageServletTokenUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextUtil;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.journal.ArticleContentException;
import com.liferay.portlet.journal.ArticleDisplayDateException;
import com.liferay.portlet.journal.ArticleExpirationDateException;
import com.liferay.portlet.journal.ArticleIdException;
import com.liferay.portlet.journal.ArticleReviewDateException;
import com.liferay.portlet.journal.ArticleSmallImageNameException;
import com.liferay.portlet.journal.ArticleSmallImageSizeException;
import com.liferay.portlet.journal.ArticleTitleException;
import com.liferay.portlet.journal.ArticleTypeException;
import com.liferay.portlet.journal.ArticleVersionException;
import com.liferay.portlet.journal.DuplicateArticleIdException;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.NoSuchArticleResourceException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.StructureXsdException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalArticleDisplayImpl;
import com.liferay.portlet.journal.service.base.JournalArticleLocalServiceBaseImpl;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.journal.util.comparator.ArticleIDComparator;
import com.liferay.portlet.journal.util.comparator.ArticleVersionComparator;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;

import java.io.File;
import java.io.IOException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import javax.portlet.PortletPreferences;

/**
 * <a href="JournalArticleLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Bruno Farache
 */
public class JournalArticleLocalServiceImpl
	extends JournalArticleLocalServiceBaseImpl {

	public JournalArticle addArticle(
			long userId, long groupId, String articleId, boolean autoArticleId,
			double version, String title, String description, String content,
			String type, String structureId, String templateId,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallFile, Map<String, byte[]> images,
			String articleURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addArticle(
			null, userId, groupId, articleId, autoArticleId, version, title,
			description, content, type, structureId, templateId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallFile, images, articleURL,
			serviceContext);
	}

	public JournalArticle addArticle(
			long userId, long groupId, String articleId, boolean autoArticleId,
			String title, String description, String content, String type,
			String structureId, String templateId, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallFile, Map<String, byte[]> images,
			String articleURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		double version = JournalArticleConstants.DEFAULT_VERSION;

		return addArticle(
			userId, groupId, articleId, autoArticleId, version, title,
			description, content, type, structureId, templateId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallFile, images, articleURL,
			serviceContext);
	}

	public JournalArticle addArticle(
			String uuid, long userId, long groupId, String articleId,
			boolean autoArticleId, double version, String title,
			String description, String content, String type, String structureId,
			String templateId, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
			int reviewDateDay, int reviewDateYear, int reviewDateHour,
			int reviewDateMinute, boolean neverReview, boolean indexable,
			boolean smallImage, String smallImageURL, File smallFile,
			Map<String, byte[]> images, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Article

		User user = userPersistence.findByPrimaryKey(userId);
		articleId = articleId.trim().toUpperCase();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, user.getTimeZone(),
			new ArticleDisplayDateException());

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				new ArticleExpirationDateException());
		}

		Date reviewDate = null;

		if (!neverReview) {
			reviewDate = PortalUtil.getDate(
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, user.getTimeZone(),
				new ArticleReviewDateException());
		}

		byte[] smallBytes = null;

		try {
			smallBytes = FileUtil.getBytes(smallFile);
		}
		catch (IOException ioe) {
		}

		Date now = new Date();

		validate(
			groupId, articleId, autoArticleId, version, title, content, type,
			structureId, templateId, smallImage, smallImageURL, smallFile,
			smallBytes);

		if (autoArticleId) {
			articleId = String.valueOf(counterLocalService.increment());
		}

		long id = counterLocalService.increment();

		long resourcePrimKey =
			journalArticleResourceLocalService.getArticleResourcePrimKey(
				groupId, articleId);

		JournalArticle article = journalArticlePersistence.create(id);

		content = format(
			groupId, articleId, version, false, content, structureId, images);

		article.setUuid(uuid);
		article.setResourcePrimKey(resourcePrimKey);
		article.setGroupId(groupId);
		article.setCompanyId(user.getCompanyId());
		article.setUserId(user.getUserId());
		article.setUserName(user.getFullName());
		article.setCreateDate(serviceContext.getCreateDate(now));
		article.setModifiedDate(serviceContext.getModifiedDate(now));
		article.setArticleId(articleId);
		article.setVersion(version);
		article.setTitle(title);
		article.setUrlTitle(getUniqueUrlTitle(id, groupId, articleId, title));
		article.setDescription(description);
		article.setContent(content);
		article.setType(type);
		article.setStructureId(structureId);
		article.setTemplateId(templateId);
		article.setDisplayDate(displayDate);

		if ((expirationDate == null) || expirationDate.after(now)) {
			article.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			article.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		article.setExpirationDate(expirationDate);
		article.setReviewDate(reviewDate);
		article.setIndexable(indexable);
		article.setSmallImage(smallImage);
		article.setSmallImageId(counterLocalService.increment());
		article.setSmallImageURL(smallImageURL);

		journalArticlePersistence.update(article, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addArticleResources(
				article, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addArticleResources(
				article, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Expando

		ExpandoBridge expandoBridge = article.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Small image

		saveImages(
			smallImage, article.getSmallImageId(), smallFile, smallBytes);

		// Asset

		updateAsset(
			userId, article, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Message boards

		if (PropsValues.JOURNAL_ARTICLE_COMMENTS_ENABLED) {
			mbMessageLocalService.addDiscussionMessage(
				userId, article.getUserName(), groupId,
				JournalArticle.class.getName(), resourcePrimKey,
				WorkflowConstants.ACTION_PUBLISH);
		}

		// Email

		PortletPreferences preferences =
			ServiceContextUtil.getPortletPreferences(serviceContext);

		try {
			sendEmail(article, articleURL, preferences, "requested");
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), groupId, userId,
			JournalArticle.class.getName(), article.getId(), article,
			serviceContext);

		return article;
	}

	public void addArticleResources(
			JournalArticle article, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			article.getCompanyId(), article.getGroupId(),
			article.getUserId(), JournalArticle.class.getName(),
			article.getResourcePrimKey(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addArticleResources(
			JournalArticle article, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			article.getCompanyId(), article.getGroupId(),
			article.getUserId(), JournalArticle.class.getName(),
			article.getResourcePrimKey(), communityPermissions,
			guestPermissions);
	}

	public void addArticleResources(
			long groupId, String articleId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId);

		addArticleResources(
			article, addCommunityPermissions, addGuestPermissions);
	}

	public void addArticleResources(
			long groupId, String articleId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId);

		addArticleResources(article, communityPermissions, guestPermissions);
	}

	public JournalArticle checkArticleResourcePrimKey(
			long groupId, String articleId, double version)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		if (article.getResourcePrimKey() > 0) {
			return article;
		}

		long resourcePrimKey =
			journalArticleResourceLocalService.getArticleResourcePrimKey(
				groupId, articleId);

		article.setResourcePrimKey(resourcePrimKey);

		journalArticlePersistence.update(article, false);

		return article;
	}

	public void checkArticles() throws PortalException, SystemException {
		Date now = new Date();

		List<JournalArticle> articles =
			journalArticleFinder.findByExpirationDate(
				WorkflowConstants.STATUS_APPROVED, now,
				new Date(now.getTime() - _journalArticleCheckInterval));

		if (_log.isDebugEnabled()) {
			_log.debug("Expiring " + articles.size() + " articles");
		}

		Set<Long> companyIds = new HashSet<Long>();

		for (JournalArticle article : articles) {
			article.setStatus(WorkflowConstants.STATUS_EXPIRED);

			journalArticlePersistence.update(article, false);

			if (article.isIndexable()) {
				Indexer indexer = IndexerRegistryUtil.getIndexer(
					JournalArticle.class);

				indexer.delete(article);
			}

			JournalContentUtil.clearCache(
				article.getGroupId(), article.getArticleId(),
				article.getTemplateId());

			companyIds.add(article.getCompanyId());
		}

		for (long companyId : companyIds) {
			CacheUtil.clearCache(companyId);
		}

		articles = journalArticleFinder.findByReviewDate(
			now, new Date(now.getTime() - _journalArticleCheckInterval));

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Sending review notifications for " + articles.size() +
					" articles");
		}

		for (JournalArticle article : articles) {
			String articleURL = StringPool.BLANK;

			long ownerId = article.getGroupId();
			int ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
			long plid = PortletKeys.PREFS_PLID_SHARED;
			String portletId = PortletKeys.JOURNAL;

			PortletPreferences preferences =
				portletPreferencesLocalService.getPreferences(
					article.getCompanyId(), ownerId, ownerType, plid,
					portletId);

			try {
				sendEmail(article, articleURL, preferences, "review");
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}
	}

	public void checkNewLine(long groupId, String articleId, double version)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		String content = GetterUtil.getString(article.getContent());

		if (content.indexOf("\\n") != -1) {
			content = StringUtil.replace(
				content,
				new String[] {"\\n", "\\r"},
				new String[] {"\n", "\r"});

			article.setContent(content);

			journalArticlePersistence.update(article, false);
		}
	}

	public void checkStructure(long groupId, String articleId, double version)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		if (Validator.isNull(article.getStructureId())) {
			return;
		}

		try {
			checkStructure(article);
		}
		catch (DocumentException de) {
			_log.error(de, de);
		}
	}

	public JournalArticle copyArticle(
			long userId, long groupId, String oldArticleId, String newArticleId,
			boolean autoArticleId, double version)
		throws PortalException, SystemException {

		// Article

		User user = userPersistence.findByPrimaryKey(userId);
		oldArticleId = oldArticleId.trim().toUpperCase();
		newArticleId = newArticleId.trim().toUpperCase();
		Date now = new Date();

		JournalArticle oldArticle = journalArticlePersistence.findByG_A_V(
			groupId, oldArticleId, version);

		if (autoArticleId) {
			newArticleId = String.valueOf(counterLocalService.increment());
		}
		else {
			validate(newArticleId);

			JournalArticle newArticle = journalArticlePersistence.fetchByG_A_V(
				groupId, newArticleId, version);

			if (newArticle != null) {
				throw new DuplicateArticleIdException();
			}
		}

		long id = counterLocalService.increment();

		long resourcePrimKey =
			journalArticleResourceLocalService.getArticleResourcePrimKey(
				groupId, newArticleId);

		JournalArticle newArticle = journalArticlePersistence.create(id);

		newArticle.setResourcePrimKey(resourcePrimKey);
		newArticle.setGroupId(groupId);
		newArticle.setCompanyId(user.getCompanyId());
		newArticle.setUserId(user.getUserId());
		newArticle.setUserName(user.getFullName());
		newArticle.setCreateDate(now);
		newArticle.setModifiedDate(now);
		newArticle.setArticleId(newArticleId);
		newArticle.setVersion(JournalArticleConstants.DEFAULT_VERSION);
		newArticle.setTitle(oldArticle.getTitle());
		newArticle.setDescription(oldArticle.getDescription());

		try {
			copyArticleImages(oldArticle, newArticle);
		}
		catch (Exception e) {
			newArticle.setContent(oldArticle.getContent());
		}

		newArticle.setType(oldArticle.getType());
		newArticle.setStructureId(oldArticle.getStructureId());
		newArticle.setTemplateId(oldArticle.getTemplateId());
		newArticle.setDisplayDate(oldArticle.getDisplayDate());
		newArticle.setStatus(oldArticle.getStatus());
		newArticle.setExpirationDate(oldArticle.getExpirationDate());
		newArticle.setReviewDate(oldArticle.getReviewDate());
		newArticle.setIndexable(oldArticle.isIndexable());
		newArticle.setSmallImage(oldArticle.isSmallImage());
		newArticle.setSmallImageId(counterLocalService.increment());
		newArticle.setSmallImageURL(oldArticle.getSmallImageURL());

		journalArticlePersistence.update(newArticle, false);

		// Resources

		addArticleResources(newArticle, true, true);

		// Small image

		if (oldArticle.getSmallImage()) {
			Image image = imageLocalService.getImage(
				oldArticle.getSmallImageId());

			byte[] smallBytes = image.getTextObj();

			imageLocalService.updateImage(
				newArticle.getSmallImageId(), smallBytes);
		}

		// Asset

		String[] assetTagNames = assetTagLocalService.getTagNames(
			JournalArticle.class.getName(), oldArticle.getResourcePrimKey());

		updateAsset(userId, newArticle, null, assetTagNames);

		return newArticle;
	}

	public void deleteArticle(
			JournalArticle article, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (article.isApproved() &&
			isLatestVersion(
				article.getGroupId(), article.getArticleId(),
				article.getVersion(), WorkflowConstants.STATUS_APPROVED)) {

			updatePreviousApprovedArticle(article);
		}

		// Email

		PortletPreferences preferences =
			ServiceContextUtil.getPortletPreferences(serviceContext);

		if ((preferences != null) && !article.isApproved() &&
			isLatestVersion(
				article.getGroupId(), article.getArticleId(),
				article.getVersion())) {

			try {
				sendEmail(article, articleURL, preferences, "denied");
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}

		// Images

		journalArticleImageLocalService.deleteImages(
			article.getGroupId(), article.getArticleId(), article.getVersion());

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
			article.getCompanyId(), article.getGroupId(),
			JournalArticle.class.getName(), article.getId());

		int articlesCount = journalArticlePersistence.countByG_A(
			article.getGroupId(), article.getArticleId());

		if (articlesCount == 1) {

			// Ratings

			ratingsStatsLocalService.deleteStats(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			// Message boards

			mbMessageLocalService.deleteDiscussionMessages(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			// Asset

			assetEntryLocalService.deleteEntry(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			// Content searches

			journalContentSearchLocalService.deleteArticleContentSearches(
				article.getGroupId(), article.getArticleId());

			// Small image

			imageLocalService.deleteImage(article.getSmallImageId());

			// Expando

			expandoValueLocalService.deleteValues(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			// Resources

			resourceLocalService.deleteResource(
				article.getCompanyId(), JournalArticle.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				article.getResourcePrimKey());

			// Resource

			try {
				journalArticleResourceLocalService.deleteArticleResource(
					article.getGroupId(), article.getArticleId());
			}
			catch (NoSuchArticleResourceException nsare) {
			}
		}

		// Article

		journalArticlePersistence.remove(article);
	}

	public void deleteArticle(
			long groupId, String articleId, double version, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		deleteArticle(article, articleURL, serviceContext);
	}

	public void deleteArticle(
			long groupId, String articleId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_A(
			groupId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new ArticleVersionComparator(true));

		for (JournalArticle article : articles) {
			deleteArticle(article, null, serviceContext);
		}
	}

	public void deleteArticles(long groupId)
		throws PortalException, SystemException {

		for (JournalArticle article :
				journalArticlePersistence.findByGroupId(groupId)) {

			deleteArticle(article, null, null);
		}
	}

	public JournalArticle expireArticle(
			long userId, long groupId, String articleId, double version,
			String articleURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return updateStatus(
			userId, groupId, articleId, version,
			WorkflowConstants.STATUS_EXPIRED, articleURL, serviceContext);
	}

	public JournalArticle getArticle(long id)
		throws PortalException, SystemException {

		return journalArticlePersistence.findByPrimaryKey(id);
	}

	public JournalArticle getArticle(long groupId, String articleId)
		throws PortalException, SystemException {

		// Get the latest article that is approved, if none are approved, get
		// the latest unapproved article

		try {
			return getLatestArticle(
				groupId, articleId, WorkflowConstants.STATUS_APPROVED);
		}
		catch (NoSuchArticleException nsae) {
			return getLatestArticle(
				groupId, articleId, WorkflowConstants.STATUS_ANY);
		}
	}

	public JournalArticle getArticle(
			long groupId, String articleId, double version)
		throws PortalException, SystemException {

		return journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);
	}

	public JournalArticle getArticleByUrlTitle(long groupId, String urlTitle)
		throws PortalException, SystemException {

		// Get the latest article that is approved, if none are approved, get
		// the latest unapproved article

		try {
			return getLatestArticleByUrlTitle(
				groupId, urlTitle, WorkflowConstants.STATUS_APPROVED);
		}
		catch (NoSuchArticleException nsae) {
			return getLatestArticleByUrlTitle(
				groupId, urlTitle, WorkflowConstants.STATUS_PENDING);
		}
	}

	public String getArticleContent(
			JournalArticle article, String templateId, String viewMode,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticleDisplay articleDisplay = getArticleDisplay(
			article, templateId, viewMode, languageId, 1, null, themeDisplay);

		if (articleDisplay == null) {
			return StringPool.BLANK;
		}
		else {
			return articleDisplay.getContent();
		}
	}

	public String getArticleContent(
			long groupId, String articleId, double version, String viewMode,
			String templateId, String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticleDisplay articleDisplay = getArticleDisplay(
			groupId, articleId, version, templateId, viewMode, languageId,
			themeDisplay);

		if (articleDisplay == null) {
			return StringPool.BLANK;
		}
		else {
			return articleDisplay.getContent();
		}
	}

	public String getArticleContent(
			long groupId, String articleId, double version, String viewMode,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleContent(
			groupId, articleId, version, viewMode, null, languageId,
			themeDisplay);
	}

	public String getArticleContent(
			long groupId, String articleId, String viewMode, String templateId,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticleDisplay articleDisplay = getArticleDisplay(
			groupId, articleId, templateId, viewMode, languageId, themeDisplay);

		return articleDisplay.getContent();
	}

	public String getArticleContent(
			long groupId, String articleId, String viewMode, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleContent(
			groupId, articleId, viewMode, null, languageId, themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			JournalArticle article, String templateId, String viewMode,
			String languageId, int page, String xmlRequest,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		String content = null;

		if (page < 1) {
			page = 1;
		}

		int numberOfPages = 1;
		boolean paginate = false;
		boolean pageFlow = false;

		boolean cacheable = true;

		if (Validator.isNull(xmlRequest)) {
			xmlRequest = "<request />";
		}

		Map<String, String> tokens = JournalUtil.getTokens(
			article.getGroupId(), themeDisplay, xmlRequest);

		tokens.put(
			"article_resource_pk",
			String.valueOf(article.getResourcePrimKey()));

		String defaultTemplateId = article.getTemplateId();

		if (article.isTemplateDriven()) {
			if (Validator.isNull(templateId)) {
				templateId = defaultTemplateId;
			}

			tokens.put("structure_id", article.getStructureId());
			tokens.put("template_id", templateId);
		}

		String xml = article.getContent();

		try {
			Document doc = null;

			Element root = null;

			if (article.isTemplateDriven()) {
				doc = SAXReaderUtil.read(xml);

				root = doc.getRootElement();

				Document request = SAXReaderUtil.read(xmlRequest);

				List<Element> pages = root.elements("page");

				if (pages.size() > 0) {
					pageFlow = true;

					String targetPage = request.valueOf(
						"/request/parameters/parameter[name='targetPage']/" +
							"value");

					Element pageEl = null;

					if (Validator.isNotNull(targetPage)) {
						XPath xpathSelector = SAXReaderUtil.createXPath(
							"/root/page[@id = '" + targetPage + "']");

						pageEl = (Element)xpathSelector.selectSingleNode(doc);
					}

					if (pageEl != null) {
						doc = SAXReaderUtil.createDocument(pageEl);

						root = doc.getRootElement();

						numberOfPages = pages.size();
					}
					else {
						if (page > pages.size()) {
							page = 1;
						}

						pageEl = pages.get(page - 1);

						doc = SAXReaderUtil.createDocument(pageEl);

						root = doc.getRootElement();

						numberOfPages = pages.size();
						paginate = true;
					}
				}

				root.add(request.getRootElement().createCopy());

				JournalUtil.addAllReservedEls(root, tokens, article);

				xml = JournalUtil.formatXML(doc);
			}
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		try {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Transforming " + article.getArticleId() + " " +
						article.getVersion() + " " + languageId);
			}

			String script = null;
			String langType = null;

			if (article.isTemplateDriven()) {

				// Try with specified template first. If a template is not
				// specified, use the default one. If the specified template
				// does not exit, use the default one. If the default one does
				// not exist, throw an exception.

				JournalTemplate template = null;

				try {
					template = journalTemplatePersistence.findByG_T(
						article.getGroupId(), templateId);
				}
				catch (NoSuchTemplateException nste) {
					if (!defaultTemplateId.equals(templateId)) {
						template = journalTemplatePersistence.findByG_T(
							article.getGroupId(), defaultTemplateId);
					}
					else {
						throw nste;
					}
				}

				script = template.getXsl();
				langType = template.getLangType();
				cacheable = template.isCacheable();
			}

			content = JournalUtil.transform(
				themeDisplay, tokens, viewMode, languageId, xml, script,
				langType);

			if (!pageFlow) {
				String[] pieces = StringUtil.split(content, _TOKEN_PAGE_BREAK);

				if (pieces.length > 1) {
					if (page > pieces.length) {
						page = 1;
					}

					content = pieces[page - 1];
					numberOfPages = pieces.length;
					paginate = true;
				}
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		return new JournalArticleDisplayImpl(
			article.getId(), article.getResourcePrimKey(), article.getGroupId(),
			article.getUserId(), article.getArticleId(), article.getVersion(),
			article.getTitle(), article.getUrlTitle(), article.getDescription(),
			article.getAvailableLocales(), content, article.getType(),
			article.getStructureId(), templateId, article.isSmallImage(),
			article.getSmallImageId(), article.getSmallImageURL(),
			numberOfPages, page, paginate, cacheable);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, double version, String templateId,
			String viewMode, String languageId, int page, String xmlRequest,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		Date now = new Date();

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		if (article.isExpired()) {
			Date expirationDate = article.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(now)) {
				return null;
			}
		}

		if (article.getDisplayDate().after(now)) {
			return null;
		}

		return getArticleDisplay(
			article, templateId, viewMode, languageId, page, xmlRequest,
			themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, double version, String templateId,
			String viewMode, String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleDisplay(
			groupId, articleId, version, templateId, viewMode, languageId, 1,
			null, themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String viewMode, String languageId,
			int page, String xmlRequest, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleDisplay(
			groupId, articleId, null, viewMode, languageId, page, xmlRequest,
			themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String templateId, String viewMode,
			String languageId, int page, String xmlRequest,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticle article = getDisplayArticle(groupId, articleId);

		return getArticleDisplay(
			groupId, articleId, article.getVersion(), templateId, viewMode,
			languageId, page, xmlRequest, themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String templateId, String viewMode,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticle article = getDisplayArticle(groupId, articleId);

		return getArticleDisplay(
			groupId, articleId, article.getVersion(), templateId, viewMode,
			languageId, themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String viewMode, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleDisplay(
			groupId, articleId, null, viewMode, languageId, themeDisplay);
	}

	public List<JournalArticle> getArticles() throws SystemException {
		return journalArticlePersistence.findAll();
	}

	public List<JournalArticle> getArticles(long groupId)
		throws SystemException {

		return journalArticlePersistence.findByGroupId(groupId);
	}

	public List<JournalArticle> getArticles(long groupId, int start, int end)
		throws SystemException {

		return journalArticlePersistence.findByGroupId(groupId, start, end);
	}

	public List<JournalArticle> getArticles(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return journalArticlePersistence.findByGroupId(
			groupId, start, end, obc);
	}

	public List<JournalArticle> getArticles(long groupId, String articleId)
		throws SystemException {

		return journalArticlePersistence.findByG_A(groupId, articleId);
	}

	public List<JournalArticle> getArticlesBySmallImageId(long smallImageId)
		throws SystemException {

		return journalArticlePersistence.findBySmallImageId(smallImageId);
	}

	public int getArticlesCount(long groupId) throws SystemException {
		return journalArticlePersistence.countByGroupId(groupId);
	}

	public List<JournalArticle> getCompanyArticles(
			long companyId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return journalArticlePersistence.findByCompanyId(
				companyId, start, end, new ArticleIDComparator(true));
		}
		else {
			return journalArticlePersistence.findByC_S(
				companyId, status, start, end, new ArticleIDComparator(true));
		}
	}

	public int getCompanyArticlesCount(long companyId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return journalArticlePersistence.countByCompanyId(companyId);
		}
		else {
			return journalArticlePersistence.countByC_S(companyId, status);
		}
	}

	public JournalArticle getDisplayArticle(long groupId, String articleId)
		throws PortalException, SystemException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_A(
			groupId, articleId);

		if (articles.size() == 0) {
			throw new NoSuchArticleException();
		}

		Date now = new Date();

		for (int i = 0; i < articles.size(); i++) {
			JournalArticle article = articles.get(i);

			Date expirationDate = article.getExpirationDate();

			if (article.getDisplayDate().before(now) &&
				((expirationDate == null) || expirationDate.after(now))) {

				return article;
			}
		}

		return articles.get(0);
	}

	public JournalArticle getLatestArticle(long resourcePrimKey)
		throws PortalException, SystemException {

		return getLatestArticle(resourcePrimKey, WorkflowConstants.STATUS_ANY);
	}

	public JournalArticle getLatestArticle(long resourcePrimKey, int status)
		throws PortalException, SystemException {

		List<JournalArticle> articles = null;

		OrderByComparator orderByComparator = new ArticleVersionComparator();

		if (status == WorkflowConstants.STATUS_ANY) {
			articles = journalArticlePersistence.findByR_S(
				resourcePrimKey, WorkflowConstants.STATUS_APPROVED, 0, 1,
				orderByComparator);

			if (articles.size() == 0) {
				articles = journalArticlePersistence.findByR_S(
					resourcePrimKey, WorkflowConstants.STATUS_PENDING, 0, 1,
					orderByComparator);
			}
		}
		else {
			articles = journalArticlePersistence.findByR_S(
				resourcePrimKey, status, 0, 1, orderByComparator);
		}

		if (articles.size() == 0) {
			throw new NoSuchArticleException();
		}

		return articles.get(0);
	}

	public JournalArticle getLatestArticle(long groupId, String articleId)
		throws PortalException, SystemException {

		return getLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_ANY);
	}

	public JournalArticle getLatestArticle(
			long groupId, String articleId, int status)
		throws PortalException, SystemException {

		List<JournalArticle> articles = null;

		OrderByComparator orderByComparator = new ArticleVersionComparator();

		if (status == WorkflowConstants.STATUS_ANY) {
			articles = journalArticlePersistence.findByG_A(
				groupId, articleId, 0, 1, orderByComparator);
		}
		else {
			articles = journalArticlePersistence.findByG_A_S(
				groupId, articleId, status, 0, 1, orderByComparator);
		}

		if (articles.size() == 0) {
			throw new NoSuchArticleException();
		}

		return articles.get(0);
	}

	public JournalArticle getLatestArticleByUrlTitle(
			long groupId, String urlTitle, int status)
		throws PortalException, SystemException {

		List<JournalArticle> articles = null;

		OrderByComparator orderByComparator = new ArticleVersionComparator();

		if (status == WorkflowConstants.STATUS_ANY) {
			articles = journalArticlePersistence.findByG_UT(
				groupId, urlTitle, 0, 1, orderByComparator);
		}
		else {
			articles = journalArticlePersistence.findByG_UT_S(
				groupId, urlTitle, status, 0, 1, orderByComparator);
		}

		if (articles.size() == 0) {
			throw new NoSuchArticleException();
		}

		return articles.get(0);
	}

	public double getLatestVersion(long groupId, String articleId)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId);

		return article.getVersion();
	}

	public double getLatestVersion(
			long groupId, String articleId, int status)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId, status);

		return article.getVersion();
	}

	public List<JournalArticle> getStructureArticles(
			long groupId, String structureId)
		throws SystemException {

		return journalArticlePersistence.findByG_S(groupId, structureId);
	}

	public List<JournalArticle> getStructureArticles(
			long groupId, String structureId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalArticlePersistence.findByG_S(
			groupId, structureId, start, end, obc);
	}

	public int getStructureArticlesCount(long groupId, String structureId)
		throws SystemException {

		return journalArticlePersistence.countByG_S(groupId, structureId);
	}

	public List<JournalArticle> getTemplateArticles(
			long groupId, String templateId)
		throws SystemException {

		return journalArticlePersistence.findByG_T(groupId, templateId);
	}

	public List<JournalArticle> getTemplateArticles(
			long groupId, String templateId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalArticlePersistence.findByG_T(
			groupId, templateId, start, end, obc);
	}

	public int getTemplateArticlesCount(long groupId, String templateId)
		throws SystemException {

		return journalArticlePersistence.countByG_T(groupId, templateId);
	}

	public boolean hasArticle(long groupId, String articleId)
		throws SystemException {

		try {
			getArticle(groupId, articleId);

			return true;
		}
		catch (PortalException pe) {
			return false;
		}
	}

	public boolean isLatestVersion(
			long groupId, String articleId, double version)
		throws PortalException, SystemException {

		if (getLatestVersion(groupId, articleId) == version) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isLatestVersion(
			long groupId, String articleId, double version, int status)
		throws PortalException, SystemException {

		if (getLatestVersion(groupId, articleId, status) == version) {
			return true;
		}
		else {
			return false;
		}
	}

	public JournalArticle removeArticleLocale(
			long groupId, String articleId, double version, String languageId)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		String content = article.getContent();

		if (article.isTemplateDriven()) {
			content = JournalUtil.removeArticleLocale(content, languageId);
		}
		else {
			content = LocalizationUtil.removeLocalization(
				content, "static-content", languageId, true);
		}

		article.setContent(content);

		journalArticlePersistence.update(article, false);

		return article;
	}

	public List<JournalArticle> search(
			long companyId, long groupId, String keywords, Double version,
			String type, String structureId, String templateId,
			Date displayDateGT, Date displayDateLT, int status, Date reviewDate,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		return journalArticleFinder.findByKeywords(
			companyId, groupId, keywords, version, type, structureId,
			templateId, displayDateGT, displayDateLT, status, reviewDate, start,
			end, obc);
	}

	public List<JournalArticle> search(
			long companyId, long groupId, String articleId, Double version,
			String title, String description, String content, String type,
			String structureId, String templateId, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate,
			boolean andOperator, int start, int end, OrderByComparator obc)
		throws SystemException {

		return journalArticleFinder.findByC_G_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, articleId, version, title, description, content,
			type, structureId, templateId, displayDateGT, displayDateLT,
			status, reviewDate, andOperator, start, end, obc);
	}

	public List<JournalArticle> search(
			long companyId, long groupId, String articleId, Double version,
			String title, String description, String content, String type,
			String[] structureIds, String[] templateIds, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate,
			boolean andOperator, int start, int end, OrderByComparator obc)
		throws SystemException {

		return journalArticleFinder.findByC_G_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, articleId, version, title, description, content,
			type, structureIds, templateIds, displayDateGT, displayDateLT,
			status, reviewDate, andOperator, start, end, obc);
	}

	public int searchCount(
			long companyId, long groupId, String keywords, Double version,
			String type, String structureId, String templateId,
			Date displayDateGT, Date displayDateLT, int status, Date reviewDate)
		throws SystemException {

		return journalArticleFinder.countByKeywords(
			companyId, groupId, keywords, version, type, structureId,
			templateId, displayDateGT, displayDateLT, status, reviewDate);
	}

	public int searchCount(
			long companyId, long groupId, String articleId, Double version,
			String title, String description, String content, String type,
			String structureId, String templateId, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate,
			boolean andOperator)
		throws SystemException {

		return journalArticleFinder.countByC_G_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, articleId, version, title, description, content,
			type, structureId, templateId, displayDateGT, displayDateLT,
			status, reviewDate, andOperator);
	}

	public int searchCount(
			long companyId, long groupId, String articleId, Double version,
			String title, String description, String content, String type,
			String[] structureIds, String[] templateIds, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate,
			boolean andOperator)
		throws SystemException {

		return journalArticleFinder.countByC_G_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, articleId, version, title, description, content,
			type, structureIds, templateIds, displayDateGT, displayDateLT,
			status, reviewDate, andOperator);
	}

	public JournalArticle updateArticle(
			long userId, long groupId, String articleId, double version,
			String content)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		Date displayDate = article.getDisplayDate();

		int displayDateMonth = 0;
		int displayDateDay = 0;
		int displayDateYear = 0;
		int displayDateHour = 0;
		int displayDateMinute = 0;

		if (displayDate != null) {
			Calendar displayCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			displayCal.setTime(displayDate);

			displayDateMonth = displayCal.get(Calendar.MONTH);
			displayDateDay = displayCal.get(Calendar.DATE);
			displayDateYear = displayCal.get(Calendar.YEAR);
			displayDateHour = displayCal.get(Calendar.HOUR);
			displayDateMinute = displayCal.get(Calendar.MINUTE);

			if (displayCal.get(Calendar.AM_PM) == Calendar.PM) {
				displayDateHour += 12;
			}
		}

		Date expirationDate = article.getExpirationDate();

		int expirationDateMonth = 0;
		int expirationDateDay = 0;
		int expirationDateYear = 0;
		int expirationDateHour = 0;
		int expirationDateMinute = 0;
		boolean neverExpire = true;

		if (expirationDate != null) {
			Calendar expirationCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			expirationCal.setTime(expirationDate);

			expirationDateMonth = expirationCal.get(Calendar.MONTH);
			expirationDateDay = expirationCal.get(Calendar.DATE);
			expirationDateYear = expirationCal.get(Calendar.YEAR);
			expirationDateHour = expirationCal.get(Calendar.HOUR);
			expirationDateMinute = expirationCal.get(Calendar.MINUTE);
			neverExpire = false;

			if (expirationCal.get(Calendar.AM_PM) == Calendar.PM) {
				expirationDateHour += 12;
			}
		}

		Date reviewDate = article.getReviewDate();

		int reviewDateMonth = 0;
		int reviewDateDay = 0;
		int reviewDateYear = 0;
		int reviewDateHour = 0;
		int reviewDateMinute = 0;
		boolean neverReview = true;

		if (reviewDate != null) {
			Calendar reviewCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			reviewCal.setTime(reviewDate);

			reviewDateMonth = reviewCal.get(Calendar.MONTH);
			reviewDateDay = reviewCal.get(Calendar.DATE);
			reviewDateYear = reviewCal.get(Calendar.YEAR);
			reviewDateHour = reviewCal.get(Calendar.HOUR);
			reviewDateMinute = reviewCal.get(Calendar.MINUTE);
			neverReview = false;

			if (reviewCal.get(Calendar.AM_PM) == Calendar.PM) {
				reviewDateHour += 12;
			}
		}

		PortletPreferencesIds portletPreferencesIds = new PortletPreferencesIds(
			article.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, PortletKeys.PREFS_PLID_SHARED,
			PortletKeys.JOURNAL);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setPortletPreferencesIds(portletPreferencesIds);

		return updateArticle(
			userId, groupId, articleId, version, article.getTitle(),
			article.getDescription(), content, article.getType(),
			article.getStructureId(), article.getTemplateId(), displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, article.getIndexable(),
			article.isSmallImage(), article.getSmallImageURL(), null, null,
			null, serviceContext);
	}

	public JournalArticle updateArticle(
			long userId, long groupId, String articleId, double version,
			String title, String description, String content, String type,
			String structureId, String templateId, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallFile, Map<String, byte[]> images,
			String articleURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Article

		User user = userPersistence.findByPrimaryKey(userId);
		articleId = articleId.trim().toUpperCase();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, user.getTimeZone(),
			new ArticleDisplayDateException());

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				new ArticleExpirationDateException());
		}

		Date reviewDate = null;

		if (!neverReview) {
			reviewDate = PortalUtil.getDate(
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, user.getTimeZone(),
				new ArticleReviewDateException());
		}

		byte[] smallBytes = null;

		try {
			smallBytes = FileUtil.getBytes(smallFile);
		}
		catch (IOException ioe) {
		}

		Date now = new Date();

		validate(
			groupId, title, content, type, structureId, templateId, smallImage,
			smallImageURL, smallFile, smallBytes);

		JournalArticle oldArticle = getLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_ANY);

		double oldVersion = oldArticle.getVersion();

		if ((version > 0) && (version != oldVersion)) {
			throw new ArticleVersionException();
		}

		boolean incrementVersion = false;

		if (oldArticle.isApproved() || oldArticle.isExpired()) {
			incrementVersion = true;
		}

		JournalArticle article = null;

		if (incrementVersion) {
			double newVersion = MathUtil.format(oldVersion + 0.1, 1, 1);

			long id = counterLocalService.increment();

			article = journalArticlePersistence.create(id);

			article.setResourcePrimKey(oldArticle.getResourcePrimKey());
			article.setGroupId(oldArticle.getGroupId());
			article.setCompanyId(user.getCompanyId());
			article.setUserId(user.getUserId());
			article.setUserName(user.getFullName());
			article.setCreateDate(serviceContext.getModifiedDate(now));
			article.setArticleId(articleId);
			article.setVersion(newVersion);
			article.setSmallImageId(oldArticle.getSmallImageId());
		}
		else {
			article = oldArticle;
		}

		content = format(
			groupId, articleId, article.getVersion(), incrementVersion,
			content, structureId, images);

		article.setModifiedDate(serviceContext.getModifiedDate(now));
		article.setTitle(title);
		article.setUrlTitle(
			getUniqueUrlTitle(article.getId(), groupId, articleId, title));
		article.setDescription(description);
		article.setContent(content);
		article.setType(type);
		article.setStructureId(structureId);
		article.setTemplateId(templateId);
		article.setDisplayDate(displayDate);

		if (oldArticle.isPending()) {
			article.setStatus(oldArticle.getStatus());
		}
		else if ((expirationDate == null) || expirationDate.after(now)) {
			article.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			article.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		article.setExpirationDate(expirationDate);
		article.setReviewDate(reviewDate);
		article.setIndexable(indexable);
		article.setSmallImage(smallImage);

		if (article.getSmallImageId() == 0) {
			article.setSmallImageId(counterLocalService.increment());
		}

		article.setSmallImageURL(smallImageURL);

		journalArticlePersistence.update(article, false);

		// Asset

		long[] assetCategoryIds = serviceContext.getAssetCategoryIds();
		String[] assetTagNames = serviceContext.getAssetTagNames();

		updateAsset(userId, article, assetCategoryIds, assetTagNames);

		// Expando

		ExpandoBridge expandoBridge = article.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Small image

		saveImages(
			smallImage, article.getSmallImageId(), smallFile, smallBytes);

		// Email

		PortletPreferences preferences =
			ServiceContextUtil.getPortletPreferences(serviceContext);

		// Workflow

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_PUBLISH) {

			try {
				sendEmail(article, articleURL, preferences, "requested");
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				user.getCompanyId(), groupId, userId,
				JournalArticle.class.getName(), article.getId(), article,
				serviceContext);
		}

		return article;
	}

	public void updateAsset(
			long userId, JournalArticle article, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException, SystemException {

		// Get the earliest display date and latest expiration date among
		// all article versions

		Date[] dateInterval = getDateInterval(
			article.getGroupId(), article.getArticleId(),
			article.getDisplayDate(), article.getExpirationDate());

		Date displayDate = dateInterval[0];
		Date expirationDate = dateInterval[1];

		boolean visible = article.isApproved();

		boolean addDraftAssetEntry = false;

		if (!article.isApproved() &&
			(article.getVersion() != JournalArticleConstants.DEFAULT_VERSION)) {

			int approvedArticlesCount =
				journalArticlePersistence.countByG_A_S(
					article.getGroupId(), article.getArticleId(),
					WorkflowConstants.STATUS_APPROVED);

			if (approvedArticlesCount > 0) {
				addDraftAssetEntry = true;
			}
		}

		if (addDraftAssetEntry) {
			assetEntryLocalService.updateEntry(
				userId, article.getGroupId(), JournalArticle.class.getName(),
				article.getPrimaryKey(), assetCategoryIds, assetTagNames,
				false, null, null, displayDate, expirationDate,
				ContentTypes.TEXT_HTML, article.getTitle(),
				article.getDescription(), null, null, 0, 0, null, false);
		}
		else {
			assetEntryLocalService.updateEntry(
				userId, article.getGroupId(), JournalArticle.class.getName(),
				article.getResourcePrimKey(), assetCategoryIds, assetTagNames,
				visible, null, null, displayDate, expirationDate,
				ContentTypes.TEXT_HTML, article.getTitle(),
				article.getDescription(), null, null, 0, 0, null, false);
		}
	}

	public JournalArticle updateContent(
			long groupId, String articleId, double version, String content)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		article.setContent(content);

		journalArticlePersistence.update(article, false);

		return article;
	}

	public JournalArticle updateStatus(
			long userId, JournalArticle article, int status, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		int oldStatus = article.getStatus();

		// Article

		article.setModifiedDate(serviceContext.getModifiedDate(now));
		article.setStatus(status);
		article.setStatusByUserId(user.getUserId());
		article.setStatusByUserName(user.getFullName());
		article.setStatusDate(serviceContext.getModifiedDate(now));

		if ((article.getExpirationDate() != null) &&
			(article.getExpirationDate().before(now))) {

			article.setExpirationDate(null);
		}

		journalArticlePersistence.update(article, false);

		if (isLatestVersion(
				article.getGroupId(), article.getArticleId(),
				article.getVersion())) {

			if (status == WorkflowConstants.STATUS_APPROVED) {
				updateUrlTitles(
					article.getGroupId(), article.getArticleId(),
					article.getUrlTitle());

				// Asset

				if ((oldStatus != WorkflowConstants.STATUS_APPROVED) &&
					(article.getVersion() !=
						JournalArticleConstants.DEFAULT_VERSION)) {

					AssetEntry draftAssetEntry = null;

					try {
						draftAssetEntry = assetEntryLocalService.getEntry(
							JournalArticle.class.getName(),
							article.getPrimaryKey());

						Date[] dateInterval = getDateInterval(
							article.getGroupId(), article.getArticleId(),
							article.getDisplayDate(),
							article.getExpirationDate());

						Date displayDate = dateInterval[0];
						Date expirationDate = dateInterval[1];

						long[] assetCategoryIds =
							draftAssetEntry.getCategoryIds();
						String[] assetTagNames = draftAssetEntry.getTagNames();

						assetEntryLocalService.updateEntry(
							userId, article.getGroupId(),
							JournalArticle.class.getName(),
							article.getResourcePrimKey(), assetCategoryIds,
							assetTagNames, true, null, null, displayDate,
							expirationDate, ContentTypes.TEXT_HTML,
							article.getTitle(), article.getDescription(), null,
							null, 0, 0, null, false);

						assetEntryLocalService.deleteEntry(
							JournalArticle.class.getName(),
							article.getPrimaryKey());
					}
					catch (NoSuchEntryException nsee) {
					}
				}

				assetEntryLocalService.updateVisible(
					JournalArticle.class.getName(),
					article.getResourcePrimKey(), true);

				// Expando

				ExpandoBridge expandoBridge = article.getExpandoBridge();

				expandoBridge.setAttributes(serviceContext);

				// Indexer

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					JournalArticle.class);

				indexer.reindex(article);
			}
			else {
				if (article.isApproved()) {
					updatePreviousApprovedArticle(article);
				}
			}
		}

		// Email

		if ((oldStatus == WorkflowConstants.STATUS_PENDING) &&
			((status == WorkflowConstants.STATUS_APPROVED) ||
			 (status == WorkflowConstants.STATUS_DENIED))) {

			String msg = "granted";

			if (status == WorkflowConstants.STATUS_DENIED) {
				msg = "denied";
			}

			try {
				PortletPreferences preferences =
					ServiceContextUtil.getPortletPreferences(serviceContext);

				sendEmail(article, articleURL, preferences, msg);
			}
			catch (Exception e) {
				_log.error(
					"Unable to send email to notify the change of status " +
						" to " + msg + " for article " + article.getId() +
							": " + e.getMessage());
			}
		}

		// Subscriptions

		notifySubscribers(article, serviceContext);

		return article;
	}

	public JournalArticle updateStatus(
			long userId, long classPK, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticle article = getArticle(classPK);

		return updateStatus(userId, article, status, null, serviceContext);
	}

	public JournalArticle updateStatus(
			long userId, long groupId, String articleId, double version,
			int status, String articleURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		return updateStatus(
			userId, article, status, articleURL, serviceContext);
	}

	protected void checkStructure(Document contentDoc, Element root)
		throws PortalException {

		for (Element el : root.elements()) {
			checkStructureField(el, contentDoc);

			checkStructure(contentDoc, el);
		}
	}

	protected void checkStructure(JournalArticle article)
		throws DocumentException, PortalException, SystemException {

		JournalStructure structure = journalStructurePersistence.findByG_S(
			article.getGroupId(), article.getStructureId());

		String content = GetterUtil.getString(article.getContent());

		Document contentDoc = SAXReaderUtil.read(content);
		Document xsdDoc = SAXReaderUtil.read(structure.getXsd());

		try {
			checkStructure(contentDoc, xsdDoc.getRootElement());
		}
		catch (StructureXsdException sxsde) {
			long groupId = article.getGroupId();
			String articleId = article.getArticleId();
			double version = article.getVersion();

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Article {groupId=" + groupId + ", articleId=" +
						articleId + ", version=" + version +
							"} has content that does not match its " +
								"structure: " + sxsde.getMessage());
			}
		}
	}

	protected void checkStructureField(Element el, Document contentDoc)
		throws PortalException {

		StringBuilder elPath = new StringBuilder();

		elPath.append(el.attributeValue("name"));

		Element elParent = el.getParent();

		for (;;) {
			if ((elParent == null) ||
				(elParent.getName().equals("root"))) {

				break;
			}

			elPath.insert(
				0, elParent.attributeValue("name") + StringPool.COMMA);

			elParent = elParent.getParent();
		}

		String[] elPathNames = StringUtil.split(elPath.toString());

		Element contentEl = contentDoc.getRootElement();

		for (int i = 0; i < elPathNames.length; i++) {
			boolean foundEl = false;

			for (Element tempEl : contentEl.elements()) {
				if (elPathNames[i].equals(
						tempEl.attributeValue("name", StringPool.BLANK))) {

					contentEl = tempEl;
					foundEl = true;

					break;
				}
			}

			if (!foundEl) {
				String elType = contentEl.attributeValue(
					"type", StringPool.BLANK);

				if (!elType.equals("list") && !elType.equals("multi-list")) {
					throw new StructureXsdException(elPath.toString());
				}

				break;
			}
		}
	}

	protected void copyArticleImages(
			JournalArticle oldArticle, JournalArticle newArticle)
		throws Exception {

		Document contentDoc = SAXReaderUtil.read(oldArticle.getContent());

		XPath xpathSelector = SAXReaderUtil.createXPath(
			"//dynamic-element[@type='image']");

		List<Node> imageNodes = xpathSelector.selectNodes(contentDoc);

		for (Node imageNode : imageNodes) {
			Element imageEl = (Element)imageNode;

			String instanceId = imageEl.attributeValue("instance-id");
			String name = imageEl.attributeValue("name");

			List<Element> dynamicContentEls = imageEl.elements(
				"dynamic-content");

			for (Element dynamicContentEl : dynamicContentEls) {
				long imageId = GetterUtil.getLong(
					dynamicContentEl.attributeValue("id"));
				String languageId = dynamicContentEl.attributeValue(
					"language-id");

				Image oldImage = null;

				try {
					oldImage = imageLocalService.getImage(imageId);
				}
				catch (NoSuchImageException nsie) {
					continue;
				}

				imageId = journalArticleImageLocalService.getArticleImageId(
					newArticle.getGroupId(), newArticle.getArticleId(),
					newArticle.getVersion(), instanceId, name, languageId);

				imageLocalService.updateImage(imageId, oldImage.getTextObj());

				String elContent =
					"/image/journal/article?img_id=" + imageId + "&t=" +
						ImageServletTokenUtil.getToken(imageId);

				dynamicContentEl.setText(elContent);
				dynamicContentEl.addAttribute("id", String.valueOf(imageId));
			}
		}

		newArticle.setContent(contentDoc.formattedString());
	}

	protected void format(
			long groupId, String articleId, double version,
			boolean incrementVersion, Element root, Map<String, byte[]> images)
		throws PortalException, SystemException {

		for (Element el : root.elements()) {
			String elInstanceId = el.attributeValue(
				"instance-id", StringPool.BLANK);
			String elName = el.attributeValue("name", StringPool.BLANK);
			String elType = el.attributeValue("type", StringPool.BLANK);

			if (elType.equals("image")) {
				formatImage(
					groupId, articleId, version, incrementVersion, el,
					elInstanceId, elName, images);
			}
			/*else if (elType.equals("text_area")) {
				Element dynamicContent = el.element("dynamic-content");

				String text = dynamicContent.getText();

				// LEP-1594

				try {
					text = ParserUtils.trimTags(
						text, new String[] {"script"}, false, true);
				}
				catch (ParserException pe) {
					text = pe.getLocalizedMessage();
				}
				catch (UnsupportedEncodingException uee) {
					text = uee.getLocalizedMessage();
				}

				dynamicContent.setText(text);
			}*/

			format(groupId, articleId, version, incrementVersion, el, images);
		}
	}

	protected String format(
			long groupId, String articleId, double version,
			boolean incrementVersion, String content, String structureId,
			Map<String, byte[]> images)
		throws PortalException, SystemException {

		if (Validator.isNotNull(structureId)) {
			Document doc = null;

			try {
				doc = SAXReaderUtil.read(content);

				Element root = doc.getRootElement();

				format(
					groupId, articleId, version, incrementVersion, root,
					images);

				content = JournalUtil.formatXML(doc);
			}
			catch (DocumentException de) {
				_log.error(de);
			}
			catch (IOException ioe) {
				_log.error(ioe);
			}
		}

		content = HtmlUtil.replaceMsWordCharacters(content);

		return content;
	}

	protected void formatImage(
			long groupId, String articleId, double version,
			boolean incrementVersion, Element el, String elInstanceId,
			String elName, Map<String, byte[]> images)
		throws PortalException, SystemException {

		List<Element> imageContents = el.elements("dynamic-content");

		for (Element dynamicContent : imageContents) {
			String elLanguage = dynamicContent.attributeValue(
				"language-id", StringPool.BLANK);

			if (!elLanguage.equals(StringPool.BLANK)) {
				elLanguage = "_" + elLanguage;
			}

			long imageId =
				journalArticleImageLocalService.getArticleImageId(
					groupId, articleId, version, elInstanceId, elName,
					elLanguage);

			double oldVersion = MathUtil.format(version - 0.1, 1, 1);

			long oldImageId = 0;

			if ((oldVersion >= 1) && incrementVersion) {
				oldImageId =
					journalArticleImageLocalService.getArticleImageId(
						groupId, articleId, oldVersion, elInstanceId, elName,
						elLanguage);
			}

			String elContent =
				"/image/journal/article?img_id=" + imageId + "&t=" +
					ImageServletTokenUtil.getToken(imageId);

			if (dynamicContent.getText().equals("delete")) {
				dynamicContent.setText(StringPool.BLANK);

				imageLocalService.deleteImage(imageId);

				String defaultElLanguage = "";

				if (!Validator.isNotNull(elLanguage)) {
					defaultElLanguage =
						"_" + LocaleUtil.toLanguageId(LocaleUtil.getDefault());
				}

				long defaultImageId =
					journalArticleImageLocalService.getArticleImageId(
						groupId, articleId, version, elInstanceId, elName,
						defaultElLanguage);

				imageLocalService.deleteImage(defaultImageId);

				continue;
			}

			byte[] bytes = images.get(elInstanceId + "_" + elName + elLanguage);

			if (bytes != null && (bytes.length > 0)) {
				dynamicContent.setText(elContent);
				dynamicContent.addAttribute("id", String.valueOf(imageId));

				imageLocalService.updateImage(imageId, bytes);

				continue;
			}

			if ((version > JournalArticleConstants.DEFAULT_VERSION) &&
				(incrementVersion)) {

				Image oldImage = null;

				if (oldImageId > 0) {
					oldImage = imageLocalService.getImage(oldImageId);
				}

				if (oldImage != null) {
					dynamicContent.setText(elContent);
					dynamicContent.addAttribute("id", String.valueOf(imageId));

					bytes = oldImage.getTextObj();

					imageLocalService.updateImage(imageId, bytes);
				}

				continue;
			}

			Image image = imageLocalService.getImage(imageId);

			if (image != null) {
				dynamicContent.setText(elContent);
				dynamicContent.addAttribute("id", String.valueOf(imageId));

				continue;
			}

			long contentImageId = GetterUtil.getLong(HttpUtil.getParameter(
				dynamicContent.getText(), "img_id"));

			if (contentImageId <= 0) {
				contentImageId = GetterUtil.getLong(HttpUtil.getParameter(
					dynamicContent.getText(), "img_id", false));
			}

			if (contentImageId > 0) {
				image = imageLocalService.getImage(contentImageId);

				if (image != null) {
					dynamicContent.addAttribute(
						"id", String.valueOf(contentImageId));

					continue;
				}
			}

			String defaultElLanguage = "";

			if (!Validator.isNotNull(elLanguage)) {
				defaultElLanguage =
					"_" + LocaleUtil.toLanguageId(LocaleUtil.getDefault());
			}

			long defaultImageId =
				journalArticleImageLocalService.getArticleImageId(
					groupId, articleId, version, elInstanceId, elName,
					defaultElLanguage);

			Image defaultImage = imageLocalService.getImage(defaultImageId);

			if (defaultImage != null) {
				dynamicContent.setText(elContent);
				dynamicContent.addAttribute(
					"id", String.valueOf(defaultImageId));

				bytes = defaultImage.getTextObj();

				imageLocalService.updateImage(defaultImageId, bytes);

				continue;
			}

			dynamicContent.setText(StringPool.BLANK);
		}
	}

	protected Date[] getDateInterval(
			long groupId, String articleId, Date earliestDisplayDate,
			Date latestExpirationDate)
		throws SystemException {

		Date[] dateInterval = new Date[2];

		List<JournalArticle> articles = journalArticlePersistence.findByG_A_S(
			groupId, articleId, WorkflowConstants.STATUS_APPROVED);

		boolean expiringArticle = true;

		if (latestExpirationDate == null) {
			expiringArticle = false;
		}

		for (JournalArticle article : articles) {
			if ((earliestDisplayDate == null) ||
				((article.getDisplayDate() != null) &&
				 earliestDisplayDate.after(article.getDisplayDate()))) {

				earliestDisplayDate = article.getDisplayDate();
			}

			if (expiringArticle &&
				((latestExpirationDate == null) ||
				 ((article.getExpirationDate() != null) &&
				  latestExpirationDate.before(article.getExpirationDate())))) {

				latestExpirationDate = article.getExpirationDate();
			}

			if (expiringArticle && (article.getExpirationDate() == null)) {
				latestExpirationDate = null;
				expiringArticle = false;
			}
		}

		dateInterval[0] = earliestDisplayDate;
		dateInterval[1] = latestExpirationDate;

		return dateInterval;
	}

	protected String getUniqueUrlTitle(
			long id, long groupId, String articleId, String title)
		throws PortalException, SystemException {

		String urlTitle = JournalUtil.getUrlTitle(id, title);

		String newUrlTitle = urlTitle;

		for (int i = 1;; i++) {
			JournalArticle article = null;

			try {
				article = getArticleByUrlTitle(groupId, newUrlTitle);
			}
			catch (NoSuchArticleException nsae) {
			}

			if ((article == null) || article.getArticleId().equals(articleId)) {
				break;
			}
			else {
				newUrlTitle = urlTitle + StringPool.DASH + i;
			}
		}

		return newUrlTitle;
	}

	protected void notifySubscribers(
			JournalArticle article, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!article.isApproved()) {
			return;
		}

		String articleURL = PortalUtil.getControlPanelFullURL(
			serviceContext.getScopeGroupId(), PortletKeys.JOURNAL, null);

		if (Validator.isNull(articleURL)) {
			return;
		}

		PortletPreferences preferences =
			ServiceContextUtil.getPortletPreferences(serviceContext);

		if (preferences == null) {
			long ownerId = article.getGroupId();
			int ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
			long plid = PortletKeys.PREFS_PLID_SHARED;
			String portletId = PortletKeys.JOURNAL;
			String defaultPreferences = null;

			preferences = portletPreferencesLocalService.getPreferences(
				article.getCompanyId(), ownerId, ownerType, plid, portletId,
				defaultPreferences);
		}

		if ((article.getVersion() == 1.0) &&
			JournalUtil.getEmailArticleAddedEnabled(preferences)) {
		}
		else if ((article.getVersion() != 1.0) &&
				 JournalUtil.getEmailArticleUpdatedEnabled(preferences)) {
		}
		else {
			return;
		}

		Company company = companyPersistence.findByPrimaryKey(
			article.getCompanyId());

		String emailAddress = StringPool.BLANK;
		String fullName = article.getUserName();

		try {
			User user = userPersistence.findByPrimaryKey(article.getUserId());

			emailAddress = user.getEmailAddress();
			fullName = user.getFullName();
		}
		catch (NoSuchUserException nsue) {
		}

		String portletName = PortalUtil.getPortletTitle(
			PortletKeys.JOURNAL, LocaleUtil.getDefault());

		String fromName = JournalUtil.getEmailFromName(preferences);
		String fromAddress = JournalUtil.getEmailFromAddress(preferences);

		fromName = StringUtil.replace(
			fromName,
			new String[] {
				"[$ARTICLE_ID$]",
				"[$ARTICLE_TITLE$]",
				"[$ARTICLE_USER_ADDRESS$]",
				"[$ARTICLE_USER_NAME$]",
				"[$ARTICLE_VERSION$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$PORTLET_NAME$]",
			},
			new String[] {
				article.getArticleId(),
				article.getTitle(),
				emailAddress,
				fullName,
				String.valueOf(article.getVersion()),
				fromAddress,
				fromName,
				company.getVirtualHost(),
				portletName,
			});

		fromAddress = StringUtil.replace(
			fromAddress,
			new String[] {
				"[$ARTICLE_ID$]",
				"[$ARTICLE_TITLE$]",
				"[$ARTICLE_USER_ADDRESS$]",
				"[$ARTICLE_USER_NAME$]",
				"[$ARTICLE_VERSION$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$PORTLET_NAME$]",
			},
			new String[] {
				article.getArticleId(),
				article.getTitle(),
				emailAddress,
				fullName,
				String.valueOf(article.getVersion()),
				fromAddress,
				fromName,
				company.getVirtualHost(),
				portletName,
			});

		String subject = null;
		String body = null;

		if (article.getVersion() == 1.0) {
			subject = JournalUtil.getEmailArticleAddedSubject(preferences);
			body = JournalUtil.getEmailArticleAddedBody(preferences);
		}
		else {
			subject = JournalUtil.getEmailArticleUpdatedSubject(preferences);
			body = JournalUtil.getEmailArticleUpdatedBody(preferences);
		}

		subject = StringUtil.replace(
			subject,
			new String[] {
				"[$ARTICLE_ID$]",
				"[$ARTICLE_TITLE$]",
				"[$ARTICLE_URL$]",
				"[$ARTICLE_USER_ADDRESS$]",
				"[$ARTICLE_USER_NAME$]",
				"[$ARTICLE_VERSION$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$PORTLET_NAME$]",
			},
			new String[] {
				article.getArticleId(),
				article.getTitle(),
				articleURL,
				emailAddress,
				fullName,
				String.valueOf(article.getVersion()),
				fromAddress,
				fromName,
				company.getVirtualHost(),
				portletName,
			});

		body = StringUtil.replace(
			body,
			new String[] {
				"[$ARTICLE_ID$]",
				"[$ARTICLE_TITLE$]",
				"[$ARTICLE_URL$]",
				"[$ARTICLE_USER_ADDRESS$]",
				"[$ARTICLE_USER_NAME$]",
				"[$ARTICLE_VERSION$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$PORTLET_NAME$]",
			},
			new String[] {
				article.getArticleId(),
				article.getTitle(),
				articleURL,
				emailAddress,
				fullName,
				String.valueOf(article.getVersion()),
				fromAddress,
				fromName,
				company.getVirtualHost(),
				portletName,
			});

		Message message = new Message();

		message.put("companyId", article.getCompanyId());
		message.put("userId", article.getUserId());
		message.put("groupId", article.getGroupId());
		message.put("articleId", article.getArticleId());
		message.put("fromName", fromName);
		message.put("fromAddress", fromAddress);
		message.put("subject", subject);
		message.put("body", body);
		message.put("replyToAddress", fromAddress);
		message.put(
			"mailId",
			JournalUtil.getMailId(company.getMx(), article.getArticleId()));
		message.put("htmlFormat", Boolean.TRUE);

		MessageBusUtil.sendMessage(DestinationNames.JOURNAL, message);
	}

	protected void saveImages(
			boolean smallImage, long smallImageId, File smallFile,
			byte[] smallBytes)
		throws PortalException, SystemException {

		if (smallImage) {
			if ((smallFile != null) && (smallBytes != null)) {
				imageLocalService.updateImage(smallImageId, smallBytes);
			}
		}
		else {
			imageLocalService.deleteImage(smallImageId);
		}
	}

	protected void sendEmail(
			JournalArticle article, String articleURL,
			PortletPreferences preferences, String emailType)
		throws IOException, PortalException, SystemException {

		if (preferences == null) {
			return;
		}
		else if (emailType.equals("denied") &&
			JournalUtil.getEmailArticleApprovalDeniedEnabled(preferences)) {
		}
		else if (emailType.equals("granted") &&
				 JournalUtil.getEmailArticleApprovalGrantedEnabled(
					preferences)) {
		}
		else if (emailType.equals("requested") &&
				 JournalUtil.getEmailArticleApprovalRequestedEnabled(
					preferences)) {
		}
		else if (emailType.equals("review") &&
				 JournalUtil.getEmailArticleReviewEnabled(preferences)) {
		}
		else {
			return;
		}

		Company company = companyPersistence.findByPrimaryKey(
			article.getCompanyId());

		User user = userPersistence.findByPrimaryKey(article.getUserId());

		articleURL +=
			"&groupId=" + article.getGroupId() + "&articleId=" +
				article.getArticleId() + "&version=" + article.getVersion();

		String portletName = PortalUtil.getPortletTitle(
			PortletKeys.JOURNAL, user);

		String fromName = JournalUtil.getEmailFromName(preferences);
		String fromAddress = JournalUtil.getEmailFromAddress(preferences);

		String toName = user.getFullName();
		String toAddress = user.getEmailAddress();

		if (emailType.equals("requested") ||
			emailType.equals("review")) {

			String tempToName = fromName;
			String tempToAddress = fromAddress;

			fromName = toName;
			fromAddress = toAddress;

			toName = tempToName;
			toAddress = tempToAddress;
		}

		String subject = null;
		String body = null;

		if (emailType.equals("denied")) {
			subject =
				JournalUtil.getEmailArticleApprovalDeniedSubject(preferences);
			body = JournalUtil.getEmailArticleApprovalDeniedBody(preferences);
		}
		else if (emailType.equals("granted")) {
			subject =
				JournalUtil.getEmailArticleApprovalGrantedSubject(preferences);
			body = JournalUtil.getEmailArticleApprovalGrantedBody(preferences);
		}
		else if (emailType.equals("requested")) {
			subject =
				JournalUtil.getEmailArticleApprovalRequestedSubject(
				preferences);
			body = JournalUtil.getEmailArticleApprovalRequestedBody(
				preferences);
		}
		else if (emailType.equals("review")) {
			subject = JournalUtil.getEmailArticleReviewSubject(preferences);
			body = JournalUtil.getEmailArticleReviewBody(preferences);
		}

		subject = StringUtil.replace(
			subject,
			new String[] {
				"[$ARTICLE_ID$]",
				"[$ARTICLE_TITLE$]",
				"[$ARTICLE_URL$]",
				"[$ARTICLE_USER_NAME$]",
				"[$ARTICLE_VERSION$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$PORTLET_NAME$]",
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			},
			new String[] {
				article.getArticleId(),
				article.getTitle(),
				articleURL,
				article.getUserName(),
				String.valueOf(article.getVersion()),
				fromAddress,
				fromName,
				company.getVirtualHost(),
				portletName,
				toAddress,
				toName,
			});

		body = StringUtil.replace(
			body,
			new String[] {
				"[$ARTICLE_ID$]",
				"[$ARTICLE_TITLE$]",
				"[$ARTICLE_URL$]",
				"[$ARTICLE_USER_NAME$]",
				"[$ARTICLE_VERSION$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$PORTLET_NAME$]",
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			},
			new String[] {
				article.getArticleId(),
				article.getTitle(),
				articleURL,
				article.getUserName(),
				String.valueOf(article.getVersion()),
				fromAddress,
				fromName,
				company.getVirtualHost(),
				portletName,
				toAddress,
				toName,
			});

		InternetAddress from = new InternetAddress(fromAddress, fromName);

		InternetAddress to = new InternetAddress(toAddress, toName);

		MailMessage message = new MailMessage(from, to, subject, body, true);

		mailService.sendEmail(message);
	}

	protected void updatePreviousApprovedArticle(JournalArticle article)
		throws PortalException, SystemException {

		List<JournalArticle> approvedArticles =
			journalArticlePersistence.findByG_A_S(
				article.getGroupId(), article.getArticleId(),
				WorkflowConstants.STATUS_APPROVED, 0, 2);

		if (approvedArticles.size() > 1) {
			JournalArticle previousApprovedArticle = approvedArticles.get(1);

			if (article.isIndexable()) {
				Indexer indexer = IndexerRegistryUtil.getIndexer(
					JournalArticle.class);

				indexer.reindex(previousApprovedArticle);
			}
		}
		else {
			if (article.isIndexable()) {
				Indexer indexer = IndexerRegistryUtil.getIndexer(
					JournalArticle.class);

				indexer.delete(article);
			}

			assetEntryLocalService.updateVisible(
				JournalArticle.class.getName(), article.getResourcePrimKey(),
				false);
		}
	}

	protected void updateUrlTitles(
			long groupId, String articleId, String urlTitle)
		throws SystemException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_A(
			groupId, articleId);

		for (JournalArticle article : articles) {
			if (!article.getUrlTitle().equals(urlTitle)) {
				article.setUrlTitle(urlTitle);

				journalArticlePersistence.update(article, false);
			}
		}
	}

	protected void validate(
			long groupId, String articleId, boolean autoArticleId,
			double version, String title, String content, String type,
			String structureId, String templateId, boolean smallImage,
			String smallImageURL, File smallFile, byte[] smallBytes)
		throws PortalException, SystemException {

		if (!autoArticleId) {
			validate(articleId);

			JournalArticle article = journalArticlePersistence.fetchByG_A_V(
				groupId, articleId, version);

			if (article != null) {
				throw new DuplicateArticleIdException();
			}
		}

		validate(
			groupId, title, content, type, structureId, templateId,
			smallImage, smallImageURL, smallFile, smallBytes);
	}

	protected void validate(
			long groupId, String title, String content, String type,
			String structureId, String templateId, boolean smallImage,
			String smallImageURL, File smallFile, byte[] smallBytes)
		throws PortalException, SystemException {

		if (Validator.isNull(title)) {
			throw new ArticleTitleException();
		}
		else if (Validator.isNull(content)) {
			throw new ArticleContentException();
		}
		else if (Validator.isNull(type)) {
			throw new ArticleTypeException();
		}

		if (Validator.isNotNull(structureId)) {
			journalStructurePersistence.findByG_S(groupId, structureId);

			JournalTemplate template = journalTemplatePersistence.findByG_T(
				groupId, templateId);

			if (!template.getStructureId().equals(structureId)) {
				throw new NoSuchTemplateException();
			}
		}

		String[] imageExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.JOURNAL_IMAGE_EXTENSIONS, StringPool.COMMA);

		if (smallImage && Validator.isNull(smallImageURL) &&
			smallFile != null && smallBytes != null) {

			String smallImageName = smallFile.getName();

			if (smallImageName != null) {
				boolean validSmallImageExtension = false;

				for (int i = 0; i < imageExtensions.length; i++) {
					if (StringPool.STAR.equals(imageExtensions[i]) ||
						StringUtil.endsWith(
							smallImageName, imageExtensions[i])) {

						validSmallImageExtension = true;

						break;
					}
				}

				if (!validSmallImageExtension) {
					throw new ArticleSmallImageNameException(smallImageName);
				}
			}

			long smallImageMaxSize = PrefsPropsUtil.getLong(
				PropsKeys.JOURNAL_IMAGE_SMALL_MAX_SIZE);

			if ((smallImageMaxSize > 0) &&
				((smallBytes == null) ||
					(smallBytes.length > smallImageMaxSize))) {

				throw new ArticleSmallImageSizeException();
			}
		}
	}

	protected void validate(String articleId) throws PortalException {
		if ((Validator.isNull(articleId)) ||
			(articleId.indexOf(StringPool.SPACE) != -1)) {

			throw new ArticleIdException();
		}
	}

	private static final String _TOKEN_PAGE_BREAK = PropsUtil.get(
		PropsKeys.JOURNAL_ARTICLE_TOKEN_PAGE_BREAK);

	private long _journalArticleCheckInterval =
		PropsValues.JOURNAL_ARTICLE_CHECK_INTERVAL * Time.MINUTE;

	private static Log _log = LogFactoryUtil.getLog(
		JournalArticleLocalServiceImpl.class);

}