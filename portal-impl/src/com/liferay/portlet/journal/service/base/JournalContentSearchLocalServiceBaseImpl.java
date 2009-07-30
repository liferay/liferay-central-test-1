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

package com.liferay.portlet.journal.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutService;
import com.liferay.portal.service.PortletPreferencesLocalService;
import com.liferay.portal.service.PortletPreferencesService;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.LayoutFinder;
import com.liferay.portal.service.persistence.LayoutPersistence;
import com.liferay.portal.service.persistence.PortletPreferencesFinder;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.service.JournalArticleImageLocalService;
import com.liferay.portlet.journal.service.JournalArticleLocalService;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalService;
import com.liferay.portlet.journal.service.JournalArticleService;
import com.liferay.portlet.journal.service.JournalContentSearchLocalService;
import com.liferay.portlet.journal.service.JournalFeedLocalService;
import com.liferay.portlet.journal.service.JournalFeedService;
import com.liferay.portlet.journal.service.JournalStructureLocalService;
import com.liferay.portlet.journal.service.JournalStructureService;
import com.liferay.portlet.journal.service.JournalTemplateLocalService;
import com.liferay.portlet.journal.service.JournalTemplateService;
import com.liferay.portlet.journal.service.persistence.JournalArticleFinder;
import com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticlePersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence;
import com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence;
import com.liferay.portlet.journal.service.persistence.JournalFeedFinder;
import com.liferay.portlet.journal.service.persistence.JournalFeedPersistence;
import com.liferay.portlet.journal.service.persistence.JournalStructureFinder;
import com.liferay.portlet.journal.service.persistence.JournalStructurePersistence;
import com.liferay.portlet.journal.service.persistence.JournalTemplateFinder;
import com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence;

import java.util.List;

/**
 * <a href="JournalContentSearchLocalServiceBaseImpl.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class JournalContentSearchLocalServiceBaseImpl
	implements JournalContentSearchLocalService {
	public JournalContentSearch addJournalContentSearch(
		JournalContentSearch journalContentSearch) throws SystemException {
		journalContentSearch.setNew(true);

		return journalContentSearchPersistence.update(journalContentSearch,
			false);
	}

	public JournalContentSearch createJournalContentSearch(long contentSearchId) {
		return journalContentSearchPersistence.create(contentSearchId);
	}

	public void deleteJournalContentSearch(long contentSearchId)
		throws PortalException, SystemException {
		journalContentSearchPersistence.remove(contentSearchId);
	}

	public void deleteJournalContentSearch(
		JournalContentSearch journalContentSearch) throws SystemException {
		journalContentSearchPersistence.remove(journalContentSearch);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return journalContentSearchPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return journalContentSearchPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	public JournalContentSearch getJournalContentSearch(long contentSearchId)
		throws PortalException, SystemException {
		return journalContentSearchPersistence.findByPrimaryKey(contentSearchId);
	}

	public List<JournalContentSearch> getJournalContentSearchs(int start,
		int end) throws SystemException {
		return journalContentSearchPersistence.findAll(start, end);
	}

	public int getJournalContentSearchsCount() throws SystemException {
		return journalContentSearchPersistence.countAll();
	}

	public JournalContentSearch updateJournalContentSearch(
		JournalContentSearch journalContentSearch) throws SystemException {
		journalContentSearch.setNew(false);

		return journalContentSearchPersistence.update(journalContentSearch, true);
	}

	public JournalContentSearch updateJournalContentSearch(
		JournalContentSearch journalContentSearch, boolean merge)
		throws SystemException {
		journalContentSearch.setNew(false);

		return journalContentSearchPersistence.update(journalContentSearch,
			merge);
	}

	public JournalArticleLocalService getJournalArticleLocalService() {
		return journalArticleLocalService;
	}

	public void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {
		this.journalArticleLocalService = journalArticleLocalService;
	}

	public JournalArticleService getJournalArticleService() {
		return journalArticleService;
	}

	public void setJournalArticleService(
		JournalArticleService journalArticleService) {
		this.journalArticleService = journalArticleService;
	}

	public JournalArticlePersistence getJournalArticlePersistence() {
		return journalArticlePersistence;
	}

	public void setJournalArticlePersistence(
		JournalArticlePersistence journalArticlePersistence) {
		this.journalArticlePersistence = journalArticlePersistence;
	}

	public JournalArticleFinder getJournalArticleFinder() {
		return journalArticleFinder;
	}

	public void setJournalArticleFinder(
		JournalArticleFinder journalArticleFinder) {
		this.journalArticleFinder = journalArticleFinder;
	}

	public JournalArticleImageLocalService getJournalArticleImageLocalService() {
		return journalArticleImageLocalService;
	}

	public void setJournalArticleImageLocalService(
		JournalArticleImageLocalService journalArticleImageLocalService) {
		this.journalArticleImageLocalService = journalArticleImageLocalService;
	}

	public JournalArticleImagePersistence getJournalArticleImagePersistence() {
		return journalArticleImagePersistence;
	}

	public void setJournalArticleImagePersistence(
		JournalArticleImagePersistence journalArticleImagePersistence) {
		this.journalArticleImagePersistence = journalArticleImagePersistence;
	}

	public JournalArticleResourceLocalService getJournalArticleResourceLocalService() {
		return journalArticleResourceLocalService;
	}

	public void setJournalArticleResourceLocalService(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {
		this.journalArticleResourceLocalService = journalArticleResourceLocalService;
	}

	public JournalArticleResourcePersistence getJournalArticleResourcePersistence() {
		return journalArticleResourcePersistence;
	}

	public void setJournalArticleResourcePersistence(
		JournalArticleResourcePersistence journalArticleResourcePersistence) {
		this.journalArticleResourcePersistence = journalArticleResourcePersistence;
	}

	public JournalContentSearchLocalService getJournalContentSearchLocalService() {
		return journalContentSearchLocalService;
	}

	public void setJournalContentSearchLocalService(
		JournalContentSearchLocalService journalContentSearchLocalService) {
		this.journalContentSearchLocalService = journalContentSearchLocalService;
	}

	public JournalContentSearchPersistence getJournalContentSearchPersistence() {
		return journalContentSearchPersistence;
	}

	public void setJournalContentSearchPersistence(
		JournalContentSearchPersistence journalContentSearchPersistence) {
		this.journalContentSearchPersistence = journalContentSearchPersistence;
	}

	public JournalFeedLocalService getJournalFeedLocalService() {
		return journalFeedLocalService;
	}

	public void setJournalFeedLocalService(
		JournalFeedLocalService journalFeedLocalService) {
		this.journalFeedLocalService = journalFeedLocalService;
	}

	public JournalFeedService getJournalFeedService() {
		return journalFeedService;
	}

	public void setJournalFeedService(JournalFeedService journalFeedService) {
		this.journalFeedService = journalFeedService;
	}

	public JournalFeedPersistence getJournalFeedPersistence() {
		return journalFeedPersistence;
	}

	public void setJournalFeedPersistence(
		JournalFeedPersistence journalFeedPersistence) {
		this.journalFeedPersistence = journalFeedPersistence;
	}

	public JournalFeedFinder getJournalFeedFinder() {
		return journalFeedFinder;
	}

	public void setJournalFeedFinder(JournalFeedFinder journalFeedFinder) {
		this.journalFeedFinder = journalFeedFinder;
	}

	public JournalStructureLocalService getJournalStructureLocalService() {
		return journalStructureLocalService;
	}

	public void setJournalStructureLocalService(
		JournalStructureLocalService journalStructureLocalService) {
		this.journalStructureLocalService = journalStructureLocalService;
	}

	public JournalStructureService getJournalStructureService() {
		return journalStructureService;
	}

	public void setJournalStructureService(
		JournalStructureService journalStructureService) {
		this.journalStructureService = journalStructureService;
	}

	public JournalStructurePersistence getJournalStructurePersistence() {
		return journalStructurePersistence;
	}

	public void setJournalStructurePersistence(
		JournalStructurePersistence journalStructurePersistence) {
		this.journalStructurePersistence = journalStructurePersistence;
	}

	public JournalStructureFinder getJournalStructureFinder() {
		return journalStructureFinder;
	}

	public void setJournalStructureFinder(
		JournalStructureFinder journalStructureFinder) {
		this.journalStructureFinder = journalStructureFinder;
	}

	public JournalTemplateLocalService getJournalTemplateLocalService() {
		return journalTemplateLocalService;
	}

	public void setJournalTemplateLocalService(
		JournalTemplateLocalService journalTemplateLocalService) {
		this.journalTemplateLocalService = journalTemplateLocalService;
	}

	public JournalTemplateService getJournalTemplateService() {
		return journalTemplateService;
	}

	public void setJournalTemplateService(
		JournalTemplateService journalTemplateService) {
		this.journalTemplateService = journalTemplateService;
	}

	public JournalTemplatePersistence getJournalTemplatePersistence() {
		return journalTemplatePersistence;
	}

	public void setJournalTemplatePersistence(
		JournalTemplatePersistence journalTemplatePersistence) {
		this.journalTemplatePersistence = journalTemplatePersistence;
	}

	public JournalTemplateFinder getJournalTemplateFinder() {
		return journalTemplateFinder;
	}

	public void setJournalTemplateFinder(
		JournalTemplateFinder journalTemplateFinder) {
		this.journalTemplateFinder = journalTemplateFinder;
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public CounterService getCounterService() {
		return counterService;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	public GroupLocalService getGroupLocalService() {
		return groupLocalService;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	public GroupFinder getGroupFinder() {
		return groupFinder;
	}

	public void setGroupFinder(GroupFinder groupFinder) {
		this.groupFinder = groupFinder;
	}

	public LayoutLocalService getLayoutLocalService() {
		return layoutLocalService;
	}

	public void setLayoutLocalService(LayoutLocalService layoutLocalService) {
		this.layoutLocalService = layoutLocalService;
	}

	public LayoutService getLayoutService() {
		return layoutService;
	}

	public void setLayoutService(LayoutService layoutService) {
		this.layoutService = layoutService;
	}

	public LayoutPersistence getLayoutPersistence() {
		return layoutPersistence;
	}

	public void setLayoutPersistence(LayoutPersistence layoutPersistence) {
		this.layoutPersistence = layoutPersistence;
	}

	public LayoutFinder getLayoutFinder() {
		return layoutFinder;
	}

	public void setLayoutFinder(LayoutFinder layoutFinder) {
		this.layoutFinder = layoutFinder;
	}

	public PortletPreferencesLocalService getPortletPreferencesLocalService() {
		return portletPreferencesLocalService;
	}

	public void setPortletPreferencesLocalService(
		PortletPreferencesLocalService portletPreferencesLocalService) {
		this.portletPreferencesLocalService = portletPreferencesLocalService;
	}

	public PortletPreferencesService getPortletPreferencesService() {
		return portletPreferencesService;
	}

	public void setPortletPreferencesService(
		PortletPreferencesService portletPreferencesService) {
		this.portletPreferencesService = portletPreferencesService;
	}

	public PortletPreferencesPersistence getPortletPreferencesPersistence() {
		return portletPreferencesPersistence;
	}

	public void setPortletPreferencesPersistence(
		PortletPreferencesPersistence portletPreferencesPersistence) {
		this.portletPreferencesPersistence = portletPreferencesPersistence;
	}

	public PortletPreferencesFinder getPortletPreferencesFinder() {
		return portletPreferencesFinder;
	}

	public void setPortletPreferencesFinder(
		PortletPreferencesFinder portletPreferencesFinder) {
		this.portletPreferencesFinder = portletPreferencesFinder;
	}

	protected void runSQL(String sql) throws SystemException {
		try {
			PortalUtil.runSQL(sql);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(name = "com.liferay.portlet.journal.service.JournalArticleLocalService.impl")
	protected JournalArticleLocalService journalArticleLocalService;
	@BeanReference(name = "com.liferay.portlet.journal.service.JournalArticleService.impl")
	protected JournalArticleService journalArticleService;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticlePersistence.impl")
	protected JournalArticlePersistence journalArticlePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleFinder.impl")
	protected JournalArticleFinder journalArticleFinder;
	@BeanReference(name = "com.liferay.portlet.journal.service.JournalArticleImageLocalService.impl")
	protected JournalArticleImageLocalService journalArticleImageLocalService;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence.impl")
	protected JournalArticleImagePersistence journalArticleImagePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.JournalArticleResourceLocalService.impl")
	protected JournalArticleResourceLocalService journalArticleResourceLocalService;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence.impl")
	protected JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.JournalContentSearchLocalService.impl")
	protected JournalContentSearchLocalService journalContentSearchLocalService;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence.impl")
	protected JournalContentSearchPersistence journalContentSearchPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.JournalFeedLocalService.impl")
	protected JournalFeedLocalService journalFeedLocalService;
	@BeanReference(name = "com.liferay.portlet.journal.service.JournalFeedService.impl")
	protected JournalFeedService journalFeedService;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalFeedPersistence.impl")
	protected JournalFeedPersistence journalFeedPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalFeedFinder.impl")
	protected JournalFeedFinder journalFeedFinder;
	@BeanReference(name = "com.liferay.portlet.journal.service.JournalStructureLocalService.impl")
	protected JournalStructureLocalService journalStructureLocalService;
	@BeanReference(name = "com.liferay.portlet.journal.service.JournalStructureService.impl")
	protected JournalStructureService journalStructureService;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalStructurePersistence.impl")
	protected JournalStructurePersistence journalStructurePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalStructureFinder.impl")
	protected JournalStructureFinder journalStructureFinder;
	@BeanReference(name = "com.liferay.portlet.journal.service.JournalTemplateLocalService.impl")
	protected JournalTemplateLocalService journalTemplateLocalService;
	@BeanReference(name = "com.liferay.portlet.journal.service.JournalTemplateService.impl")
	protected JournalTemplateService journalTemplateService;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence.impl")
	protected JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalTemplateFinder.impl")
	protected JournalTemplateFinder journalTemplateFinder;
	@BeanReference(name = "com.liferay.counter.service.CounterLocalService.impl")
	protected CounterLocalService counterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterService.impl")
	protected CounterService counterService;
	@BeanReference(name = "com.liferay.portal.service.GroupLocalService.impl")
	protected GroupLocalService groupLocalService;
	@BeanReference(name = "com.liferay.portal.service.GroupService.impl")
	protected GroupService groupService;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupFinder.impl")
	protected GroupFinder groupFinder;
	@BeanReference(name = "com.liferay.portal.service.LayoutLocalService.impl")
	protected LayoutLocalService layoutLocalService;
	@BeanReference(name = "com.liferay.portal.service.LayoutService.impl")
	protected LayoutService layoutService;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence.impl")
	protected LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutFinder.impl")
	protected LayoutFinder layoutFinder;
	@BeanReference(name = "com.liferay.portal.service.PortletPreferencesLocalService.impl")
	protected PortletPreferencesLocalService portletPreferencesLocalService;
	@BeanReference(name = "com.liferay.portal.service.PortletPreferencesService.impl")
	protected PortletPreferencesService portletPreferencesService;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence.impl")
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesFinder.impl")
	protected PortletPreferencesFinder portletPreferencesFinder;
}