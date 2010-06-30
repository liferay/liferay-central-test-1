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

package com.liferay.portlet.messageboards.service.persistence;

/**
 * <a href="MBThreadFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface MBThreadFinder {
	public int countByG_C_S(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByS_G_C_U_S(long groupId, long[] categoryIds, long userId,
		int status) throws com.liferay.portal.kernel.exception.SystemException;

	public int filterCountByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int filterCountByG_C_S(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int filterCountByS_G_C_U_S(long groupId, long[] categoryIds,
		long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C(
		long groupId, long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C_S(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByS_G_C_U_S(
		long groupId, long[] categoryIds, long userId, int status, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByS_G_C_U_S(
		long groupId, long[] categoryIds, long userId, int status, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;
}