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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.service.base.DLFileRankLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.comparator.FileRankCreateDateComparator;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileRankLocalServiceImpl extends DLFileRankLocalServiceBaseImpl {

	public DLFileRank addFileRank(
			long groupId, long companyId, long userId, long fileEntryId,
			ServiceContext serviceContext)
		throws SystemException {

		long fileRankId = counterLocalService.increment();

		DLFileRank fileRank = dlFileRankPersistence.create(fileRankId);

		fileRank.setGroupId(groupId);
		fileRank.setCompanyId(companyId);
		fileRank.setUserId(userId);
		fileRank.setCreateDate(serviceContext.getCreateDate(null));
		fileRank.setFileEntryId(fileEntryId);

		try {
			dlFileRankPersistence.update(fileRank, false);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Add failed, fetch {companyId=" + companyId + ", userId=" +
						userId + ", fileEntryId=" + fileEntryId + "}");
			}

			fileRank = dlFileRankPersistence.fetchByC_U_F(
				companyId, userId, fileEntryId, false);

			if (fileRank == null) {
				throw se;
			}
		}

		return fileRank;
	}

	public void checkFileRanks() throws SystemException {
		List<Object[]> list = dlFileRankFinder.findGU_ByC(
			PropsValues.DL_FILE_RANK_MAX_SIZE);

		for (Object[] pair : list) {
			long groupId = (Long)pair[0];
			long userId = (Long)pair[1];

			List<DLFileRank> fileRanks = getFileRanks(
				groupId, userId, PropsValues.DL_FILE_RANK_MAX_SIZE,
				QueryUtil.ALL_POS);

			for (int i = 0; i < fileRanks.size(); i++) {
				DLFileRank lastFileRank = fileRanks.get(i);

				long lastFileRankId = lastFileRank.getFileRankId();

				try {
					dlFileRankPersistence.remove(lastFileRank);
				}
				catch (Exception e) {
					_log.warn(
						"Failed to remove file rank " + lastFileRankId);
				}
			}
		}
	}

	public void deleteFileRank(DLFileRank dlFileRank) throws SystemException {
		dlFileRankPersistence.remove(dlFileRank);
	}

	public void deleteFileRank(long fileRankId)
		throws PortalException,	SystemException {

		DLFileRank dlFileRank = dlFileRankPersistence.findByPrimaryKey(
			fileRankId);

		deleteFileRank(dlFileRank);
	}

	public void deleteFileRanksByFileEntryId(long fileEntryId)
		throws SystemException {

		List<DLFileRank> dlFileRanks = dlFileRankPersistence.findByFileEntryId(
			fileEntryId);

		for (DLFileRank dlFileRank : dlFileRanks) {
			deleteFileRank(dlFileRank);
		}
	}

	public void deleteFileRanksByUserId(long userId) throws SystemException {
		List<DLFileRank> dlFileRanks = dlFileRankPersistence.findByUserId(
			userId);

		for (DLFileRank dlFileRank : dlFileRanks) {
			deleteFileRank(dlFileRank);
		}
	}

	public List<DLFileRank> getFileRanks(long groupId, long userId)
		throws SystemException {

		return getFileRanks(
			groupId, userId, 0, PropsValues.DL_FILE_RANK_MAX_SIZE);
	}

	public DLFileRank updateFileRank(
			long groupId, long companyId, long userId, long fileEntryId,
			ServiceContext serviceContext)
		throws SystemException {

		if (!PropsValues.DL_FILE_RANK_ENABLED) {
			return null;
		}

		DLFileRank fileRank = dlFileRankPersistence.fetchByC_U_F(
			companyId, userId, fileEntryId);

		if (fileRank != null) {
			fileRank.setCreateDate(serviceContext.getCreateDate(null));

			dlFileRankPersistence.update(fileRank, false);
		}
		else {
			fileRank = addFileRank(
				groupId, companyId, userId, fileEntryId, serviceContext);
		}

		return fileRank;
	}

	protected List<DLFileRank> getFileRanks(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return dlFileRankPersistence.findByG_U(
			groupId, userId, start, end, new FileRankCreateDateComparator());
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLFileRankLocalServiceImpl.class);

}