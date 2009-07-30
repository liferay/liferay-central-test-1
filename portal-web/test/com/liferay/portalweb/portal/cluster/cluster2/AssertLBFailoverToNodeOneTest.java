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

package com.liferay.portalweb.portal.cluster.cluster2;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AssertLBFailoverToNodeOneTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssertLBFailoverToNodeOneTest extends BaseTestCase {
	public void testAssertLBFailoverToNodeOne() throws Exception {
		selenium.open("/");
		Thread.sleep(5000);
		selenium.type("_58_login", RuntimeVariables.replace("test@liferay.com"));
		selenium.type("_58_password", RuntimeVariables.replace("test"));
		selenium.click(RuntimeVariables.replace("//input[@value='Sign In']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//li[5]/a/span"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Node: [$CLUSTER_NODE_1$]"),
			selenium.getText("//div[@id='wrapper']/div[6]"));
	}
}