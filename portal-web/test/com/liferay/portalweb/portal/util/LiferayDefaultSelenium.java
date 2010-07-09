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

package com.liferay.portalweb.portal.util;

import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.DefaultSelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayDefaultSelenium
	extends DefaultSelenium implements LiferaySelenium {

	public LiferayDefaultSelenium(
		String serverHost, int serverPort, String browserStartCommand,
		String browserURL) {

		super(serverHost, serverPort, browserStartCommand, browserURL);
	}

	public LiferayDefaultSelenium(CommandProcessor processor) {
		super(processor);
	}

	public String getCurrentDay() {
		return commandProcessor.getString(
			"getCurrentDay", new String[0]);
	}

	public String getCurrentMonth() {
		return commandProcessor.getString(
			"getCurrentMonth", new String[0]);
	}

	public String getCurrentYear() {
		return commandProcessor.getString(
			"getCurrentYear", new String[0]);
	}

	public String getIncrementedText(String locator) {
		return commandProcessor.getString(
			"getIncrementedText", new String[] {locator,});
	}

	public boolean isPartialText(String locator, String value) {
		return commandProcessor.getBoolean(
			"isPartialText", new String[] {locator, value,});
	}

}