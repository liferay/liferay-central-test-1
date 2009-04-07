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

package com.liferay.portlet.rss.action;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;

/**
 * <a href="ConfigurationActionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ConfigurationActionImpl implements ConfigurationAction {

	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, portletResource);

		if (cmd.equals("remove-footer-article")) {
			removeFooterArticle(actionRequest, preferences);
		}
		else if (cmd.equals("remove-header-article")) {
			removeHeaderArticle(actionRequest, preferences);
		}
		else if (cmd.equals("set-footer-article")) {
			setFooterArticle(actionRequest, preferences);
		}
		else if (cmd.equals("set-header-article")) {
			setHeaderArticle(actionRequest, preferences);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			updateConfiguration(actionRequest, preferences);
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			try {
				preferences.store();
			}
			catch (ValidatorException ve) {
				SessionErrors.add(
					actionRequest, ValidatorException.class.getName(), ve);

				return;
			}

			SessionMessages.add(
				actionRequest, portletConfig.getPortletName() + ".doConfigure");
		}
	}

	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return "/html/portlet/rss/configuration.jsp";
	}

	protected void removeFooterArticle(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		preferences.setValues(
			"footer-article-resource-values", new String[] {"0", ""});
	}

	protected void removeHeaderArticle(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		preferences.setValues(
			"header-article-resource-values", new String[] {"0", ""});
	}

	protected void setFooterArticle(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String footerArticleResourcePrimKey = ParamUtil.getString(
			actionRequest, "resourcePrimKey");
		String footerArticleResouceTitle = ParamUtil.getString(
			actionRequest, "resourceTitle");

		preferences.setValues(
			"footer-article-resource-values",
			new String[] {
				footerArticleResourcePrimKey, footerArticleResouceTitle
			});
	}

	protected void setHeaderArticle(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String headerArticleResourcePrimKey = ParamUtil.getString(
			actionRequest, "resourcePrimKey");
		String headerArticleResouceTitle = ParamUtil.getString(
			actionRequest, "resourceTitle");

		preferences.setValues(
			"header-article-resource-values",
		new String[] {headerArticleResourcePrimKey, headerArticleResouceTitle});
	}

	protected void updateConfiguration(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String[] urls = actionRequest.getParameterValues("url");
		String[] titles = actionRequest.getParameterValues("title");
		int entriesPerFeed = ParamUtil.getInteger(
			actionRequest, "entriesPerFeed", 4);
		int expandedEntriesPerFeed = ParamUtil.getInteger(
			actionRequest, "expandedEntriesPerFeed", 1);
		boolean showFeedTitle = ParamUtil.getBoolean(
			actionRequest, "showFeedTitle");
		boolean showFeedPublishedDate = ParamUtil.getBoolean(
			actionRequest, "showFeedPublishedDate");
		boolean showFeedDescription = ParamUtil.getBoolean(
			actionRequest, "showFeedDescription");
		boolean showFeedImage = ParamUtil.getBoolean(
			actionRequest, "showFeedImage");
		String feedImageAlignment = ParamUtil.getString(
			actionRequest, "feedImageAlignment");
		boolean showFeedItemAuthor = ParamUtil.getBoolean(
			actionRequest, "showFeedItemAuthor");

		if (urls != null && titles != null) {
			preferences.setValues("urls", urls);
			preferences.setValues("titles", titles);
		}
		else {
			preferences.setValues("urls", new String[0]);
			preferences.setValues("titles", new String[0]);
		}

		preferences.setValue(
			"items-per-channel", String.valueOf(entriesPerFeed));
		preferences.setValue(
			"expanded-items-per-channel",
			String.valueOf(expandedEntriesPerFeed));
		preferences.setValue("show-feed-title", String.valueOf(showFeedTitle));
		preferences.setValue(
			"show-feed-published-date", String.valueOf(showFeedPublishedDate));
		preferences.setValue(
			"show-feed-description", String.valueOf(showFeedDescription));
		preferences.setValue("show-feed-image", String.valueOf(showFeedImage));
		preferences.setValue(
			"feed-image-alignment", String.valueOf(feedImageAlignment));
		preferences.setValue(
			"show-feed-item-author", String.valueOf(showFeedItemAuthor));
	}

}