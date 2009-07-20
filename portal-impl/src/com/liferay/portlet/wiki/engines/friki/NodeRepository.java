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

package com.liferay.portlet.wiki.engines.friki;

import com.efsol.friki.BasicDriver;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.stringtree.util.tract.Tract;

public class NodeRepository extends BasicDriver {

	public NodeRepository(long nodeId) {
		_nodeId = nodeId;
		_names = new HashMap<String, Boolean>();
	}

	public Iterator<String> allPageNames() {
		return _names.keySet().iterator();
	}

	public String backup(String name) {
		return name;
	}

	public boolean contains(String name) {
		boolean exists = false;

		try {
			Boolean existsObj = _names.get(name);

			if (existsObj == null) {
				if (WikiPageLocalServiceUtil.getPagesCount(
						_nodeId, name, true) > 0) {

					existsObj = Boolean.TRUE;
				}
				else {
					existsObj = Boolean.FALSE;
				}

				_names.put(name, existsObj);
			}

			exists = existsObj.booleanValue();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return exists;
	}

	public Tract get(String name) {
		return null;
	}

	public Map<String, Boolean> getTitles() {
		return _names;
	}

	public void put(String name, Tract page) {
	}

	private static Log _log = LogFactoryUtil.getLog(NodeRepository.class);

	private long _nodeId;
	private Map<String, Boolean> _names;

}