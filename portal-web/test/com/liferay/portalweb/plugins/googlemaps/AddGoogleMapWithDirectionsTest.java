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

package com.liferay.portalweb.plugins.googlemaps;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddGoogleMapWithDirectionsTest extends BaseTestCase {
	public void testAddGoogleMapWithDirections() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=Google Maps Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Google Maps Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Configuration",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_86_license",
					RuntimeVariables.replace(
						"ABQIAAAA3nrHjKy73DtxJL8D67iR6hSqd3WNkXftHeaSLroSolGIoU-u5BTriDnzHVQc9TudabxQnFqk-gNe8A"));
				selenium.type("_86_mapAddress",
					RuntimeVariables.replace(
						"17730 Antonio Ave, Cerritos, CA, 90703"));
				selenium.type("_86_directionsAddress",
					RuntimeVariables.replace(
						"11947 Del Amo Blvd, Cerritos, CA, 90703"));

				boolean DirectionChecked = selenium.isChecked(
						"_86_directionsInputEnabledCheckbox");

				if (DirectionChecked) {
					label = 2;

					continue;
				}

				selenium.click("_86_directionsInputEnabledCheckbox");

			case 2:
				selenium.clickAt("//form/input[2]", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@id='p_p_id_86_']/div/div"));

			case 100:
				label = -1;
			}
		}
	}
}