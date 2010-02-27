/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="JournalTemplateFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class JournalTemplateFinderUtil {
	public static int countByKeywords(long companyId, long groupId,
		java.lang.String keywords, java.lang.String structureId,
		java.lang.String structureIdComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByKeywords(companyId, groupId, keywords, structureId,
			structureIdComparator);
	}

	public static int countByC_G_T_S_N_D(long companyId, long groupId,
		java.lang.String templateId, java.lang.String structureId,
		java.lang.String structureIdComparator, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByC_G_T_S_N_D(companyId, groupId, templateId,
			structureId, structureIdComparator, name, description, andOperator);
	}

	public static int countByC_G_T_S_N_D(long companyId, long groupId,
		java.lang.String[] templateIds, java.lang.String structureId,
		java.lang.String structureIdComparator, java.lang.String[] names,
		java.lang.String[] descriptions, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByC_G_T_S_N_D(companyId, groupId, templateIds,
			structureId, structureIdComparator, names, descriptions, andOperator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByKeywords(
		long companyId, long groupId, java.lang.String keywords,
		java.lang.String structureId, java.lang.String structureIdComparator,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByKeywords(companyId, groupId, keywords, structureId,
			structureIdComparator, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByC_G_T_S_N_D(
		long companyId, long groupId, java.lang.String templateId,
		java.lang.String structureId, java.lang.String structureIdComparator,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByC_G_T_S_N_D(companyId, groupId, templateId,
			structureId, structureIdComparator, name, description, andOperator,
			start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByC_G_T_S_N_D(
		long companyId, long groupId, java.lang.String[] templateIds,
		java.lang.String structureId, java.lang.String structureIdComparator,
		java.lang.String[] names, java.lang.String[] descriptions,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByC_G_T_S_N_D(companyId, groupId, templateIds,
			structureId, structureIdComparator, names, descriptions,
			andOperator, start, end, obc);
	}

	public static JournalTemplateFinder getFinder() {
		if (_finder == null) {
			_finder = (JournalTemplateFinder)PortalBeanLocatorUtil.locate(JournalTemplateFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(JournalTemplateFinder finder) {
		_finder = finder;
	}

	private static JournalTemplateFinder _finder;
}