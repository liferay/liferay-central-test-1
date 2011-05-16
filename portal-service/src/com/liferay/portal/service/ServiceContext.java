/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.PortletPreferencesIds;

import java.io.Serializable;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class ServiceContext implements Cloneable, Serializable {

	public ServiceContext() {
		_attributes = new LinkedHashMap<String, Serializable>();
		_expandoBridgeAttributes = new LinkedHashMap<String, Serializable>();
	}

	public Object clone() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(getAddGroupPermissions());
		serviceContext.setAddGuestPermissions(getAddGuestPermissions());
		serviceContext.setAssetCategoryIds(getAssetCategoryIds());
		serviceContext.setAssetTagNames(getAssetTagNames());
		serviceContext.setAttributes(getAttributes());
		serviceContext.setCommand(getCommand());
		serviceContext.setCompanyId(getCompanyId());
		serviceContext.setCreateDate(getCreateDate());
		serviceContext.setCurrentURL(getCurrentURL());
		serviceContext.setExpandoBridgeAttributes(getExpandoBridgeAttributes());
		serviceContext.setGroupPermissions(getGroupPermissions());
		serviceContext.setGuestPermissions(getGuestPermissions());
		serviceContext.setLanguageId(getLanguageId());
		serviceContext.setLayoutFullURL(getLayoutFullURL());
		serviceContext.setLayoutURL(getLayoutURL());
		serviceContext.setModifiedDate(getModifiedDate());
		serviceContext.setPathMain(getPathMain());
		serviceContext.setPlid(getPlid());
		serviceContext.setPortalURL(getPortalURL());
		serviceContext.setPortletPreferencesIds(getPortletPreferencesIds());
		serviceContext.setScopeGroupId(getScopeGroupId());
		serviceContext.setSignedIn(isSignedIn());
		serviceContext.setUserDisplayURL(getUserDisplayURL());
		serviceContext.setUserId(getUserId());
		serviceContext.setUuid(getUuid());
		serviceContext.setWorkflowAction(getWorkflowAction());

		return serviceContext;
	}

	/**
	 * @deprecated As of 6.1, renamed to {@link #getAddGroupPermissions()}
	 */
	public boolean getAddCommunityPermissions() {
		return getAddGroupPermissions();
	}

	public boolean getAddGuestPermissions() {
		return _addGuestPermissions;
	}

	public boolean getAddGroupPermissions() {
		return _addGroupPermissions;
	}

	public long[] getAssetCategoryIds() {
		return _assetCategoryIds;
	}

	public String[] getAssetTagNames() {
		return _assetTagNames;
	}

	public Serializable getAttribute(String name) {
		return _attributes.get(name);
	}

	public Map<String, Serializable> getAttributes() {
		return _attributes;
	}

	public String getCommand() {
		return _command;
	}

	/**
	 * @deprecated As of 6.1, renamed to {@link #getGroupPermissions()}
	 */
	public String[] getCommunityPermissions() {
		return getGroupPermissions();
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public Date getCreateDate(Date defaultCreateDate) {
		if (_createDate != null) {
			return _createDate;
		}
		else if (defaultCreateDate != null) {
			return defaultCreateDate;
		}
		else {
			return new Date();
		}
	}

	public String getCurrentURL() {
		return _currentURL;
	}

	public Map<String, Serializable> getExpandoBridgeAttributes() {
		return _expandoBridgeAttributes;
	}

	public String[] getGroupPermissions() {
		return _groupPermissions;
	}

	public String[] getGuestPermissions() {
		return _guestPermissions;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public String getLayoutFullURL() {
		return _layoutFullURL;
	}

	public String getLayoutURL() {
		return _layoutURL;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public Date getModifiedDate(Date defaultModifiedDate) {
		if (_modifiedDate != null) {
			return _modifiedDate;
		}
		else if (defaultModifiedDate != null) {
			return defaultModifiedDate;
		}
		else {
			return new Date();
		}
	}

	public String getPathMain() {
		return _pathMain;
	}

	public long getPlid() {
		return _plid;
	}

	public String getPortalURL() {
		return _portalURL;
	}

	public PortletPreferencesIds getPortletPreferencesIds() {
		return _portletPreferencesIds;
	}

	public long getScopeGroupId() {
		return _scopeGroupId;
	}

	public String getUserDisplayURL() {
		return _userDisplayURL;
	}

	public long getUserId() {
		return _userId;
	}

	public String getUuid() {
		String uuid = _uuid;

		_uuid = null;

		return uuid;
	}

	public int getWorkflowAction() {
		return _workflowAction;
	}

	public boolean isCommandAdd() {
		if (Validator.equals(_command, Constants.ADD)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isCommandUpdate() {
		if (Validator.equals(_command, Constants.UPDATE)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isSignedIn() {
		return _signedIn;
	}

	public Serializable removeAttribute(String name) {
		return _attributes.remove(name);
	}

	/**
	 * @deprecated As of 6.1, renamed to {@link
	 * 		#setAddGroupPermissions(boolean)}
	 */
	public void setAddCommunityPermissions(boolean addCommunityPermissions) {
		setAddGroupPermissions(addCommunityPermissions);
	}

	public void setAddGroupPermissions(boolean addGroupPermissions) {
		_addGroupPermissions = addGroupPermissions;
	}

	public void setAddGuestPermissions(boolean addGuestPermissions) {
		_addGuestPermissions = addGuestPermissions;
	}

	public void setAssetCategoryIds(long[] assetCategoryIds) {
		_assetCategoryIds = assetCategoryIds;
	}

	public void setAssetTagNames(String[] assetTagNames) {
		_assetTagNames = assetTagNames;
	}

	public void setAttribute(String name, Serializable value) {
		_attributes.put(name, value);
	}

	public void setAttributes(Map<String, Serializable> attributes) {
		_attributes = attributes;
	}

	public void setCommand(String command) {
		_command = command;
	}

	/**
	 * @deprecated As of 6.1, renamed to {@link #setGroupPermissions(String[])}
	 */
	public void setCommunityPermissions(String[] communityPermissions) {
		setGroupPermissions(communityPermissions);
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setCurrentURL(String currentURL) {
		_currentURL = currentURL;
	}

	public void setExpandoBridgeAttributes(
		Map<String, Serializable> expandoBridgeAttributes) {

		_expandoBridgeAttributes = expandoBridgeAttributes;
	}

	public void setGroupPermissions(String[] groupPermissions) {
		_groupPermissions = groupPermissions;
	}

	public void setGuestPermissions(String[] guestPermissions) {
		_guestPermissions = guestPermissions;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public void setLayoutFullURL(String layoutFullURL) {
		_layoutFullURL = layoutFullURL;
	}

	public void setLayoutURL(String layoutURL) {
		_layoutURL = layoutURL;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setPathMain(String pathMain) {
		_pathMain = pathMain;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public void setPortalURL(String portalURL) {
		_portalURL = portalURL;
	}

	public void setPortletPreferencesIds(
		PortletPreferencesIds portletPreferencesIds) {

		_portletPreferencesIds = portletPreferencesIds;
	}

	public void setScopeGroupId(long scopeGroupId) {
		_scopeGroupId = scopeGroupId;
	}

	public void setSignedIn(boolean signedIn) {
		_signedIn = signedIn;
	}

	public void setUserDisplayURL(String userDisplayURL) {
		_userDisplayURL = userDisplayURL;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public void setWorkflowAction(int workflowAction) {
		_workflowAction = workflowAction;
	}

	private boolean _addGroupPermissions;
	private boolean _addGuestPermissions;
	private long[] _assetCategoryIds;
	private String[] _assetTagNames;
	private Map<String, Serializable> _attributes;
	private String _command;
	private long _companyId;
	private Date _createDate;
	private String _currentURL;
	private Map<String, Serializable> _expandoBridgeAttributes;
	private String[] _groupPermissions;
	private String[] _guestPermissions;
	private String _languageId;
	private String _layoutFullURL;
	private String _layoutURL;
	private Date _modifiedDate;
	private String _pathMain;
	private String _portalURL;
	private PortletPreferencesIds _portletPreferencesIds;
	private long _scopeGroupId;
	private boolean _signedIn;
	private String _userDisplayURL;
	private long _plid;
	private int _workflowAction = WorkflowConstants.ACTION_PUBLISH;
	private long _userId;
	private String _uuid;

}