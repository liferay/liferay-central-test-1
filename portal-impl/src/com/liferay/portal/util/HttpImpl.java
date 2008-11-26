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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.ByteArrayMaker;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.SystemProperties;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthPolicy;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="HttpImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class HttpImpl implements Http {

	public HttpImpl() {

		// Mimic behavior found in
		// http://java.sun.com/j2se/1.5.0/docs/guide/net/properties.html

		if (Validator.isNotNull(_NON_PROXY_HOSTS)) {
			String nonProxyHostsRegEx = _NON_PROXY_HOSTS;

			nonProxyHostsRegEx = nonProxyHostsRegEx.replaceAll(
				"\\.", "\\\\.");
			nonProxyHostsRegEx = nonProxyHostsRegEx.replaceAll(
				"\\*", ".*?");
			nonProxyHostsRegEx = nonProxyHostsRegEx.replaceAll(
				"\\|", ")|(");

			nonProxyHostsRegEx = "(" + nonProxyHostsRegEx + ")";

			_nonProxyHostsPattern = Pattern.compile(nonProxyHostsRegEx);
		}

		MultiThreadedHttpConnectionManager connectionManager =
			new MultiThreadedHttpConnectionManager();

		HttpConnectionParams params = connectionManager.getParams();

		params.setParameter(
			"maxConnectionsPerHost", new Integer(_MAX_CONNECTIONS_PER_HOST));
		params.setParameter(
			"maxTotalConnections", new Integer(_MAX_TOTAL_CONNECTIONS));
		params.setConnectionTimeout(_TIMEOUT);
		params.setSoTimeout(_TIMEOUT);

		_client.setHttpConnectionManager(connectionManager);
		_proxyClient.setHttpConnectionManager(connectionManager);

		if (hasProxyConfig() && Validator.isNotNull(_PROXY_USERNAME)) {
			if (_PROXY_AUTH_TYPE.equals("username-password")) {
				_proxyCredentials = new UsernamePasswordCredentials(
					_PROXY_USERNAME, _PROXY_PASSWORD);
			}
			else if (_PROXY_AUTH_TYPE.equals("ntlm")) {
				_proxyCredentials = new NTCredentials(
					_PROXY_USERNAME, _PROXY_PASSWORD, _PROXY_NTLM_HOST,
					_PROXY_NTLM_DOMAIN);

				List<String> authPrefs = new ArrayList<String>();

				authPrefs.add(AuthPolicy.NTLM);
				authPrefs.add(AuthPolicy.BASIC);
				authPrefs.add(AuthPolicy.DIGEST);

				_proxyClient.getParams().setParameter(
					AuthPolicy.AUTH_SCHEME_PRIORITY, authPrefs);
			}
		}
	}

	public String addParameter(String url, String name, boolean value) {
		return addParameter(url, name, String.valueOf(value));
	}

	public String addParameter(String url, String name, double value) {
		return addParameter(url, name, String.valueOf(value));
	}

	public String addParameter(String url, String name, int value) {
		return addParameter(url, name, String.valueOf(value));
	}

	public String addParameter(String url, String name, long value) {
		return addParameter(url, name, String.valueOf(value));
	}

	public String addParameter(String url, String name, short value) {
		return addParameter(url, name, String.valueOf(value));
	}

	public String addParameter(String url, String name, String value) {
		if (url == null) {
			return null;
		}

		String anchor = StringPool.BLANK;

		int pos = url.indexOf(StringPool.POUND);

		if (pos != -1) {
			anchor = url.substring(pos);
			url = url.substring(0, pos);
		}

		if (url.indexOf(StringPool.QUESTION) == -1) {
			url += StringPool.QUESTION;
		}

		if (!url.endsWith(StringPool.QUESTION) &&
			!url.endsWith(StringPool.AMPERSAND)) {

			url += StringPool.AMPERSAND;
		}

		return url + name + StringPool.EQUAL + encodeURL(value) + anchor;
	}

	public String decodeURL(String url) {
		return decodeURL(url, false);
	}

	public String decodeURL(String url, boolean unescapeSpace) {
		if (url == null) {
			return null;
		}

		try {
			url = URLDecoder.decode(url, StringPool.UTF8);

			if (unescapeSpace) {
				url = StringUtil.replace(url, "%20", StringPool.PLUS);
			}

			return url;
		}
		catch (UnsupportedEncodingException uee) {
			_log.error(uee, uee);

			return StringPool.BLANK;
		}
	}

	public String encodeURL(String url) {
		return encodeURL(url, false);
	}

	public String encodeURL(String url, boolean escapeSpaces) {
		if (url == null) {
			return null;
		}

		try {
			url = URLEncoder.encode(url, StringPool.UTF8);

			if (escapeSpaces) {
				url = StringUtil.replace(url, StringPool.PLUS, "%20");
			}

			return url;
		}
		catch (UnsupportedEncodingException uee) {
			_log.error(uee, uee);

			return StringPool.BLANK;
		}
	}

	public HttpClient getClient(HostConfiguration hostConfig) {
		if (isProxyHost(hostConfig.getHost())) {
			return _proxyClient;
		}
		else {
			return _client;
		}
	}

	public String getCompleteURL(HttpServletRequest request) {
		StringBuffer completeURL = request.getRequestURL();

		if (completeURL == null) {
			completeURL = new StringBuffer();
		}

		if (request.getQueryString() != null) {
			completeURL.append(StringPool.QUESTION);
			completeURL.append(request.getQueryString());
		}

		return completeURL.toString();
	}

	public String getDomain(String url) {
		url = removeProtocol(url);

		int pos = url.indexOf(StringPool.SLASH);

		if (pos != -1) {
			return url.substring(0, pos);
		}
		else {
			return url;
		}
	}

	public HostConfiguration getHostConfig(String location) throws IOException {
		if (_log.isDebugEnabled()) {
			_log.debug("Location is " + location);
		}

		HostConfiguration hostConfig = new HostConfiguration();

		hostConfig.setHost(new URI(location, false));

		if (isProxyHost(hostConfig.getHost())) {
			hostConfig.setProxy(_PROXY_HOST, _PROXY_PORT);
		}

		return hostConfig;
	}

	public String getParameter(String url, String name) {
		return getParameter(url, name, true);
	}

	public String getParameter(String url, String name, boolean escaped) {
		if (Validator.isNull(url) || Validator.isNull(name)) {
			return StringPool.BLANK;
		}

		String[] parts = StringUtil.split(url, StringPool.QUESTION);

		if (parts.length == 2) {
			String[] params = null;

			if (escaped) {
				params = StringUtil.split(parts[1], "&amp;");
			}
			else {
				params = StringUtil.split(parts[1], StringPool.AMPERSAND);
			}

			for (int i = 0; i < params.length; i++) {
				String[] kvp = StringUtil.split(params[i], StringPool.EQUAL);

				if ((kvp.length == 2) && kvp[0].equals(name)) {
					return kvp[1];
				}
			}
		}

		return StringPool.BLANK;
	}

	public Map<String, String[]> getParameterMap(String queryString) {
		return parameterMapFromString(queryString);
	}

	public String getProtocol(boolean secure) {
		if (!secure) {
			return Http.HTTP;
		}
		else {
			return Http.HTTPS;
		}
	}

	public String getProtocol(String url) {
		int pos = url.indexOf(Http.PROTOCOL_DELIMITER);

		if (pos != -1) {
			return url.substring(0, pos);
		}
		else {
			return Http.HTTP;
		}
	}

	public String getProtocol(HttpServletRequest request) {
		return getProtocol(request.isSecure());
	}

	public String getProtocol(ActionRequest actionRequest) {
		return getProtocol(actionRequest.isSecure());
	}

	public String getProtocol(RenderRequest renderRequest) {
		return getProtocol(renderRequest.isSecure());
	}

	public String getQueryString(String url) {
		if (Validator.isNull(url)) {
			return url;
		}

		int pos = url.indexOf(StringPool.QUESTION);

		if (pos == -1) {
			return StringPool.BLANK;
		}
		else {
			return url.substring(pos + 1, url.length());
		}
	}

	public String getRequestURL(HttpServletRequest request) {
		return request.getRequestURL().toString();
	}

	public boolean hasDomain(String url) {
		return Validator.isNotNull(getDomain(url));
	}

	public boolean hasProxyConfig() {
		if (Validator.isNotNull(_PROXY_HOST) && (_PROXY_PORT > 0)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isNonProxyHost(String host) {
		if (_nonProxyHostsPattern == null ||
			_nonProxyHostsPattern.matcher(host).matches()) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isProxyHost(String host) {
		if (hasProxyConfig() && !isNonProxyHost(host)) {
			return true;
		}
		else {
			return false;
		}
	}

	public Map<String, String[]> parameterMapFromString(String queryString) {
		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		if (Validator.isNull(queryString)) {
			return parameterMap;
		}

		Map<String, List<String>> tempParameterMap =
			new LinkedHashMap<String, List<String>>();

		StringTokenizer st = new StringTokenizer(
			queryString, StringPool.AMPERSAND);

		while (st.hasMoreTokens()) {
			String token = st.nextToken();

			if (Validator.isNotNull(token)) {
				String[] kvp = StringUtil.split(token, StringPool.EQUAL);

				String key = kvp[0];

				String value = StringPool.BLANK;

				if (kvp.length > 1) {
					value = kvp[1];
				}

				List<String> values = tempParameterMap.get(key);

				if (values == null) {
					values = new ArrayList<String>();

					tempParameterMap.put(key, values);
				}

				values.add(value);
			}
		}

		for (Map.Entry<String, List<String>> entry :
				tempParameterMap.entrySet()) {

			String key = entry.getKey();
			List<String> values = entry.getValue();

			parameterMap.put(key, values.toArray(new String[values.size()]));
		}

		return parameterMap;
	}

	public String parameterMapToString(Map<String, String[]> parameterMap) {
		return parameterMapToString(parameterMap, true);
	}

	public String parameterMapToString(
		Map<String, String[]> parameterMap, boolean addQuestion) {

		StringBuilder sb = new StringBuilder();

		if (parameterMap.size() > 0) {
			if (addQuestion) {
				sb.append(StringPool.QUESTION);
			}

			for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
				String name = entry.getKey();
				String[] values = entry.getValue();

				for (String value : values) {
					sb.append(name);
					sb.append(StringPool.EQUAL);
					sb.append(encodeURL(value));
					sb.append(StringPool.AMPERSAND);
				}
			}

			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

	public String protocolize(String url, boolean secure) {
		if (secure) {
			if (url.startsWith(Http.HTTP_WITH_SLASH)) {
				return StringUtil.replace(
					url, Http.HTTP_WITH_SLASH, Http.HTTPS_WITH_SLASH);
			}
		}
		else {
			if (url.startsWith(Http.HTTPS_WITH_SLASH)) {
				return StringUtil.replace(
					url, Http.HTTPS_WITH_SLASH, Http.HTTP_WITH_SLASH);
			}
		}

		return url;
	}

	public String protocolize(String url, HttpServletRequest request) {
		return protocolize(url, request.isSecure());
	}

	public String protocolize(String url, ActionRequest actionRequest) {
		return protocolize(url, actionRequest.isSecure());
	}

	public String protocolize(String url, RenderRequest renderRequest) {
		return protocolize(url, renderRequest.isSecure());
	}

	public String removeDomain(String url) {
		url = removeProtocol(url);

		int pos = url.indexOf(StringPool.SLASH);

		if (pos > 0) {
			return url.substring(pos);
		}
		else {
			return url;
		}
	}

	public String removeParameter(String url, String name) {
		int pos = url.indexOf(StringPool.QUESTION);

		if (pos == -1) {
			return url;
		}

		String anchor = StringPool.BLANK;

		int anchorPos = url.indexOf(StringPool.POUND);

		if (anchorPos != -1) {
			anchor = url.substring(anchorPos);
			url = url.substring(0, anchorPos);
		}

		StringBuilder sb = new StringBuilder();

		sb.append(url.substring(0, pos + 1));

		StringTokenizer st = new StringTokenizer(
			url.substring(pos + 1, url.length()), StringPool.AMPERSAND);

		while (st.hasMoreTokens()) {
			String token = st.nextToken();

			if (Validator.isNotNull(token)) {
				String[] kvp = StringUtil.split(token, StringPool.EQUAL);

				String key = kvp[0];

				String value = StringPool.BLANK;

				if (kvp.length > 1) {
					value = kvp[1];
				}

				if (!key.equals(name)) {
					sb.append(key);
					sb.append(StringPool.EQUAL);
					sb.append(value);
					sb.append(StringPool.AMPERSAND);
				}
			}
		}

		url = StringUtil.replace(
			sb.toString(), StringPool.AMPERSAND + StringPool.AMPERSAND,
			StringPool.AMPERSAND);

		if (url.endsWith(StringPool.AMPERSAND)) {
			url = url.substring(0, url.length() - 1);
		}

		if (url.endsWith(StringPool.QUESTION)) {
			url = url.substring(0, url.length() - 1);
		}

		return url + anchor;
	}

	public String removeProtocol(String url) {
		if (url.startsWith(Http.HTTP_WITH_SLASH)) {
			return url.substring(Http.HTTP_WITH_SLASH.length() , url.length());
		}
		else if (url.startsWith(Http.HTTPS_WITH_SLASH)) {
			return url.substring(Http.HTTPS_WITH_SLASH.length() , url.length());
		}
		else {
			return url;
		}
	}

	public String setParameter(String url, String name, boolean value) {
		return setParameter(url, name, String.valueOf(value));
	}

	public String setParameter(String url, String name, double value) {
		return setParameter(url, name, String.valueOf(value));
	}

	public String setParameter(String url, String name, int value) {
		return setParameter(url, name, String.valueOf(value));
	}

	public String setParameter(String url, String name, long value) {
		return setParameter(url, name, String.valueOf(value));
	}

	public String setParameter(String url, String name, short value) {
		return setParameter(url, name, String.valueOf(value));
	}

	public String setParameter(String url, String name, String value) {
		if (url == null) {
			return null;
		}

		url = removeParameter(url, name);

		return addParameter(url, name, value);
	}

	public void submit(String location) throws IOException {
		submit(location, null);
	}

	public void submit(String location, Cookie[] cookies) throws IOException {
		submit(location, cookies, false);
	}

	public void submit(String location, boolean post) throws IOException {
		submit(location, null, post);
	}

	public void submit(String location, Cookie[] cookies, boolean post)
		throws IOException {

		URLtoByteArray(location, cookies, post);
	}

	public void submit(
			String location, Cookie[] cookies, Http.Body body, boolean post)
		throws IOException {

		URLtoByteArray(location, cookies, body, post);
	}

	public void submit(
			String location, Cookie[] cookies, Map<String, String> parts,
			boolean post)
		throws IOException {

		URLtoByteArray(location, cookies, parts, post);
	}

	public byte[] URLtoByteArray(String location) throws IOException {
		return URLtoByteArray(location, null);
	}

	public byte[] URLtoByteArray(String location, Cookie[] cookies)
		throws IOException {

		return URLtoByteArray(location, cookies, false);
	}

	public byte[] URLtoByteArray(String location, boolean post)
		throws IOException {

		return URLtoByteArray(location, null, post);
	}

	public byte[] URLtoByteArray(
			String location, Cookie[] cookies, boolean post)
		throws IOException {

		return URLtoByteArray(location, cookies, null, null, post);
	}

	public byte[] URLtoByteArray(
			String location, Cookie[] cookies, Http.Body body, boolean post)
		throws IOException {

		return URLtoByteArray(location, cookies, body, null, post);
	}

	public byte[] URLtoByteArray(
			String location, Cookie[] cookies, Map<String, String> parts,
			boolean post)
		throws IOException {

		return URLtoByteArray(location, cookies, null, parts, post);
	}

	public String URLtoString(String location) throws IOException {
		return URLtoString(location, null);
	}

	public String URLtoString(String location, Cookie[] cookies)
		throws IOException {

		return URLtoString(location, cookies, false);
	}

	public String URLtoString(String location, boolean post)
		throws IOException {

		return URLtoString(location, null, post);
	}

	public String URLtoString(String location, Cookie[] cookies, boolean post)
		throws IOException {

		return new String(URLtoByteArray(location, cookies, post));
	}

	public String URLtoString(
			String location, Cookie[] cookies, Http.Body body, boolean post)
		throws IOException {

		return new String(URLtoByteArray(location, cookies, body, post));
	}

	public String URLtoString(
			String location, Cookie[] cookies, Map<String, String> parts,
			boolean post)
		throws IOException {

		return new String(URLtoByteArray(location, cookies, parts, post));
	}

	public String URLtoString(
			String location, String host, int port, String realm,
			String username, String password)
		throws IOException {

		HostConfiguration hostConfig = getHostConfig(location);

		HttpClient client = getClient(hostConfig);

		GetMethod getMethod = new GetMethod(location);

		getMethod.setDoAuthentication(true);

		HttpState state = new HttpState();

		state.setCredentials(
			new AuthScope(host, port, realm),
			new UsernamePasswordCredentials(username, password));

		proxifyState(state, hostConfig);

		try {
			client.executeMethod(hostConfig, getMethod, state);

			return getMethod.getResponseBodyAsString();
		}
		finally {
			getMethod.releaseConnection();
		}
	}

	/**
	 * This method only uses the default Commons HttpClient implementation when
	 * the URL object represents a HTTP resource. The URL object could also
	 * represent a file or some JNDI resource. In that case, the default Java
	 * implementation is used.
	 *
	 * @param		url URL object
	 * @return		A string representation of the resource referenced by the
	 *				URL object
	 * @throws		IOException
	 */
	public String URLtoString(URL url) throws IOException {
		String xml = null;

		if (url != null) {
			String protocol = url.getProtocol().toLowerCase();

			if (protocol.startsWith(Http.HTTP) ||
				protocol.startsWith(Http.HTTPS)) {

				return URLtoString(url.toString());
			}

			URLConnection con = url.openConnection();

			InputStream is = con.getInputStream();

			ByteArrayMaker bam = new ByteArrayMaker();
			byte[] bytes = new byte[512];

			for (int i = is.read(bytes, 0, 512); i != -1;
					i = is.read(bytes, 0, 512)) {

				bam.write(bytes, 0, i);
			}

			xml = new String(bam.toByteArray());

			is.close();
			bam.close();
		}

		return xml;
	}

	protected void proxifyState(HttpState state, HostConfiguration hostConfig) {
		Credentials proxyCredentials = _proxyCredentials;

		String host = hostConfig.getHost();

		if (isProxyHost(host) && (proxyCredentials != null)) {
			AuthScope scope = new AuthScope(_PROXY_HOST, _PROXY_PORT, null);

			state.setProxyCredentials(scope, proxyCredentials);
		}
	}

	protected byte[] URLtoByteArray(
			String location, Cookie[] cookies, Http.Body body,
			Map<String, String> parts, boolean post)
		throws IOException {

		byte[] bytes = null;

		HttpMethod method = null;

		try {
			if (location == null) {
				return bytes;
			}
			else if (!location.startsWith(Http.HTTP_WITH_SLASH) &&
					 !location.startsWith(Http.HTTPS_WITH_SLASH)) {

				location = Http.HTTP_WITH_SLASH + location;
			}

			HostConfiguration hostConfig = getHostConfig(location);

			HttpClient client = getClient(hostConfig);

			if (post) {
				method = new PostMethod(location);

				if (body != null) {
					RequestEntity requestEntity = new StringRequestEntity(
						body.getContent(), body.getContentType(),
						body.getCharset());

					PostMethod postMethod = (PostMethod)method;

					postMethod.setRequestEntity(requestEntity);
				}
				else if ((parts != null) && (parts.size() > 0)) {
					List<NameValuePair> nvpList =
						new ArrayList<NameValuePair>();

					for (Map.Entry<String, String> entry : parts.entrySet()) {
						String key = entry.getKey();
						String value = entry.getValue();

						if (value != null) {
							nvpList.add(new NameValuePair(key, value));
						}
					}

					NameValuePair[] nvpArray = nvpList.toArray(
						new NameValuePair[nvpList.size()]);

					PostMethod postMethod = (PostMethod)method;

					postMethod.setRequestBody(nvpArray);
				}
			}
			else {
				method = new GetMethod(location);
			}

			method.addRequestHeader(
				"Content-Type", "application/x-www-form-urlencoded");

			method.addRequestHeader(
				"User-agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");

			//method.setFollowRedirects(true);

			HttpState state = new HttpState();

			if ((cookies != null) && (cookies.length > 0)) {
				org.apache.commons.httpclient.Cookie[] commonsCookies =
					new org.apache.commons.httpclient.Cookie[0];

				for (int i = 0; i < cookies.length; i++) {
					Cookie cookie = cookies[i];

					commonsCookies[i] =
						new org.apache.commons.httpclient.Cookie(
							cookie.getDomain(), cookie.getName(),
							cookie.getValue(), cookie.getPath(),
							cookie.getMaxAge(), cookie.getSecure());
				}

				state.addCookies(commonsCookies);

				method.getParams().setCookiePolicy(
					CookiePolicy.BROWSER_COMPATIBILITY);
			}

			proxifyState(state, hostConfig);

			client.executeMethod(hostConfig, method, state);

			Header locationHeader = method.getResponseHeader("location");

			if (locationHeader != null) {
				return URLtoByteArray(locationHeader.getValue(), cookies, post);
			}

			InputStream is = method.getResponseBodyAsStream();

			if (is != null) {
				bytes = FileUtil.getBytes(is);

				is.close();
			}

			return bytes;
		}
		finally {
			try {
				if (method != null) {
					method.releaseConnection();
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private static final int _MAX_CONNECTIONS_PER_HOST = GetterUtil.getInteger(
		PropsUtil.get(HttpImpl.class.getName() + ".max.connections.per.host"),
		2);

	private static final int _MAX_TOTAL_CONNECTIONS = GetterUtil.getInteger(
		PropsUtil.get(HttpImpl.class.getName() + ".max.total.connections"),
		20);

	private static final String _PROXY_HOST = GetterUtil.getString(
		SystemProperties.get("http.proxyHost"));

	private static final int _PROXY_PORT = GetterUtil.getInteger(
		SystemProperties.get("http.proxyPort"));

	private static final String _NON_PROXY_HOSTS =
		SystemProperties.get("http.nonProxyHosts");

	private static final String _PROXY_AUTH_TYPE = GetterUtil.getString(
		PropsUtil.get(HttpImpl.class.getName() + ".proxy.auth.type"));

	private static final String _PROXY_USERNAME = GetterUtil.getString(
		PropsUtil.get(HttpImpl.class.getName() + ".proxy.username"));

	private static final String _PROXY_PASSWORD = GetterUtil.getString(
		PropsUtil.get(HttpImpl.class.getName() + ".proxy.password"));

	private static final String _PROXY_NTLM_DOMAIN = GetterUtil.getString(
		PropsUtil.get(HttpImpl.class.getName() + ".proxy.ntlm.domain"));

	private static final String _PROXY_NTLM_HOST = GetterUtil.getString(
		PropsUtil.get(HttpImpl.class.getName() + ".proxy.ntlm.host"));

	private static final int _TIMEOUT = GetterUtil.getInteger(
		PropsUtil.get(HttpImpl.class.getName() + ".timeout"), 5000);

	private static Log _log = LogFactory.getLog(HttpImpl.class);

	private HttpClient _client = new HttpClient();
	private HttpClient _proxyClient = new HttpClient();
	private Credentials _proxyCredentials;
	private Pattern _nonProxyHostsPattern;

}