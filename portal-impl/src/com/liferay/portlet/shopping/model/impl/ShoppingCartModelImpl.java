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

package com.liferay.portlet.shopping.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
import com.liferay.portlet.shopping.model.ShoppingCart;
import com.liferay.portlet.shopping.model.ShoppingCartSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="ShoppingCartModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>ShoppingCart</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.shopping.model.ShoppingCart
 * @see com.liferay.portlet.shopping.model.ShoppingCartModel
 * @see com.liferay.portlet.shopping.model.impl.ShoppingCartImpl
 *
 */
public class ShoppingCartModelImpl extends BaseModelImpl<ShoppingCart> {
	public static final String TABLE_NAME = "ShoppingCart";
	public static final Object[][] TABLE_COLUMNS = {
			{ "cartId", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "userName", new Integer(Types.VARCHAR) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "itemIds", new Integer(Types.VARCHAR) },
			

			{ "couponCodes", new Integer(Types.VARCHAR) },
			

			{ "altShipping", new Integer(Types.INTEGER) },
			

			{ "insure", new Integer(Types.BOOLEAN) }
		};
	public static final String TABLE_SQL_CREATE = "create table ShoppingCart (cartId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,itemIds STRING null,couponCodes VARCHAR(75) null,altShipping INTEGER,insure BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table ShoppingCart";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.shopping.model.ShoppingCart"),
			true);

	public static ShoppingCart toModel(ShoppingCartSoap soapModel) {
		ShoppingCart model = new ShoppingCartImpl();

		model.setCartId(soapModel.getCartId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setItemIds(soapModel.getItemIds());
		model.setCouponCodes(soapModel.getCouponCodes());
		model.setAltShipping(soapModel.getAltShipping());
		model.setInsure(soapModel.getInsure());

		return model;
	}

	public static List<ShoppingCart> toModels(ShoppingCartSoap[] soapModels) {
		List<ShoppingCart> models = new ArrayList<ShoppingCart>(soapModels.length);

		for (ShoppingCartSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.shopping.model.ShoppingCart"));

	public ShoppingCartModelImpl() {
	}

	public long getPrimaryKey() {
		return _cartId;
	}

	public void setPrimaryKey(long pk) {
		setCartId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_cartId);
	}

	public long getCartId() {
		return _cartId;
	}

	public void setCartId(long cartId) {
		if (cartId != _cartId) {
			_cartId = cartId;
		}
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (groupId != _groupId) {
			_groupId = groupId;
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

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		if (((userName == null) && (_userName != null)) ||
				((userName != null) && (_userName == null)) ||
				((userName != null) && (_userName != null) &&
				!userName.equals(_userName))) {
			_userName = userName;
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

	public String getItemIds() {
		return GetterUtil.getString(_itemIds);
	}

	public void setItemIds(String itemIds) {
		if (((itemIds == null) && (_itemIds != null)) ||
				((itemIds != null) && (_itemIds == null)) ||
				((itemIds != null) && (_itemIds != null) &&
				!itemIds.equals(_itemIds))) {
			_itemIds = itemIds;
		}
	}

	public String getCouponCodes() {
		return GetterUtil.getString(_couponCodes);
	}

	public void setCouponCodes(String couponCodes) {
		if (((couponCodes == null) && (_couponCodes != null)) ||
				((couponCodes != null) && (_couponCodes == null)) ||
				((couponCodes != null) && (_couponCodes != null) &&
				!couponCodes.equals(_couponCodes))) {
			_couponCodes = couponCodes;
		}
	}

	public int getAltShipping() {
		return _altShipping;
	}

	public void setAltShipping(int altShipping) {
		if (altShipping != _altShipping) {
			_altShipping = altShipping;
		}
	}

	public boolean getInsure() {
		return _insure;
	}

	public boolean isInsure() {
		return _insure;
	}

	public void setInsure(boolean insure) {
		if (insure != _insure) {
			_insure = insure;
		}
	}

	public ShoppingCart toEscapedModel() {
		if (isEscapedModel()) {
			return (ShoppingCart)this;
		}
		else {
			ShoppingCart model = new ShoppingCartImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setCartId(getCartId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setItemIds(HtmlUtil.escape(getItemIds()));
			model.setCouponCodes(HtmlUtil.escape(getCouponCodes()));
			model.setAltShipping(getAltShipping());
			model.setInsure(getInsure());

			model = (ShoppingCart)Proxy.newProxyInstance(ShoppingCart.class.getClassLoader(),
					new Class[] { ShoppingCart.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(ShoppingCart.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		ShoppingCartImpl clone = new ShoppingCartImpl();

		clone.setCartId(getCartId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setItemIds(getItemIds());
		clone.setCouponCodes(getCouponCodes());
		clone.setAltShipping(getAltShipping());
		clone.setInsure(getInsure());

		return clone;
	}

	public int compareTo(ShoppingCart shoppingCart) {
		long pk = shoppingCart.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ShoppingCart shoppingCart = null;

		try {
			shoppingCart = (ShoppingCart)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = shoppingCart.getPrimaryKey();

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

	private long _cartId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _itemIds;
	private String _couponCodes;
	private int _altShipping;
	private boolean _insure;
	private transient ExpandoBridge _expandoBridge;
}