/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * <a href="WorkflowDefinitionLinkUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowDefinitionLinkPersistence
 * @see       WorkflowDefinitionLinkPersistenceImpl
 * @generated
 */
public class WorkflowDefinitionLinkUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(WorkflowDefinitionLink)
	 */
	public static void clearCache(WorkflowDefinitionLink workflowDefinitionLink) {
		getPersistence().clearCache(workflowDefinitionLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<WorkflowDefinitionLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WorkflowDefinitionLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WorkflowDefinitionLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static WorkflowDefinitionLink remove(
		WorkflowDefinitionLink workflowDefinitionLink)
		throws SystemException {
		return getPersistence().remove(workflowDefinitionLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static WorkflowDefinitionLink update(
		WorkflowDefinitionLink workflowDefinitionLink, boolean merge)
		throws SystemException {
		return getPersistence().update(workflowDefinitionLink, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static WorkflowDefinitionLink update(
		WorkflowDefinitionLink workflowDefinitionLink, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence()
				   .update(workflowDefinitionLink, merge, serviceContext);
	}

	public static void cacheResult(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink) {
		getPersistence().cacheResult(workflowDefinitionLink);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> workflowDefinitionLinks) {
		getPersistence().cacheResult(workflowDefinitionLinks);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink create(
		long workflowDefinitionLinkId) {
		return getPersistence().create(workflowDefinitionLinkId);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink remove(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(workflowDefinitionLinkId);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink updateImpl(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(workflowDefinitionLink, merge);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink findByPrimaryKey(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(workflowDefinitionLinkId);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink fetchByPrimaryKey(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(workflowDefinitionLinkId);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink[] findByCompanyId_PrevAndNext(
		long workflowDefinitionLinkId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(workflowDefinitionLinkId,
			companyId, orderByComparator);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink findByG_C_C(
		long groupId, long companyId, long classNameId)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C_C(groupId, companyId, classNameId);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink fetchByG_C_C(
		long groupId, long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_C_C(groupId, companyId, classNameId);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink fetchByG_C_C(
		long groupId, long companyId, long classNameId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_C(groupId, companyId, classNameId,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByC_W_W(
		long companyId, java.lang.String workflowDefinitionName,
		int workflowDefinitionVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_W_W(companyId, workflowDefinitionName,
			workflowDefinitionVersion);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByC_W_W(
		long companyId, java.lang.String workflowDefinitionName,
		int workflowDefinitionVersion, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_W_W(companyId, workflowDefinitionName,
			workflowDefinitionVersion, start, end);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByC_W_W(
		long companyId, java.lang.String workflowDefinitionName,
		int workflowDefinitionVersion, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_W_W(companyId, workflowDefinitionName,
			workflowDefinitionVersion, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink findByC_W_W_First(
		long companyId, java.lang.String workflowDefinitionName,
		int workflowDefinitionVersion,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_W_W_First(companyId, workflowDefinitionName,
			workflowDefinitionVersion, orderByComparator);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink findByC_W_W_Last(
		long companyId, java.lang.String workflowDefinitionName,
		int workflowDefinitionVersion,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_W_W_Last(companyId, workflowDefinitionName,
			workflowDefinitionVersion, orderByComparator);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink[] findByC_W_W_PrevAndNext(
		long workflowDefinitionLinkId, long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_W_W_PrevAndNext(workflowDefinitionLinkId,
			companyId, workflowDefinitionName, workflowDefinitionVersion,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByG_C_C(long groupId, long companyId,
		long classNameId)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C_C(groupId, companyId, classNameId);
	}

	public static void removeByC_W_W(long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByC_W_W(companyId, workflowDefinitionName,
			workflowDefinitionVersion);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByG_C_C(long groupId, long companyId,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C_C(groupId, companyId, classNameId);
	}

	public static int countByC_W_W(long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByC_W_W(companyId, workflowDefinitionName,
			workflowDefinitionVersion);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static WorkflowDefinitionLinkPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (WorkflowDefinitionLinkPersistence)PortalBeanLocatorUtil.locate(WorkflowDefinitionLinkPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(WorkflowDefinitionLinkPersistence persistence) {
		_persistence = persistence;
	}

	private static WorkflowDefinitionLinkPersistence _persistence;
}