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

package com.liferay.portalweb.portal.util.liferayselenium;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.TestPropsValues;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseWebDriverImpl
	extends WebDriverToSeleniumBridge implements LiferaySelenium {

	public BaseWebDriverImpl(
		String projectDir, String browserURL, WebDriver webDriver) {

		super(webDriver);

		_projectDir = projectDir;

		webDriver.get(browserURL);
	}

	public void downloadTempFile(String value) {
		LiferaySeleniumHelper.downloadTempFile(value);
	}

	public String getCurrentDay() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.DATE));
	}

	public String getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.MONTH) + 1);
	}

	public String getCurrentYear() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.YEAR));
	}

	public String getFirstNumber(String locator) {
		String text = getWebElement(locator).getText();

		if (text == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		char[] chars = text.toCharArray();

		for (char c : chars) {
			boolean digit = false;

			if (Validator.isDigit(c)) {
				sb.append(c);

				digit = true;
			}

			String s = sb.toString();

			if (Validator.isNotNull(s) && !digit) {
				return s;
			}
		}

		return sb.toString();
	}

	public String getFirstNumberIncrement(String locator) {
		String firstNumber = getFirstNumber(locator);

		return StringUtil.valueOf(GetterUtil.getInteger(firstNumber) + 1);
	}

	public boolean isElementNotPresent(String locator) {
		WebDriver.Options options = manage();

		Timeouts timeouts = options.timeouts();

		timeouts.implicitlyWait(1, TimeUnit.SECONDS);

		List<WebElement> webElements = getWebElements(locator);

		timeouts.implicitlyWait(
			TestPropsValues.TIMEOUT_IMPLICIT_WAIT, TimeUnit.SECONDS);

		return webElements.isEmpty();
	}

	public boolean isPartialText(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		String text = webElement.getText();

		return text.contains(value);
	}

	public void saveScreenShotAndSource() throws Exception {
	}

	public void sendKeys(String locator, String value) {
		typeKeys(locator, value);
	}

	public void setBrowserOption() {
		LiferaySeleniumHelper.setBrowserOption();
	}

	public void uploadCommonFile(String location, String value) {
		uploadFile(
			location,
			_projectDir + "portal-web\\test\\com\\liferay\\portalweb\\" +
				"dependencies\\" + value);
	}

	public void uploadFile(String location, String value) {
		WebElement webElement = getWebElement(location);

		webElement.sendKeys(value);
	}

	public void uploadTempFile(String location, String value) {
		uploadFile(location, TestPropsValues.OUTPUT_DIR + value);
	}

	public void waitForConfirmation(String pattern) throws Exception {
		int timeout =
			TestPropsValues.TIMEOUT_EXPLICIT_WAIT /
				TestPropsValues.TIMEOUT_IMPLICIT_WAIT;

		for (int second = 0;; second++) {
			if (second >= timeout) {
				BaseTestCase.fail("Timeout");
			}

			try {
				if (pattern.equals(getConfirmation())) {
					break;
				}
			}
			catch (Exception e) {
			}
		}
	}

	public void waitForElementNotPresent(String locator) throws Exception {
		LiferaySeleniumHelper.waitForElementNotPresent(this, locator);
	}

	public void waitForElementPresent(String locator) throws Exception {
		WebDriver.Options options = manage();

		Timeouts timeouts = options.timeouts();

		timeouts.implicitlyWait(
			TestPropsValues.TIMEOUT_EXPLICIT_WAIT, TimeUnit.SECONDS);

		try {
			getWebElement(locator);
		}
		catch (Exception e) {
			BaseTestCase.fail(
				"Timeout: unable to find the locator \"" + locator + "\"");
		}
		finally {
			timeouts.implicitlyWait(
				TestPropsValues.TIMEOUT_IMPLICIT_WAIT, TimeUnit.SECONDS);
		}
	}

	public void waitForNotPartialText(String locator, String value)
		throws Exception {

		LiferaySeleniumHelper.waitForNotPartialText(this, locator, value);
	}

	public void waitForNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.waitForNotSelectedLabel(
			this, selectLocator, pattern);
	}

	public void waitForNotText(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForNotText(this, locator, value);
	}

	public void waitForNotValue(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForNotValue(this, locator, value);
	}

	public void waitForNotVisible(String locator) throws Exception {
		LiferaySeleniumHelper.waitForNotVisible(this, locator);
	}

	public void waitForPartialText(String locator, String value)
		throws Exception {

		LiferaySeleniumHelper.waitForPartialText(this, locator, value);
	}

	public void waitForSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.waitForSelectedLabel(
			this, selectLocator, pattern);
	}

	public void waitForText(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForText(this, locator, value);
	}

	public void waitForTextNotPresent(String value) throws Exception {
		LiferaySeleniumHelper.waitForTextNotPresent(this, value);
	}

	public void waitForTextPresent(String value) throws Exception {
		LiferaySeleniumHelper.waitForTextPresent(this, value);
	}

	public void waitForValue(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForValue(this, locator, value);
	}

	public void waitForVisible(String locator) throws Exception {
		LiferaySeleniumHelper.waitForVisible(this, locator);
	}

	private String _projectDir;

}