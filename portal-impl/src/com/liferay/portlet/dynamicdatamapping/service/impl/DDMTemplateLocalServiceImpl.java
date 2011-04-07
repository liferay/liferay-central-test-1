/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMTemplateLocalServiceBaseImpl;
import com.liferay.portlet.journal.TemplateNameException;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class DDMTemplateLocalServiceImpl
	extends DDMTemplateLocalServiceBaseImpl {

	public DDMTemplate addTemplate(
			long structureId, String name, String description, String type,
			String language, String script, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Template

		User user = userPersistence.findByPrimaryKey(
			serviceContext.getUserId());
		Date now = new Date();

		validate(name);

		long templateId = counterLocalService.increment();

		DDMTemplate template = ddmTemplatePersistence.create(templateId);

		template.setUuid(serviceContext.getUuid());
		template.setGroupId(serviceContext.getScopeGroupId());
		template.setCompanyId(user.getCompanyId());
		template.setUserId(user.getUserId());
		template.setUserName(user.getFullName());
		template.setCreateDate(serviceContext.getCreateDate(now));
		template.setModifiedDate(serviceContext.getModifiedDate(now));
		template.setStructureId(structureId);
		template.setName(name);
		template.setDescription(description);
		template.setType(type);
		template.setLanguage(language);
		template.setScript(script);

		ddmTemplatePersistence.update(template, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addTemplateResources(
				template, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addTemplateResources(
				template, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		return template;
	}

	public void addTemplateResources(
			DDMTemplate template, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			template.getCompanyId(), template.getGroupId(),
			template.getUserId(), DDMTemplate.class.getName(),
			template.getTemplateId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addTemplateResources(
			DDMTemplate template, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			template.getCompanyId(), template.getGroupId(),
			template.getUserId(), DDMTemplate.class.getName(),
			template.getTemplateId(), communityPermissions,
			guestPermissions);
	}

	public void deleteTemplate(DDMTemplate template)
		throws PortalException, SystemException {

		// Template

		ddmTemplatePersistence.remove(template);

		// Resources

		resourceLocalService.deleteResource(
			template.getCompanyId(), DDMTemplate.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, template.getTemplateId());
	}

	public void deleteTemplate(long templateId)
		throws PortalException, SystemException {

		DDMTemplate template = ddmTemplatePersistence.findByPrimaryKey(
			templateId);

		deleteTemplate(template);
	}

	public DDMTemplate getTemplate(long templateId)
		throws PortalException, SystemException {

		return ddmTemplatePersistence.findByPrimaryKey(templateId);
	}

	public List<DDMTemplate> search(
			long companyId, long groupId, String keywords, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmTemplateFinder.findByKeywords(
			companyId, groupId, keywords, start, end, orderByComparator);
	}

	public List<DDMTemplate> search(
			long companyId, long groupId, String name, String description,
			String type, String language, boolean andOperator, int start,
			int end, OrderByComparator orderByComparator)
		throws SystemException {

		return ddmTemplateFinder.findByC_G_N_D_T_L(
			companyId, groupId, name, description, type, language, andOperator,
			start, end, orderByComparator);
	}

	public int searchCount(long companyId, long groupId, String keywords)
		throws SystemException {

		return ddmTemplateFinder.countByKeywords(companyId, groupId, keywords);
	}

	public int searchCount(
			long companyId, long groupId, String name, String description,
			String type, String language, boolean andOperator)
		throws SystemException {

		return ddmTemplateFinder.countByC_G_N_D_T_L(
			companyId, groupId, name, description, type, language, andOperator);
	}

	public DDMTemplate updateTemplate(
			long templateId, String name, String description, String type,
			String language, String script, ServiceContext serviceContext)
		throws PortalException, SystemException {

		validate(name);

		DDMTemplate template = ddmTemplateLocalService.getDDMTemplate(
			templateId);

		template.setModifiedDate(serviceContext.getModifiedDate(null));
		template.setName(name);
		template.setDescription(description);
		template.setType(type);
		template.setLanguage(language);
		template.setScript(script);

		ddmTemplatePersistence.update(template, false);

		return template;
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name) || Validator.isNumber(name) ||
			name.contains(StringPool.SPACE)) {

			throw new TemplateNameException();
		}
	}

}