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

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.model.impl.UserModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="UserPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserPersistenceImpl extends BasePersistenceImpl
	implements UserPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = UserImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_CONTACTID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByContactId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_CONTACTID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByContactId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_EMAILADDRESS = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByEmailAddress", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_EMAILADDRESS = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByEmailAddress",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_EMAILADDRESS = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByEmailAddress", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_OPENID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByOpenId", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_OPENID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByOpenId", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_PORTRAITID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByPortraitId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_PORTRAITID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByPortraitId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_U = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_U = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_DU = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_DU",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_DU = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_DU",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_SN = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_SN",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_SN = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_SN",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_EA = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_EA",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_EA = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_EA",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(User user) {
		EntityCacheUtil.putResult(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserImpl.class, user.getPrimaryKey(), user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONTACTID,
			new Object[] { new Long(user.getContactId()) }, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_OPENID,
			new Object[] { user.getOpenId() }, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PORTRAITID,
			new Object[] { new Long(user.getPortraitId()) }, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U,
			new Object[] {
				new Long(user.getCompanyId()), new Long(user.getUserId())
			}, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DU,
			new Object[] {
				new Long(user.getCompanyId()),
				Boolean.valueOf(user.getDefaultUser())
			}, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_SN,
			new Object[] { new Long(user.getCompanyId()), user.getScreenName() },
			user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_EA,
			new Object[] { new Long(user.getCompanyId()), user.getEmailAddress() },
			user);
	}

	public void cacheResult(List<User> users) {
		for (User user : users) {
			if (EntityCacheUtil.getResult(UserModelImpl.ENTITY_CACHE_ENABLED,
						UserImpl.class, user.getPrimaryKey(), this) == null) {
				cacheResult(user);
			}
		}
	}

	public User create(long userId) {
		User user = new UserImpl();

		user.setNew(true);
		user.setPrimaryKey(userId);

		String uuid = PortalUUIDUtil.generate();

		user.setUuid(uuid);

		return user;
	}

	public User remove(long userId) throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			User user = (User)session.get(UserImpl.class, new Long(userId));

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No User exists with the primary key " + userId);
				}

				throw new NoSuchUserException(
					"No User exists with the primary key " + userId);
			}

			return remove(user);
		}
		catch (NoSuchUserException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public User remove(User user) throws SystemException {
		for (ModelListener<User> listener : listeners) {
			listener.onBeforeRemove(user);
		}

		user = removeImpl(user);

		for (ModelListener<User> listener : listeners) {
			listener.onAfterRemove(user);
		}

		return user;
	}

	protected User removeImpl(User user) throws SystemException {
		try {
			clearGroups.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}

		try {
			clearOrganizations.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}

		try {
			clearPermissions.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}

		try {
			clearRoles.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}

		try {
			clearUserGroups.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}

		Session session = null;

		try {
			session = openSession();

			if (user.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(UserImpl.class,
						user.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(user);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		UserModelImpl userModelImpl = (UserModelImpl)user;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONTACTID,
			new Object[] { new Long(userModelImpl.getOriginalContactId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_OPENID,
			new Object[] { userModelImpl.getOriginalOpenId() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PORTRAITID,
			new Object[] { new Long(userModelImpl.getOriginalPortraitId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U,
			new Object[] {
				new Long(userModelImpl.getOriginalCompanyId()),
				new Long(userModelImpl.getOriginalUserId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DU,
			new Object[] {
				new Long(userModelImpl.getOriginalCompanyId()),
				Boolean.valueOf(userModelImpl.getOriginalDefaultUser())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_SN,
			new Object[] {
				new Long(userModelImpl.getOriginalCompanyId()),
				
			userModelImpl.getOriginalScreenName()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_EA,
			new Object[] {
				new Long(userModelImpl.getOriginalCompanyId()),
				
			userModelImpl.getOriginalEmailAddress()
			});

		EntityCacheUtil.removeResult(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserImpl.class, user.getPrimaryKey());

		return user;
	}

	/**
	 * @deprecated Use <code>update(User user, boolean merge)</code>.
	 */
	public User update(User user) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(User user) method. Use update(User user, boolean merge) instead.");
		}

		return update(user, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        user the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when user is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public User update(User user, boolean merge) throws SystemException {
		boolean isNew = user.isNew();

		for (ModelListener<User> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(user);
			}
			else {
				listener.onBeforeUpdate(user);
			}
		}

		user = updateImpl(user, merge);

		for (ModelListener<User> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(user);
			}
			else {
				listener.onAfterUpdate(user);
			}
		}

		return user;
	}

	public User updateImpl(com.liferay.portal.model.User user, boolean merge)
		throws SystemException {
		boolean isNew = user.isNew();

		UserModelImpl userModelImpl = (UserModelImpl)user;

		if (Validator.isNull(user.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			user.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, user, merge);

			user.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserImpl.class, user.getPrimaryKey(), user);

		if (!isNew &&
				(user.getContactId() != userModelImpl.getOriginalContactId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONTACTID,
				new Object[] { new Long(userModelImpl.getOriginalContactId()) });
		}

		if (isNew ||
				(user.getContactId() != userModelImpl.getOriginalContactId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONTACTID,
				new Object[] { new Long(user.getContactId()) }, user);
		}

		if (!isNew &&
				(!user.getOpenId().equals(userModelImpl.getOriginalOpenId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_OPENID,
				new Object[] { userModelImpl.getOriginalOpenId() });
		}

		if (isNew ||
				(!user.getOpenId().equals(userModelImpl.getOriginalOpenId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_OPENID,
				new Object[] { user.getOpenId() }, user);
		}

		if (!isNew &&
				(user.getPortraitId() != userModelImpl.getOriginalPortraitId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PORTRAITID,
				new Object[] { new Long(userModelImpl.getOriginalPortraitId()) });
		}

		if (isNew ||
				(user.getPortraitId() != userModelImpl.getOriginalPortraitId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PORTRAITID,
				new Object[] { new Long(user.getPortraitId()) }, user);
		}

		if (!isNew &&
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				(user.getUserId() != userModelImpl.getOriginalUserId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U,
				new Object[] {
					new Long(userModelImpl.getOriginalCompanyId()),
					new Long(userModelImpl.getOriginalUserId())
				});
		}

		if (isNew ||
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				(user.getUserId() != userModelImpl.getOriginalUserId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U,
				new Object[] {
					new Long(user.getCompanyId()), new Long(user.getUserId())
				}, user);
		}

		if (!isNew &&
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				(user.getDefaultUser() != userModelImpl.getOriginalDefaultUser()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DU,
				new Object[] {
					new Long(userModelImpl.getOriginalCompanyId()),
					Boolean.valueOf(userModelImpl.getOriginalDefaultUser())
				});
		}

		if (isNew ||
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				(user.getDefaultUser() != userModelImpl.getOriginalDefaultUser()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DU,
				new Object[] {
					new Long(user.getCompanyId()),
					Boolean.valueOf(user.getDefaultUser())
				}, user);
		}

		if (!isNew &&
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				!user.getScreenName()
						 .equals(userModelImpl.getOriginalScreenName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_SN,
				new Object[] {
					new Long(userModelImpl.getOriginalCompanyId()),
					
				userModelImpl.getOriginalScreenName()
				});
		}

		if (isNew ||
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				!user.getScreenName()
						 .equals(userModelImpl.getOriginalScreenName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_SN,
				new Object[] { new Long(user.getCompanyId()), user.getScreenName() },
				user);
		}

		if (!isNew &&
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				!user.getEmailAddress()
						 .equals(userModelImpl.getOriginalEmailAddress()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_EA,
				new Object[] {
					new Long(userModelImpl.getOriginalCompanyId()),
					
				userModelImpl.getOriginalEmailAddress()
				});
		}

		if (isNew ||
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				!user.getEmailAddress()
						 .equals(userModelImpl.getOriginalEmailAddress()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_EA,
				new Object[] {
					new Long(user.getCompanyId()),
					
				user.getEmailAddress()
				}, user);
		}

		return user;
	}

	public User findByPrimaryKey(long userId)
		throws NoSuchUserException, SystemException {
		User user = fetchByPrimaryKey(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No User exists with the primary key " + userId);
			}

			throw new NoSuchUserException(
				"No User exists with the primary key " + userId);
		}

		return user;
	}

	public User fetchByPrimaryKey(long userId) throws SystemException {
		User user = (User)EntityCacheUtil.getResult(UserModelImpl.ENTITY_CACHE_ENABLED,
				UserImpl.class, userId, this);

		if (user == null) {
			Session session = null;

			try {
				session = openSession();

				user = (User)session.get(UserImpl.class, new Long(userId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (user != null) {
					cacheResult(user);
				}

				closeSession(session);
			}
		}

		return user;
	}

	public List<User> findByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<User> list = (List<User>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<User>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<User> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<User> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<User> list = (List<User>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<User>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<User>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public User findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		List<User> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		int count = countByUuid(uuid);

		List<User> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User[] findByUuid_PrevAndNext(long userId, String uuid,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (uuid == null) {
				query.append("uuid_ IS NULL");
			}
			else {
				query.append("uuid_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, user);

			User[] array = new UserImpl[3];

			array[0] = (User)objArray[0];
			array[1] = (User)objArray[1];
			array[2] = (User)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<User> findByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<User> list = (List<User>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<User>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<User> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<User> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<User> list = (List<User>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<User>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<User>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public User findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		List<User> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		int count = countByCompanyId(companyId);

		List<User> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User[] findByCompanyId_PrevAndNext(long userId, long companyId,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.User WHERE ");

			query.append("companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, user);

			User[] array = new UserImpl[3];

			array[0] = (User)objArray[0];
			array[1] = (User)objArray[1];
			array[2] = (User)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public User findByContactId(long contactId)
		throws NoSuchUserException, SystemException {
		User user = fetchByContactId(contactId);

		if (user == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("contactId=" + contactId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByContactId(long contactId) throws SystemException {
		return fetchByContactId(contactId, true);
	}

	public User fetchByContactId(long contactId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(contactId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CONTACTID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("contactId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(contactId);

				List<User> list = q.list();

				result = list;

				User user = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONTACTID,
						finderArgs, list);
				}
				else {
					user = list.get(0);

					cacheResult(user);

					if ((user.getContactId() != contactId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONTACTID,
							finderArgs, list);
					}
				}

				return user;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONTACTID,
						finderArgs, new ArrayList<User>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (User)result;
			}
		}
	}

	public List<User> findByEmailAddress(String emailAddress)
		throws SystemException {
		Object[] finderArgs = new Object[] { emailAddress };

		List<User> list = (List<User>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_EMAILADDRESS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (emailAddress == null) {
					query.append("emailAddress IS NULL");
				}
				else {
					query.append("emailAddress = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (emailAddress != null) {
					qPos.add(emailAddress);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<User>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_EMAILADDRESS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<User> findByEmailAddress(String emailAddress, int start, int end)
		throws SystemException {
		return findByEmailAddress(emailAddress, start, end, null);
	}

	public List<User> findByEmailAddress(String emailAddress, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				emailAddress,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<User> list = (List<User>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_EMAILADDRESS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (emailAddress == null) {
					query.append("emailAddress IS NULL");
				}
				else {
					query.append("emailAddress = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (emailAddress != null) {
					qPos.add(emailAddress);
				}

				list = (List<User>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<User>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_EMAILADDRESS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public User findByEmailAddress_First(String emailAddress,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		List<User> list = findByEmailAddress(emailAddress, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("emailAddress=" + emailAddress);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User findByEmailAddress_Last(String emailAddress,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		int count = countByEmailAddress(emailAddress);

		List<User> list = findByEmailAddress(emailAddress, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("emailAddress=" + emailAddress);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User[] findByEmailAddress_PrevAndNext(long userId,
		String emailAddress, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		int count = countByEmailAddress(emailAddress);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (emailAddress == null) {
				query.append("emailAddress IS NULL");
			}
			else {
				query.append("emailAddress = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (emailAddress != null) {
				qPos.add(emailAddress);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, user);

			User[] array = new UserImpl[3];

			array[0] = (User)objArray[0];
			array[1] = (User)objArray[1];
			array[2] = (User)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public User findByOpenId(String openId)
		throws NoSuchUserException, SystemException {
		User user = fetchByOpenId(openId);

		if (user == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("openId=" + openId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByOpenId(String openId) throws SystemException {
		return fetchByOpenId(openId, true);
	}

	public User fetchByOpenId(String openId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { openId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_OPENID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (openId == null) {
					query.append("openId IS NULL");
				}
				else {
					query.append("openId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (openId != null) {
					qPos.add(openId);
				}

				List<User> list = q.list();

				result = list;

				User user = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_OPENID,
						finderArgs, list);
				}
				else {
					user = list.get(0);

					cacheResult(user);

					if ((user.getOpenId() == null) ||
							!user.getOpenId().equals(openId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_OPENID,
							finderArgs, list);
					}
				}

				return user;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_OPENID,
						finderArgs, new ArrayList<User>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (User)result;
			}
		}
	}

	public User findByPortraitId(long portraitId)
		throws NoSuchUserException, SystemException {
		User user = fetchByPortraitId(portraitId);

		if (user == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("portraitId=" + portraitId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByPortraitId(long portraitId) throws SystemException {
		return fetchByPortraitId(portraitId, true);
	}

	public User fetchByPortraitId(long portraitId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(portraitId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_PORTRAITID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("portraitId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(portraitId);

				List<User> list = q.list();

				result = list;

				User user = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PORTRAITID,
						finderArgs, list);
				}
				else {
					user = list.get(0);

					cacheResult(user);

					if ((user.getPortraitId() != portraitId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PORTRAITID,
							finderArgs, list);
					}
				}

				return user;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PORTRAITID,
						finderArgs, new ArrayList<User>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (User)result;
			}
		}
	}

	public User findByC_U(long companyId, long userId)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_U(companyId, userId);

		if (user == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByC_U(long companyId, long userId)
		throws SystemException {
		return fetchByC_U(companyId, userId, true);
	}

	public User fetchByC_U(long companyId, long userId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), new Long(userId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_U,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				List<User> list = q.list();

				result = list;

				User user = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U,
						finderArgs, list);
				}
				else {
					user = list.get(0);

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U,
							finderArgs, list);
					}
				}

				return user;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U,
						finderArgs, new ArrayList<User>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (User)result;
			}
		}
	}

	public User findByC_DU(long companyId, boolean defaultUser)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_DU(companyId, defaultUser);

		if (user == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("defaultUser=" + defaultUser);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByC_DU(long companyId, boolean defaultUser)
		throws SystemException {
		return fetchByC_DU(companyId, defaultUser, true);
	}

	public User fetchByC_DU(long companyId, boolean defaultUser,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(defaultUser)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_DU,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("defaultUser = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultUser);

				List<User> list = q.list();

				result = list;

				User user = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DU,
						finderArgs, list);
				}
				else {
					user = list.get(0);

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getDefaultUser() != defaultUser)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DU,
							finderArgs, list);
					}
				}

				return user;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DU,
						finderArgs, new ArrayList<User>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (User)result;
			}
		}
	}

	public User findByC_SN(long companyId, String screenName)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_SN(companyId, screenName);

		if (user == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("screenName=" + screenName);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByC_SN(long companyId, String screenName)
		throws SystemException {
		return fetchByC_SN(companyId, screenName, true);
	}

	public User fetchByC_SN(long companyId, String screenName,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), screenName };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_SN,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (screenName == null) {
					query.append("screenName IS NULL");
				}
				else {
					query.append("screenName = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (screenName != null) {
					qPos.add(screenName);
				}

				List<User> list = q.list();

				result = list;

				User user = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_SN,
						finderArgs, list);
				}
				else {
					user = list.get(0);

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getScreenName() == null) ||
							!user.getScreenName().equals(screenName)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_SN,
							finderArgs, list);
					}
				}

				return user;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_SN,
						finderArgs, new ArrayList<User>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (User)result;
			}
		}
	}

	public User findByC_EA(long companyId, String emailAddress)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_EA(companyId, emailAddress);

		if (user == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("emailAddress=" + emailAddress);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByC_EA(long companyId, String emailAddress)
		throws SystemException {
		return fetchByC_EA(companyId, emailAddress, true);
	}

	public User fetchByC_EA(long companyId, String emailAddress,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), emailAddress };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_EA,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (emailAddress == null) {
					query.append("emailAddress IS NULL");
				}
				else {
					query.append("emailAddress = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (emailAddress != null) {
					qPos.add(emailAddress);
				}

				List<User> list = q.list();

				result = list;

				User user = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_EA,
						finderArgs, list);
				}
				else {
					user = list.get(0);

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getEmailAddress() == null) ||
							!user.getEmailAddress().equals(emailAddress)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_EA,
							finderArgs, list);
					}
				}

				return user;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_EA,
						finderArgs, new ArrayList<User>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (User)result;
			}
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

	public List<User> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<User> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<User> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<User> list = (List<User>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.User ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<User>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (User user : findByUuid(uuid)) {
			remove(user);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (User user : findByCompanyId(companyId)) {
			remove(user);
		}
	}

	public void removeByContactId(long contactId)
		throws NoSuchUserException, SystemException {
		User user = findByContactId(contactId);

		remove(user);
	}

	public void removeByEmailAddress(String emailAddress)
		throws SystemException {
		for (User user : findByEmailAddress(emailAddress)) {
			remove(user);
		}
	}

	public void removeByOpenId(String openId)
		throws NoSuchUserException, SystemException {
		User user = findByOpenId(openId);

		remove(user);
	}

	public void removeByPortraitId(long portraitId)
		throws NoSuchUserException, SystemException {
		User user = findByPortraitId(portraitId);

		remove(user);
	}

	public void removeByC_U(long companyId, long userId)
		throws NoSuchUserException, SystemException {
		User user = findByC_U(companyId, userId);

		remove(user);
	}

	public void removeByC_DU(long companyId, boolean defaultUser)
		throws NoSuchUserException, SystemException {
		User user = findByC_DU(companyId, defaultUser);

		remove(user);
	}

	public void removeByC_SN(long companyId, String screenName)
		throws NoSuchUserException, SystemException {
		User user = findByC_SN(companyId, screenName);

		remove(user);
	}

	public void removeByC_EA(long companyId, String emailAddress)
		throws NoSuchUserException, SystemException {
		User user = findByC_EA(companyId, emailAddress);

		remove(user);
	}

	public void removeAll() throws SystemException {
		for (User user : findAll()) {
			remove(user);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByContactId(long contactId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(contactId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CONTACTID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("contactId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(contactId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CONTACTID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByEmailAddress(String emailAddress)
		throws SystemException {
		Object[] finderArgs = new Object[] { emailAddress };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_EMAILADDRESS,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (emailAddress == null) {
					query.append("emailAddress IS NULL");
				}
				else {
					query.append("emailAddress = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (emailAddress != null) {
					qPos.add(emailAddress);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_EMAILADDRESS,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByOpenId(String openId) throws SystemException {
		Object[] finderArgs = new Object[] { openId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_OPENID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (openId == null) {
					query.append("openId IS NULL");
				}
				else {
					query.append("openId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (openId != null) {
					qPos.add(openId);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_OPENID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByPortraitId(long portraitId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(portraitId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PORTRAITID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("portraitId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(portraitId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PORTRAITID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_U(long companyId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_U,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_DU(long companyId, boolean defaultUser)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(defaultUser)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_DU,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("defaultUser = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultUser);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_DU,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_SN(long companyId, String screenName)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), screenName };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_SN,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (screenName == null) {
					query.append("screenName IS NULL");
				}
				else {
					query.append("screenName = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (screenName != null) {
					qPos.add(screenName);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_SN,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_EA(long companyId, String emailAddress)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), emailAddress };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_EA,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (emailAddress == null) {
					query.append("emailAddress IS NULL");
				}
				else {
					query.append("emailAddress = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (emailAddress != null) {
					qPos.add(emailAddress);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_EA,
					finderArgs, count);

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
						"SELECT COUNT(*) FROM com.liferay.portal.model.User");

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

	public List<com.liferay.portal.model.Group> getGroups(long pk)
		throws SystemException {
		return getGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end) throws SystemException {
		return getGroups(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_GROUPS = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_GROUPS, "Users_Groups",
			"getGroups",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portal.model.Group> list = (List<com.liferay.portal.model.Group>)FinderCacheUtil.getResult(FINDER_PATH_GET_GROUPS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETGROUPS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

					sb.append("Group_.name ASC");
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Group_",
					com.liferay.portal.model.impl.GroupImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Group>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.Group>();
				}

				groupPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_GROUPS, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_GROUPS_SIZE = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_GROUPS, "Users_Groups",
			"getGroupsSize", new String[] { Long.class.getName() });

	public int getGroupsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_GROUPS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETGROUPSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_GROUPS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_GROUP = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_GROUPS, "Users_Groups",
			"containsGroup",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsGroup(long pk, long groupPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(groupPK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_GROUP,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsGroup.contains(pk, groupPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_GROUP,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsGroups(long pk) throws SystemException {
		if (getGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addGroup(long pk, long groupPK) throws SystemException {
		try {
			addGroup.add(pk, groupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void addGroup(long pk, com.liferay.portal.model.Group group)
		throws SystemException {
		try {
			addGroup.add(pk, group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void addGroups(long pk, long[] groupPKs) throws SystemException {
		try {
			for (long groupPK : groupPKs) {
				addGroup.add(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void addGroups(long pk, List<com.liferay.portal.model.Group> groups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Group group : groups) {
				addGroup.add(pk, group.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void clearGroups(long pk) throws SystemException {
		try {
			clearGroups.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void removeGroup(long pk, long groupPK) throws SystemException {
		try {
			removeGroup.remove(pk, groupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws SystemException {
		try {
			removeGroup.remove(pk, group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void removeGroups(long pk, long[] groupPKs)
		throws SystemException {
		try {
			for (long groupPK : groupPKs) {
				removeGroup.remove(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void removeGroups(long pk,
		List<com.liferay.portal.model.Group> groups) throws SystemException {
		try {
			for (com.liferay.portal.model.Group group : groups) {
				removeGroup.remove(pk, group.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void setGroups(long pk, long[] groupPKs) throws SystemException {
		try {
			clearGroups.clear(pk);

			for (long groupPK : groupPKs) {
				addGroup.add(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void setGroups(long pk, List<com.liferay.portal.model.Group> groups)
		throws SystemException {
		try {
			clearGroups.clear(pk);

			for (com.liferay.portal.model.Group group : groups) {
				addGroup.add(pk, group.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public List<com.liferay.portal.model.Organization> getOrganizations(long pk)
		throws SystemException {
		return getOrganizations(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end) throws SystemException {
		return getOrganizations(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ORGANIZATIONS = new FinderPath(com.liferay.portal.model.impl.OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ORGS, "Users_Orgs",
			"getOrganizations",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portal.model.Organization> list = (List<com.liferay.portal.model.Organization>)FinderCacheUtil.getResult(FINDER_PATH_GET_ORGANIZATIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETORGANIZATIONS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

					sb.append("Organization_.name ASC");
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Organization_",
					com.liferay.portal.model.impl.OrganizationImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Organization>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.Organization>();
				}

				organizationPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_ORGANIZATIONS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ORGANIZATIONS_SIZE = new FinderPath(com.liferay.portal.model.impl.OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ORGS, "Users_Orgs",
			"getOrganizationsSize", new String[] { Long.class.getName() });

	public int getOrganizationsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ORGANIZATIONS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETORGANIZATIONSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_ORGANIZATIONS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ORGANIZATION = new FinderPath(com.liferay.portal.model.impl.OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ORGS, "Users_Orgs",
			"containsOrganization",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsOrganization(long pk, long organizationPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk),
				
				new Long(organizationPK)
			};

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ORGANIZATION,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsOrganization.contains(pk,
							organizationPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ORGANIZATION,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsOrganizations(long pk) throws SystemException {
		if (getOrganizationsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addOrganization(long pk, long organizationPK)
		throws SystemException {
		try {
			addOrganization.add(pk, organizationPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void addOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		try {
			addOrganization.add(pk, organization.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void addOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			for (long organizationPK : organizationPKs) {
				addOrganization.add(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void addOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Organization organization : organizations) {
				addOrganization.add(pk, organization.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void clearOrganizations(long pk) throws SystemException {
		try {
			clearOrganizations.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void removeOrganization(long pk, long organizationPK)
		throws SystemException {
		try {
			removeOrganization.remove(pk, organizationPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		try {
			removeOrganization.remove(pk, organization.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void removeOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			for (long organizationPK : organizationPKs) {
				removeOrganization.remove(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void removeOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Organization organization : organizations) {
				removeOrganization.remove(pk, organization.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void setOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			clearOrganizations.clear(pk);

			for (long organizationPK : organizationPKs) {
				addOrganization.add(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void setOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			clearOrganizations.clear(pk);

			for (com.liferay.portal.model.Organization organization : organizations) {
				addOrganization.add(pk, organization.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public List<com.liferay.portal.model.Permission> getPermissions(long pk)
		throws SystemException {
		return getPermissions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Permission> getPermissions(long pk,
		int start, int end) throws SystemException {
		return getPermissions(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_PERMISSIONS = new FinderPath(com.liferay.portal.model.impl.PermissionModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_PERMISSIONS,
			"Users_Permissions", "getPermissions",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.Permission> getPermissions(long pk,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portal.model.Permission> list = (List<com.liferay.portal.model.Permission>)FinderCacheUtil.getResult(FINDER_PATH_GET_PERMISSIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETPERMISSIONS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Permission_",
					com.liferay.portal.model.impl.PermissionImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Permission>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.Permission>();
				}

				permissionPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_PERMISSIONS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_PERMISSIONS_SIZE = new FinderPath(com.liferay.portal.model.impl.PermissionModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_PERMISSIONS,
			"Users_Permissions", "getPermissionsSize",
			new String[] { Long.class.getName() });

	public int getPermissionsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_PERMISSIONS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETPERMISSIONSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_PERMISSIONS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_PERMISSION = new FinderPath(com.liferay.portal.model.impl.PermissionModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_PERMISSIONS,
			"Users_Permissions", "containsPermission",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsPermission(long pk, long permissionPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(permissionPK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_PERMISSION,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsPermission.contains(pk,
							permissionPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_PERMISSION,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsPermissions(long pk) throws SystemException {
		if (getPermissionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addPermission(long pk, long permissionPK)
		throws SystemException {
		try {
			addPermission.add(pk, permissionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void addPermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws SystemException {
		try {
			addPermission.add(pk, permission.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void addPermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			for (long permissionPK : permissionPKs) {
				addPermission.add(pk, permissionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void addPermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Permission permission : permissions) {
				addPermission.add(pk, permission.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void clearPermissions(long pk) throws SystemException {
		try {
			clearPermissions.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void removePermission(long pk, long permissionPK)
		throws SystemException {
		try {
			removePermission.remove(pk, permissionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void removePermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws SystemException {
		try {
			removePermission.remove(pk, permission.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void removePermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			for (long permissionPK : permissionPKs) {
				removePermission.remove(pk, permissionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void removePermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Permission permission : permissions) {
				removePermission.remove(pk, permission.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void setPermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			clearPermissions.clear(pk);

			for (long permissionPK : permissionPKs) {
				addPermission.add(pk, permissionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void setPermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			clearPermissions.clear(pk);

			for (com.liferay.portal.model.Permission permission : permissions) {
				addPermission.add(pk, permission.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public List<com.liferay.portal.model.Role> getRoles(long pk)
		throws SystemException {
		return getRoles(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end) throws SystemException {
		return getRoles(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ROLES = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ROLES, "Users_Roles",
			"getRoles",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portal.model.Role> list = (List<com.liferay.portal.model.Role>)FinderCacheUtil.getResult(FINDER_PATH_GET_ROLES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETROLES);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

					sb.append("Role_.name ASC");
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Role_",
					com.liferay.portal.model.impl.RoleImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Role>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.Role>();
				}

				rolePersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_ROLES, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ROLES_SIZE = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ROLES, "Users_Roles",
			"getRolesSize", new String[] { Long.class.getName() });

	public int getRolesSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ROLES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETROLESSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_ROLES_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ROLE = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ROLES, "Users_Roles",
			"containsRole",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsRole(long pk, long rolePK) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(rolePK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ROLE,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsRole.contains(pk, rolePK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ROLE,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsRoles(long pk) throws SystemException {
		if (getRolesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addRole(long pk, long rolePK) throws SystemException {
		try {
			addRole.add(pk, rolePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void addRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			addRole.add(pk, role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void addRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				addRole.add(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void addRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				addRole.add(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void clearRoles(long pk) throws SystemException {
		try {
			clearRoles.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void removeRole(long pk, long rolePK) throws SystemException {
		try {
			removeRole.remove(pk, rolePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void removeRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			removeRole.remove(pk, role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void removeRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				removeRole.remove(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void removeRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				removeRole.remove(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void setRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			clearRoles.clear(pk);

			for (long rolePK : rolePKs) {
				addRole.add(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void setRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			clearRoles.clear(pk);

			for (com.liferay.portal.model.Role role : roles) {
				addRole.add(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk)
		throws SystemException {
		return getUserGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end) throws SystemException {
		return getUserGroups(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_USERGROUPS = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_USERGROUPS,
			"Users_UserGroups", "getUserGroups",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portal.model.UserGroup> list = (List<com.liferay.portal.model.UserGroup>)FinderCacheUtil.getResult(FINDER_PATH_GET_USERGROUPS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETUSERGROUPS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

					sb.append("UserGroup.name ASC");
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("UserGroup",
					com.liferay.portal.model.impl.UserGroupImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.UserGroup>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.UserGroup>();
				}

				userGroupPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERGROUPS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_USERGROUPS_SIZE = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_USERGROUPS,
			"Users_UserGroups", "getUserGroupsSize",
			new String[] { Long.class.getName() });

	public int getUserGroupsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_USERGROUPS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETUSERGROUPSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERGROUPS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_USERGROUP = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_USERGROUPS,
			"Users_UserGroups", "containsUserGroup",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsUserGroup(long pk, long userGroupPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(userGroupPK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_USERGROUP,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsUserGroup.contains(pk,
							userGroupPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_USERGROUP,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsUserGroups(long pk) throws SystemException {
		if (getUserGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addUserGroup(long pk, long userGroupPK)
		throws SystemException {
		try {
			addUserGroup.add(pk, userGroupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		try {
			addUserGroup.add(pk, userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void addUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			for (long userGroupPK : userGroupPKs) {
				addUserGroup.add(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void addUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				addUserGroup.add(pk, userGroup.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void clearUserGroups(long pk) throws SystemException {
		try {
			clearUserGroups.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void removeUserGroup(long pk, long userGroupPK)
		throws SystemException {
		try {
			removeUserGroup.remove(pk, userGroupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		try {
			removeUserGroup.remove(pk, userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void removeUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			for (long userGroupPK : userGroupPKs) {
				removeUserGroup.remove(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void removeUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				removeUserGroup.remove(pk, userGroup.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void setUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			clearUserGroups.clear(pk);

			for (long userGroupPK : userGroupPKs) {
				addUserGroup.add(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void setUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			clearUserGroups.clear(pk);

			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				addUserGroup.add(pk, userGroup.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.User")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<User>> listenersList = new ArrayList<ModelListener<User>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<User>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsGroup = new ContainsGroup(this);

		addGroup = new AddGroup(this);
		clearGroups = new ClearGroups(this);
		removeGroup = new RemoveGroup(this);

		containsOrganization = new ContainsOrganization(this);

		addOrganization = new AddOrganization(this);
		clearOrganizations = new ClearOrganizations(this);
		removeOrganization = new RemoveOrganization(this);

		containsPermission = new ContainsPermission(this);

		addPermission = new AddPermission(this);
		clearPermissions = new ClearPermissions(this);
		removePermission = new RemovePermission(this);

		containsRole = new ContainsRole(this);

		addRole = new AddRole(this);
		clearRoles = new ClearRoles(this);
		removeRole = new RemoveRole(this);

		containsUserGroup = new ContainsUserGroup(this);

		addUserGroup = new AddUserGroup(this);
		clearUserGroups = new ClearUserGroups(this);
		removeUserGroup = new RemoveUserGroup(this);
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence.impl")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence.impl")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
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
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence.impl")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
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
	@BeanReference(name = "com.liferay.portlet.announcements.service.persistence.AnnouncementsDeliveryPersistence.impl")
	protected com.liferay.portlet.announcements.service.persistence.AnnouncementsDeliveryPersistence announcementsDeliveryPersistence;
	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence.impl")
	protected com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence blogsStatsUserPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence.impl")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBBanPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBBanPersistence mbBanPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence mbMessageFlagPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence shoppingCartPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence.impl")
	protected com.liferay.portlet.social.service.persistence.SocialActivityPersistence socialActivityPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialRequestPersistence.impl")
	protected com.liferay.portlet.social.service.persistence.SocialRequestPersistence socialRequestPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsAssetPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsAssetPersistence tagsAssetPersistence;
	protected ContainsGroup containsGroup;
	protected AddGroup addGroup;
	protected ClearGroups clearGroups;
	protected RemoveGroup removeGroup;
	protected ContainsOrganization containsOrganization;
	protected AddOrganization addOrganization;
	protected ClearOrganizations clearOrganizations;
	protected RemoveOrganization removeOrganization;
	protected ContainsPermission containsPermission;
	protected AddPermission addPermission;
	protected ClearPermissions clearPermissions;
	protected RemovePermission removePermission;
	protected ContainsRole containsRole;
	protected AddRole addRole;
	protected ClearRoles clearRoles;
	protected RemoveRole removeRole;
	protected ContainsUserGroup containsUserGroup;
	protected AddUserGroup addUserGroup;
	protected ClearUserGroups clearUserGroups;
	protected RemoveUserGroup removeUserGroup;

	protected class ContainsGroup {
		protected ContainsGroup(UserPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSGROUP,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long userId, long groupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(groupId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddGroup {
		protected AddGroup(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Groups (userId, groupId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long userId, long groupId) throws SystemException {
			if (!_persistenceImpl.containsGroup.contains(userId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeAddAssociation(groupId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(groupId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterAddAssociation(groupId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearGroups {
		protected ClearGroups(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Groups WHERE userId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

			List<com.liferay.portal.model.Group> groups = null;

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				groups = getGroups(userId);

				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onBeforeRemoveAssociation(group.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onBeforeRemoveAssociation(group.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveGroup {
		protected RemoveGroup(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Groups WHERE userId = ? AND groupId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long userId, long groupId)
			throws SystemException {
			if (_persistenceImpl.containsGroup.contains(userId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeRemoveAssociation(groupId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(groupId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterRemoveAssociation(groupId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ContainsOrganization {
		protected ContainsOrganization(UserPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSORGANIZATION,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long userId, long organizationId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(organizationId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddOrganization {
		protected AddOrganization(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Orgs (userId, organizationId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long userId, long organizationId)
			throws SystemException {
			if (!_persistenceImpl.containsOrganization.contains(userId,
						organizationId)) {
				ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
					organizationPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onBeforeAddAssociation(organizationId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(organizationId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onAfterAddAssociation(organizationId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearOrganizations {
		protected ClearOrganizations(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Orgs WHERE userId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
				organizationPersistence.getListeners();

			List<com.liferay.portal.model.Organization> organizations = null;

			if ((listeners.length > 0) || (organizationListeners.length > 0)) {
				organizations = getOrganizations(userId);

				for (com.liferay.portal.model.Organization organization : organizations) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Organization.class.getName(),
							organization.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
						listener.onBeforeRemoveAssociation(organization.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (organizationListeners.length > 0)) {
				for (com.liferay.portal.model.Organization organization : organizations) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Organization.class.getName(),
							organization.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
						listener.onBeforeRemoveAssociation(organization.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveOrganization {
		protected RemoveOrganization(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Orgs WHERE userId = ? AND organizationId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long userId, long organizationId)
			throws SystemException {
			if (_persistenceImpl.containsOrganization.contains(userId,
						organizationId)) {
				ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
					organizationPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onBeforeRemoveAssociation(organizationId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(organizationId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onAfterRemoveAssociation(organizationId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ContainsPermission {
		protected ContainsPermission(UserPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSPERMISSION,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long userId, long permissionId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(permissionId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddPermission {
		protected AddPermission(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Permissions (userId, permissionId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long userId, long permissionId)
			throws SystemException {
			if (!_persistenceImpl.containsPermission.contains(userId,
						permissionId)) {
				ModelListener<com.liferay.portal.model.Permission>[] permissionListeners =
					permissionPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onBeforeAddAssociation(permissionId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(permissionId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onAfterAddAssociation(permissionId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearPermissions {
		protected ClearPermissions(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Permissions WHERE userId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Permission>[] permissionListeners =
				permissionPersistence.getListeners();

			List<com.liferay.portal.model.Permission> permissions = null;

			if ((listeners.length > 0) || (permissionListeners.length > 0)) {
				permissions = getPermissions(userId);

				for (com.liferay.portal.model.Permission permission : permissions) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Permission.class.getName(),
							permission.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
						listener.onBeforeRemoveAssociation(permission.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (permissionListeners.length > 0)) {
				for (com.liferay.portal.model.Permission permission : permissions) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Permission.class.getName(),
							permission.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
						listener.onBeforeRemoveAssociation(permission.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemovePermission {
		protected RemovePermission(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Permissions WHERE userId = ? AND permissionId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long userId, long permissionId)
			throws SystemException {
			if (_persistenceImpl.containsPermission.contains(userId,
						permissionId)) {
				ModelListener<com.liferay.portal.model.Permission>[] permissionListeners =
					permissionPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onBeforeRemoveAssociation(permissionId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(permissionId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onAfterRemoveAssociation(permissionId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ContainsRole {
		protected ContainsRole(UserPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSROLE,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long userId, long roleId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(roleId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddRole {
		protected AddRole(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Roles (userId, roleId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long userId, long roleId) throws SystemException {
			if (!_persistenceImpl.containsRole.contains(userId, roleId)) {
				ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onBeforeAddAssociation(roleId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(roleId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onAfterAddAssociation(roleId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearRoles {
		protected ClearRoles(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Roles WHERE userId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

			List<com.liferay.portal.model.Role> roles = null;

			if ((listeners.length > 0) || (roleListeners.length > 0)) {
				roles = getRoles(userId);

				for (com.liferay.portal.model.Role role : roles) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Role.class.getName(),
							role.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
						listener.onBeforeRemoveAssociation(role.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (roleListeners.length > 0)) {
				for (com.liferay.portal.model.Role role : roles) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Role.class.getName(),
							role.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
						listener.onBeforeRemoveAssociation(role.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveRole {
		protected RemoveRole(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Roles WHERE userId = ? AND roleId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long userId, long roleId)
			throws SystemException {
			if (_persistenceImpl.containsRole.contains(userId, roleId)) {
				ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onBeforeRemoveAssociation(roleId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(roleId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onAfterRemoveAssociation(roleId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ContainsUserGroup {
		protected ContainsUserGroup(UserPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSERGROUP,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long userId, long userGroupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(userGroupId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddUserGroup {
		protected AddUserGroup(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_UserGroups (userId, userGroupId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long userId, long userGroupId)
			throws SystemException {
			if (!_persistenceImpl.containsUserGroup.contains(userId, userGroupId)) {
				ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
					userGroupPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onBeforeAddAssociation(userGroupId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(userGroupId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onAfterAddAssociation(userGroupId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearUserGroups {
		protected ClearUserGroups(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_UserGroups WHERE userId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
				userGroupPersistence.getListeners();

			List<com.liferay.portal.model.UserGroup> userGroups = null;

			if ((listeners.length > 0) || (userGroupListeners.length > 0)) {
				userGroups = getUserGroups(userId);

				for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.UserGroup.class.getName(),
							userGroup.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
						listener.onBeforeRemoveAssociation(userGroup.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (userGroupListeners.length > 0)) {
				for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.UserGroup.class.getName(),
							userGroup.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
						listener.onBeforeRemoveAssociation(userGroup.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUserGroup {
		protected RemoveUserGroup(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_UserGroups WHERE userId = ? AND userGroupId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long userId, long userGroupId)
			throws SystemException {
			if (_persistenceImpl.containsUserGroup.contains(userId, userGroupId)) {
				ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
					userGroupPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onBeforeRemoveAssociation(userGroupId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(userGroupId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onAfterRemoveAssociation(userGroupId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_GETGROUPS = "SELECT {Group_.*} FROM Group_ INNER JOIN Users_Groups ON (Users_Groups.groupId = Group_.groupId) WHERE (Users_Groups.userId = ?)";
	private static final String _SQL_GETGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE userId = ?";
	private static final String _SQL_CONTAINSGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE userId = ? AND groupId = ?";
	private static final String _SQL_GETORGANIZATIONS = "SELECT {Organization_.*} FROM Organization_ INNER JOIN Users_Orgs ON (Users_Orgs.organizationId = Organization_.organizationId) WHERE (Users_Orgs.userId = ?)";
	private static final String _SQL_GETORGANIZATIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE userId = ?";
	private static final String _SQL_CONTAINSORGANIZATION = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE userId = ? AND organizationId = ?";
	private static final String _SQL_GETPERMISSIONS = "SELECT {Permission_.*} FROM Permission_ INNER JOIN Users_Permissions ON (Users_Permissions.permissionId = Permission_.permissionId) WHERE (Users_Permissions.userId = ?)";
	private static final String _SQL_GETPERMISSIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE userId = ?";
	private static final String _SQL_CONTAINSPERMISSION = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE userId = ? AND permissionId = ?";
	private static final String _SQL_GETROLES = "SELECT {Role_.*} FROM Role_ INNER JOIN Users_Roles ON (Users_Roles.roleId = Role_.roleId) WHERE (Users_Roles.userId = ?)";
	private static final String _SQL_GETROLESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE userId = ?";
	private static final String _SQL_CONTAINSROLE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE userId = ? AND roleId = ?";
	private static final String _SQL_GETUSERGROUPS = "SELECT {UserGroup.*} FROM UserGroup INNER JOIN Users_UserGroups ON (Users_UserGroups.userGroupId = UserGroup.userGroupId) WHERE (Users_UserGroups.userId = ?)";
	private static final String _SQL_GETUSERGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_UserGroups WHERE userId = ?";
	private static final String _SQL_CONTAINSUSERGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_UserGroups WHERE userId = ? AND userGroupId = ?";
	private static Log _log = LogFactoryUtil.getLog(UserPersistenceImpl.class);
}