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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletSession;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.portlet.PortletContext;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <a href="PortletSessionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletSessionImpl implements LiferayPortletSession {

	public static final String getPortletScope(String portletName, long plid) {
		String portletScope =
			_PORTLET_SCOPE_NAMESPACE + portletName + _LAYOUT_SEPARATOR +
				plid;

		return portletScope;
	}

	public static final String getPortletScopeName(
		String portletName, long plid, String name) {

		String portletScopeName =
			getPortletScope(portletName, plid) + StringPool.QUESTION + name;

		return portletScopeName;
	}

	public PortletSessionImpl(
		HttpServletRequest req, String portletName, PortletContext ctx,
		String portalSessionId, long plid) {

		_req = req;
		_portletName = portletName;
		_ctx = ctx;
		_creationTime = System.currentTimeMillis();
		_lastAccessedTime = _creationTime;
		_interval = getHttpSession().getMaxInactiveInterval();
		_new = true;
		_invalid = false;
		_portalSessionId = portalSessionId;
		_plid = plid;
	}

	public Object getAttribute(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (_invalid) {
			throw new IllegalStateException();
		}

		return getAttribute(name, PortletSession.PORTLET_SCOPE);
	}

	public Object getAttribute(String name, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (_invalid) {
			throw new IllegalStateException();
		}

		if (scope == PortletSession.PORTLET_SCOPE) {
			return getHttpSession().getAttribute(_getPortletScopeName(name));
		}
		else {
			return getHttpSession().getAttribute(name);
		}
	}

	public Map<String, Object> getAttributeMap() {
		return getAttributeMap(PortletSession.PORTLET_SCOPE);
	}

	public Map<String, Object> getAttributeMap(int scope) {
		Map<String, Object> map = new HashMap<String, Object>();

		Enumeration<String> enu = getAttributeNames(scope);

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			Object value = getAttribute(name);

			map.put(name, value);
		}

		return map;
	}

	public Enumeration<String> getAttributeNames() {
		if (_invalid) {
			throw new IllegalStateException();
		}

		return getAttributeNames(PortletSession.PORTLET_SCOPE);
	}

	public Enumeration<String> getAttributeNames(int scope) {
		if (_invalid) {
			throw new IllegalStateException();
		}

		if (scope == PortletSession.PORTLET_SCOPE) {
			List<String> attributeNames = new ArrayList<String>();

			String portletScope = getPortletScope(_portletName, _plid);

			Enumeration<String> enu = getHttpSession().getAttributeNames();

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				StringTokenizer st = new StringTokenizer(
					name, StringPool.QUESTION);

				if (st.countTokens() == 2) {
					if (st.nextToken().equals(portletScope)) {
						attributeNames.add(st.nextToken());
					}
				}
			}

			return Collections.enumeration(attributeNames);
		}
		else {
			return getHttpSession().getAttributeNames();
		}
	}

	public long getCreationTime() {
		if (_invalid) {
			throw new IllegalStateException();
		}

		return _creationTime;
	}

	public HttpSession getHttpSession() {
		if (_ses == null) {
			return _req.getSession();
		}
		else {
			return _ses;
		}
	}

	public String getId() {
		return getHttpSession().getId();
	}

	public long getLastAccessedTime() {
		return _lastAccessedTime;
	}

	public int getMaxInactiveInterval() {
		return _interval;
	}

	public String getPortalSessionId() {
		return _portalSessionId;
	}

	public PortletContext getPortletContext() {
		return _ctx;
	}

	public void invalidate() {
		if (_invalid) {
			throw new IllegalStateException();
		}

		getHttpSession().invalidate();

		_invalid = true;
	}

	public boolean isNew() {
		if (_invalid) {
			throw new IllegalStateException();
		}

		return _new;
	}

	public boolean isValid() {
		return !_invalid;
	}

	public void removeAttribute(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (_invalid) {
			throw new IllegalStateException();
		}

		removeAttribute(name, PortletSession.PORTLET_SCOPE);
	}

	public void removeAttribute(String name, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (_invalid) {
			throw new IllegalStateException();
		}

		if (scope == PortletSession.PORTLET_SCOPE) {
			getHttpSession().removeAttribute(_getPortletScopeName(name));
		}
		else {
			getHttpSession().removeAttribute(name);
		}
	}

	public void setAttribute(String name, Object value) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (_invalid) {
			throw new IllegalStateException();
		}

		setAttribute(name, value, PortletSession.PORTLET_SCOPE);
	}

	public void setAttribute(String name, Object value, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (_invalid) {
			throw new IllegalStateException();
		}

		if (scope == PortletSession.PORTLET_SCOPE) {
			getHttpSession().setAttribute(_getPortletScopeName(name), value);
		}
		else {
			getHttpSession().setAttribute(name, value);
		}
	}

	public void setHttpSession(HttpSession ses) {
		_ses = ses;
	}

	public void setLastAccessedTime(long lastAccessedTime) {
		_lastAccessedTime = lastAccessedTime;
		_new = false;
	}

	public void setMaxInactiveInterval(int interval) {
		_interval = interval;
	}

	private String _getPortletScopeName(String name) {
		return getPortletScopeName(_portletName, _plid, name);
	}

 	private static final String _PORTLET_SCOPE_NAMESPACE = "javax.portlet.p.";

 	private static final String _LAYOUT_SEPARATOR = "_LAYOUT_";

	private HttpServletRequest _req;
	private HttpSession _ses;
	private String _portletName;
	private PortletContext _ctx;
	private long _creationTime;
	private long _lastAccessedTime;
	private int _interval;
	private boolean _new;
	private boolean _invalid;
	private String _portalSessionId;
	private long _plid;

}