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

package com.liferay.portlet.wiki.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="WikiPageServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link WikiPageService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPageService
 * @generated
 */
public class WikiPageServiceUtil {
	public static com.liferay.portlet.wiki.model.WikiPage addPage(long nodeId,
		java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addPage(nodeId, title, content, summary, minorEdit,
			serviceContext);
	}

	public static com.liferay.portlet.wiki.model.WikiPage addPage(long nodeId,
		java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit, java.lang.String format,
		java.lang.String parentTitle, java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addPage(nodeId, title, content, summary, minorEdit, format,
			parentTitle, redirectTitle, serviceContext);
	}

	public static void addPageAttachments(long nodeId, java.lang.String title,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, byte[]>> files)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addPageAttachments(nodeId, title, files);
	}

	public static void changeParent(long nodeId, java.lang.String title,
		java.lang.String newParentTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().changeParent(nodeId, title, newParentTitle, serviceContext);
	}

	public static void deletePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePage(nodeId, title);
	}

	public static void deletePage(long nodeId, java.lang.String title,
		double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePage(nodeId, title, version);
	}

	public static void deletePageAttachment(long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePageAttachment(nodeId, title, fileName);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getDraftPage(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDraftPage(nodeId, title);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getNodePages(
		long nodeId, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getNodePages(nodeId, max);
	}

	public static java.lang.String getNodePagesRSS(long nodeId, int max,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getNodePagesRSS(nodeId, max, type, version, displayStyle,
			feedURL, entryURL);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPage(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, java.lang.Boolean head)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPage(nodeId, title, head);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPage(nodeId, title, version);
	}

	public static java.lang.String getPagesRSS(long companyId, long nodeId,
		java.lang.String title, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getPagesRSS(companyId, nodeId, title, max, type, version,
			displayStyle, feedURL, entryURL, locale);
	}

	public static void movePage(long nodeId, java.lang.String title,
		java.lang.String newTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().movePage(nodeId, title, newTitle, serviceContext);
	}

	public static com.liferay.portlet.wiki.model.WikiPage revertPage(
		long nodeId, java.lang.String title, double version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().revertPage(nodeId, title, version, serviceContext);
	}

	public static void subscribePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().subscribePage(nodeId, title);
	}

	public static void unsubscribePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsubscribePage(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPage updatePage(
		long nodeId, java.lang.String title, double version,
		java.lang.String content, java.lang.String summary, boolean minorEdit,
		java.lang.String format, java.lang.String parentTitle,
		java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updatePage(nodeId, title, version, content, summary,
			minorEdit, format, parentTitle, redirectTitle, serviceContext);
	}

	public static WikiPageService getService() {
		if (_service == null) {
			_service = (WikiPageService)PortalBeanLocatorUtil.locate(WikiPageService.class.getName());
		}

		return _service;
	}

	public void setService(WikiPageService service) {
		_service = service;
	}

	private static WikiPageService _service;
}