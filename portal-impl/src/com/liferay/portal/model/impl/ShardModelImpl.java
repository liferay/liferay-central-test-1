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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Shard;
import com.liferay.portal.model.ShardSoap;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ShardModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>Shard</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.model.Shard
 * @see com.liferay.portal.model.ShardModel
 * @see com.liferay.portal.model.impl.ShardImpl
 *
 */
public class ShardModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "Shard";
	public static final Object[][] TABLE_COLUMNS = {
			{ "shardId", new Integer(Types.BIGINT) },
			

			{ "classNameId", new Integer(Types.BIGINT) },
			

			{ "classPK", new Integer(Types.BIGINT) },
			

			{ "jdbcName", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table Shard (shardId LONG not null primary key,classNameId LONG,classPK LONG,jdbcName VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table Shard";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Shard"),
			true);

	public static Shard toModel(ShardSoap soapModel) {
		Shard model = new ShardImpl();

		model.setShardId(soapModel.getShardId());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setJdbcName(soapModel.getJdbcName());

		return model;
	}

	public static List<Shard> toModels(ShardSoap[] soapModels) {
		List<Shard> models = new ArrayList<Shard>(soapModels.length);

		for (ShardSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Shard"));

	public ShardModelImpl() {
	}

	public long getPrimaryKey() {
		return _shardId;
	}

	public void setPrimaryKey(long pk) {
		setShardId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_shardId);
	}

	public long getShardId() {
		return _shardId;
	}

	public void setShardId(long shardId) {
		if (shardId != _shardId) {
			_shardId = shardId;
		}
	}

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		if (classNameId != _classNameId) {
			_classNameId = classNameId;
		}
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		if (classPK != _classPK) {
			_classPK = classPK;
		}
	}

	public String getJdbcName() {
		return GetterUtil.getString(_jdbcName);
	}

	public void setJdbcName(String jdbcName) {
		if (((jdbcName == null) && (_jdbcName != null)) ||
				((jdbcName != null) && (_jdbcName == null)) ||
				((jdbcName != null) && (_jdbcName != null) &&
				!jdbcName.equals(_jdbcName))) {
			_jdbcName = jdbcName;
		}
	}

	public Shard toEscapedModel() {
		if (isEscapedModel()) {
			return (Shard)this;
		}
		else {
			Shard model = new ShardImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setShardId(getShardId());
			model.setClassNameId(getClassNameId());
			model.setClassPK(getClassPK());
			model.setJdbcName(HtmlUtil.escape(getJdbcName()));

			model = (Shard)Proxy.newProxyInstance(Shard.class.getClassLoader(),
					new Class[] { Shard.class }, new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(Shard.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		ShardImpl clone = new ShardImpl();

		clone.setShardId(getShardId());
		clone.setClassNameId(getClassNameId());
		clone.setClassPK(getClassPK());
		clone.setJdbcName(getJdbcName());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ShardImpl shard = (ShardImpl)obj;

		long pk = shard.getPrimaryKey();

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

		ShardImpl shard = null;

		try {
			shard = (ShardImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = shard.getPrimaryKey();

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

	private long _shardId;
	private long _classNameId;
	private long _classPK;
	private String _jdbcName;
	private transient ExpandoBridge _expandoBridge;
}