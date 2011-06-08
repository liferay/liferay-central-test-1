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

package com.liferay.portlet.dynamicdatalists.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.dynamicdatalists.service.DDLRecordServiceUtil;

/**
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portlet.dynamicdatalists.service.DDLRecordServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
 * </p>
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDLRecordServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.dynamicdatalists.service.DDLRecordServiceUtil
 * @generated
 */
public class DDLRecordServiceHttp {
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord addRecord(
		HttpPrincipal httpPrincipal, long groupId, long recordSetId,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		int displayIndex,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDLRecordServiceUtil.class.getName(),
					"addRecord", _addRecordParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					recordSetId, fields, displayIndex, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.dynamicdatalists.model.DDLRecord)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord addRecord(
		HttpPrincipal httpPrincipal, long groupId, long recordSetId,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		int displayIndex,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDLRecordServiceUtil.class.getName(),
					"addRecord", _addRecordParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					recordSetId, fieldsMap, displayIndex, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.dynamicdatalists.model.DDLRecord)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord updateRecord(
		HttpPrincipal httpPrincipal, long recordId,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		int displayIndex, boolean merge, boolean majorVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDLRecordServiceUtil.class.getName(),
					"updateRecord", _updateRecordParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					recordId, fields, displayIndex, merge, majorVersion,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.dynamicdatalists.model.DDLRecord)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord updateRecord(
		HttpPrincipal httpPrincipal, long recordId,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		int displayIndex, boolean merge,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDLRecordServiceUtil.class.getName(),
					"updateRecord", _updateRecordParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					recordId, fieldsMap, displayIndex, merge, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.dynamicdatalists.model.DDLRecord)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDLRecordServiceHttp.class);
	private static final Class<?>[] _addRecordParameterTypes0 = new Class[] {
			long.class, long.class,
			com.liferay.portlet.dynamicdatamapping.storage.Fields.class,
			int.class, com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addRecordParameterTypes1 = new Class[] {
			long.class, long.class, java.util.Map.class, int.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateRecordParameterTypes2 = new Class[] {
			long.class,
			com.liferay.portlet.dynamicdatamapping.storage.Fields.class,
			int.class, boolean.class, boolean.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateRecordParameterTypes3 = new Class[] {
			long.class, java.util.Map.class, int.class, boolean.class,
			com.liferay.portal.service.ServiceContext.class
		};
}