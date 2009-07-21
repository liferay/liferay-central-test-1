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

package com.liferay.portal.model;

public interface User extends UserModel {
	public java.util.Date getBirthday();

	public java.lang.String getCompanyMx();

	public com.liferay.portal.model.Contact getContact();

	public java.lang.String getDisplayURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay);

	public java.lang.String getDisplayURL(java.lang.String portalURL,
		java.lang.String mainPath);

	public boolean getFemale();

	public java.lang.String getFullName();

	public com.liferay.portal.model.Group getGroup();

	public long[] getGroupIds();

	public java.util.List<com.liferay.portal.model.Group> getGroups();

	public java.util.Locale getLocale();

	public java.lang.String getLogin()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public boolean getMale();

	public java.util.List<com.liferay.portal.model.Group> getMyPlaces();

	public java.util.List<com.liferay.portal.model.Group> getMyPlaces(int max);

	public long[] getOrganizationIds();

	public java.util.List<com.liferay.portal.model.Organization> getOrganizations();

	public boolean getPasswordModified();

	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.lang.String getPasswordUnencrypted();

	public int getPrivateLayoutsPageCount();

	public int getPublicLayoutsPageCount();

	public java.util.Set<String> getReminderQueryQuestions()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public long[] getRoleIds();

	public java.util.List<com.liferay.portal.model.Role> getRoles();

	public long[] getUserGroupIds();

	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups();

	public java.util.TimeZone getTimeZone();

	public boolean hasCompanyMx();

	public boolean hasCompanyMx(java.lang.String emailAddress);

	public boolean hasMyPlaces();

	public boolean hasOrganization();

	public boolean hasPrivateLayouts();

	public boolean hasPublicLayouts();

	public boolean hasReminderQuery();

	public boolean isFemale();

	public boolean isMale();

	public boolean isPasswordModified();

	public void setLanguageId(java.lang.String languageId);

	public void setPasswordModified(boolean passwordModified);

	public void setPasswordUnencrypted(java.lang.String passwordUnencrypted);

	public void setTimeZoneId(java.lang.String timeZoneId);
}