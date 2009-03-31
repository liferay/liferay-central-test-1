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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
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
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="SCProductScreenshotPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCProductScreenshotPersistenceImpl extends BasePersistenceImpl
	implements SCProductScreenshotPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SCProductScreenshotImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_PRODUCTENTRYID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByProductEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_PRODUCTENTRYID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByProductEntryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_PRODUCTENTRYID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByProductEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_THUMBNAILID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByThumbnailId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_THUMBNAILID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByThumbnailId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_FULLIMAGEID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByFullImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_FULLIMAGEID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByFullImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_P_P = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByP_P",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_P_P = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByP_P",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SCProductScreenshot scProductScreenshot) {
		EntityCacheUtil.putResult(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotImpl.class, scProductScreenshot.getPrimaryKey(),
			scProductScreenshot);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
			new Object[] { new Long(scProductScreenshot.getThumbnailId()) },
			scProductScreenshot);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
			new Object[] { new Long(scProductScreenshot.getFullImageId()) },
			scProductScreenshot);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_P,
			new Object[] {
				new Long(scProductScreenshot.getProductEntryId()),
				new Integer(scProductScreenshot.getPriority())
			}, scProductScreenshot);
	}

	public void cacheResult(List<SCProductScreenshot> scProductScreenshots) {
		for (SCProductScreenshot scProductScreenshot : scProductScreenshots) {
			if (EntityCacheUtil.getResult(
						SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
						SCProductScreenshotImpl.class,
						scProductScreenshot.getPrimaryKey(), this) == null) {
				cacheResult(scProductScreenshot);
			}
		}
	}

	public SCProductScreenshot create(long productScreenshotId) {
		SCProductScreenshot scProductScreenshot = new SCProductScreenshotImpl();

		scProductScreenshot.setNew(true);
		scProductScreenshot.setPrimaryKey(productScreenshotId);

		return scProductScreenshot;
	}

	public SCProductScreenshot remove(long productScreenshotId)
		throws NoSuchProductScreenshotException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCProductScreenshot scProductScreenshot = (SCProductScreenshot)session.get(SCProductScreenshotImpl.class,
					new Long(productScreenshotId));

			if (scProductScreenshot == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No SCProductScreenshot exists with the primary key " +
						productScreenshotId);
				}

				throw new NoSuchProductScreenshotException(
					"No SCProductScreenshot exists with the primary key " +
					productScreenshotId);
			}

			return remove(scProductScreenshot);
		}
		catch (NoSuchProductScreenshotException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCProductScreenshot remove(SCProductScreenshot scProductScreenshot)
		throws SystemException {
		for (ModelListener<SCProductScreenshot> listener : listeners) {
			listener.onBeforeRemove(scProductScreenshot);
		}

		scProductScreenshot = removeImpl(scProductScreenshot);

		for (ModelListener<SCProductScreenshot> listener : listeners) {
			listener.onAfterRemove(scProductScreenshot);
		}

		return scProductScreenshot;
	}

	protected SCProductScreenshot removeImpl(
		SCProductScreenshot scProductScreenshot) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (scProductScreenshot.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SCProductScreenshotImpl.class,
						scProductScreenshot.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(scProductScreenshot);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SCProductScreenshotModelImpl scProductScreenshotModelImpl = (SCProductScreenshotModelImpl)scProductScreenshot;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
			new Object[] {
				new Long(scProductScreenshotModelImpl.getOriginalThumbnailId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
			new Object[] {
				new Long(scProductScreenshotModelImpl.getOriginalFullImageId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_P,
			new Object[] {
				new Long(scProductScreenshotModelImpl.getOriginalProductEntryId()),
				new Integer(scProductScreenshotModelImpl.getOriginalPriority())
			});

		EntityCacheUtil.removeResult(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotImpl.class, scProductScreenshot.getPrimaryKey());

		return scProductScreenshot;
	}

	/**
	 * @deprecated Use <code>update(SCProductScreenshot scProductScreenshot, boolean merge)</code>.
	 */
	public SCProductScreenshot update(SCProductScreenshot scProductScreenshot)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(SCProductScreenshot scProductScreenshot) method. Use update(SCProductScreenshot scProductScreenshot, boolean merge) instead.");
		}

		return update(scProductScreenshot, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        scProductScreenshot the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when scProductScreenshot is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public SCProductScreenshot update(SCProductScreenshot scProductScreenshot,
		boolean merge) throws SystemException {
		boolean isNew = scProductScreenshot.isNew();

		for (ModelListener<SCProductScreenshot> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(scProductScreenshot);
			}
			else {
				listener.onBeforeUpdate(scProductScreenshot);
			}
		}

		scProductScreenshot = updateImpl(scProductScreenshot, merge);

		for (ModelListener<SCProductScreenshot> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(scProductScreenshot);
			}
			else {
				listener.onAfterUpdate(scProductScreenshot);
			}
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot,
		boolean merge) throws SystemException {
		boolean isNew = scProductScreenshot.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, scProductScreenshot, merge);

			scProductScreenshot.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotImpl.class, scProductScreenshot.getPrimaryKey(),
			scProductScreenshot);

		SCProductScreenshotModelImpl scProductScreenshotModelImpl = (SCProductScreenshotModelImpl)scProductScreenshot;

		if (!isNew &&
				(scProductScreenshot.getThumbnailId() != scProductScreenshotModelImpl.getOriginalThumbnailId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
				new Object[] {
					new Long(scProductScreenshotModelImpl.getOriginalThumbnailId())
				});
		}

		if (isNew ||
				(scProductScreenshot.getThumbnailId() != scProductScreenshotModelImpl.getOriginalThumbnailId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
				new Object[] { new Long(scProductScreenshot.getThumbnailId()) },
				scProductScreenshot);
		}

		if (!isNew &&
				(scProductScreenshot.getFullImageId() != scProductScreenshotModelImpl.getOriginalFullImageId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
				new Object[] {
					new Long(scProductScreenshotModelImpl.getOriginalFullImageId())
				});
		}

		if (isNew ||
				(scProductScreenshot.getFullImageId() != scProductScreenshotModelImpl.getOriginalFullImageId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
				new Object[] { new Long(scProductScreenshot.getFullImageId()) },
				scProductScreenshot);
		}

		if (!isNew &&
				((scProductScreenshot.getProductEntryId() != scProductScreenshotModelImpl.getOriginalProductEntryId()) ||
				(scProductScreenshot.getPriority() != scProductScreenshotModelImpl.getOriginalPriority()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_P,
				new Object[] {
					new Long(scProductScreenshotModelImpl.getOriginalProductEntryId()),
					new Integer(scProductScreenshotModelImpl.getOriginalPriority())
				});
		}

		if (isNew ||
				((scProductScreenshot.getProductEntryId() != scProductScreenshotModelImpl.getOriginalProductEntryId()) ||
				(scProductScreenshot.getPriority() != scProductScreenshotModelImpl.getOriginalPriority()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_P,
				new Object[] {
					new Long(scProductScreenshot.getProductEntryId()),
					new Integer(scProductScreenshot.getPriority())
				}, scProductScreenshot);
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot findByPrimaryKey(long productScreenshotId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByPrimaryKey(productScreenshotId);

		if (scProductScreenshot == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SCProductScreenshot exists with the primary key " +
					productScreenshotId);
			}

			throw new NoSuchProductScreenshotException(
				"No SCProductScreenshot exists with the primary key " +
				productScreenshotId);
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot fetchByPrimaryKey(long productScreenshotId)
		throws SystemException {
		SCProductScreenshot scProductScreenshot = (SCProductScreenshot)EntityCacheUtil.getResult(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
				SCProductScreenshotImpl.class, productScreenshotId, this);

		if (scProductScreenshot == null) {
			Session session = null;

			try {
				session = openSession();

				scProductScreenshot = (SCProductScreenshot)session.get(SCProductScreenshotImpl.class,
						new Long(productScreenshotId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (scProductScreenshot != null) {
					cacheResult(scProductScreenshot);
				}

				closeSession(session);
			}
		}

		return scProductScreenshot;
	}

	public List<SCProductScreenshot> findByProductEntryId(long productEntryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(productEntryId) };

		List<SCProductScreenshot> list = (List<SCProductScreenshot>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_PRODUCTENTRYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("productEntryId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("productEntryId ASC, ");
				query.append("priority ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCProductScreenshot>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_PRODUCTENTRYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SCProductScreenshot> findByProductEntryId(long productEntryId,
		int start, int end) throws SystemException {
		return findByProductEntryId(productEntryId, start, end, null);
	}

	public List<SCProductScreenshot> findByProductEntryId(long productEntryId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(productEntryId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SCProductScreenshot> list = (List<SCProductScreenshot>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_PRODUCTENTRYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("productEntryId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("productEntryId ASC, ");
					query.append("priority ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				list = (List<SCProductScreenshot>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCProductScreenshot>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_PRODUCTENTRYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SCProductScreenshot findByProductEntryId_First(long productEntryId,
		OrderByComparator obc)
		throws NoSuchProductScreenshotException, SystemException {
		List<SCProductScreenshot> list = findByProductEntryId(productEntryId,
				0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCProductScreenshot exists with the key {");

			msg.append("productEntryId=" + productEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductScreenshotException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductScreenshot findByProductEntryId_Last(long productEntryId,
		OrderByComparator obc)
		throws NoSuchProductScreenshotException, SystemException {
		int count = countByProductEntryId(productEntryId);

		List<SCProductScreenshot> list = findByProductEntryId(productEntryId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCProductScreenshot exists with the key {");

			msg.append("productEntryId=" + productEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductScreenshotException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductScreenshot[] findByProductEntryId_PrevAndNext(
		long productScreenshotId, long productEntryId, OrderByComparator obc)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByPrimaryKey(productScreenshotId);

		int count = countByProductEntryId(productEntryId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

			query.append("productEntryId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("productEntryId ASC, ");
				query.append("priority ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(productEntryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scProductScreenshot);

			SCProductScreenshot[] array = new SCProductScreenshotImpl[3];

			array[0] = (SCProductScreenshot)objArray[0];
			array[1] = (SCProductScreenshot)objArray[1];
			array[2] = (SCProductScreenshot)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCProductScreenshot findByThumbnailId(long thumbnailId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByThumbnailId(thumbnailId);

		if (scProductScreenshot == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCProductScreenshot exists with the key {");

			msg.append("thumbnailId=" + thumbnailId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductScreenshotException(msg.toString());
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot fetchByThumbnailId(long thumbnailId)
		throws SystemException {
		return fetchByThumbnailId(thumbnailId, true);
	}

	public SCProductScreenshot fetchByThumbnailId(long thumbnailId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(thumbnailId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("thumbnailId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("productEntryId ASC, ");
				query.append("priority ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(thumbnailId);

				List<SCProductScreenshot> list = q.list();

				result = list;

				SCProductScreenshot scProductScreenshot = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
						finderArgs, list);
				}
				else {
					scProductScreenshot = list.get(0);

					cacheResult(scProductScreenshot);
				}

				return scProductScreenshot;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
						finderArgs, new ArrayList<SCProductScreenshot>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (SCProductScreenshot)result;
			}
		}
	}

	public SCProductScreenshot findByFullImageId(long fullImageId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByFullImageId(fullImageId);

		if (scProductScreenshot == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCProductScreenshot exists with the key {");

			msg.append("fullImageId=" + fullImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductScreenshotException(msg.toString());
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot fetchByFullImageId(long fullImageId)
		throws SystemException {
		return fetchByFullImageId(fullImageId, true);
	}

	public SCProductScreenshot fetchByFullImageId(long fullImageId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(fullImageId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("fullImageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("productEntryId ASC, ");
				query.append("priority ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fullImageId);

				List<SCProductScreenshot> list = q.list();

				result = list;

				SCProductScreenshot scProductScreenshot = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
						finderArgs, list);
				}
				else {
					scProductScreenshot = list.get(0);

					cacheResult(scProductScreenshot);
				}

				return scProductScreenshot;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
						finderArgs, new ArrayList<SCProductScreenshot>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (SCProductScreenshot)result;
			}
		}
	}

	public SCProductScreenshot findByP_P(long productEntryId, int priority)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByP_P(productEntryId,
				priority);

		if (scProductScreenshot == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCProductScreenshot exists with the key {");

			msg.append("productEntryId=" + productEntryId);

			msg.append(", ");
			msg.append("priority=" + priority);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductScreenshotException(msg.toString());
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot fetchByP_P(long productEntryId, int priority)
		throws SystemException {
		return fetchByP_P(productEntryId, priority, true);
	}

	public SCProductScreenshot fetchByP_P(long productEntryId, int priority,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(productEntryId), new Integer(priority)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_P_P,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("productEntryId = ?");

				query.append(" AND ");

				query.append("priority = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("productEntryId ASC, ");
				query.append("priority ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				qPos.add(priority);

				List<SCProductScreenshot> list = q.list();

				result = list;

				SCProductScreenshot scProductScreenshot = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_P,
						finderArgs, list);
				}
				else {
					scProductScreenshot = list.get(0);

					cacheResult(scProductScreenshot);
				}

				return scProductScreenshot;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_P,
						finderArgs, new ArrayList<SCProductScreenshot>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (SCProductScreenshot)result;
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

	public List<SCProductScreenshot> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SCProductScreenshot> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SCProductScreenshot> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SCProductScreenshot> list = (List<SCProductScreenshot>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("productEntryId ASC, ");
					query.append("priority ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<SCProductScreenshot>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SCProductScreenshot>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCProductScreenshot>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByProductEntryId(long productEntryId)
		throws SystemException {
		for (SCProductScreenshot scProductScreenshot : findByProductEntryId(
				productEntryId)) {
			remove(scProductScreenshot);
		}
	}

	public void removeByThumbnailId(long thumbnailId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByThumbnailId(thumbnailId);

		remove(scProductScreenshot);
	}

	public void removeByFullImageId(long fullImageId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByFullImageId(fullImageId);

		remove(scProductScreenshot);
	}

	public void removeByP_P(long productEntryId, int priority)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByP_P(productEntryId,
				priority);

		remove(scProductScreenshot);
	}

	public void removeAll() throws SystemException {
		for (SCProductScreenshot scProductScreenshot : findAll()) {
			remove(scProductScreenshot);
		}
	}

	public int countByProductEntryId(long productEntryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(productEntryId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("productEntryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByThumbnailId(long thumbnailId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(thumbnailId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_THUMBNAILID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("thumbnailId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(thumbnailId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_THUMBNAILID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByFullImageId(long fullImageId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(fullImageId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FULLIMAGEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("fullImageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fullImageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FULLIMAGEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByP_P(long productEntryId, int priority)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(productEntryId), new Integer(priority)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("productEntryId = ?");

				query.append(" AND ");

				query.append("priority = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				qPos.add(priority);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_P, finderArgs,
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
						"SELECT COUNT(*) FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot");

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
						"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCProductScreenshot")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SCProductScreenshot>> listenersList = new ArrayList<ModelListener<SCProductScreenshot>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SCProductScreenshot>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCLicensePersistence.impl")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCLicensePersistence scLicensePersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence.impl")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence scFrameworkVersionPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence.impl")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence scProductEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductScreenshotPersistence.impl")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCProductScreenshotPersistence scProductScreenshotPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionPersistence.impl")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionPersistence scProductVersionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	private static Log _log = LogFactoryUtil.getLog(SCProductScreenshotPersistenceImpl.class);
}