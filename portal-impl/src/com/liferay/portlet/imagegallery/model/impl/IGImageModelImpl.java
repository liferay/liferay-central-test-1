/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.IGImageSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="IGImageModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>IGImage</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.imagegallery.model.IGImage
 * @see com.liferay.portlet.imagegallery.model.IGImageModel
 * @see com.liferay.portlet.imagegallery.model.impl.IGImageImpl
 *
 */
public class IGImageModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "IGImage";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			

			{ "imageId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "folderId", new Integer(Types.BIGINT) },
			

			{ "name", new Integer(Types.VARCHAR) },
			

			{ "description", new Integer(Types.VARCHAR) },
			

			{ "smallImageId", new Integer(Types.BIGINT) },
			

			{ "largeImageId", new Integer(Types.BIGINT) },
			

			{ "custom1ImageId", new Integer(Types.BIGINT) },
			

			{ "custom2ImageId", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table IGImage (uuid_ VARCHAR(75) null,imageId LONG not null primary key,companyId LONG,userId LONG,createDate DATE null,modifiedDate DATE null,folderId LONG,name VARCHAR(75) null,description STRING null,smallImageId LONG,largeImageId LONG,custom1ImageId LONG,custom2ImageId LONG)";
	public static final String TABLE_SQL_DROP = "drop table IGImage";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.imagegallery.model.IGImage"),
			true);

	public static IGImage toModel(IGImageSoap soapModel) {
		IGImage model = new IGImageImpl();

		model.setUuid(soapModel.getUuid());
		model.setImageId(soapModel.getImageId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setFolderId(soapModel.getFolderId());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setSmallImageId(soapModel.getSmallImageId());
		model.setLargeImageId(soapModel.getLargeImageId());
		model.setCustom1ImageId(soapModel.getCustom1ImageId());
		model.setCustom2ImageId(soapModel.getCustom2ImageId());

		return model;
	}

	public static List<IGImage> toModels(IGImageSoap[] soapModels) {
		List<IGImage> models = new ArrayList<IGImage>(soapModels.length);

		for (IGImageSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.imagegallery.model.IGImage"));

	public IGImageModelImpl() {
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

	public String getUuid() {
		return GetterUtil.getString(_uuid);
	}

	public void setUuid(String uuid) {
		if ((uuid != null) && (uuid != _uuid)) {
			_uuid = uuid;
		}
	}

	public long getImageId() {
		return _imageId;
	}

	public void setImageId(long imageId) {
		if (imageId != _imageId) {
			_imageId = imageId;
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		if (companyId != _companyId) {
			_companyId = companyId;
		}
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		if (userId != _userId) {
			_userId = userId;
		}
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		if (((createDate == null) && (_createDate != null)) ||
				((createDate != null) && (_createDate == null)) ||
				((createDate != null) && (_createDate != null) &&
				!createDate.equals(_createDate))) {
			_createDate = createDate;
		}
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (((modifiedDate == null) && (_modifiedDate != null)) ||
				((modifiedDate != null) && (_modifiedDate == null)) ||
				((modifiedDate != null) && (_modifiedDate != null) &&
				!modifiedDate.equals(_modifiedDate))) {
			_modifiedDate = modifiedDate;
		}
	}

	public long getFolderId() {
		return _folderId;
	}

	public void setFolderId(long folderId) {
		if (folderId != _folderId) {
			_folderId = folderId;
		}
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		if (((name == null) && (_name != null)) ||
				((name != null) && (_name == null)) ||
				((name != null) && (_name != null) && !name.equals(_name))) {
			_name = name;
		}
	}

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		if (((description == null) && (_description != null)) ||
				((description != null) && (_description == null)) ||
				((description != null) && (_description != null) &&
				!description.equals(_description))) {
			_description = description;
		}
	}

	public long getSmallImageId() {
		return _smallImageId;
	}

	public void setSmallImageId(long smallImageId) {
		if (smallImageId != _smallImageId) {
			_smallImageId = smallImageId;
		}
	}

	public long getLargeImageId() {
		return _largeImageId;
	}

	public void setLargeImageId(long largeImageId) {
		if (largeImageId != _largeImageId) {
			_largeImageId = largeImageId;
		}
	}

	public long getCustom1ImageId() {
		return _custom1ImageId;
	}

	public void setCustom1ImageId(long custom1ImageId) {
		if (custom1ImageId != _custom1ImageId) {
			_custom1ImageId = custom1ImageId;
		}
	}

	public long getCustom2ImageId() {
		return _custom2ImageId;
	}

	public void setCustom2ImageId(long custom2ImageId) {
		if (custom2ImageId != _custom2ImageId) {
			_custom2ImageId = custom2ImageId;
		}
	}

	public IGImage toEscapedModel() {
		if (isEscapedModel()) {
			return (IGImage)this;
		}
		else {
			IGImage model = new IGImageImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUuid(HtmlUtil.escape(getUuid()));
			model.setImageId(getImageId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setFolderId(getFolderId());
			model.setName(HtmlUtil.escape(getName()));
			model.setDescription(HtmlUtil.escape(getDescription()));
			model.setSmallImageId(getSmallImageId());
			model.setLargeImageId(getLargeImageId());
			model.setCustom1ImageId(getCustom1ImageId());
			model.setCustom2ImageId(getCustom2ImageId());

			model = (IGImage)Proxy.newProxyInstance(IGImage.class.getClassLoader(),
					new Class[] { IGImage.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		IGImageImpl clone = new IGImageImpl();

		clone.setUuid(getUuid());
		clone.setImageId(getImageId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setFolderId(getFolderId());
		clone.setName(getName());
		clone.setDescription(getDescription());
		clone.setSmallImageId(getSmallImageId());
		clone.setLargeImageId(getLargeImageId());
		clone.setCustom1ImageId(getCustom1ImageId());
		clone.setCustom2ImageId(getCustom2ImageId());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		IGImageImpl igImage = (IGImageImpl)obj;

		int value = 0;

		if (getImageId() < igImage.getImageId()) {
			value = -1;
		}
		else if (getImageId() > igImage.getImageId()) {
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

		IGImageImpl igImage = null;

		try {
			igImage = (IGImageImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = igImage.getPrimaryKey();

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

	private String _uuid;
	private long _imageId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _folderId;
	private String _name;
	private String _description;
	private long _smallImageId;
	private long _largeImageId;
	private long _custom1ImageId;
	private long _custom2ImageId;
}