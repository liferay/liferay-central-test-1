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
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.UserGroupRoleSoap;
import com.liferay.portal.service.persistence.UserGroupRolePK;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="UserGroupRoleModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>UserGroupRole</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.model.UserGroupRole
 * @see com.liferay.portal.model.UserGroupRoleModel
 * @see com.liferay.portal.model.impl.UserGroupRoleImpl
 *
 */
public class UserGroupRoleModelImpl extends BaseModelImpl<UserGroupRole> {
	public static final String TABLE_NAME = "UserGroupRole";
	public static final Object[][] TABLE_COLUMNS = {
			{ "userId", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "roleId", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table UserGroupRole (userId LONG not null,groupId LONG not null,roleId LONG not null,primary key (userId, groupId, roleId))";
	public static final String TABLE_SQL_DROP = "drop table UserGroupRole";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.UserGroupRole"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.UserGroupRole"),
			true);

	public static UserGroupRole toModel(UserGroupRoleSoap soapModel) {
		UserGroupRole model = new UserGroupRoleImpl();

		model.setUserId(soapModel.getUserId());
		model.setGroupId(soapModel.getGroupId());
		model.setRoleId(soapModel.getRoleId());

		return model;
	}

	public static List<UserGroupRole> toModels(UserGroupRoleSoap[] soapModels) {
		List<UserGroupRole> models = new ArrayList<UserGroupRole>(soapModels.length);

		for (UserGroupRoleSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.UserGroupRole"));

	public UserGroupRoleModelImpl() {
	}

	public UserGroupRolePK getPrimaryKey() {
		return new UserGroupRolePK(_userId, _groupId, _roleId);
	}

	public void setPrimaryKey(UserGroupRolePK pk) {
		setUserId(pk.userId);
		setGroupId(pk.groupId);
		setRoleId(pk.roleId);
	}

	public Serializable getPrimaryKeyObj() {
		return new UserGroupRolePK(_userId, _groupId, _roleId);
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getRoleId() {
		return _roleId;
	}

	public void setRoleId(long roleId) {
		_roleId = roleId;
	}

	public UserGroupRole toEscapedModel() {
		if (isEscapedModel()) {
			return (UserGroupRole)this;
		}
		else {
			UserGroupRole model = new UserGroupRoleImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUserId(getUserId());
			model.setGroupId(getGroupId());
			model.setRoleId(getRoleId());

			model = (UserGroupRole)Proxy.newProxyInstance(UserGroupRole.class.getClassLoader(),
					new Class[] { UserGroupRole.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		UserGroupRoleImpl clone = new UserGroupRoleImpl();

		clone.setUserId(getUserId());
		clone.setGroupId(getGroupId());
		clone.setRoleId(getRoleId());

		return clone;
	}

	public int compareTo(UserGroupRole userGroupRole) {
		UserGroupRolePK pk = userGroupRole.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		UserGroupRole userGroupRole = null;

		try {
			userGroupRole = (UserGroupRole)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		UserGroupRolePK pk = userGroupRole.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private long _userId;
	private long _groupId;
	private long _roleId;
}