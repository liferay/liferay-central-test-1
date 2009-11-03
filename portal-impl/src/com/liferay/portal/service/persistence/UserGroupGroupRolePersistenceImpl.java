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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchUserGroupGroupRoleException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.UserGroupGroupRole;
import com.liferay.portal.model.impl.UserGroupGroupRoleImpl;
import com.liferay.portal.model.impl.UserGroupGroupRoleModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="UserGroupGroupRolePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupGroupRolePersistence
 * @see       UserGroupGroupRoleUtil
 * @generated
 */
public class UserGroupGroupRolePersistenceImpl extends BasePersistenceImpl<UserGroupGroupRole>
	implements UserGroupGroupRolePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = UserGroupGroupRoleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERGROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERGROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERGROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_ROLEID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByRoleId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ROLEID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByRoleId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ROLEID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByRoleId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_U_G = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU_G",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_U_G = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU_G",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_G = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_G",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_R = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_R = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_R = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(UserGroupGroupRole userGroupGroupRole) {
		EntityCacheUtil.putResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey(),
			userGroupGroupRole);
	}

	public void cacheResult(List<UserGroupGroupRole> userGroupGroupRoles) {
		for (UserGroupGroupRole userGroupGroupRole : userGroupGroupRoles) {
			if (EntityCacheUtil.getResult(
						UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
						UserGroupGroupRoleImpl.class,
						userGroupGroupRole.getPrimaryKey(), this) == null) {
				cacheResult(userGroupGroupRole);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(UserGroupGroupRoleImpl.class.getName());
		EntityCacheUtil.clearCache(UserGroupGroupRoleImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public UserGroupGroupRole create(UserGroupGroupRolePK userGroupGroupRolePK) {
		UserGroupGroupRole userGroupGroupRole = new UserGroupGroupRoleImpl();

		userGroupGroupRole.setNew(true);
		userGroupGroupRole.setPrimaryKey(userGroupGroupRolePK);

		return userGroupGroupRole;
	}

	public UserGroupGroupRole remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove((UserGroupGroupRolePK)primaryKey);
	}

	public UserGroupGroupRole remove(UserGroupGroupRolePK userGroupGroupRolePK)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole userGroupGroupRole = (UserGroupGroupRole)session.get(UserGroupGroupRoleImpl.class,
					userGroupGroupRolePK);

			if (userGroupGroupRole == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No UserGroupGroupRole exists with the primary key " +
						userGroupGroupRolePK);
				}

				throw new NoSuchUserGroupGroupRoleException(
					"No UserGroupGroupRole exists with the primary key " +
					userGroupGroupRolePK);
			}

			return remove(userGroupGroupRole);
		}
		catch (NoSuchUserGroupGroupRoleException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public UserGroupGroupRole remove(UserGroupGroupRole userGroupGroupRole)
		throws SystemException {
		for (ModelListener<UserGroupGroupRole> listener : listeners) {
			listener.onBeforeRemove(userGroupGroupRole);
		}

		userGroupGroupRole = removeImpl(userGroupGroupRole);

		for (ModelListener<UserGroupGroupRole> listener : listeners) {
			listener.onAfterRemove(userGroupGroupRole);
		}

		return userGroupGroupRole;
	}

	protected UserGroupGroupRole removeImpl(
		UserGroupGroupRole userGroupGroupRole) throws SystemException {
		userGroupGroupRole = toUnwrappedModel(userGroupGroupRole);

		Session session = null;

		try {
			session = openSession();

			if (userGroupGroupRole.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(UserGroupGroupRoleImpl.class,
						userGroupGroupRole.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(userGroupGroupRole);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey());

		return userGroupGroupRole;
	}

	public UserGroupGroupRole updateImpl(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole,
		boolean merge) throws SystemException {
		userGroupGroupRole = toUnwrappedModel(userGroupGroupRole);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, userGroupGroupRole, merge);

			userGroupGroupRole.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey(),
			userGroupGroupRole);

		return userGroupGroupRole;
	}

	protected UserGroupGroupRole toUnwrappedModel(
		UserGroupGroupRole userGroupGroupRole) {
		if (userGroupGroupRole instanceof UserGroupGroupRoleImpl) {
			return userGroupGroupRole;
		}

		UserGroupGroupRoleImpl userGroupGroupRoleImpl = new UserGroupGroupRoleImpl();

		userGroupGroupRoleImpl.setNew(userGroupGroupRole.isNew());
		userGroupGroupRoleImpl.setPrimaryKey(userGroupGroupRole.getPrimaryKey());

		userGroupGroupRoleImpl.setUserGroupId(userGroupGroupRole.getUserGroupId());
		userGroupGroupRoleImpl.setGroupId(userGroupGroupRole.getGroupId());
		userGroupGroupRoleImpl.setRoleId(userGroupGroupRole.getRoleId());

		return userGroupGroupRoleImpl;
	}

	public UserGroupGroupRole findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey((UserGroupGroupRolePK)primaryKey);
	}

	public UserGroupGroupRole findByPrimaryKey(
		UserGroupGroupRolePK userGroupGroupRolePK)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = fetchByPrimaryKey(userGroupGroupRolePK);

		if (userGroupGroupRole == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No UserGroupGroupRole exists with the primary key " +
					userGroupGroupRolePK);
			}

			throw new NoSuchUserGroupGroupRoleException(
				"No UserGroupGroupRole exists with the primary key " +
				userGroupGroupRolePK);
		}

		return userGroupGroupRole;
	}

	public UserGroupGroupRole fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey((UserGroupGroupRolePK)primaryKey);
	}

	public UserGroupGroupRole fetchByPrimaryKey(
		UserGroupGroupRolePK userGroupGroupRolePK) throws SystemException {
		UserGroupGroupRole userGroupGroupRole = (UserGroupGroupRole)EntityCacheUtil.getResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
				UserGroupGroupRoleImpl.class, userGroupGroupRolePK, this);

		if (userGroupGroupRole == null) {
			Session session = null;

			try {
				session = openSession();

				userGroupGroupRole = (UserGroupGroupRole)session.get(UserGroupGroupRoleImpl.class,
						userGroupGroupRolePK);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (userGroupGroupRole != null) {
					cacheResult(userGroupGroupRole);
				}

				closeSession(session);
			}
		}

		return userGroupGroupRole;
	}

	public List<UserGroupGroupRole> findByUserGroupId(long userGroupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userGroupId) };

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERGROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ");

				query.append("userGroupGroupRole.id.userGroupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERGROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserGroupGroupRole> findByUserGroupId(long userGroupId,
		int start, int end) throws SystemException {
		return findByUserGroupId(userGroupId, start, end, null);
	}

	public List<UserGroupGroupRole> findByUserGroupId(long userGroupId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userGroupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERGROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ");

				query.append("userGroupGroupRole.id.userGroupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("userGroupGroupRole.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERGROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserGroupGroupRole findByUserGroupId_First(long userGroupId,
		OrderByComparator obc)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		List<UserGroupGroupRole> list = findByUserGroupId(userGroupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupGroupRole exists with the key {");

			msg.append("userGroupId=" + userGroupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole findByUserGroupId_Last(long userGroupId,
		OrderByComparator obc)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		int count = countByUserGroupId(userGroupId);

		List<UserGroupGroupRole> list = findByUserGroupId(userGroupId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupGroupRole exists with the key {");

			msg.append("userGroupId=" + userGroupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole[] findByUserGroupId_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long userGroupId,
		OrderByComparator obc)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		int count = countByUserGroupId(userGroupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ");

			query.append("userGroupGroupRole.id.userGroupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("userGroupGroupRole.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userGroupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userGroupGroupRole);

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = (UserGroupGroupRole)objArray[0];
			array[1] = (UserGroupGroupRole)objArray[1];
			array[2] = (UserGroupGroupRole)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserGroupGroupRole> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ");

				query.append("userGroupGroupRole.id.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserGroupGroupRole> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<UserGroupGroupRole> findByGroupId(long groupId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ");

				query.append("userGroupGroupRole.id.groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("userGroupGroupRole.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserGroupGroupRole findByGroupId_First(long groupId,
		OrderByComparator obc)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		List<UserGroupGroupRole> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupGroupRole exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole findByGroupId_Last(long groupId,
		OrderByComparator obc)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		int count = countByGroupId(groupId);

		List<UserGroupGroupRole> list = findByGroupId(groupId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupGroupRole exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole[] findByGroupId_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long groupId,
		OrderByComparator obc)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ");

			query.append("userGroupGroupRole.id.groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("userGroupGroupRole.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userGroupGroupRole);

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = (UserGroupGroupRole)objArray[0];
			array[1] = (UserGroupGroupRole)objArray[1];
			array[2] = (UserGroupGroupRole)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserGroupGroupRole> findByRoleId(long roleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(roleId) };

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ROLEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ");

				query.append("userGroupGroupRole.id.roleId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ROLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserGroupGroupRole> findByRoleId(long roleId, int start, int end)
		throws SystemException {
		return findByRoleId(roleId, start, end, null);
	}

	public List<UserGroupGroupRole> findByRoleId(long roleId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(roleId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ROLEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ");

				query.append("userGroupGroupRole.id.roleId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("userGroupGroupRole.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ROLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserGroupGroupRole findByRoleId_First(long roleId,
		OrderByComparator obc)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		List<UserGroupGroupRole> list = findByRoleId(roleId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupGroupRole exists with the key {");

			msg.append("roleId=" + roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole findByRoleId_Last(long roleId,
		OrderByComparator obc)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		int count = countByRoleId(roleId);

		List<UserGroupGroupRole> list = findByRoleId(roleId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupGroupRole exists with the key {");

			msg.append("roleId=" + roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole[] findByRoleId_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long roleId,
		OrderByComparator obc)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		int count = countByRoleId(roleId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ");

			query.append("userGroupGroupRole.id.roleId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("userGroupGroupRole.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(roleId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userGroupGroupRole);

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = (UserGroupGroupRole)objArray[0];
			array[1] = (UserGroupGroupRole)objArray[1];
			array[2] = (UserGroupGroupRole)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserGroupGroupRole> findByU_G(long userGroupId, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userGroupId), new Long(groupId)
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_G,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ");

				query.append("userGroupGroupRole.id.userGroupId = ?");

				query.append(" AND ");

				query.append("userGroupGroupRole.id.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_G, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserGroupGroupRole> findByU_G(long userGroupId, long groupId,
		int start, int end) throws SystemException {
		return findByU_G(userGroupId, groupId, start, end, null);
	}

	public List<UserGroupGroupRole> findByU_G(long userGroupId, long groupId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userGroupId), new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_U_G,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ");

				query.append("userGroupGroupRole.id.userGroupId = ?");

				query.append(" AND ");

				query.append("userGroupGroupRole.id.groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("userGroupGroupRole.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

				qPos.add(groupId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_U_G,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserGroupGroupRole findByU_G_First(long userGroupId, long groupId,
		OrderByComparator obc)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		List<UserGroupGroupRole> list = findByU_G(userGroupId, groupId, 0, 1,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupGroupRole exists with the key {");

			msg.append("userGroupId=" + userGroupId);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole findByU_G_Last(long userGroupId, long groupId,
		OrderByComparator obc)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		int count = countByU_G(userGroupId, groupId);

		List<UserGroupGroupRole> list = findByU_G(userGroupId, groupId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupGroupRole exists with the key {");

			msg.append("userGroupId=" + userGroupId);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole[] findByU_G_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long userGroupId,
		long groupId, OrderByComparator obc)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		int count = countByU_G(userGroupId, groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ");

			query.append("userGroupGroupRole.id.userGroupId = ?");

			query.append(" AND ");

			query.append("userGroupGroupRole.id.groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("userGroupGroupRole.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userGroupId);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userGroupGroupRole);

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = (UserGroupGroupRole)objArray[0];
			array[1] = (UserGroupGroupRole)objArray[1];
			array[2] = (UserGroupGroupRole)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserGroupGroupRole> findByG_R(long groupId, long roleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(roleId) };

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_R,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ");

				query.append("userGroupGroupRole.id.groupId = ?");

				query.append(" AND ");

				query.append("userGroupGroupRole.id.roleId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(roleId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_R, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserGroupGroupRole> findByG_R(long groupId, long roleId,
		int start, int end) throws SystemException {
		return findByG_R(groupId, roleId, start, end, null);
	}

	public List<UserGroupGroupRole> findByG_R(long groupId, long roleId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(roleId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_R,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ");

				query.append("userGroupGroupRole.id.groupId = ?");

				query.append(" AND ");

				query.append("userGroupGroupRole.id.roleId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("userGroupGroupRole.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(roleId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_R,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserGroupGroupRole findByG_R_First(long groupId, long roleId,
		OrderByComparator obc)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		List<UserGroupGroupRole> list = findByG_R(groupId, roleId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupGroupRole exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("roleId=" + roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole findByG_R_Last(long groupId, long roleId,
		OrderByComparator obc)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		int count = countByG_R(groupId, roleId);

		List<UserGroupGroupRole> list = findByG_R(groupId, roleId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserGroupGroupRole exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("roleId=" + roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserGroupGroupRole[] findByG_R_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long groupId, long roleId,
		OrderByComparator obc)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		int count = countByG_R(groupId, roleId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ");

			query.append("userGroupGroupRole.id.groupId = ?");

			query.append(" AND ");

			query.append("userGroupGroupRole.id.roleId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("userGroupGroupRole.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(roleId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userGroupGroupRole);

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = (UserGroupGroupRole)objArray[0];
			array[1] = (UserGroupGroupRole)objArray[1];
			array[2] = (UserGroupGroupRole)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserGroupGroupRole> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<UserGroupGroupRole> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<UserGroupGroupRole> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("userGroupGroupRole.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserGroupGroupRole>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUserGroupId(long userGroupId) throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findByUserGroupId(
				userGroupId)) {
			remove(userGroupGroupRole);
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findByGroupId(groupId)) {
			remove(userGroupGroupRole);
		}
	}

	public void removeByRoleId(long roleId) throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findByRoleId(roleId)) {
			remove(userGroupGroupRole);
		}
	}

	public void removeByU_G(long userGroupId, long groupId)
		throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findByU_G(userGroupId,
				groupId)) {
			remove(userGroupGroupRole);
		}
	}

	public void removeByG_R(long groupId, long roleId)
		throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findByG_R(groupId, roleId)) {
			remove(userGroupGroupRole);
		}
	}

	public void removeAll() throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findAll()) {
			remove(userGroupGroupRole);
		}
	}

	public int countByUserGroupId(long userGroupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userGroupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERGROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(userGroupGroupRole) ");
				query.append(
					"FROM UserGroupGroupRole userGroupGroupRole WHERE ");

				query.append("userGroupGroupRole.id.userGroupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERGROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(userGroupGroupRole) ");
				query.append(
					"FROM UserGroupGroupRole userGroupGroupRole WHERE ");

				query.append("userGroupGroupRole.id.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

	public int countByRoleId(long roleId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(roleId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ROLEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(userGroupGroupRole) ");
				query.append(
					"FROM UserGroupGroupRole userGroupGroupRole WHERE ");

				query.append("userGroupGroupRole.id.roleId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ROLEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_G(long userGroupId, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userGroupId), new Long(groupId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_G,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(userGroupGroupRole) ");
				query.append(
					"FROM UserGroupGroupRole userGroupGroupRole WHERE ");

				query.append("userGroupGroupRole.id.userGroupId = ?");

				query.append(" AND ");

				query.append("userGroupGroupRole.id.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_G, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_R(long groupId, long roleId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(roleId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_R,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(userGroupGroupRole) ");
				query.append(
					"FROM UserGroupGroupRole userGroupGroupRole WHERE ");

				query.append("userGroupGroupRole.id.groupId = ?");

				query.append(" AND ");

				query.append("userGroupGroupRole.id.roleId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(roleId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_R, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(userGroupGroupRole) FROM UserGroupGroupRole userGroupGroupRole");

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

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.UserGroupGroupRole")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<UserGroupGroupRole>> listenersList = new ArrayList<ModelListener<UserGroupGroupRole>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<UserGroupGroupRole>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence.impl")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence.impl")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.BrowserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence.impl")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence.impl")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence.impl")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence.impl")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPrototypePersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPrototypePersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence.impl")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence.impl")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence.impl")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence.impl")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence.impl")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence.impl")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence.impl")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence.impl")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence.impl")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence.impl")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence.impl")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence.impl")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence.impl")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence.impl")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence.impl")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence.impl")
	protected com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence.impl")
	protected com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static Log _log = LogFactoryUtil.getLog(UserGroupGroupRolePersistenceImpl.class);
}