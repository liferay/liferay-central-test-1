/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.StagedGroupedModel;
import com.liferay.portal.model.StagedModel;

import java.io.Serializable;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class ExportImportPathUtil {

	public String getExpandoPath(String path) {
		if (!Validator.isFilePath(path, false)) {
			throw new IllegalArgumentException(
				path + " is located outside of the lar");
		}

		int pos = path.lastIndexOf(".xml");

		if (pos == -1) {
			throw new IllegalArgumentException(
				path + " does not end with .xml");
		}

		return path.substring(0, pos).concat("-expando").concat(
			path.substring(pos));
	}

	public String getLayoutPath(long layoutId) {
		return getRootPath() + ROOT_PATH_LAYOUTS + layoutId;
	}

	public static String getPath(
		PortletDataContext portletDataContext, String className, long classPK) {

		return getPath(portletDataContext, className, classPK, null);
	}

	public static String getPath(
		PortletDataContext portletDataContext, String className, long classPK,
		String dependentFileName) {

		return getPath(
			_PATH_PREFIX_GROUP, portletDataContext.getSourceGroupId(),
			className, classPK, dependentFileName);
	}

	public static String getPath(StagedModel stagedModel) {
		return getPath(stagedModel, null);
	}

	public static String getPath(
		StagedModel stagedModel, String dependentFileName) {

		if (stagedModel instanceof StagedGroupedModel) {
			StagedGroupedModel stagedGroupedModel =
				(StagedGroupedModel)stagedModel;

			return getPath(
				_PATH_PREFIX_GROUP, stagedGroupedModel.getGroupId(),
				stagedModel.getModelClassName(), stagedModel.getPrimaryKeyObj(),
				dependentFileName);
		}
		else {
			return getPath(
				_PATH_PREFIX_COMPANY, stagedModel.getCompanyId(),
				stagedModel.getModelClassName(), stagedModel.getPrimaryKeyObj(),
				dependentFileName);
		}
	}

	protected static String getPath(
			String pathPrefix, long pathPrimaryKey, String className,
			Serializable primaryKeyObj,
		String dependentFileName) {

		StringBundler sb = new StringBundler(11);

		sb.append(StringPool.FORWARD_SLASH);
		sb.append(pathPrefix);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(pathPrimaryKey);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(className);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(primaryKeyObj.toString());

		if (dependentFileName == null) {
			sb.append(".xml");
		}
		else {
			sb.append(StringPool.FORWARD_SLASH);
			sb.append(dependentFileName);
		}

		return sb.toString();
	}

	public String getPortletPath(String portletId) {
		return getRootPath() + ROOT_PATH_PORTLETS + portletId;
	}

	public String getRootPath() {
		return ROOT_PATH_GROUPS + getScopeGroupId();
	}

	public String getSourceLayoutPath(long layoutId) {
		return getSourceRootPath() + ROOT_PATH_LAYOUTS + layoutId;
	}

	public String getSourcePortletPath(String portletId) {
		return getSourceRootPath() + ROOT_PATH_PORTLETS + portletId;
	}

	public String getSourceRootPath() {
		return ROOT_PATH_GROUPS + getSourceGroupId();
	}

	public static final String ROOT_PATH_GROUPS = "/groups/";

	public static final String ROOT_PATH_LAYOUTS = "/layouts/";

	public static final String ROOT_PATH_PORTLETS = "/portlets/";

	private static final String _PATH_PREFIX_COMPANY = "company";

	private static final String _PATH_PREFIX_GROUP = "group";

}