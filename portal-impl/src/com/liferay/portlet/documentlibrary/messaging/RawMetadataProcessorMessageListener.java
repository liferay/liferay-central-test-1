/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.messaging;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.metadata.RawMetadataProcessorUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLDocumentMetadataSetLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

/**
 * @author Miguel Pastor
 */
public class RawMetadataProcessorMessageListener extends BaseMessageListener {

	protected void doReceive(Message message) throws Exception {
		DLFileEntry dlFileEntry = (DLFileEntry)message.getPayload();

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion();

		InputStream inputStream = DLFileEntryLocalServiceUtil.getFileAsStream(
			dlFileEntry.getUserId(), dlFileEntry.getFileEntryId(),
			dlFileEntry.getVersion());

		Map<String, Fields> rawMetadataMap =
			RawMetadataProcessorUtil.getRawMetadataMap(inputStream);

		List<DDMStructure> ddmStructures =
			DDMStructureLocalServiceUtil.getClassStructures(
				PortalUtil.getClassNameId(DLFileEntry.class),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(dlFileEntry.getGroupId());
		serviceContext.setUserId(dlFileEntry.getUserId());

		DLDocumentMetadataSetLocalServiceUtil.updateDocumentMetadataSets(
			dlFileEntry.getCompanyId(), 0L, dlFileVersion.getFileVersionId(),
			rawMetadataMap, ddmStructures, serviceContext);
	}

}