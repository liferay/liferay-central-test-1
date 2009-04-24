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

package com.liferay.portal.model.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portlet.expando.model.ExpandoBridge;

/**
 * <a href="BaseModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class BaseModelImpl<T> implements BaseModel<T> {

	public BaseModelImpl() {
	}

	public boolean isNew() {
		return _new;
	}

	public boolean setNew(boolean n) {
		return _new = n;
	}

	public boolean isCachedModel() {
		return _cachedModel;
	}

	public void setCachedModel(boolean cachedModel) {
		_cachedModel = cachedModel;
	}

	public boolean isEscapedModel() {
		return _escapedModel;
	}

	public void setEscapedModel(boolean escapedModel) {
		_escapedModel = escapedModel;
	}

	public ExpandoBridge getExpandoBridge() {
		throw new UnsupportedOperationException();
	}

	public String toHtmlString() {
		try {
			Field field = getClass().getField("TABLE_COLUMNS");

			Object[][] tableColumns = (Object[][])field.get(this);

			StringBuilder sb = new StringBuilder();

			sb.append("<table class=\"lfr-table\">\n");

			for (Object[] tableColumn : tableColumns) {
				String column = ((String)tableColumn[0]).replaceAll("_$", "");
				String getter = "get" + StringUtil.upperCaseFirstLetter(column);

				Method method = getClass().getMethod(getter);

				sb.append("<tr>");
				sb.append("<td align=\"right\" valign=\"top\">");
				sb.append("<b>" + column + "</b>");
				sb.append("</td>");
				sb.append("<td>");
				sb.append(method.invoke(this));
				sb.append("</td>");
				sb.append("</tr>");
			}

			sb.append("</table>");

			return sb.toString();
		}
		catch (Exception e) {
		}

		return toString();
	}

	public String toString() {
		try {
			Field field = getClass().getField("TABLE_COLUMNS");

			Object[][] tableColumns = (Object[][])field.get(this);

			StringBuilder sb = new StringBuilder();

			sb.append(getClass().getName() + " (");

			for (Object[] tableColumn : tableColumns) {
				String column = ((String)tableColumn[0]).replaceAll("_$", "");
				String getter = "get" + StringUtil.upperCaseFirstLetter(column);

				Method method = getClass().getMethod(getter);

				sb.append(column + ": " + method.invoke(this) + ", ");
			}

			sb.append(")");

			return sb.toString();
		}
		catch (Exception e) {
		}

		return super.toString();
	}


	public abstract Object clone();

	private boolean _new;
	private boolean _cachedModel;
	private boolean _escapedModel;

}