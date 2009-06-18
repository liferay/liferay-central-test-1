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

package com.liferay.portlet.asset.service;


/**
 * <a href="AssetTagStatsLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.asset.service.AssetTagStatsLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.asset.service.AssetTagStatsLocalService
 *
 */
public class AssetTagStatsLocalServiceUtil {
	public static com.liferay.portlet.asset.model.AssetTagStats addAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.SystemException {
		return getService().addAssetTagStats(assetTagStats);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats createAssetTagStats(
		long tagStatsId) {
		return getService().createAssetTagStats(tagStatsId);
	}

	public static void deleteAssetTagStats(long tagStatsId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteAssetTagStats(tagStatsId);
	}

	public static void deleteAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.SystemException {
		getService().deleteAssetTagStats(assetTagStats);
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

	public static com.liferay.portlet.asset.model.AssetTagStats getAssetTagStats(
		long tagStatsId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getAssetTagStats(tagStatsId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> getAssetTagStatses(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getAssetTagStatses(start, end);
	}

	public static int getAssetTagStatsesCount()
		throws com.liferay.portal.SystemException {
		return getService().getAssetTagStatsesCount();
	}

	public static com.liferay.portlet.asset.model.AssetTagStats updateAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.SystemException {
		return getService().updateAssetTagStats(assetTagStats);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats updateAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateAssetTagStats(assetTagStats, merge);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats addTagStats(
		long classNameId, long tagId) throws com.liferay.portal.SystemException {
		return getService().addTagStats(classNameId, tagId);
	}

	public static void deleteTagStatsByTagId(long tagId)
		throws com.liferay.portal.SystemException {
		getService().deleteTagStatsByTagId(tagId);
	}

	public static void deleteTagStatsByClassNameId(long classNameId)
		throws com.liferay.portal.SystemException {
		getService().deleteTagStatsByClassNameId(classNameId);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats getTagStats(
		long classNameId, long tagId) throws com.liferay.portal.SystemException {
		return getService().getTagStats(classNameId, tagId);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats updateTagStats(
		long classNameId, long tagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateTagStats(classNameId, tagId);
	}

	public static AssetTagStatsLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("AssetTagStatsLocalService is not set");
		}

		return _service;
	}

	public void setService(AssetTagStatsLocalService service) {
		_service = service;
	}

	private static AssetTagStatsLocalService _service;
}