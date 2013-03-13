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

package com.liferay.portlet.dynamicdatamapping.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelPathUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portlet.dynamicdatamapping.TemplateDuplicateTemplateKeyException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMTemplateUtil;
import com.liferay.portlet.journal.lar.JournalPortletDataHandler;

import java.io.File;

import java.util.Map;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class DDMTemplateStagedModelDataHandler
	extends BaseStagedModelDataHandler<DDMTemplate> {

	@Override
	public String getClassName() {
		return DDMTemplate.class.getName();
	}

	protected DDMTemplate addTemplate(
			long userId, long groupId, DDMTemplate template, long classPK,
			File smallFile, ServiceContext serviceContext)
		throws Exception {

		DDMTemplate newTemplate = null;

		try {
			return DDMTemplateLocalServiceUtil.addTemplate(
				userId, groupId, template.getClassNameId(), classPK,
				template.getTemplateKey(), template.getNameMap(),
				template.getDescriptionMap(), template.getType(),
				template.getMode(), template.getLanguage(),
				template.getScript(), template.isCacheable(),
				template.isSmallImage(), template.getSmallImageURL(), smallFile,
				serviceContext);
		}
		catch (TemplateDuplicateTemplateKeyException tdtke) {
			newTemplate = DDMTemplateLocalServiceUtil.addTemplate(
				userId, groupId, template.getClassNameId(), classPK, null,
				template.getNameMap(), template.getDescriptionMap(),
				template.getType(), template.getMode(), template.getLanguage(),
				template.getScript(), template.isCacheable(),
				template.isSmallImage(), template.getSmallImageURL(), smallFile,
				serviceContext);

			if (_log.isWarnEnabled()) {
				_log.warn(
					"A template with the key " + template.getTemplateKey() +
						" already exists. The new generated key is " +
							newTemplate.getTemplateKey());
			}
		}

		return newTemplate;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Element[] elements,
			DDMTemplate template)
		throws Exception {

		Element templatesElement = elements[0];
		Element dlFileEntryTypesElement = null;
		Element dlFoldersElement = null;
		Element dlFileEntriesElement = null;
		Element dlFileRanksElement = null;
		Element dlRepositoriesElement = null;
		Element dlRepositoryEntriesElement = null;

		if (elements.length > 1) {
			dlFileEntryTypesElement = elements[1];
			dlFoldersElement = elements[2];
			dlFileEntriesElement = elements[3];
			dlFileRanksElement = elements[4];
			dlRepositoriesElement = elements[5];
			dlRepositoryEntriesElement = elements[6];
		}

		Element templateElement = templatesElement.addElement("template");

		if (template.isSmallImage()) {
			Image smallImage = ImageUtil.fetchByPrimaryKey(
				template.getSmallImageId());

			if (Validator.isNotNull(template.getSmallImageURL())) {
				String smallImageURL =
					DDMPortletDataHandler.exportReferencedContent(
						portletDataContext, dlFileEntryTypesElement,
						dlFoldersElement, dlFileEntriesElement,
						dlFileRanksElement, dlRepositoriesElement,
						dlRepositoryEntriesElement, templateElement,
						template.getSmallImageURL().concat(StringPool.SPACE));

				template.setSmallImageURL(smallImageURL);
			}
			else if (smallImage != null) {
				String smallImagePath = StagedModelPathUtil.getPath(
					template,
					smallImage.getImageId() + StringPool.PERIOD +
						template.getSmallImageType());

				templateElement.addAttribute(
					"small-image-path", smallImagePath);

				template.setSmallImageType(smallImage.getType());

				portletDataContext.addZipEntry(
					smallImagePath, smallImage.getTextObj());
			}
		}

		if (portletDataContext.getBooleanParameter(
				DDMPortletDataHandler.NAMESPACE, "embedded-assets")) {

			String content = DDMPortletDataHandler.exportReferencedContent(
				portletDataContext, dlFileEntryTypesElement, dlFoldersElement,
				dlFileEntriesElement, dlFileRanksElement, dlRepositoriesElement,
				dlRepositoryEntriesElement, templateElement,
				template.getScript());

			template.setScript(content);
		}

		portletDataContext.addClassedModel(
			templateElement, StagedModelPathUtil.getPath(template), template,
			DDMPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Element element, String path,
			DDMTemplate template)
		throws Exception {

		long userId = portletDataContext.getUserId(template.getUserUuid());

		Map<Long, Long> structureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		long classPK = MapUtil.getLong(
			structureIds, template.getClassPK(), template.getClassPK());

		File smallFile = null;

		if (template.isSmallImage()) {
			String smallImagePath = element.attributeValue("small-image-path");

			if (Validator.isNotNull(template.getSmallImageURL())) {
				String smallImageURL =
					JournalPortletDataHandler.importReferencedContent(
						portletDataContext, element,
						template.getSmallImageURL());

				template.setSmallImageURL(smallImageURL);
			}
			else if (Validator.isNotNull(smallImagePath)) {
				byte[] bytes = portletDataContext.getZipEntryAsByteArray(
					smallImagePath);

				if (bytes != null) {
					smallFile = FileUtil.createTempFile(
						template.getSmallImageType());

					FileUtil.write(smallFile, bytes);
				}
			}
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			element, template, DDMPortletDataHandler.NAMESPACE);

		DDMTemplate importedTemplate = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DDMTemplate existingTemplate = DDMTemplateUtil.fetchByUUID_G(
				template.getUuid(), portletDataContext.getScopeGroupId());

			if (existingTemplate == null) {
				serviceContext.setUuid(template.getUuid());

				importedTemplate = addTemplate(
					userId, portletDataContext.getScopeGroupId(), template,
					classPK, smallFile, serviceContext);
			}
			else {
				importedTemplate = DDMTemplateLocalServiceUtil.updateTemplate(
					existingTemplate.getTemplateId(), template.getNameMap(),
					template.getDescriptionMap(), template.getType(),
					template.getMode(), template.getLanguage(),
					template.getScript(), template.isCacheable(),
					template.isSmallImage(), template.getSmallImageURL(),
					smallFile, serviceContext);
			}
		}
		else {
			importedTemplate = addTemplate(
				userId, portletDataContext.getScopeGroupId(), template, classPK,
				smallFile, serviceContext);
		}

		portletDataContext.importClassedModel(
			template, importedTemplate, DDMPortletDataHandler.NAMESPACE);
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMTemplateStagedModelDataHandler.class);

}