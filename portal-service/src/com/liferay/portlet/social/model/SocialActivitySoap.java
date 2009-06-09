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

package com.liferay.portlet.social.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="SocialActivitySoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * <code>com.liferay.portlet.social.service.http.SocialActivityServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.social.service.http.SocialActivityServiceSoap
 *
 */
public class SocialActivitySoap implements Serializable {
	public static SocialActivitySoap toSoapModel(SocialActivity model) {
		SocialActivitySoap soapModel = new SocialActivitySoap();

		soapModel.setActivityId(model.getActivityId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setMirrorActivityId(model.getMirrorActivityId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setType(model.getType());
		soapModel.setExtraData(model.getExtraData());
		soapModel.setReceiverUserId(model.getReceiverUserId());

		return soapModel;
	}

	public static SocialActivitySoap[] toSoapModels(SocialActivity[] models) {
		SocialActivitySoap[] soapModels = new SocialActivitySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialActivitySoap[][] toSoapModels(SocialActivity[][] models) {
		SocialActivitySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SocialActivitySoap[models.length][models[0].length];
		}
		else {
			soapModels = new SocialActivitySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialActivitySoap[] toSoapModels(List<SocialActivity> models) {
		List<SocialActivitySoap> soapModels = new ArrayList<SocialActivitySoap>(models.size());

		for (SocialActivity model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SocialActivitySoap[soapModels.size()]);
	}

	public SocialActivitySoap() {
	}

	public long getPrimaryKey() {
		return _activityId;
	}

	public void setPrimaryKey(long pk) {
		setActivityId(pk);
	}

	public long getActivityId() {
		return _activityId;
	}

	public void setActivityId(long activityId) {
		_activityId = activityId;
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

	public long getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(long createDate) {
		_createDate = createDate;
	}

	public long getMirrorActivityId() {
		return _mirrorActivityId;
	}

	public void setMirrorActivityId(long mirrorActivityId) {
		_mirrorActivityId = mirrorActivityId;
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

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public String getExtraData() {
		return _extraData;
	}

	public void setExtraData(String extraData) {
		_extraData = extraData;
	}

	public long getReceiverUserId() {
		return _receiverUserId;
	}

	public void setReceiverUserId(long receiverUserId) {
		_receiverUserId = receiverUserId;
	}

	private long _activityId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private long _createDate;
	private long _mirrorActivityId;
	private long _classNameId;
	private long _classPK;
	private int _type;
	private String _extraData;
	private long _receiverUserId;
}