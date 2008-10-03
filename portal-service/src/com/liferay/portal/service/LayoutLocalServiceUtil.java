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

package com.liferay.portal.service;


/**
 * <a href="LayoutLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.LayoutLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.LayoutLocalService
 *
 */
public class LayoutLocalServiceUtil {
	public static com.liferay.portal.model.Layout addLayout(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.SystemException {
		return getService().addLayout(layout);
	}

	public static com.liferay.portal.model.Layout createLayout(long plid) {
		return getService().createLayout(plid);
	}

	public static void deleteLayout(long plid)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteLayout(plid);
	}

	public static void deleteLayout(com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.SystemException {
		getService().deleteLayout(layout);
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

	public static com.liferay.portal.model.Layout getLayout(long plid)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getLayout(plid);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getLayouts(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getLayouts(start, end);
	}

	public static int getLayoutsCount()
		throws com.liferay.portal.SystemException {
		return getService().getLayoutsCount();
	}

	public static com.liferay.portal.model.Layout updateLayout(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.SystemException {
		return getService().updateLayout(layout);
	}

	public static com.liferay.portal.model.Layout addLayout(long userId,
		long groupId, boolean privateLayout, long parentLayoutId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addLayout(userId, groupId, privateLayout, parentLayoutId,
			name, title, description, type, hidden, friendlyURL);
	}

	public static com.liferay.portal.model.Layout addLayout(long userId,
		long groupId, boolean privateLayout, long parentLayoutId,
		java.util.Map<java.util.Locale, String> localeNamesMap,
		java.util.Map<java.util.Locale, String> localeTitlesMap,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addLayout(userId, groupId, privateLayout, parentLayoutId,
			localeNamesMap, localeTitlesMap, description, type, hidden,
			friendlyURL);
	}

	public static com.liferay.portal.model.Layout addLayout(long userId,
		long groupId, boolean privateLayout, long parentLayoutId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL, long dlFolderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addLayout(userId, groupId, privateLayout, parentLayoutId,
			name, title, description, type, hidden, friendlyURL, dlFolderId);
	}

	public static com.liferay.portal.model.Layout addLayout(long userId,
		long groupId, boolean privateLayout, long parentLayoutId,
		java.util.Map<java.util.Locale, String> localeNamesMap,
		java.util.Map<java.util.Locale, String> localeTitlesMap,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL, long dlFolderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addLayout(userId, groupId, privateLayout, parentLayoutId,
			localeNamesMap, localeTitlesMap, description, type, hidden,
			friendlyURL, dlFolderId);
	}

	public static void deleteLayout(long groupId, boolean privateLayout,
		long layoutId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteLayout(groupId, privateLayout, layoutId);
	}

	public static void deleteLayout(com.liferay.portal.model.Layout layout,
		boolean updateLayoutSet)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteLayout(layout, updateLayoutSet);
	}

	public static void deleteLayouts(long groupId, boolean privateLayout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteLayouts(groupId, privateLayout);
	}

	public static byte[] exportLayouts(long groupId, boolean privateLayout,
		java.util.Map<String, String[]> parameterMap, java.util.Date startDate,
		java.util.Date endDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .exportLayouts(groupId, privateLayout, parameterMap,
			startDate, endDate);
	}

	public static byte[] exportLayouts(long groupId, boolean privateLayout,
		long[] layoutIds, java.util.Map<String, String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .exportLayouts(groupId, privateLayout, layoutIds,
			parameterMap, startDate, endDate);
	}

	public static byte[] exportPortletInfo(long plid,
		java.lang.String portletId,
		java.util.Map<String, String[]> parameterMap, java.util.Date startDate,
		java.util.Date endDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .exportPortletInfo(plid, portletId, parameterMap, startDate,
			endDate);
	}

	public static long getDefaultPlid(long groupId)
		throws com.liferay.portal.SystemException {
		return getService().getDefaultPlid(groupId);
	}

	public static long getDefaultPlid(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException {
		return getService().getDefaultPlid(groupId, privateLayout);
	}

	public static long getDefaultPlid(long groupId, boolean privateLayout,
		java.lang.String portletId) throws com.liferay.portal.SystemException {
		return getService().getDefaultPlid(groupId, privateLayout, portletId);
	}

	public static com.liferay.portal.model.Layout getDLFolderLayout(
		long dlFolderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getDLFolderLayout(dlFolderId);
	}

	public static com.liferay.portal.model.Layout getFriendlyURLLayout(
		long groupId, boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getFriendlyURLLayout(groupId, privateLayout, friendlyURL);
	}

	public static com.liferay.portal.model.Layout getLayout(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getLayout(groupId, privateLayout, layoutId);
	}

	public static com.liferay.portal.model.Layout getLayoutByIconImageId(
		long iconImageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getLayoutByIconImageId(iconImageId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException {
		return getService().getLayouts(groupId, privateLayout);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId)
		throws com.liferay.portal.SystemException {
		return getService().getLayouts(groupId, privateLayout, parentLayoutId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout, java.lang.String type)
		throws com.liferay.portal.SystemException {
		return getService().getLayouts(groupId, privateLayout, type);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService()
				   .getLayouts(groupId, privateLayout, parentLayoutId, start,
			end);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout, long[] layoutIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getLayouts(groupId, privateLayout, layoutIds);
	}

	public static com.liferay.portal.model.LayoutReference[] getLayouts(
		long companyId, java.lang.String portletId,
		java.lang.String preferencesKey, java.lang.String preferencesValue)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getLayouts(companyId, portletId, preferencesKey,
			preferencesValue);
	}

	public static long getNextLayoutId(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException {
		return getService().getNextLayoutId(groupId, privateLayout);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getNullFriendlyURLLayouts()
		throws com.liferay.portal.SystemException {
		return getService().getNullFriendlyURLLayouts();
	}

	public static void importLayouts(long userId, long groupId,
		boolean privateLayout, java.util.Map<String, String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.importLayouts(userId, groupId, privateLayout, parameterMap, file);
	}

	public static void importLayouts(long userId, long groupId,
		boolean privateLayout, java.util.Map<String, String[]> parameterMap,
		byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.importLayouts(userId, groupId, privateLayout, parameterMap, bytes);
	}

	public static void importLayouts(long userId, long groupId,
		boolean privateLayout, java.util.Map<String, String[]> parameterMap,
		java.io.InputStream is)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.importLayouts(userId, groupId, privateLayout, parameterMap, is);
	}

	public static void importPortletInfo(long userId, long plid,
		java.lang.String portletId,
		java.util.Map<String, String[]> parameterMap, java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.importPortletInfo(userId, plid, portletId, parameterMap, file);
	}

	public static void importPortletInfo(long userId, long plid,
		java.lang.String portletId,
		java.util.Map<String, String[]> parameterMap, java.io.InputStream is)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().importPortletInfo(userId, plid, portletId, parameterMap, is);
	}

	public static void setLayouts(long groupId, boolean privateLayout,
		long parentLayoutId, long[] layoutIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.setLayouts(groupId, privateLayout, parentLayoutId, layoutIds);
	}

	public static com.liferay.portal.model.Layout updateFriendlyURL(long plid,
		java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateFriendlyURL(plid, friendlyURL);
	}

	public static com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId,
		java.util.Map<java.util.Locale, String> localeNamesMap,
		java.util.Map<java.util.Locale, String> localeTitlesMap,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateLayout(groupId, privateLayout, layoutId,
			parentLayoutId, localeNamesMap, localeTitlesMap, description, type,
			hidden, friendlyURL);
	}

	public static com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId,
		java.util.Map<java.util.Locale, String> localeNamesMap,
		java.util.Map<java.util.Locale, String> localeTitlesMap,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL, java.lang.Boolean iconImage,
		byte[] iconBytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateLayout(groupId, privateLayout, layoutId,
			parentLayoutId, localeNamesMap, localeTitlesMap, description, type,
			hidden, friendlyURL, iconImage, iconBytes);
	}

	public static com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, java.lang.String typeSettings)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateLayout(groupId, privateLayout, layoutId, typeSettings);
	}

	public static com.liferay.portal.model.Layout updateLookAndFeel(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String themeId, java.lang.String colorSchemeId,
		java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateLookAndFeel(groupId, privateLayout, layoutId,
			themeId, colorSchemeId, css, wapTheme);
	}

	public static com.liferay.portal.model.Layout updateName(long plid,
		java.lang.String name, java.lang.String languageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateName(plid, name, languageId);
	}

	public static com.liferay.portal.model.Layout updateName(long groupId,
		boolean privateLayout, long layoutId, java.lang.String name,
		java.lang.String languageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateName(groupId, privateLayout, layoutId, name,
			languageId);
	}

	public static com.liferay.portal.model.Layout updateName(
		com.liferay.portal.model.Layout layout, java.lang.String name,
		java.lang.String languageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateName(layout, name, languageId);
	}

	public static com.liferay.portal.model.Layout updateParentLayoutId(
		long plid, long parentPlid)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateParentLayoutId(plid, parentPlid);
	}

	public static com.liferay.portal.model.Layout updateParentLayoutId(
		long groupId, boolean privateLayout, long layoutId, long parentLayoutId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateParentLayoutId(groupId, privateLayout, layoutId,
			parentLayoutId);
	}

	public static com.liferay.portal.model.Layout updatePriority(long plid,
		int priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updatePriority(plid, priority);
	}

	public static com.liferay.portal.model.Layout updatePriority(long groupId,
		boolean privateLayout, long layoutId, int priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updatePriority(groupId, privateLayout, layoutId, priority);
	}

	public static com.liferay.portal.model.Layout updatePriority(
		com.liferay.portal.model.Layout layout, int priority)
		throws com.liferay.portal.SystemException {
		return getService().updatePriority(layout, priority);
	}

	public static LayoutLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("LayoutLocalService is not set");
		}

		return _service;
	}

	public void setService(LayoutLocalService service) {
		_service = service;
	}

	private static LayoutLocalService _service;
}