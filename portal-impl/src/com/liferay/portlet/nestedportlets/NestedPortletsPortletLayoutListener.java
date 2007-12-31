/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.nestedportlets;

import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.PortletLayoutListenerException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="NestedPortletsPortletLayoutListener.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class NestedPortletsPortletLayoutListener
	implements PortletLayoutListener {

	public void onAddToLayout(String portletId, long plid)
		throws PortletLayoutListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug("Add " + portletId + " to layout " + plid);
		}
	}

	public void onMoveInLayout(String portletId, long plid)
		throws PortletLayoutListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug("Move " + portletId + " from in " + plid);
		}
	}

	public void onRemoveFromLayout(String portletId, long plid)
		throws PortletLayoutListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug("Remove " + portletId + " from layout " + plid);
		}

		try {
			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			layoutTypePortlet.removeNestedColumns(portletId);

			LayoutLocalServiceUtil.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), layout.getTypeSettings());
		}
		catch (Exception e) {
			throw new PortletLayoutListenerException(e);
		}
	}

	private static Log _log =
		LogFactory.getLog(NestedPortletsPortletLayoutListener.class);

}