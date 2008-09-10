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

package com.liferay.portlet.tags.service.permission;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.model.TagsEntryConstants;
import com.liferay.portlet.tags.service.TagsEntryLocalServiceUtil;

/**
 * <a href="TagsEntryPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 *
 */
public class TagsEntryPermission {

	public static void check(
			PermissionChecker permissionChecker, long plid, long entryId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, plid, entryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, entryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, TagsEntry entry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, entry, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long plid, long entryId,
			String actionId)
		throws PortalException, SystemException {

		if (entryId == TagsEntryConstants.DEFAULT_PARENT_ENTRY_ID) {
			return PortletPermissionUtil.contains(
				permissionChecker, plid, PortletKeys.TAGS_ADMIN, actionId);
		}
		else {
			return contains(permissionChecker, entryId, actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId,
			String actionId)
		throws PortalException, SystemException {

		TagsEntry entry = TagsEntryLocalServiceUtil.getEntry(entryId);

		return contains(permissionChecker, entry, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, TagsEntry entry, String actionId) {

		return permissionChecker.hasPermission(
			entry.getGroupId(), TagsEntry.class.getName(), entry.getEntryId(),
			actionId);
	}

}