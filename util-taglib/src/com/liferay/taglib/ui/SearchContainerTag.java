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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * <a href="SearchContainerTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class SearchContainerTag extends ParamAndPropertyAncestorTagImpl {

	public static final String DEFAULT_VAR = "searchContainer";

	public int doEndTag() {
		_curParam = SearchContainer.DEFAULT_CUR_PARAM;
		_delta = SearchContainer.DEFAULT_DELTA;
		_deltaParam = SearchContainer.DEFAULT_DELTA_PARAM;
		_displayTerms = null;
		_emptyResultsMessage = null;
		_hasResults = false;
		_headerNames = null;
		_hover = false;
		_id = null;
		_iteratorURL = null;
		_orderByCol = null;
		_orderByColParam = SearchContainer.DEFAULT_ORDER_BY_COL_PARAM;
		_orderByComparator = null;
		_orderByType = null;
		_orderByTypeParam = SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM;
		_rowChecker = null;
		_searchContainer = null;
		_searchTerms = null;
		_var = DEFAULT_VAR;

		return EVAL_PAGE;
	}

	public int doStartTag() throws JspException {
		try {
			HttpServletRequest request = getServletRequest();

			PortletRequest portletRequest =
				(PortletRequest)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);
			PortletResponse portletResponse =
				(PortletResponse)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			if (_iteratorURL == null) {
				_iteratorURL =
					((RenderResponse)portletResponse).createRenderURL();
			}

			if (_searchContainer == null) {
				_searchContainer = new SearchContainer(
					portletRequest, _displayTerms, _searchTerms, getCurParam(),
					getDelta(), _iteratorURL,  null, _emptyResultsMessage);
			}

			_searchContainer.setId(_id);

			if (_headerNames != null) {
				_searchContainer.setHeaderNames(_headerNames);
			}

			_searchContainer.setHover(_hover);

			if (Validator.isNotNull(_orderByColParam)) {
				_searchContainer.setOrderByColParam(_orderByColParam);
			}

			if (Validator.isNotNull(_orderByCol)) {
				_searchContainer.setOrderByCol(_orderByCol);
			}

			if (_orderByComparator != null) {
				_searchContainer.setOrderByComparator(_orderByComparator);
			}

			if (Validator.isNotNull(_orderByTypeParam)) {
				_searchContainer.setOrderByTypeParam(_orderByTypeParam);
			}

			if (Validator.isNotNull(_orderByType)) {
				_searchContainer.setOrderByType(_orderByType);
			}

			if (_rowChecker != null) {
				_searchContainer.setRowChecker(_rowChecker);
			}

			pageContext.setAttribute(_var, _searchContainer);

			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getCurParam() {
		return _curParam;
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

	public PortletURL getIteratorURL() {
		return _iteratorURL;
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

	public RowChecker getRowChecker() {
		return _rowChecker;
	}

	public SearchContainer getSearchContainer() {
		return _searchContainer;
	}

	public DisplayTerms getSearchTerms() {
		return _searchTerms;
	}

	public String getVar() {
		return _var;
	}

	public boolean isHasResults() {
		return _hasResults;
	}

	public boolean isHover() {
		return _hover;
	}

	public void setCurParam(String curParam) {
		_curParam = curParam;
	}

	public void setDelta(int delta) {
		_delta = delta;
	}

	public void setDeltaParam(String deltaParam) {
		_deltaParam = deltaParam;
	}

	public void setDisplayTerms(DisplayTerms displayTerms) {
		_displayTerms = displayTerms;
	}

	public void setEmptyResultsMessage(String emptyResultsMessage) {
		_emptyResultsMessage = emptyResultsMessage;
	}

	public void setHasResults(boolean hasResults) {
		_hasResults = hasResults;
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

	public void setOrderByCol(String orderByCol) {
		_orderByCol = orderByCol;
	}

	public void setOrderByColParam(String orderByColParam) {
		_orderByColParam = orderByColParam;
	}

	public void setOrderByComparator(OrderByComparator orderByComparator) {
		_orderByComparator = orderByComparator;
	}

	public void setOrderByType(String orderByType) {
		_orderByType = orderByType;
	}

	public void setOrderByTypeParam(String orderByTypeParam) {
		_orderByTypeParam = orderByTypeParam;
	}

	public void setRowChecker(RowChecker rowChecker) {
		_rowChecker = rowChecker;
	}

	public void setSearchContainer(SearchContainer searchContainer) {
		_searchContainer = searchContainer;
	}

	public void setSearchTerms(DisplayTerms searchTerms) {
		_searchTerms = searchTerms;
	}

	public void setVar(String var) {
		_var = var;
	}

	private String _curParam = SearchContainer.DEFAULT_CUR_PARAM;
	private int _delta = SearchContainer.DEFAULT_DELTA;
	private String _deltaParam = SearchContainer.DEFAULT_DELTA_PARAM;
	private DisplayTerms _displayTerms;
	private String _emptyResultsMessage;
	private boolean _hasResults;
	private List<String> _headerNames;
	private boolean _hover = true;
	private String _id;
	private PortletURL _iteratorURL;
	private OrderByComparator _orderByComparator;
	private String _orderByCol;
	private String _orderByColParam =
		SearchContainer.DEFAULT_ORDER_BY_COL_PARAM;
	private String _orderByType;
	private String _orderByTypeParam =
		SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM;
	private RowChecker _rowChecker;
	private SearchContainer _searchContainer;
	private DisplayTerms _searchTerms;
	private String _var = DEFAULT_VAR;

}