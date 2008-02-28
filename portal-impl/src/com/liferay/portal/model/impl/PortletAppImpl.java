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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.EventDefinition;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletFilter;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.model.PublicRenderParameter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.XMLConstants;

/**
 * <a href="PortletAppImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletAppImpl implements PortletApp {

	public PortletAppImpl(String servletContextName) {
		_servletContextName = servletContextName;

		if (Validator.isNotNull(_servletContextName)) {
			_warFile = true;
		}
		else {
			_warFile = false;
		}
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	public Set<String> getServletURLPatterns() {
		return _servletURLPatterns;
	}

	public Set<String> getUserAttributes() {
		return _userAttributes;
	}

	public Map<String, String> getCustomUserAttributes() {
		return _customUserAttributes;
	}

	public void addPortletFilter(PortletFilter portletFilter) {
		_portletFilters.add(portletFilter);
		_portletFiltersByFilterName.put(
			portletFilter.getFilterName(), portletFilter);
	}

	public PortletFilter getPortletFilter(String filterName) {
		return _portletFiltersByFilterName.get(filterName);
	}

	public Set<PortletFilter> getPortletFilters() {
		return _portletFilters;
	}

	public String getDefaultNamespace() {
		return _defaultNamespace;
	}

	public void setDefaultNamespace(String defaultNamespace) {
		_defaultNamespace = defaultNamespace;
	}

	public void addEventDefinition(EventDefinition eventDefinition) {
		_eventDefinitions.add(eventDefinition);
	}

	public void addPublicRenderParameter(
		PublicRenderParameter publicRenderParameter) {

		_publicRenderParameters.add(publicRenderParameter);
		_publicRenderParametersByIdentifier.put(
			publicRenderParameter.getIdentifier(), publicRenderParameter);
	}

	public PublicRenderParameter getPublicRenderParameter(String identifier) {
		return _publicRenderParametersByIdentifier.get(identifier);
	}

	public void addPortletURLListener(PortletURLListener portletURLListener) {
		_portletURLListeners.add(portletURLListener);
		_portletURLListenersByListenerClass.put(
			portletURLListener.getListenerClass(), portletURLListener);
	}

	public PortletURLListener getPortletURLListener(String listenerClass) {
		return _portletURLListenersByListenerClass.get(listenerClass);
	}

	public Set<PortletURLListener> getPortletURLListeners() {
		return _portletURLListeners;
	}

	public boolean isWARFile() {
		return _warFile;
	}

	private String _servletContextName = StringPool.BLANK;
	private Set<String> _servletURLPatterns = new LinkedHashSet<String>();
	private Set<String> _userAttributes = new LinkedHashSet<String>();
	private Map<String, String> _customUserAttributes =
		new LinkedHashMap<String, String>();
	private Set<PortletFilter> _portletFilters =
		new LinkedHashSet<PortletFilter>();
	private Map<String, PortletFilter> _portletFiltersByFilterName =
		new HashMap<String, PortletFilter>();
	private String _defaultNamespace = XMLConstants.NULL_NS_URI;
	private Set<EventDefinition> _eventDefinitions =
		new LinkedHashSet<EventDefinition>();
	private Set<PublicRenderParameter> _publicRenderParameters =
		new LinkedHashSet<PublicRenderParameter>();
	private Map<String, PublicRenderParameter>
		_publicRenderParametersByIdentifier =
			new HashMap<String, PublicRenderParameter>();
	private Set<PortletURLListener> _portletURLListeners =
		new LinkedHashSet<PortletURLListener>();
	private Map<String, PortletURLListener>
		_portletURLListenersByListenerClass =
			new HashMap<String, PortletURLListener>();
	private boolean _warFile;

}