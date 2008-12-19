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

package com.liferay.portal.service;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;

/**
 * <a href="LayoutSetLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.portal.service.impl.LayoutSetLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.LayoutSetLocalServiceUtil
 *
 */
@Transactional(rollbackFor =  {
	PortalException.class, SystemException.class})
public interface LayoutSetLocalService {
	public com.liferay.portal.model.LayoutSet addLayoutSet(
		com.liferay.portal.model.LayoutSet layoutSet)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.LayoutSet createLayoutSet(long layoutSetId);

	public void deleteLayoutSet(long layoutSetId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteLayoutSet(com.liferay.portal.model.LayoutSet layoutSet)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.LayoutSet getLayoutSet(long layoutSetId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.LayoutSet> getLayoutSets(
		int start, int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutSetsCount() throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.LayoutSet updateLayoutSet(
		com.liferay.portal.model.LayoutSet layoutSet)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.LayoutSet addLayoutSet(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteLayoutSet(long groupId, boolean privateLayout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.LayoutSet getLayoutSet(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.LayoutSet getLayoutSet(
		java.lang.String virtualHost)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void updateLogo(long groupId, boolean privateLayout, boolean logo,
		java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void updateLookAndFeel(long groupId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.LayoutSet updateLookAndFeel(long groupId,
		boolean privateLayout, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.LayoutSet updatePageCount(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.LayoutSet updateVirtualHost(long groupId,
		boolean privateLayout, java.lang.String virtualHost)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;
}