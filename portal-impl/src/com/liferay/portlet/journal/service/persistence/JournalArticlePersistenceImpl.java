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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.NoSuchModelException;
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

import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="JournalArticlePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticlePersistence
 * @see       JournalArticleUtil
 * @generated
 */
public class JournalArticlePersistenceImpl extends BasePersistenceImpl<JournalArticle>
	implements JournalArticlePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = JournalArticleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_SMALLIMAGEID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findBySmallImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_SMALLIMAGEID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findBySmallImageId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_SMALLIMAGEID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countBySmallImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_R_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByR_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_R_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByR_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_R_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByR_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_A = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_A",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_A = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_A",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_S",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_S",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_T = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_T = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_UT = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_UT",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_UT = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_UT",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_UT = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_UT",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_A_V = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_A_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A_V = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_A_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_A_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_A_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_A_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_A_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_A_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_UT_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_UT_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_UT_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_UT_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_UT_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_UT_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(JournalArticle journalArticle) {
		EntityCacheUtil.putResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImpl.class, journalArticle.getPrimaryKey(),
			journalArticle);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalArticle.getUuid(), new Long(journalArticle.getGroupId())
			}, journalArticle);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V,
			new Object[] {
				new Long(journalArticle.getGroupId()),
				
			journalArticle.getArticleId(),
				new Double(journalArticle.getVersion())
			}, journalArticle);
	}

	public void cacheResult(List<JournalArticle> journalArticles) {
		for (JournalArticle journalArticle : journalArticles) {
			if (EntityCacheUtil.getResult(
						JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
						JournalArticleImpl.class,
						journalArticle.getPrimaryKey(), this) == null) {
				cacheResult(journalArticle);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(JournalArticleImpl.class.getName());
		EntityCacheUtil.clearCache(JournalArticleImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public JournalArticle create(long id) {
		JournalArticle journalArticle = new JournalArticleImpl();

		journalArticle.setNew(true);
		journalArticle.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalArticle.setUuid(uuid);

		return journalArticle;
	}

	public JournalArticle remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public JournalArticle remove(long id)
		throws NoSuchArticleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalArticle journalArticle = (JournalArticle)session.get(JournalArticleImpl.class,
					new Long(id));

			if (journalArticle == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No JournalArticle exists with the primary key " +
						id);
				}

				throw new NoSuchArticleException(
					"No JournalArticle exists with the primary key " + id);
			}

			return remove(journalArticle);
		}
		catch (NoSuchArticleException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticle remove(JournalArticle journalArticle)
		throws SystemException {
		for (ModelListener<JournalArticle> listener : listeners) {
			listener.onBeforeRemove(journalArticle);
		}

		journalArticle = removeImpl(journalArticle);

		for (ModelListener<JournalArticle> listener : listeners) {
			listener.onAfterRemove(journalArticle);
		}

		return journalArticle;
	}

	protected JournalArticle removeImpl(JournalArticle journalArticle)
		throws SystemException {
		journalArticle = toUnwrappedModel(journalArticle);

		Session session = null;

		try {
			session = openSession();

			if (journalArticle.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(JournalArticleImpl.class,
						journalArticle.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(journalArticle);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		JournalArticleModelImpl journalArticleModelImpl = (JournalArticleModelImpl)journalArticle;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalArticleModelImpl.getOriginalUuid(),
				new Long(journalArticleModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_A_V,
			new Object[] {
				new Long(journalArticleModelImpl.getOriginalGroupId()),
				
			journalArticleModelImpl.getOriginalArticleId(),
				new Double(journalArticleModelImpl.getOriginalVersion())
			});

		EntityCacheUtil.removeResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImpl.class, journalArticle.getPrimaryKey());

		return journalArticle;
	}

	public JournalArticle updateImpl(
		com.liferay.portlet.journal.model.JournalArticle journalArticle,
		boolean merge) throws SystemException {
		journalArticle = toUnwrappedModel(journalArticle);

		boolean isNew = journalArticle.isNew();

		JournalArticleModelImpl journalArticleModelImpl = (JournalArticleModelImpl)journalArticle;

		if (Validator.isNull(journalArticle.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalArticle.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, journalArticle, merge);

			journalArticle.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImpl.class, journalArticle.getPrimaryKey(),
			journalArticle);

		if (!isNew &&
				(!Validator.equals(journalArticle.getUuid(),
					journalArticleModelImpl.getOriginalUuid()) ||
				(journalArticle.getGroupId() != journalArticleModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalArticleModelImpl.getOriginalUuid(),
					new Long(journalArticleModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(journalArticle.getUuid(),
					journalArticleModelImpl.getOriginalUuid()) ||
				(journalArticle.getGroupId() != journalArticleModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalArticle.getUuid(),
					new Long(journalArticle.getGroupId())
				}, journalArticle);
		}

		if (!isNew &&
				((journalArticle.getGroupId() != journalArticleModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalArticle.getArticleId(),
					journalArticleModelImpl.getOriginalArticleId()) ||
				(journalArticle.getVersion() != journalArticleModelImpl.getOriginalVersion()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_A_V,
				new Object[] {
					new Long(journalArticleModelImpl.getOriginalGroupId()),
					
				journalArticleModelImpl.getOriginalArticleId(),
					new Double(journalArticleModelImpl.getOriginalVersion())
				});
		}

		if (isNew ||
				((journalArticle.getGroupId() != journalArticleModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalArticle.getArticleId(),
					journalArticleModelImpl.getOriginalArticleId()) ||
				(journalArticle.getVersion() != journalArticleModelImpl.getOriginalVersion()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V,
				new Object[] {
					new Long(journalArticle.getGroupId()),
					
				journalArticle.getArticleId(),
					new Double(journalArticle.getVersion())
				}, journalArticle);
		}

		return journalArticle;
	}

	protected JournalArticle toUnwrappedModel(JournalArticle journalArticle) {
		if (journalArticle instanceof JournalArticleImpl) {
			return journalArticle;
		}

		JournalArticleImpl journalArticleImpl = new JournalArticleImpl();

		journalArticleImpl.setNew(journalArticle.isNew());
		journalArticleImpl.setPrimaryKey(journalArticle.getPrimaryKey());

		journalArticleImpl.setUuid(journalArticle.getUuid());
		journalArticleImpl.setId(journalArticle.getId());
		journalArticleImpl.setResourcePrimKey(journalArticle.getResourcePrimKey());
		journalArticleImpl.setGroupId(journalArticle.getGroupId());
		journalArticleImpl.setCompanyId(journalArticle.getCompanyId());
		journalArticleImpl.setUserId(journalArticle.getUserId());
		journalArticleImpl.setUserName(journalArticle.getUserName());
		journalArticleImpl.setCreateDate(journalArticle.getCreateDate());
		journalArticleImpl.setModifiedDate(journalArticle.getModifiedDate());
		journalArticleImpl.setArticleId(journalArticle.getArticleId());
		journalArticleImpl.setVersion(journalArticle.getVersion());
		journalArticleImpl.setTitle(journalArticle.getTitle());
		journalArticleImpl.setUrlTitle(journalArticle.getUrlTitle());
		journalArticleImpl.setDescription(journalArticle.getDescription());
		journalArticleImpl.setContent(journalArticle.getContent());
		journalArticleImpl.setType(journalArticle.getType());
		journalArticleImpl.setStructureId(journalArticle.getStructureId());
		journalArticleImpl.setTemplateId(journalArticle.getTemplateId());
		journalArticleImpl.setDisplayDate(journalArticle.getDisplayDate());
		journalArticleImpl.setStatus(journalArticle.getStatus());
		journalArticleImpl.setStatusByUserId(journalArticle.getStatusByUserId());
		journalArticleImpl.setStatusByUserName(journalArticle.getStatusByUserName());
		journalArticleImpl.setStatusDate(journalArticle.getStatusDate());
		journalArticleImpl.setExpirationDate(journalArticle.getExpirationDate());
		journalArticleImpl.setReviewDate(journalArticle.getReviewDate());
		journalArticleImpl.setIndexable(journalArticle.isIndexable());
		journalArticleImpl.setSmallImage(journalArticle.isSmallImage());
		journalArticleImpl.setSmallImageId(journalArticle.getSmallImageId());
		journalArticleImpl.setSmallImageURL(journalArticle.getSmallImageURL());

		return journalArticleImpl;
	}

	public JournalArticle findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalArticle findByPrimaryKey(long id)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = fetchByPrimaryKey(id);

		if (journalArticle == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No JournalArticle exists with the primary key " +
					id);
			}

			throw new NoSuchArticleException(
				"No JournalArticle exists with the primary key " + id);
		}

		return journalArticle;
	}

	public JournalArticle fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalArticle fetchByPrimaryKey(long id) throws SystemException {
		JournalArticle journalArticle = (JournalArticle)EntityCacheUtil.getResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
				JournalArticleImpl.class, id, this);

		if (journalArticle == null) {
			Session session = null;

			try {
				session = openSession();

				journalArticle = (JournalArticle)session.get(JournalArticleImpl.class,
						new Long(id));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (journalArticle != null) {
					cacheResult(journalArticle);
				}

				closeSession(session);
			}
		}

		return journalArticle;
	}

	public List<JournalArticle> findByUuid(String uuid)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				if (uuid == null) {
					query.append("journalArticle.uuid IS NULL");
				}
				else {
					query.append("journalArticle.uuid = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");

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
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<JournalArticle> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				if (uuid == null) {
					query.append("journalArticle.uuid IS NULL");
				}
				else {
					query.append("journalArticle.uuid = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticle.");
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

					query.append("journalArticle.articleId ASC, ");
					query.append("journalArticle.version DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticle findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		int count = countByUuid(uuid);

		List<JournalArticle> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle[] findByUuid_PrevAndNext(long id, String uuid,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

			if (uuid == null) {
				query.append("journalArticle.uuid IS NULL");
			}
			else {
				query.append("journalArticle.uuid = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalArticle.");
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

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticle findByUUID_G(String uuid, long groupId)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = fetchByUUID_G(uuid, groupId);

		if (journalArticle == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchArticleException(msg.toString());
		}

		return journalArticle;
	}

	public JournalArticle fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public JournalArticle fetchByUUID_G(String uuid, long groupId,
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

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				if (uuid == null) {
					query.append("journalArticle.uuid IS NULL");
				}
				else {
					query.append("journalArticle.uuid = ?");
				}

				query.append(" AND ");

				query.append("journalArticle.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<JournalArticle> list = q.list();

				result = list;

				JournalArticle journalArticle = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					journalArticle = list.get(0);

					cacheResult(journalArticle);

					if ((journalArticle.getUuid() == null) ||
							!journalArticle.getUuid().equals(uuid) ||
							(journalArticle.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, journalArticle);
					}
				}

				return journalArticle;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<JournalArticle>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalArticle)result;
			}
		}
	}

	public List<JournalArticle> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<JournalArticle> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticle.");
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

					query.append("journalArticle.articleId ASC, ");
					query.append("journalArticle.version DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticle findByGroupId_First(long groupId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		int count = countByGroupId(groupId);

		List<JournalArticle> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle[] findByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

			query.append("journalArticle.groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalArticle.");
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

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalArticle> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");

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
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<JournalArticle> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticle.");
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

					query.append("journalArticle.articleId ASC, ");
					query.append("journalArticle.version DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticle findByCompanyId_First(long companyId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle findByCompanyId_Last(long companyId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		int count = countByCompanyId(companyId);

		List<JournalArticle> list = findByCompanyId(companyId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle[] findByCompanyId_PrevAndNext(long id,
		long companyId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

			query.append("journalArticle.companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalArticle.");
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

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalArticle> findBySmallImageId(long smallImageId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(smallImageId) };

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_SMALLIMAGEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.smallImageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_SMALLIMAGEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findBySmallImageId(long smallImageId,
		int start, int end) throws SystemException {
		return findBySmallImageId(smallImageId, start, end, null);
	}

	public List<JournalArticle> findBySmallImageId(long smallImageId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(smallImageId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_SMALLIMAGEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.smallImageId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticle.");
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

					query.append("journalArticle.articleId ASC, ");
					query.append("journalArticle.version DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_SMALLIMAGEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticle findBySmallImageId_First(long smallImageId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findBySmallImageId(smallImageId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("smallImageId=" + smallImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle findBySmallImageId_Last(long smallImageId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		int count = countBySmallImageId(smallImageId);

		List<JournalArticle> list = findBySmallImageId(smallImageId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("smallImageId=" + smallImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle[] findBySmallImageId_PrevAndNext(long id,
		long smallImageId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		int count = countBySmallImageId(smallImageId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

			query.append("journalArticle.smallImageId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalArticle.");
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

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(smallImageId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalArticle> findByR_S(long resourcePrimKey, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(resourcePrimKey), new Integer(status)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_R_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.resourcePrimKey = ?");

				query.append(" AND ");

				query.append("journalArticle.status = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_R_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByR_S(long resourcePrimKey, int status,
		int start, int end) throws SystemException {
		return findByR_S(resourcePrimKey, status, start, end, null);
	}

	public List<JournalArticle> findByR_S(long resourcePrimKey, int status,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(resourcePrimKey), new Integer(status),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_R_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.resourcePrimKey = ?");

				query.append(" AND ");

				query.append("journalArticle.status = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticle.");
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

					query.append("journalArticle.articleId ASC, ");
					query.append("journalArticle.version DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(status);

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_R_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticle findByR_S_First(long resourcePrimKey, int status,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByR_S(resourcePrimKey, status, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("resourcePrimKey=" + resourcePrimKey);

			msg.append(", ");
			msg.append("status=" + status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle findByR_S_Last(long resourcePrimKey, int status,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		int count = countByR_S(resourcePrimKey, status);

		List<JournalArticle> list = findByR_S(resourcePrimKey, status,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("resourcePrimKey=" + resourcePrimKey);

			msg.append(", ");
			msg.append("status=" + status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle[] findByR_S_PrevAndNext(long id,
		long resourcePrimKey, int status, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		int count = countByR_S(resourcePrimKey, status);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

			query.append("journalArticle.resourcePrimKey = ?");

			query.append(" AND ");

			query.append("journalArticle.status = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalArticle.");
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

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(resourcePrimKey);

			qPos.add(status);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalArticle> findByG_A(long groupId, String articleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), articleId };

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalArticle.articleId IS NULL");
				}
				else {
					query.append("journalArticle.articleId = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_A, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByG_A(long groupId, String articleId,
		int start, int end) throws SystemException {
		return findByG_A(groupId, articleId, start, end, null);
	}

	public List<JournalArticle> findByG_A(long groupId, String articleId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalArticle.articleId IS NULL");
				}
				else {
					query.append("journalArticle.articleId = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticle.");
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

					query.append("journalArticle.articleId ASC, ");
					query.append("journalArticle.version DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_A,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticle findByG_A_First(long groupId, String articleId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByG_A(groupId, articleId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("articleId=" + articleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle findByG_A_Last(long groupId, String articleId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		int count = countByG_A(groupId, articleId);

		List<JournalArticle> list = findByG_A(groupId, articleId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("articleId=" + articleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle[] findByG_A_PrevAndNext(long id, long groupId,
		String articleId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		int count = countByG_A(groupId, articleId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

			query.append("journalArticle.groupId = ?");

			query.append(" AND ");

			if (articleId == null) {
				query.append("journalArticle.articleId IS NULL");
			}
			else {
				query.append("journalArticle.articleId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalArticle.");
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

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (articleId != null) {
				qPos.add(articleId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalArticle> findByG_S(long groupId, String structureId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), structureId };

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (structureId == null) {
					query.append("journalArticle.structureId IS NULL");
				}
				else {
					query.append("journalArticle.structureId = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByG_S(long groupId, String structureId,
		int start, int end) throws SystemException {
		return findByG_S(groupId, structureId, start, end, null);
	}

	public List<JournalArticle> findByG_S(long groupId, String structureId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				structureId,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (structureId == null) {
					query.append("journalArticle.structureId IS NULL");
				}
				else {
					query.append("journalArticle.structureId = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticle.");
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

					query.append("journalArticle.articleId ASC, ");
					query.append("journalArticle.version DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
				}

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticle findByG_S_First(long groupId, String structureId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByG_S(groupId, structureId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("structureId=" + structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle findByG_S_Last(long groupId, String structureId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		int count = countByG_S(groupId, structureId);

		List<JournalArticle> list = findByG_S(groupId, structureId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("structureId=" + structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle[] findByG_S_PrevAndNext(long id, long groupId,
		String structureId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		int count = countByG_S(groupId, structureId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

			query.append("journalArticle.groupId = ?");

			query.append(" AND ");

			if (structureId == null) {
				query.append("journalArticle.structureId IS NULL");
			}
			else {
				query.append("journalArticle.structureId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalArticle.");
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

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (structureId != null) {
				qPos.add(structureId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalArticle> findByG_T(long groupId, String templateId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), templateId };

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (templateId == null) {
					query.append("journalArticle.templateId IS NULL");
				}
				else {
					query.append("journalArticle.templateId = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (templateId != null) {
					qPos.add(templateId);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_T, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByG_T(long groupId, String templateId,
		int start, int end) throws SystemException {
		return findByG_T(groupId, templateId, start, end, null);
	}

	public List<JournalArticle> findByG_T(long groupId, String templateId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				templateId,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (templateId == null) {
					query.append("journalArticle.templateId IS NULL");
				}
				else {
					query.append("journalArticle.templateId = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticle.");
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

					query.append("journalArticle.articleId ASC, ");
					query.append("journalArticle.version DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (templateId != null) {
					qPos.add(templateId);
				}

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticle findByG_T_First(long groupId, String templateId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByG_T(groupId, templateId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("templateId=" + templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle findByG_T_Last(long groupId, String templateId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		int count = countByG_T(groupId, templateId);

		List<JournalArticle> list = findByG_T(groupId, templateId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("templateId=" + templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle[] findByG_T_PrevAndNext(long id, long groupId,
		String templateId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		int count = countByG_T(groupId, templateId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

			query.append("journalArticle.groupId = ?");

			query.append(" AND ");

			if (templateId == null) {
				query.append("journalArticle.templateId IS NULL");
			}
			else {
				query.append("journalArticle.templateId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalArticle.");
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

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (templateId != null) {
				qPos.add(templateId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalArticle> findByG_UT(long groupId, String urlTitle)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), urlTitle };

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_UT,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (urlTitle == null) {
					query.append("journalArticle.urlTitle IS NULL");
				}
				else {
					query.append("journalArticle.urlTitle = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (urlTitle != null) {
					qPos.add(urlTitle);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_UT, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByG_UT(long groupId, String urlTitle,
		int start, int end) throws SystemException {
		return findByG_UT(groupId, urlTitle, start, end, null);
	}

	public List<JournalArticle> findByG_UT(long groupId, String urlTitle,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				urlTitle,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_UT,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (urlTitle == null) {
					query.append("journalArticle.urlTitle IS NULL");
				}
				else {
					query.append("journalArticle.urlTitle = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticle.");
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

					query.append("journalArticle.articleId ASC, ");
					query.append("journalArticle.version DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (urlTitle != null) {
					qPos.add(urlTitle);
				}

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_UT,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticle findByG_UT_First(long groupId, String urlTitle,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByG_UT(groupId, urlTitle, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("urlTitle=" + urlTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle findByG_UT_Last(long groupId, String urlTitle,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		int count = countByG_UT(groupId, urlTitle);

		List<JournalArticle> list = findByG_UT(groupId, urlTitle, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("urlTitle=" + urlTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle[] findByG_UT_PrevAndNext(long id, long groupId,
		String urlTitle, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		int count = countByG_UT(groupId, urlTitle);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

			query.append("journalArticle.groupId = ?");

			query.append(" AND ");

			if (urlTitle == null) {
				query.append("journalArticle.urlTitle IS NULL");
			}
			else {
				query.append("journalArticle.urlTitle = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalArticle.");
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

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (urlTitle != null) {
				qPos.add(urlTitle);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticle findByG_A_V(long groupId, String articleId,
		double version) throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = fetchByG_A_V(groupId, articleId, version);

		if (journalArticle == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("articleId=" + articleId);

			msg.append(", ");
			msg.append("version=" + version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchArticleException(msg.toString());
		}

		return journalArticle;
	}

	public JournalArticle fetchByG_A_V(long groupId, String articleId,
		double version) throws SystemException {
		return fetchByG_A_V(groupId, articleId, version, true);
	}

	public JournalArticle fetchByG_A_V(long groupId, String articleId,
		double version, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId, new Double(version)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_A_V,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalArticle.articleId IS NULL");
				}
				else {
					query.append("journalArticle.articleId = ?");
				}

				query.append(" AND ");

				query.append("journalArticle.version = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				qPos.add(version);

				List<JournalArticle> list = q.list();

				result = list;

				JournalArticle journalArticle = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V,
						finderArgs, list);
				}
				else {
					journalArticle = list.get(0);

					cacheResult(journalArticle);

					if ((journalArticle.getGroupId() != groupId) ||
							(journalArticle.getArticleId() == null) ||
							!journalArticle.getArticleId().equals(articleId) ||
							(journalArticle.getVersion() != version)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V,
							finderArgs, journalArticle);
					}
				}

				return journalArticle;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V,
						finderArgs, new ArrayList<JournalArticle>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalArticle)result;
			}
		}
	}

	public List<JournalArticle> findByG_A_S(long groupId, String articleId,
		int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId, new Integer(status)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_A_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalArticle.articleId IS NULL");
				}
				else {
					query.append("journalArticle.articleId = ?");
				}

				query.append(" AND ");

				query.append("journalArticle.status = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_A_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByG_A_S(long groupId, String articleId,
		int status, int start, int end) throws SystemException {
		return findByG_A_S(groupId, articleId, status, start, end, null);
	}

	public List<JournalArticle> findByG_A_S(long groupId, String articleId,
		int status, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId, new Integer(status),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_A_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalArticle.articleId IS NULL");
				}
				else {
					query.append("journalArticle.articleId = ?");
				}

				query.append(" AND ");

				query.append("journalArticle.status = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticle.");
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

					query.append("journalArticle.articleId ASC, ");
					query.append("journalArticle.version DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				qPos.add(status);

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_A_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticle findByG_A_S_First(long groupId, String articleId,
		int status, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByG_A_S(groupId, articleId, status, 0,
				1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("articleId=" + articleId);

			msg.append(", ");
			msg.append("status=" + status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle findByG_A_S_Last(long groupId, String articleId,
		int status, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		int count = countByG_A_S(groupId, articleId, status);

		List<JournalArticle> list = findByG_A_S(groupId, articleId, status,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("articleId=" + articleId);

			msg.append(", ");
			msg.append("status=" + status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle[] findByG_A_S_PrevAndNext(long id, long groupId,
		String articleId, int status, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		int count = countByG_A_S(groupId, articleId, status);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

			query.append("journalArticle.groupId = ?");

			query.append(" AND ");

			if (articleId == null) {
				query.append("journalArticle.articleId IS NULL");
			}
			else {
				query.append("journalArticle.articleId = ?");
			}

			query.append(" AND ");

			query.append("journalArticle.status = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalArticle.");
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

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (articleId != null) {
				qPos.add(articleId);
			}

			qPos.add(status);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalArticle> findByG_UT_S(long groupId, String urlTitle,
		int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				urlTitle, new Integer(status)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_UT_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (urlTitle == null) {
					query.append("journalArticle.urlTitle IS NULL");
				}
				else {
					query.append("journalArticle.urlTitle = ?");
				}

				query.append(" AND ");

				query.append("journalArticle.status = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (urlTitle != null) {
					qPos.add(urlTitle);
				}

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_UT_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByG_UT_S(long groupId, String urlTitle,
		int status, int start, int end) throws SystemException {
		return findByG_UT_S(groupId, urlTitle, status, start, end, null);
	}

	public List<JournalArticle> findByG_UT_S(long groupId, String urlTitle,
		int status, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				urlTitle, new Integer(status),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_UT_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (urlTitle == null) {
					query.append("journalArticle.urlTitle IS NULL");
				}
				else {
					query.append("journalArticle.urlTitle = ?");
				}

				query.append(" AND ");

				query.append("journalArticle.status = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticle.");
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

					query.append("journalArticle.articleId ASC, ");
					query.append("journalArticle.version DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (urlTitle != null) {
					qPos.add(urlTitle);
				}

				qPos.add(status);

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_UT_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticle findByG_UT_S_First(long groupId, String urlTitle,
		int status, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByG_UT_S(groupId, urlTitle, status, 0,
				1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("urlTitle=" + urlTitle);

			msg.append(", ");
			msg.append("status=" + status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle findByG_UT_S_Last(long groupId, String urlTitle,
		int status, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		int count = countByG_UT_S(groupId, urlTitle, status);

		List<JournalArticle> list = findByG_UT_S(groupId, urlTitle, status,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticle exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("urlTitle=" + urlTitle);

			msg.append(", ");
			msg.append("status=" + status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticle[] findByG_UT_S_PrevAndNext(long id, long groupId,
		String urlTitle, int status, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		int count = countByG_UT_S(groupId, urlTitle, status);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT journalArticle FROM JournalArticle journalArticle WHERE ");

			query.append("journalArticle.groupId = ?");

			query.append(" AND ");

			if (urlTitle == null) {
				query.append("journalArticle.urlTitle IS NULL");
			}
			else {
				query.append("journalArticle.urlTitle = ?");
			}

			query.append(" AND ");

			query.append("journalArticle.status = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalArticle.");
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

				query.append("journalArticle.articleId ASC, ");
				query.append("journalArticle.version DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (urlTitle != null) {
				qPos.add(urlTitle);
			}

			qPos.add(status);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
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

	public List<JournalArticle> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalArticle> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<JournalArticle> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticle FROM JournalArticle journalArticle ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticle.");
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

					query.append("journalArticle.articleId ASC, ");
					query.append("journalArticle.version DESC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (JournalArticle journalArticle : findByUuid(uuid)) {
			remove(journalArticle);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByUUID_G(uuid, groupId);

		remove(journalArticle);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalArticle journalArticle : findByGroupId(groupId)) {
			remove(journalArticle);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (JournalArticle journalArticle : findByCompanyId(companyId)) {
			remove(journalArticle);
		}
	}

	public void removeBySmallImageId(long smallImageId)
		throws SystemException {
		for (JournalArticle journalArticle : findBySmallImageId(smallImageId)) {
			remove(journalArticle);
		}
	}

	public void removeByR_S(long resourcePrimKey, int status)
		throws SystemException {
		for (JournalArticle journalArticle : findByR_S(resourcePrimKey, status)) {
			remove(journalArticle);
		}
	}

	public void removeByG_A(long groupId, String articleId)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_A(groupId, articleId)) {
			remove(journalArticle);
		}
	}

	public void removeByG_S(long groupId, String structureId)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_S(groupId, structureId)) {
			remove(journalArticle);
		}
	}

	public void removeByG_T(long groupId, String templateId)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_T(groupId, templateId)) {
			remove(journalArticle);
		}
	}

	public void removeByG_UT(long groupId, String urlTitle)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_UT(groupId, urlTitle)) {
			remove(journalArticle);
		}
	}

	public void removeByG_A_V(long groupId, String articleId, double version)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByG_A_V(groupId, articleId, version);

		remove(journalArticle);
	}

	public void removeByG_A_S(long groupId, String articleId, int status)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_A_S(groupId, articleId,
				status)) {
			remove(journalArticle);
		}
	}

	public void removeByG_UT_S(long groupId, String urlTitle, int status)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_UT_S(groupId, urlTitle,
				status)) {
			remove(journalArticle);
		}
	}

	public void removeAll() throws SystemException {
		for (JournalArticle journalArticle : findAll()) {
			remove(journalArticle);
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

				query.append("SELECT COUNT(journalArticle) ");
				query.append("FROM JournalArticle journalArticle WHERE ");

				if (uuid == null) {
					query.append("journalArticle.uuid IS NULL");
				}
				else {
					query.append("journalArticle.uuid = ?");
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

	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticle) ");
				query.append("FROM JournalArticle journalArticle WHERE ");

				if (uuid == null) {
					query.append("journalArticle.uuid IS NULL");
				}
				else {
					query.append("journalArticle.uuid = ?");
				}

				query.append(" AND ");

				query.append("journalArticle.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticle) ");
				query.append("FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticle) ");
				query.append("FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.companyId = ?");

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

	public int countBySmallImageId(long smallImageId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(smallImageId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticle) ");
				query.append("FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.smallImageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

	public int countByR_S(long resourcePrimKey, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(resourcePrimKey), new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_R_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticle) ");
				query.append("FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.resourcePrimKey = ?");

				query.append(" AND ");

				query.append("journalArticle.status = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_R_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_A(long groupId, String articleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), articleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticle) ");
				query.append("FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalArticle.articleId IS NULL");
				}
				else {
					query.append("journalArticle.articleId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_A, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticle) ");
				query.append("FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (structureId == null) {
					query.append("journalArticle.structureId IS NULL");
				}
				else {
					query.append("journalArticle.structureId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

	public int countByG_T(long groupId, String templateId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), templateId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticle) ");
				query.append("FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (templateId == null) {
					query.append("journalArticle.templateId IS NULL");
				}
				else {
					query.append("journalArticle.templateId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

	public int countByG_UT(long groupId, String urlTitle)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), urlTitle };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_UT,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticle) ");
				query.append("FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (urlTitle == null) {
					query.append("journalArticle.urlTitle IS NULL");
				}
				else {
					query.append("journalArticle.urlTitle = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

	public int countByG_A_V(long groupId, String articleId, double version)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId, new Double(version)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A_V,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticle) ");
				query.append("FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalArticle.articleId IS NULL");
				}
				else {
					query.append("journalArticle.articleId = ?");
				}

				query.append(" AND ");

				query.append("journalArticle.version = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				qPos.add(version);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_A_V,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_A_S(long groupId, String articleId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId, new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticle) ");
				query.append("FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalArticle.articleId IS NULL");
				}
				else {
					query.append("journalArticle.articleId = ?");
				}

				query.append(" AND ");

				query.append("journalArticle.status = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_A_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_UT_S(long groupId, String urlTitle, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				urlTitle, new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_UT_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticle) ");
				query.append("FROM JournalArticle journalArticle WHERE ");

				query.append("journalArticle.groupId = ?");

				query.append(" AND ");

				if (urlTitle == null) {
					query.append("journalArticle.urlTitle IS NULL");
				}
				else {
					query.append("journalArticle.urlTitle = ?");
				}

				query.append(" AND ");

				query.append("journalArticle.status = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (urlTitle != null) {
					qPos.add(urlTitle);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_UT_S,
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
						"SELECT COUNT(journalArticle) FROM JournalArticle journalArticle");

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
						"value.object.listener.com.liferay.portlet.journal.model.JournalArticle")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalArticle>> listenersList = new ArrayList<ModelListener<JournalArticle>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalArticle>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticlePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticlePersistence journalArticlePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence journalArticleImagePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence journalContentSearchPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalFeedPersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalFeedPersistence journalFeedPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalStructurePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalStructurePersistence journalStructurePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetEntryPersistence assetEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetTagPersistence assetTagPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence")
	protected com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence ratingsStatsPersistence;
	private static Log _log = LogFactoryUtil.getLog(JournalArticlePersistenceImpl.class);
}