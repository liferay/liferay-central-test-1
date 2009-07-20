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

package com.liferay.portlet.shopping.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portlet.shopping.model.ShoppingOrder;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

public class OrderSearch extends SearchContainer<ShoppingOrder> {

	static List<String> headerNames = new ArrayList<String>();

	static {
		headerNames.add("number");
		headerNames.add("date");
		headerNames.add("status");
		headerNames.add("customer");
	}

	public static final String EMPTY_RESULTS_MESSAGE =
		"no-orders-were-found";

	public OrderSearch(PortletRequest portletRequest, PortletURL iteratorURL) {
		super(
			portletRequest, new OrderDisplayTerms(portletRequest),
			new OrderSearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		OrderDisplayTerms displayTerms =
			(OrderDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			OrderDisplayTerms.NUMBER, displayTerms.getNumber());
		iteratorURL.setParameter(
			OrderDisplayTerms.STATUS, displayTerms.getStatus());
		iteratorURL.setParameter(
			OrderDisplayTerms.FIRST_NAME, displayTerms.getFirstName());
		iteratorURL.setParameter(
			OrderDisplayTerms.LAST_NAME, displayTerms.getLastName());
		iteratorURL.setParameter(
			OrderDisplayTerms.EMAIL_ADDRESS, displayTerms.getEmailAddress());
	}

}