/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.seleniumbuilder;

import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.util.InitUtil;

import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class SeleniumBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new SeleniumBuilder(args);
	}

	public SeleniumBuilder(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String baseDir = arguments.get("selenium.base.dir");
		String types = arguments.get("selenium.types");

		System.out.println(types);

		SeleniumBuilderContext seleniumBuilderContext =
			new SeleniumBuilderContext(baseDir);
		
		System.out.println(seleniumBuilderContext);
	}

}