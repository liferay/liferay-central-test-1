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

package com.liferay.portlet.expando.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.DuplicateTableNameException;
import com.liferay.portlet.expando.TableNameException;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.impl.ExpandoTableImpl;
import com.liferay.portlet.expando.service.base.ExpandoTableLocalServiceBaseImpl;

import java.util.List;

/**
 * <a href="ExpandoTableLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoTableLocalServiceImpl
	extends ExpandoTableLocalServiceBaseImpl {

	public ExpandoTable addTable(String className, String name)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return addTable(classNameId, name);
	}

	public ExpandoTable addTable(long classNameId, String name)
		throws PortalException, SystemException {

		validate(0, classNameId, name);

		long tableId = counterLocalService.increment();

		ExpandoTable table = expandoTablePersistence.create(tableId);

		table.setClassNameId(classNameId);
		table.setName(name);

		expandoTablePersistence.update(table, false);

		return table;
	}

	public ExpandoTable addDefaultTable(String className)
		throws PortalException, SystemException {

		return addTable(className, ExpandoTableImpl.DEFAULT_TABLE_NAME);
	}

	public ExpandoTable addDefaultTable(long classNameId)
		throws PortalException, SystemException {

		return addTable(classNameId, ExpandoTableImpl.DEFAULT_TABLE_NAME);
	}

	public void deleteTable(long tableId)
		throws PortalException, SystemException {

		// Values

		expandoValueLocalService.deleteTableValues(tableId);

		// Columns

		expandoColumnLocalService.deleteColumns(tableId);

		// Table

		expandoTablePersistence.remove(tableId);
	}

	public void deleteTable(String className, String name)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		deleteTable(classNameId, name);
	}

	public void deleteTable(long classNameId, String name)
		throws PortalException, SystemException {

		// Table

		ExpandoTable table = expandoTablePersistence.findByC_N(
			classNameId, name);

		deleteTable(table.getTableId());
	}

	public void deleteTables(String className)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		deleteTables(classNameId);
	}

	public void deleteTables(long classNameId)
		throws PortalException, SystemException {

		List<ExpandoTable> tables = expandoTablePersistence.findByClassNameId(
			classNameId);

		for (ExpandoTable table : tables) {
			deleteTable(table.getTableId());
		}
	}

	public ExpandoTable getDefaultTable(String className)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getTable(classNameId, ExpandoTableImpl.DEFAULT_TABLE_NAME);
	}

	public ExpandoTable getDefaultTable(long classNameId)
		throws PortalException, SystemException {

		return getTable(classNameId, ExpandoTableImpl.DEFAULT_TABLE_NAME);
	}

	public ExpandoTable getTable(long tableId)
		throws PortalException, SystemException {

		return expandoTablePersistence.findByPrimaryKey(tableId);
	}

	public ExpandoTable getTable(String className, String name)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getTable(classNameId, name);
	}

	public ExpandoTable getTable(long classNameId, String name)
		throws PortalException, SystemException {

		return expandoTablePersistence.findByC_N(classNameId, name);
	}

	public List<ExpandoTable> getTables(String className)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getTables(classNameId);
	}

	public List<ExpandoTable> getTables(long classNameId)
		throws PortalException, SystemException {

		return expandoTablePersistence.findByClassNameId(classNameId);
	}

	public ExpandoTable updateTable(long tableId, String name)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTablePersistence.findByPrimaryKey(tableId);

		if (Validator.equals(table.getName(),
				ExpandoTableImpl.DEFAULT_TABLE_NAME)) {
			throw new TableNameException(
				"Cannot rename " + ExpandoTableImpl.DEFAULT_TABLE_NAME);
		}

		validate(tableId, table.getClassNameId(), name);

		table.setName(name);

		return expandoTablePersistence.update(table, false);
	}

	protected void validate(long tableId, long classNameId, String name)
		throws PortalException, SystemException {

		if (Validator.isNull(name)) {
			throw new TableNameException();
		}

		ExpandoTable table = expandoTablePersistence.fetchByC_N(
			classNameId, name);

		if ((table != null) && (table.getTableId() != tableId)) {
			throw new DuplicateTableNameException();
		}
	}

}