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


/**
 * <a href="ContactLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.ContactLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.ContactLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ContactLocalService
 * @see com.liferay.portal.service.ContactLocalServiceFactory
 *
 */
public class ContactLocalServiceUtil {
	public static com.liferay.portal.model.Contact addContact(
		com.liferay.portal.model.Contact contact)
		throws com.liferay.portal.SystemException {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.addContact(contact);
	}

	public static void deleteContact(long contactId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		contactLocalService.deleteContact(contactId);
	}

	public static void deleteContact(com.liferay.portal.model.Contact contact)
		throws com.liferay.portal.SystemException {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		contactLocalService.deleteContact(contact);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.Contact getContact(long contactId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getContact(contactId);
	}

	public static java.util.List<com.liferay.portal.model.Contact> getContacts(
		int start, int end) throws com.liferay.portal.SystemException {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getContacts(start, end);
	}

	public static int getContactsCount()
		throws com.liferay.portal.SystemException {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getContactsCount();
	}

	public static com.liferay.portal.model.Contact updateContact(
		com.liferay.portal.model.Contact contact)
		throws com.liferay.portal.SystemException {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.updateContact(contact);
	}
}