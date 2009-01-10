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

package com.liferay.portlet.shopping.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.shopping.DuplicateItemSKUException;
import com.liferay.portlet.shopping.ItemLargeImageNameException;
import com.liferay.portlet.shopping.ItemLargeImageSizeException;
import com.liferay.portlet.shopping.ItemMediumImageNameException;
import com.liferay.portlet.shopping.ItemMediumImageSizeException;
import com.liferay.portlet.shopping.ItemNameException;
import com.liferay.portlet.shopping.ItemSKUException;
import com.liferay.portlet.shopping.ItemSmallImageNameException;
import com.liferay.portlet.shopping.ItemSmallImageSizeException;
import com.liferay.portlet.shopping.NoSuchCategoryException;
import com.liferay.portlet.shopping.NoSuchItemException;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.model.ShoppingItemField;
import com.liferay.portlet.shopping.model.ShoppingItemPrice;
import com.liferay.portlet.shopping.model.impl.ShoppingItemPriceImpl;
import com.liferay.portlet.shopping.service.ShoppingItemServiceUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditItemAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EditItemAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateItem(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteItem(actionRequest);
			}

			if (Validator.isNotNull(cmd)) {
				sendRedirect(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCategoryException ||
				e instanceof NoSuchItemException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.shopping.error");
			}
			else if (e instanceof DuplicateItemSKUException ||
					 e instanceof ItemLargeImageNameException ||
					 e instanceof ItemLargeImageSizeException ||
					 e instanceof ItemMediumImageNameException ||
					 e instanceof ItemMediumImageSizeException ||
					 e instanceof ItemNameException ||
					 e instanceof ItemSKUException ||
					 e instanceof ItemSmallImageNameException ||
					 e instanceof ItemSmallImageSizeException) {

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getItem(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchItemException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.shopping.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.shopping.edit_item"));
	}

	protected void deleteItem(ActionRequest actionRequest) throws Exception {
		long itemId = ParamUtil.getLong(actionRequest, "itemId");

		ShoppingItemServiceUtil.deleteItem(itemId);
	}

	protected void updateItem(ActionRequest actionRequest) throws Exception {
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(
			actionRequest);

		long itemId = ParamUtil.getLong(uploadRequest, "itemId");

		long categoryId = ParamUtil.getLong(uploadRequest, "categoryId");
		String sku = ParamUtil.getString(uploadRequest, "sku");
		String name = ParamUtil.getString(uploadRequest, "name");
		String description = ParamUtil.getString(uploadRequest, "description");
		String properties = ParamUtil.getString(uploadRequest, "properties");

		int fieldsCount = ParamUtil.getInteger(uploadRequest, "fieldsCount", 1);

		List<ShoppingItemField> itemFields = new ArrayList<ShoppingItemField>();

		for (int i = 0; i < fieldsCount; i ++) {
			String fieldName = ParamUtil.getString(
				uploadRequest, "fieldName" + i);
			String fieldValues = ParamUtil.getString(
				uploadRequest, "fieldValues" + i);
			String fieldDescription = ParamUtil.getString(
				uploadRequest, "fieldDescription" + i);

			ShoppingItemField itemField = ShoppingItemFieldUtil.create(0);

			itemField.setName(fieldName);
			itemField.setValues(fieldValues);
			itemField.setDescription(fieldDescription);

			itemFields.add(itemField);
		}

		String fieldsQuantities = ParamUtil.getString(
			uploadRequest, "fieldsQuantities");

		int pricesCount = ParamUtil.getInteger(uploadRequest, "pricesCount", 1);

		List<ShoppingItemPrice> itemPrices = new ArrayList<ShoppingItemPrice>();

		for (int i = 0; i < pricesCount; i ++) {
			int minQuantity = ParamUtil.getInteger(
				uploadRequest, "minQuantity" + i);
			int maxQuantity = ParamUtil.getInteger(
				uploadRequest, "maxQuantity" + i);
			double price = ParamUtil.getDouble(uploadRequest, "price" + i);
			double discount = ParamUtil.getDouble(
				uploadRequest, "discount" + i) / 100;
			boolean taxable = ParamUtil.getBoolean(
				uploadRequest, "taxable" + i);
			double shipping = ParamUtil.getDouble(
				uploadRequest, "shipping" + i);
			boolean useShippingFormula = ParamUtil.getBoolean(
				uploadRequest, "useShippingFormula" + i);
			boolean active = ParamUtil.getBoolean(uploadRequest, "active" + i);
			int defaultPrice = ParamUtil.getInteger(
				uploadRequest, "defaultPrice");

			int status = ShoppingItemPriceImpl.STATUS_ACTIVE_DEFAULT;

			if ((defaultPrice != i) && active) {
				status = ShoppingItemPriceImpl.STATUS_ACTIVE;
			}
			else if ((defaultPrice != i) && !active) {
				status = ShoppingItemPriceImpl.STATUS_INACTIVE;
			}

			ShoppingItemPrice itemPrice = ShoppingItemPriceUtil.create(0);

			itemPrice.setMinQuantity(minQuantity);
			itemPrice.setMaxQuantity(maxQuantity);
			itemPrice.setPrice(price);
			itemPrice.setDiscount(discount);
			itemPrice.setTaxable(taxable);
			itemPrice.setShipping(shipping);
			itemPrice.setUseShippingFormula(useShippingFormula);
			itemPrice.setStatus(status);

			itemPrices.add(itemPrice);
		}

		boolean requiresShipping = ParamUtil.getBoolean(
			uploadRequest, "requiresShipping");
		int stockQuantity = ParamUtil.getInteger(
			uploadRequest, "stockQuantity");

		boolean featured = ParamUtil.getBoolean(uploadRequest, "featured");
		Boolean sale = null;

		boolean smallImage = ParamUtil.getBoolean(uploadRequest, "smallImage");
		String smallImageURL = ParamUtil.getString(
			uploadRequest, "smallImageURL");
		File smallFile = uploadRequest.getFile("smallFile");

		boolean mediumImage = ParamUtil.getBoolean(
			uploadRequest, "mediumImage");
		String mediumImageURL = ParamUtil.getString(
			uploadRequest, "mediumImageURL");
		File mediumFile = uploadRequest.getFile("mediumFile");

		boolean largeImage = ParamUtil.getBoolean(uploadRequest, "largeImage");
		String largeImageURL = ParamUtil.getString(
			uploadRequest, "largeImageURL");
		File largeFile = uploadRequest.getFile("largeFile");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			ShoppingItem.class.getName(), actionRequest);

		if (itemId <= 0) {

			// Add item

			ShoppingItemServiceUtil.addItem(
				categoryId, sku, name, description, properties,
				fieldsQuantities, requiresShipping, stockQuantity, featured,
				sale, smallImage, smallImageURL, smallFile, mediumImage,
				mediumImageURL, mediumFile, largeImage, largeImageURL,
				largeFile, itemFields, itemPrices, serviceContext);
		}
		else {

			// Update item

			ShoppingItemServiceUtil.updateItem(
				itemId, categoryId, sku, name, description, properties,
				fieldsQuantities, requiresShipping, stockQuantity, featured,
				sale, smallImage, smallImageURL, smallFile, mediumImage,
				mediumImageURL, mediumFile, largeImage, largeImageURL,
				largeFile, itemFields, itemPrices, serviceContext);
		}
	}

}