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

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

/**
 * @author Amos Fong
 * @author Brian Wing Shun Chan
 */
public class Modifications {

	public static Modifications getInstance() {
		return new Modifications();
	}

	public ModificationItem addItem(
		int modificationOp, String id, String value) {

		BasicAttribute basicAttribute = new BasicAttribute(id);

		if (Validator.isNotNull(value)) {
			basicAttribute.add(value);
		}

		ModificationItem item = new ModificationItem(
			modificationOp, basicAttribute);

		_items.add(item);

		return item;
	}

	public ModificationItem addItem(String id, String value) {
		return addItem(DirContext.REPLACE_ATTRIBUTE, id, value);
	}

	public ModificationItem[] getItems() {
		return _items.toArray(new ModificationItem[_items.size()]);
	}

	private Modifications() {
	}

	private List<ModificationItem> _items = new ArrayList<ModificationItem>();

}