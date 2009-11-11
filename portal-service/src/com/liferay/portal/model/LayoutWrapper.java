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

package com.liferay.portal.model;


/**
 * <a href="LayoutSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link Layout}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Layout
 * @generated
 */
public class LayoutWrapper implements Layout {
	public LayoutWrapper(Layout layout) {
		_layout = layout;
	}

	public long getPrimaryKey() {
		return _layout.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_layout.setPrimaryKey(pk);
	}

	public long getPlid() {
		return _layout.getPlid();
	}

	public void setPlid(long plid) {
		_layout.setPlid(plid);
	}

	public long getGroupId() {
		return _layout.getGroupId();
	}

	public void setGroupId(long groupId) {
		_layout.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _layout.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_layout.setCompanyId(companyId);
	}

	public boolean getPrivateLayout() {
		return _layout.getPrivateLayout();
	}

	public boolean isPrivateLayout() {
		return _layout.isPrivateLayout();
	}

	public void setPrivateLayout(boolean privateLayout) {
		_layout.setPrivateLayout(privateLayout);
	}

	public long getLayoutId() {
		return _layout.getLayoutId();
	}

	public void setLayoutId(long layoutId) {
		_layout.setLayoutId(layoutId);
	}

	public long getParentLayoutId() {
		return _layout.getParentLayoutId();
	}

	public void setParentLayoutId(long parentLayoutId) {
		_layout.setParentLayoutId(parentLayoutId);
	}

	public java.lang.String getName() {
		return _layout.getName();
	}

	public void setName(java.lang.String name) {
		_layout.setName(name);
	}

	public java.lang.String getTitle() {
		return _layout.getTitle();
	}

	public void setTitle(java.lang.String title) {
		_layout.setTitle(title);
	}

	public java.lang.String getDescription() {
		return _layout.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_layout.setDescription(description);
	}

	public java.lang.String getType() {
		return _layout.getType();
	}

	public void setType(java.lang.String type) {
		_layout.setType(type);
	}

	public java.lang.String getTypeSettings() {
		return _layout.getTypeSettings();
	}

	public void setTypeSettings(java.lang.String typeSettings) {
		_layout.setTypeSettings(typeSettings);
	}

	public boolean getHidden() {
		return _layout.getHidden();
	}

	public boolean isHidden() {
		return _layout.isHidden();
	}

	public void setHidden(boolean hidden) {
		_layout.setHidden(hidden);
	}

	public java.lang.String getFriendlyURL() {
		return _layout.getFriendlyURL();
	}

	public void setFriendlyURL(java.lang.String friendlyURL) {
		_layout.setFriendlyURL(friendlyURL);
	}

	public boolean getIconImage() {
		return _layout.getIconImage();
	}

	public boolean isIconImage() {
		return _layout.isIconImage();
	}

	public void setIconImage(boolean iconImage) {
		_layout.setIconImage(iconImage);
	}

	public long getIconImageId() {
		return _layout.getIconImageId();
	}

	public void setIconImageId(long iconImageId) {
		_layout.setIconImageId(iconImageId);
	}

	public java.lang.String getThemeId() {
		return _layout.getThemeId();
	}

	public void setThemeId(java.lang.String themeId) {
		_layout.setThemeId(themeId);
	}

	public java.lang.String getColorSchemeId() {
		return _layout.getColorSchemeId();
	}

	public void setColorSchemeId(java.lang.String colorSchemeId) {
		_layout.setColorSchemeId(colorSchemeId);
	}

	public java.lang.String getWapThemeId() {
		return _layout.getWapThemeId();
	}

	public void setWapThemeId(java.lang.String wapThemeId) {
		_layout.setWapThemeId(wapThemeId);
	}

	public java.lang.String getWapColorSchemeId() {
		return _layout.getWapColorSchemeId();
	}

	public void setWapColorSchemeId(java.lang.String wapColorSchemeId) {
		_layout.setWapColorSchemeId(wapColorSchemeId);
	}

	public java.lang.String getCss() {
		return _layout.getCss();
	}

	public void setCss(java.lang.String css) {
		_layout.setCss(css);
	}

	public int getPriority() {
		return _layout.getPriority();
	}

	public void setPriority(int priority) {
		_layout.setPriority(priority);
	}

	public long getLayoutPrototypeId() {
		return _layout.getLayoutPrototypeId();
	}

	public void setLayoutPrototypeId(long layoutPrototypeId) {
		_layout.setLayoutPrototypeId(layoutPrototypeId);
	}

	public long getDlFolderId() {
		return _layout.getDlFolderId();
	}

	public void setDlFolderId(long dlFolderId) {
		_layout.setDlFolderId(dlFolderId);
	}

	public com.liferay.portal.model.Layout toEscapedModel() {
		return _layout.toEscapedModel();
	}

	public boolean isNew() {
		return _layout.isNew();
	}

	public boolean setNew(boolean n) {
		return _layout.setNew(n);
	}

	public boolean isCachedModel() {
		return _layout.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_layout.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _layout.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_layout.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _layout.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layout.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layout.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _layout.clone();
	}

	public int compareTo(com.liferay.portal.model.Layout layout) {
		return _layout.compareTo(layout);
	}

	public int hashCode() {
		return _layout.hashCode();
	}

	public java.lang.String toString() {
		return _layout.toString();
	}

	public java.lang.String toXmlString() {
		return _layout.toXmlString();
	}

	public com.liferay.portal.model.Group getGroup() {
		return _layout.getGroup();
	}

	public com.liferay.portal.model.Group getScopeGroup()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layout.getScopeGroup();
	}

	public boolean hasScopeGroup()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layout.hasScopeGroup();
	}

	public boolean isPublicLayout() {
		return _layout.isPublicLayout();
	}

	public long getAncestorPlid() {
		return _layout.getAncestorPlid();
	}

	public long getAncestorLayoutId() {
		return _layout.getAncestorLayoutId();
	}

	public java.util.List<com.liferay.portal.model.Layout> getAncestors()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layout.getAncestors();
	}

	public boolean hasAncestor(long layoutId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layout.hasAncestor(layoutId);
	}

	public boolean isFirstParent() {
		return _layout.isFirstParent();
	}

	public boolean isFirstChild() {
		return _layout.isFirstChild();
	}

	public boolean isRootLayout() {
		return _layout.isRootLayout();
	}

	public java.util.List<com.liferay.portal.model.Layout> getChildren()
		throws com.liferay.portal.SystemException {
		return _layout.getChildren();
	}

	public boolean hasChildren() throws com.liferay.portal.SystemException {
		return _layout.hasChildren();
	}

	public java.util.List<com.liferay.portal.model.Layout> getAllChildren()
		throws com.liferay.portal.SystemException {
		return _layout.getAllChildren();
	}

	public java.util.List<com.liferay.portal.model.Layout> getChildren(
		com.liferay.portal.security.permission.PermissionChecker permissionChecker)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layout.getChildren(permissionChecker);
	}

	public java.lang.String getName(java.util.Locale locale) {
		return _layout.getName(locale);
	}

	public java.lang.String getName(java.lang.String localeLanguageId) {
		return _layout.getName(localeLanguageId);
	}

	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _layout.getName(locale, useDefault);
	}

	public java.lang.String getName(java.lang.String localeLanguageId,
		boolean useDefault) {
		return _layout.getName(localeLanguageId, useDefault);
	}

	public void setName(java.lang.String name, java.util.Locale locale) {
		_layout.setName(name, locale);
	}

	public java.lang.String getTitle(java.util.Locale locale) {
		return _layout.getTitle(locale);
	}

	public java.lang.String getTitle(java.lang.String localeLanguageId) {
		return _layout.getTitle(localeLanguageId);
	}

	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _layout.getTitle(locale, useDefault);
	}

	public java.lang.String getTitle(java.lang.String localeLanguageId,
		boolean useDefault) {
		return _layout.getTitle(localeLanguageId, useDefault);
	}

	public java.lang.String getHTMLTitle(java.util.Locale locale) {
		return _layout.getHTMLTitle(locale);
	}

	public java.lang.String getHTMLTitle(java.lang.String localeLanguageId) {
		return _layout.getHTMLTitle(localeLanguageId);
	}

	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_layout.setTitle(title, locale);
	}

	public com.liferay.portal.model.LayoutType getLayoutType() {
		return _layout.getLayoutType();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _layout.getTypeSettingsProperties();
	}

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_layout.setTypeSettingsProperties(typeSettingsProperties);
	}

	public com.liferay.portal.model.LayoutSet getLayoutSet() {
		return _layout.getLayoutSet();
	}

	public boolean isInheritLookAndFeel() {
		return _layout.isInheritLookAndFeel();
	}

	public com.liferay.portal.model.Theme getTheme()
		throws com.liferay.portal.SystemException {
		return _layout.getTheme();
	}

	public com.liferay.portal.model.ColorScheme getColorScheme()
		throws com.liferay.portal.SystemException {
		return _layout.getColorScheme();
	}

	public boolean isInheritWapLookAndFeel() {
		return _layout.isInheritWapLookAndFeel();
	}

	public com.liferay.portal.model.Theme getWapTheme()
		throws com.liferay.portal.SystemException {
		return _layout.getWapTheme();
	}

	public com.liferay.portal.model.ColorScheme getWapColorScheme()
		throws com.liferay.portal.SystemException {
		return _layout.getWapColorScheme();
	}

	public java.lang.String getCssText() {
		return _layout.getCssText();
	}

	public java.lang.String getRegularURL(
		javax.servlet.http.HttpServletRequest request)
		throws com.liferay.portal.SystemException {
		return _layout.getRegularURL(request);
	}

	public java.lang.String getResetMaxStateURL(
		javax.servlet.http.HttpServletRequest request)
		throws com.liferay.portal.SystemException {
		return _layout.getResetMaxStateURL(request);
	}

	public java.lang.String getResetLayoutURL(
		javax.servlet.http.HttpServletRequest request)
		throws com.liferay.portal.SystemException {
		return _layout.getResetLayoutURL(request);
	}

	public java.lang.String getTarget() {
		return _layout.getTarget();
	}

	public boolean isChildSelected(boolean selectable,
		com.liferay.portal.model.Layout layout) {
		return _layout.isChildSelected(selectable, layout);
	}

	public boolean isSelected(boolean selectable,
		com.liferay.portal.model.Layout layout, long ancestorPlid) {
		return _layout.isSelected(selectable, layout, ancestorPlid);
	}

	public Layout getWrappedLayout() {
		return _layout;
	}

	private Layout _layout;
}