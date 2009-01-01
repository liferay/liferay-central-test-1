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

package com.liferay.portlet.announcements.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portlet.announcements.NoSuchDeliveryException;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.model.impl.AnnouncementsEntryImpl;
import com.liferay.portlet.announcements.service.base.AnnouncementsDeliveryLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="AnnouncementsDeliveryLocalServiceImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementsDeliveryLocalServiceImpl
	extends AnnouncementsDeliveryLocalServiceBaseImpl {

	public void deleteDeliveries(long userId) throws SystemException {
		announcementsDeliveryPersistence.removeByUserId(userId);
	}

	public void deleteDelivery(long deliveryId)
		throws PortalException, SystemException {

		announcementsDeliveryPersistence.remove(deliveryId);
	}

	public void deleteDelivery(long userId, String type)
		throws SystemException {

		try {
			announcementsDeliveryPersistence.removeByU_T(userId, type);
		}
		catch (NoSuchDeliveryException nsde) {
		}
	}

	public AnnouncementsDelivery getDelivery(long deliveryId)
		throws PortalException, SystemException {

		return announcementsDeliveryPersistence.findByPrimaryKey(deliveryId);
	}

	public List<AnnouncementsDelivery> getUserDeliveries(long userId)
		throws PortalException, SystemException {

		List<AnnouncementsDelivery> deliveries =
			new ArrayList<AnnouncementsDelivery>(
				AnnouncementsEntryImpl.TYPES.length);

		for (String type : AnnouncementsEntryImpl.TYPES) {
			deliveries.add(getUserDelivery(userId, type));
		}

		return deliveries;
	}

	public AnnouncementsDelivery getUserDelivery(long userId, String type)
		throws PortalException, SystemException {

		AnnouncementsDelivery delivery =
			announcementsDeliveryPersistence.fetchByU_T(userId, type);

		if (delivery == null) {
			User user = userPersistence.findByPrimaryKey(userId);

			long deliveryId = counterLocalService.increment();

			delivery = announcementsDeliveryPersistence.create(deliveryId);

			delivery.setCompanyId(user.getCompanyId());
			delivery.setUserId(user.getUserId());
			delivery.setType(type);
			delivery.setEmail(false);
			delivery.setSms(false);
			delivery.setWebsite(true);

			announcementsDeliveryPersistence.update(delivery, false);
		}

		return delivery;
	}

	public AnnouncementsDelivery updateDelivery(
			long userId, String type, boolean email, boolean sms,
			boolean website)
		throws PortalException, SystemException {

		AnnouncementsDelivery delivery = getUserDelivery(userId, type);

		delivery.setEmail(email);
		delivery.setSms(sms);
		delivery.setWebsite(website);

		announcementsDeliveryPersistence.update(delivery, false);

		return delivery;
	}

}