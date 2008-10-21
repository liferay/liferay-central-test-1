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

package com.liferay.wsrp.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import com.liferay.wsrp.model.WSRPConfiguredProducer;
import com.liferay.wsrp.service.WSRPConfiguredProducerLocalService;
import com.liferay.wsrp.service.WSRPPortletLocalService;
import com.liferay.wsrp.service.persistence.WSRPConfiguredProducerPersistence;
import com.liferay.wsrp.service.persistence.WSRPPortletPersistence;

import java.util.List;

/**
 * <a href="WSRPConfiguredProducerLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class WSRPConfiguredProducerLocalServiceBaseImpl
	implements WSRPConfiguredProducerLocalService {
	public WSRPConfiguredProducer addWSRPConfiguredProducer(
		WSRPConfiguredProducer wsrpConfiguredProducer)
		throws SystemException {
		wsrpConfiguredProducer.setNew(true);

		return wsrpConfiguredProducerPersistence.update(wsrpConfiguredProducer,
			false);
	}

	public WSRPConfiguredProducer createWSRPConfiguredProducer(
		long configuredProducerId) {
		return wsrpConfiguredProducerPersistence.create(configuredProducerId);
	}

	public void deleteWSRPConfiguredProducer(long configuredProducerId)
		throws PortalException, SystemException {
		wsrpConfiguredProducerPersistence.remove(configuredProducerId);
	}

	public void deleteWSRPConfiguredProducer(
		WSRPConfiguredProducer wsrpConfiguredProducer)
		throws SystemException {
		wsrpConfiguredProducerPersistence.remove(wsrpConfiguredProducer);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return wsrpConfiguredProducerPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return wsrpConfiguredProducerPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	public WSRPConfiguredProducer getWSRPConfiguredProducer(
		long configuredProducerId) throws PortalException, SystemException {
		return wsrpConfiguredProducerPersistence.findByPrimaryKey(configuredProducerId);
	}

	public List<WSRPConfiguredProducer> getWSRPConfiguredProducers(int start,
		int end) throws SystemException {
		return wsrpConfiguredProducerPersistence.findAll(start, end);
	}

	public int getWSRPConfiguredProducersCount() throws SystemException {
		return wsrpConfiguredProducerPersistence.countAll();
	}

	public WSRPConfiguredProducer updateWSRPConfiguredProducer(
		WSRPConfiguredProducer wsrpConfiguredProducer)
		throws SystemException {
		wsrpConfiguredProducer.setNew(false);

		return wsrpConfiguredProducerPersistence.update(wsrpConfiguredProducer,
			true);
	}

	public WSRPConfiguredProducerLocalService getWSRPConfiguredProducerLocalService() {
		return wsrpConfiguredProducerLocalService;
	}

	public void setWSRPConfiguredProducerLocalService(
		WSRPConfiguredProducerLocalService wsrpConfiguredProducerLocalService) {
		this.wsrpConfiguredProducerLocalService = wsrpConfiguredProducerLocalService;
	}

	public WSRPConfiguredProducerPersistence getWSRPConfiguredProducerPersistence() {
		return wsrpConfiguredProducerPersistence;
	}

	public void setWSRPConfiguredProducerPersistence(
		WSRPConfiguredProducerPersistence wsrpConfiguredProducerPersistence) {
		this.wsrpConfiguredProducerPersistence = wsrpConfiguredProducerPersistence;
	}

	public WSRPPortletLocalService getWSRPPortletLocalService() {
		return wsrpPortletLocalService;
	}

	public void setWSRPPortletLocalService(
		WSRPPortletLocalService wsrpPortletLocalService) {
		this.wsrpPortletLocalService = wsrpPortletLocalService;
	}

	public WSRPPortletPersistence getWSRPPortletPersistence() {
		return wsrpPortletPersistence;
	}

	public void setWSRPPortletPersistence(
		WSRPPortletPersistence wsrpPortletPersistence) {
		this.wsrpPortletPersistence = wsrpPortletPersistence;
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public CounterService getCounterService() {
		return counterService;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	@javax.annotation.Resource(name = "com.liferay.wsrp.service.WSRPConfiguredProducerLocalService.impl")
	protected WSRPConfiguredProducerLocalService wsrpConfiguredProducerLocalService;
	@javax.annotation.Resource(name = "com.liferay.wsrp.service.persistence.WSRPConfiguredProducerPersistence.impl")
	protected WSRPConfiguredProducerPersistence wsrpConfiguredProducerPersistence;
	@javax.annotation.Resource(name = "com.liferay.wsrp.service.WSRPPortletLocalService.impl")
	protected WSRPPortletLocalService wsrpPortletLocalService;
	@javax.annotation.Resource(name = "com.liferay.wsrp.service.persistence.WSRPPortletPersistence.impl")
	protected WSRPPortletPersistence wsrpPortletPersistence;
	@javax.annotation.Resource(name = "com.liferay.counter.service.CounterLocalService.impl")
	protected CounterLocalService counterLocalService;
	@javax.annotation.Resource(name = "com.liferay.counter.service.CounterService.impl")
	protected CounterService counterService;
}