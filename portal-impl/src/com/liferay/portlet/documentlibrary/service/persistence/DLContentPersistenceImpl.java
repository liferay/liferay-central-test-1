/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchContentException;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.portlet.documentlibrary.model.impl.DLContentImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLContentModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the document library content service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLContentPersistence
 * @see DLContentUtil
 * @generated
 */
public class DLContentPersistenceImpl extends BasePersistenceImpl<DLContent>
	implements DLContentPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLContentUtil} to access the document library content persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLContentImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_C_P_R_P = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, DLContentImpl.class,
			FINDER_CLASS_NAME_LIST, "findByC_P_R_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P_R_P = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByC_P_R_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_C_P_R_P_V = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, DLContentImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_P_R_P_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P_R_P_V = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByC_P_R_P_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, DLContentImpl.class,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the document library content in the entity cache if it is enabled.
	 *
	 * @param dlContent the document library content
	 */
	public void cacheResult(DLContent dlContent) {
		EntityCacheUtil.putResult(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentImpl.class, dlContent.getPrimaryKey(), dlContent);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P_R_P_V,
			new Object[] {
				Long.valueOf(dlContent.getCompanyId()),
				
			dlContent.getPortletId(), Long.valueOf(dlContent.getRepositoryId()),
				
			dlContent.getPath(),
				
			dlContent.getVersion()
			}, dlContent);

		dlContent.resetOriginalValues();
	}

	/**
	 * Caches the document library contents in the entity cache if it is enabled.
	 *
	 * @param dlContents the document library contents
	 */
	public void cacheResult(List<DLContent> dlContents) {
		for (DLContent dlContent : dlContents) {
			if (EntityCacheUtil.getResult(
						DLContentModelImpl.ENTITY_CACHE_ENABLED,
						DLContentImpl.class, dlContent.getPrimaryKey()) == null) {
				cacheResult(dlContent);
			}

			else {
				dlContent.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all document library contents.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DLContentImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DLContentImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the document library content.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLContent dlContent) {
		EntityCacheUtil.removeResult(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentImpl.class, dlContent.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_P_R_P_V,
			new Object[] {
				Long.valueOf(dlContent.getCompanyId()),
				
			dlContent.getPortletId(), Long.valueOf(dlContent.getRepositoryId()),
				
			dlContent.getPath(),
				
			dlContent.getVersion()
			});
	}

	/**
	 * Creates a new document library content with the primary key. Does not add the document library content to the database.
	 *
	 * @param contentId the primary key for the new document library content
	 * @return the new document library content
	 */
	public DLContent create(long contentId) {
		DLContent dlContent = new DLContentImpl();

		dlContent.setNew(true);
		dlContent.setPrimaryKey(contentId);

		return dlContent;
	}

	/**
	 * Removes the document library content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the document library content
	 * @return the document library content that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a document library content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLContent remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the document library content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentId the primary key of the document library content
	 * @return the document library content that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a document library content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLContent remove(long contentId)
		throws NoSuchContentException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLContent dlContent = (DLContent)session.get(DLContentImpl.class,
					Long.valueOf(contentId));

			if (dlContent == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + contentId);
				}

				throw new NoSuchContentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					contentId);
			}

			return dlContentPersistence.remove(dlContent);
		}
		catch (NoSuchContentException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes the document library content from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlContent the document library content
	 * @return the document library content that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLContent remove(DLContent dlContent) throws SystemException {
		return super.remove(dlContent);
	}

	@Override
	protected DLContent removeImpl(DLContent dlContent)
		throws SystemException {
		dlContent = toUnwrappedModel(dlContent);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, dlContent);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		DLContentModelImpl dlContentModelImpl = (DLContentModelImpl)dlContent;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_P_R_P_V,
			new Object[] {
				Long.valueOf(dlContentModelImpl.getCompanyId()),
				
			dlContentModelImpl.getPortletId(),
				Long.valueOf(dlContentModelImpl.getRepositoryId()),
				
			dlContentModelImpl.getPath(),
				
			dlContentModelImpl.getVersion()
			});

		EntityCacheUtil.removeResult(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentImpl.class, dlContent.getPrimaryKey());

		return dlContent;
	}

	@Override
	public DLContent updateImpl(
		com.liferay.portlet.documentlibrary.model.DLContent dlContent,
		boolean merge) throws SystemException {
		dlContent = toUnwrappedModel(dlContent);

		boolean isNew = dlContent.isNew();

		DLContentModelImpl dlContentModelImpl = (DLContentModelImpl)dlContent;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, dlContent, merge);

			dlContent.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DLContentModelImpl.ENTITY_CACHE_ENABLED,
			DLContentImpl.class, dlContent.getPrimaryKey(), dlContent);

		dlContent.resetOriginalValues();

		if (!isNew &&
				((dlContent.getCompanyId() != dlContentModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(dlContent.getPortletId(),
					dlContentModelImpl.getOriginalPortletId()) ||
				(dlContent.getRepositoryId() != dlContentModelImpl.getOriginalRepositoryId()) ||
				!Validator.equals(dlContent.getPath(),
					dlContentModelImpl.getOriginalPath()) ||
				!Validator.equals(dlContent.getVersion(),
					dlContentModelImpl.getOriginalVersion()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_P_R_P_V,
				new Object[] {
					Long.valueOf(dlContentModelImpl.getOriginalCompanyId()),
					
				dlContentModelImpl.getOriginalPortletId(),
					Long.valueOf(dlContentModelImpl.getOriginalRepositoryId()),
					
				dlContentModelImpl.getOriginalPath(),
					
				dlContentModelImpl.getOriginalVersion()
				});
		}

		if (isNew ||
				((dlContent.getCompanyId() != dlContentModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(dlContent.getPortletId(),
					dlContentModelImpl.getOriginalPortletId()) ||
				(dlContent.getRepositoryId() != dlContentModelImpl.getOriginalRepositoryId()) ||
				!Validator.equals(dlContent.getPath(),
					dlContentModelImpl.getOriginalPath()) ||
				!Validator.equals(dlContent.getVersion(),
					dlContentModelImpl.getOriginalVersion()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P_R_P_V,
				new Object[] {
					Long.valueOf(dlContent.getCompanyId()),
					
				dlContent.getPortletId(),
					Long.valueOf(dlContent.getRepositoryId()),
					
				dlContent.getPath(),
					
				dlContent.getVersion()
				}, dlContent);
		}

		return dlContent;
	}

	protected DLContent toUnwrappedModel(DLContent dlContent) {
		if (dlContent instanceof DLContentImpl) {
			return dlContent;
		}

		DLContentImpl dlContentImpl = new DLContentImpl();

		dlContentImpl.setNew(dlContent.isNew());
		dlContentImpl.setPrimaryKey(dlContent.getPrimaryKey());

		dlContentImpl.setContentId(dlContent.getContentId());
		dlContentImpl.setGroupId(dlContent.getGroupId());
		dlContentImpl.setCompanyId(dlContent.getCompanyId());
		dlContentImpl.setPortletId(dlContent.getPortletId());
		dlContentImpl.setRepositoryId(dlContent.getRepositoryId());
		dlContentImpl.setPath(dlContent.getPath());
		dlContentImpl.setVersion(dlContent.getVersion());
		dlContentImpl.setData(dlContent.getData());
		dlContentImpl.setSize(dlContent.getSize());

		return dlContentImpl;
	}

	/**
	 * Returns the document library content with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library content
	 * @return the document library content
	 * @throws com.liferay.portal.NoSuchModelException if a document library content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLContent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the document library content with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchContentException} if it could not be found.
	 *
	 * @param contentId the primary key of the document library content
	 * @return the document library content
	 * @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a document library content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLContent findByPrimaryKey(long contentId)
		throws NoSuchContentException, SystemException {
		DLContent dlContent = fetchByPrimaryKey(contentId);

		if (dlContent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + contentId);
			}

			throw new NoSuchContentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				contentId);
		}

		return dlContent;
	}

	/**
	 * Returns the document library content with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library content
	 * @return the document library content, or <code>null</code> if a document library content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLContent fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the document library content with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param contentId the primary key of the document library content
	 * @return the document library content, or <code>null</code> if a document library content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLContent fetchByPrimaryKey(long contentId)
		throws SystemException {
		DLContent dlContent = (DLContent)EntityCacheUtil.getResult(DLContentModelImpl.ENTITY_CACHE_ENABLED,
				DLContentImpl.class, contentId);

		if (dlContent == _nullDLContent) {
			return null;
		}

		if (dlContent == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				dlContent = (DLContent)session.get(DLContentImpl.class,
						Long.valueOf(contentId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (dlContent != null) {
					cacheResult(dlContent);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(DLContentModelImpl.ENTITY_CACHE_ENABLED,
						DLContentImpl.class, contentId, _nullDLContent);
				}

				closeSession(session);
			}
		}

		return dlContent;
	}

	/**
	 * Returns all the document library contents where companyId = &#63; and portletId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @return the matching document library contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLContent> findByC_P_R_P(long companyId, String portletId,
		long repositoryId, String path) throws SystemException {
		return findByC_P_R_P(companyId, portletId, repositoryId, path,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library contents where companyId = &#63; and portletId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @return the range of matching document library contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLContent> findByC_P_R_P(long companyId, String portletId,
		long repositoryId, String path, int start, int end)
		throws SystemException {
		return findByC_P_R_P(companyId, portletId, repositoryId, path, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the document library contents where companyId = &#63; and portletId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLContent> findByC_P_R_P(long companyId, String portletId,
		long repositoryId, String path, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, portletId, repositoryId, path,
				
				start, end, orderByComparator
			};

		List<DLContent> list = (List<DLContent>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_P_R_P,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_DLCONTENT_WHERE);

			query.append(_FINDER_COLUMN_C_P_R_P_COMPANYID_2);

			if (portletId == null) {
				query.append(_FINDER_COLUMN_C_P_R_P_PORTLETID_1);
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_P_R_P_PORTLETID_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_P_R_P_PORTLETID_2);
				}
			}

			query.append(_FINDER_COLUMN_C_P_R_P_REPOSITORYID_2);

			if (path == null) {
				query.append(_FINDER_COLUMN_C_P_R_P_PATH_1);
			}
			else {
				if (path.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_P_R_P_PATH_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_P_R_P_PATH_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(repositoryId);

				if (path != null) {
					qPos.add(path);
				}

				list = (List<DLContent>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_C_P_R_P,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_P_R_P,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first document library content in the ordered set where companyId = &#63; and portletId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library content
	 * @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a matching document library content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLContent findByC_P_R_P_First(long companyId, String portletId,
		long repositoryId, String path, OrderByComparator orderByComparator)
		throws NoSuchContentException, SystemException {
		List<DLContent> list = findByC_P_R_P(companyId, portletId,
				repositoryId, path, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", portletId=");
			msg.append(portletId);

			msg.append(", repositoryId=");
			msg.append(repositoryId);

			msg.append(", path=");
			msg.append(path);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchContentException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last document library content in the ordered set where companyId = &#63; and portletId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library content
	 * @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a matching document library content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLContent findByC_P_R_P_Last(long companyId, String portletId,
		long repositoryId, String path, OrderByComparator orderByComparator)
		throws NoSuchContentException, SystemException {
		int count = countByC_P_R_P(companyId, portletId, repositoryId, path);

		List<DLContent> list = findByC_P_R_P(companyId, portletId,
				repositoryId, path, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", portletId=");
			msg.append(portletId);

			msg.append(", repositoryId=");
			msg.append(repositoryId);

			msg.append(", path=");
			msg.append(path);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchContentException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the document library contents before and after the current document library content in the ordered set where companyId = &#63; and portletId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param contentId the primary key of the current document library content
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library content
	 * @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a document library content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLContent[] findByC_P_R_P_PrevAndNext(long contentId,
		long companyId, String portletId, long repositoryId, String path,
		OrderByComparator orderByComparator)
		throws NoSuchContentException, SystemException {
		DLContent dlContent = findByPrimaryKey(contentId);

		Session session = null;

		try {
			session = openSession();

			DLContent[] array = new DLContentImpl[3];

			array[0] = getByC_P_R_P_PrevAndNext(session, dlContent, companyId,
					portletId, repositoryId, path, orderByComparator, true);

			array[1] = dlContent;

			array[2] = getByC_P_R_P_PrevAndNext(session, dlContent, companyId,
					portletId, repositoryId, path, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLContent getByC_P_R_P_PrevAndNext(Session session,
		DLContent dlContent, long companyId, String portletId,
		long repositoryId, String path, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLCONTENT_WHERE);

		query.append(_FINDER_COLUMN_C_P_R_P_COMPANYID_2);

		if (portletId == null) {
			query.append(_FINDER_COLUMN_C_P_R_P_PORTLETID_1);
		}
		else {
			if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_P_R_P_PORTLETID_3);
			}
			else {
				query.append(_FINDER_COLUMN_C_P_R_P_PORTLETID_2);
			}
		}

		query.append(_FINDER_COLUMN_C_P_R_P_REPOSITORYID_2);

		if (path == null) {
			query.append(_FINDER_COLUMN_C_P_R_P_PATH_1);
		}
		else {
			if (path.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_P_R_P_PATH_3);
			}
			else {
				query.append(_FINDER_COLUMN_C_P_R_P_PATH_2);
			}
		}

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (portletId != null) {
			qPos.add(portletId);
		}

		qPos.add(repositoryId);

		if (path != null) {
			qPos.add(path);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlContent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLContent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the document library content where companyId = &#63; and portletId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchContentException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @return the matching document library content
	 * @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a matching document library content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLContent findByC_P_R_P_V(long companyId, String portletId,
		long repositoryId, String path, String version)
		throws NoSuchContentException, SystemException {
		DLContent dlContent = fetchByC_P_R_P_V(companyId, portletId,
				repositoryId, path, version);

		if (dlContent == null) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", portletId=");
			msg.append(portletId);

			msg.append(", repositoryId=");
			msg.append(repositoryId);

			msg.append(", path=");
			msg.append(path);

			msg.append(", version=");
			msg.append(version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchContentException(msg.toString());
		}

		return dlContent;
	}

	/**
	 * Returns the document library content where companyId = &#63; and portletId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @return the matching document library content, or <code>null</code> if a matching document library content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLContent fetchByC_P_R_P_V(long companyId, String portletId,
		long repositoryId, String path, String version)
		throws SystemException {
		return fetchByC_P_R_P_V(companyId, portletId, repositoryId, path,
			version, true);
	}

	/**
	 * Returns the document library content where companyId = &#63; and portletId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching document library content, or <code>null</code> if a matching document library content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLContent fetchByC_P_R_P_V(long companyId, String portletId,
		long repositoryId, String path, String version,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, portletId, repositoryId, path, version
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_P_R_P_V,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_DLCONTENT_WHERE);

			query.append(_FINDER_COLUMN_C_P_R_P_V_COMPANYID_2);

			if (portletId == null) {
				query.append(_FINDER_COLUMN_C_P_R_P_V_PORTLETID_1);
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_P_R_P_V_PORTLETID_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_P_R_P_V_PORTLETID_2);
				}
			}

			query.append(_FINDER_COLUMN_C_P_R_P_V_REPOSITORYID_2);

			if (path == null) {
				query.append(_FINDER_COLUMN_C_P_R_P_V_PATH_1);
			}
			else {
				if (path.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_P_R_P_V_PATH_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_P_R_P_V_PATH_2);
				}
			}

			if (version == null) {
				query.append(_FINDER_COLUMN_C_P_R_P_V_VERSION_1);
			}
			else {
				if (version.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_P_R_P_V_VERSION_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_P_R_P_V_VERSION_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(repositoryId);

				if (path != null) {
					qPos.add(path);
				}

				if (version != null) {
					qPos.add(version);
				}

				List<DLContent> list = q.list();

				result = list;

				DLContent dlContent = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P_R_P_V,
						finderArgs, list);
				}
				else {
					dlContent = list.get(0);

					cacheResult(dlContent);

					if ((dlContent.getCompanyId() != companyId) ||
							(dlContent.getPortletId() == null) ||
							!dlContent.getPortletId().equals(portletId) ||
							(dlContent.getRepositoryId() != repositoryId) ||
							(dlContent.getPath() == null) ||
							!dlContent.getPath().equals(path) ||
							(dlContent.getVersion() == null) ||
							!dlContent.getVersion().equals(version)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P_R_P_V,
							finderArgs, dlContent);
					}
				}

				return dlContent;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_P_R_P_V,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (DLContent)result;
			}
		}
	}

	/**
	 * Returns all the document library contents.
	 *
	 * @return the document library contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLContent> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @return the range of document library contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLContent> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLContent> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<DLContent> list = (List<DLContent>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLCONTENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLCONTENT;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DLContent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLContent>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs,
						list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the document library contents where companyId = &#63; and portletId = &#63; and repositoryId = &#63; and path = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_P_R_P(long companyId, String portletId,
		long repositoryId, String path) throws SystemException {
		for (DLContent dlContent : findByC_P_R_P(companyId, portletId,
				repositoryId, path)) {
			dlContentPersistence.remove(dlContent);
		}
	}

	/**
	 * Removes the document library content where companyId = &#63; and portletId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_P_R_P_V(long companyId, String portletId,
		long repositoryId, String path, String version)
		throws NoSuchContentException, SystemException {
		DLContent dlContent = findByC_P_R_P_V(companyId, portletId,
				repositoryId, path, version);

		dlContentPersistence.remove(dlContent);
	}

	/**
	 * Removes all the document library contents from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DLContent dlContent : findAll()) {
			dlContentPersistence.remove(dlContent);
		}
	}

	/**
	 * Returns the number of document library contents where companyId = &#63; and portletId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @return the number of matching document library contents
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_P_R_P(long companyId, String portletId,
		long repositoryId, String path) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, portletId, repositoryId, path
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_P_R_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLCONTENT_WHERE);

			query.append(_FINDER_COLUMN_C_P_R_P_COMPANYID_2);

			if (portletId == null) {
				query.append(_FINDER_COLUMN_C_P_R_P_PORTLETID_1);
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_P_R_P_PORTLETID_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_P_R_P_PORTLETID_2);
				}
			}

			query.append(_FINDER_COLUMN_C_P_R_P_REPOSITORYID_2);

			if (path == null) {
				query.append(_FINDER_COLUMN_C_P_R_P_PATH_1);
			}
			else {
				if (path.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_P_R_P_PATH_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_P_R_P_PATH_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(repositoryId);

				if (path != null) {
					qPos.add(path);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_P_R_P,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library contents where companyId = &#63; and portletId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @return the number of matching document library contents
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_P_R_P_V(long companyId, String portletId,
		long repositoryId, String path, String version)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, portletId, repositoryId, path, version
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_P_R_P_V,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_DLCONTENT_WHERE);

			query.append(_FINDER_COLUMN_C_P_R_P_V_COMPANYID_2);

			if (portletId == null) {
				query.append(_FINDER_COLUMN_C_P_R_P_V_PORTLETID_1);
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_P_R_P_V_PORTLETID_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_P_R_P_V_PORTLETID_2);
				}
			}

			query.append(_FINDER_COLUMN_C_P_R_P_V_REPOSITORYID_2);

			if (path == null) {
				query.append(_FINDER_COLUMN_C_P_R_P_V_PATH_1);
			}
			else {
				if (path.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_P_R_P_V_PATH_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_P_R_P_V_PATH_2);
				}
			}

			if (version == null) {
				query.append(_FINDER_COLUMN_C_P_R_P_V_VERSION_1);
			}
			else {
				if (version.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_P_R_P_V_VERSION_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_P_R_P_V_VERSION_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(repositoryId);

				if (path != null) {
					qPos.add(path);
				}

				if (version != null) {
					qPos.add(version);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_P_R_P_V,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library contents.
	 *
	 * @return the number of document library contents
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLCONTENT);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the document library content persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLContent")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLContent>> listenersList = new ArrayList<ModelListener<DLContent>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLContent>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(DLContentImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = DLContentPersistence.class)
	protected DLContentPersistence dlContentPersistence;
	@BeanReference(type = DLFileEntryPersistence.class)
	protected DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(type = DLFileEntryMetadataPersistence.class)
	protected DLFileEntryMetadataPersistence dlFileEntryMetadataPersistence;
	@BeanReference(type = DLFileEntryTypePersistence.class)
	protected DLFileEntryTypePersistence dlFileEntryTypePersistence;
	@BeanReference(type = DLFileRankPersistence.class)
	protected DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(type = DLFileShortcutPersistence.class)
	protected DLFileShortcutPersistence dlFileShortcutPersistence;
	@BeanReference(type = DLFileVersionPersistence.class)
	protected DLFileVersionPersistence dlFileVersionPersistence;
	@BeanReference(type = DLFolderPersistence.class)
	protected DLFolderPersistence dlFolderPersistence;
	@BeanReference(type = DLSyncPersistence.class)
	protected DLSyncPersistence dlSyncPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_DLCONTENT = "SELECT dlContent FROM DLContent dlContent";
	private static final String _SQL_SELECT_DLCONTENT_WHERE = "SELECT dlContent FROM DLContent dlContent WHERE ";
	private static final String _SQL_COUNT_DLCONTENT = "SELECT COUNT(dlContent) FROM DLContent dlContent";
	private static final String _SQL_COUNT_DLCONTENT_WHERE = "SELECT COUNT(dlContent) FROM DLContent dlContent WHERE ";
	private static final String _FINDER_COLUMN_C_P_R_P_COMPANYID_2 = "dlContent.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_R_P_PORTLETID_1 = "dlContent.portletId IS NULL AND ";
	private static final String _FINDER_COLUMN_C_P_R_P_PORTLETID_2 = "dlContent.portletId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_R_P_PORTLETID_3 = "(dlContent.portletId IS NULL OR dlContent.portletId = ?) AND ";
	private static final String _FINDER_COLUMN_C_P_R_P_REPOSITORYID_2 = "dlContent.repositoryId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_R_P_PATH_1 = "dlContent.path IS NULL";
	private static final String _FINDER_COLUMN_C_P_R_P_PATH_2 = "dlContent.path = ?";
	private static final String _FINDER_COLUMN_C_P_R_P_PATH_3 = "(dlContent.path IS NULL OR dlContent.path = ?)";
	private static final String _FINDER_COLUMN_C_P_R_P_V_COMPANYID_2 = "dlContent.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_R_P_V_PORTLETID_1 = "dlContent.portletId IS NULL AND ";
	private static final String _FINDER_COLUMN_C_P_R_P_V_PORTLETID_2 = "dlContent.portletId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_R_P_V_PORTLETID_3 = "(dlContent.portletId IS NULL OR dlContent.portletId = ?) AND ";
	private static final String _FINDER_COLUMN_C_P_R_P_V_REPOSITORYID_2 = "dlContent.repositoryId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_R_P_V_PATH_1 = "dlContent.path IS NULL AND ";
	private static final String _FINDER_COLUMN_C_P_R_P_V_PATH_2 = "dlContent.path = ? AND ";
	private static final String _FINDER_COLUMN_C_P_R_P_V_PATH_3 = "(dlContent.path IS NULL OR dlContent.path = ?) AND ";
	private static final String _FINDER_COLUMN_C_P_R_P_V_VERSION_1 = "dlContent.version IS NULL";
	private static final String _FINDER_COLUMN_C_P_R_P_V_VERSION_2 = "dlContent.version = ?";
	private static final String _FINDER_COLUMN_C_P_R_P_V_VERSION_3 = "(dlContent.version IS NULL OR dlContent.version = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlContent.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLContent exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLContent exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DLContentPersistenceImpl.class);
	private static DLContent _nullDLContent = new DLContentImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DLContent> toCacheModel() {
				return _nullDLContentCacheModel;
			}
		};

	private static CacheModel<DLContent> _nullDLContentCacheModel = new CacheModel<DLContent>() {
			public DLContent toEntityModel() {
				return _nullDLContent;
			}
		};
}