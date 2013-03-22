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

package com.liferay.portlet.messageboards.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelPathUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryUtil;

import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class MBCategoryStagedModelDataHandler
	extends BaseStagedModelDataHandler<MBCategory> {

	@Override
	public String getClassName() {
		return MBCategory.class.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Element[] elements,
			MBCategory category)
		throws Exception {

		if ((category.getCategoryId() ==
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(category.getCategoryId() ==
				MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return;
		}

		Element categoriesElement = elements[0];

		if (category.getParentCategory() != null) {
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, categoriesElement,
				category.getParentCategory());
		}

		Element categoryElement = categoriesElement.addElement("category");

		portletDataContext.addClassedModel(
			categoryElement, StagedModelPathUtil.getPath(category), category,
			MBPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Element element,
			MBCategory category)
		throws Exception {

		long userId = portletDataContext.getUserId(category.getUserUuid());

		Map<Long, Long> categoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBCategory.class);

		long parentCategoryId = MapUtil.getLong(
			categoryIds, category.getParentCategoryId(),
			category.getParentCategoryId());

		String emailAddress = null;
		String inProtocol = null;
		String inServerName = null;
		int inServerPort = 0;
		boolean inUseSSL = false;
		String inUserName = null;
		String inPassword = null;
		int inReadInterval = 0;
		String outEmailAddress = null;
		boolean outCustom = false;
		String outServerName = null;
		int outServerPort = 0;
		boolean outUseSSL = false;
		String outUserName = null;
		String outPassword = null;
		boolean allowAnonymous = false;
		boolean mailingListActive = false;

		// Parent category

		if ((parentCategoryId !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(parentCategoryId != MBCategoryConstants.DISCUSSION_CATEGORY_ID) &&
			(parentCategoryId == category.getParentCategoryId())) {

			String parentCategoryPath = StagedModelPathUtil.getPath(
				portletDataContext, getClassName(), parentCategoryId);

			MBCategory parentCategory =
				(MBCategory)portletDataContext.getZipEntryAsObject(
					parentCategoryPath);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, element, parentCategory);

			parentCategoryId = MapUtil.getLong(
				categoryIds, category.getParentCategoryId(),
				category.getParentCategoryId());
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			category, MBPortletDataHandler.NAMESPACE);

		MBCategory importedCategory = null;

		if (portletDataContext.isDataStrategyMirror()) {
			MBCategory existingCategory = MBCategoryUtil.fetchByUUID_G(
				category.getUuid(), portletDataContext.getScopeGroupId());

			if (existingCategory == null) {
				serviceContext.setUuid(category.getUuid());

				importedCategory = MBCategoryLocalServiceUtil.addCategory(
					userId, parentCategoryId, category.getName(),
					category.getDescription(), category.getDisplayStyle(),
					emailAddress, inProtocol, inServerName, inServerPort,
					inUseSSL, inUserName, inPassword, inReadInterval,
					outEmailAddress, outCustom, outServerName, outServerPort,
					outUseSSL, outUserName, outPassword, allowAnonymous,
					mailingListActive, serviceContext);
			}
			else {
				importedCategory = MBCategoryLocalServiceUtil.updateCategory(
					existingCategory.getCategoryId(), parentCategoryId,
					category.getName(), category.getDescription(),
					category.getDisplayStyle(), emailAddress, inProtocol,
					inServerName, inServerPort, inUseSSL, inUserName,
					inPassword, inReadInterval, outEmailAddress, outCustom,
					outServerName, outServerPort, outUseSSL, outUserName,
					outPassword, allowAnonymous, mailingListActive, false,
					serviceContext);
			}
		}
		else {
			importedCategory = MBCategoryLocalServiceUtil.addCategory(
				userId, parentCategoryId, category.getName(),
				category.getDescription(), category.getDisplayStyle(),
				emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
				inUserName, inPassword, inReadInterval, outEmailAddress,
				outCustom, outServerName, outServerPort, outUseSSL, outUserName,
				outPassword, allowAnonymous, mailingListActive, serviceContext);
		}

		portletDataContext.importClassedModel(
			category, importedCategory, MBPortletDataHandler.NAMESPACE);
	}

}