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

package com.liferay.portlet.social.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="SocialEquityLogSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.social.service.http.SocialEquityLogServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.social.service.http.SocialEquityLogServiceSoap
 * @generated
 */
public class SocialEquityLogSoap implements Serializable {
	public static SocialEquityLogSoap toSoapModel(SocialEquityLog model) {
		SocialEquityLogSoap soapModel = new SocialEquityLogSoap();

		soapModel.setEquityLogId(model.getEquityLogId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setAssetEntryId(model.getAssetEntryId());
		soapModel.setActionId(model.getActionId());
		soapModel.setActionDate(model.getActionDate());
		soapModel.setType(model.getType());
		soapModel.setValue(model.getValue());
		soapModel.setValidity(model.getValidity());

		return soapModel;
	}

	public static SocialEquityLogSoap[] toSoapModels(SocialEquityLog[] models) {
		SocialEquityLogSoap[] soapModels = new SocialEquityLogSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialEquityLogSoap[][] toSoapModels(
		SocialEquityLog[][] models) {
		SocialEquityLogSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SocialEquityLogSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SocialEquityLogSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialEquityLogSoap[] toSoapModels(
		List<SocialEquityLog> models) {
		List<SocialEquityLogSoap> soapModels = new ArrayList<SocialEquityLogSoap>(models.size());

		for (SocialEquityLog model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SocialEquityLogSoap[soapModels.size()]);
	}

	public SocialEquityLogSoap() {
	}

	public long getPrimaryKey() {
		return _equityLogId;
	}

	public void setPrimaryKey(long pk) {
		setEquityLogId(pk);
	}

	public long getEquityLogId() {
		return _equityLogId;
	}

	public void setEquityLogId(long equityLogId) {
		_equityLogId = equityLogId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getAssetEntryId() {
		return _assetEntryId;
	}

	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;
	}

	public String getActionId() {
		return _actionId;
	}

	public void setActionId(String actionId) {
		_actionId = actionId;
	}

	public int getActionDate() {
		return _actionDate;
	}

	public void setActionDate(int actionDate) {
		_actionDate = actionDate;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public int getValue() {
		return _value;
	}

	public void setValue(int value) {
		_value = value;
	}

	public int getValidity() {
		return _validity;
	}

	public void setValidity(int validity) {
		_validity = validity;
	}

	private long _equityLogId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private long _assetEntryId;
	private String _actionId;
	private int _actionDate;
	private int _type;
	private int _value;
	private int _validity;
}