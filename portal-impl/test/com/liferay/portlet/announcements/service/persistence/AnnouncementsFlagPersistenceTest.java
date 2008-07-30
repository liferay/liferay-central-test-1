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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.announcements.NoSuchFlagException;
import com.liferay.portlet.announcements.model.AnnouncementsFlag;

/**
 * <a href="AnnouncementsFlagPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementsFlagPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (AnnouncementsFlagPersistence)PortalBeanLocatorUtil.locate(_TX_IMPL);
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AnnouncementsFlag announcementsFlag = _persistence.create(pk);

		assertNotNull(announcementsFlag);

		assertEquals(announcementsFlag.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		_persistence.remove(newAnnouncementsFlag);

		AnnouncementsFlag existingAnnouncementsFlag = _persistence.fetchByPrimaryKey(newAnnouncementsFlag.getPrimaryKey());

		assertNull(existingAnnouncementsFlag);
	}

	public void testUpdateNew() throws Exception {
		addAnnouncementsFlag();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AnnouncementsFlag newAnnouncementsFlag = _persistence.create(pk);

		newAnnouncementsFlag.setUserId(nextLong());
		newAnnouncementsFlag.setCreateDate(nextDate());
		newAnnouncementsFlag.setEntryId(nextLong());
		newAnnouncementsFlag.setValue(nextInt());

		_persistence.update(newAnnouncementsFlag, false);

		AnnouncementsFlag existingAnnouncementsFlag = _persistence.findByPrimaryKey(newAnnouncementsFlag.getPrimaryKey());

		assertEquals(existingAnnouncementsFlag.getFlagId(),
			newAnnouncementsFlag.getFlagId());
		assertEquals(existingAnnouncementsFlag.getUserId(),
			newAnnouncementsFlag.getUserId());
		assertEquals(existingAnnouncementsFlag.getCreateDate(),
			newAnnouncementsFlag.getCreateDate());
		assertEquals(existingAnnouncementsFlag.getEntryId(),
			newAnnouncementsFlag.getEntryId());
		assertEquals(existingAnnouncementsFlag.getValue(),
			newAnnouncementsFlag.getValue());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		AnnouncementsFlag existingAnnouncementsFlag = _persistence.findByPrimaryKey(newAnnouncementsFlag.getPrimaryKey());

		assertEquals(existingAnnouncementsFlag, newAnnouncementsFlag);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchFlagException");
		}
		catch (NoSuchFlagException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		AnnouncementsFlag existingAnnouncementsFlag = _persistence.fetchByPrimaryKey(newAnnouncementsFlag.getPrimaryKey());

		assertEquals(existingAnnouncementsFlag, newAnnouncementsFlag);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AnnouncementsFlag missingAnnouncementsFlag = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAnnouncementsFlag);
	}

	protected AnnouncementsFlag addAnnouncementsFlag()
		throws Exception {
		long pk = nextLong();

		AnnouncementsFlag announcementsFlag = _persistence.create(pk);

		announcementsFlag.setUserId(nextLong());
		announcementsFlag.setCreateDate(nextDate());
		announcementsFlag.setEntryId(nextLong());
		announcementsFlag.setValue(nextInt());

		_persistence.update(announcementsFlag, false);

		return announcementsFlag;
	}

	private static final String _TX_IMPL = AnnouncementsFlagPersistence.class.getName() +
		".transaction";
	private AnnouncementsFlagPersistence _persistence;
}