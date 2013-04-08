/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Michael C. Han
 */
public class VerifyAuditedModel extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		for (String[] model : _MODELS) {
			verifyModel(model[0], model[1]);
		}
	}

	protected Object[] getDefaultUserArray(Connection con, long companyId)
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(
				"select userId, firstName, middleName, lastName from User_" +
					" where companyId = ? and defaultUser = ?");

			ps.setLong(1, companyId);
			ps.setBoolean(2, true);

			rs = ps.executeQuery();

			if (rs.next()) {
				long userId = rs.getLong("userId");
				String firstName = rs.getString("firstName");
				String middleName = rs.getString("middleName");
				String lastName = rs.getString("lastName");

				Object[] defaultUserArray = new Object[2];

				defaultUserArray[0] = userId;

				FullNameGenerator fullNameGenerator =
					FullNameGeneratorFactory.getInstance();

				defaultUserArray[1] = fullNameGenerator.getFullName(
					firstName, middleName, lastName);

				return defaultUserArray;
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}
	}

	protected void verifyModel(String modelName, String pkColumnName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select " + pkColumnName + ", companyId from " +
					modelName + " where userName is null order by companyId");

			rs = ps.executeQuery();

			Timestamp createDate = new Timestamp(System.currentTimeMillis());

			Object[] defaultUserArray = null;

			long previousCompanyId = 0;

			while (rs.next()) {
				long companyId = rs.getLong("companyId");

				long tablePrimaryKey = rs.getLong(pkColumnName);

				if ((previousCompanyId != companyId) ||
					(defaultUserArray == null)) {

					defaultUserArray = getDefaultUserArray(con, companyId);
				}

				ps = con.prepareStatement(
					"update " + modelName + " set userId = ?, userName = ?, " +
						"createDate = ?, modifiedDate = ? where " +
						pkColumnName + " = ?");

				ps.setLong(1, (Long)defaultUserArray[0]);
				ps.setString(2, (String)defaultUserArray[1]);
				ps.setTimestamp(3, createDate);
				ps.setTimestamp(4, createDate);
				ps.setLong(5, tablePrimaryKey);

				ps.executeUpdate();

				previousCompanyId = companyId;
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String[][] _MODELS = new String[][] {
		new String[] {
			"Organization_", "organizationId"
		},
		new String[] {
			"Role_", "roleId"
		},
		new String[] {
			"UserGroup", "userGroupId"
		},
	};

}