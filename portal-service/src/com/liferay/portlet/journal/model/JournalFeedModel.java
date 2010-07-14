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

package com.liferay.portlet.journal.model;

import com.liferay.portal.kernel.annotation.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the JournalFeed table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalFeed
 * @see       com.liferay.portlet.journal.model.impl.JournalFeedImpl
 * @see       com.liferay.portlet.journal.model.impl.JournalFeedModelImpl
 * @generated
 */
public interface JournalFeedModel extends BaseModel<JournalFeed> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	@AutoEscape
	public String getUuid();

	public void setUuid(String uuid);

	public long getId();

	public void setId(long id);

	public long getGroupId();

	public void setGroupId(long groupId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getUserId();

	public void setUserId(long userId);

	public String getUserUuid() throws SystemException;

	public void setUserUuid(String userUuid);

	@AutoEscape
	public String getUserName();

	public void setUserName(String userName);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	public String getFeedId();

	public void setFeedId(String feedId);

	@AutoEscape
	public String getName();

	public void setName(String name);

	@AutoEscape
	public String getDescription();

	public void setDescription(String description);

	@AutoEscape
	public String getType();

	public void setType(String type);

	public String getStructureId();

	public void setStructureId(String structureId);

	public String getTemplateId();

	public void setTemplateId(String templateId);

	@AutoEscape
	public String getRendererTemplateId();

	public void setRendererTemplateId(String rendererTemplateId);

	public int getDelta();

	public void setDelta(int delta);

	@AutoEscape
	public String getOrderByCol();

	public void setOrderByCol(String orderByCol);

	@AutoEscape
	public String getOrderByType();

	public void setOrderByType(String orderByType);

	@AutoEscape
	public String getTargetLayoutFriendlyUrl();

	public void setTargetLayoutFriendlyUrl(String targetLayoutFriendlyUrl);

	@AutoEscape
	public String getTargetPortletId();

	public void setTargetPortletId(String targetPortletId);

	@AutoEscape
	public String getContentField();

	public void setContentField(String contentField);

	@AutoEscape
	public String getFeedType();

	public void setFeedType(String feedType);

	public double getFeedVersion();

	public void setFeedVersion(double feedVersion);

	public JournalFeed toEscapedModel();

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(JournalFeed journalFeed);

	public int hashCode();

	public String toString();

	public String toXmlString();
}