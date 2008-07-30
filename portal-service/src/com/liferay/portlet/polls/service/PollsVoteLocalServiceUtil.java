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

package com.liferay.portlet.polls.service;


/**
 * <a href="PollsVoteLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.polls.service.PollsVoteLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.polls.service.PollsVoteLocalService
 *
 */
public class PollsVoteLocalServiceUtil {
	public static com.liferay.portlet.polls.model.PollsVote addPollsVote(
		com.liferay.portlet.polls.model.PollsVote pollsVote)
		throws com.liferay.portal.SystemException {
		return _service.addPollsVote(pollsVote);
	}

	public static void deletePollsVote(long voteId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deletePollsVote(voteId);
	}

	public static void deletePollsVote(
		com.liferay.portlet.polls.model.PollsVote pollsVote)
		throws com.liferay.portal.SystemException {
		_service.deletePollsVote(pollsVote);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.polls.model.PollsVote getPollsVote(
		long voteId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getPollsVote(voteId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> getPollsVotes(
		int start, int end) throws com.liferay.portal.SystemException {
		return _service.getPollsVotes(start, end);
	}

	public static int getPollsVotesCount()
		throws com.liferay.portal.SystemException {
		return _service.getPollsVotesCount();
	}

	public static com.liferay.portlet.polls.model.PollsVote updatePollsVote(
		com.liferay.portlet.polls.model.PollsVote pollsVote)
		throws com.liferay.portal.SystemException {
		return _service.updatePollsVote(pollsVote);
	}

	public static com.liferay.portlet.polls.model.PollsVote addVote(
		long userId, long questionId, long choiceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addVote(userId, questionId, choiceId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> getChoiceVotes(
		long choiceId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _service.getChoiceVotes(choiceId, start, end);
	}

	public static int getChoiceVotesCount(long choiceId)
		throws com.liferay.portal.SystemException {
		return _service.getChoiceVotesCount(choiceId);
	}

	public static com.liferay.portlet.polls.model.PollsVote getVote(
		long questionId, long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getVote(questionId, userId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> getQuestionVotes(
		long questionId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _service.getQuestionVotes(questionId, start, end);
	}

	public static int getQuestionVotesCount(long questionId)
		throws com.liferay.portal.SystemException {
		return _service.getQuestionVotesCount(questionId);
	}

	public static PollsVoteLocalService getService() {
		return _service;
	}

	public void setService(PollsVoteLocalService service) {
		_service = service;
	}

	private static PollsVoteLocalService _service;
}