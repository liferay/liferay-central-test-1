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

package com.liferay.portlet.shopping.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import com.liferay.portlet.shopping.model.ShoppingItem;

import java.util.List;

/**
 * <a href="ShoppingItemJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * <code>com.liferay.portlet.shopping.service.http.ShoppingItemServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.shopping.service.http.ShoppingItemServiceJSON
 *
 */
public class ShoppingItemJSONSerializer {
	public static JSONObject toJSONObject(ShoppingItem model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("itemId", model.getItemId());
		jsonObj.put("companyId", model.getCompanyId());
		jsonObj.put("userId", model.getUserId());
		jsonObj.put("userName", model.getUserName());
		jsonObj.put("createDate", model.getCreateDate().getTime());
		jsonObj.put("modifiedDate", model.getModifiedDate().getTime());
		jsonObj.put("categoryId", model.getCategoryId());
		jsonObj.put("sku", model.getSku());
		jsonObj.put("name", model.getName());
		jsonObj.put("description", model.getDescription());
		jsonObj.put("properties", model.getProperties());
		jsonObj.put("fields", model.getFields());
		jsonObj.put("fieldsQuantities", model.getFieldsQuantities());
		jsonObj.put("minQuantity", model.getMinQuantity());
		jsonObj.put("maxQuantity", model.getMaxQuantity());
		jsonObj.put("price", model.getPrice());
		jsonObj.put("discount", model.getDiscount());
		jsonObj.put("taxable", model.getTaxable());
		jsonObj.put("shipping", model.getShipping());
		jsonObj.put("useShippingFormula", model.getUseShippingFormula());
		jsonObj.put("requiresShipping", model.getRequiresShipping());
		jsonObj.put("stockQuantity", model.getStockQuantity());
		jsonObj.put("featured", model.getFeatured());
		jsonObj.put("sale", model.getSale());
		jsonObj.put("smallImage", model.getSmallImage());
		jsonObj.put("smallImageId", model.getSmallImageId());
		jsonObj.put("smallImageURL", model.getSmallImageURL());
		jsonObj.put("mediumImage", model.getMediumImage());
		jsonObj.put("mediumImageId", model.getMediumImageId());
		jsonObj.put("mediumImageURL", model.getMediumImageURL());
		jsonObj.put("largeImage", model.getLargeImage());
		jsonObj.put("largeImageId", model.getLargeImageId());
		jsonObj.put("largeImageURL", model.getLargeImageURL());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.shopping.model.ShoppingItem> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ShoppingItem model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}