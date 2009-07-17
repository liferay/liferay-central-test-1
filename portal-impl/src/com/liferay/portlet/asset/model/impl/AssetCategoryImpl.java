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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="AssetCategoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssetCategoryImpl
	extends AssetCategoryModelImpl implements AssetCategory {

	public AssetCategoryImpl() {
	}

	public List<AssetCategory> getAncestors()
		throws PortalException, SystemException {

		List<AssetCategory> categories = new ArrayList<AssetCategory>();

		AssetCategory category = this;

		while (true) {
			if (!category.isRootCategory()) {
				category = AssetCategoryLocalServiceUtil.getAssetCategory(
					category.getParentCategoryId());

				categories.add(category);
			}
			else {
				break;
			}
		}

		return categories;
	}

	public AssetCategory getParentCategory() {
		if (getParentCategoryId() ==
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			return null;
		}

		AssetCategory category = null;

		try {
			category = AssetCategoryLocalServiceUtil.getCategory(
				getParentCategoryId());
		}
		catch (Exception e) {
			_log.error(e);
		}

		return category;
	}

	public List<AssetCategory> getParentCategories() {
		List<AssetCategory> parentCategories = new ArrayList<AssetCategory>();

		AssetCategory parentCategory = getParentCategory();

		if (parentCategory != null) {
			parentCategories.addAll(parentCategory.getParentCategories());
			parentCategories.add(parentCategory);
		}

		return parentCategories;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", StringPool.BLANK);
	}

	public boolean isRootCategory() {
		if (getParentCategoryId() == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetCategoryImpl.class);

}