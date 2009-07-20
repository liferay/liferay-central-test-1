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

public interface Organization extends OrganizationModel {
	public java.util.List<com.liferay.portal.model.Organization> getAncestors()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Organization getParentOrganization()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Address getAddress();

	public java.util.List<com.liferay.portal.model.Address> getAddresses()
		throws com.liferay.portal.SystemException;

	public java.lang.String[] getChildrenTypes();

	public java.util.List<com.liferay.portal.model.Organization> getDescendants()
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Group getGroup();

	public long getLogoId();

	public javax.portlet.PortletPreferences getPreferences()
		throws com.liferay.portal.SystemException;

	public int getPrivateLayoutsPageCount();

	public int getPublicLayoutsPageCount();

	public java.util.Set<String> getReminderQueryQuestions(
		java.util.Locale locale) throws com.liferay.portal.SystemException;

	public java.util.Set<String> getReminderQueryQuestions(
		java.lang.String languageId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> getSuborganizations()
		throws com.liferay.portal.SystemException;

	public int getSuborganizationsSize()
		throws com.liferay.portal.SystemException;

	public int getTypeOrder();

	public boolean hasPrivateLayouts();

	public boolean hasPublicLayouts();

	public boolean hasSuborganizations()
		throws com.liferay.portal.SystemException;

	public boolean isParentable();

	public boolean isRoot();
}