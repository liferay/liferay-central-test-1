/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.wiki.model.WikiPage;

/**
 * @author Bruno Farache
 */
public class PageVersionComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "version ASC";

	public static String ORDER_BY_DESC = "version DESC";

	public static String[] ORDER_BY_FIELDS = {"version"};

	public PageVersionComparator() {
		this(false);
	}

	public PageVersionComparator(boolean ascending) {
		_ascending = ascending;
	}

	public int compare(Object obj1, Object obj2) {
		WikiPage page1 = (WikiPage)obj1;
		WikiPage page2 = (WikiPage)obj2;

		int value = 0;

		if (page1.getVersion() < page2.getVersion()) {
			value = -1;
		}
		else if (page1.getVersion() > page2.getVersion()) {
			value = 1;
		}

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	public boolean isAscending() {
		return _ascending;
	}

	private boolean _ascending;

}