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

package com.liferay.portlet.social.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the social relation local service. This utility wraps {@link com.liferay.portlet.social.service.impl.SocialRelationLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialRelationLocalService
 * @see com.liferay.portlet.social.service.base.SocialRelationLocalServiceBaseImpl
 * @see com.liferay.portlet.social.service.impl.SocialRelationLocalServiceImpl
 * @generated
 */
public class SocialRelationLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.social.service.impl.SocialRelationLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the social relation to the database. Also notifies the appropriate model listeners.
	*
	* @param socialRelation the social relation to add
	* @return the social relation that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialRelation addSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSocialRelation(socialRelation);
	}

	/**
	* Creates a new social relation with the primary key. Does not add the social relation to the database.
	*
	* @param relationId the primary key for the new social relation
	* @return the new social relation
	*/
	public static com.liferay.portlet.social.model.SocialRelation createSocialRelation(
		long relationId) {
		return getService().createSocialRelation(relationId);
	}

	/**
	* Deletes the social relation with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param relationId the primary key of the social relation to delete
	* @throws PortalException if a social relation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSocialRelation(long relationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialRelation(relationId);
	}

	/**
	* Deletes the social relation from the database. Also notifies the appropriate model listeners.
	*
	* @param socialRelation the social relation to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialRelation(socialRelation);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the social relation with the primary key.
	*
	* @param relationId the primary key of the social relation to get
	* @return the social relation
	* @throws PortalException if a social relation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialRelation getSocialRelation(
		long relationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialRelation(relationId);
	}

	/**
	* Gets a range of all the social relations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social relations to return
	* @param end the upper bound of the range of social relations to return (not inclusive)
	* @return the range of social relations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> getSocialRelations(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialRelations(start, end);
	}

	/**
	* Gets the number of social relations.
	*
	* @return the number of social relations
	* @throws SystemException if a system exception occurred
	*/
	public static int getSocialRelationsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialRelationsCount();
	}

	/**
	* Updates the social relation in the database. Also notifies the appropriate model listeners.
	*
	* @param socialRelation the social relation to update
	* @return the social relation that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialRelation updateSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialRelation(socialRelation);
	}

	/**
	* Updates the social relation in the database. Also notifies the appropriate model listeners.
	*
	* @param socialRelation the social relation to update
	* @param merge whether to merge the social relation with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the social relation that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialRelation updateSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialRelation(socialRelation, merge);
	}

	public static com.liferay.portlet.social.model.SocialRelation addRelation(
		long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addRelation(userId1, userId2, type);
	}

	public static void deleteRelation(long relationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRelation(relationId);
	}

	public static void deleteRelation(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRelation(userId1, userId2, type);
	}

	public static void deleteRelation(
		com.liferay.portlet.social.model.SocialRelation relation)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRelation(relation);
	}

	public static void deleteRelations(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRelations(userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> getInverseRelations(
		long userId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getInverseRelations(userId, type, start, end);
	}

	public static int getInverseRelationsCount(long userId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getInverseRelationsCount(userId, type);
	}

	public static com.liferay.portlet.social.model.SocialRelation getRelation(
		long relationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRelation(relationId);
	}

	public static com.liferay.portlet.social.model.SocialRelation getRelation(
		long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRelation(userId1, userId2, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> getRelations(
		long userId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRelations(userId, type, start, end);
	}

	public static int getRelationsCount(long userId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRelationsCount(userId, type);
	}

	public static boolean hasRelation(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasRelation(userId1, userId2, type);
	}

	public static boolean isRelatable(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().isRelatable(userId1, userId2, type);
	}

	public static SocialRelationLocalService getService() {
		if (_service == null) {
			_service = (SocialRelationLocalService)PortalBeanLocatorUtil.locate(SocialRelationLocalService.class.getName());

			ReferenceRegistry.registerReference(SocialRelationLocalServiceUtil.class,
				"_service");
			MethodCache.remove(SocialRelationLocalService.class);
		}

		return _service;
	}

	public void setService(SocialRelationLocalService service) {
		MethodCache.remove(SocialRelationLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(SocialRelationLocalServiceUtil.class,
			"_service");
		MethodCache.remove(SocialRelationLocalService.class);
	}

	private static SocialRelationLocalService _service;
}