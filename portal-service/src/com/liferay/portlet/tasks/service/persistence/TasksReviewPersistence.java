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
 * <a href="TasksReviewPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface TasksReviewPersistence {
	public com.liferay.portlet.tasks.model.TasksReview create(long reviewId);

	public com.liferay.portlet.tasks.model.TasksReview remove(long reviewId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview remove(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(TasksReview tasksReview, boolean merge)</code>.
	 */
	public com.liferay.portlet.tasks.model.TasksReview update(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        tasksReview the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when tasksReview is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portlet.tasks.model.TasksReview update(
		com.liferay.portlet.tasks.model.TasksReview tasksReview, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksReview updateImpl(
		com.liferay.portlet.tasks.model.TasksReview tasksReview, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksReview findByPrimaryKey(
		long reviewId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview fetchByPrimaryKey(
		long reviewId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByUserId(
		long userId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksReview findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview[] findByUserId_PrevAndNext(
		long reviewId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByProposalId(
		long proposalId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByProposalId(
		long proposalId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByProposalId(
		long proposalId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksReview findByProposalId_First(
		long proposalId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview findByProposalId_Last(
		long proposalId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview[] findByProposalId_PrevAndNext(
		long reviewId, long proposalId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview findByU_P(long userId,
		long proposalId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview fetchByU_P(long userId,
		long proposalId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S(
		long proposalId, int stage) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S(
		long proposalId, int stage, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S(
		long proposalId, int stage, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksReview findByP_S_First(
		long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview findByP_S_Last(
		long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview[] findByP_S_PrevAndNext(
		long reviewId, long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C(
		long proposalId, int stage, boolean completed)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C(
		long proposalId, int stage, boolean completed, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C(
		long proposalId, int stage, boolean completed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksReview findByP_S_C_First(
		long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview findByP_S_C_Last(
		long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview[] findByP_S_C_PrevAndNext(
		long reviewId, long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C_R(
		long proposalId, int stage, boolean completed, boolean rejected)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C_R(
		long proposalId, int stage, boolean completed, boolean rejected,
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findByP_S_C_R(
		long proposalId, int stage, boolean completed, boolean rejected,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksReview findByP_S_C_R_First(
		long proposalId, int stage, boolean completed, boolean rejected,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview findByP_S_C_R_Last(
		long proposalId, int stage, boolean completed, boolean rejected,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public com.liferay.portlet.tasks.model.TasksReview[] findByP_S_C_R_PrevAndNext(
		long reviewId, long proposalId, int stage, boolean completed,
		boolean rejected, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksReview> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public void removeByProposalId(long proposalId)
		throws com.liferay.portal.SystemException;

	public void removeByU_P(long userId, long proposalId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException;

	public void removeByP_S(long proposalId, int stage)
		throws com.liferay.portal.SystemException;

	public void removeByP_S_C(long proposalId, int stage, boolean completed)
		throws com.liferay.portal.SystemException;

	public void removeByP_S_C_R(long proposalId, int stage, boolean completed,
		boolean rejected) throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public int countByProposalId(long proposalId)
		throws com.liferay.portal.SystemException;

	public int countByU_P(long userId, long proposalId)
		throws com.liferay.portal.SystemException;

	public int countByP_S(long proposalId, int stage)
		throws com.liferay.portal.SystemException;

	public int countByP_S_C(long proposalId, int stage, boolean completed)
		throws com.liferay.portal.SystemException;

	public int countByP_S_C_R(long proposalId, int stage, boolean completed,
		boolean rejected) throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;

	public void registerListener(
		com.liferay.portal.model.ModelListener listener);

	public void unregisterListener(
		com.liferay.portal.model.ModelListener listener);
}