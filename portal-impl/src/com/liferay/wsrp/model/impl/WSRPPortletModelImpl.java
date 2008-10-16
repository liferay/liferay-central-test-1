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

package com.liferay.wsrp.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import com.liferay.wsrp.model.WSRPPortlet;
import com.liferay.wsrp.model.WSRPPortletSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="WSRPPortletModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>WSRPPortlet</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.wsrp.model.WSRPPortlet
 * @see com.liferay.wsrp.model.WSRPPortletModel
 * @see com.liferay.wsrp.model.impl.WSRPPortletImpl
 *
 */
public class WSRPPortletModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "WSRPPortlet";
	public static final Object[][] TABLE_COLUMNS = {
			{ "portletId", new Integer(Types.BIGINT) },
			

			{ "name", new Integer(Types.VARCHAR) },
			

			{ "channelName", new Integer(Types.VARCHAR) },
			

			{ "title", new Integer(Types.VARCHAR) },
			

			{ "shortTitle", new Integer(Types.VARCHAR) },
			

			{ "displayName", new Integer(Types.VARCHAR) },
			

			{ "keywords", new Integer(Types.VARCHAR) },
			

			{ "status", new Integer(Types.INTEGER) },
			

			{ "producerEntityId", new Integer(Types.VARCHAR) },
			

			{ "consumerId", new Integer(Types.VARCHAR) },
			

			{ "portletHandle", new Integer(Types.VARCHAR) },
			

			{ "mimeTypes", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table WSRPPortlet (portletId LONG not null primary key,name VARCHAR(75) null,channelName VARCHAR(75) null,title VARCHAR(75) null,shortTitle VARCHAR(75) null,displayName VARCHAR(75) null,keywords VARCHAR(75) null,status INTEGER,producerEntityId VARCHAR(75) null,consumerId VARCHAR(75) null,portletHandle VARCHAR(75) null,mimeTypes STRING null)";
	public static final String TABLE_SQL_DROP = "drop table WSRPPortlet";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.wsrp.model.WSRPPortlet"),
			true);

	public static WSRPPortlet toModel(WSRPPortletSoap soapModel) {
		WSRPPortlet model = new WSRPPortletImpl();

		model.setPortletId(soapModel.getPortletId());
		model.setName(soapModel.getName());
		model.setChannelName(soapModel.getChannelName());
		model.setTitle(soapModel.getTitle());
		model.setShortTitle(soapModel.getShortTitle());
		model.setDisplayName(soapModel.getDisplayName());
		model.setKeywords(soapModel.getKeywords());
		model.setStatus(soapModel.getStatus());
		model.setProducerEntityId(soapModel.getProducerEntityId());
		model.setConsumerId(soapModel.getConsumerId());
		model.setPortletHandle(soapModel.getPortletHandle());
		model.setMimeTypes(soapModel.getMimeTypes());

		return model;
	}

	public static List<WSRPPortlet> toModels(WSRPPortletSoap[] soapModels) {
		List<WSRPPortlet> models = new ArrayList<WSRPPortlet>(soapModels.length);

		for (WSRPPortletSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.wsrp.model.WSRPPortlet"));

	public WSRPPortletModelImpl() {
	}

	public long getPrimaryKey() {
		return _portletId;
	}

	public void setPrimaryKey(long pk) {
		setPortletId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_portletId);
	}

	public long getPortletId() {
		return _portletId;
	}

	public void setPortletId(long portletId) {
		if (portletId != _portletId) {
			_portletId = portletId;
		}
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		if (((name == null) && (_name != null)) ||
				((name != null) && (_name == null)) ||
				((name != null) && (_name != null) && !name.equals(_name))) {
			_name = name;
		}
	}

	public String getChannelName() {
		return GetterUtil.getString(_channelName);
	}

	public void setChannelName(String channelName) {
		if (((channelName == null) && (_channelName != null)) ||
				((channelName != null) && (_channelName == null)) ||
				((channelName != null) && (_channelName != null) &&
				!channelName.equals(_channelName))) {
			_channelName = channelName;
		}
	}

	public String getTitle() {
		return GetterUtil.getString(_title);
	}

	public void setTitle(String title) {
		if (((title == null) && (_title != null)) ||
				((title != null) && (_title == null)) ||
				((title != null) && (_title != null) && !title.equals(_title))) {
			_title = title;
		}
	}

	public String getShortTitle() {
		return GetterUtil.getString(_shortTitle);
	}

	public void setShortTitle(String shortTitle) {
		if (((shortTitle == null) && (_shortTitle != null)) ||
				((shortTitle != null) && (_shortTitle == null)) ||
				((shortTitle != null) && (_shortTitle != null) &&
				!shortTitle.equals(_shortTitle))) {
			_shortTitle = shortTitle;
		}
	}

	public String getDisplayName() {
		return GetterUtil.getString(_displayName);
	}

	public void setDisplayName(String displayName) {
		if (((displayName == null) && (_displayName != null)) ||
				((displayName != null) && (_displayName == null)) ||
				((displayName != null) && (_displayName != null) &&
				!displayName.equals(_displayName))) {
			_displayName = displayName;
		}
	}

	public String getKeywords() {
		return GetterUtil.getString(_keywords);
	}

	public void setKeywords(String keywords) {
		if (((keywords == null) && (_keywords != null)) ||
				((keywords != null) && (_keywords == null)) ||
				((keywords != null) && (_keywords != null) &&
				!keywords.equals(_keywords))) {
			_keywords = keywords;
		}
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		if (status != _status) {
			_status = status;
		}
	}

	public String getProducerEntityId() {
		return GetterUtil.getString(_producerEntityId);
	}

	public void setProducerEntityId(String producerEntityId) {
		if (((producerEntityId == null) && (_producerEntityId != null)) ||
				((producerEntityId != null) && (_producerEntityId == null)) ||
				((producerEntityId != null) && (_producerEntityId != null) &&
				!producerEntityId.equals(_producerEntityId))) {
			_producerEntityId = producerEntityId;
		}
	}

	public String getConsumerId() {
		return GetterUtil.getString(_consumerId);
	}

	public void setConsumerId(String consumerId) {
		if (((consumerId == null) && (_consumerId != null)) ||
				((consumerId != null) && (_consumerId == null)) ||
				((consumerId != null) && (_consumerId != null) &&
				!consumerId.equals(_consumerId))) {
			_consumerId = consumerId;
		}
	}

	public String getPortletHandle() {
		return GetterUtil.getString(_portletHandle);
	}

	public void setPortletHandle(String portletHandle) {
		if (((portletHandle == null) && (_portletHandle != null)) ||
				((portletHandle != null) && (_portletHandle == null)) ||
				((portletHandle != null) && (_portletHandle != null) &&
				!portletHandle.equals(_portletHandle))) {
			_portletHandle = portletHandle;
		}
	}

	public String getMimeTypes() {
		return GetterUtil.getString(_mimeTypes);
	}

	public void setMimeTypes(String mimeTypes) {
		if (((mimeTypes == null) && (_mimeTypes != null)) ||
				((mimeTypes != null) && (_mimeTypes == null)) ||
				((mimeTypes != null) && (_mimeTypes != null) &&
				!mimeTypes.equals(_mimeTypes))) {
			_mimeTypes = mimeTypes;
		}
	}

	public WSRPPortlet toEscapedModel() {
		if (isEscapedModel()) {
			return (WSRPPortlet)this;
		}
		else {
			WSRPPortlet model = new WSRPPortletImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setPortletId(getPortletId());
			model.setName(HtmlUtil.escape(getName()));
			model.setChannelName(HtmlUtil.escape(getChannelName()));
			model.setTitle(HtmlUtil.escape(getTitle()));
			model.setShortTitle(HtmlUtil.escape(getShortTitle()));
			model.setDisplayName(HtmlUtil.escape(getDisplayName()));
			model.setKeywords(HtmlUtil.escape(getKeywords()));
			model.setStatus(getStatus());
			model.setProducerEntityId(HtmlUtil.escape(getProducerEntityId()));
			model.setConsumerId(HtmlUtil.escape(getConsumerId()));
			model.setPortletHandle(HtmlUtil.escape(getPortletHandle()));
			model.setMimeTypes(HtmlUtil.escape(getMimeTypes()));

			model = (WSRPPortlet)Proxy.newProxyInstance(WSRPPortlet.class.getClassLoader(),
					new Class[] { WSRPPortlet.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(WSRPPortlet.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		WSRPPortletImpl clone = new WSRPPortletImpl();

		clone.setPortletId(getPortletId());
		clone.setName(getName());
		clone.setChannelName(getChannelName());
		clone.setTitle(getTitle());
		clone.setShortTitle(getShortTitle());
		clone.setDisplayName(getDisplayName());
		clone.setKeywords(getKeywords());
		clone.setStatus(getStatus());
		clone.setProducerEntityId(getProducerEntityId());
		clone.setConsumerId(getConsumerId());
		clone.setPortletHandle(getPortletHandle());
		clone.setMimeTypes(getMimeTypes());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		WSRPPortletImpl wsrpPortlet = (WSRPPortletImpl)obj;

		long pk = wsrpPortlet.getPrimaryKey();

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

		WSRPPortletImpl wsrpPortlet = null;

		try {
			wsrpPortlet = (WSRPPortletImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = wsrpPortlet.getPrimaryKey();

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

	private long _portletId;
	private String _name;
	private String _channelName;
	private String _title;
	private String _shortTitle;
	private String _displayName;
	private String _keywords;
	private int _status;
	private String _producerEntityId;
	private String _consumerId;
	private String _portletHandle;
	private String _mimeTypes;
	private ExpandoBridge _expandoBridge;
}