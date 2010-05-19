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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ImagePersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.WebDAVPropsPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="JournalTemplatePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalTemplatePersistence
 * @see       JournalTemplateUtil
 * @generated
 */
public class JournalTemplatePersistenceImpl extends BasePersistenceImpl<JournalTemplate>
	implements JournalTemplatePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = JournalTemplateImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_TEMPLATEID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByTemplateId",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TEMPLATEID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByTemplateId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_SMALLIMAGEID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchBySmallImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_SMALLIMAGEID = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countBySmallImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_T = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_S = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_S",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(JournalTemplate journalTemplate) {
		EntityCacheUtil.putResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateImpl.class, journalTemplate.getPrimaryKey(),
			journalTemplate);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalTemplate.getUuid(),
				new Long(journalTemplate.getGroupId())
			}, journalTemplate);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
			new Object[] { new Long(journalTemplate.getSmallImageId()) },
			journalTemplate);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
			new Object[] {
				new Long(journalTemplate.getGroupId()),
				
			journalTemplate.getTemplateId()
			}, journalTemplate);
	}

	public void cacheResult(List<JournalTemplate> journalTemplates) {
		for (JournalTemplate journalTemplate : journalTemplates) {
			if (EntityCacheUtil.getResult(
						JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
						JournalTemplateImpl.class,
						journalTemplate.getPrimaryKey(), this) == null) {
				cacheResult(journalTemplate);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(JournalTemplateImpl.class.getName());
		EntityCacheUtil.clearCache(JournalTemplateImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(JournalTemplate journalTemplate) {
		EntityCacheUtil.removeResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateImpl.class, journalTemplate.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalTemplate.getUuid(),
				new Long(journalTemplate.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
			new Object[] { new Long(journalTemplate.getSmallImageId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_T,
			new Object[] {
				new Long(journalTemplate.getGroupId()),
				
			journalTemplate.getTemplateId()
			});
	}

	public JournalTemplate create(long id) {
		JournalTemplate journalTemplate = new JournalTemplateImpl();

		journalTemplate.setNew(true);
		journalTemplate.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalTemplate.setUuid(uuid);

		return journalTemplate;
	}

	public JournalTemplate remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public JournalTemplate remove(long id)
		throws NoSuchTemplateException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalTemplate journalTemplate = (JournalTemplate)session.get(JournalTemplateImpl.class,
					new Long(id));

			if (journalTemplate == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
				}

				throw new NoSuchTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					id);
			}

			return remove(journalTemplate);
		}
		catch (NoSuchTemplateException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalTemplate remove(JournalTemplate journalTemplate)
		throws SystemException {
		for (ModelListener<JournalTemplate> listener : listeners) {
			listener.onBeforeRemove(journalTemplate);
		}

		journalTemplate = removeImpl(journalTemplate);

		for (ModelListener<JournalTemplate> listener : listeners) {
			listener.onAfterRemove(journalTemplate);
		}

		return journalTemplate;
	}

	protected JournalTemplate removeImpl(JournalTemplate journalTemplate)
		throws SystemException {
		journalTemplate = toUnwrappedModel(journalTemplate);

		Session session = null;

		try {
			session = openSession();

			if (journalTemplate.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(JournalTemplateImpl.class,
						journalTemplate.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(journalTemplate);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		JournalTemplateModelImpl journalTemplateModelImpl = (JournalTemplateModelImpl)journalTemplate;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalTemplateModelImpl.getOriginalUuid(),
				new Long(journalTemplateModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
			new Object[] {
				new Long(journalTemplateModelImpl.getOriginalSmallImageId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_T,
			new Object[] {
				new Long(journalTemplateModelImpl.getOriginalGroupId()),
				
			journalTemplateModelImpl.getOriginalTemplateId()
			});

		EntityCacheUtil.removeResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateImpl.class, journalTemplate.getPrimaryKey());

		return journalTemplate;
	}

	public JournalTemplate updateImpl(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate,
		boolean merge) throws SystemException {
		journalTemplate = toUnwrappedModel(journalTemplate);

		boolean isNew = journalTemplate.isNew();

		JournalTemplateModelImpl journalTemplateModelImpl = (JournalTemplateModelImpl)journalTemplate;

		if (Validator.isNull(journalTemplate.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalTemplate.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, journalTemplate, merge);

			journalTemplate.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
			JournalTemplateImpl.class, journalTemplate.getPrimaryKey(),
			journalTemplate);

		if (!isNew &&
				(!Validator.equals(journalTemplate.getUuid(),
					journalTemplateModelImpl.getOriginalUuid()) ||
				(journalTemplate.getGroupId() != journalTemplateModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalTemplateModelImpl.getOriginalUuid(),
					new Long(journalTemplateModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(journalTemplate.getUuid(),
					journalTemplateModelImpl.getOriginalUuid()) ||
				(journalTemplate.getGroupId() != journalTemplateModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalTemplate.getUuid(),
					new Long(journalTemplate.getGroupId())
				}, journalTemplate);
		}

		if (!isNew &&
				(journalTemplate.getSmallImageId() != journalTemplateModelImpl.getOriginalSmallImageId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
				new Object[] {
					new Long(journalTemplateModelImpl.getOriginalSmallImageId())
				});
		}

		if (isNew ||
				(journalTemplate.getSmallImageId() != journalTemplateModelImpl.getOriginalSmallImageId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
				new Object[] { new Long(journalTemplate.getSmallImageId()) },
				journalTemplate);
		}

		if (!isNew &&
				((journalTemplate.getGroupId() != journalTemplateModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalTemplate.getTemplateId(),
					journalTemplateModelImpl.getOriginalTemplateId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_T,
				new Object[] {
					new Long(journalTemplateModelImpl.getOriginalGroupId()),
					
				journalTemplateModelImpl.getOriginalTemplateId()
				});
		}

		if (isNew ||
				((journalTemplate.getGroupId() != journalTemplateModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalTemplate.getTemplateId(),
					journalTemplateModelImpl.getOriginalTemplateId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
				new Object[] {
					new Long(journalTemplate.getGroupId()),
					
				journalTemplate.getTemplateId()
				}, journalTemplate);
		}

		return journalTemplate;
	}

	protected JournalTemplate toUnwrappedModel(JournalTemplate journalTemplate) {
		if (journalTemplate instanceof JournalTemplateImpl) {
			return journalTemplate;
		}

		JournalTemplateImpl journalTemplateImpl = new JournalTemplateImpl();

		journalTemplateImpl.setNew(journalTemplate.isNew());
		journalTemplateImpl.setPrimaryKey(journalTemplate.getPrimaryKey());

		journalTemplateImpl.setUuid(journalTemplate.getUuid());
		journalTemplateImpl.setId(journalTemplate.getId());
		journalTemplateImpl.setGroupId(journalTemplate.getGroupId());
		journalTemplateImpl.setCompanyId(journalTemplate.getCompanyId());
		journalTemplateImpl.setUserId(journalTemplate.getUserId());
		journalTemplateImpl.setUserName(journalTemplate.getUserName());
		journalTemplateImpl.setCreateDate(journalTemplate.getCreateDate());
		journalTemplateImpl.setModifiedDate(journalTemplate.getModifiedDate());
		journalTemplateImpl.setTemplateId(journalTemplate.getTemplateId());
		journalTemplateImpl.setStructureId(journalTemplate.getStructureId());
		journalTemplateImpl.setName(journalTemplate.getName());
		journalTemplateImpl.setDescription(journalTemplate.getDescription());
		journalTemplateImpl.setXsl(journalTemplate.getXsl());
		journalTemplateImpl.setLangType(journalTemplate.getLangType());
		journalTemplateImpl.setCacheable(journalTemplate.isCacheable());
		journalTemplateImpl.setSmallImage(journalTemplate.isSmallImage());
		journalTemplateImpl.setSmallImageId(journalTemplate.getSmallImageId());
		journalTemplateImpl.setSmallImageURL(journalTemplate.getSmallImageURL());

		return journalTemplateImpl;
	}

	public JournalTemplate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalTemplate findByPrimaryKey(long id)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByPrimaryKey(id);

		if (journalTemplate == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
			}

			throw new NoSuchTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				id);
		}

		return journalTemplate;
	}

	public JournalTemplate fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalTemplate fetchByPrimaryKey(long id) throws SystemException {
		JournalTemplate journalTemplate = (JournalTemplate)EntityCacheUtil.getResult(JournalTemplateModelImpl.ENTITY_CACHE_ENABLED,
				JournalTemplateImpl.class, id, this);

		if (journalTemplate == null) {
			Session session = null;

			try {
				session = openSession();

				journalTemplate = (JournalTemplate)session.get(JournalTemplateImpl.class,
						new Long(id));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (journalTemplate != null) {
					cacheResult(journalTemplate);
				}

				closeSession(session);
			}
		}

		return journalTemplate;
	}

	public List<JournalTemplate> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalTemplate> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<JournalTemplate> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
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

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

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
					query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalTemplate>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalTemplate findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		List<JournalTemplate> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		int count = countByUuid(uuid);

		List<JournalTemplate> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate[] findByUuid_PrevAndNext(long id, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = getByUuid_PrevAndNext(session, journalTemplate, uuid,
					orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = getByUuid_PrevAndNext(session, journalTemplate, uuid,
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

	protected JournalTemplate getByUuid_PrevAndNext(Session session,
		JournalTemplate journalTemplate, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

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
			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public JournalTemplate findByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByUUID_G(uuid, groupId);

		if (journalTemplate == null) {
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

			throw new NoSuchTemplateException(msg.toString());
		}

		return journalTemplate;
	}

	public JournalTemplate fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public JournalTemplate fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

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

				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<JournalTemplate> list = q.list();

				result = list;

				JournalTemplate journalTemplate = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					journalTemplate = list.get(0);

					cacheResult(journalTemplate);

					if ((journalTemplate.getUuid() == null) ||
							!journalTemplate.getUuid().equals(uuid) ||
							(journalTemplate.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, journalTemplate);
					}
				}

				return journalTemplate;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<JournalTemplate>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalTemplate)result;
			}
		}
	}

	public List<JournalTemplate> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalTemplate> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<JournalTemplate> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalTemplate>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalTemplate findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		List<JournalTemplate> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		int count = countByGroupId(groupId);

		List<JournalTemplate> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate[] findByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, journalTemplate,
					groupId, orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = getByGroupId_PrevAndNext(session, journalTemplate,
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

	protected JournalTemplate getByGroupId_PrevAndNext(Session session,
		JournalTemplate journalTemplate, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

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
			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<JournalTemplate> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<JournalTemplate> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	public List<JournalTemplate> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					JournalTemplate.class.getName(), _FILTER_COLUMN_ID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_FILTER_ENTITY_ALIAS, JournalTemplateImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalTemplate> findByTemplateId(String templateId)
		throws SystemException {
		return findByTemplateId(templateId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<JournalTemplate> findByTemplateId(String templateId, int start,
		int end) throws SystemException {
		return findByTemplateId(templateId, start, end, null);
	}

	public List<JournalTemplate> findByTemplateId(String templateId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				templateId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TEMPLATEID,
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

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

				if (templateId == null) {
					query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1);
				}
				else {
					if (templateId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3);
					}
					else {
						query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (templateId != null) {
					qPos.add(templateId);
				}

				list = (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalTemplate>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TEMPLATEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalTemplate findByTemplateId_First(String templateId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		List<JournalTemplate> list = findByTemplateId(templateId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("templateId=");
			msg.append(templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate findByTemplateId_Last(String templateId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		int count = countByTemplateId(templateId);

		List<JournalTemplate> list = findByTemplateId(templateId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("templateId=");
			msg.append(templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate[] findByTemplateId_PrevAndNext(long id,
		String templateId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = getByTemplateId_PrevAndNext(session, journalTemplate,
					templateId, orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = getByTemplateId_PrevAndNext(session, journalTemplate,
					templateId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalTemplate getByTemplateId_PrevAndNext(Session session,
		JournalTemplate journalTemplate, String templateId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

		if (templateId == null) {
			query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1);
		}
		else {
			if (templateId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3);
			}
			else {
				query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);
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
			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (templateId != null) {
			qPos.add(templateId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public JournalTemplate findBySmallImageId(long smallImageId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchBySmallImageId(smallImageId);

		if (journalTemplate == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("smallImageId=");
			msg.append(smallImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateException(msg.toString());
		}

		return journalTemplate;
	}

	public JournalTemplate fetchBySmallImageId(long smallImageId)
		throws SystemException {
		return fetchBySmallImageId(smallImageId, true);
	}

	public JournalTemplate fetchBySmallImageId(long smallImageId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(smallImageId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				List<JournalTemplate> list = q.list();

				result = list;

				JournalTemplate journalTemplate = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
						finderArgs, list);
				}
				else {
					journalTemplate = list.get(0);

					cacheResult(journalTemplate);

					if ((journalTemplate.getSmallImageId() != smallImageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
							finderArgs, journalTemplate);
					}
				}

				return journalTemplate;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SMALLIMAGEID,
						finderArgs, new ArrayList<JournalTemplate>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalTemplate)result;
			}
		}
	}

	public JournalTemplate findByG_T(long groupId, String templateId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByG_T(groupId, templateId);

		if (journalTemplate == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", templateId=");
			msg.append(templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTemplateException(msg.toString());
		}

		return journalTemplate;
	}

	public JournalTemplate fetchByG_T(long groupId, String templateId)
		throws SystemException {
		return fetchByG_T(groupId, templateId, true);
	}

	public JournalTemplate fetchByG_T(long groupId, String templateId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), templateId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_T,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_G_T_GROUPID_2);

				if (templateId == null) {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEID_1);
				}
				else {
					if (templateId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_T_TEMPLATEID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_T_TEMPLATEID_2);
					}
				}

				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (templateId != null) {
					qPos.add(templateId);
				}

				List<JournalTemplate> list = q.list();

				result = list;

				JournalTemplate journalTemplate = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
						finderArgs, list);
				}
				else {
					journalTemplate = list.get(0);

					cacheResult(journalTemplate);

					if ((journalTemplate.getGroupId() != groupId) ||
							(journalTemplate.getTemplateId() == null) ||
							!journalTemplate.getTemplateId().equals(templateId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
							finderArgs, journalTemplate);
					}
				}

				return journalTemplate;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_T,
						finderArgs, new ArrayList<JournalTemplate>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalTemplate)result;
			}
		}
	}

	public List<JournalTemplate> findByG_S(long groupId, String structureId)
		throws SystemException {
		return findByG_S(groupId, structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<JournalTemplate> findByG_S(long groupId, String structureId,
		int start, int end) throws SystemException {
		return findByG_S(groupId, structureId, start, end, null);
	}

	public List<JournalTemplate> findByG_S(long groupId, String structureId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				structureId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(4 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_G_S_GROUPID_2);

				if (structureId == null) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
				}
				else {
					if (structureId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
				}

				list = (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalTemplate>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalTemplate findByG_S_First(long groupId, String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		List<JournalTemplate> list = findByG_S(groupId, structureId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate findByG_S_Last(long groupId, String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		int count = countByG_S(groupId, structureId);

		List<JournalTemplate> list = findByG_S(groupId, structureId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTemplateException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalTemplate[] findByG_S_PrevAndNext(long id, long groupId,
		String structureId, OrderByComparator orderByComparator)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalTemplate[] array = new JournalTemplateImpl[3];

			array[0] = getByG_S_PrevAndNext(session, journalTemplate, groupId,
					structureId, orderByComparator, true);

			array[1] = journalTemplate;

			array[2] = getByG_S_PrevAndNext(session, journalTemplate, groupId,
					structureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalTemplate getByG_S_PrevAndNext(Session session,
		JournalTemplate journalTemplate, long groupId, String structureId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALTEMPLATE_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		if (structureId == null) {
			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
		}
		else {
			if (structureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
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
			query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (structureId != null) {
			qPos.add(structureId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<JournalTemplate> filterFindByG_S(long groupId,
		String structureId) throws SystemException {
		return filterFindByG_S(groupId, structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<JournalTemplate> filterFindByG_S(long groupId,
		String structureId, int start, int end) throws SystemException {
		return filterFindByG_S(groupId, structureId, start, end, null);
	}

	public List<JournalTemplate> filterFindByG_S(long groupId,
		String structureId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_FILTER_SQL_SELECT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					JournalTemplate.class.getName(), _FILTER_COLUMN_ID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_FILTER_ENTITY_ALIAS, JournalTemplateImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (structureId != null) {
				qPos.add(structureId);
			}

			return (List<JournalTemplate>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalTemplate> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalTemplate> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<JournalTemplate> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalTemplate> list = (List<JournalTemplate>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_JOURNALTEMPLATE);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_JOURNALTEMPLATE.concat(JournalTemplateModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JournalTemplate>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalTemplate>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (JournalTemplate journalTemplate : findByUuid(uuid)) {
			remove(journalTemplate);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByUUID_G(uuid, groupId);

		remove(journalTemplate);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalTemplate journalTemplate : findByGroupId(groupId)) {
			remove(journalTemplate);
		}
	}

	public void removeByTemplateId(String templateId) throws SystemException {
		for (JournalTemplate journalTemplate : findByTemplateId(templateId)) {
			remove(journalTemplate);
		}
	}

	public void removeBySmallImageId(long smallImageId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findBySmallImageId(smallImageId);

		remove(journalTemplate);
	}

	public void removeByG_T(long groupId, String templateId)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByG_T(groupId, templateId);

		remove(journalTemplate);
	}

	public void removeByG_S(long groupId, String structureId)
		throws SystemException {
		for (JournalTemplate journalTemplate : findByG_S(groupId, structureId)) {
			remove(journalTemplate);
		}
	}

	public void removeAll() throws SystemException {
		for (JournalTemplate journalTemplate : findAll()) {
			remove(journalTemplate);
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

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

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

	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

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

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				String sql = query.toString();

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

	public int filterCountByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler(2);

			query.append(_FILTER_SQL_COUNT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					JournalTemplate.class.getName(), _FILTER_COLUMN_ID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

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

	public int countByTemplateId(String templateId) throws SystemException {
		Object[] finderArgs = new Object[] { templateId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TEMPLATEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

				if (templateId == null) {
					query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1);
				}
				else {
					if (templateId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3);
					}
					else {
						query.append(_FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (templateId != null) {
					qPos.add(templateId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TEMPLATEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countBySmallImageId(long smallImageId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(smallImageId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_T(long groupId, String templateId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), templateId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_G_T_GROUPID_2);

				if (templateId == null) {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEID_1);
				}
				else {
					if (templateId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_T_TEMPLATEID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_T_TEMPLATEID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (templateId != null) {
					qPos.add(templateId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_T, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int filterCountByG_T(long groupId, String templateId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler(3);

			query.append(_FILTER_SQL_COUNT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			if (templateId == null) {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_1);
			}
			else {
				if (templateId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEID_2);
				}
			}

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					JournalTemplate.class.getName(), _FILTER_COLUMN_ID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (templateId != null) {
				qPos.add(templateId);
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

	public int countByG_S(long groupId, String structureId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), structureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_JOURNALTEMPLATE_WHERE);

				query.append(_FINDER_COLUMN_G_S_GROUPID_2);

				if (structureId == null) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
				}
				else {
					if (structureId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int filterCountByG_S(long groupId, String structureId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler(3);

			query.append(_FILTER_SQL_COUNT_JOURNALTEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
				}
			}

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					JournalTemplate.class.getName(), _FILTER_COLUMN_ID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (structureId != null) {
				qPos.add(structureId);
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

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_JOURNALTEMPLATE);

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
						"value.object.listener.com.liferay.portlet.journal.model.JournalTemplate")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalTemplate>> listenersList = new ArrayList<ModelListener<JournalTemplate>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalTemplate>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = JournalArticlePersistence.class)
	protected JournalArticlePersistence journalArticlePersistence;
	@BeanReference(type = JournalArticleImagePersistence.class)
	protected JournalArticleImagePersistence journalArticleImagePersistence;
	@BeanReference(type = JournalArticleResourcePersistence.class)
	protected JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(type = JournalContentSearchPersistence.class)
	protected JournalContentSearchPersistence journalContentSearchPersistence;
	@BeanReference(type = JournalFeedPersistence.class)
	protected JournalFeedPersistence journalFeedPersistence;
	@BeanReference(type = JournalStructurePersistence.class)
	protected JournalStructurePersistence journalStructurePersistence;
	@BeanReference(type = JournalTemplatePersistence.class)
	protected JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	private static final String _SQL_SELECT_JOURNALTEMPLATE = "SELECT journalTemplate FROM JournalTemplate journalTemplate";
	private static final String _SQL_SELECT_JOURNALTEMPLATE_WHERE = "SELECT journalTemplate FROM JournalTemplate journalTemplate WHERE ";
	private static final String _SQL_COUNT_JOURNALTEMPLATE = "SELECT COUNT(journalTemplate) FROM JournalTemplate journalTemplate";
	private static final String _SQL_COUNT_JOURNALTEMPLATE_WHERE = "SELECT COUNT(journalTemplate) FROM JournalTemplate journalTemplate WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "journalTemplate.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "journalTemplate.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(journalTemplate.uuid IS NULL OR journalTemplate.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "journalTemplate.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "journalTemplate.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(journalTemplate.uuid IS NULL OR journalTemplate.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "journalTemplate.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "journalTemplate.groupId = ?";
	private static final String _FINDER_COLUMN_TEMPLATEID_TEMPLATEID_1 = "journalTemplate.templateId IS NULL";
	private static final String _FINDER_COLUMN_TEMPLATEID_TEMPLATEID_2 = "journalTemplate.templateId = ?";
	private static final String _FINDER_COLUMN_TEMPLATEID_TEMPLATEID_3 = "(journalTemplate.templateId IS NULL OR journalTemplate.templateId = ?)";
	private static final String _FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2 = "journalTemplate.smallImageId = ?";
	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "journalTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEID_1 = "journalTemplate.templateId IS NULL";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEID_2 = "journalTemplate.templateId = ?";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEID_3 = "(journalTemplate.templateId IS NULL OR journalTemplate.templateId = ?)";
	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "journalTemplate.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_1 = "journalTemplate.structureId IS NULL";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_2 = "journalTemplate.structureId = ?";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_3 = "(journalTemplate.structureId IS NULL OR journalTemplate.structureId = ?)";
	private static final String _FILTER_SQL_SELECT_JOURNALTEMPLATE_WHERE = "SELECT {journalTemplate.*} FROM JournalTemplate journalTemplate WHERE ";
	private static final String _FILTER_SQL_COUNT_JOURNALTEMPLATE_WHERE = "SELECT COUNT(journalTemplate.id) AS COUNT_VALUE FROM JournalTemplate journalTemplate WHERE ";
	private static final String _FILTER_COLUMN_ID = "journalTemplate.id";
	private static final String _FILTER_COLUMN_USERID = "journalTemplate.userId";
	private static final String _FILTER_ENTITY_ALIAS = "journalTemplate";
	private static final String _ORDER_BY_ENTITY_ALIAS = "journalTemplate.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JournalTemplate exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JournalTemplate exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(JournalTemplatePersistenceImpl.class);
}