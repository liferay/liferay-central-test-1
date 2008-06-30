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

package com.liferay.portal.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestFactory;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseFactory;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.EventRequestFactory;
import com.liferay.portlet.EventRequestImpl;
import com.liferay.portlet.EventResponseFactory;
import com.liferay.portlet.EventResponseImpl;
import com.liferay.portlet.InvokerPortlet;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.portlet.PortletInstanceFactory;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletRequestImpl;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.RenderParametersPool;
import com.liferay.portlet.RenderRequestFactory;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseFactory;
import com.liferay.portlet.RenderResponseImpl;
import com.liferay.portlet.ResourceRequestFactory;
import com.liferay.portlet.ResourceRequestImpl;
import com.liferay.portlet.ResourceResponseFactory;
import com.liferay.portlet.ResourceResponseImpl;
import com.liferay.portlet.StateAwareResponseImpl;
import com.liferay.util.MapUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.Event;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.UnavailableException;
import javax.portlet.WindowState;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="LayoutAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		Boolean layoutDefault = (Boolean)request.getAttribute(
			WebKeys.LAYOUT_DEFAULT);

		if ((layoutDefault != null) && (layoutDefault.booleanValue())) {
			Layout requestedLayout =
				(Layout)request.getAttribute(WebKeys.REQUESTED_LAYOUT);

			if (requestedLayout != null) {
				String redirectParam = "redirect";

				String authLoginURL = PortalUtil.getCommunityLoginURL(
					themeDisplay);

				if (Validator.isNull(authLoginURL)) {
					authLoginURL = PropsValues.AUTH_LOGIN_URL;
				}

				if (Validator.isNotNull(PropsValues.AUTH_LOGIN_PORTLET_NAME) &&
					Validator.isNotNull(authLoginURL)) {

					redirectParam =
						PortalUtil.getPortletNamespace(
							PropsValues.AUTH_LOGIN_PORTLET_NAME) +
						redirectParam;
				}

				String url = PortalUtil.getLayoutURL(
					requestedLayout, themeDisplay);

				String redirect = HttpUtil.addParameter(
					themeDisplay.getURLSignIn(), redirectParam, url);

				if (_log.isDebugEnabled()) {
					_log.debug("Redirect requested layout to " + redirect);
				}

				response.sendRedirect(redirect);
			}
			else {
				String redirect = PortalUtil.getLayoutURL(layout, themeDisplay);

				if (_log.isDebugEnabled()) {
					_log.debug("Redirect default layout to " + redirect);
				}

				response.sendRedirect(redirect);
			}

			return null;
		}

		long plid = ParamUtil.getLong(request, "p_l_id");

		if (plid > 0) {
			return processLayout(mapping, request, response, plid);
		}
		else {
			try {
				forwardLayout(request);

				return mapping.findForward(ActionConstants.COMMON_FORWARD);
			}
			catch (Exception e) {
				PortalUtil.sendError(e, request, response);

				return null;
			}
		}
	}

	protected void forwardLayout(HttpServletRequest request) throws Exception {
		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);
		long plid = LayoutConstants.DEFAULT_PLID;
		String layoutFriendlyURL = null;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (layout != null) {
			plid = layout.getPlid();
			layoutFriendlyURL =
				PortalUtil.getLayoutFriendlyURL(layout, themeDisplay);
		}

		String forwardURL = layoutFriendlyURL;

		if (Validator.isNull(forwardURL)) {
			forwardURL =
				themeDisplay.getPathMain() + "/portal/layout?p_l_id=" + plid;

			if (Validator.isNotNull(themeDisplay.getDoAsUserId())) {
				forwardURL = HttpUtil.addParameter(
					forwardURL, "doAsUserId", themeDisplay.getDoAsUserId());
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Forward layout to " + forwardURL);
		}

		request.setAttribute(WebKeys.FORWARD_URL, forwardURL);
	}

	protected void includeLayoutContent(
			HttpServletRequest request, HttpServletResponse response,
			ThemeDisplay themeDisplay, Layout layout)
		throws Exception {

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		String path = StrutsUtil.TEXT_HTML_DIR;

		if (BrowserSnifferUtil.is_wap(request)) {
			path = StrutsUtil.TEXT_WAP_DIR;
		}

		// Manually check the p_p_id. See LEP-1724.

		if (themeDisplay.isStateExclusive() ||
			Validator.isNotNull(ParamUtil.getString(request, "p_p_id"))) {

			if (layout.getType().equals(LayoutConstants.TYPE_PANEL)) {
				path += "/portal/layout/view/panel.jsp";
			}
			else {
				path += "/portal/layout/view/portlet.jsp";
			}
		}
		else {
			path += PortalUtil.getLayoutViewPage(layout);
		}

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(path);

		StringServletResponse stringResponse = new StringServletResponse(
			response);

		requestDispatcher.include(request, stringResponse);

		request.setAttribute(
			WebKeys.LAYOUT_CONTENT, stringResponse.getString());
	}

	protected void processEvent(
			PortletRequestImpl portletRequestImpl,
			StateAwareResponseImpl stateAwareResponseImpl, Portlet portlet,
			List<Portlet> portlets, Event event)
		throws Exception {

		HttpServletRequest request = portletRequestImpl.getHttpServletRequest();
		HttpServletResponse response =
			stateAwareResponseImpl.getHttpServletResponse();

		String portletId = portlet.getPortletId();

		ServletContext servletContext =
			(ServletContext)request.getAttribute(WebKeys.CTX);

		InvokerPortlet invokerPortlet = PortletInstanceFactory.create(
			portlet, servletContext);

		PortletConfig portletConfig = PortletConfigFactory.create(
			portlet, servletContext);
		PortletContext portletContext = portletConfig.getPortletContext();

		WindowState windowState = portletRequestImpl.getWindowState();
		PortletMode portletMode = portletRequestImpl.getPortletMode();

		User user = stateAwareResponseImpl.getUser();
		Layout layout = stateAwareResponseImpl.getLayout();

		PortletPreferences portletPreferences =
			portletRequestImpl.getPreferencesImpl();

		EventRequestImpl eventRequestImpl = EventRequestFactory.create(
			request, portlet, invokerPortlet, portletContext, windowState,
			portletMode, portletPreferences, layout.getPlid());

		eventRequestImpl.setEvent(event);

		EventResponseImpl eventResponseImpl = EventResponseFactory.create(
			eventRequestImpl, response, portletId, user, layout, windowState,
			portletMode);

		eventRequestImpl.defineObjects(portletConfig, eventResponseImpl);

		try {
			try {
				invokerPortlet.processEvent(
					eventRequestImpl, eventResponseImpl);

				if (eventResponseImpl.isCalledSetRenderParameter()) {
					Map<String, String[]> renderParameterMap =
						new HashMap<String, String[]>();

					MapUtil.copy(
						eventResponseImpl.getRenderParameterMap(),
						renderParameterMap);

					RenderParametersPool.put(
						request, layout.getPlid(), portletId,
						renderParameterMap);
				}
			}
			catch (UnavailableException ue) {
				throw ue;
			}
			catch (PortletException pe) {
				eventResponseImpl.setWindowState(windowState);
				eventResponseImpl.setPortletMode(portletMode);
			}

			processEvents(eventRequestImpl, eventResponseImpl, portlets);
		}
		finally {
			EventRequestFactory.recycle(eventRequestImpl);
			EventResponseFactory.recycle(eventResponseImpl);
		}
	}

	protected void processEvents(
			PortletRequestImpl portletRequestImpl,
			StateAwareResponseImpl stateAwareResponseImpl,
			List<Portlet> portlets)
		throws Exception {

		List<Event> events = stateAwareResponseImpl.getEvents();

		if (events.size() == 0) {
			return;
		}

		for (Event event : events) {
			QName qName = event.getQName();

			for (Portlet portlet : portlets) {
				QName processingQName = portlet.getProcessingEvent(
					qName.getNamespaceURI(), qName.getLocalPart());

				if (processingQName != null) {
					processEvent(
						portletRequestImpl, stateAwareResponseImpl, portlet,
						portlets, event);
				}
			}
		}
	}

	protected ActionForward processLayout(
			ActionMapping mapping, HttpServletRequest request,
			HttpServletResponse response, long plid)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			Layout layout = themeDisplay.getLayout();

			boolean resetLayout = ParamUtil.getBoolean(
				request, "p_l_reset", PropsValues.LAYOUT_DEFAULT_P_L_RESET);

			if (!PropsValues.TCK_URL && resetLayout) {
				RenderParametersPool.clear(request, plid);
			}

			if (themeDisplay.isLifecycleAction()) {
				Portlet portlet = processPortletRequest(
					request, response, PortletRequest.ACTION_PHASE);

				if (portlet != null) {
					ActionResponseImpl actionResponseImpl =
						(ActionResponseImpl)request.getAttribute(
							JavaConstants.JAVAX_PORTLET_RESPONSE);

					String redirectLocation =
						actionResponseImpl.getRedirectLocation();

					if (Validator.isNotNull(redirectLocation)) {
						response.sendRedirect(redirectLocation);

						return null;
					}

					if (portlet.isActionURLRedirect()) {
						redirectActionURL(
							request, response, actionResponseImpl, portlet);

						return null;
					}
				}
			}
			else if (themeDisplay.isLifecycleRender()) {
				processPortletRequest(
					request, response, PortletRequest.RENDER_PHASE);
			}

			if (themeDisplay.isLifecycleResource()) {
				processPortletRequest(
					request, response, PortletRequest.RESOURCE_PHASE);

				return null;
			}
			else {
				if (layout != null) {

					// Include layout content before the page loads because
					// portlets on the page can set the page title and page
					// subtitle

					includeLayoutContent(
						request, response, themeDisplay, layout);

					if (themeDisplay.isStateExclusive()) {
						renderExclusive(request, response, themeDisplay);

						return null;
					}
				}

				return mapping.findForward("portal.layout");
			}
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
		finally {
			PortletRequest portletRequest =
				(PortletRequest)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			try {
				if (portletRequest != null) {
					if (themeDisplay.isLifecycleAction()) {
						ActionRequestImpl actionRequestImpl =
							(ActionRequestImpl)portletRequest;

						ActionRequestFactory.recycle(actionRequestImpl);
					}
					else if (themeDisplay.isLifecycleRender()) {
						RenderRequestImpl renderRequestImpl =
							(RenderRequestImpl)portletRequest;

						RenderRequestFactory.recycle(renderRequestImpl);
					}
					else if (themeDisplay.isLifecycleResource()) {
						ResourceRequestImpl resourceRequestImpl =
							(ResourceRequestImpl)portletRequest;

						ResourceRequestFactory.recycle(resourceRequestImpl);
					}
				}
			}
			catch (Exception e) {
				_log.error(e);
			}

			request.removeAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);

			PortletResponse portletResponse =
				(PortletResponse)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			try {
				if (portletResponse != null) {
					if (themeDisplay.isLifecycleAction()) {
						ActionResponseImpl actionResponseImpl =
							(ActionResponseImpl)portletResponse;

						ActionResponseFactory.recycle(actionResponseImpl);
					}
					else if (themeDisplay.isLifecycleRender()) {
						RenderResponseImpl renderResponseImpl =
							(RenderResponseImpl)portletResponse;

						RenderResponseFactory.recycle(renderResponseImpl);
					}
					else if (themeDisplay.isLifecycleResource()) {
						ResourceResponseImpl resourceResponseImpl =
							(ResourceResponseImpl)portletResponse;

						ResourceResponseFactory.recycle(resourceResponseImpl);
					}
				}
			}
			catch (Exception e) {
				_log.error(e);
			}

			request.removeAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);
		}
	}

	protected Portlet processPortletRequest(
			HttpServletRequest request, HttpServletResponse response,
			String lifecycle)
		throws Exception {

		HttpSession session = request.getSession();

		long companyId = PortalUtil.getCompanyId(request);
		User user = PortalUtil.getUser(request);
		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		String portletId = ParamUtil.getString(request, "p_p_id");

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		if (portlet == null) {
			return null;
		}

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		InvokerPortlet invokerPortlet = PortletInstanceFactory.create(
			portlet, servletContext);

		if (user != null) {
			InvokerPortlet.clearResponse(
				session, layout.getPrimaryKey(), portletId,
				LanguageUtil.getLanguageId(request));
		}

		PortletConfig portletConfig = PortletConfigFactory.create(
			portlet, servletContext);
		PortletContext portletContext = portletConfig.getPortletContext();

		WindowState windowState = WindowStateFactory.getWindowState(
			ParamUtil.getString(request, "p_p_state"));

		PortletMode portletMode = PortletModeFactory.getPortletMode(
			ParamUtil.getString(request, "p_p_mode"));

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				request, portletId);

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getPreferences(
				portletPreferencesIds);

		if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);

			if (_log.isDebugEnabled()) {
				_log.debug("Content type " + contentType);
			}

			UploadServletRequest uploadRequest = null;

			try {
				if ((contentType != null) &&
					(contentType.startsWith(
						ContentTypes.MULTIPART_FORM_DATA))) {

					if (!invokerPortlet.getPortletConfig().isWARFile() ||
						invokerPortlet.isStrutsPortlet()) {

						uploadRequest = new UploadServletRequestImpl(request);

						request = uploadRequest;
					}
				}

				ActionRequestImpl actionRequestImpl =
					ActionRequestFactory.create(
						request, portlet, invokerPortlet, portletContext,
						windowState, portletMode, portletPreferences,
						layout.getPlid());

				ActionResponseImpl actionResponseImpl =
					ActionResponseFactory.create(
						actionRequestImpl, response, portletId, user, layout,
						windowState, portletMode);

				actionRequestImpl.defineObjects(
					portletConfig, actionResponseImpl);

				invokerPortlet.processAction(
					actionRequestImpl, actionResponseImpl);

				actionResponseImpl.transferHeaders(response);

				RenderParametersPool.put(
					request, layout.getPlid(), portletId,
					actionResponseImpl.getRenderParameterMap());

				if (actionResponseImpl.getEvents().size() > 0) {
					if (layout.getType().equals(LayoutConstants.TYPE_PORTLET)) {
						LayoutTypePortlet layoutTypePortlet =
							(LayoutTypePortlet)layout.getLayoutType();

						List<Portlet> portlets =
							layoutTypePortlet.getPortlets();

						processEvents(
							actionRequestImpl, actionResponseImpl, portlets);

						actionRequestImpl.defineObjects(
							portletConfig, actionResponseImpl);
					}
				}
			}
			finally {
				if (uploadRequest != null) {
					uploadRequest.cleanUp();
				}
			}
		}
		else if (lifecycle.equals(PortletRequest.RENDER_PHASE) ||
				 lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			PortalUtil.updateWindowState(
				portletId, user, layout, windowState, request);

			PortalUtil.updatePortletMode(
				portletId, user, layout, portletMode, request);
		}

		if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			String portletPrimaryKey = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portletId);

			portletDisplay.setId(portletId);
			portletDisplay.setRootPortletId(portlet.getRootPortletId());
			portletDisplay.setInstanceId(portlet.getInstanceId());
			portletDisplay.setResourcePK(portletPrimaryKey);
			portletDisplay.setPortletName(portletConfig.getPortletName());
			portletDisplay.setNamespace(
				PortalUtil.getPortletNamespace(portletId));

			ResourceRequestImpl resourceRequestImpl =
				ResourceRequestFactory.create(
					request, portlet, invokerPortlet, portletContext,
					windowState, portletMode, portletPreferences,
					layout.getPlid());

			StringServletResponse stringResponse = new StringServletResponse(
				response);

			ResourceResponseImpl resourceResponseImpl =
				ResourceResponseFactory.create(
					resourceRequestImpl, stringResponse, portletId, companyId);

			resourceRequestImpl.defineObjects(
				portletConfig, resourceResponseImpl);

			invokerPortlet.serveResource(
				resourceRequestImpl, resourceResponseImpl);

			resourceResponseImpl.transferHeaders(response);

			if (stringResponse.isCalledGetOutputStream()) {
				InputStream is = new ByteArrayInputStream(
					stringResponse.getByteArrayMaker().toByteArray());

				ServletResponseUtil.sendFile(
					response, resourceRequestImpl.getResourceID(), is,
					resourceResponseImpl.getContentType());
			}
			else {
				byte[] content = stringResponse.getString().getBytes(
					StringPool.UTF8);

				ServletResponseUtil.sendFile(
					response, resourceRequestImpl.getResourceID(), content,
					resourceResponseImpl.getContentType());
			}
		}

		return portlet;
	}

	protected void redirectActionURL(
			HttpServletRequest request, HttpServletResponse response,
			ActionResponseImpl actionResponseImpl, Portlet portlet)
		throws Exception {

		ActionRequestImpl actionRequestImpl =
			(ActionRequestImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		PortletURL portletURL = new PortletURLImpl(
			actionRequestImpl, actionRequestImpl.getPortletName(),
			layout.getLayoutId(), PortletRequest.RENDER_PHASE);

		Map<String, String[]> renderParameters =
			actionResponseImpl.getRenderParameterMap();

		for (Map.Entry<String, String[]> entry : renderParameters.entrySet()) {
			String key = entry.getKey();
			String[] value = entry.getValue();

			portletURL.setParameter(key, value);
		}

		response.sendRedirect(portletURL.toString());
	}

	protected void renderExclusive(
			HttpServletRequest request, HttpServletResponse response,
			ThemeDisplay themeDisplay)
		throws Exception {

		RenderRequestImpl renderRequestImpl =
			(RenderRequestImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		RenderResponseImpl renderResponseImpl =
			(RenderResponseImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		StringServletResponse stringResponse =
			(StringServletResponse)renderRequestImpl.getAttribute(
				WebKeys.STRING_SERVLET_RESPONSE);

		renderResponseImpl.transferHeaders(response);

		if (stringResponse.isCalledGetOutputStream()) {
			InputStream is = new ByteArrayInputStream(
				stringResponse.getByteArrayMaker().toByteArray());

			ServletResponseUtil.sendFile(
				response, renderResponseImpl.getResourceName(), is,
				renderResponseImpl.getContentType());
		}
		else {
			byte[] content = stringResponse.getString().getBytes(
				StringPool.UTF8);

			ServletResponseUtil.sendFile(
				response, renderResponseImpl.getResourceName(), content,
				renderResponseImpl.getContentType());
		}

		RenderRequestFactory.recycle(renderRequestImpl);
		RenderResponseFactory.recycle(renderResponseImpl);
	}

	private static Log _log = LogFactory.getLog(LayoutAction.class);

}