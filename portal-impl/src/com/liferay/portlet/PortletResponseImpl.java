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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.PortletURLGenerationListener;
import javax.portlet.ResourceURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

/**
 * <a href="PortletResponseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class PortletResponseImpl implements LiferayPortletResponse {

	public static PortletResponseImpl getPortletResponseImpl(
		PortletResponse portletResponse) {

		PortletResponseImpl portletResponseImpl = null;

		if (portletResponse instanceof PortletResponseImpl) {
			portletResponseImpl = (PortletResponseImpl)portletResponse;
		}
		else {

			// LEP-4033

			try {
				Method method = portletResponse.getClass().getMethod(
					"getResponse");

				Object obj = method.invoke(portletResponse, (Object[])null);

				portletResponseImpl = getPortletResponseImpl(
					(PortletResponse)obj);
			}
			catch (Exception e) {
				throw new RuntimeException(
					"Unable to get the HTTP servlet resuest from " +
						portletResponse.getClass().getName());
			}
		}

		return portletResponseImpl;
	}

	public void addDateHeader(String name, long date) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (_headers.containsKey(name)) {
			Long[] values = (Long[])_headers.get(name);

			ArrayUtil.append(values, new Long(date));

			_headers.put(name, values);
		}
		else {
			setDateHeader(name, date);
		}
	}

	public void addHeader(String name, String value) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (_headers.containsKey(name)) {
			String[] values = (String[])_headers.get(name);

			ArrayUtil.append(values, value);

			_headers.put(name, values);
		}
		else {
			setHeader(name, value);
		}
	}

	public void addIntHeader(String name, int value) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (_headers.containsKey(name)) {
			Integer[] values = (Integer[])_headers.get(name);

			ArrayUtil.append(values, new Integer(value));

			_headers.put(name, values);
		}
		else {
			setIntHeader(name, value);
		}
	}

	public void addProperty(Cookie cookie) {
		if (Validator.isNull(cookie)) {
			throw new IllegalArgumentException();
		}

		if (_headers.containsKey("cookies")) {
			Cookie[] cookies = (Cookie[])_headers.get("cookies");

			ArrayUtil.append(cookies, cookie);

			_headers.put("cookies", cookies);
		}
		else {
			Cookie[] cookies = new Cookie[] {cookie};

			_headers.put("cookies", cookies);
		}
	}

	public void addProperty(String key, Element element) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
	}

	public void addProperty(String key, String value) {
		if (Validator.isNull(key)) {
			throw new IllegalArgumentException();
		}

		addHeader(key, value);
	}

	public PortletURL createActionURL() {
		return createActionURL(_portletName);
	}

	public LiferayPortletURL createActionURL(String portletName) {
		return createPortletURLImpl(portletName, PortletRequest.ACTION_PHASE);
	}

	public Element createElement(String tagName) throws DOMException {
		return null;
	}

	public PortletURLImpl createPortletURLImpl(String lifecycle) {
		return createPortletURLImpl(_portletName, lifecycle);
	}

	public PortletURLImpl createPortletURLImpl(
		String portletName, String lifecycle) {

		long plid = _plid;

		try {
			Layout layout = (Layout)_portletRequestImpl.getAttribute(
				WebKeys.LAYOUT);

			PortletPreferences portletSetup =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					layout, _portletName);

			plid = GetterUtil.getLong(portletSetup.getValue(
				"portlet-setup-link-to-plid", String.valueOf(_plid)));

			if (plid <= 0) {
				plid = _plid;
			}
		}
		catch (PortalException e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}
		}
		catch (SystemException e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}
		}

		PortletURLImpl portletURLImpl = null;

		Portlet portlet = getPortlet();

		String portletURLClass = portlet.getPortletURLClass();

		if (portlet.getPortletId().equals(portletName) &&
			Validator.isNotNull(portletURLClass)) {

			try {
				Class<?> portletURLClassObj = Class.forName(portletURLClass);

				Constructor<?> constructor = portletURLClassObj.getConstructor(
					new Class[] {
						com.liferay.portlet.PortletResponseImpl.class,
						long.class, String.class
					});

				portletURLImpl = (PortletURLImpl)constructor.newInstance(
					new Object[] {this, plid, lifecycle});
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (portletURLImpl == null) {
			portletURLImpl = new PortletURLImpl(
				_portletRequestImpl, portletName, plid, lifecycle);
		}

		PortletApp portletApp = portlet.getPortletApp();

		Set<PortletURLListener> portletURLListeners =
			portletApp.getPortletURLListeners();

		for (PortletURLListener portletURLListener : portletURLListeners) {
			try {
				PortletURLGenerationListener portletURLGenerationListener =
					PortletURLListenerFactory.create(portletURLListener);

				if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
					portletURLGenerationListener.filterActionURL(
						portletURLImpl);
				}
				else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
					portletURLGenerationListener.filterRenderURL(
						portletURLImpl);
				}
				else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
					portletURLGenerationListener.filterResourceURL(
						portletURLImpl);
				}
			}
			catch (PortletException pe) {
				_log.error(pe, pe);
			}
		}

		try {
			if (!lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				portletURLImpl.setWindowState(
					_portletRequestImpl.getWindowState());
			}
		}
		catch (WindowStateException wse) {
			_log.error(wse.getMessage());
		}

		try {
			if (!lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				portletURLImpl.setPortletMode(
					_portletRequestImpl.getPortletMode());
			}
		}
		catch (PortletModeException pme) {
			_log.error(pme.getMessage());
		}

		if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			portletURLImpl.setCopyCurrentRenderParameters(true);
		}

		return portletURLImpl;
	}

	public PortletURL createRenderURL() {
		return createRenderURL(_portletName);
	}

	public LiferayPortletURL createRenderURL(String portletName) {
		return createPortletURLImpl(portletName, PortletRequest.RENDER_PHASE);
	}

	public ResourceURL createResourceURL() {
		return createResourceURL(_portletName);
	}

	public LiferayPortletURL createResourceURL(String portletName) {
		return createPortletURLImpl(portletName, PortletRequest.RESOURCE_PHASE);
	}

	public String encodeURL(String path) {
		if ((path == null) ||
			(!path.startsWith("#") && !path.startsWith("/") &&
				(path.indexOf("://") == -1))) {

			// Allow '#' as well to workaround a bug in Oracle ADF 10.1.3

			throw new IllegalArgumentException(
				"URL path must start with a '/' or include '://'");
		}

		if (_urlEncoder != null) {
			return _urlEncoder.encodeURL(_response, path);
		}
		else {
			return path;
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _portletRequestImpl.getHttpServletRequest();
	}

	public HttpServletResponse getHttpServletResponse() {
		return _response;
	}

	public abstract String getLifecycle();

	public String getNamespace() {
		if (_namespace == null) {
			_namespace = PortalUtil.getPortletNamespace(_portletName);
		}

		return _namespace;
	}

	public long getPlid() {
		return _plid;
	}

	public Portlet getPortlet() {
		if (_portlet == null) {
			try {
				_portlet = PortletLocalServiceUtil.getPortletById(
					_companyId, _portletName);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return _portlet;
	}

	public String getPortletName() {
		return _portletName;
	}

	public PortletRequestImpl getPortletRequest() {
		return _portletRequestImpl;
	}

	public Map<String, String[]> getProperties() {
		Map<String, String[]> properties =
			new LinkedHashMap<String, String[]>();

		for (Map.Entry<String, Object> entry : _headers.entrySet()) {
			String name = entry.getKey();
			Object[] values = (Object[])entry.getValue();

			String[] valuesString = new String[values.length];

			for (int i = 0; i < values.length; i++) {
				valuesString[i] = values[i].toString();
			}

			properties.put(name, valuesString);
		}

		return properties;
	}

	public URLEncoder getUrlEncoder() {
		return _urlEncoder;
	}

	public void setDateHeader(String name, long date) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (date <= 0) {
			_headers.remove(name);
		}
		else {
			_headers.put(name, new Long[] {new Long(date)});
		}
	}

	public void setHeader(String name, String value) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (Validator.isNull(value)) {
			_headers.remove(name);
		}
		else {
			_headers.put(name, new String[] {value});
		}
	}

	public void setIntHeader(String name, int value) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (value <= 0) {
			_headers.remove(name);
		}
		else {
			_headers.put(name, new Integer[] {new Integer(value)});
		}
	}

	public void setPlid(long plid) {
		_plid = plid;

		if (_plid <= 0) {
			Layout layout = (Layout)_portletRequestImpl.getAttribute(
				WebKeys.LAYOUT);

			if (layout != null) {
				_plid = layout.getPlid();
			}
		}
	}

	public void setProperty(String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		setHeader(key, value);
	}

	public void setURLEncoder(URLEncoder urlEncoder) {
		_urlEncoder = urlEncoder;
	}

	public void transferHeaders(HttpServletResponse response) {
		for (Map.Entry<String, Object> entry : _headers.entrySet()) {
			String name = entry.getKey();
			Object values = entry.getValue();

			if (values instanceof Integer[]) {
				Integer[] intValues = (Integer[])values;

				for (int value : intValues) {
					if (response.containsHeader(name)) {
						response.addIntHeader(name, value);
					}
					else {
						response.setIntHeader(name, value);
					}
				}
			}
			else if (values instanceof Long[]) {
				Long[] dateValues = (Long[])values;

				for (long value : dateValues) {
					if (response.containsHeader(name)) {
						response.addDateHeader(name, value);
					}
					else {
						response.setDateHeader(name, value);
					}
				}
			}
			else if (values instanceof String[]) {
				String[] stringValues = (String[])values;

				for (String value : stringValues) {
					if (response.containsHeader(name)) {
						response.addHeader(name, value);
					}
					else {
						response.setHeader(name, value);
					}
				}
			}
			else if (values instanceof Cookie[]) {
				Cookie[] cookies = (Cookie[])values;

				for (Cookie cookie : cookies) {
					response.addCookie(cookie);
				}
			}
		}
	}

	protected void init(
		PortletRequestImpl portletRequestImpl, HttpServletResponse response,
		String portletName, long companyId, long plid) {

		_portletRequestImpl = portletRequestImpl;
		_response = response;
		_portletName = portletName;
		_companyId = companyId;
		setPlid(plid);
	}

	protected void recycle() {
		_portletRequestImpl = null;
		_response = null;
		_portletName = null;
		_portlet = null;
		_namespace = null;
		_companyId = 0;
		_plid = 0;
		_headers.clear();
	}

	private static Log _log = LogFactory.getLog(PortletResponseImpl.class);

	private PortletRequestImpl _portletRequestImpl;
	private HttpServletResponse _response;
	private String _portletName;
	private Portlet _portlet;
	private String _namespace;
	private long _companyId;
	private long _plid;
	private URLEncoder _urlEncoder;
	private Map<String, Object> _headers = new LinkedHashMap<String, Object>();

}