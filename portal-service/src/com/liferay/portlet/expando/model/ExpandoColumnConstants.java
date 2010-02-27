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

package com.liferay.portlet.expando.model;

/**
 * <a href="ExpandoColumnConstants.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class ExpandoColumnConstants {

	public static final int BOOLEAN = 1;

	public static final int BOOLEAN_ARRAY = 2;

	public static final String BOOLEAN_ARRAY_LABEL = "boolean[]";

	public static final String BOOLEAN_LABEL = "boolean";

	public static final int DATE = 3;

	public static final int DATE_ARRAY = 4;

	public static final String DATE_ARRAY_LABEL = "java.util.Date[]";

	public static final String DATE_LABEL = "java.util.Date";

	public static final int DOUBLE = 5;

	public static final int DOUBLE_ARRAY = 6;

	public static final String DOUBLE_ARRAY_LABEL = "double[]";

	public static final String DOUBLE_LABEL = "double";

	public static final int FLOAT = 7;

	public static final int FLOAT_ARRAY = 8;

	public static final String FLOAT_ARRAY_LABEL = "float[]";

	public static final String FLOAT_LABEL = "float";

	public static final int INTEGER = 9;

	public static final int INTEGER_ARRAY = 10;

	public static final String INTEGER_ARRAY_LABEL = "int[]";

	public static final String INTEGER_LABEL = "int";

	public static final int LONG = 11;

	public static final int LONG_ARRAY = 12;

	public static final String LONG_ARRAY_LABEL = "long[]";

	public static final String LONG_LABEL = "long";

	public static final String PROPERTY_HEIGHT = "height";

	public static final String PROPERTY_HIDDEN = "hidden";

	public static final String PROPERTY_SECRET = "secret";

	public static final String PROPERTY_SELECTION = "selection";

	public static final String PROPERTY_WIDTH = "width";

	public static final int SHORT = 13;

	public static final int SHORT_ARRAY = 14;

	public static final String SHORT_ARRAY_LABEL = "short[]";

	public static final String SHORT_LABEL = "short";

	public static final int STRING = 15;

	public static final int STRING_ARRAY = 16;

	public static final String STRING_ARRAY_LABEL = "java.lang.String[]";

	public static final String STRING_LABEL = "java.lang.String";

	public static final int[] TYPES = new int[] {
		BOOLEAN, BOOLEAN_ARRAY, DATE, DATE_ARRAY, DOUBLE, DOUBLE_ARRAY, FLOAT,
		FLOAT_ARRAY, INTEGER, INTEGER_ARRAY, LONG, LONG_ARRAY, SHORT,
		SHORT_ARRAY, STRING, STRING_ARRAY
	};

	public static final String UNKNOWN_LABEL = "Unknown";

	public static final String getTypeLabel(int type) {
		if (type == BOOLEAN) {
			return BOOLEAN_LABEL;
		}
		else if (type == BOOLEAN_ARRAY) {
			return BOOLEAN_ARRAY_LABEL;
		}
		else if (type == DATE) {
			return DATE_LABEL;
		}
		else if (type == DATE_ARRAY) {
			return DATE_ARRAY_LABEL;
		}
		else if (type == DOUBLE) {
			return DOUBLE_LABEL;
		}
		else if (type == DOUBLE_ARRAY) {
			return DOUBLE_ARRAY_LABEL;
		}
		else if (type == FLOAT) {
			return FLOAT_LABEL;
		}
		else if (type == FLOAT_ARRAY) {
			return FLOAT_ARRAY_LABEL;
		}
		else if (type == INTEGER) {
			return INTEGER_LABEL;
		}
		else if (type == INTEGER_ARRAY) {
			return INTEGER_ARRAY_LABEL;
		}
		else if (type == LONG) {
			return LONG_LABEL;
		}
		else if (type == LONG_ARRAY) {
			return LONG_ARRAY_LABEL;
		}
		else if (type == SHORT) {
			return SHORT_LABEL;
		}
		else if (type == SHORT_ARRAY) {
			return SHORT_ARRAY_LABEL;
		}
		else if (type == STRING) {
			return STRING_LABEL;
		}
		else if (type == STRING_ARRAY) {
			return STRING_ARRAY_LABEL;
		}

		return UNKNOWN_LABEL;
	}

}