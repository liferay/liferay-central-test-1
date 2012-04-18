/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.trash.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.impl.TrashEntryImpl;
import com.liferay.portlet.trash.service.base.TrashEntryLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * The trash local service is responsible for accessing, creating, modifying and
 * deleting trash entries in the Recycle Bin.
 *
 * <p>
 * Here is a listing of entities that can be moved to trash:
 * </p>
 *
 * <ul>
 * <li>
 * @link com.liferay.portlet.blogs.model.BlogsEntry
 * </li>
 * </ul>
 *
 * @author Zsolt Berentey
 */
public class TrashEntryLocalServiceImpl extends TrashEntryLocalServiceBaseImpl {

	/**
	 * Moves an entry to trash.
	 *
	 * @param companyId the primary key of the company
	 * @param groupId the primary key of the group
	 * @param className the class name of the entity
	 * @param classPK the primary key of the entity
	 * @param status the status of the entity prior to be moved to trash
	 * @param typeSettingsProperties
	 * @return the trashEntry
	 * @throws SystemException if a system exception occurred
	 */
	public TrashEntry addTrashEntry(
			long companyId, long groupId, String className, long classPK,
			int status, UnicodeProperties typeSettingsProperties)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		long entryId = counterLocalService.increment();

		TrashEntryImpl trashEntry =
			(TrashEntryImpl)trashEntryPersistence.create(entryId);

		trashEntry.setCompanyId(companyId);
		trashEntry.setGroupId(groupId);
		trashEntry.setClassNameId(classNameId);
		trashEntry.setClassPK(classPK);
		trashEntry.setStatus(status);
		trashEntry.setTrashedDate(new Date());

		if (typeSettingsProperties != null) {
			trashEntry.setTypeSettingsProperties(typeSettingsProperties);
		}

		trashEntryPersistence.update(trashEntry, false);

		return trashEntry;
	}

	/**
	 * Deletes an entry using its class name and primary key.
	 *
	 * @param className the class name of entry's entity
	 * @param classPK the primary key of the entry
	 * @throws PortalException if there are no permissions to delete the entry
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteEntry(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		trashEntryPersistence.removeByC_C(classNameId, classPK);
	}

	/**
	 * Returns the entry using its primary key.
	 *
	 * @param entryId the primary key of the entry
	 * @return the trash entry
	 * @throws SystemException if a system exception occurred
	 */
	public TrashEntry fetchEntry(long entryId) throws SystemException {
		return trashEntryPersistence.fetchByPrimaryKey(entryId);
	}

	/**
	 * Returns the entry using its entity's class name and primary key.
	 *
	 * @param className the class name of the entity
	 * @param classPK the primary key of the entity
	 * @return the trash entry
	 * @throws SystemException if a system exception occurred
	 */
	public TrashEntry fetchEntry(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return trashEntryPersistence.fetchByC_C(classNameId, classPK);
	}

	/**
	 * Returns the trash entries matching a specified groupId.
	 *
	 * @param groupId the primary key of the group
	 * @return the trash entries with the groupId
	 * @throws SystemException if a system exception occurred
	 */
	public List<TrashEntry> getEntries(long groupId) throws SystemException {
		return trashEntryPersistence.findByGroupId(groupId);
	}

	/**
	 * Returns a range with the trash entries matching a specified groupId.
	 *
	 * @param groupId the primary key of the group
	 * @param start the lower bound of the range of trash entries to return
	 * @param end the upper bound of the range of trash entries to return (not
	 *        inclusive)
	 * @return the range of trash entries associated with the groupId
	 * @throws SystemException if a system exception occurred
	 */
	public List<TrashEntry> getEntries(long groupId, int start, int end)
		throws SystemException {

		return trashEntryPersistence.findByGroupId(groupId, start, end);
	}

	/**
	 * Returns the number of trash entries that match the groupId.
	 *
	 * @param groupId the primary key of the group
	 * @return the number of matching trash entries
	 * @throws SystemException if a system exception occurred
	 */
	public int getEntriesCount(long groupId) throws SystemException {
		return trashEntryPersistence.countByGroupId(groupId);
	}

	/**
	 * Returns the entry using its primary key.
	 *
	 * @param entryId the primary key of the trash entry
	 * @return the trash entry
	 * @throws PortalException
	 * @throws SystemException if a system exception occurred
	 */
	public TrashEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return trashEntryPersistence.findByPrimaryKey(entryId);
	}

	/**
	 * Returns the entry using its entity's class name and primary key.
	 *
	 * @param className the class name of the entity
	 * @param classPK the primary key of the entity
	 * @return the trash entry
	 * @throws PortalException if a trash entry with the primary key could not
	 *         be found
	 * @throws SystemException if a system exception occurred
	 */
	public TrashEntry getEntry(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return trashEntryPersistence.findByC_C(classNameId, classPK);
	}

}