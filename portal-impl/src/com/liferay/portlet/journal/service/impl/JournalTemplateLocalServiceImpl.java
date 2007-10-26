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

package com.liferay.portlet.journal.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.impl.ImageLocalUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.journal.DuplicateTemplateIdException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.RequiredTemplateException;
import com.liferay.portlet.journal.TemplateDescriptionException;
import com.liferay.portlet.journal.TemplateIdException;
import com.liferay.portlet.journal.TemplateNameException;
import com.liferay.portlet.journal.TemplateSmallImageNameException;
import com.liferay.portlet.journal.TemplateSmallImageSizeException;
import com.liferay.portlet.journal.TemplateXslException;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.journal.service.base.JournalTemplateLocalServiceBaseImpl;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplateFinder;
import com.liferay.portlet.journal.service.persistence.JournalTemplateUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.DocumentException;

/**
 * <a href="JournalTemplateLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalTemplateLocalServiceImpl
	extends JournalTemplateLocalServiceBaseImpl {

	public JournalTemplate addTemplate(
			long userId, String templateId, boolean autoTemplateId, long plid,
			String structureId, String name, String description, String xsl,
			boolean formatXsl, String langType, boolean smallImage,
			String smallImageURL, File smallFile,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addTemplate(
			userId, templateId, autoTemplateId, plid, structureId, name,
			description, xsl, formatXsl, langType, smallImage, smallImageURL,
			smallFile, Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public JournalTemplate addTemplate(
			long userId, String templateId, boolean autoTemplateId, long plid,
			String structureId, String name, String description, String xsl,
			boolean formatXsl, String langType, boolean smallImage,
			String smallImageURL, File smallFile, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		return addTemplate(
			userId, templateId, autoTemplateId, plid, structureId, name,
			description, xsl, formatXsl, langType, smallImage, smallImageURL,
			smallFile, null, null, communityPermissions, guestPermissions);
	}

	public JournalTemplate addTemplate(
			long userId, String templateId, boolean autoTemplateId, long plid,
			String structureId, String name, String description, String xsl,
			boolean formatXsl, String langType, boolean smallImage,
			String smallImageURL, File smallFile,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		long groupId = PortalUtil.getPortletGroupId(plid);

		return addTemplateToGroup(
			userId, templateId, autoTemplateId, groupId, structureId, name,
			description, xsl, formatXsl, langType, smallImage, smallImageURL,
			smallFile, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public JournalTemplate addTemplateToGroup(
			long userId, String templateId, boolean autoTemplateId,
			long groupId, String structureId, String name, String description,
			String xsl, boolean formatXsl, String langType, boolean smallImage,
			String smallImageURL, File smallFile,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// Template

		User user = UserUtil.findByPrimaryKey(userId);
		templateId = templateId.trim().toUpperCase();
		Date now = new Date();

		try {
			if (formatXsl) {
				if (langType.equals(JournalTemplateImpl.LANG_TYPE_VM)) {
					xsl = JournalUtil.formatVM(xsl);
				}
				else {
					xsl = JournalUtil.formatXML(xsl);
				}
			}
		}
		catch (DocumentException de) {
			throw new TemplateXslException();
		}
		catch (IOException ioe) {
			throw new TemplateXslException();
		}

		byte[] smallBytes = null;

		try {
			smallBytes = FileUtil.getBytes(smallFile);
		}
		catch (IOException ioe) {
		}

		validate(
			groupId, templateId, autoTemplateId, name, description, xsl,
			smallImage, smallImageURL, smallFile, smallBytes);

		if (autoTemplateId) {
			templateId = String.valueOf(CounterLocalServiceUtil.increment());
		}

		long id = CounterLocalServiceUtil.increment();

		JournalTemplate template = JournalTemplateUtil.create(id);

		template.setGroupId(groupId);
		template.setCompanyId(user.getCompanyId());
		template.setUserId(user.getUserId());
		template.setUserName(user.getFullName());
		template.setCreateDate(now);
		template.setModifiedDate(now);
		template.setTemplateId(templateId);
		template.setStructureId(structureId);
		template.setName(name);
		template.setDescription(description);
		template.setXsl(xsl);
		template.setLangType(langType);
		template.setSmallImage(smallImage);
		template.setSmallImageId(CounterLocalServiceUtil.increment());
		template.setSmallImageURL(smallImageURL);

		JournalTemplateUtil.update(template);

		// Small image

		saveImages(
			smallImage, template.getSmallImageId(), smallFile, smallBytes);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addTemplateResources(
				template, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addTemplateResources(
				template, communityPermissions, guestPermissions);
		}

		return template;
	}

	public void addTemplateResources(
			long groupId, String templateId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		JournalTemplate template = JournalTemplateUtil.findByG_T(
			groupId, templateId);

		addTemplateResources(
			template, addCommunityPermissions, addGuestPermissions);
	}

	public void addTemplateResources(
			JournalTemplate template, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			template.getCompanyId(), template.getGroupId(),
			template.getUserId(), JournalTemplate.class.getName(),
			template.getId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addTemplateResources(
			long groupId, String templateId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		JournalTemplate template = JournalTemplateUtil.findByG_T(
			groupId, templateId);

		addTemplateResources(template, communityPermissions, guestPermissions);
	}

	public void addTemplateResources(
			JournalTemplate template, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			template.getCompanyId(), template.getGroupId(),
			template.getUserId(), JournalTemplate.class.getName(),
			template.getId(), communityPermissions, guestPermissions);
	}

	public void checkNewLine(long groupId, String templateId)
		throws PortalException, SystemException {

		JournalTemplate template = JournalTemplateUtil.findByG_T(
			groupId, templateId);

		String xsl = template.getXsl();

		if ((xsl != null) && (xsl.indexOf("\\n") != -1)) {
			xsl = StringUtil.replace(
				xsl,
				new String[] {"\\n", "\\r"},
				new String[] {"\n", "\r"});

			template.setXsl(xsl);

			JournalTemplateUtil.update(template);
		}
	}

	public void deleteTemplate(long groupId, String templateId)
		throws PortalException, SystemException {

		templateId = templateId.trim().toUpperCase();

		JournalTemplate template = JournalTemplateUtil.findByG_T(
			groupId, templateId);

		deleteTemplate(template);
	}

	public void deleteTemplate(JournalTemplate template)
		throws PortalException, SystemException {

		if (JournalArticleUtil.countByG_T(
				template.getGroupId(), template.getTemplateId()) > 0) {

			throw new RequiredTemplateException();
		}

		// Small image

		ImageLocalUtil.deleteImage(template.getSmallImageId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			template.getCompanyId(), JournalTemplate.class.getName(),
			ResourceImpl.SCOPE_INDIVIDUAL, template.getId());

		// Template

		JournalTemplateUtil.remove(template.getPrimaryKey());
	}

	public List getStructureTemplates(long groupId, String structureId)
		throws SystemException {

		return JournalTemplateUtil.findByG_S(groupId, structureId);
	}

	public List getStructureTemplates(
			long groupId, String structureId, int begin, int end)
		throws SystemException {

		return JournalTemplateUtil.findByG_S(groupId, structureId, begin, end);
	}

	public int getStructureTemplatesCount(long groupId, String structureId)
		throws SystemException {

		return JournalTemplateUtil.countByG_S(groupId, structureId);
	}

	public JournalTemplate getTemplate(long id)
		throws PortalException, SystemException {

		return JournalTemplateUtil.findByPrimaryKey(id);
	}

	public JournalTemplate getTemplate(long groupId, String templateId)
		throws PortalException, SystemException {

		templateId = templateId.trim().toUpperCase();

		if (groupId == 0) {
			_log.error(
				"No group id was passed for " + templateId + ". Group id is " +
					"required since 4.2.0. Please update all custom code and " +
						"data that references templates without a group id.");

			List templates = JournalTemplateUtil.findByTemplateId(templateId);

			if (templates.size() == 0) {
				throw new NoSuchTemplateException(
					"No JournalTemplate exists with the template id " +
						templateId);
			}
			else {
				return (JournalTemplate)templates.get(0);
			}
		}
		else {
			return JournalTemplateUtil.findByG_T(groupId, templateId);
		}
	}

	public List getTemplates() throws SystemException {
		return JournalTemplateUtil.findAll();
	}

	public List getTemplates(long groupId) throws SystemException {
		return JournalTemplateUtil.findByGroupId(groupId);
	}

	public List getTemplates(long groupId, int begin, int end)
		throws SystemException {

		return JournalTemplateUtil.findByGroupId(groupId, begin, end);
	}

	public int getTemplatesCount(long groupId) throws SystemException {
		return JournalTemplateUtil.countByGroupId(groupId);
	}

	public boolean hasTemplate(long groupId, String templateId)
		throws SystemException {

		try {
			getTemplate(groupId, templateId);

			return true;
		}
		catch (PortalException pe) {
			return false;
		}
	}

	public List search(
			long companyId, long groupId, String keywords, String structureId,
			String structureIdComparator, int begin, int end,
			OrderByComparator obc)
		throws SystemException {

		return JournalTemplateFinder.findByKeywords(
			companyId, groupId, keywords, structureId, structureIdComparator,
			begin, end, obc);
	}

	public List search(
			long companyId, long groupId, String templateId, String structureId,
			String structureIdComparator, String name, String description,
			boolean andOperator, int begin, int end, OrderByComparator obc)
		throws SystemException {

		return JournalTemplateFinder.findByC_G_T_S_N_D(
			companyId, groupId, templateId, structureId, structureIdComparator,
			name, description, andOperator, begin, end, obc);
	}

	public int searchCount(
			long companyId, long groupId, String keywords, String structureId,
			String structureIdComparator)
		throws SystemException {

		return JournalTemplateFinder.countByKeywords(
			companyId, groupId, keywords, structureId, structureIdComparator);
	}

	public int searchCount(
			long companyId, long groupId, String templateId, String structureId,
			String structureIdComparator, String name, String description,
			boolean andOperator)
		throws SystemException {

		return JournalTemplateFinder.countByC_G_T_S_N_D(
			companyId, groupId, templateId, structureId, structureIdComparator,
			name, description, andOperator);
	}

	public JournalTemplate updateTemplate(
			long groupId, String templateId, String structureId, String name,
			String description, String xsl, boolean formatXsl, String langType,
			boolean smallImage, String smallImageURL, File smallFile)
		throws PortalException, SystemException {

		// Template

		templateId = templateId.trim().toUpperCase();

		try {
			if (formatXsl) {
				if (langType.equals(JournalTemplateImpl.LANG_TYPE_VM)) {
					xsl = JournalUtil.formatVM(xsl);
				}
				else {
					xsl = JournalUtil.formatXML(xsl);
				}
			}
		}
		catch (DocumentException de) {
			throw new TemplateXslException();
		}
		catch (IOException ioe) {
			throw new TemplateXslException();
		}

		byte[] smallBytes = null;

		try {
			smallBytes = FileUtil.getBytes(smallFile);
		}
		catch (IOException ioe) {
		}

		validate(
			name, description, xsl, smallImage, smallImageURL, smallFile,
			smallBytes);

		JournalTemplate template = JournalTemplateUtil.findByG_T(
			groupId, templateId);

		template.setModifiedDate(new Date());

		if (Validator.isNull(template.getStructureId()) &&
			Validator.isNotNull(structureId)) {

			// Allow users to set the structure if and only if it currently
			// does not have one. Otherwise, you can have bad data because there
			// may be an existing article that has chosen to use a structure and
			// template combination that no longer exists.

			template.setStructureId(structureId);
		}

		template.setName(name);
		template.setDescription(description);
		template.setXsl(xsl);
		template.setLangType(langType);
		template.setSmallImage(smallImage);
		template.setSmallImageURL(smallImageURL);

		JournalTemplateUtil.update(template);

		// Small image

		saveImages(
			smallImage, template.getSmallImageId(), smallFile, smallBytes);

		return template;
	}

	protected void saveImages(
			boolean smallImage, long smallImageId, File smallFile,
			byte[] smallBytes)
		throws SystemException {

		if (smallImage) {
			if ((smallFile != null) && (smallBytes != null)) {
				ImageLocalUtil.updateImage(smallImageId, smallBytes);
			}
		}
		else {
			ImageLocalUtil.deleteImage(smallImageId);
		}
	}

	protected void validate(
			long groupId, String templateId, boolean autoTemplateId,
			String name, String description, String xsl, boolean smallImage,
			String smallImageURL, File smallFile, byte[] smallBytes)
		throws PortalException, SystemException {

		if (!autoTemplateId) {
			if ((Validator.isNull(templateId)) ||
				(Validator.isNumber(templateId)) ||
				(templateId.indexOf(StringPool.SPACE) != -1)) {

				throw new TemplateIdException();
			}

			try {
				JournalTemplateUtil.findByG_T(groupId, templateId);

				throw new DuplicateTemplateIdException();
			}
			catch (NoSuchTemplateException nste) {
			}
		}

		validate(
			name, description, xsl, smallImage, smallImageURL, smallFile,
			smallBytes);
	}

	protected void validate(
			String name, String description, String xsl, boolean smallImage,
			String smallImageURL, File smallFile, byte[] smallBytes)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new TemplateNameException();
		}
		else if (Validator.isNull(description)) {
			throw new TemplateDescriptionException();
		}
		else if (Validator.isNull(xsl)) {
			throw new TemplateXslException();
		}

		String[] imageExtensions =
			PropsUtil.getArray(PropsUtil.JOURNAL_IMAGE_EXTENSIONS);

		if (smallImage && Validator.isNull(smallImageURL) &&
			smallFile != null && smallBytes != null) {

			String smallImageName = smallFile.getName();

			if (smallImageName != null) {
				boolean validSmallImageExtension = false;

				for (int i = 0; i < imageExtensions.length; i++) {
					if (StringPool.STAR.equals(imageExtensions[i]) ||
						StringUtil.endsWith(
							smallImageName, imageExtensions[i])) {

						validSmallImageExtension = true;

						break;
					}
				}

				if (!validSmallImageExtension) {
					throw new TemplateSmallImageNameException(smallImageName);
				}
			}

			long smallImageMaxSize = GetterUtil.getLong(
				PropsUtil.get(PropsUtil.JOURNAL_IMAGE_SMALL_MAX_SIZE));

			if ((smallImageMaxSize > 0) &&
				((smallBytes == null) ||
					(smallBytes.length > smallImageMaxSize))) {

				throw new TemplateSmallImageSizeException();
			}
		}
	}

	private static Log _log =
		LogFactory.getLog(JournalTemplateLocalServiceImpl.class);

}