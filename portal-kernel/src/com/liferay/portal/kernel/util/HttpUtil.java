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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.bean.BeanLocatorUtil;

import java.io.IOException;

import java.net.URL;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <a href="HttpUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class HttpUtil {

	public static String addParameter(String url, String name, boolean value) {
		return getHttp().addParameter(url, name, value);
	}

	public static String addParameter(String url, String name, double value) {
		return getHttp().addParameter(url, name, value);
	}

	public static String addParameter(String url, String name, int value) {
		return getHttp().addParameter(url, name, value);
	}

	public static String addParameter(String url, String name, long value) {
		return getHttp().addParameter(url, name, value);
	}

	public static String addParameter(String url, String name, short value) {
		return getHttp().addParameter(url, name, value);
	}

	public static String addParameter(String url, String name, String value) {
		return getHttp().addParameter(url, name, value);
	}

	public static String decodeURL(String url) {
		return getHttp().decodeURL(url);
	}

	public static String decodeURL(String url, boolean unescapeSpace) {
		return getHttp().decodeURL(url, unescapeSpace);
	}

	public static String encodeURL(String url) {
		return getHttp().encodeURL(url);
	}

	public static String encodeURL(String url, boolean escapeSpaces) {
		return getHttp().encodeURL(url, escapeSpaces);
	}

	public static String getCompleteURL(HttpServletRequest req) {
		return getHttp().getCompleteURL(req);
	}

	public static String getDomain(String url) {
		return getHttp().getDomain(url);
	}

	public static Http getHttp() {
		return _getUtil()._http;
	}

	public static String getParameter(String url, String name) {
		return getHttp().getParameter(url, name);
	}

	public static String getParameter(
		String url, String name, boolean escaped) {

		return getHttp().getParameter(url, name, escaped);
	}

	public static Map getParameterMap(String queryString) {
		return getHttp().getParameterMap(queryString);
	}

	public static String getProtocol(boolean secure) {
		return getHttp().getProtocol(secure);
	}

	public static String getProtocol(String url) {
		return getHttp().getProtocol(url);
	}

	public static String getProtocol(HttpServletRequest req) {
		return getHttp().getProtocol(req);
	}

	public static String getProtocol(ActionRequest req) {
		return getHttp().getProtocol(req);
	}

	public static String getProtocol(RenderRequest req) {
		return getHttp().getProtocol(req);
	}

	public static String getQueryString(String url) {
		return getHttp().getQueryString(url);
	}

	public static String getRequestURL(HttpServletRequest req) {
		return getHttp().getRequestURL(req);
	}

	public static boolean hasProxyConfig() {
		return getHttp().hasProxyConfig();
	}

	public static boolean isNonProxyHost(String host) {
		return getHttp().isNonProxyHost(host);
	}

	public static boolean isProxyHost(String host) {
		return getHttp().isProxyHost(host);
	}

	public static Map parameterMapFromString(String queryString) {
		return getHttp().parameterMapFromString(queryString);
	}

	public static String parameterMapToString(Map parameterMap) {
		return getHttp().parameterMapToString(parameterMap);
	}

	public static String parameterMapToString(
		Map parameterMap, boolean addQuestion) {

		return getHttp().parameterMapToString(parameterMap, addQuestion);
	}

	public static String protocolize(String url, boolean secure) {
		return getHttp().protocolize(url, secure);
	}

	public static String protocolize(String url, HttpServletRequest req) {
		return getHttp().protocolize(url, req);
	}

	public static String protocolize(String url, ActionRequest req) {
		return getHttp().protocolize(url, req);
	}

	public static String protocolize(String url, RenderRequest req) {
		return getHttp().protocolize(url, req);
	}

	public static String removeParameter(String url, String name) {
		return getHttp().removeParameter(url, name);
	}

	public static String removeProtocol(String url) {
		return getHttp().removeProtocol(url);
	}

	public void setHttp(Http http) {
		_http = http;
	}

	public static void submit(String location) throws IOException {
		getHttp().submit(location);
	}

	public static void submit(String location, Cookie[] cookies)
		throws IOException {

		getHttp().submit(location, cookies);
	}

	public static void submit(String location, boolean post)
		throws IOException {

		getHttp().submit(location, post);
	}

	public static void submit(String location, Cookie[] cookies, boolean post)
		throws IOException {

		getHttp().submit(location, cookies, post);
	}

	public static void submit(
			String location, Cookie[] cookies, Map parts, boolean post)
		throws IOException {

		getHttp().submit(location, cookies, parts, post);
	}

	public static byte[] URLtoByteArray(String location) throws IOException {
		return getHttp().URLtoByteArray(location);
	}

	public static byte[] URLtoByteArray(String location, Cookie[] cookies)
		throws IOException {

		return getHttp().URLtoByteArray(location, cookies);
	}

	public static byte[] URLtoByteArray(String location, boolean post)
		throws IOException {

		return getHttp().URLtoByteArray(location, post);
	}

	public static byte[] URLtoByteArray(
			String location, Cookie[] cookies, boolean post)
		throws IOException {

		return getHttp().URLtoByteArray(location, cookies, post);
	}

	public static byte[] URLtoByteArray(
			String location, Cookie[] cookies, Map parts, boolean post)
		throws IOException {

		return getHttp().URLtoByteArray(location, cookies, parts, post);
	}

	public static String URLtoString(String location) throws IOException {
		return getHttp().URLtoString(location);
	}

	public static String URLtoString(String location, Cookie[] cookies)
		throws IOException {

		 return getHttp().URLtoString(location, cookies);
	}

	public static String URLtoString(String location, boolean post)
		throws IOException {

		return getHttp().URLtoString(location, post);
	}

	public static String URLtoString(
			String location, Cookie[] cookies, boolean post)
		throws IOException {

		return getHttp().URLtoString(location, cookies, post);
	}

	public static String URLtoString(
			String location, Cookie[] cookies, Map parts, boolean post)
		throws IOException {

		return getHttp().URLtoString(location, cookies, parts, post);
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
	public static String URLtoString(URL url) throws IOException {
		return getHttp().URLtoString(url);
	}

	private static HttpUtil _getUtil() {
		if (_util == null) {
			_util = (HttpUtil)BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = HttpUtil.class.getName();

	private static HttpUtil _util;

	private Http _http;

}