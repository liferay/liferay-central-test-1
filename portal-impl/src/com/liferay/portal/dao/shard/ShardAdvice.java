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

package com.liferay.portal.dao.shard;

import com.liferay.counter.service.persistence.CounterFinder;
import com.liferay.counter.service.persistence.CounterPersistence;
import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.kernel.dao.shard.ShardUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Shard;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ShardLocalServiceUtil;
import com.liferay.portal.service.persistence.ClassNamePersistence;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.PortalPreferencesPersistence;
import com.liferay.portal.service.persistence.ReleasePersistence;
import com.liferay.portal.service.persistence.ResourceActionPersistence;
import com.liferay.portal.service.persistence.ServiceComponentPersistence;
import com.liferay.portal.service.persistence.ShardPersistence;
import com.liferay.portal.service.persistence.VirtualHostPersistence;
import com.liferay.portal.util.PropsValues;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.sql.DataSource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

/**
 * @author Michael Young
 * @author Alexander Chow
 */
public class ShardAdvice {

	public void afterPropertiesSet() {
		if (_shardDataSourceTargetSource == null) {
			_shardDataSourceTargetSource =
				(ShardDataSourceTargetSource)InfrastructureUtil.
					getShardDataSourceTargetSource();
		}

		if (_shardSessionFactoryTargetSource == null) {
			_shardSessionFactoryTargetSource =
				(ShardSessionFactoryTargetSource)InfrastructureUtil.
					getShardSessionFactoryTargetSource();
		}
	}

	public Object invokeByParameter(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		Object[] arguments = proceedingJoinPoint.getArgs();

		long companyId = (Long)arguments[0];

		Shard shard = ShardLocalServiceUtil.getShard(
			Company.class.getName(), companyId);

		String shardName = shard.getName();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Service being set to shard " + shardName + " for " +
					_getSignature(proceedingJoinPoint));
		}

		Object returnValue = null;

		pushCompanyService(shardName);

		try {
			returnValue = proceedingJoinPoint.proceed();
		}
		finally {
			popCompanyService();
		}

		return returnValue;
	}

	public Object invokeCompanyService(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		Signature signature = proceedingJoinPoint.getSignature();

		String methodName = signature.getName();

		Object[] arguments = proceedingJoinPoint.getArgs();

		String shardName = PropsValues.SHARD_DEFAULT_NAME;

		if (methodName.equals("addCompany")) {
			String webId = (String)arguments[0];
			String virtualHostname = (String)arguments[1];
			String mx = (String)arguments[2];
			shardName = (String)arguments[3];

			shardName = _getCompanyShardName(
				webId, virtualHostname, mx, shardName);

			arguments[3] = shardName;
		}
		else if (methodName.equals("checkCompany")) {
			String webId = (String)arguments[0];

			if (!webId.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {
				if (arguments.length == 3) {
					String mx = (String)arguments[1];
					shardName = (String)arguments[2];

					shardName = _getCompanyShardName(
						webId, null, mx, shardName);

					arguments[2] = shardName;
				}

				try {
					Company company = CompanyLocalServiceUtil.getCompanyByWebId(
						webId);

					shardName = company.getShardName();
				}
				catch (NoSuchCompanyException nsce) {
				}
			}
		}
		else if (methodName.startsWith("update")) {
			long companyId = (Long)arguments[0];

			Shard shard = ShardLocalServiceUtil.getShard(
				Company.class.getName(), companyId);

			shardName = shard.getName();
		}
		else {
			return proceedingJoinPoint.proceed();
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Company service being set to shard " + shardName + " for " +
					_getSignature(proceedingJoinPoint));
		}

		Object returnValue = null;

		pushCompanyService(shardName);

		try {
			returnValue = proceedingJoinPoint.proceed(arguments);
		}
		finally {
			popCompanyService();
		}

		return returnValue;
	}

	/**
	 * Invoke a join point across all shards while ignoring the company service
	 * stack.
	 *
	 * @see #invokeIteratively
	 */
	public Object invokeGlobally(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		_globalCall.set(new Object());

		try {
			if (_log.isInfoEnabled()) {
				_log.info(
					"All shards invoked for " +
						_getSignature(proceedingJoinPoint));
			}

			for (String shardName : ShardUtil.getAvailableShardNames()) {
				_shardDataSourceTargetSource.setDataSource(shardName);
				_shardSessionFactoryTargetSource.setSessionFactory(shardName);

				proceedingJoinPoint.proceed();
			}
		}
		finally {
			_globalCall.set(null);
		}

		return null;
	}

	/**
	 * Invoke a join point across all shards while using the company service
	 * stack.
	 *
	 * @see #invokeGlobally
	 */
	public Object invokeIteratively(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		if (_log.isInfoEnabled()) {
			_log.info(
				"Iterating through all shards for " +
					_getSignature(proceedingJoinPoint));
		}

		for (String shardName : ShardUtil.getAvailableShardNames()) {
			pushCompanyService(shardName);

			try {
				proceedingJoinPoint.proceed();
			}
			finally {
				popCompanyService();
			}
		}

		return null;
	}

	public Object invokePersistence(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		if ((_shardDataSourceTargetSource == null) ||
			(_shardSessionFactoryTargetSource == null)) {

			return proceedingJoinPoint.proceed();
		}

		Object target = proceedingJoinPoint.getTarget();

		if (target instanceof ClassNamePersistence ||
			target instanceof CompanyPersistence ||
			target instanceof CounterFinder ||
			target instanceof CounterPersistence ||
			target instanceof PortalPreferencesPersistence ||
			target instanceof ReleasePersistence ||
			target instanceof ResourceActionPersistence ||
			target instanceof ServiceComponentPersistence ||
			target instanceof ShardPersistence ||
			target instanceof VirtualHostPersistence) {

			_shardDataSourceTargetSource.setDataSource(
				PropsValues.SHARD_DEFAULT_NAME);
			_shardSessionFactoryTargetSource.setSessionFactory(
				PropsValues.SHARD_DEFAULT_NAME);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Using default shard for " +
						_getSignature(proceedingJoinPoint));
			}

			return proceedingJoinPoint.proceed();
		}

		if (_globalCall.get() == null) {
			_setShardNameByCompany();

			String shardName = _getShardName();

			_shardDataSourceTargetSource.setDataSource(shardName);
			_shardSessionFactoryTargetSource.setSessionFactory(shardName);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Using shard name " + shardName + " for " +
						_getSignature(proceedingJoinPoint));
			}

			return proceedingJoinPoint.proceed();
		}
		else {
			return proceedingJoinPoint.proceed();
		}
	}

	public Object invokePortletService(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		Signature signature = proceedingJoinPoint.getSignature();

		String methodName = signature.getName();

		Object[] arguments = proceedingJoinPoint.getArgs();

		if (arguments.length == 0) {
			return proceedingJoinPoint.proceed();
		}

		Object argument = arguments[0];

		long companyId = -1;

		if (argument instanceof Long) {
			if (methodName.equals("checkPortlets") ||
				methodName.equals("clonePortlet") ||
				methodName.equals("getPortletById") ||
				methodName.equals("getPortletByStrutsPath") ||
				methodName.equals("getPortlets") ||
				methodName.equals("hasPortlet") ||
				methodName.equals("updatePortlet")) {

				companyId = (Long)argument;
			}
		}
		else if (argument instanceof Portlet) {
			if (methodName.equals("checkPortlet") ||
				methodName.equals("deployRemotePortlet") ||
				methodName.equals("destroyPortlet") ||
				methodName.equals("destroyRemotePortlet")) {

				Portlet portlet = (Portlet)argument;

				companyId = portlet.getCompanyId();
			}
		}

		if (companyId <= 0) {
			return proceedingJoinPoint.proceed();
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Company service being set to shard of companyId " +
					companyId + " for " + _getSignature(proceedingJoinPoint));
		}

		Object returnValue = null;

		pushCompanyService(companyId);

		try {
			returnValue = proceedingJoinPoint.proceed(arguments);
		}
		finally {
			popCompanyService();
		}

		return returnValue;
	}

	public void setShardDataSourceTargetSource(
		ShardDataSourceTargetSource shardDataSourceTargetSource) {

		_shardDataSourceTargetSource = shardDataSourceTargetSource;
	}

	public void setShardSessionFactoryTargetSource(
		ShardSessionFactoryTargetSource shardSessionFactoryTargetSource) {

		_shardSessionFactoryTargetSource = shardSessionFactoryTargetSource;
	}

	protected String getCurrentShardName() {
		String shardName = null;

		try {
			shardName = _getCompanyServiceStack().peek();
		}
		catch (EmptyStackException ese) {
		}

		if (shardName == null) {
			shardName = PropsValues.SHARD_DEFAULT_NAME;
		}

		return shardName;
	}

	protected DataSource getDataSource() {
		return _shardDataSourceTargetSource.getDataSource();
	}

	protected String popCompanyService() {
		return _getCompanyServiceStack().pop();
	}

	protected void pushCompanyService(long companyId) {
		try {
			Shard shard = ShardLocalServiceUtil.getShard(
				Company.class.getName(), companyId);

			String shardName = shard.getName();

			pushCompanyService(shardName);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void pushCompanyService(String shardName) {
		_getCompanyServiceStack().push(shardName);
	}

	private Stack<String> _getCompanyServiceStack() {
		Stack<String> companyServiceStack = _companyServiceStack.get();

		if (companyServiceStack == null) {
			companyServiceStack = new Stack<String>();

			_companyServiceStack.set(companyServiceStack);
		}

		return companyServiceStack;
	}

	private String _getCompanyShardName(
		String webId, String virtualHostname, String mx, String shardName) {

		Map<String, String> shardParams = new HashMap<String, String>();

		shardParams.put("webId", webId);
		shardParams.put("mx", mx);

		if (virtualHostname != null) {
			shardParams.put("virtualHostname", virtualHostname);
		}

		shardName = _shardSelector.getShardName(
			ShardSelector.COMPANY_SCOPE, shardName, shardParams);

		return shardName;
	}

	private String _getShardName() {
		return _shardName.get();
	}

	private String _getSignature(ProceedingJoinPoint proceedingJoinPoint) {
		String methodName = StringUtil.extractLast(
			proceedingJoinPoint.getTarget().getClass().getName(),
			StringPool.PERIOD);

		Signature signature = proceedingJoinPoint.getSignature();

		methodName += StringPool.PERIOD + signature.getName() + "()";

		return methodName;
	}

	private void _setShardName(String shardName) {
		_shardName.set(shardName);
	}

	private void _setShardNameByCompany() throws Throwable {
		Stack<String> companyServiceStack = _getCompanyServiceStack();

		if (companyServiceStack.isEmpty()) {
			long companyId = CompanyThreadLocal.getCompanyId();

			_setShardNameByCompanyId(companyId);
		}
		else {
			String shardName = companyServiceStack.peek();

			_setShardName(shardName);
		}
	}

	private void _setShardNameByCompanyId(long companyId)
		throws PortalException, SystemException {

		if (companyId == 0) {
			_setShardName(PropsValues.SHARD_DEFAULT_NAME);
		}
		else {
			Shard shard = ShardLocalServiceUtil.getShard(
				Company.class.getName(), companyId);

			String shardName = shard.getName();

			_setShardName(shardName);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ShardAdvice.class);

	private static ThreadLocal<Stack<String>> _companyServiceStack =
		new ThreadLocal<Stack<String>>();
	private static ThreadLocal<Object> _globalCall = new ThreadLocal<Object>();
	private static ThreadLocal<String> _shardName =
		new InitialThreadLocal<String>(
			ShardAdvice.class + "._shardName", PropsValues.SHARD_DEFAULT_NAME);
	private static ShardSelector _shardSelector;

	private ShardDataSourceTargetSource _shardDataSourceTargetSource;
	private ShardSessionFactoryTargetSource _shardSessionFactoryTargetSource;

	static {
		try {
			_shardSelector = (ShardSelector)Class.forName(
				PropsValues.SHARD_SELECTOR).newInstance();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

}