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

package com.liferay.portlet.tags.service;

/**
 * <a href="TagsEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portlet.tags.service.TagsEntryLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.tags.service.TagsEntryLocalServiceFactory</code> is
 * responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tags.service.TagsEntryLocalService
 * @see com.liferay.portlet.tags.service.TagsEntryLocalServiceFactory
 *
 */
public class TagsEntryLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portlet.tags.service.persistence.TagsAssetPersistence getTagsAssetPersistence() {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.getTagsAssetPersistence();
	}

	public static void setTagsAssetPersistence(
		com.liferay.portlet.tags.service.persistence.TagsAssetPersistence tagsAssetPersistence) {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();
		tagsEntryLocalService.setTagsAssetPersistence(tagsAssetPersistence);
	}

	public static com.liferay.portlet.tags.service.persistence.TagsEntryPersistence getTagsEntryPersistence() {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.getTagsEntryPersistence();
	}

	public static void setTagsEntryPersistence(
		com.liferay.portlet.tags.service.persistence.TagsEntryPersistence tagsEntryPersistence) {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();
		tagsEntryLocalService.setTagsEntryPersistence(tagsEntryPersistence);
	}

	public static com.liferay.portlet.tags.service.persistence.TagsPropertyPersistence getTagsPropertyPersistence() {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.getTagsPropertyPersistence();
	}

	public static void setTagsPropertyPersistence(
		com.liferay.portlet.tags.service.persistence.TagsPropertyPersistence tagsPropertyPersistence) {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();
		tagsEntryLocalService.setTagsPropertyPersistence(tagsPropertyPersistence);
	}

	public static com.liferay.portlet.tags.service.persistence.TagsSourcePersistence getTagsSourcePersistence() {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.getTagsSourcePersistence();
	}

	public static void setTagsSourcePersistence(
		com.liferay.portlet.tags.service.persistence.TagsSourcePersistence tagsSourcePersistence) {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();
		tagsEntryLocalService.setTagsSourcePersistence(tagsSourcePersistence);
	}

	public static void afterPropertiesSet() {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();
		tagsEntryLocalService.afterPropertiesSet();
	}

	public static com.liferay.portlet.tags.model.TagsEntry addEntry(
		long userId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.addEntry(userId, name);
	}

	public static com.liferay.portlet.tags.model.TagsEntry addEntry(
		long userId, java.lang.String name, java.lang.String[] properties)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.addEntry(userId, name, properties);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();
		tagsEntryLocalService.deleteEntry(entryId);
	}

	public static void deleteEntry(
		com.liferay.portlet.tags.model.TagsEntry entry)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();
		tagsEntryLocalService.deleteEntry(entry);
	}

	public static boolean hasEntry(long companyId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.hasEntry(companyId, name);
	}

	public static java.util.List getAssetEntries(long assetId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.getAssetEntries(assetId);
	}

	public static java.util.List getEntries()
		throws com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.getEntries();
	}

	public static java.util.List getEntries(java.lang.String className,
		long classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.getEntries(className, classPK);
	}

	public static com.liferay.portlet.tags.model.TagsEntry getEntry(
		long entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.getEntry(entryId);
	}

	public static com.liferay.portlet.tags.model.TagsEntry getEntry(
		long companyId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.getEntry(companyId, name);
	}

	public static long[] getEntryIds(long companyId, java.lang.String[] names)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.getEntryIds(companyId, names);
	}

	public static java.util.List search(long companyId, java.lang.String name,
		java.lang.String[] properties)
		throws com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.search(companyId, name, properties);
	}

	public static java.util.List search(long companyId, java.lang.String name,
		java.lang.String[] properties, int begin, int end)
		throws com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.search(companyId, name, properties, begin,
			end);
	}

	public static com.liferay.portal.kernel.json.JSONArrayWrapper searchAutocomplete(
		long companyId, java.lang.String name, java.lang.String[] properties,
		int begin, int end) throws com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.searchAutocomplete(companyId, name,
			properties, begin, end);
	}

	public static int searchCount(long companyId, java.lang.String name,
		java.lang.String[] properties)
		throws com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.searchCount(companyId, name, properties);
	}

	public static com.liferay.portlet.tags.model.TagsEntry updateEntry(
		long entryId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.updateEntry(entryId, name);
	}

	public static com.liferay.portlet.tags.model.TagsEntry updateEntry(
		long userId, long entryId, java.lang.String name,
		java.lang.String[] properties)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsEntryLocalService tagsEntryLocalService = TagsEntryLocalServiceFactory.getService();

		return tagsEntryLocalService.updateEntry(userId, entryId, name,
			properties);
	}
}