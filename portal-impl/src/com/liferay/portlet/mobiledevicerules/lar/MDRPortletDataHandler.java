/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRActionActionableDynamicQuery;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleActionableDynamicQuery;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
 */
public class MDRPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "mobile_device_rules";

	public MDRPortletDataHandler() {
		setAlwaysExportable(true);
		setAlwaysStaged(true);
		setExportControls(
			new PortletDataHandlerBoolean(NAMESPACE, "actions", true, true),
			new PortletDataHandlerBoolean(NAMESPACE, "rules", true, true));
		setImportControls(new PortletDataHandlerControl[0]);
		setPublishToLiveByDefault(true);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				MDRPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		MDRRuleGroupInstanceLocalServiceUtil.deleteGroupRuleGroupInstances(
			portletDataContext.getScopeGroupId());

		MDRRuleGroupLocalServiceUtil.deleteRuleGroups(
			portletDataContext.getGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.mobiledevicerules",
			portletDataContext.getScopeGroupId());

		Element rootElement = addExportRootElement();

		final Element ruleGroupsElement = rootElement.addElement("rule-groups");
		final Element rulesElement = rootElement.addElement("rules");

		// Rules

		ActionableDynamicQuery rulesActionableDynamicQuery =
			new MDRRuleActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				MDRRule rule = (MDRRule)object;

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext,
					new Element[] {ruleGroupsElement, rulesElement}, rule);
			}

		};

		rulesActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		rulesActionableDynamicQuery.performActions();

		// Actions

		final Element ruleGroupInstancesElement = rootElement.addElement(
			"rule-group-instances");
		final Element actionsElement = rootElement.addElement("actions");

		ActionableDynamicQuery actionsActionableDynamicQuery =
			new MDRActionActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				MDRAction action = (MDRAction)object;

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext,
					new Element[] {ruleGroupInstancesElement, actionsElement},
					action);
			}

		};

		actionsActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		actionsActionableDynamicQuery.performActions();

		return rootElement.formattedString();
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.mobiledevicerules",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		// Rules

		Element rulesElement = rootElement.element("rules");

		List<Element> ruleElements = rulesElement.elements("rule");

		for (Element ruleElement : ruleElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, ruleElement);
		}

		// Actions

		Element actionsElement = rootElement.element("actions");

		List<Element> actionElements = actionsElement.elements("action");

		for (Element actionElement : actionElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, actionElement);
		}

		return null;
	}

}