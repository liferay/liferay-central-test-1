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

package com.liferay.portlet.social.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="SocialRelationLocalServiceFactory.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is responsible for the lookup of the implementation for
 * <code>com.liferay.portlet.social.service.SocialRelationLocalService</code>.
 * Spring manages the lookup and lifecycle of the beans. This means you can
 * modify the Spring configuration files to return a different implementation or
 * to inject additional behavior.
 * </p>
 *
 * <p>
 * See the <code>spring.configs</code> property in portal.properties for
 * additional information on how to customize the Spring XML files.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.social.service.SocialRelationLocalService
 * @see com.liferay.portlet.social.service.SocialRelationLocalServiceUtil
 *
 */
public class SocialRelationLocalServiceFactory {
	/**
	 * @deprecated
	 */
	public static SocialRelationLocalService getService() {
		return _getFactory()._service;
	}

	/**
	 * @deprecated
	 */
	public static SocialRelationLocalService getImpl() {
		if (_impl == null) {
			_impl = (SocialRelationLocalService)PortalBeanLocatorUtil.locate(_IMPL);
		}

		return _impl;
	}

	/**
	 * @deprecated
	 */
	public static SocialRelationLocalService getTxImpl() {
		if (_txImpl == null) {
			_txImpl = (SocialRelationLocalService)PortalBeanLocatorUtil.locate(_TX_IMPL);
		}

		return _txImpl;
	}

	/**
	 * @deprecated
	 */
	public void setService(SocialRelationLocalService service) {
		_service = service;
	}

	private static SocialRelationLocalServiceFactory _getFactory() {
		if (_factory == null) {
			_factory = (SocialRelationLocalServiceFactory)PortalBeanLocatorUtil.locate(_FACTORY);
		}

		return _factory;
	}

	private static final String _FACTORY = SocialRelationLocalServiceFactory.class.getName();
	private static final String _IMPL = SocialRelationLocalService.class.getName() +
		".impl";
	private static final String _TX_IMPL = SocialRelationLocalService.class.getName() +
		".transaction";
	private static SocialRelationLocalServiceFactory _factory;
	private static SocialRelationLocalService _impl;
	private static SocialRelationLocalService _txImpl;
	private SocialRelationLocalService _service;
}