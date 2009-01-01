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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.model.Company;

import java.util.List;

/**
 * <a href="CompanyJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * <code>com.liferay.portal.service.http.CompanyServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.CompanyServiceJSON
 *
 */
public class CompanyJSONSerializer {
	public static JSONObject toJSONObject(Company model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("companyId", model.getCompanyId());
		jsonObj.put("accountId", model.getAccountId());
		jsonObj.put("webId", model.getWebId());
		jsonObj.put("key", model.getKey());
		jsonObj.put("virtualHost", model.getVirtualHost());
		jsonObj.put("mx", model.getMx());
		jsonObj.put("logoId", model.getLogoId());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portal.model.Company> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Company model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}