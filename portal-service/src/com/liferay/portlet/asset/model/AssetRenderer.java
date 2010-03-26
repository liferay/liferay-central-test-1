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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.security.permission.PermissionChecker;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="AssetRenderer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public interface AssetRenderer {

	public static final String TEMPLATE_ABSTRACT = "abstract";

	public static final String TEMPLATE_FULL_CONTENT = "full_content";

	public String[] getAvailableLocales() throws Exception;

	public long getClassPK();

	public String getDiscussionPath();

	public long getGroupId();

	public String getSummary();

	public String getTitle();

	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception;

	public PortletURL getURLExport(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception;

	public String getUrlTitle();

	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception;

	public long getUserId();

	public String getViewInContextMessage();

	public boolean hasEditPermission(PermissionChecker permissionChecker);

	public boolean hasViewPermission(PermissionChecker permissionChecker);

	public boolean isConvertible();

	public boolean isLocalizable();

	public boolean isPrintable();

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception;

}