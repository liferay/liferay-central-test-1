/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.portletconfiguration.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.PermissionServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.servlet.filters.layoutcache.LayoutCacheUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditPermissionsAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EditPermissionsAction extends EditConfigurationAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals("group_permissions")) {
				updateGroupPermissions(actionRequest);
			}
			else if (cmd.equals("guest_permissions")) {
				updateGuestPermissions(actionRequest);
			}
			else if (cmd.equals("organization_permissions")) {
				updateOrganizationPermissions(actionRequest);
			}
			else if (cmd.equals("role_permissions")) {
				updateRolePermissions(actionRequest);
			}
			else if (cmd.equals("user_group_permissions")) {
				updateUserGroupPermissions(actionRequest);
			}
			else if (cmd.equals("user_permissions")) {
				updateUserPermissions(actionRequest);
			}

			String redirect = ParamUtil.getString(
				actionRequest, "permissionsRedirect");

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(
					actionRequest, "portlet.portlet_configuration.error");
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();

		String portletResource = ParamUtil.getString(
			renderRequest, "portletResource");
		String modelResource = ParamUtil.getString(
			renderRequest, "modelResource");
		String resourcePrimKey = ParamUtil.getString(
			renderRequest, "resourcePrimKey");

		String selResource = portletResource;

		if (Validator.isNotNull(modelResource)) {
			selResource = modelResource;
		}

		try {
			PermissionServiceUtil.checkPermission(
				groupId, selResource, resourcePrimKey);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			setForward(renderRequest, "portlet.portlet_configuration.error");
		}

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), portletResource);

		if (portlet != null) {
			renderResponse.setTitle(getTitle(portlet, renderRequest));
		}

		return mapping.findForward(getForward(
			renderRequest, "portlet.portlet_configuration.edit_permissions"));
	}

	protected String[] getActionIds(ActionRequest actionRequest, long roleId) {
		List<String> actionIds = new ArrayList<String>();

		Enumeration<String> enu = actionRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith(roleId + "_ACTION_")) {
				int pos = name.indexOf("_ACTION_");

				String actionId = name.substring(pos + 8);

				actionIds.add(actionId);
			}
		}

		return actionIds.toArray(new String[actionIds.size()]);
	}

	protected void updateGroupPermissions(ActionRequest actionRequest)
		throws Exception {

		Layout layout = (Layout)actionRequest.getAttribute(WebKeys.LAYOUT);

		long resourceId = ParamUtil.getLong(actionRequest, "resourceId");
		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String[] actionIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "groupIdActionIds"));

		PermissionServiceUtil.setGroupPermissions(
			groupId, actionIds, resourceId);

		if (!layout.isPrivateLayout()) {
			Resource resource =
				ResourceLocalServiceUtil.getResource(resourceId);

			if (resource.getPrimKey().startsWith(
					layout.getPlid() + PortletConstants.LAYOUT_SEPARATOR)) {

				LayoutCacheUtil.clearCache(layout.getCompanyId());
			}
		}
	}

	protected void updateGuestPermissions(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long resourceId = ParamUtil.getLong(actionRequest, "resourceId");
		String[] actionIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "guestActionIds"));

		PermissionServiceUtil.setUserPermissions(
			themeDisplay.getDefaultUserId(), themeDisplay.getScopeGroupId(),
			actionIds, resourceId);
	}

	protected void updateOrganizationPermissions(ActionRequest actionRequest)
		throws Exception {

		Layout layout = (Layout)actionRequest.getAttribute(WebKeys.LAYOUT);

		long resourceId = ParamUtil.getLong(actionRequest, "resourceId");
		long organizationId = ParamUtil.getLong(
			actionRequest, "organizationIdsPosValue");
		String[] actionIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "organizationIdActionIds"));
		//boolean organizationIntersection = ParamUtil.getBoolean(
		//	actionRequest, "organizationIntersection");

		//if (!organizationIntersection) {
			PermissionServiceUtil.setGroupPermissions(
				Organization.class.getName(), String.valueOf(organizationId),
				layout.getGroupId(), actionIds, resourceId);
		/*}
		else {
			PermissionServiceUtil.setOrgGroupPermissions(
				organizationId, layout.getGroupId(), actionIds, resourceId);
		}*/
	}

	protected void updateRolePermissions(ActionRequest actionRequest)
		throws Exception {

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			updateRolePermissions5(actionRequest);
		}
		else {
			updateRolePermissions1to4(actionRequest);
		}
	}

	protected void updateRolePermissions1to4(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long resourceId = ParamUtil.getLong(actionRequest, "resourceId");
		long roleId = ParamUtil.getLong(actionRequest, "roleIdsPosValue");
		String[] actionIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "roleIdActionIds"));

		PermissionServiceUtil.setRolePermissions(
			roleId, themeDisplay.getScopeGroupId(), actionIds, resourceId);
	}

	protected void updateRolePermissions5(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		long resourceId = ParamUtil.getLong(actionRequest, "resourceId");
		String modelResource = ParamUtil.getString(
			actionRequest, "modelResource");

		List<Role> roles = ResourceActionsUtil.getRoles(
			layout.getGroup(), modelResource);

		for (Role role : roles) {
			String[] actionIds = getActionIds(actionRequest, role.getRoleId());

			PermissionServiceUtil.setRolePermissions(
				role.getRoleId(), themeDisplay.getScopeGroupId(), actionIds,
				resourceId);
		}
	}

	protected void updateUserGroupPermissions(ActionRequest actionRequest)
		throws Exception {

		Layout layout = (Layout)actionRequest.getAttribute(WebKeys.LAYOUT);

		long resourceId = ParamUtil.getLong(actionRequest, "resourceId");
		long userGroupId = ParamUtil.getLong(
			actionRequest, "userGroupIdsPosValue");
		String[] actionIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "userGroupIdActionIds"));

		PermissionServiceUtil.setGroupPermissions(
			UserGroup.class.getName(), String.valueOf(userGroupId),
			layout.getGroupId(), actionIds, resourceId);
	}

	protected void updateUserPermissions(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long resourceId = ParamUtil.getLong(actionRequest, "resourceId");
		long userId = ParamUtil.getLong(actionRequest, "userIdsPosValue");
		String[] actionIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "userIdActionIds"));

		PermissionServiceUtil.setUserPermissions(
			userId, themeDisplay.getScopeGroupId(), actionIds, resourceId);
	}

}