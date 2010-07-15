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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.softwarecatalog.model.SCProductVersion;

/**
 * @author    Brian Wing Shun Chan
 * @see       SCProductVersionPersistenceImpl
 * @see       SCProductVersionUtil
 * @generated
 */
public interface SCProductVersionPersistence extends BasePersistence<SCProductVersion> {
	public void cacheResult(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion);

	public void cacheResult(
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions);

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion create(
		long productVersionId);

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion remove(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion findByPrimaryKey(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion fetchByPrimaryKey(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findByProductEntryId(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findByProductEntryId(
		long productEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findByProductEntryId(
		long productEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion findByProductEntryId_First(
		long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion findByProductEntryId_Last(
		long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion[] findByProductEntryId_PrevAndNext(
		long productVersionId, long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion findByDirectDownloadURL(
		java.lang.String directDownloadURL)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion fetchByDirectDownloadURL(
		java.lang.String directDownloadURL)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion fetchByDirectDownloadURL(
		java.lang.String directDownloadURL, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByProductEntryId(long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByDirectDownloadURL(java.lang.String directDownloadURL)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByProductEntryId(long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByDirectDownloadURL(java.lang.String directDownloadURL)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int getSCFrameworkVersionsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsSCFrameworkVersion(long pk, long scFrameworkVersionPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsSCFrameworkVersions(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addSCFrameworkVersion(long pk, long scFrameworkVersionPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addSCFrameworkVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void clearSCFrameworkVersions(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeSCFrameworkVersion(long pk, long scFrameworkVersionPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeSCFrameworkVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setSCFrameworkVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws com.liferay.portal.kernel.exception.SystemException;
}