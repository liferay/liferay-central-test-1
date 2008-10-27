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

package com.liferay.documentlibrary.service;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;

import java.io.File;

/**
 * <a href="DLServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLServiceUtil {

	public static void addDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		_service.addDirectory(companyId, repositoryId, dirName);
	}

	public static void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String properties, String[] tagsEntries, File file)
		throws PortalException, SystemException {

		_service.addFile(
			companyId, portletId, groupId, repositoryId, fileName, properties,
			tagsEntries, file);
	}

	public static void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String properties, String[] tagsEntries,
			byte[] bytes)
		throws PortalException, SystemException {

		_service.addFile(
			companyId, portletId, groupId, repositoryId, fileName, properties,
			tagsEntries, bytes);
	}

	public static void deleteDirectory(
			long companyId, String portletId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		DLService _service = DLServiceFactory.getService();

		_service.deleteDirectory(companyId, portletId, repositoryId, dirName);
	}

	public static void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName)
		throws PortalException, SystemException {

		_service.deleteFile(companyId, portletId, repositoryId, fileName);
	}

	public static void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName, double versionNumber)
		throws PortalException, SystemException {

		_service.deleteFile(
			companyId, portletId, repositoryId, fileName, versionNumber);
	}

	public static byte[] getFile(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return _service.getFile(companyId, repositoryId, fileName);
	}

	public static byte[] getFile(
			long companyId, long repositoryId, String fileName,
			double versionNumber)
		throws PortalException, SystemException {

		DLService _service = DLServiceFactory.getService();

		return _service.getFile(
			companyId, repositoryId, fileName, versionNumber);
	}

	public static String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		return _service.getFileNames(companyId, repositoryId, dirName);
	}

	public static long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return _service.getFileSize(companyId, repositoryId, fileName);
	}

	public static void reIndex(String[] ids) throws SystemException {
		_service.reIndex(ids);
	}

	public void setService(DLService service) {
		_service = service;
	}

	public static void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, double versionNumber, String sourceFileName,
			String properties, String[] tagsEntries, File file)
		throws PortalException, SystemException {

		_service.updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, sourceFileName, properties, tagsEntries, file);
	}

	public static void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, double versionNumber, String sourceFileName,
			String properties, String[] tagsEntries, byte[] bytes)
		throws PortalException, SystemException {

		DLService _service = DLServiceFactory.getService();

		_service.updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, sourceFileName, properties, tagsEntries, bytes);
	}

	public static void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			long newRepositoryId, String fileName)
		throws PortalException, SystemException {

		_service.updateFile(
			companyId, portletId, groupId, repositoryId, newRepositoryId,
			fileName);
	}

	private static DLService _service;
}