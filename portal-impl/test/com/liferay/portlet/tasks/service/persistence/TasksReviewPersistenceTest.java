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

package com.liferay.portlet.tasks.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.tasks.NoSuchReviewException;
import com.liferay.portlet.tasks.model.TasksReview;

/**
 * <a href="TasksReviewPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TasksReviewPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (TasksReviewPersistence)PortalBeanLocatorUtil.locate(_TX_IMPL);
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		TasksReview tasksReview = _persistence.create(pk);

		assertNotNull(tasksReview);

		assertEquals(tasksReview.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		TasksReview newTasksReview = addTasksReview();

		_persistence.remove(newTasksReview);

		TasksReview existingTasksReview = _persistence.fetchByPrimaryKey(newTasksReview.getPrimaryKey());

		assertNull(existingTasksReview);
	}

	public void testUpdateNew() throws Exception {
		addTasksReview();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		TasksReview newTasksReview = _persistence.create(pk);

		newTasksReview.setGroupId(nextLong());
		newTasksReview.setCompanyId(nextLong());
		newTasksReview.setUserId(nextLong());
		newTasksReview.setUserName(randomString());
		newTasksReview.setCreateDate(nextDate());
		newTasksReview.setModifiedDate(nextDate());
		newTasksReview.setProposalId(nextLong());
		newTasksReview.setAssignedByUserId(nextLong());
		newTasksReview.setAssignedByUserName(randomString());
		newTasksReview.setStage(nextInt());
		newTasksReview.setCompleted(randomBoolean());
		newTasksReview.setRejected(randomBoolean());

		_persistence.update(newTasksReview, false);

		TasksReview existingTasksReview = _persistence.findByPrimaryKey(newTasksReview.getPrimaryKey());

		assertEquals(existingTasksReview.getReviewId(),
			newTasksReview.getReviewId());
		assertEquals(existingTasksReview.getGroupId(),
			newTasksReview.getGroupId());
		assertEquals(existingTasksReview.getCompanyId(),
			newTasksReview.getCompanyId());
		assertEquals(existingTasksReview.getUserId(), newTasksReview.getUserId());
		assertEquals(existingTasksReview.getUserName(),
			newTasksReview.getUserName());
		assertEquals(existingTasksReview.getCreateDate(),
			newTasksReview.getCreateDate());
		assertEquals(existingTasksReview.getModifiedDate(),
			newTasksReview.getModifiedDate());
		assertEquals(existingTasksReview.getProposalId(),
			newTasksReview.getProposalId());
		assertEquals(existingTasksReview.getAssignedByUserId(),
			newTasksReview.getAssignedByUserId());
		assertEquals(existingTasksReview.getAssignedByUserName(),
			newTasksReview.getAssignedByUserName());
		assertEquals(existingTasksReview.getStage(), newTasksReview.getStage());
		assertEquals(existingTasksReview.getCompleted(),
			newTasksReview.getCompleted());
		assertEquals(existingTasksReview.getRejected(),
			newTasksReview.getRejected());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		TasksReview newTasksReview = addTasksReview();

		TasksReview existingTasksReview = _persistence.findByPrimaryKey(newTasksReview.getPrimaryKey());

		assertEquals(existingTasksReview, newTasksReview);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchReviewException");
		}
		catch (NoSuchReviewException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		TasksReview newTasksReview = addTasksReview();

		TasksReview existingTasksReview = _persistence.fetchByPrimaryKey(newTasksReview.getPrimaryKey());

		assertEquals(existingTasksReview, newTasksReview);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		TasksReview missingTasksReview = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingTasksReview);
	}

	protected TasksReview addTasksReview() throws Exception {
		long pk = nextLong();

		TasksReview tasksReview = _persistence.create(pk);

		tasksReview.setGroupId(nextLong());
		tasksReview.setCompanyId(nextLong());
		tasksReview.setUserId(nextLong());
		tasksReview.setUserName(randomString());
		tasksReview.setCreateDate(nextDate());
		tasksReview.setModifiedDate(nextDate());
		tasksReview.setProposalId(nextLong());
		tasksReview.setAssignedByUserId(nextLong());
		tasksReview.setAssignedByUserName(randomString());
		tasksReview.setStage(nextInt());
		tasksReview.setCompleted(randomBoolean());
		tasksReview.setRejected(randomBoolean());

		_persistence.update(tasksReview, false);

		return tasksReview;
	}

	private static final String _TX_IMPL = TasksReviewPersistence.class.getName() +
		".transaction";
	private TasksReviewPersistence _persistence;
}