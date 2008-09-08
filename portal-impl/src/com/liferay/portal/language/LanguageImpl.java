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

package com.liferay.portal.language;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageWrapper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletConfigFactory;

import java.text.MessageFormat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

/**
 * <a href="LanguageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Andrius Vitkauskas
 *
 */
public class LanguageImpl implements Language {

	public String format(Locale locale, String pattern, Object argument) {
		long companyId = CompanyThreadLocal.getCompanyId();

		return format(companyId, locale, pattern, new Object[] {argument});
	}

	public String format(Locale locale, String pattern, Object[] arguments) {
		long companyId = CompanyThreadLocal.getCompanyId();

		return format(companyId, locale, pattern, arguments);
	}

	public String format(
		long companyId, Locale locale, String pattern, Object argument) {

		return format(companyId, locale, pattern, new Object[] {argument});
	}

	public String format(
		long companyId, Locale locale, String pattern, Object[] arguments) {

		String value = null;

		try {
			pattern = get(companyId, locale, pattern);

			if (arguments != null) {
				pattern = _escapePattern(pattern);

				Object[] formattedArguments = new Object[arguments.length];

				for (int i = 0; i < arguments.length; i++) {
					formattedArguments[i] = get(
						companyId, locale, arguments[i].toString());
				}

				value = MessageFormat.format(pattern, formattedArguments);
			}
			else {
				value = pattern;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return value;
	}

	public String format(
		PageContext pageContext, String pattern, Object argument) {

		return format(pageContext, pattern, new Object[] {argument}, true);
	}

	public String format(
		PageContext pageContext, String pattern, Object argument,
		boolean translateArguments) {

		return format(
			pageContext, pattern, new Object[] {argument}, translateArguments);
	}

	public String format(
		PageContext pageContext, String pattern, Object[] arguments) {

		return format(pageContext, pattern, arguments, true);
	}

	public String format(
		PageContext pageContext, String pattern, Object[] arguments,
		boolean translateArguments) {

		String value = null;

		try {
			pattern = get(pageContext, pattern);

			if (arguments != null) {
				pattern = _escapePattern(pattern);

				Object[] formattedArguments = new Object[arguments.length];

				for (int i = 0; i < arguments.length; i++) {
					if (translateArguments) {
						formattedArguments[i] =
							get(pageContext, arguments[i].toString());
					}
					else {
						formattedArguments[i] = arguments[i];
					}
				}

				value = MessageFormat.format(pattern, formattedArguments);
			}
			else {
				value = pattern;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return value;
	}

	public String format(
		PageContext pageContext, String pattern, LanguageWrapper argument) {

		return format(
			pageContext, pattern, new LanguageWrapper[] {argument}, true);
	}

	public String format(
		PageContext pageContext, String pattern, LanguageWrapper argument,
		boolean translateArguments) {

		return format(
			pageContext, pattern, new LanguageWrapper[] {argument},
			translateArguments);
	}

	public String format(
		PageContext pageContext, String pattern, LanguageWrapper[] arguments) {

		return format(pageContext, pattern, arguments, true);
	}

	public String format(
		PageContext pageContext, String pattern, LanguageWrapper[] arguments,
		boolean translateArguments) {

		String value = null;

		try {
			pattern = get(pageContext, pattern);

			if (arguments != null) {
				pattern = _escapePattern(pattern);

				Object[] formattedArguments = new Object[arguments.length];

				for (int i = 0; i < arguments.length; i++) {
					if (translateArguments) {
						formattedArguments[i] =
							arguments[i].getBefore() +
							get(pageContext, arguments[i].getText()) +
							arguments[i].getAfter();
					}
					else {
						formattedArguments[i] =
							arguments[i].getBefore() +
							arguments[i].getText() +
							arguments[i].getAfter();
					}
				}

				value = MessageFormat.format(pattern, formattedArguments);
			}
			else {
				value = pattern;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return value;
	}

	public void init() {
		_instances.clear();
	}

	public String get(Locale locale, String key) {
		long companyId = CompanyThreadLocal.getCompanyId();

		return get(companyId, locale, key, key);
	}

	public String get(long companyId, Locale locale, String key) {
		return get(companyId, locale, key, key);
	}

	public String get(
		long companyId, Locale locale, String key, String defaultValue) {

		if (key == null) {
			return null;
		}

		String value = null;

		try {
			MessageResources resources = (MessageResources)WebAppPool.get(
				String.valueOf(companyId), Globals.MESSAGES_KEY);

			if (resources == null) {

				// LEP-4505

				ResourceBundle bundle = ResourceBundle.getBundle(
					"content/Language", locale);

				value = bundle.getString(key);
			}
			else {
				value = resources.getMessage(locale, key);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	public String get(PageContext pageContext, String key) {
		return get(pageContext, key, key);
	}

	public String get(
		PageContext pageContext, String key, String defaultValue) {

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String value = null;

		if (themeDisplay != null) {
			value = get(
				themeDisplay.getCompanyId(), themeDisplay.getLocale(), key,
				defaultValue);

			// LEP-7292

			if (value != defaultValue) {
				return value;
			}
		}

		if (key == null) {
			return null;
		}

		try {
			value = TagUtils.getInstance().message(
				pageContext, null, null, key);
		}
		catch (Exception e) {
			_log.error(e);
		}

		if (value == null) {

			// LEP-2849

			PortletConfig portletConfig = (PortletConfig)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

			if (portletConfig != null) {
				Locale locale = request.getLocale();

				ResourceBundle bundle = portletConfig.getResourceBundle(locale);

				try {
					value = bundle.getString(key);
				}
				catch (MissingResourceException mre) {
				}

				// LEP-7393

				if ((value == null) || (value == defaultValue) &&
					(portletConfig.getPortletName().equals(
						PortletKeys.PORTLET_CONFIGURATION))) {

					value = _getPortletConfigurationValue(pageContext, key);
				}
			}
		}

		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	public Locale[] getAvailableLocales() {
		return _getInstance()._locales;
	}

	public String getCharset(Locale locale) {
		return _getInstance()._getCharset(locale);
	}

	public String getLanguageId(PortletRequest portletRequest) {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getLanguageId(request);
	}

	public String getLanguageId(HttpServletRequest request) {
		String languageId = ParamUtil.getString(request, "languageId");

		if (Validator.isNotNull(languageId)) {
			return languageId;
		}

		Locale locale = PortalUtil.getLocale(request);

		return getLanguageId(locale);
	}

	public String getLanguageId(Locale locale) {
		return LocaleUtil.toLanguageId(locale);
	}

	public Locale getLocale(String languageCode) {
		return _getInstance()._getLocale(languageCode);
	}

	public String getTimeDescription(
		PageContext pageContext, Long milliseconds) {

		return getTimeDescription(pageContext, milliseconds.longValue());
	}

	public String getTimeDescription(
		PageContext pageContext, long milliseconds) {

		String desc = Time.getDescription(milliseconds);

		String value = null;

		try {
			int pos = desc.indexOf(StringPool.SPACE);

			int x = GetterUtil.getInteger(desc.substring(0, pos));

			value =
				x + " " +
				get(
					pageContext,
					desc.substring(pos + 1, desc.length()).toLowerCase());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return value;
	}

	public boolean isAvailableLocale(Locale locale) {
		return _localesSet.contains(locale);
	}

	public void updateCookie(HttpServletResponse response, Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		Cookie languageIdCookie = new Cookie(
			CookieKeys.GUEST_LANGUAGE_ID, languageId);

		languageIdCookie.setPath(StringPool.SLASH);
		languageIdCookie.setMaxAge(CookieKeys.MAX_AGE);

		CookieKeys.addCookie(response, languageIdCookie);
	}

	private static LanguageImpl _getInstance() {
		long companyId = CompanyThreadLocal.getCompanyId();

		LanguageImpl instance = _instances.get(companyId);

		if (instance == null) {
			instance = new LanguageImpl();

			_instances.put(companyId, instance);
		}

		return instance;
	}

	private LanguageImpl() {
		String[] localesArray = PropsValues.LOCALES;

		_locales = new Locale[localesArray.length];
		_localesSet = new HashSet<Locale>(localesArray.length);
		_localesMap = new HashMap<String, Locale>(localesArray.length);
		_charEncodings = new HashMap<String, String>();

		for (int i = 0; i < localesArray.length; i++) {
			String languageId = localesArray[i];

			int pos = languageId.indexOf(StringPool.UNDERLINE);

			String language = languageId.substring(0, pos);
			//String country = languageId.substring(pos + 1);

			Locale locale = LocaleUtil.fromLanguageId(languageId);

			_locales[i] = locale;
			_localesSet.add(locale);
			_localesMap.put(language, locale);
			_charEncodings.put(locale.toString(), StringPool.UTF8);
		}
	}

	private String _escapePattern(String pattern) {
		return StringUtil.replace(
			pattern, StringPool.APOSTROPHE, StringPool.DOUBLE_APOSTROPHE);
	}

	private String _getCharset(Locale locale) {
		return StringPool.UTF8;
	}

	private Locale _getLocale(String languageCode) {
		return _localesMap.get(languageCode);
	}

	private String _getPortletConfigurationValue(
		PageContext pageContext, String key) {

		String value = null;

		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			String portletResource = ParamUtil.getString(
				request, "portletResource");

			long companyId = PortalUtil.getCompanyId(request);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, portletResource);

			PortletConfig portletConfig = PortletConfigFactory.create(
				portlet, pageContext.getServletContext());

			Locale locale = request.getLocale();

			ResourceBundle bundle = portletConfig.getResourceBundle(locale);

			value = bundle.getString(key);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return value;
	}

	private static Log _log = LogFactory.getLog(LanguageImpl.class);

	private static Map<Long, LanguageImpl> _instances =
		new ConcurrentHashMap<Long, LanguageImpl>();

	private Locale[] _locales;
	private Set<Locale> _localesSet;
	private Map<String, Locale> _localesMap;
	private Map<String, String> _charEncodings;

}