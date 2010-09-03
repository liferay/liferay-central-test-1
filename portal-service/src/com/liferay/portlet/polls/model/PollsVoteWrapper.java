/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.polls.model;

/**
 * <p>
 * This class is a wrapper for {@link PollsVote}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsVote
 * @generated
 */
public class PollsVoteWrapper implements PollsVote {
	public PollsVoteWrapper(PollsVote pollsVote) {
		_pollsVote = pollsVote;
	}

	/**
	* Gets the primary key of this polls vote.
	*
	* @return the primary key of this polls vote
	*/
	public long getPrimaryKey() {
		return _pollsVote.getPrimaryKey();
	}

	/**
	* Sets the primary key of this polls vote
	*
	* @param pk the primary key of this polls vote
	*/
	public void setPrimaryKey(long pk) {
		_pollsVote.setPrimaryKey(pk);
	}

	/**
	* Gets the vote id of this polls vote.
	*
	* @return the vote id of this polls vote
	*/
	public long getVoteId() {
		return _pollsVote.getVoteId();
	}

	/**
	* Sets the vote id of this polls vote.
	*
	* @param voteId the vote id of this polls vote
	*/
	public void setVoteId(long voteId) {
		_pollsVote.setVoteId(voteId);
	}

	/**
	* Gets the user id of this polls vote.
	*
	* @return the user id of this polls vote
	*/
	public long getUserId() {
		return _pollsVote.getUserId();
	}

	/**
	* Sets the user id of this polls vote.
	*
	* @param userId the user id of this polls vote
	*/
	public void setUserId(long userId) {
		_pollsVote.setUserId(userId);
	}

	/**
	* Gets the user uuid of this polls vote.
	*
	* @return the user uuid of this polls vote
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _pollsVote.getUserUuid();
	}

	/**
	* Sets the user uuid of this polls vote.
	*
	* @param userUuid the user uuid of this polls vote
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_pollsVote.setUserUuid(userUuid);
	}

	/**
	* Gets the question id of this polls vote.
	*
	* @return the question id of this polls vote
	*/
	public long getQuestionId() {
		return _pollsVote.getQuestionId();
	}

	/**
	* Sets the question id of this polls vote.
	*
	* @param questionId the question id of this polls vote
	*/
	public void setQuestionId(long questionId) {
		_pollsVote.setQuestionId(questionId);
	}

	/**
	* Gets the choice id of this polls vote.
	*
	* @return the choice id of this polls vote
	*/
	public long getChoiceId() {
		return _pollsVote.getChoiceId();
	}

	/**
	* Sets the choice id of this polls vote.
	*
	* @param choiceId the choice id of this polls vote
	*/
	public void setChoiceId(long choiceId) {
		_pollsVote.setChoiceId(choiceId);
	}

	/**
	* Gets the vote date of this polls vote.
	*
	* @return the vote date of this polls vote
	*/
	public java.util.Date getVoteDate() {
		return _pollsVote.getVoteDate();
	}

	/**
	* Sets the vote date of this polls vote.
	*
	* @param voteDate the vote date of this polls vote
	*/
	public void setVoteDate(java.util.Date voteDate) {
		_pollsVote.setVoteDate(voteDate);
	}

	public boolean isNew() {
		return _pollsVote.isNew();
	}

	public void setNew(boolean n) {
		_pollsVote.setNew(n);
	}

	public boolean isCachedModel() {
		return _pollsVote.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_pollsVote.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _pollsVote.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_pollsVote.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _pollsVote.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _pollsVote.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_pollsVote.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _pollsVote.clone();
	}

	public int compareTo(com.liferay.portlet.polls.model.PollsVote pollsVote) {
		return _pollsVote.compareTo(pollsVote);
	}

	public int hashCode() {
		return _pollsVote.hashCode();
	}

	public com.liferay.portlet.polls.model.PollsVote toEscapedModel() {
		return _pollsVote.toEscapedModel();
	}

	public java.lang.String toString() {
		return _pollsVote.toString();
	}

	public java.lang.String toXmlString() {
		return _pollsVote.toXmlString();
	}

	public com.liferay.portlet.polls.model.PollsChoice getChoice()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _pollsVote.getChoice();
	}

	public PollsVote getWrappedPollsVote() {
		return _pollsVote;
	}

	private PollsVote _pollsVote;
}