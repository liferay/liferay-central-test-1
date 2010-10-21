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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.Revision;

/**
 * The persistence interface for the revision service.
 *
 * <p>
 * Never modify or reference this interface directly. Always use {@link RevisionUtil} to access the revision persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RevisionPersistenceImpl
 * @see RevisionUtil
 * @generated
 */
public interface RevisionPersistence extends BasePersistence<Revision> {
	/**
	* Caches the revision in the entity cache if it is enabled.
	*
	* @param revision the revision to cache
	*/
	public void cacheResult(com.liferay.portal.model.Revision revision);

	/**
	* Caches the revisions in the entity cache if it is enabled.
	*
	* @param revisions the revisions to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.Revision> revisions);

	/**
	* Creates a new revision with the primary key. Does not add the revision to the database.
	*
	* @param revisionId the primary key for the new revision
	* @return the new revision
	*/
	public com.liferay.portal.model.Revision create(long revisionId);

	/**
	* Removes the revision with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param revisionId the primary key of the revision to remove
	* @return the revision that was removed
	* @throws com.liferay.portal.NoSuchRevisionException if a revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision remove(long revisionId)
		throws com.liferay.portal.NoSuchRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Revision updateImpl(
		com.liferay.portal.model.Revision revision, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the revision with the primary key or throws a {@link com.liferay.portal.NoSuchRevisionException} if it could not be found.
	*
	* @param revisionId the primary key of the revision to find
	* @return the revision
	* @throws com.liferay.portal.NoSuchRevisionException if a revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision findByPrimaryKey(long revisionId)
		throws com.liferay.portal.NoSuchRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the revision with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param revisionId the primary key of the revision to find
	* @return the revision, or <code>null</code> if a revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision fetchByPrimaryKey(long revisionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the revisions where branchId = &#63;.
	*
	* @param branchId the branch id to search with
	* @return the matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Revision> findByBranchId(
		long branchId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the revisions where branchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param start the lower bound of the range of revisions to return
	* @param end the upper bound of the range of revisions to return (not inclusive)
	* @return the range of matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Revision> findByBranchId(
		long branchId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the revisions where branchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param start the lower bound of the range of revisions to return
	* @param end the upper bound of the range of revisions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Revision> findByBranchId(
		long branchId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first revision in the ordered set where branchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching revision
	* @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision findByBranchId_First(
		long branchId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last revision in the ordered set where branchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching revision
	* @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision findByBranchId_Last(
		long branchId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the revisions before and after the current revision in the ordered set where branchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param revisionId the primary key of the current revision
	* @param branchId the branch id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next revision
	* @throws com.liferay.portal.NoSuchRevisionException if a revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision[] findByBranchId_PrevAndNext(
		long revisionId, long branchId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the revisions where plid = &#63;.
	*
	* @param plid the plid to search with
	* @return the matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Revision> findByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the revisions where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the plid to search with
	* @param start the lower bound of the range of revisions to return
	* @param end the upper bound of the range of revisions to return (not inclusive)
	* @return the range of matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Revision> findByPlid(
		long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the revisions where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the plid to search with
	* @param start the lower bound of the range of revisions to return
	* @param end the upper bound of the range of revisions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Revision> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first revision in the ordered set where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching revision
	* @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision findByPlid_First(long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last revision in the ordered set where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching revision
	* @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision findByPlid_Last(long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the revisions before and after the current revision in the ordered set where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param revisionId the primary key of the current revision
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next revision
	* @throws com.liferay.portal.NoSuchRevisionException if a revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision[] findByPlid_PrevAndNext(
		long revisionId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the revisions where branchId = &#63; and plid = &#63;.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @return the matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Revision> findByB_P(
		long branchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the revisions where branchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param start the lower bound of the range of revisions to return
	* @param end the upper bound of the range of revisions to return (not inclusive)
	* @return the range of matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Revision> findByB_P(
		long branchId, long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the revisions where branchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param start the lower bound of the range of revisions to return
	* @param end the upper bound of the range of revisions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Revision> findByB_P(
		long branchId, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first revision in the ordered set where branchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching revision
	* @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision findByB_P_First(long branchId,
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last revision in the ordered set where branchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching revision
	* @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision findByB_P_Last(long branchId,
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the revisions before and after the current revision in the ordered set where branchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param revisionId the primary key of the current revision
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next revision
	* @throws com.liferay.portal.NoSuchRevisionException if a revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision[] findByB_P_PrevAndNext(
		long revisionId, long branchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the revision where branchId = &#63; and plid = &#63; and head = &#63; or throws a {@link com.liferay.portal.NoSuchRevisionException} if it could not be found.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param head the head to search with
	* @return the matching revision
	* @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision findByB_P_H(long branchId,
		long plid, boolean head)
		throws com.liferay.portal.NoSuchRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the revision where branchId = &#63; and plid = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param head the head to search with
	* @return the matching revision, or <code>null</code> if a matching revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision fetchByB_P_H(long branchId,
		long plid, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the revision where branchId = &#63; and plid = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param head the head to search with
	* @return the matching revision, or <code>null</code> if a matching revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision fetchByB_P_H(long branchId,
		long plid, boolean head, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @return the matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Revision> findByB_P_S(
		long branchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @param start the lower bound of the range of revisions to return
	* @param end the upper bound of the range of revisions to return (not inclusive)
	* @return the range of matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Revision> findByB_P_S(
		long branchId, long plid, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @param start the lower bound of the range of revisions to return
	* @param end the upper bound of the range of revisions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Revision> findByB_P_S(
		long branchId, long plid, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first revision in the ordered set where branchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching revision
	* @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision findByB_P_S_First(long branchId,
		long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last revision in the ordered set where branchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching revision
	* @throws com.liferay.portal.NoSuchRevisionException if a matching revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision findByB_P_S_Last(long branchId,
		long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the revisions before and after the current revision in the ordered set where branchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param revisionId the primary key of the current revision
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next revision
	* @throws com.liferay.portal.NoSuchRevisionException if a revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision[] findByB_P_S_PrevAndNext(
		long revisionId, long branchId, long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the revisions.
	*
	* @return the revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Revision> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of revisions to return
	* @param end the upper bound of the range of revisions to return (not inclusive)
	* @return the range of revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Revision> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of revisions to return
	* @param end the upper bound of the range of revisions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Revision> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the revisions where branchId = &#63; from the database.
	*
	* @param branchId the branch id to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByBranchId(long branchId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the revisions where plid = &#63; from the database.
	*
	* @param plid the plid to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the revisions where branchId = &#63; and plid = &#63; from the database.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByB_P(long branchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the revision where branchId = &#63; and plid = &#63; and head = &#63; from the database.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param head the head to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByB_P_H(long branchId, long plid, boolean head)
		throws com.liferay.portal.NoSuchRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the revisions where branchId = &#63; and plid = &#63; and status = &#63; from the database.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByB_P_S(long branchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the revisions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the revisions where branchId = &#63;.
	*
	* @param branchId the branch id to search with
	* @return the number of matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByBranchId(long branchId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the revisions where plid = &#63;.
	*
	* @param plid the plid to search with
	* @return the number of matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the revisions where branchId = &#63; and plid = &#63;.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @return the number of matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByB_P(long branchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the revisions where branchId = &#63; and plid = &#63; and head = &#63;.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param head the head to search with
	* @return the number of matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByB_P_H(long branchId, long plid, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @return the number of matching revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByB_P_S(long branchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the revisions.
	*
	* @return the number of revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}