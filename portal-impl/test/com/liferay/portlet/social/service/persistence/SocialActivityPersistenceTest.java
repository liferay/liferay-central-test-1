/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.bean.BeanLocatorUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.social.NoSuchActivityException;
import com.liferay.portlet.social.model.SocialActivity;

/**
 * <a href="SocialActivityPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SocialActivityPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (SocialActivityPersistence)BeanLocatorUtil.locate(_TX_IMPL);
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SocialActivity socialActivity = _persistence.create(pk);

		assertNotNull(socialActivity);

		assertEquals(socialActivity.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SocialActivity newSocialActivity = addSocialActivity();

		_persistence.remove(newSocialActivity);

		SocialActivity existingSocialActivity = _persistence.fetchByPrimaryKey(newSocialActivity.getPrimaryKey());

		assertNull(existingSocialActivity);
	}

	public void testUpdateNew() throws Exception {
		addSocialActivity();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SocialActivity newSocialActivity = _persistence.create(pk);

		newSocialActivity.setGroupId(nextLong());
		newSocialActivity.setCompanyId(nextLong());
		newSocialActivity.setUserId(nextLong());
		newSocialActivity.setCreateDate(nextDate());
		newSocialActivity.setClassNameId(nextLong());
		newSocialActivity.setClassPK(nextLong());
		newSocialActivity.setType(randomString());
		newSocialActivity.setExtraData(randomString());
		newSocialActivity.setReceiverUserId(nextLong());

		_persistence.update(newSocialActivity, false);

		SocialActivity existingSocialActivity = _persistence.findByPrimaryKey(newSocialActivity.getPrimaryKey());

		assertEquals(existingSocialActivity.getActivityId(),
			newSocialActivity.getActivityId());
		assertEquals(existingSocialActivity.getGroupId(),
			newSocialActivity.getGroupId());
		assertEquals(existingSocialActivity.getCompanyId(),
			newSocialActivity.getCompanyId());
		assertEquals(existingSocialActivity.getUserId(),
			newSocialActivity.getUserId());
		assertEquals(existingSocialActivity.getCreateDate(),
			newSocialActivity.getCreateDate());
		assertEquals(existingSocialActivity.getClassNameId(),
			newSocialActivity.getClassNameId());
		assertEquals(existingSocialActivity.getClassPK(),
			newSocialActivity.getClassPK());
		assertEquals(existingSocialActivity.getType(),
			newSocialActivity.getType());
		assertEquals(existingSocialActivity.getExtraData(),
			newSocialActivity.getExtraData());
		assertEquals(existingSocialActivity.getReceiverUserId(),
			newSocialActivity.getReceiverUserId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialActivity newSocialActivity = addSocialActivity();

		SocialActivity existingSocialActivity = _persistence.findByPrimaryKey(newSocialActivity.getPrimaryKey());

		assertEquals(existingSocialActivity, newSocialActivity);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchActivityException");
		}
		catch (NoSuchActivityException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialActivity newSocialActivity = addSocialActivity();

		SocialActivity existingSocialActivity = _persistence.fetchByPrimaryKey(newSocialActivity.getPrimaryKey());

		assertEquals(existingSocialActivity, newSocialActivity);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SocialActivity missingSocialActivity = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSocialActivity);
	}

	protected SocialActivity addSocialActivity() throws Exception {
		long pk = nextLong();

		SocialActivity socialActivity = _persistence.create(pk);

		socialActivity.setGroupId(nextLong());
		socialActivity.setCompanyId(nextLong());
		socialActivity.setUserId(nextLong());
		socialActivity.setCreateDate(nextDate());
		socialActivity.setClassNameId(nextLong());
		socialActivity.setClassPK(nextLong());
		socialActivity.setType(randomString());
		socialActivity.setExtraData(randomString());
		socialActivity.setReceiverUserId(nextLong());

		_persistence.update(socialActivity, false);

		return socialActivity;
	}

	private static final String _TX_IMPL = SocialActivityPersistence.class.getName() +
		".transaction";
	private SocialActivityPersistence _persistence;
}