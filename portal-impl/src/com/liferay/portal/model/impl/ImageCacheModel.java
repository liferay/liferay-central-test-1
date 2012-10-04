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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Image;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Image in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Image
 * @generated
 */
public class ImageCacheModel implements CacheModel<Image>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{imageId=");
		sb.append(imageId);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", text=");
		sb.append(text);
		sb.append(", type=");
		sb.append(type);
		sb.append(", height=");
		sb.append(height);
		sb.append(", width=");
		sb.append(width);
		sb.append(", size=");
		sb.append(size);
		sb.append("}");

		return sb.toString();
	}

	public Image toEntityModel() {
		ImageImpl imageImpl = new ImageImpl();

		imageImpl.setImageId(imageId);

		if (modifiedDate == Long.MIN_VALUE) {
			imageImpl.setModifiedDate(null);
		}
		else {
			imageImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (text == null) {
			imageImpl.setText(StringPool.BLANK);
		}
		else {
			imageImpl.setText(text);
		}

		if (type == null) {
			imageImpl.setType(StringPool.BLANK);
		}
		else {
			imageImpl.setType(type);
		}

		imageImpl.setHeight(height);
		imageImpl.setWidth(width);
		imageImpl.setSize(size);

		imageImpl.resetOriginalValues();

		return imageImpl;
	}

	public void readExternal(ObjectInput objectInput) throws IOException {
		imageId = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		text = objectInput.readUTF();
		type = objectInput.readUTF();
		height = objectInput.readInt();
		width = objectInput.readInt();
		size = objectInput.readInt();
	}

	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(imageId);
		objectOutput.writeLong(modifiedDate);

		if (text == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(text);
		}

		if (type == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeInt(height);
		objectOutput.writeInt(width);
		objectOutput.writeInt(size);
	}

	public long imageId;
	public long modifiedDate;
	public String text;
	public String type;
	public int height;
	public int width;
	public int size;
}