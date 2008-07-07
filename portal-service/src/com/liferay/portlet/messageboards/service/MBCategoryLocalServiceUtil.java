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

package com.liferay.portlet.messageboards.service;


/**
 * <a href="MBCategoryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.messageboards.service.MBCategoryLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.messageboards.service.MBCategoryLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.MBCategoryLocalService
 * @see com.liferay.portlet.messageboards.service.MBCategoryLocalServiceFactory
 *
 */
public class MBCategoryLocalServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBCategory addMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.addMBCategory(mbCategory);
	}

	public static void deleteMBCategory(long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		mbCategoryLocalService.deleteMBCategory(categoryId);
	}

	public static void deleteMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		mbCategoryLocalService.deleteMBCategory(mbCategory);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory getMBCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.getMBCategory(categoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory updateMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.updateMBCategory(mbCategory);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory addCategory(
		long userId, long plid, long parentCategoryId, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.addCategory(userId, plid,
			parentCategoryId, name, description, addCommunityPermissions,
			addGuestPermissions);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory addCategory(
		java.lang.String uuid, long userId, long plid, long parentCategoryId,
		java.lang.String name, java.lang.String description,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.addCategory(uuid, userId, plid,
			parentCategoryId, name, description, addCommunityPermissions,
			addGuestPermissions);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory addCategory(
		long userId, long plid, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.addCategory(userId, plid,
			parentCategoryId, name, description, communityPermissions,
			guestPermissions);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory addCategory(
		java.lang.String uuid, long userId, long plid, long parentCategoryId,
		java.lang.String name, java.lang.String description,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.addCategory(uuid, userId, plid,
			parentCategoryId, name, description, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public static void addCategoryResources(long categoryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		mbCategoryLocalService.addCategoryResources(categoryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		mbCategoryLocalService.addCategoryResources(category,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addCategoryResources(long categoryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		mbCategoryLocalService.addCategoryResources(categoryId,
			communityPermissions, guestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		mbCategoryLocalService.addCategoryResources(category,
			communityPermissions, guestPermissions);
	}

	public static void deleteCategories(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		mbCategoryLocalService.deleteCategories(groupId);
	}

	public static void deleteCategory(long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		mbCategoryLocalService.deleteCategory(categoryId);
	}

	public static void deleteCategory(
		com.liferay.portlet.messageboards.model.MBCategory category)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		mbCategoryLocalService.deleteCategory(category);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long parentCategoryId)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.getCategories(groupId, parentCategoryId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.getCategories(groupId, parentCategoryId,
			start, end);
	}

	public static int getCategoriesCount(long groupId)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.getCategoriesCount(groupId);
	}

	public static int getCategoriesCount(long groupId, long parentCategoryId)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.getCategoriesCount(groupId,
			parentCategoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory getCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.getCategory(categoryId);
	}

	public static void getSubcategoryIds(java.util.List<Long> categoryIds,
		long groupId, long categoryId)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		mbCategoryLocalService.getSubcategoryIds(categoryIds, groupId,
			categoryId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getSubscribedCategories(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.getSubscribedCategories(groupId, userId,
			start, end);
	}

	public static int getSubscribedCategoriesCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.getSubscribedCategoriesCount(groupId,
			userId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory getSystemCategory()
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.getSystemCategory();
	}

	public static void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		mbCategoryLocalService.reIndex(ids);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, long[] categoryIds, long threadId,
		java.lang.String keywords, int start, int end)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.search(companyId, groupId, categoryIds,
			threadId, keywords, start, end);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentCategory)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.updateCategory(categoryId,
			parentCategoryId, name, description, mergeWithParentCategory);
	}

	public static void subscribeCategory(long userId, long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		mbCategoryLocalService.subscribeCategory(userId, categoryId);
	}

	public static void unsubscribeCategory(long userId, long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		mbCategoryLocalService.unsubscribeCategory(userId, categoryId);
	}
}