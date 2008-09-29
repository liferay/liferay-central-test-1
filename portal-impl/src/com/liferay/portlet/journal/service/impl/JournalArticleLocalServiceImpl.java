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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.servlet.ImageServletTokenUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.servlet.filters.layoutcache.LayoutCacheUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.journal.ArticleContentException;
import com.liferay.portlet.journal.ArticleDisplayDateException;
import com.liferay.portlet.journal.ArticleExpirationDateException;
import com.liferay.portlet.journal.ArticleIdException;
import com.liferay.portlet.journal.ArticleReviewDateException;
import com.liferay.portlet.journal.ArticleSmallImageNameException;
import com.liferay.portlet.journal.ArticleSmallImageSizeException;
import com.liferay.portlet.journal.ArticleTitleException;
import com.liferay.portlet.journal.ArticleTypeException;
import com.liferay.portlet.journal.DuplicateArticleIdException;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.NoSuchArticleResourceException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.StructureXsdException;
import com.liferay.portlet.journal.job.CheckArticleJob;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalArticleDisplayImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.service.base.JournalArticleLocalServiceBaseImpl;
import com.liferay.portlet.journal.util.Indexer;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.LocalizationUtil;
import com.liferay.util.MathUtil;

import java.io.File;
import java.io.IOException;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="JournalArticleLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalArticleLocalServiceImpl
	extends JournalArticleLocalServiceBaseImpl {

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
			String articleURL, PortletPreferences prefs, String[] tagsEntries,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		double version = JournalArticleImpl.DEFAULT_VERSION;

		return addArticle(
			userId, groupId, articleId, autoArticleId, version, title,
			description, content, type, structureId, templateId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallFile, images, articleURL, prefs,
			tagsEntries, addCommunityPermissions, addGuestPermissions);
	}

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
			String articleURL, PortletPreferences prefs, String[] tagsEntries,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addArticle(
			null, userId, groupId, articleId, autoArticleId, version, title,
			description, content, type, structureId, templateId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallFile, images, articleURL, prefs,
			tagsEntries, Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
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
			PortletPreferences prefs, String[] tagsEntries,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addArticle(
			uuid, userId, groupId, articleId, autoArticleId, version, title,
			description, content, type, structureId, templateId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallFile, images, articleURL, prefs,
			tagsEntries, Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public JournalArticle addArticle(
			long userId, long groupId,String articleId, boolean autoArticleId,
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
			String articleURL, PortletPreferences prefs, String[] tagsEntries,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		double version = JournalArticleImpl.DEFAULT_VERSION;

		return addArticle(
			null, userId, groupId, articleId, autoArticleId,
			version, title, description, content, type, structureId,
			templateId, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, reviewDateMonth, reviewDateDay,
			reviewDateYear, reviewDateHour, reviewDateMinute, neverReview,
			indexable, smallImage, smallImageURL, smallFile, images, articleURL,
			prefs, tagsEntries, null, null, communityPermissions,
			guestPermissions);
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
			PortletPreferences prefs, String[] tagsEntries,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
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
		article.setCreateDate(now);
		article.setModifiedDate(now);
		article.setArticleId(articleId);
		article.setVersion(version);
		article.setTitle(title);
		article.setDescription(description);
		article.setContent(content);
		article.setType(type);
		article.setStructureId(structureId);
		article.setTemplateId(templateId);
		article.setDisplayDate(displayDate);
		article.setApproved(false);

		if ((expirationDate == null) || expirationDate.after(now)) {
			article.setExpired(false);
		}
		else {
			article.setExpired(true);
		}

		article.setExpirationDate(expirationDate);
		article.setReviewDate(reviewDate);
		article.setIndexable(indexable);
		article.setSmallImage(smallImage);
		article.setSmallImageId(counterLocalService.increment());
		article.setSmallImageURL(smallImageURL);

		journalArticlePersistence.update(article, false);

		// Small image

		saveImages(
			smallImage, article.getSmallImageId(), smallFile, smallBytes);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addArticleResources(
				article, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addArticleResources(
				article, communityPermissions, guestPermissions);
		}

		// Tags

		updateTagsAsset(userId, article, tagsEntries);

		// Email

		try {
			sendEmail(article, articleURL, prefs, "requested");
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return article;
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
			long groupId, String articleId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId);

		addArticleResources(article, communityPermissions, guestPermissions);
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

	public JournalArticle approveArticle(
			long userId, long groupId, String articleId, double version,
			String articleURL, PortletPreferences prefs)
		throws PortalException, SystemException {

		// Article

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		article.setModifiedDate(now);
		article.setApproved(true);
		article.setApprovedByUserId(user.getUserId());
		article.setApprovedByUserName(user.getFullName());
		article.setApprovedDate(now);
		article.setExpired(false);

		if ((article.getExpirationDate() != null) &&
			(article.getExpirationDate().before(now))) {

			article.setExpirationDate(null);
		}

		journalArticlePersistence.update(article, false);

		// Email

		try {
			sendEmail(article, articleURL, prefs, "granted");
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		// Lucene

		try {
			if (article.isIndexable()) {
				String[] tagsEntries = tagsEntryLocalService.getEntryNames(
					JournalArticle.class.getName(),
					article.getResourcePrimKey());

				Indexer.updateArticle(
					article.getCompanyId(), article.getGroupId(),
					article.getArticleId(), article.getVersion(),
					article.getTitle(), article.getDescription(),
					article.getContent(), article.getType(),
					article.getDisplayDate(), tagsEntries);
			}
		}
		catch (SearchException se) {
			_log.error("Indexing " + article.getId(), se);
		}

		return article;
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
				Boolean.FALSE, now,
				new Date(now.getTime() - CheckArticleJob.INTERVAL));

		if (_log.isDebugEnabled()) {
			_log.debug("Expiring " + articles.size() + " articles");
		}

		Set<Long> companyIds = new HashSet<Long>();

		for (JournalArticle article : articles) {
			article.setApproved(false);
			article.setExpired(true);

			journalArticlePersistence.update(article, false);

			try {
				if (article.isIndexable()) {
					Indexer.deleteArticle(
						article.getCompanyId(), article.getArticleId());
				}
			}
			catch (SearchException se) {
				_log.error("Removing index " + article.getId(), se);
			}

			JournalContentUtil.clearCache(
				article.getGroupId(), article.getArticleId(),
				article.getTemplateId());

			companyIds.add(article.getCompanyId());
		}

		for (long companyId : companyIds) {
			LayoutCacheUtil.clearCache(companyId);
		}

		articles = journalArticleFinder.findByReviewDate(
			now, new Date(now.getTime() - CheckArticleJob.INTERVAL));

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

			PortletPreferences prefs =
				portletPreferencesLocalService.getPreferences(
					article.getCompanyId(), ownerId, ownerType, plid,
					portletId);

			try {
				sendEmail(article, articleURL, prefs, "review");
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
		newArticle.setVersion(JournalArticleImpl.DEFAULT_VERSION);
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
		newArticle.setApproved(oldArticle.isApproved());
		newArticle.setExpired(oldArticle.isExpired());
		newArticle.setExpirationDate(oldArticle.getExpirationDate());
		newArticle.setReviewDate(oldArticle.getReviewDate());
		newArticle.setIndexable(oldArticle.isIndexable());
		newArticle.setSmallImage(oldArticle.isSmallImage());
		newArticle.setSmallImageId(counterLocalService.increment());
		newArticle.setSmallImageURL(oldArticle.getSmallImageURL());

		journalArticlePersistence.update(newArticle, false);

		// Small image

		if (oldArticle.getSmallImage()) {
			Image image = imageLocalService.getImage(
				oldArticle.getSmallImageId());

			byte[] smallBytes = image.getTextObj();

			imageLocalService.updateImage(
				newArticle.getSmallImageId(), smallBytes);
		}

		// Resources

		addArticleResources(newArticle, true, true);

		// Tags

		String[] tagsEntries = tagsEntryLocalService.getEntryNames(
			JournalArticle.class.getName(), oldArticle.getResourcePrimKey());

		updateTagsAsset(userId, newArticle, tagsEntries);

		return null;
	}

	public void deleteArticle(
			long groupId, String articleId, double version, String articleURL,
			PortletPreferences prefs)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		deleteArticle(article, articleURL, prefs);
	}

	public void deleteArticle(
			JournalArticle article, String articleURL, PortletPreferences prefs)
		throws PortalException, SystemException {

		// Lucene

		try {
			if (article.isApproved() && article.isIndexable()) {
				Indexer.deleteArticle(
					article.getCompanyId(), article.getArticleId());
			}
		}
		catch (SearchException se) {
			_log.error("Deleting index " + article.getPrimaryKey(), se);
		}

		// Email

		if ((prefs != null) && !article.isApproved() &&
			isLatestVersion(
				article.getGroupId(), article.getArticleId(),
				article.getVersion())) {

			try {
				sendEmail(article, articleURL, prefs, "denied");
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}

		// Tags

		tagsAssetLocalService.deleteAsset(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		// Content searches

		journalContentSearchLocalService.deleteArticleContentSearches(
			article.getGroupId(), article.getArticleId());

		// Images

		journalArticleImageLocalService.deleteImages(
			article.getGroupId(), article.getArticleId(), article.getVersion());

		// Small image

		imageLocalService.deleteImage(article.getSmallImageId());

		// Resources

		if (journalArticlePersistence.countByG_A(
				article.getGroupId(), article.getArticleId()) == 1) {

			resourceLocalService.deleteResource(
				article.getCompanyId(), JournalArticle.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				article.getResourcePrimKey());
		}

		// Resource

		if (journalArticlePersistence.countByG_A(
				article.getGroupId(), article.getArticleId()) == 1) {

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

	public void deleteArticles(long groupId)
		throws PortalException, SystemException {

		for (JournalArticle article :
				journalArticlePersistence.findByGroupId(groupId)) {

			deleteArticle(article, null, null);
		}
	}

	public void expireArticle(
			long groupId, String articleId, double version, String articleURL,
			PortletPreferences prefs)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		expireArticle(article, articleURL, prefs);
	}

	public void expireArticle(
			JournalArticle article, String articleURL, PortletPreferences prefs)
		throws PortalException, SystemException {

		// Email

		if ((prefs != null) && !article.isApproved() &&
			isLatestVersion(
				article.getGroupId(), article.getArticleId(),
				article.getVersion())) {

			try {
				sendEmail(article, articleURL, prefs, "denied");
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}

		// Article

		article.setExpirationDate(new Date());

		article.setApproved(false);
		article.setExpired(true);

		journalArticlePersistence.update(article, false);

		// Lucene

		try {
			if (article.isIndexable()) {
				Indexer.deleteArticle(
					article.getCompanyId(), article.getArticleId());
			}
		}
		catch (SearchException se) {
			_log.error("Removing index " + article.getId(), se);
		}
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
			return getLatestArticle(groupId, articleId, Boolean.TRUE);
		}
		catch (NoSuchArticleException nsae) {
			return getLatestArticle(groupId, articleId, Boolean.FALSE);
		}
	}

	public JournalArticle getArticle(
			long groupId, String articleId, double version)
		throws PortalException, SystemException {

		return journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);
	}

	public String getArticleContent(
			long groupId, String articleId, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleContent(
			groupId, articleId, null, languageId, themeDisplay);
	}

	public String getArticleContent(
			long groupId, String articleId, String templateId,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticleDisplay articleDisplay = getArticleDisplay(
			groupId, articleId, templateId, languageId, themeDisplay);

		return articleDisplay.getContent();
	}

	public String getArticleContent(
			long groupId, String articleId, double version, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleContent(
			groupId, articleId, version, null, languageId, themeDisplay);
	}

	public String getArticleContent(
			long groupId, String articleId, double version, String templateId,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticleDisplay articleDisplay = getArticleDisplay(
			groupId, articleId, version, templateId, languageId, themeDisplay);

		if (articleDisplay == null) {
			return StringPool.BLANK;
		}
		else {
			return articleDisplay.getContent();
		}
	}

	public String getArticleContent(
			JournalArticle article, String templateId, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticleDisplay articleDisplay = getArticleDisplay(
			article, templateId, languageId, 1, null, themeDisplay);

		if (articleDisplay == null) {
			return StringPool.BLANK;
		}
		else {
			return articleDisplay.getContent();
		}
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleDisplay(
			groupId, articleId, null, languageId, themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String languageId,
			int page, String xmlRequest, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleDisplay(
			groupId, articleId, null, languageId, page, xmlRequest,
			themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String templateId,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticle article = getDisplayArticle(groupId, articleId);

		return getArticleDisplay(
			groupId, articleId, article.getVersion(), templateId, languageId,
			themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String templateId,
			String languageId, int page, String xmlRequest,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticle article = getDisplayArticle(groupId, articleId);

		return getArticleDisplay(
			groupId, articleId, article.getVersion(), templateId, languageId,
			page, xmlRequest, themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, double version, String templateId,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleDisplay(
			groupId, articleId, version, templateId, languageId, 1, null,
			themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, double version, String templateId,
			String languageId, int page, String xmlRequest,
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
			article, templateId, languageId, page, xmlRequest, themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			JournalArticle article, String templateId, String languageId,
			int page, String xmlRequest, ThemeDisplay themeDisplay)
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

				String defaultTemplateId = article.getTemplateId();

				if (Validator.isNull(templateId)) {
					templateId = defaultTemplateId;
				}

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
				tokens, languageId, xml, script, langType);

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
			article.getTitle(), article.getDescription(),
			article.getAvailableLocales(), content, article.getType(),
			article.getStructureId(), templateId, article.isSmallImage(),
			article.getSmallImageId(), article.getSmallImageURL(),
			numberOfPages, page, paginate, cacheable);
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

	public JournalArticle getDisplayArticle(long groupId, String articleId)
		throws PortalException, SystemException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_A_A(
			groupId, articleId, true);

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

	public JournalArticle getLatestArticle(long groupId, String articleId)
		throws PortalException, SystemException {

		return getLatestArticle(groupId, articleId, null);
	}

	public JournalArticle getLatestArticle(
			long groupId, String articleId, Boolean approved)
		throws PortalException, SystemException {

		List<JournalArticle> articles = null;

		if (approved == null) {
			articles = journalArticlePersistence.findByG_A(
				groupId, articleId, 0, 1);
		}
		else {
			articles = journalArticlePersistence.findByG_A_A(
				groupId, articleId, approved.booleanValue(), 0, 1);
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
			long groupId, String articleId, Boolean approved)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId, approved);

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
			long groupId, String articleId, double version, Boolean active)
		throws PortalException, SystemException {

		if (getLatestVersion(groupId, articleId, active) == version) {
			return true;
		}
		else {
			return false;
		}
	}

	public void reIndex(String[] ids) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		long companyId = GetterUtil.getLong(ids[0]);

		try {
			for (JournalArticle article :
					journalArticlePersistence.findByCompanyId(companyId)) {

				if (article.isApproved() && article.isIndexable()) {
					long resourcePrimKey = article.getResourcePrimKey();
					long groupId = article.getGroupId();
					String articleId = article.getArticleId();
					double version = article.getVersion();
					String title = article.getTitle();
					String description = article.getDescription();
					String content = article.getContent();
					String type = article.getType();
					Date displayDate = article.getDisplayDate();

					String[] tagsEntries = tagsEntryLocalService.getEntryNames(
						JournalArticle.class.getName(), resourcePrimKey);

					try {
						com.liferay.portal.kernel.search.Document doc =
							Indexer.getArticleDocument(
								companyId, groupId, articleId, version, title,
								description, content, type, displayDate,
								tagsEntries);

						SearchEngineUtil.addDocument(companyId, doc);
					}
					catch (Exception e1) {
						_log.error("Reindexing " + article.getId(), e1);
					}
				}
			}
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e2) {
			throw new SystemException(e2);
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

	public Hits search(
			long companyId, long groupId, String keywords, int start, int end)
		throws SystemException {

		return search(
			companyId, groupId, keywords, "displayDate", Sort.LONG_TYPE, start,
			end);
	}

	public Hits search(
			long companyId, long groupId, String keywords, String sortField,
			int sortType, int start, int end)
		throws SystemException {

		try {
			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create();

			contextQuery.addRequiredTerm(Field.PORTLET_ID, Indexer.PORTLET_ID);

			if (groupId > 0) {
				contextQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			}

			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			if (Validator.isNotNull(keywords)) {
				searchQuery.addTerm(Field.TITLE, keywords);
				searchQuery.addTerm(Field.CONTENT, keywords);
				searchQuery.addTerm(Field.DESCRIPTION, keywords);
				searchQuery.addTerm(Field.TAGS_ENTRIES, keywords);
			}

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			if (searchQuery.clauses().size() > 0) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			Sort sort = new Sort(sortField, sortType, true);

			return SearchEngineUtil.search(
				companyId, fullQuery, sort, start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public List<JournalArticle> search(
			long companyId, long groupId, String keywords, Double version,
			String type, String structureId, String templateId,
			Date displayDateGT, Date displayDateLT, Boolean approved,
			Boolean expired, Date reviewDate, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalArticleFinder.findByKeywords(
			companyId, groupId, keywords, version, type, structureId,
			templateId, displayDateGT, displayDateLT, approved, expired,
			reviewDate, start, end, obc);
	}

	public List<JournalArticle> search(
			long companyId, long groupId, String articleId, Double version,
			String title, String description, String content, String type,
			String structureId, String templateId, Date displayDateGT,
			Date displayDateLT, Boolean approved, Boolean expired,
			Date reviewDate, boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalArticleFinder.findByC_G_A_V_T_D_C_T_S_T_D_A_E_R(
			companyId, groupId, articleId, version, title, description, content,
			type, structureId, templateId, displayDateGT, displayDateLT,
			approved, expired, reviewDate, andOperator, start, end, obc);
	}

	public List<JournalArticle> search(
			long companyId, long groupId, String articleId, Double version,
			String title, String description, String content, String type,
			String[] structureIds, String[] templateIds, Date displayDateGT,
			Date displayDateLT, Boolean approved, Boolean expired,
			Date reviewDate, boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalArticleFinder.findByC_G_A_V_T_D_C_T_S_T_D_A_E_R(
			companyId, groupId, articleId, version, title, description, content,
			type, structureIds, templateIds, displayDateGT, displayDateLT,
			approved, expired, reviewDate, andOperator, start, end, obc);
	}

	public int searchCount(
			long companyId, long groupId, String keywords, Double version,
			String type, String structureId, String templateId,
			Date displayDateGT, Date displayDateLT, Boolean approved,
			Boolean expired, Date reviewDate)
		throws SystemException {

		return journalArticleFinder.countByKeywords(
			companyId, groupId, keywords, version, type, structureId,
			templateId, displayDateGT, displayDateLT, approved, expired,
			reviewDate);
	}

	public int searchCount(
			long companyId, long groupId, String articleId, Double version,
			String title, String description, String content, String type,
			String structureId, String templateId, Date displayDateGT,
			Date displayDateLT, Boolean approved, Boolean expired,
			Date reviewDate, boolean andOperator)
		throws SystemException {

		return journalArticleFinder.countByC_G_A_V_T_D_C_T_S_T_D_A_E_R(
			companyId, groupId, articleId, version, title, description, content,
			type, structureId, templateId, displayDateGT, displayDateLT,
			approved, expired, reviewDate, andOperator);
	}

	public int searchCount(
			long companyId, long groupId, String articleId, Double version,
			String title, String description, String content, String type,
			String[] structureIds, String[] templateIds, Date displayDateGT,
			Date displayDateLT, Boolean approved, Boolean expired,
			Date reviewDate, boolean andOperator)
		throws SystemException {

		return journalArticleFinder.countByC_G_A_V_T_D_C_T_S_T_D_A_E_R(
			companyId, groupId, articleId, version, title, description, content,
			type, structureIds, templateIds, displayDateGT, displayDateLT,
			approved, expired, reviewDate, andOperator);
	}

	public JournalArticle updateArticle(
			long userId, long groupId, String articleId, double version,
			boolean incrementVersion, String title, String description,
			String content, String type, String structureId, String templateId,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallFile, Map<String, byte[]> images,
			String articleURL, PortletPreferences prefs, String[] tagsEntries)
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

		JournalArticle oldArticle = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		JournalArticle article = null;

		if (incrementVersion) {
			double latestVersion = getLatestVersion(groupId, articleId);

			long id = counterLocalService.increment();

			article = journalArticlePersistence.create(id);

			article.setResourcePrimKey(oldArticle.getResourcePrimKey());
			article.setGroupId(oldArticle.getGroupId());
			article.setCompanyId(user.getCompanyId());
			article.setUserId(user.getUserId());
			article.setUserName(user.getFullName());
			article.setCreateDate(now);
			article.setArticleId(articleId);
			article.setVersion(MathUtil.format(latestVersion + 0.1, 1, 1));
			article.setSmallImageId(oldArticle.getSmallImageId());
		}
		else {
			article = oldArticle;
		}

		content = format(
			groupId, articleId, article.getVersion(), incrementVersion, content,
			structureId, images);

		boolean approved = oldArticle.isApproved();

		if (incrementVersion) {
			approved = false;
		}

		article.setModifiedDate(now);
		article.setTitle(title);
		article.setDescription(description);
		article.setContent(content);
		article.setType(type);
		article.setStructureId(structureId);
		article.setTemplateId(templateId);
		article.setDisplayDate(displayDate);
		article.setApproved(approved);

		if ((expirationDate == null) || expirationDate.after(now)) {
			article.setExpired(false);
		}
		else {
			article.setExpired(true);
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

		// Small image

		saveImages(
			smallImage, article.getSmallImageId(), smallFile, smallBytes);

		// Tags

		updateTagsAsset(userId, article, tagsEntries);

		// Email

		if (incrementVersion) {
			try {
				sendEmail(article, articleURL, prefs, "requested");
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}

		// Lucene

		try {
			if (article.isIndexable()) {
				if (article.isApproved()) {
					Indexer.updateArticle(
						article.getCompanyId(), article.getGroupId(),
						article.getArticleId(), article.getVersion(),
						article.getTitle(), article.getDescription(),
						article.getContent(), article.getType(),
						article.getDisplayDate(), tagsEntries);
				}
				else {
					Indexer.deleteArticle(
						article.getCompanyId(), article.getArticleId());
				}
			}
		}
		catch (SearchException se) {
			_log.error("Indexing " + article.getPrimaryKey(), se);
		}

		return article;
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

	public void updateTagsAsset(
			long userId, JournalArticle article, String[] tagsEntries)
		throws PortalException, SystemException {

		// Get the earliest display date and latest expiration date among
		// all article versions

		Date[] dateInterval = getDateInterval(
			article.getGroupId(), article.getArticleId(),
			article.getDisplayDate(), article.getExpirationDate());

		Date displayDate = dateInterval[0];
		Date expirationDate = dateInterval[1];

		tagsAssetLocalService.updateAsset(
			userId, article.getGroupId(), JournalArticle.class.getName(),
			article.getResourcePrimKey(), tagsEntries, null, null,
			displayDate, expirationDate, ContentTypes.TEXT_HTML,
			article.getTitle(), article.getDescription(), null, null, 0, 0,
			null, false);
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

	protected void checkStructure(Document contentDoc, Element root)
		throws PortalException {

		for (Element el : root.elements()) {
			checkStructureField(el, contentDoc);

			checkStructure(contentDoc, el);
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
					oldImage = ImageLocalServiceUtil.getImage(imageId);
				}
				catch (NoSuchImageException nsie) {
					continue;
				}

				imageId = journalArticleImageLocalService.getArticleImageId(
					newArticle.getGroupId(), newArticle.getArticleId(),
					newArticle.getVersion(), name, languageId);

				ImageLocalServiceUtil.updateImage(
					imageId, oldImage.getTextObj());

				String elContent =
					"/image/journal/article?img_id=" + imageId + "&t=" +
						ImageServletTokenUtil.getToken(imageId);

				dynamicContentEl.setText(elContent);
				dynamicContentEl.addAttribute("id", String.valueOf(imageId));
			}
		}

		newArticle.setContent(contentDoc.formattedString());
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

	protected void format(
			long groupId, String articleId, double version,
			boolean incrementVersion, Element root, Map<String, byte[]> images)
		throws PortalException, SystemException {

		for (Element el : root.elements()) {
			String elName = el.attributeValue("name", StringPool.BLANK);
			String elType = el.attributeValue("type", StringPool.BLANK);

			if (elType.equals("image")) {
				formatImage(
					groupId, articleId, version, incrementVersion, el, elName,
					images);
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

	protected void formatImage(
			long groupId, String articleId, double version,
			boolean incrementVersion, Element el, String elName,
			Map<String, byte[]> images)
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
					groupId, articleId, version, elName, elLanguage);

			double oldVersion = MathUtil.format(version - 0.1, 1, 1);

			long oldImageId = 0;

			if ((oldVersion >= 1) && incrementVersion) {
				oldImageId =
					journalArticleImageLocalService.getArticleImageId(
						groupId, articleId, oldVersion, elName, elLanguage);
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
						groupId, articleId, version, elName, defaultElLanguage);

				imageLocalService.deleteImage(defaultImageId);

				continue;
			}

			byte[] bytes = images.get(elName + elLanguage);

			if (bytes != null && (bytes.length > 0)) {
				dynamicContent.setText(elContent);
				dynamicContent.addAttribute("id", String.valueOf(imageId));

				imageLocalService.updateImage(imageId, bytes);

				continue;
			}

			if ((version > JournalArticleImpl.DEFAULT_VERSION) &&
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
					groupId, articleId, version, elName, defaultElLanguage);

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

		List<JournalArticle> articles = journalArticlePersistence.findByG_A_A(
			groupId, articleId, true);

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
			JournalArticle article, String articleURL, PortletPreferences prefs,
			String emailType)
		throws IOException, PortalException, SystemException {

		if (prefs == null) {
			return;
		}
		else if (emailType.equals("denied") &&
			JournalUtil.getEmailArticleApprovalDeniedEnabled(prefs)) {
		}
		else if (emailType.equals("granted") &&
				 JournalUtil.getEmailArticleApprovalGrantedEnabled(prefs)) {
		}
		else if (emailType.equals("requested") &&
				 JournalUtil.getEmailArticleApprovalRequestedEnabled(prefs)) {
		}
		else if (emailType.equals("review") &&
				 JournalUtil.getEmailArticleReviewEnabled(prefs)) {
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

		String fromName = JournalUtil.getEmailFromName(prefs);
		String fromAddress = JournalUtil.getEmailFromAddress(prefs);

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
				JournalUtil.getEmailArticleApprovalDeniedSubject(prefs);
			body = JournalUtil.getEmailArticleApprovalDeniedBody(prefs);
		}
		else if (emailType.equals("granted")) {
			subject =
				JournalUtil.getEmailArticleApprovalGrantedSubject(prefs);
			body = JournalUtil.getEmailArticleApprovalGrantedBody(prefs);
		}
		else if (emailType.equals("requested")) {
			subject =
				JournalUtil.getEmailArticleApprovalRequestedSubject(prefs);
			body = JournalUtil.getEmailArticleApprovalRequestedBody(prefs);
		}
		else if (emailType.equals("review")) {
			subject = JournalUtil.getEmailArticleReviewSubject(prefs);
			body = JournalUtil.getEmailArticleReviewBody(prefs);
		}

		subject = StringUtil.replace(
			subject,
			new String[] {
				"[$ARTICLE_ID$]",
				"[$ARTICLE_TITLE$]",
				"[$ARTICLE_URL$]",
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

	protected void validate(String articleId) throws PortalException {
		if ((Validator.isNull(articleId)) ||
			(articleId.indexOf(StringPool.SPACE) != -1)) {

			throw new ArticleIdException();
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

		String[] imageExtensions =
			PropsUtil.getArray(PropsKeys.JOURNAL_IMAGE_EXTENSIONS);

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

			long smallImageMaxSize = GetterUtil.getLong(
				PropsUtil.get(PropsKeys.JOURNAL_IMAGE_SMALL_MAX_SIZE));

			if ((smallImageMaxSize > 0) &&
				((smallBytes == null) ||
					(smallBytes.length > smallImageMaxSize))) {

				throw new ArticleSmallImageSizeException();
			}
		}
	}

	private static final String _TOKEN_PAGE_BREAK = PropsUtil.get(
		PropsKeys.JOURNAL_ARTICLE_TOKEN_PAGE_BREAK);

	private static Log _log =
		LogFactory.getLog(JournalArticleLocalServiceImpl.class);

}