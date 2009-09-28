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

package com.liferay.portlet.workflowadmin.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.service.WorkflowLinkLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditLinkAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class EditLinkAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			updateWorkflowLink(actionRequest);

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof WorkflowException) {
				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.workflow_admin.error");
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

		return mapping.findForward(getForward(
			renderRequest, "portlet.workflow_admin.view"));
	}

	protected void updateWorkflowLink(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Enumeration<String> enu = actionRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (!name.startsWith(_PREFIX)) {
				continue;
			}

			String workflowDefinitionName = ParamUtil.getString(
				actionRequest, name);
			long classNameId = GetterUtil.getLong(
				name.substring(_PREFIX.length(), name.length()));

			WorkflowLinkLocalServiceUtil.updateWorkflowLink(
				themeDisplay.getUserId(), themeDisplay.getCompanyId(), 0,
				classNameId, workflowDefinitionName);
		}
	}

	private static final String _PREFIX = "workflowDefinitionName@";

}