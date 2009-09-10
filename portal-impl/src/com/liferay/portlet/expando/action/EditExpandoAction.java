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

package com.liferay.portlet.expando.action;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.ColumnNameException;
import com.liferay.portlet.expando.ColumnTypeException;
import com.liferay.portlet.expando.DuplicateColumnNameException;
import com.liferay.portlet.expando.NoSuchColumnException;
import com.liferay.portlet.expando.ValueDataException;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.service.ExpandoColumnServiceUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexer;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditExpandoAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Aug�
 */
public class EditExpandoAction extends PortletAction {

	public static Serializable getValue(
			PortletRequest portletRequest, String name, int type)
		throws PortalException, SystemException {

		Serializable value = null;

		if (type == ExpandoColumnConstants.BOOLEAN) {
			value = ParamUtil.getBoolean(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
		}
		else if (type == ExpandoColumnConstants.DATE) {
			User user = PortalUtil.getUser(portletRequest);

			int valueDateMonth = ParamUtil.getInteger(
				portletRequest, name + "Month");
			int valueDateDay = ParamUtil.getInteger(
				portletRequest, name + "Day");
			int valueDateYear = ParamUtil.getInteger(
				portletRequest, name + "Year");
			int valueDateHour = ParamUtil.getInteger(
				portletRequest, name + "Hour");
			int valueDateMinute = ParamUtil.getInteger(
				portletRequest, name + "Minute");
			int valueDateAmPm = ParamUtil.getInteger(
				portletRequest, name + "AmPm");

			if (valueDateAmPm == Calendar.PM) {
				valueDateHour += 12;
			}

			value = PortalUtil.getDate(
				valueDateMonth, valueDateDay, valueDateYear, valueDateHour,
				valueDateMinute, user.getTimeZone(), new ValueDataException());
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
		}
		else if (type == ExpandoColumnConstants.DOUBLE) {
			value = ParamUtil.getDouble(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			String[] values = StringUtil.split(
				ParamUtil.getString(portletRequest, name), StringPool.NEW_LINE);

			value = GetterUtil.getDoubleValues(values);
		}
		else if (type == ExpandoColumnConstants.FLOAT) {
			value = ParamUtil.getFloat(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			String[] values = StringUtil.split(
				ParamUtil.getString(portletRequest, name), StringPool.NEW_LINE);

			value = GetterUtil.getFloatValues(values);
		}
		else if (type == ExpandoColumnConstants.INTEGER) {
			value = ParamUtil.getInteger(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			String[] values = StringUtil.split(
				ParamUtil.getString(portletRequest, name), StringPool.NEW_LINE);

			value = GetterUtil.getIntegerValues(values);
		}
		else if (type == ExpandoColumnConstants.LONG) {
			value = ParamUtil.getLong(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			String[] values = StringUtil.split(
				ParamUtil.getString(portletRequest, name), StringPool.NEW_LINE);

			value = GetterUtil.getLongValues(values);
		}
		else if (type == ExpandoColumnConstants.SHORT) {
			value = ParamUtil.getShort(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			String[] values = StringUtil.split(
				ParamUtil.getString(portletRequest, name), StringPool.NEW_LINE);

			value = GetterUtil.getShortValues(values);
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			value = StringUtil.split(
				ParamUtil.getString(portletRequest, name), StringPool.NEW_LINE);
		}
		else {
			value = ParamUtil.getString(portletRequest, name);
		}

		return value;
	}

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				addExpando(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteExpando(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateExpando(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchColumnException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.expando.error");
			}
			else if (e instanceof ColumnNameException ||
					 e instanceof ColumnTypeException ||
					 e instanceof DuplicateColumnNameException ||
					 e instanceof ValueDataException) {

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
			ActionUtil.getColumn(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchColumnException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.expando.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.expando.edit_expando"));
	}

	protected void addExpando(ActionRequest actionRequest) throws Exception {
		String modelResource = ParamUtil.getString(
			actionRequest, "modelResource");
		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");

		String name = ParamUtil.getString(actionRequest, "name");
		String preset = ParamUtil.getString(actionRequest, "type");

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			modelResource, resourcePrimKey);

		if (preset.startsWith("Preset")) {
			addPresetExpando(actionRequest, expandoBridge, preset, name);
		}
		else {
			int type = ParamUtil.getInteger(actionRequest, "type");

			expandoBridge.addAttribute(name, type);

			updateProperties(actionRequest, expandoBridge, name);
		}
	}

	protected void addPresetExpando(
			ActionRequest actionRequest, ExpandoBridge expandoBridge,
			String preset, String name)
		throws Exception {

		int type = 0;
		UnicodeProperties properties = expandoBridge.getAttributeProperties(
			name);

		if (preset.equals("PresetSelectionIntegerArray()")) {
			type = ExpandoColumnConstants.INTEGER_ARRAY;
			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_SELECTION,
				Boolean.TRUE.toString());
		}
		else if (preset.equals("PresetSelectionDoubleArray()")) {
			type = ExpandoColumnConstants.DOUBLE_ARRAY;
			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_SELECTION,
				Boolean.TRUE.toString());
		}
		else if (preset.equals("PresetSelectionStringArray()")) {
			type = ExpandoColumnConstants.STRING_ARRAY;
			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_SELECTION,
				Boolean.TRUE.toString());
		}
		else if (preset.equals("PresetTextBox()")) {
			type = ExpandoColumnConstants.STRING;
			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_HEIGHT, "105");
			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_WIDTH, "450");
		}
		else if (preset.equals("PresetTextBoxIndexed()")) {
			type = ExpandoColumnConstants.STRING;
			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_HEIGHT, "105");
			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_WIDTH, "450");
			properties.setProperty(
				ExpandoBridgeIndexer.INDEXABLE, Boolean.TRUE.toString());
		}
		else if (preset.equals("PresetTextFieldSecret()")) {
			type = ExpandoColumnConstants.STRING;
			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_SECRET,
				Boolean.TRUE.toString());
		}
		else {
			type = ExpandoColumnConstants.STRING;
			properties.setProperty(
				ExpandoBridgeIndexer.INDEXABLE, Boolean.TRUE.toString());
		}

		expandoBridge.addAttribute(name, type);

		expandoBridge.setAttributeProperties(name, properties);
	}

	protected void deleteExpando(ActionRequest actionRequest) throws Exception {
		long columnId = ParamUtil.getLong(actionRequest, "columnId");

		ExpandoColumnServiceUtil.deleteColumn(columnId);
	}

	protected void updateExpando(ActionRequest actionRequest) throws Exception {
		String modelResource = ParamUtil.getString(
			actionRequest, "modelResource");
		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");

		String name = ParamUtil.getString(actionRequest, "name");
		int type = ParamUtil.getInteger(actionRequest, "type");

		Serializable defaultValue = getValue(
			actionRequest, "defaultValue", type);

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			modelResource, resourcePrimKey);

		expandoBridge.setAttributeDefault(name, defaultValue);

		updateProperties(actionRequest, expandoBridge, name);
	}

	protected void updateProperties(
			ActionRequest actionRequest, ExpandoBridge expandoBridge,
			String name)
		throws Exception {

		Enumeration<String> enu = actionRequest.getParameterNames();

		UnicodeProperties properties = expandoBridge.getAttributeProperties(
			name);

		List<String> propertyNames = new ArrayList<String>();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();

			if (param.indexOf("PropertyName(") != -1) {
				String propertyName = ParamUtil.getString(actionRequest, param);

				propertyNames.add(propertyName);
			}
		}

		for (String propertyName : propertyNames) {
			String value = ParamUtil.getString(
				actionRequest, "Property(" + propertyName + ")");

			properties.setProperty(propertyName, value);
		}

		expandoBridge.setAttributeProperties(name, properties);
	}

}