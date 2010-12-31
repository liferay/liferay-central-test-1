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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.Role;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class RolePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (RolePersistence)PortalBeanLocatorUtil.locate(RolePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Role role = _persistence.create(pk);

		assertNotNull(role);

		assertEquals(role.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Role newRole = addRole();

		_persistence.remove(newRole);

		Role existingRole = _persistence.fetchByPrimaryKey(newRole.getPrimaryKey());

		assertNull(existingRole);
	}

	public void testUpdateNew() throws Exception {
		addRole();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Role newRole = _persistence.create(pk);

		newRole.setCompanyId(nextLong());
		newRole.setClassNameId(nextLong());
		newRole.setClassPK(nextLong());
		newRole.setName(randomString());
		newRole.setTitle(randomString());
		newRole.setDescription(randomString());
		newRole.setType(nextInt());
		newRole.setSubtype(randomString());

		_persistence.update(newRole, false);

		Role existingRole = _persistence.findByPrimaryKey(newRole.getPrimaryKey());

		assertEquals(existingRole.getRoleId(), newRole.getRoleId());
		assertEquals(existingRole.getCompanyId(), newRole.getCompanyId());
		assertEquals(existingRole.getClassNameId(), newRole.getClassNameId());
		assertEquals(existingRole.getClassPK(), newRole.getClassPK());
		assertEquals(existingRole.getName(), newRole.getName());
		assertEquals(existingRole.getTitle(), newRole.getTitle());
		assertEquals(existingRole.getDescription(), newRole.getDescription());
		assertEquals(existingRole.getType(), newRole.getType());
		assertEquals(existingRole.getSubtype(), newRole.getSubtype());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Role newRole = addRole();

		Role existingRole = _persistence.findByPrimaryKey(newRole.getPrimaryKey());

		assertEquals(existingRole, newRole);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchRoleException");
		}
		catch (NoSuchRoleException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Role newRole = addRole();

		Role existingRole = _persistence.fetchByPrimaryKey(newRole.getPrimaryKey());

		assertEquals(existingRole, newRole);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Role missingRole = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingRole);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Role newRole = addRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Role.class,
				Role.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("roleId",
				newRole.getRoleId()));

		List<Role> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Role existingRole = result.get(0);

		assertEquals(existingRole, newRole);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Role.class,
				Role.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("roleId", nextLong()));

		List<Role> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Role addRole() throws Exception {
		long pk = nextLong();

		Role role = _persistence.create(pk);

		role.setCompanyId(nextLong());
		role.setClassNameId(nextLong());
		role.setClassPK(nextLong());
		role.setName(randomString());
		role.setTitle(randomString());
		role.setDescription(randomString());
		role.setType(nextInt());
		role.setSubtype(randomString());

		_persistence.update(role, false);

		return role;
	}

	private RolePersistence _persistence;
}