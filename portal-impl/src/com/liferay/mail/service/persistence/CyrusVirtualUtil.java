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

package com.liferay.mail.service.persistence;

import com.liferay.mail.NoSuchCyrusVirtualException;
import com.liferay.mail.model.CyrusVirtual;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

import java.util.List;

/**
 * <a href="CyrusVirtualUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CyrusVirtualUtil {

	public static CyrusVirtual findByPrimaryKey(String emailAddress)
		throws NoSuchCyrusVirtualException, SystemException {

		return getPersistence().findByPrimaryKey(emailAddress);
	}

	public static List<CyrusVirtual> findByUserId(long userId)
		throws SystemException {

		return getPersistence().findByUserId(userId);
	}

	public static CyrusVirtualPersistence getPersistence() {
		if (_persistence == null) {
			_persistence =
				(CyrusVirtualPersistence)PortalBeanLocatorUtil.locate(
					CyrusVirtualPersistence.class.getName());
		}

		return _persistence;
	}

	public static void remove(String emailAddress)
		throws NoSuchCyrusVirtualException, SystemException {

		getPersistence().remove(emailAddress);
	}

	public static void removeByUserId(long userId) throws SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void update(CyrusVirtual user) throws SystemException {
		getPersistence().update(user);
	}

	public void setPersistence(CyrusVirtualPersistence persistence) {
		_persistence = persistence;
	}

	private static CyrusVirtualPersistence _persistence;

}