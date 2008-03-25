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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.ExpandoTable;
import com.liferay.portal.service.base.ExpandoTableServiceBaseImpl;

import java.util.List;

/**
 * <a href="ExpandoTableServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoTableServiceImpl extends ExpandoTableServiceBaseImpl {

	public ExpandoTable addTable(long classNameId, String name)
		throws PortalException, SystemException {

		return expandoTableLocalService.addTable(classNameId, name);
	}

	public void deleteTable(long tableId)
		throws PortalException, SystemException {

		expandoTableLocalService.deleteTable(tableId);
	}

	public ExpandoTable getTable(long tableId)
		throws PortalException, SystemException {

		return expandoTableLocalService.getTable(tableId);
	}

	public ExpandoTable getTable(long classNameId, String name)
		throws PortalException, SystemException {

		return expandoTableLocalService.getTable(classNameId, name);
	}

	public List<ExpandoTable> getTables(long classNameId)
		throws PortalException, SystemException {

		return expandoTableLocalService.getTables(classNameId);
	}

	public ExpandoTable setTable(long classNameId, String name)
		throws PortalException, SystemException {

		return expandoTableLocalService.setTable(classNameId, name);
	}

	public ExpandoTable updateTableName(long tableId, String name)
		throws PortalException, SystemException {

		return expandoTableLocalService.updateTableName(tableId, name);
	}

}