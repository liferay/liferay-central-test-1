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

package com.liferay.portlet.wiki.engines;

import com.liferay.portlet.wiki.model.WikiPage;

import java.util.Collections;
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * <a href="HtmlEngine.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class HtmlEngine implements WikiEngine {

	public String convert(WikiPage page, PortletURL portletURL) {
		return page.getContent();
	}

	public Map<String, Boolean> getOutgoingLinks(WikiPage page) {
		return Collections.EMPTY_MAP;
	}

	public void setInterWikiConfiguration(String interWikiConfiguration) {
	}

	public void setMainConfiguration(String mainConfiguration) {
	}

	public boolean validate(long nodeId, String newContent) {
		return true;
	}

}