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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;

import java.io.File;
import java.io.IOException;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.time.StopWatch;

/**
 * <a href="LayoutExporter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Karthik Sudarshan
 * @author Zsigmond Rab
 * @author Douglas Wong
 */
public class LayoutExporter {

	public static final String SAME_GROUP_FRIENDLY_URL =
		"/[$SAME_GROUP_FRIENDLY_URL$]";

	public static List<Portlet> getAlwaysExportablePortlets(long companyId)
		throws SystemException {

		List<Portlet> portlets = PortletLocalServiceUtil.getPortlets(companyId);

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			if (!portlet.isActive()) {
				itr.remove();

				continue;
			}

			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			if ((portletDataHandler == null) ||
				(!portletDataHandler.isAlwaysExportable())) {

				itr.remove();
			}
		}

		return portlets;
	}

	public byte[] exportLayouts(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		File file = exportLayoutsAsFile(
			groupId, privateLayout, layoutIds, parameterMap, startDate,
			endDate);

		try {
			return FileUtil.getBytes(file);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		finally {
			file.delete();
		}
	}

	public File exportLayoutsAsFile(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		boolean exportCategories = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.CATEGORIES);
		boolean exportPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean exportUserPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.USER_PERMISSIONS);
		boolean exportPortletArchivedSetups = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean exportPortletUserPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);
		boolean exportTheme = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.THEME);

		if (_log.isDebugEnabled()) {
			_log.debug("Export categories " + exportCategories);
			_log.debug("Export permissions " + exportPermissions);
			_log.debug("Export user permissions " + exportUserPermissions);
			_log.debug(
				"Export portlet archived setups " +
					exportPortletArchivedSetups);
			_log.debug(
				"Export portlet user preferences " +
					exportPortletUserPreferences);
			_log.debug("Export theme " + exportTheme);
		}

		long lastPublishDate = System.currentTimeMillis();

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		LayoutCache layoutCache = new LayoutCache();

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayout);

		long companyId = layoutSet.getCompanyId();
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		PortletDataContext context = new PortletDataContextImpl(
			companyId, groupId, parameterMap, new HashSet<String>(), startDate,
			endDate, zipWriter);

		context.setPortetDataContextListener(
			new PortletDataContextListenerImpl(context));

		// Build compatibility

		Document doc = SAXReaderUtil.createDocument();

		Element root = doc.addElement("root");

		Element header = root.addElement("header");

		header.addAttribute(
			"build-number", String.valueOf(ReleaseInfo.getBuildNumber()));
		header.addAttribute("export-date", Time.getRFC822());

		if (context.hasDateRange()) {
			header.addAttribute(
				"start-date", String.valueOf(context.getStartDate()));
			header.addAttribute(
				"end-date", String.valueOf(context.getEndDate()));
		}

		header.addAttribute("type", "layout-set");
		header.addAttribute("group-id", String.valueOf(groupId));
		header.addAttribute("private-layout", String.valueOf(privateLayout));
		header.addAttribute("theme-id", layoutSet.getThemeId());
		header.addAttribute("color-scheme-id", layoutSet.getColorSchemeId());

		// Layout Configuration Portlet

		Portlet layoutConfigurationPortlet =
			PortletLocalServiceUtil.getPortletById(
				context.getCompanyId(), PortletKeys.LAYOUT_CONFIGURATION);

		// Layouts

		Map<String, Object[]> portletIds =
			new LinkedHashMap<String, Object[]>();

		List<Layout> layouts = null;

		if ((layoutIds == null) || (layoutIds.length == 0)) {
			layouts = LayoutLocalServiceUtil.getLayouts(groupId, privateLayout);
		}
		else {
			layouts = LayoutLocalServiceUtil.getLayouts(
				groupId, privateLayout, layoutIds);
		}

		Layout firstLayout = layouts.get(0);

		List<Portlet> portlets = getAlwaysExportablePortlets(
			context.getCompanyId());

		for (Portlet portlet : portlets) {
			String portletId = portlet.getRootPortletId();

			if (portlet.isScopeable() && firstLayout.hasScopeGroup()) {
				String key = PortletPermissionUtil.getPrimaryKey(
					firstLayout.getPlid(), portletId);

				portletIds.put(
					key,
					new Object[] {
						portletId, firstLayout.getPlid(),
						firstLayout.getScopeGroup().getGroupId(),
						firstLayout.getLayoutId()
					}
				);
			}
			else {
				String key = PortletPermissionUtil.getPrimaryKey(
					0, portletId);

				if (portletIds.get(key) == null) {
					portletIds.put(
						key,
						new Object[] {
							portletId, firstLayout.getPlid(), groupId, 0L
						}
					);
				}
			}
		}

		Element layoutsEl = root.addElement("layouts");

		for (Layout layout : layouts) {
			exportLayout(
				context, layoutConfigurationPortlet, layoutCache, portletIds,
				exportPermissions, exportUserPermissions, layout, layoutsEl);
		}

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM < 5) {
			Element rolesEl = root.addElement("roles");

			// Layout roles

			if (exportPermissions) {
				_permissionExporter.exportLayoutRoles(
					layoutCache, companyId, groupId, rolesEl);
			}
		}

		// Export portlets

		long previousScopeGroupId = context.getScopeGroupId();

		Element portletsEl = root.addElement("portlets");

		for (Map.Entry<String, Object[]> portletIdsEntry :
				portletIds.entrySet()) {

			String portletId = (String)portletIdsEntry.getValue()[0];
			long plid = (Long)portletIdsEntry.getValue()[1];
			long scopeGroupId = (Long)portletIdsEntry.getValue()[2];
			long scopeLayoutId = (Long)portletIdsEntry.getValue()[3];

			Layout layout = LayoutUtil.findByPrimaryKey(plid);

			context.setPlid(layout.getPlid());
			context.setOldPlid(layout.getPlid());
			context.setScopeGroupId(scopeGroupId);
			context.setScopeLayoutId(scopeLayoutId);

			boolean[] exportPortletControls = getExportPortletControls(
				context.getCompanyId(), portletId, context, parameterMap);

			_portletExporter.exportPortlet(
				context, layoutCache, portletId, layout, portletsEl,
				defaultUserId, exportPermissions, exportPortletArchivedSetups,
				exportPortletControls[0], exportPortletControls[1],
				exportPortletUserPreferences, exportUserPermissions);
		}

		context.setScopeGroupId(previousScopeGroupId);

		// Categories

		if (exportCategories) {
			exportCategories(context);
		}

		// Comments

		_portletExporter.exportComments(context, root);

		// Locks

		_portletExporter.exportLocks(context, root);

		// Portlet data permissions

		if (exportPermissions) {
			_permissionExporter.exportPortletDataPermissions(context);
		}

		// Ratings

		_portletExporter.exportRatings(context, root);

		// Tags

		_portletExporter.exportTags(context, root);

		// Look and feel

		try {
			if (exportTheme) {
				exportTheme(layoutSet, zipWriter);
			}

			// Log

			if (_log.isInfoEnabled()) {
				if (stopWatch != null) {
					_log.info(
						"Exporting layouts takes " + stopWatch.getTime() +
							" ms");
				}
				else {
					_log.info("Exporting layouts is finished");
				}
			}

			// Zip

			context.addZipEntry("/manifest.xml", doc.formattedString());
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		try {
			return zipWriter.getFile();
		}
		finally {
			UnicodeProperties settingsProperties =
				layoutSet.getSettingsProperties();

			settingsProperties.setProperty(
				"last-publish-date", String.valueOf(lastPublishDate));

			LayoutSetLocalServiceUtil.updateSettings(
				layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
				settingsProperties.toString());
		}
	}

	protected void exportCategories(PortletDataContext context)
		throws SystemException {

		try {
			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("categories-hierarchy");

			Element vocabulariesEl = root.addElement("vocabularies");

			List<AssetVocabulary> assetVocabularies =
				AssetVocabularyLocalServiceUtil.getGroupVocabularies(
					context.getGroupId());

			for (AssetVocabulary assetVocabulary : assetVocabularies) {
				_portletExporter.exportVocabulary(
					context, vocabulariesEl, assetVocabulary);
			}

			Element categoriesEl = root.addElement("categories");

			List<AssetCategory> assetCategories =
				AssetCategoryUtil.findByGroupId(context.getGroupId());

			for (AssetCategory assetCategory : assetCategories) {
				_portletExporter.exportCategory(
					context, vocabulariesEl, categoriesEl, assetCategory);
			}

			_portletExporter.exportCategories(context, root);

			context.addZipEntry(
				context.getRootPath() + "/categories-hierarchy.xml",
				doc.formattedString());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void exportLayout(
			PortletDataContext context, Portlet layoutConfigurationPortlet,
			LayoutCache layoutCache, Map<String, Object[]> portletIds,
			boolean exportPermissions, boolean exportUserPermissions,
			Layout layout, Element layoutsEl)
		throws PortalException, SystemException {

		String path = context.getLayoutPath(
			layout.getLayoutId()) + "/layout.xml";

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element layoutEl = layoutsEl.addElement("layout");

		layoutEl.addAttribute(
			"layout-id", String.valueOf(layout.getLayoutId()));

		boolean deleteLayout = MapUtil.getBoolean(
			context.getParameterMap(), "delete_" + layout.getPlid());

		if (deleteLayout) {
			layoutEl.addAttribute("delete", String.valueOf(true));

			return;
		}

		context.setPlid(layout.getPlid());

		long scopeGroupId = context.getScopeGroupId();

		if (layout.isIconImage()) {
			Image image = ImageLocalServiceUtil.getImage(
				layout.getIconImageId());

			if (image != null) {
				String iconPath = getLayoutIconPath(context, layout, image);

				layoutEl.addElement("icon-image-path").addText(iconPath);

				context.addZipEntry(iconPath, image.getTextObj());
			}
		}

		_portletExporter.exportPortletData(
			context, layoutConfigurationPortlet, layout, null, layoutEl);

		// Layout permissions

		if (exportPermissions) {
			_permissionExporter.exportLayoutPermissions(
				context, layoutCache, context.getCompanyId(),
				context.getScopeGroupId(), layout, layoutEl,
				exportUserPermissions);
		}

		if (layout.isTypePortlet()) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			for (String portletId : layoutTypePortlet.getPortletIds()) {
				javax.portlet.PortletPreferences jxPreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						layout, portletId);

				long scopeLayoutId = GetterUtil.getLong(
					jxPreferences.getValue("lfr-scope-layout-id", null));

				if (scopeLayoutId != 0) {
					Layout scopeLayout = LayoutLocalServiceUtil.getLayout(
						scopeGroupId, layout.isPrivateLayout(), scopeLayoutId);

					Group scopeGroup = scopeLayout.getScopeGroup();

					if (scopeGroup != null) {
						scopeGroupId = scopeGroup.getGroupId();
					}
				}

				String key = PortletPermissionUtil.getPrimaryKey(
					layout.getPlid(), portletId);

				portletIds.put(
					key,
					new Object[] {
						portletId, layout.getPlid(), scopeGroupId,
						scopeLayoutId
					}
				);
			}
		}
		else if (layout.isTypeLinkToLayout()) {
			UnicodeProperties typeSettingsProperties =
				layout.getTypeSettingsProperties();

			long linkToLayoutId = GetterUtil.getLong(
				typeSettingsProperties.getProperty(
					"linkToLayoutId", StringPool.BLANK));

			Layout linkedToLayout = LayoutUtil.findByG_P_L(
				scopeGroupId, layout.isPrivateLayout(), linkToLayoutId);

			exportLayout(
				context, layoutConfigurationPortlet, layoutCache, portletIds,
				exportPermissions, exportUserPermissions, linkedToLayout,
				layoutsEl);
		}

		fixTypeSettings(layout);

		layoutEl.addAttribute("path", path);

		context.addZipEntry(path, layout);
	}

	protected void exportTheme(LayoutSet layoutSet, ZipWriter zipWriter)
		throws IOException, SystemException {

		Theme theme = layoutSet.getTheme();

		String lookAndFeelXML = ContentUtil.get(
			"com/liferay/portal/dependencies/liferay-look-and-feel.xml.tmpl");

		lookAndFeelXML = StringUtil.replace(
			lookAndFeelXML,
			new String[] {
				"[$TEMPLATE_EXTENSION$]", "[$VIRTUAL_PATH$]"
			},
			new String[] {
				theme.getTemplateExtension(), theme.getVirtualPath()
			}
		);

		String servletContextName = theme.getServletContextName();

		ServletContext servletContext = ServletContextPool.get(
			servletContextName);

		if (servletContext == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Servlet context not found for theme " +
						theme.getThemeId());
			}

			return;
		}

		File themeZip = new File(zipWriter.getPath() + "/theme.zip");

		ZipWriter themeZipWriter = ZipWriterFactoryUtil.getZipWriter(themeZip);

		themeZipWriter.addEntry("liferay-look-and-feel.xml", lookAndFeelXML);

		File cssPath = null;
		File imagesPath = null;
		File javaScriptPath = null;
		File templatesPath = null;

		if (!theme.isLoadFromServletContext()) {
			ThemeLoader themeLoader = ThemeLoaderFactory.getThemeLoader(
				servletContextName);

			if (themeLoader == null) {
				_log.error(
					servletContextName + " does not map to a theme loader");
			}
			else {
				String realPath =
					themeLoader.getFileStorage().getPath() + StringPool.SLASH +
						theme.getName();

				cssPath = new File(realPath + "/css");
				imagesPath = new File(realPath + "/images");
				javaScriptPath = new File(realPath + "/javascript");
				templatesPath = new File(realPath + "/templates");
			}
		}
		else {
			cssPath = new File(servletContext.getRealPath(theme.getCssPath()));
			imagesPath = new File(
				servletContext.getRealPath(theme.getImagesPath()));
			javaScriptPath = new File(
				servletContext.getRealPath(theme.getJavaScriptPath()));
			templatesPath = new File(
				servletContext.getRealPath(theme.getTemplatesPath()));
		}

		exportThemeFiles("css", cssPath, themeZipWriter);
		exportThemeFiles("images", imagesPath, themeZipWriter);
		exportThemeFiles("javascript", javaScriptPath, themeZipWriter);
		exportThemeFiles("templates", templatesPath, themeZipWriter);
	}

	protected void exportThemeFiles(String path, File dir, ZipWriter zipWriter)
		throws IOException {

		if ((dir == null) || (!dir.exists())) {
			return;
		}

		File[] files = dir.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				exportThemeFiles(
					path + StringPool.SLASH + file.getName(), file, zipWriter);
			}
			else {
				zipWriter.addEntry(
					path + StringPool.SLASH + file.getName(),
					FileUtil.getBytes(file));
			}
		}
	}

	protected void fixTypeSettings(Layout layout)
		throws PortalException, SystemException {

		if (!layout.isTypeURL()) {
			return;
		}

		UnicodeProperties typeSettings = layout.getTypeSettingsProperties();

		String url = GetterUtil.getString(typeSettings.getProperty("url"));

		String friendlyURLPrivateGroupPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;
		String friendlyURLPrivateUserPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;
		String friendlyURLPublicPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

		if (!url.startsWith(friendlyURLPrivateGroupPath) &&
			!url.startsWith(friendlyURLPrivateUserPath) &&
			!url.startsWith(friendlyURLPublicPath)) {

			return;
		}

		int x = url.indexOf(StringPool.SLASH, 1);

		if (x == -1) {
			return;
		}

		int y = url.indexOf(StringPool.SLASH, x + 1);

		if (y == -1) {
			return;
		}

		String friendlyURL = url.substring(x, y);
		String groupFriendlyURL = layout.getGroup().getFriendlyURL();

		if (!friendlyURL.equals(groupFriendlyURL)) {
			return;
		}

		typeSettings.setProperty(
			"url",
			url.substring(0, x) + SAME_GROUP_FRIENDLY_URL + url.substring(y));
	}

	protected boolean[] getExportPortletControls(
			long companyId, String portletId, PortletDataContext context,
			Map<String, String[]> parameterMap)
		throws SystemException {

		boolean exportPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean exportPortletDataAll = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL);
		boolean exportPortletSetup = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_SETUP);

		if (_log.isDebugEnabled()) {
			_log.debug("Export portlet data " + exportPortletData);
			_log.debug("Export all portlet data " + exportPortletDataAll);
			_log.debug("Export portlet setup " + exportPortletSetup);
		}

		boolean exportCurPortletData = exportPortletData;
		boolean exportCurPortletSetup = exportPortletSetup;

		// If PORTLET_DATA_ALL is true, this means that staging has just been
		// activated and all data and setup must be exported. There is no
		// portlet export control to check in this case.

		if (exportPortletDataAll) {
			exportCurPortletData = true;
			exportCurPortletSetup = true;
		}
		else {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, portletId);

			if (portlet != null) {
				String portletDataHandlerClass =
					portlet.getPortletDataHandlerClass();

				// Checking if the portlet has a data handler, if it doesn't,
				// the default values are the ones set in PORTLET_DATA and
				// PORTLET_SETUP. If it has a data handler, iterate over each
				// portlet export control.

				if (portletDataHandlerClass != null) {
					String rootPortletId = PortletConstants.getRootPortletId(
						portletId);

					// PORTLET_DATA and the PORTLET_DATA for this specific
					// data handler must be true

					exportCurPortletData =
						exportPortletData &&
						MapUtil.getBoolean(
							parameterMap,
							PortletDataHandlerKeys.PORTLET_DATA +
								StringPool.UNDERLINE + rootPortletId);

					// PORTLET_DATA and the PORTLET_SETUP for this specific
					// data handler must be true

					exportCurPortletSetup =
						exportPortletData &&
						MapUtil.getBoolean(
							parameterMap,
							PortletDataHandlerKeys.PORTLET_SETUP +
								StringPool.UNDERLINE + rootPortletId);
				}
			}
		}

		return new boolean[] {exportCurPortletData, exportCurPortletSetup};
	}

	protected String getLayoutIconPath(
		PortletDataContext context, Layout layout, Image image) {

		StringBundler sb = new StringBundler(5);

		sb.append(context.getLayoutPath(layout.getLayoutId()));
		sb.append("/icons/");
		sb.append(image.getImageId());
		sb.append(StringPool.PERIOD);
		sb.append(image.getType());

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutExporter.class);

	private PermissionExporter _permissionExporter = new PermissionExporter();
	private PortletExporter _portletExporter = new PortletExporter();

}