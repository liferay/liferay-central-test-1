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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.util.PortletKeys;

/**
 * @author Brian Wing Shun Chan
 */
public class PrefsPlidUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public PrefsPlidUpgradeColumnImpl(
		PrefsOwnerIdUpgradeColumnImpl ownerIdColumn,
		UpgradeColumn layoutIdColumn, ValueMapper layoutPlidMapper) {

		super("plid");

		_ownerIdColumn = ownerIdColumn;
		_layoutIdColumn = layoutIdColumn;
		_layoutPlidMapper = layoutPlidMapper;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		Long oldGroupId = _ownerIdColumn.getOldGroupId();
		Long newGroupId = _ownerIdColumn.getNewGroupId();
		Boolean privateLayout = _ownerIdColumn.isPrivateLayout();
		String layoutId = (String)_layoutIdColumn.getOldValue();

		if ((!layoutId.equals("SHARED")) && (oldGroupId != null) &&
			(newGroupId != null) && (privateLayout != null)) {

			String oldOwnerId = null;

			if (privateLayout.booleanValue()) {
				oldOwnerId = "PRI.";
			}
			else {
				oldOwnerId = "PUB.";
			}

			oldOwnerId += oldGroupId.longValue();

			String oldPlidValue =
				"{layoutId=" + layoutId + ", ownerId=" + oldOwnerId + "}";

			return _layoutPlidMapper.getNewValue(oldPlidValue);
		}
		else {
			return new Long(PortletKeys.PREFS_PLID_SHARED);
		}
	}

	private PrefsOwnerIdUpgradeColumnImpl _ownerIdColumn;
	private UpgradeColumn _layoutIdColumn;
	private ValueMapper _layoutPlidMapper;

}