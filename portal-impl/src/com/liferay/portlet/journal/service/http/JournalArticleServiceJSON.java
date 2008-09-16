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

package com.liferay.portlet.journal.service.http;

import com.liferay.portal.kernel.json.JSONObject;

import com.liferay.portlet.journal.service.JournalArticleServiceUtil;

/**
 * <a href="JournalArticleServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a JSON utility for the
 * <code>com.liferay.portlet.journal.service.JournalArticleServiceUtil</code>
 * service utility. The static methods of this class calls the same methods of
 * the service utility. However, the signatures are different because it is
 * difficult for JSON to support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to a
 * <code>com.liferay.portal.kernel.json.JSONArray</code>. If the method in the
 * service utility returns a <code>com.liferay.portlet.journal.model.JournalArticle</code>,
 * that is translated to a
 * <code>com.liferay.portal.kernel.json.JSONObject</code>. Methods that JSON
 * cannot safely use are skipped. The logic for the translation is encapsulated
 * in <code>com.liferay.portlet.journal.service.http.JournalArticleJSONSerializer</code>.
 * </p>
 *
 * <p>
 * This allows you to call the the backend services directly from JavaScript.
 * See <code>portal-web/docroot/html/portlet/tags_admin/unpacked.js</code> for a
 * reference of how that portlet uses the generated JavaScript in
 * <code>portal-web/docroot/html/js/service.js</code> to call the backend
 * services directly from JavaScript.
 * </p>
 *
 * <p>
 * The JSON utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.JournalArticleServiceUtil
 * @see com.liferay.portlet.journal.service.http.JournalArticleJSONSerializer
 *
 */
public class JournalArticleServiceJSON {
	public static JSONObject addArticle(long groupId,
		java.lang.String articleId, boolean autoArticleId,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
		int reviewDateDay, int reviewDateYear, int reviewDateHour,
		int reviewDateMinute, boolean neverReview, boolean indexable,
		java.lang.String articleURL, java.lang.String[] tagsEntries,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws java.rmi.RemoteException, com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.addArticle(groupId,
				articleId, autoArticleId, title, description, content, type,
				structureId, templateId, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, neverReview, indexable, articleURL,
				tagsEntries, addCommunityPermissions, addGuestPermissions);

		return JournalArticleJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject addArticle(long groupId,
		java.lang.String articleId, boolean autoArticleId,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
		int reviewDateDay, int reviewDateYear, int reviewDateHour,
		int reviewDateMinute, boolean neverReview, boolean indexable,
		java.lang.String articleURL, java.lang.String[] tagsEntries,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws java.rmi.RemoteException, com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.addArticle(groupId,
				articleId, autoArticleId, title, description, content, type,
				structureId, templateId, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, neverReview, indexable, articleURL,
				tagsEntries, communityPermissions, guestPermissions);

		return JournalArticleJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject copyArticle(long groupId,
		java.lang.String oldArticleId, java.lang.String newArticleId,
		boolean autoArticleId, double version)
		throws java.rmi.RemoteException, com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.copyArticle(groupId,
				oldArticleId, newArticleId, autoArticleId, version);

		return JournalArticleJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getArticle(long groupId,
		java.lang.String articleId, double version)
		throws java.rmi.RemoteException, com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.getArticle(groupId,
				articleId, version);

		return JournalArticleJSONSerializer.toJSONObject(returnValue);
	}

	public static void removeArticleLocale(long companyId,
		java.lang.String languageId)
		throws java.rmi.RemoteException, com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleServiceUtil.removeArticleLocale(companyId, languageId);
	}

	public static JSONObject removeArticleLocale(long groupId,
		java.lang.String articleId, double version, java.lang.String languageId)
		throws java.rmi.RemoteException, com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.removeArticleLocale(groupId,
				articleId, version, languageId);

		return JournalArticleJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateContent(long groupId,
		java.lang.String articleId, double version, java.lang.String content)
		throws java.rmi.RemoteException, com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portlet.journal.model.JournalArticle returnValue = JournalArticleServiceUtil.updateContent(groupId,
				articleId, version, content);

		return JournalArticleJSONSerializer.toJSONObject(returnValue);
	}
}