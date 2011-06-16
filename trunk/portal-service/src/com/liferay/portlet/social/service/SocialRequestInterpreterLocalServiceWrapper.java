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

package com.liferay.portlet.social.service;

/**
 * <p>
 * This class is a wrapper for {@link SocialRequestInterpreterLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRequestInterpreterLocalService
 * @generated
 */
public class SocialRequestInterpreterLocalServiceWrapper
	implements SocialRequestInterpreterLocalService {
	public SocialRequestInterpreterLocalServiceWrapper(
		SocialRequestInterpreterLocalService socialRequestInterpreterLocalService) {
		_socialRequestInterpreterLocalService = socialRequestInterpreterLocalService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _socialRequestInterpreterLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialRequestInterpreterLocalService.setBeanIdentifier(beanIdentifier);
	}

	public void addRequestInterpreter(
		com.liferay.portlet.social.model.SocialRequestInterpreter requestInterpreter) {
		_socialRequestInterpreterLocalService.addRequestInterpreter(requestInterpreter);
	}

	public void deleteRequestInterpreter(
		com.liferay.portlet.social.model.SocialRequestInterpreter requestInterpreter) {
		_socialRequestInterpreterLocalService.deleteRequestInterpreter(requestInterpreter);
	}

	public com.liferay.portlet.social.model.SocialRequestFeedEntry interpret(
		com.liferay.portlet.social.model.SocialRequest request,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _socialRequestInterpreterLocalService.interpret(request,
			themeDisplay);
	}

	public void processConfirmation(
		com.liferay.portlet.social.model.SocialRequest request,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		_socialRequestInterpreterLocalService.processConfirmation(request,
			themeDisplay);
	}

	public void processRejection(
		com.liferay.portlet.social.model.SocialRequest request,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		_socialRequestInterpreterLocalService.processRejection(request,
			themeDisplay);
	}

	public SocialRequestInterpreterLocalService getWrappedSocialRequestInterpreterLocalService() {
		return _socialRequestInterpreterLocalService;
	}

	public void setWrappedSocialRequestInterpreterLocalService(
		SocialRequestInterpreterLocalService socialRequestInterpreterLocalService) {
		_socialRequestInterpreterLocalService = socialRequestInterpreterLocalService;
	}

	private SocialRequestInterpreterLocalService _socialRequestInterpreterLocalService;
}