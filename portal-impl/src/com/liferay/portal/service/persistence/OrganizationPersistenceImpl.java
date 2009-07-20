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

import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.impl.OrganizationImpl;
import com.liferay.portal.model.impl.OrganizationModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrganizationPersistenceImpl extends BasePersistenceImpl
	implements OrganizationPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = OrganizationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_LOCATIONS = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByLocations", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_LOCATIONS = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByLocations",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_LOCATIONS = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByLocations", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_P = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_P = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Organization organization) {
		EntityCacheUtil.putResult(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationImpl.class, organization.getPrimaryKey(), organization);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] {
				new Long(organization.getCompanyId()),
				
			organization.getName()
			}, organization);
	}

	public void cacheResult(List<Organization> organizations) {
		for (Organization organization : organizations) {
			if (EntityCacheUtil.getResult(
						OrganizationModelImpl.ENTITY_CACHE_ENABLED,
						OrganizationImpl.class, organization.getPrimaryKey(),
						this) == null) {
				cacheResult(organization);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(OrganizationImpl.class.getName());
		EntityCacheUtil.clearCache(OrganizationImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public Organization create(long organizationId) {
		Organization organization = new OrganizationImpl();

		organization.setNew(true);
		organization.setPrimaryKey(organizationId);

		return organization;
	}

	public Organization remove(long organizationId)
		throws NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Organization organization = (Organization)session.get(OrganizationImpl.class,
					new Long(organizationId));

			if (organization == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Organization exists with the primary key " +
						organizationId);
				}

				throw new NoSuchOrganizationException(
					"No Organization exists with the primary key " +
					organizationId);
			}

			return remove(organization);
		}
		catch (NoSuchOrganizationException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Organization remove(Organization organization)
		throws SystemException {
		for (ModelListener<Organization> listener : listeners) {
			listener.onBeforeRemove(organization);
		}

		organization = removeImpl(organization);

		for (ModelListener<Organization> listener : listeners) {
			listener.onAfterRemove(organization);
		}

		return organization;
	}

	protected Organization removeImpl(Organization organization)
		throws SystemException {
		try {
			clearGroups.clear(organization.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Orgs");
		}

		try {
			clearUsers.clear(organization.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}

		shrinkTree(organization);

		Session session = null;

		try {
			session = openSession();

			if (organization.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(OrganizationImpl.class,
						organization.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(organization);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		OrganizationModelImpl organizationModelImpl = (OrganizationModelImpl)organization;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] {
				new Long(organizationModelImpl.getOriginalCompanyId()),
				
			organizationModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationImpl.class, organization.getPrimaryKey());

		return organization;
	}

	/**
	 * @deprecated Use <code>update(Organization organization, boolean merge)</code>.
	 */
	public Organization update(Organization organization)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(Organization organization) method. Use update(Organization organization, boolean merge) instead.");
		}

		return update(organization, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        organization the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when organization is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public Organization update(Organization organization, boolean merge)
		throws SystemException {
		boolean isNew = organization.isNew();

		for (ModelListener<Organization> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(organization);
			}
			else {
				listener.onBeforeUpdate(organization);
			}
		}

		organization = updateImpl(organization, merge);

		for (ModelListener<Organization> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(organization);
			}
			else {
				listener.onAfterUpdate(organization);
			}
		}

		return organization;
	}

	public Organization updateImpl(
		com.liferay.portal.model.Organization organization, boolean merge)
		throws SystemException {
		boolean isNew = organization.isNew();

		OrganizationModelImpl organizationModelImpl = (OrganizationModelImpl)organization;

		if (isNew) {
			expandTree(organization);
		}
		else {
			if (organization.getParentOrganizationId() != organizationModelImpl.getOriginalParentOrganizationId()) {
				shrinkTree(organization);
				expandTree(organization);
			}
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, organization, merge);

			organization.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationImpl.class, organization.getPrimaryKey(), organization);

		if (!isNew &&
				((organization.getCompanyId() != organizationModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(organization.getName(),
					organizationModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
				new Object[] {
					new Long(organizationModelImpl.getOriginalCompanyId()),
					
				organizationModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((organization.getCompanyId() != organizationModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(organization.getName(),
					organizationModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
				new Object[] {
					new Long(organization.getCompanyId()),
					
				organization.getName()
				}, organization);
		}

		return organization;
	}

	public Organization findByPrimaryKey(long organizationId)
		throws NoSuchOrganizationException, SystemException {
		Organization organization = fetchByPrimaryKey(organizationId);

		if (organization == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Organization exists with the primary key " +
					organizationId);
			}

			throw new NoSuchOrganizationException(
				"No Organization exists with the primary key " +
				organizationId);
		}

		return organization;
	}

	public Organization fetchByPrimaryKey(long organizationId)
		throws SystemException {
		Organization organization = (Organization)EntityCacheUtil.getResult(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
				OrganizationImpl.class, organizationId, this);

		if (organization == null) {
			Session session = null;

			try {
				session = openSession();

				organization = (Organization)session.get(OrganizationImpl.class,
						new Long(organizationId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (organization != null) {
					cacheResult(organization);
				}

				closeSession(session);
			}
		}

		return organization;
	}

	public List<Organization> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<Organization> list = (List<Organization>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT organization FROM Organization organization WHERE ");

				query.append("organization.companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("organization.name ASC");

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
					list = new ArrayList<Organization>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Organization> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<Organization> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Organization> list = (List<Organization>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT organization FROM Organization organization WHERE ");

				query.append("organization.companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("organization.");
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

				else {
					query.append("ORDER BY ");

					query.append("organization.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<Organization>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Organization>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Organization findByCompanyId_First(long companyId,
		OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		List<Organization> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Organization exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrganizationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Organization findByCompanyId_Last(long companyId,
		OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		int count = countByCompanyId(companyId);

		List<Organization> list = findByCompanyId(companyId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Organization exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrganizationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Organization[] findByCompanyId_PrevAndNext(long organizationId,
		long companyId, OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		Organization organization = findByPrimaryKey(organizationId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT organization FROM Organization organization WHERE ");

			query.append("organization.companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("organization.");
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

			else {
				query.append("ORDER BY ");

				query.append("organization.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					organization);

			Organization[] array = new OrganizationImpl[3];

			array[0] = (Organization)objArray[0];
			array[1] = (Organization)objArray[1];
			array[2] = (Organization)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Organization> findByLocations(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<Organization> list = (List<Organization>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_LOCATIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT organization FROM Organization organization WHERE ");

				query.append("organization.companyId = ?");

				query.append(" AND organization.parentOrganizationId != 0 ");

				query.append("ORDER BY ");

				query.append("organization.name ASC");

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
					list = new ArrayList<Organization>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_LOCATIONS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Organization> findByLocations(long companyId, int start, int end)
		throws SystemException {
		return findByLocations(companyId, start, end, null);
	}

	public List<Organization> findByLocations(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Organization> list = (List<Organization>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_LOCATIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT organization FROM Organization organization WHERE ");

				query.append("organization.companyId = ?");

				query.append(" AND organization.parentOrganizationId != 0 ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("organization.");
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

				else {
					query.append("ORDER BY ");

					query.append("organization.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<Organization>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Organization>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_LOCATIONS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Organization findByLocations_First(long companyId,
		OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		List<Organization> list = findByLocations(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Organization exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrganizationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Organization findByLocations_Last(long companyId,
		OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		int count = countByLocations(companyId);

		List<Organization> list = findByLocations(companyId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Organization exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrganizationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Organization[] findByLocations_PrevAndNext(long organizationId,
		long companyId, OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		Organization organization = findByPrimaryKey(organizationId);

		int count = countByLocations(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT organization FROM Organization organization WHERE ");

			query.append("organization.companyId = ?");

			query.append(" AND organization.parentOrganizationId != 0 ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("organization.");
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

			else {
				query.append("ORDER BY ");

				query.append("organization.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					organization);

			Organization[] array = new OrganizationImpl[3];

			array[0] = (Organization)objArray[0];
			array[1] = (Organization)objArray[1];
			array[2] = (Organization)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Organization> findByC_P(long companyId,
		long parentOrganizationId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(parentOrganizationId)
			};

		List<Organization> list = (List<Organization>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT organization FROM Organization organization WHERE ");

				query.append("organization.companyId = ?");

				query.append(" AND ");

				query.append("organization.parentOrganizationId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("organization.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(parentOrganizationId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Organization>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_P, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Organization> findByC_P(long companyId,
		long parentOrganizationId, int start, int end)
		throws SystemException {
		return findByC_P(companyId, parentOrganizationId, start, end, null);
	}

	public List<Organization> findByC_P(long companyId,
		long parentOrganizationId, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(parentOrganizationId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Organization> list = (List<Organization>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT organization FROM Organization organization WHERE ");

				query.append("organization.companyId = ?");

				query.append(" AND ");

				query.append("organization.parentOrganizationId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("organization.");
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

				else {
					query.append("ORDER BY ");

					query.append("organization.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(parentOrganizationId);

				list = (List<Organization>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Organization>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Organization findByC_P_First(long companyId,
		long parentOrganizationId, OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		List<Organization> list = findByC_P(companyId, parentOrganizationId, 0,
				1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Organization exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("parentOrganizationId=" + parentOrganizationId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrganizationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Organization findByC_P_Last(long companyId,
		long parentOrganizationId, OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		int count = countByC_P(companyId, parentOrganizationId);

		List<Organization> list = findByC_P(companyId, parentOrganizationId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Organization exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("parentOrganizationId=" + parentOrganizationId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrganizationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Organization[] findByC_P_PrevAndNext(long organizationId,
		long companyId, long parentOrganizationId, OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		Organization organization = findByPrimaryKey(organizationId);

		int count = countByC_P(companyId, parentOrganizationId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT organization FROM Organization organization WHERE ");

			query.append("organization.companyId = ?");

			query.append(" AND ");

			query.append("organization.parentOrganizationId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("organization.");
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

			else {
				query.append("ORDER BY ");

				query.append("organization.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(parentOrganizationId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					organization);

			Organization[] array = new OrganizationImpl[3];

			array[0] = (Organization)objArray[0];
			array[1] = (Organization)objArray[1];
			array[2] = (Organization)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Organization findByC_N(long companyId, String name)
		throws NoSuchOrganizationException, SystemException {
		Organization organization = fetchByC_N(companyId, name);

		if (organization == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Organization exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchOrganizationException(msg.toString());
		}

		return organization;
	}

	public Organization fetchByC_N(long companyId, String name)
		throws SystemException {
		return fetchByC_N(companyId, name, true);
	}

	public Organization fetchByC_N(long companyId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_N,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT organization FROM Organization organization WHERE ");

				query.append("organization.companyId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("organization.name IS NULL");
				}
				else {
					query.append("organization.name = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("organization.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				List<Organization> list = q.list();

				result = list;

				Organization organization = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, list);
				}
				else {
					organization = list.get(0);

					cacheResult(organization);

					if ((organization.getCompanyId() != companyId) ||
							(organization.getName() == null) ||
							!organization.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
							finderArgs, organization);
					}
				}

				return organization;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, new ArrayList<Organization>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (Organization)result;
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

	public List<Organization> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Organization> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<Organization> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Organization> list = (List<Organization>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT organization FROM Organization organization ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("organization.");
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

				else {
					query.append("ORDER BY ");

					query.append("organization.name ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<Organization>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Organization>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Organization>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (Organization organization : findByCompanyId(companyId)) {
			remove(organization);
		}
	}

	public void removeByLocations(long companyId) throws SystemException {
		for (Organization organization : findByLocations(companyId)) {
			remove(organization);
		}
	}

	public void removeByC_P(long companyId, long parentOrganizationId)
		throws SystemException {
		for (Organization organization : findByC_P(companyId,
				parentOrganizationId)) {
			remove(organization);
		}
	}

	public void removeByC_N(long companyId, String name)
		throws NoSuchOrganizationException, SystemException {
		Organization organization = findByC_N(companyId, name);

		remove(organization);
	}

	public void removeAll() throws SystemException {
		for (Organization organization : findAll()) {
			remove(organization);
		}
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

				query.append("SELECT COUNT(organization) ");
				query.append("FROM Organization organization WHERE ");

				query.append("organization.companyId = ?");

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

	public int countByLocations(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_LOCATIONS,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(organization) ");
				query.append("FROM Organization organization WHERE ");

				query.append("organization.companyId = ?");

				query.append(" AND organization.parentOrganizationId != 0 ");

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_LOCATIONS,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_P(long companyId, long parentOrganizationId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(parentOrganizationId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(organization) ");
				query.append("FROM Organization organization WHERE ");

				query.append("organization.companyId = ?");

				query.append(" AND ");

				query.append("organization.parentOrganizationId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(parentOrganizationId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_N(long companyId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(organization) ");
				query.append("FROM Organization organization WHERE ");

				query.append("organization.companyId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("organization.name IS NULL");
				}
				else {
					query.append("organization.name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, finderArgs,
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
						"SELECT COUNT(organization) FROM Organization organization");

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
			OrganizationModelImpl.FINDER_CACHE_ENABLED_GROUPS_ORGS,
			"Groups_Orgs", "getGroups",
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
			OrganizationModelImpl.FINDER_CACHE_ENABLED_GROUPS_ORGS,
			"Groups_Orgs", "getGroupsSize",
			new String[] { Long.class.getName() });

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
			OrganizationModelImpl.FINDER_CACHE_ENABLED_GROUPS_ORGS,
			"Groups_Orgs", "containsGroup",
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
			FinderCacheUtil.clearCache("Groups_Orgs");
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
			FinderCacheUtil.clearCache("Groups_Orgs");
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
			FinderCacheUtil.clearCache("Groups_Orgs");
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
			FinderCacheUtil.clearCache("Groups_Orgs");
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
			FinderCacheUtil.clearCache("Groups_Orgs");
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
			FinderCacheUtil.clearCache("Groups_Orgs");
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
			FinderCacheUtil.clearCache("Groups_Orgs");
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
			FinderCacheUtil.clearCache("Groups_Orgs");
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
			FinderCacheUtil.clearCache("Groups_Orgs");
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
			FinderCacheUtil.clearCache("Groups_Orgs");
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
			FinderCacheUtil.clearCache("Groups_Orgs");
		}
	}

	public List<com.liferay.portal.model.User> getUsers(long pk)
		throws SystemException {
		return getUsers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end) throws SystemException {
		return getUsers(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_USERS = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED_USERS_ORGS,
			"Users_Orgs", "getUsers",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portal.model.User> list = (List<com.liferay.portal.model.User>)FinderCacheUtil.getResult(FINDER_PATH_GET_USERS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETUSERS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("User_",
					com.liferay.portal.model.impl.UserImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.User>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.User>();
				}

				userPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERS, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_USERS_SIZE = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED_USERS_ORGS,
			"Users_Orgs", "getUsersSize", new String[] { Long.class.getName() });

	public int getUsersSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_USERS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETUSERSSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_USER = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED_USERS_ORGS,
			"Users_Orgs", "containsUser",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsUser(long pk, long userPK) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(userPK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_USER,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsUser.contains(pk, userPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_USER,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsUsers(long pk) throws SystemException {
		if (getUsersSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addUser(long pk, long userPK) throws SystemException {
		try {
			addUser.add(pk, userPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void addUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		try {
			addUser.add(pk, user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void addUsers(long pk, long[] userPKs) throws SystemException {
		try {
			for (long userPK : userPKs) {
				addUser.add(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void addUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			for (com.liferay.portal.model.User user : users) {
				addUser.add(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void clearUsers(long pk) throws SystemException {
		try {
			clearUsers.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void removeUser(long pk, long userPK) throws SystemException {
		try {
			removeUser.remove(pk, userPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void removeUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		try {
			removeUser.remove(pk, user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void removeUsers(long pk, long[] userPKs) throws SystemException {
		try {
			for (long userPK : userPKs) {
				removeUser.remove(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void removeUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			for (com.liferay.portal.model.User user : users) {
				removeUser.remove(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void setUsers(long pk, long[] userPKs) throws SystemException {
		try {
			clearUsers.clear(pk);

			for (long userPK : userPKs) {
				addUser.add(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void setUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			clearUsers.clear(pk);

			for (com.liferay.portal.model.User user : users) {
				addUser.add(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void rebuildTree(long companyId, boolean force)
		throws SystemException {
		if (force || (countOrphanTreeNodes(companyId) > 0)) {
			rebuildTree(companyId, 0, 1);

			CacheRegistry.clear(OrganizationImpl.class.getName());
			EntityCacheUtil.clearCache(OrganizationImpl.class.getName());
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
		}
	}

	protected long countOrphanTreeNodes(long companyId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(
					"SELECT COUNT(*) AS COUNT_VALUE FROM Organization_ WHERE companyId = ? AND (leftOrganizationId = 0 OR leftOrganizationId IS NULL OR rightOrganizationId = 0 OR rightOrganizationId IS NULL)");

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return (Long)q.uniqueResult();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void expandTree(Organization organization)
		throws SystemException {
		long companyId = organization.getCompanyId();

		long lastRightOrganizationId = getLastRightOrganizationId(companyId,
				organization.getParentOrganizationId());

		long leftOrganizationId = 2;
		long rightOrganizationId = 3;

		if (lastRightOrganizationId > 0) {
			leftOrganizationId = lastRightOrganizationId + 1;
			rightOrganizationId = lastRightOrganizationId + 2;

			expandTreeLeftOrganizationId.expand(companyId,
				lastRightOrganizationId);
			expandTreeRightOrganizationId.expand(companyId,
				lastRightOrganizationId);

			CacheRegistry.clear(OrganizationImpl.class.getName());
			EntityCacheUtil.clearCache(OrganizationImpl.class.getName());
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
		}

		organization.setLeftOrganizationId(leftOrganizationId);
		organization.setRightOrganizationId(rightOrganizationId);
	}

	protected long getLastRightOrganizationId(long companyId,
		long parentOrganizationId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(
					"SELECT rightOrganizationId FROM Organization_ WHERE (companyId = ?) AND (parentOrganizationId = ?) ORDER BY rightOrganizationId DESC");

			q.addScalar("rightOrganizationId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(parentOrganizationId);

			List<Long> list = (List<Long>)QueryUtil.list(q, getDialect(), 0, 1);

			if (list.isEmpty()) {
				if (parentOrganizationId > 0) {
					Organization parentOrganization = findByPrimaryKey(parentOrganizationId);

					return parentOrganization.getLeftOrganizationId();
				}

				return 0;
			}
			else {
				return list.get(0);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected long rebuildTree(long companyId, long parentOrganizationId,
		long leftOrganizationId) throws SystemException {
		List<Long> organizationIds = null;

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(
					"SELECT organizationId FROM Organization_ WHERE companyId = ? AND parentOrganizationId = ? ORDER BY organizationId ASC");

			q.addScalar("organizationId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(parentOrganizationId);

			organizationIds = q.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		long rightOrganizationId = leftOrganizationId + 1;

		for (long organizationId : organizationIds) {
			rightOrganizationId = rebuildTree(companyId, organizationId,
					rightOrganizationId);
		}

		if (parentOrganizationId > 0) {
			updateTree.update(parentOrganizationId, leftOrganizationId,
				rightOrganizationId);
		}

		return rightOrganizationId + 1;
	}

	protected void shrinkTree(Organization organization) {
		long companyId = organization.getCompanyId();

		long leftOrganizationId = organization.getLeftOrganizationId();
		long rightOrganizationId = organization.getRightOrganizationId();

		long delta = (rightOrganizationId - leftOrganizationId) + 1;

		shrinkTreeLeftOrganizationId.shrink(companyId, rightOrganizationId,
			delta);
		shrinkTreeRightOrganizationId.shrink(companyId, rightOrganizationId,
			delta);

		CacheRegistry.clear(OrganizationImpl.class.getName());
		EntityCacheUtil.clearCache(OrganizationImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Organization")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Organization>> listenersList = new ArrayList<ModelListener<Organization>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Organization>)Class.forName(
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

		containsUser = new ContainsUser(this);

		addUser = new AddUser(this);
		clearUsers = new ClearUsers(this);
		removeUser = new RemoveUser(this);

		expandTreeLeftOrganizationId = new ExpandTreeLeftOrganizationId();
		expandTreeRightOrganizationId = new ExpandTreeRightOrganizationId();
		shrinkTreeLeftOrganizationId = new ShrinkTreeLeftOrganizationId();
		shrinkTreeRightOrganizationId = new ShrinkTreeRightOrganizationId();
		updateTree = new UpdateTree();
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
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetEntryPersistence assetEntryPersistence;
	protected ContainsGroup containsGroup;
	protected AddGroup addGroup;
	protected ClearGroups clearGroups;
	protected RemoveGroup removeGroup;
	protected ContainsUser containsUser;
	protected AddUser addUser;
	protected ClearUsers clearUsers;
	protected RemoveUser removeUser;

	protected class ContainsGroup {
		protected ContainsGroup(OrganizationPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSGROUP,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long organizationId, long groupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(organizationId), new Long(groupId)
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
		protected AddGroup(OrganizationPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Groups_Orgs (organizationId, groupId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long organizationId, long groupId)
			throws SystemException {
			if (!_persistenceImpl.containsGroup.contains(organizationId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<Organization> listener : listeners) {
					listener.onBeforeAddAssociation(organizationId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeAddAssociation(groupId,
						Organization.class.getName(), organizationId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(organizationId), new Long(groupId)
					});

				for (ModelListener<Organization> listener : listeners) {
					listener.onAfterAddAssociation(organizationId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterAddAssociation(groupId,
						Organization.class.getName(), organizationId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private OrganizationPersistenceImpl _persistenceImpl;
	}

	protected class ClearGroups {
		protected ClearGroups(OrganizationPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Orgs WHERE organizationId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long organizationId) throws SystemException {
			ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

			List<com.liferay.portal.model.Group> groups = null;

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				groups = getGroups(organizationId);

				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<Organization> listener : listeners) {
						listener.onBeforeRemoveAssociation(organizationId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onBeforeRemoveAssociation(group.getPrimaryKey(),
							Organization.class.getName(), organizationId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(organizationId) });

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<Organization> listener : listeners) {
						listener.onAfterRemoveAssociation(organizationId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onAfterRemoveAssociation(group.getPrimaryKey(),
							Organization.class.getName(), organizationId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveGroup {
		protected RemoveGroup(OrganizationPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Orgs WHERE organizationId = ? AND groupId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long organizationId, long groupId)
			throws SystemException {
			if (_persistenceImpl.containsGroup.contains(organizationId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<Organization> listener : listeners) {
					listener.onBeforeRemoveAssociation(organizationId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeRemoveAssociation(groupId,
						Organization.class.getName(), organizationId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(organizationId), new Long(groupId)
					});

				for (ModelListener<Organization> listener : listeners) {
					listener.onAfterRemoveAssociation(organizationId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterRemoveAssociation(groupId,
						Organization.class.getName(), organizationId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private OrganizationPersistenceImpl _persistenceImpl;
	}

	protected class ContainsUser {
		protected ContainsUser(OrganizationPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSER,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long organizationId, long userId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(organizationId), new Long(userId)
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

	protected class AddUser {
		protected AddUser(OrganizationPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Orgs (organizationId, userId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long organizationId, long userId)
			throws SystemException {
			if (!_persistenceImpl.containsUser.contains(organizationId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Organization> listener : listeners) {
					listener.onBeforeAddAssociation(organizationId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeAddAssociation(userId,
						Organization.class.getName(), organizationId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(organizationId), new Long(userId)
					});

				for (ModelListener<Organization> listener : listeners) {
					listener.onAfterAddAssociation(organizationId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterAddAssociation(userId,
						Organization.class.getName(), organizationId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private OrganizationPersistenceImpl _persistenceImpl;
	}

	protected class ClearUsers {
		protected ClearUsers(OrganizationPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Orgs WHERE organizationId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long organizationId) throws SystemException {
			ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

			List<com.liferay.portal.model.User> users = null;

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				users = getUsers(organizationId);

				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Organization> listener : listeners) {
						listener.onBeforeRemoveAssociation(organizationId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onBeforeRemoveAssociation(user.getPrimaryKey(),
							Organization.class.getName(), organizationId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(organizationId) });

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Organization> listener : listeners) {
						listener.onAfterRemoveAssociation(organizationId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onAfterRemoveAssociation(user.getPrimaryKey(),
							Organization.class.getName(), organizationId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUser {
		protected RemoveUser(OrganizationPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Orgs WHERE organizationId = ? AND userId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long organizationId, long userId)
			throws SystemException {
			if (_persistenceImpl.containsUser.contains(organizationId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Organization> listener : listeners) {
					listener.onBeforeRemoveAssociation(organizationId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeRemoveAssociation(userId,
						Organization.class.getName(), organizationId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(organizationId), new Long(userId)
					});

				for (ModelListener<Organization> listener : listeners) {
					listener.onAfterRemoveAssociation(organizationId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterRemoveAssociation(userId,
						Organization.class.getName(), organizationId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private OrganizationPersistenceImpl _persistenceImpl;
	}

	protected ExpandTreeLeftOrganizationId expandTreeLeftOrganizationId;
	protected ExpandTreeRightOrganizationId expandTreeRightOrganizationId;
	protected ShrinkTreeLeftOrganizationId shrinkTreeLeftOrganizationId;
	protected ShrinkTreeRightOrganizationId shrinkTreeRightOrganizationId;
	protected UpdateTree updateTree;

	protected class ExpandTreeLeftOrganizationId {
		protected ExpandTreeLeftOrganizationId() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE Organization_ SET leftOrganizationId = (leftOrganizationId + 2) WHERE (companyId = ?) AND (leftOrganizationId > ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
		}

		protected void expand(long companyId, long leftOrganizationId) {
			_sqlUpdate.update(new Object[] { companyId, leftOrganizationId });
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ExpandTreeRightOrganizationId {
		protected ExpandTreeRightOrganizationId() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE Organization_ SET rightOrganizationId = (rightOrganizationId + 2) WHERE (companyId = ?) AND (rightOrganizationId > ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
		}

		protected void expand(long companyId, long rightOrganizationId) {
			_sqlUpdate.update(new Object[] { companyId, rightOrganizationId });
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ShrinkTreeLeftOrganizationId {
		protected ShrinkTreeLeftOrganizationId() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE Organization_ SET leftOrganizationId = (leftOrganizationId - ?) WHERE (companyId = ?) AND (leftOrganizationId > ?)",
					new int[] { Types.BIGINT, Types.BIGINT, Types.BIGINT });
		}

		protected void shrink(long companyId, long leftOrganizationId,
			long delta) {
			_sqlUpdate.update(new Object[] { delta, companyId, leftOrganizationId });
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ShrinkTreeRightOrganizationId {
		protected ShrinkTreeRightOrganizationId() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE Organization_ SET rightOrganizationId = (rightOrganizationId - ?) WHERE (companyId = ?) AND (rightOrganizationId > ?)",
					new int[] { Types.BIGINT, Types.BIGINT, Types.BIGINT });
		}

		protected void shrink(long companyId, long rightOrganizationId,
			long delta) {
			_sqlUpdate.update(new Object[] { delta, companyId, rightOrganizationId });
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class UpdateTree {
		protected UpdateTree() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE Organization_ SET leftOrganizationId = ?, rightOrganizationId = ? WHERE organizationId = ?",
					new int[] { Types.BIGINT, Types.BIGINT, Types.BIGINT });
		}

		protected void update(long organizationId, long leftOrganizationId,
			long rightOrganizationId) {
			_sqlUpdate.update(new Object[] {
					leftOrganizationId, rightOrganizationId, organizationId
				});
		}

		private SqlUpdate _sqlUpdate;
	}

	private static final String _SQL_GETGROUPS = "SELECT {Group_.*} FROM Group_ INNER JOIN Groups_Orgs ON (Groups_Orgs.groupId = Group_.groupId) WHERE (Groups_Orgs.organizationId = ?)";
	private static final String _SQL_GETGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Orgs WHERE organizationId = ?";
	private static final String _SQL_CONTAINSGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Orgs WHERE organizationId = ? AND groupId = ?";
	private static final String _SQL_GETUSERS = "SELECT {User_.*} FROM User_ INNER JOIN Users_Orgs ON (Users_Orgs.userId = User_.userId) WHERE (Users_Orgs.organizationId = ?)";
	private static final String _SQL_GETUSERSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE organizationId = ?";
	private static final String _SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE organizationId = ? AND userId = ?";
	private static Log _log = LogFactoryUtil.getLog(OrganizationPersistenceImpl.class);
}