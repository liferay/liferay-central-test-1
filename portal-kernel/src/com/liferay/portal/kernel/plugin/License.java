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

package com.liferay.portal.kernel.plugin;

/**
 * <a href="License.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class License {

	public String getName() {
		return _name;
	}

	public String getUrl() {
		return _url;
	}

	public boolean isOsiApproved() {
		return _osiApproved;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOsiApproved(boolean osiApproved) {
		_osiApproved = osiApproved;
	}

	public void setUrl(String url) {
		_url = url;
	}

	private String _name;
	private boolean _osiApproved;
	private String _url;

}