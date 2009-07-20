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

package com.liferay.portlet.tasks.model;

import com.liferay.portal.SystemException;
import com.liferay.portal.model.BaseModel;

import java.util.Date;

public interface TasksReviewModel extends BaseModel<TasksReview> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getReviewId();

	public void setReviewId(long reviewId);

	public long getGroupId();

	public void setGroupId(long groupId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getUserId();

	public void setUserId(long userId);

	public String getUserUuid() throws SystemException;

	public void setUserUuid(String userUuid);

	public String getUserName();

	public void setUserName(String userName);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	public long getProposalId();

	public void setProposalId(long proposalId);

	public long getAssignedByUserId();

	public void setAssignedByUserId(long assignedByUserId);

	public String getAssignedByUserUuid() throws SystemException;

	public void setAssignedByUserUuid(String assignedByUserUuid);

	public String getAssignedByUserName();

	public void setAssignedByUserName(String assignedByUserName);

	public int getStage();

	public void setStage(int stage);

	public boolean getCompleted();

	public boolean isCompleted();

	public void setCompleted(boolean completed);

	public boolean getRejected();

	public boolean isRejected();

	public void setRejected(boolean rejected);

	public TasksReview toEscapedModel();
}