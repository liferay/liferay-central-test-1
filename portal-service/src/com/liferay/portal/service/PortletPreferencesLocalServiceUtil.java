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
 * <a href="PortletPreferencesLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.PortletPreferencesLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.PortletPreferencesLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.PortletPreferencesLocalService
 * @see com.liferay.portal.service.PortletPreferencesLocalServiceFactory
 *
 */
public class PortletPreferencesLocalServiceUtil {
	public static com.liferay.portal.model.PortletPreferences addPortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences)
		throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.addPortletPreferences(portletPreferences);
	}

	public static void deletePortletPreferences(long portletPreferencesId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		portletPreferencesLocalService.deletePortletPreferences(portletPreferencesId);
	}

	public static void deletePortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences)
		throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		portletPreferencesLocalService.deletePortletPreferences(portletPreferences);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> dynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> dynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.dynamicQuery(queryInitializer,
			start, end);
	}

	public static com.liferay.portal.model.PortletPreferences getPortletPreferences(
		long portletPreferencesId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getPortletPreferences(portletPreferencesId);
	}

	public static com.liferay.portal.model.PortletPreferences updatePortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences)
		throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.updatePortletPreferences(portletPreferences);
	}

	public static void deletePortletPreferences(long ownerId, int ownerType,
		long plid) throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		portletPreferencesLocalService.deletePortletPreferences(ownerId,
			ownerType, plid);
	}

	public static void deletePortletPreferences(long ownerId, int ownerType,
		long plid, java.lang.String portletId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		portletPreferencesLocalService.deletePortletPreferences(ownerId,
			ownerType, plid, portletId);
	}

	public static javax.portlet.PortletPreferences getDefaultPreferences(
		long companyId, java.lang.String portletId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getDefaultPreferences(companyId,
			portletId);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences()
		throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getPortletPreferences();
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences(
		long plid, java.lang.String portletId)
		throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getPortletPreferences(plid,
			portletId);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences(
		long ownerId, int ownerType, long plid)
		throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getPortletPreferences(ownerId,
			ownerType, plid);
	}

	public static com.liferay.portal.model.PortletPreferences getPortletPreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getPortletPreferences(ownerId,
			ownerType, plid, portletId);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferencesByPlid(
		long plid) throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getPortletPreferencesByPlid(plid);
	}

	public static javax.portlet.PortletPreferences getPreferences(
		com.liferay.portal.model.PortletPreferencesIds portletPreferencesIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getPreferences(portletPreferencesIds);
	}

	public static javax.portlet.PortletPreferences getPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		java.lang.String portletId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getPreferences(companyId,
			ownerId, ownerType, plid, portletId);
	}

	public static javax.portlet.PortletPreferences getPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		java.lang.String portletId, java.lang.String defaultPreferences)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getPreferences(companyId,
			ownerId, ownerType, plid, portletId, defaultPreferences);
	}

	public static com.liferay.portal.model.PortletPreferences updatePreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.updatePreferences(ownerId,
			ownerType, plid, portletId, prefs);
	}
}