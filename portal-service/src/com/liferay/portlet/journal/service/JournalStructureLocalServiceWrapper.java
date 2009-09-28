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

package com.liferay.portlet.journal.service;


/**
 * <a href="JournalStructureLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link JournalStructureLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalStructureLocalService
 * @generated
 */
public class JournalStructureLocalServiceWrapper
	implements JournalStructureLocalService {
	public JournalStructureLocalServiceWrapper(
		JournalStructureLocalService journalStructureLocalService) {
		_journalStructureLocalService = journalStructureLocalService;
	}

	public com.liferay.portlet.journal.model.JournalStructure addJournalStructure(
		com.liferay.portlet.journal.model.JournalStructure journalStructure)
		throws com.liferay.portal.SystemException {
		return _journalStructureLocalService.addJournalStructure(journalStructure);
	}

	public com.liferay.portlet.journal.model.JournalStructure createJournalStructure(
		long id) {
		return _journalStructureLocalService.createJournalStructure(id);
	}

	public void deleteJournalStructure(long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalStructureLocalService.deleteJournalStructure(id);
	}

	public void deleteJournalStructure(
		com.liferay.portlet.journal.model.JournalStructure journalStructure)
		throws com.liferay.portal.SystemException {
		_journalStructureLocalService.deleteJournalStructure(journalStructure);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _journalStructureLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _journalStructureLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portlet.journal.model.JournalStructure getJournalStructure(
		long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalStructureLocalService.getJournalStructure(id);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalStructure> getJournalStructures(
		int start, int end) throws com.liferay.portal.SystemException {
		return _journalStructureLocalService.getJournalStructures(start, end);
	}

	public int getJournalStructuresCount()
		throws com.liferay.portal.SystemException {
		return _journalStructureLocalService.getJournalStructuresCount();
	}

	public com.liferay.portlet.journal.model.JournalStructure updateJournalStructure(
		com.liferay.portlet.journal.model.JournalStructure journalStructure)
		throws com.liferay.portal.SystemException {
		return _journalStructureLocalService.updateJournalStructure(journalStructure);
	}

	public com.liferay.portlet.journal.model.JournalStructure updateJournalStructure(
		com.liferay.portlet.journal.model.JournalStructure journalStructure,
		boolean merge) throws com.liferay.portal.SystemException {
		return _journalStructureLocalService.updateJournalStructure(journalStructure,
			merge);
	}

	public com.liferay.portlet.journal.model.JournalStructure addStructure(
		long userId, long groupId, java.lang.String structureId,
		boolean autoStructureId, java.lang.String parentStructureId,
		java.lang.String name, java.lang.String description,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalStructureLocalService.addStructure(userId, groupId,
			structureId, autoStructureId, parentStructureId, name, description,
			xsd, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalStructure addStructure(
		java.lang.String uuid, long userId, long groupId,
		java.lang.String structureId, boolean autoStructureId,
		java.lang.String parentStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalStructureLocalService.addStructure(uuid, userId,
			groupId, structureId, autoStructureId, parentStructureId, name,
			description, xsd, serviceContext);
	}

	public void addStructureResources(long groupId,
		java.lang.String structureId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalStructureLocalService.addStructureResources(groupId,
			structureId, addCommunityPermissions, addGuestPermissions);
	}

	public void addStructureResources(
		com.liferay.portlet.journal.model.JournalStructure structure,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalStructureLocalService.addStructureResources(structure,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addStructureResources(long groupId,
		java.lang.String structureId, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalStructureLocalService.addStructureResources(groupId,
			structureId, communityPermissions, guestPermissions);
	}

	public void addStructureResources(
		com.liferay.portlet.journal.model.JournalStructure structure,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalStructureLocalService.addStructureResources(structure,
			communityPermissions, guestPermissions);
	}

	public void checkNewLine(long groupId, java.lang.String structureId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalStructureLocalService.checkNewLine(groupId, structureId);
	}

	public com.liferay.portlet.journal.model.JournalStructure copyStructure(
		long userId, long groupId, java.lang.String oldStructureId,
		java.lang.String newStructureId, boolean autoStructureId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalStructureLocalService.copyStructure(userId, groupId,
			oldStructureId, newStructureId, autoStructureId);
	}

	public void deleteStructure(long groupId, java.lang.String structureId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalStructureLocalService.deleteStructure(groupId, structureId);
	}

	public void deleteStructure(
		com.liferay.portlet.journal.model.JournalStructure structure)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalStructureLocalService.deleteStructure(structure);
	}

	public void deleteStructures(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalStructureLocalService.deleteStructures(groupId);
	}

	public com.liferay.portlet.journal.model.JournalStructure getStructure(
		long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalStructureLocalService.getStructure(id);
	}

	public com.liferay.portlet.journal.model.JournalStructure getStructure(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalStructureLocalService.getStructure(groupId, structureId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalStructure> getStructures()
		throws com.liferay.portal.SystemException {
		return _journalStructureLocalService.getStructures();
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalStructure> getStructures(
		long groupId) throws com.liferay.portal.SystemException {
		return _journalStructureLocalService.getStructures(groupId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalStructure> getStructures(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _journalStructureLocalService.getStructures(groupId, start, end);
	}

	public int getStructuresCount(long groupId)
		throws com.liferay.portal.SystemException {
		return _journalStructureLocalService.getStructuresCount(groupId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalStructure> search(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _journalStructureLocalService.search(companyId, groupId,
			keywords, start, end, obc);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalStructure> search(
		long companyId, long groupId, java.lang.String structureId,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _journalStructureLocalService.search(companyId, groupId,
			structureId, name, description, andOperator, start, end, obc);
	}

	public int searchCount(long companyId, long groupId,
		java.lang.String keywords) throws com.liferay.portal.SystemException {
		return _journalStructureLocalService.searchCount(companyId, groupId,
			keywords);
	}

	public int searchCount(long companyId, long groupId,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return _journalStructureLocalService.searchCount(companyId, groupId,
			structureId, name, description, andOperator);
	}

	public com.liferay.portlet.journal.model.JournalStructure updateStructure(
		long groupId, java.lang.String structureId,
		java.lang.String parentStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalStructureLocalService.updateStructure(groupId,
			structureId, parentStructureId, name, description, xsd,
			serviceContext);
	}

	public JournalStructureLocalService getWrappedJournalStructureLocalService() {
		return _journalStructureLocalService;
	}

	private JournalStructureLocalService _journalStructureLocalService;
}