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

package com.liferay.portlet.journal.service;


/**
 * <a href="JournalTemplateLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.portlet.journal.service.impl.JournalTemplateLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.JournalTemplateLocalServiceFactory
 * @see com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil
 *
 */
public interface JournalTemplateLocalService {
	public com.liferay.portlet.journal.model.JournalTemplate addJournalTemplate(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate)
		throws com.liferay.portal.SystemException;

	public void deleteJournalTemplate(long id)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteJournalTemplate(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalTemplate getJournalTemplate(
		long id)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portlet.journal.model.JournalTemplate updateJournalTemplate(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		long userId, java.lang.String templateId, boolean autoTemplateId,
		long plid, java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsl, boolean formatXsl,
		java.lang.String langType, boolean cacheable, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		java.lang.String uuid, long userId, java.lang.String templateId,
		boolean autoTemplateId, long plid, java.lang.String structureId,
		java.lang.String name, java.lang.String description,
		java.lang.String xsl, boolean formatXsl, java.lang.String langType,
		boolean cacheable, boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		long userId, java.lang.String templateId, boolean autoTemplateId,
		long plid, java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsl, boolean formatXsl,
		java.lang.String langType, boolean cacheable, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		java.lang.String uuid, long userId, java.lang.String templateId,
		boolean autoTemplateId, long plid, java.lang.String structureId,
		java.lang.String name, java.lang.String description,
		java.lang.String xsl, boolean formatXsl, java.lang.String langType,
		boolean cacheable, boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portlet.journal.model.JournalTemplate addTemplateToGroup(
		java.lang.String uuid, long userId, java.lang.String templateId,
		boolean autoTemplateId, long groupId, java.lang.String structureId,
		java.lang.String name, java.lang.String description,
		java.lang.String xsl, boolean formatXsl, java.lang.String langType,
		boolean cacheable, boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void addTemplateResources(long groupId, java.lang.String templateId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void addTemplateResources(
		com.liferay.portlet.journal.model.JournalTemplate template,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void addTemplateResources(long groupId, java.lang.String templateId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void addTemplateResources(
		com.liferay.portlet.journal.model.JournalTemplate template,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void checkNewLine(long groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteTemplate(long groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteTemplate(
		com.liferay.portlet.journal.model.JournalTemplate template)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteTemplates(long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getStructureTemplates(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getStructureTemplates(
		long groupId, java.lang.String structureId, int start, int end)
		throws com.liferay.portal.SystemException;

	public int getStructureTemplatesCount(long groupId,
		java.lang.String structureId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalTemplate getTemplate(
		long id)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portlet.journal.model.JournalTemplate getTemplate(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portlet.journal.model.JournalTemplate getTemplateBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getTemplates()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getTemplates(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getTemplates(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public int getTemplatesCount(long groupId)
		throws com.liferay.portal.SystemException;

	public boolean hasTemplate(long groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> search(
		long companyId, long groupId, java.lang.String keywords,
		java.lang.String structureId, java.lang.String structureIdComparator,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> search(
		long companyId, long groupId, java.lang.String templateId,
		java.lang.String structureId, java.lang.String structureIdComparator,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int searchCount(long companyId, long groupId,
		java.lang.String keywords, java.lang.String structureId,
		java.lang.String structureIdComparator)
		throws com.liferay.portal.SystemException;

	public int searchCount(long companyId, long groupId,
		java.lang.String templateId, java.lang.String structureId,
		java.lang.String structureIdComparator, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalTemplate updateTemplate(
		long groupId, java.lang.String templateId,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsl, boolean formatXsl,
		java.lang.String langType, boolean cacheable, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;
}