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

package com.liferay.portlet.tasks.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.service.TasksProposalLocalServiceUtil;

/**
 * @author Raymond Augé
 */
public class TasksProposalPermission {

	public static void check(
			PermissionChecker permissionChecker, long proposalId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, proposalId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, TasksProposal proposal,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, proposal, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long proposalId,
			String actionId)
		throws PortalException, SystemException {

		TasksProposal proposal =
			TasksProposalLocalServiceUtil.getProposal(proposalId);

		return contains(permissionChecker, proposal, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, TasksProposal proposal,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				proposal.getCompanyId(), TasksProposal.class.getName(),
				proposal.getProposalId(), proposal.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			proposal.getGroupId(), TasksProposal.class.getName(),
			proposal.getProposalId(), actionId);
	}

}