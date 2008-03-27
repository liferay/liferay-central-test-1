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

/**
 * <a href="TasksProposalUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TasksProposalUtil {
	public static com.liferay.portlet.tasks.model.TasksProposal create(
		long proposalId) {
		return getPersistence().create(proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal remove(
		long proposalId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().remove(proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal remove(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(tasksProposal);
	}

	/**
	 * @deprecated Use <code>update(TasksProposal tasksProposal, boolean merge)</code>.
	 */
	public static com.liferay.portlet.tasks.model.TasksProposal update(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(tasksProposal);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        tasksProposal the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when tasksProposal is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.tasks.model.TasksProposal update(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(tasksProposal, merge);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal updateImpl(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(tasksProposal, merge);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByPrimaryKey(
		long proposalId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByPrimaryKey(proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal fetchByPrimaryKey(
		long proposalId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(proposalId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByGroupId(
		long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByGroupId(
		long groupId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal[] findByGroupId_PrevAndNext(
		long proposalId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(proposalId, groupId, obc);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByG_U(
		long groupId, long userId) throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByG_U(
		long groupId, long userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U(groupId, userId, begin, end);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByG_U(
		long groupId, long userId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U(groupId, userId, begin, end, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByG_U_First(groupId, userId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByG_U_Last(groupId, userId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal[] findByG_U_PrevAndNext(
		long proposalId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence()
				   .findByG_U_PrevAndNext(proposalId, groupId, userId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByC_C(
		long classNameId, java.lang.String classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal fetchByC_C(
		long classNameId, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findWithDynamicQuery(queryInitializer, begin, end);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findAll(
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_U(groupId, userId);
	}

	public static void removeByC_C(long classNameId, java.lang.String classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	public static int countByC_C(long classNameId, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static TasksProposalPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(TasksProposalPersistence persistence) {
		_persistence = persistence;
	}

	private static TasksProposalUtil _getUtil() {
		if (_util == null) {
			_util = (TasksProposalUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = TasksProposalUtil.class.getName();
	private static TasksProposalUtil _util;
	private TasksProposalPersistence _persistence;
}