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

package com.liferay.portal.security.permission;

/**
 * @author Raymond Augé
 */
public interface InlineSQLHelper {

	public boolean isEnabled();

	public boolean isEnabled(long groupId);

	public boolean isEnabled(long[] groupIds);

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField);

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long groupId);

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long groupId, String bridgeJoin);

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long[] groupIds);

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long[] groupIds, String bridgeJoin);

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		String bridgeJoin);

}