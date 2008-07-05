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

package com.liferay.portlet.announcements.service;


/**
 * <a href="AnnouncementsDeliveryLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.portlet.announcements.service.impl.AnnouncementsDeliveryLocalServiceImpl</code>.
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
 * @see com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalServiceFactory
 * @see com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalServiceUtil
 *
 */
public interface AnnouncementsDeliveryLocalService {
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery addAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.SystemException;

	public void deleteAnnouncementsDelivery(long deliveryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery getAnnouncementsDelivery(
		long deliveryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery updateAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.SystemException;

	public void deleteDeliveries(long userId)
		throws com.liferay.portal.SystemException;

	public void deleteDelivery(long deliveryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteDelivery(long userId, java.lang.String type)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery getDelivery(
		long deliveryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> getUserDeliveries(
		long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery getUserDelivery(
		long userId, java.lang.String type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery updateDelivery(
		long userId, java.lang.String type, boolean email, boolean sms,
		boolean website)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;
}