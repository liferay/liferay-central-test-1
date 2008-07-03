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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.model.Account;

import java.util.List;

/**
 * <a href="AccountJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * <code>com.liferay.portal.service.http.AccountServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.AccountServiceJSON
 *
 */
public class AccountJSONSerializer {
	public static JSONObject toJSONObject(Account model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("accountId", model.getAccountId());
		jsonObj.put("companyId", model.getCompanyId());
		jsonObj.put("userId", model.getUserId());
		jsonObj.put("userName", model.getUserName());
		jsonObj.put("createDate", model.getCreateDate().getTime());
		jsonObj.put("modifiedDate", model.getModifiedDate().getTime());
		jsonObj.put("parentAccountId", model.getParentAccountId());
		jsonObj.put("name", model.getName());
		jsonObj.put("legalName", model.getLegalName());
		jsonObj.put("legalId", model.getLegalId());
		jsonObj.put("legalType", model.getLegalType());
		jsonObj.put("sicCode", model.getSicCode());
		jsonObj.put("tickerSymbol", model.getTickerSymbol());
		jsonObj.put("industry", model.getIndustry());
		jsonObj.put("type", model.getType());
		jsonObj.put("size", model.getSize());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portal.model.Account> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Account model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}