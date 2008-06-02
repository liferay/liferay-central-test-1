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
 * <a href="AddressLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.AddressLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.AddressLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.AddressLocalService
 * @see com.liferay.portal.service.AddressLocalServiceFactory
 *
 */
public class AddressLocalServiceUtil {
	public static com.liferay.portal.model.Address addAddress(
		com.liferay.portal.model.Address address)
		throws com.liferay.portal.SystemException {
		AddressLocalService addressLocalService = AddressLocalServiceFactory.getService();

		return addressLocalService.addAddress(address);
	}

	public static void deleteAddress(long addressId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AddressLocalService addressLocalService = AddressLocalServiceFactory.getService();

		addressLocalService.deleteAddress(addressId);
	}

	public static void deleteAddress(com.liferay.portal.model.Address address)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AddressLocalService addressLocalService = AddressLocalServiceFactory.getService();

		addressLocalService.deleteAddress(address);
	}

	public static java.util.List<com.liferay.portal.model.Address> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		AddressLocalService addressLocalService = AddressLocalServiceFactory.getService();

		return addressLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.Address> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		AddressLocalService addressLocalService = AddressLocalServiceFactory.getService();

		return addressLocalService.dynamicQuery(queryInitializer, start, end);
	}

	public static com.liferay.portal.model.Address getAddress(long addressId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AddressLocalService addressLocalService = AddressLocalServiceFactory.getService();

		return addressLocalService.getAddress(addressId);
	}

	public static com.liferay.portal.model.Address updateAddress(
		com.liferay.portal.model.Address address)
		throws com.liferay.portal.SystemException {
		AddressLocalService addressLocalService = AddressLocalServiceFactory.getService();

		return addressLocalService.updateAddress(address);
	}

	public static com.liferay.portal.model.Address addAddress(long userId,
		java.lang.String className, long classPK, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long regionId,
		long countryId, int typeId, boolean mailing, boolean primary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AddressLocalService addressLocalService = AddressLocalServiceFactory.getService();

		return addressLocalService.addAddress(userId, className, classPK,
			street1, street2, street3, city, zip, regionId, countryId, typeId,
			mailing, primary);
	}

	public static void deleteAddresses(long companyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		AddressLocalService addressLocalService = AddressLocalServiceFactory.getService();

		addressLocalService.deleteAddresses(companyId, className, classPK);
	}

	public static java.util.List<com.liferay.portal.model.Address> getAddresses()
		throws com.liferay.portal.SystemException {
		AddressLocalService addressLocalService = AddressLocalServiceFactory.getService();

		return addressLocalService.getAddresses();
	}

	public static java.util.List<com.liferay.portal.model.Address> getAddresses(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		AddressLocalService addressLocalService = AddressLocalServiceFactory.getService();

		return addressLocalService.getAddresses(companyId, className, classPK);
	}

	public static com.liferay.portal.model.Address updateAddress(
		long addressId, java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long regionId, long countryId, int typeId, boolean mailing,
		boolean primary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AddressLocalService addressLocalService = AddressLocalServiceFactory.getService();

		return addressLocalService.updateAddress(addressId, street1, street2,
			street3, city, zip, regionId, countryId, typeId, mailing, primary);
	}
}