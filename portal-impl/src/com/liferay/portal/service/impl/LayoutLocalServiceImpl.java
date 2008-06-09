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

package com.liferay.portal.service.impl;

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.LayoutHiddenException;
import com.liferay.portal.LayoutParentLayoutIdException;
import com.liferay.portal.LayoutTypeException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.lar.LayoutExporter;
import com.liferay.portal.lar.LayoutImporter;
import com.liferay.portal.lar.PortletExporter;
import com.liferay.portal.lar.PortletImporter;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutReference;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.base.LayoutLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.comparator.LayoutPriorityComparator;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.util.Normalizer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LayoutLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 *
 */
public class LayoutLocalServiceImpl extends LayoutLocalServiceBaseImpl {

	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, String name, String title, String description,
			String type, boolean hidden, String friendlyURL)
		throws PortalException, SystemException {

		Map<Locale, String> localeNamesMap = new HashMap<Locale, String>();

		Locale defaultLocale = LocaleUtil.getDefault();

		localeNamesMap.put(defaultLocale, name);

		return addLayout(
			userId, groupId, privateLayout, parentLayoutId, localeNamesMap,
			new HashMap<Locale, String>(), description, type, hidden,
			friendlyURL);
	}

	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, Map<Locale, String> localeNamesMap,
			Map<Locale, String> localeTitlesMap, String description,
			String type, boolean hidden, String friendlyURL)
		throws PortalException, SystemException {

		return addLayout(
			userId, groupId, privateLayout, parentLayoutId, localeNamesMap,
			localeTitlesMap, description, type, hidden, friendlyURL,
			DLFolderImpl.DEFAULT_PARENT_FOLDER_ID);
	}

	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, String name, String title, String description,
			String type, boolean hidden, String friendlyURL, long dlFolderId)
		throws PortalException, SystemException {

		Map<Locale, String> localeNamesMap = new HashMap<Locale, String>();

		Locale defaultLocale = LocaleUtil.getDefault();

		localeNamesMap.put(defaultLocale, name);

		return addLayout(
			userId, groupId, privateLayout, parentLayoutId, localeNamesMap,
			new HashMap<Locale, String>(), description, type, hidden,
			friendlyURL, dlFolderId);
	}

	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, Map<Locale, String> localeNamesMap,
			Map<Locale, String> localeTitlesMap, String description,
			String type, boolean hidden, String friendlyURL, long dlFolderId)
		throws PortalException, SystemException {

		// Layout

		User user = userPersistence.findByPrimaryKey(userId);
		long layoutId = getNextLayoutId(groupId, privateLayout);
		parentLayoutId = getParentLayoutId(
			groupId, privateLayout, parentLayoutId);
		friendlyURL = getFriendlyURL(layoutId, friendlyURL);
		int priority = getNextPriority(groupId, privateLayout, parentLayoutId);

		validate(
			groupId, privateLayout, layoutId, parentLayoutId, type, hidden,
			friendlyURL);

		long plid = counterLocalService.increment();

		Layout layout = layoutPersistence.create(plid);

		layout.setGroupId(groupId);
		layout.setCompanyId(user.getCompanyId());
		layout.setPrivateLayout(privateLayout);
		layout.setLayoutId(layoutId);
		layout.setParentLayoutId(parentLayoutId);
		layout.setDescription(description);
		layout.setType(type);
		layout.setHidden(hidden);
		layout.setFriendlyURL(friendlyURL);
		layout.setPriority(priority);
		layout.setDlFolderId(dlFolderId);

		setLocalizedAttributes(layout, localeNamesMap, localeTitlesMap);

		layoutPersistence.update(layout, false);

		// Resources

		resourceLocalService.addResources(
			user.getCompanyId(), groupId, user.getUserId(),
			Layout.class.getName(), layout.getPlid(), false, true, true);

		// Layout set

		layoutSetLocalService.updatePageCount(groupId, privateLayout);

		return layout;
	}

	public void deleteLayout(long plid)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		deleteLayout(layout, true);
	}

	public void deleteLayout(long groupId, boolean privateLayout, long layoutId)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		deleteLayout(layout, true);
	}

	public void deleteLayout(Layout layout, boolean updateLayoutSet)
		throws PortalException, SystemException {

		// Child layouts

		List<Layout> childLayouts = layoutPersistence.findByG_P_P(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getLayoutId());

		for (Layout childLayout : childLayouts) {
			deleteLayout(childLayout, updateLayoutSet);
		}

		// Portlet preferences

		portletPreferencesLocalService.deletePortletPreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid());

		// Tasks

		tasksProposalLocalService.deleteProposal(
			Layout.class.getName(), String.valueOf(layout.getPlid()));

		// Ratings

		ratingsStatsLocalService.deleteStats(
			Layout.class.getName(), layout.getPlid());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			Layout.class.getName(), layout.getPlid());

		// Journal content searches

		journalContentSearchLocalService.deleteLayoutContentSearches(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getLayoutId());

		// Icon

		imageLocalService.deleteImage(layout.getIconImageId());

		// Resources

		String primKey =
			layout.getPlid() + PortletConstants.LAYOUT_SEPARATOR + "%";

		List<Resource> resources = resourceFinder.findByC_P(
			layout.getCompanyId(), primKey);

		for (Resource resource : resources) {
			resourceLocalService.deleteResource(resource);
		}

		resourceLocalService.deleteResource(
			layout.getCompanyId(), Layout.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, layout.getPlid());

		// Layout

		layoutPersistence.remove(layout.getPlid());

		// Layout set

		if (updateLayoutSet) {
			layoutSetLocalService.updatePageCount(
				layout.getGroupId(), layout.isPrivateLayout());
		}
	}

	public void deleteLayouts(long groupId, boolean privateLayout)
		throws PortalException, SystemException {

		// Layouts

		List<Layout> layouts = layoutPersistence.findByG_P_P(
			groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		for (Layout layout : layouts) {
			try {
				deleteLayout(layout, false);
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		// Layout set

		layoutSetLocalService.updatePageCount(groupId, privateLayout);
	}

	public byte[] exportLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		return exportLayouts(
			groupId, privateLayout, null, parameterMap, startDate, endDate);
	}

	public byte[] exportLayouts(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		LayoutExporter layoutExporter = new LayoutExporter();

		return layoutExporter.exportLayouts(
			groupId, privateLayout, layoutIds, parameterMap, startDate,
			endDate);
	}

	public byte[] exportPortletInfo(
			long plid, String portletId, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws PortalException, SystemException {

		PortletExporter portletExporter = new PortletExporter();

		return portletExporter.exportPortletInfo(
			plid, portletId, parameterMap, startDate, endDate);
	}

	public long getDefaultPlid(long groupId) throws SystemException {
		if (groupId > 0) {
			List<Layout> layouts = layoutPersistence.findByGroupId(
				groupId, 0, 1);

			if (layouts.size() > 0) {
				Layout layout = layouts.get(0);

				return layout.getPlid();
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	public long getDefaultPlid(long groupId, boolean privateLayout)
		throws SystemException {

		if (groupId > 0) {
			List<Layout> layouts = layoutPersistence.findByG_P(
				groupId, privateLayout, 0, 1);

			if (layouts.size() > 0) {
				Layout layout = layouts.get(0);

				return layout.getPlid();
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	public long getDefaultPlid(
			long groupId, boolean privateLayout, String portletId)
		throws SystemException {

		if (groupId > 0) {
			List<Layout> layouts = layoutPersistence.findByG_P(
				groupId, privateLayout);

			for (Layout layout : layouts) {
				if (layout.getType().equals(LayoutConstants.TYPE_PORTLET)) {
					LayoutTypePortlet layoutTypePortlet =
						(LayoutTypePortlet)layout.getLayoutType();

					if (layoutTypePortlet.hasPortletId(portletId)) {
						return layout.getPlid();
					}
				}
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	public Layout getDLFolderLayout(long dlFolderId)
		throws PortalException, SystemException {

		return layoutPersistence.findByDLFolderId(dlFolderId);
	}

	public Layout getFriendlyURLLayout(
			long groupId, boolean privateLayout, String friendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNull(friendlyURL)) {
			throw new NoSuchLayoutException();
		}

		friendlyURL = getFriendlyURL(friendlyURL);

		Layout layout = layoutPersistence.fetchByG_P_F(
			groupId, privateLayout, friendlyURL);

		if ((layout == null) &&
			(friendlyURL.startsWith(StringPool.SLASH))) {

			long layoutId = GetterUtil.getLong(friendlyURL.substring(1));

			layout = layoutPersistence.fetchByG_P_L(
				groupId, privateLayout, layoutId);
		}

		if (layout == null) {
			throw new NoSuchLayoutException();
		}

		return layout;
	}

	public Layout getLayout(long plid)
		throws PortalException, SystemException {

		return layoutPersistence.findByPrimaryKey(plid);
	}

	public Layout getLayout(long groupId, boolean privateLayout, long layoutId)
		throws PortalException, SystemException {

		return layoutPersistence.findByG_P_L(groupId, privateLayout, layoutId);
	}

	public Layout getLayoutByIconImageId(long iconImageId)
		throws PortalException, SystemException {

		return layoutPersistence.findByIconImageId(iconImageId);
	}

	public List<Layout> getLayouts(long groupId, boolean privateLayout)
		throws SystemException {

		return layoutPersistence.findByG_P(groupId, privateLayout);
	}

	public List<Layout> getLayouts(
			long groupId, boolean privateLayout, long parentLayoutId)
		throws SystemException {

		return layoutPersistence.findByG_P_P(
			groupId, privateLayout, parentLayoutId);
	}

	public List<Layout> getLayouts(
			long groupId, boolean privateLayout, String type)
		throws SystemException {

		return layoutPersistence.findByG_P_T(groupId, privateLayout, type);
	}

	public List<Layout> getLayouts(
			long groupId, boolean privateLayout, long parentLayoutId, int start,
			int end)
		throws SystemException {

		return layoutPersistence.findByG_P_P(
			groupId, privateLayout, parentLayoutId, start, end);
	}

	public List<Layout> getLayouts(
			long groupId, boolean privateLayout, long[] layoutIds)
		throws PortalException, SystemException {

		List<Layout> layouts = new ArrayList<Layout>();

		for (long layoutId : layoutIds) {
			Layout layout = getLayout(groupId, privateLayout, layoutId);

			layouts.add(layout);
		}

		return layouts;
	}

	public LayoutReference[] getLayouts(
			long companyId, String portletId, String prefsKey,
			String prefsValue)
		throws SystemException {

		List<LayoutReference> layoutReferences = layoutFinder.findByC_P_P(
			companyId, portletId, prefsKey, prefsValue);

		return layoutReferences.toArray(
			new LayoutReference[layoutReferences.size()]);
	}

	public long getNextLayoutId(long groupId, boolean privateLayout)
		throws SystemException {

		long layoutId = 0;

		List<Layout> layouts = layoutPersistence.findByG_P(
			groupId, privateLayout);

		for (Layout curLayout : layouts) {
			long curLayoutId = curLayout.getLayoutId();

			if (curLayoutId > layoutId) {
				layoutId = curLayoutId;
			}
		}

		return ++layoutId;
	}

	public List<Layout> getNullFriendlyURLLayouts() throws SystemException {
		return layoutFinder.findByNullFriendlyURL();
	}

	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws PortalException, SystemException {

		try {
			importLayouts(
				userId, groupId, privateLayout, parameterMap,
				new FileInputStream(file));
		}
		catch (FileNotFoundException fnfe) {
			throw new SystemException(fnfe);
		}
	}

	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, byte[] bytes)
		throws PortalException, SystemException {

		importLayouts(
			userId, groupId, privateLayout, parameterMap,
			new ByteArrayInputStream(bytes));
	}

	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException, SystemException {

		LayoutImporter layoutImporter = new LayoutImporter();

		layoutImporter.importLayouts(
			userId, groupId, privateLayout, parameterMap, is);
	}

	public void importPortletInfo(
			long userId, long plid, String portletId,
			Map<String, String[]> parameterMap, File file)
		throws PortalException, SystemException {

		try {
			importPortletInfo(
				userId, plid, portletId, parameterMap,
				new FileInputStream(file));
		}
		catch (FileNotFoundException fnfe) {
			throw new SystemException(fnfe);
		}
	}

	public void importPortletInfo(
			long userId, long plid, String portletId,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException, SystemException {

		PortletImporter portletImporter = new PortletImporter();

		portletImporter.importPortletInfo(
			userId, plid, portletId, parameterMap, is);
	}

	public void setLayouts(
			long groupId, boolean privateLayout, long parentLayoutId,
			long[] layoutIds)
		throws PortalException, SystemException {

		if (layoutIds == null) {
			return;
		}

		if (parentLayoutId == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			if (layoutIds.length < 1) {
				throw new RequiredLayoutException(
					RequiredLayoutException.AT_LEAST_ONE);
			}

			Layout layout = layoutPersistence.findByG_P_L(
				groupId, privateLayout, layoutIds[0]);

			if (!layout.getType().equals(LayoutConstants.TYPE_PORTLET)) {
				throw new RequiredLayoutException(
					RequiredLayoutException.FIRST_LAYOUT_TYPE);
			}

			if (layout.isHidden()) {
				throw new RequiredLayoutException(
					RequiredLayoutException.FIRST_LAYOUT_HIDDEN);
			}
		}

		Set<Long> layoutIdsSet = new LinkedHashSet<Long>();

		for (int i = 0; i < layoutIds.length; i++) {
			layoutIdsSet.add(layoutIds[i]);
		}

		Set<Long> newLayoutIdsSet = new HashSet<Long>();

		List<Layout> layouts = layoutPersistence.findByG_P_P(
			groupId, privateLayout, parentLayoutId);

		for (Layout layout : layouts) {
			if (!layoutIdsSet.contains(layout.getLayoutId())) {
				deleteLayout(layout, true);
			}
			else {
				newLayoutIdsSet.add(layout.getLayoutId());
			}
		}

		int priority = 0;

		for (long layoutId : layoutIdsSet) {
			Layout layout = layoutPersistence.findByG_P_L(
				groupId, privateLayout, layoutId);

			layout.setPriority(priority++);

			layoutPersistence.update(layout, false);
		}

		layoutSetLocalService.updatePageCount(groupId, privateLayout);
	}

	public Layout updateFriendlyURL(long plid, String friendlyURL)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		friendlyURL = getFriendlyURL(layout.getLayoutId(), friendlyURL);

		validateFriendlyURL(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			friendlyURL);

		layout.setFriendlyURL(friendlyURL);

		layoutPersistence.update(layout, false);

		return layout;
	}

	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, Map<Locale, String> localeNamesMap,
			Map<Locale, String> localeTitlesMap, String description,
			String type, boolean hidden, String friendlyURL)
		throws PortalException, SystemException {

		return updateLayout(
			groupId, privateLayout, layoutId, parentLayoutId, localeNamesMap,
			localeTitlesMap, description, type, hidden, friendlyURL, null,
			null);
	}

	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, Map<Locale, String> localeNamesMap,
			Map<Locale, String> localeTitlesMap, String description,
			String type, boolean hidden, String friendlyURL, Boolean iconImage,
			byte[] iconBytes)
		throws PortalException, SystemException {

		// Layout

		parentLayoutId = getParentLayoutId(
			groupId, privateLayout, parentLayoutId);
		friendlyURL = getFriendlyURL(layoutId, friendlyURL);

		validate(
			groupId, privateLayout, layoutId, parentLayoutId, type, hidden,
			friendlyURL);

		validateParentLayoutId(
			groupId, privateLayout, layoutId, parentLayoutId);

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (parentLayoutId != layout.getParentLayoutId()) {
			layout.setPriority(
				getNextPriority(groupId, privateLayout, parentLayoutId));
		}

		layout.setParentLayoutId(parentLayoutId);
		layout.setDescription(description);
		layout.setType(type);
		layout.setHidden(hidden);
		layout.setFriendlyURL(friendlyURL);

		setLocalizedAttributes(layout, localeNamesMap, localeTitlesMap);

		if (iconImage != null) {
			layout.setIconImage(iconImage.booleanValue());

			if (iconImage.booleanValue()) {
				long iconImageId = layout.getIconImageId();

				if (iconImageId <= 0) {
					iconImageId = counterLocalService.increment();

					layout.setIconImageId(iconImageId);
				}
			}
		}

		layoutPersistence.update(layout, false);

		// Icon

		if (iconImage != null) {
			if (!iconImage.booleanValue()) {
				imageLocalService.deleteImage(layout.getIconImageId());
			}
			else if ((iconBytes != null) && (iconBytes.length > 0)) {
				imageLocalService.updateImage(
					layout.getIconImageId(), iconBytes);
			}
		}

		try {
			if (layout.getDlFolderId() > 0) {
				DLFolder folder = dlFolderLocalService.getFolder(
					layout.getDlFolderId());

				String name = layout.getName(LocaleUtil.getDefault());

				if (!name.equals(folder.getName())) {
					dlFolderLocalService.updateFolder(
						folder.getFolderId(), folder.getParentFolderId(), name,
						folder.getDescription());
				}
			}
		}
		catch (DuplicateFolderNameException dfne) {
			if (_log.isDebugEnabled()) {
				_log.debug(dfne);
			}
		}
		catch (NoSuchFolderException nsfe) {
		}

		return layout;
	}

	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			String typeSettings)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		layout.setTypeSettings(typeSettings);

		layoutPersistence.update(layout, false);

		return layout;
	}

	public Layout updateLookAndFeel(
			long groupId, boolean privateLayout, long layoutId, String themeId,
			String colorSchemeId, String css, boolean wapTheme)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (wapTheme) {
			layout.setWapThemeId(themeId);
			layout.setWapColorSchemeId(colorSchemeId);
		}
		else {
			layout.setThemeId(themeId);
			layout.setColorSchemeId(colorSchemeId);
			layout.setCss(css);
		}

		layoutPersistence.update(layout, false);

		return layout;
	}

	public Layout updateName(long plid, String name, String languageId)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		return updateName(layout, name, languageId);
	}

	public Layout updateName(
			long groupId, boolean privateLayout, long layoutId, String name,
			String languageId)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		return updateName(layout, name, languageId);
	}

	public Layout updateName(Layout layout, String name, String languageId)
		throws PortalException, SystemException {

		layout.setName(name, LocaleUtil.fromLanguageId(languageId));

		layoutPersistence.update(layout, false);

		try {
			if (layout.getDlFolderId() > 0) {
				DLFolder folder = dlFolderLocalService.getFolder(
					layout.getDlFolderId());

				dlFolderLocalService.updateFolder(
					folder.getFolderId(), folder.getParentFolderId(),
					layout.getName(LocaleUtil.getDefault()),
					folder.getDescription());
			}
		}
		catch (NoSuchFolderException nsfe) {
		}

		return layout;
	}

	public Layout updateParentLayoutId(long plid, long parentPlid)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		long parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

		if (parentPlid > 0) {
			try {
				Layout parentLayout = layoutPersistence.findByPrimaryKey(
					parentPlid);

				parentLayoutId = parentLayout.getLayoutId();
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		parentLayoutId = getParentLayoutId(
			layout.getGroupId(), layout.isPrivateLayout(), parentLayoutId);

		validateParentLayoutId(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			parentLayoutId);

		if (parentLayoutId != layout.getParentLayoutId()) {
			int priority = getNextPriority(
				layout.getGroupId(), layout.isPrivateLayout(), parentLayoutId);

			layout.setPriority(priority);
		}

		layout.setParentLayoutId(parentLayoutId);

		layoutPersistence.update(layout, false);

		return layout;
	}

	public Layout updateParentLayoutId(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId)
		throws PortalException, SystemException {

		parentLayoutId = getParentLayoutId(
			groupId, privateLayout, parentLayoutId);

		validateParentLayoutId(
			groupId, privateLayout, layoutId, parentLayoutId);

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (parentLayoutId != layout.getParentLayoutId()) {
			layout.setPriority(
				getNextPriority(groupId, privateLayout, parentLayoutId));
		}

		layout.setParentLayoutId(parentLayoutId);

		layoutPersistence.update(layout, false);

		return layout;
	}

	public Layout updatePriority(long plid, int priority)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		return updatePriority(layout, priority);
	}

	public Layout updatePriority(
			long groupId, boolean privateLayout, long layoutId, int priority)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		return updatePriority(layout, priority);
	}

	public Layout updatePriority(Layout layout, int priority)
		throws SystemException {

		if (layout.getPriority() == priority) {
			return layout;
		}

		boolean lessThan = false;

		if (layout.getPriority() < priority) {
			lessThan = true;
		}

		layout.setPriority(priority);

		layoutPersistence.update(layout, false);

		priority = 0;

		List<Layout> layouts = layoutPersistence.findByG_P_P(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getParentLayoutId());

		Collections.sort(
			layouts, new LayoutPriorityComparator(layout, lessThan));

		for (Layout curLayout : layouts) {
			curLayout.setPriority(priority++);

			layoutPersistence.update(curLayout, false);

			if (curLayout.equals(layout)) {
				layout = curLayout;
			}
		}

		return layout;
	}

	protected String getFriendlyURL(String friendlyURL) {
		friendlyURL = GetterUtil.getString(friendlyURL);

		return Normalizer.normalizeToAscii(friendlyURL.trim().toLowerCase());
	}

	protected String getFriendlyURL(long layoutId, String friendlyURL) {
		friendlyURL = getFriendlyURL(friendlyURL);

		if (Validator.isNull(friendlyURL)) {
			friendlyURL = StringPool.SLASH + layoutId;
		}

		return friendlyURL;
	}

	protected int getNextPriority(
			long groupId, boolean privateLayout, long parentLayoutId)
		throws SystemException {

		List<Layout> layouts = layoutPersistence.findByG_P_P(
			groupId, privateLayout, parentLayoutId);

		if (layouts.size() == 0) {
			return 0;
		}

		Layout layout = layouts.get(layouts.size() - 1);

		return layout.getPriority() + 1;
	}

	protected long getParentLayoutId(
			long groupId, boolean privateLayout, long parentLayoutId)
		throws SystemException {

		if (parentLayoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

			// Ensure parent layout exists

			try {
				layoutPersistence.findByG_P_L(
					groupId, privateLayout, parentLayoutId);
			}
			catch (NoSuchLayoutException nsfe) {
				parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;
			}
		}

		return parentLayoutId;
	}

	protected boolean isDescendant(Layout layout, long layoutId)
		throws PortalException, SystemException {

		if (layout.getLayoutId() == layoutId) {
			return true;
		}
		else {
			for (Layout childLayout : layout.getChildren()) {
				if (isDescendant(childLayout, layoutId)) {
					return true;
				}
			}

			return false;
		}
	}

	protected void setLocalizedAttributes(
		Layout layout, Map<Locale, String> localeNamesMap,
		Map<Locale, String> localeTitlesMap) {

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			String name = localeNamesMap.get(locale);
			String title = localeTitlesMap.get(locale);

			layout.setName(name, locale);
			layout.setTitle(title, locale);
		}
	}

	protected void validate(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, String type, boolean hidden,
			String friendlyURL)
		throws PortalException, SystemException {

		boolean firstLayout = false;

		if (parentLayoutId == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			List<Layout> layouts = layoutPersistence.findByG_P_P(
				groupId, privateLayout, parentLayoutId, 0, 1);

			if (layouts.size() == 0) {
				firstLayout = true;
			}
			else {
				long firstLayoutId = layouts.get(0).getLayoutId();

				if (firstLayoutId == layoutId) {
					firstLayout = true;
				}
			}
		}

		if (firstLayout) {
			validateFirstLayout(type, hidden);
		}

		if (!PortalUtil.isLayoutParentable(type)) {
			if (layoutPersistence.countByG_P_P(
					groupId, privateLayout, layoutId) > 0) {

				throw new LayoutTypeException(
					LayoutTypeException.NOT_PARENTABLE);
			}
		}

		validateFriendlyURL(groupId, privateLayout, layoutId, friendlyURL);
	}

	protected void validateFirstLayout(String type, boolean hidden)
		throws PortalException {

		if (!type.equals(LayoutConstants.TYPE_PORTLET)) {
			throw new LayoutTypeException(LayoutTypeException.FIRST_LAYOUT);
		}

		if (hidden) {
			throw new LayoutHiddenException();
		}
	}

	protected void validateFriendlyURL(
			long groupId, boolean privateLayout, long layoutId,
			String friendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNull(friendlyURL)) {
			return;
		}

		int exceptionType = LayoutImpl.validateFriendlyURL(friendlyURL);

		if (exceptionType != -1) {
			throw new LayoutFriendlyURLException(exceptionType);
		}

		try {
			Layout layout = layoutPersistence.findByG_P_F(
				groupId, privateLayout, friendlyURL);

			if (layout.getLayoutId() != layoutId) {
				throw new LayoutFriendlyURLException(
					LayoutFriendlyURLException.DUPLICATE);
			}
		}
		catch (NoSuchLayoutException nsle) {
		}

		LayoutImpl.validateFriendlyURLKeyword(friendlyURL);

		List<FriendlyURLMapper> friendlyURLMappers =
			portletLocalService.getFriendlyURLMappers();

		for (FriendlyURLMapper friendlyURLMapper : friendlyURLMappers) {
			if (friendlyURL.indexOf(friendlyURLMapper.getMapping()) != -1) {
				LayoutFriendlyURLException lfurle =
					new LayoutFriendlyURLException(
						LayoutFriendlyURLException.KEYWORD_CONFLICT);

				lfurle.setKeywordConflict(friendlyURLMapper.getMapping());

				throw lfurle;
			}
		}

		String layoutIdFriendlyURL = friendlyURL.substring(1);

		if (Validator.isNumber(layoutIdFriendlyURL) &&
			!layoutIdFriendlyURL.equals(String.valueOf(layoutId))) {

			LayoutFriendlyURLException lfurle = new LayoutFriendlyURLException(
				LayoutFriendlyURLException.POSSIBLE_DUPLICATE);

			lfurle.setKeywordConflict(layoutIdFriendlyURL);

			throw lfurle;
		}
	}

	protected void validateParentLayoutId(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (parentLayoutId != layout.getParentLayoutId()) {

			// Layouts can always be moved to the root level

			if (parentLayoutId == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
				return;
			}

			// Layout cannot become a child of a layout that is not parentable

			Layout parentLayout = layoutPersistence.findByG_P_L(
				groupId, privateLayout, parentLayoutId);

			if (!PortalUtil.isLayoutParentable(parentLayout)) {
				throw new LayoutParentLayoutIdException(
					LayoutParentLayoutIdException.NOT_PARENTABLE);
			}

			// Layout cannot become descendant of itself

			if (isDescendant(layout, parentLayoutId)) {
				throw new LayoutParentLayoutIdException(
					LayoutParentLayoutIdException.SELF_DESCENDANT);
			}

			// If layout is moved, the new first layout must be valid

			if (layout.getParentLayoutId() ==
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

				List<Layout> layouts = layoutPersistence.findByG_P_P(
					groupId, privateLayout,
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, 0, 2);

				// You can only reach this point if there are more than two
				// layouts at the root level because of the descendant check

				long firstLayoutId = layouts.get(0).getLayoutId();

				if (firstLayoutId == layoutId) {
					Layout secondLayout = layouts.get(1);

					try {
						validateFirstLayout(
							secondLayout.getType(), secondLayout.getHidden());
					}
					catch (LayoutHiddenException lhe) {
						throw new LayoutParentLayoutIdException(
							LayoutParentLayoutIdException.FIRST_LAYOUT_HIDDEN);
					}
					catch (LayoutTypeException lte) {
						throw new LayoutParentLayoutIdException(
							LayoutParentLayoutIdException.FIRST_LAYOUT_TYPE);
					}
				}
			}
		}
	}

	private static Log _log = LogFactory.getLog(LayoutLocalServiceImpl.class);

}