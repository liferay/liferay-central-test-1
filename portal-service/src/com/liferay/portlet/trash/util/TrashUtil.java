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

package com.liferay.portlet.trash.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.trash.util.comparator.EntryCreateDateComparator;
import com.liferay.portlet.trash.util.comparator.EntryTypeComparator;
import com.liferay.portlet.trash.util.comparator.EntryUserNameComparator;

/**
 * @author Sergio González
 */
public class TrashUtil {

	public static final int TRASH_DISABLED = 0;

	public static final int TRASH_DISABLED_BY_DEFAULT = 1;

	public static final int TRASH_ENABLED_BY_DEFAULT = 2;

	public static OrderByComparator getEntryOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("removed-by")) {
			orderByComparator = new EntryUserNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("removed-date")) {
			orderByComparator = new EntryCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("type")) {
			orderByComparator = new EntryTypeComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static boolean isTrashEnabled(long groupId)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		UnicodeProperties typeSettingsProperties =
			group.getTypeSettingsProperties();

		int trashEnabled = PrefsPropsUtil.getInteger(
			group.getCompanyId(), PropsKeys.TRASH_ENABLED);

		if (trashEnabled == TRASH_DISABLED) {
			return false;
		}

		return GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("trashEnabled"),
			(trashEnabled == TRASH_ENABLED_BY_DEFAULT));
	}

}