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
 * <a href="PluginSettingLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.PluginSettingLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.PluginSettingLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.PluginSettingLocalService
 * @see com.liferay.portal.service.PluginSettingLocalServiceFactory
 *
 */
public class PluginSettingLocalServiceUtil {
	public static com.liferay.portal.model.PluginSetting addPluginSetting(
		com.liferay.portal.model.PluginSetting pluginSetting)
		throws com.liferay.portal.SystemException {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		return pluginSettingLocalService.addPluginSetting(pluginSetting);
	}

	public static void deletePluginSetting(long pluginSettingId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		pluginSettingLocalService.deletePluginSetting(pluginSettingId);
	}

	public static void deletePluginSetting(
		com.liferay.portal.model.PluginSetting pluginSetting)
		throws com.liferay.portal.SystemException {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		pluginSettingLocalService.deletePluginSetting(pluginSetting);
	}

	public static java.util.List<com.liferay.portal.model.PluginSetting> dynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		return pluginSettingLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.PluginSetting> dynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		return pluginSettingLocalService.dynamicQuery(queryInitializer, start,
			end);
	}

	public static com.liferay.portal.model.PluginSetting getPluginSetting(
		long pluginSettingId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		return pluginSettingLocalService.getPluginSetting(pluginSettingId);
	}

	public static com.liferay.portal.model.PluginSetting updatePluginSetting(
		com.liferay.portal.model.PluginSetting pluginSetting)
		throws com.liferay.portal.SystemException {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		return pluginSettingLocalService.updatePluginSetting(pluginSetting);
	}

	public static void checkPermission(long userId, java.lang.String pluginId,
		java.lang.String pluginType) throws com.liferay.portal.PortalException {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		pluginSettingLocalService.checkPermission(userId, pluginId, pluginType);
	}

	public static com.liferay.portal.model.PluginSetting getDefaultPluginSetting() {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		return pluginSettingLocalService.getDefaultPluginSetting();
	}

	public static com.liferay.portal.model.PluginSetting getPluginSetting(
		long companyId, java.lang.String pluginId, java.lang.String pluginType)
		throws com.liferay.portal.SystemException {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		return pluginSettingLocalService.getPluginSetting(companyId, pluginId,
			pluginType);
	}

	public static boolean hasPermission(long userId, java.lang.String pluginId,
		java.lang.String pluginType) {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		return pluginSettingLocalService.hasPermission(userId, pluginId,
			pluginType);
	}

	public static com.liferay.portal.model.PluginSetting updatePluginSetting(
		long companyId, java.lang.String pluginId, java.lang.String pluginType,
		java.lang.String roles, boolean active)
		throws com.liferay.portal.SystemException {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		return pluginSettingLocalService.updatePluginSetting(companyId,
			pluginId, pluginType, roles, active);
	}
}