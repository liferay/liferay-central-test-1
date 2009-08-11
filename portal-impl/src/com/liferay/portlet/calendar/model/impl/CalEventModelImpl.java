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

package com.liferay.portlet.calendar.model.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.model.CalEventSoap;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="CalEventModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the CalEvent table in the
 * database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    CalEventImpl
 * @see    com.liferay.portlet.calendar.model.CalEvent
 * @see    com.liferay.portlet.calendar.model.CalEventModel
 * @generated
 */
public class CalEventModelImpl extends BaseModelImpl<CalEvent> {
	public static final String TABLE_NAME = "CalEvent";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			

			{ "eventId", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "userName", new Integer(Types.VARCHAR) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "title", new Integer(Types.VARCHAR) },
			

			{ "description", new Integer(Types.VARCHAR) },
			

			{ "startDate", new Integer(Types.TIMESTAMP) },
			

			{ "endDate", new Integer(Types.TIMESTAMP) },
			

			{ "durationHour", new Integer(Types.INTEGER) },
			

			{ "durationMinute", new Integer(Types.INTEGER) },
			

			{ "allDay", new Integer(Types.BOOLEAN) },
			

			{ "timeZoneSensitive", new Integer(Types.BOOLEAN) },
			

			{ "type_", new Integer(Types.VARCHAR) },
			

			{ "repeating", new Integer(Types.BOOLEAN) },
			

			{ "recurrence", new Integer(Types.CLOB) },
			

			{ "remindBy", new Integer(Types.INTEGER) },
			

			{ "firstReminder", new Integer(Types.INTEGER) },
			

			{ "secondReminder", new Integer(Types.INTEGER) }
		};
	public static final String TABLE_SQL_CREATE = "create table CalEvent (uuid_ VARCHAR(75) null,eventId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,title VARCHAR(75) null,description STRING null,startDate DATE null,endDate DATE null,durationHour INTEGER,durationMinute INTEGER,allDay BOOLEAN,timeZoneSensitive BOOLEAN,type_ VARCHAR(75) null,repeating BOOLEAN,recurrence TEXT null,remindBy INTEGER,firstReminder INTEGER,secondReminder INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table CalEvent";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.calendar.model.CalEvent"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.calendar.model.CalEvent"),
			true);

	public static CalEvent toModel(CalEventSoap soapModel) {
		CalEvent model = new CalEventImpl();

		model.setUuid(soapModel.getUuid());
		model.setEventId(soapModel.getEventId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setTitle(soapModel.getTitle());
		model.setDescription(soapModel.getDescription());
		model.setStartDate(soapModel.getStartDate());
		model.setEndDate(soapModel.getEndDate());
		model.setDurationHour(soapModel.getDurationHour());
		model.setDurationMinute(soapModel.getDurationMinute());
		model.setAllDay(soapModel.getAllDay());
		model.setTimeZoneSensitive(soapModel.getTimeZoneSensitive());
		model.setType(soapModel.getType());
		model.setRepeating(soapModel.getRepeating());
		model.setRecurrence(soapModel.getRecurrence());
		model.setRemindBy(soapModel.getRemindBy());
		model.setFirstReminder(soapModel.getFirstReminder());
		model.setSecondReminder(soapModel.getSecondReminder());

		return model;
	}

	public static List<CalEvent> toModels(CalEventSoap[] soapModels) {
		List<CalEvent> models = new ArrayList<CalEvent>(soapModels.length);

		for (CalEventSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.calendar.model.CalEvent"));

	public CalEventModelImpl() {
	}

	public long getPrimaryKey() {
		return _eventId;
	}

	public void setPrimaryKey(long pk) {
		setEventId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_eventId);
	}

	public String getUuid() {
		return GetterUtil.getString(_uuid);
	}

	public void setUuid(String uuid) {
		_uuid = uuid;

		if (_originalUuid == null) {
			_originalUuid = uuid;
		}
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	public long getEventId() {
		return _eventId;
	}

	public void setEventId(long eventId) {
		_eventId = eventId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = groupId;
		}
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
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

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getTitle() {
		return GetterUtil.getString(_title);
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		_description = description;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public int getDurationHour() {
		return _durationHour;
	}

	public void setDurationHour(int durationHour) {
		_durationHour = durationHour;
	}

	public int getDurationMinute() {
		return _durationMinute;
	}

	public void setDurationMinute(int durationMinute) {
		_durationMinute = durationMinute;
	}

	public boolean getAllDay() {
		return _allDay;
	}

	public boolean isAllDay() {
		return _allDay;
	}

	public void setAllDay(boolean allDay) {
		_allDay = allDay;
	}

	public boolean getTimeZoneSensitive() {
		return _timeZoneSensitive;
	}

	public boolean isTimeZoneSensitive() {
		return _timeZoneSensitive;
	}

	public void setTimeZoneSensitive(boolean timeZoneSensitive) {
		_timeZoneSensitive = timeZoneSensitive;
	}

	public String getType() {
		return GetterUtil.getString(_type);
	}

	public void setType(String type) {
		_type = type;
	}

	public boolean getRepeating() {
		return _repeating;
	}

	public boolean isRepeating() {
		return _repeating;
	}

	public void setRepeating(boolean repeating) {
		_repeating = repeating;
	}

	public String getRecurrence() {
		return GetterUtil.getString(_recurrence);
	}

	public void setRecurrence(String recurrence) {
		_recurrence = recurrence;
	}

	public int getRemindBy() {
		return _remindBy;
	}

	public void setRemindBy(int remindBy) {
		_remindBy = remindBy;
	}

	public int getFirstReminder() {
		return _firstReminder;
	}

	public void setFirstReminder(int firstReminder) {
		_firstReminder = firstReminder;
	}

	public int getSecondReminder() {
		return _secondReminder;
	}

	public void setSecondReminder(int secondReminder) {
		_secondReminder = secondReminder;
	}

	public CalEvent toEscapedModel() {
		if (isEscapedModel()) {
			return (CalEvent)this;
		}
		else {
			CalEvent model = new CalEventImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUuid(HtmlUtil.escape(getUuid()));
			model.setEventId(getEventId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setTitle(HtmlUtil.escape(getTitle()));
			model.setDescription(HtmlUtil.escape(getDescription()));
			model.setStartDate(getStartDate());
			model.setEndDate(getEndDate());
			model.setDurationHour(getDurationHour());
			model.setDurationMinute(getDurationMinute());
			model.setAllDay(getAllDay());
			model.setTimeZoneSensitive(getTimeZoneSensitive());
			model.setType(HtmlUtil.escape(getType()));
			model.setRepeating(getRepeating());
			model.setRecurrence(getRecurrence());
			model.setRemindBy(getRemindBy());
			model.setFirstReminder(getFirstReminder());
			model.setSecondReminder(getSecondReminder());

			model = (CalEvent)Proxy.newProxyInstance(CalEvent.class.getClassLoader(),
					new Class[] { CalEvent.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(CalEvent.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		CalEventImpl clone = new CalEventImpl();

		clone.setUuid(getUuid());
		clone.setEventId(getEventId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setTitle(getTitle());
		clone.setDescription(getDescription());
		clone.setStartDate(getStartDate());
		clone.setEndDate(getEndDate());
		clone.setDurationHour(getDurationHour());
		clone.setDurationMinute(getDurationMinute());
		clone.setAllDay(getAllDay());
		clone.setTimeZoneSensitive(getTimeZoneSensitive());
		clone.setType(getType());
		clone.setRepeating(getRepeating());
		clone.setRecurrence(getRecurrence());
		clone.setRemindBy(getRemindBy());
		clone.setFirstReminder(getFirstReminder());
		clone.setSecondReminder(getSecondReminder());

		return clone;
	}

	public int compareTo(CalEvent calEvent) {
		int value = 0;

		value = DateUtil.compareTo(getStartDate(), calEvent.getStartDate());

		if (value != 0) {
			return value;
		}

		value = getTitle().toLowerCase()
					.compareTo(calEvent.getTitle().toLowerCase());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		CalEvent calEvent = null;

		try {
			calEvent = (CalEvent)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = calEvent.getPrimaryKey();

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

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", eventId=");
		sb.append(getEventId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", title=");
		sb.append(getTitle());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", startDate=");
		sb.append(getStartDate());
		sb.append(", endDate=");
		sb.append(getEndDate());
		sb.append(", durationHour=");
		sb.append(getDurationHour());
		sb.append(", durationMinute=");
		sb.append(getDurationMinute());
		sb.append(", allDay=");
		sb.append(getAllDay());
		sb.append(", timeZoneSensitive=");
		sb.append(getTimeZoneSensitive());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", repeating=");
		sb.append(getRepeating());
		sb.append(", recurrence=");
		sb.append(getRecurrence());
		sb.append(", remindBy=");
		sb.append(getRemindBy());
		sb.append(", firstReminder=");
		sb.append(getFirstReminder());
		sb.append(", secondReminder=");
		sb.append(getSecondReminder());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.calendar.model.CalEvent");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>eventId</column-name><column-value><![CDATA[");
		sb.append(getEventId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>title</column-name><column-value><![CDATA[");
		sb.append(getTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>startDate</column-name><column-value><![CDATA[");
		sb.append(getStartDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>endDate</column-name><column-value><![CDATA[");
		sb.append(getEndDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>durationHour</column-name><column-value><![CDATA[");
		sb.append(getDurationHour());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>durationMinute</column-name><column-value><![CDATA[");
		sb.append(getDurationMinute());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>allDay</column-name><column-value><![CDATA[");
		sb.append(getAllDay());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>timeZoneSensitive</column-name><column-value><![CDATA[");
		sb.append(getTimeZoneSensitive());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>repeating</column-name><column-value><![CDATA[");
		sb.append(getRepeating());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>recurrence</column-name><column-value><![CDATA[");
		sb.append(getRecurrence());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>remindBy</column-name><column-value><![CDATA[");
		sb.append(getRemindBy());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>firstReminder</column-name><column-value><![CDATA[");
		sb.append(getFirstReminder());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>secondReminder</column-name><column-value><![CDATA[");
		sb.append(getSecondReminder());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private String _originalUuid;
	private long _eventId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _title;
	private String _description;
	private Date _startDate;
	private Date _endDate;
	private int _durationHour;
	private int _durationMinute;
	private boolean _allDay;
	private boolean _timeZoneSensitive;
	private String _type;
	private boolean _repeating;
	private String _recurrence;
	private int _remindBy;
	private int _firstReminder;
	private int _secondReminder;
	private transient ExpandoBridge _expandoBridge;
}