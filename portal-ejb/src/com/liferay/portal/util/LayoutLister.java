/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <a href="LayoutLister.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutLister {

	public LayoutView getLayoutView(
			String ownerId, String rootNodeName, Locale locale)
		throws PortalException, SystemException {

		_ownerId = ownerId;
		_locale = locale;
		_nodeId = 1;

		_list = new ArrayList();
		_list.add("1|0|0|-1|" + rootNodeName + "|0");

		_createList(LayoutImpl.DEFAULT_PARENT_LAYOUT_ID, _nodeId, 0);

		return new LayoutView(_list, _depth);
	}

	private void _createList(
			String parentLayoutId, int parentId, int depth)
		throws PortalException, SystemException {

		List layouts = LayoutLocalServiceUtil.getLayouts(
			_ownerId, parentLayoutId);

		for (int i = 0; i < layouts.size(); i++) {
			Layout layout = (Layout)layouts.get(i);

			if (i == 0) {
				depth++;

				if (depth > _depth) {
					_depth = depth;
				}
			}

			StringBuffer sb = new StringBuffer();

			sb.append(++_nodeId).append("|");
			sb.append(parentId).append("|");

			if ((i + 1) == layouts.size()) {
				sb.append("1");
			}
			else {
				sb.append("0");
			}

			sb.append("|");
			sb.append(layout.getPlid()).append("|");
			sb.append(layout.getName(_locale)).append("|");
			//sb.append("9");
			sb.append("11");
			sb.append("|");
			sb.append(depth);

			_list.add(sb.toString());

			_createList(layout.getLayoutId(), _nodeId, depth);
		}
	}

	private String _ownerId;
	private Locale _locale;
	private int _nodeId;
	private List _list;
	private int _depth;

}