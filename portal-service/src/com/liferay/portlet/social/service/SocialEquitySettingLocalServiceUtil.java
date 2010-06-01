/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="SocialEquitySettingLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link SocialEquitySettingLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquitySettingLocalService
 * @generated
 */
public class SocialEquitySettingLocalServiceUtil {
	public static com.liferay.portlet.social.model.SocialEquitySetting addSocialEquitySetting(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSocialEquitySetting(socialEquitySetting);
	}

	public static com.liferay.portlet.social.model.SocialEquitySetting createSocialEquitySetting(
		long equitySettingId) {
		return getService().createSocialEquitySetting(equitySettingId);
	}

	public static void deleteSocialEquitySetting(long equitySettingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquitySetting(equitySettingId);
	}

	public static void deleteSocialEquitySetting(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquitySetting(socialEquitySetting);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.social.model.SocialEquitySetting getSocialEquitySetting(
		long equitySettingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquitySetting(equitySettingId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> getSocialEquitySettings(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquitySettings(start, end);
	}

	public static int getSocialEquitySettingsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquitySettingsCount();
	}

	public static com.liferay.portlet.social.model.SocialEquitySetting updateSocialEquitySetting(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialEquitySetting(socialEquitySetting);
	}

	public static com.liferay.portlet.social.model.SocialEquitySetting updateSocialEquitySetting(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialEquitySetting(socialEquitySetting, merge);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> getEquitySettings(
		long groupId, long classNameId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEquitySettings(groupId, classNameId, actionId);
	}

	public static void updateSocialEquitySettings(java.lang.String model,
		long groupId, long classNameId,
		java.util.List<com.liferay.portlet.social.model.SocialEquityActionMapping> mappings)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateSocialEquitySettings(model, groupId, classNameId, mappings);
	}

	public static SocialEquitySettingLocalService getService() {
		if (_service == null) {
			_service = (SocialEquitySettingLocalService)PortalBeanLocatorUtil.locate(SocialEquitySettingLocalService.class.getName());
		}

		return _service;
	}

	public void setService(SocialEquitySettingLocalService service) {
		_service = service;
	}

	private static SocialEquitySettingLocalService _service;
}