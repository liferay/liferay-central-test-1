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

package com.liferay.portlet.enterpriseadmin.util;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.util.List;

import javax.portlet.PortletURL;

/**
 * <a href="UserIndexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class UserIndexer implements Indexer {

	public static final String PORTLET_ID = PortletKeys.ENTERPRISE_ADMIN_USERS;

	public static void deleteUser(long companyId, long userId)
		throws SearchException {

		SearchEngineUtil.deleteDocument(companyId, getUserUID(userId));
	}

	public static Document getUserDocument(
		long companyId, long userId, String screenName, String emailAddress,
		String firstName, String middleName, String lastName, String jobTitle,
		boolean active, long[] groupIds, long[] organizationIds,
		long[] roleIds, long[] userGroupIds, ExpandoBridge expandoBridge) {

		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, String.valueOf(userId));

		doc.addModifiedDate();

		doc.addKeyword(Field.COMPANY_ID, companyId);
		doc.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		doc.addKeyword(Field.USER_ID, userId);

		doc.addKeyword("screenName", screenName);
		doc.addKeyword("emailAddress", emailAddress);
		doc.addText("firstName", firstName);
		doc.addText("middleName", middleName);
		doc.addText("lastName", lastName);
		doc.addText("jobTitle", jobTitle);
		doc.addKeyword("active", active);
		doc.addKeyword(Field.GROUP_IDS, groupIds);
		doc.addKeyword(Field.ORGANIZATION_IDS, organizationIds);
		doc.addKeyword(Field.ROLE_IDS, roleIds);
		doc.addKeyword(Field.USER_GROUP_IDS, userGroupIds);

		ExpandoBridgeIndexerUtil.addAttributes(doc, expandoBridge);

		return doc;
	}

	public static String getUserUID(long userId) {
		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, String.valueOf(userId));

		return doc.get(Field.UID);
	}

	public static void updateUser(User user) throws SearchException {
		try {
			if (user.isDefaultUser()) {
				return;
			}

			Contact contact = user.getContact();

			Document doc = getUserDocument(
				user.getCompanyId(), user.getUserId(), user.getScreenName(),
				user.getEmailAddress(), contact.getFirstName(),
				contact.getMiddleName(), contact.getLastName(),
				contact.getJobTitle(), user.getActive(),
				user.getGroupIds(), user.getOrganizationIds(),
				user.getRoleIds(), user.getUserGroupIds(),
				user.getExpandoBridge());

			SearchEngineUtil.updateDocument(
				user.getCompanyId(), doc.get(Field.UID), doc);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public static void updateUsers(long[] userIds) throws SearchException {
		for (long userId : userIds) {
			try {
				User user = UserLocalServiceUtil.getUserById(userId);

				updateUser(user);
			}
			catch (Exception e) {
				throw new SearchException(e);
			}
		}
	}

	public static void updateUsers(List<User> users) throws SearchException {
		for (User user : users) {
			updateUser(user);
		}
	}

	public DocumentSummary getDocumentSummary(
		com.liferay.portal.kernel.search.Document doc, PortletURL portletURL) {

		// Title

		String firstName = doc.get("firstName");
		String middleName = doc.get("middleName");
		String lastName = doc.get("lastName");

		String title = ContactConstants.getFullName(
			firstName, middleName, lastName);

		// Content

		String content = null;

		// Portlet URL

		String userId = doc.get(Field.USER_ID);

		portletURL.setParameter("struts_action", "/enterprise_admin/edit_user");
		portletURL.setParameter("p_u_i_d", userId);

		return new DocumentSummary(title, content, portletURL);
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			UserLocalServiceUtil.reIndex(ids);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

}