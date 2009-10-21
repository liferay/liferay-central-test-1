/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.softwarecatalog.model.SCProductEntry;

/**
 * <a href="SCProductEntryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductEntryPersistenceImpl
 * @see       SCProductEntryUtil
 * @generated
 */
public interface SCProductEntryPersistence extends BasePersistence<SCProductEntry> {
	public void cacheResult(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry);

	public void cacheResult(
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries);

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry create(
		long productEntryId);

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry remove(
		long productEntryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry findByPrimaryKey(
		long productEntryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry fetchByPrimaryKey(
		long productEntryId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry[] findByGroupId_PrevAndNext(
		long productEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry[] findByCompanyId_PrevAndNext(
		long productEntryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByG_U(
		long groupId, long userId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry[] findByG_U_PrevAndNext(
		long productEntryId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry findByRG_RA(
		java.lang.String repoGroupId, java.lang.String repoArtifactId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry fetchByRG_RA(
		java.lang.String repoGroupId, java.lang.String repoArtifactId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry fetchByRG_RA(
		java.lang.String repoGroupId, java.lang.String repoArtifactId,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException;

	public void removeByRG_RA(java.lang.String repoGroupId,
		java.lang.String repoArtifactId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException;

	public int countByRG_RA(java.lang.String repoGroupId,
		java.lang.String repoArtifactId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk, int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int getSCLicensesSize(long pk)
		throws com.liferay.portal.SystemException;

	public boolean containsSCLicense(long pk, long scLicensePK)
		throws com.liferay.portal.SystemException;

	public boolean containsSCLicenses(long pk)
		throws com.liferay.portal.SystemException;

	public void addSCLicense(long pk, long scLicensePK)
		throws com.liferay.portal.SystemException;

	public void addSCLicense(long pk,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.SystemException;

	public void addSCLicenses(long pk, long[] scLicensePKs)
		throws com.liferay.portal.SystemException;

	public void addSCLicenses(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses)
		throws com.liferay.portal.SystemException;

	public void clearSCLicenses(long pk)
		throws com.liferay.portal.SystemException;

	public void removeSCLicense(long pk, long scLicensePK)
		throws com.liferay.portal.SystemException;

	public void removeSCLicense(long pk,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.SystemException;

	public void removeSCLicenses(long pk, long[] scLicensePKs)
		throws com.liferay.portal.SystemException;

	public void removeSCLicenses(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses)
		throws com.liferay.portal.SystemException;

	public void setSCLicenses(long pk, long[] scLicensePKs)
		throws com.liferay.portal.SystemException;

	public void setSCLicenses(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses)
		throws com.liferay.portal.SystemException;
}