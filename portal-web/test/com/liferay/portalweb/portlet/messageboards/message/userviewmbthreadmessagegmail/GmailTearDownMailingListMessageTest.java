/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.messageboards.message.userviewmbthreadmessagegmail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class GmailTearDownMailingListMessageTest extends BaseTestCase {
	public void testGmailTearDownMailingListMessage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.openWindow("http://www.gmail.com/",
					RuntimeVariables.replace("gmail"));
				selenium.waitForPopUp("gmail", RuntimeVariables.replace(""));
				selenium.selectWindow("gmail");
				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);
				Thread.sleep(5000);

				boolean signedIn1 = selenium.isPartialText("//td/a", "Sign out");

				if (!signedIn1) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//td/a"));
				selenium.clickAt("//td/a", RuntimeVariables.replace("Sign out"));
				selenium.clickAt("//span/a",
					RuntimeVariables.replace("Sign in to Gmail"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

			case 2:

				boolean signInAsADifferentUserPresent = selenium.isElementPresent(
						"link=Sign in as a different user");

				if (!signInAsADifferentUserPresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("link=Sign in as a different user",
					RuntimeVariables.replace("Sign in as a different user"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

			case 3:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@id='Email']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.type("//input[@id='Email']",
					RuntimeVariables.replace("liferay.qa.server.trunk"));
				selenium.saveScreenShotAndSource();
				selenium.type("//input[@id='Passwd']",
					RuntimeVariables.replace("loveispatient"));
				selenium.saveScreenShotAndSource();

				boolean staySignedInChecked = selenium.isChecked(
						"PersistentCookie");

				if (staySignedInChecked) {
					label = 4;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='PersistentCookie']"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@id='PersistentCookie']",
					RuntimeVariables.replace("Stay signed in"));

			case 4:
				assertTrue(selenium.isChecked("//input[@id='PersistentCookie']"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@id='signIn']",
					RuntimeVariables.replace("Sign In"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);
				selenium.close();
				selenium.selectWindow("null");
				selenium.saveScreenShotAndSource();
				selenium.openWindow("http://groups.google.com/",
					RuntimeVariables.replace("Google Groups"));
				selenium.waitForPopUp("Google Groups",
					RuntimeVariables.replace(""));
				selenium.selectWindow("Google Groups");
				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("liferay-mailinglist"),
					selenium.getText("//a[1]/font/b"));
				selenium.clickAt("//a[1]/font/b",
					RuntimeVariables.replace("liferay-mailinglist"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean MLMessage1Present = selenium.isElementPresent(
						"//span[1]/a");

				if (!MLMessage1Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//span[1]/a",
					RuntimeVariables.replace(
						"[MB Category Name] MB Message Subject"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Remove",
					RuntimeVariables.replace("Remove"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.check("//input[@id='really_delete.yes']");
				selenium.clickAt("//input[@name='Action.Confirm']",
					RuntimeVariables.replace("Confirm"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean MLMessage2Present = selenium.isElementPresent(
						"//span[1]/a");

				if (!MLMessage2Present) {
					label = 6;

					continue;
				}

				selenium.clickAt("//span[1]/a",
					RuntimeVariables.replace(
						"[MB Category Name] MB Message Subject"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Remove",
					RuntimeVariables.replace("Remove"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.check("//input[@id='really_delete.yes']");
				selenium.clickAt("//input[@name='Action.Confirm']",
					RuntimeVariables.replace("Confirm"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean MLMessage3Present = selenium.isElementPresent(
						"//span[1]/a");

				if (!MLMessage3Present) {
					label = 7;

					continue;
				}

				selenium.clickAt("//span[1]/a",
					RuntimeVariables.replace(
						"[MB Category Name] MB Message Subject"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Remove",
					RuntimeVariables.replace("Remove"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.check("//input[@id='really_delete.yes']");
				selenium.clickAt("//input[@name='Action.Confirm']",
					RuntimeVariables.replace("Confirm"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean MLMessage4Present = selenium.isElementPresent(
						"//span[1]/a");

				if (!MLMessage4Present) {
					label = 8;

					continue;
				}

				selenium.clickAt("//span[1]/a",
					RuntimeVariables.replace(
						"[MB Category Name] MB Message Subject"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Remove",
					RuntimeVariables.replace("Remove"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.check("//input[@id='really_delete.yes']");
				selenium.clickAt("//input[@name='Action.Confirm']",
					RuntimeVariables.replace("Confirm"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean MLMessage5Present = selenium.isElementPresent(
						"//span[1]/a");

				if (!MLMessage5Present) {
					label = 9;

					continue;
				}

				selenium.clickAt("//span[1]/a",
					RuntimeVariables.replace(
						"[MB Category Name] MB Message Subject"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Remove",
					RuntimeVariables.replace("Remove"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.check("//input[@id='really_delete.yes']");
				selenium.clickAt("//input[@name='Action.Confirm']",
					RuntimeVariables.replace("Confirm"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

			case 5:
			case 6:
			case 7:
			case 8:
			case 9:

				boolean SignedIn2 = selenium.isPartialText("//td/a", "Sign out");

				if (!SignedIn2) {
					label = 10;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//td/a"));
				selenium.clickAt("//td/a", RuntimeVariables.replace("Sign out"));

			case 10:
				Thread.sleep(5000);
				Thread.sleep(5000);
				selenium.close();
				selenium.selectWindow("null");
				selenium.saveScreenShotAndSource();

			case 100:
				label = -1;
			}
		}
	}
}