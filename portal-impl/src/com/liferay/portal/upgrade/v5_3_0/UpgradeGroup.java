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

package com.liferay.portal.upgrade.v5_3_0;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradeGroup.java.html"><b><i>View Source</i></b></a>
 *
 * @author Wesley Gong
 */
public class UpgradeGroup extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		updateParentGroupId();
	}

	protected void updateParentGroupId() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ClassName layoutClassName = ClassNameLocalServiceUtil.getClassName(
				Layout.class.getName());

			ps = con.prepareStatement(
				"select groupId, classPK from Group_ where classNameId = " +
					layoutClassName.getClassNameId());

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long classPK = rs.getLong("classPK");

				try {
					Layout layout = LayoutLocalServiceUtil.getLayout(classPK);

					runSQL(
						"update Group_ set parentGroupId = " +
							layout.getGroupId() + " where groupId = " +
								groupId);
				}
				catch (NoSuchLayoutException nsle) {
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}