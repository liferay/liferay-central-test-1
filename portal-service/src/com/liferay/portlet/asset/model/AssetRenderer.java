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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import javax.portlet.PortletRequest;
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
			PortletRequest portletRequest,
			LiferayPortletResponse portletResponse)
		throws Exception;

	public PortletURL getURLExport(
			PortletRequest portletRequest,
			LiferayPortletResponse portletResponse)
		throws Exception;

	public String getUrlTitle();

	public String getURLViewInContext(
			PortletRequest portletRequest,
			LiferayPortletResponse portletResponse, String noSuchEntryRedirect)
		throws Exception;

	public long getUserId();

	public String getViewInContextMessage();

	public boolean isConvertible();

	public boolean isLocalizable();

	public boolean isPrintable();

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception;

}