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

import com.liferay.portal.NoSuchMembershipRequestException;
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.MembershipRequestImpl;
import com.liferay.portal.model.impl.MembershipRequestModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the membership request service.
 *
 * <p>
 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Never reference this class directly, use {@link MembershipRequestUtil} instead.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MembershipRequestPersistence
 * @see MembershipRequestUtil
 * @generated
 */
public class MembershipRequestPersistenceImpl extends BasePersistenceImpl<MembershipRequest>
	implements MembershipRequestPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = MembershipRequestImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_S = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the membership request in the entity cache if it is enabled.
	 *
	 * @param membershipRequest the membership request to cache
	 */
	public void cacheResult(MembershipRequest membershipRequest) {
		EntityCacheUtil.putResult(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestImpl.class, membershipRequest.getPrimaryKey(),
			membershipRequest);
	}

	/**
	 * Caches the membership requests in the entity cache if it is enabled.
	 *
	 * @param membershipRequests the membership requests to cache
	 */
	public void cacheResult(List<MembershipRequest> membershipRequests) {
		for (MembershipRequest membershipRequest : membershipRequests) {
			if (EntityCacheUtil.getResult(
						MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
						MembershipRequestImpl.class,
						membershipRequest.getPrimaryKey(), this) == null) {
				cacheResult(membershipRequest);
			}
		}
	}

	/**
	 * Clears the cache for all membership requests.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(MembershipRequestImpl.class.getName());
		EntityCacheUtil.clearCache(MembershipRequestImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the membership request.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(MembershipRequest membershipRequest) {
		EntityCacheUtil.removeResult(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestImpl.class, membershipRequest.getPrimaryKey());
	}

	/**
	 * Creates a new membership request with the primary key. Does not add the membership request to the database.
	 *
	 * @param membershipRequestId the primary key for the new membership request
	 * @return the new membership request
	 */
	public MembershipRequest create(long membershipRequestId) {
		MembershipRequest membershipRequest = new MembershipRequestImpl();

		membershipRequest.setNew(true);
		membershipRequest.setPrimaryKey(membershipRequestId);

		return membershipRequest;
	}

	/**
	 * Removes the membership request with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the membership request to remove
	 * @return the membership request that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a membership request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MembershipRequest remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the membership request with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param membershipRequestId the primary key of the membership request to remove
	 * @return the membership request that was removed
	 * @throws com.liferay.portal.NoSuchMembershipRequestException if a membership request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MembershipRequest remove(long membershipRequestId)
		throws NoSuchMembershipRequestException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MembershipRequest membershipRequest = (MembershipRequest)session.get(MembershipRequestImpl.class,
					new Long(membershipRequestId));

			if (membershipRequest == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						membershipRequestId);
				}

				throw new NoSuchMembershipRequestException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					membershipRequestId);
			}

			return remove(membershipRequest);
		}
		catch (NoSuchMembershipRequestException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MembershipRequest removeImpl(MembershipRequest membershipRequest)
		throws SystemException {
		membershipRequest = toUnwrappedModel(membershipRequest);

		Session session = null;

		try {
			session = openSession();

			if (membershipRequest.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(MembershipRequestImpl.class,
						membershipRequest.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(membershipRequest);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestImpl.class, membershipRequest.getPrimaryKey());

		return membershipRequest;
	}

	public MembershipRequest updateImpl(
		com.liferay.portal.model.MembershipRequest membershipRequest,
		boolean merge) throws SystemException {
		membershipRequest = toUnwrappedModel(membershipRequest);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, membershipRequest, merge);

			membershipRequest.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestImpl.class, membershipRequest.getPrimaryKey(),
			membershipRequest);

		return membershipRequest;
	}

	protected MembershipRequest toUnwrappedModel(
		MembershipRequest membershipRequest) {
		if (membershipRequest instanceof MembershipRequestImpl) {
			return membershipRequest;
		}

		MembershipRequestImpl membershipRequestImpl = new MembershipRequestImpl();

		membershipRequestImpl.setNew(membershipRequest.isNew());
		membershipRequestImpl.setPrimaryKey(membershipRequest.getPrimaryKey());

		membershipRequestImpl.setMembershipRequestId(membershipRequest.getMembershipRequestId());
		membershipRequestImpl.setCompanyId(membershipRequest.getCompanyId());
		membershipRequestImpl.setUserId(membershipRequest.getUserId());
		membershipRequestImpl.setCreateDate(membershipRequest.getCreateDate());
		membershipRequestImpl.setGroupId(membershipRequest.getGroupId());
		membershipRequestImpl.setComments(membershipRequest.getComments());
		membershipRequestImpl.setReplyComments(membershipRequest.getReplyComments());
		membershipRequestImpl.setReplyDate(membershipRequest.getReplyDate());
		membershipRequestImpl.setReplierUserId(membershipRequest.getReplierUserId());
		membershipRequestImpl.setStatusId(membershipRequest.getStatusId());

		return membershipRequestImpl;
	}

	/**
	 * Finds the membership request with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the membership request to find
	 * @return the membership request
	 * @throws com.liferay.portal.NoSuchModelException if a membership request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MembershipRequest findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the membership request with the primary key or throws a {@link com.liferay.portal.NoSuchMembershipRequestException} if it could not be found.
	 *
	 * @param membershipRequestId the primary key of the membership request to find
	 * @return the membership request
	 * @throws com.liferay.portal.NoSuchMembershipRequestException if a membership request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MembershipRequest findByPrimaryKey(long membershipRequestId)
		throws NoSuchMembershipRequestException, SystemException {
		MembershipRequest membershipRequest = fetchByPrimaryKey(membershipRequestId);

		if (membershipRequest == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					membershipRequestId);
			}

			throw new NoSuchMembershipRequestException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				membershipRequestId);
		}

		return membershipRequest;
	}

	/**
	 * Finds the membership request with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the membership request to find
	 * @return the membership request, or <code>null</code> if a membership request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MembershipRequest fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the membership request with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param membershipRequestId the primary key of the membership request to find
	 * @return the membership request, or <code>null</code> if a membership request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MembershipRequest fetchByPrimaryKey(long membershipRequestId)
		throws SystemException {
		MembershipRequest membershipRequest = (MembershipRequest)EntityCacheUtil.getResult(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
				MembershipRequestImpl.class, membershipRequestId, this);

		if (membershipRequest == null) {
			Session session = null;

			try {
				session = openSession();

				membershipRequest = (MembershipRequest)session.get(MembershipRequestImpl.class,
						new Long(membershipRequestId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (membershipRequest != null) {
					cacheResult(membershipRequest);
				}

				closeSession(session);
			}
		}

		return membershipRequest;
	}

	/**
	 * Finds all the membership requests where groupId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @return the matching membership requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<MembershipRequest> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the membership requests where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param start the lower bound of the range of membership requests to return
	 * @param end the upper bound of the range of membership requests to return (not inclusive)
	 * @return the range of matching membership requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<MembershipRequest> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the membership requests where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param start the lower bound of the range of membership requests to return
	 * @param end the upper bound of the range of membership requests to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching membership requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<MembershipRequest> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MembershipRequest> list = (List<MembershipRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_MEMBERSHIPREQUEST_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(MembershipRequestModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<MembershipRequest>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MembershipRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first membership request in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching membership request
	 * @throws com.liferay.portal.NoSuchMembershipRequestException if a matching membership request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MembershipRequest findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchMembershipRequestException, SystemException {
		List<MembershipRequest> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMembershipRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last membership request in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching membership request
	 * @throws com.liferay.portal.NoSuchMembershipRequestException if a matching membership request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MembershipRequest findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchMembershipRequestException, SystemException {
		int count = countByGroupId(groupId);

		List<MembershipRequest> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMembershipRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the membership requests before and after the current membership request in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param membershipRequestId the primary key of the current membership request
	 * @param groupId the group id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next membership request
	 * @throws com.liferay.portal.NoSuchMembershipRequestException if a membership request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MembershipRequest[] findByGroupId_PrevAndNext(
		long membershipRequestId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchMembershipRequestException, SystemException {
		MembershipRequest membershipRequest = findByPrimaryKey(membershipRequestId);

		Session session = null;

		try {
			session = openSession();

			MembershipRequest[] array = new MembershipRequestImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, membershipRequest,
					groupId, orderByComparator, true);

			array[1] = membershipRequest;

			array[2] = getByGroupId_PrevAndNext(session, membershipRequest,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MembershipRequest getByGroupId_PrevAndNext(Session session,
		MembershipRequest membershipRequest, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MEMBERSHIPREQUEST_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(MembershipRequestModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(membershipRequest);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MembershipRequest> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the membership requests where userId = &#63;.
	 *
	 * @param userId the user id to search with
	 * @return the matching membership requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<MembershipRequest> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the membership requests where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user id to search with
	 * @param start the lower bound of the range of membership requests to return
	 * @param end the upper bound of the range of membership requests to return (not inclusive)
	 * @return the range of matching membership requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<MembershipRequest> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the membership requests where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user id to search with
	 * @param start the lower bound of the range of membership requests to return
	 * @param end the upper bound of the range of membership requests to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching membership requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<MembershipRequest> findByUserId(long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				userId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MembershipRequest> list = (List<MembershipRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_MEMBERSHIPREQUEST_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(MembershipRequestModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<MembershipRequest>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MembershipRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first membership request in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching membership request
	 * @throws com.liferay.portal.NoSuchMembershipRequestException if a matching membership request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MembershipRequest findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchMembershipRequestException, SystemException {
		List<MembershipRequest> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMembershipRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last membership request in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching membership request
	 * @throws com.liferay.portal.NoSuchMembershipRequestException if a matching membership request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MembershipRequest findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchMembershipRequestException, SystemException {
		int count = countByUserId(userId);

		List<MembershipRequest> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMembershipRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the membership requests before and after the current membership request in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param membershipRequestId the primary key of the current membership request
	 * @param userId the user id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next membership request
	 * @throws com.liferay.portal.NoSuchMembershipRequestException if a membership request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MembershipRequest[] findByUserId_PrevAndNext(
		long membershipRequestId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchMembershipRequestException, SystemException {
		MembershipRequest membershipRequest = findByPrimaryKey(membershipRequestId);

		Session session = null;

		try {
			session = openSession();

			MembershipRequest[] array = new MembershipRequestImpl[3];

			array[0] = getByUserId_PrevAndNext(session, membershipRequest,
					userId, orderByComparator, true);

			array[1] = membershipRequest;

			array[2] = getByUserId_PrevAndNext(session, membershipRequest,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MembershipRequest getByUserId_PrevAndNext(Session session,
		MembershipRequest membershipRequest, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MEMBERSHIPREQUEST_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(MembershipRequestModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(membershipRequest);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MembershipRequest> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the membership requests where groupId = &#63; and statusId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param statusId the status id to search with
	 * @return the matching membership requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<MembershipRequest> findByG_S(long groupId, int statusId)
		throws SystemException {
		return findByG_S(groupId, statusId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the membership requests where groupId = &#63; and statusId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param statusId the status id to search with
	 * @param start the lower bound of the range of membership requests to return
	 * @param end the upper bound of the range of membership requests to return (not inclusive)
	 * @return the range of matching membership requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<MembershipRequest> findByG_S(long groupId, int statusId,
		int start, int end) throws SystemException {
		return findByG_S(groupId, statusId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the membership requests where groupId = &#63; and statusId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param statusId the status id to search with
	 * @param start the lower bound of the range of membership requests to return
	 * @param end the upper bound of the range of membership requests to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching membership requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<MembershipRequest> findByG_S(long groupId, int statusId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, statusId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MembershipRequest> list = (List<MembershipRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(4 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_MEMBERSHIPREQUEST_WHERE);

				query.append(_FINDER_COLUMN_G_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_S_STATUSID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(MembershipRequestModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(statusId);

				list = (List<MembershipRequest>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MembershipRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first membership request in the ordered set where groupId = &#63; and statusId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param statusId the status id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching membership request
	 * @throws com.liferay.portal.NoSuchMembershipRequestException if a matching membership request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MembershipRequest findByG_S_First(long groupId, int statusId,
		OrderByComparator orderByComparator)
		throws NoSuchMembershipRequestException, SystemException {
		List<MembershipRequest> list = findByG_S(groupId, statusId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", statusId=");
			msg.append(statusId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMembershipRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last membership request in the ordered set where groupId = &#63; and statusId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param statusId the status id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching membership request
	 * @throws com.liferay.portal.NoSuchMembershipRequestException if a matching membership request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MembershipRequest findByG_S_Last(long groupId, int statusId,
		OrderByComparator orderByComparator)
		throws NoSuchMembershipRequestException, SystemException {
		int count = countByG_S(groupId, statusId);

		List<MembershipRequest> list = findByG_S(groupId, statusId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", statusId=");
			msg.append(statusId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMembershipRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the membership requests before and after the current membership request in the ordered set where groupId = &#63; and statusId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param membershipRequestId the primary key of the current membership request
	 * @param groupId the group id to search with
	 * @param statusId the status id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next membership request
	 * @throws com.liferay.portal.NoSuchMembershipRequestException if a membership request with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MembershipRequest[] findByG_S_PrevAndNext(long membershipRequestId,
		long groupId, int statusId, OrderByComparator orderByComparator)
		throws NoSuchMembershipRequestException, SystemException {
		MembershipRequest membershipRequest = findByPrimaryKey(membershipRequestId);

		Session session = null;

		try {
			session = openSession();

			MembershipRequest[] array = new MembershipRequestImpl[3];

			array[0] = getByG_S_PrevAndNext(session, membershipRequest,
					groupId, statusId, orderByComparator, true);

			array[1] = membershipRequest;

			array[2] = getByG_S_PrevAndNext(session, membershipRequest,
					groupId, statusId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MembershipRequest getByG_S_PrevAndNext(Session session,
		MembershipRequest membershipRequest, long groupId, int statusId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MEMBERSHIPREQUEST_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUSID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(MembershipRequestModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(statusId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(membershipRequest);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MembershipRequest> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the membership requests.
	 *
	 * @return the membership requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<MembershipRequest> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the membership requests.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of membership requests to return
	 * @param end the upper bound of the range of membership requests to return (not inclusive)
	 * @return the range of membership requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<MembershipRequest> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the membership requests.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of membership requests to return
	 * @param end the upper bound of the range of membership requests to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of membership requests
	 * @throws SystemException if a system exception occurred
	 */
	public List<MembershipRequest> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MembershipRequest> list = (List<MembershipRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (orderByComparator != null) {
					query = new StringBundler(2 +
							(orderByComparator.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_MEMBERSHIPREQUEST);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}
				else {
					sql = _SQL_SELECT_MEMBERSHIPREQUEST.concat(MembershipRequestModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<MembershipRequest>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MembershipRequest>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MembershipRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the membership requests where groupId = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (MembershipRequest membershipRequest : findByGroupId(groupId)) {
			remove(membershipRequest);
		}
	}

	/**
	 * Removes all the membership requests where userId = &#63; from the database.
	 *
	 * @param userId the user id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (MembershipRequest membershipRequest : findByUserId(userId)) {
			remove(membershipRequest);
		}
	}

	/**
	 * Removes all the membership requests where groupId = &#63; and statusId = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @param statusId the status id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_S(long groupId, int statusId)
		throws SystemException {
		for (MembershipRequest membershipRequest : findByG_S(groupId, statusId)) {
			remove(membershipRequest);
		}
	}

	/**
	 * Removes all the membership requests from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (MembershipRequest membershipRequest : findAll()) {
			remove(membershipRequest);
		}
	}

	/**
	 * Counts all the membership requests where groupId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @return the number of matching membership requests
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_MEMBERSHIPREQUEST_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the membership requests where userId = &#63;.
	 *
	 * @param userId the user id to search with
	 * @return the number of matching membership requests
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_MEMBERSHIPREQUEST_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the membership requests where groupId = &#63; and statusId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param statusId the status id to search with
	 * @return the number of matching membership requests
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_S(long groupId, int statusId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, statusId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_MEMBERSHIPREQUEST_WHERE);

				query.append(_FINDER_COLUMN_G_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_S_STATUSID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(statusId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the membership requests.
	 *
	 * @return the number of membership requests
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MEMBERSHIPREQUEST);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the membership request persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.MembershipRequest")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MembershipRequest>> listenersList = new ArrayList<ModelListener<MembershipRequest>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MembershipRequest>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupPermissionPersistence.class)
	protected OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PermissionPersistence.class)
	protected PermissionPersistence permissionPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceCodePersistence.class)
	protected ResourceCodePersistence resourceCodePersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_MEMBERSHIPREQUEST = "SELECT membershipRequest FROM MembershipRequest membershipRequest";
	private static final String _SQL_SELECT_MEMBERSHIPREQUEST_WHERE = "SELECT membershipRequest FROM MembershipRequest membershipRequest WHERE ";
	private static final String _SQL_COUNT_MEMBERSHIPREQUEST = "SELECT COUNT(membershipRequest) FROM MembershipRequest membershipRequest";
	private static final String _SQL_COUNT_MEMBERSHIPREQUEST_WHERE = "SELECT COUNT(membershipRequest) FROM MembershipRequest membershipRequest WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "membershipRequest.groupId = ?";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "membershipRequest.userId = ?";
	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "membershipRequest.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STATUSID_2 = "membershipRequest.statusId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "membershipRequest.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MembershipRequest exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MembershipRequest exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(MembershipRequestPersistenceImpl.class);
}