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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v5_0_0.UpgradeImageGallery;
import com.liferay.portal.upgrade.v5_0_0.UpgradeLayoutSet;
import com.liferay.portal.upgrade.v5_0_0.UpgradeSchema;
import com.liferay.portal.upgrade.v5_0_0.UpgradeSoftwareCatalog;

/**
 * <a href="UpgradeProcess_5_0_0.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UpgradeProcess_5_0_0 extends UpgradeProcess {

	public int getThreshold() {
		return ReleaseInfo.RELEASE_5_0_0_BUILD_NUMBER;
	}

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		upgrade(UpgradeSchema.class);
		upgrade(UpgradeImageGallery.class);
		upgrade(UpgradeLayoutSet.class);
		upgrade(UpgradeSoftwareCatalog.class);
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeProcess_5_0_0.class);

}