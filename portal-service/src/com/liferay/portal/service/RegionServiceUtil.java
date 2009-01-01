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

package com.liferay.portal.service;


/**
 * <a href="RegionServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.RegionService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.RegionService
 *
 */
public class RegionServiceUtil {
	public static com.liferay.portal.model.Region addRegion(long countryId,
		java.lang.String regionCode, java.lang.String name, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addRegion(countryId, regionCode, name, active);
	}

	public static java.util.List<com.liferay.portal.model.Region> getRegions()
		throws com.liferay.portal.SystemException {
		return getService().getRegions();
	}

	public static java.util.List<com.liferay.portal.model.Region> getRegions(
		long countryId) throws com.liferay.portal.SystemException {
		return getService().getRegions(countryId);
	}

	public static java.util.List<com.liferay.portal.model.Region> getRegions(
		boolean active) throws com.liferay.portal.SystemException {
		return getService().getRegions(active);
	}

	public static java.util.List<com.liferay.portal.model.Region> getRegions(
		long countryId, boolean active)
		throws com.liferay.portal.SystemException {
		return getService().getRegions(countryId, active);
	}

	public static com.liferay.portal.model.Region getRegion(long regionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getRegion(regionId);
	}

	public static RegionService getService() {
		if (_service == null) {
			throw new RuntimeException("RegionService is not set");
		}

		return _service;
	}

	public void setService(RegionService service) {
		_service = service;
	}

	private static RegionService _service;
}