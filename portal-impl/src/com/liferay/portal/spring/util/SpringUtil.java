/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.util;

import com.liferay.portal.kernel.bean.InitializingBean;
import com.liferay.portal.spring.context.ArrayApplicationContext;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import org.springframework.context.ApplicationContext;

/**
 * <a href="SpringUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * In most cases, SpringUtil.setContext() would have been called by
 * com.liferay.portal.spring.context.PortalContextLoaderListener, configured in
 * web.xml for the web application. However, there will be times in which
 * SpringUtil will be called in a non-web application and, therefore, require
 * manual instantiation of the application context.
 * </p>
 *
 * @author Michael Young
 *
 */
public class SpringUtil {

	public static ApplicationContext getContext() {
		if (_applicationContext == null) {
			System.out.println("Manually loading Spring context");

			_applicationContext = new ArrayApplicationContext(
				PropsUtil.getArray(PropsKeys.SPRING_CONFIGS));
		}

		return _applicationContext;
	}

	public static void initContext(ApplicationContext applicationContext) {

		// Preinitialize Spring beans. See LEP-4734.

		String[] beanDefinitionNames =
			applicationContext.getBeanDefinitionNames();

		for (String beanDefinitionName : beanDefinitionNames) {
			applicationContext.getBean(beanDefinitionName);
		}

		for (String beanDefinitionName : beanDefinitionNames) {
			Object obj = applicationContext.getBean(beanDefinitionName);

			if (obj instanceof InitializingBean) {
				InitializingBean initializingBean = (InitializingBean)obj;

				initializingBean.afterPropertiesSet();
			}
		}
	}

	public static void setContext(ApplicationContext applicationContext) {
		_applicationContext = applicationContext;

		initContext(applicationContext);
	}

	private static ApplicationContext _applicationContext = null;

}