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

package com.liferay.portlet.tags.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.base.PrincipalBean;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.expando.service.ExpandoValueLocalService;
import com.liferay.portlet.expando.service.ExpandoValueService;
import com.liferay.portlet.expando.service.persistence.ExpandoValueFinder;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.tags.service.TagsAssetLocalService;
import com.liferay.portlet.tags.service.TagsAssetService;
import com.liferay.portlet.tags.service.TagsEntryLocalService;
import com.liferay.portlet.tags.service.TagsEntryService;
import com.liferay.portlet.tags.service.TagsPropertyLocalService;
import com.liferay.portlet.tags.service.TagsPropertyService;
import com.liferay.portlet.tags.service.TagsSourceLocalService;
import com.liferay.portlet.tags.service.TagsSourceService;
import com.liferay.portlet.tags.service.TagsVocabularyLocalService;
import com.liferay.portlet.tags.service.TagsVocabularyService;
import com.liferay.portlet.tags.service.persistence.TagsAssetFinder;
import com.liferay.portlet.tags.service.persistence.TagsAssetPersistence;
import com.liferay.portlet.tags.service.persistence.TagsEntryFinder;
import com.liferay.portlet.tags.service.persistence.TagsEntryPersistence;
import com.liferay.portlet.tags.service.persistence.TagsPropertyFinder;
import com.liferay.portlet.tags.service.persistence.TagsPropertyKeyFinder;
import com.liferay.portlet.tags.service.persistence.TagsPropertyPersistence;
import com.liferay.portlet.tags.service.persistence.TagsSourcePersistence;
import com.liferay.portlet.tags.service.persistence.TagsVocabularyPersistence;

/**
 * <a href="TagsVocabularyServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class TagsVocabularyServiceBaseImpl extends PrincipalBean
	implements TagsVocabularyService {
	public TagsAssetLocalService getTagsAssetLocalService() {
		return tagsAssetLocalService;
	}

	public void setTagsAssetLocalService(
		TagsAssetLocalService tagsAssetLocalService) {
		this.tagsAssetLocalService = tagsAssetLocalService;
	}

	public TagsAssetService getTagsAssetService() {
		return tagsAssetService;
	}

	public void setTagsAssetService(TagsAssetService tagsAssetService) {
		this.tagsAssetService = tagsAssetService;
	}

	public TagsAssetPersistence getTagsAssetPersistence() {
		return tagsAssetPersistence;
	}

	public void setTagsAssetPersistence(
		TagsAssetPersistence tagsAssetPersistence) {
		this.tagsAssetPersistence = tagsAssetPersistence;
	}

	public TagsAssetFinder getTagsAssetFinder() {
		return tagsAssetFinder;
	}

	public void setTagsAssetFinder(TagsAssetFinder tagsAssetFinder) {
		this.tagsAssetFinder = tagsAssetFinder;
	}

	public TagsEntryLocalService getTagsEntryLocalService() {
		return tagsEntryLocalService;
	}

	public void setTagsEntryLocalService(
		TagsEntryLocalService tagsEntryLocalService) {
		this.tagsEntryLocalService = tagsEntryLocalService;
	}

	public TagsEntryService getTagsEntryService() {
		return tagsEntryService;
	}

	public void setTagsEntryService(TagsEntryService tagsEntryService) {
		this.tagsEntryService = tagsEntryService;
	}

	public TagsEntryPersistence getTagsEntryPersistence() {
		return tagsEntryPersistence;
	}

	public void setTagsEntryPersistence(
		TagsEntryPersistence tagsEntryPersistence) {
		this.tagsEntryPersistence = tagsEntryPersistence;
	}

	public TagsEntryFinder getTagsEntryFinder() {
		return tagsEntryFinder;
	}

	public void setTagsEntryFinder(TagsEntryFinder tagsEntryFinder) {
		this.tagsEntryFinder = tagsEntryFinder;
	}

	public TagsPropertyLocalService getTagsPropertyLocalService() {
		return tagsPropertyLocalService;
	}

	public void setTagsPropertyLocalService(
		TagsPropertyLocalService tagsPropertyLocalService) {
		this.tagsPropertyLocalService = tagsPropertyLocalService;
	}

	public TagsPropertyService getTagsPropertyService() {
		return tagsPropertyService;
	}

	public void setTagsPropertyService(TagsPropertyService tagsPropertyService) {
		this.tagsPropertyService = tagsPropertyService;
	}

	public TagsPropertyPersistence getTagsPropertyPersistence() {
		return tagsPropertyPersistence;
	}

	public void setTagsPropertyPersistence(
		TagsPropertyPersistence tagsPropertyPersistence) {
		this.tagsPropertyPersistence = tagsPropertyPersistence;
	}

	public TagsPropertyFinder getTagsPropertyFinder() {
		return tagsPropertyFinder;
	}

	public void setTagsPropertyFinder(TagsPropertyFinder tagsPropertyFinder) {
		this.tagsPropertyFinder = tagsPropertyFinder;
	}

	public TagsPropertyKeyFinder getTagsPropertyKeyFinder() {
		return tagsPropertyKeyFinder;
	}

	public void setTagsPropertyKeyFinder(
		TagsPropertyKeyFinder tagsPropertyKeyFinder) {
		this.tagsPropertyKeyFinder = tagsPropertyKeyFinder;
	}

	public TagsSourceLocalService getTagsSourceLocalService() {
		return tagsSourceLocalService;
	}

	public void setTagsSourceLocalService(
		TagsSourceLocalService tagsSourceLocalService) {
		this.tagsSourceLocalService = tagsSourceLocalService;
	}

	public TagsSourceService getTagsSourceService() {
		return tagsSourceService;
	}

	public void setTagsSourceService(TagsSourceService tagsSourceService) {
		this.tagsSourceService = tagsSourceService;
	}

	public TagsSourcePersistence getTagsSourcePersistence() {
		return tagsSourcePersistence;
	}

	public void setTagsSourcePersistence(
		TagsSourcePersistence tagsSourcePersistence) {
		this.tagsSourcePersistence = tagsSourcePersistence;
	}

	public TagsVocabularyLocalService getTagsVocabularyLocalService() {
		return tagsVocabularyLocalService;
	}

	public void setTagsVocabularyLocalService(
		TagsVocabularyLocalService tagsVocabularyLocalService) {
		this.tagsVocabularyLocalService = tagsVocabularyLocalService;
	}

	public TagsVocabularyService getTagsVocabularyService() {
		return tagsVocabularyService;
	}

	public void setTagsVocabularyService(
		TagsVocabularyService tagsVocabularyService) {
		this.tagsVocabularyService = tagsVocabularyService;
	}

	public TagsVocabularyPersistence getTagsVocabularyPersistence() {
		return tagsVocabularyPersistence;
	}

	public void setTagsVocabularyPersistence(
		TagsVocabularyPersistence tagsVocabularyPersistence) {
		this.tagsVocabularyPersistence = tagsVocabularyPersistence;
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

	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	public ResourceFinder getResourceFinder() {
		return resourceFinder;
	}

	public void setResourceFinder(ResourceFinder resourceFinder) {
		this.resourceFinder = resourceFinder;
	}

	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public UserFinder getUserFinder() {
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public ExpandoValueLocalService getExpandoValueLocalService() {
		return expandoValueLocalService;
	}

	public void setExpandoValueLocalService(
		ExpandoValueLocalService expandoValueLocalService) {
		this.expandoValueLocalService = expandoValueLocalService;
	}

	public ExpandoValueService getExpandoValueService() {
		return expandoValueService;
	}

	public void setExpandoValueService(ExpandoValueService expandoValueService) {
		this.expandoValueService = expandoValueService;
	}

	public ExpandoValuePersistence getExpandoValuePersistence() {
		return expandoValuePersistence;
	}

	public void setExpandoValuePersistence(
		ExpandoValuePersistence expandoValuePersistence) {
		this.expandoValuePersistence = expandoValuePersistence;
	}

	public ExpandoValueFinder getExpandoValueFinder() {
		return expandoValueFinder;
	}

	public void setExpandoValueFinder(ExpandoValueFinder expandoValueFinder) {
		this.expandoValueFinder = expandoValueFinder;
	}

	@BeanReference(name = "com.liferay.portlet.tags.service.TagsAssetLocalService.impl")
	protected TagsAssetLocalService tagsAssetLocalService;
	@BeanReference(name = "com.liferay.portlet.tags.service.TagsAssetService.impl")
	protected TagsAssetService tagsAssetService;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsAssetPersistence.impl")
	protected TagsAssetPersistence tagsAssetPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsAssetFinder.impl")
	protected TagsAssetFinder tagsAssetFinder;
	@BeanReference(name = "com.liferay.portlet.tags.service.TagsEntryLocalService.impl")
	protected TagsEntryLocalService tagsEntryLocalService;
	@BeanReference(name = "com.liferay.portlet.tags.service.TagsEntryService.impl")
	protected TagsEntryService tagsEntryService;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsEntryPersistence.impl")
	protected TagsEntryPersistence tagsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsEntryFinder.impl")
	protected TagsEntryFinder tagsEntryFinder;
	@BeanReference(name = "com.liferay.portlet.tags.service.TagsPropertyLocalService.impl")
	protected TagsPropertyLocalService tagsPropertyLocalService;
	@BeanReference(name = "com.liferay.portlet.tags.service.TagsPropertyService.impl")
	protected TagsPropertyService tagsPropertyService;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsPropertyPersistence.impl")
	protected TagsPropertyPersistence tagsPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsPropertyFinder.impl")
	protected TagsPropertyFinder tagsPropertyFinder;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsPropertyKeyFinder.impl")
	protected TagsPropertyKeyFinder tagsPropertyKeyFinder;
	@BeanReference(name = "com.liferay.portlet.tags.service.TagsSourceLocalService.impl")
	protected TagsSourceLocalService tagsSourceLocalService;
	@BeanReference(name = "com.liferay.portlet.tags.service.TagsSourceService.impl")
	protected TagsSourceService tagsSourceService;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsSourcePersistence.impl")
	protected TagsSourcePersistence tagsSourcePersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.TagsVocabularyLocalService.impl")
	protected TagsVocabularyLocalService tagsVocabularyLocalService;
	@BeanReference(name = "com.liferay.portlet.tags.service.TagsVocabularyService.impl")
	protected TagsVocabularyService tagsVocabularyService;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsVocabularyPersistence.impl")
	protected TagsVocabularyPersistence tagsVocabularyPersistence;
	@BeanReference(name = "com.liferay.counter.service.CounterLocalService.impl")
	protected CounterLocalService counterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterService.impl")
	protected CounterService counterService;
	@BeanReference(name = "com.liferay.portal.service.ResourceLocalService.impl")
	protected ResourceLocalService resourceLocalService;
	@BeanReference(name = "com.liferay.portal.service.ResourceService.impl")
	protected ResourceService resourceService;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceFinder.impl")
	protected ResourceFinder resourceFinder;
	@BeanReference(name = "com.liferay.portal.service.UserLocalService.impl")
	protected UserLocalService userLocalService;
	@BeanReference(name = "com.liferay.portal.service.UserService.impl")
	protected UserService userService;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserFinder.impl")
	protected UserFinder userFinder;
	@BeanReference(name = "com.liferay.portlet.expando.service.ExpandoValueLocalService.impl")
	protected ExpandoValueLocalService expandoValueLocalService;
	@BeanReference(name = "com.liferay.portlet.expando.service.ExpandoValueService.impl")
	protected ExpandoValueService expandoValueService;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence.impl")
	protected ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValueFinder.impl")
	protected ExpandoValueFinder expandoValueFinder;
}