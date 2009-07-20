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

import com.liferay.portal.kernel.dao.search.DAOParamUtil;
import com.liferay.portlet.shopping.model.impl.ShoppingOrderImpl;

import javax.portlet.PortletRequest;

public class OrderSearchTerms extends OrderDisplayTerms {

	public OrderSearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		number = DAOParamUtil.getLike(portletRequest, NUMBER);
		status = DAOParamUtil.getString(portletRequest, STATUS);
		firstName = DAOParamUtil.getLike(portletRequest, FIRST_NAME);
		lastName = DAOParamUtil.getLike(portletRequest, LAST_NAME);
		emailAddress = DAOParamUtil.getLike(portletRequest, EMAIL_ADDRESS);
	}

	public String getStatus() {
		if (status == null) {
			return null;
		}
		else if (status.equals(ShoppingOrderImpl.STATUSES[0])) {
			return ShoppingOrderImpl.STATUS_CHECKOUT;
		}
		else if (status.equals(ShoppingOrderImpl.STATUSES[1])) {
			return ShoppingOrderImpl.STATUS_COMPLETED;
		}
		else if (status.equals(ShoppingOrderImpl.STATUSES[2])) {
			return ShoppingOrderImpl.STATUS_DENIED;
		}
		else if (status.equals(ShoppingOrderImpl.STATUSES[3])) {
			return ShoppingOrderImpl.STATUS_PENDING;
		}
		else if (status.equals(ShoppingOrderImpl.STATUSES[4])) {
			return ShoppingOrderImpl.STATUS_REFUNDED;
		}
		else {
			return null;
		}
	}

}