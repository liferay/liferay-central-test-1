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

package com.liferay.portlet.dynamicdatamapping.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class DDMListItemSoap implements Serializable {
	public static DDMListItemSoap toSoapModel(DDMListItem model) {
		DDMListItemSoap soapModel = new DDMListItemSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setListItemId(model.getListItemId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setListId(model.getListId());

		return soapModel;
	}

	public static DDMListItemSoap[] toSoapModels(DDMListItem[] models) {
		DDMListItemSoap[] soapModels = new DDMListItemSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMListItemSoap[][] toSoapModels(DDMListItem[][] models) {
		DDMListItemSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DDMListItemSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMListItemSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMListItemSoap[] toSoapModels(List<DDMListItem> models) {
		List<DDMListItemSoap> soapModels = new ArrayList<DDMListItemSoap>(models.size());

		for (DDMListItem model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DDMListItemSoap[soapModels.size()]);
	}

	public DDMListItemSoap() {
	}

	public long getPrimaryKey() {
		return _listItemId;
	}

	public void setPrimaryKey(long pk) {
		setListItemId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getListItemId() {
		return _listItemId;
	}

	public void setListItemId(long listItemId) {
		_listItemId = listItemId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getListId() {
		return _listId;
	}

	public void setListId(long listId) {
		_listId = listId;
	}

	private String _uuid;
	private long _listItemId;
	private long _classNameId;
	private long _classPK;
	private long _listId;
}