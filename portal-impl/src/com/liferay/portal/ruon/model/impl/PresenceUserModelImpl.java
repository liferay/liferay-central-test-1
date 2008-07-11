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

package com.liferay.portal.ruon.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.ruon.model.PresenceUser;
import com.liferay.portal.ruon.model.PresenceUserSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="PresenceUserModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>PresenceUser</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.ruon.service.model.PresenceUser
 * @see com.liferay.portal.ruon.service.model.PresenceUserModel
 * @see com.liferay.portal.ruon.service.model.impl.PresenceUserImpl
 *
 */
public class PresenceUserModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "PresenceUser";
	public static final Object[][] TABLE_COLUMNS = {
			{ "presenceUserId", new Integer(Types.BIGINT) },
			

			{ "presenceStatus", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table PresenceUser (presenceUserId LONG not null primary key,presenceStatus LONG)";
	public static final String TABLE_SQL_DROP = "drop table PresenceUser";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.ruon.model.PresenceUser"),
			true);

	public static PresenceUser toModel(PresenceUserSoap soapModel) {
		PresenceUser model = new PresenceUserImpl();

		model.setPresenceUserId(soapModel.getPresenceUserId());
		model.setPresenceStatus(soapModel.getPresenceStatus());

		return model;
	}

	public static List<PresenceUser> toModels(PresenceUserSoap[] soapModels) {
		List<PresenceUser> models = new ArrayList<PresenceUser>(soapModels.length);

		for (PresenceUserSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.ruon.model.PresenceUser"));

	public PresenceUserModelImpl() {
	}

	public long getPrimaryKey() {
		return _presenceUserId;
	}

	public void setPrimaryKey(long pk) {
		setPresenceUserId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_presenceUserId);
	}

	public long getPresenceUserId() {
		return _presenceUserId;
	}

	public void setPresenceUserId(long presenceUserId) {
		if (presenceUserId != _presenceUserId) {
			_presenceUserId = presenceUserId;
		}
	}

	public long getPresenceStatus() {
		return _presenceStatus;
	}

	public void setPresenceStatus(long presenceStatus) {
		if (presenceStatus != _presenceStatus) {
			_presenceStatus = presenceStatus;
		}
	}

	public PresenceUser toEscapedModel() {
		if (isEscapedModel()) {
			return (PresenceUser)this;
		}
		else {
			PresenceUser model = new PresenceUserImpl();

			model.setEscapedModel(true);

			model.setPresenceUserId(getPresenceUserId());
			model.setPresenceStatus(getPresenceStatus());

			model = (PresenceUser)Proxy.newProxyInstance(PresenceUser.class.getClassLoader(),
					new Class[] { PresenceUser.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		PresenceUserImpl clone = new PresenceUserImpl();

		clone.setPresenceUserId(getPresenceUserId());
		clone.setPresenceStatus(getPresenceStatus());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		PresenceUserImpl presenceUser = (PresenceUserImpl)obj;

		long pk = presenceUser.getPrimaryKey();

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

		PresenceUserImpl presenceUser = null;

		try {
			presenceUser = (PresenceUserImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = presenceUser.getPrimaryKey();

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

	private long _presenceUserId;
	private long _presenceStatus;
}