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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="GroupFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class GroupFinderImpl
	extends BasePersistenceImpl implements GroupFinder {

	public static String COUNT_BY_GROUP_ID =
		GroupFinder.class.getName() + ".countByGroupId";

	public static String COUNT_BY_C_N_D =
		GroupFinder.class.getName() + ".countByC_N_D";

	public static String FIND_BY_NO_LAYOUTS =
		GroupFinder.class.getName() + ".findByNoLayouts";

	public static String FIND_BY_NULL_FRIENDLY_URL =
		GroupFinder.class.getName() + ".findByNullFriendlyURL";

	public static String FIND_BY_SYSTEM =
		GroupFinder.class.getName() + ".findBySystem";

	public static String FIND_BY_C_N =
		GroupFinder.class.getName() + ".findByC_N";

	public static String FIND_BY_C_N_D =
		GroupFinder.class.getName() + ".findByC_N_D";

	public static String JOIN_BY_ACTIVE =
		GroupFinder.class.getName() + ".joinByActive";

	public static String JOIN_BY_CREATOR_USER_ID =
		GroupFinder.class.getName() + ".joinByCreatorUserId";

	public static String JOIN_BY_GROUPS_ORGS =
		GroupFinder.class.getName() + ".joinByGroupsOrgs";

	public static String JOIN_BY_GROUPS_ROLES =
		GroupFinder.class.getName() + ".joinByGroupsRoles";

	public static String JOIN_BY_GROUPS_USER_GROUPS =
		GroupFinder.class.getName() + ".joinByGroupsUserGroups";

	public static String JOIN_BY_LAYOUT_SET =
		GroupFinder.class.getName() + ".joinByLayoutSet";

	public static String JOIN_BY_PAGE_COUNT =
		GroupFinder.class.getName() + ".joinByPageCount";

	public static String JOIN_BY_ROLE_PERMISSIONS =
		GroupFinder.class.getName() + ".joinByRolePermissions";

	public static String JOIN_BY_TYPE =
		GroupFinder.class.getName() + ".joinByType";

	public static String JOIN_BY_USER_GROUP_ROLE =
		GroupFinder.class.getName() + ".joinByUserGroupRole";

	public static String JOIN_BY_USERS_GROUPS =
		GroupFinder.class.getName() + ".joinByUsersGroups";

	public int countByG_U(long groupId, long userId) throws SystemException {
		LinkedHashMap<String, Object> params1 =
			new LinkedHashMap<String, Object>();

		params1.put("usersGroups", userId);

		LinkedHashMap<String, Object> params2 =
			new LinkedHashMap<String, Object>();

		params2.put("groupsOrgs", userId);

		LinkedHashMap<String, Object> params3 =
			new LinkedHashMap<String, Object>();

		params3.put("groupsUserGroups", userId);

		Session session = null;

		try {
			session = openSession();

			int count = countByGroupId(session, groupId, params1);
			count += countByGroupId(session, groupId, params2);
			count += countByGroupId(session, groupId, params3);

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_N_D(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		name = StringUtil.lowerCase(name);
		description = StringUtil.lowerCase(description);

		if (params == null) {
			params = new LinkedHashMap<String, Object>();
		}

		Long userId = (Long)params.get("usersGroups");

		LinkedHashMap<String, Object> params1 = params;

		LinkedHashMap<String, Object> params2 =
			new LinkedHashMap<String, Object>();

		params2.putAll(params1);

		if (userId != null) {
			params2.remove("usersGroups");
			params2.put("groupsOrgs", userId);
		}

		LinkedHashMap<String, Object> params3 =
			new LinkedHashMap<String, Object>();

		params3.putAll(params1);

		if (userId != null) {
			params3.remove("usersGroups");
			params3.put("groupsUserGroups", userId);
		}

		Session session = null;

		try {
			session = openSession();

			int count = countByC_N_D(
				session, companyId, name, description, params1);

			if (Validator.isNotNull(userId)) {
				count += countByC_N_D(
					session, companyId, name, description, params2);

				count += countByC_N_D(
					session, companyId, name, description, params3);
			}

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Group> findByNoLayouts(
			long classNameId, boolean privateLayout, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_SYSTEM);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Group_", GroupImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);
			qPos.add(privateLayout);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Group> findByNullFriendlyURL() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NULL_FRIENDLY_URL);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Group_", GroupImpl.class);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Group> findBySystem(long companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_SYSTEM);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Group_", GroupImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Group findByC_N(long companyId, String name)
		throws NoSuchGroupException, SystemException {

		name = StringUtil.lowerCase(name);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_N);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Group_", GroupImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);

			List<Group> list = q.list();

			if (!list.isEmpty()) {
				return list.get(0);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}

		StringBuilder sb = new StringBuilder();

		sb.append("No Group exists with the key {companyId=");
		sb.append(companyId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		throw new NoSuchGroupException(sb.toString());
	}

	public List<Group> findByC_N_D(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		name = StringUtil.lowerCase(name);
		description = StringUtil.lowerCase(description);

		if (params == null) {
			params = new LinkedHashMap<String, Object>();
		}

		Long userId = (Long)params.get("usersGroups");

		LinkedHashMap<String, Object> params1 = params;

		LinkedHashMap<String, Object> params2 =
			new LinkedHashMap<String, Object>();

		params2.putAll(params1);

		if (userId != null) {
			params2.remove("usersGroups");
			params2.put("groupsOrgs", userId);
		}

		LinkedHashMap<String, Object> params3 =
			new LinkedHashMap<String, Object>();

		params3.putAll(params1);

		if (userId != null) {
			params3.remove("usersGroups");
			params3.put("groupsUserGroups", userId);
		}

		StringBuilder sb = new StringBuilder();

		sb.append("(");

		sb.append(CustomSQLUtil.get(FIND_BY_C_N_D));

		String sql = sb.toString();

		sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params1));
		sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params1));

		sb = new StringBuilder();

		sb.append(sql);

		sb.append(")");

		if (Validator.isNotNull(userId)) {
			sb.append(" UNION (");

			sb.append(CustomSQLUtil.get(FIND_BY_C_N_D));

			sql = sb.toString();

			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params2));
			sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params2));

			sb = new StringBuilder();

			sb.append(sql);

			sb.append(") UNION (");

			sb.append(CustomSQLUtil.get(FIND_BY_C_N_D));

			sql = sb.toString();

			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params3));
			sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params3));

			sb = new StringBuilder();

			sb.append(sql);

			sb.append(")");
		}

		sql = sb.toString();
		sql = CustomSQLUtil.replaceOrderBy(sql, obc);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("groupId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, params1);
			qPos.add(companyId);
			qPos.add(name);
			qPos.add(name);
			qPos.add(description);
			qPos.add(description);

			if (Validator.isNotNull(userId)) {
				setJoin(qPos, params2);
				qPos.add(companyId);
				qPos.add(name);
				qPos.add(name);
				qPos.add(description);
				qPos.add(description);

				setJoin(qPos, params3);
				qPos.add(companyId);
				qPos.add(name);
				qPos.add(name);
				qPos.add(description);
				qPos.add(description);
			}

			List<Group> groups = new ArrayList<Group>();

			Iterator<Long> itr = (Iterator<Long>)QueryUtil.iterate(
				q, getDialect(), start, end);

			while (itr.hasNext()) {
				long groupId = itr.next();

				Group group = GroupUtil.findByPrimaryKey(groupId);

				groups.add(group);
			}

			return groups;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected int countByGroupId(
		Session session, long groupId, LinkedHashMap<String, Object> params) {

		String sql = CustomSQLUtil.get(COUNT_BY_GROUP_ID);

		sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
		sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));

		SQLQuery q = session.createSQLQuery(sql);

		q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

		QueryPos qPos = QueryPos.getInstance(q);

		setJoin(qPos, params);
		qPos.add(groupId);

		Iterator<Long> itr = q.list().iterator();

		if (itr.hasNext()) {
			Long count = itr.next();

			if (count != null) {
				return count.intValue();
			}
		}

		return 0;
	}

	protected int countByC_N_D(
		Session session, long companyId, String name, String description,
		LinkedHashMap<String, Object> params) {

		String sql = CustomSQLUtil.get(COUNT_BY_C_N_D);

		sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
		sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));

		SQLQuery q = session.createSQLQuery(sql);

		q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

		QueryPos qPos = QueryPos.getInstance(q);

		setJoin(qPos, params);
		qPos.add(companyId);
		qPos.add(name);
		qPos.add(name);
		qPos.add(description);
		qPos.add(description);

		Iterator<Long> itr = q.list().iterator();

		if (itr.hasNext()) {
			Long count = itr.next();

			if (count != null) {
				return count.intValue();
			}
		}

		return 0;
	}

	protected String getJoin(LinkedHashMap<String, Object> params) {
		if (params == null) {
			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder();

		Iterator<Map.Entry<String, Object>> itr = params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Object> entry = itr.next();

			String key = entry.getKey();
			Object value = entry.getValue();

			if (Validator.isNotNull(value)) {
				sb.append(getJoin(key));
			}
		}

		return sb.toString();
	}

	protected String getJoin(String key) {
		String join = StringPool.BLANK;

		if (key.equals("groupsOrgs")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_ORGS);
		}
		else if (key.equals("groupsRoles")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_ROLES);
		}
		else if (key.equals("groupsUserGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_USER_GROUPS);
		}
		else if (key.equals("layoutSet")) {
			join = CustomSQLUtil.get(JOIN_BY_LAYOUT_SET);
		}
		else if (key.equals("pageCount")) {
			join = CustomSQLUtil.get(JOIN_BY_PAGE_COUNT);
		}
		else if (key.equals("rolePermissions")) {
			join = CustomSQLUtil.get(JOIN_BY_ROLE_PERMISSIONS);
		}
		else if (key.equals("userGroupRole")) {
			join = CustomSQLUtil.get(JOIN_BY_USER_GROUP_ROLE);
		}
		else if (key.equals("usersGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_GROUPS);
		}

		if (Validator.isNotNull(join)) {
			int pos = join.indexOf("WHERE");

			if (pos != -1) {
				join = join.substring(0, pos);
			}
		}

		return join;
	}

	protected String getWhere(LinkedHashMap<String, Object> params) {
		if (params == null) {
			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder();

		Iterator<Map.Entry<String, Object>> itr = params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Object> entry = itr.next();

			String key = entry.getKey();
			Object value = entry.getValue();

			if (Validator.isNotNull(value)) {
				sb.append(getWhere(key, value));
			}
		}

		return sb.toString();
	}

	protected String getWhere(String key, Object value) {
		String join = StringPool.BLANK;

		if (key.equals("active")) {
			join = CustomSQLUtil.get(JOIN_BY_ACTIVE);
		}
		else if (key.equals("creatorUserId")) {
			join = CustomSQLUtil.get(JOIN_BY_CREATOR_USER_ID);
		}
		else if (key.equals("groupsOrgs")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_ORGS);
		}
		else if (key.equals("groupsRoles")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_ROLES);
		}
		else if (key.equals("groupsUserGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_USER_GROUPS);
		}
		else if (key.equals("layoutSet")) {
			join = CustomSQLUtil.get(JOIN_BY_LAYOUT_SET);
		}
		else if (key.equals("pageCount")) {
			join = CustomSQLUtil.get(JOIN_BY_PAGE_COUNT);
		}
		else if (key.equals("rolePermissions")) {
			join = CustomSQLUtil.get(JOIN_BY_ROLE_PERMISSIONS);
		}
		else if (key.equals("type")) {
			join = CustomSQLUtil.get(JOIN_BY_TYPE);
		}
		else if (key.equals("types")) {
			List<Integer> types = (List<Integer>)value;

			if (!types.isEmpty()) {
				StringBuilder sb = new StringBuilder();

				sb.append("WHERE (");

				for (int i = 0; i < types.size(); i++) {
					sb.append("(Group_.type_ = ?) ");

					if ((i + 1) < types.size()) {
						sb.append("OR ");
					}
				}

				sb.append(")");

				join = sb.toString();
			}
		}
		else if (key.equals("userGroupRole")) {
			join = CustomSQLUtil.get(JOIN_BY_USER_GROUP_ROLE);
		}
		else if (key.equals("usersGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_GROUPS);
		}

		if (Validator.isNotNull(join)) {
			int pos = join.indexOf("WHERE");

			if (pos != -1) {
				StringBuilder sb = new StringBuilder();

				sb.append(join.substring(pos + 5, join.length()));
				sb.append(" AND ");

				join = sb.toString();
			}
			else {
				join = StringPool.BLANK;
			}
		}

		return join;
	}

	protected void setJoin(
		QueryPos qPos, LinkedHashMap<String, Object> params) {

		if (params != null) {
			Iterator<Map.Entry<String, Object>> itr =
				params.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();

				String key = entry.getKey();

				if (key.equals("active") || key.equals("layoutSet")) {
					Boolean value = (Boolean)entry.getValue();

					qPos.add(value);
				}
				else if (key.equals("pageCount")) {
				}
				else if (key.equals("rolePermissions")) {
					List<Object> values = (List<Object>)entry.getValue();

					for (int i = 0; i < values.size(); i++) {
						Object value = values.get(i);

						if (value instanceof Integer) {
							Integer valueInteger = (Integer)value;

							qPos.add(valueInteger);
						}
						else if (value instanceof Long) {
							Long valueLong = (Long)value;

							qPos.add(valueLong);
						}
						else if (value instanceof String) {
							String valueString = (String)value;

							qPos.add(valueString);
						}
					}
				}
				else if (key.equals("types")) {
					List<Integer> values = (List<Integer>)entry.getValue();

					for (int i = 0; i < values.size(); i++) {
						Integer value = values.get(i);

						qPos.add(value);
					}
				}
				else if (key.equals("userGroupRole")) {
					List<Long> values = (List<Long>)entry.getValue();

					Long userId = values.get(0);
					Long roleId = values.get(1);

					qPos.add(userId);
					qPos.add(roleId);
				}
				else {
					Object value = entry.getValue();

					if (value instanceof Long) {
						Long valueLong = (Long)value;

						if (Validator.isNotNull(valueLong)) {
							qPos.add(valueLong);
						}
					}
					else if (value instanceof String) {
						String valueString = (String)value;

						if (Validator.isNotNull(valueString)) {
							qPos.add(valueString);
						}
					}
				}
			}
		}
	}

}