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

package com.liferay.portal.kernel.search.messaging;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.util.Arrays;

/**
 * <a href="SearchRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class SearchRequest implements Serializable {

	public static SearchRequest add(long companyId, Document document) {
		SearchRequest searchRequest = new SearchRequest(
			SearchEngineCommand.ADD);

		searchRequest.setCompanyId(companyId);
		searchRequest.setDocument(document);

		return searchRequest;
	}

	public static SearchRequest delete(long companyId, String uid) {
		SearchRequest searchRequest = new SearchRequest(
			SearchEngineCommand.DELETE);

		searchRequest.setCompanyId(companyId);
		searchRequest.setId(uid);

		return searchRequest;
	}

	public static SearchRequest deletePortletDocuments(
		long companyId, String portletId) {

		SearchRequest searchRequest = new SearchRequest(
			SearchEngineCommand.DELETE_PORTLET_DOCUMENTS);

		searchRequest.setCompanyId(companyId);
		searchRequest.setId(portletId);

		return searchRequest;
	}

	public static SearchRequest search(
		long companyId, Query query, Sort[] sorts, int start, int end) {

		SearchRequest searchRequest = new SearchRequest(
			SearchEngineCommand.SEARCH);

		searchRequest.setCompanyId(companyId);
		searchRequest.setQuery(query);
		searchRequest.setSorts(sorts);
		searchRequest.setStart(start);
		searchRequest.setEnd(end);

		return searchRequest;
	}

	public static SearchRequest update(
		long companyId, String uid, Document document) {

		SearchRequest searchRequest = new SearchRequest(
			SearchEngineCommand.UPDATE);

		searchRequest.setCompanyId(companyId);
		searchRequest.setId(uid);
		searchRequest.setDocument(document);

		return searchRequest;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Document getDocument() {
		return _document;
	}

	public int getEnd() {
		return _end;
	}

	public String getId() {
		return _id;
	}

	public Query getQuery() {
		return _query;
	}

	public SearchEngineCommand getSearchEngineCommand() {
		return _searchEngineCommand;
	}

	public Sort[] getSorts() {
		return _sorts;
	}

	public int getStart() {
		return _start;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setDocument(Document document) {
		_document = document;
	}

	public void setEnd(int end) {
		_end = end;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setQuery(Query query) {
		_query = query;
	}

	public void setSorts(Sort[] sorts) {
		_sorts = sorts;
	}

	public void setStart(int start) {
		_start = start;
	}

	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{searchEngineCommand=");
		sb.append(_searchEngineCommand);
		sb.append(", companyId=");
		sb.append(_companyId);
		sb.append(", id=");
		sb.append(_id);
		sb.append(", document=");
		sb.append(_document);
		sb.append(", query=");
		sb.append(_query);
		sb.append(", sorts=");
		sb.append(Arrays.toString(_sorts));
		sb.append(", start=");
		sb.append(_start);
		sb.append(", end=");
		sb.append(_end);
		sb.append("}");

		return sb.toString();
	}

	private SearchRequest(SearchEngineCommand searchEngineCommand) {
		_searchEngineCommand = searchEngineCommand;
	}

	private long _companyId;
	private Document _document;
	private int _end;
	private String _id;
	private Query _query;
	private SearchEngineCommand _searchEngineCommand;
	private Sort[] _sorts;
	private int _start;

}