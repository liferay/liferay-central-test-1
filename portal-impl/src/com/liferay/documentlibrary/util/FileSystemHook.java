/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.documentlibrary.util;

import com.liferay.documentlibrary.DuplicateDirectoryException;
import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="FileSystemHook.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Sten Martinez
 *
 */
public class FileSystemHook extends BaseHook {

	public FileSystemHook() {
		_rootDir = new File(_ROOT_DIR);

		if (!_rootDir.exists()) {
			_rootDir.mkdirs();
		}
	}

	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException {

		File dirNameDir = getDirNameDir(companyId, repositoryId, dirName);

		if (dirNameDir.exists()) {
			throw new DuplicateDirectoryException();
		}

		dirNameDir.mkdirs();
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			String[] tagsEntries, InputStream is)
		throws PortalException, SystemException {

		try {
			File fileNameVersionFile = getFileNameVersionFile(
				companyId, repositoryId, fileName, DEFAULT_VERSION);

			if (fileNameVersionFile.exists()) {
				throw new DuplicateFileException();
			}

			FileUtil.write(fileNameVersionFile, is);

			Indexer.addFile(
				companyId, portletId, groupId, repositoryId, fileName,
				fileEntryId, properties, tagsEntries);
		}
		catch (IOException ioe) {
			throw new SystemException();
		}
	}

	public void checkRoot(long companyId) {
	}

	public void deleteDirectory(
			long companyId, String portletId, long repositoryId, String dirName)
		throws PortalException {

		File dirNameDir = getDirNameDir(companyId, repositoryId, dirName);

		if (!dirNameDir.exists()) {
			throw new NoSuchDirectoryException();
		}

		FileUtil.deltree(dirNameDir);
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName)
		throws PortalException, SystemException {

		try {
			File fileNameDir = getFileNameDir(
				companyId, repositoryId, fileName);

			if (!fileNameDir.exists()) {
				throw new NoSuchFileException();
			}

			FileUtil.deltree(fileNameDir);

			Indexer.deleteFile(companyId, portletId, repositoryId, fileName);
		}
		catch (SearchException se) {
			throw new SystemException();
		}
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName, double versionNumber)
		throws PortalException {

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionNumber);

		if (!fileNameVersionFile.exists()) {
			throw new NoSuchFileException();
		}

		fileNameVersionFile.delete();
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			double versionNumber)
		throws PortalException, SystemException {

		try {
			if (versionNumber == 0) {
				versionNumber = getHeadVersionNumber(
					companyId, repositoryId, fileName);
			}

			File fileNameVersionFile = getFileNameVersionFile(
				companyId, repositoryId, fileName, versionNumber);

			if (!fileNameVersionFile.exists()) {
				throw new NoSuchFileException();
			}

			return new FileInputStream(fileNameVersionFile);
		}
		catch (IOException ioe) {
			throw new SystemException();
		}
	}

	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		File dirNameDir = getDirNameDir(companyId, repositoryId, dirName);

		if (!dirNameDir.exists()) {
			throw new NoSuchDirectoryException();
		}

		String[] fileNames = FileUtil.listDirs(dirNameDir);

		Arrays.sort(fileNames);

		// Convert /${fileName} to /${dirName}/${fileName}

		for (int i = 0; i < fileNames.length; i++) {
			fileNames[i] =
				StringPool.SLASH + dirName + StringPool.SLASH + fileNames[i];
		}

		return fileNames;
	}

	public long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		double versionNumber = getHeadVersionNumber(
			companyId, repositoryId, fileName);

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionNumber);

		if (!fileNameVersionFile.exists()) {
			throw new NoSuchFileException();
		}

		return fileNameVersionFile.length();
	}

	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		double versionNumber) {

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionNumber);

		if (fileNameVersionFile.exists()) {
			return true;
		}
		else {
			return false;
		}
	}

	public void move(String srcDir, String destDir) {
	}

	public void reIndex(String[] ids) {
		long companyId = GetterUtil.getLong(ids[0]);
		String portletId = ids[1];
		long groupId = GetterUtil.getLong(ids[2]);
		long repositoryId = GetterUtil.getLong(ids[3]);

		File repistoryDir = getRepositoryDir(companyId, repositoryId);

		String[] fileNames = FileUtil.listDirs(repistoryDir);

		for (int i = 0; i < fileNames.length; i++) {
			String fileName = fileNames[i];

			try {
				Document doc = Indexer.getFileDocument(
					companyId, portletId, groupId, repositoryId, fileName);

				SearchEngineUtil.updateDocument(
					companyId, doc.get(Field.UID), doc);
			}
			catch (Exception e) {
				_log.error("Reindexing " + fileName, e);
			}
		}
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, double versionNumber, String sourceFileName,
			long fileEntryId, String properties, String[] tagsEntries,
			InputStream is)
		throws PortalException, SystemException {

		try {
			File fileNameVersionFile = getFileNameVersionFile(
				companyId, repositoryId, fileName, versionNumber);

			if (fileNameVersionFile.exists()) {
				throw new DuplicateFileException();
			}

			FileUtil.write(fileNameVersionFile, is);

			Indexer.updateFile(
				companyId, portletId, groupId, repositoryId, fileName,
				fileEntryId, properties, new Date(), tagsEntries);
		}
		catch (IOException ioe) {
			throw new SystemException();
		}
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			long newRepositoryId, String fileName, long fileEntryId)
		throws SystemException {

		try {
			File fileNameDir = getFileNameDir(
				companyId, repositoryId, fileName);
			File newFileNameDir = getFileNameDir(
				companyId, newRepositoryId, fileName);

			FileUtil.copyDirectory(fileNameDir, newFileNameDir);

			FileUtil.deltree(fileNameDir);

			try {
				Indexer.deleteFile(
					companyId, portletId, repositoryId, fileName);
			}
			catch (SearchException se) {
			}

			Indexer.addFile(
				companyId, portletId, groupId, newRepositoryId, fileName);
		}
		catch (SearchException se) {
			throw new SystemException();
		}
	}

	protected File getCompanyDir(long companyId) {
		File companyDir = new File(_rootDir + StringPool.SLASH + companyId);

		if (!companyDir.exists()) {
			companyDir.mkdirs();
		}

		return companyDir;
	}

	protected File getDirNameDir(
		long companyId, long repositoryId, String dirName) {

		return getFileNameDir(companyId, repositoryId, dirName);
	}

	protected File getRepositoryDir(long companyId, long repositoryId) {
		File companyDir = getCompanyDir(companyId);

		File repositoryDir = new File(
			companyDir + StringPool.SLASH + repositoryId);

		if (!repositoryDir.exists()) {
			repositoryDir.mkdirs();
		}

		return repositoryDir;
	}

	protected File getFileNameDir(
		long companyId, long repositoryId, String fileName) {

		File repositoryDir = getRepositoryDir(companyId, repositoryId);

		File fileNameDir = new File(
			repositoryDir + StringPool.SLASH + fileName);

		return fileNameDir;
	}

	protected File getFileNameVersionFile(
		long companyId, long repositoryId, String fileName, double version) {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		File fileNameVersionFile = new File(
			fileNameDir + StringPool.SLASH + version);

		return fileNameVersionFile;
	}

	protected double getHeadVersionNumber(
		long companyId, long repositoryId, String fileName) {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		if (!fileNameDir.exists()) {
			return DEFAULT_VERSION;
		}

		String[] versionNumbers = FileUtil.listFiles(fileNameDir);

		double headVersionNumber = DEFAULT_VERSION;

		for (int i = 0; i < versionNumbers.length; i++) {
			double versionNumber = GetterUtil.getDouble(versionNumbers[i]);

			if (versionNumber > headVersionNumber) {
				headVersionNumber = versionNumber;
			}
		}

		return headVersionNumber;
	}

	private static final String _ROOT_DIR = PropsUtil.get(
		PropsKeys.DL_HOOK_FILE_SYSTEM_ROOT_DIR);

	private static Log _log = LogFactory.getLog(FileSystemHook.class);

	private File _rootDir;

}