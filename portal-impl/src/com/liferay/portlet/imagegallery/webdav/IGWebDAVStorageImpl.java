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

package com.liferay.portlet.imagegallery.webdav;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.util.ContentTypeUtil;
import com.liferay.portal.webdav.BaseResourceImpl;
import com.liferay.portal.webdav.BaseWebDAVStorageImpl;
import com.liferay.portal.webdav.Resource;
import com.liferay.portal.webdav.Status;
import com.liferay.portal.webdav.WebDAVException;
import com.liferay.portal.webdav.WebDAVRequest;
import com.liferay.portal.webdav.WebDAVUtil;
import com.liferay.portlet.imagegallery.DuplicateFolderNameException;
import com.liferay.portlet.imagegallery.NoSuchFolderException;
import com.liferay.portlet.imagegallery.NoSuchImageException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.impl.IGFolderImpl;
import com.liferay.portlet.imagegallery.service.IGFolderServiceUtil;
import com.liferay.portlet.imagegallery.service.IGImageServiceUtil;
import com.liferay.portlet.tags.service.TagsEntryLocalServiceUtil;

import java.io.File;
import java.io.InputStream;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="IGWebDAVStorageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class IGWebDAVStorageImpl extends BaseWebDAVStorageImpl {

	public int copyCollectionResource(
			WebDAVRequest webDavRequest, Resource resource, String destination,
			boolean overwrite, long depth)
		throws WebDAVException {

		try {
			String[] destinationArray = WebDAVUtil.getPathArray(
				destination, true);

			long parentFolderId = IGFolderImpl.DEFAULT_PARENT_FOLDER_ID;

			try {
				parentFolderId = getParentFolderId(destinationArray);
			}
			catch (NoSuchFolderException nsfe) {
				return HttpServletResponse.SC_CONFLICT;
			}

			IGFolder folder = (IGFolder)resource.getModel();

			long groupId = WebDAVUtil.getGroupId(destination);
			long plid = getPlid(groupId);
			String name = WebDAVUtil.getResourceName(destinationArray);
			String description = folder.getDescription();
			boolean addCommunityPermissions = true;
			boolean addGuestPermissions = true;

			int status = HttpServletResponse.SC_CREATED;

			if (overwrite) {
				if (deleteResource(groupId, parentFolderId, name)) {
					status = HttpServletResponse.SC_NO_CONTENT;
				}
			}

			if (depth == 0) {
				IGFolderServiceUtil.addFolder(
					plid, parentFolderId, name, description,
					addCommunityPermissions, addGuestPermissions);
			}
			else {
				IGFolderServiceUtil.copyFolder(
					plid, folder.getFolderId(), parentFolderId, name,
					description, addCommunityPermissions, addGuestPermissions);
			}

			return status;
		}
		catch (DuplicateFolderNameException dfne) {
			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public int copySimpleResource(
			WebDAVRequest webDavRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		File file = null;

		try {
			String[] destinationArray = WebDAVUtil.getPathArray(
				destination, true);

			long parentFolderId = IGFolderImpl.DEFAULT_PARENT_FOLDER_ID;

			try {
				parentFolderId = getParentFolderId(destinationArray);
			}
			catch (NoSuchFolderException nsfe) {
				return HttpServletResponse.SC_CONFLICT;
			}

			IGImage image = (IGImage)resource.getModel();

			long groupId = WebDAVUtil.getGroupId(destination);
			String name = WebDAVUtil.getResourceName(destinationArray);
			String description = image.getDescription();
			String contentType = ContentTypeUtil.getContentType(
				image.getNameWithExtension());

			file = FileUtil.createTempFile(image.getImageType());

			InputStream is = resource.getContentAsStream();

			FileUtil.write(file, is);

			String[] tagsEntries = null;
			boolean addCommunityPermissions = true;
			boolean addGuestPermissions = true;

			int status = HttpServletResponse.SC_CREATED;

			if (overwrite) {
				if (deleteResource(groupId, parentFolderId, name)) {
					status = HttpServletResponse.SC_NO_CONTENT;
				}
			}

			IGImageServiceUtil.addImage(
				parentFolderId, name, description, file, contentType,
				tagsEntries, addCommunityPermissions, addGuestPermissions);

			return status;
		}
		catch (DuplicateFolderNameException dfne) {
			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (DuplicateFileException dfe) {
			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
		finally {
			if (file != null) {
				file.delete();
			}
		}
	}

	public int deleteResource(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			Resource resource = getResource(webDavRequest);

			if (resource == null) {
				return HttpServletResponse.SC_NOT_FOUND;
			}

			Object model = resource.getModel();

			if (model instanceof IGFolder) {
				IGFolder folder = (IGFolder)model;

				IGFolderServiceUtil.deleteFolder(folder.getFolderId());
			}
			else {
				IGImage image = (IGImage)model;

				IGImageServiceUtil.deleteImage(image.getImageId());
			}

			return HttpServletResponse.SC_NO_CONTENT;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public Resource getResource(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			String[] pathArray = webDavRequest.getPathArray();

			long parentFolderId = getParentFolderId(pathArray);
			String name = WebDAVUtil.getResourceName(pathArray);

			if (Validator.isNull(name)) {
				String path = getRootPath() + webDavRequest.getPath();

				return new BaseResourceImpl(path, StringPool.BLANK, getToken());
			}

			try {
				IGFolder folder = IGFolderServiceUtil.getFolder(
					webDavRequest.getGroupId(), parentFolderId, name);

				if ((folder.getParentFolderId() != parentFolderId) ||
					(webDavRequest.getGroupId() != folder.getGroupId())) {

					throw new NoSuchFolderException();
				}

				return toResource(webDavRequest, folder, false);
			}
			catch (NoSuchFolderException nsfe) {
				try {
					IGImage image =
						IGImageServiceUtil.
							getImageByFolderIdAndNameWithExtension(
								parentFolderId, name);

					return toResource(webDavRequest, image, false);
				}
				catch (NoSuchImageException nsie) {
					return null;
				}
			}
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public List<Resource> getResources(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			long folderId = getFolderId(webDavRequest.getPathArray());

			List<Resource> folders = getFolders(webDavRequest, folderId);
			List<Resource> images = getImages(webDavRequest, folderId);

			List<Resource> resources = new ArrayList<Resource>(
				folders.size() + images.size());

			resources.addAll(folders);
			resources.addAll(images);

			return resources;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public Status makeCollection(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			HttpServletRequest request = webDavRequest.getHttpServletRequest();

			if (request.getContentLength() > 0) {
				return new Status(
					HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
			}

			String[] pathArray = webDavRequest.getPathArray();

			long plid = getPlid(webDavRequest.getGroupId());
			long parentFolderId = getParentFolderId(pathArray);
			String name = WebDAVUtil.getResourceName(pathArray);
			String description = StringPool.BLANK;
			boolean addCommunityPermissions = true;
			boolean addGuestPermissions = true;

			IGFolderServiceUtil.addFolder(
				plid, parentFolderId, name, description,
				addCommunityPermissions, addGuestPermissions);

			String location = StringUtil.merge(pathArray, StringPool.SLASH);

			return new Status(location, HttpServletResponse.SC_CREATED);
		}
		catch (DuplicateFolderNameException dfne) {
			return new Status(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		catch (NoSuchFolderException nsfe) {
			return new Status(HttpServletResponse.SC_CONFLICT);
		}
		catch (PrincipalException pe) {
			return new Status(HttpServletResponse.SC_FORBIDDEN);
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public int moveCollectionResource(
			WebDAVRequest webDavRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		try {
			String[] destinationArray = WebDAVUtil.getPathArray(
				destination, true);

			IGFolder folder = (IGFolder)resource.getModel();

			long groupId = WebDAVUtil.getGroupId(destinationArray);
			long folderId = folder.getFolderId();
			long parentFolderId = getParentFolderId(destinationArray);
			String name = WebDAVUtil.getResourceName(destinationArray);
			String description = folder.getDescription();

			int status = HttpServletResponse.SC_CREATED;

			if (overwrite) {
				if (deleteResource(groupId, parentFolderId, name)) {
					status = HttpServletResponse.SC_NO_CONTENT;
				}
			}

			IGFolderServiceUtil.updateFolder(
				folderId, parentFolderId, name, description, false);

			return status;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (DuplicateFolderNameException dfne) {
			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public int moveSimpleResource(
			WebDAVRequest webDavRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		try {
			String[] destinationArray = WebDAVUtil.getPathArray(
				destination, true);

			IGImage image = (IGImage)resource.getModel();

			long groupId = WebDAVUtil.getGroupId(destinationArray);
			long parentFolderId = getParentFolderId(destinationArray);
			String name = WebDAVUtil.getResourceName(destinationArray);
			String description = image.getDescription();
			File file = null;
			String contentType = null;
			String[] tagsEntries = null;

			int status = HttpServletResponse.SC_CREATED;

			if (overwrite) {
				if (deleteResource(groupId, parentFolderId, name)) {
					status = HttpServletResponse.SC_NO_CONTENT;
				}
			}

			IGImageServiceUtil.updateImage(
				image.getImageId(), parentFolderId, name, description, file,
				contentType, tagsEntries);

			return status;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (DuplicateFileException dfe) {
			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (DuplicateFolderNameException dfne) {
			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public int putResource(WebDAVRequest webDavRequest) throws WebDAVException {
		File file = null;

		try {
			HttpServletRequest request = webDavRequest.getHttpServletRequest();

			String[] pathArray = webDavRequest.getPathArray();

			long parentFolderId = getParentFolderId(pathArray);
			String name = WebDAVUtil.getResourceName(pathArray);
			String description = StringPool.BLANK;

			file = FileUtil.createTempFile(FileUtil.getExtension(name));

			FileUtil.write(file, request.getInputStream());

			String contentType = ContentTypeUtil.getContentType(name);
			String[] tagsEntries = null;
			boolean addCommunityPermissions = true;
			boolean addGuestPermissions = true;

			try {
				IGImage image =
					IGImageServiceUtil.getImageByFolderIdAndNameWithExtension(
						parentFolderId, name);

				long imageId = image.getImageId();

				description = image.getDescription();
				tagsEntries = TagsEntryLocalServiceUtil.getEntryNames(
					IGImage.class.getName(), imageId);

				IGImageServiceUtil.updateImage(
					imageId, parentFolderId, name, description, file,
					contentType, tagsEntries);
			}
			catch (NoSuchImageException nsie) {
				IGImageServiceUtil.addImage(
					parentFolderId, name, description, file, contentType,
					tagsEntries, addCommunityPermissions, addGuestPermissions);
			}

			return HttpServletResponse.SC_CREATED;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}

			return HttpServletResponse.SC_CONFLICT;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
		finally {
			if (file != null) {
				file.delete();
			}
		}
	}

	protected boolean deleteResource(
			long groupId, long parentFolderId, String name)
		throws PortalException, SystemException, RemoteException {

		try {
			IGFolder folder = IGFolderServiceUtil.getFolder(
				groupId, parentFolderId, name);

			IGFolderServiceUtil.deleteFolder(folder.getFolderId());

			return true;
		}
		catch (NoSuchFolderException nsfe) {
			if (name.indexOf(StringPool.PERIOD) == -1) {
				return false;
			}

			try {
				IGImageServiceUtil.deleteImageByFolderIdAndNameWithExtension(
					parentFolderId, name);

				return true;
			}
			catch (NoSuchImageException nsie) {
			}
		}

		return false;
	}

	protected List<Resource> getFolders(
			WebDAVRequest webDavRequest, long parentFolderId)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		long groupId = webDavRequest.getGroupId();

		List<IGFolder> folders = IGFolderServiceUtil.getFolders(
			groupId, parentFolderId);

		for (IGFolder folder : folders) {
			Resource resource = toResource(webDavRequest, folder, true);

			resources.add(resource);
		}

		return resources;
	}

	protected List<Resource> getImages(
			WebDAVRequest webDavRequest, long parentFolderId)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		List<IGImage> images = IGImageServiceUtil.getImages(parentFolderId);

		for (IGImage image : images) {
			Resource resource = toResource(webDavRequest, image, true);

			resources.add(resource);
		}

		return resources;
	}

	protected long getFolderId(String[] pathArray) throws Exception {
		return getFolderId(pathArray, false);
	}

	protected long getFolderId(String[] pathArray, boolean parent)
		throws Exception {

		long folderId = IGFolderImpl.DEFAULT_PARENT_FOLDER_ID;

		if (pathArray.length <= 2) {
			return folderId;
		}
		else {
			long groupId = WebDAVUtil.getGroupId(pathArray);

			int x = pathArray.length;

			if (parent) {
				x--;
			}

			for (int i = 3; i < x; i++) {
				String name = pathArray[i];

				IGFolder folder = IGFolderServiceUtil.getFolder(
					groupId, folderId, name);

				if (groupId == folder.getGroupId()) {
					folderId = folder.getFolderId();
				}
			}
		}

		return folderId;
	}

	protected long getParentFolderId(String[] pathArray) throws Exception {
		return getFolderId(pathArray, true);
	}

	protected Resource toResource(
		WebDAVRequest webDavRequest, IGImage image, boolean appendPath) {

		String parentPath = getRootPath() + webDavRequest.getPath();
		String name = StringPool.BLANK;

		if (appendPath) {
			name = image.getNameWithExtension();
		}

		return new IGImageResourceImpl(image, parentPath, name);
	}

	protected Resource toResource(
		WebDAVRequest webDavRequest, IGFolder folder, boolean appendPath) {

		String parentPath = getRootPath() + webDavRequest.getPath();
		String name = StringPool.BLANK;

		if (appendPath) {
			name = folder.getName();
		}

		Resource resource = new BaseResourceImpl(
			parentPath, name, folder.getName(), folder.getCreateDate(),
			folder.getModifiedDate());

		resource.setModel(folder);
		resource.setClassName(IGFolder.class.getName());
		resource.setPrimaryKey(folder.getPrimaryKey());

		return resource;
	}

	private static Log _log = LogFactory.getLog(IGWebDAVStorageImpl.class);

}