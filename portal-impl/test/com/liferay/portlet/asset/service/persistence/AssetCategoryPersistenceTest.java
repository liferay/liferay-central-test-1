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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.asset.NoSuchCategoryException;
import com.liferay.portlet.asset.model.AssetCategory;

/**
 * <a href="AssetCategoryPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssetCategoryPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (AssetCategoryPersistence)PortalBeanLocatorUtil.locate(AssetCategoryPersistence.class.getName() +
				".impl");
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AssetCategory assetCategory = _persistence.create(pk);

		assertNotNull(assetCategory);

		assertEquals(assetCategory.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AssetCategory newAssetCategory = addAssetCategory();

		_persistence.remove(newAssetCategory);

		AssetCategory existingAssetCategory = _persistence.fetchByPrimaryKey(newAssetCategory.getPrimaryKey());

		assertNull(existingAssetCategory);
	}

	public void testUpdateNew() throws Exception {
		addAssetCategory();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AssetCategory newAssetCategory = _persistence.create(pk);

		newAssetCategory.setGroupId(nextLong());
		newAssetCategory.setCompanyId(nextLong());
		newAssetCategory.setUserId(nextLong());
		newAssetCategory.setUserName(randomString());
		newAssetCategory.setCreateDate(nextDate());
		newAssetCategory.setModifiedDate(nextDate());
		newAssetCategory.setParentCategoryId(nextLong());
		newAssetCategory.setName(randomString());
		newAssetCategory.setVocabularyId(nextLong());

		_persistence.update(newAssetCategory, false);

		AssetCategory existingAssetCategory = _persistence.findByPrimaryKey(newAssetCategory.getPrimaryKey());

		assertEquals(existingAssetCategory.getCategoryId(),
			newAssetCategory.getCategoryId());
		assertEquals(existingAssetCategory.getGroupId(),
			newAssetCategory.getGroupId());
		assertEquals(existingAssetCategory.getCompanyId(),
			newAssetCategory.getCompanyId());
		assertEquals(existingAssetCategory.getUserId(),
			newAssetCategory.getUserId());
		assertEquals(existingAssetCategory.getUserName(),
			newAssetCategory.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingAssetCategory.getCreateDate()),
			Time.getShortTimestamp(newAssetCategory.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingAssetCategory.getModifiedDate()),
			Time.getShortTimestamp(newAssetCategory.getModifiedDate()));
		assertEquals(existingAssetCategory.getParentCategoryId(),
			newAssetCategory.getParentCategoryId());
		assertEquals(existingAssetCategory.getName(), newAssetCategory.getName());
		assertEquals(existingAssetCategory.getVocabularyId(),
			newAssetCategory.getVocabularyId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetCategory newAssetCategory = addAssetCategory();

		AssetCategory existingAssetCategory = _persistence.findByPrimaryKey(newAssetCategory.getPrimaryKey());

		assertEquals(existingAssetCategory, newAssetCategory);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchCategoryException");
		}
		catch (NoSuchCategoryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetCategory newAssetCategory = addAssetCategory();

		AssetCategory existingAssetCategory = _persistence.fetchByPrimaryKey(newAssetCategory.getPrimaryKey());

		assertEquals(existingAssetCategory, newAssetCategory);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AssetCategory missingAssetCategory = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAssetCategory);
	}

	protected AssetCategory addAssetCategory() throws Exception {
		long pk = nextLong();

		AssetCategory assetCategory = _persistence.create(pk);

		assetCategory.setGroupId(nextLong());
		assetCategory.setCompanyId(nextLong());
		assetCategory.setUserId(nextLong());
		assetCategory.setUserName(randomString());
		assetCategory.setCreateDate(nextDate());
		assetCategory.setModifiedDate(nextDate());
		assetCategory.setParentCategoryId(nextLong());
		assetCategory.setName(randomString());
		assetCategory.setVocabularyId(nextLong());

		_persistence.update(assetCategory, false);

		return assetCategory;
	}

	private AssetCategoryPersistence _persistence;
}