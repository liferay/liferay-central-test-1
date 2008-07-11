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

package com.liferay.portal.ruon.service.base;

import com.liferay.portal.ruon.service.CommunicationLocalService;
import com.liferay.portal.ruon.service.CommunicationLocalServiceFactory;
import com.liferay.portal.ruon.service.CommunicationService;
import com.liferay.portal.ruon.service.CommunicationServiceFactory;
import com.liferay.portal.ruon.service.PresenceLocalService;
import com.liferay.portal.ruon.service.PresenceStatusesLocalService;
import com.liferay.portal.ruon.service.PresenceStatusesLocalServiceFactory;
import com.liferay.portal.ruon.service.PresenceStatusesService;
import com.liferay.portal.ruon.service.PresenceStatusesServiceFactory;
import com.liferay.portal.ruon.service.PresenceUserLocalService;
import com.liferay.portal.ruon.service.PresenceUserLocalServiceFactory;
import com.liferay.portal.ruon.service.PresenceUserService;
import com.liferay.portal.ruon.service.PresenceUserServiceFactory;
import com.liferay.portal.ruon.service.persistence.PresenceStatusesPersistence;
import com.liferay.portal.ruon.service.persistence.PresenceStatusesUtil;
import com.liferay.portal.ruon.service.persistence.PresenceUserPersistence;
import com.liferay.portal.ruon.service.persistence.PresenceUserUtil;

/**
 * <a href="PresenceLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class PresenceLocalServiceBaseImpl
	implements PresenceLocalService {
	public PresenceUserLocalService getPresenceUserLocalService() {
		return presenceUserLocalService;
	}

	public void setPresenceUserLocalService(
		PresenceUserLocalService presenceUserLocalService) {
		this.presenceUserLocalService = presenceUserLocalService;
	}

	public PresenceUserService getPresenceUserService() {
		return presenceUserService;
	}

	public void setPresenceUserService(PresenceUserService presenceUserService) {
		this.presenceUserService = presenceUserService;
	}

	public PresenceUserPersistence getPresenceUserPersistence() {
		return presenceUserPersistence;
	}

	public void setPresenceUserPersistence(
		PresenceUserPersistence presenceUserPersistence) {
		this.presenceUserPersistence = presenceUserPersistence;
	}

	public PresenceStatusesLocalService getPresenceStatusesLocalService() {
		return presenceStatusesLocalService;
	}

	public void setPresenceStatusesLocalService(
		PresenceStatusesLocalService presenceStatusesLocalService) {
		this.presenceStatusesLocalService = presenceStatusesLocalService;
	}

	public PresenceStatusesService getPresenceStatusesService() {
		return presenceStatusesService;
	}

	public void setPresenceStatusesService(
		PresenceStatusesService presenceStatusesService) {
		this.presenceStatusesService = presenceStatusesService;
	}

	public PresenceStatusesPersistence getPresenceStatusesPersistence() {
		return presenceStatusesPersistence;
	}

	public void setPresenceStatusesPersistence(
		PresenceStatusesPersistence presenceStatusesPersistence) {
		this.presenceStatusesPersistence = presenceStatusesPersistence;
	}

	public CommunicationLocalService getCommunicationLocalService() {
		return communicationLocalService;
	}

	public void setCommunicationLocalService(
		CommunicationLocalService communicationLocalService) {
		this.communicationLocalService = communicationLocalService;
	}

	public CommunicationService getCommunicationService() {
		return communicationService;
	}

	public void setCommunicationService(
		CommunicationService communicationService) {
		this.communicationService = communicationService;
	}

	protected void init() {
		if (presenceUserLocalService == null) {
			presenceUserLocalService = PresenceUserLocalServiceFactory.getImpl();
		}

		if (presenceUserService == null) {
			presenceUserService = PresenceUserServiceFactory.getImpl();
		}

		if (presenceUserPersistence == null) {
			presenceUserPersistence = PresenceUserUtil.getPersistence();
		}

		if (presenceStatusesLocalService == null) {
			presenceStatusesLocalService = PresenceStatusesLocalServiceFactory.getImpl();
		}

		if (presenceStatusesService == null) {
			presenceStatusesService = PresenceStatusesServiceFactory.getImpl();
		}

		if (presenceStatusesPersistence == null) {
			presenceStatusesPersistence = PresenceStatusesUtil.getPersistence();
		}

		if (communicationLocalService == null) {
			communicationLocalService = CommunicationLocalServiceFactory.getImpl();
		}

		if (communicationService == null) {
			communicationService = CommunicationServiceFactory.getImpl();
		}
	}

	protected PresenceUserLocalService presenceUserLocalService;
	protected PresenceUserService presenceUserService;
	protected PresenceUserPersistence presenceUserPersistence;
	protected PresenceStatusesLocalService presenceStatusesLocalService;
	protected PresenceStatusesService presenceStatusesService;
	protected PresenceStatusesPersistence presenceStatusesPersistence;
	protected CommunicationLocalService communicationLocalService;
	protected CommunicationService communicationService;
}