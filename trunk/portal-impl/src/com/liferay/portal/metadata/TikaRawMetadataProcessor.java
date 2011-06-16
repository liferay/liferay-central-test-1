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

package com.liferay.portal.metadata;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.metadata.RawMetadataProcessor;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.tika.Tika;
import org.apache.tika.metadata.ClimateForcast;
import org.apache.tika.metadata.CreativeCommons;
import org.apache.tika.metadata.DublinCore;
import org.apache.tika.metadata.Geographic;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.MSOffice;
import org.apache.tika.metadata.Message;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Property;
import org.apache.tika.metadata.TIFF;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.metadata.TikaMimeKeys;

/**
 * @author Miguel Pastor
 */
public class TikaRawMetadataProcessor implements RawMetadataProcessor {

	public static Map<String, Field[]> getFields() {
		return _fields;
	}

	public Map<String, Fields> getRawMetadataMap(InputStream inputStream) {
		Metadata metadata = extractMetadata(inputStream);

		return createDDMFieldsMap(metadata, _fields);
	}

	public void setTika(Tika tika) {
		_tika = tika;
	}

	protected Fields createDDMFields(Metadata metadata, Field[] fields) {
		Fields ddmFields = new Fields();

		for (Field field : fields) {
			String value = getMetadataValue(metadata, field);

			if (value == null) {
				continue;
			}

			com.liferay.portlet.dynamicdatamapping.storage.Field ddmField =
				new com.liferay.portlet.dynamicdatamapping.storage.Field(
					field.getName(), value);

			ddmFields.put(ddmField);
		}

		return ddmFields;
	}

	protected Map<String, Fields> createDDMFieldsMap(
		Metadata metadata, Map<String, Field[]> fieldsMap) {

		Map<String, Fields> ddmFieldsMap = new HashMap<String, Fields>();

		for (String key : fieldsMap.keySet()) {
			Field[] fields = fieldsMap.get(key);

			Fields ddmFields = createDDMFields(metadata, fields);

			Set<String> names = ddmFields.getNames();

			if (!names.isEmpty()) {
				ddmFieldsMap.put(key, ddmFields);
			}
		}

		return ddmFieldsMap;
	}

	protected Metadata extractMetadata(InputStream inputStream) {
		Metadata metadata = new Metadata();

		try {
			_tika.parse(inputStream, metadata);
		}
		catch (IOException ioe) {

		}
		finally {
			try {
				inputStream.close();
			}
			catch (IOException ioe) {
			}
		}

		return metadata;
	}

	protected Object getFieldValue(Metadata metadata, Field field) {
		Object fieldValue = null;

		try {
			fieldValue = field.get(metadata);
		}
		catch (IllegalAccessException iae) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"The property " + field.getName() +
						" will not be added to the metatada set");
			}
		}

		return fieldValue;
	}

	protected String getMetadataValue(Metadata metadata, Field field) {
		Object fieldValue = getFieldValue(metadata, field);

		if (fieldValue instanceof String) {
			return metadata.get((String)fieldValue);
		}
		else {
			Property property = (Property)fieldValue;

			return metadata.get(property.getName());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		TikaRawMetadataProcessor.class);

	private static Map<String, Field[]> _fields =
		new HashMap<String, Field[]>();

	private Tika _tika;

	static {
		_fields.put(
			ClimateForcast.class.getSimpleName(),
			ClimateForcast.class.getFields());
		_fields.put(
			CreativeCommons.class.getSimpleName(),
			CreativeCommons.class.getFields());
		_fields.put(
			DublinCore.class.getSimpleName(), DublinCore.class.getFields());
		_fields.put(
			Geographic.class.getSimpleName(), Geographic.class.getFields());
		_fields.put(
			HttpHeaders.class.getSimpleName(), HttpHeaders.class.getFields());
		_fields.put(
			Message.class.getSimpleName(), Message.class.getFields());
		_fields.put(
			MSOffice.class.getSimpleName(), MSOffice.class.getFields());
		_fields.put(TIFF.class.getSimpleName(), TIFF.class.getFields());
		_fields.put(
			TikaMetadataKeys.class.getSimpleName(),
			TikaMetadataKeys.class.getFields());
		_fields.put(
			TikaMimeKeys.class.getSimpleName(), TikaMimeKeys.class.getFields());
	}

}