/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerBag;
import com.liferay.portal.service.base.PermissionServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.permission.BlogsEntryPermission;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.permission.BookmarksFolderPermission;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.permission.CalEventPermission;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.service.permission.IGFolderPermission;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.permission.JournalFeedPermission;
import com.liferay.portlet.journal.service.permission.JournalStructurePermission;
import com.liferay.portlet.journal.service.permission.JournalTemplatePermission;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.permission.PollsQuestionPermission;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.service.permission.ShoppingCategoryPermission;
import com.liferay.portlet.shopping.service.permission.ShoppingItemPermission;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.service.permission.SCFrameworkVersionPermission;
import com.liferay.portlet.softwarecatalog.service.permission.SCProductEntryPermission;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;

/**
 * <a href="PermissionServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 *
 */
public class PermissionServiceImpl extends PermissionServiceBaseImpl {

	public void checkPermission(long groupId, long resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);
	}

	public void checkPermission(long groupId, String name, long primKey)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, name, primKey);
	}

	public void checkPermission(long groupId, String name, String primKey)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, name, primKey);
	}

	public boolean hasGroupPermission(
			long groupId, String actionId, long resourceId)
		throws SystemException {

		return permissionLocalService.hasGroupPermission(
			groupId, actionId, resourceId);
	}

	public boolean hasUserPermission(
			long userId, String actionId, long resourceId)
		throws SystemException {

		return permissionLocalService.hasUserPermission(
			userId, actionId, resourceId);
	}

	public boolean hasUserPermissions(
			long userId, long groupId, String actionId, long[] resourceIds,
			PermissionCheckerBag permissionCheckerBag)
		throws SystemException {

		return permissionLocalService.hasUserPermissions(
			userId, groupId, actionId, resourceIds, permissionCheckerBag);
	}

	public void setGroupPermissions(
			long groupId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		permissionLocalService.setGroupPermissions(
			groupId, actionIds, resourceId);
	}

	public void setGroupPermissions(
			String className, String classPK, long groupId,
			String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		permissionLocalService.setGroupPermissions(
			className, classPK, groupId, actionIds, resourceId);
	}

	public void setOrgGroupPermissions(
			long organizationId, long groupId, String[] actionIds,
			long resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		permissionLocalService.setOrgGroupPermissions(
			organizationId, groupId, actionIds, resourceId);
	}

	public void setRolePermission(
			long roleId, long groupId, String name, int scope, String primKey,
			String actionId)
		throws PortalException, SystemException {

		checkPermission(
			getPermissionChecker(), groupId, Role.class.getName(), roleId);

		permissionLocalService.setRolePermission(
			roleId, getUser().getCompanyId(), name, scope, primKey, actionId);
	}

	public void setRolePermissions(
			long roleId, long groupId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		permissionLocalService.setRolePermissions(
			roleId, actionIds, resourceId);
	}

	public void setUserPermissions(
			long userId, long groupId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		permissionLocalService.setUserPermissions(
			userId, actionIds, resourceId);
	}

	public void unsetRolePermission(
			long roleId, long groupId, long permissionId)
		throws SystemException, PortalException {

		checkPermission(
			getPermissionChecker(), groupId, Role.class.getName(), roleId);

		permissionLocalService.unsetRolePermission(roleId, permissionId);
	}

	public void unsetRolePermission(
			long roleId, long groupId, String name, int scope, String primKey,
			String actionId)
		throws PortalException, SystemException {

		checkPermission(
			getPermissionChecker(), groupId, Role.class.getName(), roleId);

		permissionLocalService.unsetRolePermission(
			roleId, getUser().getCompanyId(), name, scope, primKey, actionId);
	}

	public void unsetRolePermissions(
			long roleId, long groupId, String name, int scope, String actionId)
		throws PortalException, SystemException {

		checkPermission(
			getPermissionChecker(), groupId, Role.class.getName(), roleId);

		permissionLocalService.unsetRolePermissions(
			roleId, getUser().getCompanyId(), name, scope, actionId);
	}

	public void unsetUserPermissions(
			long userId, long groupId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		permissionLocalService.unsetUserPermissions(
			userId, actionIds, resourceId);
	}

	protected void checkPermission(
			PermissionChecker permissionChecker, long groupId,
			long resourceId)
		throws PortalException, SystemException {

		Resource resource = resourcePersistence.findByPrimaryKey(resourceId);

		checkPermission(
			permissionChecker, groupId, resource.getName(),
			resource.getPrimKey().toString());
	}

	protected void checkPermission(
			PermissionChecker permissionChecker, long groupId, String name,
			long primKey)
		throws PortalException, SystemException {

		checkPermission(
			permissionChecker, groupId, name, String.valueOf(primKey));
	}

	protected void checkPermission(
			PermissionChecker permissionChecker, long groupId, String name,
			String primKey)
		throws PortalException, SystemException {

		if (name.equals(BlogsEntry.class.getName())) {
			BlogsEntryPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(BookmarksFolder.class.getName())) {
			BookmarksFolderPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(CalEvent.class.getName())) {
			CalEventPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(DLFolder.class.getName())) {
			DLFolderPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(Group.class.getName())) {
			GroupPermissionUtil.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(IGFolder.class.getName())) {
			IGFolderPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(JournalArticle.class.getName())) {
			JournalArticlePermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(JournalFeed.class.getName())) {
			JournalFeedPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(JournalStructure.class.getName())) {
			JournalStructurePermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(JournalTemplate.class.getName())) {
			JournalTemplatePermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(Layout.class.getName())) {
			long plid = GetterUtil.getLong(primKey);

			Layout layout = layoutPersistence.findByPrimaryKey(plid);

			GroupPermissionUtil.check(
				permissionChecker, layout.getGroupId(),
				ActionKeys.MANAGE_LAYOUTS);
		}
		else if (name.equals(MBCategory.class.getName())) {
			MBCategoryPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(PollsQuestion.class.getName())) {
			PollsQuestionPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(SCFrameworkVersion.class.getName())) {
			SCFrameworkVersionPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(SCProductEntry.class.getName())) {
			SCProductEntryPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(ShoppingCategory.class.getName())) {
			ShoppingCategoryPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(ShoppingItem.class.getName())) {
			ShoppingItemPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(User.class.getName())) {
			long userId = GetterUtil.getLong(primKey);

			User user = userPersistence.findByPrimaryKey(userId);

			UserPermissionUtil.check(
				permissionChecker, userId, user.getOrganizationIds(),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(WikiNode.class.getName())) {
			WikiNodePermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if ((primKey != null) &&
				 (primKey.indexOf(PortletConstants.LAYOUT_SEPARATOR) != -1)) {

			int pos = primKey.indexOf(PortletConstants.LAYOUT_SEPARATOR);

			long plid = GetterUtil.getLong(primKey.substring(0, pos));

			String portletId = primKey.substring(
				pos + PortletConstants.LAYOUT_SEPARATOR.length(),
				primKey.length());

			PortletPermissionUtil.check(
				permissionChecker, plid, portletId, ActionKeys.CONFIGURATION);
		}
		else if (!permissionChecker.hasPermission(
					groupId, name, primKey, ActionKeys.PERMISSIONS) &&
				 !permissionChecker.hasPermission(
					groupId, name, primKey, ActionKeys.DEFINE_PERMISSIONS)) {

			throw new PrincipalException();
		}
	}

}