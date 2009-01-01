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

package com.liferay.portal.mirage.aop;

import com.sun.portal.cms.mirage.exception.CMSException;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <a href="MirageInvoker.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MirageInvoker {

	public MirageInvoker(ProceedingJoinPoint proceedingJoinPoint) {
		_proceedingJoinPoint = proceedingJoinPoint;
	}

	public Object getReturnValue() {
		return _returnValue;
	}

	public Object invoke() throws CMSException {
		try {
			Object returnValue = _proceedingJoinPoint.proceed();

			_returnValue = returnValue;

			return returnValue;
		}
		catch (Exception e) {
			throw new CMSException(e.getMessage(), e);
		}
		catch (Throwable t) {
			throw new CMSException(t.getMessage());
		}
	}

	private ProceedingJoinPoint _proceedingJoinPoint;
	private Object _returnValue;

}