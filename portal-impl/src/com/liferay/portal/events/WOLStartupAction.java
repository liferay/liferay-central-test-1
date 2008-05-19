/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.social.model.SocialRelationConstants;
import com.liferay.portlet.social.service.SocialRelationLocalServiceUtil;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="WOLStartupAction.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This class is used on www.liferay.com to provide WOL features. It is included
 * in the core source as an example. Do not use this directly because the ids
 * are hard coded and will not work for other installations. It is provided as
 * an example.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.events.WOLLoginPostAction
 *
 */
public class WOLStartupAction extends SimpleAction {

	public void run(String[] ids) throws ActionException {
		try {
			doRun(GetterUtil.getLong(ids[0]));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void doRun(long companyId) throws Exception {
		setupLiferayUsers();
	}

	protected void setupLiferayUsers() throws Exception {
		List<User> users = UserLocalServiceUtil.getOrganizationUsers(
			WOLLoginPostAction.ORGANIZATION_LIFERAY_INC_ID);

		for (User user1 : users) {
			for (User user2 : users) {
				if (user1.getUserId() != user2.getUserId()) {
					SocialRelationLocalServiceUtil.addRelation(
						user1.getUserId(), user2.getUserId(),
						SocialRelationConstants.TYPE_BI_FRIEND);
				}
			}
		}
	}

	private static Log _log = LogFactory.getLog(WOLStartupAction.class);

}