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

package com.liferay.portalweb.portal.failover;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddMessage4Test.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddMessage4Test extends BaseTestCase {
	public void testAddMessage4() throws Exception {
		selenium.click(RuntimeVariables.replace(
				"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Test Category"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"//input[@value=\"Post New Thread\"]"));
		selenium.waitForPageToLoad("30000");
		selenium.type("_19_subject", RuntimeVariables.replace("Test Message 4"));
		selenium.type("_19_textArea",
			RuntimeVariables.replace("This is Test Message 4."));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("This is Test Message 4."));
		assertTrue(selenium.isElementPresent("link=Test Message 4"));
		selenium.click(RuntimeVariables.replace("link=Test Category"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Test Message 4"));
		System.out.println("Sample data 4 added successfully.\n");
	}
}