/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.DuplicateCategoryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.base.AssetCategoryLocalServiceBaseImpl;
import com.liferay.util.Autocomplete;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <a href="AssetCategoryLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Alvaro del Castillo
 * @author Jorge Ferrer
 * @author Bruno Farache
 */
public class AssetCategoryLocalServiceImpl
	extends AssetCategoryLocalServiceBaseImpl {

	public AssetCategory addCategory(
			String uuid, long userId, long parentCategoryId,
			Map<Locale, String> titleMap, long vocabularyId,
			String[] categoryProperties, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Category

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		String name = titleMap.get(LocaleUtil.getDefault());

		if (categoryProperties == null) {
			categoryProperties = new String[0];
		}

		Date now = new Date();

		validate(0, parentCategoryId, name, vocabularyId);

		if (parentCategoryId > 0) {
			assetCategoryPersistence.findByPrimaryKey(parentCategoryId);
		}

		assetVocabularyPersistence.findByPrimaryKey(vocabularyId);

		long categoryId = counterLocalService.increment();

		AssetCategory category = assetCategoryPersistence.create(categoryId);

		category.setUuid(uuid);
		category.setGroupId(groupId);
		category.setCompanyId(user.getCompanyId());
		category.setUserId(user.getUserId());
		category.setUserName(user.getFullName());
		category.setCreateDate(now);
		category.setModifiedDate(now);
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setTitleMap(titleMap);
		category.setVocabularyId(vocabularyId);

		assetCategoryPersistence.update(category, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addCategoryResources(
				category, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addCategoryResources(
				category, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Properties

		for (int i = 0; i < categoryProperties.length; i++) {
			String[] categoryProperty = StringUtil.split(
				categoryProperties[i], StringPool.COLON);

			String key = StringPool.BLANK;

			if (categoryProperty.length > 1) {
				key = GetterUtil.getString(categoryProperty[1]);
			}

			String value = StringPool.BLANK;

			if (categoryProperty.length > 2) {
				value = GetterUtil.getString(categoryProperty[2]);
			}

			if (Validator.isNotNull(key)) {
				assetCategoryPropertyLocalService.addCategoryProperty(
					userId, categoryId, key, value);
			}
		}

		return category;
	}

	public void addCategoryResources(
			AssetCategory category, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), AssetCategory.class.getName(),
			category.getCategoryId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addCategoryResources(
			AssetCategory category, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), AssetCategory.class.getName(),
			category.getCategoryId(), communityPermissions, guestPermissions);
	}

	public void deleteCategory(AssetCategory category)
		throws PortalException, SystemException {

		// Category

		assetCategoryPersistence.remove(category);

		// Resources

		resourceLocalService.deleteResource(
			category.getCompanyId(), AssetCategory.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, category.getCategoryId());

		// Categories

		List<AssetCategory> categories =
			assetCategoryPersistence.findByParentCategoryId(
				category.getCategoryId());

		for (AssetCategory curCategory : categories) {
			deleteCategory(curCategory);
		}

		// Properties

		assetCategoryPropertyLocalService.deleteCategoryProperties(
			category.getCategoryId());
	}

	public void deleteCategory(long categoryId)
		throws PortalException, SystemException {

		AssetCategory category = assetCategoryPersistence.findByPrimaryKey(
			categoryId);

		deleteCategory(category);
	}

	public void deleteVocabularyCategories(long vocabularyId)
		throws PortalException, SystemException {

		List<AssetCategory> categories =
			assetCategoryPersistence.findByVocabularyId(vocabularyId);

		for (AssetCategory category : categories) {
			deleteCategory(category);
		}
	}

	public List<AssetCategory> getCategories() throws SystemException {
		return assetCategoryPersistence.findAll();
	}

	public List<AssetCategory> getCategories(long classNameId, long classPK)
		throws SystemException {

		return assetCategoryFinder.findByC_C(classNameId, classPK);
	}

	public List<AssetCategory> getCategories(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getCategories(classNameId, classPK);
	}

	public AssetCategory getCategory(long categoryId)
		throws PortalException, SystemException {

		return assetCategoryPersistence.findByPrimaryKey(categoryId);
	}

	public long[] getCategoryIds(String className, long classPK)
		throws SystemException {

		return getCategoryIds(getCategories(className, classPK));
	}

	public List<AssetCategory> getChildCategories(long parentCategoryId)
		throws SystemException {

		return assetCategoryPersistence.findByParentCategoryId(
			parentCategoryId);
	}

	public List<AssetCategory> getEntryCategories(long entryId)
		throws SystemException {

		return assetCategoryFinder.findByEntryId(entryId);
	}

	public List<AssetCategory> getVocabularyCategories(long vocabularyId)
		throws SystemException {

		return assetCategoryPersistence.findByVocabularyId(vocabularyId);
	}

	public List<AssetCategory> getVocabularyRootCategories(long vocabularyId)
		throws SystemException {

		return assetCategoryPersistence.findByP_V(
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, vocabularyId);
	}

	public void mergeCategories(long fromCategoryId, long toCategoryId)
		throws PortalException, SystemException {

		List<AssetEntry> entries = assetCategoryPersistence.getAssetEntries(
			fromCategoryId);

		assetCategoryPersistence.addAssetEntries(toCategoryId, entries);

		List<AssetCategoryProperty> categoryProperties =
			assetCategoryPropertyPersistence.findByCategoryId(fromCategoryId);

		for (AssetCategoryProperty fromCategoryProperty : categoryProperties) {
			AssetCategoryProperty toCategoryProperty =
				assetCategoryPropertyPersistence.fetchByCA_K(
					toCategoryId, fromCategoryProperty.getKey());

			if (toCategoryProperty == null) {
				fromCategoryProperty.setCategoryId(toCategoryId);

				assetCategoryPropertyPersistence.update(
					fromCategoryProperty, false);
			}
		}

		deleteCategory(fromCategoryId);
	}

	public JSONArray search(
			long groupId, String name, String[] categoryProperties, int start,
			int end)
		throws SystemException {

		List<AssetCategory> list = assetCategoryFinder.findByG_N_P(
			groupId, name, categoryProperties, start, end);

		return Autocomplete.listToJson(list, "name", "name");
	}

	public AssetCategory updateCategory(
			long userId, long categoryId, long parentCategoryId,
			Map<Locale, String> titleMap, long vocabularyId,
			String[] categoryProperties, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Category

		String name = titleMap.get(LocaleUtil.getDefault());

		if (categoryProperties == null) {
			categoryProperties = new String[0];
		}

		validate(categoryId, parentCategoryId, name, vocabularyId);

		if (parentCategoryId > 0) {
			assetCategoryPersistence.findByPrimaryKey(parentCategoryId);
		}

		assetVocabularyPersistence.findByPrimaryKey(vocabularyId);

		AssetCategory category = assetCategoryPersistence.findByPrimaryKey(
			categoryId);

		category.setModifiedDate(new Date());
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setTitleMap(titleMap);
		category.setVocabularyId(vocabularyId);

		assetCategoryPersistence.update(category, false);

		// Properties

		List<AssetCategoryProperty> oldCategoryProperties =
			assetCategoryPropertyPersistence.findByCategoryId(categoryId);

		for (AssetCategoryProperty categoryProperty : oldCategoryProperties) {
			assetCategoryPropertyLocalService.deleteAssetCategoryProperty(
				categoryProperty);
		}

		for (int i = 0; i < categoryProperties.length; i++) {
			String[] categoryProperty = StringUtil.split(
				categoryProperties[i], StringPool.COLON);

			String key = StringPool.BLANK;

			if (categoryProperty.length > 0) {
				key = GetterUtil.getString(categoryProperty[0]);
			}

			String value = StringPool.BLANK;

			if (categoryProperty.length > 1) {
				value = GetterUtil.getString(categoryProperty[1]);
			}

			if (Validator.isNotNull(key)) {
				assetCategoryPropertyLocalService.addCategoryProperty(
					userId, categoryId, key, value);
			}
		}

		return category;
	}

	protected long[] getCategoryIds(List <AssetCategory>categories) {
		return StringUtil.split(
			ListUtil.toString(categories, "categoryId"), 0L);
	}

	protected void validate(
			long categoryId, long parentCategoryId, String name,
			long vocabularyId)
		throws PortalException, SystemException {

		List<AssetCategory> categories = null;

		if (parentCategoryId == 0) {
			categories = assetCategoryPersistence.findByN_V(
				name, vocabularyId);
		}
		else {
			categories = assetCategoryPersistence.findByP_N(
				parentCategoryId, name);
		}

		if ((categories.size() > 0) &&
			(categories.get(0).getCategoryId() != categoryId)) {

			StringBundler sb = new StringBundler(4);

			sb.append("There is another category category named ");
			sb.append(name);
			sb.append(" as a child of category ");
			sb.append(parentCategoryId);

			throw new DuplicateCategoryException(sb.toString());
		}
	}

}