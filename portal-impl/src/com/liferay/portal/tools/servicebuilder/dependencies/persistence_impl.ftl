package ${packagePath}.service.persistence;

<#assign noSuchEntity = serviceBuilder.getNoSuchEntityException(entity)>

import ${packagePath}.${noSuchEntity}Exception;
import ${packagePath}.model.${entity.name};
import ${packagePath}.model.impl.${entity.name}Impl;
import ${packagePath}.model.impl.${entity.name}ModelImpl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ${entity.name}PersistenceImpl extends BasePersistenceImpl implements ${entity.name}Persistence {

	public ${entity.name} create(${entity.PKClassName} ${entity.PKVarName}) {
		${entity.name} ${entity.varName} = new ${entity.name}Impl();

		${entity.varName}.setNew(true);
		${entity.varName}.setPrimaryKey(${entity.PKVarName});

		<#if entity.hasUuid()>
			String uuid = PortalUUIDUtil.generate();

			${entity.varName}.setUuid(uuid);
		</#if>

		return ${entity.varName};
	}

	public ${entity.name} remove(${entity.PKClassName} ${entity.PKVarName}) throws ${noSuchEntity}Exception, SystemException {
		Session session = null;

		try {
			session = openSession();

			${entity.name} ${entity.varName} = (${entity.name})session.get(${entity.name}Impl.class,

			<#if entity.hasPrimitivePK()>
				new ${serviceBuilder.getPrimitiveObj("${entity.PKClassName}")}(
			</#if>

			${entity.PKVarName}

			<#if entity.hasPrimitivePK()>
				)
			</#if>

			);

			if (${entity.varName} == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ${entity.name} exists with the primary key " + ${entity.PKVarName});
				}

				throw new ${noSuchEntity}Exception("No ${entity.name} exists with the primary key " + ${entity.PKVarName});
			}

			return remove(${entity.varName});
		}
		catch (${noSuchEntity}Exception nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ${entity.name} remove(${entity.name} ${entity.varName}) throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(${entity.varName});
			}
		}

		${entity.varName} = removeImpl(${entity.varName});

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(${entity.varName});
			}
		}

		return ${entity.varName};
	}

	protected ${entity.name} removeImpl(${entity.name} ${entity.varName}) throws SystemException {
		<#list entity.columnList as column>
			<#if column.isCollection() && column.isMappingManyToMany()>
				<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>

				try {
					clear${tempEntity.names}.clear(${entity.varName}.getPrimaryKey());
				}
				catch (Exception e) {
					throw processException(e);
				}
				finally {
					FinderCacheUtil.clearCache("${column.mappingTable}");
				}
			</#if>
		</#list>

		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(${entity.name}Impl.class, ${entity.varName}.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(${entity.varName});

			session.flush();

			return ${entity.varName};
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(${entity.name}.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(${entity.name} ${entity.varName}, boolean merge)</code>.
	 */
	public ${entity.name} update(${entity.name} ${entity.varName}) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn("Using the deprecated update(${entity.name} ${entity.varName}) method. Use update(${entity.name} ${entity.varName}, boolean merge) instead.");
		}

		return update(${entity.varName}, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param		${entity.varName} the entity to add, update, or merge
	 * @param		merge boolean value for whether to merge the entity. The
	 *				default value is false. Setting merge to true is more
	 *				expensive and should only be true when ${entity.varName} is
	 *				transient. See LEP-5473 for a detailed discussion of this
	 *				method.
	 * @return		true if the portlet can be displayed via Ajax
	 */
	public ${entity.name} update(${entity.name} ${entity.varName}, boolean merge) throws SystemException {
		boolean isNew = ${entity.varName}.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(${entity.varName});
				}
				else {
					listener.onBeforeUpdate(${entity.varName});
				}
			}
		}

		${entity.varName} = updateImpl(${entity.varName}, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(${entity.varName});
				}
				else {
					listener.onAfterUpdate(${entity.varName});
				}
			}
		}

		return ${entity.varName};
	}

	public ${entity.name} updateImpl(${packagePath}.model.${entity.name} ${entity.varName}, boolean merge) throws SystemException {
		<#list entity.columnList as column>
			<#if column.isCollection() && column.isMappingManyToMany()>
				FinderCacheUtil.clearCache("${column.mappingTable}");
			</#if>
		</#list>

		<#if entity.hasUuid()>
			if (Validator.isNull(${entity.varName}.getUuid())) {
				String uuid = PortalUUIDUtil.generate();

				${entity.varName}.setUuid(uuid);
			}
		</#if>

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, ${entity.varName}, merge);

			${entity.varName}.setNew(false);

			return ${entity.varName};
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(${entity.name}.class.getName());
		}
	}

	public ${entity.name} findByPrimaryKey(${entity.PKClassName} ${entity.PKVarName}) throws ${noSuchEntity}Exception, SystemException {
		${entity.name} ${entity.varName} = fetchByPrimaryKey(${entity.PKVarName});

		if (${entity.varName} == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ${entity.name} exists with the primary key " + ${entity.PKVarName});
			}

			throw new ${noSuchEntity}Exception("No ${entity.name} exists with the primary key " + ${entity.PKVarName});
		}

		return ${entity.varName};
	}

	public ${entity.name} fetchByPrimaryKey(${entity.PKClassName} ${entity.PKVarName}) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (${entity.name})session.get(${entity.name}Impl.class,

			<#if entity.hasPrimitivePK()>
				new ${serviceBuilder.getPrimitiveObj("${entity.PKClassName}")}(
			</#if>

			${entity.PKVarName}

			<#if entity.hasPrimitivePK()>
				)
			</#if>

			);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	<#list entity.getFinderList() as finder>
		<#assign finderColsList = finder.getColumns()>

		<#if finder.isCollection()>
			public List<${entity.name}> findBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}

				<#if finderCol_has_next>
					,
				</#if>
			</#list>

			) throws SystemException {
				boolean finderClassNameCacheEnabled = ${entity.name}ModelImpl.CACHE_ENABLED;
				String finderClassName = ${entity.name}.class.getName();
				String finderMethodName = "findBy${finder.name}";
				String[] finderParams = new String[] {
					<#list finderColsList as finderCol>
						${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.class.getName()

						<#if finderCol_has_next>
							,
						</#if>
					</#list>
				};
				Object[] finderArgs = new Object[] {
					<#list finderColsList as finderCol>
						<#if finderCol.isPrimitiveType()>
							<#if finderCol.type == "boolean">
								Boolean.valueOf(
							<#else>
								new ${serviceBuilder.getPrimitiveObj("${finderCol.type}")}(
							</#if>
						</#if>

						${finderCol.name}

						<#if finderCol.isPrimitiveType()>
							)
						</#if>

						<#if finderCol_has_next>
							,
						</#if>
					</#list>
				};

				Object result = null;

				if (finderClassNameCacheEnabled) {
					result = FinderCacheUtil.getResult(finderClassName, finderMethodName, finderParams, finderArgs, this);
				}

				if (result == null) {
					Session session = null;

					try {
						session = openSession();

						StringBuilder query = new StringBuilder();

						query.append("FROM ${packagePath}.model.${entity.name} WHERE ");

						<#list finderColsList as finderCol>
							<#if !finderCol.isPrimitiveType()>
								if (${finderCol.name} == null) {
									<#if finderCol.comparator == "=">
										query.append("${finderCol.DBName} IS NULL");
									<#elseif finderCol.comparator == "<>" || finderCol.comparator = "!=">
										query.append("${finderCol.DBName} IS NOT NULL");
									<#else>
										query.append("${finderCol.DBName} ${finderCol.comparator} null");
									</#if>
								}
								else {
							</#if>

							<#if finderCol.type == "String" && !finderCol.isCaseSensitive()>
								query.append("lower(${finderCol.DBName}) ${finderCol.comparator} ?");
							<#else>
								query.append("${finderCol.DBName} ${finderCol.comparator} ?");
							</#if>

							<#if !finderCol.isPrimitiveType()>
								}
							</#if>

							<#if finderCol_has_next>
								query.append(" AND ");
							<#elseif finder.where?? && !validator.isNull(finder.getWhere())>
								query.append(" AND ${finder.where} ");
							<#else>
								query.append(" ");
							</#if>
						</#list>

						<#if entity.getOrder()??>
							query.append("ORDER BY ");

							<#assign orderList = entity.getOrder().getColumns()>

							<#list orderList as order>
								query.append("${order.DBName} <#if order.isOrderByAscending()>ASC<#else>DESC</#if><#if order_has_next>, </#if>");
							</#list>
						</#if>

						Query q = session.createQuery(query.toString());

						QueryPos qPos = QueryPos.getInstance(q);

						<#list finderColsList as finderCol>
							<#if !finderCol.isPrimitiveType()>
								if (${finderCol.name} != null) {
							</#if>

							qPos.add(

							<#if finderCol.type == "Date">
								CalendarUtil.getTimestamp(
							</#if>

							${finderCol.name}${serviceBuilder.getPrimitiveObjValue("${finderCol.type}")}

							<#if finderCol.type == "Date">
								)
							</#if>

							);

							<#if !finderCol.isPrimitiveType()>
								}
							</#if>
						</#list>

						List<${entity.name}> list = q.list();

						FinderCacheUtil.putResult(finderClassNameCacheEnabled, finderClassName, finderMethodName, finderParams, finderArgs, list);

						return list;
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						closeSession(session);
					}
				}
				else {
					return (List<${entity.name}>)result;
				}
			}

			public List<${entity.name}> findBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			int start, int end) throws SystemException {
				return findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name},
				</#list>

				start, end, null);
			}

			public List<${entity.name}> findBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			int start, int end, OrderByComparator obc) throws SystemException {
				boolean finderClassNameCacheEnabled = ${entity.name}ModelImpl.CACHE_ENABLED;
				String finderClassName = ${entity.name}.class.getName();
				String finderMethodName = "findBy${finder.name}";
				String[] finderParams = new String[] {
					<#list finderColsList as finderCol>
						${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.class.getName(),
					</#list>

					"java.lang.Integer", "java.lang.Integer", "com.liferay.portal.kernel.util.OrderByComparator"
				};
				Object[] finderArgs = new Object[] {
					<#list finderColsList as finderCol>
						<#if finderCol.isPrimitiveType()>
							<#if finderCol.type == "boolean">
								Boolean.valueOf(
							<#else>
								new ${serviceBuilder.getPrimitiveObj("${finderCol.type}")}(
							</#if>
						</#if>

						${finderCol.name}

						<#if finderCol.isPrimitiveType()>
							)
						</#if>

						,
					</#list>

					String.valueOf(start), String.valueOf(end), String.valueOf(obc)
				};

				Object result = null;

				if (finderClassNameCacheEnabled) {
					result = FinderCacheUtil.getResult(finderClassName, finderMethodName, finderParams, finderArgs, this);
				}

				if (result == null) {
					Session session = null;

					try {
						session = openSession();

						StringBuilder query = new StringBuilder();

						query.append("FROM ${packagePath}.model.${entity.name} WHERE ");

						<#list finderColsList as finderCol>
							<#if !finderCol.isPrimitiveType()>
								if (${finderCol.name} == null) {
									<#if finderCol.comparator == "=">
										query.append("${finderCol.DBName} IS NULL");
									<#elseif finderCol.comparator == "<>" || finderCol.comparator = "!=">
										query.append("${finderCol.DBName} IS NOT NULL");
									<#else>
										query.append("${finderCol.DBName} ${finderCol.comparator} null");
									</#if>
								}
								else {
							</#if>

							<#if finderCol.type == "String" && !finderCol.isCaseSensitive()>
								query.append("lower(${finderCol.DBName}) ${finderCol.comparator} ?");
							<#else>
								query.append("${finderCol.DBName} ${finderCol.comparator} ?");
							</#if>

							<#if !finderCol.isPrimitiveType()>
								}
							</#if>

							<#if finderCol_has_next>
								query.append(" AND ");
							<#elseif finder.where?? && !validator.isNull(finder.getWhere())>
								query.append(" AND ${finder.where} ");
							<#else>
								query.append(" ");
							</#if>
						</#list>

						if (obc != null) {
							query.append("ORDER BY ");
							query.append(obc.getOrderBy());
						}

						<#if entity.getOrder()??>
							else {
								query.append("ORDER BY ");

								<#assign orderList = entity.getOrder().getColumns()>

								<#list orderList as order>
									query.append("${order.DBName} <#if order.isOrderByAscending()>ASC<#else>DESC</#if><#if order_has_next>, </#if>");
								</#list>
							}
						</#if>

						Query q = session.createQuery(query.toString());

						QueryPos qPos = QueryPos.getInstance(q);

						<#list finderColsList as finderCol>
							<#if !finderCol.isPrimitiveType()>
								if (${finderCol.name} != null) {
							</#if>

							qPos.add(

							<#if finderCol.type == "Date">
								CalendarUtil.getTimestamp(
							</#if>

							${finderCol.name}${serviceBuilder.getPrimitiveObjValue("${finderCol.type}")}

							<#if finderCol.type == "Date">
								)
							</#if>

							);

							<#if !finderCol.isPrimitiveType()>
								}
							</#if>
						</#list>

						List<${entity.name}> list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);

						FinderCacheUtil.putResult(finderClassNameCacheEnabled, finderClassName, finderMethodName, finderParams, finderArgs, list);

						return list;
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						closeSession(session);
					}
				}
				else {
					return (List<${entity.name}>)result;
				}
			}

			public ${entity.name} findBy${finder.name}_First(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			OrderByComparator obc) throws ${noSuchEntity}Exception, SystemException {
				List<${entity.name}> list = findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name},
				</#list>

				0, 1, obc);

				if (list.size() == 0) {
					StringBuilder msg = new StringBuilder();

					msg.append("No ${entity.name} exists with the key {");

					<#list finderColsList as finderCol>
						msg.append("${finderCol.name}=" + ${finderCol.name});

						<#if finderCol_has_next>
							msg.append(", ");
						<#else>
							msg.append(StringPool.CLOSE_CURLY_BRACE);
						</#if>
					</#list>

					throw new ${noSuchEntity}Exception(msg.toString());
				}
				else {
					return list.get(0);
				}
			}

			public ${entity.name} findBy${finder.name}_Last(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			OrderByComparator obc) throws ${noSuchEntity}Exception, SystemException {
				int count = countBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name}

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				);

				List<${entity.name}> list = findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name},
				</#list>

				count - 1, count, obc);

				if (list.size() == 0) {
					StringBuilder msg = new StringBuilder();

					msg.append("No ${entity.name} exists with the key {");

					<#list finderColsList as finderCol>
						msg.append("${finderCol.name}=" + ${finderCol.name});

						<#if finderCol_has_next>
							msg.append(", ");
						<#else>
							msg.append(StringPool.CLOSE_CURLY_BRACE);
						</#if>
					</#list>

					throw new ${noSuchEntity}Exception(msg.toString());
				}
				else {
					return list.get(0);
				}
			}

			public ${entity.name}[] findBy${finder.name}_PrevAndNext(${entity.PKClassName} ${entity.PKVarName},

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			OrderByComparator obc) throws ${noSuchEntity}Exception, SystemException {
				${entity.name} ${entity.varName} = findByPrimaryKey(${entity.PKVarName});

				int count = countBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name}

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				);

				Session session = null;

				try {
					session = openSession();

					StringBuilder query = new StringBuilder();

					query.append("FROM ${packagePath}.model.${entity.name} WHERE ");

					<#list finderColsList as finderCol>
						<#if !finderCol.isPrimitiveType()>
							if (${finderCol.name} == null) {
								<#if finderCol.comparator == "=">
									query.append("${finderCol.DBName} IS NULL");
								<#elseif finderCol.comparator == "<>" || finderCol.comparator = "!=">
									query.append("${finderCol.DBName} IS NOT NULL");
								<#else>
									query.append("${finderCol.DBName} ${finderCol.comparator} null");
								</#if>
							}
							else {
						</#if>

						<#if finderCol.type == "String" && !finderCol.isCaseSensitive()>
							query.append("lower(${finderCol.DBName}) ${finderCol.comparator} ?");
						<#else>
							query.append("${finderCol.DBName} ${finderCol.comparator} ?");
						</#if>

						<#if !finderCol.isPrimitiveType()>
							}
						</#if>

						<#if finderCol_has_next>
							query.append(" AND ");
						<#elseif finder.where?? && !validator.isNull(finder.getWhere())>
							query.append(" AND ${finder.where} ");
						<#else>
							query.append(" ");
						</#if>
					</#list>

					if (obc != null) {
						query.append("ORDER BY ");
						query.append(obc.getOrderBy());
					}

					<#if entity.getOrder()??>
						else {
							query.append("ORDER BY ");

							<#assign orderList = entity.getOrder().getColumns()>

							<#list orderList as order>
								query.append("${order.DBName} <#if order.isOrderByAscending()>ASC<#else>DESC</#if><#if order_has_next>, </#if>");
							</#list>
						}
					</#if>

					Query q = session.createQuery(query.toString());

					QueryPos qPos = QueryPos.getInstance(q);

					<#list finderColsList as finderCol>
						<#if !finderCol.isPrimitiveType()>
							if (${finderCol.name} != null) {
						</#if>

						qPos.add(

						<#if finderCol.type == "Date">
							CalendarUtil.getTimestamp(
						</#if>

						${finderCol.name}${serviceBuilder.getPrimitiveObjValue("${finderCol.type}")}

						<#if finderCol.type == "Date">
							)
						</#if>

						);

						<#if !finderCol.isPrimitiveType()>
							}
						</#if>

					</#list>

					Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, ${entity.varName});

					${entity.name}[] array = new ${entity.name}Impl[3];

					array[0] = (${entity.name})objArray[0];
					array[1] = (${entity.name})objArray[1];
					array[2] = (${entity.name})objArray[2];

					return array;
				}
				catch (Exception e) {
					throw processException(e);
				}
				finally {
					closeSession(session);
				}
			}
		<#else>
			public ${entity.name} findBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}

				<#if finderCol_has_next>
					,
				</#if>
			</#list>

			) throws ${noSuchEntity}Exception, SystemException {
				${entity.name} ${entity.varName} = fetchBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name}

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				);

				if ( ${entity.varName} == null) {
					StringBuilder msg = new StringBuilder();

					msg.append("No ${entity.name} exists with the key {");

					<#list finderColsList as finderCol>
						msg.append("${finderCol.name}=" + ${finderCol.name});

						<#if finderCol_has_next>
							msg.append(", ");
						<#else>
							msg.append(StringPool.CLOSE_CURLY_BRACE);
						</#if>
					</#list>

					if (_log.isWarnEnabled()) {
						_log.warn(msg.toString());
					}

					throw new ${noSuchEntity}Exception(msg.toString());
				}

				return ${entity.varName};
			}

			public ${entity.name} fetchBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}

				<#if finderCol_has_next>
					,
				</#if>
			</#list>

			) throws SystemException {
				boolean finderClassNameCacheEnabled = ${entity.name}ModelImpl.CACHE_ENABLED;
				String finderClassName = ${entity.name}.class.getName();
				String finderMethodName = "fetchBy${finder.name}";
				String[] finderParams = new String[] {
					<#list finderColsList as finderCol>
						${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.class.getName()

						<#if finderCol_has_next>
							,
						</#if>
					</#list>
				};
				Object[] finderArgs = new Object[] {
					<#list finderColsList as finderCol>
						<#if finderCol.isPrimitiveType()>
							<#if finderCol.type == "boolean">
								Boolean.valueOf(
							<#else>
								new ${serviceBuilder.getPrimitiveObj("${finderCol.type}")}(
							</#if>
						</#if>

						${finderCol.name}

						<#if finderCol.isPrimitiveType()>
							)
						</#if>

						<#if finderCol_has_next>
							,
						</#if>
					</#list>
				};

				Object result = null;

				if (finderClassNameCacheEnabled) {
					result = FinderCacheUtil.getResult(finderClassName, finderMethodName, finderParams, finderArgs, this);
				}

				if (result == null) {
					Session session = null;

					try {
						session = openSession();

						StringBuilder query = new StringBuilder();

						query.append("FROM ${packagePath}.model.${entity.name} WHERE ");

						<#list finderColsList as finderCol>
							<#if !finderCol.isPrimitiveType()>
								if (${finderCol.name} == null) {
									<#if finderCol.comparator == "=">
										query.append("${finderCol.DBName} IS NULL");
									<#elseif finderCol.comparator == "<>" || finderCol.comparator = "!=">
										query.append("${finderCol.DBName} IS NOT NULL");
									<#else>
										query.append("${finderCol.DBName} ${finderCol.comparator} null");
									</#if>
								}
								else {
							</#if>

							<#if finderCol.type == "String" && !finderCol.isCaseSensitive()>
								query.append("lower(${finderCol.DBName}) ${finderCol.comparator} ?");
							<#else>
								query.append("${finderCol.DBName} ${finderCol.comparator} ?");
							</#if>

							<#if !finderCol.isPrimitiveType()>
								}
							</#if>

							<#if finderCol_has_next>
								query.append(" AND ");
							<#elseif finder.where?? && !validator.isNull(finder.getWhere())>
								query.append(" AND ${finder.where} ");
							<#else>
								query.append(" ");
							</#if>
						</#list>

						<#if entity.getOrder()??>
							query.append("ORDER BY ");

							<#assign orderList = entity.getOrder().getColumns()>

							<#list orderList as order>
								query.append("${order.DBName} <#if order.isOrderByAscending()>ASC<#else>DESC</#if><#if order_has_next>, </#if>");
							</#list>
						</#if>

						Query q = session.createQuery(query.toString());

						QueryPos qPos = QueryPos.getInstance(q);

						<#list finderColsList as finderCol>
							<#if !finderCol.isPrimitiveType()>
								if (${finderCol.name} != null) {
							</#if>

							qPos.add(

							<#if finderCol.type == "Date">
								CalendarUtil.getTimestamp(
							</#if>

							${finderCol.name}${serviceBuilder.getPrimitiveObjValue("${finderCol.type}")}

							<#if finderCol.type == "Date">
								)
							</#if>

							);

							<#if !finderCol.isPrimitiveType()>
								}
							</#if>
						</#list>

						List<${entity.name}> list = q.list();

						FinderCacheUtil.putResult(finderClassNameCacheEnabled, finderClassName, finderMethodName, finderParams, finderArgs, list);

						if (list.size() == 0) {
							return null;
						}
						else {
							return list.get(0);
						}
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						closeSession(session);
					}
				}
				else {
					List<${entity.name}> list = (List<${entity.name}>)result;

					if (list.size() == 0) {
						return null;
					}
					else {
						return list.get(0);
					}
				}
			}
		</#if>
	</#list>

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery) throws SystemException {
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

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end) throws SystemException {
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

	public List<${entity.name}> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<${entity.name}> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<${entity.name}> findAll(int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ${entity.name}ModelImpl.CACHE_ENABLED;
		String finderClassName = ${entity.name}.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {"java.lang.Integer", "java.lang.Integer", "com.liferay.portal.kernel.util.OrderByComparator"};
		Object[] finderArgs = new Object[] {String.valueOf(start), String.valueOf(end), String.valueOf(obc)};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName, finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM ${packagePath}.model.${entity.name} ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				<#if entity.getOrder()??>
					else {
						query.append("ORDER BY ");

						<#assign orderList = entity.getOrder().getColumns()>

						<#list orderList as order>
							query.append("${order.DBName} <#if order.isOrderByAscending()>ASC<#else>DESC</#if><#if order_has_next>, </#if>");
						</#list>
					}
				</#if>

				Query q = session.createQuery(query.toString());

				List<${entity.name}> list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled, finderClassName, finderMethodName, finderParams, finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<${entity.name}>)result;
		}
	}

	<#list entity.getFinderList() as finder>
		<#assign finderColsList = finder.getColumns()>

		<#if finder.isCollection()>
			public void removeBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}<#if finderCol_has_next>,</#if>
			</#list>

			) throws SystemException {
				for (${entity.name} ${entity.varName} : findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name}

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				)) {
					remove(${entity.varName});
				}
			}
		<#else>
			public void removeBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}

				<#if finderCol_has_next>
					,
				</#if>
			</#list>

			) throws ${noSuchEntity}Exception, SystemException {
				${entity.name} ${entity.varName} = findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name}

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				);

				remove(${entity.varName});
			}
		</#if>
	</#list>

	public void removeAll() throws SystemException {
		for (${entity.name} ${entity.varName} : findAll()) {
			remove(${entity.varName});
		}
	}

	<#list entity.getFinderList() as finder>
		<#assign finderColsList = finder.getColumns()>

		public int countBy${finder.name}(

		<#list finderColsList as finderCol>
			${finderCol.type} ${finderCol.name}

			<#if finderCol_has_next>
				,
			</#if>
		</#list>

		) throws SystemException {
			boolean finderClassNameCacheEnabled = ${entity.name}ModelImpl.CACHE_ENABLED;
			String finderClassName = ${entity.name}.class.getName();
			String finderMethodName = "countBy${finder.name}";
			String[] finderParams = new String[] {
				<#list finderColsList as finderCol>
					${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.class.getName()

					<#if finderCol_has_next>
						,
					</#if>
				</#list>
			};
			Object[] finderArgs = new Object[] {
				<#list finderColsList as finderCol>
					<#if finderCol.isPrimitiveType()>
						<#if finderCol.type == "boolean">
							Boolean.valueOf(
						<#else>
							new ${serviceBuilder.getPrimitiveObj("${finderCol.type}")}(
						</#if>
					</#if>

					${finderCol.name}

					<#if finderCol.isPrimitiveType()>
						)
					</#if>

					<#if finderCol_has_next>
						,
					</#if>
				</#list>
			};

			Object result = null;

			if (finderClassNameCacheEnabled) {
				result = FinderCacheUtil.getResult(finderClassName, finderMethodName, finderParams, finderArgs, this);
			}

			if (result == null) {
				Session session = null;

				try {
					session = openSession();

					StringBuilder query = new StringBuilder();

					query.append("SELECT COUNT(*) ");
					query.append("FROM ${packagePath}.model.${entity.name} WHERE ");

					<#list finderColsList as finderCol>
						<#if !finderCol.isPrimitiveType()>
							if (${finderCol.name} == null) {
								<#if finderCol.comparator == "=">
									query.append("${finderCol.DBName} IS NULL");
								<#elseif finderCol.comparator == "<>" || finderCol.comparator = "!=">
									query.append("${finderCol.DBName} IS NOT NULL");
								<#else>
									query.append("${finderCol.DBName} ${finderCol.comparator} null");
								</#if>
							}
							else {
						</#if>

						<#if finderCol.type == "String" && !finderCol.isCaseSensitive()>
							query.append("lower(${finderCol.DBName}) ${finderCol.comparator} ?");
						<#else>
							query.append("${finderCol.DBName} ${finderCol.comparator} ?");
						</#if>

						<#if !finderCol.isPrimitiveType()>
							}
						</#if>

						<#if finderCol_has_next>
							query.append(" AND ");
						<#elseif finder.where?? && !validator.isNull(finder.getWhere())>
							query.append(" AND ${finder.where} ");
						<#else>
							query.append(" ");
						</#if>
					</#list>

					Query q = session.createQuery(query.toString());

					QueryPos qPos = QueryPos.getInstance(q);

					<#list finderColsList as finderCol>
						<#if !finderCol.isPrimitiveType()>
							if (${finderCol.name} != null) {
						</#if>

						qPos.add(

						<#if finderCol.type == "Date">
							CalendarUtil.getTimestamp(
						</#if>

						${finderCol.name}${serviceBuilder.getPrimitiveObjValue("${finderCol.type}")}

						<#if finderCol.type == "Date">
							)
						</#if>

						);

						<#if !finderCol.isPrimitiveType()>
							}
						</#if>
					</#list>

					Long count = null;

					Iterator<Long> itr = q.list().iterator();

					if (itr.hasNext()) {
						count = itr.next();
					}

					if (count == null) {
						count = new Long(0);
					}

					FinderCacheUtil.putResult(finderClassNameCacheEnabled, finderClassName, finderMethodName, finderParams, finderArgs, count);

					return count.intValue();
				}
				catch (Exception e) {
					throw processException(e);
				}
				finally {
					closeSession(session);
				}
			}
			else {
				return ((Long)result).intValue();
			}
		}
	</#list>

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = ${entity.name}ModelImpl.CACHE_ENABLED;
		String finderClassName = ${entity.name}.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {};
		Object[] finderArgs = new Object[] {};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName, finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery("SELECT COUNT(*) FROM ${packagePath}.model.${entity.name}");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled, finderClassName, finderMethodName, finderParams, finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	<#list entity.columnList as column>
		<#if column.isCollection() && (column.isMappingManyToMany() || column.isMappingOneToMany())>
			<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>

			public List<${tempEntity.packagePath}.model.${tempEntity.name}> get${tempEntity.names}(${entity.PKClassName} pk) throws SystemException {
				return get${tempEntity.names}(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			}

			public List<${tempEntity.packagePath}.model.${tempEntity.name}> get${tempEntity.names}(${entity.PKClassName} pk, int start, int end) throws SystemException {
				return get${tempEntity.names}(pk, start, end, null);
			}

			public List<${tempEntity.packagePath}.model.${tempEntity.name}> get${tempEntity.names}(${entity.PKClassName} pk, int start, int end, OrderByComparator obc) throws SystemException {
				boolean finderClassNameCacheEnabled =
					<#if column.mappingTable??>
						${entity.name}ModelImpl.CACHE_ENABLED_${stringUtil.upperCase(column.mappingTable)}
					<#else>
						${tempEntity.packagePath}.model.impl.${tempEntity.name}ModelImpl.CACHE_ENABLED
					</#if>

					;
				String finderClassName =
					<#if column.mappingTable??>
						"${column.mappingTable}"
					<#else>
						${tempEntity.packagePath}.model.${tempEntity.name}.class.getName()
					</#if>

					;
				String finderMethodName = "get${tempEntity.names}";
				String[] finderParams = new String[] {
					<#if entity.hasPrimitivePK()>
						${serviceBuilder.getPrimitiveObj(entity.getPKClassName())}
					<#else>
						${entity.PKClassName}
					</#if>

					.class.getName(), "java.lang.Integer", "java.lang.Integer", "com.liferay.portal.kernel.util.OrderByComparator"
				};
				Object[] finderArgs = new Object[] {
					<#if entity.hasPrimitivePK()>
						<#if entity.PKClassName == "boolean">
							Boolean.valueOf(
						<#else>
							new ${serviceBuilder.getPrimitiveObj(entity.getPKClassName())}(
						</#if>
					</#if>

					pk

					<#if entity.hasPrimitivePK()>
						)
					</#if>

					, String.valueOf(start), String.valueOf(end), String.valueOf(obc)
				};

				Object result = null;

				if (finderClassNameCacheEnabled) {
					result = FinderCacheUtil.getResult(finderClassName, finderMethodName, finderParams, finderArgs, this);
				}

				if (result == null) {
					Session session = null;

					try {
						session = openSession();

						StringBuilder sb = new StringBuilder();

						sb.append(_SQL_GET${tempEntity.names?upper_case});

						if (obc != null) {
							sb.append("ORDER BY ");
							sb.append(obc.getOrderBy());
						}

						<#if tempEntity.getOrder()??>
							else {
								sb.append("ORDER BY ");

								<#assign orderList = tempEntity.getOrder().getColumns()>

								<#list orderList as order>
									sb.append("${tempEntity.table}.${order.DBName} <#if order.isOrderByAscending()>ASC<#else>DESC</#if><#if order_has_next>, </#if>");
								</#list>
							}
						</#if>

						String sql = sb.toString();

						SQLQuery q = session.createSQLQuery(sql);

						q.addEntity("${tempEntity.table}", ${tempEntity.packagePath}.model.impl.${tempEntity.name}Impl.class);

						QueryPos qPos = QueryPos.getInstance(q);

						qPos.add(pk);

						List<${tempEntity.packagePath}.model.${tempEntity.name}> list = (List<${tempEntity.packagePath}.model.${tempEntity.name}>)QueryUtil.list(q, getDialect(), start, end);

						FinderCacheUtil.putResult(finderClassNameCacheEnabled, finderClassName, finderMethodName, finderParams, finderArgs, list);

						return list;
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						closeSession(session);
					}
				}
				else {
					return (List<${tempEntity.packagePath}.model.${tempEntity.name}>)result;
				}
			}

			public int get${tempEntity.names}Size(${entity.PKClassName} pk) throws SystemException {
				boolean finderClassNameCacheEnabled =
					<#if column.mappingTable??>
						${entity.name}ModelImpl.CACHE_ENABLED_${stringUtil.upperCase(column.mappingTable)}
					<#else>
						${tempEntity.packagePath}.model.impl.${tempEntity.name}ModelImpl.CACHE_ENABLED
					</#if>

					;
				String finderClassName =
					<#if column.mappingTable??>
						"${column.mappingTable}"
					<#else>
						${tempEntity.packagePath}.model.${tempEntity.name}.class.getName()
					</#if>

					;
				String finderMethodName = "get${tempEntity.names}Size";
				String[] finderParams = new String[] {
					<#if entity.hasPrimitivePK()>
						${serviceBuilder.getPrimitiveObj(entity.getPKClassName())}
					<#else>
						${entity.PKClassName}
					</#if>

					.class.getName()
				};
				Object[] finderArgs = new Object[] {
					<#if entity.hasPrimitivePK()>
						<#if entity.PKClassName == "boolean">
							Boolean.valueOf(
						<#else>
							new ${serviceBuilder.getPrimitiveObj(entity.getPKClassName())}(
						</#if>
					</#if>

					pk

					<#if entity.hasPrimitivePK()>
						)
					</#if>
				};

				Object result = null;

				if (finderClassNameCacheEnabled) {
					result = FinderCacheUtil.getResult(finderClassName, finderMethodName, finderParams, finderArgs, this);
				}

				if (result == null) {
					Session session = null;

					try {
						session = openSession();

						SQLQuery q = session.createSQLQuery(_SQL_GET${tempEntity.names?upper_case}SIZE);

						q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

						QueryPos qPos = QueryPos.getInstance(q);

						qPos.add(pk);

						Long count = null;

						Iterator<Long> itr = q.list().iterator();

						if (itr.hasNext()) {
							count = itr.next();
						}

						if (count == null) {
							count = new Long(0);
						}

						FinderCacheUtil.putResult(finderClassNameCacheEnabled, finderClassName, finderMethodName, finderParams, finderArgs, count);

						return count.intValue();
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						closeSession(session);
					}
				}
				else {
					return ((Long)result).intValue();
				}
			}

			public boolean contains${tempEntity.name}(${entity.PKClassName} pk, ${tempEntity.PKClassName} ${tempEntity.varName}PK) throws SystemException {
				boolean finderClassNameCacheEnabled =
					<#if column.mappingTable??>
						${entity.name}ModelImpl.CACHE_ENABLED_${stringUtil.upperCase(column.mappingTable)}
					<#else>
						${tempEntity.packagePath}.model.impl.${tempEntity.name}ModelImpl.CACHE_ENABLED
					</#if>

					;
				String finderClassName =
					<#if column.mappingTable??>
						"${column.mappingTable}"
					<#else>
						${tempEntity.packagePath}.model.${tempEntity.name}.class.getName()
					</#if>

					;
				String finderMethodName = "contains${tempEntity.names}";
				String[] finderParams = new String[] {
					<#if entity.hasPrimitivePK()>
						${serviceBuilder.getPrimitiveObj(entity.getPKClassName())}
					<#else>
						${entity.PKClassName}
					</#if>

					.class.getName(),

					<#if tempEntity.hasPrimitivePK()>
						${serviceBuilder.getPrimitiveObj(tempEntity.getPKClassName())}
					<#else>
						${tempEntity.PKClassName}
					</#if>

					.class.getName()
				};
				Object[] finderArgs = new Object[] {
					<#if entity.hasPrimitivePK()>
						<#if entity.PKClassName == "boolean">
							Boolean.valueOf(
						<#else>
							new ${serviceBuilder.getPrimitiveObj(entity.getPKClassName())}(
						</#if>
					</#if>

					pk

					<#if entity.hasPrimitivePK()>
						)
					</#if>,

					<#if tempEntity.hasPrimitivePK()>
						<#if entity.PKClassName == "boolean">
							Boolean.valueOf(
						<#else>
							new ${serviceBuilder.getPrimitiveObj(tempEntity.getPKClassName())}(
						</#if>
					</#if>

					${tempEntity.varName}PK

					<#if tempEntity.hasPrimitivePK()>
						)
					</#if>
				};

				Object result = null;

				if (finderClassNameCacheEnabled) {
					result = FinderCacheUtil.getResult(finderClassName, finderMethodName, finderParams, finderArgs, this);
				}

				if (result == null) {
					try {
						Boolean value = Boolean.valueOf(contains${tempEntity.name}.contains(pk, ${tempEntity.varName}PK));

						FinderCacheUtil.putResult(finderClassNameCacheEnabled, finderClassName, finderMethodName, finderParams, finderArgs, value);

						return value.booleanValue();
					}
					catch (Exception e) {
						throw processException(e);
					}
				}
				else {
					return ((Boolean)result).booleanValue();
				}
			}

			public boolean contains${tempEntity.names}(${entity.PKClassName} pk) throws SystemException {
				if (get${tempEntity.names}Size(pk)> 0) {
					return true;
				}
				else {
					return false;
				}
			}

			<#if column.isMappingManyToMany()>
				<#assign noSuchTempEntity = serviceBuilder.getNoSuchEntityException(tempEntity)>

				public void add${tempEntity.name}(${entity.PKClassName} pk, ${tempEntity.PKClassName} ${tempEntity.varName}PK) throws SystemException {
					try {
						add${tempEntity.name}.add(pk, ${tempEntity.varName}PK);
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache("${column.mappingTable}");
					}
				}

				public void add${tempEntity.name}(${entity.PKClassName} pk, ${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName}) throws SystemException {
					try {
						add${tempEntity.name}.add(pk, ${tempEntity.varName}.getPrimaryKey());
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache("${column.mappingTable}");
					}
				}

				public void add${tempEntity.names}(${entity.PKClassName} pk, ${tempEntity.PKClassName}[] ${tempEntity.varName}PKs) throws SystemException {
					try {
						for (${tempEntity.PKClassName} ${tempEntity.varName}PK : ${tempEntity.varName}PKs) {
							add${tempEntity.name}.add(pk, ${tempEntity.varName}PK);
						}
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache("${column.mappingTable}");
					}
				}

				public void add${tempEntity.names}(${entity.PKClassName} pk, List<${tempEntity.packagePath}.model.${tempEntity.name}> ${tempEntity.varNames}) throws SystemException {
					try {
						for (${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName} : ${tempEntity.varNames}) {
							add${tempEntity.name}.add(pk, ${tempEntity.varName}.getPrimaryKey());
						}
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache("${column.mappingTable}");
					}
				}

				public void clear${tempEntity.names}(${entity.PKClassName} pk) throws SystemException {
					try {
						clear${tempEntity.names}.clear(pk);
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache("${column.mappingTable}");
					}
				}

				public void remove${tempEntity.name}(${entity.PKClassName} pk, ${tempEntity.PKClassName} ${tempEntity.varName}PK) throws SystemException {
					try {
						remove${tempEntity.name}.remove(pk, ${tempEntity.varName}PK);
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache("${column.mappingTable}");
					}
				}

				public void remove${tempEntity.name}(${entity.PKClassName} pk, ${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName}) throws SystemException {
					try {
						remove${tempEntity.name}.remove(pk, ${tempEntity.varName}.getPrimaryKey());
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache("${column.mappingTable}");
					}
				}

				public void remove${tempEntity.names}(${entity.PKClassName} pk, ${tempEntity.PKClassName}[] ${tempEntity.varName}PKs) throws SystemException {
					try {
						for (${tempEntity.PKClassName} ${tempEntity.varName}PK : ${tempEntity.varName}PKs) {
							remove${tempEntity.name}.remove(pk, ${tempEntity.varName}PK);
						}
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache("${column.mappingTable}");
					}
				}

				public void remove${tempEntity.names}(${entity.PKClassName} pk, List<${tempEntity.packagePath}.model.${tempEntity.name}> ${tempEntity.varNames}) throws SystemException {
					try {
						for (${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName} : ${tempEntity.varNames}) {
							remove${tempEntity.name}.remove(pk, ${tempEntity.varName}.getPrimaryKey());
						}
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache("${column.mappingTable}");
					}
				}

				public void set${tempEntity.names}(${entity.PKClassName} pk, ${tempEntity.PKClassName}[] ${tempEntity.varName}PKs) throws SystemException {
					try {
						clear${tempEntity.names}.clear(pk);

						for (${tempEntity.PKClassName} ${tempEntity.varName}PK : ${tempEntity.varName}PKs) {
							add${tempEntity.name}.add(pk, ${tempEntity.varName}PK);
						}
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache("${column.mappingTable}");
					}
				}

				public void set${tempEntity.names}(${entity.PKClassName} pk, List<${tempEntity.packagePath}.model.${tempEntity.name}> ${tempEntity.varNames}) throws SystemException {
					try {
						clear${tempEntity.names}.clear(pk);

						for (${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName} : ${tempEntity.varNames}) {
							add${tempEntity.name}.add(pk, ${tempEntity.varName}.getPrimaryKey());
						}
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache("${column.mappingTable}");
					}
				}
			</#if>
		</#if>
	</#list>

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
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(${propsUtil}.get("value.object.listener.${packagePath}.model.${entity.name}")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener> listeners = new ArrayList<ModelListener>();

				for (String listenerClassName : listenerClassNames) {
					listeners.add((ModelListener)Class.forName(listenerClassName).newInstance());
				}

				_listeners = listeners.toArray(new ModelListener[listeners.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		<#list entity.columnList as column>
			<#if column.isCollection() && (column.isMappingManyToMany() || column.isMappingOneToMany())>
				<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>

				contains${tempEntity.name} = new Contains${tempEntity.name}(this);

				<#if column.isMappingManyToMany()>
					add${tempEntity.name} = new Add${tempEntity.name}(this);
					clear${tempEntity.names} = new Clear${tempEntity.names}(this);
					remove${tempEntity.name} = new Remove${tempEntity.name}(this);
				</#if>
			</#if>
		</#list>
	}

	<#list entity.columnList as column>
		<#if column.isCollection() && (column.isMappingManyToMany() || column.isMappingOneToMany())>
			<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>

			protected Contains${tempEntity.name} contains${tempEntity.name};

			<#if column.isMappingManyToMany()>
				protected Add${tempEntity.name} add${tempEntity.name};
				protected Clear${tempEntity.names} clear${tempEntity.names};
				protected Remove${tempEntity.name} remove${tempEntity.name};
			</#if>
		</#if>
	</#list>

	<#list entity.columnList as column>
		<#if column.isCollection() && (column.isMappingManyToMany() || column.isMappingOneToMany())>
			<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>
			<#assign entitySqlType = serviceBuilder.getSqlType(packagePath + ".model." + entity.getName(), entity.getPKVarName(), entity.getPKClassName())>
			<#assign tempEntitySqlType = serviceBuilder.getSqlType(tempEntity.getPackagePath() + ".model." + entity.getName(), tempEntity.getPKVarName(), tempEntity.getPKClassName())>

			<#if entity.hasPrimitivePK()>
				<#assign pkVarNameWrapper = "new " + serviceBuilder.getPrimitiveObj(entity.getPKClassName()) + "("+ entity.getPKVarName() +")">
			<#else>
				<#assign pkVarNameWrapper = entity.getPKVarName()>
			</#if>

			<#if tempEntity.hasPrimitivePK()>
				<#assign tempEntityPkVarNameWrapper = "new " + serviceBuilder.getPrimitiveObj(tempEntity.getPKClassName()) + "("+ tempEntity.getPKVarName() +")">
			<#else>
				<#assign tempEntityPkVarNameWrapper = tempEntity.getPKVarName()>
			</#if>

			protected class Contains${tempEntity.name} {

				protected Contains${tempEntity.name}(${entity.name}PersistenceImpl persistenceImpl) {
					super();

					_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(), _SQL_CONTAINS${tempEntity.name?upper_case}, new int[] {Types.${entitySqlType}, Types.${tempEntitySqlType}}, RowMapper.COUNT);
				}

				protected boolean contains(${entity.PKClassName} ${entity.PKVarName}, ${tempEntity.PKClassName} ${tempEntity.PKVarName}) {
					List<Integer> results = _mappingSqlQuery.execute(new Object[] {${pkVarNameWrapper}, ${tempEntityPkVarNameWrapper}});

					if (results.size()> 0) {
						Integer count = results.get(0);

						if (count.intValue()> 0) {
							return true;
						}
					}

					return false;
				}

				private MappingSqlQuery _mappingSqlQuery;

			}

			<#if column.isMappingManyToMany()>
				protected class Add${tempEntity.name} {

					protected Add${tempEntity.name}(${entity.name}PersistenceImpl persistenceImpl) {
						_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(), "INSERT INTO ${column.mappingTable} (${entity.PKVarName}, ${tempEntity.PKVarName}) VALUES (?, ?)", new int[] {Types.${entitySqlType}, Types.${tempEntitySqlType}});
						_persistenceImpl = persistenceImpl;
					}

					protected void add(${entity.PKClassName} ${entity.PKVarName}, ${tempEntity.PKClassName} ${tempEntity.PKVarName}) throws SystemException {
						if (!_persistenceImpl.contains${tempEntity.name}.contains(${entity.PKVarName}, ${tempEntity.PKVarName})) {

							if (_listeners.length > 0) {
								for (ModelListener listener : _listeners) {
									listener.onBeforeAddAssociation(${entity.PKVarName}, ${tempEntity.packagePath}.model.${tempEntity.name}.class.getName(), ${tempEntity.PKVarName});
								}
							}

							_sqlUpdate.update(new Object[] {${pkVarNameWrapper}, ${tempEntityPkVarNameWrapper}});

							if (_listeners.length > 0) {
								for (ModelListener listener : _listeners) {
									listener.onAfterAddAssociation(${entity.PKVarName}, ${tempEntity.packagePath}.model.${tempEntity.name}.class.getName(), ${tempEntity.PKVarName});
								}
							}
						}
					}

					private SqlUpdate _sqlUpdate;
					private ${entity.name}PersistenceImpl _persistenceImpl;

				}

				protected class Clear${tempEntity.names} {

					protected Clear${tempEntity.names}(${entity.name}PersistenceImpl persistenceImpl) {
						_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(), "DELETE FROM ${column.mappingTable} WHERE ${entity.PKVarName} = ?", new int[] {Types.${entitySqlType}});
					}

					protected void clear(${entity.PKClassName} ${entity.PKVarName}) throws SystemException {

						if (_listeners.length > 0) {
							for (ModelListener listener : _listeners) {
								listener.onBeforeClearAssociation(${entity.PKVarName}, ${tempEntity.packagePath}.model.${tempEntity.name}.class.getName());
							}
						}

						_sqlUpdate.update(new Object[] { ${pkVarNameWrapper} });

						if (_listeners.length > 0) {
							for (ModelListener listener : _listeners) {
								listener.onAfterClearAssociation(${entity.PKVarName}, ${tempEntity.packagePath}.model.${tempEntity.name}.class.getName());
							}
						}
					}

					private SqlUpdate _sqlUpdate;

				}

				protected class Remove${tempEntity.name} {

					protected Remove${tempEntity.name}(${entity.name}PersistenceImpl persistenceImpl) {
						_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(), "DELETE FROM ${column.mappingTable} WHERE ${entity.PKVarName} = ? AND ${tempEntity.PKVarName} = ?", new int[] {Types.${entitySqlType}, Types.${tempEntitySqlType}});
						_persistenceImpl = persistenceImpl;
					}

					protected void remove(${entity.PKClassName} ${entity.PKVarName}, ${tempEntity.PKClassName} ${tempEntity.PKVarName}) throws SystemException {
						if (_persistenceImpl.contains${tempEntity.name}.contains(${entity.PKVarName}, ${tempEntity.PKVarName})) {

							if (_listeners.length > 0) {
								for (ModelListener listener : _listeners) {
									listener.onBeforeRemoveAssociation(${entity.PKVarName}, ${tempEntity.packagePath}.model.${tempEntity.name}.class.getName(), ${tempEntity.PKVarName});
								}
							}

							_sqlUpdate.update(new Object[] {${pkVarNameWrapper}, ${tempEntityPkVarNameWrapper}});

							if (_listeners.length > 0) {
								for (ModelListener listener : _listeners) {
									listener.onAfterRemoveAssociation(${entity.PKVarName}, ${tempEntity.packagePath}.model.${tempEntity.name}.class.getName(), ${tempEntity.PKVarName});
								}
							}

						}
					}

					private SqlUpdate _sqlUpdate;
					private ${entity.name}PersistenceImpl _persistenceImpl;

				}
			</#if>
		</#if>
	</#list>

	<#list entity.columnList as column>
		<#if column.isCollection()>
			<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>

			<#if column.isMappingManyToMany()>
				private static final String _SQL_GET${tempEntity.names?upper_case} = "SELECT {${tempEntity.table}.*} FROM ${tempEntity.table} INNER JOIN ${column.mappingTable} ON (${column.mappingTable}.${tempEntity.PKVarName} = ${tempEntity.table}.${tempEntity.PKVarName}) WHERE (${column.mappingTable}.${entity.PKVarName} = ?)";

				private static final String _SQL_GET${tempEntity.names?upper_case}SIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM ${column.mappingTable} WHERE ${entity.PKVarName} = ?";

				private static final String _SQL_CONTAINS${tempEntity.name?upper_case} = "SELECT COUNT(*) AS COUNT_VALUE FROM ${column.mappingTable} WHERE ${entity.PKVarName} = ? AND ${tempEntity.PKVarName} = ?";
			<#elseif column.isMappingOneToMany()>
				private static final String _SQL_GET${tempEntity.names?upper_case} = "SELECT {${tempEntity.table}.*} FROM ${tempEntity.table} INNER JOIN ${entity.table} ON (${entity.table}.${entity.PKVarName} = ${tempEntity.table}.${entity.PKVarName}) WHERE (${entity.table}.${entity.PKVarName} = ?)";

				private static final String _SQL_GET${tempEntity.names?upper_case}SIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM ${tempEntity.table} WHERE ${entity.PKVarName} = ?";

				private static final String _SQL_CONTAINS${tempEntity.name?upper_case} = "SELECT COUNT(*) AS COUNT_VALUE FROM ${tempEntity.table} WHERE ${entity.PKVarName} = ? AND ${tempEntity.PKVarName} = ?";
			</#if>
		</#if>
	</#list>

	private static Log _log = LogFactory.getLog(${entity.name}PersistenceImpl.class);

	private ModelListener[] _listeners = new ModelListener[0];

}