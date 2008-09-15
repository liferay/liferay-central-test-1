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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.QNameUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.social.util.FacebookUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;
import com.liferay.util.MapUtil;

import com.sun.portal.container.ChannelURLType;
import com.sun.portal.portletcontainer.common.URLHelper;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

import java.security.Key;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortletURLImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class PortletURLImpl
	implements LiferayPortletURL, PortletURL, ResourceURL, Serializable {

	public PortletURLImpl(
		PortletRequestImpl portletRequestImpl, String portletId, long plid,
		String lifecycle) {

		this(
			portletRequestImpl.getHttpServletRequest(), portletId, plid,
			lifecycle);

		_portletRequest = portletRequestImpl;
	}

	public PortletURLImpl(
		HttpServletRequest request, String portletId, long plid,
		String lifecycle) {

		_request = request;
		_portletId = portletId;
		_plid = plid;
		_lifecycle = lifecycle;
		_parametersIncludedInPath = new LinkedHashSet<String>();
		_params = new LinkedHashMap<String, String[]>();
		_secure = request.isSecure();

		Portlet portlet = getPortlet();

		if (portlet != null) {
			PortletApp portletApp = portlet.getPortletApp();

			_escapeXml = MapUtil.getBoolean(
				portletApp.getContainerRuntimeOptions(),
				PortletConfigImpl.RUNTIME_OPTION_ESCAPE_XML,
				PropsValues.PORTLET_URL_ESCAPE_XML);
		}
	}

	public void addParameterIncludedInPath(String name) {
		_parametersIncludedInPath.add(name);
	}

	public void addProperty(String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
	}

	public String getCacheability() {
		return _cacheability;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _request;
	}

	public String getNamespace() {
		if (_namespace == null) {
			_namespace = PortalUtil.getPortletNamespace(_portletId);
		}

		return _namespace;
	}

	public Layout getLayout() {
		if (_layout == null) {
			try {
				if (_plid > 0) {
					_layout = LayoutLocalServiceUtil.getLayout(_plid);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Layout cannot be found for " + _plid);
				}
			}
		}

		return _layout;
	}

	public String getLayoutFriendlyURL() {
		return _layoutFriendlyURL;
	}

	public String getLifecycle() {
		return _lifecycle;
	}

	public String getParameter(String name) {
		String[] values = _params.get(name);

		if ((values != null) && (values.length > 0)) {
			return values[0];
		}
		else {
			return null;
		}
	}

	public Map<String, String[]> getParameterMap() {
		return _params;
	}

	public Set<String> getParametersIncludedInPath() {
		return _parametersIncludedInPath;
	}

	public long getPlid() {
		return _plid;
	}

	public Portlet getPortlet() {
		if (_portlet == null) {
			try {
				_portlet = PortletLocalServiceUtil.getPortletById(
					PortalUtil.getCompanyId(_request), _portletId);
			}
			catch (SystemException se) {
				_log.error(se.getMessage());
			}
		}

		return _portlet;
	}

	public String getPortletFriendlyURLPath() {
		String portletFriendlyURLPath = null;

		Portlet portlet = getPortlet();

		if (portlet != null) {
			FriendlyURLMapper mapper = portlet.getFriendlyURLMapperInstance();

			if (mapper != null) {
				portletFriendlyURLPath = mapper.buildPath(this);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Portlet friendly URL path " + portletFriendlyURLPath);
				}
			}
		}

		return portletFriendlyURLPath;
	}

	public String getPortletId() {
		return _portletId;
	}

	public PortletMode getPortletMode() {
		return _portletMode;
	}

	public PortletRequest getPortletRequest() {
		return _portletRequest;
	}

	public String getResourceID() {
		return _resourceID;
	}

	public WindowState getWindowState() {
		return _windowState;
	}

	public boolean isAnchor() {
		return _anchor;
	}

	public boolean isCopyCurrentRenderParameters() {
		return _copyCurrentRenderParameters;
	}

	public boolean isEncrypt() {
		return _encrypt;
	}

	public boolean isEscapeXml() {
		return _escapeXml;
	}

	public boolean isParameterIncludedInPath(String name) {
		if (_parametersIncludedInPath.contains(name)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isSecure() {
		return _secure;
	}

	public void removePublicRenderParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		_params.remove(name);
	}

	public void setAnchor(boolean anchor) {
		_anchor = anchor;

		// Clear cache

		_toString = null;
	}

	public void setCacheability(String cacheability) {
		if (cacheability == null) {
			throw new IllegalArgumentException("Cacheability is null");
		}

		if (!cacheability.equals(FULL) && !cacheability.equals(PORTLET) &&
			!cacheability.equals(PAGE)) {

			throw new IllegalArgumentException(
				"Cacheability " + cacheability + " is not " + FULL + ", " +
					PORTLET + ", or " + PAGE);
		}

		if (_portletRequest instanceof ResourceRequest) {
			ResourceRequest resourceRequest = (ResourceRequest)_portletRequest;

			String parentCacheability = resourceRequest.getCacheability();

			if (parentCacheability.equals(FULL)) {
				if (!cacheability.equals(FULL)) {
					throw new IllegalStateException(
						"Unable to set a weaker cacheability " + cacheability);
				}
			}
			else if (parentCacheability.equals(PORTLET)) {
				if (!cacheability.equals(FULL) &&
					!cacheability.equals(PORTLET)) {

					throw new IllegalStateException(
						"Unable to set a weaker cacheability " + cacheability);
				}
			}
		}

		_cacheability = cacheability;

		// Clear cache

		_toString = null;
	}

	public void setCopyCurrentRenderParameters(
		boolean copyCurrentRenderParameters) {

		_copyCurrentRenderParameters = copyCurrentRenderParameters;
	}

	public void setDoAsUserId(long doAsUserId) {
		_doAsUserId = doAsUserId;

		// Clear cache

		_toString = null;
	}

	public void setEncrypt(boolean encrypt) {
		_encrypt = encrypt;

		// Clear cache

		_toString = null;
	}

	public void setEscapeXml(boolean escapeXml) {
		_escapeXml = escapeXml;

		// Clear cache

		_toString = null;
	}

	public void setLifecycle(String lifecycle) {
		_lifecycle = lifecycle;

		// Clear cache

		_toString = null;
	}

	public void setParameter(String name, String value) {
		setParameter(name, value, PropsValues.PORTLET_URL_APPEND_PARAMETERS);
	}

	public void setParameter(String name, String value, boolean append) {
		if ((name == null) || (value == null)) {
			throw new IllegalArgumentException();
		}

		setParameter(name, new String[] {value}, append);
	}

	public void setParameter(String name, String[] values) {
		setParameter(name, values, PropsValues.PORTLET_URL_APPEND_PARAMETERS);
	}

	public void setParameter(String name, String[] values, boolean append) {
		if ((name == null) || (values == null)) {
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < values.length; i++) {
			if (values[i] == null) {
				throw new IllegalArgumentException();
			}
		}

		if (append && _params.containsKey(name)) {
			String[] oldValues = _params.get(name);

			String[] newValues = ArrayUtil.append(oldValues, values);

			_params.put(name, newValues);
		}
		else {
			_params.put(name, values);
		}

		// Clear cache

		_toString = null;
	}

	public void setParameters(Map<String, String[]> params) {
		if (params == null) {
			throw new IllegalArgumentException();
		}
		else {
			Map<String, String[]> newParams =
				new LinkedHashMap<String, String[]>();

			for (Map.Entry<String, String[]> entry : params.entrySet()) {
				try {
					String key = entry.getKey();
					String[] value = entry.getValue();

					if (key == null) {
						throw new IllegalArgumentException();
					}
					else if (value == null) {
						throw new IllegalArgumentException();
					}

					newParams.put(key, value);
				}
				catch (ClassCastException cce) {
					throw new IllegalArgumentException(cce);
				}
			}

			_params = newParams;
		}

		// Clear cache

		_toString = null;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;

		// Clear cache

		_toString = null;
	}

	public void setPortletMode(String portletMode) throws PortletModeException {
		setPortletMode(PortletModeFactory.getPortletMode(portletMode));
	}

	public void setPortletMode(PortletMode portletMode)
		throws PortletModeException {

		if (_portletRequest != null) {
			if (!getPortlet().hasPortletMode(
					_portletRequest.getResponseContentType(), portletMode)) {

				throw new PortletModeException(
					portletMode.toString(), portletMode);
			}
		}

		_portletMode = portletMode;

		// Clear cache

		_toString = null;
	}

	public void setProperty(String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
	}

	public void setResourceID(String resourceID) {
		_resourceID = resourceID;
	}

	public void setSecure(boolean secure) {
		_secure = secure;

		// Clear cache

		_toString = null;
	}

	public void setWindowState(String windowState) throws WindowStateException {
		setWindowState(WindowStateFactory.getWindowState(windowState));
	}

	public void setWindowState(WindowState windowState)
		throws WindowStateException {

		if (_portletRequest != null) {
			if (!_portletRequest.isWindowStateAllowed(windowState)) {
				throw new WindowStateException(
					windowState.toString(), windowState);
			}
		}

		if (LiferayWindowState.isWindowStatePreserved(
				getWindowState(), windowState)) {

			_windowState = windowState;
		}

		// Clear cache

		_toString = null;
	}

	public void setURLType(ChannelURLType urlType) {
		_urlType = urlType;

		// Clear cache

		_toString = null;
	}

	public String toString() {
		if (_toString != null) {
			return _toString;
		}

		_toString = generateToString();

		return _toString;
	}

	public void write(Writer writer) throws IOException {
		write(writer, _escapeXml);
	}

	public void write(Writer writer, boolean escapeXml) throws IOException {
		String toString = toString();

		if (escapeXml && !_escapeXml) {
			toString = URLHelper.escapeURL(toString);
		}

		writer.write(toString());
	}

	protected String generateToString() {
		StringBuilder sb = new StringBuilder();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		Portlet portlet = getPortlet();

		String portalURL = null;

		if (themeDisplay.isFacebook()) {
			portalURL =
				FacebookUtil.FACEBOOK_APPS_URL +
					themeDisplay.getFacebookCanvasPageURL();
		}
		else {
			portalURL = PortalUtil.getPortalURL(_request, _secure);
		}

		try {
			if (_layoutFriendlyURL == null) {
				Layout layout = getLayout();

				if (layout != null) {
					_layoutFriendlyURL = GetterUtil.getString(
						PortalUtil.getLayoutFriendlyURL(layout, themeDisplay));
				}
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		Key key = null;

		try {
			if (_encrypt) {
				Company company = PortalUtil.getCompany(_request);

				key = company.getKeyObj();
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		if (Validator.isNull(_layoutFriendlyURL)) {
			sb.append(portalURL);
			sb.append(themeDisplay.getPathMain());
			sb.append("/portal/layout?");

			sb.append("p_l_id");
			sb.append(StringPool.EQUAL);
			sb.append(processValue(key, _plid));
			sb.append(StringPool.AMPERSAND);
		}
		else {

			// A virtual host URL will contain the complete path. Do not append
			// the portal URL if the virtual host URL starts with "http://" or
			// "https://".

			if (!_layoutFriendlyURL.startsWith(Http.HTTP_WITH_SLASH) &&
				!_layoutFriendlyURL.startsWith(Http.HTTPS_WITH_SLASH)) {

				sb.append(portalURL);
			}

			if (!themeDisplay.isFacebook()) {
				sb.append(_layoutFriendlyURL);
			}

			String friendlyURLPath = getPortletFriendlyURLPath();

			if (Validator.isNotNull(friendlyURLPath)) {
				if (themeDisplay.isFacebook()) {
					int pos = friendlyURLPath.indexOf(StringPool.SLASH, 1);

					if (pos != -1) {
						sb.append(friendlyURLPath.substring(pos));
					}
					else {
						sb.append(friendlyURLPath);
					}
				}
				else {
					sb.append("/-");
					sb.append(friendlyURLPath);
				}

				if (_lifecycle.equals(PortletRequest.RENDER_PHASE)) {
					addParameterIncludedInPath("p_p_lifecycle");
				}

				//if ((_windowState != null) &&
				//	_windowState.equals(WindowState.MAXIMIZED)) {

					addParameterIncludedInPath("p_p_state");
				//}

				//if ((_portletMode != null) &&
				//	_portletMode.equals(PortletMode.VIEW)) {

					addParameterIncludedInPath("p_p_mode");
				//}

				addParameterIncludedInPath("p_p_col_id");
				addParameterIncludedInPath("p_p_col_pos");
				addParameterIncludedInPath("p_p_col_count");
			}

			sb.append(StringPool.QUESTION);
		}

		if (!isParameterIncludedInPath("p_p_id")) {
			sb.append("p_p_id");
			sb.append(StringPool.EQUAL);
			sb.append(processValue(key, _portletId));
			sb.append(StringPool.AMPERSAND);
		}

		if (!isParameterIncludedInPath("p_p_lifecycle")) {
			sb.append("p_p_lifecycle");
			sb.append(StringPool.EQUAL);

			if (_lifecycle.equals(PortletRequest.ACTION_PHASE)) {
				sb.append(processValue(key, "1"));
			}
			else if (_lifecycle.equals(PortletRequest.RENDER_PHASE)) {
				sb.append(processValue(key, "0"));
			}
			else if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				sb.append(processValue(key, "2"));
			}

			sb.append(StringPool.AMPERSAND);
		}

		if (PropsValues.PORTLET_CONTAINER_IMPL_SUN &&
			!isParameterIncludedInPath("p_p_url_type")) {

			sb.append("p_p_url_type");
			sb.append(StringPool.EQUAL);

			if (ChannelURLType.ACTION.equals(_urlType)) {
				sb.append(processValue(key, "1"));
			}
			else if (ChannelURLType.RENDER.equals(_urlType)) {
				sb.append(processValue(key, "0"));
			}
			else if (ChannelURLType.RESOURCE.equals(_urlType)) {
				sb.append(processValue(key, "2"));
			}
			else {
				sb.append(processValue(key, "0"));
			}

			sb.append(StringPool.AMPERSAND);
		}

		if (!isParameterIncludedInPath("p_p_state")) {
			if (_windowState != null) {
				sb.append("p_p_state");
				sb.append(StringPool.EQUAL);
				sb.append(processValue(key, _windowState.toString()));
				sb.append(StringPool.AMPERSAND);
			}
		}

		if (!isParameterIncludedInPath("p_p_mode")) {
			if (_portletMode != null) {
				sb.append("p_p_mode");
				sb.append(StringPool.EQUAL);
				sb.append(processValue(key, _portletMode.toString()));
				sb.append(StringPool.AMPERSAND);
			}
		}

		if (!isParameterIncludedInPath("p_p_resource_id")) {
			if (_resourceID != null) {
				sb.append("p_p_resource_id");
				sb.append(StringPool.EQUAL);
				sb.append(processValue(key, _resourceID));
				sb.append(StringPool.AMPERSAND);
			}
		}

		if (!isParameterIncludedInPath("p_p_cacheability")) {
			if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				sb.append("p_p_cacheability");
				sb.append(StringPool.EQUAL);
				sb.append(processValue(key, _cacheability));
				sb.append(StringPool.AMPERSAND);
			}
		}

		if (!isParameterIncludedInPath("p_p_col_id")) {
			if (Validator.isNotNull(portletDisplay.getColumnId())) {
				sb.append("p_p_col_id");
				sb.append(StringPool.EQUAL);
				sb.append(processValue(key, portletDisplay.getColumnId()));
				sb.append(StringPool.AMPERSAND);
			}
		}

		if (!isParameterIncludedInPath("p_p_col_pos")) {
			if (portletDisplay.getColumnPos() > 0) {
				sb.append("p_p_col_pos");
				sb.append(StringPool.EQUAL);
				sb.append(processValue(key, portletDisplay.getColumnPos()));
				sb.append(StringPool.AMPERSAND);
			}
		}

		if (!isParameterIncludedInPath("p_p_col_count")) {
			if (portletDisplay.getColumnCount() > 0) {
				sb.append("p_p_col_count");
				sb.append(StringPool.EQUAL);
				sb.append(processValue(key, portletDisplay.getColumnCount()));
				sb.append(StringPool.AMPERSAND);
			}
		}

		if (_doAsUserId > 0) {
			try {
				Company company = PortalUtil.getCompany(_request);

				sb.append("doAsUserId");
				sb.append(StringPool.EQUAL);
				sb.append(processValue(company.getKeyObj(), _doAsUserId));
				sb.append(StringPool.AMPERSAND);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
		else {
			String doAsUserId = themeDisplay.getDoAsUserId();

			if (Validator.isNotNull(doAsUserId)) {
				sb.append("doAsUserId");
				sb.append(StringPool.EQUAL);
				sb.append(processValue(key, doAsUserId));
				sb.append(StringPool.AMPERSAND);
			}
		}

		if (_copyCurrentRenderParameters) {
			Enumeration<String> enu = _request.getParameterNames();

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				String[] oldValues = _request.getParameterValues(name);
				String[] newValues = _params.get(name);

				if (newValues == null) {
					_params.put(name, oldValues);
				}
				else if (isBlankValue(newValues)) {
				}
				else {
					newValues = ArrayUtil.append(newValues, oldValues);

					_params.put(name, newValues);
				}
			}
		}

		Iterator<Map.Entry<String, String[]>> itr =
			_params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, String[]> entry = itr.next();

			String name = entry.getKey();
			String[] values = entry.getValue();

			if (portlet != null) {
				PublicRenderParameter publicRenderParameter =
					portlet.getPublicRenderParameter(name);

				if (publicRenderParameter != null) {
					QName qName = publicRenderParameter.getQName();

					if (!_copyCurrentRenderParameters) {
						String[] oldValues = _request.getParameterValues(name);

						if (oldValues != null) {
							if (values == null) {
								values = oldValues;
							}
							else {
								values = ArrayUtil.append(values, oldValues);
							}
						}
					}

					String identifier = name;

					name = QNameUtil.getPublicRenderParameterName(qName);

					QNameUtil.setPublicRenderParameterIdentifier(
						name, identifier);
				}
			}

			// LEP-7495

			//if (isBlankValue(values)) {
			//	continue;
			//}

			for (int i = 0; i < values.length; i++) {
				if (isParameterIncludedInPath(name)) {
					continue;
				}

				if (!PortalUtil.isReservedParameter(name) &&
					!name.startsWith(
						QNameUtil.PUBLIC_RENDER_PARAMETER_NAMESPACE)) {

					sb.append(getNamespace());
				}

				sb.append(name);
				sb.append(StringPool.EQUAL);
				sb.append(processValue(key, values[i]));

				if ((i + 1 < values.length) || itr.hasNext()) {
					sb.append(StringPool.AMPERSAND);
				}
			}
		}

		if (_encrypt) {
			sb.append(StringPool.AMPERSAND + WebKeys.ENCRYPT + "=1");
		}

		if (PropsValues.PORTLET_URL_ANCHOR_ENABLE) {
			if (_anchor && (_windowState != null) &&
				(!_windowState.equals(WindowState.MAXIMIZED)) &&
				(!_windowState.equals(LiferayWindowState.EXCLUSIVE)) &&
				(!_windowState.equals(LiferayWindowState.POP_UP))) {

				if (sb.lastIndexOf(StringPool.AMPERSAND) != (sb.length() - 1)) {
					sb.append(StringPool.AMPERSAND);
				}

				sb.append("#p_").append(_portletId);
			}
		}

		String result = sb.toString();

		if (result.endsWith(StringPool.AMPERSAND) ||
			result.endsWith(StringPool.QUESTION)) {

			result = result.substring(0, result.length() - 1);
		}

		if (themeDisplay.isFacebook()) {

			// Facebook requires the path portion of the URL to end with a slash

			int pos = result.indexOf(StringPool.QUESTION);

			if (pos == -1) {
				if (!result.endsWith(StringPool.SLASH)) {
					result += StringPool.SLASH;
				}
			}
			else {
				String path = result.substring(0, pos);

				if (!result.endsWith(StringPool.SLASH)) {
					result = path + StringPool.SLASH + result.substring(pos);
				}
			}
		}

		if (!CookieKeys.hasSessionId(_request)) {
			result = PortalUtil.getURLWithSessionId(
				result, _request.getSession().getId());
		}

		if (_escapeXml) {
			result = URLHelper.escapeURL(result);
		}

		return result;
	}

	protected boolean isBlankValue(String[] value) {
		if ((value != null) && (value.length == 1) &&
			(value[0].equals(StringPool.BLANK))) {

			return true;
		}
		else {
			return false;
		}
	}

	protected String processValue(Key key, int value) {
		return processValue(key, String.valueOf(value));
	}

	protected String processValue(Key key, long value) {
		return processValue(key, String.valueOf(value));
	}

	protected String processValue(Key key, String value) {
		if (key == null) {
			return HttpUtil.encodeURL(value);
		}
		else {
			try {
				return HttpUtil.encodeURL(Encryptor.encrypt(key, value));
			}
			catch (EncryptorException ee) {
				return value;
			}
		}
	}

	private static Log _log = LogFactory.getLog(PortletURLImpl.class);

	private HttpServletRequest _request;
	private PortletRequest _portletRequest;
	private String _portletId;
	private Portlet _portlet;
	private String _namespace;
	private long _plid;
	private Layout _layout;
	private String _layoutFriendlyURL;
	private String _lifecycle;
	private boolean _anchor = true;
	private String _cacheability = ResourceURL.PAGE;
	private boolean _copyCurrentRenderParameters;
	private long _doAsUserId;
	private boolean _encrypt;
	private boolean _escapeXml = PropsValues.PORTLET_URL_ESCAPE_XML;
	private Set<String> _parametersIncludedInPath;
	private Map<String, String[]> _params;
	private PortletMode _portletMode;
	private String _resourceID;
	private boolean _secure;
	private WindowState _windowState;
	private ChannelURLType _urlType;
	private String _toString;

}