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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;

import javax.portlet.ReadOnlyException;

/**
 * <a href="VelocityPortletPreferences.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class VelocityPortletPreferences {

	public VelocityPortletPreferences() {
		_preferencesImpl = new PortletPreferencesImpl();
	}

	public void reset() {
		_preferencesImpl.reset();
	}

	public void setValue(String key, String value) throws ReadOnlyException {
		_preferencesImpl.setValue(key, value);
	}

	public void setValues(String key, String[] values)
		throws ReadOnlyException {

		_preferencesImpl.setValues(key, values);
	}

	public String toString() {
		try {
			return PortletPreferencesSerializer.toXML(_preferencesImpl);
		}
		catch (Exception e) {
			_log.error(e, e);

			return PortletConstants.DEFAULT_PREFERENCES;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		VelocityPortletPreferences.class);

	private PortletPreferencesImpl _preferencesImpl;

}