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

package com.liferay.portalweb.portal.controlpanel.virtualhosting;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AssertPrivatePageHostURLTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssertPrivatePageHostURLTest extends BaseTestCase {
	public void testAssertPrivatePageHostURL() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("http://www.baker.com:8080/");
				Thread.sleep(5000);

				boolean LoggedOut = selenium.isElementPresent("_58_login");

				if (!LoggedOut) {
					label = 2;

					continue;
				}

				selenium.typeKeys("_58_login",
					RuntimeVariables.replace("test@lifera.com"));
				selenium.type("_58_login",
					RuntimeVariables.replace("test@liferay.com"));
				selenium.typeKeys("_58_password",
					RuntimeVariables.replace("test"));
				selenium.type("_58_password", RuntimeVariables.replace("test"));
				selenium.clickAt("_58_rememberMeCheckbox",
					RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Sign In']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

			case 2:
				assertEquals("http://www.baker.com:8080/",
					selenium.getLocation());
				assertEquals(RuntimeVariables.replace(
						"Virtual Hosting Community"),
					selenium.getText("//h1/span"));
				assertTrue(selenium.isElementPresent("link=Private Page"));
				selenium.clickAt("link=Private Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals("http://www.baker.com:8080/private-page",
					selenium.getLocation());

			case 100:
				label = -1;
			}
		}
	}
}