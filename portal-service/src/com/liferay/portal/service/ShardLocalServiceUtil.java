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

package com.liferay.portal.service;


/**
 * <a href="ShardLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.ShardLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ShardLocalService
 *
 */
public class ShardLocalServiceUtil {
	public static com.liferay.portal.model.Shard addShard(
		com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.SystemException {
		return getService().addShard(shard);
	}

	public static com.liferay.portal.model.Shard createShard(long shardId) {
		return getService().createShard(shardId);
	}

	public static void deleteShard(long shardId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteShard(shardId);
	}

	public static void deleteShard(com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.SystemException {
		getService().deleteShard(shard);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.Shard getShard(long shardId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getShard(shardId);
	}

	public static java.util.List<com.liferay.portal.model.Shard> getShards(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getShards(start, end);
	}

	public static int getShardsCount()
		throws com.liferay.portal.SystemException {
		return getService().getShardsCount();
	}

	public static com.liferay.portal.model.Shard updateShard(
		com.liferay.portal.model.Shard shard)
		throws com.liferay.portal.SystemException {
		return getService().updateShard(shard);
	}

	public static com.liferay.portal.model.Shard updateShard(
		com.liferay.portal.model.Shard shard, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateShard(shard, merge);
	}

	public static void addCompany(long companyId, java.lang.String shardName)
		throws com.liferay.portal.SystemException {
		getService().addCompany(companyId, shardName);
	}

	public static java.lang.String getShardNameByCompanyId(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getShardNameByCompanyId(companyId);
	}

	public static ShardLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("ShardLocalService is not set");
		}

		return _service;
	}

	public void setService(ShardLocalService service) {
		_service = service;
	}

	private static ShardLocalService _service;
}