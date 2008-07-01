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

package com.liferay.portal.search;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Element;

/**
 * <a href="PortalOpenSearchImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 * @author Brian Wing Shun Chan
 *
 */
public class PortalOpenSearchImpl extends BaseOpenSearchImpl {

	public static final String SEARCH_PATH = "/c/search/open_search";

	public String search(
			HttpServletRequest request, String keywords, int startPage,
			int itemsPerPage)
		throws SearchException {

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			int start = (startPage * itemsPerPage) - itemsPerPage;
			int end = startPage * itemsPerPage;

			Hits results = CompanyLocalServiceUtil.search(
				themeDisplay.getCompanyId(), keywords, start, end);

			int total = results.getLength();

			Object[] values = addSearchResults(
				keywords, startPage, itemsPerPage, total, start,
				"Liferay Portal Search: " + keywords, SEARCH_PATH,
				themeDisplay);

			org.dom4j.Document doc = (org.dom4j.Document)values[0];
			Element root = (Element)values[1];

			for (int i = 0; i < results.getDocs().length; i++) {
				Document result = results.doc(i);

				String portletId = result.get(Field.PORTLET_ID);

				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					themeDisplay.getCompanyId(), portletId);

				if (portlet == null) {
					continue;
				}

				String portletTitle = PortalUtil.getPortletTitle(
					portletId, themeDisplay.getUser());

				long groupId = GetterUtil.getLong(result.get(Field.GROUP_ID));

				String title = StringPool.BLANK;

				PortletURL portletURL = getPortletURL(
					request, portletId, groupId);

				String url = portletURL.toString();

				Date modifedDate = result.getDate(Field.MODIFIED);

				String content = StringPool.BLANK;

				if (Validator.isNotNull(portlet.getIndexerClass())) {
					Indexer indexer = (Indexer)InstancePool.get(
						portlet.getIndexerClass());

					DocumentSummary docSummary = indexer.getDocumentSummary(
						result, portletURL);

					title = docSummary.getTitle();
					url = portletURL.toString();
					content = docSummary.getContent();

					if (portlet.getPortletId().equals(PortletKeys.JOURNAL)) {
						url = getJournalURL(themeDisplay, groupId, result);
					}
				}

				double score = results.score(i);

				addSearchResult(
					root, portletTitle + " &raquo; " + title, url, modifedDate,
					content, score);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Return\n" + doc.asXML());
			}

			return doc.asXML();

		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	protected String getJournalURL(
			ThemeDisplay themeDisplay, long groupId, Document result)
		throws Exception {

		Layout layout = themeDisplay.getLayout();

		String articleId = result.get("articleId");
		String version = result.get("version");

		List<Long> hitLayoutIds =
			JournalContentSearchLocalServiceUtil.getLayoutIds(
				layout.getGroupId(), layout.isPrivateLayout(), articleId);

		if (hitLayoutIds.size() > 0) {
			Long hitLayoutId = hitLayoutIds.get(0);

			Layout hitLayout = LayoutLocalServiceUtil.getLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				hitLayoutId.longValue());

			return PortalUtil.getLayoutURL(hitLayout, themeDisplay);
		}
		else {
			StringBuilder sb = new StringBuilder();

			sb.append(themeDisplay.getPathMain());
			sb.append("/journal/view_article_content?groupId=");
			sb.append(groupId);
			sb.append("&articleId=");
			sb.append(articleId);
			sb.append("&version=");
			sb.append(version);

			return sb.toString();
		}
	}

	private static Log _log = LogFactory.getLog(PortalOpenSearchImpl.class);

}