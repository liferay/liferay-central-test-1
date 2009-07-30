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
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationSoap;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="OrganizationModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Organization_ table in the
 * database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    OrganizationImpl
 * @see    com.liferay.portal.model.Organization
 * @see    com.liferay.portal.model.OrganizationModel
 */
public class OrganizationModelImpl extends BaseModelImpl<Organization> {
	public static final String TABLE_NAME = "Organization_";
	public static final Object[][] TABLE_COLUMNS = {
			{ "organizationId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "parentOrganizationId", new Integer(Types.BIGINT) },
			

			{ "leftOrganizationId", new Integer(Types.BIGINT) },
			

			{ "rightOrganizationId", new Integer(Types.BIGINT) },
			

			{ "name", new Integer(Types.VARCHAR) },
			

			{ "type_", new Integer(Types.VARCHAR) },
			

			{ "recursable", new Integer(Types.BOOLEAN) },
			

			{ "regionId", new Integer(Types.BIGINT) },
			

			{ "countryId", new Integer(Types.BIGINT) },
			

			{ "statusId", new Integer(Types.INTEGER) },
			

			{ "comments", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table Organization_ (organizationId LONG not null primary key,companyId LONG,parentOrganizationId LONG,leftOrganizationId LONG,rightOrganizationId LONG,name VARCHAR(100) null,type_ VARCHAR(75) null,recursable BOOLEAN,regionId LONG,countryId LONG,statusId INTEGER,comments STRING null)";
	public static final String TABLE_SQL_DROP = "drop table Organization_";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Organization"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Organization"),
			true);

	public static Organization toModel(OrganizationSoap soapModel) {
		Organization model = new OrganizationImpl();

		model.setOrganizationId(soapModel.getOrganizationId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setParentOrganizationId(soapModel.getParentOrganizationId());
		model.setLeftOrganizationId(soapModel.getLeftOrganizationId());
		model.setRightOrganizationId(soapModel.getRightOrganizationId());
		model.setName(soapModel.getName());
		model.setType(soapModel.getType());
		model.setRecursable(soapModel.getRecursable());
		model.setRegionId(soapModel.getRegionId());
		model.setCountryId(soapModel.getCountryId());
		model.setStatusId(soapModel.getStatusId());
		model.setComments(soapModel.getComments());

		return model;
	}

	public static List<Organization> toModels(OrganizationSoap[] soapModels) {
		List<Organization> models = new ArrayList<Organization>(soapModels.length);

		for (OrganizationSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final boolean FINDER_CACHE_ENABLED_GROUPS_ORGS = com.liferay.portal.model.impl.GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_ORGS;
	public static final boolean FINDER_CACHE_ENABLED_USERS_ORGS = com.liferay.portal.model.impl.UserModelImpl.FINDER_CACHE_ENABLED_USERS_ORGS;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Organization"));

	public OrganizationModelImpl() {
	}

	public long getPrimaryKey() {
		return _organizationId;
	}

	public void setPrimaryKey(long pk) {
		setOrganizationId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_organizationId);
	}

	public long getOrganizationId() {
		return _organizationId;
	}

	public void setOrganizationId(long organizationId) {
		_organizationId = organizationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = companyId;
		}
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	public long getParentOrganizationId() {
		return _parentOrganizationId;
	}

	public void setParentOrganizationId(long parentOrganizationId) {
		_parentOrganizationId = parentOrganizationId;

		if (!_setOriginalParentOrganizationId) {
			_setOriginalParentOrganizationId = true;

			_originalParentOrganizationId = parentOrganizationId;
		}
	}

	public long getOriginalParentOrganizationId() {
		return _originalParentOrganizationId;
	}

	public long getLeftOrganizationId() {
		return _leftOrganizationId;
	}

	public void setLeftOrganizationId(long leftOrganizationId) {
		_leftOrganizationId = leftOrganizationId;
	}

	public long getRightOrganizationId() {
		return _rightOrganizationId;
	}

	public void setRightOrganizationId(long rightOrganizationId) {
		_rightOrganizationId = rightOrganizationId;
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		_name = name;

		if (_originalName == null) {
			_originalName = name;
		}
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
	}

	public String getType() {
		return GetterUtil.getString(_type);
	}

	public void setType(String type) {
		_type = type;
	}

	public boolean getRecursable() {
		return _recursable;
	}

	public boolean isRecursable() {
		return _recursable;
	}

	public void setRecursable(boolean recursable) {
		_recursable = recursable;
	}

	public long getRegionId() {
		return _regionId;
	}

	public void setRegionId(long regionId) {
		_regionId = regionId;
	}

	public long getCountryId() {
		return _countryId;
	}

	public void setCountryId(long countryId) {
		_countryId = countryId;
	}

	public int getStatusId() {
		return _statusId;
	}

	public void setStatusId(int statusId) {
		_statusId = statusId;
	}

	public String getComments() {
		return GetterUtil.getString(_comments);
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public Organization toEscapedModel() {
		if (isEscapedModel()) {
			return (Organization)this;
		}
		else {
			Organization model = new OrganizationImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setOrganizationId(getOrganizationId());
			model.setCompanyId(getCompanyId());
			model.setParentOrganizationId(getParentOrganizationId());
			model.setLeftOrganizationId(getLeftOrganizationId());
			model.setRightOrganizationId(getRightOrganizationId());
			model.setName(HtmlUtil.escape(getName()));
			model.setType(getType());
			model.setRecursable(getRecursable());
			model.setRegionId(getRegionId());
			model.setCountryId(getCountryId());
			model.setStatusId(getStatusId());
			model.setComments(HtmlUtil.escape(getComments()));

			model = (Organization)Proxy.newProxyInstance(Organization.class.getClassLoader(),
					new Class[] { Organization.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(Organization.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		OrganizationImpl clone = new OrganizationImpl();

		clone.setOrganizationId(getOrganizationId());
		clone.setCompanyId(getCompanyId());
		clone.setParentOrganizationId(getParentOrganizationId());
		clone.setLeftOrganizationId(getLeftOrganizationId());
		clone.setRightOrganizationId(getRightOrganizationId());
		clone.setName(getName());
		clone.setType(getType());
		clone.setRecursable(getRecursable());
		clone.setRegionId(getRegionId());
		clone.setCountryId(getCountryId());
		clone.setStatusId(getStatusId());
		clone.setComments(getComments());

		return clone;
	}

	public int compareTo(Organization organization) {
		int value = 0;

		value = getName().compareTo(organization.getName());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		Organization organization = null;

		try {
			organization = (Organization)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = organization.getPrimaryKey();

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

		sb.append("{organizationId=");
		sb.append(getOrganizationId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", parentOrganizationId=");
		sb.append(getParentOrganizationId());
		sb.append(", leftOrganizationId=");
		sb.append(getLeftOrganizationId());
		sb.append(", rightOrganizationId=");
		sb.append(getRightOrganizationId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", recursable=");
		sb.append(getRecursable());
		sb.append(", regionId=");
		sb.append(getRegionId());
		sb.append(", countryId=");
		sb.append(getCountryId());
		sb.append(", statusId=");
		sb.append(getStatusId());
		sb.append(", comments=");
		sb.append(getComments());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Organization");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>organizationId</column-name><column-value><![CDATA[");
		sb.append(getOrganizationId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>parentOrganizationId</column-name><column-value><![CDATA[");
		sb.append(getParentOrganizationId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>leftOrganizationId</column-name><column-value><![CDATA[");
		sb.append(getLeftOrganizationId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>rightOrganizationId</column-name><column-value><![CDATA[");
		sb.append(getRightOrganizationId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>recursable</column-name><column-value><![CDATA[");
		sb.append(getRecursable());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>regionId</column-name><column-value><![CDATA[");
		sb.append(getRegionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>countryId</column-name><column-value><![CDATA[");
		sb.append(getCountryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusId</column-name><column-value><![CDATA[");
		sb.append(getStatusId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>comments</column-name><column-value><![CDATA[");
		sb.append(getComments());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _organizationId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _parentOrganizationId;
	private long _originalParentOrganizationId;
	private boolean _setOriginalParentOrganizationId;
	private long _leftOrganizationId;
	private long _rightOrganizationId;
	private String _name;
	private String _originalName;
	private String _type;
	private boolean _recursable;
	private long _regionId;
	private long _countryId;
	private int _statusId;
	private String _comments;
	private transient ExpandoBridge _expandoBridge;
}