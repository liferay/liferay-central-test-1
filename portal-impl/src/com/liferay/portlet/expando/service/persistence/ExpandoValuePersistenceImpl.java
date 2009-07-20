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

package com.liferay.portlet.expando.service.persistence;

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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.NoSuchValueException;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.model.impl.ExpandoValueImpl;
import com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpandoValuePersistenceImpl extends BasePersistenceImpl
	implements ExpandoValuePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ExpandoValueImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_TABLEID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByTableId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_TABLEID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByTableId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TABLEID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByTableId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COLUMNID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByColumnId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COLUMNID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByColumnId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COLUMNID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByColumnId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_ROWID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByRowId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ROWID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByRowId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ROWID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByRowId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_T_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_T_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByT_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_T_CPK = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_CPK",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_T_CPK = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_CPK = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByT_CPK",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_T_R = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_T_R = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_R = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByT_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_R = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_R = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_T_C_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByT_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_C_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByT_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_T_C_D = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_T_C_D = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_C_D = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByT_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(ExpandoValue expandoValue) {
		EntityCacheUtil.putResult(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueImpl.class, expandoValue.getPrimaryKey(), expandoValue);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_R,
			new Object[] {
				new Long(expandoValue.getColumnId()),
				new Long(expandoValue.getRowId())
			}, expandoValue);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C_C,
			new Object[] {
				new Long(expandoValue.getTableId()),
				new Long(expandoValue.getColumnId()),
				new Long(expandoValue.getClassPK())
			}, expandoValue);
	}

	public void cacheResult(List<ExpandoValue> expandoValues) {
		for (ExpandoValue expandoValue : expandoValues) {
			if (EntityCacheUtil.getResult(
						ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
						ExpandoValueImpl.class, expandoValue.getPrimaryKey(),
						this) == null) {
				cacheResult(expandoValue);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ExpandoValueImpl.class.getName());
		EntityCacheUtil.clearCache(ExpandoValueImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public ExpandoValue create(long valueId) {
		ExpandoValue expandoValue = new ExpandoValueImpl();

		expandoValue.setNew(true);
		expandoValue.setPrimaryKey(valueId);

		return expandoValue;
	}

	public ExpandoValue remove(long valueId)
		throws NoSuchValueException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ExpandoValue expandoValue = (ExpandoValue)session.get(ExpandoValueImpl.class,
					new Long(valueId));

			if (expandoValue == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ExpandoValue exists with the primary key " +
						valueId);
				}

				throw new NoSuchValueException(
					"No ExpandoValue exists with the primary key " + valueId);
			}

			return remove(expandoValue);
		}
		catch (NoSuchValueException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoValue remove(ExpandoValue expandoValue)
		throws SystemException {
		for (ModelListener<ExpandoValue> listener : listeners) {
			listener.onBeforeRemove(expandoValue);
		}

		expandoValue = removeImpl(expandoValue);

		for (ModelListener<ExpandoValue> listener : listeners) {
			listener.onAfterRemove(expandoValue);
		}

		return expandoValue;
	}

	protected ExpandoValue removeImpl(ExpandoValue expandoValue)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (expandoValue.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ExpandoValueImpl.class,
						expandoValue.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(expandoValue);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ExpandoValueModelImpl expandoValueModelImpl = (ExpandoValueModelImpl)expandoValue;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_R,
			new Object[] {
				new Long(expandoValueModelImpl.getOriginalColumnId()),
				new Long(expandoValueModelImpl.getOriginalRowId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_C_C,
			new Object[] {
				new Long(expandoValueModelImpl.getOriginalTableId()),
				new Long(expandoValueModelImpl.getOriginalColumnId()),
				new Long(expandoValueModelImpl.getOriginalClassPK())
			});

		EntityCacheUtil.removeResult(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueImpl.class, expandoValue.getPrimaryKey());

		return expandoValue;
	}

	/**
	 * @deprecated Use <code>update(ExpandoValue expandoValue, boolean merge)</code>.
	 */
	public ExpandoValue update(ExpandoValue expandoValue)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ExpandoValue expandoValue) method. Use update(ExpandoValue expandoValue, boolean merge) instead.");
		}

		return update(expandoValue, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        expandoValue the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when expandoValue is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ExpandoValue update(ExpandoValue expandoValue, boolean merge)
		throws SystemException {
		boolean isNew = expandoValue.isNew();

		for (ModelListener<ExpandoValue> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(expandoValue);
			}
			else {
				listener.onBeforeUpdate(expandoValue);
			}
		}

		expandoValue = updateImpl(expandoValue, merge);

		for (ModelListener<ExpandoValue> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(expandoValue);
			}
			else {
				listener.onAfterUpdate(expandoValue);
			}
		}

		return expandoValue;
	}

	public ExpandoValue updateImpl(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue,
		boolean merge) throws SystemException {
		boolean isNew = expandoValue.isNew();

		ExpandoValueModelImpl expandoValueModelImpl = (ExpandoValueModelImpl)expandoValue;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, expandoValue, merge);

			expandoValue.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueImpl.class, expandoValue.getPrimaryKey(), expandoValue);

		if (!isNew &&
				((expandoValue.getColumnId() != expandoValueModelImpl.getOriginalColumnId()) ||
				(expandoValue.getRowId() != expandoValueModelImpl.getOriginalRowId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_R,
				new Object[] {
					new Long(expandoValueModelImpl.getOriginalColumnId()),
					new Long(expandoValueModelImpl.getOriginalRowId())
				});
		}

		if (isNew ||
				((expandoValue.getColumnId() != expandoValueModelImpl.getOriginalColumnId()) ||
				(expandoValue.getRowId() != expandoValueModelImpl.getOriginalRowId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_R,
				new Object[] {
					new Long(expandoValue.getColumnId()),
					new Long(expandoValue.getRowId())
				}, expandoValue);
		}

		if (!isNew &&
				((expandoValue.getTableId() != expandoValueModelImpl.getOriginalTableId()) ||
				(expandoValue.getColumnId() != expandoValueModelImpl.getOriginalColumnId()) ||
				(expandoValue.getClassPK() != expandoValueModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_C_C,
				new Object[] {
					new Long(expandoValueModelImpl.getOriginalTableId()),
					new Long(expandoValueModelImpl.getOriginalColumnId()),
					new Long(expandoValueModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				((expandoValue.getTableId() != expandoValueModelImpl.getOriginalTableId()) ||
				(expandoValue.getColumnId() != expandoValueModelImpl.getOriginalColumnId()) ||
				(expandoValue.getClassPK() != expandoValueModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C_C,
				new Object[] {
					new Long(expandoValue.getTableId()),
					new Long(expandoValue.getColumnId()),
					new Long(expandoValue.getClassPK())
				}, expandoValue);
		}

		return expandoValue;
	}

	public ExpandoValue findByPrimaryKey(long valueId)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByPrimaryKey(valueId);

		if (expandoValue == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ExpandoValue exists with the primary key " +
					valueId);
			}

			throw new NoSuchValueException(
				"No ExpandoValue exists with the primary key " + valueId);
		}

		return expandoValue;
	}

	public ExpandoValue fetchByPrimaryKey(long valueId)
		throws SystemException {
		ExpandoValue expandoValue = (ExpandoValue)EntityCacheUtil.getResult(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
				ExpandoValueImpl.class, valueId, this);

		if (expandoValue == null) {
			Session session = null;

			try {
				session = openSession();

				expandoValue = (ExpandoValue)session.get(ExpandoValueImpl.class,
						new Long(valueId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (expandoValue != null) {
					cacheResult(expandoValue);
				}

				closeSession(session);
			}
		}

		return expandoValue;
	}

	public List<ExpandoValue> findByTableId(long tableId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId) };

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TABLEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TABLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoValue> findByTableId(long tableId, int start, int end)
		throws SystemException {
		return findByTableId(tableId, start, end, null);
	}

	public List<ExpandoValue> findByTableId(long tableId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_TABLEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("expandoValue.");
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

					query.append("expandoValue.tableId ASC, ");
					query.append("expandoValue.rowId ASC, ");
					query.append("expandoValue.columnId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_TABLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoValue findByTableId_First(long tableId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByTableId(tableId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByTableId_Last(long tableId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		int count = countByTableId(tableId);

		List<ExpandoValue> list = findByTableId(tableId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByTableId_PrevAndNext(long valueId, long tableId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByTableId(tableId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

			query.append("expandoValue.tableId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("expandoValue.");
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

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByColumnId(long columnId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(columnId) };

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COLUMNID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.columnId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COLUMNID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoValue> findByColumnId(long columnId, int start, int end)
		throws SystemException {
		return findByColumnId(columnId, start, end, null);
	}

	public List<ExpandoValue> findByColumnId(long columnId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(columnId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COLUMNID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.columnId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("expandoValue.");
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

					query.append("expandoValue.tableId ASC, ");
					query.append("expandoValue.rowId ASC, ");
					query.append("expandoValue.columnId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COLUMNID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoValue findByColumnId_First(long columnId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByColumnId(columnId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("columnId=" + columnId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByColumnId_Last(long columnId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		int count = countByColumnId(columnId);

		List<ExpandoValue> list = findByColumnId(columnId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("columnId=" + columnId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByColumnId_PrevAndNext(long valueId,
		long columnId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByColumnId(columnId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

			query.append("expandoValue.columnId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("expandoValue.");
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

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(columnId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByRowId(long rowId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(rowId) };

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ROWID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.rowId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(rowId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ROWID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoValue> findByRowId(long rowId, int start, int end)
		throws SystemException {
		return findByRowId(rowId, start, end, null);
	}

	public List<ExpandoValue> findByRowId(long rowId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(rowId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ROWID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.rowId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("expandoValue.");
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

					query.append("expandoValue.tableId ASC, ");
					query.append("expandoValue.rowId ASC, ");
					query.append("expandoValue.columnId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(rowId);

				list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ROWID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoValue findByRowId_First(long rowId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByRowId(rowId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("rowId=" + rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByRowId_Last(long rowId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		int count = countByRowId(rowId);

		List<ExpandoValue> list = findByRowId(rowId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("rowId=" + rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByRowId_PrevAndNext(long valueId, long rowId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByRowId(rowId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

			query.append("expandoValue.rowId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("expandoValue.");
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

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(rowId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByT_C(long tableId, long columnId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(columnId) };

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_T_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" AND ");

				query.append("expandoValue.columnId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_T_C, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoValue> findByT_C(long tableId, long columnId, int start,
		int end) throws SystemException {
		return findByT_C(tableId, columnId, start, end, null);
	}

	public List<ExpandoValue> findByT_C(long tableId, long columnId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_T_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" AND ");

				query.append("expandoValue.columnId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("expandoValue.");
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

					query.append("expandoValue.tableId ASC, ");
					query.append("expandoValue.rowId ASC, ");
					query.append("expandoValue.columnId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_T_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoValue findByT_C_First(long tableId, long columnId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByT_C(tableId, columnId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("columnId=" + columnId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByT_C_Last(long tableId, long columnId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		int count = countByT_C(tableId, columnId);

		List<ExpandoValue> list = findByT_C(tableId, columnId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("columnId=" + columnId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByT_C_PrevAndNext(long valueId, long tableId,
		long columnId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByT_C(tableId, columnId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

			query.append("expandoValue.tableId = ?");

			query.append(" AND ");

			query.append("expandoValue.columnId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("expandoValue.");
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

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			qPos.add(columnId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByT_CPK(long tableId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(classPK) };

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_T_CPK,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" AND ");

				query.append("expandoValue.classPK = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(classPK);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_T_CPK,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoValue> findByT_CPK(long tableId, long classPK,
		int start, int end) throws SystemException {
		return findByT_CPK(tableId, classPK, start, end, null);
	}

	public List<ExpandoValue> findByT_CPK(long tableId, long classPK,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(classPK),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_T_CPK,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" AND ");

				query.append("expandoValue.classPK = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("expandoValue.");
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

					query.append("expandoValue.tableId ASC, ");
					query.append("expandoValue.rowId ASC, ");
					query.append("expandoValue.columnId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(classPK);

				list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_T_CPK,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoValue findByT_CPK_First(long tableId, long classPK,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByT_CPK(tableId, classPK, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByT_CPK_Last(long tableId, long classPK,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		int count = countByT_CPK(tableId, classPK);

		List<ExpandoValue> list = findByT_CPK(tableId, classPK, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByT_CPK_PrevAndNext(long valueId, long tableId,
		long classPK, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByT_CPK(tableId, classPK);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

			query.append("expandoValue.tableId = ?");

			query.append(" AND ");

			query.append("expandoValue.classPK = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("expandoValue.");
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

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByT_R(long tableId, long rowId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(rowId) };

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_T_R,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" AND ");

				query.append("expandoValue.rowId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(rowId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_T_R, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoValue> findByT_R(long tableId, long rowId, int start,
		int end) throws SystemException {
		return findByT_R(tableId, rowId, start, end, null);
	}

	public List<ExpandoValue> findByT_R(long tableId, long rowId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(rowId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_T_R,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" AND ");

				query.append("expandoValue.rowId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("expandoValue.");
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

					query.append("expandoValue.tableId ASC, ");
					query.append("expandoValue.rowId ASC, ");
					query.append("expandoValue.columnId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(rowId);

				list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_T_R,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoValue findByT_R_First(long tableId, long rowId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByT_R(tableId, rowId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("rowId=" + rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByT_R_Last(long tableId, long rowId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		int count = countByT_R(tableId, rowId);

		List<ExpandoValue> list = findByT_R(tableId, rowId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("rowId=" + rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByT_R_PrevAndNext(long valueId, long tableId,
		long rowId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByT_R(tableId, rowId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

			query.append("expandoValue.tableId = ?");

			query.append(" AND ");

			query.append("expandoValue.rowId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("expandoValue.");
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

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			qPos.add(rowId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoValue findByC_R(long columnId, long rowId)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByC_R(columnId, rowId);

		if (expandoValue == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("columnId=" + columnId);

			msg.append(", ");
			msg.append("rowId=" + rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchValueException(msg.toString());
		}

		return expandoValue;
	}

	public ExpandoValue fetchByC_R(long columnId, long rowId)
		throws SystemException {
		return fetchByC_R(columnId, rowId, true);
	}

	public ExpandoValue fetchByC_R(long columnId, long rowId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(columnId), new Long(rowId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_R,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.columnId = ?");

				query.append(" AND ");

				query.append("expandoValue.rowId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				qPos.add(rowId);

				List<ExpandoValue> list = q.list();

				result = list;

				ExpandoValue expandoValue = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_R,
						finderArgs, list);
				}
				else {
					expandoValue = list.get(0);

					cacheResult(expandoValue);

					if ((expandoValue.getColumnId() != columnId) ||
							(expandoValue.getRowId() != rowId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_R,
							finderArgs, expandoValue);
					}
				}

				return expandoValue;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_R,
						finderArgs, new ArrayList<ExpandoValue>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (ExpandoValue)result;
			}
		}
	}

	public List<ExpandoValue> findByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.classNameId = ?");

				query.append(" AND ");

				query.append("expandoValue.classPK = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");

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
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoValue> findByC_C(long classNameId, long classPK,
		int start, int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	public List<ExpandoValue> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.classNameId = ?");

				query.append(" AND ");

				query.append("expandoValue.classPK = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("expandoValue.");
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

					query.append("expandoValue.tableId ASC, ");
					query.append("expandoValue.rowId ASC, ");
					query.append("expandoValue.columnId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoValue findByC_C_First(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByC_C(classNameId, classPK, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByC_C_Last(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		int count = countByC_C(classNameId, classPK);

		List<ExpandoValue> list = findByC_C(classNameId, classPK, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByC_C_PrevAndNext(long valueId, long classNameId,
		long classPK, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByC_C(classNameId, classPK);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

			query.append("expandoValue.classNameId = ?");

			query.append(" AND ");

			query.append("expandoValue.classPK = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("expandoValue.");
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

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoValue findByT_C_C(long tableId, long columnId, long classPK)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByT_C_C(tableId, columnId, classPK);

		if (expandoValue == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("columnId=" + columnId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchValueException(msg.toString());
		}

		return expandoValue;
	}

	public ExpandoValue fetchByT_C_C(long tableId, long columnId, long classPK)
		throws SystemException {
		return fetchByT_C_C(tableId, columnId, classPK, true);
	}

	public ExpandoValue fetchByT_C_C(long tableId, long columnId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId), new Long(classPK)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_T_C_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" AND ");

				query.append("expandoValue.columnId = ?");

				query.append(" AND ");

				query.append("expandoValue.classPK = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				qPos.add(classPK);

				List<ExpandoValue> list = q.list();

				result = list;

				ExpandoValue expandoValue = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C_C,
						finderArgs, list);
				}
				else {
					expandoValue = list.get(0);

					cacheResult(expandoValue);

					if ((expandoValue.getTableId() != tableId) ||
							(expandoValue.getColumnId() != columnId) ||
							(expandoValue.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C_C,
							finderArgs, expandoValue);
					}
				}

				return expandoValue;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C_C,
						finderArgs, new ArrayList<ExpandoValue>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (ExpandoValue)result;
			}
		}
	}

	public List<ExpandoValue> findByT_C_D(long tableId, long columnId,
		String data) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId),
				
				data
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_T_C_D,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" AND ");

				query.append("expandoValue.columnId = ?");

				query.append(" AND ");

				if (data == null) {
					query.append("expandoValue.data IS NULL");
				}
				else {
					query.append("expandoValue.data = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				if (data != null) {
					qPos.add(data);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_T_C_D,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoValue> findByT_C_D(long tableId, long columnId,
		String data, int start, int end) throws SystemException {
		return findByT_C_D(tableId, columnId, data, start, end, null);
	}

	public List<ExpandoValue> findByT_C_D(long tableId, long columnId,
		String data, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId),
				
				data,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_T_C_D,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" AND ");

				query.append("expandoValue.columnId = ?");

				query.append(" AND ");

				if (data == null) {
					query.append("expandoValue.data IS NULL");
				}
				else {
					query.append("expandoValue.data = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("expandoValue.");
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

					query.append("expandoValue.tableId ASC, ");
					query.append("expandoValue.rowId ASC, ");
					query.append("expandoValue.columnId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				if (data != null) {
					qPos.add(data);
				}

				list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_T_C_D,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoValue findByT_C_D_First(long tableId, long columnId,
		String data, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByT_C_D(tableId, columnId, data, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("columnId=" + columnId);

			msg.append(", ");
			msg.append("data=" + data);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByT_C_D_Last(long tableId, long columnId,
		String data, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		int count = countByT_C_D(tableId, columnId, data);

		List<ExpandoValue> list = findByT_C_D(tableId, columnId, data,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("columnId=" + columnId);

			msg.append(", ");
			msg.append("data=" + data);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByT_C_D_PrevAndNext(long valueId, long tableId,
		long columnId, String data, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByT_C_D(tableId, columnId, data);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT expandoValue FROM ExpandoValue expandoValue WHERE ");

			query.append("expandoValue.tableId = ?");

			query.append(" AND ");

			query.append("expandoValue.columnId = ?");

			query.append(" AND ");

			if (data == null) {
				query.append("expandoValue.data IS NULL");
			}
			else {
				query.append("expandoValue.data = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("expandoValue.");
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

				query.append("expandoValue.tableId ASC, ");
				query.append("expandoValue.rowId ASC, ");
				query.append("expandoValue.columnId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			qPos.add(columnId);

			if (data != null) {
				qPos.add(data);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

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

	public List<ExpandoValue> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoValue> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ExpandoValue> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT expandoValue FROM ExpandoValue expandoValue ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("expandoValue.");
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

					query.append("expandoValue.tableId ASC, ");
					query.append("expandoValue.rowId ASC, ");
					query.append("expandoValue.columnId ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByTableId(long tableId) throws SystemException {
		for (ExpandoValue expandoValue : findByTableId(tableId)) {
			remove(expandoValue);
		}
	}

	public void removeByColumnId(long columnId) throws SystemException {
		for (ExpandoValue expandoValue : findByColumnId(columnId)) {
			remove(expandoValue);
		}
	}

	public void removeByRowId(long rowId) throws SystemException {
		for (ExpandoValue expandoValue : findByRowId(rowId)) {
			remove(expandoValue);
		}
	}

	public void removeByT_C(long tableId, long columnId)
		throws SystemException {
		for (ExpandoValue expandoValue : findByT_C(tableId, columnId)) {
			remove(expandoValue);
		}
	}

	public void removeByT_CPK(long tableId, long classPK)
		throws SystemException {
		for (ExpandoValue expandoValue : findByT_CPK(tableId, classPK)) {
			remove(expandoValue);
		}
	}

	public void removeByT_R(long tableId, long rowId) throws SystemException {
		for (ExpandoValue expandoValue : findByT_R(tableId, rowId)) {
			remove(expandoValue);
		}
	}

	public void removeByC_R(long columnId, long rowId)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByC_R(columnId, rowId);

		remove(expandoValue);
	}

	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (ExpandoValue expandoValue : findByC_C(classNameId, classPK)) {
			remove(expandoValue);
		}
	}

	public void removeByT_C_C(long tableId, long columnId, long classPK)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByT_C_C(tableId, columnId, classPK);

		remove(expandoValue);
	}

	public void removeByT_C_D(long tableId, long columnId, String data)
		throws SystemException {
		for (ExpandoValue expandoValue : findByT_C_D(tableId, columnId, data)) {
			remove(expandoValue);
		}
	}

	public void removeAll() throws SystemException {
		for (ExpandoValue expandoValue : findAll()) {
			remove(expandoValue);
		}
	}

	public int countByTableId(long tableId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TABLEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(expandoValue) ");
				query.append("FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TABLEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByColumnId(long columnId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(columnId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COLUMNID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(expandoValue) ");
				query.append("FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.columnId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COLUMNID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByRowId(long rowId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(rowId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ROWID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(expandoValue) ");
				query.append("FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.rowId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(rowId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ROWID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByT_C(long tableId, long columnId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(columnId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(expandoValue) ");
				query.append("FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" AND ");

				query.append("expandoValue.columnId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByT_CPK(long tableId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(classPK) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_CPK,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(expandoValue) ");
				query.append("FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" AND ");

				query.append("expandoValue.classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_CPK,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByT_R(long tableId, long rowId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(rowId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_R,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(expandoValue) ");
				query.append("FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" AND ");

				query.append("expandoValue.rowId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(rowId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_R, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_R(long columnId, long rowId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(columnId), new Long(rowId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_R,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(expandoValue) ");
				query.append("FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.columnId = ?");

				query.append(" AND ");

				query.append("expandoValue.rowId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				qPos.add(rowId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_R, finderArgs,
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

				query.append("SELECT COUNT(expandoValue) ");
				query.append("FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.classNameId = ?");

				query.append(" AND ");

				query.append("expandoValue.classPK = ?");

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

	public int countByT_C_C(long tableId, long columnId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId), new Long(classPK)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(expandoValue) ");
				query.append("FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" AND ");

				query.append("expandoValue.columnId = ?");

				query.append(" AND ");

				query.append("expandoValue.classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_C_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByT_C_D(long tableId, long columnId, String data)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId),
				
				data
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_C_D,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(expandoValue) ");
				query.append("FROM ExpandoValue expandoValue WHERE ");

				query.append("expandoValue.tableId = ?");

				query.append(" AND ");

				query.append("expandoValue.columnId = ?");

				query.append(" AND ");

				if (data == null) {
					query.append("expandoValue.data IS NULL");
				}
				else {
					query.append("expandoValue.data = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				if (data != null) {
					qPos.add(data);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_C_D,
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
						"SELECT COUNT(expandoValue) FROM ExpandoValue expandoValue");

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
						"value.object.listener.com.liferay.portlet.expando.model.ExpandoValue")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ExpandoValue>> listenersList = new ArrayList<ModelListener<ExpandoValue>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ExpandoValue>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence expandoColumnPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence expandoRowPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence expandoTablePersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	private static Log _log = LogFactoryUtil.getLog(ExpandoValuePersistenceImpl.class);
}