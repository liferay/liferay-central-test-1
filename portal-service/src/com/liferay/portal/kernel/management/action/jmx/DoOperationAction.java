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

import com.liferay.portal.kernel.management.ManageActionException;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * <a href="DoOperationAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class DoOperationAction extends BaseJMXManageAction {

	public DoOperationAction(
		ObjectName objectName, String operationName, Object[] parameters,
		String[] signature) {

		_objectName = objectName;
		_operationName = operationName;
		_parameters = parameters;
		_signature = signature;
	}

	public void action() throws ManageActionException {
		try {
			MBeanServer mBeanServer = getMBeanServer();

			_result = mBeanServer.invoke(
				_objectName, _operationName, _parameters, _signature);

		}
		catch (Exception e) {
			throw new ManageActionException(e);
		}
	}

	public Object getResult() {
		return _result;
	}

	private ObjectName _objectName;
	private String _operationName;
	private Object[] _parameters;
	private Object _result;
	private String[] _signature;

}