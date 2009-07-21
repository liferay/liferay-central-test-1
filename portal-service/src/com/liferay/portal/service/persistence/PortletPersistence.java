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

package com.liferay.portal.service.persistence;

public interface PortletPersistence extends BasePersistence {
	public void cacheResult(com.liferay.portal.model.Portlet portlet);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Portlet> portlets);

	public void clearCache();

	public com.liferay.portal.model.Portlet create(long id);

	public com.liferay.portal.model.Portlet remove(long id)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Portlet remove(
		com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Portlet update(
		com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Portlet update(
		com.liferay.portal.model.Portlet portlet, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Portlet updateImpl(
		com.liferay.portal.model.Portlet portlet, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Portlet findByPrimaryKey(long id)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Portlet fetchByPrimaryKey(long id)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Portlet findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Portlet findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Portlet[] findByCompanyId_PrevAndNext(
		long id, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Portlet findByC_P(long companyId,
		java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Portlet fetchByC_P(long companyId,
		java.lang.String portletId) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Portlet fetchByC_P(long companyId,
		java.lang.String portletId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Portlet> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Portlet> findAll(int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Portlet> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByC_P(long companyId, java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByC_P(long companyId, java.lang.String portletId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}