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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.lock.ExpiredLockException;
import com.liferay.lock.InvalidLockException;
import com.liferay.lock.NoSuchLockException;
import com.liferay.lock.model.Lock;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.File;

import java.rmi.RemoteException;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="DLFileEntryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLFileEntryServiceImpl extends DLFileEntryServiceBaseImpl {

	public DLFileEntry addFileEntry(
			long folderId, String name, String title, String description,
			String[] tagsEntries, String extraSettings, File file,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), folderId, ActionKeys.ADD_DOCUMENT);

		return dlFileEntryLocalService.addFileEntry(
			getUserId(), folderId, name, title, description, tagsEntries,
			extraSettings, file, addCommunityPermissions,
			addGuestPermissions);
	}

	public DLFileEntry addFileEntry(
			long folderId, String name, String title, String description,
			String[] tagsEntries, String extraSettings, byte[] bytes,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), folderId, ActionKeys.ADD_DOCUMENT);

		return dlFileEntryLocalService.addFileEntry(
			getUserId(), folderId, name, title, description, tagsEntries,
			extraSettings, bytes, addCommunityPermissions,
			addGuestPermissions);
	}

	public DLFileEntry addFileEntry(
			long folderId, String name, String title, String description,
			String[] tagsEntries, String extraSettings, File file,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), folderId, ActionKeys.ADD_DOCUMENT);

		return dlFileEntryLocalService.addFileEntry(
			getUserId(), folderId, name, title, description, tagsEntries,
			extraSettings, file, communityPermissions, guestPermissions);
	}

	public DLFileEntry addFileEntry(
			long folderId, String name, String title, String description,
			String[] tagsEntries, String extraSettings, byte[] bytes,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), folderId, ActionKeys.ADD_DOCUMENT);

		return dlFileEntryLocalService.addFileEntry(
			getUserId(), folderId, name, title, description, tagsEntries,
			extraSettings, bytes, communityPermissions, guestPermissions);
	}

	public void deleteFileEntry(long folderId, String name)
		throws PortalException, RemoteException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), folderId, name, ActionKeys.DELETE);

		boolean hasLock = hasFileEntryLock(folderId, name);

		if (!hasLock) {

			// Lock

			lockFileEntry(folderId, name);
		}

		try {
			dlFileEntryLocalService.deleteFileEntry(folderId, name);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFileEntry(folderId, name);
			}
		}
	}

	public void deleteFileEntry(long folderId, String name, double version)
		throws PortalException, RemoteException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), folderId, name, ActionKeys.DELETE);

		boolean hasLock = hasFileEntryLock(folderId, name);

		if (!hasLock) {

			// Lock

			lockFileEntry(folderId, name);
		}

		try {
			dlFileEntryLocalService.deleteFileEntry(folderId, name, version);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFileEntry(folderId, name);
			}
		}
	}

	public void deleteFileEntryByTitle(long folderId, String titleWithExtension)
		throws PortalException, RemoteException, SystemException {

		DLFileEntry fileEntry = getFileEntryByTitle(
			folderId, titleWithExtension);

		deleteFileEntry(folderId, fileEntry.getName());
	}

	public List<DLFileEntry> getFileEntries(long folderId)
		throws PortalException, SystemException {

		List<DLFileEntry> fileEntries = dlFileEntryLocalService.getFileEntries(
			folderId);

		Iterator<DLFileEntry> itr = fileEntries.iterator();

		while (itr.hasNext()) {
			DLFileEntry fileEntry = itr.next();

			if (!DLFileEntryPermission.contains(
					getPermissionChecker(), fileEntry, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return fileEntries;
	}

	public DLFileEntry getFileEntry(long folderId, String name)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), folderId, name, ActionKeys.VIEW);

		return dlFileEntryLocalService.getFileEntry(folderId, name);
	}

	public DLFileEntry getFileEntryByTitle(
			long folderId, String titleWithExtension)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlFileEntryLocalService.getFileEntryByTitle(
			folderId, titleWithExtension);

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntry, ActionKeys.VIEW);

		return fileEntry;
	}

	public boolean hasFileEntryLock(long folderId, String name)
		throws PortalException, RemoteException, SystemException {

		String lockId = DLUtil.getLockId(folderId, name);

		boolean hasLock = lockService.hasLock(
			DLFileEntry.class.getName(), lockId, getUserId());

		if (!hasLock) {
			hasLock = dlFolderService.hasInheritableLock(folderId);
		}

		return hasLock;
	}

	public Lock lockFileEntry(long folderId, String name)
		throws PortalException, RemoteException, SystemException {

		return lockFileEntry(
			folderId, name, null, DLFileEntryImpl.LOCK_EXPIRATION_TIME);
	}

	public Lock lockFileEntry(
			long folderId, String name, String owner, long expirationTime)
		throws PortalException, RemoteException, SystemException {

		if ((expirationTime <= 0) ||
			(expirationTime > DLFileEntryImpl.LOCK_EXPIRATION_TIME)) {

			expirationTime = DLFileEntryImpl.LOCK_EXPIRATION_TIME;
		}

		String lockId = DLUtil.getLockId(folderId, name);

		return lockService.lock(
			DLFileEntry.class.getName(), lockId, getUser().getUserId(), owner,
			expirationTime);
	}

	public Lock refreshFileEntryLock(String lockUuid, long expirationTime)
		throws PortalException, RemoteException {

		return lockService.refresh(lockUuid, expirationTime);
	}

	public void unlockFileEntry(long folderId, String name)
		throws RemoteException {

		String lockId = DLUtil.getLockId(folderId, name);

		lockService.unlock(DLFileEntry.class.getName(), lockId);
	}

	public void unlockFileEntry(long folderId, String name, String lockUuid)
		throws PortalException, RemoteException {

		String lockId = DLUtil.getLockId(folderId, name);

		if (Validator.isNotNull(lockUuid)) {
			try {
				Lock lock = lockService.getLock(
					DLFileEntry.class.getName(), lockId);

				if (!lock.getUuid().equals(lockUuid)) {
					throw new InvalidLockException("UUIDs do not match");
				}
			}
			catch (PortalException pe) {
				if (pe instanceof ExpiredLockException ||
					pe instanceof NoSuchLockException) {
				}
				else {
					throw pe;
				}
			}
		}

		lockService.unlock(DLFileEntry.class.getName(), lockId);
	}

	public DLFileEntry updateFileEntry(
			long folderId, long newFolderId, String name, String sourceFileName,
			String title, String description, String[] tagsEntries,
			String extraSettings, byte[] bytes)
		throws PortalException, RemoteException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), folderId, name, ActionKeys.UPDATE);

		boolean hasLock = hasFileEntryLock(folderId, name);

		if (!hasLock) {

			// Lock

			lockFileEntry(folderId, name);
		}

		DLFileEntry fileEntry = null;

		try {
			fileEntry = dlFileEntryLocalService.updateFileEntry(
				getUserId(), folderId, newFolderId, name, sourceFileName, title,
				description, tagsEntries, extraSettings, bytes);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFileEntry(folderId, name);
			}
		}

		return fileEntry;
	}

	public boolean verifyFileEntryLock(
			long folderId, String name, String lockUuid)
		throws PortalException, RemoteException, SystemException {

		boolean verified = false;

		try {
			String lockId = DLUtil.getLockId(folderId, name);

			Lock lock = lockService.getLock(
				DLFileEntry.class.getName(), lockId);

			if (lock.getUuid().equals(lockUuid)) {
				verified = true;
			}
		}
		catch (PortalException pe) {
			if (pe instanceof ExpiredLockException ||
				pe instanceof NoSuchLockException) {

				verified = dlFolderService.verifyInheritableLock(
					folderId, lockUuid);
			}
			else {
				throw pe;
			}
		}

		return verified;
	}

}