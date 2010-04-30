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

package com.liferay.portal.kernel.search;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.text.ParseException;

import java.util.Date;
import java.util.Map;

/**
 * <a href="Document.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public interface Document extends Serializable {

	public void add(Field field);

	public void addDate(String name, Date value);

	public void addFile(String name, byte[] bytes, String fileExt)
		throws IOException;

	public void addFile(String name, File file, String fileExt)
		throws IOException;

	public void addFile(String name, InputStream is, String fileExt)
		throws IOException;

	public void addKeyword(String name, boolean value);

	public void addKeyword(String name, Boolean value);

	public void addKeyword(String name, boolean[] values);

	public void addKeyword(String name, Boolean[] values);

	public void addKeyword(String name, double value);

	public void addKeyword(String name, Double value);

	public void addKeyword(String name, double[] values);

	public void addKeyword(String name, Double[] values);

	public void addKeyword(String name, int value);

	public void addKeyword(String name, int[] values);

	public void addKeyword(String name, Integer value);

	public void addKeyword(String name, Integer[] values);

	public void addKeyword(String name, long value);

	public void addKeyword(String name, Long value);

	public void addKeyword(String name, long[] values);

	public void addKeyword(String name, Long[] values);

	public void addKeyword(String name, short value);

	public void addKeyword(String name, Short value);

	public void addKeyword(String name, short[] values);

	public void addKeyword(String name, Short[] values);

	public void addKeyword(String name, String value);

	public void addKeyword(String name, String value, boolean lowerCase);

	public void addKeyword(String name, String[] values);

	public void addModifiedDate();

	public void addModifiedDate(Date modifiedDate);

	public void addText(String name, String value);

	public void addUID(String portletId, long field1);

	public void addUID(String portletId, long field1, String field2);

	public void addUID(String portletId, Long field1);

	public void addUID(String portletId, Long field1, String field2);

	public void addUID(String portletId, String field1);

	public void addUID(String portletId, String field1, String field2);

	public void addUID(
		String portletId, String field1, String field2, String field3);

	public void addUID(
		String portletId, String field1, String field2, String field3,
		String field4);

	public String get(String name);

	public String getUID();

	public Date getDate(String name) throws ParseException;

	public Map<String, Field> getFields();

	public String[] getValues(String name);

}