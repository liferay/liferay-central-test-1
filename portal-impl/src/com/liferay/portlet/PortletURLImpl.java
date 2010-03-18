/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.social.util.FacebookUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;

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

/**
 * <a href="PortletURLImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class PortletURLImpl
	implements LiferayPortletURL, PortletURL, ResourceURL, Serializable {

	public PortletURLImpl(
		HttpServletRequest request, String portletId, long plid,
		String lifecycle) {

		_request = request;
		_portletId = portletId;
		_plid = plid;
		_lifecycle = lifecycle;
		_parametersIncludedInPath = new LinkedHashSet<String>();
		_params = new LinkedHashMap<String, String[]>();
		_removePublicRenderParameters = new LinkedHashMap<String, String[]>();
		_secure = request.isSecure();
		_wsrp = ParamUtil.getBoolean(request, "wsrp");

		Portlet portlet = getPortlet();

		if (portlet != null) {
			PortletApp portletApp = portlet.getPortletApp();

			_escapeXml = MapUtil.getBoolean(
				portletApp.getContainerRuntimeOptions(),
				LiferayPortletConfig.RUNTIME_OPTION_ESCAPE_XML,
				PropsValues.PORTLET_URL_ESCAPE_XML);
		}
	}

	public PortletURLImpl(
		PortletRequestImpl portletRequestImpl, String portletId, long plid,
		String lifecycle) {

		this(
			portletRequestImpl.getHttpServletRequest(), portletId, plid,
			lifecycle);

		_portletRequest = portletRequestImpl;
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

	public String getNamespace() {
		if (_namespace == null) {
			_namespace = PortalUtil.getPortletNamespace(_portletId);
		}

		return _namespace;
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

	public boolean isCopyCurrentPublicRenderParameters() {
		return _copyCurrentPublicRenderParameters;
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

		PublicRenderParameter publicRenderParameter =
			_portlet.getPublicRenderParameter(name);

		if (publicRenderParameter == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Public parameter " + name + "does not exist");
			}

			return;
		}

		QName qName = publicRenderParameter.getQName();

		_removePublicRenderParameters.put(
			PortletQNameUtil.getRemovePublicRenderParameterName(qName),
			new String[] {"1"});
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

	public void setCopyCurrentPublicRenderParameters(
		boolean copyCurrentPublicRenderParameters) {

		_copyCurrentPublicRenderParameters = copyCurrentPublicRenderParameters;
	}

	public void setCopyCurrentRenderParameters(
		boolean copyCurrentRenderParameters) {

		_copyCurrentRenderParameters = copyCurrentRenderParameters;
	}

	public void setDoAsGroupId(long doAsGroupId) {
		_doAsGroupId = doAsGroupId;

		// Clear cache

		_toString = null;
	}

	public void setDoAsUserId(long doAsUserId) {
		_doAsUserId = doAsUserId;

		// Clear cache

		_toString = null;
	}

	public void setDoAsUserLanguageId(String doAsUserLanguageId) {
		_doAsUserLanguageId = doAsUserLanguageId;

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

	public void setPlid(long plid) {
		_plid = plid;

		// Clear cache

		_toString = null;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;

		// Clear cache

		_toString = null;
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

	public void setPortletMode(String portletMode) throws PortletModeException {
		setPortletMode(PortletModeFactory.getPortletMode(portletMode));
	}

	public void setProperty(String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
	}

	public void setRefererPlid(long refererPlid) {
		_refererPlid = refererPlid;

		// Clear cache

		_toString = null;
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

	public String toString() {
		if (_toString != null) {
			return _toString;
		}

		if (_wsrp) {
			_toString = generateWSRPToString();
		}
		else {
			_toString = generateToString();
		}

		return _toString;
	}

	public void write(Writer writer) throws IOException {
		write(writer, _escapeXml);
	}

	public void write(Writer writer, boolean escapeXml) throws IOException {
		String toString = toString();

		if (escapeXml && !_escapeXml) {
			toString = HtmlUtil.escape(toString);
		}

		writer.write(toString);
	}

	protected void addAuthToken(StringBundler sb, Key key) {
		if (!PropsValues.AUTH_TOKEN_CHECK_ENABLED ||
			!_lifecycle.equals(PortletRequest.ACTION_PHASE)) {

			return;
		}

		sb.append("p_auth");
		sb.append(StringPool.EQUAL);
		sb.append(processValue(key, AuthTokenUtil.getToken(_request)));
		sb.append(StringPool.AMPERSAND);
	}

	protected String generateToString() {
		StringBundler sb = new StringBundler(32);

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

			addAuthToken(sb, key);

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

			addAuthToken(sb, key);
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

		String doAsUserLanguageId = _doAsUserLanguageId;

		if (Validator.isNull(doAsUserLanguageId)) {
			doAsUserLanguageId = themeDisplay.getDoAsUserLanguageId();
		}

		if (Validator.isNotNull(doAsUserLanguageId)) {
			sb.append("doAsUserLanguageId");
			sb.append(StringPool.EQUAL);
			sb.append(processValue(key, doAsUserLanguageId));
			sb.append(StringPool.AMPERSAND);
		}

		long doAsGroupId = _doAsGroupId;

		if (doAsGroupId <= 0) {
			doAsGroupId = themeDisplay.getDoAsGroupId();
		}

		if (doAsGroupId > 0) {
			sb.append("doAsGroupId");
			sb.append(StringPool.EQUAL);
			sb.append(processValue(key, doAsGroupId));
			sb.append(StringPool.AMPERSAND);
		}

		long refererPlid = _refererPlid;

		if (refererPlid <= 0) {
			refererPlid = themeDisplay.getRefererPlid();
		}

		if (refererPlid > 0) {
			sb.append("refererPlid");
			sb.append(StringPool.EQUAL);
			sb.append(processValue(key, refererPlid));
			sb.append(StringPool.AMPERSAND);
		}

		Iterator<Map.Entry<String, String[]>> itr =
			_removePublicRenderParameters.entrySet().iterator();

		while (itr.hasNext()) {
			String lastString = sb.stringAt(sb.index() - 1);

			if (lastString.charAt(lastString.length() - 1) !=
					CharPool.AMPERSAND) {

				sb.append(StringPool.AMPERSAND);
			}

			Map.Entry<String, String[]> entry = itr.next();

			sb.append(entry.getKey());
			sb.append(StringPool.EQUAL);
			sb.append(processValue(key, entry.getValue()[0]));
			sb.append(StringPool.AMPERSAND);
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
					_params.remove(name);
				}
				else {
					newValues = ArrayUtil.append(newValues, oldValues);

					_params.put(name, newValues);
				}
			}
		}

		itr = _params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, String[]> entry = itr.next();

			String name = entry.getKey();
			String[] values = entry.getValue();

			String identifier = null;

			if (portlet != null) {
				PublicRenderParameter publicRenderParameter =
					portlet.getPublicRenderParameter(name);

				if (publicRenderParameter != null) {
					QName qName = publicRenderParameter.getQName();

					if (_copyCurrentPublicRenderParameters) {
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

					identifier = name;

					name = PortletQNameUtil.getPublicRenderParameterName(qName);

					PortletQNameUtil.setPublicRenderParameterIdentifier(
						name, identifier);
				}
			}

			// LEP-7495

			//if (isBlankValue(values)) {
			//	continue;
			//}

			for (int i = 0; i < values.length; i++) {
				String parameterName = name;

				if (identifier != null) {
					parameterName = identifier;
				}

				if (isParameterIncludedInPath(parameterName)) {
					continue;
				}

				if (!PortalUtil.isReservedParameter(name) &&
					!name.startsWith(
						PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE)) {

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

				String lastString = sb.stringAt(sb.index() - 1);

				if (lastString.charAt(lastString.length() - 1) !=
						CharPool.AMPERSAND) {

					sb.append(StringPool.AMPERSAND);
				}

				sb.append("#p_");
				sb.append(_portletId);
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
			result = HtmlUtil.escape(result);
		}

		return result;
	}

	protected String generateWSRPToString() {
		StringBundler sb = new StringBundler("wsrp_rewrite?");

		Portlet portlet = getPortlet();

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

		sb.append("wsrp-urlType");
		sb.append(StringPool.EQUAL);

		if (_lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			sb.append(processValue(key, "blockingAction"));
		}
		else if (_lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			sb.append(processValue(key, "render"));
		}
		else if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			sb.append(processValue(key, "resource"));
		}

		sb.append(StringPool.AMPERSAND);

		if (_windowState != null) {
			sb.append("wsrp-windowState");
			sb.append(StringPool.EQUAL);
			sb.append(processValue(key, "wsrp:" + _windowState.toString()));
			sb.append(StringPool.AMPERSAND);
		}

		if (_portletMode != null) {
			sb.append("wsrp-mode");
			sb.append(StringPool.EQUAL);
			sb.append(processValue(key, "wsrp:" + _portletMode.toString()));
			sb.append(StringPool.AMPERSAND);
		}

		if (_resourceID != null) {
			sb.append("wsrp-resourceID");
			sb.append(StringPool.EQUAL);
			sb.append(processValue(key, _resourceID));
			sb.append(StringPool.AMPERSAND);
		}

		if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			sb.append("wsrp-resourceCacheability");
			sb.append(StringPool.EQUAL);
			sb.append(processValue(key, _cacheability));
			sb.append(StringPool.AMPERSAND);
		}

		if (PropsValues.PORTLET_URL_ANCHOR_ENABLE) {
			if (_anchor && (_windowState != null) &&
				(!_windowState.equals(WindowState.MAXIMIZED)) &&
				(!_windowState.equals(LiferayWindowState.EXCLUSIVE)) &&
				(!_windowState.equals(LiferayWindowState.POP_UP))) {

				sb.append("wsrp-fragmentID");
				sb.append(StringPool.EQUAL);
				sb.append("#p_");
				sb.append(_portletId);
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
					_params.remove(name);
				}
				else {
					newValues = ArrayUtil.append(newValues, oldValues);

					_params.put(name, newValues);
				}
			}
		}

		StringBundler parameterSb = new StringBundler();

		Iterator<Map.Entry<String, String[]>> itr =
			_params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, String[]> entry = itr.next();

			String name = entry.getKey();
			String[] values = entry.getValue();

			String identifier = null;

			if (portlet != null) {
				PublicRenderParameter publicRenderParameter =
					portlet.getPublicRenderParameter(name);

				if (publicRenderParameter != null) {
					QName qName = publicRenderParameter.getQName();

					if (_copyCurrentPublicRenderParameters) {
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

					identifier = name;

					name = PortletQNameUtil.getPublicRenderParameterName(qName);

					PortletQNameUtil.setPublicRenderParameterIdentifier(
						name, identifier);
				}
			}

			for (int i = 0; i < values.length; i++) {
				String parameterName = name;

				if (identifier != null) {
					parameterName = identifier;
				}

				if (isParameterIncludedInPath(parameterName)) {
					continue;
				}

				if (!PortalUtil.isReservedParameter(name) &&
					!name.startsWith(
						PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE)) {

					parameterSb.append(getNamespace());
				}

				parameterSb.append(name);
				parameterSb.append(StringPool.EQUAL);
				parameterSb.append(processValue(key, values[i]));

				if ((i + 1 < values.length) || itr.hasNext()) {
					parameterSb.append(StringPool.AMPERSAND);
				}
			}
		}

		if (_encrypt) {
			parameterSb.append(StringPool.AMPERSAND + WebKeys.ENCRYPT + "=1");
		}

		sb.append("wsrp-navigationalState");
		sb.append(StringPool.EQUAL);
		sb.append(HttpUtil.encodeURL(parameterSb.toString()));

		sb.append("/wsrp_rewrite");

		return sb.toString();
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

	private static Log _log = LogFactoryUtil.getLog(PortletURLImpl.class);

	private boolean _anchor = true;
	private String _cacheability = ResourceURL.PAGE;
	private boolean _copyCurrentPublicRenderParameters;
	private boolean _copyCurrentRenderParameters;
	private long _doAsGroupId;
	private long _doAsUserId;
	private String _doAsUserLanguageId;
	private boolean _encrypt;
	private boolean _escapeXml = PropsValues.PORTLET_URL_ESCAPE_XML;
	private Layout _layout;
	private String _layoutFriendlyURL;
	private String _lifecycle;
	private String _namespace;
	private Set<String> _parametersIncludedInPath;
	private Map<String, String[]> _params;
	private long _plid;
	private Portlet _portlet;
	private String _portletId;
	private PortletMode _portletMode;
	private PortletRequest _portletRequest;
	private long _refererPlid;
	private Map<String, String[]> _removePublicRenderParameters;
	private HttpServletRequest _request;
	private String _resourceID;
	private boolean _secure;
	private String _toString;
	private WindowState _windowState;
	private boolean _wsrp;

}