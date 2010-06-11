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

package com.liferay.portal.kernel.management.action.jmx;

import com.liferay.portal.kernel.jmx.model.MBean;
import com.liferay.portal.kernel.management.ManageActionException;

import javax.management.AttributeList;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * <a href="GetAttributesAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class GetAttributesAction extends BaseJMXManageAction {

	public GetAttributesAction(MBean mBean) {
		_mBean = mBean;
	}

	public void action() throws ManageActionException {
		try {
			ObjectName objectName = _mBean.getObjectName();

			MBeanServer mBeanServer = getMBeanServer();

			MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(objectName);

			MBeanAttributeInfo[] mBeanAttributeInfos =
				mBeanInfo.getAttributes();

			String[] attributeNames = new String[mBeanAttributeInfos.length];

			for (int i = 0; i < attributeNames.length; i++) {
				attributeNames[i] = mBeanAttributeInfos[i].getName();
			}

			_attributeList = mBeanServer.getAttributes(
				objectName, attributeNames);
		}
		catch (Exception e) {
			throw new ManageActionException(e);
		}
	}

	public AttributeList getAttributeList() {
		return _attributeList;
	}

	private AttributeList _attributeList;
	private MBean _mBean;

}