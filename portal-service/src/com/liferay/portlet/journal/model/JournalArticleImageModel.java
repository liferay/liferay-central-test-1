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
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * <p>
 * This interface is a model that represents the JournalArticleImage table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleImage
 * @see       com.liferay.portlet.journal.model.impl.JournalArticleImageImpl
 * @see       com.liferay.portlet.journal.model.impl.JournalArticleImageModelImpl
 * @generated
 */
public interface JournalArticleImageModel extends BaseModel<JournalArticleImage> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getArticleImageId();

	public void setArticleImageId(long articleImageId);

	public long getGroupId();

	public void setGroupId(long groupId);

	@AutoEscape
	public String getArticleId();

	public void setArticleId(String articleId);

	public double getVersion();

	public void setVersion(double version);

	@AutoEscape
	public String getElInstanceId();

	public void setElInstanceId(String elInstanceId);

	@AutoEscape
	public String getElName();

	public void setElName(String elName);

	@AutoEscape
	public String getLanguageId();

	public void setLanguageId(String languageId);

	public boolean getTempImage();

	public boolean isTempImage();

	public void setTempImage(boolean tempImage);

	public JournalArticleImage toEscapedModel();

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

	public int compareTo(JournalArticleImage journalArticleImage);

	public int hashCode();

	public String toString();

	public String toXmlString();
}