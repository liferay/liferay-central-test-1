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

package com.liferay.portlet.imagegallery.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermQueryFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.imagegallery.DuplicateFolderNameException;
import com.liferay.portlet.imagegallery.FolderNameException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.impl.IGFolderImpl;
import com.liferay.portlet.imagegallery.service.base.IGFolderLocalServiceBaseImpl;
import com.liferay.portlet.imagegallery.util.Indexer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="IGFolderLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class IGFolderLocalServiceImpl extends IGFolderLocalServiceBaseImpl {

	public IGFolder addFolder(
			long userId, long plid, long parentFolderId, String name,
			String description, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addFolder(
			null, userId, plid, parentFolderId, name, description,
			Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public IGFolder addFolder(
			String uuid, long userId, long plid, long parentFolderId,
			String name, String description, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addFolder(
			uuid, userId, plid, parentFolderId, name, description,
			Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public IGFolder addFolder(
			long userId, long plid, long parentFolderId, String name,
			String description, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		return addFolder(
			null, userId, plid, parentFolderId, name, description, null, null,
			communityPermissions, guestPermissions);
	}

	public IGFolder addFolder(
			String uuid, long userId, long plid, long parentFolderId,
			String name, String description, Boolean addCommunityPermissions,
			Boolean addGuestPermissions, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		long groupId = PortalUtil.getPortletGroupId(plid);

		return addFolderToGroup(
			uuid, userId, groupId, parentFolderId, name, description,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public IGFolder addFolderToGroup(
			String uuid, long userId, long groupId, long parentFolderId,
			String name, String description, Boolean addCommunityPermissions,
			Boolean addGuestPermissions, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		// Folder

		User user = userPersistence.findByPrimaryKey(userId);
		parentFolderId = getParentFolderId(groupId, parentFolderId);
		Date now = new Date();

		validate(groupId, parentFolderId, name);

		long folderId = counterLocalService.increment();

		IGFolder folder = igFolderPersistence.create(folderId);

		folder.setUuid(uuid);
		folder.setGroupId(groupId);
		folder.setCompanyId(user.getCompanyId());
		folder.setUserId(user.getUserId());
		folder.setCreateDate(now);
		folder.setModifiedDate(now);
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);

		igFolderPersistence.update(folder, false);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addFolderResources(
				folder, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addFolderResources(folder, communityPermissions, guestPermissions);
		}

		return folder;
	}

	public void addFolderResources(
			long folderId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		IGFolder folder = igFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(
			folder, addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
			IGFolder folder, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			IGFolder.class.getName(), folder.getFolderId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
			long folderId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		IGFolder folder = igFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(folder, communityPermissions, guestPermissions);
	}

	public void addFolderResources(
			IGFolder folder, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			IGFolder.class.getName(), folder.getFolderId(),
			communityPermissions, guestPermissions);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		IGFolder folder = igFolderPersistence.findByPrimaryKey(folderId);

		deleteFolder(folder);
	}

	public void deleteFolder(IGFolder folder)
		throws PortalException, SystemException {

		// Folders

		List<IGFolder> folders = igFolderPersistence.findByG_P(
			folder.getGroupId(), folder.getFolderId());

		for (IGFolder curFolder : folders) {
			deleteFolder(curFolder);
		}

		// Images

		igImageLocalService.deleteImages(folder.getFolderId());

		// Resources

		resourceLocalService.deleteResource(
			folder.getCompanyId(), IGFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, folder.getFolderId());

		// Folder

		igFolderPersistence.remove(folder.getFolderId());
	}

	public void deleteFolders(long groupId)
		throws PortalException, SystemException {

		List<IGFolder> folders = igFolderPersistence.findByG_P(
			groupId, IGFolderImpl.DEFAULT_PARENT_FOLDER_ID);

		for (IGFolder folder : folders) {
			deleteFolder(folder);
		}
	}

	public IGFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return igFolderPersistence.findByPrimaryKey(folderId);
	}

	public IGFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		return igFolderPersistence.findByG_P_N(groupId, parentFolderId, name);
	}

	public List<IGFolder> getFolders(long groupId) throws SystemException {
		return igFolderPersistence.findByGroupId(groupId);
	}

	public List<IGFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return igFolderPersistence.findByG_P(groupId, parentFolderId);
	}

	public List<IGFolder> getFolders(
			long groupId, long parentFolderId, int start, int end)
		throws SystemException {

		return igFolderPersistence.findByG_P(
			groupId, parentFolderId, start, end);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return igFolderPersistence.countByG_P(groupId, parentFolderId);
	}

	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		List<IGFolder> folders = igFolderPersistence.findByG_P(
			groupId, folderId);

		for (IGFolder folder : folders) {
			folderIds.add(folder.getFolderId());

			getSubfolderIds(
				folderIds, folder.getGroupId(), folder.getFolderId());
		}
	}

	public void reIndex(String[] ids) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		long companyId = GetterUtil.getLong(ids[0]);

		try {
			List<IGFolder> folders = igFolderPersistence.findByCompanyId(
				companyId);

			for (IGFolder folder : folders) {
				long folderId = folder.getFolderId();

				List<IGImage> images = igImagePersistence.findByFolderId(
					folderId);

				for (IGImage image : images) {
					long groupId = folder.getGroupId();
					long imageId = image.getImageId();
					String name = image.getName();
					String description = image.getDescription();

					String[] tagsEntries = tagsEntryLocalService.getEntryNames(
						IGImage.class.getName(), imageId);

					try {
						Document doc = Indexer.getImageDocument(
							companyId, groupId, folderId, imageId, name,
							description, tagsEntries);

						SearchEngineUtil.addDocument(companyId, doc);
					}
					catch (Exception e1) {
						_log.error("Reindexing " + imageId, e1);
					}
				}
			}
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e2) {
			throw new SystemException(e2);
		}
	}

	public Hits search(
			long companyId, long groupId, long[] folderIds, String keywords,
			int start, int end)
		throws SystemException {

		try {
			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create();

			contextQuery.addRequiredTerm(Field.PORTLET_ID, Indexer.PORTLET_ID);

			if (groupId > 0) {
				contextQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			}

			if ((folderIds != null) && (folderIds.length > 0)) {
				BooleanQuery folderIdsQuery = BooleanQueryFactoryUtil.create();

				for (long folderId : folderIds) {
					TermQuery termQuery = TermQueryFactoryUtil.create(
						"folderId", folderId);

					folderIdsQuery.add(termQuery, BooleanClauseOccur.SHOULD);
				}

				contextQuery.add(folderIdsQuery, BooleanClauseOccur.MUST);
			}

			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			if (Validator.isNotNull(keywords)) {
				searchQuery.addTerm(Field.DESCRIPTION, keywords);
				searchQuery.addTerm(Field.TAGS_ENTRIES, keywords);
			}

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			if (searchQuery.clauses().size() > 0) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			return SearchEngineUtil.search(companyId, fullQuery, start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public IGFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			boolean mergeWithParentFolder)
		throws PortalException, SystemException {

		// Folder

		IGFolder folder = igFolderPersistence.findByPrimaryKey(folderId);

		parentFolderId = getParentFolderId(folder, parentFolderId);

		validate(
			folder.getFolderId(), folder.getGroupId(), parentFolderId, name);

		folder.setModifiedDate(new Date());
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);

		igFolderPersistence.update(folder, false);

		// Merge folders

		if (mergeWithParentFolder && (folderId != parentFolderId) &&
			(parentFolderId != IGFolderImpl.DEFAULT_PARENT_FOLDER_ID)) {

			mergeFolders(folder, parentFolderId);
		}

		return folder;
	}

	protected long getParentFolderId(long groupId, long parentFolderId)
		throws SystemException {

		if (parentFolderId != IGFolderImpl.DEFAULT_PARENT_FOLDER_ID) {
			IGFolder parentFolder = igFolderPersistence.fetchByPrimaryKey(
				parentFolderId);

			if ((parentFolder == null) ||
				(groupId != parentFolder.getGroupId())) {

				parentFolderId = IGFolderImpl.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return parentFolderId;
	}

	protected long getParentFolderId(IGFolder folder, long parentFolderId)
		throws SystemException {

		if (parentFolderId == IGFolderImpl.DEFAULT_PARENT_FOLDER_ID) {
			return parentFolderId;
		}

		if (folder.getFolderId() == parentFolderId) {
			return folder.getParentFolderId();
		}
		else {
			IGFolder parentFolder = igFolderPersistence.fetchByPrimaryKey(
				parentFolderId);

			if ((parentFolder == null) ||
				(folder.getGroupId() != parentFolder.getGroupId())) {

				return folder.getParentFolderId();
			}

			List<Long> subfolderIds = new ArrayList<Long>();

			getSubfolderIds(
				subfolderIds, folder.getGroupId(), folder.getFolderId());

			if (subfolderIds.contains(parentFolderId)) {
				return folder.getParentFolderId();
			}

			return parentFolderId;
		}
	}

	protected void mergeFolders(IGFolder fromFolder, long toFolderId)
		throws PortalException, SystemException {

		List<IGFolder> folders = igFolderPersistence.findByG_P(
			fromFolder.getGroupId(), fromFolder.getFolderId());

		for (IGFolder folder : folders) {
			mergeFolders(folder, toFolderId);
		}

		List<IGImage> images = igImagePersistence.findByFolderId(
			fromFolder.getFolderId());

		for (IGImage image : images) {
			image.setFolderId(toFolderId);

			igImagePersistence.update(image, false);
		}

		igFolderPersistence.remove(fromFolder.getFolderId());
	}

	protected void validate(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		long folderId = 0;

		validate(folderId, groupId, parentFolderId, name);
	}

	protected void validate(
			long folderId, long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		if ((Validator.isNull(name)) || (name.indexOf("\\\\") != -1) ||
			(name.indexOf("//") != -1)) {

			throw new FolderNameException();
		}

		IGFolder folder = igFolderPersistence.fetchByG_P_N(
			groupId, parentFolderId, name);

		if ((folder != null) && (folder.getFolderId() != folderId)) {
			throw new DuplicateFolderNameException();
		}

		if (name.indexOf(StringPool.PERIOD) != -1) {
			String nameWithExtension = name;

			name = FileUtil.stripExtension(nameWithExtension);

			List<IGImage> images = igImagePersistence.findByF_N(
				parentFolderId, name);

			for (IGImage image : images) {
				if (nameWithExtension.equals(image.getNameWithExtension())) {
					throw new DuplicateFolderNameException();
				}
			}
		}
	}

	private static Log _log = LogFactory.getLog(IGFolderLocalServiceImpl.class);

}