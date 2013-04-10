/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.lar.DLPortletDataHandler;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.lar.DDMPortletDataHandler;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMTemplateUtil;
import com.liferay.portlet.dynamicdatamapping.util.comparator.StructureIdComparator;
import com.liferay.portlet.journal.ArticleContentException;
import com.liferay.portlet.journal.FeedTargetLayoutFriendlyUrlException;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleImageUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleResourceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.service.persistence.JournalFeedUtil;
import com.liferay.portlet.journal.service.persistence.JournalFolderUtil;
import com.liferay.portlet.journal.util.comparator.ArticleIDComparator;

import java.io.File;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;

/**
 * <p>
 * Provides the Journal portlet export and import functionality, which is to
 * clone all articles, structures, and templates associated with the layout's
 * group. Upon import, new instances of the corresponding articles, structures,
 * and templates are created or updated according to the DATA_MIRROW strategy
 * The author of the newly created objects are determined by the
 * JournalCreationStrategy class defined in <i>portal.properties</i>. That
 * strategy also allows the text of the journal article to be modified prior to
 * import.
 * </p>
 *
 * <p>
 * This <code>PortletDataHandler</code> differs from
 * <code>JournalContentPortletDataHandlerImpl</code> in that it exports all
 * articles owned by the group whether or not they are actually displayed in a
 * portlet in the layout set.
 * </p>
 *
 * @author Raymond Augé
 * @author Joel Kozikowski
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Karthik Sudarshan
 * @author Wesley Gong
 * @author Hugo Huijser
 * @see    com.liferay.portal.kernel.lar.PortletDataHandler
 * @see    com.liferay.portlet.journal.lar.JournalContentPortletDataHandler
 * @see    com.liferay.portlet.journal.lar.JournalCreationStrategy
 */
public class JournalPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "journal";

	public static void exportArticle(
			PortletDataContext portletDataContext, Element articlesElement,
			Element ddmStructuresElement, Element ddmTemplatesElement,
			Element dlFileEntryTypesElement, Element dlFoldersElement,
			Element dlFileEntriesElement, Element dlFileRanksElement,
			Element dlRepositoriesElement, Element dlRepositoryEntriesElement,
			JournalArticle article, boolean checkDateRange)
		throws Exception {

		if (checkDateRange &&
			!portletDataContext.isWithinDateRange(article.getModifiedDate())) {

			return;
		}

		if ((article.getStatus() != WorkflowConstants.STATUS_APPROVED) &&
			(article.getStatus() != WorkflowConstants.STATUS_EXPIRED)) {

			return;
		}

		String path = getArticlePath(portletDataContext, article);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		// Clone this article to make sure changes to its content are never
		// persisted

		article = (JournalArticle)article.clone();

		Element articleElement = (Element)articlesElement.selectSingleNode(
			"//article[@path='".concat(path).concat("']"));

		if (articleElement == null) {
			articleElement = articlesElement.addElement("article");
		}

		articleElement.addAttribute(
			"article-resource-uuid", article.getArticleResourceUuid());

		if (Validator.isNotNull(article.getStructureId())) {
			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.getStructure(
					article.getGroupId(),
					PortalUtil.getClassNameId(JournalArticle.class),
					article.getStructureId(), true);

			articleElement.addAttribute(
				"ddm-structure-uuid", ddmStructure.getUuid());

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, ddmStructure);
		}

		if (Validator.isNotNull(article.getTemplateId())) {
			DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
				article.getGroupId(),
				PortalUtil.getClassNameId(DDMStructure.class),
				article.getTemplateId(), true);

			articleElement.addAttribute(
				"ddm-template-uuid", ddmTemplate.getUuid());

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, ddmTemplate);
		}

		if (article.isSmallImage()) {
			Image smallImage = ImageUtil.fetchByPrimaryKey(
				article.getSmallImageId());

			if (Validator.isNotNull(article.getSmallImageURL())) {
				String smallImageURL =
					DDMPortletDataHandler.exportReferencedContent(
						portletDataContext, dlFileEntryTypesElement,
						dlFoldersElement, dlFileEntriesElement,
						dlFileRanksElement, dlRepositoriesElement,
						dlRepositoryEntriesElement, articleElement,
						article.getSmallImageURL().concat(StringPool.SPACE));

				article.setSmallImageURL(smallImageURL);
			}
			else if (smallImage != null) {
				String smallImagePath = getArticleSmallImagePath(
					portletDataContext, article);

				articleElement.addAttribute("small-image-path", smallImagePath);

				article.setSmallImageType(smallImage.getType());

				portletDataContext.addZipEntry(
					smallImagePath, smallImage.getTextObj());
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "images")) {
			String imagePath = getArticleImagePath(portletDataContext, article);

			articleElement.addAttribute("image-path", imagePath);

			List<JournalArticleImage> articleImages =
				JournalArticleImageUtil.findByG_A_V(
					article.getGroupId(), article.getArticleId(),
					article.getVersion());

			for (JournalArticleImage articleImage : articleImages) {
				Image image = null;

				try {
					image = ImageUtil.findByPrimaryKey(
						articleImage.getArticleImageId());
				}
				catch (NoSuchImageException nsie) {
					continue;
				}

				if (image.getTextObj() == null) {
					continue;
				}

				String articleImagePath = getArticleImagePath(
					portletDataContext, article, articleImage, image);

				if (!portletDataContext.isPathNotProcessed(articleImagePath)) {
					continue;
				}

				portletDataContext.addZipEntry(
					articleImagePath, image.getTextObj());
			}
		}

		article.setStatusByUserUuid(article.getStatusByUserUuid());

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "embedded-assets")) {

			String content = DDMPortletDataHandler.exportReferencedContent(
				portletDataContext, dlFileEntryTypesElement, dlFoldersElement,
				dlFileEntriesElement, dlFileRanksElement, dlRepositoriesElement,
				dlRepositoryEntriesElement, articleElement,
				article.getContent());

			article.setContent(content);
		}

		portletDataContext.addClassedModel(
			articleElement, path, article, NAMESPACE);
	}

	public static String getArticlePath(
			PortletDataContext portletDataContext, JournalArticle article)
		throws Exception {

		StringBundler sb = new StringBundler(8);

		sb.append(
			ExportImportPathUtil.getPortletPath(
				portletDataContext, PortletKeys.JOURNAL));
		sb.append("/articles/");
		sb.append(article.getArticleResourceUuid());
		sb.append(StringPool.SLASH);
		sb.append(article.getVersion());
		sb.append(StringPool.SLASH);
		sb.append("article.xml");

		return sb.toString();
	}

	public static void importArticle(
			PortletDataContext portletDataContext, Element articleElement)
		throws Exception {

		String path = articleElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		JournalArticle article =
			(JournalArticle)portletDataContext.getZipEntryAsObject(
				articleElement, path);

		prepareLanguagesForImport(article);

		long userId = portletDataContext.getUserId(article.getUserUuid());

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(
			portletDataContext, article);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		User user = UserLocalServiceUtil.getUser(userId);

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				JournalFolder.class);

		long folderId = MapUtil.getLong(
			folderIds, article.getFolderId(), article.getFolderId());

		if ((folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId == article.getFolderId())) {

			String folderPath = getImportFolderPath(
				portletDataContext, folderId);

			JournalFolder folder =
				(JournalFolder)portletDataContext.getZipEntryAsObject(
					folderPath);

			importFolder(portletDataContext, folderPath, folder);

			folderId = MapUtil.getLong(
				folderIds, article.getFolderId(), article.getFolderId());
		}

		String articleId = article.getArticleId();
		boolean autoArticleId = false;

		if (Validator.isNumber(articleId) ||
			(JournalArticleUtil.fetchByG_A_V(
				portletDataContext.getScopeGroupId(), articleId,
				JournalArticleConstants.VERSION_DEFAULT) != null)) {

			autoArticleId = true;
		}

		Map<String, String> articleIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class + ".articleId");

		String newArticleId = articleIds.get(articleId);

		if (Validator.isNotNull(newArticleId)) {

			// A sibling of a different version was already assigned a new
			// article id

			articleId = newArticleId;
			autoArticleId = false;
		}

		String content = article.getContent();

		content = importReferencedContent(
			portletDataContext, articleElement, content);

		article.setContent(content);

		String newContent = creationStrategy.getTransformedContent(
			portletDataContext, article);

		if (newContent != JournalCreationStrategy.ARTICLE_CONTENT_UNCHANGED) {
			article.setContent(newContent);
		}

		Map<String, String> ddmStructureKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		String parentDDMStructureKey = MapUtil.getString(
			ddmStructureKeys, article.getStructureId(),
			article.getStructureId());

		Map<String, String> ddmTemplateKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class);

		String parentDDMTemplateKey = MapUtil.getString(
			ddmTemplateKeys, article.getTemplateId(), article.getTemplateId());

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

		long ddmStructureId = 0;

		if (Validator.isNotNull(article.getStructureId())) {
			String ddmStructureUuid = articleElement.attributeValue(
				"ddm-structure-uuid");

			DDMStructure existingDDMStructure = DDMStructureUtil.fetchByUUID_G(
				ddmStructureUuid, portletDataContext.getScopeGroupId());

			if (existingDDMStructure == null) {
				Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
					portletDataContext.getCompanyId());

				long companyGroupId = companyGroup.getGroupId();

				existingDDMStructure = DDMStructureUtil.fetchByUUID_G(
					ddmStructureUuid, companyGroupId);
			}

			if (existingDDMStructure == null) {
				String newStructureId = ddmStructureKeys.get(
					article.getStructureId());

				if (Validator.isNotNull(newStructureId)) {
					existingDDMStructure = DDMStructureUtil.fetchByG_C_S(
						portletDataContext.getScopeGroupId(),
						PortalUtil.getClassNameId(JournalArticle.class),
						String.valueOf(newStructureId));
				}

				if (existingDDMStructure == null) {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler();

						sb.append("Structure ");
						sb.append(article.getStructureId());
						sb.append(" is missing for article ");
						sb.append(article.getArticleId());
						sb.append(", skipping this article.");

						_log.warn(sb.toString());
					}

					return;
				}
			}

			ddmStructureId = existingDDMStructure.getStructureId();

			parentDDMStructureKey = existingDDMStructure.getStructureKey();
		}

		if (Validator.isNotNull(article.getTemplateId())) {
			String ddmTemplateUuid = articleElement.attributeValue(
				"ddm-template-uuid");

			DDMTemplate existingDDMTemplate = DDMTemplateUtil.fetchByUUID_G(
				ddmTemplateUuid, portletDataContext.getScopeGroupId());

			if (existingDDMTemplate == null) {
				Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
					portletDataContext.getCompanyId());

				long companyGroupId = companyGroup.getGroupId();

				existingDDMTemplate = DDMTemplateUtil.fetchByUUID_G(
					ddmTemplateUuid, companyGroupId);
			}

			if (existingDDMTemplate == null) {
				String newTemplateId = ddmTemplateKeys.get(
					article.getTemplateId());

				if (Validator.isNotNull(newTemplateId)) {
					existingDDMTemplate = DDMTemplateUtil.fetchByG_C_T(
						portletDataContext.getScopeGroupId(),
						PortalUtil.getClassNameId(DDMStructure.class),
						newTemplateId);
				}

				if (existingDDMTemplate == null) {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler();

						sb.append("Template ");
						sb.append(article.getTemplateId());
						sb.append(" is missing for article ");
						sb.append(article.getArticleId());
						sb.append(", skipping this article.");

						_log.warn(sb.toString());
					}

					return;
				}
			}

			parentDDMTemplateKey = existingDDMTemplate.getTemplateKey();
		}

		File smallFile = null;

		if (article.isSmallImage()) {
			String smallImagePath = articleElement.attributeValue(
				"small-image-path");

			if (Validator.isNotNull(article.getSmallImageURL())) {
				String smallImageURL = importReferencedContent(
					portletDataContext, articleElement,
					article.getSmallImageURL());

				article.setSmallImageURL(smallImageURL);
			}
			else if (Validator.isNotNull(smallImagePath)) {
				byte[] bytes = portletDataContext.getZipEntryAsByteArray(
					smallImagePath);

				if (bytes != null) {
					smallFile = FileUtil.createTempFile(
						article.getSmallImageType());

					FileUtil.write(smallFile, bytes);
				}
			}
		}

		Map<String, byte[]> images = new HashMap<String, byte[]>();

		String imagePath = articleElement.attributeValue("image-path");

		if (portletDataContext.getBooleanParameter(NAMESPACE, "images") &&
			Validator.isNotNull(imagePath)) {

			List<String> imageFiles = portletDataContext.getZipFolderEntries(
				imagePath);

			for (String imageFile : imageFiles) {
				String fileName = imageFile;

				if (fileName.contains(StringPool.SLASH)) {
					fileName = fileName.substring(
						fileName.lastIndexOf(CharPool.SLASH) + 1);
				}

				if (fileName.endsWith(".xml")) {
					continue;
				}

				int pos = fileName.lastIndexOf(CharPool.PERIOD);

				if (pos != -1) {
					fileName = fileName.substring(0, pos);
				}

				images.put(
					fileName,
					portletDataContext.getZipEntryAsByteArray(imageFile));
			}
		}

		String articleURL = null;

		boolean addGroupPermissions = creationStrategy.addGroupPermissions(
			portletDataContext, article);
		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
			portletDataContext, article);

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			articleElement, article, NAMESPACE);

		serviceContext.setAddGroupPermissions(addGroupPermissions);
		serviceContext.setAddGuestPermissions(addGuestPermissions);

		if (article.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		JournalArticle importedArticle = null;

		String articleResourceUuid = articleElement.attributeValue(
			"article-resource-uuid");

		if (portletDataContext.isDataStrategyMirror()) {
			JournalArticleResource articleResource =
				JournalArticleResourceUtil.fetchByUUID_G(
					articleResourceUuid, portletDataContext.getScopeGroupId());

			if (articleResource == null) {
				Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
					portletDataContext.getCompanyId());

				long companyGroupId = companyGroup.getGroupId();

				articleResource = JournalArticleResourceUtil.fetchByUUID_G(
					articleResourceUuid, companyGroupId);
			}

			serviceContext.setUuid(articleResourceUuid);
			serviceContext.setAttribute("urlTitle", article.getUrlTitle());

			JournalArticle existingArticle = null;

			if (articleResource != null) {
				try {
					existingArticle =
						JournalArticleLocalServiceUtil.getLatestArticle(
							articleResource.getResourcePrimKey(),
							WorkflowConstants.STATUS_ANY, false);
				}
				catch (NoSuchArticleException nsae) {
				}
			}

			if (existingArticle == null) {
				existingArticle = JournalArticleUtil.fetchByG_A_V(
					portletDataContext.getScopeGroupId(), newArticleId,
					article.getVersion());
			}

			if (existingArticle == null) {
				importedArticle = JournalArticleLocalServiceUtil.addArticle(
					userId, portletDataContext.getScopeGroupId(), folderId,
					article.getClassNameId(), ddmStructureId, articleId,
					autoArticleId, article.getVersion(), article.getTitleMap(),
					article.getDescriptionMap(), article.getContent(),
					article.getType(), parentDDMStructureKey,
					parentDDMTemplateKey, article.getLayoutUuid(),
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, reviewDateMonth,
					reviewDateDay, reviewDateYear, reviewDateHour,
					reviewDateMinute, neverReview, article.isIndexable(),
					article.isSmallImage(), article.getSmallImageURL(),
					smallFile, images, articleURL, serviceContext);
			}
			else {
				importedArticle = JournalArticleLocalServiceUtil.updateArticle(
					userId, existingArticle.getGroupId(), folderId,
					existingArticle.getArticleId(), article.getVersion(),
					article.getTitleMap(), article.getDescriptionMap(),
					article.getContent(), article.getType(),
					parentDDMStructureKey, parentDDMTemplateKey,
					article.getLayoutUuid(), displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					reviewDateMonth, reviewDateDay, reviewDateYear,
					reviewDateHour, reviewDateMinute, neverReview,
					article.isIndexable(), article.isSmallImage(),
					article.getSmallImageURL(), smallFile, images, articleURL,
					serviceContext);
			}
		}
		else {
			importedArticle = JournalArticleLocalServiceUtil.addArticle(
				userId, portletDataContext.getScopeGroupId(), folderId,
				article.getClassNameId(), ddmStructureId, articleId,
				autoArticleId, article.getVersion(), article.getTitleMap(),
				article.getDescriptionMap(), article.getContent(),
				article.getType(), parentDDMStructureKey, parentDDMTemplateKey,
				article.getLayoutUuid(), displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, neverReview, article.isIndexable(),
				article.isSmallImage(), article.getSmallImageURL(), smallFile,
				images, articleURL, serviceContext);
		}

		if (smallFile != null) {
			smallFile.delete();
		}

		portletDataContext.importClassedModel(
			article, importedArticle, NAMESPACE);

		if (Validator.isNull(newArticleId)) {
			articleIds.put(
				article.getArticleId(), importedArticle.getArticleId());
		}

		if (!articleId.equals(importedArticle.getArticleId())) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"An article with the ID " + articleId + " already " +
						"exists. The new generated ID is " +
							importedArticle.getArticleId());
			}
		}
	}

	public static void importFeed(
			PortletDataContext portletDataContext, Element feedElement)
		throws Exception {

		String path = feedElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		JournalFeed feed = (JournalFeed)portletDataContext.getZipEntryAsObject(
			path);

		long userId = portletDataContext.getUserId(feed.getUserUuid());

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(
			portletDataContext, feed);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		String newGroupFriendlyURL = group.getFriendlyURL().substring(1);

		String[] friendlyUrlParts = StringUtil.split(
			feed.getTargetLayoutFriendlyUrl(), '/');

		String oldGroupFriendlyURL = friendlyUrlParts[2];

		if (oldGroupFriendlyURL.equals("@data_handler_group_friendly_url@")) {
			feed.setTargetLayoutFriendlyUrl(
				StringUtil.replace(
					feed.getTargetLayoutFriendlyUrl(),
					"@data_handler_group_friendly_url@", newGroupFriendlyURL));
		}

		String feedId = feed.getFeedId();
		boolean autoFeedId = false;

		if (Validator.isNumber(feedId) ||
			(JournalFeedUtil.fetchByG_F(
				portletDataContext.getScopeGroupId(), feedId) != null)) {

			autoFeedId = true;
		}

		Map<String, String> ddmStructureKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class + ".structureKey");

		String parentDDMStructureKey = MapUtil.getString(
			ddmStructureKeys, feed.getStructureId(), feed.getStructureId());

		Map<String, String> ddmTemplateKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class + ".templateKey");

		String parentDDMTemplateKey = MapUtil.getString(
			ddmTemplateKeys, feed.getTemplateId(), feed.getTemplateId());
		String parentRenderDDMTemplateKey = MapUtil.getString(
			ddmTemplateKeys, feed.getRendererTemplateId(),
			feed.getRendererTemplateId());

		boolean addGroupPermissions = creationStrategy.addGroupPermissions(
			portletDataContext, feed);
		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
			portletDataContext, feed);

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			feedElement, feed, NAMESPACE);

		serviceContext.setAddGroupPermissions(addGroupPermissions);
		serviceContext.setAddGuestPermissions(addGuestPermissions);

		JournalFeed importedFeed = null;

		try {
			if (portletDataContext.isDataStrategyMirror()) {
				JournalFeed existingFeed = JournalFeedUtil.fetchByUUID_G(
					feed.getUuid(), portletDataContext.getScopeGroupId());

				if (existingFeed == null) {
					serviceContext.setUuid(feed.getUuid());

					importedFeed = JournalFeedLocalServiceUtil.addFeed(
						userId, portletDataContext.getScopeGroupId(), feedId,
						autoFeedId, feed.getName(), feed.getDescription(),
						feed.getType(), parentDDMStructureKey,
						parentDDMTemplateKey, parentRenderDDMTemplateKey,
						feed.getDelta(), feed.getOrderByCol(),
						feed.getOrderByType(),
						feed.getTargetLayoutFriendlyUrl(),
						feed.getTargetPortletId(), feed.getContentField(),
						feed.getFeedFormat(), feed.getFeedVersion(),
						serviceContext);
				}
				else {
					importedFeed = JournalFeedLocalServiceUtil.updateFeed(
						existingFeed.getGroupId(), existingFeed.getFeedId(),
						feed.getName(), feed.getDescription(), feed.getType(),
						parentDDMStructureKey, parentDDMTemplateKey,
						parentRenderDDMTemplateKey, feed.getDelta(),
						feed.getOrderByCol(), feed.getOrderByType(),
						feed.getTargetLayoutFriendlyUrl(),
						feed.getTargetPortletId(), feed.getContentField(),
						feed.getFeedFormat(), feed.getFeedVersion(),
						serviceContext);
				}
			}
			else {
				importedFeed = JournalFeedLocalServiceUtil.addFeed(
					userId, portletDataContext.getScopeGroupId(), feedId,
					autoFeedId, feed.getName(), feed.getDescription(),
					feed.getType(), parentDDMStructureKey, parentDDMTemplateKey,
					parentRenderDDMTemplateKey, feed.getDelta(),
					feed.getOrderByCol(), feed.getOrderByType(),
					feed.getTargetLayoutFriendlyUrl(),
					feed.getTargetPortletId(), feed.getContentField(),
					feed.getFeedFormat(), feed.getFeedVersion(),
					serviceContext);
			}

			portletDataContext.importClassedModel(
				feed, importedFeed, NAMESPACE);

			if (!feedId.equals(importedFeed.getFeedId())) {
				if (_log.isWarnEnabled()) {
					StringBundler sb = new StringBundler(5);

					sb.append("A feed with the ID ");
					sb.append(feedId);
					sb.append(" already exists. The new generated ID is ");
					sb.append(importedFeed.getFeedId());
					sb.append(".");

					_log.warn(sb.toString());
				}
			}
		}
		catch (FeedTargetLayoutFriendlyUrlException ftlfurle) {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(6);

				sb.append("A feed with the ID ");
				sb.append(feedId);
				sb.append(" cannot be imported because layout with friendly ");
				sb.append("URL ");
				sb.append(feed.getTargetLayoutFriendlyUrl());
				sb.append(" does not exist");

				_log.warn(sb.toString());
			}
		}
	}

	public static String importReferencedContent(
			PortletDataContext portletDataContext, Element parentElement,
			String content)
		throws Exception {

		content = importDLFileEntries(
			portletDataContext, parentElement, content);
		content = importLayoutFriendlyURLs(portletDataContext, content);
		content = importLinksToLayout(portletDataContext, content);

		return content;
	}

	public static void importReferencedData(
			PortletDataContext portletDataContext, Element entityElement)
		throws Exception {

		Element dlRepositoriesElement = entityElement.element(
			"dl-repositories");

		List<Element> dlRepositoryElements = Collections.emptyList();

		if (dlRepositoriesElement != null) {
			dlRepositoryElements = dlRepositoriesElement.elements("repository");
		}

		for (Element repositoryElement : dlRepositoryElements) {
			DLPortletDataHandler.importRepository(
				portletDataContext, repositoryElement);
		}

		Element dlRepositoryEntriesElement = entityElement.element(
			"dl-repository-entries");

		List<Element> dlRepositoryEntryElements = Collections.emptyList();

		if (dlRepositoryEntriesElement != null) {
			dlRepositoryEntryElements = dlRepositoryEntriesElement.elements(
				"repository-entry");
		}

		for (Element repositoryEntryElement : dlRepositoryEntryElements) {
			DLPortletDataHandler.importRepositoryEntry(
				portletDataContext, repositoryEntryElement);
		}

		Element dlFoldersElement = entityElement.element("dl-folders");

		List<Element> dlFolderElements = Collections.emptyList();

		if (dlFoldersElement != null) {
			dlFolderElements = dlFoldersElement.elements("folder");
		}

		for (Element folderElement : dlFolderElements) {
			DLPortletDataHandler.importFolder(
				portletDataContext, folderElement);
		}

		Element dlFileEntriesElement = entityElement.element("dl-file-entries");

		List<Element> dlFileEntryElements = Collections.emptyList();

		if (dlFileEntriesElement != null) {
			dlFileEntryElements = dlFileEntriesElement.elements("file-entry");
		}

		for (Element fileEntryElement : dlFileEntryElements) {
			DLPortletDataHandler.importFileEntry(
				portletDataContext, fileEntryElement);
		}

		Element dlFileRanksElement = entityElement.element("dl-file-ranks");

		List<Element> dlFileRankElements = Collections.emptyList();

		if (dlFileRanksElement != null) {
			dlFileRankElements = dlFileRanksElement.elements("file-rank");
		}

		for (Element fileRankElement : dlFileRankElements) {
			DLPortletDataHandler.importFileRank(
				portletDataContext, fileRankElement);
		}
	}

	public JournalPortletDataHandler() {
		setAlwaysExportable(true);
		setDataLocalized(true);
		setExportControls(
			new PortletDataHandlerBoolean(NAMESPACE, "web-content"),
			new PortletDataHandlerBoolean(
				NAMESPACE, "structures-templates-and-feeds", true, true),
			new PortletDataHandlerBoolean(NAMESPACE, "embedded-assets"),
			new PortletDataHandlerBoolean(
				NAMESPACE, "version-history",
				PropsValues.JOURNAL_PUBLISH_VERSION_HISTORY_BY_DEFAULT));
		setExportMetadataControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "web-content", true,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(NAMESPACE, "images"),
					new PortletDataHandlerBoolean(NAMESPACE, "categories"),
					new PortletDataHandlerBoolean(NAMESPACE, "comments"),
					new PortletDataHandlerBoolean(NAMESPACE, "ratings"),
					new PortletDataHandlerBoolean(NAMESPACE, "tags")
				}));
		setImportControls(getExportControls()[0], getExportControls()[1]);
		setPublishToLiveByDefault(
			PropsValues.JOURNAL_PUBLISH_TO_LIVE_BY_DEFAULT);
	}

	protected static void exportFeed(
			PortletDataContext portletDataContext, Element feedsElement,
			JournalFeed feed)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(feed.getModifiedDate())) {
			return;
		}

		String path = getFeedPath(portletDataContext, feed);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		feed = (JournalFeed)feed.clone();

		Element feedElement = feedsElement.addElement("feed");

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		String newGroupFriendlyURL = group.getFriendlyURL().substring(1);

		String[] friendlyUrlParts = StringUtil.split(
			feed.getTargetLayoutFriendlyUrl(), '/');

		String oldGroupFriendlyURL = friendlyUrlParts[2];

		if (newGroupFriendlyURL.equals(oldGroupFriendlyURL)) {
			String targetLayoutFriendlyUrl = StringUtil.replaceFirst(
				feed.getTargetLayoutFriendlyUrl(),
				StringPool.SLASH + newGroupFriendlyURL + StringPool.SLASH,
				"/@data_handler_group_friendly_url@/");

			feed.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
		}

		portletDataContext.addClassedModel(feedElement, path, feed, NAMESPACE);
	}

	protected static void exportFolder(
			PortletDataContext portletDataContext, Element foldersElement,
			Element articlesElement, Element ddmStructuresElement,
			Element ddmTemplatesElement, Element dlFileEntryTypesElement,
			Element dlFoldersElement, Element dlFileEntriesElement,
			Element dlFileRanksElement, Element dlRepositoriesElement,
			Element dlRepositoryEntriesElement, JournalFolder folder,
			boolean checkDateRange)
		throws Exception {

		if (checkDateRange &&
			!portletDataContext.isWithinDateRange(folder.getModifiedDate())) {

			return;
		}

		exportParentFolder(
			portletDataContext, foldersElement, folder.getParentFolderId());

		String path = getFolderPath(portletDataContext, folder);

		if (portletDataContext.isPathNotProcessed(path)) {
			Element folderElement = foldersElement.addElement("folder");

			portletDataContext.addClassedModel(
				folderElement, path, folder, NAMESPACE);
		}

		List<JournalArticle> articles = JournalArticleUtil.findByG_F(
			folder.getGroupId(), folder.getFolderId());

		for (JournalArticle article : articles) {
			exportArticle(
				portletDataContext, articlesElement, ddmStructuresElement,
				ddmTemplatesElement, dlFileEntryTypesElement, dlFoldersElement,
				dlFileEntriesElement, dlFileRanksElement, dlRepositoriesElement,
				dlRepositoryEntriesElement, article, true);
		}
	}

	protected static void exportParentFolder(
			PortletDataContext portletDataContext, Element foldersElement,
			long folderId)
		throws Exception {

		if (folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		JournalFolder folder = JournalFolderUtil.findByPrimaryKey(folderId);

		exportParentFolder(
			portletDataContext, foldersElement, folder.getParentFolderId());

		String path = getFolderPath(portletDataContext, folder);

		if (portletDataContext.isPathNotProcessed(path)) {
			Element folderElement = foldersElement.addElement("folder");

			portletDataContext.addClassedModel(
				folderElement, path, folder, NAMESPACE);
		}
	}

	protected static String getArticleImagePath(
			PortletDataContext portletDataContext, JournalArticle article)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append(
			ExportImportPathUtil.getPortletPath(
				portletDataContext, PortletKeys.JOURNAL));
		sb.append("/articles/");
		sb.append(article.getArticleResourceUuid());
		sb.append(StringPool.SLASH);
		sb.append(article.getVersion());
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected static String getArticleImagePath(
			PortletDataContext portletDataContext, JournalArticle article,
			JournalArticleImage articleImage, Image image)
		throws Exception {

		StringBundler sb = new StringBundler(12);

		sb.append(
			ExportImportPathUtil.getPortletPath(
				portletDataContext, PortletKeys.JOURNAL));
		sb.append("/articles/");
		sb.append(article.getArticleResourceUuid());
		sb.append(StringPool.SLASH);
		sb.append(article.getVersion());
		sb.append(StringPool.SLASH);
		sb.append(articleImage.getElInstanceId());
		sb.append(StringPool.UNDERLINE);
		sb.append(articleImage.getElName());

		if (Validator.isNotNull(articleImage.getLanguageId())) {
			sb.append(articleImage.getLanguageId());
		}

		sb.append(StringPool.PERIOD);
		sb.append(image.getType());

		return sb.toString();
	}

	protected static String getArticleSmallImagePath(
			PortletDataContext portletDataContext, JournalArticle article)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append(
			ExportImportPathUtil.getPortletPath(
				portletDataContext, PortletKeys.JOURNAL));
		sb.append("/articles/");
		sb.append(article.getArticleResourceUuid());
		sb.append("/thumbnail");
		sb.append(StringPool.PERIOD);
		sb.append(article.getSmallImageType());

		return sb.toString();
	}

	protected static String getDDMStructurePath(
		PortletDataContext portletDataContext, String uuid) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			ExportImportPathUtil.getPortletPath(
				portletDataContext, PortletKeys.JOURNAL));
		sb.append("/ddm-structures/");
		sb.append(uuid);
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getDDMTemplatePath(
		PortletDataContext portletDataContext, DDMTemplate ddmTemplate) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			ExportImportPathUtil.getPortletPath(
				portletDataContext, PortletKeys.JOURNAL));
		sb.append("/ddm-templates/");
		sb.append(ddmTemplate.getUuid());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getFeedPath(
		PortletDataContext portletDataContext, JournalFeed feed) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			ExportImportPathUtil.getPortletPath(
				portletDataContext, PortletKeys.JOURNAL));
		sb.append("/feeds/");
		sb.append(feed.getUuid());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getFolderPath(
		PortletDataContext portletDataContext, JournalFolder folder) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			ExportImportPathUtil.getPortletPath(
				portletDataContext, PortletKeys.JOURNAL));
		sb.append("/folders/");
		sb.append(folder.getFolderId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getImportFolderPath(
		PortletDataContext portletDataContext, long folderId) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			ExportImportPathUtil.getPortletPath(
				portletDataContext, PortletKeys.JOURNAL));
		sb.append("/folders/");
		sb.append(folderId);
		sb.append(".xml");

		return sb.toString();
	}

	protected static String importDLFileEntries(
			PortletDataContext portletDataContext, Element parentElement,
			String content)
		throws Exception {

		List<Element> dlReferenceElements = parentElement.elements(
			"dl-reference");

		for (Element dlReferenceElement : dlReferenceElements) {
			String dlReferencePath = dlReferenceElement.attributeValue("path");

			String fileEntryUUID = null;

			try {
				Object zipEntryObject = portletDataContext.getZipEntryAsObject(
					dlReferencePath);

				if (zipEntryObject == null) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to reference " + dlReferencePath);
					}

					continue;
				}

				boolean defaultRepository = GetterUtil.getBoolean(
					dlReferenceElement.attributeValue("default-repository"));

				if (defaultRepository) {
					FileEntry fileEntry = (FileEntry)zipEntryObject;

					fileEntryUUID = fileEntry.getUuid();
				}
				else {
					RepositoryEntry repositoryEntry =
						(RepositoryEntry)zipEntryObject;

					fileEntryUUID = repositoryEntry.getUuid();
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(e.getMessage());
				}
			}

			if (fileEntryUUID == null) {
				continue;
			}

			FileEntry fileEntry =
				DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
					fileEntryUUID, portletDataContext.getScopeGroupId());

			if (fileEntry == null) {
				continue;
			}

			String dlReference = "[$dl-reference=" + dlReferencePath + "$]";

			String url = DLUtil.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
				false, false);

			content = StringUtil.replace(content, dlReference, url);
		}

		return content;
	}

	protected static void importFolder(
			PortletDataContext portletDataContext, Element folderElement)
		throws Exception {

		String path = folderElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		JournalFolder folder =
			(JournalFolder)portletDataContext.getZipEntryAsObject(path);

		importFolder(portletDataContext, path, folder);
	}

	protected static void importFolder(
			PortletDataContext portletDataContext, String folderPath,
			JournalFolder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				JournalFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderIds, folder.getParentFolderId(), folder.getParentFolderId());

		if ((parentFolderId !=
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(parentFolderId == folder.getParentFolderId())) {

			String path = getImportFolderPath(
				portletDataContext, parentFolderId);

			JournalFolder parentFolder =
				(JournalFolder)portletDataContext.getZipEntryAsObject(path);

			importFolder(portletDataContext, path, parentFolder);

			parentFolderId = MapUtil.getLong(
				folderIds, folder.getParentFolderId(),
				folder.getParentFolderId());
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			folderPath, folder, NAMESPACE);

		JournalFolder importedFolder = null;

		long groupId = portletDataContext.getScopeGroupId();

		if (portletDataContext.isDataStrategyMirror()) {
			JournalFolder existingFolder = JournalFolderUtil.fetchByUUID_G(
				folder.getUuid(), groupId);

			if (existingFolder == null) {
				serviceContext.setUuid(folder.getUuid());

				importedFolder = JournalFolderLocalServiceUtil.addFolder(
					userId, groupId, parentFolderId, folder.getName(),
					folder.getDescription(), serviceContext);
			}
			else {
				importedFolder = JournalFolderLocalServiceUtil.updateFolder(
					userId, existingFolder.getFolderId(), parentFolderId,
					folder.getName(), folder.getDescription(), false,
					serviceContext);
			}
		}
		else {
			importedFolder = JournalFolderLocalServiceUtil.addFolder(
				userId, groupId, parentFolderId, folder.getName(),
				folder.getDescription(), serviceContext);
		}

		portletDataContext.importClassedModel(
			folder, importedFolder, NAMESPACE);
	}

	protected static String importLayoutFriendlyURLs(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		String privateGroupServletMapping =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;
		String privateUserServletMapping =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;
		String publicServletMapping =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

		String portalContextPath = PortalUtil.getPathContext();

		if (Validator.isNotNull(portalContextPath)) {
			privateGroupServletMapping = portalContextPath.concat(
				privateGroupServletMapping);
			privateUserServletMapping = portalContextPath.concat(
				privateUserServletMapping);
			publicServletMapping = portalContextPath.concat(
				publicServletMapping);
		}

		content = StringUtil.replace(
			content, "@data_handler_private_group_servlet_mapping@",
			privateGroupServletMapping);
		content = StringUtil.replace(
			content, "@data_handler_private_user_servlet_mapping@",
			privateUserServletMapping);
		content = StringUtil.replace(
			content, "@data_handler_public_servlet_mapping@",
			publicServletMapping);

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		content = StringUtil.replace(
			content, "@data_handler_group_friendly_url@",
			group.getFriendlyURL());

		return content;
	}

	protected static String importLinksToLayout(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		List<String> oldLinksToLayout = new ArrayList<String>();
		List<String> newLinksToLayout = new ArrayList<String>();

		Matcher matcher = _importLinksToLayoutPattern.matcher(content);

		while (matcher.find()) {
			long oldLayoutId = GetterUtil.getLong(matcher.group(1));

			long newLayoutId = oldLayoutId;

			String type = matcher.group(2);

			boolean privateLayout = type.startsWith("private");

			String layoutUuid = matcher.group(3);

			String friendlyURL = matcher.group(4);

			try {
				Layout layout = LayoutUtil.fetchByUUID_G_P(
					layoutUuid, portletDataContext.getScopeGroupId(),
					privateLayout);

				if (layout == null) {
					layout = LayoutUtil.fetchByG_P_F(
						portletDataContext.getScopeGroupId(), privateLayout,
						friendlyURL);
				}

				if (layout == null) {
					layout = LayoutUtil.fetchByG_P_L(
						portletDataContext.getScopeGroupId(), privateLayout,
						oldLayoutId);
				}

				if (layout == null) {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler(9);

						sb.append("Unable to get layout with UUID ");
						sb.append(layoutUuid);
						sb.append(", friendly URL ");
						sb.append(friendlyURL);
						sb.append(", or ");
						sb.append("layoutId ");
						sb.append(oldLayoutId);
						sb.append(" in group ");
						sb.append(portletDataContext.getScopeGroupId());

						_log.warn(sb.toString());
					}
				}
				else {
					newLayoutId = layout.getLayoutId();
				}
			}
			catch (SystemException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get layout in group " +
							portletDataContext.getScopeGroupId(), se);
				}
			}

			String oldLinkToLayout = matcher.group(0);

			StringBundler sb = new StringBundler(4);

			sb.append(StringPool.AT);
			sb.append(layoutUuid);
			sb.append(StringPool.AT);
			sb.append(friendlyURL);

			String newLinkToLayout = StringUtil.replace(
				oldLinkToLayout,
				new String[] {sb.toString(), String.valueOf(oldLayoutId)},
				new String[] {StringPool.BLANK, String.valueOf(newLayoutId)});

			oldLinksToLayout.add(oldLinkToLayout);
			newLinksToLayout.add(newLinkToLayout);
		}

		content = StringUtil.replace(
			content, ArrayUtil.toStringArray(oldLinksToLayout.toArray()),
			ArrayUtil.toStringArray(newLinksToLayout.toArray()));

		return content;
	}

	protected static void prepareLanguagesForImport(JournalArticle article)
		throws PortalException {

		Locale articleDefaultLocale = LocaleUtil.fromLanguageId(
			article.getDefaultLocale());

		Locale[] articleAvailableLocales = LocaleUtil.fromLanguageIds(
			article.getAvailableLocales());

		Locale defaultImportLocale = LocalizationUtil.getDefaultImportLocale(
			JournalArticle.class.getName(), article.getPrimaryKey(),
			articleDefaultLocale, articleAvailableLocales);

		article.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				JournalPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		JournalArticleLocalServiceUtil.deleteArticles(
			portletDataContext.getScopeGroupId());

		DDMTemplateLocalServiceUtil.deleteTemplates(
			portletDataContext.getScopeGroupId());

		DDMStructureLocalServiceUtil.deleteStructures(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.journal",
			portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Element ddmStructuresElement =
			portletDataContext.getExportDataGroupElement(DDMStructure.class);

		List<DDMStructure> ddmStructures = DDMStructureUtil.findByG_C(
			portletDataContext.getScopeGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new StructureIdComparator(true));

		List<DDMTemplate> ddmTemplates = new ArrayList<DDMTemplate>();

		for (DDMStructure ddmStructure : ddmStructures) {
			if (portletDataContext.isWithinDateRange(
					ddmStructure.getModifiedDate())) {

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, ddmStructure);
			}

			ddmTemplates.addAll(ddmStructure.getTemplates());
		}

		Element ddmTemplatesElement =
			portletDataContext.getExportDataGroupElement(DDMTemplate.class);

		// Does not have staged model data handler yet

		Element dlFileEntryTypesElement = rootElement.addElement(
			"dl-file-entry-types");
		Element dlFoldersElement = rootElement.addElement("dl-folders");
		Element dlFilesElement = rootElement.addElement("dl-file-entries");
		Element dlFileRanksElement = rootElement.addElement("dl-file-ranks");
		Element dlRepositoriesElement = rootElement.addElement(
			"dl-repositories");
		Element dlRepositoryEntriesElement = rootElement.addElement(
			"dl-repository-entries");

		for (DDMTemplate ddmTemplate : ddmTemplates) {
			if (portletDataContext.isWithinDateRange(
					ddmTemplate.getModifiedDate())) {

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, ddmTemplate);
			}
		}

		Element feedsElement = rootElement.addElement("feeds");

		List<JournalFeed> feeds = JournalFeedUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (JournalFeed feed : feeds) {
			if (portletDataContext.isWithinDateRange(feed.getModifiedDate())) {
				exportFeed(portletDataContext, feedsElement, feed);
			}
		}

		Element foldersElement = rootElement.addElement("folders");
		Element articlesElement = rootElement.addElement("articles");

		if (portletDataContext.getBooleanParameter(NAMESPACE, "web-content")) {
			List<JournalFolder> folders = JournalFolderUtil.findByGroupId(
				portletDataContext.getScopeGroupId());

			for (JournalFolder folder : folders) {
				exportFolder(
					portletDataContext, foldersElement, articlesElement,
					ddmStructuresElement, ddmTemplatesElement,
					dlFileEntryTypesElement, dlFoldersElement, dlFilesElement,
					dlFileRanksElement, dlRepositoriesElement,
					dlRepositoryEntriesElement, folder, true);
			}

			List<JournalArticle> articles = JournalArticleUtil.findByG_F(
				portletDataContext.getScopeGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new ArticleIDComparator(true));

			for (JournalArticle article : articles) {
				if (portletDataContext.getBooleanParameter(
						NAMESPACE, "version-history") ||
					JournalArticleLocalServiceUtil.isLatestVersion(
						article.getGroupId(), article.getArticleId(),
						article.getVersion(),
						WorkflowConstants.STATUS_APPROVED)) {

					exportArticle(
						portletDataContext, articlesElement,
						ddmStructuresElement, ddmTemplatesElement,
						dlFileEntryTypesElement, dlFoldersElement,
						dlFilesElement, dlFileRanksElement,
						dlRepositoriesElement, dlRepositoryEntriesElement,
						article, true);
				}
			}
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.journal",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Element rootElement = portletDataContext.getImportDataRootElement();

		importReferencedData(portletDataContext, rootElement);

		Element ddmStructuresElement =
			portletDataContext.getImportDataGroupElement(DDMStructure.class);

		List<Element> ddmStructureElements = ddmStructuresElement.elements();

		for (Element ddmStructureElement : ddmStructureElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, ddmStructureElement);
		}

		Element ddmTemplatesElement =
			portletDataContext.getImportDataGroupElement(DDMTemplate.class);

		List<Element> ddmTemplateElements = ddmTemplatesElement.elements();

		for (Element ddmTemplateElement : ddmTemplateElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, ddmTemplateElement);
		}

		Element feedsElement = rootElement.element("feeds");

		List<Element> feedElements = feedsElement.elements("feed");

		for (Element feedElement : feedElements) {
			importFeed(portletDataContext, feedElement);
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "web-content")) {
			Element foldersElement = rootElement.element("folders");

			List<Element> folderElements = foldersElement.elements("folder");

			for (Element folderElement : folderElements) {
				importFolder(portletDataContext, folderElement);
			}

			Element articlesElement = rootElement.element("articles");

			List<Element> articleElements = articlesElement.elements("article");

			for (Element articleElement : articleElements) {
				try {
					importArticle(portletDataContext, articleElement);
				}
				catch (ArticleContentException ace) {
					if (_log.isWarnEnabled()) {
						String path = articleElement.attributeValue("path");

						_log.warn(
							"Skipping article with path " + path +
								" because of invalid content");
					}
				}
			}
		}

		return portletPreferences;
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalPortletDataHandler.class);

	private static Pattern _importLinksToLayoutPattern = Pattern.compile(
		"\\[([0-9]+)@(public|private\\-[a-z]*)@(\\p{XDigit}{8}\\-" +
		"(?:\\p{XDigit}{4}\\-){3}\\p{XDigit}{12})@([^\\]]*)\\]");

}