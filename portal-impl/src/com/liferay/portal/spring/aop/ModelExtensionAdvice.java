/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.spring.aop;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.extension.ModelExtensionHandler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

/**
 * <a href="ModelExtensionAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class ModelExtensionAdvice {

	public Object invoke(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		Object result = proceedingJoinPoint.proceed();

		Signature signature = proceedingJoinPoint.getSignature();

		if (_extensionMethodNames.isEmpty() ||
			_extensionMethodNames.contains(signature.getName())) {

			if (_log.isDebugEnabled()) {
				StringBuilder sb = new StringBuilder();

				sb.append("Extending method ");
				sb.append(signature.getDeclaringTypeName());
				sb.append(".");
				sb.append(signature.getName());
				sb.append("(");

				Object[] args = proceedingJoinPoint.getArgs();

				for (int i = 0; i < args.length; i++) {
					if (i > 0) {
						sb.append(", ");
					}

					sb.append(args[i].getClass().getSimpleName());
				}

				sb.append(")");

				_log.debug(sb.toString());
			}

			if (result instanceof List) {
				result = _modelExtensionHandler.extendList(
					(List<BaseModel>)result);
			}
			else if (result instanceof BaseModel) {
				result = _modelExtensionHandler.extendSingle((BaseModel)result);
			}
		}

		return result;
	}

	public ModelExtensionHandler getModelExtensionHandler() {
		return _modelExtensionHandler;
	}

	public void setModelExtensionHandler(
		ModelExtensionHandler modelExtensionHandler) {

		_modelExtensionHandler = modelExtensionHandler;

		if (_modelExtensionHandler.getExtensionMethodNames() != null) {
			_extensionMethodNames =
				_modelExtensionHandler.getExtensionMethodNames();
		}
		else {
			_extensionMethodNames = new ArrayList<String>();
		}
	}

	private static final Log _log = LogFactory.getLog(
		ModelExtensionAdvice.class);

	private List<String> _extensionMethodNames;
	private ModelExtensionHandler _modelExtensionHandler;

}
