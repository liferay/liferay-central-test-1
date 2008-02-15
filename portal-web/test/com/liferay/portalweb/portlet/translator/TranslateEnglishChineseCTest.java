/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.translator;

import com.liferay.portalweb.portal.BaseTestCase;

/**
 * <a href="TranslateEnglishChineseCTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TranslateEnglishChineseCTest extends BaseTestCase {
	public void testTranslateEnglishChineseC() throws Exception {
		selenium.select("_26_id", "label=English to Chinese (China)");
		selenium.type("_26_text",
			"My name is Liferay Translator, fluent in over 6 million forms of communication.");
		selenium.click("//input[@value='Translate']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent(
				"\u6211\u7684\u540d\u5b57\u662fLiferay \u8bd1\u8005, \u6d41\u5229\u5b8c\u5168\u6210\u529f6 \u901a\u4fe1\u7684\u767e\u4e07\u4e2a\u5f62\u5f0f\u3002"));
	}
}