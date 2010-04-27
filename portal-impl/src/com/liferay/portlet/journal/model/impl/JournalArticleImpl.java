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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.util.LocaleTransformerListener;

/**
 * <a href="JournalArticleImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class JournalArticleImpl
	extends JournalArticleModelImpl implements JournalArticle {

	public static String getContentByLocale(
		String content, boolean templateDriven, String languageId) {

		LocaleTransformerListener listener = new LocaleTransformerListener();

		listener.setTemplateDriven(templateDriven);
		listener.setLanguageId(languageId);

		return listener.onXml(content);
	}

	public JournalArticleImpl() {
	}

	public String[] getAvailableLocales() {
		return LocalizationUtil.getAvailableLocales(getContent());
	}

	public String getContentByLocale(String languageId) {
		return getContentByLocale(getContent(), isTemplateDriven(), languageId);
	}

	public String getDefaultLocale() {
		String xml = getContent();

		if (xml == null) {
			return StringPool.BLANK;
		}

		String defaultLanguageId = LocalizationUtil.getDefaultLocale(xml);

		if (isTemplateDriven() && Validator.isNull(defaultLanguageId)) {
			defaultLanguageId = LocaleUtil.toLanguageId(
				LocaleUtil.getDefault());
		}

		return defaultLanguageId;
	}

	public String getSmallImageType() throws PortalException, SystemException {
		if (_smallImageType == null && isSmallImage()) {
			Image smallImage =  ImageLocalServiceUtil.getImage(
				getSmallImageId());

			_smallImageType = smallImage.getType();
		}

		return _smallImageType;
	}

	public boolean isApproved() {
		if (getStatus() == WorkflowConstants.STATUS_APPROVED) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isExpired() {
		if (getStatus() == WorkflowConstants.STATUS_EXPIRED) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isTemplateDriven() {
		if (Validator.isNull(getStructureId())) {
			return false;
		}
		else {
			return true;
		}
	}

	public void setSmallImageType(String smallImageType) {
		_smallImageType = smallImageType;
	}

	private String _smallImageType;

}