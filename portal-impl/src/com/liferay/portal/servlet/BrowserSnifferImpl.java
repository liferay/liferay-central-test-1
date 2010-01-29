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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="BrowserSnifferImpl.java.html"><b><i>View Source</i></b></a>
 *
 * See http://www.zytrax.com/tech/web/browser_ids.htm for examples.
 *
 * @author Eduardo Lundgren
 * @author Nate Cavanaugh
 */
public class BrowserSnifferImpl implements BrowserSniffer {

	public boolean acceptsGzip(HttpServletRequest request) {
		String acceptEncoding = request.getHeader(HttpHeaders.ACCEPT_ENCODING);

		if ((acceptEncoding != null) &&
			(acceptEncoding.indexOf("gzip") != -1)) {

			return true;
		}
		else {
			return false;
		}
	}

	public String getBrowserId(HttpServletRequest request) {
		if (isIe(request)) {
			return BROWSER_ID_IE;
		}
		else if (isFirefox(request)) {
			return BROWSER_ID_FIREFOX;
		}
		else {
			return BROWSER_ID_OTHER;
		}
	}

	public float getMajorVersion(HttpServletRequest request) {
		float majorVersion = 0;

		String version = getVersion(request);

		Matcher matcher = _majorVersionPattern.matcher(version);

		if (matcher.find()) {
			majorVersion = GetterUtil.getFloat(matcher.group(1));
		}

		return majorVersion;
	}

	public String getRevision(HttpServletRequest request) {
		String revision = StringPool.BLANK;

		String userAgent = getUserAgent(request);

		Matcher matcher = _revisionPattern.matcher(userAgent);

		if (matcher.find()) {
			revision = matcher.group(1);
		}

		return revision;
	}

	public String getVersion(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		String version = StringPool.BLANK;

		Matcher matcher = _versionPattern.matcher(userAgent);

		if (matcher.find()) {
			version = matcher.group(1);
		}
		else if (isFirefox(request)) {
			Matcher versionFirefoxMatcher = _versionFirefoxPattern.matcher(
				userAgent);

			if (versionFirefoxMatcher.find()) {
				version = versionFirefoxMatcher.group(1);
			}
		}
		else if (isChrome(request)) {
			Matcher versionChromeMatcher = _versionChromePattern.matcher(
				userAgent);

			if (versionChromeMatcher.find()) {
				version = versionChromeMatcher.group(1);
			}
		}
		else {
			version = getRevision(request);
		}

		return version;
	}

	public boolean isAir(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.indexOf("adobeair") != -1) {
			return true;
		}

		return false;
	}

	public boolean isChrome(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.indexOf("chrome") != -1) {
			return true;
		}

		return false;
	}

	public boolean isFirefox(HttpServletRequest request) {
		if (!isMozilla(request)) {
			return false;
		}

		String userAgent = getUserAgent(request);

		Matcher matcher = _firefoxPattern.matcher(userAgent);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	public boolean isGecko(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.indexOf("gecko") != -1) {
			return true;
		}

		return false;
	}

	public boolean isIe(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if ((userAgent.indexOf("msie") != -1) &&
			(userAgent.indexOf("opera") == -1)) {

			return true;
		}

		return false;
	}

	public boolean isIphone(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.indexOf("iphone") != -1) {
			return true;
		}

		return false;
	}

	public boolean isLinux(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.indexOf("linux") != -1) {
			return true;
		}

		return false;
	}

	public boolean isMac(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.indexOf("mac") != -1) {
			return true;
		}

		return false;
	}

	public boolean isMobile(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.indexOf("mobile") != -1) {
			return true;
		}

		return false;
	}

	public boolean isMozilla(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if ((userAgent.indexOf("mozilla") != -1) &&
			(!userAgent.matches("compatible|webkit"))) {

			return true;
		}

		return false;
	}

	public boolean isOpera(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.indexOf("opera") != -1) {
			return true;
		}

		return false;
	}

	public boolean isRtf(HttpServletRequest request) {
		float majorVersion = getMajorVersion(request);

		if (isIe(request) && (majorVersion >= 5.5)) {
			return true;
		}

		if (isMozilla(request) && (majorVersion >= 1.3)) {
			return true;
		}

		if (isSafari(request) && (majorVersion >= 3.0) && !isMobile(request)) {
			return true;
		}

		return false;
	}

	public boolean isSafari(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (isWebKit(request) && (userAgent.indexOf("safari") != -1)) {
			return true;
		}

		return false;
	}

	public boolean isSun(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.indexOf("sunos") != -1) {
			return true;
		}

		return false;
	}

	public boolean isWap(HttpServletRequest request) {
		return isWapXhtml(request);
	}

	public boolean isWapXhtml(HttpServletRequest request) {
		String accept = getAccept(request);

		if (accept.indexOf("wap.xhtml") != -1) {
			return true;
		}

		return false;
	}

	public boolean isWebKit(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		Matcher matcher = _webKitPattern.matcher(userAgent);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	public boolean isWindows(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		Matcher matcher = _windowsPattern.matcher(userAgent);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	public boolean isWml(HttpServletRequest request) {
		String accept = getAccept(request);

		if (accept.indexOf("wap.wml") != -1) {
			return true;
		}

		return false;
	}

	protected String getAccept(HttpServletRequest request) {
		String accept = StringPool.BLANK;

		if (request != null) {
			String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);

			if (acceptHeader != null) {
				accept = acceptHeader.toLowerCase();
			}
		}

		return accept;
	}

	protected String getUserAgent(HttpServletRequest request) {
		String userAgent = StringPool.BLANK;

		if (request != null) {
			String userAgentHeader = request.getHeader(HttpHeaders.USER_AGENT);

			if (userAgentHeader != null) {
				userAgent = userAgentHeader.toLowerCase();
			}
		}

		return userAgent;
	}

	private static Pattern _firefoxPattern = Pattern.compile(
		"(firefox|minefield|granparadiso|bonecho|firebird|phoenix|camino)");
	private static Pattern _majorVersionPattern = Pattern.compile(
		"(\\d+[.]\\d+)");
	private static Pattern _revisionPattern = Pattern.compile(
		"(?:rv|it|ra|ie)[\\/: ]([\\d.]+)");
	private static Pattern _versionChromePattern = Pattern.compile(
		"(?:chrome)[\\/]([\\d.]+)");
	private static Pattern _versionFirefoxPattern = Pattern.compile(
		"(?:firefox|minefield)[\\/]([\\d.]+)");
	private static Pattern _versionPattern = Pattern.compile(
		"(?:version)[\\/]([\\d.]+)");
	private static Pattern _webKitPattern = Pattern.compile(
		"(khtml|applewebkit)");
	private static Pattern _windowsPattern = Pattern.compile(
		"(windows|win32|16bit)");

}