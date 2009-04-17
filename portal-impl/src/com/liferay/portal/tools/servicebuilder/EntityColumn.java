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

package com.liferay.portal.tools.servicebuilder;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.TextFormatter;

/**
 * <a href="EntityColumn.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Charles May
 *
 */
public class EntityColumn implements Cloneable {

	public EntityColumn(String name) {
		this(
			name, null, null, false, null, null, null, true, true, null, null,
			null, true);
	}

	public EntityColumn(
		String name, String dbName, String type, boolean primary,
		String ejbName, String mappingKey, String mappingTable,
		boolean caseSensitive, boolean orderByAscending, String comparator,
		String idType, String idParam, boolean convertNull) {

		_name = name;
		_dbName = dbName;
		_type = type;
		_primary = primary;
		_methodName = TextFormatter.format(name, TextFormatter.G);
		_ejbName = ejbName;
		_mappingKey = mappingKey;
		_mappingTable = mappingTable;
		_caseSensitive = caseSensitive;
		_orderByAscending = orderByAscending;
		_comparator = comparator;
		_idType = idType;
		_idParam = idParam;
		_convertNull = convertNull;
	}

	public EntityColumn(
		String name, String dbName, String type, boolean primary,
		String ejbName, String mappingKey, String mappingTable, String idType,
		String idParam, boolean convertNull) {

		this(
			name, dbName, type, primary, ejbName, mappingKey, mappingTable,
			true, true, null, idType, idParam, convertNull);
	}

	public Object clone() {
		return new EntityColumn(
			getName(), getDBName(), getType(), isPrimary(), getEJBName(),
			getMappingKey(), getMappingTable(), isCaseSensitive(),
			isOrderByAscending(), getComparator(), getIdType(), getIdParam(),
			isConvertNull());
	}

	public boolean equals(Object obj) {
		EntityColumn col = (EntityColumn)obj;

		String name = col.getName();

		if (_name.equals(name)) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getComparator() {
		return _comparator;
	}

	public String getDBName() {
		return _dbName;
	}

	public String getEJBName() {
		return _ejbName;
	}

	public String getIdParam() {
		return _idParam;
	}

	public String getIdType() {
		return _idType;
	}

	public String getMappingKey() {
		return _mappingKey;
	}

	public String getMappingTable() {
		return _mappingTable;
	}

	public String getMethodName() {
		return _methodName;
	}

	public String getName() {
		return _name;
	}

	public String getNames() {
		return TextFormatter.formatPlural(new String(_name));
	}

	public String getType() {
		return _type;
	}

	public boolean isCaseSensitive() {
		return _caseSensitive;
	}

	public boolean isCollection() {
		if (_type.equals("Collection")) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isConvertNull() {
		return _convertNull;
	}

	public boolean isFetchFinderPath() {
		return _fetchFinderPath;
	}

	public boolean isMappingManyToMany() {
		return Validator.isNotNull(_mappingTable);
	}

	public boolean isMappingOneToMany() {
		return Validator.isNotNull(_mappingKey);
	}

	public boolean isOrderByAscending() {
		return _orderByAscending;
	}

	public boolean isPrimary() {
		return _primary;
	}

	public boolean isPrimitiveType() {
		if (Character.isLowerCase(_type.charAt(0))) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setCaseSensitive(boolean caseSensitive) {
		_caseSensitive = caseSensitive;
	}

	public void setComparator(String comparator) {
		_comparator = comparator;
	}

	public void setConvertNull(boolean convertNull) {
		_convertNull = convertNull;
	}

	public void setDBName(String dbName) {
		_dbName = dbName;
	}

	public void setFetchFinderPath(boolean fetchFinderPath) {
		_fetchFinderPath = fetchFinderPath;
	}

	public void setIdParam(String idParam) {
		_idParam = idParam;
	}

	public void setIdType(String idType) {
		_idType = idType;
	}

	public void setOrderByAscending(boolean orderByAscending) {
		_orderByAscending = orderByAscending;
	}

	private boolean _caseSensitive;
	private String _comparator;
	private boolean _convertNull;
	private String _dbName;
	private String _ejbName;
	private boolean _fetchFinderPath;
	private String _idParam;
	private String _idType;
	private String _mappingKey;
	private String _mappingTable;
	private String _methodName;
	private String _name;
	private boolean _orderByAscending;
	private boolean _primary;
	private String _type;

}