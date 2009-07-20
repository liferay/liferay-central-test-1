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

package com.liferay.portal.kernel.dao.search;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

public class SearchContainer<R> {

	public static final int DEFAULT_CUR = 1;

	public static final String DEFAULT_CUR_PARAM = "cur";

	public static final int DEFAULT_CUR_VALUE = DEFAULT_CUR;

	public static final int DEFAULT_DELTA = 20;

	public static final String DEFAULT_DELTA_PARAM = "delta";

	public static final int DEFAULT_MAX_PAGES = 25;

	public static final String DEFAULT_ORDER_BY_COL_PARAM = "orderByCol";

	public static final String DEFAULT_ORDER_BY_TYPE_PARAM = "orderByType";

	public static final int MAX_DELTA = 200;

	public SearchContainer() {
	}

	public SearchContainer(
		PortletRequest portletRequest, PortletURL iteratorURL,
		List<String> headerNames, String emptyResultsMessage) {

		this(
			portletRequest, null, null, DEFAULT_CUR_PARAM, DEFAULT_DELTA,
			iteratorURL, headerNames, emptyResultsMessage);
	}

	public SearchContainer(
		PortletRequest portletRequest, DisplayTerms displayTerms,
		DisplayTerms searchTerms, String curParam, int delta,
		PortletURL iteratorURL, List<String> headerNames,
		String emptyResultsMessage) {

		_portletRequest = portletRequest;
		_displayTerms = displayTerms;
		_searchTerms = searchTerms;

		_curParam = curParam;

		_cur = ParamUtil.getInteger(portletRequest, _curParam, DEFAULT_CUR);

		if (_cur < 1) {
			_cur = DEFAULT_CUR;
		}

		setDelta(ParamUtil.getInteger(portletRequest, _deltaParam, delta));

		_iteratorURL = iteratorURL;

		_iteratorURL.setParameter(_curParam, String.valueOf(_cur));
		_iteratorURL.setParameter(_deltaParam, String.valueOf(_delta));
		_iteratorURL.setParameter(
			DisplayTerms.KEYWORDS,
			ParamUtil.getString(portletRequest, DisplayTerms.KEYWORDS));
		_iteratorURL.setParameter(
			DisplayTerms.ADVANCED_SEARCH,
			String.valueOf(
				ParamUtil.getBoolean(
					portletRequest, DisplayTerms.ADVANCED_SEARCH)));
		_iteratorURL.setParameter(
			DisplayTerms.AND_OPERATOR,
			String.valueOf(
				ParamUtil.getBoolean(
					portletRequest, DisplayTerms.AND_OPERATOR, true)));

		if (headerNames != null) {
			_headerNames = new ArrayList<String>(headerNames.size());

			_headerNames.addAll(headerNames);
		}

		_emptyResultsMessage = emptyResultsMessage;
	}

	public int getCur() {
		return _cur;
	}

	public String getCurParam() {
		return _curParam;
	}

	public int getCurValue() {
		return getCur();
	}

	public int getDelta() {
		return _delta;
	}

	public String getDeltaParam() {
		return _deltaParam;
	}

	public DisplayTerms getDisplayTerms() {
		return _displayTerms;
	}

	public String getEmptyResultsMessage() {
		return _emptyResultsMessage;
	}

	public int getEnd() {
		return _end;
	}

	public List<String> getHeaderNames() {
		return _headerNames;
	}

	public String getId() {
		return _id;
	}

	public PortletURL getIteratorURL() {
		return _iteratorURL;
	}

	public int getMaxPages() {
		return _maxPages;
	}

	public Map<String, String> getOrderableHeaders() {
		return _orderableHeaders;
	}

	public String getOrderByCol() {
		return _orderByCol;
	}

	public String getOrderByColParam() {
		return _orderByColParam;
	}

	public OrderByComparator getOrderByComparator() {
		return _orderByComparator;
	}

	public String getOrderByType() {
		return _orderByType;
	}

	public String getOrderByTypeParam() {
		return _orderByTypeParam;
	}

	public PortletRequest getPortletRequest() {
		return _portletRequest;
	}

	public int getResultEnd() {
		return _resultEnd;
	}

	public List<ResultRow> getResultRows() {
		return _resultRows;
	}

	public List<R> getResults() {
		return _results;
	}

	public RowChecker getRowChecker() {
		return _rowChecker;
	}

	public DisplayTerms getSearchTerms() {
		return _searchTerms;
	}

	public int getStart() {
		return _start;
	}

	public int getTotal() {
		return _total;
	}

	public boolean isHover() {
		return _hover;
	}

	public void setDelta(int delta) {
		if (delta <= 0) {
			_delta = DEFAULT_DELTA;
		}
		else if (delta > MAX_DELTA) {
			_delta = MAX_DELTA;
		}
		else {
			_delta = delta;
		}

		_calculateStartAndEnd();
	}

	public void setDeltaParam(String deltaParam) {
		_deltaParam = deltaParam;
	}

	public void setEmptyResultsMessage(String emptyResultsMessage) {
		_emptyResultsMessage = emptyResultsMessage;
	}

	public void setHeaderNames(List<String> headerNames) {
		_headerNames = headerNames;
	}

	public void setHover(boolean hover) {
		_hover = hover;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setIteratorURL(PortletURL iteratorURL) {
		_iteratorURL = iteratorURL;
	}

	public void setMaxPages(int maxPages) {
		_maxPages = maxPages;
	}

	public void setOrderableHeaders(Map<String, String> orderableHeaders) {
		_orderableHeaders = orderableHeaders;
	}

	public void setOrderByCol(String orderByCol) {
		_orderByCol = orderByCol;

		_iteratorURL.setParameter(_orderByColParam, _orderByCol);
	}

	public void setOrderByColParam(String orderByColParam) {
		_orderByColParam = orderByColParam;
	}

	public void setOrderByComparator(OrderByComparator orderByComparator) {
		_orderByComparator = orderByComparator;
	}

	public void setOrderByType(String orderByType) {
		_orderByType = orderByType;

		_iteratorURL.setParameter(_orderByTypeParam, _orderByType);
	}

	public void setOrderByTypeParam(String orderByTypeParam) {
		_orderByTypeParam = orderByTypeParam;
	}

	public void setResults(List<R> results) {
		_results = results;
	}

	public void setRowChecker(RowChecker rowChecker) {
		_rowChecker = rowChecker;
	}

	public void setTotal(int total) {
		_total = total;

		if (((_cur - 1) * _delta) > _total) {
			_cur = DEFAULT_CUR;
		}

		_calculateStartAndEnd();
	}

	private void _calculateStartAndEnd() {
		_start = (_cur - 1) * _delta;
		_end = _start + _delta;

		_resultEnd = _end;

		if (_resultEnd > _total) {
			_resultEnd = _total;
		}
	}

	private int _cur;
	private String _curParam = DEFAULT_CUR_PARAM;
	private int _delta = DEFAULT_DELTA;
	private String _deltaParam = DEFAULT_DELTA_PARAM;
	private DisplayTerms _displayTerms;
	private String _emptyResultsMessage;
	private int _end;
	private List<String> _headerNames;
	private boolean _hover = true;
	private String _id;
	private PortletURL _iteratorURL;
	private int _maxPages = DEFAULT_MAX_PAGES;
	private Map<String, String> _orderableHeaders;
	private String _orderByCol;
	private String _orderByColParam = DEFAULT_ORDER_BY_COL_PARAM;
	private OrderByComparator _orderByComparator;
	private String _orderByType;
	private String _orderByTypeParam = DEFAULT_ORDER_BY_TYPE_PARAM;
	private PortletRequest _portletRequest;
	private int _resultEnd;
	private List<ResultRow> _resultRows = new ArrayList<ResultRow>();
	private List<R> _results = new ArrayList<R>();
	private RowChecker _rowChecker;
	private DisplayTerms _searchTerms;
	private int _start;
	private int _total;

}