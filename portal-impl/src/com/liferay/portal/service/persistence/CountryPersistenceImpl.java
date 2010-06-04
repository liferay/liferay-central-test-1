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

import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.CountryImpl;
import com.liferay.portal.model.impl.CountryModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="CountryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CountryPersistence
 * @see       CountryUtil
 * @generated
 */
public class CountryPersistenceImpl extends BasePersistenceImpl<Country>
	implements CountryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = CountryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_NAME = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByName", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_NAME = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByName", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_A2 = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByA2", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_A2 = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByA2", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_A3 = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByA3", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_A3 = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByA3", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_ACTIVE = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByActive",
			new String[] {
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ACTIVE = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByActive", new String[] { Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Country country) {
		EntityCacheUtil.putResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryImpl.class, country.getPrimaryKey(), country);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
			new Object[] { country.getName() }, country);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A2,
			new Object[] { country.getA2() }, country);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A3,
			new Object[] { country.getA3() }, country);
	}

	public void cacheResult(List<Country> countries) {
		for (Country country : countries) {
			if (EntityCacheUtil.getResult(
						CountryModelImpl.ENTITY_CACHE_ENABLED,
						CountryImpl.class, country.getPrimaryKey(), this) == null) {
				cacheResult(country);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(CountryImpl.class.getName());
		EntityCacheUtil.clearCache(CountryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(Country country) {
		EntityCacheUtil.removeResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryImpl.class, country.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NAME,
			new Object[] { country.getName() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A2,
			new Object[] { country.getA2() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A3,
			new Object[] { country.getA3() });
	}

	public Country create(long countryId) {
		Country country = new CountryImpl();

		country.setNew(true);
		country.setPrimaryKey(countryId);

		return country;
	}

	public Country remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Country remove(long countryId)
		throws NoSuchCountryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Country country = (Country)session.get(CountryImpl.class,
					new Long(countryId));

			if (country == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + countryId);
				}

				throw new NoSuchCountryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					countryId);
			}

			return remove(country);
		}
		catch (NoSuchCountryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Country remove(Country country) throws SystemException {
		for (ModelListener<Country> listener : listeners) {
			listener.onBeforeRemove(country);
		}

		country = removeImpl(country);

		for (ModelListener<Country> listener : listeners) {
			listener.onAfterRemove(country);
		}

		return country;
	}

	protected Country removeImpl(Country country) throws SystemException {
		country = toUnwrappedModel(country);

		Session session = null;

		try {
			session = openSession();

			if (country.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(CountryImpl.class,
						country.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(country);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		CountryModelImpl countryModelImpl = (CountryModelImpl)country;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NAME,
			new Object[] { countryModelImpl.getOriginalName() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A2,
			new Object[] { countryModelImpl.getOriginalA2() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A3,
			new Object[] { countryModelImpl.getOriginalA3() });

		EntityCacheUtil.removeResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryImpl.class, country.getPrimaryKey());

		return country;
	}

	public Country updateImpl(com.liferay.portal.model.Country country,
		boolean merge) throws SystemException {
		country = toUnwrappedModel(country);

		boolean isNew = country.isNew();

		CountryModelImpl countryModelImpl = (CountryModelImpl)country;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, country, merge);

			country.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryImpl.class, country.getPrimaryKey(), country);

		if (!isNew &&
				(!Validator.equals(country.getName(),
					countryModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NAME,
				new Object[] { countryModelImpl.getOriginalName() });
		}

		if (isNew ||
				(!Validator.equals(country.getName(),
					countryModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
				new Object[] { country.getName() }, country);
		}

		if (!isNew &&
				(!Validator.equals(country.getA2(),
					countryModelImpl.getOriginalA2()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A2,
				new Object[] { countryModelImpl.getOriginalA2() });
		}

		if (isNew ||
				(!Validator.equals(country.getA2(),
					countryModelImpl.getOriginalA2()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A2,
				new Object[] { country.getA2() }, country);
		}

		if (!isNew &&
				(!Validator.equals(country.getA3(),
					countryModelImpl.getOriginalA3()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A3,
				new Object[] { countryModelImpl.getOriginalA3() });
		}

		if (isNew ||
				(!Validator.equals(country.getA3(),
					countryModelImpl.getOriginalA3()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A3,
				new Object[] { country.getA3() }, country);
		}

		return country;
	}

	protected Country toUnwrappedModel(Country country) {
		if (country instanceof CountryImpl) {
			return country;
		}

		CountryImpl countryImpl = new CountryImpl();

		countryImpl.setNew(country.isNew());
		countryImpl.setPrimaryKey(country.getPrimaryKey());

		countryImpl.setCountryId(country.getCountryId());
		countryImpl.setName(country.getName());
		countryImpl.setA2(country.getA2());
		countryImpl.setA3(country.getA3());
		countryImpl.setNumber(country.getNumber());
		countryImpl.setIdd(country.getIdd());
		countryImpl.setActive(country.isActive());

		return countryImpl;
	}

	public Country findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Country findByPrimaryKey(long countryId)
		throws NoSuchCountryException, SystemException {
		Country country = fetchByPrimaryKey(countryId);

		if (country == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + countryId);
			}

			throw new NoSuchCountryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				countryId);
		}

		return country;
	}

	public Country fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Country fetchByPrimaryKey(long countryId) throws SystemException {
		Country country = (Country)EntityCacheUtil.getResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
				CountryImpl.class, countryId, this);

		if (country == null) {
			Session session = null;

			try {
				session = openSession();

				country = (Country)session.get(CountryImpl.class,
						new Long(countryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (country != null) {
					cacheResult(country);
				}

				closeSession(session);
			}
		}

		return country;
	}

	public Country findByName(String name)
		throws NoSuchCountryException, SystemException {
		Country country = fetchByName(name);

		if (country == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCountryException(msg.toString());
		}

		return country;
	}

	public Country fetchByName(String name) throws SystemException {
		return fetchByName(name, true);
	}

	public Country fetchByName(String name, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_NAME,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_COUNTRY_WHERE);

				if (name == null) {
					query.append(_FINDER_COLUMN_NAME_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_NAME_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_NAME_NAME_2);
					}
				}

				query.append(CountryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				List<Country> list = q.list();

				result = list;

				Country country = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
						finderArgs, list);
				}
				else {
					country = list.get(0);

					cacheResult(country);

					if ((country.getName() == null) ||
							!country.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
							finderArgs, country);
					}
				}

				return country;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
						finderArgs, new ArrayList<Country>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Country)result;
			}
		}
	}

	public Country findByA2(String a2)
		throws NoSuchCountryException, SystemException {
		Country country = fetchByA2(a2);

		if (country == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("a2=");
			msg.append(a2);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCountryException(msg.toString());
		}

		return country;
	}

	public Country fetchByA2(String a2) throws SystemException {
		return fetchByA2(a2, true);
	}

	public Country fetchByA2(String a2, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { a2 };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_A2,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_COUNTRY_WHERE);

				if (a2 == null) {
					query.append(_FINDER_COLUMN_A2_A2_1);
				}
				else {
					if (a2.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_A2_A2_3);
					}
					else {
						query.append(_FINDER_COLUMN_A2_A2_2);
					}
				}

				query.append(CountryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (a2 != null) {
					qPos.add(a2);
				}

				List<Country> list = q.list();

				result = list;

				Country country = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A2,
						finderArgs, list);
				}
				else {
					country = list.get(0);

					cacheResult(country);

					if ((country.getA2() == null) ||
							!country.getA2().equals(a2)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A2,
							finderArgs, country);
					}
				}

				return country;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A2,
						finderArgs, new ArrayList<Country>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Country)result;
			}
		}
	}

	public Country findByA3(String a3)
		throws NoSuchCountryException, SystemException {
		Country country = fetchByA3(a3);

		if (country == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("a3=");
			msg.append(a3);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCountryException(msg.toString());
		}

		return country;
	}

	public Country fetchByA3(String a3) throws SystemException {
		return fetchByA3(a3, true);
	}

	public Country fetchByA3(String a3, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { a3 };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_A3,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_COUNTRY_WHERE);

				if (a3 == null) {
					query.append(_FINDER_COLUMN_A3_A3_1);
				}
				else {
					if (a3.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_A3_A3_3);
					}
					else {
						query.append(_FINDER_COLUMN_A3_A3_2);
					}
				}

				query.append(CountryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (a3 != null) {
					qPos.add(a3);
				}

				List<Country> list = q.list();

				result = list;

				Country country = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A3,
						finderArgs, list);
				}
				else {
					country = list.get(0);

					cacheResult(country);

					if ((country.getA3() == null) ||
							!country.getA3().equals(a3)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A3,
							finderArgs, country);
					}
				}

				return country;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A3,
						finderArgs, new ArrayList<Country>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Country)result;
			}
		}
	}

	public List<Country> findByActive(boolean active) throws SystemException {
		return findByActive(active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Country> findByActive(boolean active, int start, int end)
		throws SystemException {
		return findByActive(active, start, end, null);
	}

	public List<Country> findByActive(boolean active, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				Boolean.valueOf(active),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Country> list = (List<Country>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ACTIVE,
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

				query.append(_SQL_SELECT_COUNTRY_WHERE);

				query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(CountryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				list = (List<Country>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Country>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ACTIVE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Country findByActive_First(boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchCountryException, SystemException {
		List<Country> list = findByActive(active, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("active=");
			msg.append(active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCountryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Country findByActive_Last(boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchCountryException, SystemException {
		int count = countByActive(active);

		List<Country> list = findByActive(active, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("active=");
			msg.append(active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCountryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Country[] findByActive_PrevAndNext(long countryId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchCountryException, SystemException {
		Country country = findByPrimaryKey(countryId);

		Session session = null;

		try {
			session = openSession();

			Country[] array = new CountryImpl[3];

			array[0] = getByActive_PrevAndNext(session, country, active,
					orderByComparator, true);

			array[1] = country;

			array[2] = getByActive_PrevAndNext(session, country, active,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Country getByActive_PrevAndNext(Session session, Country country,
		boolean active, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COUNTRY_WHERE);

		query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

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
			query.append(CountryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(country);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Country> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<Country> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Country> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Country> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Country> list = (List<Country>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_COUNTRY);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_COUNTRY.concat(CountryModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Country>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Country>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Country>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByName(String name)
		throws NoSuchCountryException, SystemException {
		Country country = findByName(name);

		remove(country);
	}

	public void removeByA2(String a2)
		throws NoSuchCountryException, SystemException {
		Country country = findByA2(a2);

		remove(country);
	}

	public void removeByA3(String a3)
		throws NoSuchCountryException, SystemException {
		Country country = findByA3(a3);

		remove(country);
	}

	public void removeByActive(boolean active) throws SystemException {
		for (Country country : findByActive(active)) {
			remove(country);
		}
	}

	public void removeAll() throws SystemException {
		for (Country country : findAll()) {
			remove(country);
		}
	}

	public int countByName(String name) throws SystemException {
		Object[] finderArgs = new Object[] { name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_NAME,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_COUNTRY_WHERE);

				if (name == null) {
					query.append(_FINDER_COLUMN_NAME_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_NAME_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_NAME_NAME_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_NAME,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByA2(String a2) throws SystemException {
		Object[] finderArgs = new Object[] { a2 };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_A2,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_COUNTRY_WHERE);

				if (a2 == null) {
					query.append(_FINDER_COLUMN_A2_A2_1);
				}
				else {
					if (a2.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_A2_A2_3);
					}
					else {
						query.append(_FINDER_COLUMN_A2_A2_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (a2 != null) {
					qPos.add(a2);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_A2, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByA3(String a3) throws SystemException {
		Object[] finderArgs = new Object[] { a3 };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_A3,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_COUNTRY_WHERE);

				if (a3 == null) {
					query.append(_FINDER_COLUMN_A3_A3_1);
				}
				else {
					if (a3.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_A3_A3_3);
					}
					else {
						query.append(_FINDER_COLUMN_A3_A3_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (a3 != null) {
					qPos.add(a3);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_A3, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByActive(boolean active) throws SystemException {
		Object[] finderArgs = new Object[] { Boolean.valueOf(active) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACTIVE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_COUNTRY_WHERE);

				query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ACTIVE,
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

				Query q = session.createQuery(_SQL_COUNT_COUNTRY);

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
						"value.object.listener.com.liferay.portal.model.Country")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Country>> listenersList = new ArrayList<ModelListener<Country>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Country>)InstanceFactory.newInstance(
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
	private static final String _SQL_SELECT_COUNTRY = "SELECT country FROM Country country";
	private static final String _SQL_SELECT_COUNTRY_WHERE = "SELECT country FROM Country country WHERE ";
	private static final String _SQL_COUNT_COUNTRY = "SELECT COUNT(country) FROM Country country";
	private static final String _SQL_COUNT_COUNTRY_WHERE = "SELECT COUNT(country) FROM Country country WHERE ";
	private static final String _FINDER_COLUMN_NAME_NAME_1 = "country.name IS NULL";
	private static final String _FINDER_COLUMN_NAME_NAME_2 = "country.name = ?";
	private static final String _FINDER_COLUMN_NAME_NAME_3 = "(country.name IS NULL OR country.name = ?)";
	private static final String _FINDER_COLUMN_A2_A2_1 = "country.a2 IS NULL";
	private static final String _FINDER_COLUMN_A2_A2_2 = "country.a2 = ?";
	private static final String _FINDER_COLUMN_A2_A2_3 = "(country.a2 IS NULL OR country.a2 = ?)";
	private static final String _FINDER_COLUMN_A3_A3_1 = "country.a3 IS NULL";
	private static final String _FINDER_COLUMN_A3_A3_2 = "country.a3 = ?";
	private static final String _FINDER_COLUMN_A3_A3_3 = "(country.a3 IS NULL OR country.a3 = ?)";
	private static final String _FINDER_COLUMN_ACTIVE_ACTIVE_2 = "country.active = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "country.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Country exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Country exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(CountryPersistenceImpl.class);
}