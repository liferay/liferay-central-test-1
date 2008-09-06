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

package com.liferay.portlet.expando.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.InitializingBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.service.ExpandoColumnLocalService;
import com.liferay.portlet.expando.service.ExpandoRowLocalService;
import com.liferay.portlet.expando.service.ExpandoTableLocalService;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;
import com.liferay.portlet.expando.service.persistence.ExpandoColumnFinder;
import com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoRowFinder;
import com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoValueFinder;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;

import java.util.List;

/**
 * <a href="ExpandoRowLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class ExpandoRowLocalServiceBaseImpl
	implements ExpandoRowLocalService, InitializingBean {
	public ExpandoRow addExpandoRow(ExpandoRow expandoRow)
		throws SystemException {
		expandoRow.setNew(true);

		return expandoRowPersistence.update(expandoRow, false);
	}

	public ExpandoRow createExpandoRow(long rowId) {
		return expandoRowPersistence.create(rowId);
	}

	public void deleteExpandoRow(long rowId)
		throws PortalException, SystemException {
		expandoRowPersistence.remove(rowId);
	}

	public void deleteExpandoRow(ExpandoRow expandoRow)
		throws SystemException {
		expandoRowPersistence.remove(expandoRow);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return expandoRowPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return expandoRowPersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	public ExpandoRow getExpandoRow(long rowId)
		throws PortalException, SystemException {
		return expandoRowPersistence.findByPrimaryKey(rowId);
	}

	public List<ExpandoRow> getExpandoRows(int start, int end)
		throws SystemException {
		return expandoRowPersistence.findAll(start, end);
	}

	public int getExpandoRowsCount() throws SystemException {
		return expandoRowPersistence.countAll();
	}

	public ExpandoRow updateExpandoRow(ExpandoRow expandoRow)
		throws SystemException {
		expandoRow.setNew(false);

		return expandoRowPersistence.update(expandoRow, true);
	}

	public ExpandoColumnLocalService getExpandoColumnLocalService() {
		return expandoColumnLocalService;
	}

	public void setExpandoColumnLocalService(
		ExpandoColumnLocalService expandoColumnLocalService) {
		this.expandoColumnLocalService = expandoColumnLocalService;
	}

	public ExpandoColumnPersistence getExpandoColumnPersistence() {
		return expandoColumnPersistence;
	}

	public void setExpandoColumnPersistence(
		ExpandoColumnPersistence expandoColumnPersistence) {
		this.expandoColumnPersistence = expandoColumnPersistence;
	}

	public ExpandoColumnFinder getExpandoColumnFinder() {
		return expandoColumnFinder;
	}

	public void setExpandoColumnFinder(ExpandoColumnFinder expandoColumnFinder) {
		this.expandoColumnFinder = expandoColumnFinder;
	}

	public ExpandoRowPersistence getExpandoRowPersistence() {
		return expandoRowPersistence;
	}

	public void setExpandoRowPersistence(
		ExpandoRowPersistence expandoRowPersistence) {
		this.expandoRowPersistence = expandoRowPersistence;
	}

	public ExpandoRowFinder getExpandoRowFinder() {
		return expandoRowFinder;
	}

	public void setExpandoRowFinder(ExpandoRowFinder expandoRowFinder) {
		this.expandoRowFinder = expandoRowFinder;
	}

	public ExpandoTableLocalService getExpandoTableLocalService() {
		return expandoTableLocalService;
	}

	public void setExpandoTableLocalService(
		ExpandoTableLocalService expandoTableLocalService) {
		this.expandoTableLocalService = expandoTableLocalService;
	}

	public ExpandoTablePersistence getExpandoTablePersistence() {
		return expandoTablePersistence;
	}

	public void setExpandoTablePersistence(
		ExpandoTablePersistence expandoTablePersistence) {
		this.expandoTablePersistence = expandoTablePersistence;
	}

	public ExpandoValueLocalService getExpandoValueLocalService() {
		return expandoValueLocalService;
	}

	public void setExpandoValueLocalService(
		ExpandoValueLocalService expandoValueLocalService) {
		this.expandoValueLocalService = expandoValueLocalService;
	}

	public ExpandoValuePersistence getExpandoValuePersistence() {
		return expandoValuePersistence;
	}

	public void setExpandoValuePersistence(
		ExpandoValuePersistence expandoValuePersistence) {
		this.expandoValuePersistence = expandoValuePersistence;
	}

	public ExpandoValueFinder getExpandoValueFinder() {
		return expandoValueFinder;
	}

	public void setExpandoValueFinder(ExpandoValueFinder expandoValueFinder) {
		this.expandoValueFinder = expandoValueFinder;
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

	public void afterPropertiesSet() {
		if (expandoColumnLocalService == null) {
			expandoColumnLocalService = (ExpandoColumnLocalService)PortalBeanLocatorUtil.locate(ExpandoColumnLocalService.class.getName() +
					".impl");
		}

		if (expandoColumnPersistence == null) {
			expandoColumnPersistence = (ExpandoColumnPersistence)PortalBeanLocatorUtil.locate(ExpandoColumnPersistence.class.getName() +
					".impl");
		}

		if (expandoColumnFinder == null) {
			expandoColumnFinder = (ExpandoColumnFinder)PortalBeanLocatorUtil.locate(ExpandoColumnFinder.class.getName() +
					".impl");
		}

		if (expandoRowPersistence == null) {
			expandoRowPersistence = (ExpandoRowPersistence)PortalBeanLocatorUtil.locate(ExpandoRowPersistence.class.getName() +
					".impl");
		}

		if (expandoRowFinder == null) {
			expandoRowFinder = (ExpandoRowFinder)PortalBeanLocatorUtil.locate(ExpandoRowFinder.class.getName() +
					".impl");
		}

		if (expandoTableLocalService == null) {
			expandoTableLocalService = (ExpandoTableLocalService)PortalBeanLocatorUtil.locate(ExpandoTableLocalService.class.getName() +
					".impl");
		}

		if (expandoTablePersistence == null) {
			expandoTablePersistence = (ExpandoTablePersistence)PortalBeanLocatorUtil.locate(ExpandoTablePersistence.class.getName() +
					".impl");
		}

		if (expandoValueLocalService == null) {
			expandoValueLocalService = (ExpandoValueLocalService)PortalBeanLocatorUtil.locate(ExpandoValueLocalService.class.getName() +
					".impl");
		}

		if (expandoValuePersistence == null) {
			expandoValuePersistence = (ExpandoValuePersistence)PortalBeanLocatorUtil.locate(ExpandoValuePersistence.class.getName() +
					".impl");
		}

		if (expandoValueFinder == null) {
			expandoValueFinder = (ExpandoValueFinder)PortalBeanLocatorUtil.locate(ExpandoValueFinder.class.getName() +
					".impl");
		}

		if (counterLocalService == null) {
			counterLocalService = (CounterLocalService)PortalBeanLocatorUtil.locate(CounterLocalService.class.getName() +
					".impl");
		}

		if (counterService == null) {
			counterService = (CounterService)PortalBeanLocatorUtil.locate(CounterService.class.getName() +
					".impl");
		}
	}

	protected ExpandoColumnLocalService expandoColumnLocalService;
	protected ExpandoColumnPersistence expandoColumnPersistence;
	protected ExpandoColumnFinder expandoColumnFinder;
	protected ExpandoRowPersistence expandoRowPersistence;
	protected ExpandoRowFinder expandoRowFinder;
	protected ExpandoTableLocalService expandoTableLocalService;
	protected ExpandoTablePersistence expandoTablePersistence;
	protected ExpandoValueLocalService expandoValueLocalService;
	protected ExpandoValuePersistence expandoValuePersistence;
	protected ExpandoValueFinder expandoValueFinder;
	protected CounterLocalService counterLocalService;
	protected CounterService counterService;
}