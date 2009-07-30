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

package com.liferay.portlet.social.service.persistence;

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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchRelationException;
import com.liferay.portlet.social.model.SocialRelation;
import com.liferay.portlet.social.model.impl.SocialRelationImpl;
import com.liferay.portlet.social.model.impl.SocialRelationModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="SocialRelationPersistenceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    SocialRelationPersistence
 * @see    SocialRelationUtil
 */
public class SocialRelationPersistenceImpl extends BasePersistenceImpl
	implements SocialRelationPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SocialRelationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID1 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId1",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID1 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId1",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID1 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId1",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID2 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId2",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID2 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId2",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID2 = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId2",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_TYPE = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByType",
			new String[] { Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_TYPE = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByType",
			new String[] {
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TYPE = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByType",
			new String[] { Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_U1_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU1_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_U1_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU1_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U1_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU1_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_U2_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU2_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_U2_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU2_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U2_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU2_T",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U1_U2_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByU1_U2_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U1_U2_T = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU1_U2_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SocialRelation socialRelation) {
		EntityCacheUtil.putResult(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationImpl.class, socialRelation.getPrimaryKey(),
			socialRelation);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U1_U2_T,
			new Object[] {
				new Long(socialRelation.getUserId1()),
				new Long(socialRelation.getUserId2()),
				new Integer(socialRelation.getType())
			}, socialRelation);
	}

	public void cacheResult(List<SocialRelation> socialRelations) {
		for (SocialRelation socialRelation : socialRelations) {
			if (EntityCacheUtil.getResult(
						SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
						SocialRelationImpl.class,
						socialRelation.getPrimaryKey(), this) == null) {
				cacheResult(socialRelation);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(SocialRelationImpl.class.getName());
		EntityCacheUtil.clearCache(SocialRelationImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public SocialRelation create(long relationId) {
		SocialRelation socialRelation = new SocialRelationImpl();

		socialRelation.setNew(true);
		socialRelation.setPrimaryKey(relationId);

		String uuid = PortalUUIDUtil.generate();

		socialRelation.setUuid(uuid);

		return socialRelation;
	}

	public SocialRelation remove(long relationId)
		throws NoSuchRelationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialRelation socialRelation = (SocialRelation)session.get(SocialRelationImpl.class,
					new Long(relationId));

			if (socialRelation == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No SocialRelation exists with the primary key " +
						relationId);
				}

				throw new NoSuchRelationException(
					"No SocialRelation exists with the primary key " +
					relationId);
			}

			return remove(socialRelation);
		}
		catch (NoSuchRelationException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialRelation remove(SocialRelation socialRelation)
		throws SystemException {
		for (ModelListener<SocialRelation> listener : listeners) {
			listener.onBeforeRemove(socialRelation);
		}

		socialRelation = removeImpl(socialRelation);

		for (ModelListener<SocialRelation> listener : listeners) {
			listener.onAfterRemove(socialRelation);
		}

		return socialRelation;
	}

	protected SocialRelation removeImpl(SocialRelation socialRelation)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (socialRelation.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SocialRelationImpl.class,
						socialRelation.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(socialRelation);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SocialRelationModelImpl socialRelationModelImpl = (SocialRelationModelImpl)socialRelation;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U1_U2_T,
			new Object[] {
				new Long(socialRelationModelImpl.getOriginalUserId1()),
				new Long(socialRelationModelImpl.getOriginalUserId2()),
				new Integer(socialRelationModelImpl.getOriginalType())
			});

		EntityCacheUtil.removeResult(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationImpl.class, socialRelation.getPrimaryKey());

		return socialRelation;
	}

	/**
	 * @deprecated Use {@link #update(SocialRelation, boolean merge)}.
	 */
	public SocialRelation update(SocialRelation socialRelation)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(SocialRelation socialRelation) method. Use update(SocialRelation socialRelation, boolean merge) instead.");
		}

		return update(socialRelation, false);
	}

	public SocialRelation update(SocialRelation socialRelation, boolean merge)
		throws SystemException {
		boolean isNew = socialRelation.isNew();

		for (ModelListener<SocialRelation> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(socialRelation);
			}
			else {
				listener.onBeforeUpdate(socialRelation);
			}
		}

		socialRelation = updateImpl(socialRelation, merge);

		for (ModelListener<SocialRelation> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(socialRelation);
			}
			else {
				listener.onAfterUpdate(socialRelation);
			}
		}

		return socialRelation;
	}

	public SocialRelation updateImpl(
		com.liferay.portlet.social.model.SocialRelation socialRelation,
		boolean merge) throws SystemException {
		boolean isNew = socialRelation.isNew();

		SocialRelationModelImpl socialRelationModelImpl = (SocialRelationModelImpl)socialRelation;

		if (Validator.isNull(socialRelation.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			socialRelation.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialRelation, merge);

			socialRelation.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
			SocialRelationImpl.class, socialRelation.getPrimaryKey(),
			socialRelation);

		if (!isNew &&
				((socialRelation.getUserId1() != socialRelationModelImpl.getOriginalUserId1()) ||
				(socialRelation.getUserId2() != socialRelationModelImpl.getOriginalUserId2()) ||
				(socialRelation.getType() != socialRelationModelImpl.getOriginalType()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U1_U2_T,
				new Object[] {
					new Long(socialRelationModelImpl.getOriginalUserId1()),
					new Long(socialRelationModelImpl.getOriginalUserId2()),
					new Integer(socialRelationModelImpl.getOriginalType())
				});
		}

		if (isNew ||
				((socialRelation.getUserId1() != socialRelationModelImpl.getOriginalUserId1()) ||
				(socialRelation.getUserId2() != socialRelationModelImpl.getOriginalUserId2()) ||
				(socialRelation.getType() != socialRelationModelImpl.getOriginalType()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U1_U2_T,
				new Object[] {
					new Long(socialRelation.getUserId1()),
					new Long(socialRelation.getUserId2()),
					new Integer(socialRelation.getType())
				}, socialRelation);
		}

		return socialRelation;
	}

	public SocialRelation findByPrimaryKey(long relationId)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByPrimaryKey(relationId);

		if (socialRelation == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SocialRelation exists with the primary key " +
					relationId);
			}

			throw new NoSuchRelationException(
				"No SocialRelation exists with the primary key " + relationId);
		}

		return socialRelation;
	}

	public SocialRelation fetchByPrimaryKey(long relationId)
		throws SystemException {
		SocialRelation socialRelation = (SocialRelation)EntityCacheUtil.getResult(SocialRelationModelImpl.ENTITY_CACHE_ENABLED,
				SocialRelationImpl.class, relationId, this);

		if (socialRelation == null) {
			Session session = null;

			try {
				session = openSession();

				socialRelation = (SocialRelation)session.get(SocialRelationImpl.class,
						new Long(relationId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (socialRelation != null) {
					cacheResult(socialRelation);
				}

				closeSession(session);
			}
		}

		return socialRelation;
	}

	public List<SocialRelation> findByUuid(String uuid)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				if (uuid == null) {
					query.append("socialRelation.uuid IS NULL");
				}
				else {
					query.append("socialRelation.uuid = ?");
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
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRelation> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<SocialRelation> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				if (uuid == null) {
					query.append("socialRelation.uuid IS NULL");
				}
				else {
					query.append("socialRelation.uuid = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialRelation.");
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

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRelation findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		int count = countByUuid(uuid);

		List<SocialRelation> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByUuid_PrevAndNext(long relationId,
		String uuid, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

			if (uuid == null) {
				query.append("socialRelation.uuid IS NULL");
			}
			else {
				query.append("socialRelation.uuid = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("socialRelation.");
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

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRelation);

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = (SocialRelation)objArray[0];
			array[1] = (SocialRelation)objArray[1];
			array[2] = (SocialRelation)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRelation> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.companyId = ?");

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
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRelation> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<SocialRelation> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialRelation.");
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

				qPos.add(companyId);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRelation findByCompanyId_First(long companyId,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByCompanyId_Last(long companyId,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		int count = countByCompanyId(companyId);

		List<SocialRelation> list = findByCompanyId(companyId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByCompanyId_PrevAndNext(long relationId,
		long companyId, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

			query.append("socialRelation.companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("socialRelation.");
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

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRelation);

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = (SocialRelation)objArray[0];
			array[1] = (SocialRelation)objArray[1];
			array[2] = (SocialRelation)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRelation> findByUserId1(long userId1)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId1) };

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID1,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.userId1 = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID1,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRelation> findByUserId1(long userId1, int start, int end)
		throws SystemException {
		return findByUserId1(userId1, start, end, null);
	}

	public List<SocialRelation> findByUserId1(long userId1, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId1),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID1,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.userId1 = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialRelation.");
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

				qPos.add(userId1);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID1,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRelation findByUserId1_First(long userId1,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByUserId1(userId1, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId1=" + userId1);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByUserId1_Last(long userId1, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		int count = countByUserId1(userId1);

		List<SocialRelation> list = findByUserId1(userId1, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId1=" + userId1);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByUserId1_PrevAndNext(long relationId,
		long userId1, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		int count = countByUserId1(userId1);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

			query.append("socialRelation.userId1 = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("socialRelation.");
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

			qPos.add(userId1);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRelation);

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = (SocialRelation)objArray[0];
			array[1] = (SocialRelation)objArray[1];
			array[2] = (SocialRelation)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRelation> findByUserId2(long userId2)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId2) };

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID2,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.userId2 = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID2,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRelation> findByUserId2(long userId2, int start, int end)
		throws SystemException {
		return findByUserId2(userId2, start, end, null);
	}

	public List<SocialRelation> findByUserId2(long userId2, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId2),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID2,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.userId2 = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialRelation.");
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

				qPos.add(userId2);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID2,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRelation findByUserId2_First(long userId2,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByUserId2(userId2, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId2=" + userId2);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByUserId2_Last(long userId2, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		int count = countByUserId2(userId2);

		List<SocialRelation> list = findByUserId2(userId2, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId2=" + userId2);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByUserId2_PrevAndNext(long relationId,
		long userId2, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		int count = countByUserId2(userId2);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

			query.append("socialRelation.userId2 = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("socialRelation.");
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

			qPos.add(userId2);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRelation);

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = (SocialRelation)objArray[0];
			array[1] = (SocialRelation)objArray[1];
			array[2] = (SocialRelation)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRelation> findByType(int type) throws SystemException {
		Object[] finderArgs = new Object[] { new Integer(type) };

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TYPE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.type = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TYPE, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRelation> findByType(int type, int start, int end)
		throws SystemException {
		return findByType(type, start, end, null);
	}

	public List<SocialRelation> findByType(int type, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Integer(type),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_TYPE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.type = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialRelation.");
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

				qPos.add(type);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_TYPE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRelation findByType_First(int type, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByType(type, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByType_Last(int type, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		int count = countByType(type);

		List<SocialRelation> list = findByType(type, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByType_PrevAndNext(long relationId, int type,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		int count = countByType(type);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

			query.append("socialRelation.type = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("socialRelation.");
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

			qPos.add(type);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRelation);

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = (SocialRelation)objArray[0];
			array[1] = (SocialRelation)objArray[1];
			array[2] = (SocialRelation)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRelation> findByC_T(long companyId, int type)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(type)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.companyId = ?");

				query.append(" AND ");

				query.append("socialRelation.type = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(type);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_T, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRelation> findByC_T(long companyId, int type, int start,
		int end) throws SystemException {
		return findByC_T(companyId, type, start, end, null);
	}

	public List<SocialRelation> findByC_T(long companyId, int type, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(type),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.companyId = ?");

				query.append(" AND ");

				query.append("socialRelation.type = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialRelation.");
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

				qPos.add(companyId);

				qPos.add(type);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRelation findByC_T_First(long companyId, int type,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByC_T(companyId, type, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByC_T_Last(long companyId, int type,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		int count = countByC_T(companyId, type);

		List<SocialRelation> list = findByC_T(companyId, type, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByC_T_PrevAndNext(long relationId,
		long companyId, int type, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		int count = countByC_T(companyId, type);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

			query.append("socialRelation.companyId = ?");

			query.append(" AND ");

			query.append("socialRelation.type = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("socialRelation.");
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

			qPos.add(companyId);

			qPos.add(type);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRelation);

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = (SocialRelation)objArray[0];
			array[1] = (SocialRelation)objArray[1];
			array[2] = (SocialRelation)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRelation> findByU1_T(long userId1, int type)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId1), new Integer(type) };

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U1_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.userId1 = ?");

				query.append(" AND ");

				query.append("socialRelation.type = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(type);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U1_T, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRelation> findByU1_T(long userId1, int type, int start,
		int end) throws SystemException {
		return findByU1_T(userId1, type, start, end, null);
	}

	public List<SocialRelation> findByU1_T(long userId1, int type, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId1), new Integer(type),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_U1_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.userId1 = ?");

				query.append(" AND ");

				query.append("socialRelation.type = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialRelation.");
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

				qPos.add(userId1);

				qPos.add(type);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_U1_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRelation findByU1_T_First(long userId1, int type,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByU1_T(userId1, type, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId1=" + userId1);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByU1_T_Last(long userId1, int type,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		int count = countByU1_T(userId1, type);

		List<SocialRelation> list = findByU1_T(userId1, type, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId1=" + userId1);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByU1_T_PrevAndNext(long relationId,
		long userId1, int type, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		int count = countByU1_T(userId1, type);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

			query.append("socialRelation.userId1 = ?");

			query.append(" AND ");

			query.append("socialRelation.type = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("socialRelation.");
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

			qPos.add(userId1);

			qPos.add(type);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRelation);

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = (SocialRelation)objArray[0];
			array[1] = (SocialRelation)objArray[1];
			array[2] = (SocialRelation)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRelation> findByU2_T(long userId2, int type)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId2), new Integer(type) };

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U2_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.userId2 = ?");

				query.append(" AND ");

				query.append("socialRelation.type = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				qPos.add(type);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U2_T, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialRelation> findByU2_T(long userId2, int type, int start,
		int end) throws SystemException {
		return findByU2_T(userId2, type, start, end, null);
	}

	public List<SocialRelation> findByU2_T(long userId2, int type, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId2), new Integer(type),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_U2_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.userId2 = ?");

				query.append(" AND ");

				query.append("socialRelation.type = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialRelation.");
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

				qPos.add(userId2);

				qPos.add(type);

				list = (List<SocialRelation>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_U2_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialRelation findByU2_T_First(long userId2, int type,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		List<SocialRelation> list = findByU2_T(userId2, type, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId2=" + userId2);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation findByU2_T_Last(long userId2, int type,
		OrderByComparator obc) throws NoSuchRelationException, SystemException {
		int count = countByU2_T(userId2, type);

		List<SocialRelation> list = findByU2_T(userId2, type, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId2=" + userId2);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRelationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRelation[] findByU2_T_PrevAndNext(long relationId,
		long userId2, int type, OrderByComparator obc)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByPrimaryKey(relationId);

		int count = countByU2_T(userId2, type);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

			query.append("socialRelation.userId2 = ?");

			query.append(" AND ");

			query.append("socialRelation.type = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("socialRelation.");
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

			qPos.add(userId2);

			qPos.add(type);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRelation);

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = (SocialRelation)objArray[0];
			array[1] = (SocialRelation)objArray[1];
			array[2] = (SocialRelation)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialRelation findByU1_U2_T(long userId1, long userId2, int type)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = fetchByU1_U2_T(userId1, userId2, type);

		if (socialRelation == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialRelation exists with the key {");

			msg.append("userId1=" + userId1);

			msg.append(", ");
			msg.append("userId2=" + userId2);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRelationException(msg.toString());
		}

		return socialRelation;
	}

	public SocialRelation fetchByU1_U2_T(long userId1, long userId2, int type)
		throws SystemException {
		return fetchByU1_U2_T(userId1, userId2, type, true);
	}

	public SocialRelation fetchByU1_U2_T(long userId1, long userId2, int type,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId1), new Long(userId2), new Integer(type)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U1_U2_T,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.userId1 = ?");

				query.append(" AND ");

				query.append("socialRelation.userId2 = ?");

				query.append(" AND ");

				query.append("socialRelation.type = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(userId2);

				qPos.add(type);

				List<SocialRelation> list = q.list();

				result = list;

				SocialRelation socialRelation = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U1_U2_T,
						finderArgs, list);
				}
				else {
					socialRelation = list.get(0);

					cacheResult(socialRelation);

					if ((socialRelation.getUserId1() != userId1) ||
							(socialRelation.getUserId2() != userId2) ||
							(socialRelation.getType() != type)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U1_U2_T,
							finderArgs, socialRelation);
					}
				}

				return socialRelation;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U1_U2_T,
						finderArgs, new ArrayList<SocialRelation>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (SocialRelation)result;
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

	public List<SocialRelation> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialRelation> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SocialRelation> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialRelation> list = (List<SocialRelation>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT socialRelation FROM SocialRelation socialRelation ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialRelation.");
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
					list = (List<SocialRelation>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialRelation>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialRelation>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (SocialRelation socialRelation : findByUuid(uuid)) {
			remove(socialRelation);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (SocialRelation socialRelation : findByCompanyId(companyId)) {
			remove(socialRelation);
		}
	}

	public void removeByUserId1(long userId1) throws SystemException {
		for (SocialRelation socialRelation : findByUserId1(userId1)) {
			remove(socialRelation);
		}
	}

	public void removeByUserId2(long userId2) throws SystemException {
		for (SocialRelation socialRelation : findByUserId2(userId2)) {
			remove(socialRelation);
		}
	}

	public void removeByType(int type) throws SystemException {
		for (SocialRelation socialRelation : findByType(type)) {
			remove(socialRelation);
		}
	}

	public void removeByC_T(long companyId, int type) throws SystemException {
		for (SocialRelation socialRelation : findByC_T(companyId, type)) {
			remove(socialRelation);
		}
	}

	public void removeByU1_T(long userId1, int type) throws SystemException {
		for (SocialRelation socialRelation : findByU1_T(userId1, type)) {
			remove(socialRelation);
		}
	}

	public void removeByU2_T(long userId2, int type) throws SystemException {
		for (SocialRelation socialRelation : findByU2_T(userId2, type)) {
			remove(socialRelation);
		}
	}

	public void removeByU1_U2_T(long userId1, long userId2, int type)
		throws NoSuchRelationException, SystemException {
		SocialRelation socialRelation = findByU1_U2_T(userId1, userId2, type);

		remove(socialRelation);
	}

	public void removeAll() throws SystemException {
		for (SocialRelation socialRelation : findAll()) {
			remove(socialRelation);
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

				query.append("SELECT COUNT(socialRelation) ");
				query.append("FROM SocialRelation socialRelation WHERE ");

				if (uuid == null) {
					query.append("socialRelation.uuid IS NULL");
				}
				else {
					query.append("socialRelation.uuid = ?");
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

				query.append("SELECT COUNT(socialRelation) ");
				query.append("FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.companyId = ?");

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

	public int countByUserId1(long userId1) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId1) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID1,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(socialRelation) ");
				query.append("FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.userId1 = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID1,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByUserId2(long userId2) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId2) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID2,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(socialRelation) ");
				query.append("FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.userId2 = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID2,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByType(int type) throws SystemException {
		Object[] finderArgs = new Object[] { new Integer(type) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TYPE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(socialRelation) ");
				query.append("FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.type = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TYPE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_T(long companyId, int type) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(type)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(socialRelation) ");
				query.append("FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.companyId = ?");

				query.append(" AND ");

				query.append("socialRelation.type = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_T, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU1_T(long userId1, int type) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId1), new Integer(type) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U1_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(socialRelation) ");
				query.append("FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.userId1 = ?");

				query.append(" AND ");

				query.append("socialRelation.type = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U1_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU2_T(long userId2, int type) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId2), new Integer(type) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U2_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(socialRelation) ");
				query.append("FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.userId2 = ?");

				query.append(" AND ");

				query.append("socialRelation.type = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId2);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U2_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU1_U2_T(long userId1, long userId2, int type)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId1), new Long(userId2), new Integer(type)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U1_U2_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(socialRelation) ");
				query.append("FROM SocialRelation socialRelation WHERE ");

				query.append("socialRelation.userId1 = ?");

				query.append(" AND ");

				query.append("socialRelation.userId2 = ?");

				query.append(" AND ");

				query.append("socialRelation.type = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId1);

				qPos.add(userId2);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U1_U2_T,
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
						"SELECT COUNT(socialRelation) FROM SocialRelation socialRelation");

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
						"value.object.listener.com.liferay.portlet.social.model.SocialRelation")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialRelation>> listenersList = new ArrayList<ModelListener<SocialRelation>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialRelation>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence.impl")
	protected com.liferay.portlet.social.service.persistence.SocialActivityPersistence socialActivityPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialRelationPersistence.impl")
	protected com.liferay.portlet.social.service.persistence.SocialRelationPersistence socialRelationPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialRequestPersistence.impl")
	protected com.liferay.portlet.social.service.persistence.SocialRequestPersistence socialRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(SocialRelationPersistenceImpl.class);
}