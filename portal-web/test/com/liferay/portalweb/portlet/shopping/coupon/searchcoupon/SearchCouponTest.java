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

package com.liferay.portalweb.portlet.shopping.coupon.searchcoupon;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchCouponTest extends BaseTestCase {
	public void testSearchCoupon() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Shopping Test Page");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Coupons", RuntimeVariables.replace("Coupons"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Shopping Coupon Name\nShopping Coupon Description"),
			selenium.getText("//td[3]/a"));

		String codeSearch = selenium.getText("//td[2]/a");
		RuntimeVariables.setValue("codeSearch", codeSearch);
		selenium.type("//input[@id='_34_code']",
			RuntimeVariables.replace(RuntimeVariables.getValue("codeSearch")));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Shopping Coupon Name\nShopping Coupon Description"),
			selenium.getText("//td[3]/a"));
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Shopping Test Page");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Coupons", RuntimeVariables.replace("Coupons"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_34_code']",
			RuntimeVariables.replace("testtest"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent(
				"Shopping Coupon Name\nShopping Coupon Description"));
	}
}