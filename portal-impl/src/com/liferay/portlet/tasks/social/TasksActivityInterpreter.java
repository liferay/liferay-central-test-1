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

package com.liferay.portlet.tasks.social;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.service.TasksProposalLocalServiceUtil;
import com.liferay.portlet.tasks.service.permission.TasksProposalPermission;

/**
 * <a href="TasksActivityInterpreter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Aug�
 */
public class TasksActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!TasksProposalPermission.contains(
				permissionChecker, activity.getClassPK(), ActionKeys.VIEW)) {

			return null;
		}

		String creatorUserName = getUserName(
			activity.getUserId(), themeDisplay);
		String receiverUserName = getUserName(
			activity.getReceiverUserId(), themeDisplay);

		int activityType = activity.getType();

		JSONObject extraData = null;

		if (Validator.isNotNull(activity.getExtraData())) {
			extraData = JSONFactoryUtil.createJSONObject(
				activity.getExtraData());
		}

		// Title

		String groupName = StringPool.BLANK;

		if (activity.getGroupId() != themeDisplay.getScopeGroupId()) {
			Group group = GroupLocalServiceUtil.getGroup(activity.getGroupId());

			groupName = group.getDescriptiveName();
		}

		String titlePattern = null;
		Object[] titleArguments = null;

		if (activityType == TasksActivityKeys.ADD_PROPOSAL) {
			titlePattern = "activity-tasks-add-proposal";

			if (Validator.isNotNull(groupName)) {
				titlePattern += "-in";
			}

			titleArguments = new Object[] {creatorUserName, groupName};
		}
		else if (activityType == TasksActivityKeys.ASSIGN_PROPOSAL) {
			titlePattern = "activity-tasks-assign-proposal";

			if (Validator.isNotNull(groupName)) {
				titlePattern += "-in";
			}

			titleArguments = new Object[] {
				creatorUserName, receiverUserName, groupName
			};
		}
		else if (activityType == TasksActivityKeys.REVIEW_PROPOSAL) {
			titlePattern = "activity-tasks-review-proposal";

			if (Validator.isNotNull(groupName)) {
				titlePattern += "-in";
			}

			titleArguments = new Object[] {
				creatorUserName, receiverUserName, groupName
			};
		}

		String title = themeDisplay.translate(titlePattern, titleArguments);

		// Body

		TasksProposal proposal = TasksProposalLocalServiceUtil.getProposal(
			activity.getClassPK());

		StringBuilder sb = new StringBuilder();

		sb.append("<b>");
		sb.append(proposal.getName());
		sb.append("</b> (");
		sb.append(
			themeDisplay.translate(
				"model.resource." + proposal.getClassName()));
		sb.append(")<br />");
		sb.append(themeDisplay.translate("description"));
		sb.append(": ");
		sb.append(proposal.getDescription());

		if (activityType != TasksActivityKeys.ADD_PROPOSAL) {
			int stage = extraData.getInt("stage");
			boolean completed = extraData.getBoolean("completed");
			boolean rejected = extraData.getBoolean("rejected");

			sb.append("<br />");
			sb.append(themeDisplay.translate("stage"));
			sb.append(": ");
			sb.append(stage);
			sb.append("<br />");
			sb.append(themeDisplay.translate("status"));
			sb.append(": ");

			if (completed && rejected) {
				sb.append(themeDisplay.translate("rejected"));
			}
			else if (completed && !rejected) {
				sb.append(themeDisplay.translate("approved"));
			}
			else {
				sb.append(themeDisplay.translate("awaiting-approval"));
			}
		}

		String body = sb.toString();

		return new SocialActivityFeedEntry(title, body);
	}

	private static final String[] _CLASS_NAMES = new String[] {
		TasksProposal.class.getName()
	};

}