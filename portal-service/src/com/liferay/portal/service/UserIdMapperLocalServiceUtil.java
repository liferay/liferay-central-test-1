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
 * <a href="UserIdMapperLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.UserIdMapperLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.UserIdMapperLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.UserIdMapperLocalService
 * @see com.liferay.portal.service.UserIdMapperLocalServiceFactory
 *
 */
public class UserIdMapperLocalServiceUtil {
	public static com.liferay.portal.model.UserIdMapper addUserIdMapper(
		com.liferay.portal.model.UserIdMapper userIdMapper)
		throws com.liferay.portal.SystemException {
		UserIdMapperLocalService userIdMapperLocalService = UserIdMapperLocalServiceFactory.getService();

		return userIdMapperLocalService.addUserIdMapper(userIdMapper);
	}

	public static void deleteUserIdMapper(long userIdMapperId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserIdMapperLocalService userIdMapperLocalService = UserIdMapperLocalServiceFactory.getService();

		userIdMapperLocalService.deleteUserIdMapper(userIdMapperId);
	}

	public static void deleteUserIdMapper(
		com.liferay.portal.model.UserIdMapper userIdMapper)
		throws com.liferay.portal.SystemException {
		UserIdMapperLocalService userIdMapperLocalService = UserIdMapperLocalServiceFactory.getService();

		userIdMapperLocalService.deleteUserIdMapper(userIdMapper);
	}

	public static java.util.List<com.liferay.portal.model.UserIdMapper> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		UserIdMapperLocalService userIdMapperLocalService = UserIdMapperLocalServiceFactory.getService();

		return userIdMapperLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.UserIdMapper> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		UserIdMapperLocalService userIdMapperLocalService = UserIdMapperLocalServiceFactory.getService();

		return userIdMapperLocalService.dynamicQuery(queryInitializer, start,
			end);
	}

	public static com.liferay.portal.model.UserIdMapper getUserIdMapper(
		long userIdMapperId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserIdMapperLocalService userIdMapperLocalService = UserIdMapperLocalServiceFactory.getService();

		return userIdMapperLocalService.getUserIdMapper(userIdMapperId);
	}

	public static com.liferay.portal.model.UserIdMapper updateUserIdMapper(
		com.liferay.portal.model.UserIdMapper userIdMapper)
		throws com.liferay.portal.SystemException {
		UserIdMapperLocalService userIdMapperLocalService = UserIdMapperLocalServiceFactory.getService();

		return userIdMapperLocalService.updateUserIdMapper(userIdMapper);
	}

	public static void deleteUserIdMappers(long userId)
		throws com.liferay.portal.SystemException {
		UserIdMapperLocalService userIdMapperLocalService = UserIdMapperLocalServiceFactory.getService();

		userIdMapperLocalService.deleteUserIdMappers(userId);
	}

	public static com.liferay.portal.model.UserIdMapper getUserIdMapper(
		long userId, java.lang.String type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserIdMapperLocalService userIdMapperLocalService = UserIdMapperLocalServiceFactory.getService();

		return userIdMapperLocalService.getUserIdMapper(userId, type);
	}

	public static com.liferay.portal.model.UserIdMapper getUserIdMapperByExternalUserId(
		java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		UserIdMapperLocalService userIdMapperLocalService = UserIdMapperLocalServiceFactory.getService();

		return userIdMapperLocalService.getUserIdMapperByExternalUserId(type,
			externalUserId);
	}

	public static java.util.List<com.liferay.portal.model.UserIdMapper> getUserIdMappers(
		long userId) throws com.liferay.portal.SystemException {
		UserIdMapperLocalService userIdMapperLocalService = UserIdMapperLocalServiceFactory.getService();

		return userIdMapperLocalService.getUserIdMappers(userId);
	}

	public static com.liferay.portal.model.UserIdMapper updateUserIdMapper(
		long userId, java.lang.String type, java.lang.String description,
		java.lang.String externalUserId)
		throws com.liferay.portal.SystemException {
		UserIdMapperLocalService userIdMapperLocalService = UserIdMapperLocalServiceFactory.getService();

		return userIdMapperLocalService.updateUserIdMapper(userId, type,
			description, externalUserId);
	}
}