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
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence;
import com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructurePersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the document library file entry type service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryTypePersistence
 * @see DLFileEntryTypeUtil
 * @generated
 */
public class DLFileEntryTypePersistenceImpl extends BasePersistenceImpl<DLFileEntryType>
	implements DLFileEntryTypePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLFileEntryTypeUtil} to access the document library file entry type persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileEntryTypeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(DLFileEntryTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryTypeModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryTypeImpl.class, FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DLFileEntryTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryTypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_N_D = new FinderPath(DLFileEntryTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryTypeModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryTypeImpl.class, FINDER_CLASS_NAME_LIST, "findByG_N_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N_D = new FinderPath(DLFileEntryTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryTypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_N_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DLFileEntryTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryTypeModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryTypeImpl.class, FINDER_CLASS_NAME_LIST, "findAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileEntryTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryTypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the document library file entry type in the entity cache if it is enabled.
	 *
	 * @param dlFileEntryType the document library file entry type
	 */
	public void cacheResult(DLFileEntryType dlFileEntryType) {
		EntityCacheUtil.putResult(DLFileEntryTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryTypeImpl.class, dlFileEntryType.getPrimaryKey(),
			dlFileEntryType);

		dlFileEntryType.resetOriginalValues();
	}

	/**
	 * Caches the document library file entry types in the entity cache if it is enabled.
	 *
	 * @param dlFileEntryTypes the document library file entry types
	 */
	public void cacheResult(List<DLFileEntryType> dlFileEntryTypes) {
		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			if (EntityCacheUtil.getResult(
						DLFileEntryTypeModelImpl.ENTITY_CACHE_ENABLED,
						DLFileEntryTypeImpl.class,
						dlFileEntryType.getPrimaryKey()) == null) {
				cacheResult(dlFileEntryType);
			}
		}
	}

	/**
	 * Clears the cache for all document library file entry types.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DLFileEntryTypeImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DLFileEntryTypeImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the document library file entry type.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFileEntryType dlFileEntryType) {
		EntityCacheUtil.removeResult(DLFileEntryTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryTypeImpl.class, dlFileEntryType.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);
	}

	/**
	 * Creates a new document library file entry type with the primary key. Does not add the document library file entry type to the database.
	 *
	 * @param fileEntryTypeId the primary key for the new document library file entry type
	 * @return the new document library file entry type
	 */
	public DLFileEntryType create(long fileEntryTypeId) {
		DLFileEntryType dlFileEntryType = new DLFileEntryTypeImpl();

		dlFileEntryType.setNew(true);
		dlFileEntryType.setPrimaryKey(fileEntryTypeId);

		return dlFileEntryType;
	}

	/**
	 * Removes the document library file entry type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the document library file entry type
	 * @return the document library file entry type that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a document library file entry type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileEntryType remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the document library file entry type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileEntryTypeId the primary key of the document library file entry type
	 * @return the document library file entry type that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryType remove(long fileEntryTypeId)
		throws NoSuchFileEntryTypeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileEntryType dlFileEntryType = (DLFileEntryType)session.get(DLFileEntryTypeImpl.class,
					Long.valueOf(fileEntryTypeId));

			if (dlFileEntryType == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						fileEntryTypeId);
				}

				throw new NoSuchFileEntryTypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					fileEntryTypeId);
			}

			return dlFileEntryTypePersistence.remove(dlFileEntryType);
		}
		catch (NoSuchFileEntryTypeException nsee) {
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
	 * Removes the document library file entry type from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileEntryType the document library file entry type
	 * @return the document library file entry type that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileEntryType remove(DLFileEntryType dlFileEntryType)
		throws SystemException {
		return super.remove(dlFileEntryType);
	}

	@Override
	protected DLFileEntryType removeImpl(DLFileEntryType dlFileEntryType)
		throws SystemException {
		dlFileEntryType = toUnwrappedModel(dlFileEntryType);

		try {
			clearDLFolders.clear(dlFileEntryType.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}

		try {
			clearDDMStructures.clear(dlFileEntryType.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, dlFileEntryType);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);

		EntityCacheUtil.removeResult(DLFileEntryTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryTypeImpl.class, dlFileEntryType.getPrimaryKey());

		return dlFileEntryType;
	}

	@Override
	public DLFileEntryType updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType,
		boolean merge) throws SystemException {
		dlFileEntryType = toUnwrappedModel(dlFileEntryType);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, dlFileEntryType, merge);

			dlFileEntryType.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DLFileEntryTypeModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryTypeImpl.class, dlFileEntryType.getPrimaryKey(),
			dlFileEntryType);

		return dlFileEntryType;
	}

	protected DLFileEntryType toUnwrappedModel(DLFileEntryType dlFileEntryType) {
		if (dlFileEntryType instanceof DLFileEntryTypeImpl) {
			return dlFileEntryType;
		}

		DLFileEntryTypeImpl dlFileEntryTypeImpl = new DLFileEntryTypeImpl();

		dlFileEntryTypeImpl.setNew(dlFileEntryType.isNew());
		dlFileEntryTypeImpl.setPrimaryKey(dlFileEntryType.getPrimaryKey());

		dlFileEntryTypeImpl.setFileEntryTypeId(dlFileEntryType.getFileEntryTypeId());
		dlFileEntryTypeImpl.setGroupId(dlFileEntryType.getGroupId());
		dlFileEntryTypeImpl.setCompanyId(dlFileEntryType.getCompanyId());
		dlFileEntryTypeImpl.setUserId(dlFileEntryType.getUserId());
		dlFileEntryTypeImpl.setUserName(dlFileEntryType.getUserName());
		dlFileEntryTypeImpl.setCreateDate(dlFileEntryType.getCreateDate());
		dlFileEntryTypeImpl.setModifiedDate(dlFileEntryType.getModifiedDate());
		dlFileEntryTypeImpl.setName(dlFileEntryType.getName());
		dlFileEntryTypeImpl.setDescription(dlFileEntryType.getDescription());

		return dlFileEntryTypeImpl;
	}

	/**
	 * Returns the document library file entry type with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file entry type
	 * @return the document library file entry type
	 * @throws com.liferay.portal.NoSuchModelException if a document library file entry type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileEntryType findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the document library file entry type with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException} if it could not be found.
	 *
	 * @param fileEntryTypeId the primary key of the document library file entry type
	 * @return the document library file entry type
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryType findByPrimaryKey(long fileEntryTypeId)
		throws NoSuchFileEntryTypeException, SystemException {
		DLFileEntryType dlFileEntryType = fetchByPrimaryKey(fileEntryTypeId);

		if (dlFileEntryType == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + fileEntryTypeId);
			}

			throw new NoSuchFileEntryTypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				fileEntryTypeId);
		}

		return dlFileEntryType;
	}

	/**
	 * Returns the document library file entry type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file entry type
	 * @return the document library file entry type, or <code>null</code> if a document library file entry type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileEntryType fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the document library file entry type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileEntryTypeId the primary key of the document library file entry type
	 * @return the document library file entry type, or <code>null</code> if a document library file entry type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryType fetchByPrimaryKey(long fileEntryTypeId)
		throws SystemException {
		DLFileEntryType dlFileEntryType = (DLFileEntryType)EntityCacheUtil.getResult(DLFileEntryTypeModelImpl.ENTITY_CACHE_ENABLED,
				DLFileEntryTypeImpl.class, fileEntryTypeId);

		if (dlFileEntryType == _nullDLFileEntryType) {
			return null;
		}

		if (dlFileEntryType == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				dlFileEntryType = (DLFileEntryType)session.get(DLFileEntryTypeImpl.class,
						Long.valueOf(fileEntryTypeId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (dlFileEntryType != null) {
					cacheResult(dlFileEntryType);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(DLFileEntryTypeModelImpl.ENTITY_CACHE_ENABLED,
						DLFileEntryTypeImpl.class, fileEntryTypeId,
						_nullDLFileEntryType);
				}

				closeSession(session);
			}
		}

		return dlFileEntryType;
	}

	/**
	 * Returns all the document library file entry types where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching document library file entry types
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryType> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry types where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @return the range of matching document library file entry types
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryType> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry types where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry types
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryType> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				start, end, orderByComparator
			};

		List<DLFileEntryType> list = (List<DLFileEntryType>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_DLFILEENTRYTYPE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

				qPos.add(groupId);

				list = (List<DLFileEntryType>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first document library file entry type in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry type
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryType findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryTypeException, SystemException {
		List<DLFileEntryType> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryTypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last document library file entry type in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry type
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryType findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryTypeException, SystemException {
		int count = countByGroupId(groupId);

		List<DLFileEntryType> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryTypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the document library file entry types before and after the current document library file entry type in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileEntryTypeId the primary key of the current document library file entry type
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry type
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryType[] findByGroupId_PrevAndNext(long fileEntryTypeId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryTypeException, SystemException {
		DLFileEntryType dlFileEntryType = findByPrimaryKey(fileEntryTypeId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryType[] array = new DLFileEntryTypeImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, dlFileEntryType,
					groupId, orderByComparator, true);

			array[1] = dlFileEntryType;

			array[2] = getByGroupId_PrevAndNext(session, dlFileEntryType,
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

	protected DLFileEntryType getByGroupId_PrevAndNext(Session session,
		DLFileEntryType dlFileEntryType, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRYTYPE_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFileEntryType);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntryType> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entry types that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching document library file entry types that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryType> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry types that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @return the range of matching document library file entry types that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryType> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry types that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry types that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryType> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(2);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRYTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntryType.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryTypeImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryTypeImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<DLFileEntryType>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library file entry types before and after the current document library file entry type in the ordered set of document library file entry types that the user has permission to view where groupId = &#63;.
	 *
	 * @param fileEntryTypeId the primary key of the current document library file entry type
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry type
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryType[] filterFindByGroupId_PrevAndNext(
		long fileEntryTypeId, long groupId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryTypeException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(fileEntryTypeId, groupId,
				orderByComparator);
		}

		DLFileEntryType dlFileEntryType = findByPrimaryKey(fileEntryTypeId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryType[] array = new DLFileEntryTypeImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, dlFileEntryType,
					groupId, orderByComparator, true);

			array[1] = dlFileEntryType;

			array[2] = filterGetByGroupId_PrevAndNext(session, dlFileEntryType,
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

	protected DLFileEntryType filterGetByGroupId_PrevAndNext(Session session,
		DLFileEntryType dlFileEntryType, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRYTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntryType.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryTypeImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryTypeImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFileEntryType);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntryType> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entry types where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @return the matching document library file entry types
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryType> findByG_N_D(long groupId, String name,
		String description) throws SystemException {
		return findByG_N_D(groupId, name, description, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry types where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @return the range of matching document library file entry types
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryType> findByG_N_D(long groupId, String name,
		String description, int start, int end) throws SystemException {
		return findByG_N_D(groupId, name, description, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry types where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry types
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryType> findByG_N_D(long groupId, String name,
		String description, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, name, description,
				
				start, end, orderByComparator
			};

		List<DLFileEntryType> list = (List<DLFileEntryType>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_N_D,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DLFILEENTRYTYPE_WHERE);

			query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_D_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_D_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_D_NAME_2);
				}
			}

			if (description == null) {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
			}
			else {
				if (description.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
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

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				if (description != null) {
					qPos.add(description);
				}

				list = (List<DLFileEntryType>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_N_D,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_N_D,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first document library file entry type in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry type
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryType findByG_N_D_First(long groupId, String name,
		String description, OrderByComparator orderByComparator)
		throws NoSuchFileEntryTypeException, SystemException {
		List<DLFileEntryType> list = findByG_N_D(groupId, name, description, 0,
				1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", description=");
			msg.append(description);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryTypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last document library file entry type in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry type
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryType findByG_N_D_Last(long groupId, String name,
		String description, OrderByComparator orderByComparator)
		throws NoSuchFileEntryTypeException, SystemException {
		int count = countByG_N_D(groupId, name, description);

		List<DLFileEntryType> list = findByG_N_D(groupId, name, description,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", description=");
			msg.append(description);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryTypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the document library file entry types before and after the current document library file entry type in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileEntryTypeId the primary key of the current document library file entry type
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry type
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryType[] findByG_N_D_PrevAndNext(long fileEntryTypeId,
		long groupId, String name, String description,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryTypeException, SystemException {
		DLFileEntryType dlFileEntryType = findByPrimaryKey(fileEntryTypeId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryType[] array = new DLFileEntryTypeImpl[3];

			array[0] = getByG_N_D_PrevAndNext(session, dlFileEntryType,
					groupId, name, description, orderByComparator, true);

			array[1] = dlFileEntryType;

			array[2] = getByG_N_D_PrevAndNext(session, dlFileEntryType,
					groupId, name, description, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntryType getByG_N_D_PrevAndNext(Session session,
		DLFileEntryType dlFileEntryType, long groupId, String name,
		String description, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRYTYPE_WHERE);

		query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_G_N_D_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_N_D_NAME_2);
			}
		}

		if (description == null) {
			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
		}
		else {
			if (description.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
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

		qPos.add(groupId);

		if (name != null) {
			qPos.add(name);
		}

		if (description != null) {
			qPos.add(description);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFileEntryType);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntryType> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entry types that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @return the matching document library file entry types that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryType> filterFindByG_N_D(long groupId, String name,
		String description) throws SystemException {
		return filterFindByG_N_D(groupId, name, description, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry types that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @return the range of matching document library file entry types that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryType> filterFindByG_N_D(long groupId, String name,
		String description, int start, int end) throws SystemException {
		return filterFindByG_N_D(groupId, name, description, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry types that the user has permissions to view where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry types that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryType> filterFindByG_N_D(long groupId, String name,
		String description, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_D(groupId, name, description, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRYTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_G_N_D_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_N_D_NAME_2);
			}
		}

		if (description == null) {
			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
		}
		else {
			if (description.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntryType.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryTypeImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryTypeImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (name != null) {
				qPos.add(name);
			}

			if (description != null) {
				qPos.add(description);
			}

			return (List<DLFileEntryType>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library file entry types before and after the current document library file entry type in the ordered set of document library file entry types that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param fileEntryTypeId the primary key of the current document library file entry type
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry type
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryType[] filterFindByG_N_D_PrevAndNext(
		long fileEntryTypeId, long groupId, String name, String description,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryTypeException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_D_PrevAndNext(fileEntryTypeId, groupId, name,
				description, orderByComparator);
		}

		DLFileEntryType dlFileEntryType = findByPrimaryKey(fileEntryTypeId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryType[] array = new DLFileEntryTypeImpl[3];

			array[0] = filterGetByG_N_D_PrevAndNext(session, dlFileEntryType,
					groupId, name, description, orderByComparator, true);

			array[1] = dlFileEntryType;

			array[2] = filterGetByG_N_D_PrevAndNext(session, dlFileEntryType,
					groupId, name, description, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntryType filterGetByG_N_D_PrevAndNext(Session session,
		DLFileEntryType dlFileEntryType, long groupId, String name,
		String description, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRYTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_G_N_D_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_N_D_NAME_2);
			}
		}

		if (description == null) {
			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
		}
		else {
			if (description.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntryType.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryTypeImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryTypeImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (name != null) {
			qPos.add(name);
		}

		if (description != null) {
			qPos.add(description);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(dlFileEntryType);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntryType> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entry types.
	 *
	 * @return the document library file entry types
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryType> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @return the range of document library file entry types
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryType> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file entry types
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryType> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<DLFileEntryType> list = (List<DLFileEntryType>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLFILEENTRYTYPE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILEENTRYTYPE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DLFileEntryType>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFileEntryType>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the document library file entry types where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (DLFileEntryType dlFileEntryType : findByGroupId(groupId)) {
			dlFileEntryTypePersistence.remove(dlFileEntryType);
		}
	}

	/**
	 * Removes all the document library file entry types where groupId = &#63; and name = &#63; and description = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_N_D(long groupId, String name, String description)
		throws SystemException {
		for (DLFileEntryType dlFileEntryType : findByG_N_D(groupId, name,
				description)) {
			dlFileEntryTypePersistence.remove(dlFileEntryType);
		}
	}

	/**
	 * Removes all the document library file entry types from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DLFileEntryType dlFileEntryType : findAll()) {
			dlFileEntryTypePersistence.remove(dlFileEntryType);
		}
	}

	/**
	 * Returns the number of document library file entry types where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching document library file entry types
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRYTYPE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

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
	 * Returns the number of document library file entry types that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching document library file entry types that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_DLFILEENTRYTYPE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntryType.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of document library file entry types where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @return the number of matching document library file entry types
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_N_D(long groupId, String name, String description)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name, description };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_N_D,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEENTRYTYPE_WHERE);

			query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_D_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_D_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_D_NAME_2);
				}
			}

			if (description == null) {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
			}
			else {
				if (description.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				if (description != null) {
					qPos.add(description);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_N_D,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entry types that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @return the number of matching document library file entry types that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_N_D(long groupId, String name, String description)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_N_D(groupId, name, description);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_DLFILEENTRYTYPE_WHERE);

		query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_G_N_D_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_N_D_NAME_2);
			}
		}

		if (description == null) {
			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
		}
		else {
			if (description.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntryType.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (name != null) {
				qPos.add(name);
			}

			if (description != null) {
				qPos.add(description);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of document library file entry types.
	 *
	 * @return the number of document library file entry types
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLFILEENTRYTYPE);

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
	 * Returns all the document library folders associated with the document library file entry type.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @return the document library folders associated with the document library file entry type
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFolders(
		long pk) throws SystemException {
		return getDLFolders(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the document library folders associated with the document library file entry type.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @return the range of document library folders associated with the document library file entry type
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFolders(
		long pk, int start, int end) throws SystemException {
		return getDLFolders(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_DLFOLDERS = new FinderPath(com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryTypeModelImpl.FINDER_CACHE_ENABLED_DLFILEENTRYTYPES_DLFOLDERS,
			com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl.class,
			DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME,
			"getDLFolders",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	/**
	 * Returns an ordered range of all the document library folders associated with the document library file entry type.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library folders associated with the document library file entry type
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFolders(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portlet.documentlibrary.model.DLFolder> list = (List<com.liferay.portlet.documentlibrary.model.DLFolder>)FinderCacheUtil.getResult(FINDER_PATH_GET_DLFOLDERS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETDLFOLDERS.concat(ORDER_BY_CLAUSE)
										   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETDLFOLDERS.concat(com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("DLFolder",
					com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.documentlibrary.model.DLFolder>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_DLFOLDERS,
						finderArgs);
				}
				else {
					dlFolderPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_DLFOLDERS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_DLFOLDERS_SIZE = new FinderPath(com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryTypeModelImpl.FINDER_CACHE_ENABLED_DLFILEENTRYTYPES_DLFOLDERS,
			Long.class,
			DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME,
			"getDLFoldersSize", new String[] { Long.class.getName() });

	/**
	 * Returns the number of document library folders associated with the document library file entry type.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @return the number of document library folders associated with the document library file entry type
	 * @throws SystemException if a system exception occurred
	 */
	public int getDLFoldersSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_DLFOLDERS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETDLFOLDERSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_DLFOLDERS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_DLFOLDER = new FinderPath(com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryTypeModelImpl.FINDER_CACHE_ENABLED_DLFILEENTRYTYPES_DLFOLDERS,
			Boolean.class,
			DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME,
			"containsDLFolder",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the document library folder is associated with the document library file entry type.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolderPK the primary key of the document library folder
	 * @return <code>true</code> if the document library folder is associated with the document library file entry type; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsDLFolder(long pk, long dlFolderPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, dlFolderPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_DLFOLDER,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsDLFolder.contains(pk, dlFolderPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_DLFOLDER,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the document library file entry type has any document library folders associated with it.
	 *
	 * @param pk the primary key of the document library file entry type to check for associations with document library folders
	 * @return <code>true</code> if the document library file entry type has any document library folders associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsDLFolders(long pk) throws SystemException {
		if (getDLFoldersSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the document library file entry type and the document library folder. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolderPK the primary key of the document library folder
	 * @throws SystemException if a system exception occurred
	 */
	public void addDLFolder(long pk, long dlFolderPK) throws SystemException {
		try {
			addDLFolder.add(pk, dlFolderPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Adds an association between the document library file entry type and the document library folder. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolder the document library folder
	 * @throws SystemException if a system exception occurred
	 */
	public void addDLFolder(long pk,
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws SystemException {
		try {
			addDLFolder.add(pk, dlFolder.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Adds an association between the document library file entry type and the document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolderPKs the primary keys of the document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public void addDLFolders(long pk, long[] dlFolderPKs)
		throws SystemException {
		try {
			for (long dlFolderPK : dlFolderPKs) {
				addDLFolder.add(pk, dlFolderPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Adds an association between the document library file entry type and the document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolders the document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public void addDLFolders(long pk,
		List<com.liferay.portlet.documentlibrary.model.DLFolder> dlFolders)
		throws SystemException {
		try {
			for (com.liferay.portlet.documentlibrary.model.DLFolder dlFolder : dlFolders) {
				addDLFolder.add(pk, dlFolder.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Clears all associations between the document library file entry type and its document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type to clear the associated document library folders from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearDLFolders(long pk) throws SystemException {
		try {
			clearDLFolders.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Removes the association between the document library file entry type and the document library folder. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolderPK the primary key of the document library folder
	 * @throws SystemException if a system exception occurred
	 */
	public void removeDLFolder(long pk, long dlFolderPK)
		throws SystemException {
		try {
			removeDLFolder.remove(pk, dlFolderPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Removes the association between the document library file entry type and the document library folder. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolder the document library folder
	 * @throws SystemException if a system exception occurred
	 */
	public void removeDLFolder(long pk,
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws SystemException {
		try {
			removeDLFolder.remove(pk, dlFolder.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Removes the association between the document library file entry type and the document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolderPKs the primary keys of the document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public void removeDLFolders(long pk, long[] dlFolderPKs)
		throws SystemException {
		try {
			for (long dlFolderPK : dlFolderPKs) {
				removeDLFolder.remove(pk, dlFolderPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Removes the association between the document library file entry type and the document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolders the document library folders
	 * @throws SystemException if a system exception occurred
	 */
	public void removeDLFolders(long pk,
		List<com.liferay.portlet.documentlibrary.model.DLFolder> dlFolders)
		throws SystemException {
		try {
			for (com.liferay.portlet.documentlibrary.model.DLFolder dlFolder : dlFolders) {
				removeDLFolder.remove(pk, dlFolder.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Sets the document library folders associated with the document library file entry type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolderPKs the primary keys of the document library folders to be associated with the document library file entry type
	 * @throws SystemException if a system exception occurred
	 */
	public void setDLFolders(long pk, long[] dlFolderPKs)
		throws SystemException {
		try {
			Set<Long> dlFolderPKSet = SetUtil.fromArray(dlFolderPKs);

			List<com.liferay.portlet.documentlibrary.model.DLFolder> dlFolders = getDLFolders(pk);

			for (com.liferay.portlet.documentlibrary.model.DLFolder dlFolder : dlFolders) {
				if (!dlFolderPKSet.remove(dlFolder.getPrimaryKey())) {
					removeDLFolder.remove(pk, dlFolder.getPrimaryKey());
				}
			}

			for (Long dlFolderPK : dlFolderPKSet) {
				addDLFolder.add(pk, dlFolderPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Sets the document library folders associated with the document library file entry type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolders the document library folders to be associated with the document library file entry type
	 * @throws SystemException if a system exception occurred
	 */
	public void setDLFolders(long pk,
		List<com.liferay.portlet.documentlibrary.model.DLFolder> dlFolders)
		throws SystemException {
		try {
			long[] dlFolderPKs = new long[dlFolders.size()];

			for (int i = 0; i < dlFolders.size(); i++) {
				com.liferay.portlet.documentlibrary.model.DLFolder dlFolder = dlFolders.get(i);

				dlFolderPKs[i] = dlFolder.getPrimaryKey();
			}

			setDLFolders(pk, dlFolderPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME);
		}
	}

	/**
	 * Returns all the d d m structures associated with the document library file entry type.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @return the d d m structures associated with the document library file entry type
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long pk) throws SystemException {
		return getDDMStructures(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the d d m structures associated with the document library file entry type.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @return the range of d d m structures associated with the document library file entry type
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long pk, int start, int end) throws SystemException {
		return getDDMStructures(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_DDMSTRUCTURES = new FinderPath(com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryTypeModelImpl.FINDER_CACHE_ENABLED_DLFILEENTRYTYPES_DDMSTRUCTURES,
			com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImpl.class,
			DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_NAME,
			"getDDMStructures",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	/**
	 * Returns an ordered range of all the d d m structures associated with the document library file entry type.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m structures associated with the document library file entry type
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> list = (List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>)FinderCacheUtil.getResult(FINDER_PATH_GET_DDMSTRUCTURES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETDDMSTRUCTURES.concat(ORDER_BY_CLAUSE)
											   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETDDMSTRUCTURES;
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("DDMStructure",
					com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_DDMSTRUCTURES,
						finderArgs);
				}
				else {
					ddmStructurePersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_DDMSTRUCTURES,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_DDMSTRUCTURES_SIZE = new FinderPath(com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryTypeModelImpl.FINDER_CACHE_ENABLED_DLFILEENTRYTYPES_DDMSTRUCTURES,
			Long.class,
			DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_NAME,
			"getDDMStructuresSize", new String[] { Long.class.getName() });

	/**
	 * Returns the number of d d m structures associated with the document library file entry type.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @return the number of d d m structures associated with the document library file entry type
	 * @throws SystemException if a system exception occurred
	 */
	public int getDDMStructuresSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_DDMSTRUCTURES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETDDMSTRUCTURESSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_DDMSTRUCTURES_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_DDMSTRUCTURE = new FinderPath(com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryTypeModelImpl.FINDER_CACHE_ENABLED_DLFILEENTRYTYPES_DDMSTRUCTURES,
			Boolean.class,
			DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_NAME,
			"containsDDMStructure",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the d d m structure is associated with the document library file entry type.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param ddmStructurePK the primary key of the d d m structure
	 * @return <code>true</code> if the d d m structure is associated with the document library file entry type; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsDDMStructure(long pk, long ddmStructurePK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, ddmStructurePK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_DDMSTRUCTURE,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsDDMStructure.contains(pk,
							ddmStructurePK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_DDMSTRUCTURE,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the document library file entry type has any d d m structures associated with it.
	 *
	 * @param pk the primary key of the document library file entry type to check for associations with d d m structures
	 * @return <code>true</code> if the document library file entry type has any d d m structures associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsDDMStructures(long pk) throws SystemException {
		if (getDDMStructuresSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the document library file entry type and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param ddmStructurePK the primary key of the d d m structure
	 * @throws SystemException if a system exception occurred
	 */
	public void addDDMStructure(long pk, long ddmStructurePK)
		throws SystemException {
		try {
			addDDMStructure.add(pk, ddmStructurePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_NAME);
		}
	}

	/**
	 * Adds an association between the document library file entry type and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param ddmStructure the d d m structure
	 * @throws SystemException if a system exception occurred
	 */
	public void addDDMStructure(long pk,
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws SystemException {
		try {
			addDDMStructure.add(pk, ddmStructure.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_NAME);
		}
	}

	/**
	 * Adds an association between the document library file entry type and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param ddmStructurePKs the primary keys of the d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public void addDDMStructures(long pk, long[] ddmStructurePKs)
		throws SystemException {
		try {
			for (long ddmStructurePK : ddmStructurePKs) {
				addDDMStructure.add(pk, ddmStructurePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_NAME);
		}
	}

	/**
	 * Adds an association between the document library file entry type and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param ddmStructures the d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public void addDDMStructures(long pk,
		List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures)
		throws SystemException {
		try {
			for (com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure : ddmStructures) {
				addDDMStructure.add(pk, ddmStructure.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_NAME);
		}
	}

	/**
	 * Clears all associations between the document library file entry type and its d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type to clear the associated d d m structures from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearDDMStructures(long pk) throws SystemException {
		try {
			clearDDMStructures.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_NAME);
		}
	}

	/**
	 * Removes the association between the document library file entry type and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param ddmStructurePK the primary key of the d d m structure
	 * @throws SystemException if a system exception occurred
	 */
	public void removeDDMStructure(long pk, long ddmStructurePK)
		throws SystemException {
		try {
			removeDDMStructure.remove(pk, ddmStructurePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_NAME);
		}
	}

	/**
	 * Removes the association between the document library file entry type and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param ddmStructure the d d m structure
	 * @throws SystemException if a system exception occurred
	 */
	public void removeDDMStructure(long pk,
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws SystemException {
		try {
			removeDDMStructure.remove(pk, ddmStructure.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_NAME);
		}
	}

	/**
	 * Removes the association between the document library file entry type and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param ddmStructurePKs the primary keys of the d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public void removeDDMStructures(long pk, long[] ddmStructurePKs)
		throws SystemException {
		try {
			for (long ddmStructurePK : ddmStructurePKs) {
				removeDDMStructure.remove(pk, ddmStructurePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_NAME);
		}
	}

	/**
	 * Removes the association between the document library file entry type and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param ddmStructures the d d m structures
	 * @throws SystemException if a system exception occurred
	 */
	public void removeDDMStructures(long pk,
		List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures)
		throws SystemException {
		try {
			for (com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure : ddmStructures) {
				removeDDMStructure.remove(pk, ddmStructure.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_NAME);
		}
	}

	/**
	 * Sets the d d m structures associated with the document library file entry type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param ddmStructurePKs the primary keys of the d d m structures to be associated with the document library file entry type
	 * @throws SystemException if a system exception occurred
	 */
	public void setDDMStructures(long pk, long[] ddmStructurePKs)
		throws SystemException {
		try {
			Set<Long> ddmStructurePKSet = SetUtil.fromArray(ddmStructurePKs);

			List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures =
				getDDMStructures(pk);

			for (com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure : ddmStructures) {
				if (!ddmStructurePKSet.remove(ddmStructure.getPrimaryKey())) {
					removeDDMStructure.remove(pk, ddmStructure.getPrimaryKey());
				}
			}

			for (Long ddmStructurePK : ddmStructurePKSet) {
				addDDMStructure.add(pk, ddmStructurePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_NAME);
		}
	}

	/**
	 * Sets the d d m structures associated with the document library file entry type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param ddmStructures the d d m structures to be associated with the document library file entry type
	 * @throws SystemException if a system exception occurred
	 */
	public void setDDMStructures(long pk,
		List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures)
		throws SystemException {
		try {
			long[] ddmStructurePKs = new long[ddmStructures.size()];

			for (int i = 0; i < ddmStructures.size(); i++) {
				com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure =
					ddmStructures.get(i);

				ddmStructurePKs[i] = ddmStructure.getPrimaryKey();
			}

			setDDMStructures(pk, ddmStructurePKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(DLFileEntryTypeModelImpl.MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_NAME);
		}
	}

	/**
	 * Initializes the document library file entry type persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileEntryType")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFileEntryType>> listenersList = new ArrayList<ModelListener<DLFileEntryType>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFileEntryType>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsDLFolder = new ContainsDLFolder(this);

		addDLFolder = new AddDLFolder(this);
		clearDLFolders = new ClearDLFolders(this);
		removeDLFolder = new RemoveDLFolder(this);

		containsDDMStructure = new ContainsDDMStructure(this);

		addDDMStructure = new AddDDMStructure(this);
		clearDDMStructures = new ClearDDMStructures(this);
		removeDDMStructure = new RemoveDDMStructure(this);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(DLFileEntryTypeImpl.class.getName());
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
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	@BeanReference(type = DDMStructurePersistence.class)
	protected DDMStructurePersistence ddmStructurePersistence;
	protected ContainsDLFolder containsDLFolder;
	protected AddDLFolder addDLFolder;
	protected ClearDLFolders clearDLFolders;
	protected RemoveDLFolder removeDLFolder;
	protected ContainsDDMStructure containsDDMStructure;
	protected AddDDMStructure addDDMStructure;
	protected ClearDDMStructures clearDDMStructures;
	protected RemoveDDMStructure removeDDMStructure;

	protected class ContainsDLFolder {
		protected ContainsDLFolder(
			DLFileEntryTypePersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSDLFOLDER,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long fileEntryTypeId, long folderId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(fileEntryTypeId), new Long(folderId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddDLFolder {
		protected AddDLFolder(DLFileEntryTypePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO DLFileEntryTypes_DLFolders (fileEntryTypeId, folderId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long fileEntryTypeId, long folderId)
			throws SystemException {
			if (!_persistenceImpl.containsDLFolder.contains(fileEntryTypeId,
						folderId)) {
				ModelListener<com.liferay.portlet.documentlibrary.model.DLFolder>[] dlFolderListeners =
					dlFolderPersistence.getListeners();

				for (ModelListener<DLFileEntryType> listener : listeners) {
					listener.onBeforeAddAssociation(fileEntryTypeId,
						com.liferay.portlet.documentlibrary.model.DLFolder.class.getName(),
						folderId);
				}

				for (ModelListener<com.liferay.portlet.documentlibrary.model.DLFolder> listener : dlFolderListeners) {
					listener.onBeforeAddAssociation(folderId,
						DLFileEntryType.class.getName(), fileEntryTypeId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(fileEntryTypeId), new Long(folderId)
					});

				for (ModelListener<DLFileEntryType> listener : listeners) {
					listener.onAfterAddAssociation(fileEntryTypeId,
						com.liferay.portlet.documentlibrary.model.DLFolder.class.getName(),
						folderId);
				}

				for (ModelListener<com.liferay.portlet.documentlibrary.model.DLFolder> listener : dlFolderListeners) {
					listener.onAfterAddAssociation(folderId,
						DLFileEntryType.class.getName(), fileEntryTypeId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private DLFileEntryTypePersistenceImpl _persistenceImpl;
	}

	protected class ClearDLFolders {
		protected ClearDLFolders(DLFileEntryTypePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM DLFileEntryTypes_DLFolders WHERE fileEntryTypeId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long fileEntryTypeId) throws SystemException {
			ModelListener<com.liferay.portlet.documentlibrary.model.DLFolder>[] dlFolderListeners =
				dlFolderPersistence.getListeners();

			List<com.liferay.portlet.documentlibrary.model.DLFolder> dlFolders = null;

			if ((listeners.length > 0) || (dlFolderListeners.length > 0)) {
				dlFolders = getDLFolders(fileEntryTypeId);

				for (com.liferay.portlet.documentlibrary.model.DLFolder dlFolder : dlFolders) {
					for (ModelListener<DLFileEntryType> listener : listeners) {
						listener.onBeforeRemoveAssociation(fileEntryTypeId,
							com.liferay.portlet.documentlibrary.model.DLFolder.class.getName(),
							dlFolder.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.documentlibrary.model.DLFolder> listener : dlFolderListeners) {
						listener.onBeforeRemoveAssociation(dlFolder.getPrimaryKey(),
							DLFileEntryType.class.getName(), fileEntryTypeId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(fileEntryTypeId) });

			if ((listeners.length > 0) || (dlFolderListeners.length > 0)) {
				for (com.liferay.portlet.documentlibrary.model.DLFolder dlFolder : dlFolders) {
					for (ModelListener<DLFileEntryType> listener : listeners) {
						listener.onAfterRemoveAssociation(fileEntryTypeId,
							com.liferay.portlet.documentlibrary.model.DLFolder.class.getName(),
							dlFolder.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.documentlibrary.model.DLFolder> listener : dlFolderListeners) {
						listener.onAfterRemoveAssociation(dlFolder.getPrimaryKey(),
							DLFileEntryType.class.getName(), fileEntryTypeId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveDLFolder {
		protected RemoveDLFolder(DLFileEntryTypePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM DLFileEntryTypes_DLFolders WHERE fileEntryTypeId = ? AND folderId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long fileEntryTypeId, long folderId)
			throws SystemException {
			if (_persistenceImpl.containsDLFolder.contains(fileEntryTypeId,
						folderId)) {
				ModelListener<com.liferay.portlet.documentlibrary.model.DLFolder>[] dlFolderListeners =
					dlFolderPersistence.getListeners();

				for (ModelListener<DLFileEntryType> listener : listeners) {
					listener.onBeforeRemoveAssociation(fileEntryTypeId,
						com.liferay.portlet.documentlibrary.model.DLFolder.class.getName(),
						folderId);
				}

				for (ModelListener<com.liferay.portlet.documentlibrary.model.DLFolder> listener : dlFolderListeners) {
					listener.onBeforeRemoveAssociation(folderId,
						DLFileEntryType.class.getName(), fileEntryTypeId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(fileEntryTypeId), new Long(folderId)
					});

				for (ModelListener<DLFileEntryType> listener : listeners) {
					listener.onAfterRemoveAssociation(fileEntryTypeId,
						com.liferay.portlet.documentlibrary.model.DLFolder.class.getName(),
						folderId);
				}

				for (ModelListener<com.liferay.portlet.documentlibrary.model.DLFolder> listener : dlFolderListeners) {
					listener.onAfterRemoveAssociation(folderId,
						DLFileEntryType.class.getName(), fileEntryTypeId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private DLFileEntryTypePersistenceImpl _persistenceImpl;
	}

	protected class ContainsDDMStructure {
		protected ContainsDDMStructure(
			DLFileEntryTypePersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSDDMSTRUCTURE,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long fileEntryTypeId, long structureId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(fileEntryTypeId), new Long(structureId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddDDMStructure {
		protected AddDDMStructure(
			DLFileEntryTypePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO DLFileEntryTypes_DDMStructures (fileEntryTypeId, structureId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long fileEntryTypeId, long structureId)
			throws SystemException {
			if (!_persistenceImpl.containsDDMStructure.contains(
						fileEntryTypeId, structureId)) {
				ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>[] ddmStructureListeners =
					ddmStructurePersistence.getListeners();

				for (ModelListener<DLFileEntryType> listener : listeners) {
					listener.onBeforeAddAssociation(fileEntryTypeId,
						com.liferay.portlet.dynamicdatamapping.model.DDMStructure.class.getName(),
						structureId);
				}

				for (ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> listener : ddmStructureListeners) {
					listener.onBeforeAddAssociation(structureId,
						DLFileEntryType.class.getName(), fileEntryTypeId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(fileEntryTypeId), new Long(structureId)
					});

				for (ModelListener<DLFileEntryType> listener : listeners) {
					listener.onAfterAddAssociation(fileEntryTypeId,
						com.liferay.portlet.dynamicdatamapping.model.DDMStructure.class.getName(),
						structureId);
				}

				for (ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> listener : ddmStructureListeners) {
					listener.onAfterAddAssociation(structureId,
						DLFileEntryType.class.getName(), fileEntryTypeId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private DLFileEntryTypePersistenceImpl _persistenceImpl;
	}

	protected class ClearDDMStructures {
		protected ClearDDMStructures(
			DLFileEntryTypePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM DLFileEntryTypes_DDMStructures WHERE fileEntryTypeId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long fileEntryTypeId) throws SystemException {
			ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>[] ddmStructureListeners =
				ddmStructurePersistence.getListeners();

			List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures =
				null;

			if ((listeners.length > 0) || (ddmStructureListeners.length > 0)) {
				ddmStructures = getDDMStructures(fileEntryTypeId);

				for (com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure : ddmStructures) {
					for (ModelListener<DLFileEntryType> listener : listeners) {
						listener.onBeforeRemoveAssociation(fileEntryTypeId,
							com.liferay.portlet.dynamicdatamapping.model.DDMStructure.class.getName(),
							ddmStructure.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> listener : ddmStructureListeners) {
						listener.onBeforeRemoveAssociation(ddmStructure.getPrimaryKey(),
							DLFileEntryType.class.getName(), fileEntryTypeId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(fileEntryTypeId) });

			if ((listeners.length > 0) || (ddmStructureListeners.length > 0)) {
				for (com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure : ddmStructures) {
					for (ModelListener<DLFileEntryType> listener : listeners) {
						listener.onAfterRemoveAssociation(fileEntryTypeId,
							com.liferay.portlet.dynamicdatamapping.model.DDMStructure.class.getName(),
							ddmStructure.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> listener : ddmStructureListeners) {
						listener.onAfterRemoveAssociation(ddmStructure.getPrimaryKey(),
							DLFileEntryType.class.getName(), fileEntryTypeId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveDDMStructure {
		protected RemoveDDMStructure(
			DLFileEntryTypePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM DLFileEntryTypes_DDMStructures WHERE fileEntryTypeId = ? AND structureId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long fileEntryTypeId, long structureId)
			throws SystemException {
			if (_persistenceImpl.containsDDMStructure.contains(
						fileEntryTypeId, structureId)) {
				ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>[] ddmStructureListeners =
					ddmStructurePersistence.getListeners();

				for (ModelListener<DLFileEntryType> listener : listeners) {
					listener.onBeforeRemoveAssociation(fileEntryTypeId,
						com.liferay.portlet.dynamicdatamapping.model.DDMStructure.class.getName(),
						structureId);
				}

				for (ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> listener : ddmStructureListeners) {
					listener.onBeforeRemoveAssociation(structureId,
						DLFileEntryType.class.getName(), fileEntryTypeId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(fileEntryTypeId), new Long(structureId)
					});

				for (ModelListener<DLFileEntryType> listener : listeners) {
					listener.onAfterRemoveAssociation(fileEntryTypeId,
						com.liferay.portlet.dynamicdatamapping.model.DDMStructure.class.getName(),
						structureId);
				}

				for (ModelListener<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> listener : ddmStructureListeners) {
					listener.onAfterRemoveAssociation(structureId,
						DLFileEntryType.class.getName(), fileEntryTypeId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private DLFileEntryTypePersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_SELECT_DLFILEENTRYTYPE = "SELECT dlFileEntryType FROM DLFileEntryType dlFileEntryType";
	private static final String _SQL_SELECT_DLFILEENTRYTYPE_WHERE = "SELECT dlFileEntryType FROM DLFileEntryType dlFileEntryType WHERE ";
	private static final String _SQL_COUNT_DLFILEENTRYTYPE = "SELECT COUNT(dlFileEntryType) FROM DLFileEntryType dlFileEntryType";
	private static final String _SQL_COUNT_DLFILEENTRYTYPE_WHERE = "SELECT COUNT(dlFileEntryType) FROM DLFileEntryType dlFileEntryType WHERE ";
	private static final String _SQL_GETDLFOLDERS = "SELECT {DLFolder.*} FROM DLFolder INNER JOIN DLFileEntryTypes_DLFolders ON (DLFileEntryTypes_DLFolders.folderId = DLFolder.folderId) WHERE (DLFileEntryTypes_DLFolders.fileEntryTypeId = ?)";
	private static final String _SQL_GETDLFOLDERSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM DLFileEntryTypes_DLFolders WHERE fileEntryTypeId = ?";
	private static final String _SQL_CONTAINSDLFOLDER = "SELECT COUNT(*) AS COUNT_VALUE FROM DLFileEntryTypes_DLFolders WHERE fileEntryTypeId = ? AND folderId = ?";
	private static final String _SQL_GETDDMSTRUCTURES = "SELECT {DDMStructure.*} FROM DDMStructure INNER JOIN DLFileEntryTypes_DDMStructures ON (DLFileEntryTypes_DDMStructures.structureId = DDMStructure.structureId) WHERE (DLFileEntryTypes_DDMStructures.fileEntryTypeId = ?)";
	private static final String _SQL_GETDDMSTRUCTURESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM DLFileEntryTypes_DDMStructures WHERE fileEntryTypeId = ?";
	private static final String _SQL_CONTAINSDDMSTRUCTURE = "SELECT COUNT(*) AS COUNT_VALUE FROM DLFileEntryTypes_DDMStructures WHERE fileEntryTypeId = ? AND structureId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "dlFileEntryType.groupId = ?";
	private static final String _FINDER_COLUMN_G_N_D_GROUPID_2 = "dlFileEntryType.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_D_NAME_1 = "dlFileEntryType.name IS NULL AND ";
	private static final String _FINDER_COLUMN_G_N_D_NAME_2 = "dlFileEntryType.name = ? AND ";
	private static final String _FINDER_COLUMN_G_N_D_NAME_3 = "(dlFileEntryType.name IS NULL OR dlFileEntryType.name = ?) AND ";
	private static final String _FINDER_COLUMN_G_N_D_DESCRIPTION_1 = "dlFileEntryType.description IS NULL";
	private static final String _FINDER_COLUMN_G_N_D_DESCRIPTION_2 = "dlFileEntryType.description = ?";
	private static final String _FINDER_COLUMN_G_N_D_DESCRIPTION_3 = "(dlFileEntryType.description IS NULL OR dlFileEntryType.description = ?)";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "dlFileEntryType.fileEntryTypeId";
	private static final String _FILTER_SQL_SELECT_DLFILEENTRYTYPE_WHERE = "SELECT DISTINCT {dlFileEntryType.*} FROM DLFileEntryType dlFileEntryType WHERE ";
	private static final String _FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {DLFileEntryType.*} FROM (SELECT DISTINCT dlFileEntryType.fileEntryTypeId FROM DLFileEntryType dlFileEntryType WHERE ";
	private static final String _FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN DLFileEntryType ON TEMP_TABLE.fileEntryTypeId = DLFileEntryType.fileEntryTypeId";
	private static final String _FILTER_SQL_COUNT_DLFILEENTRYTYPE_WHERE = "SELECT COUNT(DISTINCT dlFileEntryType.fileEntryTypeId) AS COUNT_VALUE FROM DLFileEntryType dlFileEntryType WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "dlFileEntryType";
	private static final String _FILTER_ENTITY_TABLE = "DLFileEntryType";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileEntryType.";
	private static final String _ORDER_BY_ENTITY_TABLE = "DLFileEntryType.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileEntryType exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileEntryType exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DLFileEntryTypePersistenceImpl.class);
	private static DLFileEntryType _nullDLFileEntryType = new DLFileEntryTypeImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DLFileEntryType> toCacheModel() {
				return _nullDLFileEntryTypeCacheModel;
			}
		};

	private static CacheModel<DLFileEntryType> _nullDLFileEntryTypeCacheModel = new CacheModel<DLFileEntryType>() {
			public DLFileEntryType toEntityModel() {
				return _nullDLFileEntryType;
			}
		};
}