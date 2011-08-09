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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.documentlibrary.DuplicateDirectoryException;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.NoSuchDirectoryException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 * @author Sten Martinez
 * @author Alexander Chow
 */
public class FileSystemStore extends BaseStore {

	public FileSystemStore() {
		_rootDir = new File(_ROOT_DIR);

		if (!_rootDir.exists()) {
			_rootDir.mkdirs();
		}
	}

	@Override
	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException {

		File dirNameDir = getDirNameDir(companyId, repositoryId, dirName);

		if (dirNameDir.exists()) {
			throw new DuplicateDirectoryException(dirNameDir.getPath());
		}

		dirNameDir.mkdirs();
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException, SystemException {

		try {
			File fileNameVersionFile = getFileNameVersionFile(
				companyId, repositoryId, fileName, VERSION_DEFAULT);

			if (fileNameVersionFile.exists()) {
				throw new DuplicateFileException(fileNameVersionFile.getPath());
			}

			FileUtil.write(fileNameVersionFile, is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void checkRoot(long companyId) {
	}

	@Override
	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		File fromFileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, fromVersionLabel);

		File toFileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, toVersionLabel);

		if (toFileNameVersionFile.exists()) {
			throw new DuplicateFileException(toFileNameVersionFile.getPath());
		}

		try {
			toFileNameVersionFile.createNewFile();

			FileUtil.copyFile(fromFileNameVersionFile, toFileNameVersionFile);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void deleteDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		File dirNameDir = getDirNameDir(companyId, repositoryId, dirName);

		if (!dirNameDir.exists()) {
			throw new NoSuchDirectoryException(dirNameDir.getPath());
		}

		FileUtil.deltree(dirNameDir);
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName)
		throws PortalException {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		if (!fileNameDir.exists()) {
			throw new NoSuchFileException(fileNameDir.getPath());
		}

		FileUtil.deltree(fileNameDir);
	}

	@Override
	public void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionLabel);

		if (!fileNameVersionFile.exists()) {
			throw new NoSuchFileException(fileNameVersionFile.getPath());
		}

		fileNameVersionFile.delete();
	}

	@Override
	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		if (Validator.isNull(versionLabel)) {
			versionLabel = getHeadVersionLabel(
				companyId, repositoryId, fileName);
		}

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionLabel);

		if (!fileNameVersionFile.exists()) {
			throw new NoSuchFileException(fileNameVersionFile.getPath());
		}

		return fileNameVersionFile;
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		try {
			if (Validator.isNull(versionLabel)) {
				versionLabel = getHeadVersionLabel(
					companyId, repositoryId, fileName);
			}

			File fileNameVersionFile = getFileNameVersionFile(
				companyId, repositoryId, fileName, versionLabel);

			if (!fileNameVersionFile.exists()) {
				throw new NoSuchFileException(fileNameVersionFile.getPath());
			}

			return new FileInputStream(fileNameVersionFile);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public String[] getFileNames(long companyId, long repositoryId) {
		File repositoryDir = getRepositoryDir(companyId, repositoryId);

		return FileUtil.listDirs(repositoryDir);
	}

	@Override
	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		File dirNameDir = getDirNameDir(companyId, repositoryId, dirName);

		if (!dirNameDir.exists()) {
			throw new NoSuchDirectoryException(dirNameDir.getPath());
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

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException {

		String versionLabel = getHeadVersionLabel(
			companyId, repositoryId, fileName);

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionLabel);

		if (!fileNameVersionFile.exists()) {
			throw new NoSuchFileException(fileNameVersionFile.getPath());
		}

		return fileNameVersionFile.length();
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionLabel);

		if (fileNameVersionFile.exists()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void move(String srcDir, String destDir) {
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws SystemException {

		try {
			File fileNameDir = getFileNameDir(
				companyId, repositoryId, fileName);
			File newFileNameDir = getFileNameDir(
				companyId, newRepositoryId, fileName);

			FileUtil.copyDirectory(fileNameDir, newFileNameDir);

			FileUtil.deltree(fileNameDir);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws SystemException {

		try {
			File fileNameDir = getFileNameDir(
				companyId, repositoryId, fileName);
			File newFileNameDir = getFileNameDir(
				companyId, repositoryId, newFileName);

			FileUtil.copyDirectory(fileNameDir, newFileNameDir);

			FileUtil.deltree(fileNameDir);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws PortalException, SystemException {

		try {
			File fileNameVersionFile = getFileNameVersionFile(
				companyId, repositoryId, fileName, versionLabel);

			if (fileNameVersionFile.exists()) {
				throw new DuplicateFileException(fileNameVersionFile.getPath());
			}

			FileUtil.write(fileNameVersionFile, is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException {

		File fromFileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, fromVersionLabel);

		File toFileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, toVersionLabel);

		if (toFileNameVersionFile.exists()) {
			throw new DuplicateFileException(toFileNameVersionFile.getPath());
		}

		fromFileNameVersionFile.renameTo(toFileNameVersionFile);
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

	protected File getFileNameDir(
		long companyId, long repositoryId, String fileName) {

		File repositoryDir = getRepositoryDir(companyId, repositoryId);

		File fileNameDir = new File(
			repositoryDir + StringPool.SLASH + fileName);

		return fileNameDir;
	}

	protected File getFileNameVersionFile(
		long companyId, long repositoryId, String fileName, String version) {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		File fileNameVersionFile = new File(
			fileNameDir + StringPool.SLASH + version);

		return fileNameVersionFile;
	}

	protected String getHeadVersionLabel(
		long companyId, long repositoryId, String fileName) {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		if (!fileNameDir.exists()) {
			return VERSION_DEFAULT;
		}

		String[] versionLabels = FileUtil.listFiles(fileNameDir);

		String headVersionLabel = VERSION_DEFAULT;

		for (String versionLabel : versionLabels) {
			if (DLUtil.compareVersions(versionLabel, headVersionLabel) > 0) {
				headVersionLabel = versionLabel;
			}
		}

		return headVersionLabel;
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

	private static final String _ROOT_DIR = PropsUtil.get(
		PropsKeys.DL_STORE_FILE_SYSTEM_ROOT_DIR);

	private File _rootDir;

}