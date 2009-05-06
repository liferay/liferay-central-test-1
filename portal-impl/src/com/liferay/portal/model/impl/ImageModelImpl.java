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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.ImageSoap;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="ImageModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>Image</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.model.Image
 * @see com.liferay.portal.model.ImageModel
 * @see com.liferay.portal.model.impl.ImageImpl
 *
 */
public class ImageModelImpl extends BaseModelImpl<Image> {
	public static final String TABLE_NAME = "Image";
	public static final Object[][] TABLE_COLUMNS = {
			{ "imageId", new Integer(Types.BIGINT) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "text_", new Integer(Types.CLOB) },
			

			{ "type_", new Integer(Types.VARCHAR) },
			

			{ "height", new Integer(Types.INTEGER) },
			

			{ "width", new Integer(Types.INTEGER) },
			

			{ "size_", new Integer(Types.INTEGER) }
		};
	public static final String TABLE_SQL_CREATE = "create table Image (imageId LONG not null primary key,modifiedDate DATE null,text_ TEXT null,type_ VARCHAR(75) null,height INTEGER,width INTEGER,size_ INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table Image";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Image"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Image"),
			true);

	public static Image toModel(ImageSoap soapModel) {
		Image model = new ImageImpl();

		model.setImageId(soapModel.getImageId());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setText(soapModel.getText());
		model.setType(soapModel.getType());
		model.setHeight(soapModel.getHeight());
		model.setWidth(soapModel.getWidth());
		model.setSize(soapModel.getSize());

		return model;
	}

	public static List<Image> toModels(ImageSoap[] soapModels) {
		List<Image> models = new ArrayList<Image>(soapModels.length);

		for (ImageSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Image"));

	public ImageModelImpl() {
	}

	public long getPrimaryKey() {
		return _imageId;
	}

	public void setPrimaryKey(long pk) {
		setImageId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_imageId);
	}

	public long getImageId() {
		return _imageId;
	}

	public void setImageId(long imageId) {
		_imageId = imageId;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getText() {
		return GetterUtil.getString(_text);
	}

	public void setText(String text) {
		_text = text;
	}

	public String getType() {
		return GetterUtil.getString(_type);
	}

	public void setType(String type) {
		_type = type;
	}

	public int getHeight() {
		return _height;
	}

	public void setHeight(int height) {
		_height = height;
	}

	public int getWidth() {
		return _width;
	}

	public void setWidth(int width) {
		_width = width;
	}

	public int getSize() {
		return _size;
	}

	public void setSize(int size) {
		_size = size;
	}

	public Image toEscapedModel() {
		if (isEscapedModel()) {
			return (Image)this;
		}
		else {
			Image model = new ImageImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setImageId(getImageId());
			model.setModifiedDate(getModifiedDate());
			model.setText(HtmlUtil.escape(getText()));
			model.setType(HtmlUtil.escape(getType()));
			model.setHeight(getHeight());
			model.setWidth(getWidth());
			model.setSize(getSize());

			model = (Image)Proxy.newProxyInstance(Image.class.getClassLoader(),
					new Class[] { Image.class }, new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(Image.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		ImageImpl clone = new ImageImpl();

		clone.setImageId(getImageId());
		clone.setModifiedDate(getModifiedDate());
		clone.setText(getText());
		clone.setType(getType());
		clone.setHeight(getHeight());
		clone.setWidth(getWidth());
		clone.setSize(getSize());

		return clone;
	}

	public int compareTo(Image image) {
		int value = 0;

		if (getImageId() < image.getImageId()) {
			value = -1;
		}
		else if (getImageId() > image.getImageId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		Image image = null;

		try {
			image = (Image)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = image.getPrimaryKey();

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

		sb.append("{imageId=");
		sb.append(getImageId());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", text=");
		sb.append(getText());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", height=");
		sb.append(getHeight());
		sb.append(", width=");
		sb.append(getWidth());
		sb.append(", size=");
		sb.append(getSize());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Image");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>imageId</column-name><column-value><![CDATA[");
		sb.append(getImageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>text</column-name><column-value><![CDATA[");
		sb.append(getText());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>height</column-name><column-value><![CDATA[");
		sb.append(getHeight());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>width</column-name><column-value><![CDATA[");
		sb.append(getWidth());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>size</column-name><column-value><![CDATA[");
		sb.append(getSize());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _imageId;
	private Date _modifiedDate;
	private String _text;
	private String _type;
	private int _height;
	private int _width;
	private int _size;
	private transient ExpandoBridge _expandoBridge;
}