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

package com.liferay.portal.lar;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.LARFileException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.comm.CommLink;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ColorSchemeImpl;
import com.liferay.portal.model.impl.LayoutTypePortletImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.LocalizationUtil;
import com.liferay.util.MapUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LayoutImporter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 *
 */
public class LayoutImporter {

	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException, SystemException {

		boolean deleteMissingLayouts = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			Boolean.TRUE.booleanValue());
		boolean deletePortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.DELETE_PORTLET_DATA);
		boolean importPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean importUserPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean importPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean importPortletSetup = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_SETUP);
		boolean importPortletArchivedSetups = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean importPortletUserPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);
		boolean importTheme = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.THEME);
		String layoutsImportMode = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_ID);
		String portletsMergeMode = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.PORTLETS_MERGE_MODE,
			PortletDataHandlerKeys.PORTLETS_MERGE_MODE_REPLACE);
		String userIdStrategy = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);

		if (_log.isDebugEnabled()) {
			_log.debug("Delete portlet data " + deletePortletData);
			_log.debug("Import permissions " + importPermissions);
			_log.debug("Import user permissions " + importUserPermissions);
			_log.debug("Import portlet data " + importPortletData);
			_log.debug("Import portlet setup " + importPortletSetup);
			_log.debug(
				"Import portlet archived setups " +
					importPortletArchivedSetups);
			_log.debug(
				"Import portlet user preferences " +
					importPortletUserPreferences);
			_log.debug("Import theme " + importTheme);
		}

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		LayoutCache layoutCache = new LayoutCache();

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayout);

		long companyId = layoutSet.getCompanyId();

		User user = UserUtil.findByPrimaryKey(userId);

		UserIdStrategy strategy = _portletImporter.getUserIdStrategy(
			user, userIdStrategy);

		ZipReader zipReader = new ZipReader(is);

		PortletDataContext context = new PortletDataContextImpl(
			companyId, groupId, parameterMap, new HashSet<String>(), strategy,
			zipReader);

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			companyId, GroupConstants.GUEST);

		// Zip

		Element root = null;
		byte[] themeZip = null;

		// Manifest

		String xml = context.getZipEntryAsString("/manifest.xml");

		if (xml == null) {
			throw new LARFileException("manifest.xml not found in the LAR");
		}

		try {
			Document doc = SAXReaderUtil.read(xml);

			root = doc.getRootElement();
		}
		catch (Exception e) {
			throw new LARFileException(e);
		}

		// Build compatibility

		Element header = root.element("header");

		int buildNumber = ReleaseInfo.getBuildNumber();

		int importBuildNumber = GetterUtil.getInteger(
			header.attributeValue("build-number"));

		if (buildNumber != importBuildNumber) {
			throw new LayoutImportException(
				"LAR build number " + importBuildNumber + " does not match " +
					"portal build number " + buildNumber);
		}

		// Type compatibility

		String larType = header.attributeValue("type");

		if (!larType.equals("layout-set")) {
			throw new LARTypeException(
				"Invalid type of LAR file (" + larType + ")");
		}

		// Import GroupId

		long importGroupId = GetterUtil.getLong(
			header.attributeValue("group-id"));

		context.setImportGroupId(importGroupId);

		// Look and feel

		if (importTheme) {
			themeZip = context.getZipEntryAsByteArray("theme.zip");
		}

		// Look and feel

		String themeId = header.attributeValue("theme-id");
		String colorSchemeId = header.attributeValue("color-scheme-id");

		boolean useThemeZip = false;

		if (themeZip != null) {
			try {
				String importThemeId = importTheme(layoutSet, themeZip);

				if (importThemeId != null) {
					themeId = importThemeId;
					colorSchemeId =
						ColorSchemeImpl.getDefaultRegularColorSchemeId();

					useThemeZip = true;
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Importing theme takes " + stopWatch.getTime() + " ms");
				}
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}

		boolean wapTheme = false;

		LayoutSetLocalServiceUtil.updateLookAndFeel(
			groupId, privateLayout, themeId, colorSchemeId, StringPool.BLANK,
			wapTheme);

		// Read comments, ratings, and tags to make them available to the data
		// handlers through the context

		_portletImporter.readComments(context, root);
		_portletImporter.readRatings(context, root);
		_portletImporter.readTags(context, root);

		// Layouts

		List<Layout> previousLayouts = LayoutUtil.findByG_P(
			groupId, privateLayout);

		Set<Long> newLayoutIds = new HashSet<Long>();

		Map<Long, Long> newLayoutIdPlidMap =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(Layout.class);

		List<Element> layoutEls = root.element("layouts").elements("layout");

		if (_log.isDebugEnabled()) {
			if (layoutEls.size() > 0) {
				_log.debug("Importing layouts");
			}
		}

		for (Element layoutRefEl : layoutEls) {
			long layoutId = GetterUtil.getInteger(
				layoutRefEl.attributeValue("layout-id"));

			long oldLayoutId = layoutId;

			String layoutPath = layoutRefEl.attributeValue("path");

			Element layoutEl = null;

			try {
				Document layoutDoc = SAXReaderUtil.read(
					context.getZipEntryAsString(layoutPath));

				layoutEl = layoutDoc.getRootElement();
			}
			catch (DocumentException de) {
				throw new SystemException(de);
			}

			long parentLayoutId = GetterUtil.getInteger(
				layoutEl.elementText("parent-layout-id"));

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Importing layout with layout id " + layoutId +
						" and parent layout id " + parentLayoutId);
			}

			long oldPlid = GetterUtil.getInteger(
				layoutEl.attributeValue("old-plid"));

			String name = layoutEl.elementText("name");
			String title = layoutEl.elementText("title");
			String description = layoutEl.elementText("description");
			String type = layoutEl.elementText("type");
			String typeSettings = layoutEl.elementText("type-settings");
			boolean hidden = GetterUtil.getBoolean(
				layoutEl.elementText("hidden"));
			String friendlyURL = layoutEl.elementText("friendly-url");
			boolean iconImage = GetterUtil.getBoolean(
				layoutEl.elementText("icon-image"));

			byte[] iconBytes = null;

			if (iconImage) {
				String path = layoutEl.elementText("icon-image-path");

				iconBytes = context.getZipEntryAsByteArray(path);
			}

			if (useThemeZip) {
				themeId = StringPool.BLANK;
				colorSchemeId = StringPool.BLANK;
			}
			else {
				themeId = layoutEl.elementText("theme-id");
				colorSchemeId = layoutEl.elementText("color-scheme-id");
			}

			String wapThemeId = layoutEl.elementText("wap-theme-id");
			String wapColorSchemeId = layoutEl.elementText(
				"wap-color-scheme-id");
			String css = layoutEl.elementText("css");
			int priority = GetterUtil.getInteger(
				layoutEl.elementText("priority"));

			Layout layout = null;

			if (layoutsImportMode.equals(
					PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_ADD_AS_NEW)) {

				layoutId = LayoutLocalServiceUtil.getNextLayoutId(
					groupId, privateLayout);
				friendlyURL = StringPool.SLASH + layoutId;
			}
			else if (layoutsImportMode.equals(
					PortletDataHandlerKeys.
						LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_NAME)) {

				Locale locale = LocaleUtil.getDefault();

				String localizedName = LocalizationUtil.getLocalization(
					name, LocaleUtil.toLanguageId(locale));

				for (Layout curLayout : previousLayouts) {
					if (curLayout.getName(locale).equals(localizedName)) {
						layout = curLayout;
						break;
					}
				}
			}
			else {
				layout = LayoutUtil.fetchByG_P_L(
					groupId, privateLayout, layoutId);
			}

			if (_log.isDebugEnabled()) {
				if (layout == null) {
					_log.debug(
						"Layout with {groupId=" + groupId + ",privateLayout=" +
							privateLayout + ",layoutId=" + layoutId +
								"} does not exist");
				}
				else {
					_log.debug(
						"Layout with {groupId=" + groupId + ",privateLayout=" +
							privateLayout + ",layoutId=" + layoutId +
								"} exists");
				}
			}

			if (layout == null) {
				long plid = CounterLocalServiceUtil.increment();

				layout = LayoutUtil.create(plid);

				layout.setGroupId(groupId);
				layout.setPrivateLayout(privateLayout);
				layout.setLayoutId(layoutId);
			}

			layout.setCompanyId(user.getCompanyId());
			layout.setParentLayoutId(parentLayoutId);
			layout.setName(name);
			layout.setTitle(title);
			layout.setDescription(description);
			layout.setType(type);

			if (layout.getType().equals(LayoutConstants.TYPE_PORTLET) &&
					Validator.isNotNull(layout.getTypeSettings()) &&
						!portletsMergeMode.equals(
							PortletDataHandlerKeys.
								PORTLETS_MERGE_MODE_REPLACE)) {
				mergePortlets(layout, typeSettings, portletsMergeMode);
			}
			else {
				layout.setTypeSettings(typeSettings);
			}

			layout.setHidden(hidden);
			layout.setFriendlyURL(friendlyURL);

			if (iconImage) {
				layout.setIconImage(iconImage);

				if (layout.isNew()) {
					long iconImageId = CounterLocalServiceUtil.increment();

					layout.setIconImageId(iconImageId);
				}
			}

			layout.setThemeId(themeId);
			layout.setColorSchemeId(colorSchemeId);
			layout.setWapThemeId(wapThemeId);
			layout.setWapColorSchemeId(wapColorSchemeId);
			layout.setCss(css);
			layout.setPriority(priority);

			fixTypeSettings(layout);

			LayoutUtil.update(layout, false);

			if ((iconBytes != null) && (iconBytes.length > 0)) {
				ImageLocalServiceUtil.updateImage(
					layout.getIconImageId(), iconBytes);
			}

			context.setPlid(layout.getPlid());
			context.setOldPlid(oldPlid);

			newLayoutIdPlidMap.put(oldLayoutId, layout.getPlid());

			newLayoutIds.add(layoutId);

			Element permissionsEl = layoutEl.element("permissions");

			// Layout permissions

			if (importPermissions) {
				importLayoutPermissions(
					layoutCache, companyId, groupId, guestGroup, layout,
					permissionsEl, importUserPermissions);
			}

			_portletImporter.importPortletData(
				context, PortletKeys.LAYOUT_CONFIGURATION, null, layoutEl);
		}

		List<Element> portletEls = root.element("portlets").elements("portlet");

		// Delete portlet data

		if (deletePortletData) {
			if (_log.isDebugEnabled()) {
				if (portletEls.size() > 0) {
					_log.debug("Deleting portlet data");
				}
			}

			for (Element portletRefEl : portletEls) {
				String portletId = portletRefEl.attributeValue("portlet-id");
				long layoutId = GetterUtil.getLong(
					portletRefEl.attributeValue("layout-id"));
				long plid = newLayoutIdPlidMap.get(layoutId);

				context.setPlid(plid);

				_portletImporter.deletePortletData(context, portletId, plid);
			}
		}

		// Import portlets

		if (_log.isDebugEnabled()) {
			if (portletEls.size() > 0) {
				_log.debug("Importing portlets");
			}
		}

		for (Element portletRefEl : portletEls) {
			String portletPath = portletRefEl.attributeValue("path");
			String portletId = portletRefEl.attributeValue("portlet-id");
			long layoutId = GetterUtil.getLong(
				portletRefEl.attributeValue("layout-id"));
			long oldPlid = GetterUtil.getLong(
				portletRefEl.attributeValue("old-plid"));
			long plid = newLayoutIdPlidMap.get(layoutId);

			Layout layout = LayoutUtil.findByPrimaryKey(plid);

			context.setPlid(plid);
			context.setOldPlid(oldPlid);

			Element portletEl = null;

			try {
				Document portletDoc = SAXReaderUtil.read(
					context.getZipEntryAsString(portletPath));

				portletEl = portletDoc.getRootElement();
			}
			catch (DocumentException de) {
				throw new SystemException(de);
			}

			// The order of the import is important. You must always import
			// the portlet preferences first, then the portlet data, then
			// the portlet permissions. The import of the portlet data
			// assumes that portlet preferences already exist.

			// Portlet preferences

			_portletImporter.importPortletPreferences(
				context, layoutSet.getCompanyId(), layout.getGroupId(),
				layout.getPlid(), null, portletEl, importPortletSetup,
				importPortletArchivedSetups, importPortletUserPreferences);

			// Portlet data

			Element portletDataEl = portletEl.element("portlet-data");

			if (importPortletData && portletDataEl != null) {
				_portletImporter.importPortletData(
					context, portletId, plid, portletDataEl);
			}

			// Portlet permissions

			Element permissionsEl = portletEl.element("permissions");

			if (importPermissions && (permissionsEl != null)) {
				importPortletPermissions(
					layoutCache, companyId, groupId, guestGroup, layout,
					permissionsEl, importUserPermissions);
			}

			// Archived setups

			_portletImporter.importPortletPreferences(
				context, layoutSet.getCompanyId(), groupId, 0, null, portletEl,
				importPortletSetup, importPortletArchivedSetups,
				importPortletUserPreferences);

			// Portlet roles

			Element rolesEl = portletEl.element("roles");

			if (importPermissions && (rolesEl != null)) {
				importPortletRoles(layoutCache, companyId, groupId, portletEl);
				importPortletRoles(
					layoutCache, companyId, groupId, portletId, rolesEl);
			}
		}

		Element rolesEl = root.element("roles");

		// Layout roles

		if (importPermissions) {
			importLayoutRoles(layoutCache, companyId, groupId, rolesEl);
		}

		// Delete missing layouts

		if (deleteMissingLayouts) {
			deleteMissingLayouts(
				groupId, privateLayout, newLayoutIds, previousLayouts);
		}

		// Page count

		LayoutSetLocalServiceUtil.updatePageCount(groupId, privateLayout);

		if (_log.isInfoEnabled()) {
			_log.info("Importing layouts takes " + stopWatch.getTime() + " ms");
		}
	}

	protected void deleteMissingLayouts(
			long groupId, boolean privateLayout, Set<Long> newLayoutIds,
			List<Layout> previousLayouts)
		throws PortalException, SystemException {

		// Layouts

		if (_log.isDebugEnabled()) {
			if (newLayoutIds.size() > 0) {
				_log.debug("Delete missing layouts");
			}
		}

		for (Layout layout : previousLayouts) {
			if (!newLayoutIds.contains(layout.getLayoutId())) {
				try {
					LayoutLocalServiceUtil.deleteLayout(layout, false);
				}
				catch (NoSuchLayoutException nsle) {
				}
			}
		}

		// Layout set

		LayoutSetLocalServiceUtil.updatePageCount(groupId, privateLayout);
	}

	protected void fixTypeSettings(Layout layout) {
		if (layout.getType().equals(LayoutConstants.TYPE_URL)) {
			UnicodeProperties typeSettings = layout.getTypeSettingsProperties();

			String url = GetterUtil.getString(typeSettings.getProperty("url"));

			String friendlyURLPrivateGroupPath =
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;
			String friendlyURLPrivateUserPath =
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;
			String friendlyURLPublicPath =
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

			if (url.startsWith(friendlyURLPrivateGroupPath) ||
				url.startsWith(friendlyURLPrivateUserPath) ||
				url.startsWith(friendlyURLPublicPath)) {

				int x = url.indexOf(StringPool.SLASH, 1);

				if (x > 0) {
					int y = url.indexOf(StringPool.SLASH, x + 1);

					if (y > x) {
						String fixedUrl = url.substring(0, x) +

						layout.getGroup().getFriendlyURL() +

						url.substring(y);

						typeSettings.setProperty("url", fixedUrl);
					}
				}
			}
		}
	}

	protected List<String> getActions(Element el) {
		List<String> actions = new ArrayList<String>();

		Iterator<Element> itr = el.elements("action-key").iterator();

		while (itr.hasNext()) {
			Element actionEl = itr.next();

			actions.add(actionEl.getText());
		}

		return actions;
	}

	protected void importGroupPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element parentEl,
			String elName, boolean portletActions)
		throws PortalException, SystemException {

		Element actionEl = parentEl.element(elName);

		if (actionEl == null) {
			return;
		}

		List<String> actions = getActions(actionEl);

		Resource resource = layoutCache.getResource(
			companyId, groupId, resourceName,
			ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
			portletActions);

		PermissionLocalServiceUtil.setGroupPermissions(
			groupId, actions.toArray(new String[actions.size()]),
			resource.getResourceId());
	}

	protected void importGroupRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName,
			Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = parentEl.element(entityName + "-roles");

		if (entityRolesEl == null) {
			return;
		}

		importRolePermissions(
			layoutCache, companyId, resourceName, ResourceConstants.SCOPE_GROUP,
			String.valueOf(groupId), entityRolesEl, true);
	}

	protected void importInheritedPermissions(
			LayoutCache layoutCache, long companyId, String resourceName,
			String resourcePrimKey, Element permissionsEl, String entityName,
			boolean portletActions)
		throws PortalException, SystemException {

		Element entityPermissionsEl = permissionsEl.element(
			entityName + "-permissions");

		if (entityPermissionsEl == null) {
			return;
		}

		List<Element> actionsEls = entityPermissionsEl.elements(
			entityName + "-actions");

		for (int i = 0; i < actionsEls.size(); i++) {
			Element actionEl = actionsEls.get(i);

			String name = actionEl.attributeValue("name");

			long entityGroupId = layoutCache.getEntityGroupId(
				companyId, entityName, name);

			if (entityGroupId == 0) {
				_log.warn(
					"Ignore inherited permissions for entity " + entityName +
						" with name " + name);
			}
			else {
				Element parentEl = SAXReaderUtil.createElement("parent");

				parentEl.add(actionEl.createCopy());

				importGroupPermissions(
					layoutCache, companyId, entityGroupId, resourceName,
					resourcePrimKey, parentEl, entityName + "-actions",
					portletActions);
			}
		}
	}

	protected void importInheritedRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName, Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = parentEl.element(entityName + "-roles");

		if (entityRolesEl == null) {
			return;
		}

		List<Element> entityEls = entityRolesEl.elements(entityName);

		for (int i = 0; i < entityEls.size(); i++) {
			Element entityEl = entityEls.get(i);

			String name = entityEl.attributeValue("name");

			long entityGroupId = layoutCache.getEntityGroupId(
				companyId, entityName, name);

			if (entityGroupId == 0) {
				_log.warn(
					"Ignore inherited roles for entity " + entityName +
						" with name " + name);
			}
			else {
				importRolePermissions(
					layoutCache, companyId, resourceName,
					ResourceConstants.SCOPE_GROUP, String.valueOf(groupId),
					entityEl, false);
			}
		}
	}

	protected void importLayoutPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, Layout layout, Element permissionsEl,
			boolean importUserPermissions)
		throws PortalException, SystemException {

		String resourceName = Layout.class.getName();
		String resourcePrimKey = String.valueOf(layout.getPlid());

		importGroupPermissions(
			layoutCache, companyId, groupId, resourceName, resourcePrimKey,
			permissionsEl, "community-actions", false);

		if (groupId != guestGroup.getGroupId()) {
			importGroupPermissions(
				layoutCache, companyId, guestGroup.getGroupId(), resourceName,
				resourcePrimKey, permissionsEl, "guest-actions", false);
		}

		if (importUserPermissions) {
			importUserPermissions(
				layoutCache, companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl, false);
		}

		importInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "organization", false);

		importInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "location", false);

		importInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "user-group", false);
	}

	protected void importLayoutRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			Element rolesEl)
		throws PortalException, SystemException {

		String resourceName = Layout.class.getName();

		importGroupRoles(
			layoutCache, companyId, groupId, resourceName, "community",
			rolesEl);

		importUserRoles(layoutCache, companyId, groupId, resourceName, rolesEl);

		importInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "organization",
			rolesEl);

		importInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "location", rolesEl);

		importInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "user-group",
			rolesEl);
	}

	protected void importPortletPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, Layout layout, Element permissionsEl,
			boolean importUserPermissions)
		throws PortalException, SystemException {

		Iterator<Element> itr = permissionsEl.elements("portlet").iterator();

		while (itr.hasNext()) {
			Element portletEl = itr.next();

			String portletId = portletEl.attributeValue("portlet-id");

			String resourceName = PortletConstants.getRootPortletId(portletId);
			String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portletId);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, resourceName);

			if (portlet == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Do not import portlet permissions for " + portletId +
							" because the portlet does not exist");
				}
			}
			else {
				importGroupPermissions(
					layoutCache, companyId, groupId, resourceName,
					resourcePrimKey, portletEl, "community-actions", true);

				if (groupId != guestGroup.getGroupId()) {
					importGroupPermissions(
						layoutCache, companyId, guestGroup.getGroupId(),
						resourceName, resourcePrimKey, portletEl,
						"guest-actions", true);
				}

				if (importUserPermissions) {
					importUserPermissions(
						layoutCache, companyId, groupId, resourceName,
						resourcePrimKey, portletEl, true);
				}

				importInheritedPermissions(
					layoutCache, companyId, resourceName, resourcePrimKey,
					portletEl, "organization", true);

				importInheritedPermissions(
					layoutCache, companyId, resourceName, resourcePrimKey,
					portletEl, "location", true);

				importInheritedPermissions(
					layoutCache, companyId, resourceName, resourcePrimKey,
					portletEl, "user-group", true);
			}
		}
	}

	protected void importPortletRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String portletId, Element rolesEl)
		throws PortalException, SystemException {

		String resourceName = PortletConstants.getRootPortletId(portletId);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, resourceName);

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not import portlet roles for " + portletId +
						" because the portlet does not exist");
			}
		}
		else {
			importGroupRoles(
				layoutCache, companyId, groupId, resourceName, "community",
				rolesEl);

			importUserRoles(
				layoutCache, companyId, groupId, resourceName, rolesEl);

			importInheritedRoles(
				layoutCache, companyId, groupId, resourceName,
				"organization", rolesEl);

			importInheritedRoles(
				layoutCache, companyId, groupId, resourceName, "location",
				rolesEl);

			importInheritedRoles(
				layoutCache, companyId, groupId, resourceName, "user-group",
				rolesEl);
		}
	}

	protected void importPortletRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			Element rolesEl)
		throws PortalException, SystemException {

		Iterator<Element> itr = rolesEl.elements("portlet").iterator();

		while (itr.hasNext()) {
			Element portletEl = itr.next();

			String portletId = portletEl.attributeValue("portlet-id");

			String resourceName = PortletConstants.getRootPortletId(portletId);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, resourceName);

			if (portlet == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Do not import portlet roles for " + portletId +
							" because the portlet does not exist");
				}
			}
			else {
				importGroupRoles(
					layoutCache, companyId, groupId, resourceName, "community",
					portletEl);

				importUserRoles(
					layoutCache, companyId, groupId, resourceName, portletEl);

				importInheritedRoles(
					layoutCache, companyId, groupId, resourceName,
					"organization", portletEl);

				importInheritedRoles(
					layoutCache, companyId, groupId, resourceName, "location",
					portletEl);

				importInheritedRoles(
					layoutCache, companyId, groupId, resourceName, "user-group",
					portletEl);
			}
		}
	}

	protected void importRolePermissions(
			LayoutCache layoutCache, long companyId, String resourceName,
			int scope, String resourcePrimKey, Element parentEl,
			boolean communityRole)
		throws PortalException, SystemException {

		List<Element> roleEls = parentEl.elements("role");

		for (int i = 0; i < roleEls.size(); i++) {
			Element roleEl = roleEls.get(i);

			String roleName = roleEl.attributeValue("name");

			Role role = layoutCache.getRole(companyId, roleName);

			if (role == null) {
				_log.warn(
					"Ignoring permissions for role with name " + roleName);
			}
			else {
				List<String> actions = getActions(roleEl);

				PermissionLocalServiceUtil.setRolePermissions(
					role.getRoleId(), companyId, resourceName, scope,
					resourcePrimKey,
					actions.toArray(new String[actions.size()]));

				if (communityRole) {
					long[] groupIds = {GetterUtil.getLong(resourcePrimKey)};

					GroupLocalServiceUtil.addRoleGroups(
						role.getRoleId(), groupIds);
				}
			}
		}
	}

	protected String importTheme(LayoutSet layoutSet, byte[] themeZip)
		throws IOException {

		ThemeLoader themeLoader = ThemeLoaderFactory.getDefaultThemeLoader();

		if (themeLoader == null) {
			_log.error("No theme loaders are deployed");

			return null;
		}

		ZipReader zipReader = new ZipReader(new ByteArrayInputStream(themeZip));

		Map<String, byte[]> entries = zipReader.getEntries();

		String lookAndFeelXML = new String(
			entries.get("liferay-look-and-feel.xml"));

		String themeId = String.valueOf(layoutSet.getGroupId());

		if (layoutSet.isPrivateLayout()) {
			themeId += "-private";
		}
		else {
			themeId += "-public";
		}

		if (PropsValues.THEME_LOADER_NEW_THEME_ID_ON_IMPORT) {
			Date now = new Date();

			themeId += "-" + Time.getShortTimestamp(now);
		}

		String themeName = themeId;

		lookAndFeelXML = StringUtil.replace(
			lookAndFeelXML,
			new String[] {
				"[$GROUP_ID$]", "[$THEME_ID$]", "[$THEME_NAME$]"
			},
			new String[] {
				String.valueOf(layoutSet.getGroupId()), themeId, themeName
			}
		);

		FileUtil.deltree(themeLoader.getFileStorage() + "/" + themeId);

		Iterator<Map.Entry<String, byte[]>> itr = entries.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, byte[]> entry = itr.next();

			String key = entry.getKey();
			byte[] value = entry.getValue();

			if (key.equals("liferay-look-and-feel.xml")) {
				value = lookAndFeelXML.getBytes();
			}

			FileUtil.write(
				themeLoader.getFileStorage() + "/" + themeId + "/" + key,
				value);
		}

		themeLoader.loadThemes();

		CommLink commLink = CommLink.getInstance();

		MethodWrapper methodWrapper = new MethodWrapper(
			ThemeLoaderFactory.class.getName(), "loadThemes");

		commLink.send(methodWrapper);

		themeId +=
			PortletConstants.WAR_SEPARATOR +
				themeLoader.getServletContextName();

		return PortalUtil.getJsSafePortletId(themeId);
	}

	protected void importUserPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element parentEl,
			boolean portletActions)
		throws PortalException, SystemException {

		Element userPermissionsEl = parentEl.element("user-permissions");

		if (userPermissionsEl == null) {
			return;
		}

		List<Element> userActionsEls = userPermissionsEl.elements(
			"user-actions");

		for (int i = 0; i < userActionsEls.size(); i++) {
			Element userActionsEl = userActionsEls.get(i);

			String emailAddress = userActionsEl.attributeValue("email-address");

			User user = layoutCache.getUser(companyId, groupId, emailAddress);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Ignoring permissions for user with email address " +
							emailAddress);
				}
			}
			else {
				List<String> actions = getActions(userActionsEl);

				Resource resource = layoutCache.getResource(
					companyId, groupId, resourceName,
					ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
					portletActions);

				PermissionLocalServiceUtil.setUserPermissions(
					user.getUserId(),
					actions.toArray(new String[actions.size()]),
					resource.getResourceId());
			}
		}
	}

	protected void importUserRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, Element parentEl)
		throws PortalException, SystemException {

		Element userRolesEl = parentEl.element("user-roles");

		if (userRolesEl == null) {
			return;
		}

		List<Element> userEls = userRolesEl.elements("user");

		for (int i = 0; i < userEls.size(); i++) {
			Element userEl = userEls.get(i);

			String emailAddress = userEl.attributeValue("email-address");

			User user = layoutCache.getUser(companyId, groupId, emailAddress);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Ignoring roles for user with email address " +
							emailAddress);
				}
			}
			else {
				importRolePermissions(
					layoutCache, companyId, resourceName,
					ResourceConstants.SCOPE_GROUP, String.valueOf(groupId),
					userEl, false);
			}
		}
	}

	protected void mergePortlets(
		Layout layout, String newTypeSettings, String portletsMergeMode) {

		try {
			UnicodeProperties previousProps =
				layout.getTypeSettingsProperties();
			LayoutTypePortlet previousLayoutType =
				(LayoutTypePortlet)layout.getLayoutType();
			List<String> previousColumns =
				previousLayoutType.getLayoutTemplate().getColumns();

			UnicodeProperties newProps = new UnicodeProperties(true);

			newProps.load(newTypeSettings);

			String layoutTemplateId = newProps.getProperty(
					LayoutTypePortletImpl.LAYOUT_TEMPLATE_ID);

			LayoutTemplate newLayoutTemplate =
				LayoutTemplateLocalServiceUtil.getLayoutTemplate(
					layoutTemplateId, false, null);

			String[] lostPortletIds = new String[0];

			for (String columnId : newLayoutTemplate.getColumns()) {
				String columnValue =
					newProps.getProperty(columnId);

				String[] portletIds = StringUtil.split(columnValue);

				if (!previousColumns.contains(columnId)) {
					lostPortletIds = ArrayUtil.append(
						lostPortletIds, portletIds);
				}
				else {

					String[] previousPortletIds = StringUtil.split(
						previousProps.getProperty(columnId));

					portletIds = appendPortletIds(
						previousPortletIds, portletIds, portletsMergeMode);

					previousProps.setProperty(
						columnId, StringUtil.merge(portletIds));
				}
			}

			// Add portlets in non-existent column to the first column

			String columnId = previousColumns.get(0);

			String[] portletIds = StringUtil.split(
				previousProps.getProperty(columnId));

			appendPortletIds(portletIds, lostPortletIds, portletsMergeMode);

			previousProps.setProperty(
				columnId, StringUtil.merge(portletIds));

			layout.setTypeSettings(previousProps.toString());

		}
		catch (IOException e) {
			layout.setTypeSettings(newTypeSettings);
		}
	}

	protected String[] appendPortletIds(
		String[] portletIds, String[] newPortletIds,
		String portletsMergeMode) {

		for (String portletId : newPortletIds) {
			if (ArrayUtil.contains(portletIds, portletId)) {
				continue;
			}

			if (portletsMergeMode.equals(
					PortletDataHandlerKeys.PORTLETS_MERGE_MODE_ADD_TO_BOTTOM)) {
				portletIds = ArrayUtil.append(
					portletIds, portletId);
			}
			else {
				portletIds = ArrayUtil.append(
					new String[]{portletId}, portletIds);
			}
		}

		return portletIds;
	}

	private static Log _log = LogFactory.getLog(LayoutImporter.class);

	private PortletImporter _portletImporter = new PortletImporter();

}