/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.announcements.service;

/**
 * <p>
 * This class is a wrapper for {@link AnnouncementsDeliveryService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsDeliveryService
 * @generated
 */
public class AnnouncementsDeliveryServiceWrapper
	implements AnnouncementsDeliveryService {
	public AnnouncementsDeliveryServiceWrapper(
		AnnouncementsDeliveryService announcementsDeliveryService) {
		_announcementsDeliveryService = announcementsDeliveryService;
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery updateDelivery(
		long userId, java.lang.String type, boolean email, boolean sms,
		boolean website)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryService.updateDelivery(userId, type,
			email, sms, website);
	}

	public AnnouncementsDeliveryService getWrappedAnnouncementsDeliveryService() {
		return _announcementsDeliveryService;
	}

	public void setWrappedAnnouncementsDeliveryService(
		AnnouncementsDeliveryService announcementsDeliveryService) {
		_announcementsDeliveryService = announcementsDeliveryService;
	}

	private AnnouncementsDeliveryService _announcementsDeliveryService;
}