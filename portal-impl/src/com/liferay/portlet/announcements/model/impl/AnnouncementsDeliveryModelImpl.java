/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.announcements.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.model.AnnouncementsDeliverySoap;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="AnnouncementsDeliveryModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>AnnouncementsDelivery</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.announcements.model.AnnouncementsDelivery
 * @see com.liferay.portlet.announcements.model.AnnouncementsDeliveryModel
 * @see com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl
 *
 */
public class AnnouncementsDeliveryModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "AnnouncementsDelivery";
	public static final Object[][] TABLE_COLUMNS = {
			{ "deliveryId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "type_", new Integer(Types.VARCHAR) },
			

			{ "email", new Integer(Types.BOOLEAN) },
			

			{ "sms", new Integer(Types.BOOLEAN) },
			

			{ "website", new Integer(Types.BOOLEAN) }
		};
	public static final String TABLE_SQL_CREATE = "create table AnnouncementsDelivery (deliveryId LONG not null primary key,companyId LONG,userId LONG,type_ VARCHAR(75) null,email BOOLEAN,sms BOOLEAN,website BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table AnnouncementsDelivery";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.announcements.model.AnnouncementsDelivery"),
			true);

	public static AnnouncementsDelivery toModel(
		AnnouncementsDeliverySoap soapModel) {
		AnnouncementsDelivery model = new AnnouncementsDeliveryImpl();

		model.setDeliveryId(soapModel.getDeliveryId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setType(soapModel.getType());
		model.setEmail(soapModel.getEmail());
		model.setSms(soapModel.getSms());
		model.setWebsite(soapModel.getWebsite());

		return model;
	}

	public static List<AnnouncementsDelivery> toModels(
		AnnouncementsDeliverySoap[] soapModels) {
		List<AnnouncementsDelivery> models = new ArrayList<AnnouncementsDelivery>(soapModels.length);

		for (AnnouncementsDeliverySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.announcements.model.AnnouncementsDelivery"));

	public AnnouncementsDeliveryModelImpl() {
	}

	public long getPrimaryKey() {
		return _deliveryId;
	}

	public void setPrimaryKey(long pk) {
		setDeliveryId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_deliveryId);
	}

	public long getDeliveryId() {
		return _deliveryId;
	}

	public void setDeliveryId(long deliveryId) {
		if (deliveryId != _deliveryId) {
			_deliveryId = deliveryId;
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		if (companyId != _companyId) {
			_companyId = companyId;
		}
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		if (userId != _userId) {
			_userId = userId;
		}
	}

	public String getType() {
		return GetterUtil.getString(_type);
	}

	public void setType(String type) {
		if (((type == null) && (_type != null)) ||
				((type != null) && (_type == null)) ||
				((type != null) && (_type != null) && !type.equals(_type))) {
			_type = type;
		}
	}

	public boolean getEmail() {
		return _email;
	}

	public boolean isEmail() {
		return _email;
	}

	public void setEmail(boolean email) {
		if (email != _email) {
			_email = email;
		}
	}

	public boolean getSms() {
		return _sms;
	}

	public boolean isSms() {
		return _sms;
	}

	public void setSms(boolean sms) {
		if (sms != _sms) {
			_sms = sms;
		}
	}

	public boolean getWebsite() {
		return _website;
	}

	public boolean isWebsite() {
		return _website;
	}

	public void setWebsite(boolean website) {
		if (website != _website) {
			_website = website;
		}
	}

	public AnnouncementsDelivery toEscapedModel() {
		if (isEscapedModel()) {
			return (AnnouncementsDelivery)this;
		}
		else {
			AnnouncementsDelivery model = new AnnouncementsDeliveryImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setDeliveryId(getDeliveryId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setType(HtmlUtil.escape(getType()));
			model.setEmail(getEmail());
			model.setSms(getSms());
			model.setWebsite(getWebsite());

			model = (AnnouncementsDelivery)Proxy.newProxyInstance(AnnouncementsDelivery.class.getClassLoader(),
					new Class[] { AnnouncementsDelivery.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(AnnouncementsDelivery.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		AnnouncementsDeliveryImpl clone = new AnnouncementsDeliveryImpl();

		clone.setDeliveryId(getDeliveryId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setType(getType());
		clone.setEmail(getEmail());
		clone.setSms(getSms());
		clone.setWebsite(getWebsite());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		AnnouncementsDeliveryImpl announcementsDelivery = (AnnouncementsDeliveryImpl)obj;

		long pk = announcementsDelivery.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		AnnouncementsDeliveryImpl announcementsDelivery = null;

		try {
			announcementsDelivery = (AnnouncementsDeliveryImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = announcementsDelivery.getPrimaryKey();

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

	private long _deliveryId;
	private long _companyId;
	private long _userId;
	private String _type;
	private boolean _email;
	private boolean _sms;
	private boolean _website;
	private ExpandoBridge _expandoBridge;
}