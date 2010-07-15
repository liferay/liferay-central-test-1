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

package com.liferay.portlet.documentlibrary.model;

import com.liferay.portal.kernel.annotation.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <p>
 * This interface is a model that represents the DLFileEntry table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntry
 * @see       com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl
 * @see       com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl
 * @generated
 */
public interface DLFileEntryModel extends BaseModel<DLFileEntry> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	@AutoEscape
	public String getUuid();

	public void setUuid(String uuid);

	public long getFileEntryId();

	public void setFileEntryId(long fileEntryId);

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

	public long getVersionUserId();

	public void setVersionUserId(long versionUserId);

	public String getVersionUserUuid() throws SystemException;

	public void setVersionUserUuid(String versionUserUuid);

	@AutoEscape
	public String getVersionUserName();

	public void setVersionUserName(String versionUserName);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	public long getFolderId();

	public void setFolderId(long folderId);

	@AutoEscape
	public String getName();

	public void setName(String name);

	@AutoEscape
	public String getExtension();

	public void setExtension(String extension);

	@AutoEscape
	public String getTitle();

	public void setTitle(String title);

	@AutoEscape
	public String getDescription();

	public void setDescription(String description);

	@AutoEscape
	public String getExtraSettings();

	public void setExtraSettings(String extraSettings);

	@AutoEscape
	public String getVersion();

	public void setVersion(String version);

	public long getSize();

	public void setSize(long size);

	public int getReadCount();

	public void setReadCount(int readCount);

	public DLFileEntry toEscapedModel();

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

	public int compareTo(DLFileEntry dlFileEntry);

	public int hashCode();

	public String toString();

	public String toXmlString();
}