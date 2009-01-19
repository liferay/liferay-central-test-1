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

package com.liferay.portalweb.portlet.imagegallery;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddImageTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddImageTest extends BaseTestCase {
	public void testAddImage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=Image Gallery Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"link=Image Gallery Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("//b"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("//b"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Add Image']"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);

				boolean IGClassicUploaderPresentA = selenium.isElementPresent(
						"link=Use the classic uploader.");

				if (IGClassicUploaderPresentA) {
					label = 2;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"link=Image Gallery Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("//b"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("//b"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Add Image']"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);

			case 2:

				boolean IGClassicUploaderPresentB = selenium.isElementPresent(
						"link=Use the classic uploader.");

				if (IGClassicUploaderPresentB) {
					label = 3;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"link=Image Gallery Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("//b"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("//b"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Add Image']"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);

			case 3:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=Use the classic uploader.")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("link=Use the classic uploader.");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_31_file")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("_31_file",
					RuntimeVariables.replace(
						"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portlet\\imagegallery\\test_image.jpg"));
				selenium.typeKeys("_31_name",
					RuntimeVariables.replace("test_image"));
				selenium.type("_31_name", RuntimeVariables.replace("test_image"));
				selenium.typeKeys("_31_description",
					RuntimeVariables.replace("This is the Lifera logo!"));
				selenium.type("_31_description",
					RuntimeVariables.replace("This is the Liferay logo!"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Save']"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				assertTrue(selenium.isElementPresent("//img[@alt='Image']"));
				assertTrue(selenium.isTextPresent("test_image"));

			case 100:
				label = -1;
			}
		}
	}
}