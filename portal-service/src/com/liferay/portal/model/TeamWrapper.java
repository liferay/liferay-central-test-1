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

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link Team}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Team
 * @generated
 */
public class TeamWrapper implements Team {
	public TeamWrapper(Team team) {
		_team = team;
	}

	/**
	* Gets the primary key of this team.
	*
	* @return the primary key of this team
	*/
	public long getPrimaryKey() {
		return _team.getPrimaryKey();
	}

	/**
	* Sets the primary key of this team
	*
	* @param pk the primary key of this team
	*/
	public void setPrimaryKey(long pk) {
		_team.setPrimaryKey(pk);
	}

	/**
	* Gets the team id of this team.
	*
	* @return the team id of this team
	*/
	public long getTeamId() {
		return _team.getTeamId();
	}

	/**
	* Sets the team id of this team.
	*
	* @param teamId the team id of this team
	*/
	public void setTeamId(long teamId) {
		_team.setTeamId(teamId);
	}

	/**
	* Gets the company id of this team.
	*
	* @return the company id of this team
	*/
	public long getCompanyId() {
		return _team.getCompanyId();
	}

	/**
	* Sets the company id of this team.
	*
	* @param companyId the company id of this team
	*/
	public void setCompanyId(long companyId) {
		_team.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this team.
	*
	* @return the user id of this team
	*/
	public long getUserId() {
		return _team.getUserId();
	}

	/**
	* Sets the user id of this team.
	*
	* @param userId the user id of this team
	*/
	public void setUserId(long userId) {
		_team.setUserId(userId);
	}

	/**
	* Gets the user uuid of this team.
	*
	* @return the user uuid of this team
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _team.getUserUuid();
	}

	/**
	* Sets the user uuid of this team.
	*
	* @param userUuid the user uuid of this team
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_team.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this team.
	*
	* @return the user name of this team
	*/
	public java.lang.String getUserName() {
		return _team.getUserName();
	}

	/**
	* Sets the user name of this team.
	*
	* @param userName the user name of this team
	*/
	public void setUserName(java.lang.String userName) {
		_team.setUserName(userName);
	}

	/**
	* Gets the create date of this team.
	*
	* @return the create date of this team
	*/
	public java.util.Date getCreateDate() {
		return _team.getCreateDate();
	}

	/**
	* Sets the create date of this team.
	*
	* @param createDate the create date of this team
	*/
	public void setCreateDate(java.util.Date createDate) {
		_team.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this team.
	*
	* @return the modified date of this team
	*/
	public java.util.Date getModifiedDate() {
		return _team.getModifiedDate();
	}

	/**
	* Sets the modified date of this team.
	*
	* @param modifiedDate the modified date of this team
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_team.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the group id of this team.
	*
	* @return the group id of this team
	*/
	public long getGroupId() {
		return _team.getGroupId();
	}

	/**
	* Sets the group id of this team.
	*
	* @param groupId the group id of this team
	*/
	public void setGroupId(long groupId) {
		_team.setGroupId(groupId);
	}

	/**
	* Gets the name of this team.
	*
	* @return the name of this team
	*/
	public java.lang.String getName() {
		return _team.getName();
	}

	/**
	* Sets the name of this team.
	*
	* @param name the name of this team
	*/
	public void setName(java.lang.String name) {
		_team.setName(name);
	}

	/**
	* Gets the description of this team.
	*
	* @return the description of this team
	*/
	public java.lang.String getDescription() {
		return _team.getDescription();
	}

	/**
	* Sets the description of this team.
	*
	* @param description the description of this team
	*/
	public void setDescription(java.lang.String description) {
		_team.setDescription(description);
	}

	public boolean isNew() {
		return _team.isNew();
	}

	public void setNew(boolean n) {
		_team.setNew(n);
	}

	public boolean isCachedModel() {
		return _team.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_team.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _team.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_team.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _team.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _team.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_team.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _team.clone();
	}

	public int compareTo(com.liferay.portal.model.Team team) {
		return _team.compareTo(team);
	}

	public int hashCode() {
		return _team.hashCode();
	}

	public com.liferay.portal.model.Team toEscapedModel() {
		return _team.toEscapedModel();
	}

	public java.lang.String toString() {
		return _team.toString();
	}

	public java.lang.String toXmlString() {
		return _team.toXmlString();
	}

	public com.liferay.portal.model.Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _team.getRole();
	}

	public Team getWrappedTeam() {
		return _team;
	}

	private Team _team;
}