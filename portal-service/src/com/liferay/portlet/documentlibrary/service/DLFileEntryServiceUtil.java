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

package com.liferay.portlet.documentlibrary.service;


/**
 * <a href="DLFileEntryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.documentlibrary.service.DLFileEntryService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.documentlibrary.service.DLFileEntryService
 *
 */
public class DLFileEntryServiceUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long folderId, java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String[] tagsEntries,
		java.lang.String extraSettings, java.io.File file,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		return getService()
				   .addFileEntry(folderId, name, title, description,
			tagsEntries, extraSettings, file, addCommunityPermissions,
			addGuestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long folderId, java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String[] tagsEntries,
		java.lang.String extraSettings, byte[] bytes,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		return getService()
				   .addFileEntry(folderId, name, title, description,
			tagsEntries, extraSettings, bytes, addCommunityPermissions,
			addGuestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long folderId, java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String[] tagsEntries,
		java.lang.String extraSettings, java.io.File file,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		return getService()
				   .addFileEntry(folderId, name, title, description,
			tagsEntries, extraSettings, file, communityPermissions,
			guestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long folderId, java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String[] tagsEntries,
		java.lang.String extraSettings, byte[] bytes,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		return getService()
				   .addFileEntry(folderId, name, title, description,
			tagsEntries, extraSettings, bytes, communityPermissions,
			guestPermissions);
	}

	public static void deleteFileEntry(long folderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		getService().deleteFileEntry(folderId, name);
	}

	public static void deleteFileEntry(long folderId, java.lang.String name,
		double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		getService().deleteFileEntry(folderId, name, version);
	}

	public static void deleteFileEntryByTitle(long folderId,
		java.lang.String titleWithExtension)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		getService().deleteFileEntryByTitle(folderId, titleWithExtension);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		return getService().getFileEntries(folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long folderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		return getService().getFileEntry(folderId, name);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByTitle(
		long folderId, java.lang.String titleWithExtension)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		return getService().getFileEntryByTitle(folderId, titleWithExtension);
	}

	public static boolean hasFileEntryLock(long folderId, java.lang.String name)
		throws com.liferay.portal.PortalException, java.rmi.RemoteException {
		return getService().hasFileEntryLock(folderId, name);
	}

	public static com.liferay.lock.model.Lock lockFileEntry(long folderId,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		return getService().lockFileEntry(folderId, name);
	}

	public static com.liferay.lock.model.Lock lockFileEntry(long folderId,
		java.lang.String name, java.lang.String owner, long expirationTime)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		return getService().lockFileEntry(folderId, name, owner, expirationTime);
	}

	public static com.liferay.lock.model.Lock refreshFileEntryLock(
		java.lang.String lockUuid, long expirationTime)
		throws com.liferay.portal.PortalException, java.rmi.RemoteException {
		return getService().refreshFileEntryLock(lockUuid, expirationTime);
	}

	public static void unlockFileEntry(long folderId, java.lang.String name)
		throws java.rmi.RemoteException {
		getService().unlockFileEntry(folderId, name);
	}

	public static void unlockFileEntry(long folderId, java.lang.String name,
		java.lang.String lockUuid)
		throws com.liferay.portal.PortalException, java.rmi.RemoteException {
		getService().unlockFileEntry(folderId, name, lockUuid);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long folderId, long newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String[] tagsEntries,
		java.lang.String extraSettings, byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		return getService()
				   .updateFileEntry(folderId, newFolderId, name,
			sourceFileName, title, description, tagsEntries, extraSettings,
			bytes);
	}

	public static boolean verifyFileEntryLock(long folderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.PortalException, java.rmi.RemoteException {
		return getService().verifyFileEntryLock(folderId, name, lockUuid);
	}

	public static DLFileEntryService getService() {
		if (_service == null) {
			throw new RuntimeException("DLFileEntryService is not set");
		}

		return _service;
	}

	public void setService(DLFileEntryService service) {
		_service = service;
	}

	private static DLFileEntryService _service;
}