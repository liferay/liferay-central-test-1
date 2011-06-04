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

package com.liferay.portal.spring.aop;

import com.liferay.portal.kernel.util.MethodTargetClassKey;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.aopalliance.intercept.MethodInvocation;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public abstract class AnnotationChainableMethodAdvice<T extends Annotation>
	extends ChainableMethodAdvice implements BeanFactoryAware {

	public AnnotationChainableMethodAdvice() {
		_nullAnnotation = getNullAnnotation();
		_annotationType = _nullAnnotation.annotationType();
	}

	public abstract T getNullAnnotation();

	public void setBeanFactory(BeanFactory beanFactory) {
		if (beanFactory == null) {
			_beanFactoryAnnotations.remove(_beanFactory);
		}
		else {
			_beanFactory = beanFactory;

			_beanFactoryAnnotations.putIfAbsent(
				_beanFactory,
				new ConcurrentHashMap<MethodTargetClassKey, Annotation[]>());
		}
	}

	protected MethodTargetClassKey buildMethodTargetClassKey(
		MethodInvocation methodInvocation) {
		Method method = methodInvocation.getMethod();

		Class<?> targetClass = null;

		Object thisObject = methodInvocation.getThis();

		if (thisObject != null) {
			targetClass = thisObject.getClass();
		}

		return new MethodTargetClassKey(method, targetClass);
	}

	protected T findAnnotation(MethodTargetClassKey methodTargetClassKey) {
		Map<MethodTargetClassKey, Annotation[]> annotationsMap =
			_beanFactoryAnnotations.get(_beanFactory);

		if (annotationsMap == null) {
			return _nullAnnotation;
		}

		Annotation[] annotations = annotationsMap.get(methodTargetClassKey);

		if (annotations != null) {
			return getAnnotation(annotations);
		}

		Method method = methodTargetClassKey.getMethod();

		Method targetMethod = methodTargetClassKey.getTargetMethod();

		if (targetMethod != null) {
			annotations = targetMethod.getAnnotations();
		}

		if ((annotations == null) || (annotations.length == 0)) {
			annotations = method.getAnnotations();
		}

		if ((annotations == null) || (annotations.length == 0)) {
			annotations = _emptyAnnotations;
		}

		annotationsMap.put(methodTargetClassKey, annotations);

		return getAnnotation(annotations);
	}

	protected T getAnnotation(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation.annotationType() == _annotationType) {
				return (T)annotation;
			}
		}

		return _nullAnnotation;
	}

	private static ConcurrentMap
		<BeanFactory, Map<MethodTargetClassKey, Annotation[]>>
			_beanFactoryAnnotations =
				new ConcurrentHashMap
					<BeanFactory, Map<MethodTargetClassKey, Annotation[]>>();
	private static Annotation[] _emptyAnnotations = new Annotation[0];

	private Class<? extends Annotation> _annotationType;
	private BeanFactory _beanFactory;
	private T _nullAnnotation;

}