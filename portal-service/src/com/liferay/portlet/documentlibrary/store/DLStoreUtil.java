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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.io.File;
import java.io.InputStream;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class DLStoreUtil {

	public static void addDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		getStore().addDirectory(companyId, repositoryId, dirName);
	}

	public static void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, boolean validateFileExtension, long fileEntryId,
			String properties, Date modifiedDate, ServiceContext serviceContext,
			InputStream is)
		throws PortalException, SystemException {

		getStore().addFile(
			companyId, portletId, groupId, repositoryId, fileName,
			validateFileExtension, fileEntryId, properties, modifiedDate,
			serviceContext, is);
	}

	public static void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext, byte[] bytes)
		throws PortalException, SystemException {

		getStore().addFile(
			companyId, portletId, groupId, repositoryId, fileName, fileEntryId,
			properties, modifiedDate, serviceContext, bytes);
	}

	public static void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext, File file)
		throws PortalException, SystemException {

		getStore().addFile(
			companyId, portletId, groupId, repositoryId, fileName, fileEntryId,
			properties, modifiedDate, serviceContext, file);
	}

	public static void checkRoot(long companyId) throws SystemException {
		getStore().checkRoot(companyId);
	}

	public static void deleteDirectory(
			long companyId, String portletId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		getStore().deleteDirectory(
			companyId, portletId, repositoryId, dirName);
	}

	public static void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName)
		throws PortalException, SystemException {

		getStore().deleteFile(
			companyId, portletId, repositoryId, fileName);
	}

	public static void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName, String versionNumber)
		throws PortalException, SystemException {

		getStore().deleteFile(
			companyId, portletId, repositoryId, fileName, versionNumber);
	}

	public static byte[] getFile(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return getStore().getFile(companyId, repositoryId, fileName);
	}

	public static byte[] getFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		return getStore().getFile(
			companyId, repositoryId, fileName, versionNumber);
	}

	public static InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return getStore().getFileAsStream(companyId, repositoryId, fileName);
	}

	public static InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		return getStore().getFileAsStream(
			companyId, repositoryId, fileName, versionNumber);
	}

	public static String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		return getStore().getFileNames(companyId, repositoryId, dirName);
	}

	public static long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return getStore().getFileSize(companyId, repositoryId, fileName);
	}

	public static DLStore getStore() {
		if (_store == null) {
			_store = (DLStore)PortalBeanLocatorUtil.locate(
				DLStore.class.getName());

			ReferenceRegistry.registerReference(DLStoreUtil.class, "_store");

			MethodCache.remove(DLStore.class);
		}

		return _store;
	}

	public static boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		return getStore().hasFile(
			companyId, repositoryId, fileName, versionNumber);
	}

	public static void move(String srcDir, String destDir)
		throws SystemException {

		getStore().move(srcDir, destDir);
	}

	public static Hits search(
			long companyId, String portletId, long groupId,
			long userId, long[] repositoryIds, String keywords, int start,
			int end)
		throws SystemException {

		return getStore().search(
			companyId, portletId, groupId, userId, repositoryIds, keywords,
			start, end);
	}

	public static void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			long newRepositoryId, String fileName, long fileEntryId)
		throws PortalException, SystemException {

		getStore().updateFile(
			companyId, portletId, groupId, repositoryId, newRepositoryId,
			fileName, fileEntryId);
	}

	public static void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String newFileName, boolean reindex)
		throws PortalException, SystemException {

		getStore().updateFile(
			companyId, portletId, groupId, repositoryId, fileName, newFileName,
			reindex);
	}

	public static void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String fileExtension,
			boolean validateFileExtension, String versionNumber,
			String sourceFileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException {

		getStore().updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			fileExtension, validateFileExtension, versionNumber, sourceFileName,
			fileEntryId, properties, modifiedDate, serviceContext, is);
	}

	public static void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String versionNumber, String sourceFileName,
			long fileEntryId, String properties, Date modifiedDate,
			ServiceContext serviceContext, byte[] bytes)
		throws PortalException, SystemException {

		getStore().updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, sourceFileName, fileEntryId, properties,
			modifiedDate, serviceContext, bytes);
	}

	public static void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String versionNumber, String sourceFileName,
			long fileEntryId, String properties, Date modifiedDate,
			ServiceContext serviceContext, File file)
		throws PortalException, SystemException {

		getStore().updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, sourceFileName, fileEntryId, properties,
			modifiedDate, serviceContext, file);
	}

	public static void validate(String fileName, boolean validateFileExtension)
		throws PortalException, SystemException {

		getStore().validate(fileName, validateFileExtension);
	}

	public static void validate(
			String fileName, boolean validateFileExtension, byte[] bytes)
		throws PortalException, SystemException {

		getStore().validate(fileName, validateFileExtension, bytes);
	}

	public static void validate(
			String fileName, boolean validateFileExtension, File file)
		throws PortalException, SystemException {

		getStore().validate(fileName, validateFileExtension, file);
	}

	public static void validate(
			String fileName, boolean validateFileExtension, InputStream is)
		throws PortalException, SystemException {

		getStore().validate(fileName, validateFileExtension, is);
	}

	public static void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, InputStream is)
		throws PortalException, SystemException {

		getStore().validate(
			fileName, fileExtension, sourceFileName, validateFileExtension, is);
	}

	public void setStore(DLStore store) {
		_store = store;

		ReferenceRegistry.registerReference(DLStoreUtil.class, "_store");

		MethodCache.remove(DLStore.class);
	}

	private static DLStore _store;

}