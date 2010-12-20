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

package com.liferay.portlet.blogs.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
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
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.ImagePersistence;
import com.liferay.portal.service.persistence.OrganizationPersistence;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.SubscriptionPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagPersistence;
import com.liferay.portlet.blogs.NoSuchEntryException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.impl.BlogsEntryImpl;
import com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;
import com.liferay.portlet.social.service.persistence.SocialEquityLogPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * The persistence implementation for the blogs entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntryPersistence
 * @see BlogsEntryUtil
 * @generated
 */
public class BlogsEntryPersistenceImpl extends BasePersistenceImpl<BlogsEntry>
	implements BlogsEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link BlogsEntryUtil} to access the blogs entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = BlogsEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_U = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_U = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_LTD = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_LtD",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_LTD = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_LtD",
			new String[] { Long.class.getName(), Date.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_UT = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_UT",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_UT = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_UT",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_LTD = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_LtD",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_LTD = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_LtD",
			new String[] { Long.class.getName(), Date.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_U_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_U_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_C_LTD_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_LtD_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_LTD_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_LtD_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_U_LTD = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U_LtD",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_LTD = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U_LtD",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_U_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_LTD_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_LtD_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_LTD_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_LtD_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_U_LTD_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U_LtD_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_LTD_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U_LtD_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	/**
	 * Caches the blogs entry in the entity cache if it is enabled.
	 *
	 * @param blogsEntry the blogs entry to cache
	 */
	public void cacheResult(BlogsEntry blogsEntry) {
		EntityCacheUtil.putResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryImpl.class, blogsEntry.getPrimaryKey(), blogsEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { blogsEntry.getUuid(), new Long(
					blogsEntry.getGroupId()) }, blogsEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
			new Object[] {
				new Long(blogsEntry.getGroupId()),
				
			blogsEntry.getUrlTitle()
			}, blogsEntry);
	}

	/**
	 * Caches the blogs entries in the entity cache if it is enabled.
	 *
	 * @param blogsEntries the blogs entries to cache
	 */
	public void cacheResult(List<BlogsEntry> blogsEntries) {
		for (BlogsEntry blogsEntry : blogsEntries) {
			if (EntityCacheUtil.getResult(
						BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
						BlogsEntryImpl.class, blogsEntry.getPrimaryKey(), this) == null) {
				cacheResult(blogsEntry);
			}
		}
	}

	/**
	 * Clears the cache for all blogs entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(BlogsEntryImpl.class.getName());
		EntityCacheUtil.clearCache(BlogsEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the blogs entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(BlogsEntry blogsEntry) {
		EntityCacheUtil.removeResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryImpl.class, blogsEntry.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { blogsEntry.getUuid(), new Long(
					blogsEntry.getGroupId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_UT,
			new Object[] {
				new Long(blogsEntry.getGroupId()),
				
			blogsEntry.getUrlTitle()
			});
	}

	/**
	 * Creates a new blogs entry with the primary key. Does not add the blogs entry to the database.
	 *
	 * @param entryId the primary key for the new blogs entry
	 * @return the new blogs entry
	 */
	public BlogsEntry create(long entryId) {
		BlogsEntry blogsEntry = new BlogsEntryImpl();

		blogsEntry.setNew(true);
		blogsEntry.setPrimaryKey(entryId);

		String uuid = PortalUUIDUtil.generate();

		blogsEntry.setUuid(uuid);

		return blogsEntry;
	}

	/**
	 * Removes the blogs entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the blogs entry to remove
	 * @return the blogs entry that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the blogs entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the blogs entry to remove
	 * @return the blogs entry that was removed
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry remove(long entryId)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BlogsEntry blogsEntry = (BlogsEntry)session.get(BlogsEntryImpl.class,
					new Long(entryId));

			if (blogsEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryId);
				}

				throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					entryId);
			}

			return remove(blogsEntry);
		}
		catch (NoSuchEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry removeImpl(BlogsEntry blogsEntry)
		throws SystemException {
		blogsEntry = toUnwrappedModel(blogsEntry);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, blogsEntry);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		BlogsEntryModelImpl blogsEntryModelImpl = (BlogsEntryModelImpl)blogsEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				blogsEntryModelImpl.getOriginalUuid(),
				new Long(blogsEntryModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_UT,
			new Object[] {
				new Long(blogsEntryModelImpl.getOriginalGroupId()),
				
			blogsEntryModelImpl.getOriginalUrlTitle()
			});

		EntityCacheUtil.removeResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryImpl.class, blogsEntry.getPrimaryKey());

		return blogsEntry;
	}

	public BlogsEntry updateImpl(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry, boolean merge)
		throws SystemException {
		blogsEntry = toUnwrappedModel(blogsEntry);

		boolean isNew = blogsEntry.isNew();

		BlogsEntryModelImpl blogsEntryModelImpl = (BlogsEntryModelImpl)blogsEntry;

		if (Validator.isNull(blogsEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			blogsEntry.setUuid(uuid);
		}

		long userId = GetterUtil.getLong(PrincipalThreadLocal.getName());

		if (userId > 0) {
			long companyId = blogsEntry.getCompanyId();

			long groupId = blogsEntry.getGroupId();

			long entryId = 0;

			if (!isNew) {
				entryId = blogsEntry.getPrimaryKey();
			}

			try {
				blogsEntry.setTitle(SanitizerUtil.sanitize(companyId, groupId,
						userId,
						com.liferay.portlet.blogs.model.BlogsEntry.class.getName(),
						entryId, ContentTypes.TEXT_PLAIN, Sanitizer.MODE_ALL,
						blogsEntry.getTitle(), null));

				blogsEntry.setContent(SanitizerUtil.sanitize(companyId,
						groupId, userId,
						com.liferay.portlet.blogs.model.BlogsEntry.class.getName(),
						entryId, ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
						blogsEntry.getContent(), null));
			}
			catch (SanitizerException se) {
				throw new SystemException(se);
			}
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, blogsEntry, merge);

			blogsEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryImpl.class, blogsEntry.getPrimaryKey(), blogsEntry);

		if (!isNew &&
				(!Validator.equals(blogsEntry.getUuid(),
					blogsEntryModelImpl.getOriginalUuid()) ||
				(blogsEntry.getGroupId() != blogsEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					blogsEntryModelImpl.getOriginalUuid(),
					new Long(blogsEntryModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(blogsEntry.getUuid(),
					blogsEntryModelImpl.getOriginalUuid()) ||
				(blogsEntry.getGroupId() != blogsEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					blogsEntry.getUuid(), new Long(blogsEntry.getGroupId())
				}, blogsEntry);
		}

		if (!isNew &&
				((blogsEntry.getGroupId() != blogsEntryModelImpl.getOriginalGroupId()) ||
				!Validator.equals(blogsEntry.getUrlTitle(),
					blogsEntryModelImpl.getOriginalUrlTitle()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_UT,
				new Object[] {
					new Long(blogsEntryModelImpl.getOriginalGroupId()),
					
				blogsEntryModelImpl.getOriginalUrlTitle()
				});
		}

		if (isNew ||
				((blogsEntry.getGroupId() != blogsEntryModelImpl.getOriginalGroupId()) ||
				!Validator.equals(blogsEntry.getUrlTitle(),
					blogsEntryModelImpl.getOriginalUrlTitle()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
				new Object[] {
					new Long(blogsEntry.getGroupId()),
					
				blogsEntry.getUrlTitle()
				}, blogsEntry);
		}

		return blogsEntry;
	}

	protected BlogsEntry toUnwrappedModel(BlogsEntry blogsEntry) {
		if (blogsEntry instanceof BlogsEntryImpl) {
			return blogsEntry;
		}

		BlogsEntryImpl blogsEntryImpl = new BlogsEntryImpl();

		blogsEntryImpl.setNew(blogsEntry.isNew());
		blogsEntryImpl.setPrimaryKey(blogsEntry.getPrimaryKey());

		blogsEntryImpl.setUuid(blogsEntry.getUuid());
		blogsEntryImpl.setEntryId(blogsEntry.getEntryId());
		blogsEntryImpl.setGroupId(blogsEntry.getGroupId());
		blogsEntryImpl.setCompanyId(blogsEntry.getCompanyId());
		blogsEntryImpl.setUserId(blogsEntry.getUserId());
		blogsEntryImpl.setUserName(blogsEntry.getUserName());
		blogsEntryImpl.setCreateDate(blogsEntry.getCreateDate());
		blogsEntryImpl.setModifiedDate(blogsEntry.getModifiedDate());
		blogsEntryImpl.setTitle(blogsEntry.getTitle());
		blogsEntryImpl.setUrlTitle(blogsEntry.getUrlTitle());
		blogsEntryImpl.setDescription(blogsEntry.getDescription());
		blogsEntryImpl.setContent(blogsEntry.getContent());
		blogsEntryImpl.setDisplayDate(blogsEntry.getDisplayDate());
		blogsEntryImpl.setAllowPingbacks(blogsEntry.isAllowPingbacks());
		blogsEntryImpl.setAllowTrackbacks(blogsEntry.isAllowTrackbacks());
		blogsEntryImpl.setTrackbacks(blogsEntry.getTrackbacks());
		blogsEntryImpl.setSmallImage(blogsEntry.isSmallImage());
		blogsEntryImpl.setSmallImageId(blogsEntry.getSmallImageId());
		blogsEntryImpl.setSmallImageURL(blogsEntry.getSmallImageURL());
		blogsEntryImpl.setStatus(blogsEntry.getStatus());
		blogsEntryImpl.setStatusByUserId(blogsEntry.getStatusByUserId());
		blogsEntryImpl.setStatusByUserName(blogsEntry.getStatusByUserName());
		blogsEntryImpl.setStatusDate(blogsEntry.getStatusDate());

		return blogsEntryImpl;
	}

	/**
	 * Finds the blogs entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the blogs entry to find
	 * @return the blogs entry
	 * @throws com.liferay.portal.NoSuchModelException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the blogs entry with the primary key or throws a {@link com.liferay.portlet.blogs.NoSuchEntryException} if it could not be found.
	 *
	 * @param entryId the primary key of the blogs entry to find
	 * @return the blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = fetchByPrimaryKey(entryId);

		if (blogsEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryId);
			}

			throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				entryId);
		}

		return blogsEntry;
	}

	/**
	 * Finds the blogs entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the blogs entry to find
	 * @return the blogs entry, or <code>null</code> if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the blogs entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the blogs entry to find
	 * @return the blogs entry, or <code>null</code> if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry fetchByPrimaryKey(long entryId) throws SystemException {
		BlogsEntry blogsEntry = (BlogsEntry)EntityCacheUtil.getResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
				BlogsEntryImpl.class, entryId, this);

		if (blogsEntry == null) {
			Session session = null;

			try {
				session = openSession();

				blogsEntry = (BlogsEntry)session.get(BlogsEntryImpl.class,
						new Long(entryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (blogsEntry != null) {
					cacheResult(blogsEntry);
				}

				closeSession(session);
			}
		}

		return blogsEntry;
	}

	/**
	 * Finds all the blogs entries where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the blogs entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Finds an ordered range of all the blogs entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first blogs entry in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last blogs entry in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByUuid(uuid);

		List<BlogsEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the blogs entries before and after the current blogs entry in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry[] findByUuid_PrevAndNext(long entryId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, blogsEntry, uuid,
					orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByUuid_PrevAndNext(session, blogsEntry, uuid,
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

	protected BlogsEntry getByUuid_PrevAndNext(Session session,
		BlogsEntry blogsEntry, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
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

		else {
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the blogs entry where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.blogs.NoSuchEntryException} if it could not be found.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = fetchByUUID_G(uuid, groupId);

		if (blogsEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return blogsEntry;
	}

	/**
	 * Finds the blogs entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Finds the blogs entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<BlogsEntry> list = q.list();

				result = list;

				BlogsEntry blogsEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					blogsEntry = list.get(0);

					cacheResult(blogsEntry);

					if ((blogsEntry.getUuid() == null) ||
							!blogsEntry.getUuid().equals(uuid) ||
							(blogsEntry.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, blogsEntry);
					}
				}

				return blogsEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
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
				return (BlogsEntry)result;
			}
		}
	}

	/**
	 * Finds all the blogs entries where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the blogs entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the blogs entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Finds the first blogs entry in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last blogs entry in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByGroupId(groupId);

		List<BlogsEntry> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry[] findByGroupId_PrevAndNext(long entryId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, blogsEntry, groupId,
					orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByGroupId_PrevAndNext(session, blogsEntry, groupId,
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

	protected BlogsEntry getByGroupId_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the blogs entries where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the blogs entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByGroupId(long groupId, int start, int end)
		throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the blogs entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByGroupId(long groupId, int start,
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
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Finds all the blogs entries where companyId = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @return the matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Finds a range of all the blogs entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the blogs entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_COMPANYID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first blogs entry in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last blogs entry in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByCompanyId(companyId);

		List<BlogsEntry> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param companyId the company ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry[] findByCompanyId_PrevAndNext(long entryId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, blogsEntry,
					companyId, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByCompanyId_PrevAndNext(session, blogsEntry,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByCompanyId_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the blogs entries where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param userId the user ID to search with
	 * @return the matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByC_U(long companyId, long userId)
		throws SystemException {
		return findByC_U(companyId, userId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the blogs entries where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param userId the user ID to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByC_U(long companyId, long userId, int start,
		int end) throws SystemException {
		return findByC_U(companyId, userId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the blogs entries where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param userId the user ID to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByC_U(long companyId, long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, userId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_U,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_C_U,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_U,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param userId the user ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByC_U_First(long companyId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByC_U(companyId, userId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param userId the user ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByC_U_Last(long companyId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByC_U(companyId, userId);

		List<BlogsEntry> list = findByC_U(companyId, userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param companyId the company ID to search with
	 * @param userId the user ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry[] findByC_U_PrevAndNext(long entryId, long companyId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_U_PrevAndNext(session, blogsEntry, companyId,
					userId, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_U_PrevAndNext(session, blogsEntry, companyId,
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

	protected BlogsEntry getByC_U_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_U_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_U_USERID_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param displayDate the display date to search with
	 * @return the matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByC_LtD(long companyId, Date displayDate)
		throws SystemException {
		return findByC_LtD(companyId, displayDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param displayDate the display date to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByC_LtD(long companyId, Date displayDate,
		int start, int end) throws SystemException {
		return findByC_LtD(companyId, displayDate, start, end, null);
	}

	/**
	 * Finds an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param displayDate the display date to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByC_LtD(long companyId, Date displayDate,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, displayDate,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_LTD,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_LTD_COMPANYID_2);

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_C_LTD_DISPLAYDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_C_LTD_DISPLAYDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_C_LTD,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_LTD,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param displayDate the display date to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByC_LtD_First(long companyId, Date displayDate,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByC_LtD(companyId, displayDate, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param displayDate the display date to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByC_LtD_Last(long companyId, Date displayDate,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByC_LtD(companyId, displayDate);

		List<BlogsEntry> list = findByC_LtD(companyId, displayDate, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param companyId the company ID to search with
	 * @param displayDate the display date to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry[] findByC_LtD_PrevAndNext(long entryId, long companyId,
		Date displayDate, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_LtD_PrevAndNext(session, blogsEntry, companyId,
					displayDate, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_LtD_PrevAndNext(session, blogsEntry, companyId,
					displayDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByC_LtD_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, Date displayDate,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_LTD_COMPANYID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_C_LTD_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_C_LTD_DISPLAYDATE_2);
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

		else {
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (displayDate != null) {
			qPos.add(CalendarUtil.getTimestamp(displayDate));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the blogs entries where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param status the status to search with
	 * @return the matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByC_S(long companyId, int status)
		throws SystemException {
		return findByC_S(companyId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the blogs entries where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByC_S(long companyId, int status, int start,
		int end) throws SystemException {
		return findByC_S(companyId, status, start, end, null);
	}

	/**
	 * Finds an ordered range of all the blogs entries where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByC_S(long companyId, int status, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_S,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_C_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByC_S_First(long companyId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByC_S(companyId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByC_S_Last(long companyId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByC_S(companyId, status);

		List<BlogsEntry> list = findByC_S(companyId, status, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param companyId the company ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry[] findByC_S_PrevAndNext(long entryId, long companyId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_S_PrevAndNext(session, blogsEntry, companyId,
					status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_S_PrevAndNext(session, blogsEntry, companyId,
					status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByC_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the blogs entry where groupId = &#63; and urlTitle = &#63; or throws a {@link com.liferay.portlet.blogs.NoSuchEntryException} if it could not be found.
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @return the matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByG_UT(long groupId, String urlTitle)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = fetchByG_UT(groupId, urlTitle);

		if (blogsEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", urlTitle=");
			msg.append(urlTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return blogsEntry;
	}

	/**
	 * Finds the blogs entry where groupId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry fetchByG_UT(long groupId, String urlTitle)
		throws SystemException {
		return fetchByG_UT(groupId, urlTitle, true);
	}

	/**
	 * Finds the blogs entry where groupId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry fetchByG_UT(long groupId, String urlTitle,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, urlTitle };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_UT,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
			}
			else {
				if (urlTitle.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
				}
			}

			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (urlTitle != null) {
					qPos.add(urlTitle);
				}

				List<BlogsEntry> list = q.list();

				result = list;

				BlogsEntry blogsEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
						finderArgs, list);
				}
				else {
					blogsEntry = list.get(0);

					cacheResult(blogsEntry);

					if ((blogsEntry.getGroupId() != groupId) ||
							(blogsEntry.getUrlTitle() == null) ||
							!blogsEntry.getUrlTitle().equals(urlTitle)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
							finderArgs, blogsEntry);
					}
				}

				return blogsEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_UT,
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
				return (BlogsEntry)result;
			}
		}
	}

	/**
	 * Finds all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @return the matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_LtD(long groupId, Date displayDate)
		throws SystemException {
		return findByG_LtD(groupId, displayDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_LtD(long groupId, Date displayDate,
		int start, int end) throws SystemException {
		return findByG_LtD(groupId, displayDate, start, end, null);
	}

	/**
	 * Finds an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_LtD(long groupId, Date displayDate,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, displayDate,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_LTD,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_LTD_GROUPID_2);

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_LTD,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_LTD,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByG_LtD_First(long groupId, Date displayDate,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByG_LtD(groupId, displayDate, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByG_LtD_Last(long groupId, Date displayDate,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByG_LtD(groupId, displayDate);

		List<BlogsEntry> list = findByG_LtD(groupId, displayDate, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry[] findByG_LtD_PrevAndNext(long entryId, long groupId,
		Date displayDate, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_LtD_PrevAndNext(session, blogsEntry, groupId,
					displayDate, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_LtD_PrevAndNext(session, blogsEntry, groupId,
					displayDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_LtD_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, Date displayDate,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_LTD_GROUPID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_2);
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

		else {
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (displayDate != null) {
			qPos.add(CalendarUtil.getTimestamp(displayDate));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @return the matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_LtD(long groupId, Date displayDate)
		throws SystemException {
		return filterFindByG_LtD(groupId, displayDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_LtD(long groupId, Date displayDate,
		int start, int end) throws SystemException {
		return filterFindByG_LtD(groupId, displayDate, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_LtD(long groupId, Date displayDate,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LtD(groupId, displayDate, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LTD_GROUPID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (displayDate != null) {
				qPos.add(CalendarUtil.getTimestamp(displayDate));
			}

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Finds all the blogs entries where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @return the matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_S(long groupId, int status)
		throws SystemException {
		return findByG_S(groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Finds a range of all the blogs entries where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_S(long groupId, int status, int start,
		int end) throws SystemException {
		return findByG_S(groupId, status, start, end, null);
	}

	/**
	 * Finds an ordered range of all the blogs entries where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_S(long groupId, int status, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_S,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByG_S_First(long groupId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByG_S(groupId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByG_S_Last(long groupId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByG_S(groupId, status);

		List<BlogsEntry> list = findByG_S(groupId, status, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry[] findByG_S_PrevAndNext(long entryId, long groupId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_S_PrevAndNext(session, blogsEntry, groupId,
					status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_S_PrevAndNext(session, blogsEntry, groupId,
					status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the blogs entries where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @return the matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_S(long groupId, int status)
		throws SystemException {
		return filterFindByG_S(groupId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the blogs entries where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_S(long groupId, int status,
		int start, int end) throws SystemException {
		return filterFindByG_S(groupId, status, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the blogs entries where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_S(long groupId, int status,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S(groupId, status, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Finds all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @return the matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByC_U_S(long companyId, long userId, int status)
		throws SystemException {
		return findByC_U_S(companyId, userId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByC_U_S(long companyId, long userId,
		int status, int start, int end) throws SystemException {
		return findByC_U_S(companyId, userId, status, start, end, null);
	}

	/**
	 * Finds an ordered range of all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByC_U_S(long companyId, long userId,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, userId, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_U_S,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_U_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_S_USERID_2);

			query.append(_FINDER_COLUMN_C_U_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(status);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_C_U_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_U_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByC_U_S_First(long companyId, long userId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByC_U_S(companyId, userId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByC_U_S_Last(long companyId, long userId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByC_U_S(companyId, userId, status);

		List<BlogsEntry> list = findByC_U_S(companyId, userId, status,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param companyId the company ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry[] findByC_U_S_PrevAndNext(long entryId, long companyId,
		long userId, int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_U_S_PrevAndNext(session, blogsEntry, companyId,
					userId, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_U_S_PrevAndNext(session, blogsEntry, companyId,
					userId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByC_U_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, long userId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_U_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_U_S_USERID_2);

		query.append(_FINDER_COLUMN_C_U_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @return the matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByC_LtD_S(long companyId, Date displayDate,
		int status) throws SystemException {
		return findByC_LtD_S(companyId, displayDate, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByC_LtD_S(long companyId, Date displayDate,
		int status, int start, int end) throws SystemException {
		return findByC_LtD_S(companyId, displayDate, status, start, end, null);
	}

	/**
	 * Finds an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByC_LtD_S(long companyId, Date displayDate,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, displayDate, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_LTD_S,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_LTD_S_COMPANYID_2);

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_C_LTD_S_DISPLAYDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_C_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_C_LTD_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(status);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_C_LTD_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_LTD_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByC_LtD_S_First(long companyId, Date displayDate,
		int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByC_LtD_S(companyId, displayDate, status,
				0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByC_LtD_S_Last(long companyId, Date displayDate,
		int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByC_LtD_S(companyId, displayDate, status);

		List<BlogsEntry> list = findByC_LtD_S(companyId, displayDate, status,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param companyId the company ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry[] findByC_LtD_S_PrevAndNext(long entryId, long companyId,
		Date displayDate, int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_LtD_S_PrevAndNext(session, blogsEntry, companyId,
					displayDate, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_LtD_S_PrevAndNext(session, blogsEntry, companyId,
					displayDate, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByC_LtD_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, Date displayDate, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_LTD_S_COMPANYID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_C_LTD_S_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_C_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_C_LTD_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (displayDate != null) {
			qPos.add(CalendarUtil.getTimestamp(displayDate));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @return the matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_U_LtD(long groupId, long userId,
		Date displayDate) throws SystemException {
		return findByG_U_LtD(groupId, userId, displayDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_U_LtD(long groupId, long userId,
		Date displayDate, int start, int end) throws SystemException {
		return findByG_U_LtD(groupId, userId, displayDate, start, end, null);
	}

	/**
	 * Finds an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_U_LtD(long groupId, long userId,
		Date displayDate, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId, displayDate,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U_LTD,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_LTD_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_LTD_USERID_2);

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_U_LTD,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U_LTD,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByG_U_LtD_First(long groupId, long userId,
		Date displayDate, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByG_U_LtD(groupId, userId, displayDate, 0,
				1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByG_U_LtD_Last(long groupId, long userId,
		Date displayDate, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByG_U_LtD(groupId, userId, displayDate);

		List<BlogsEntry> list = findByG_U_LtD(groupId, userId, displayDate,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry[] findByG_U_LtD_PrevAndNext(long entryId, long groupId,
		long userId, Date displayDate, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_U_LtD_PrevAndNext(session, blogsEntry, groupId,
					userId, displayDate, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_U_LtD_PrevAndNext(session, blogsEntry, groupId,
					userId, displayDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_U_LtD_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, long userId, Date displayDate,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_LTD_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_USERID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_2);
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

		else {
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (displayDate != null) {
			qPos.add(CalendarUtil.getTimestamp(displayDate));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @return the matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_U_LtD(long groupId, long userId,
		Date displayDate) throws SystemException {
		return filterFindByG_U_LtD(groupId, userId, displayDate,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_U_LtD(long groupId, long userId,
		Date displayDate, int start, int end) throws SystemException {
		return filterFindByG_U_LtD(groupId, userId, displayDate, start, end,
			null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_U_LtD(long groupId, long userId,
		Date displayDate, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_LtD(groupId, userId, displayDate, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_USERID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (displayDate != null) {
				qPos.add(CalendarUtil.getTimestamp(displayDate));
			}

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Finds all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @return the matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_U_S(long groupId, long userId, int status)
		throws SystemException {
		return findByG_U_S(groupId, userId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_U_S(long groupId, long userId, int status,
		int start, int end) throws SystemException {
		return findByG_U_S(groupId, userId, status, start, end, null);
	}

	/**
	 * Finds an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_U_S(long groupId, long userId, int status,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U_S,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_S_USERID_2);

			query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(status);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_U_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByG_U_S_First(long groupId, long userId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByG_U_S(groupId, userId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByG_U_S_Last(long groupId, long userId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByG_U_S(groupId, userId, status);

		List<BlogsEntry> list = findByG_U_S(groupId, userId, status, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry[] findByG_U_S_PrevAndNext(long entryId, long groupId,
		long userId, int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_U_S_PrevAndNext(session, blogsEntry, groupId,
					userId, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_U_S_PrevAndNext(session, blogsEntry, groupId,
					userId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_U_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, long userId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @return the matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_U_S(long groupId, long userId,
		int status) throws SystemException {
		return filterFindByG_U_S(groupId, userId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_U_S(long groupId, long userId,
		int status, int start, int end) throws SystemException {
		return filterFindByG_U_S(groupId, userId, status, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_U_S(long groupId, long userId,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_S(groupId, userId, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(status);

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Finds all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @return the matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_LtD_S(long groupId, Date displayDate,
		int status) throws SystemException {
		return findByG_LtD_S(groupId, displayDate, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_LtD_S(long groupId, Date displayDate,
		int status, int start, int end) throws SystemException {
		return findByG_LtD_S(groupId, displayDate, status, start, end, null);
	}

	/**
	 * Finds an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_LtD_S(long groupId, Date displayDate,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, displayDate, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_LTD_S,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_LTD_S_GROUPID_2);

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_G_LTD_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(status);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_LTD_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_LTD_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByG_LtD_S_First(long groupId, Date displayDate,
		int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByG_LtD_S(groupId, displayDate, status, 0,
				1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByG_LtD_S_Last(long groupId, Date displayDate,
		int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByG_LtD_S(groupId, displayDate, status);

		List<BlogsEntry> list = findByG_LtD_S(groupId, displayDate, status,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry[] findByG_LtD_S_PrevAndNext(long entryId, long groupId,
		Date displayDate, int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_LtD_S_PrevAndNext(session, blogsEntry, groupId,
					displayDate, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_LtD_S_PrevAndNext(session, blogsEntry, groupId,
					displayDate, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_LtD_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, Date displayDate, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_LTD_S_GROUPID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_LTD_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (displayDate != null) {
			qPos.add(CalendarUtil.getTimestamp(displayDate));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @return the matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_LtD_S(long groupId, Date displayDate,
		int status) throws SystemException {
		return filterFindByG_LtD_S(groupId, displayDate, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_LtD_S(long groupId, Date displayDate,
		int status, int start, int end) throws SystemException {
		return filterFindByG_LtD_S(groupId, displayDate, status, start, end,
			null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_LtD_S(long groupId, Date displayDate,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LtD_S(groupId, displayDate, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LTD_S_GROUPID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_LTD_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (displayDate != null) {
				qPos.add(CalendarUtil.getTimestamp(displayDate));
			}

			qPos.add(status);

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Finds all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @return the matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_U_LtD_S(long groupId, long userId,
		Date displayDate, int status) throws SystemException {
		return findByG_U_LtD_S(groupId, userId, displayDate, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_U_LtD_S(long groupId, long userId,
		Date displayDate, int status, int start, int end)
		throws SystemException {
		return findByG_U_LtD_S(groupId, userId, displayDate, status, start,
			end, null);
	}

	/**
	 * Finds an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findByG_U_LtD_S(long groupId, long userId,
		Date displayDate, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId, displayDate, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U_LTD_S,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_LTD_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_LTD_S_USERID_2);

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_G_U_LTD_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(status);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_U_LTD_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U_LTD_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByG_U_LtD_S_First(long groupId, long userId,
		Date displayDate, int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByG_U_LtD_S(groupId, userId, displayDate,
				status, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry findByG_U_LtD_S_Last(long groupId, long userId,
		Date displayDate, int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByG_U_LtD_S(groupId, userId, displayDate, status);

		List<BlogsEntry> list = findByG_U_LtD_S(groupId, userId, displayDate,
				status, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsEntry[] findByG_U_LtD_S_PrevAndNext(long entryId, long groupId,
		long userId, Date displayDate, int status,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_U_LtD_S_PrevAndNext(session, blogsEntry, groupId,
					userId, displayDate, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_U_LtD_S_PrevAndNext(session, blogsEntry, groupId,
					userId, displayDate, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_U_LtD_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, long userId, Date displayDate,
		int status, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_LTD_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_S_USERID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (displayDate != null) {
			qPos.add(CalendarUtil.getTimestamp(displayDate));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @return the matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_U_LtD_S(long groupId, long userId,
		Date displayDate, int status) throws SystemException {
		return filterFindByG_U_LtD_S(groupId, userId, displayDate, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_U_LtD_S(long groupId, long userId,
		Date displayDate, int status, int start, int end)
		throws SystemException {
		return filterFindByG_U_LtD_S(groupId, userId, displayDate, status,
			start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> filterFindByG_U_LtD_S(long groupId, long userId,
		Date displayDate, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_LtD_S(groupId, userId, displayDate, status, start,
				end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_S_USERID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (displayDate != null) {
				qPos.add(CalendarUtil.getTimestamp(displayDate));
			}

			qPos.add(status);

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Finds all the blogs entries.
	 *
	 * @return the blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the blogs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @return the range of blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the blogs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of blogs entries to return
	 * @param end the upper bound of the range of blogs entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsEntry> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_BLOGSENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BLOGSENTRY.concat(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the blogs entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (BlogsEntry blogsEntry : findByUuid(uuid)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Removes the blogs entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByUUID_G(uuid, groupId);

		remove(blogsEntry);
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (BlogsEntry blogsEntry : findByGroupId(groupId)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Removes all the blogs entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (BlogsEntry blogsEntry : findByCompanyId(companyId)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Removes all the blogs entries where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID to search with
	 * @param userId the user ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_U(long companyId, long userId)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByC_U(companyId, userId)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Removes all the blogs entries where companyId = &#63; and displayDate &lt; &#63; from the database.
	 *
	 * @param companyId the company ID to search with
	 * @param displayDate the display date to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_LtD(long companyId, Date displayDate)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByC_LtD(companyId, displayDate)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Removes all the blogs entries where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID to search with
	 * @param status the status to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_S(long companyId, int status)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByC_S(companyId, status)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Removes the blogs entry where groupId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_UT(long groupId, String urlTitle)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByG_UT(groupId, urlTitle);

		remove(blogsEntry);
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; and displayDate &lt; &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_LtD(long groupId, Date displayDate)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByG_LtD(groupId, displayDate)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_S(long groupId, int status) throws SystemException {
		for (BlogsEntry blogsEntry : findByG_S(groupId, status)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Removes all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_U_S(long companyId, long userId, int status)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByC_U_S(companyId, userId, status)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Removes all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_LtD_S(long companyId, Date displayDate, int status)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByC_LtD_S(companyId, displayDate,
				status)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U_LtD(long groupId, long userId, Date displayDate)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByG_U_LtD(groupId, userId, displayDate)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U_S(long groupId, long userId, int status)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByG_U_S(groupId, userId, status)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_LtD_S(long groupId, Date displayDate, int status)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByG_LtD_S(groupId, displayDate, status)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U_LtD_S(long groupId, long userId, Date displayDate,
		int status) throws SystemException {
		for (BlogsEntry blogsEntry : findByG_U_LtD_S(groupId, userId,
				displayDate, status)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Removes all the blogs entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (BlogsEntry blogsEntry : findAll()) {
			remove(blogsEntry);
		}
	}

	/**
	 * Counts all the blogs entries where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the number of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

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

	/**
	 * Counts all the blogs entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the number of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the blogs entries where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the number of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

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
	 * Filters by the user's permissions and counts all the blogs entries where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the number of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

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
	 * Counts all the blogs entries where companyId = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @return the number of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

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

	/**
	 * Counts all the blogs entries where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param userId the user ID to search with
	 * @return the number of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_U(long companyId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

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

	/**
	 * Counts all the blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param displayDate the display date to search with
	 * @return the number of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_LtD(long companyId, Date displayDate)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, displayDate };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_LTD,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_LTD_COMPANYID_2);

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_C_LTD_DISPLAYDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_C_LTD_DISPLAYDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_LTD,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the blogs entries where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param status the status to search with
	 * @return the number of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_S(long companyId, int status) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the blogs entries where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @return the number of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_UT(long groupId, String urlTitle)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, urlTitle };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_UT,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
			}
			else {
				if (urlTitle.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (urlTitle != null) {
					qPos.add(urlTitle);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_UT,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @return the number of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_LtD(long groupId, Date displayDate)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, displayDate };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_LTD,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_LTD_GROUPID_2);

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_LTD,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Filters by the user's permissions and counts all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @return the number of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_LtD(long groupId, Date displayDate)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_LtD(groupId, displayDate);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_LTD_GROUPID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (displayDate != null) {
				qPos.add(CalendarUtil.getTimestamp(displayDate));
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
	 * Counts all the blogs entries where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @return the number of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_S(long groupId, int status) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

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
	 * Filters by the user's permissions and counts all the blogs entries where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @return the number of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_S(long groupId, int status)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_S(groupId, status);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

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
	 * Counts all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @return the number of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_U_S(long companyId, long userId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, userId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_U_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_U_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_S_USERID_2);

			query.append(_FINDER_COLUMN_C_U_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_U_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @return the number of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_LtD_S(long companyId, Date displayDate, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, displayDate, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_LTD_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_LTD_S_COMPANYID_2);

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_C_LTD_S_DISPLAYDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_C_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_C_LTD_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_LTD_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @return the number of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U_LtD(long groupId, long userId, Date displayDate)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId, displayDate };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U_LTD,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_LTD_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_LTD_USERID_2);

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U_LTD,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Filters by the user's permissions and counts all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @return the number of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_U_LtD(long groupId, long userId, Date displayDate)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_LtD(groupId, userId, displayDate);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_LTD_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_USERID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (displayDate != null) {
				qPos.add(CalendarUtil.getTimestamp(displayDate));
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
	 * Counts all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @return the number of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U_S(long groupId, long userId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_S_USERID_2);

			query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Filters by the user's permissions and counts all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param status the status to search with
	 * @return the number of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_U_S(long groupId, long userId, int status)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_S(groupId, userId, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(status);

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
	 * Counts all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @return the number of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_LtD_S(long groupId, Date displayDate, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, displayDate, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_LTD_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_LTD_S_GROUPID_2);

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_G_LTD_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_LTD_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Filters by the user's permissions and counts all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @return the number of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_LtD_S(long groupId, Date displayDate, int status)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_LtD_S(groupId, displayDate, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_LTD_S_GROUPID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_LTD_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (displayDate != null) {
				qPos.add(CalendarUtil.getTimestamp(displayDate));
			}

			qPos.add(status);

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
	 * Counts all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @return the number of matching blogs entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U_LtD_S(long groupId, long userId, Date displayDate,
		int status) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId, displayDate, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U_LTD_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_LTD_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_LTD_S_USERID_2);

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_G_U_LTD_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U_LTD_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Filters by the user's permissions and counts all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param displayDate the display date to search with
	 * @param status the status to search with
	 * @return the number of matching blogs entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_U_LtD_S(long groupId, long userId,
		Date displayDate, int status) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_LtD_S(groupId, userId, displayDate, status);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_LTD_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_S_USERID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (displayDate != null) {
				qPos.add(CalendarUtil.getTimestamp(displayDate));
			}

			qPos.add(status);

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
	 * Counts all the blogs entries.
	 *
	 * @return the number of blogs entries
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

				Query q = session.createQuery(_SQL_COUNT_BLOGSENTRY);

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
	 * Initializes the blogs entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.blogs.model.BlogsEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<BlogsEntry>> listenersList = new ArrayList<ModelListener<BlogsEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<BlogsEntry>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(BlogsEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = BlogsEntryPersistence.class)
	protected BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(type = BlogsStatsUserPersistence.class)
	protected BlogsStatsUserPersistence blogsStatsUserPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = RatingsStatsPersistence.class)
	protected RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialEquityLogPersistence.class)
	protected SocialEquityLogPersistence socialEquityLogPersistence;
	private static final String _SQL_SELECT_BLOGSENTRY = "SELECT blogsEntry FROM BlogsEntry blogsEntry";
	private static final String _SQL_SELECT_BLOGSENTRY_WHERE = "SELECT blogsEntry FROM BlogsEntry blogsEntry WHERE ";
	private static final String _SQL_COUNT_BLOGSENTRY = "SELECT COUNT(blogsEntry) FROM BlogsEntry blogsEntry";
	private static final String _SQL_COUNT_BLOGSENTRY_WHERE = "SELECT COUNT(blogsEntry) FROM BlogsEntry blogsEntry WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "blogsEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "blogsEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(blogsEntry.uuid IS NULL OR blogsEntry.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "blogsEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "blogsEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(blogsEntry.uuid IS NULL OR blogsEntry.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "blogsEntry.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "blogsEntry.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "blogsEntry.companyId = ?";
	private static final String _FINDER_COLUMN_C_U_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_USERID_2 = "blogsEntry.userId = ?";
	private static final String _FINDER_COLUMN_C_LTD_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_LTD_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL";
	private static final String _FINDER_COLUMN_C_LTD_DISPLAYDATE_2 = "blogsEntry.displayDate < ?";
	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_S_STATUS_2 = "blogsEntry.status = ?";
	private static final String _FINDER_COLUMN_G_UT_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_1 = "blogsEntry.urlTitle IS NULL";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_2 = "blogsEntry.urlTitle = ?";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_3 = "(blogsEntry.urlTitle IS NULL OR blogsEntry.urlTitle = ?)";
	private static final String _FINDER_COLUMN_G_LTD_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_LTD_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL";
	private static final String _FINDER_COLUMN_G_LTD_DISPLAYDATE_2 = "blogsEntry.displayDate < ?";
	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STATUS_2 = "blogsEntry.status = ?";
	private static final String _FINDER_COLUMN_C_U_S_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_S_USERID_2 = "blogsEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_S_STATUS_2 = "blogsEntry.status = ?";
	private static final String _FINDER_COLUMN_C_LTD_S_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_LTD_S_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL AND ";
	private static final String _FINDER_COLUMN_C_LTD_S_DISPLAYDATE_2 = "blogsEntry.displayDate < ? AND ";
	private static final String _FINDER_COLUMN_C_LTD_S_STATUS_2 = "blogsEntry.status = ?";
	private static final String _FINDER_COLUMN_G_U_LTD_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_LTD_USERID_2 = "blogsEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_LTD_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL";
	private static final String _FINDER_COLUMN_G_U_LTD_DISPLAYDATE_2 = "blogsEntry.displayDate < ?";
	private static final String _FINDER_COLUMN_G_U_S_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_S_USERID_2 = "blogsEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_S_STATUS_2 = "blogsEntry.status = ?";
	private static final String _FINDER_COLUMN_G_LTD_S_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_LTD_S_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL AND ";
	private static final String _FINDER_COLUMN_G_LTD_S_DISPLAYDATE_2 = "blogsEntry.displayDate < ? AND ";
	private static final String _FINDER_COLUMN_G_LTD_S_STATUS_2 = "blogsEntry.status = ?";
	private static final String _FINDER_COLUMN_G_U_LTD_S_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_LTD_S_USERID_2 = "blogsEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL AND ";
	private static final String _FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_2 = "blogsEntry.displayDate < ? AND ";
	private static final String _FINDER_COLUMN_G_U_LTD_S_STATUS_2 = "blogsEntry.status = ?";
	private static final String _FILTER_SQL_SELECT_BLOGSENTRY_WHERE = "SELECT DISTINCT {blogsEntry.*} FROM BlogsEntry blogsEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {BlogsEntry.*} FROM (SELECT DISTINCT blogsEntry.entryId FROM BlogsEntry blogsEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN BlogsEntry ON TEMP_TABLE.entryId = BlogsEntry.entryId";
	private static final String _FILTER_SQL_COUNT_BLOGSENTRY_WHERE = "SELECT COUNT(DISTINCT blogsEntry.entryId) AS COUNT_VALUE FROM BlogsEntry blogsEntry WHERE ";
	private static final String _FILTER_COLUMN_PK = "blogsEntry.entryId";
	private static final String _FILTER_COLUMN_USERID = "blogsEntry.userId";
	private static final String _FILTER_ENTITY_ALIAS = "blogsEntry";
	private static final String _FILTER_ENTITY_TABLE = "BlogsEntry";
	private static final String _ORDER_BY_ENTITY_ALIAS = "blogsEntry.";
	private static final String _ORDER_BY_ENTITY_TABLE = "BlogsEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No BlogsEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No BlogsEntry exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(BlogsEntryPersistenceImpl.class);
}