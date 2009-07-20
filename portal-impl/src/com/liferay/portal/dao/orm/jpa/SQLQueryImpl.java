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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.dao.orm.jpa;

import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnmodifiableList;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class SQLQueryImpl extends QueryImpl implements SQLQuery {

	public SQLQueryImpl(SessionImpl sessionImpl, String queryString) {
		super(sessionImpl, queryString);

		sqlQuery = true;
	}

	public SQLQuery addEntity(String alias, Class<?> entityClass) {
		String columnAliases = null;

		try {
			String[] columnNames = _getColumns(entityClass);

			StringBuilder sb = new StringBuilder();

			int i = 0;

			for (String column : columnNames) {
				sb.append(alias);
				sb.append(StringPool.PERIOD);
				sb.append(column);

				if ((i + 1) < columnNames.length) {
					sb.append(StringPool.COMMA_AND_SPACE);
				}

				i++;
			}

			columnAliases = sb.toString();
		}
		catch (Exception e) {
			throw new ORMException(e.getMessage());
		}

		String escapedAlias = Pattern.quote("{" + alias + ".*}");

		queryString = queryString.replaceAll(escapedAlias, columnAliases);

		this.entityClass = entityClass;

		return this;
	}

	public SQLQuery addScalar(String columnAlias, Type type) {
		columnAlias = columnAlias.toLowerCase();

		String q = queryString.toLowerCase();

		int fromIndex = q.indexOf("from");

		if (fromIndex == -1) {
			return this;
		}

		String selectExpression = q.substring(0, fromIndex);

		String[] selectTokens = selectExpression.split(StringPool.COMMA);

		for (int pos = 0; pos < selectTokens.length; pos++) {
			String s = selectTokens[pos];

			if (s.indexOf(columnAlias) != -1) {
				_scalars.add(pos);
			}
		}

		return this;
	}

	public List<?> list(boolean unmodifiable) throws ORMException {
		try {
			List<?> list = sessionImpl.list(
				queryString, parameterMap, firstResult, maxResults,
				flushModeType, sqlQuery, entityClass);

			if ((entityClass == null) && !list.isEmpty()) {
				list = _transformList(list);
			}

			if (unmodifiable) {
				return new UnmodifiableList<Object>(list);
			}
			else {
				return ListUtil.copy(list);
			}
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Object uniqueResult() throws ORMException {
		try {
			Object object =  sessionImpl.uniqueResult(
				queryString, parameterMap, firstResult, maxResults,
				flushModeType, sqlQuery, entityClass);

			if (object instanceof Collection) {
				Collection<Object> collection = (Collection<Object>)object;

				if (collection.size() == 1) {
					object = collection.iterator().next();
				}
			}

			return object;
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	private String[] _getColumns(Class<?> entityClass) throws Exception {
		String[] columns = _entityColumns.get(entityClass);

		if (columns != null) {
			return columns;
		}

		Field field = entityClass.getField("TABLE_COLUMNS");

		Object[][] tableColumns = (Object[][])field.get(null);

		columns = new String[tableColumns.length];

		int i = 0;

		for (Object[] row : tableColumns) {
			String name = (String)row[0];

			columns[i++] = name.toUpperCase();
		}

		_entityColumns.put(entityClass, columns);

		return columns;
	}

	private List<?> _transformList(List<?> list) throws Exception {
		if (!_scalars.isEmpty()) {
			Collections.sort(_scalars);

			if (list.get(0) instanceof Collection) {
				List<Object> newList = new ArrayList<Object>();

				for (Collection<Object> collection :
						(List<Collection<Object>>)list) {

					Object[] array = collection.toArray();

					if (_scalars.size() > 1) {
						Object[] values = new Object[_scalars.size()];

						for (int i = 0; i < _scalars.size(); i++) {
							values[i] = array[_scalars.get(i)];
						}

						newList.add(values);
					}
					else {
						newList.add(array[_scalars.get(0)]);
					}
				}

				list = newList;
			}
			else if (list.get(0) instanceof Object[]) {
				List<Object> newList = new ArrayList<Object>();

				for (Object[] array : (List<Object[]>)list) {
					if (_scalars.size() > 1) {
						Object[] values = new Object[_scalars.size()];

						for (int i = 0; i < _scalars.size(); i++) {
							values[i] = array[_scalars.get(i)];
						}

						newList.add(values);
					}
					else {
						newList.add(array[_scalars.get(0)]);
					}
				}

				list = newList;
			}
		}
		else if (list.get(0) instanceof Collection) {
			List<Object> newList = new ArrayList<Object>();

			for (Collection<Object> collection :
					(List<Collection<Object>>)list) {

				if (collection.size() == 1) {
					newList.add(collection.iterator().next());
				}
				else {
					newList.add(collection.toArray());
				}
			}

			list = newList;
		}

		return list;
	}

	private static Map<Class<?>, String[]> _entityColumns =
		new ConcurrentHashMap<Class<?>, String[]>();

	private List<Integer> _scalars = new ArrayList<Integer>();

}