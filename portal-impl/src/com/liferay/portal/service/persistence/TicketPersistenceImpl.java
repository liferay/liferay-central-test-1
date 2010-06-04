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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchTicketException;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Ticket;
import com.liferay.portal.model.impl.TicketImpl;
import com.liferay.portal.model.impl.TicketModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="TicketPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TicketPersistence
 * @see       TicketUtil
 * @generated
 */
public class TicketPersistenceImpl extends BasePersistenceImpl<Ticket>
	implements TicketPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = TicketImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_KEY = new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByKey", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_KEY = new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByKey", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Ticket ticket) {
		EntityCacheUtil.putResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketImpl.class, ticket.getPrimaryKey(), ticket);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_KEY,
			new Object[] { ticket.getKey() }, ticket);
	}

	public void cacheResult(List<Ticket> tickets) {
		for (Ticket ticket : tickets) {
			if (EntityCacheUtil.getResult(
						TicketModelImpl.ENTITY_CACHE_ENABLED, TicketImpl.class,
						ticket.getPrimaryKey(), this) == null) {
				cacheResult(ticket);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(TicketImpl.class.getName());
		EntityCacheUtil.clearCache(TicketImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(Ticket ticket) {
		EntityCacheUtil.removeResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketImpl.class, ticket.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_KEY,
			new Object[] { ticket.getKey() });
	}

	public Ticket create(long ticketId) {
		Ticket ticket = new TicketImpl();

		ticket.setNew(true);
		ticket.setPrimaryKey(ticketId);

		return ticket;
	}

	public Ticket remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Ticket remove(long ticketId)
		throws NoSuchTicketException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Ticket ticket = (Ticket)session.get(TicketImpl.class,
					new Long(ticketId));

			if (ticket == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + ticketId);
				}

				throw new NoSuchTicketException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					ticketId);
			}

			return remove(ticket);
		}
		catch (NoSuchTicketException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Ticket remove(Ticket ticket) throws SystemException {
		for (ModelListener<Ticket> listener : listeners) {
			listener.onBeforeRemove(ticket);
		}

		ticket = removeImpl(ticket);

		for (ModelListener<Ticket> listener : listeners) {
			listener.onAfterRemove(ticket);
		}

		return ticket;
	}

	protected Ticket removeImpl(Ticket ticket) throws SystemException {
		ticket = toUnwrappedModel(ticket);

		Session session = null;

		try {
			session = openSession();

			if (ticket.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(TicketImpl.class,
						ticket.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(ticket);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		TicketModelImpl ticketModelImpl = (TicketModelImpl)ticket;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_KEY,
			new Object[] { ticketModelImpl.getOriginalKey() });

		EntityCacheUtil.removeResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketImpl.class, ticket.getPrimaryKey());

		return ticket;
	}

	public Ticket updateImpl(com.liferay.portal.model.Ticket ticket,
		boolean merge) throws SystemException {
		ticket = toUnwrappedModel(ticket);

		boolean isNew = ticket.isNew();

		TicketModelImpl ticketModelImpl = (TicketModelImpl)ticket;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, ticket, merge);

			ticket.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketImpl.class, ticket.getPrimaryKey(), ticket);

		if (!isNew &&
				(!Validator.equals(ticket.getKey(),
					ticketModelImpl.getOriginalKey()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_KEY,
				new Object[] { ticketModelImpl.getOriginalKey() });
		}

		if (isNew ||
				(!Validator.equals(ticket.getKey(),
					ticketModelImpl.getOriginalKey()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_KEY,
				new Object[] { ticket.getKey() }, ticket);
		}

		return ticket;
	}

	protected Ticket toUnwrappedModel(Ticket ticket) {
		if (ticket instanceof TicketImpl) {
			return ticket;
		}

		TicketImpl ticketImpl = new TicketImpl();

		ticketImpl.setNew(ticket.isNew());
		ticketImpl.setPrimaryKey(ticket.getPrimaryKey());

		ticketImpl.setTicketId(ticket.getTicketId());
		ticketImpl.setCompanyId(ticket.getCompanyId());
		ticketImpl.setCreateDate(ticket.getCreateDate());
		ticketImpl.setClassNameId(ticket.getClassNameId());
		ticketImpl.setClassPK(ticket.getClassPK());
		ticketImpl.setKey(ticket.getKey());
		ticketImpl.setExpirationDate(ticket.getExpirationDate());

		return ticketImpl;
	}

	public Ticket findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Ticket findByPrimaryKey(long ticketId)
		throws NoSuchTicketException, SystemException {
		Ticket ticket = fetchByPrimaryKey(ticketId);

		if (ticket == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + ticketId);
			}

			throw new NoSuchTicketException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				ticketId);
		}

		return ticket;
	}

	public Ticket fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Ticket fetchByPrimaryKey(long ticketId) throws SystemException {
		Ticket ticket = (Ticket)EntityCacheUtil.getResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
				TicketImpl.class, ticketId, this);

		if (ticket == null) {
			Session session = null;

			try {
				session = openSession();

				ticket = (Ticket)session.get(TicketImpl.class,
						new Long(ticketId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (ticket != null) {
					cacheResult(ticket);
				}

				closeSession(session);
			}
		}

		return ticket;
	}

	public Ticket findByKey(String key)
		throws NoSuchTicketException, SystemException {
		Ticket ticket = fetchByKey(key);

		if (ticket == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("key=");
			msg.append(key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTicketException(msg.toString());
		}

		return ticket;
	}

	public Ticket fetchByKey(String key) throws SystemException {
		return fetchByKey(key, true);
	}

	public Ticket fetchByKey(String key, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { key };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_KEY,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_TICKET_WHERE);

				if (key == null) {
					query.append(_FINDER_COLUMN_KEY_KEY_1);
				}
				else {
					if (key.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_KEY_KEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_KEY_KEY_2);
					}
				}

				query.append(TicketModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (key != null) {
					qPos.add(key);
				}

				List<Ticket> list = q.list();

				result = list;

				Ticket ticket = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_KEY,
						finderArgs, list);
				}
				else {
					ticket = list.get(0);

					cacheResult(ticket);

					if ((ticket.getKey() == null) ||
							!ticket.getKey().equals(key)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_KEY,
							finderArgs, ticket);
					}
				}

				return ticket;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_KEY,
						finderArgs, new ArrayList<Ticket>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Ticket)result;
			}
		}
	}

	public List<Ticket> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Ticket> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Ticket> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Ticket> list = (List<Ticket>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_TICKET);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_TICKET.concat(TicketModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Ticket>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Ticket>)QueryUtil.list(q, getDialect(), start,
							end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Ticket>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByKey(String key)
		throws NoSuchTicketException, SystemException {
		Ticket ticket = findByKey(key);

		remove(ticket);
	}

	public void removeAll() throws SystemException {
		for (Ticket ticket : findAll()) {
			remove(ticket);
		}
	}

	public int countByKey(String key) throws SystemException {
		Object[] finderArgs = new Object[] { key };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_KEY,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_TICKET_WHERE);

				if (key == null) {
					query.append(_FINDER_COLUMN_KEY_KEY_1);
				}
				else {
					if (key.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_KEY_KEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_KEY_KEY_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (key != null) {
					qPos.add(key);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_KEY, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_TICKET);

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
						"value.object.listener.com.liferay.portal.model.Ticket")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Ticket>> listenersList = new ArrayList<ModelListener<Ticket>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Ticket>)InstanceFactory.newInstance(
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
	private static final String _SQL_SELECT_TICKET = "SELECT ticket FROM Ticket ticket";
	private static final String _SQL_SELECT_TICKET_WHERE = "SELECT ticket FROM Ticket ticket WHERE ";
	private static final String _SQL_COUNT_TICKET = "SELECT COUNT(ticket) FROM Ticket ticket";
	private static final String _SQL_COUNT_TICKET_WHERE = "SELECT COUNT(ticket) FROM Ticket ticket WHERE ";
	private static final String _FINDER_COLUMN_KEY_KEY_1 = "ticket.key IS NULL";
	private static final String _FINDER_COLUMN_KEY_KEY_2 = "ticket.key = ?";
	private static final String _FINDER_COLUMN_KEY_KEY_3 = "(ticket.key IS NULL OR ticket.key = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ticket.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Ticket exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Ticket exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(TicketPersistenceImpl.class);
}