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

package com.liferay.portal.upgrade.v4_3_5;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.PortletConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradePortletId.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UpgradePortletId extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// This is only tested to work on instanceable portlets

		String[][] portletIdsArray = getPortletIdsArray();

		for (int i = 0; i < portletIdsArray.length; i++) {
			String[] portletIds = portletIdsArray[i];

			String oldRootPortletId = portletIds[0];
			String newRootPortletId = portletIds[1];

			updatePortlet(oldRootPortletId, newRootPortletId);
			updateResource(oldRootPortletId, newRootPortletId);
			updateResourceCode(oldRootPortletId, newRootPortletId);
		}
	}

	protected String[][] getPortletIdsArray() {
		return new String[][] {
			new String[] {
				"94",
				"1_WAR_googleadsenseportlet"
			},
			new String[] {
				"95",
				"1_WAR_googlegadgetportlet"
			},
			new String[] {
				"96",
				"1_WAR_googlemapsportlet"
			}
		};
	}

	protected void updateLayout(
			long plid, String oldPortletId, String newPortletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select typeSettings from Layout where plid = " + plid);

			rs = ps.executeQuery();

			while (rs.next()) {
				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings = StringUtil.replace(
					typeSettings, oldPortletId, newPortletId);

				updateTypeSettings(plid, newTypeSettings);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updatePortlet(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		runSQL(
			"update Portlet set portletId = '" + newRootPortletId +
				"' where portletId = '" + oldRootPortletId + "'");
	}

	protected void updateResource(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select primKey from Resource_ where primKey like ?");

			ps.setString(
				1,
				"%" + PortletConstants.LAYOUT_SEPARATOR + oldRootPortletId +
					PortletConstants.INSTANCE_SEPARATOR + "%");

			rs = ps.executeQuery();

			while (rs.next()) {
				String oldPrimKey = rs.getString("primKey");

				int pos = oldPrimKey.indexOf(PortletConstants.LAYOUT_SEPARATOR);

				long plid = GetterUtil.getLong(
					oldPrimKey.substring(0, pos));

				pos = oldPrimKey.indexOf(PortletConstants.INSTANCE_SEPARATOR);

				String instanceId = oldPrimKey.substring(
					pos + PortletConstants.INSTANCE_SEPARATOR.length());

				String newPrimKey =
					plid + PortletConstants.LAYOUT_SEPARATOR +
						newRootPortletId + PortletConstants.INSTANCE_SEPARATOR +
							instanceId;

				runSQL(
					"update Resource_ set primKey = '" + newPrimKey +
						"' where primKey = '" + oldPrimKey + "'");

				String oldPortletId =
					oldRootPortletId + PortletConstants.INSTANCE_SEPARATOR +
						instanceId;
				String newPortletId =
					newRootPortletId + PortletConstants.INSTANCE_SEPARATOR +
						instanceId;

				updateLayout(plid, oldPortletId, newPortletId);

				runSQL(
					"update PortletPreferences set portletId = '" +
						newPortletId + "' where portletId = '" + oldPortletId +
							"'");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateResourceCode(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		runSQL(
			"update ResourceCode set name = '" + newRootPortletId +
				"' where name = '" + oldRootPortletId + "'");
	}

	protected void updateTypeSettings(long plid, String typeSettings)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update Layout set typeSettings = ? where plid = " + plid);

			ps.setString(1, typeSettings);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

}