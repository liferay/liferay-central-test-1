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

package com.liferay.portal.service.persistence;

/**
 * <a href="PortletItemPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface PortletItemPersistence {
	public com.liferay.portal.model.PortletItem create(long portletItemId);

	public com.liferay.portal.model.PortletItem remove(long portletItemId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPortletItemException;

	public com.liferay.portal.model.PortletItem remove(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PortletItem update(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PortletItem update(
		com.liferay.portal.model.PortletItem portletItem, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PortletItem updateImpl(
		com.liferay.portal.model.PortletItem portletItem, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PortletItem findByPrimaryKey(
		long portletItemId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPortletItemException;

	public com.liferay.portal.model.PortletItem fetchByPrimaryKey(
		long portletItemId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId, int begin,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PortletItem findByG_P_C_First(
		long groupId, java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPortletItemException;

	public com.liferay.portal.model.PortletItem findByG_P_C_Last(long groupId,
		java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPortletItemException;

	public com.liferay.portal.model.PortletItem[] findByG_P_C_PrevAndNext(
		long portletItemId, long groupId, java.lang.String portletId,
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPortletItemException;

	public com.liferay.portal.model.PortletItem findByG_N_P_C(long groupId,
		java.lang.String name, java.lang.String portletId, long classNameId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPortletItemException;

	public com.liferay.portal.model.PortletItem fetchByG_N_P_C(long groupId,
		java.lang.String name, java.lang.String portletId, long classNameId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findAll(
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByG_P_C(long groupId, java.lang.String portletId,
		long classNameId) throws com.liferay.portal.SystemException;

	public void removeByG_N_P_C(long groupId, java.lang.String name,
		java.lang.String portletId, long classNameId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPortletItemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByG_P_C(long groupId, java.lang.String portletId,
		long classNameId) throws com.liferay.portal.SystemException;

	public int countByG_N_P_C(long groupId, java.lang.String name,
		java.lang.String portletId, long classNameId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}