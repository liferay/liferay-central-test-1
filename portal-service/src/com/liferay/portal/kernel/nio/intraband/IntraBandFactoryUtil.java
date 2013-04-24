/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.nio.intraband;

import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraBand;
import com.liferay.portal.kernel.nio.intraband.nonblocking.SelectorIntraBand;
import com.liferay.portal.kernel.nio.intraband.welder.Welder;
import com.liferay.portal.kernel.nio.intraband.welder.WelderFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.welder.socket.SocketWelder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.lang.reflect.Constructor;

/**
 * @author Shuyang Zhou
 */
public class IntraBandFactoryUtil {

	public static IntraBand createIntraBand() throws IOException {
		if (Validator.isNotNull(_INTRABAND_IMPL)) {
			try {
				Class<? extends IntraBand> intraBandClass =
					(Class<? extends IntraBand>)Class.forName(_INTRABAND_IMPL);

				Constructor<? extends IntraBand> constructor =
					intraBandClass.getConstructor(long.class);

				return constructor.newInstance(_INTRABAND_TIMEOUT_DEFAULT);
			}
			catch (Exception e) {
				throw new RuntimeException(
					"Unable to create IntraBand instance for class " +
						_INTRABAND_IMPL, e);
			}
		}
		else {
			Class<? extends Welder> welderClass =
				WelderFactoryUtil.getWelderClass();

			if (welderClass.equals(SocketWelder.class)) {
				return new SelectorIntraBand(_INTRABAND_TIMEOUT_DEFAULT);
			}
			else {
				return new ExecutorIntraBand(_INTRABAND_TIMEOUT_DEFAULT);
			}
		}
	}

	private static final String _INTRABAND_IMPL = GetterUtil.getString(
		PropsUtil.get(PropsKeys.INTRABAND_IMPL));

	private static final long _INTRABAND_TIMEOUT_DEFAULT = GetterUtil.getLong(
		PropsUtil.get(PropsKeys.INTRABAND_TIMEOUT_DEFAULT));

}