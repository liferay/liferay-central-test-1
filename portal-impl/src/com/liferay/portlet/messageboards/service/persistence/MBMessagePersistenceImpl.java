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

package com.liferay.portlet.messageboards.service.persistence;

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

import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="MBMessagePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessagePersistence
 * @see       MBMessageUtil
 * @generated
 */
public class MBMessagePersistenceImpl extends BasePersistenceImpl
	implements MBMessagePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = MBMessageImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_THREADID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByThreadId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_THREADID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByThreadId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_THREADID = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByThreadId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_THREADREPLIES = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByThreadReplies", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_THREADREPLIES = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByThreadReplies",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_THREADREPLIES = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByThreadReplies", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_U = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_T_P = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_T_P = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_P = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByT_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_C_T = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_C_T = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_T = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(MBMessage mbMessage) {
		EntityCacheUtil.putResult(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageImpl.class, mbMessage.getPrimaryKey(), mbMessage);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { mbMessage.getUuid(), new Long(mbMessage.getGroupId()) },
			mbMessage);
	}

	public void cacheResult(List<MBMessage> mbMessages) {
		for (MBMessage mbMessage : mbMessages) {
			if (EntityCacheUtil.getResult(
						MBMessageModelImpl.ENTITY_CACHE_ENABLED,
						MBMessageImpl.class, mbMessage.getPrimaryKey(), this) == null) {
				cacheResult(mbMessage);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(MBMessageImpl.class.getName());
		EntityCacheUtil.clearCache(MBMessageImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public MBMessage create(long messageId) {
		MBMessage mbMessage = new MBMessageImpl();

		mbMessage.setNew(true);
		mbMessage.setPrimaryKey(messageId);

		String uuid = PortalUUIDUtil.generate();

		mbMessage.setUuid(uuid);

		return mbMessage;
	}

	public MBMessage remove(long messageId)
		throws NoSuchMessageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBMessage mbMessage = (MBMessage)session.get(MBMessageImpl.class,
					new Long(messageId));

			if (mbMessage == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No MBMessage exists with the primary key " +
						messageId);
				}

				throw new NoSuchMessageException(
					"No MBMessage exists with the primary key " + messageId);
			}

			return remove(mbMessage);
		}
		catch (NoSuchMessageException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBMessage remove(MBMessage mbMessage) throws SystemException {
		for (ModelListener<MBMessage> listener : listeners) {
			listener.onBeforeRemove(mbMessage);
		}

		mbMessage = removeImpl(mbMessage);

		for (ModelListener<MBMessage> listener : listeners) {
			listener.onAfterRemove(mbMessage);
		}

		return mbMessage;
	}

	protected MBMessage removeImpl(MBMessage mbMessage)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (mbMessage.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(MBMessageImpl.class,
						mbMessage.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(mbMessage);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		MBMessageModelImpl mbMessageModelImpl = (MBMessageModelImpl)mbMessage;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				mbMessageModelImpl.getOriginalUuid(),
				new Long(mbMessageModelImpl.getOriginalGroupId())
			});

		EntityCacheUtil.removeResult(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageImpl.class, mbMessage.getPrimaryKey());

		return mbMessage;
	}

	/**
	 * @deprecated Use {@link #update(MBMessage, boolean merge)}.
	 */
	public MBMessage update(MBMessage mbMessage) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(MBMessage mbMessage) method. Use update(MBMessage mbMessage, boolean merge) instead.");
		}

		return update(mbMessage, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  mbMessage the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when mbMessage is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public MBMessage update(MBMessage mbMessage, boolean merge)
		throws SystemException {
		boolean isNew = mbMessage.isNew();

		for (ModelListener<MBMessage> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(mbMessage);
			}
			else {
				listener.onBeforeUpdate(mbMessage);
			}
		}

		mbMessage = updateImpl(mbMessage, merge);

		for (ModelListener<MBMessage> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(mbMessage);
			}
			else {
				listener.onAfterUpdate(mbMessage);
			}
		}

		return mbMessage;
	}

	public MBMessage updateImpl(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage,
		boolean merge) throws SystemException {
		boolean isNew = mbMessage.isNew();

		MBMessageModelImpl mbMessageModelImpl = (MBMessageModelImpl)mbMessage;

		if (Validator.isNull(mbMessage.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mbMessage.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, mbMessage, merge);

			mbMessage.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageImpl.class, mbMessage.getPrimaryKey(), mbMessage);

		if (!isNew &&
				(!Validator.equals(mbMessage.getUuid(),
					mbMessageModelImpl.getOriginalUuid()) ||
				(mbMessage.getGroupId() != mbMessageModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					mbMessageModelImpl.getOriginalUuid(),
					new Long(mbMessageModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(mbMessage.getUuid(),
					mbMessageModelImpl.getOriginalUuid()) ||
				(mbMessage.getGroupId() != mbMessageModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					mbMessage.getUuid(), new Long(mbMessage.getGroupId())
				}, mbMessage);
		}

		return mbMessage;
	}

	public MBMessage findByPrimaryKey(long messageId)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = fetchByPrimaryKey(messageId);

		if (mbMessage == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MBMessage exists with the primary key " +
					messageId);
			}

			throw new NoSuchMessageException(
				"No MBMessage exists with the primary key " + messageId);
		}

		return mbMessage;
	}

	public MBMessage fetchByPrimaryKey(long messageId)
		throws SystemException {
		MBMessage mbMessage = (MBMessage)EntityCacheUtil.getResult(MBMessageModelImpl.ENTITY_CACHE_ENABLED,
				MBMessageImpl.class, messageId, this);

		if (mbMessage == null) {
			Session session = null;

			try {
				session = openSession();

				mbMessage = (MBMessage)session.get(MBMessageImpl.class,
						new Long(messageId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (mbMessage != null) {
					cacheResult(mbMessage);
				}

				closeSession(session);
			}
		}

		return mbMessage;
	}

	public List<MBMessage> findByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				if (uuid == null) {
					query.append("mbMessage.uuid IS NULL");
				}
				else {
					query.append("mbMessage.uuid = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");

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
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMessage> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<MBMessage> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				if (uuid == null) {
					query.append("mbMessage.uuid IS NULL");
				}
				else {
					query.append("mbMessage.uuid = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbMessage.");
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

					query.append("mbMessage.createDate ASC, ");
					query.append("mbMessage.messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMessage findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		int count = countByUuid(uuid);

		List<MBMessage> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage[] findByUuid_PrevAndNext(long messageId, String uuid,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

			if (uuid == null) {
				query.append("mbMessage.uuid IS NULL");
			}
			else {
				query.append("mbMessage.uuid = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbMessage.");
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

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBMessage findByUUID_G(String uuid, long groupId)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = fetchByUUID_G(uuid, groupId);

		if (mbMessage == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchMessageException(msg.toString());
		}

		return mbMessage;
	}

	public MBMessage fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public MBMessage fetchByUUID_G(String uuid, long groupId,
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

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				if (uuid == null) {
					query.append("mbMessage.uuid IS NULL");
				}
				else {
					query.append("mbMessage.uuid = ?");
				}

				query.append(" AND ");

				query.append("mbMessage.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<MBMessage> list = q.list();

				result = list;

				MBMessage mbMessage = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					mbMessage = list.get(0);

					cacheResult(mbMessage);

					if ((mbMessage.getUuid() == null) ||
							!mbMessage.getUuid().equals(uuid) ||
							(mbMessage.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, mbMessage);
					}
				}

				return mbMessage;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<MBMessage>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (MBMessage)result;
			}
		}
	}

	public List<MBMessage> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");

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
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMessage> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<MBMessage> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbMessage.");
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

					query.append("mbMessage.createDate ASC, ");
					query.append("mbMessage.messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMessage findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		int count = countByCompanyId(companyId);

		List<MBMessage> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage[] findByCompanyId_PrevAndNext(long messageId,
		long companyId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

			query.append("mbMessage.companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbMessage.");
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

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");

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
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMessage> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<MBMessage> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbMessage.");
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

					query.append("mbMessage.createDate ASC, ");
					query.append("mbMessage.messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMessage findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		int count = countByGroupId(groupId);

		List<MBMessage> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage[] findByGroupId_PrevAndNext(long messageId, long groupId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

			query.append("mbMessage.groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbMessage.");
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

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByThreadId(long threadId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(threadId) };

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_THREADID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.threadId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_THREADID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMessage> findByThreadId(long threadId, int start, int end)
		throws SystemException {
		return findByThreadId(threadId, start, end, null);
	}

	public List<MBMessage> findByThreadId(long threadId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(threadId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_THREADID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.threadId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbMessage.");
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

					query.append("mbMessage.createDate ASC, ");
					query.append("mbMessage.messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_THREADID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMessage findByThreadId_First(long threadId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByThreadId(threadId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("threadId=" + threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage findByThreadId_Last(long threadId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		int count = countByThreadId(threadId);

		List<MBMessage> list = findByThreadId(threadId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("threadId=" + threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage[] findByThreadId_PrevAndNext(long messageId,
		long threadId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		int count = countByThreadId(threadId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

			query.append("mbMessage.threadId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbMessage.");
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

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(threadId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByThreadReplies(long threadId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(threadId) };

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_THREADREPLIES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.threadId = ?");

				query.append(" AND mbMessage.parentMessageId != 0 ");

				query.append("ORDER BY ");

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_THREADREPLIES,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMessage> findByThreadReplies(long threadId, int start, int end)
		throws SystemException {
		return findByThreadReplies(threadId, start, end, null);
	}

	public List<MBMessage> findByThreadReplies(long threadId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(threadId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_THREADREPLIES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.threadId = ?");

				query.append(" AND mbMessage.parentMessageId != 0 ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbMessage.");
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

					query.append("mbMessage.createDate ASC, ");
					query.append("mbMessage.messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_THREADREPLIES,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMessage findByThreadReplies_First(long threadId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByThreadReplies(threadId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("threadId=" + threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage findByThreadReplies_Last(long threadId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		int count = countByThreadReplies(threadId);

		List<MBMessage> list = findByThreadReplies(threadId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("threadId=" + threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage[] findByThreadReplies_PrevAndNext(long messageId,
		long threadId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		int count = countByThreadReplies(threadId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

			query.append("mbMessage.threadId = ?");

			query.append(" AND mbMessage.parentMessageId != 0 ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbMessage.");
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

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(threadId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByG_U(long groupId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.groupId = ?");

				query.append(" AND ");

				query.append("mbMessage.userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMessage> findByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	public List<MBMessage> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.groupId = ?");

				query.append(" AND ");

				query.append("mbMessage.userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbMessage.");
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

					query.append("mbMessage.createDate ASC, ");
					query.append("mbMessage.messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_U,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMessage findByG_U_First(long groupId, long userId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByG_U(groupId, userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage findByG_U_Last(long groupId, long userId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		int count = countByG_U(groupId, userId);

		List<MBMessage> list = findByG_U(groupId, userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage[] findByG_U_PrevAndNext(long messageId, long groupId,
		long userId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		int count = countByG_U(groupId, userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

			query.append("mbMessage.groupId = ?");

			query.append(" AND ");

			query.append("mbMessage.userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbMessage.");
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

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByG_C(long groupId, long categoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.groupId = ?");

				query.append(" AND ");

				query.append("mbMessage.categoryId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_C, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMessage> findByG_C(long groupId, long categoryId, int start,
		int end) throws SystemException {
		return findByG_C(groupId, categoryId, start, end, null);
	}

	public List<MBMessage> findByG_C(long groupId, long categoryId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.groupId = ?");

				query.append(" AND ");

				query.append("mbMessage.categoryId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbMessage.");
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

					query.append("mbMessage.createDate ASC, ");
					query.append("mbMessage.messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMessage findByG_C_First(long groupId, long categoryId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByG_C(groupId, categoryId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("categoryId=" + categoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage findByG_C_Last(long groupId, long categoryId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		int count = countByG_C(groupId, categoryId);

		List<MBMessage> list = findByG_C(groupId, categoryId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("categoryId=" + categoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage[] findByG_C_PrevAndNext(long messageId, long groupId,
		long categoryId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		int count = countByG_C(groupId, categoryId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

			query.append("mbMessage.groupId = ?");

			query.append(" AND ");

			query.append("mbMessage.categoryId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbMessage.");
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

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.classNameId = ?");

				query.append(" AND ");

				query.append("mbMessage.classPK = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMessage> findByC_C(long classNameId, long classPK, int start,
		int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	public List<MBMessage> findByC_C(long classNameId, long classPK, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.classNameId = ?");

				query.append(" AND ");

				query.append("mbMessage.classPK = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbMessage.");
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

					query.append("mbMessage.createDate ASC, ");
					query.append("mbMessage.messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMessage findByC_C_First(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByC_C(classNameId, classPK, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage findByC_C_Last(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		int count = countByC_C(classNameId, classPK);

		List<MBMessage> list = findByC_C(classNameId, classPK, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage[] findByC_C_PrevAndNext(long messageId, long classNameId,
		long classPK, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		int count = countByC_C(classNameId, classPK);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

			query.append("mbMessage.classNameId = ?");

			query.append(" AND ");

			query.append("mbMessage.classPK = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbMessage.");
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

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByT_P(long threadId, long parentMessageId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(threadId), new Long(parentMessageId)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_T_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.threadId = ?");

				query.append(" AND ");

				query.append("mbMessage.parentMessageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				qPos.add(parentMessageId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_T_P, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMessage> findByT_P(long threadId, long parentMessageId,
		int start, int end) throws SystemException {
		return findByT_P(threadId, parentMessageId, start, end, null);
	}

	public List<MBMessage> findByT_P(long threadId, long parentMessageId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(threadId), new Long(parentMessageId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_T_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.threadId = ?");

				query.append(" AND ");

				query.append("mbMessage.parentMessageId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbMessage.");
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

					query.append("mbMessage.createDate ASC, ");
					query.append("mbMessage.messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				qPos.add(parentMessageId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_T_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMessage findByT_P_First(long threadId, long parentMessageId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByT_P(threadId, parentMessageId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("threadId=" + threadId);

			msg.append(", ");
			msg.append("parentMessageId=" + parentMessageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage findByT_P_Last(long threadId, long parentMessageId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		int count = countByT_P(threadId, parentMessageId);

		List<MBMessage> list = findByT_P(threadId, parentMessageId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("threadId=" + threadId);

			msg.append(", ");
			msg.append("parentMessageId=" + parentMessageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage[] findByT_P_PrevAndNext(long messageId, long threadId,
		long parentMessageId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		int count = countByT_P(threadId, parentMessageId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

			query.append("mbMessage.threadId = ?");

			query.append(" AND ");

			query.append("mbMessage.parentMessageId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbMessage.");
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

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(threadId);

			qPos.add(parentMessageId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByG_C_T(long groupId, long categoryId,
		long threadId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId), new Long(threadId)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_C_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.groupId = ?");

				query.append(" AND ");

				query.append("mbMessage.categoryId = ?");

				query.append(" AND ");

				query.append("mbMessage.threadId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(threadId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_C_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMessage> findByG_C_T(long groupId, long categoryId,
		long threadId, int start, int end) throws SystemException {
		return findByG_C_T(groupId, categoryId, threadId, start, end, null);
	}

	public List<MBMessage> findByG_C_T(long groupId, long categoryId,
		long threadId, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId), new Long(threadId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_C_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.groupId = ?");

				query.append(" AND ");

				query.append("mbMessage.categoryId = ?");

				query.append(" AND ");

				query.append("mbMessage.threadId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbMessage.");
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

					query.append("mbMessage.createDate ASC, ");
					query.append("mbMessage.messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(threadId);

				list = (List<MBMessage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_C_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMessage findByG_C_T_First(long groupId, long categoryId,
		long threadId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByG_C_T(groupId, categoryId, threadId, 0, 1,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("categoryId=" + categoryId);

			msg.append(", ");
			msg.append("threadId=" + threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage findByG_C_T_Last(long groupId, long categoryId,
		long threadId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		int count = countByG_C_T(groupId, categoryId, threadId);

		List<MBMessage> list = findByG_C_T(groupId, categoryId, threadId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("categoryId=" + categoryId);

			msg.append(", ");
			msg.append("threadId=" + threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage[] findByG_C_T_PrevAndNext(long messageId, long groupId,
		long categoryId, long threadId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		int count = countByG_C_T(groupId, categoryId, threadId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT mbMessage FROM MBMessage mbMessage WHERE ");

			query.append("mbMessage.groupId = ?");

			query.append(" AND ");

			query.append("mbMessage.categoryId = ?");

			query.append(" AND ");

			query.append("mbMessage.threadId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbMessage.");
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

				query.append("mbMessage.createDate ASC, ");
				query.append("mbMessage.messageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(threadId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

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

	public List<MBMessage> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MBMessage> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<MBMessage> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessage> list = (List<MBMessage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbMessage FROM MBMessage mbMessage ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbMessage.");
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

					query.append("mbMessage.createDate ASC, ");
					query.append("mbMessage.messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<MBMessage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MBMessage>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (MBMessage mbMessage : findByUuid(uuid)) {
			remove(mbMessage);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByUUID_G(uuid, groupId);

		remove(mbMessage);
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (MBMessage mbMessage : findByCompanyId(companyId)) {
			remove(mbMessage);
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (MBMessage mbMessage : findByGroupId(groupId)) {
			remove(mbMessage);
		}
	}

	public void removeByThreadId(long threadId) throws SystemException {
		for (MBMessage mbMessage : findByThreadId(threadId)) {
			remove(mbMessage);
		}
	}

	public void removeByThreadReplies(long threadId) throws SystemException {
		for (MBMessage mbMessage : findByThreadReplies(threadId)) {
			remove(mbMessage);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (MBMessage mbMessage : findByG_U(groupId, userId)) {
			remove(mbMessage);
		}
	}

	public void removeByG_C(long groupId, long categoryId)
		throws SystemException {
		for (MBMessage mbMessage : findByG_C(groupId, categoryId)) {
			remove(mbMessage);
		}
	}

	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (MBMessage mbMessage : findByC_C(classNameId, classPK)) {
			remove(mbMessage);
		}
	}

	public void removeByT_P(long threadId, long parentMessageId)
		throws SystemException {
		for (MBMessage mbMessage : findByT_P(threadId, parentMessageId)) {
			remove(mbMessage);
		}
	}

	public void removeByG_C_T(long groupId, long categoryId, long threadId)
		throws SystemException {
		for (MBMessage mbMessage : findByG_C_T(groupId, categoryId, threadId)) {
			remove(mbMessage);
		}
	}

	public void removeAll() throws SystemException {
		for (MBMessage mbMessage : findAll()) {
			remove(mbMessage);
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

				query.append("SELECT COUNT(mbMessage) ");
				query.append("FROM MBMessage mbMessage WHERE ");

				if (uuid == null) {
					query.append("mbMessage.uuid IS NULL");
				}
				else {
					query.append("mbMessage.uuid = ?");
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

				query.append("SELECT COUNT(mbMessage) ");
				query.append("FROM MBMessage mbMessage WHERE ");

				if (uuid == null) {
					query.append("mbMessage.uuid IS NULL");
				}
				else {
					query.append("mbMessage.uuid = ?");
				}

				query.append(" AND ");

				query.append("mbMessage.groupId = ?");

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

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(mbMessage) ");
				query.append("FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.companyId = ?");

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

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(mbMessage) ");
				query.append("FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.groupId = ?");

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

	public int countByThreadId(long threadId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(threadId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_THREADID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(mbMessage) ");
				query.append("FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.threadId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_THREADID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByThreadReplies(long threadId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(threadId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_THREADREPLIES,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(mbMessage) ");
				query.append("FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.threadId = ?");

				query.append(" AND mbMessage.parentMessageId != 0 ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_THREADREPLIES,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(mbMessage) ");
				query.append("FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.groupId = ?");

				query.append(" AND ");

				query.append("mbMessage.userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_C(long groupId, long categoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(mbMessage) ");
				query.append("FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.groupId = ?");

				query.append(" AND ");

				query.append("mbMessage.categoryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(mbMessage) ");
				query.append("FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.classNameId = ?");

				query.append(" AND ");

				query.append("mbMessage.classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByT_P(long threadId, long parentMessageId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(threadId), new Long(parentMessageId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(mbMessage) ");
				query.append("FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.threadId = ?");

				query.append(" AND ");

				query.append("mbMessage.parentMessageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				qPos.add(parentMessageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_C_T(long groupId, long categoryId, long threadId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId), new Long(threadId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(mbMessage) ");
				query.append("FROM MBMessage mbMessage WHERE ");

				query.append("mbMessage.groupId = ?");

				query.append(" AND ");

				query.append("mbMessage.categoryId = ?");

				query.append(" AND ");

				query.append("mbMessage.threadId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(threadId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_T,
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
						"SELECT COUNT(mbMessage) FROM MBMessage mbMessage");

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
						"value.object.listener.com.liferay.portlet.messageboards.model.MBMessage")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MBMessage>> listenersList = new ArrayList<ModelListener<MBMessage>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MBMessage>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBBanPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBBanPersistence mbBanPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence mbCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMailingListPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBMailingListPersistence mbMailingListPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence mbMessageFlagPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence mbThreadPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence.impl")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetEntryPersistence assetEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetTagPersistence assetTagPersistence;
	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence.impl")
	protected com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence.impl")
	protected com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence.impl")
	protected com.liferay.portlet.social.service.persistence.SocialActivityPersistence socialActivityPersistence;
	private static Log _log = LogFactoryUtil.getLog(MBMessagePersistenceImpl.class);
}