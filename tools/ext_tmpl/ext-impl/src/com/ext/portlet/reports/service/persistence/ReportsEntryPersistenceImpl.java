package com.ext.portlet.reports.service.persistence;

import com.ext.portlet.reports.NoSuchEntryException;
import com.ext.portlet.reports.model.ReportsEntry;
import com.ext.portlet.reports.model.impl.ReportsEntryImpl;
import com.ext.portlet.reports.model.impl.ReportsEntryModelImpl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.InitializingBean;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class ReportsEntryPersistenceImpl extends BasePersistenceImpl
    implements ReportsEntryPersistence, InitializingBean {
    private static Log _log = LogFactory.getLog(ReportsEntryPersistenceImpl.class);
    private ModelListener[] _listeners = new ModelListener[0];

    public ReportsEntry create(String entryId) {
        ReportsEntry reportsEntry = new ReportsEntryImpl();

        reportsEntry.setNew(true);
        reportsEntry.setPrimaryKey(entryId);

        return reportsEntry;
    }

    public ReportsEntry remove(String entryId)
        throws NoSuchEntryException, SystemException {
        Session session = null;

        try {
            session = openSession();

            ReportsEntry reportsEntry = (ReportsEntry) session.get(ReportsEntryImpl.class,
                    entryId);

            if (reportsEntry == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn("No ReportsEntry exists with the primary key " +
                        entryId);
                }

                throw new NoSuchEntryException(
                    "No ReportsEntry exists with the primary key " + entryId);
            }

            return remove(reportsEntry);
        } catch (NoSuchEntryException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    public ReportsEntry remove(ReportsEntry reportsEntry)
        throws SystemException {
        if (_listeners.length > 0) {
            for (ModelListener listener : _listeners) {
                listener.onBeforeRemove(reportsEntry);
            }
        }

        reportsEntry = removeImpl(reportsEntry);

        if (_listeners.length > 0) {
            for (ModelListener listener : _listeners) {
                listener.onAfterRemove(reportsEntry);
            }
        }

        return reportsEntry;
    }

    protected ReportsEntry removeImpl(ReportsEntry reportsEntry)
        throws SystemException {
        Session session = null;

        try {
            session = openSession();

            session.delete(reportsEntry);

            session.flush();

            return reportsEntry;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);

            FinderCacheUtil.clearCache(ReportsEntry.class.getName());
        }
    }

    /**
     * @deprecated Use <code>update(ReportsEntry reportsEntry, boolean merge)</code>.
     */
    public ReportsEntry update(ReportsEntry reportsEntry)
        throws SystemException {
        if (_log.isWarnEnabled()) {
            _log.warn(
                "Using the deprecated update(ReportsEntry reportsEntry) method. Use update(ReportsEntry reportsEntry, boolean merge) instead.");
        }

        return update(reportsEntry, false);
    }

    /**
     * Add, update, or merge, the entity. This method also calls the model
     * listeners to trigger the proper events associated with adding, deleting,
     * or updating an entity.
     *
     * @param                reportsEntry the entity to add, update, or merge
     * @param                merge boolean value for whether to merge the entity. The
     *                                default value is false. Setting merge to true is more
     *                                expensive and should only be true when reportsEntry is
     *                                transient. See LEP-5473 for a detailed discussion of this
     *                                method.
     * @return                true if the portlet can be displayed via Ajax
     */
    public ReportsEntry update(ReportsEntry reportsEntry, boolean merge)
        throws SystemException {
        boolean isNew = reportsEntry.isNew();

        if (_listeners.length > 0) {
            for (ModelListener listener : _listeners) {
                if (isNew) {
                    listener.onBeforeCreate(reportsEntry);
                } else {
                    listener.onBeforeUpdate(reportsEntry);
                }
            }
        }

        reportsEntry = updateImpl(reportsEntry, merge);

        if (_listeners.length > 0) {
            for (ModelListener listener : _listeners) {
                if (isNew) {
                    listener.onAfterCreate(reportsEntry);
                } else {
                    listener.onAfterUpdate(reportsEntry);
                }
            }
        }

        return reportsEntry;
    }

    public ReportsEntry updateImpl(
        com.ext.portlet.reports.model.ReportsEntry reportsEntry, boolean merge)
        throws SystemException {
        Session session = null;

        try {
            session = openSession();

            if (merge) {
                session.merge(reportsEntry);
            } else {
                if (reportsEntry.isNew()) {
                    session.save(reportsEntry);
                }
            }

            session.flush();

            reportsEntry.setNew(false);

            return reportsEntry;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);

            FinderCacheUtil.clearCache(ReportsEntry.class.getName());
        }
    }

    public ReportsEntry findByPrimaryKey(String entryId)
        throws NoSuchEntryException, SystemException {
        ReportsEntry reportsEntry = fetchByPrimaryKey(entryId);

        if (reportsEntry == null) {
            if (_log.isWarnEnabled()) {
                _log.warn("No ReportsEntry exists with the primary key " +
                    entryId);
            }

            throw new NoSuchEntryException(
                "No ReportsEntry exists with the primary key " + entryId);
        }

        return reportsEntry;
    }

    public ReportsEntry fetchByPrimaryKey(String entryId)
        throws SystemException {
        Session session = null;

        try {
            session = openSession();

            return (ReportsEntry) session.get(ReportsEntryImpl.class, entryId);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    public List<ReportsEntry> findByCompanyId(String companyId)
        throws SystemException {
        boolean finderClassNameCacheEnabled = ReportsEntryModelImpl.CACHE_ENABLED;
        String finderClassName = ReportsEntry.class.getName();
        String finderMethodName = "findByCompanyId";
        String[] finderParams = new String[] { String.class.getName() };
        Object[] finderArgs = new Object[] { companyId };

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

                query.append(
                    "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

                if (companyId == null) {
                    query.append("companyId IS NULL");
                } else {
                    query.append("companyId = ?");
                }

                query.append(" ");

                query.append("ORDER BY ");

                query.append("name ASC");

                Query q = session.createQuery(query.toString());

                QueryPos qPos = QueryPos.getInstance(q);

                if (companyId != null) {
                    qPos.add(companyId);
                }

                List<ReportsEntry> list = q.list();

                FinderCacheUtil.putResult(finderClassNameCacheEnabled,
                    finderClassName, finderMethodName, finderParams,
                    finderArgs, list);

                return list;
            } catch (Exception e) {
                throw processException(e);
            } finally {
                closeSession(session);
            }
        } else {
            return (List<ReportsEntry>) result;
        }
    }

    public List<ReportsEntry> findByCompanyId(String companyId, int start,
        int end) throws SystemException {
        return findByCompanyId(companyId, start, end, null);
    }

    public List<ReportsEntry> findByCompanyId(String companyId, int start,
        int end, OrderByComparator obc) throws SystemException {
        boolean finderClassNameCacheEnabled = ReportsEntryModelImpl.CACHE_ENABLED;
        String finderClassName = ReportsEntry.class.getName();
        String finderMethodName = "findByCompanyId";
        String[] finderParams = new String[] {
                String.class.getName(),
                
                "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            };
        Object[] finderArgs = new Object[] {
                companyId,
                
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

                query.append(
                    "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

                if (companyId == null) {
                    query.append("companyId IS NULL");
                } else {
                    query.append("companyId = ?");
                }

                query.append(" ");

                if (obc != null) {
                    query.append("ORDER BY ");
                    query.append(obc.getOrderBy());
                }
                else {
                    query.append("ORDER BY ");

                    query.append("name ASC");
                }

                Query q = session.createQuery(query.toString());

                QueryPos qPos = QueryPos.getInstance(q);

                if (companyId != null) {
                    qPos.add(companyId);
                }

                List<ReportsEntry> list = (List<ReportsEntry>) QueryUtil.list(q,
                        getDialect(), start, end);

                FinderCacheUtil.putResult(finderClassNameCacheEnabled,
                    finderClassName, finderMethodName, finderParams,
                    finderArgs, list);

                return list;
            } catch (Exception e) {
                throw processException(e);
            } finally {
                closeSession(session);
            }
        } else {
            return (List<ReportsEntry>) result;
        }
    }

    public ReportsEntry findByCompanyId_First(String companyId,
        OrderByComparator obc) throws NoSuchEntryException, SystemException {
        List<ReportsEntry> list = findByCompanyId(companyId, 0, 1, obc);

        if (list.size() == 0) {
            StringBuilder msg = new StringBuilder();

            msg.append("No ReportsEntry exists with the key {");

            msg.append("companyId=" + companyId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchEntryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    public ReportsEntry findByCompanyId_Last(String companyId,
        OrderByComparator obc) throws NoSuchEntryException, SystemException {
        int count = countByCompanyId(companyId);

        List<ReportsEntry> list = findByCompanyId(companyId, count - 1, count,
                obc);

        if (list.size() == 0) {
            StringBuilder msg = new StringBuilder();

            msg.append("No ReportsEntry exists with the key {");

            msg.append("companyId=" + companyId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchEntryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    public ReportsEntry[] findByCompanyId_PrevAndNext(String entryId,
        String companyId, OrderByComparator obc)
        throws NoSuchEntryException, SystemException {
        ReportsEntry reportsEntry = findByPrimaryKey(entryId);

        int count = countByCompanyId(companyId);

        Session session = null;

        try {
            session = openSession();

            StringBuilder query = new StringBuilder();

            query.append(
                "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

            if (companyId == null) {
                query.append("companyId IS NULL");
            } else {
                query.append("companyId = ?");
            }

            query.append(" ");

            if (obc != null) {
                query.append("ORDER BY ");
                query.append(obc.getOrderBy());
            }
            else {
                query.append("ORDER BY ");

                query.append("name ASC");
            }

            Query q = session.createQuery(query.toString());

            QueryPos qPos = QueryPos.getInstance(q);

            if (companyId != null) {
                qPos.add(companyId);
            }

            Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
                    reportsEntry);

            ReportsEntry[] array = new ReportsEntryImpl[3];

            array[0] = (ReportsEntry) objArray[0];
            array[1] = (ReportsEntry) objArray[1];
            array[2] = (ReportsEntry) objArray[2];

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    public List<ReportsEntry> findByUserId(String userId)
        throws SystemException {
        boolean finderClassNameCacheEnabled = ReportsEntryModelImpl.CACHE_ENABLED;
        String finderClassName = ReportsEntry.class.getName();
        String finderMethodName = "findByUserId";
        String[] finderParams = new String[] { String.class.getName() };
        Object[] finderArgs = new Object[] { userId };

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

                query.append(
                    "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

                if (userId == null) {
                    query.append("userId IS NULL");
                } else {
                    query.append("userId = ?");
                }

                query.append(" ");

                query.append("ORDER BY ");

                query.append("name ASC");

                Query q = session.createQuery(query.toString());

                QueryPos qPos = QueryPos.getInstance(q);

                if (userId != null) {
                    qPos.add(userId);
                }

                List<ReportsEntry> list = q.list();

                FinderCacheUtil.putResult(finderClassNameCacheEnabled,
                    finderClassName, finderMethodName, finderParams,
                    finderArgs, list);

                return list;
            } catch (Exception e) {
                throw processException(e);
            } finally {
                closeSession(session);
            }
        } else {
            return (List<ReportsEntry>) result;
        }
    }

    public List<ReportsEntry> findByUserId(String userId, int start, int end)
        throws SystemException {
        return findByUserId(userId, start, end, null);
    }

    public List<ReportsEntry> findByUserId(String userId, int start, int end,
        OrderByComparator obc) throws SystemException {
        boolean finderClassNameCacheEnabled = ReportsEntryModelImpl.CACHE_ENABLED;
        String finderClassName = ReportsEntry.class.getName();
        String finderMethodName = "findByUserId";
        String[] finderParams = new String[] {
                String.class.getName(),
                
                "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            };
        Object[] finderArgs = new Object[] {
                userId,
                
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

                query.append(
                    "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

                if (userId == null) {
                    query.append("userId IS NULL");
                } else {
                    query.append("userId = ?");
                }

                query.append(" ");

                if (obc != null) {
                    query.append("ORDER BY ");
                    query.append(obc.getOrderBy());
                }
                else {
                    query.append("ORDER BY ");

                    query.append("name ASC");
                }

                Query q = session.createQuery(query.toString());

                QueryPos qPos = QueryPos.getInstance(q);

                if (userId != null) {
                    qPos.add(userId);
                }

                List<ReportsEntry> list = (List<ReportsEntry>) QueryUtil.list(q,
                        getDialect(), start, end);

                FinderCacheUtil.putResult(finderClassNameCacheEnabled,
                    finderClassName, finderMethodName, finderParams,
                    finderArgs, list);

                return list;
            } catch (Exception e) {
                throw processException(e);
            } finally {
                closeSession(session);
            }
        } else {
            return (List<ReportsEntry>) result;
        }
    }

    public ReportsEntry findByUserId_First(String userId, OrderByComparator obc)
        throws NoSuchEntryException, SystemException {
        List<ReportsEntry> list = findByUserId(userId, 0, 1, obc);

        if (list.size() == 0) {
            StringBuilder msg = new StringBuilder();

            msg.append("No ReportsEntry exists with the key {");

            msg.append("userId=" + userId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchEntryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    public ReportsEntry findByUserId_Last(String userId, OrderByComparator obc)
        throws NoSuchEntryException, SystemException {
        int count = countByUserId(userId);

        List<ReportsEntry> list = findByUserId(userId, count - 1, count, obc);

        if (list.size() == 0) {
            StringBuilder msg = new StringBuilder();

            msg.append("No ReportsEntry exists with the key {");

            msg.append("userId=" + userId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchEntryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    public ReportsEntry[] findByUserId_PrevAndNext(String entryId,
        String userId, OrderByComparator obc)
        throws NoSuchEntryException, SystemException {
        ReportsEntry reportsEntry = findByPrimaryKey(entryId);

        int count = countByUserId(userId);

        Session session = null;

        try {
            session = openSession();

            StringBuilder query = new StringBuilder();

            query.append(
                "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

            if (userId == null) {
                query.append("userId IS NULL");
            } else {
                query.append("userId = ?");
            }

            query.append(" ");

            if (obc != null) {
                query.append("ORDER BY ");
                query.append(obc.getOrderBy());
            }
            else {
                query.append("ORDER BY ");

                query.append("name ASC");
            }

            Query q = session.createQuery(query.toString());

            QueryPos qPos = QueryPos.getInstance(q);

            if (userId != null) {
                qPos.add(userId);
            }

            Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
                    reportsEntry);

            ReportsEntry[] array = new ReportsEntryImpl[3];

            array[0] = (ReportsEntry) objArray[0];
            array[1] = (ReportsEntry) objArray[1];
            array[2] = (ReportsEntry) objArray[2];

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
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
        } catch (Exception e) {
            throw processException(e);
        } finally {
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
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    public List<ReportsEntry> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    public List<ReportsEntry> findAll(int start, int end)
        throws SystemException {
        return findAll(start, end, null);
    }

    public List<ReportsEntry> findAll(int start, int end, OrderByComparator obc)
        throws SystemException {
        boolean finderClassNameCacheEnabled = ReportsEntryModelImpl.CACHE_ENABLED;
        String finderClassName = ReportsEntry.class.getName();
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

                query.append("FROM com.ext.portlet.reports.model.ReportsEntry ");

                if (obc != null) {
                    query.append("ORDER BY ");
                    query.append(obc.getOrderBy());
                }
                else {
                    query.append("ORDER BY ");

                    query.append("name ASC");
                }

                Query q = session.createQuery(query.toString());

                List<ReportsEntry> list = (List<ReportsEntry>) QueryUtil.list(q,
                        getDialect(), start, end);

                if (obc == null) {
                    Collections.sort(list);
                }

                FinderCacheUtil.putResult(finderClassNameCacheEnabled,
                    finderClassName, finderMethodName, finderParams,
                    finderArgs, list);

                return list;
            } catch (Exception e) {
                throw processException(e);
            } finally {
                closeSession(session);
            }
        } else {
            return (List<ReportsEntry>) result;
        }
    }

    public void removeByCompanyId(String companyId) throws SystemException {
        for (ReportsEntry reportsEntry : findByCompanyId(companyId)) {
            remove(reportsEntry);
        }
    }

    public void removeByUserId(String userId) throws SystemException {
        for (ReportsEntry reportsEntry : findByUserId(userId)) {
            remove(reportsEntry);
        }
    }

    public void removeAll() throws SystemException {
        for (ReportsEntry reportsEntry : findAll()) {
            remove(reportsEntry);
        }
    }

    public int countByCompanyId(String companyId) throws SystemException {
        boolean finderClassNameCacheEnabled = ReportsEntryModelImpl.CACHE_ENABLED;
        String finderClassName = ReportsEntry.class.getName();
        String finderMethodName = "countByCompanyId";
        String[] finderParams = new String[] { String.class.getName() };
        Object[] finderArgs = new Object[] { companyId };

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
                query.append(
                    "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

                if (companyId == null) {
                    query.append("companyId IS NULL");
                } else {
                    query.append("companyId = ?");
                }

                query.append(" ");

                Query q = session.createQuery(query.toString());

                QueryPos qPos = QueryPos.getInstance(q);

                if (companyId != null) {
                    qPos.add(companyId);
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
            } catch (Exception e) {
                throw processException(e);
            } finally {
                closeSession(session);
            }
        } else {
            return ((Long) result).intValue();
        }
    }

    public int countByUserId(String userId) throws SystemException {
        boolean finderClassNameCacheEnabled = ReportsEntryModelImpl.CACHE_ENABLED;
        String finderClassName = ReportsEntry.class.getName();
        String finderMethodName = "countByUserId";
        String[] finderParams = new String[] { String.class.getName() };
        Object[] finderArgs = new Object[] { userId };

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
                query.append(
                    "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

                if (userId == null) {
                    query.append("userId IS NULL");
                } else {
                    query.append("userId = ?");
                }

                query.append(" ");

                Query q = session.createQuery(query.toString());

                QueryPos qPos = QueryPos.getInstance(q);

                if (userId != null) {
                    qPos.add(userId);
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
            } catch (Exception e) {
                throw processException(e);
            } finally {
                closeSession(session);
            }
        } else {
            return ((Long) result).intValue();
        }
    }

    public int countAll() throws SystemException {
        boolean finderClassNameCacheEnabled = ReportsEntryModelImpl.CACHE_ENABLED;
        String finderClassName = ReportsEntry.class.getName();
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
                        "SELECT COUNT(*) FROM com.ext.portlet.reports.model.ReportsEntry");

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
            } catch (Exception e) {
                throw processException(e);
            } finally {
                closeSession(session);
            }
        } else {
            return ((Long) result).intValue();
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
                        "value.object.listener.com.ext.portlet.reports.model.ReportsEntry")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener> listeners = new ArrayList<ModelListener>();

                for (String listenerClassName : listenerClassNames) {
                    listeners.add((ModelListener) Class.forName(
                            listenerClassName).newInstance());
                }

                _listeners = listeners.toArray(new ModelListener[listeners.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }
}
