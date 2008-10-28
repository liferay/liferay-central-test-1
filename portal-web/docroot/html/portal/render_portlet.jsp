<%
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
%>

<%@ include file="/html/portal/init.jsp" %>

<%
Portlet portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);

String portletId = portlet.getPortletId();
String rootPortletId = portlet.getRootPortletId();
String instanceId = portlet.getInstanceId();

String portletPrimaryKey = PortletPermissionUtil.getPrimaryKey(plid, portletId);

String queryString = (String)request.getAttribute(WebKeys.RENDER_PORTLET_QUERY_STRING);
String columnId = (String)request.getAttribute(WebKeys.RENDER_PORTLET_COLUMN_ID);
Integer columnPos = (Integer)request.getAttribute(WebKeys.RENDER_PORTLET_COLUMN_POS);
Integer columnCount = (Integer)request.getAttribute(WebKeys.RENDER_PORTLET_COLUMN_COUNT);
Boolean renderPortletResource = (Boolean)request.getAttribute(WebKeys.RENDER_PORTLET_RESOURCE);

boolean runtimePortlet = (renderPortletResource != null) && renderPortletResource.booleanValue();

boolean denyAccess = false;

try {
	ResourceLocalServiceUtil.getResource(company.getCompanyId(), rootPortletId, ResourceConstants.SCOPE_INDIVIDUAL, portletPrimaryKey);
}
catch (NoSuchResourceException nsre) {
	boolean addDefaultResource = false;

	if (runtimePortlet) {
		addDefaultResource = true;
	}
	else if (layout.getType().equals(LayoutConstants.TYPE_PANEL)) {
		addDefaultResource = true;
	}
	else if (layout.getType().equals(LayoutConstants.TYPE_CONTROL_PANEL)) {
		addDefaultResource = true;
	}
	else if (layoutTypePortlet.hasPortletId(portletId)) {
		addDefaultResource = true;
	}
	else if (portlet.isAddDefaultResource()) {
		addDefaultResource = true;
	}
	else if (themeDisplay.isSignedIn()) {
		if (portletId.equals(PortletKeys.LAYOUT_CONFIGURATION) || portletId.equals(PortletKeys.LAYOUT_MANAGEMENT)) {
			Group group = layout.getGroup();

			if (group.isCommunity() || group.isUserGroup()) {
				if (GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.MANAGE_LAYOUTS) ||
					LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.UPDATE)) {

					addDefaultResource = true;
				}
			}
			else if (group.isOrganization()) {
				long organizationId = group.getClassPK();

				if (OrganizationPermissionUtil.contains(permissionChecker, organizationId, ActionKeys.MANAGE_LAYOUTS)) {
					addDefaultResource = true;
				}
			}
			else if (group.isUser()) {
				addDefaultResource = true;
			}
		}
	}

	if (!portlet.hasAddPortletPermission(user.getUserId())) {
		addDefaultResource = false;
	}

	if (addDefaultResource) {
		ResourceLocalServiceUtil.addResources(company.getCompanyId(), layout.getGroupId(), 0, rootPortletId, portletPrimaryKey, true, true, true);
	}
	else {
		denyAccess = true;
	}
}

boolean access = PortletPermissionUtil.contains(permissionChecker, plid, portlet, ActionKeys.VIEW);

boolean stateMax = layoutTypePortlet.hasStateMaxPortletId(portletId);
boolean stateMin = layoutTypePortlet.hasStateMinPortletId(portletId);

boolean modeAbout = layoutTypePortlet.hasModeAboutPortletId(portletId);
boolean modeConfig = layoutTypePortlet.hasModeConfigPortletId(portletId);
boolean modeEdit = layoutTypePortlet.hasModeEditPortletId(portletId);
boolean modeEditDefaults = layoutTypePortlet.hasModeEditDefaultsPortletId(portletId);
boolean modeEditGuest = layoutTypePortlet.hasModeEditGuestPortletId(portletId);
boolean modeHelp = layoutTypePortlet.hasModeHelpPortletId(portletId);
boolean modePreview = layoutTypePortlet.hasModePreviewPortletId(portletId);
boolean modePrint = layoutTypePortlet.hasModePrintPortletId(portletId);

InvokerPortlet invokerPortlet = null;

try {
	invokerPortlet = PortletInstanceFactory.create(portlet, application);
}
/*catch (UnavailableException ue) {
	ue.printStackTrace();
}*/
catch (PortletException pe) {
	pe.printStackTrace();
}
catch (RuntimeException re) {
	re.printStackTrace();
}

PortletPreferences portletSetup = PortletPreferencesFactoryUtil.getLayoutPortletSetup(layout, portletId);

PortletPreferencesIds portletPreferencesIds = PortletPreferencesFactoryUtil.getPortletPreferencesIds(request, portletId);

PortletPreferences portletPreferences = PortletPreferencesLocalServiceUtil.getPreferences(portletPreferencesIds);

PortletConfig portletConfig = PortletConfigFactory.create(portlet, application);
PortletContext portletCtx = portletConfig.getPortletContext();

WindowState windowState = WindowState.NORMAL;

if (themeDisplay.isStateExclusive()) {
	windowState = LiferayWindowState.EXCLUSIVE;
}
else if (themeDisplay.isStatePopUp()) {
	windowState = LiferayWindowState.POP_UP;
}
else if (stateMax) {
	windowState = WindowState.MAXIMIZED;
}

PortletMode portletMode = PortletMode.VIEW;

if (modeAbout) {
	portletMode = LiferayPortletMode.ABOUT;
}
else if (modeConfig) {
	portletMode = LiferayPortletMode.CONFIG;
}
else if (modeEdit) {
	portletMode = PortletMode.EDIT;
}
else if (modeEditDefaults) {
	portletMode = LiferayPortletMode.EDIT_DEFAULTS;
}
else if (modeEditGuest) {
	portletMode = LiferayPortletMode.EDIT_GUEST;
}
else if (modeHelp) {
	portletMode = PortletMode.HELP;
}
else if (modePreview) {
	portletMode = LiferayPortletMode.PREVIEW;
}
else if (modePrint) {
	portletMode = LiferayPortletMode.PRINT;
}

HttpServletRequest originalRequest = PortalUtil.getOriginalServletRequest(request);

RenderRequestImpl renderRequestImpl = RenderRequestFactory.create(originalRequest, portlet, invokerPortlet, portletCtx, windowState, portletMode, portletPreferences, plid);

if (Validator.isNotNull(queryString)) {
	DynamicServletRequest dynamicRequest = (DynamicServletRequest)renderRequestImpl.getHttpServletRequest();

	String[] params = StringUtil.split(queryString, StringPool.AMPERSAND);

	for (int i = 0; i < params.length; i++) {
		String[] kvp = StringUtil.split(params[i], StringPool.EQUAL);

		if (kvp.length > 1) {
			dynamicRequest.setParameter(kvp[0], kvp[1]);
		}
		else {
			dynamicRequest.setParameter(kvp[0], StringPool.BLANK);
		}
	}
}

StringServletResponse stringResponse = new StringServletResponse(response);

RenderResponseImpl renderResponseImpl = RenderResponseFactory.create(renderRequestImpl, stringResponse, portletId, company.getCompanyId(), plid);

renderRequestImpl.defineObjects(portletConfig, renderResponseImpl);

String responseContentType = renderRequestImpl.getResponseContentType();

String currentURL = PortalUtil.getCurrentURL(request);

Portlet portletResourcePortlet = null;

if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	String portletResource = ParamUtil.getString(request, "portletResource");

	if (Validator.isNotNull(portletResource)) {
		portletResourcePortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);
	}
}

boolean showCloseIcon = true;
boolean showConfigurationIcon = false;
boolean showEditIcon = false;
boolean showEditDefaultsIcon = false;
boolean showEditGuestIcon = false;
boolean showHelpIcon = portlet.hasPortletMode(responseContentType, PortletMode.HELP);
boolean showMaxIcon = true;
boolean showMinIcon = true;
boolean showMoveIcon = !stateMax && !themeDisplay.isStateExclusive();
boolean showPortletCssIcon = false;
boolean showPortletIcon = (portletResourcePortlet != null) ? Validator.isNotNull(portletResourcePortlet.getIcon()) : Validator.isNotNull(portlet.getIcon());
boolean showPrintIcon = portlet.hasPortletMode(responseContentType, LiferayPortletMode.PRINT);
boolean showRefreshIcon = portlet.isAjaxable() && (portlet.getRenderWeight() == 0);

Boolean portletParallelRender = (Boolean)request.getAttribute(WebKeys.PORTLET_PARALLEL_RENDER);

if ((portletParallelRender != null) && (portletParallelRender.booleanValue() == false)) {
	showRefreshIcon = false;
}

Group group = layout.getGroup();

if (!portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	if ((!group.hasStagingGroup() || group.isStagingGroup()) &&
		(PortletPermissionUtil.contains(permissionChecker, plid, portletId, ActionKeys.CONFIGURATION))) {

		showConfigurationIcon = true;

		if (PropsValues.PORTLET_CSS_ENABLED) {
			showPortletCssIcon = true;
		}
	}
}

if (portlet.hasPortletMode(responseContentType, PortletMode.EDIT)) {
	if (PortletPermissionUtil.contains(permissionChecker, plid, portletId, ActionKeys.PREFERENCES)) {
		showEditIcon = true;
	}
}

if (portlet.hasPortletMode(responseContentType, LiferayPortletMode.EDIT_DEFAULTS)) {
	if (showEditIcon && !layout.isPrivateLayout() && themeDisplay.isShowAddContentIcon()) {
		showEditDefaultsIcon = true;
	}
}

if (portlet.hasPortletMode(responseContentType, LiferayPortletMode.EDIT_GUEST)) {
	if (showEditIcon && !layout.isPrivateLayout() && themeDisplay.isShowAddContentIcon()) {
		showEditGuestIcon = true;
	}
}

boolean supportsMimeType = portlet.hasPortletMode(responseContentType, portletMode);

if (responseContentType.equals(ContentTypes.XHTML_MP) && portlet.hasMultipleMimeTypes()) {
	supportsMimeType = GetterUtil.getBoolean(portletSetup.getValue("portlet-setup-supported-clients-mobile-devices-" + portletMode, String.valueOf(supportsMimeType)));
}

// Only authenticated with the correct permissions can update a layout. If
// staging is activated, only staging layouts can be updated.

if ((!themeDisplay.isSignedIn()) ||
	(group.hasStagingGroup() && !group.isStagingGroup()) ||
	(!LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.UPDATE))) {

	showCloseIcon = false;
	showMaxIcon = PropsValues.LAYOUT_GUEST_SHOW_MAX_ICON;
	showMinIcon = PropsValues.LAYOUT_GUEST_SHOW_MIN_ICON;
	showMoveIcon = false;
}

// Portlets cannot be moved unless they belong to the layout

if (!layoutTypePortlet.hasPortletId(portletId)) {
	showCloseIcon = false;
	showMoveIcon = false;
}

// Static portlets cannot be moved

if (portlet.isStatic()) {
	showCloseIcon = false;
	showMoveIcon = false;
}

// Deny access to edit mode if you do not have permission

if (!PropsValues.TCK_URL && portletMode.equals(PortletMode.EDIT) && !PortletPermissionUtil.contains(permissionChecker, plid, portletId, ActionKeys.PREFERENCES)) {
	denyAccess = true;
}

// Deny access

if (denyAccess) {
	access = false;

	showCloseIcon = false;
	showConfigurationIcon = false;
	showEditIcon = false;
	showEditDefaultsIcon = false;
	showEditGuestIcon = false;
	showHelpIcon = false;
	showMaxIcon = false;
	showMinIcon = false;
	showMoveIcon = false;
	showPortletCssIcon = false;
	showPrintIcon = false;
}

themeDisplay.setScopeGroupId(PortalUtil.getScopeGroupId(request, portletId));

portletDisplay.recycle();

portletDisplay.setId(portletId);
portletDisplay.setRootPortletId(rootPortletId);
portletDisplay.setInstanceId(instanceId);
portletDisplay.setResourcePK(portletPrimaryKey);
portletDisplay.setPortletName(portletConfig.getPortletName());
portletDisplay.setNamespace(PortalUtil.getPortletNamespace(portletId));

portletDisplay.setAccess(access);
portletDisplay.setActive(portlet.isActive());

portletDisplay.setColumnId(columnId);
portletDisplay.setColumnPos(columnPos.intValue());
portletDisplay.setColumnCount(columnCount.intValue());

portletDisplay.setStateExclusive(themeDisplay.isStateExclusive());
portletDisplay.setStateMax(stateMax);
portletDisplay.setStateMin(stateMin);
portletDisplay.setStateNormal(windowState.equals(WindowState.NORMAL));
portletDisplay.setStatePopUp(themeDisplay.isStatePopUp());

portletDisplay.setModeAbout(modeAbout);
portletDisplay.setModeConfig(modeConfig);
portletDisplay.setModeEdit(modeEdit);
portletDisplay.setModeEditDefaults(modeEditDefaults);
portletDisplay.setModeEditGuest(modeEditGuest);
portletDisplay.setModeHelp(modeHelp);
portletDisplay.setModePreview(modePreview);
portletDisplay.setModePrint(modePrint);

portletDisplay.setShowCloseIcon(showCloseIcon);
portletDisplay.setShowConfigurationIcon(showConfigurationIcon);
portletDisplay.setShowEditIcon(showEditIcon);
portletDisplay.setShowEditDefaultsIcon(showEditDefaultsIcon);
portletDisplay.setShowEditGuestIcon(showEditGuestIcon);
portletDisplay.setShowHelpIcon(showHelpIcon);
portletDisplay.setShowMaxIcon(showMaxIcon);
portletDisplay.setShowMinIcon(showMinIcon);
portletDisplay.setShowMoveIcon(showMoveIcon);
portletDisplay.setShowPortletCssIcon(showPortletCssIcon);
portletDisplay.setShowPortletIcon(showPortletIcon);
portletDisplay.setShowPrintIcon(showPrintIcon);
portletDisplay.setShowRefreshIcon(showRefreshIcon);

portletDisplay.setRestoreCurrentView(portlet.isRestoreCurrentView());

portletDisplay.setPortletSetup(portletSetup);

// Portlet icon

String portletIcon = null;

if (portletResourcePortlet != null) {
	portletIcon = portletResourcePortlet.getContextPath() + portletResourcePortlet.getIcon();
}
else {
	portletIcon = portlet.getContextPath() + portlet.getIcon();
}

portletDisplay.setURLPortlet(themeDisplay.getCDNHost() + portletIcon);

// URL close

String urlClose = themeDisplay.getPathMain() + "/portal/update_layout?p_l_id=" + plid + "&p_p_id=" + portletDisplay.getId() + "&doAsUserId=" + themeDisplay.getDoAsUserId() + "&" + Constants.CMD + "=" + Constants.DELETE + "&referer=" + HttpUtil.encodeURL(themeDisplay.getPathMain() + "/portal/layout?p_l_id=" + plid + "&doAsUserId=" + themeDisplay.getDoAsUserId()) + "&refresh=1";

portletDisplay.setURLClose(urlClose.toString());

// URL configuration

PortletURLImpl urlConfiguration = new PortletURLImpl(request, PortletKeys.PORTLET_CONFIGURATION, plid, PortletRequest.RENDER_PHASE);

urlConfiguration.setWindowState(WindowState.MAXIMIZED);

if (Validator.isNotNull(portlet.getConfigurationActionClass())) {
	urlConfiguration.setParameter("struts_action", "/portlet_configuration/edit_configuration");
}
else {
	urlConfiguration.setParameter("struts_action", "/portlet_configuration/edit_permissions");
}

urlConfiguration.setEscapeXml(false);

urlConfiguration.setParameter("redirect", currentURL);
urlConfiguration.setParameter("returnToFullPageURL", currentURL);
urlConfiguration.setParameter("portletResource", portletDisplay.getId());
urlConfiguration.setParameter("resourcePrimKey", PortletPermissionUtil.getPrimaryKey(plid, portlet.getPortletId()));

portletDisplay.setURLConfiguration(urlConfiguration.toString() + "&" + PortalUtil.getPortletNamespace(PortletKeys.PORTLET_CONFIGURATION));

// URL edit

PortletURLImpl urlEdit = new PortletURLImpl(request, portletDisplay.getId(), plid, PortletRequest.RENDER_PHASE);

if (portletDisplay.isModeEdit()) {
	urlEdit.setWindowState(WindowState.NORMAL);
	urlEdit.setPortletMode(PortletMode.VIEW);
}
else {
	if (portlet.isMaximizeEdit()) {
		urlEdit.setWindowState(WindowState.MAXIMIZED);
	}
	else {
		urlEdit.setWindowState(WindowState.NORMAL);
	}

	urlEdit.setPortletMode(PortletMode.EDIT);
}

urlEdit.setEscapeXml(false);

portletDisplay.setURLEdit(urlEdit.toString());

// URL edit defaults

PortletURLImpl urlEditDefaults = new PortletURLImpl(request, portletDisplay.getId(), plid, PortletRequest.RENDER_PHASE);

if (portletDisplay.isModeEditDefaults()) {
	urlEditDefaults.setWindowState(WindowState.NORMAL);
	urlEditDefaults.setPortletMode(PortletMode.VIEW);
}
else {
	if (portlet.isMaximizeEdit()) {
		urlEditDefaults.setWindowState(WindowState.MAXIMIZED);
	}
	else {
		urlEditDefaults.setWindowState(WindowState.NORMAL);
	}

	urlEditDefaults.setPortletMode(LiferayPortletMode.EDIT_DEFAULTS);
}

urlEditDefaults.setEscapeXml(false);

portletDisplay.setURLEditDefaults(urlEditDefaults.toString());

// URL edit guest

PortletURLImpl urlEditGuest = new PortletURLImpl(request, portletDisplay.getId(), plid, PortletRequest.RENDER_PHASE);

if (portletDisplay.isModeEditGuest()) {
	urlEditGuest.setWindowState(WindowState.NORMAL);
	urlEditGuest.setPortletMode(PortletMode.VIEW);
}
else {
	if (portlet.isMaximizeEdit()) {
		urlEditGuest.setWindowState(WindowState.MAXIMIZED);
	}
	else {
		urlEditGuest.setWindowState(WindowState.NORMAL);
	}

	urlEditGuest.setPortletMode(LiferayPortletMode.EDIT_GUEST);
}

urlEditGuest.setEscapeXml(false);

portletDisplay.setURLEditGuest(urlEditGuest.toString());

// URL help

PortletURLImpl urlHelp = new PortletURLImpl(request, portletDisplay.getId(), plid, PortletRequest.RENDER_PHASE);

if (portletDisplay.isModeHelp()) {
	urlHelp.setWindowState(WindowState.NORMAL);
	urlHelp.setPortletMode(PortletMode.VIEW);
}
else {
	if (portlet.isMaximizeHelp()) {
		urlHelp.setWindowState(WindowState.MAXIMIZED);
	}
	else {
		urlHelp.setWindowState(WindowState.NORMAL);
	}

	urlHelp.setPortletMode(PortletMode.HELP);
}

urlHelp.setEscapeXml(false);

portletDisplay.setURLHelp(urlHelp.toString());

// URL max

String lifecycle = PortletRequest.RENDER_PHASE;

if (!portletDisplay.isRestoreCurrentView()) {
	lifecycle = PortletRequest.ACTION_PHASE;
}

PortletURLImpl urlMax = new PortletURLImpl(request, portletDisplay.getId(), plid, lifecycle);

if (portletDisplay.isStateMax()) {
	urlMax.setWindowState(WindowState.NORMAL);
}
else {
	urlMax.setWindowState(WindowState.MAXIMIZED);
}

urlMax.setEscapeXml(false);

if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
	String portletNamespace = portletDisplay.getNamespace();

	Map renderParameters = RenderParametersPool.get(request, plid, portletDisplay.getId());

	Iterator itr = renderParameters.entrySet().iterator();

	while (itr.hasNext()) {
		Map.Entry entry = (Map.Entry)itr.next();

		String key = (String)entry.getKey();

		if (key.startsWith(portletNamespace)) {
			key = key.substring(portletNamespace.length(), key.length());

			String[] values = (String[])entry.getValue();

			urlMax.setParameter(key, values);
		}
	}
}

portletDisplay.setURLMax(urlMax.toString());

// URL min

String urlMin = themeDisplay.getPathMain() + "/portal/update_layout?p_l_id=" + plid + "&p_p_id=" + portletDisplay.getId() + "&p_p_restore=" + portletDisplay.isStateMin() + "&doAsUserId=" + themeDisplay.getDoAsUserId() + "&" + Constants.CMD + "=minimize&referer=" + HttpUtil.encodeURL(themeDisplay.getPathMain() + "/portal/layout?p_l_id=" + plid + "&doAsUserId=" + themeDisplay.getDoAsUserId()) + "&refresh=1";

portletDisplay.setURLMin(urlMin);

// URL portlet css

String urlPortletCss = "javascript: ;";

portletDisplay.setURLPortletCss(urlPortletCss.toString());

// URL print

PortletURLImpl urlPrint = new PortletURLImpl(request, portletDisplay.getId(), plid, PortletRequest.RENDER_PHASE);

if (portletDisplay.isModePrint()) {
	urlPrint.setWindowState(WindowState.NORMAL);
	urlPrint.setPortletMode(PortletMode.VIEW);
}
else {
	if (portlet.isPopUpPrint()) {
		urlPrint.setWindowState(LiferayWindowState.POP_UP);
	}
	else {
		urlPrint.setWindowState(WindowState.NORMAL);
	}

	urlPrint.setPortletMode(LiferayPortletMode.PRINT);
}

urlPrint.setEscapeXml(false);

portletDisplay.setURLPrint(urlPrint.toString());

// URL refresh

String urlRefresh = "javascript: ;";

portletDisplay.setURLRefresh(urlRefresh);

// URL back

String urlBack = null;

if (portletDisplay.isModeEdit()) {
	urlBack = urlEdit.toString();
}
else if (portletDisplay.isModeEditDefaults()) {
	urlBack = urlEditDefaults.toString();
}
else if (portletDisplay.isModeEditGuest()) {
	urlBack = urlEditGuest.toString();
}
else if (portletDisplay.isModeHelp()) {
	urlBack = urlHelp.toString();
}
else if (portletDisplay.isModePrint()) {
	urlBack = urlPrint.toString();
}
else if (portletDisplay.isStateMax()) {
	//if (portletDisplay.getId().equals(PortletKeys.PORTLET_CONFIGURATION)) {
		/*String portletResource = ParamUtil.getString(request, "portletResource");

		urlMax.setAnchor(false);

		urlBack = urlMax.toString() + "#p_" + portletResource;*/

		//urlBack = ParamUtil.getString(renderRequestImpl, "returnToFullPageURL");
	//}
	//else {
	//	urlBack = urlMax.toString();
	//}

	urlBack = ParamUtil.getString(renderRequestImpl, "returnToFullPageURL");
	urlBack = HtmlUtil.stripHtml(urlBack);

	if (Validator.isNull(urlBack)) {
		urlBack = urlMax.toString();
	}
}

if (urlBack != null) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(urlBack);
}

if (themeDisplay.isWidget()) {
	portletDisplay.setShowBackIcon(false);
}

if (layout.getGroup().getName().equals(GroupConstants.CONTROL_PANEL)) {
	portletDisplay.setShowBackIcon(false);
	portletDisplay.setShowConfigurationIcon(false);
	portletDisplay.setShowMaxIcon(false);
	portletDisplay.setShowMinIcon(false);
	portletDisplay.setShowPortletCssIcon(false);
}

// Make sure the Tiles context is reset for the next portlet

if ((invokerPortlet != null) && invokerPortlet.isStrutsPortlet()) {
	request.removeAttribute(ComponentConstants.COMPONENT_CONTEXT);
}

// Render portlet

boolean portletException = false;

if (portlet.isActive() && access && supportsMimeType) {
	try {
		invokerPortlet.render(renderRequestImpl, renderResponseImpl);

		if (themeDisplay.isFacebook() || themeDisplay.isStateExclusive()) {
			renderRequestImpl.setAttribute(WebKeys.STRING_SERVLET_RESPONSE, stringResponse);
		}
	}
	catch (UnavailableException ue) {
		portletException = true;

		PortletInstanceFactory.destroy(portlet);
	}
	catch (Exception e) {
		portletException = true;

		LogUtil.log(_log, e);
	}
}
%>

<%@ include file="/html/portal/render_portlet-ext.jsp" %>

<c:if test="<%= !themeDisplay.isFacebook() && !themeDisplay.isStateExclusive() && !themeDisplay.isWapTheme() %>">

	<%
	if (themeDisplay.isStatePopUp() || themeDisplay.isWidget()) {
		PortalUtil.setPageTitle(portletDisplay.getTitle(), request);
	}

	String freeformStyles = StringPool.BLANK;

	if (themeDisplay.isFreeformLayout() && !runtimePortlet && !layoutTypePortlet.hasStateMax()) {
		StringBuilder sb = new StringBuilder();

		Properties freeformStyleProps = PropertiesUtil.load(portletSetup.getValue("portlet-freeform-styles", StringPool.BLANK));

		sb.append("style=\"position: absolute; ");
		sb.append("width: ");
		sb.append(GetterUtil.getString(freeformStyleProps.getProperty("width"), "400px"));
		sb.append("; ");
		sb.append("top: ");
		sb.append(GetterUtil.getString(freeformStyleProps.getProperty("top"), "0"));
		sb.append("; ");
		sb.append("left: ");
		sb.append(GetterUtil.getString(freeformStyleProps.getProperty("left"), "0"));
		sb.append(";\"");

		freeformStyles = sb.toString();
	}
	%>

	<div id="p_p_id<%= renderResponseImpl.getNamespace() %>" class="portlet-boundary portlet-boundary<%= PortalUtil.getPortletNamespace(portlet.getRootPortletId()) %> <%= (portletDisplay.isStateMin()) ? "portlet-minimized" : "" %> <%= portlet.getCssClassWrapper() %>" <%= freeformStyles %>>
		<a name="p_<%= portletId %>"></a>
</c:if>

<c:choose>
	<c:when test="<%= !supportsMimeType %>">
	</c:when>
	<c:when test="<%= !access && !portlet.isShowPortletAccessDenied() %>">
	</c:when>
	<c:when test="<%= !portlet.isActive() && !portlet.isShowPortletInactive() %>">
	</c:when>
	<c:otherwise>

		<%
		boolean useDefaultTemplate = portlet.isUseDefaultTemplate();
		Boolean useDefaultTemplateObj = renderResponseImpl.getUseDefaultTemplate();

		if (useDefaultTemplateObj != null) {
			useDefaultTemplate = useDefaultTemplateObj.booleanValue();
		}

		if ((invokerPortlet != null) && invokerPortlet.isStrutsPortlet()) {
			if (!access || portletException) {
				PortletRequestProcessor portletReqProcessor = (PortletRequestProcessor)portletCtx.getAttribute(WebKeys.PORTLET_STRUTS_PROCESSOR);

				ActionMapping actionMapping = portletReqProcessor.processMapping(request, response, (String)portlet.getInitParams().get("view-action"));

				ComponentDefinition definition = null;

				if (actionMapping != null) {

					// See action path /weather/view

					String definitionName = actionMapping.getForward();

					if (definitionName == null) {

						// See action path /journal/view_articles

						String[] definitionNames = actionMapping.findForwards();

						for (int definitionNamesPos = 0; definitionNamesPos < definitionNames.length; definitionNamesPos++) {
							if (definitionNames[definitionNamesPos].endsWith("view")) {
								definitionName = definitionNames[definitionNamesPos];

								break;
							}
						}

						if (definitionName == null) {
							definitionName = definitionNames[0];
						}
					}

					definition = TilesUtil.getDefinition(definitionName, request, application);
				}

				String templatePath = StrutsUtil.TEXT_HTML_DIR + "/common/themes/portlet.jsp";

				if (definition != null) {
					templatePath = StrutsUtil.TEXT_HTML_DIR + definition.getPath();
				}
		%>

				<tiles:insert template="<%= templatePath %>" flush="false">
					<tiles:put name="portlet_content" value="/portal/portlet_error.jsp" />
				</tiles:insert>

		<%
			}
			else {
				if (useDefaultTemplate) {
					renderRequestImpl.setAttribute(WebKeys.PORTLET_CONTENT, stringResponse.getString());
		%>

					<tiles:insert template='<%= StrutsUtil.TEXT_HTML_DIR + "/common/themes/portlet.jsp" %>' flush="false">
						<tiles:put name="portlet_content" value="<%= StringPool.BLANK %>" />
					</tiles:insert>

		<%
				}
				else {
					pageContext.getOut().print(stringResponse.getString());
				}
			}
		}
		else {
			renderRequestImpl.setAttribute(WebKeys.PORTLET_CONTENT, stringResponse.getString());

			String portletContent = StringPool.BLANK;

			if (portletException) {
				portletContent = "/portal/portlet_error.jsp";
			}
		%>

			<c:choose>
				<c:when test="<%= useDefaultTemplate || portletException %>">
					<tiles:insert template='<%= StrutsUtil.TEXT_HTML_DIR + "/common/themes/portlet.jsp" %>' flush="false">
						<tiles:put name="portlet_content" value="<%= portletContent %>" />
					</tiles:insert>
				</c:when>
				<c:otherwise>
					<%= renderRequestImpl.getAttribute(WebKeys.PORTLET_CONTENT) %>
				</c:otherwise>
			</c:choose>

		<%
		}
		%>

	</c:otherwise>
</c:choose>

<%
String staticVar = "yes";

if (portletDisplay.isShowMoveIcon()) {
	staticVar = "no";
}
else {
	if (portlet.isStaticStart()) {
		staticVar = "start";
	}

	if (portlet.isStaticEnd()) {
		staticVar = "end";
	}
}
%>

<c:if test="<%= !themeDisplay.isFacebook() && !themeDisplay.isStateExclusive() && !themeDisplay.isWapTheme() %>">
		<script type="text/javascript">
			Liferay.Portlet.onLoad(
				{
					canEditTitle: <%= showConfigurationIcon %>,
					columnPos: <%= columnPos %>,
					isStatic: '<%= staticVar %>',
					namespacedId: 'p_p_id<%= renderResponseImpl.getNamespace() %>',
					portletId: '<%= portletDisplay.getId() %>'
				}
			);
		</script>
	</div>
</c:if>

<%
themeDisplay.setScopeGroupId(PortalUtil.getScopeGroupId(request));

if (showPortletCssIcon) {
	themeDisplay.setIncludePortletCssJs(true);
}

SessionMessages.clear(renderRequestImpl);
SessionErrors.clear(renderRequestImpl);

if (themeDisplay.isFacebook() || themeDisplay.isStateExclusive()) {
	request.setAttribute(JavaConstants.JAVAX_PORTLET_REQUEST, renderRequestImpl);
	request.setAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE, renderResponseImpl);
}
else {
	RenderRequestFactory.recycle(renderRequestImpl);
	RenderResponseFactory.recycle(renderResponseImpl);
}
%>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portal.render_portlet.jsp");
%>