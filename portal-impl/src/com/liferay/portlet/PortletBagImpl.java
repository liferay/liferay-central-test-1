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

import com.liferay.portal.kernel.job.Scheduler;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.lar.PortletDataHandler;
import com.liferay.portal.util.ControlPanelEntry;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialRequestInterpreter;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.Portlet;
import javax.portlet.PreferencesValidator;

import javax.servlet.ServletContext;

/**
 * <a href="PortletBagImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletBagImpl implements PortletBag {

	public PortletBagImpl(
		String portletName, ServletContext	servletContext,
		Portlet portletInstance,
		ConfigurationAction configurationActionInstance,
		Indexer indexerInstance, Scheduler schedulerInstance,
		FriendlyURLMapper friendlyURLMapperInstance,
		URLEncoder urlEncoderInstance,
		PortletDataHandler portletDataHandlerInstance,
		PortletLayoutListener portletLayoutListenerInstance,
		MessageListener popMessageListenerInstance,
		SocialActivityInterpreter socialActivityInterpreterInstance,
		SocialRequestInterpreter socialRequestInterpreterInstance,
		ControlPanelEntry controlPanelEntryInstance,
		PreferencesValidator preferencesValidatorInstance,
		Map<String, ResourceBundle> resourceBundles) {

		_portletName = portletName;
		_servletContext = servletContext;
		_portletInstance = portletInstance;
		_configurationActionInstance = configurationActionInstance;
		_indexerInstance = indexerInstance;
		_schedulerInstance = schedulerInstance;
		_friendlyURLMapperInstance = friendlyURLMapperInstance;
		_urlEncoderInstance = urlEncoderInstance;
		_portletDataHandlerInstance = portletDataHandlerInstance;
		_portletLayoutListenerInstance = portletLayoutListenerInstance;
		_popMessageListenerInstance = popMessageListenerInstance;
		_socialActivityInterpreterInstance = socialActivityInterpreterInstance;
		_socialRequestInterpreterInstance = socialRequestInterpreterInstance;
		_controlPanelEntryInstance = controlPanelEntryInstance;
		_preferencesValidatorInstance = preferencesValidatorInstance;
		_resourceBundles = resourceBundles;
	}

	public String getPortletName() {
		return _portletName;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	public Portlet getPortletInstance() {
		return _portletInstance;
	}

	public void removePortletInstance() {
		_portletInstance = null;
	}

	public ConfigurationAction getConfigurationActionInstance() {
		return _configurationActionInstance;
	}

	public Indexer getIndexerInstance() {
		return _indexerInstance;
	}

	public Scheduler getSchedulerInstance() {
		return _schedulerInstance;
	}

	public FriendlyURLMapper getFriendlyURLMapperInstance() {
		return _friendlyURLMapperInstance;
	}

	public URLEncoder getURLEncoderInstance() {
		return _urlEncoderInstance;
	}

	public PortletDataHandler getPortletDataHandlerInstance() {
		return _portletDataHandlerInstance;
	}

	public PortletLayoutListener getPortletLayoutListenerInstance() {
		return _portletLayoutListenerInstance;
	}

	public MessageListener getPopMessageListenerInstance() {
		return _popMessageListenerInstance;
	}

	public SocialActivityInterpreter getSocialActivityInterpreterInstance() {
		return _socialActivityInterpreterInstance;
	}

	public SocialRequestInterpreter getSocialRequestInterpreterInstance() {
		return _socialRequestInterpreterInstance;
	}

	public ControlPanelEntry getControlPanelEntryInstance() {
		return _controlPanelEntryInstance;
	}

	public PreferencesValidator getPreferencesValidatorInstance() {
		return _preferencesValidatorInstance;
	}

	public ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle resourceBundle = _resourceBundles.get(
			LocaleUtil.toLanguageId(locale));

		if (resourceBundle == null) {
			resourceBundle = _resourceBundles.get(locale.getLanguage());

			if (resourceBundle == null) {
				resourceBundle = _resourceBundles.get(
					LocaleUtil.toLanguageId(LocaleUtil.getDefault()));
			}
		}

		return resourceBundle;
	}

	private String _portletName;
	private ServletContext _servletContext;
	private Portlet _portletInstance;
	private ConfigurationAction _configurationActionInstance;
	private Indexer _indexerInstance;
	private Scheduler _schedulerInstance;
	private FriendlyURLMapper _friendlyURLMapperInstance;
	private URLEncoder _urlEncoderInstance;
	private PortletDataHandler _portletDataHandlerInstance;
	private PortletLayoutListener _portletLayoutListenerInstance;
	private MessageListener _popMessageListenerInstance;
	private SocialActivityInterpreter _socialActivityInterpreterInstance;
	private SocialRequestInterpreter _socialRequestInterpreterInstance;
	private ControlPanelEntry _controlPanelEntryInstance;
	private PreferencesValidator _preferencesValidatorInstance;
	private Map<String, ResourceBundle> _resourceBundles;

}