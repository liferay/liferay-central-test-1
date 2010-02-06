/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.model.FileModel;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.SystemProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;

/**
 * <a href="S3Hook.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Sten Martinez
 */
public class S3Hook extends BaseHook {

	public S3Hook() {
		try {
			_s3Service = getS3Service();
			_s3Bucket = getS3Bucket();
		}
		catch (S3ServiceException s3se) {
			_log.error(s3se.getMessage());
		}
	}

	public void addDirectory(
		long companyId, long repositoryId, String dirName) {
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException {

		try {
			S3Object s3Object = new S3Object(
				_s3Bucket,
				getKey(companyId, repositoryId, fileName, DEFAULT_VERSION));

			s3Object.setDataInputStream(is);

			_s3Service.putObject(_s3Bucket, s3Object);

			Indexer indexer = IndexerRegistryUtil.getIndexer(
				FileModel.class);

			FileModel fileModel = new FileModel();

			fileModel.setAssetCategoryIds(serviceContext.getAssetCategoryIds());
			fileModel.setAssetTagNames(serviceContext.getAssetTagNames());
			fileModel.setCompanyId(companyId);
			fileModel.setFileEntryId(fileEntryId);
			fileModel.setFileName(fileName);
			fileModel.setGroupId(groupId);
			fileModel.setModifiedDate(modifiedDate);
			fileModel.setPortletId(portletId);
			fileModel.setProperties(properties);
			fileModel.setRepositoryId(repositoryId);

			indexer.reindex(fileModel);
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	public void checkRoot(long companyId) {
	}

	public void deleteDirectory(
			long companyId, String portletId, long repositoryId, String dirName)
		throws SystemException {

		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket, getKey(companyId, repositoryId, dirName), null);

			for (int i = 0; i < s3Objects.length; i++) {
				S3Object s3Object = s3Objects[i];

				_s3Service.deleteObject(_s3Bucket, s3Object.getKey());
			}
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName)
		throws PortalException, SystemException {

		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket, getKey(companyId, repositoryId, fileName), null);

			for (int i = 0; i < s3Objects.length; i++) {
				S3Object s3Object = s3Objects[i];

				_s3Service.deleteObject(_s3Bucket, s3Object.getKey());
			}

			FileModel fileModel = new FileModel();

			fileModel.setCompanyId(companyId);
			fileModel.setFileName(fileName);
			fileModel.setPortletId(portletId);
			fileModel.setRepositoryId(repositoryId);

			Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

			indexer.delete(fileModel);
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName, double versionNumber)
		throws SystemException {

		try {
			_s3Service.deleteObject(
				_s3Bucket,
				getKey(companyId, repositoryId, fileName, versionNumber));
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
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

			S3Object s3Object = _s3Service.getObject(
				_s3Bucket,
				getKey(companyId, repositoryId, fileName, versionNumber));

			return s3Object.getDataInputStream();
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws SystemException {

		try {
			List<String> list = new ArrayList<String>();

			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket, getKey(companyId, repositoryId, dirName), null);

			for (int i = 0; i < s3Objects.length; i++) {
				S3Object s3Object = s3Objects[i];

				// Convert /${companyId}/${repositoryId}/${dirName}/${fileName}
				// /${versionNumber} to /${dirName}/${fileName}

				String key = s3Object.getKey();

				int x = key.indexOf(StringPool.SLASH);

				x = key.indexOf(StringPool.SLASH, x + 1);

				int y = key.lastIndexOf(StringPool.SLASH);

				list.add(key.substring(x, y));
			}

			return list.toArray(new String[list.size()]);
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	public long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		try {
			double versionNumber = getHeadVersionNumber(
				companyId, repositoryId, fileName);

			S3Object objectDetails = _s3Service.getObjectDetails(
				_s3Bucket,
				getKey(companyId, repositoryId, fileName, versionNumber));

			return objectDetails.getContentLength();
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			double versionNumber)
		throws SystemException {

		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket,
				getKey(companyId, repositoryId, fileName, versionNumber), null);

			if (s3Objects.length == 0) {
				return false;
			}
			else {
				return true;
			}
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	public void move(String srcDir, String destDir) {
	}

	public void reindex(String[] ids) throws SearchException {
		long companyId = GetterUtil.getLong(ids[0]);
		String portletId = ids[1];
		long groupId = GetterUtil.getLong(ids[2]);
		long repositoryId = GetterUtil.getLong(ids[3]);

		try {
			S3Object[] searchObjects = _s3Service.listObjects(
				_s3Bucket, getKey(companyId, repositoryId), null);

			Set<String> fileNameSet = new HashSet<String>();

			for (int i = 0; i < searchObjects.length; i++) {
				S3Object currentObject = searchObjects[i];

				String fileName = getFileName(currentObject.getKey());

				fileNameSet.add(fileName);
			}

			Iterator<String> itr = fileNameSet.iterator();

			while (itr.hasNext()) {
				String fileName = itr.next();

				try {
					Indexer indexer = IndexerRegistryUtil.getIndexer(
						FileModel.class);

					FileModel fileModel = new FileModel();

					fileModel.setCompanyId(companyId);
					fileModel.setFileName(fileName);
					fileModel.setGroupId(groupId);
					fileModel.setPortletId(portletId);
					fileModel.setRepositoryId(repositoryId);

					Document document = indexer.getDocument(fileModel);

					SearchEngineUtil.updateDocument(
						companyId, document.get(Field.UID), document);
				}
				catch (Exception e) {
					_log.error("Reindexing " + fileName, e);
				}
			}
		}
		catch (S3ServiceException s3se) {
			throw new SearchException(s3se);
		}
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			long newRepositoryId, String fileName, long fileEntryId)
		throws PortalException, SystemException {

		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket, getKey(companyId, repositoryId, fileName), null);

			for (int i = 0; i < s3Objects.length; i++) {
				S3Object oldS3Object = s3Objects[i];

				String oldKey = oldS3Object.getKey();

				oldS3Object = _s3Service.getObject(_s3Bucket, oldKey);

				File tempFile = new File(
					SystemProperties.get(SystemProperties.TMP_DIR) +
						File.separator + PortalUUIDUtil.generate());

				FileUtil.write(tempFile, oldS3Object.getDataInputStream());

				InputStream is = new FileInputStream(tempFile);

				String newPrefix = getKey(companyId, newRepositoryId);

				int x = oldKey.indexOf(StringPool.SLASH);

				x = oldKey.indexOf(StringPool.SLASH, x + 1);

				String newKey =
					newPrefix + oldKey.substring(x + 1, oldKey.length());

				S3Object newS3Object = new S3Object(
					_s3Bucket, newKey);

				newS3Object.setDataInputStream(is);

				_s3Service.putObject(_s3Bucket, newS3Object);
				_s3Service.deleteObject(_s3Bucket, oldKey);

				FileUtil.delete(tempFile);
			}

			Indexer indexer = IndexerRegistryUtil.getIndexer(
				FileModel.class);

			FileModel fileModel = new FileModel();

			fileModel.setCompanyId(companyId);
			fileModel.setFileName(fileName);
			fileModel.setPortletId(portletId);
			fileModel.setRepositoryId(repositoryId);

			indexer.delete(fileModel);

			fileModel.setRepositoryId(newRepositoryId);
			fileModel.setGroupId(groupId);

			indexer.reindex(fileModel);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, double versionNumber, String sourceFileName,
			long fileEntryId, String properties, Date modifiedDate,
			ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException {

		try {
			S3Object s3Object = new S3Object(
				_s3Bucket,
				getKey(companyId, repositoryId, fileName, versionNumber));

			s3Object.setDataInputStream(is);

			_s3Service.putObject(_s3Bucket, s3Object);

			Indexer indexer = IndexerRegistryUtil.getIndexer(
				FileModel.class);

			FileModel fileModel = new FileModel();

			fileModel.setAssetCategoryIds(serviceContext.getAssetCategoryIds());
			fileModel.setAssetTagNames(serviceContext.getAssetTagNames());
			fileModel.setCompanyId(companyId);
			fileModel.setFileEntryId(fileEntryId);
			fileModel.setFileName(fileName);
			fileModel.setGroupId(groupId);
			fileModel.setModifiedDate(modifiedDate);
			fileModel.setPortletId(portletId);
			fileModel.setProperties(properties);
			fileModel.setRepositoryId(repositoryId);

			indexer.reindex(fileModel);
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String newFileName, boolean reindex)
		throws PortalException, SystemException {

		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket, getKey(companyId, repositoryId, fileName), null);

			for (int i = 0; i < s3Objects.length; i++) {
				S3Object oldS3Object = s3Objects[i];

				String oldKey = oldS3Object.getKey();

				oldS3Object = _s3Service.getObject(_s3Bucket, oldKey);

				File tempFile = new File(
					SystemProperties.get(SystemProperties.TMP_DIR) +
						File.separator + PortalUUIDUtil.generate());

				FileUtil.write(tempFile, oldS3Object.getDataInputStream());

				InputStream is = new FileInputStream(tempFile);

				String newPrefix = getKey(companyId, repositoryId, newFileName);

				int x = oldKey.indexOf(StringPool.SLASH);

				x = oldKey.indexOf(StringPool.SLASH, x + 1);

				x = oldKey.indexOf(StringPool.SLASH, x + 1);

				String newKey =
					newPrefix + oldKey.substring(x + 1, oldKey.length());

				S3Object newS3Object = new S3Object(
					_s3Bucket, newKey);

				newS3Object.setDataInputStream(is);

				_s3Service.putObject(_s3Bucket, newS3Object);
				_s3Service.deleteObject(_s3Bucket, oldKey);

				FileUtil.delete(tempFile);
			}

			if (reindex) {
				Indexer indexer = IndexerRegistryUtil.getIndexer(
					FileModel.class);

				FileModel fileModel = new FileModel();

				fileModel.setCompanyId(companyId);
				fileModel.setFileName(fileName);
				fileModel.setPortletId(portletId);
				fileModel.setRepositoryId(repositoryId);

				indexer.delete(fileModel);

				fileModel.setFileName(newFileName);
				fileModel.setGroupId(groupId);

				indexer.reindex(fileModel);
			}
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	protected AWSCredentials getAWSCredentials() throws S3ServiceException {
		if (Validator.isNull(_ACCESS_KEY) || Validator.isNull(_SECRET_KEY)) {
			throw new S3ServiceException(
				"S3 access and secret keys are not set");
		}
		else {
			return new AWSCredentials(_ACCESS_KEY, _SECRET_KEY);
		}
	}

	protected String getFileName(String key) {
		int x = key.indexOf(StringPool.SLASH);

		x = key.indexOf(StringPool.SLASH, x + 1);

		int y = key.lastIndexOf(StringPool.SLASH);

		return key.substring(x + 1, y);
	}

	protected double getHeadVersionNumber(
			long companyId, long repositoryId, String fileName)
		throws PortalException, S3ServiceException {

		S3Object[] s3Objects = _s3Service.listObjects(
			_s3Bucket, getKey(companyId, repositoryId, fileName), null);

		String[] keys = new String[s3Objects.length];

		for (int i = 0; i < s3Objects.length; i++) {
			S3Object s3Object = s3Objects[i];

			keys[i] = s3Object.getKey();
		}

		if (keys.length > 0) {
			Arrays.sort(keys);

			String headKey = keys[keys.length - 1];

			int x = headKey.lastIndexOf(StringPool.SLASH);

			return GetterUtil.getDouble(
				headKey.substring(x + 1, headKey.length()));
		}
		else {
			throw new NoSuchFileException(fileName);
		}
	}

	protected String getKey(long companyId, long repositoryId) {
		StringBundler sb = new StringBundler(4);

		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected String getKey(
		long companyId, long repositoryId, String fileName) {

		StringBundler sb = new StringBundler(6);

		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);
		sb.append(StringPool.SLASH);
		sb.append(fileName);
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected String getKey(
		long companyId, long repositoryId, String fileName,
		double versionNumber) {

		StringBundler sb = new StringBundler(7);

		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);
		sb.append(StringPool.SLASH);
		sb.append(fileName);
		sb.append(StringPool.SLASH);
		sb.append(versionNumber);

		return sb.toString();
	}

	protected S3Bucket getS3Bucket() throws S3ServiceException {
		if (Validator.isNull(_BUCKET_NAME)) {
			throw new S3ServiceException("S3 bucket name is not set");
		}
		else {
			return getS3Service().createBucket(_BUCKET_NAME);
		}
	}

	protected S3Service getS3Service() throws S3ServiceException {
		AWSCredentials credentials = getAWSCredentials();

		return new RestS3Service(credentials);
	}

	private static final String _ACCESS_KEY = PropsUtil.get(
		PropsKeys.DL_HOOK_S3_ACCESS_KEY);

	private static final String _BUCKET_NAME = PropsUtil.get(
		PropsKeys.DL_HOOK_S3_BUCKET_NAME);

	private static final String _SECRET_KEY = PropsUtil.get(
		PropsKeys.DL_HOOK_S3_SECRET_KEY);

	private static Log _log = LogFactoryUtil.getLog(S3Hook.class);

	private S3Bucket _s3Bucket;
	private S3Service _s3Service;

}