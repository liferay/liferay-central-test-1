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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodTargetClassKey;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shuyang Zhou
 */
public class ClusterableAdvice
	extends AnnotationChainableMethodAdvice<Clusterable> {

	public void afterReturning(MethodInvocation methodInvocation, Object result)
		throws Throwable {

		if (!ClusterInvokeThreadLocal.isEnabled()) {
			return;
		}

		MethodTargetClassKey methodTargetClassKey = buildMethodTargetClassKey(
			methodInvocation);

		Clusterable clusterable = findAnnotation(methodTargetClassKey);

		if (clusterable == _nullClusterable) {
			return;
		}

		Method method = methodTargetClassKey.getMethod();

		Method utilClassMethod = _getUtilClassMethod(method);

		MethodHandler methodHandler = new MethodHandler(
			utilClassMethod, methodInvocation.getArguments());

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			methodHandler, true);

		clusterRequest.setServletContextName(_servletContextName);

		ClusterExecutorUtil.execute(clusterRequest);
	}

	public Clusterable getNullAnnotation() {
		return _nullClusterable;
	}

	public void setServletContextName(String servletContextName) {
		_servletContextName = servletContextName;
	}

	private Method _getUtilClassMethod(Method method) throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Class<?> declaringClass = method.getDeclaringClass();

		String utilClassName = declaringClass.getName().concat("Util");

		Class<?> utilClass = contextClassLoader.loadClass(utilClassName);

		return utilClass.getMethod(
			method.getName(), method.getParameterTypes());
	}

	private static Clusterable _nullClusterable =
		new Clusterable() {

			public Class<? extends Annotation> annotationType() {
				return Clusterable.class;
			}

		};

	private String _servletContextName;

}