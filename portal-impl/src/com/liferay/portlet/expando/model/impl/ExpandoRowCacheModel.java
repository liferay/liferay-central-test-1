/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.expando.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.expando.model.ExpandoRow;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ExpandoRow in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoRow
 * @generated
 */
public class ExpandoRowCacheModel implements CacheModel<ExpandoRow>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{rowId=");
		sb.append(rowId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", tableId=");
		sb.append(tableId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append("}");

		return sb.toString();
	}

	public ExpandoRow toEntityModel() {
		ExpandoRowImpl expandoRowImpl = new ExpandoRowImpl();

		expandoRowImpl.setRowId(rowId);
		expandoRowImpl.setCompanyId(companyId);
		expandoRowImpl.setTableId(tableId);
		expandoRowImpl.setClassPK(classPK);

		expandoRowImpl.resetOriginalValues();

		return expandoRowImpl;
	}

	public void readExternal(ObjectInput objectInput) throws IOException {
		rowId = objectInput.readLong();
		companyId = objectInput.readLong();
		tableId = objectInput.readLong();
		classPK = objectInput.readLong();
	}

	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(rowId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(tableId);
		objectOutput.writeLong(classPK);
	}

	public long rowId;
	public long companyId;
	public long tableId;
	public long classPK;
}