/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.repository.cmis.search;

import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Mika Koivisto
 */
public class CMISBetweenExpression implements CMISCriterion {

	public CMISBetweenExpression(String field, String lower, String upper,
		boolean includesLower, boolean includesUpper) {

		_field = field;
		_lower = lower;
		_upper = upper;
		_includesLower = includesLower;
		_includesUpper = includesUpper;
	}

	public String toQueryFragment() {
		StringBundler sb = new StringBundler();

		sb.append("(");
		sb.append(_field);

		if (_includesLower) {
			sb.append(" >= ");
		}
		else {
			sb.append(" > ");
		}

		sb.append(CMISParameterValueUtil.formatParameterValue(_field, _lower));
		sb.append(" AND ");
		sb.append(_field);

		if (_includesUpper) {
			sb.append(" <= ");
		}
		else {
			sb.append(" < ");
		}

		sb.append(CMISParameterValueUtil.formatParameterValue(_field, _upper));
		sb.append(")");

		return sb.toString();
	}

	private String _field;
	private boolean _includesLower;
	private boolean _includesUpper;
	private String _lower;
	private String _upper;

}