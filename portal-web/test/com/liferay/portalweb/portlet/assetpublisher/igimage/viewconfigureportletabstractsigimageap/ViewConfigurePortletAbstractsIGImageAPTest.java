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

package com.liferay.portalweb.portlet.assetpublisher.igimage.viewconfigureportletabstractsigimageap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewConfigurePortletAbstractsIGImageAPTest extends BaseTestCase {
	public void testViewConfigurePortletAbstractsIGImageAP()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Asset Publisher Test Page");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("IG Folder Image Name"),
			selenium.getText("//h3[@class='asset-title']/a"));
		assertEquals(RuntimeVariables.replace(
				"Read More About IG Folder Image Name \u00bb"),
			selenium.getText("//div[@class='asset-more']/a"));
		assertTrue(selenium.isVisible(
				"//div[@class='asset-resource-info']/div/img"));
		assertEquals(RuntimeVariables.replace("IG Folder Image Name"),
			selenium.getText("//div[@class='asset-resource-info']/div"));
		assertEquals(RuntimeVariables.replace(
				"Read More About IG Folder Image Name \u00bb"),
			selenium.getText("//div[@class='asset-more']/a"));
		selenium.clickAt("//div[@class='asset-more']/a",
			RuntimeVariables.replace(
				"Read More About IG Folder Image Name \u00bb"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("IG Folder Image Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isVisible(
				"//div[@class='asset-resource-info']/a/div/img"));
		assertEquals(RuntimeVariables.replace("IG Folder Image Name"),
			selenium.getText("//div[@class='asset-resource-info']/a/div"));
		assertEquals(RuntimeVariables.replace("View in Context \u00bb"),
			selenium.getText("//div[@class='asset-more']/a"));
		selenium.clickAt("//div[@class='asset-more']/a",
			RuntimeVariables.replace("View in Context \u00bb"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible(
				"//img[@class='lfr-preview-file-image-current']"));
		assertEquals(RuntimeVariables.replace("IG Folder Image Name"),
			selenium.getText("//h2[@class='document-title']"));
	}
}