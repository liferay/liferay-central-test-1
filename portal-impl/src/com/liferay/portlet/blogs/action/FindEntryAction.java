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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FindEntryAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			long plid = ParamUtil.getLong(request, "p_l_id");
			String redirect = ParamUtil.getString(request, "redirect");
			long entryId = ParamUtil.getLong(request, "entryId");
			boolean showAllEntries = ParamUtil.getBoolean(
				request, "showAllEntries");

			plid = getPlid(plid, entryId);

			String urlTitle = getUrlTitle(entryId);

			PortletURL portletURL = new PortletURLImpl(
				request, PortletKeys.BLOGS, plid, PortletRequest.RENDER_PHASE);

			portletURL.setWindowState(WindowState.NORMAL);
			portletURL.setPortletMode(PortletMode.VIEW);

			if (Validator.isNotNull(redirect)) {
				portletURL.setParameter("redirect", redirect);
			}

			if (showAllEntries) {
				portletURL.setParameter("struts_action", "/blogs/view");
			}
			else {
				portletURL.setParameter("struts_action", "/blogs/view_entry");

				if (Validator.isNotNull(urlTitle)) {
					portletURL.setParameter("urlTitle", urlTitle);
				}
				else {
					portletURL.setParameter("entryId", String.valueOf(entryId));
				}
			}

			response.sendRedirect(portletURL.toString());

			return null;
		}
		catch (Exception e) {
			String noSuchEntryRedirect = ParamUtil.getString(
				request, "noSuchEntryRedirect");

			if (e.getClass().equals(NoSuchLayoutException.class) &&
				Validator.isNotNull(noSuchEntryRedirect)) {

				response.sendRedirect(noSuchEntryRedirect);
			}
			else {
				PortalUtil.sendError(e, request, response);
			}

			return null;
		}
	}

	protected long getPlid(long plid, long entryId) throws Exception {
		if (plid != LayoutConstants.DEFAULT_PLID) {
			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				if (layoutTypePortlet.hasPortletId(PortletKeys.BLOGS)) {
					return plid;
				}
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(entryId);

		plid = PortalUtil.getPlidFromPortletId(
			entry.getGroupId(), PortletKeys.BLOGS);

		if (plid != LayoutConstants.DEFAULT_PLID) {
			return plid;
		}
		else {
			throw new NoSuchLayoutException(
				"No page was found with the Blogs portlet.");
		}
	}

	protected String getUrlTitle(long entryId) {
		String urlTitle = StringPool.BLANK;

		try {
			BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(entryId);

			urlTitle = entry.getUrlTitle();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}
		}

		return urlTitle;
	}

	private static Log _log = LogFactoryUtil.getLog(FindEntryAction.class);

}