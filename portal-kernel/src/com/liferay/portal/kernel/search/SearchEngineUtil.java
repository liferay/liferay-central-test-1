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

package com.liferay.portal.kernel.search;

/**
 * <a href="SearchEngineUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class SearchEngineUtil {

	/**
	 * @deprecated Use
	 * <code>com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS</code>.
	 */
	public static final int ALL_POS = -1;

	public static void addDocument(long companyId, Document doc)
		throws SearchException {

		_searchPermissionChecker.addPermissionFields(companyId, doc);

		getSearchEngine().getWriter().addDocument(companyId, doc);
	}

	public static void deleteDocument(long companyId, String uid)
		throws SearchException {

		getSearchEngine().getWriter().deleteDocument(companyId, uid);
	}

	public static void deletePortletDocuments(long companyId, String portletId)
		throws SearchException {

		getSearchEngine().getWriter().deletePortletDocuments(
			companyId, portletId);
	}

	public static SearchEngine getSearchEngine() {
		return _searchEngine;
	}

	public static boolean isIndexReadOnly() {
		return getSearchEngine().isIndexReadOnly();
	}

	public static void register(String name) {
		getSearchEngine().register(name);
	}

	public static void registerDefaultSearchEngine() {
		SearchEngineUtil.register(_defaultSearchEngineName);
	}

	public static Hits search(long companyId, Query query, int start, int end)
		throws SearchException {

		return getSearchEngine().getSearcher().search(
			companyId, query, start, end);
	}

	public static Hits search(
			long companyId, Query query, Sort sort, int start, int end)
		throws SearchException {

		return getSearchEngine().getSearcher().search(
			companyId, query, new Sort[] {sort}, start, end);
	}

	public static Hits search(
			long companyId, Query query, Sort[] sorts, int start, int end)
		throws SearchException {

		return getSearchEngine().getSearcher().search(
			companyId, query, sorts, start, end);
	}

	public static Hits search(
			long companyId, long groupId, long userId, String className,
			Query query, int start, int end)
		throws SearchException {

		if (userId > 0) {
			query = _searchPermissionChecker.getPermissionQuery(
				companyId, groupId, userId, className, query);
		}

		return search(companyId, query, start, end);
	}

	public static Hits search(
			long companyId, long groupId, long userId, String className,
			Query query, Sort sort, int start, int end)
		throws SearchException {

		if (userId > 0) {
			query = _searchPermissionChecker.getPermissionQuery(
				companyId, groupId, userId, className, query);
		}

		return search(companyId, query, sort, start, end);
	}

	public static Hits search(
			long companyId, long groupId, long userId, String className,
			Query query, Sort[] sorts, int start, int end)
		throws SearchException {

		if (userId > 0) {
			query = _searchPermissionChecker.getPermissionQuery(
				companyId, groupId, userId, className, query);
		}

		return search(companyId, query, sorts, start, end);
	}

	public static void unregister(String fromName) {
		getSearchEngine().unregister(fromName);
	}

	public static void updateDocument(long companyId, String uid, Document doc)
		throws SearchException {

		_searchPermissionChecker.addPermissionFields(companyId, doc);

		getSearchEngine().getWriter().updateDocument(companyId, uid, doc);
	}

	public static void updatePermissionFields(long resourceId) {
		_searchPermissionChecker.updatePermissionFields(resourceId);
	}

	public void setDefaultSearchEngineName(String defaultSearchEngineName) {
		_defaultSearchEngineName = defaultSearchEngineName;
	}

	public void setSearchEngine(SearchEngine searchEngine) {
		_searchEngine = searchEngine;
	}

	public void setSearchPermissionChecker(
		SearchPermissionChecker searchPermissionChecker) {

		_searchPermissionChecker = searchPermissionChecker;
	}

	private static String _defaultSearchEngineName;
	private static SearchEngine _searchEngine;
	private static SearchPermissionChecker _searchPermissionChecker;

}