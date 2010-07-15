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
 * This interface is a model that represents the UserTracker table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portal.model.impl.UserTrackerImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserTrackerModel
 * @see       com.liferay.portal.model.impl.UserTrackerImpl
 * @see       com.liferay.portal.model.impl.UserTrackerModelImpl
 * @generated
 */
public interface UserTracker extends UserTrackerModel {
	public java.lang.String getFullName();

	public java.lang.String getEmailAddress();

	public java.util.List<com.liferay.portal.model.UserTrackerPath> getPaths();

	public void addPath(com.liferay.portal.model.UserTrackerPath path);

	public int getHits();

	public int compareTo(com.liferay.portal.model.UserTracker userTracker);
}