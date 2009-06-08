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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portlet.asset.TagPropertyKeyException;
import com.liferay.portlet.asset.TagPropertyValueException;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.service.base.AssetTagPropertyLocalServiceBaseImpl;
import com.liferay.portlet.asset.util.AssetUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="AssetTagPropertyLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssetTagPropertyLocalServiceImpl
	extends AssetTagPropertyLocalServiceBaseImpl {

	public AssetTagProperty addTagProperty(
			long userId, long tagId, String key, String value)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(key, value);

		long propertyId = counterLocalService.increment();

		AssetTagProperty property = assetTagPropertyPersistence.create(
			propertyId);

		property.setCompanyId(user.getCompanyId());
		property.setUserId(user.getUserId());
		property.setUserName(user.getFullName());
		property.setCreateDate(now);
		property.setModifiedDate(now);
		property.setTagId(tagId);
		property.setKey(key);
		property.setValue(value);

		assetTagPropertyPersistence.update(property, false);

		return property;
	}

	public void deleteTagProperties(long tagId) throws SystemException {
		List<AssetTagProperty> properties =
			assetTagPropertyPersistence.findByTagId(tagId);

		for (AssetTagProperty property : properties) {
			deleteTagProperty(property);
		}
	}

	public void deleteTagProperty(long tagPropertyId)
		throws PortalException, SystemException {

		AssetTagProperty property =
			assetTagPropertyPersistence.findByPrimaryKey(tagPropertyId);

		deleteTagProperty(property);
	}

	public void deleteTagProperty(AssetTagProperty tagProperty)
		throws SystemException {

		assetTagPropertyPersistence.remove(tagProperty);
	}

	public List<AssetTagProperty> getTagProperties() throws SystemException {
		return assetTagPropertyPersistence.findAll();
	}

	public List<AssetTagProperty> getTagProperties(long tagId)
		throws SystemException {

		return assetTagPropertyPersistence.findByTagId(tagId);
	}

	public AssetTagProperty getTagProperty(long tagPropertyId)
		throws PortalException, SystemException {

		return assetTagPropertyPersistence.findByPrimaryKey(tagPropertyId);
	}

	public AssetTagProperty getTagProperty(long tagId, String key)
		throws PortalException, SystemException {

		return assetTagPropertyPersistence.findByE_K(tagId, key);
	}

	public String[] getPropertyKeys(long groupId) throws SystemException {
		return assetTagPropertyKeyFinder.findByGroupId(groupId);
	}

	public List<AssetTagProperty> getPropertyValues(long groupId, String key)
		throws SystemException {

		return assetTagPropertyFinder.findByG_K(groupId, key);
	}

	public AssetTagProperty updateTagProperty(
			long tagPropertyId, String key, String value)
		throws PortalException, SystemException {

		validate(key, value);

		AssetTagProperty property =
			assetTagPropertyPersistence.findByPrimaryKey(tagPropertyId);

		property.setModifiedDate(new Date());
		property.setKey(key);
		property.setValue(value);

		assetTagPropertyPersistence.update(property, false);

		return property;
	}

	protected void validate(String key, String value) throws PortalException {
		if (!AssetUtil.isValidWord(key)) {
			throw new TagPropertyKeyException();
		}

		if (!AssetUtil.isValidWord(value)) {
			throw new TagPropertyValueException();
		}
	}

}