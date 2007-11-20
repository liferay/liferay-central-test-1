/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.service;


/**
 * <a href="BlogsEntryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.blogs.service.BlogsEntryService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.blogs.service.BlogsEntryServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.blogs.service.BlogsEntryService
 * @see com.liferay.portlet.blogs.service.BlogsEntryServiceFactory
 *
 */
public class BlogsEntryServiceUtil {
	public static com.liferay.portlet.blogs.model.BlogsEntry addEntry(
		long plid, long categoryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		com.liferay.portal.theme.ThemeDisplay themeDisplay,
		java.lang.String[] tagsEntries, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		return blogsEntryService.addEntry(plid, categoryId, title, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, themeDisplay, tagsEntries,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry addEntry(
		long plid, long categoryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		com.liferay.portal.theme.ThemeDisplay themeDisplay,
		java.lang.String[] tagsEntries,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		return blogsEntryService.addEntry(plid, categoryId, title, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, themeDisplay, tagsEntries, communityPermissions,
			guestPermissions);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		blogsEntryService.deleteEntry(entryId);
	}

	public static java.lang.String getCategoryBlogsRSS(long categoryId,
		int max, java.lang.String type, double version,
		java.lang.String feedURL, java.lang.String entryURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		return blogsEntryService.getCategoryBlogsRSS(categoryId, max, type,
			version, feedURL, entryURL);
	}

	public static java.lang.String getCategoryBlogsRSS(long categoryId,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		return blogsEntryService.getCategoryBlogsRSS(categoryId, max, type,
			version, displayStyle, feedURL, entryURL);
	}

	public static java.util.List getCompanyEntries(long companyId, int max)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		return blogsEntryService.getCompanyEntries(companyId, max);
	}

	public static java.lang.String getCompanyEntriesRSS(long companyId,
		int max, java.lang.String type, double version,
		java.lang.String feedURL, java.lang.String entryURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		return blogsEntryService.getCompanyEntriesRSS(companyId, max, type,
			version, feedURL, entryURL);
	}

	public static java.lang.String getCompanyEntriesRSS(long companyId,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		return blogsEntryService.getCompanyEntriesRSS(companyId, max, type,
			version, displayStyle, feedURL, entryURL);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry getEntry(
		long entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		return blogsEntryService.getEntry(entryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry getEntry(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		return blogsEntryService.getEntry(groupId, urlTitle);
	}

	public static java.util.List getGroupEntries(long groupId, int max)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		return blogsEntryService.getGroupEntries(groupId, max);
	}

	public static java.lang.String getGroupEntriesRSS(long groupId, int max,
		java.lang.String type, double version, java.lang.String feedURL,
		java.lang.String entryURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		return blogsEntryService.getGroupEntriesRSS(groupId, max, type,
			version, feedURL, entryURL);
	}

	public static java.lang.String getGroupEntriesRSS(long groupId, int max,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		return blogsEntryService.getGroupEntriesRSS(groupId, max, type,
			version, displayStyle, feedURL, entryURL);
	}

	public static java.util.List getOrganizationEntries(long organizationId,
		int max)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		return blogsEntryService.getOrganizationEntries(organizationId, max);
	}

	public static java.lang.String getOrganizationEntriesRSS(
		long organizationId, int max, java.lang.String type, double version,
		java.lang.String feedURL, java.lang.String entryURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		return blogsEntryService.getOrganizationEntriesRSS(organizationId, max,
			type, version, feedURL, entryURL);
	}

	public static java.lang.String getOrganizationEntriesRSS(
		long organizationId, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		return blogsEntryService.getOrganizationEntriesRSS(organizationId, max,
			type, version, displayStyle, feedURL, entryURL);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry updateEntry(
		long entryId, long categoryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		com.liferay.portal.theme.ThemeDisplay themeDisplay,
		java.lang.String[] tagsEntries)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		BlogsEntryService blogsEntryService = BlogsEntryServiceFactory.getService();

		return blogsEntryService.updateEntry(entryId, categoryId, title,
			content, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, themeDisplay, tagsEntries);
	}
}