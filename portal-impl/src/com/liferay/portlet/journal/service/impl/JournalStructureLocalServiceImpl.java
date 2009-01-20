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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portlet.journal.DuplicateStructureIdException;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.RequiredStructureException;
import com.liferay.portlet.journal.StructureDescriptionException;
import com.liferay.portlet.journal.StructureIdException;
import com.liferay.portlet.journal.StructureInheritanceException;
import com.liferay.portlet.journal.StructureNameException;
import com.liferay.portlet.journal.StructureXsdException;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.impl.JournalStructureImpl;
import com.liferay.portlet.journal.service.base.JournalStructureLocalServiceBaseImpl;
import com.liferay.portlet.journal.util.JournalUtil;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <a href="JournalStructureLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalStructureLocalServiceImpl
	extends JournalStructureLocalServiceBaseImpl {

	public JournalStructure addStructure(
			long userId, long groupId, String structureId,
			boolean autoStructureId, String parentStructureId, String name,
			String description, String xsd, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addStructure(
			null, userId, groupId, structureId, autoStructureId,
			parentStructureId, name, description, xsd,
			Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public JournalStructure addStructure(
			String uuid, long userId, long groupId, String structureId,
			boolean autoStructureId, String parentStructureId, String name,
			String description, String xsd, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addStructure(
			uuid, userId, groupId, structureId, autoStructureId,
			parentStructureId, name, description, xsd,
			Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public JournalStructure addStructure(
			long userId, long groupId, String structureId,
			boolean autoStructureId, String parentStructureId, String name,
			String description, String xsd, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		return addStructure(
			null, userId, groupId, structureId, autoStructureId,
			parentStructureId, name, description, xsd, null, null,
			communityPermissions, guestPermissions);
	}

	public JournalStructure addStructure(
			String uuid, long userId, long groupId, String structureId,
			boolean autoStructureId, String parentStructureId,
			String name, String description, String xsd,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// Structure

		User user = userPersistence.findByPrimaryKey(userId);
		structureId = structureId.trim().toUpperCase();
		Date now = new Date();

		try {
			xsd = JournalUtil.formatXML(xsd);
		}
		catch (Exception e) {
			throw new StructureXsdException();
		}

		validate(
			groupId, structureId, autoStructureId, parentStructureId, name,
			description, xsd);

		if (autoStructureId) {
			structureId = String.valueOf(counterLocalService.increment());
		}

		long id = counterLocalService.increment();

		JournalStructure structure = journalStructurePersistence.create(id);

		structure.setUuid(uuid);
		structure.setGroupId(groupId);
		structure.setCompanyId(user.getCompanyId());
		structure.setUserId(user.getUserId());
		structure.setUserName(user.getFullName());
		structure.setCreateDate(now);
		structure.setModifiedDate(now);
		structure.setStructureId(structureId);
		structure.setParentStructureId(parentStructureId);
		structure.setName(name);
		structure.setDescription(description);
		structure.setXsd(xsd);

		journalStructurePersistence.update(structure, false);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addStructureResources(
				structure, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addStructureResources(
				structure, communityPermissions, guestPermissions);
		}

		return structure;
	}

	public void addStructureResources(
			long groupId, String structureId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		JournalStructure structure = journalStructurePersistence.findByG_S(
			groupId, structureId);

		addStructureResources(
			structure, addCommunityPermissions, addGuestPermissions);
	}

	public void addStructureResources(
			JournalStructure structure, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			structure.getCompanyId(), structure.getGroupId(),
			structure.getUserId(), JournalStructure.class.getName(),
			structure.getId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addStructureResources(
			long groupId, String structureId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		JournalStructure structure = journalStructurePersistence.findByG_S(
			groupId, structureId);

		addStructureResources(
			structure, communityPermissions, guestPermissions);
	}

	public void addStructureResources(
			JournalStructure structure, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			structure.getCompanyId(), structure.getGroupId(),
			structure.getUserId(), JournalStructure.class.getName(),
			structure.getId(), communityPermissions, guestPermissions);
	}

	public void checkNewLine(long groupId, String structureId)
		throws PortalException, SystemException {

		JournalStructure structure = journalStructurePersistence.findByG_S(
			groupId, structureId);

		String xsd = structure.getXsd();

		if ((xsd != null) && (xsd.indexOf("\\n") != -1)) {
			xsd = StringUtil.replace(
				xsd,
				new String[] {"\\n", "\\r"},
				new String[] {"\n", "\r"});

			structure.setXsd(xsd);

			journalStructurePersistence.update(structure, false);
		}
	}

	public JournalStructure copyStructure(
			long userId, long groupId, String oldStructureId,
			String newStructureId, boolean autoStructureId)
		throws PortalException, SystemException {

		// Structure

		User user = userPersistence.findByPrimaryKey(userId);
		oldStructureId = oldStructureId.trim().toUpperCase();
		newStructureId = newStructureId.trim().toUpperCase();
		Date now = new Date();

		JournalStructure oldStructure = journalStructurePersistence.findByG_S(
			groupId, oldStructureId);

		if (autoStructureId) {
			newStructureId = String.valueOf(counterLocalService.increment());
		}
		else {
			validateStructureId(newStructureId);

			JournalStructure newStructure =
				journalStructurePersistence.fetchByG_S(groupId, newStructureId);

			if (newStructure != null) {
				throw new DuplicateStructureIdException();
			}
		}

		long id = counterLocalService.increment();

		JournalStructure newStructure = journalStructurePersistence.create(id);

		newStructure.setGroupId(groupId);
		newStructure.setCompanyId(user.getCompanyId());
		newStructure.setUserId(user.getUserId());
		newStructure.setUserName(user.getFullName());
		newStructure.setCreateDate(now);
		newStructure.setModifiedDate(now);
		newStructure.setStructureId(newStructureId);
		newStructure.setName(oldStructure.getName());
		newStructure.setDescription(oldStructure.getDescription());
		newStructure.setXsd(oldStructure.getXsd());

		journalStructurePersistence.update(newStructure, false);

		// Resources

		addStructureResources(newStructure, true, true);

		return newStructure;
	}

	public void deleteStructure(long groupId, String structureId)
		throws PortalException, SystemException {

		structureId = structureId.trim().toUpperCase();

		JournalStructure structure = journalStructurePersistence.findByG_S(
			groupId, structureId);

		deleteStructure(structure);
	}

	public void deleteStructure(JournalStructure structure)
		throws PortalException, SystemException {

		if (journalArticlePersistence.countByG_S(
				structure.getGroupId(), structure.getStructureId()) > 0) {

			throw new RequiredStructureException();
		}

		if (journalStructurePersistence.countByG_P(
				structure.getGroupId(), structure.getStructureId()) > 0) {

			throw new RequiredStructureException();
		}

		if (journalTemplatePersistence.countByG_S(
				structure.getGroupId(), structure.getStructureId()) > 0) {

			throw new RequiredStructureException();
		}

		// WebDAVProps

		webDAVPropsLocalService.deleteWebDAVProps(
			JournalStructure.class.getName(), structure.getPrimaryKey());

		// Resources

		resourceLocalService.deleteResource(
			structure.getCompanyId(), JournalStructure.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, structure.getId());

		// Structure

		journalStructurePersistence.remove(structure);
	}

	public void deleteStructures(long groupId)
		throws PortalException, SystemException {

		for (JournalStructure structure :
				journalStructurePersistence.findByGroupId(groupId)) {

			deleteStructure(structure);
		}
	}

	public JournalStructure getStructure(long id)
		throws PortalException, SystemException {

		return journalStructurePersistence.findByPrimaryKey(id);
	}

	public JournalStructure getStructure(long groupId, String structureId)
		throws PortalException, SystemException {

		structureId = structureId.trim().toUpperCase();

		if (groupId == 0) {
			_log.error(
				"No group id was passed for " + structureId + ". Group id is " +
					"required since 4.2.0. Please update all custom code and " +
						"data that references structures without a group id.");

			List<JournalStructure> structures =
				journalStructurePersistence.findByStructureId(structureId);

			if (structures.size() == 0) {
				throw new NoSuchStructureException(
					"No JournalStructure exists with the structure id " +
						structureId);
			}
			else {
				return structures.get(0);
			}
		}
		else {
			return journalStructurePersistence.findByG_S(groupId, structureId);
		}
	}

	public List<JournalStructure> getStructures() throws SystemException {
		return journalStructurePersistence.findAll();
	}

	public List<JournalStructure> getStructures(long groupId)
		throws SystemException {

		return journalStructurePersistence.findByGroupId(groupId);
	}

	public List<JournalStructure> getStructures(
			long groupId, int start, int end)
		throws SystemException {

		return journalStructurePersistence.findByGroupId(groupId, start, end);
	}

	public int getStructuresCount(long groupId) throws SystemException {
		return journalStructurePersistence.countByGroupId(groupId);
	}

	public List<JournalStructure> search(
			long companyId, long groupId, String keywords, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalStructureFinder.findByKeywords(
			companyId, groupId, keywords, start, end, obc);
	}

	public List<JournalStructure> search(
			long companyId, long groupId, String structureId, String name,
			String description, boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalStructureFinder.findByC_G_S_N_D(
			companyId, groupId, structureId, name, description, andOperator,
			start, end, obc);
	}

	public int searchCount(long companyId, long groupId, String keywords)
		throws SystemException {

		return journalStructureFinder.countByKeywords(
			companyId, groupId, keywords);
	}

	public int searchCount(
			long companyId, long groupId, String structureId, String name,
			String description, boolean andOperator)
		throws SystemException {

		return journalStructureFinder.countByC_G_S_N_D(
			companyId, groupId, structureId, name, description, andOperator);
	}

	public JournalStructure updateStructure(
			long groupId, String structureId, String parentStructureId,
			String name, String description, String xsd)
		throws PortalException, SystemException {

		structureId = structureId.trim().toUpperCase();

		try {
			xsd = JournalUtil.formatXML(xsd);
		}
		catch (Exception e) {
			throw new StructureXsdException();
		}

		validateParentStructureId(groupId, structureId, parentStructureId);
		validate(name, description, xsd);

		JournalStructure structure = journalStructurePersistence.findByG_S(
			groupId, structureId);

		structure.setModifiedDate(new Date());
		structure.setParentStructureId(parentStructureId);
		structure.setName(name);
		structure.setDescription(description);
		structure.setXsd(xsd);

		journalStructurePersistence.update(structure, false);

		return structure;
	}

	protected void validate(
			long groupId, String structureId, boolean autoStructureId,
			String parentStructureId, String name, String description,
			String xsd)
		throws PortalException, SystemException {

		if (!autoStructureId) {
			validateStructureId(structureId);

			JournalStructure structure = journalStructurePersistence.fetchByG_S(
				groupId, structureId);

			if (structure != null) {
				throw new DuplicateStructureIdException();
			}
		}

		validateParentStructureId(groupId, structureId, parentStructureId);
		validate(name, description, xsd);
	}

	protected void validate(String name, String description, String xsd)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new StructureNameException();
		}
		else if (Validator.isNull(description)) {
			throw new StructureDescriptionException();
		}

		if (Validator.isNull(xsd)) {
			throw new StructureXsdException();
		}
		else {
			try {
				Document doc = SAXReaderUtil.read(xsd);

				Element root = doc.getRootElement();

				List<Element> children = root.elements();

				if (children.size() == 0) {
					throw new StructureXsdException();
				}

				Set<String> elNames = new HashSet<String>();

				validate(children, elNames);
			}
			catch (Exception e) {
				throw new StructureXsdException();
			}
		}
	}

	protected void validate(List<Element> children, Set<String> elNames)
		throws PortalException {

		for (Element el : children) {
			String elName = el.attributeValue("name", StringPool.BLANK);
			String elType = el.attributeValue("type", StringPool.BLANK);

			if (Validator.isNull(elName) ||
				elName.startsWith(JournalStructureImpl.RESERVED)) {

				throw new StructureXsdException();
			}
			else {
				char[] c = elName.toCharArray();

				for (int i = 0; i < c.length; i++) {
					if ((!Validator.isChar(c[i])) &&
						(!Validator.isDigit(c[i])) && (c[i] != CharPool.DASH) &&
						(c[i] != CharPool.UNDERLINE)) {

						throw new StructureXsdException();
					}
				}

				String completePath = elName;

				Element parent = el.getParent();

				while (!parent.isRootElement()) {
					completePath =
						parent.attributeValue("name", StringPool.BLANK) +
							StringPool.SLASH + completePath;

					parent = parent.getParent();
				}

				String elNameLowerCase = completePath.toLowerCase();

				if (elNames.contains(elNameLowerCase)) {
					throw new StructureXsdException();
				}
				else {
					elNames.add(elNameLowerCase);
				}
			}

			if (Validator.isNull(elType)) {
				throw new StructureXsdException();
			}

			validate(el.elements(), elNames);
		}
	}

	protected void validateParentStructureId(
			long groupId, String structureId, String parentStructureId)
		throws PortalException, SystemException {

		if (Validator.isNull(parentStructureId)) {
			return;
		}

		if (parentStructureId.equals(structureId)) {
			throw new StructureInheritanceException();
		}

		JournalStructure parentStructure =
			journalStructurePersistence.fetchByG_S(groupId, parentStructureId);

		while (parentStructure != null) {
			if ((parentStructure != null) &&
				(parentStructure.getStructureId().equals(structureId)) ||
				(parentStructure.getParentStructureId().equals(
					structureId))) {

				throw new StructureInheritanceException();
			}

			parentStructure = journalStructurePersistence.fetchByG_S(
				groupId, parentStructure.getParentStructureId());
		}
	}

	protected void validateStructureId(String structureId)
		throws PortalException {

		if ((Validator.isNull(structureId)) ||
			(Validator.isNumber(structureId)) ||
			(structureId.indexOf(StringPool.SPACE) != -1)) {

			throw new StructureIdException();
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(JournalStructureLocalServiceImpl.class);

}