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

package com.liferay.portlet.tasks.model.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.tasks.model.TasksReview;
import com.liferay.portlet.tasks.model.TasksReviewSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="TasksReviewModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the TasksReview table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksReviewImpl
 * @see       com.liferay.portlet.tasks.model.TasksReview
 * @see       com.liferay.portlet.tasks.model.TasksReviewModel
 * @generated
 */
public class TasksReviewModelImpl extends BaseModelImpl<TasksReview> {
	public static final String TABLE_NAME = "TasksReview";
	public static final Object[][] TABLE_COLUMNS = {
			{ "reviewId", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "userName", new Integer(Types.VARCHAR) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "proposalId", new Integer(Types.BIGINT) },
			{ "assignedByUserId", new Integer(Types.BIGINT) },
			{ "assignedByUserName", new Integer(Types.VARCHAR) },
			{ "stage", new Integer(Types.INTEGER) },
			{ "completed", new Integer(Types.BOOLEAN) },
			{ "rejected", new Integer(Types.BOOLEAN) }
		};
	public static final String TABLE_SQL_CREATE = "create table TasksReview (reviewId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,proposalId LONG,assignedByUserId LONG,assignedByUserName VARCHAR(75) null,stage INTEGER,completed BOOLEAN,rejected BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table TasksReview";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.tasks.model.TasksReview"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.tasks.model.TasksReview"),
			true);

	public static TasksReview toModel(TasksReviewSoap soapModel) {
		TasksReview model = new TasksReviewImpl();

		model.setReviewId(soapModel.getReviewId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setProposalId(soapModel.getProposalId());
		model.setAssignedByUserId(soapModel.getAssignedByUserId());
		model.setAssignedByUserName(soapModel.getAssignedByUserName());
		model.setStage(soapModel.getStage());
		model.setCompleted(soapModel.getCompleted());
		model.setRejected(soapModel.getRejected());

		return model;
	}

	public static List<TasksReview> toModels(TasksReviewSoap[] soapModels) {
		List<TasksReview> models = new ArrayList<TasksReview>(soapModels.length);

		for (TasksReviewSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.tasks.model.TasksReview"));

	public TasksReviewModelImpl() {
	}

	public long getPrimaryKey() {
		return _reviewId;
	}

	public void setPrimaryKey(long pk) {
		setReviewId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_reviewId);
	}

	public long getReviewId() {
		return _reviewId;
	}

	public void setReviewId(long reviewId) {
		_reviewId = reviewId;
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

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = userId;
		}
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
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

	public long getProposalId() {
		return _proposalId;
	}

	public void setProposalId(long proposalId) {
		_proposalId = proposalId;

		if (!_setOriginalProposalId) {
			_setOriginalProposalId = true;

			_originalProposalId = proposalId;
		}
	}

	public long getOriginalProposalId() {
		return _originalProposalId;
	}

	public long getAssignedByUserId() {
		return _assignedByUserId;
	}

	public void setAssignedByUserId(long assignedByUserId) {
		_assignedByUserId = assignedByUserId;
	}

	public String getAssignedByUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getAssignedByUserId(), "uuid",
			_assignedByUserUuid);
	}

	public void setAssignedByUserUuid(String assignedByUserUuid) {
		_assignedByUserUuid = assignedByUserUuid;
	}

	public String getAssignedByUserName() {
		return GetterUtil.getString(_assignedByUserName);
	}

	public void setAssignedByUserName(String assignedByUserName) {
		_assignedByUserName = assignedByUserName;
	}

	public int getStage() {
		return _stage;
	}

	public void setStage(int stage) {
		_stage = stage;
	}

	public boolean getCompleted() {
		return _completed;
	}

	public boolean isCompleted() {
		return _completed;
	}

	public void setCompleted(boolean completed) {
		_completed = completed;
	}

	public boolean getRejected() {
		return _rejected;
	}

	public boolean isRejected() {
		return _rejected;
	}

	public void setRejected(boolean rejected) {
		_rejected = rejected;
	}

	public TasksReview toEscapedModel() {
		if (isEscapedModel()) {
			return (TasksReview)this;
		}
		else {
			TasksReview model = new TasksReviewImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setReviewId(getReviewId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setProposalId(getProposalId());
			model.setAssignedByUserId(getAssignedByUserId());
			model.setAssignedByUserName(HtmlUtil.escape(getAssignedByUserName()));
			model.setStage(getStage());
			model.setCompleted(getCompleted());
			model.setRejected(getRejected());

			model = (TasksReview)Proxy.newProxyInstance(TasksReview.class.getClassLoader(),
					new Class[] { TasksReview.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(TasksReview.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		TasksReviewImpl clone = new TasksReviewImpl();

		clone.setReviewId(getReviewId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setProposalId(getProposalId());
		clone.setAssignedByUserId(getAssignedByUserId());
		clone.setAssignedByUserName(getAssignedByUserName());
		clone.setStage(getStage());
		clone.setCompleted(getCompleted());
		clone.setRejected(getRejected());

		return clone;
	}

	public int compareTo(TasksReview tasksReview) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(), tasksReview.getCreateDate());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		TasksReview tasksReview = null;

		try {
			tasksReview = (TasksReview)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = tasksReview.getPrimaryKey();

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

		sb.append("{reviewId=");
		sb.append(getReviewId());
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
		sb.append(", proposalId=");
		sb.append(getProposalId());
		sb.append(", assignedByUserId=");
		sb.append(getAssignedByUserId());
		sb.append(", assignedByUserName=");
		sb.append(getAssignedByUserName());
		sb.append(", stage=");
		sb.append(getStage());
		sb.append(", completed=");
		sb.append(getCompleted());
		sb.append(", rejected=");
		sb.append(getRejected());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.tasks.model.TasksReview");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>reviewId</column-name><column-value><![CDATA[");
		sb.append(getReviewId());
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
			"<column><column-name>proposalId</column-name><column-value><![CDATA[");
		sb.append(getProposalId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>assignedByUserId</column-name><column-value><![CDATA[");
		sb.append(getAssignedByUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>assignedByUserName</column-name><column-value><![CDATA[");
		sb.append(getAssignedByUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>stage</column-name><column-value><![CDATA[");
		sb.append(getStage());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>completed</column-name><column-value><![CDATA[");
		sb.append(getCompleted());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>rejected</column-name><column-value><![CDATA[");
		sb.append(getRejected());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _reviewId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _proposalId;
	private long _originalProposalId;
	private boolean _setOriginalProposalId;
	private long _assignedByUserId;
	private String _assignedByUserUuid;
	private String _assignedByUserName;
	private int _stage;
	private boolean _completed;
	private boolean _rejected;
	private transient ExpandoBridge _expandoBridge;
}