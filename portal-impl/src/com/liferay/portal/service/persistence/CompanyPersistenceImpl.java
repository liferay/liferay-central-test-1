/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.model.impl.CompanyModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="CompanyPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CompanyPersistenceImpl extends BasePersistenceImpl
	implements CompanyPersistence {
	public Company create(long companyId) {
		Company company = new CompanyImpl();

		company.setNew(true);
		company.setPrimaryKey(companyId);

		return company;
	}

	public Company remove(long companyId)
		throws NoSuchCompanyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Company company = (Company)session.get(CompanyImpl.class,
					new Long(companyId));

			if (company == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Company exists with the primary key " +
						companyId);
				}

				throw new NoSuchCompanyException(
					"No Company exists with the primary key " + companyId);
			}

			return remove(company);
		}
		catch (NoSuchCompanyException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Company remove(Company company) throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(company);
			}
		}

		company = removeImpl(company);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(company);
			}
		}

		return company;
	}

	protected Company removeImpl(Company company) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(CompanyImpl.class,
						company.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(company);

			session.flush();

			return company;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(Company.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(Company company, boolean merge)</code>.
	 */
	public Company update(Company company) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(Company company) method. Use update(Company company, boolean merge) instead.");
		}

		return update(company, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        company the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when company is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public Company update(Company company, boolean merge)
		throws SystemException {
		boolean isNew = company.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(company);
				}
				else {
					listener.onBeforeUpdate(company);
				}
			}
		}

		company = updateImpl(company, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(company);
				}
				else {
					listener.onAfterUpdate(company);
				}
			}
		}

		return company;
	}

	public Company updateImpl(com.liferay.portal.model.Company company,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, company, merge);

			company.setNew(false);

			return company;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(Company.class.getName());
		}
	}

	public Company findByPrimaryKey(long companyId)
		throws NoSuchCompanyException, SystemException {
		Company company = fetchByPrimaryKey(companyId);

		if (company == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Company exists with the primary key " +
					companyId);
			}

			throw new NoSuchCompanyException(
				"No Company exists with the primary key " + companyId);
		}

		return company;
	}

	public Company fetchByPrimaryKey(long companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Company)session.get(CompanyImpl.class, new Long(companyId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Company findByWebId(String webId)
		throws NoSuchCompanyException, SystemException {
		Company company = fetchByWebId(webId);

		if (company == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Company exists with the key {");

			msg.append("webId=" + webId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCompanyException(msg.toString());
		}

		return company;
	}

	public Company fetchByWebId(String webId) throws SystemException {
		boolean finderClassNameCacheEnabled = CompanyModelImpl.CACHE_ENABLED;
		String finderClassName = Company.class.getName();
		String finderMethodName = "fetchByWebId";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { webId };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Company WHERE ");

				if (webId == null) {
					query.append("webId IS NULL");
				}
				else {
					query.append("webId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (webId != null) {
					qPos.add(webId);
				}

				List<Company> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
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
		else {
			List<Company> list = (List<Company>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public Company findByVirtualHost(String virtualHost)
		throws NoSuchCompanyException, SystemException {
		Company company = fetchByVirtualHost(virtualHost);

		if (company == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Company exists with the key {");

			msg.append("virtualHost=" + virtualHost);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCompanyException(msg.toString());
		}

		return company;
	}

	public Company fetchByVirtualHost(String virtualHost)
		throws SystemException {
		boolean finderClassNameCacheEnabled = CompanyModelImpl.CACHE_ENABLED;
		String finderClassName = Company.class.getName();
		String finderMethodName = "fetchByVirtualHost";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { virtualHost };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Company WHERE ");

				if (virtualHost == null) {
					query.append("virtualHost IS NULL");
				}
				else {
					query.append("virtualHost = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (virtualHost != null) {
					qPos.add(virtualHost);
				}

				List<Company> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
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
		else {
			List<Company> list = (List<Company>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public Company findByMx(String mx)
		throws NoSuchCompanyException, SystemException {
		Company company = fetchByMx(mx);

		if (company == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Company exists with the key {");

			msg.append("mx=" + mx);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCompanyException(msg.toString());
		}

		return company;
	}

	public Company fetchByMx(String mx) throws SystemException {
		boolean finderClassNameCacheEnabled = CompanyModelImpl.CACHE_ENABLED;
		String finderClassName = Company.class.getName();
		String finderMethodName = "fetchByMx";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { mx };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Company WHERE ");

				if (mx == null) {
					query.append("mx IS NULL");
				}
				else {
					query.append("mx = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (mx != null) {
					qPos.add(mx);
				}

				List<Company> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
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
		else {
			List<Company> list = (List<Company>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public Company findByLogoId(long logoId)
		throws NoSuchCompanyException, SystemException {
		Company company = fetchByLogoId(logoId);

		if (company == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Company exists with the key {");

			msg.append("logoId=" + logoId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCompanyException(msg.toString());
		}

		return company;
	}

	public Company fetchByLogoId(long logoId) throws SystemException {
		boolean finderClassNameCacheEnabled = CompanyModelImpl.CACHE_ENABLED;
		String finderClassName = Company.class.getName();
		String finderMethodName = "fetchByLogoId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(logoId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Company WHERE ");

				query.append("logoId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(logoId);

				List<Company> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
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
		else {
			List<Company> list = (List<Company>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
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

	public List<Company> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Company> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Company> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = CompanyModelImpl.CACHE_ENABLED;
		String finderClassName = Company.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Company ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<Company> list = null;

				if (obc == null) {
					list = (List<Company>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Company>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<Company>)result;
		}
	}

	public void removeByWebId(String webId)
		throws NoSuchCompanyException, SystemException {
		Company company = findByWebId(webId);

		remove(company);
	}

	public void removeByVirtualHost(String virtualHost)
		throws NoSuchCompanyException, SystemException {
		Company company = findByVirtualHost(virtualHost);

		remove(company);
	}

	public void removeByMx(String mx)
		throws NoSuchCompanyException, SystemException {
		Company company = findByMx(mx);

		remove(company);
	}

	public void removeByLogoId(long logoId)
		throws NoSuchCompanyException, SystemException {
		Company company = findByLogoId(logoId);

		remove(company);
	}

	public void removeAll() throws SystemException {
		for (Company company : findAll()) {
			remove(company);
		}
	}

	public int countByWebId(String webId) throws SystemException {
		boolean finderClassNameCacheEnabled = CompanyModelImpl.CACHE_ENABLED;
		String finderClassName = Company.class.getName();
		String finderMethodName = "countByWebId";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { webId };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.Company WHERE ");

				if (webId == null) {
					query.append("webId IS NULL");
				}
				else {
					query.append("webId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (webId != null) {
					qPos.add(webId);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByVirtualHost(String virtualHost) throws SystemException {
		boolean finderClassNameCacheEnabled = CompanyModelImpl.CACHE_ENABLED;
		String finderClassName = Company.class.getName();
		String finderMethodName = "countByVirtualHost";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { virtualHost };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.Company WHERE ");

				if (virtualHost == null) {
					query.append("virtualHost IS NULL");
				}
				else {
					query.append("virtualHost = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (virtualHost != null) {
					qPos.add(virtualHost);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByMx(String mx) throws SystemException {
		boolean finderClassNameCacheEnabled = CompanyModelImpl.CACHE_ENABLED;
		String finderClassName = Company.class.getName();
		String finderMethodName = "countByMx";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { mx };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.Company WHERE ");

				if (mx == null) {
					query.append("mx IS NULL");
				}
				else {
					query.append("mx = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (mx != null) {
					qPos.add(mx);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByLogoId(long logoId) throws SystemException {
		boolean finderClassNameCacheEnabled = CompanyModelImpl.CACHE_ENABLED;
		String finderClassName = Company.class.getName();
		String finderMethodName = "countByLogoId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(logoId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.Company WHERE ");

				query.append("logoId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(logoId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = CompanyModelImpl.CACHE_ENABLED;
		String finderClassName = Company.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portal.model.Company");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public void registerListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.add(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	public void unregisterListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.remove(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Company")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener> listeners = new ArrayList<ModelListener>();

				for (String listenerClassName : listenerClassNames) {
					listeners.add((ModelListener)Class.forName(
							listenerClassName).newInstance());
				}

				_listeners = listeners.toArray(new ModelListener[listeners.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	private static Log _log = LogFactory.getLog(CompanyPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}