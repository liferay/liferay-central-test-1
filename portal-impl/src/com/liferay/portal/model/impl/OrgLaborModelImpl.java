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

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.OrgLaborSoap;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="OrgLaborModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the OrgLabor table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgLaborImpl
 * @see       com.liferay.portal.model.OrgLabor
 * @see       com.liferay.portal.model.OrgLaborModel
 * @generated
 */
public class OrgLaborModelImpl extends BaseModelImpl<OrgLabor> {
	public static final String TABLE_NAME = "OrgLabor";
	public static final Object[][] TABLE_COLUMNS = {
			{ "orgLaborId", new Integer(Types.BIGINT) },
			{ "organizationId", new Integer(Types.BIGINT) },
			{ "typeId", new Integer(Types.INTEGER) },
			{ "sunOpen", new Integer(Types.INTEGER) },
			{ "sunClose", new Integer(Types.INTEGER) },
			{ "monOpen", new Integer(Types.INTEGER) },
			{ "monClose", new Integer(Types.INTEGER) },
			{ "tueOpen", new Integer(Types.INTEGER) },
			{ "tueClose", new Integer(Types.INTEGER) },
			{ "wedOpen", new Integer(Types.INTEGER) },
			{ "wedClose", new Integer(Types.INTEGER) },
			{ "thuOpen", new Integer(Types.INTEGER) },
			{ "thuClose", new Integer(Types.INTEGER) },
			{ "friOpen", new Integer(Types.INTEGER) },
			{ "friClose", new Integer(Types.INTEGER) },
			{ "satOpen", new Integer(Types.INTEGER) },
			{ "satClose", new Integer(Types.INTEGER) }
		};
	public static final String TABLE_SQL_CREATE = "create table OrgLabor (orgLaborId LONG not null primary key,organizationId LONG,typeId INTEGER,sunOpen INTEGER,sunClose INTEGER,monOpen INTEGER,monClose INTEGER,tueOpen INTEGER,tueClose INTEGER,wedOpen INTEGER,wedClose INTEGER,thuOpen INTEGER,thuClose INTEGER,friOpen INTEGER,friClose INTEGER,satOpen INTEGER,satClose INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table OrgLabor";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.OrgLabor"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.OrgLabor"),
			true);

	public static OrgLabor toModel(OrgLaborSoap soapModel) {
		OrgLabor model = new OrgLaborImpl();

		model.setOrgLaborId(soapModel.getOrgLaborId());
		model.setOrganizationId(soapModel.getOrganizationId());
		model.setTypeId(soapModel.getTypeId());
		model.setSunOpen(soapModel.getSunOpen());
		model.setSunClose(soapModel.getSunClose());
		model.setMonOpen(soapModel.getMonOpen());
		model.setMonClose(soapModel.getMonClose());
		model.setTueOpen(soapModel.getTueOpen());
		model.setTueClose(soapModel.getTueClose());
		model.setWedOpen(soapModel.getWedOpen());
		model.setWedClose(soapModel.getWedClose());
		model.setThuOpen(soapModel.getThuOpen());
		model.setThuClose(soapModel.getThuClose());
		model.setFriOpen(soapModel.getFriOpen());
		model.setFriClose(soapModel.getFriClose());
		model.setSatOpen(soapModel.getSatOpen());
		model.setSatClose(soapModel.getSatClose());

		return model;
	}

	public static List<OrgLabor> toModels(OrgLaborSoap[] soapModels) {
		List<OrgLabor> models = new ArrayList<OrgLabor>(soapModels.length);

		for (OrgLaborSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.OrgLabor"));

	public OrgLaborModelImpl() {
	}

	public long getPrimaryKey() {
		return _orgLaborId;
	}

	public void setPrimaryKey(long pk) {
		setOrgLaborId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_orgLaborId);
	}

	public long getOrgLaborId() {
		return _orgLaborId;
	}

	public void setOrgLaborId(long orgLaborId) {
		_orgLaborId = orgLaborId;
	}

	public long getOrganizationId() {
		return _organizationId;
	}

	public void setOrganizationId(long organizationId) {
		_organizationId = organizationId;
	}

	public int getTypeId() {
		return _typeId;
	}

	public void setTypeId(int typeId) {
		_typeId = typeId;
	}

	public int getSunOpen() {
		return _sunOpen;
	}

	public void setSunOpen(int sunOpen) {
		_sunOpen = sunOpen;
	}

	public int getSunClose() {
		return _sunClose;
	}

	public void setSunClose(int sunClose) {
		_sunClose = sunClose;
	}

	public int getMonOpen() {
		return _monOpen;
	}

	public void setMonOpen(int monOpen) {
		_monOpen = monOpen;
	}

	public int getMonClose() {
		return _monClose;
	}

	public void setMonClose(int monClose) {
		_monClose = monClose;
	}

	public int getTueOpen() {
		return _tueOpen;
	}

	public void setTueOpen(int tueOpen) {
		_tueOpen = tueOpen;
	}

	public int getTueClose() {
		return _tueClose;
	}

	public void setTueClose(int tueClose) {
		_tueClose = tueClose;
	}

	public int getWedOpen() {
		return _wedOpen;
	}

	public void setWedOpen(int wedOpen) {
		_wedOpen = wedOpen;
	}

	public int getWedClose() {
		return _wedClose;
	}

	public void setWedClose(int wedClose) {
		_wedClose = wedClose;
	}

	public int getThuOpen() {
		return _thuOpen;
	}

	public void setThuOpen(int thuOpen) {
		_thuOpen = thuOpen;
	}

	public int getThuClose() {
		return _thuClose;
	}

	public void setThuClose(int thuClose) {
		_thuClose = thuClose;
	}

	public int getFriOpen() {
		return _friOpen;
	}

	public void setFriOpen(int friOpen) {
		_friOpen = friOpen;
	}

	public int getFriClose() {
		return _friClose;
	}

	public void setFriClose(int friClose) {
		_friClose = friClose;
	}

	public int getSatOpen() {
		return _satOpen;
	}

	public void setSatOpen(int satOpen) {
		_satOpen = satOpen;
	}

	public int getSatClose() {
		return _satClose;
	}

	public void setSatClose(int satClose) {
		_satClose = satClose;
	}

	public OrgLabor toEscapedModel() {
		if (isEscapedModel()) {
			return (OrgLabor)this;
		}
		else {
			OrgLabor model = new OrgLaborImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setOrgLaborId(getOrgLaborId());
			model.setOrganizationId(getOrganizationId());
			model.setTypeId(getTypeId());
			model.setSunOpen(getSunOpen());
			model.setSunClose(getSunClose());
			model.setMonOpen(getMonOpen());
			model.setMonClose(getMonClose());
			model.setTueOpen(getTueOpen());
			model.setTueClose(getTueClose());
			model.setWedOpen(getWedOpen());
			model.setWedClose(getWedClose());
			model.setThuOpen(getThuOpen());
			model.setThuClose(getThuClose());
			model.setFriOpen(getFriOpen());
			model.setFriClose(getFriClose());
			model.setSatOpen(getSatOpen());
			model.setSatClose(getSatClose());

			model = (OrgLabor)Proxy.newProxyInstance(OrgLabor.class.getClassLoader(),
					new Class[] { OrgLabor.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(OrgLabor.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		OrgLaborImpl clone = new OrgLaborImpl();

		clone.setOrgLaborId(getOrgLaborId());
		clone.setOrganizationId(getOrganizationId());
		clone.setTypeId(getTypeId());
		clone.setSunOpen(getSunOpen());
		clone.setSunClose(getSunClose());
		clone.setMonOpen(getMonOpen());
		clone.setMonClose(getMonClose());
		clone.setTueOpen(getTueOpen());
		clone.setTueClose(getTueClose());
		clone.setWedOpen(getWedOpen());
		clone.setWedClose(getWedClose());
		clone.setThuOpen(getThuOpen());
		clone.setThuClose(getThuClose());
		clone.setFriOpen(getFriOpen());
		clone.setFriClose(getFriClose());
		clone.setSatOpen(getSatOpen());
		clone.setSatClose(getSatClose());

		return clone;
	}

	public int compareTo(OrgLabor orgLabor) {
		int value = 0;

		if (getOrganizationId() < orgLabor.getOrganizationId()) {
			value = -1;
		}
		else if (getOrganizationId() > orgLabor.getOrganizationId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (getTypeId() < orgLabor.getTypeId()) {
			value = -1;
		}
		else if (getTypeId() > orgLabor.getTypeId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		OrgLabor orgLabor = null;

		try {
			orgLabor = (OrgLabor)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = orgLabor.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{orgLaborId=");
		sb.append(getOrgLaborId());
		sb.append(", organizationId=");
		sb.append(getOrganizationId());
		sb.append(", typeId=");
		sb.append(getTypeId());
		sb.append(", sunOpen=");
		sb.append(getSunOpen());
		sb.append(", sunClose=");
		sb.append(getSunClose());
		sb.append(", monOpen=");
		sb.append(getMonOpen());
		sb.append(", monClose=");
		sb.append(getMonClose());
		sb.append(", tueOpen=");
		sb.append(getTueOpen());
		sb.append(", tueClose=");
		sb.append(getTueClose());
		sb.append(", wedOpen=");
		sb.append(getWedOpen());
		sb.append(", wedClose=");
		sb.append(getWedClose());
		sb.append(", thuOpen=");
		sb.append(getThuOpen());
		sb.append(", thuClose=");
		sb.append(getThuClose());
		sb.append(", friOpen=");
		sb.append(getFriOpen());
		sb.append(", friClose=");
		sb.append(getFriClose());
		sb.append(", satOpen=");
		sb.append(getSatOpen());
		sb.append(", satClose=");
		sb.append(getSatClose());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.OrgLabor");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>orgLaborId</column-name><column-value><![CDATA[");
		sb.append(getOrgLaborId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>organizationId</column-name><column-value><![CDATA[");
		sb.append(getOrganizationId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>typeId</column-name><column-value><![CDATA[");
		sb.append(getTypeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>sunOpen</column-name><column-value><![CDATA[");
		sb.append(getSunOpen());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>sunClose</column-name><column-value><![CDATA[");
		sb.append(getSunClose());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>monOpen</column-name><column-value><![CDATA[");
		sb.append(getMonOpen());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>monClose</column-name><column-value><![CDATA[");
		sb.append(getMonClose());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>tueOpen</column-name><column-value><![CDATA[");
		sb.append(getTueOpen());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>tueClose</column-name><column-value><![CDATA[");
		sb.append(getTueClose());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>wedOpen</column-name><column-value><![CDATA[");
		sb.append(getWedOpen());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>wedClose</column-name><column-value><![CDATA[");
		sb.append(getWedClose());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>thuOpen</column-name><column-value><![CDATA[");
		sb.append(getThuOpen());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>thuClose</column-name><column-value><![CDATA[");
		sb.append(getThuClose());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>friOpen</column-name><column-value><![CDATA[");
		sb.append(getFriOpen());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>friClose</column-name><column-value><![CDATA[");
		sb.append(getFriClose());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>satOpen</column-name><column-value><![CDATA[");
		sb.append(getSatOpen());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>satClose</column-name><column-value><![CDATA[");
		sb.append(getSatClose());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _orgLaborId;
	private long _organizationId;
	private int _typeId;
	private int _sunOpen;
	private int _sunClose;
	private int _monOpen;
	private int _monClose;
	private int _tueOpen;
	private int _tueClose;
	private int _wedOpen;
	private int _wedClose;
	private int _thuOpen;
	private int _thuClose;
	private int _friOpen;
	private int _friClose;
	private int _satOpen;
	private int _satClose;
	private transient ExpandoBridge _expandoBridge;
}