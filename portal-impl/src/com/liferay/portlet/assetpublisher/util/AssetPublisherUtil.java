/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.assetpublisher.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <a href="AssetPublisherUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class AssetPublisherUtil {

	public static void addAndStoreSelection(
			ActionRequest actionRequest, String className, long classPK,
			int assetEntryOrder)
		throws Exception {

		String referringPortletResource =
			ParamUtil.getString(actionRequest, "referringPortletResource");

		if (Validator.isNull(referringPortletResource)) {
			return;
		}

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			className, classPK);

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, referringPortletResource);

		addSelection(
			className, assetEntry.getEntryId(), assetEntryOrder, preferences);

		preferences.store();
	}

	public static void addRecentFolderId(
		PortletRequest portletRequest, String className, long classPK) {

		_getRecentFolderIds(portletRequest).put(className, classPK);
	}

	public static void addSelection(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String assetEntryType = ParamUtil.getString(
			actionRequest, "assetEntryType");
		long assetEntryId = ParamUtil.getLong(actionRequest, "assetEntryId");
		int assetEntryOrder = ParamUtil.getInteger(
			actionRequest, "assetEntryOrder");

		addSelection(
			assetEntryType, assetEntryId, assetEntryOrder, preferences);
	}

	public static void addSelection(
			String assetEntryType, long assetEntryId, int assetEntryOrder,
			PortletPreferences preferences)
		throws Exception {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			assetEntryId);

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetEntry.getClassName());

		AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
			assetEntry.getClassPK());

		String[] assetEntryXmls = preferences.getValues(
			"asset-entry-xml", new String[0]);

		String assetEntryXml = _getAssetEntryXml(
			assetEntryType, assetRenderer.getUuid());

		if (assetEntryOrder > -1) {
			assetEntryXmls[assetEntryOrder] = assetEntryXml;
		}
		else {
			assetEntryXmls = ArrayUtil.append(assetEntryXmls, assetEntryXml);
		}

		preferences.setValues("asset-entry-xml", assetEntryXmls);
	}

	public static AssetEntryQuery getAssetEntryQuery(
			PortletPreferences preferences, long scopeGroupId)
		throws Exception {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		long[] allAssetCategoryIds = new long[0];
		long[] anyAssetCategoryIds = new long[0];
		long[] notAllAssetCategoryIds = new long[0];
		long[] notAnyAssetCategoryIds = new long[0];

		String[] allAssetTagNames = new String[0];
		String[] anyAssetTagNames = new String[0];
		String[] notAllAssetTagNames = new String[0];
		String[] notAnyAssetTagNames = new String[0];

		for (int i = 0; true; i++) {
			String[] queryValues = preferences.getValues(
				"queryValues" + i, null);

			if ((queryValues == null) || (queryValues.length == 0)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				preferences.getValue("queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				preferences.getValue("queryAndOperator" + i, StringPool.BLANK));
			String queryName = preferences.getValue(
				"queryName" + i, StringPool.BLANK);

			if (Validator.equals(queryName, "assetCategories")) {
				long[] assetCategoryIds = GetterUtil.getLongValues(queryValues);

				if (queryContains && queryAndOperator) {
					allAssetCategoryIds = assetCategoryIds;
				}
				else if (queryContains && !queryAndOperator) {
					anyAssetCategoryIds = assetCategoryIds;
				}
				else if (!queryContains && queryAndOperator) {
					notAllAssetCategoryIds = assetCategoryIds;
				}
				else {
					notAnyAssetCategoryIds = assetCategoryIds;
				}
			}
			else {
				if (queryContains && queryAndOperator) {
					allAssetTagNames = queryValues;
				}
				else if (queryContains && !queryAndOperator) {
					anyAssetTagNames = queryValues;
				}
				else if (!queryContains && queryAndOperator) {
					notAllAssetTagNames = queryValues;
				}
				else {
					notAnyAssetTagNames = queryValues;
				}
			}
		}

		long[] allAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
			scopeGroupId, allAssetTagNames);
		long[] anyAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
			scopeGroupId, anyAssetTagNames);
		long[] notAllAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
			scopeGroupId, notAllAssetTagNames);
		long[] notAnyAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
			scopeGroupId, notAnyAssetTagNames);

		assetEntryQuery.setAllCategoryIds(allAssetCategoryIds);
		assetEntryQuery.setAllTagIds(allAssetTagIds);
		assetEntryQuery.setAnyCategoryIds(anyAssetCategoryIds);
		assetEntryQuery.setAnyTagIds(anyAssetTagIds);
		assetEntryQuery.setNotAllCategoryIds(notAllAssetCategoryIds);
		assetEntryQuery.setNotAllTagIds(notAllAssetTagIds);
		assetEntryQuery.setNotAnyCategoryIds(notAnyAssetCategoryIds);
		assetEntryQuery.setNotAnyTagIds(notAnyAssetTagIds);

		return assetEntryQuery;
	}

	public static String[] getAssetTagNames(
			PortletPreferences preferences, long scopeGroupId)
		throws Exception {

		String[] allAssetTagNames = new String[0];

		for (int i = 0; true; i++) {
			String[] queryValues = preferences.getValues(
				"queryValues" + i, null);

			if ((queryValues == null) || (queryValues.length == 0)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				preferences.getValue("queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				preferences.getValue("queryAndOperator" + i, StringPool.BLANK));
			String queryName = preferences.getValue(
				"queryName" + i, StringPool.BLANK);

			if (!Validator.equals(queryName, "assetCategories") &&
				queryContains && queryAndOperator) {

				allAssetTagNames = queryValues;
			}
		}

		return allAssetTagNames;
	}

	public static long[] getClassNameIds(
		PortletPreferences preferences, long[] availableClassNameIds) {

		boolean anyAssetType = GetterUtil.getBoolean(
			preferences.getValue("any-asset-type", Boolean.TRUE.toString()));

		long[] classNameIds = null;

		if (!anyAssetType &&
			(preferences.getValues("class-name-ids", null) != null)) {

			classNameIds = GetterUtil.getLongValues(
				preferences.getValues("class-name-ids", null));
		}
		else {
			classNameIds = availableClassNameIds;
		}

		return classNameIds;
	}

	public static long[] getGroupIds(
		PortletPreferences preferences, long scopeGroupId, Layout layout) {

		long[] groupIds = new long[] {scopeGroupId};

		boolean defaultScope = GetterUtil.getBoolean(
			preferences.getValue("default-scope", null), true);

		if (!defaultScope) {
			String[] scopeIds = preferences.getValues(
				"scope-ids",
				new String[] {"group" + StringPool.UNDERLINE + scopeGroupId});

			groupIds = new long[scopeIds.length];

			for (int i = 0; i < scopeIds.length; i++) {
				try {
					String[] scopeIdFragments = StringUtil.split(
						scopeIds[i], StringPool.UNDERLINE);

					if (scopeIdFragments[0].equals("Layout")) {
						long scopeIdLayoutId = GetterUtil.getLong(
							scopeIdFragments[1]);

						Layout scopeIdLayout =
							LayoutLocalServiceUtil.getLayout(
								scopeGroupId, layout.isPrivateLayout(),
								scopeIdLayoutId);

						Group scopeIdGroup = scopeIdLayout.getScopeGroup();

						groupIds[i] = scopeIdGroup.getGroupId();
					}
					else {
						long scopeIdGroupId = GetterUtil.getLong(
							scopeIdFragments[1]);

						groupIds[i] = scopeIdGroupId;
					}
				}
				catch (Exception e) {
					continue;
				}
			}
		}

		return groupIds;
	}

	public static long getRecentFolderId(
		PortletRequest portletRequest, String className) {

		Long classPK = _getRecentFolderIds(portletRequest).get(className);

		if (classPK == null) {
			return 0;
		}
		else {
			return classPK.longValue();
		}
	}

	public static void removeAndStoreSelection(
			List<String> assetEntryUuids, PortletPreferences preferences)
		throws Exception {

		if (assetEntryUuids.size() == 0) {
			return;
		}

		String[] assetEntryXmls = preferences.getValues(
			"asset-entry-xml", new String[0]);

		List<String> assetEntryXmlsList = ListUtil.fromArray(assetEntryXmls);

		Iterator<String> itr = assetEntryXmlsList.iterator();

		while (itr.hasNext()) {
			String assetEntryXml = itr.next();

			Document doc = SAXReaderUtil.read(assetEntryXml);

			Element root = doc.getRootElement();

			String assetEntryUuid = root.element("asset-entry-uuid").getText();

			if (assetEntryUuids.contains(assetEntryUuid)) {
				itr.remove();
			}
		}

		preferences.setValues(
			"asset-entry-xml",
			assetEntryXmlsList.toArray(new String[assetEntryXmlsList.size()]));

		preferences.store();
	}

	public static void removeRecentFolderId(
		PortletRequest portletRequest, String className, long classPK) {

		if (getRecentFolderId(portletRequest, className) == classPK) {
			_getRecentFolderIds(portletRequest).remove(className);
		}
	}

	private static String _getAssetEntryXml(
		String assetEntryType, String assetEntryUuid) {

		String xml = null;

		try {
			Document doc = SAXReaderUtil.createDocument(StringPool.UTF8);

			Element assetEntryEl = doc.addElement("asset-entry");

			assetEntryEl.addElement("asset-entry-type").addText(assetEntryType);
			assetEntryEl.addElement("asset-entry-uuid").addText(assetEntryUuid);

			xml = doc.formattedString(StringPool.BLANK);
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe);
			}
		}

		return xml;
	}

	private static Map<String, Long> _getRecentFolderIds(
		PortletRequest portletRequest) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);
		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String key =
			AssetPublisherUtil.class + "_" + themeDisplay.getScopeGroupId();

		Map<String, Long> recentFolderIds =
			(Map<String, Long>)session.getAttribute(key);

		if (recentFolderIds == null) {
			recentFolderIds = new HashMap<String, Long>();
		}

		session.setAttribute(key, recentFolderIds);

		return recentFolderIds;
	}

	private static Log _log = LogFactoryUtil.getLog(AssetPublisherUtil.class);

}