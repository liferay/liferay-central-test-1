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

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.Validator;

import java.util.Iterator;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * <a href="VelocityUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class VelocityUtil {

	public static String evaluate(String input) throws Exception {
		return evaluate(input, null);
	}

	public static String evaluate(String input, Map<String, Object> variables)
		throws Exception {

		Velocity.init();

		VelocityContext velocityContext = new VelocityContext();

		if (variables != null) {
			Iterator<Map.Entry<String, Object>> itr =
				variables.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();

				String key = entry.getKey();
				Object value = entry.getValue();

				if (Validator.isNotNull(key)) {
					velocityContext.put(key, value);
				}
			}
		}

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(true);

		Velocity.evaluate(
			velocityContext, unsyncStringWriter, VelocityUtil.class.getName(),
			input);

		return unsyncStringWriter.toString();
	}

}