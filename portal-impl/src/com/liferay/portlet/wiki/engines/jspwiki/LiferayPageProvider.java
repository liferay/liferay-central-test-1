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

package com.liferay.portlet.wiki.engines.jspwiki;

import com.ecyrd.jspwiki.QueryItem;
import com.ecyrd.jspwiki.WikiEngine;
import com.ecyrd.jspwiki.WikiPage;
import com.ecyrd.jspwiki.providers.ProviderException;
import com.ecyrd.jspwiki.providers.WikiPageProvider;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * <a href="LiferayPageProvider.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class LiferayPageProvider implements WikiPageProvider {

	public static com.ecyrd.jspwiki.WikiPage toJSPWikiPage(
		com.liferay.portlet.wiki.model.WikiPage page, WikiEngine engine) {

		com.ecyrd.jspwiki.WikiPage jspWikiPage = new com.ecyrd.jspwiki.WikiPage(
			engine, page.getTitle());

		jspWikiPage.setAuthor(page.getUserName());
		jspWikiPage.setVersion((int)(page.getVersion() * 10));
		jspWikiPage.setLastModified(page.getCreateDate());

		return jspWikiPage;
	}

	public void deletePage(String name) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deletePage(" + name + ")");
		}
	}

	public void deleteVersion(String title, int version) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Invoking deleteVersion(" + title + ", " + version + ")");
		}
	}

	public Collection<WikiPage> findPages(QueryItem[] query) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking findPages(" + query + ")");
		}

		return Collections.EMPTY_LIST;
	}

	public Collection<WikiPage> getAllChangedSince(Date date) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking getAllChangedSince(" + date + ")");
		}

		try {
			return getAllPages();
		}
		catch (ProviderException e) {
			_log.error("Could not get changed pages", e);

			return Collections.EMPTY_LIST;
		}
	}

	public Collection<WikiPage> getAllPages() throws ProviderException {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking getAllPages()");
		}

		List<WikiPage> jspWikiPages = new ArrayList<WikiPage>();

		try {
			int count = WikiPageLocalServiceUtil.getPagesCount(_nodeId, true);

			List<com.liferay.portlet.wiki.model.WikiPage> pages =
				WikiPageLocalServiceUtil.getPages(_nodeId, true, 0, count);

			for (com.liferay.portlet.wiki.model.WikiPage page : pages) {
				jspWikiPages.add(toJSPWikiPage(page, _engine));
			}
		}
		catch (SystemException se) {
			throw new ProviderException(se.toString());
		}

		return jspWikiPages;
	}

	public int getPageCount() throws ProviderException {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking getPageCount()");
		}

		try {
			return WikiPageLocalServiceUtil.getPagesCount(_nodeId);
		}
		catch (SystemException se) {
			throw new ProviderException(se.toString());
		}
	}

	public com.ecyrd.jspwiki.WikiPage getPageInfo(String title, int version)
		throws ProviderException {

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking getPageInfo(" + title + ", " + version + ")");
		}

		try {
			com.liferay.portlet.wiki.model.WikiPage page =
				WikiPageLocalServiceUtil.getPage(_nodeId, title);

			return toJSPWikiPage(page, _engine);
		}
		catch (NoSuchPageException nspe) {
			return null;
		}
		catch (Exception e) {
			throw new ProviderException(e.toString());
		}
	}

	public String getPageText(String title, int version)
		throws ProviderException {

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking getPageText(" + title + ", " + version + ")");
		}

		try {
			com.liferay.portlet.wiki.model.WikiPage page =
				WikiPageLocalServiceUtil.getPage(_nodeId, title);

			return page.getContent();
		}
		catch (Exception e) {
			throw new ProviderException(e.toString());
		}
	}

	public String getProviderInfo() {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking getProviderInfo()");
		}

		return LiferayPageProvider.class.getName();
	}

	public List<WikiPage> getVersionHistory(String title) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking getVersionHistory(" + title + ")");
		}

		return Collections.EMPTY_LIST;
	}

	public void initialize(WikiEngine engine, Properties props) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking initialize(" + engine + ", " + props + ")");
		}

		_engine = engine;
		_nodeId = GetterUtil.getLong(props.getProperty("nodeId"));
	}

	public void movePage(String from, String to) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking movePage(" + from + ", " + to + ")");
		}
	}

	public boolean pageExists(String title) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking pageExists(" + title + ")");
		}

		try {
			if (WikiPageLocalServiceUtil.getPagesCount(
					_nodeId, title, true) > 0) {

				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	public void putPageText(com.ecyrd.jspwiki.WikiPage page, String text) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking putPageText(" + page + ", " + text + ")");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LiferayPageProvider.class);

	private WikiEngine _engine;
	private long _nodeId;

}