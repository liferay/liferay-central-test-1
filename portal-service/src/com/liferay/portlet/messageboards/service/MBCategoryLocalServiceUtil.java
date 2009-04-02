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
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.MBCategoryLocalService
 *
 */
public class MBCategoryLocalServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBCategory addMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.SystemException {
		return getService().addMBCategory(mbCategory);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory createMBCategory(
		long categoryId) {
		return getService().createMBCategory(categoryId);
	}

	public static void deleteMBCategory(long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteMBCategory(categoryId);
	}

	public static void deleteMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.SystemException {
		getService().deleteMBCategory(mbCategory);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory getMBCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getMBCategory(categoryId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getMBCategories(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getMBCategories(start, end);
	}

	public static int getMBCategoriesCount()
		throws com.liferay.portal.SystemException {
		return getService().getMBCategoriesCount();
	}

	public static com.liferay.portlet.messageboards.model.MBCategory updateMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.SystemException {
		return getService().updateMBCategory(mbCategory);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory updateMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateMBCategory(mbCategory, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String emailAddress,
		java.lang.String inProtocol, java.lang.String inServerName,
		int inServerPort, boolean inUseSSL, java.lang.String inUserName,
		java.lang.String inPassword, int inReadInterval,
		java.lang.String outEmailAddress, boolean outCustom,
		java.lang.String outServerName, int outServerPort, boolean outUseSSL,
		java.lang.String outUserName, java.lang.String outPassword,
		boolean mailingListActive,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addCategory(userId, parentCategoryId, name, description,
			emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
			inUserName, inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive, serviceContext);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory addCategory(
		java.lang.String uuid, long userId, long parentCategoryId,
		java.lang.String name, java.lang.String description,
		java.lang.String emailAddress, java.lang.String inProtocol,
		java.lang.String inServerName, int inServerPort, boolean inUseSSL,
		java.lang.String inUserName, java.lang.String inPassword,
		int inReadInterval, java.lang.String outEmailAddress,
		boolean outCustom, java.lang.String outServerName, int outServerPort,
		boolean outUseSSL, java.lang.String outUserName,
		java.lang.String outPassword, boolean mailingListActive,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addCategory(uuid, userId, parentCategoryId, name,
			description, emailAddress, inProtocol, inServerName, inServerPort,
			inUseSSL, inUserName, inPassword, inReadInterval, outEmailAddress,
			outCustom, outServerName, outServerPort, outUseSSL, outUserName,
			outPassword, mailingListActive, serviceContext);
	}

	public static void addCategoryResources(long categoryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addCategoryResources(categoryId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addCategoryResources(category, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addCategoryResources(long categoryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addCategoryResources(categoryId, communityPermissions,
			guestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addCategoryResources(category, communityPermissions,
			guestPermissions);
	}

	public static void deleteCategories(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteCategories(groupId);
	}

	public static void deleteCategory(long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteCategory(categoryId);
	}

	public static void deleteCategory(
		com.liferay.portlet.messageboards.model.MBCategory category)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteCategory(category);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId) throws com.liferay.portal.SystemException {
		return getService().getCategories(groupId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long parentCategoryId)
		throws com.liferay.portal.SystemException {
		return getService().getCategories(groupId, parentCategoryId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getCategories(groupId, parentCategoryId, start, end);
	}

	public static int getCategoriesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return getService().getCategoriesCount(groupId);
	}

	public static int getCategoriesCount(long groupId, long parentCategoryId)
		throws com.liferay.portal.SystemException {
		return getService().getCategoriesCount(groupId, parentCategoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory getCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getCategory(categoryId);
	}

	public static void getSubcategoryIds(java.util.List<Long> categoryIds,
		long groupId, long categoryId)
		throws com.liferay.portal.SystemException {
		getService().getSubcategoryIds(categoryIds, groupId, categoryId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getSubscribedCategories(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getSubscribedCategories(groupId, userId, start, end);
	}

	public static int getSubscribedCategoriesCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return getService().getSubscribedCategoriesCount(groupId, userId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory getSystemCategory()
		throws com.liferay.portal.SystemException {
		return getService().getSystemCategory();
	}

	public static void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		getService().reIndex(ids);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, long userId, long[] categoryIds, long threadId,
		java.lang.String keywords, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService()
				   .search(companyId, groupId, userId, categoryIds, threadId,
			keywords, start, end);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String emailAddress,
		java.lang.String inProtocol, java.lang.String inServerName,
		int inServerPort, boolean inUseSSL, java.lang.String inUserName,
		java.lang.String inPassword, int inReadInterval,
		java.lang.String outEmailAddress, boolean outCustom,
		java.lang.String outServerName, int outServerPort, boolean outUseSSL,
		java.lang.String outUserName, java.lang.String outPassword,
		boolean mailingListActive, boolean mergeWithParentCategory)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateCategory(categoryId, parentCategoryId, name,
			description, emailAddress, inProtocol, inServerName, inServerPort,
			inUseSSL, inUserName, inPassword, inReadInterval, outEmailAddress,
			outCustom, outServerName, outServerPort, outUseSSL, outUserName,
			outPassword, mailingListActive, mergeWithParentCategory);
	}

	public static void subscribeCategory(long userId, long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().subscribeCategory(userId, categoryId);
	}

	public static void unsubscribeCategory(long userId, long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().unsubscribeCategory(userId, categoryId);
	}

	public static MBCategoryLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("MBCategoryLocalService is not set");
		}

		return _service;
	}

	public void setService(MBCategoryLocalService service) {
		_service = service;
	}

	private static MBCategoryLocalService _service;
}