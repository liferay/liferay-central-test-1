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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "export");

String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String portletResource = ParamUtil.getString(request, "portletResource");

Portlet selPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

String selPortletPrimaryKey = PortletPermissionUtil.getPrimaryKey(layout.getPlid(), selPortlet.getPortletId());

String path = (String)request.getAttribute(WebKeys.CONFIGURATION_ACTION_PATH);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/portlet_configuration/export_import");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
portletURL.setParameter("portletResource", portletResource);

boolean supportsLAR = Validator.isNotNull(selPortlet.getPortletDataHandlerClass());
boolean supportsSetup = Validator.isNotNull(selPortlet.getConfigurationActionClass());
%>

<script type="text/javascript">
	function <portlet:namespace />copyFromLive() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-copy-from-live-and-update-the-existing-staging-portlet-information") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "copy_from_live";

			submitForm(document.<portlet:namespace />fm);
		}
	}

	function <portlet:namespace />exportData() {
		document.<portlet:namespace />fm.encoding = "multipart/form-data";

		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "export";

		submitForm(document.<portlet:namespace />fm, '<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/export_import" /></portlet:actionURL>');
	}

	function <portlet:namespace />importData() {
		document.<portlet:namespace />fm.encoding = "multipart/form-data";

		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "import";

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />publishToLive() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-publish-to-live-and-update-the-existing-portlet-data") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "publish_to_live";

			submitForm(document.<portlet:namespace />fm);
		}
	}

	function <portlet:namespace />toggleChildren(checkbox, parentDivId) {
		if (checkbox.checked) {
			jQuery('#' + parentDivId).find("input").not(".disabled").removeAttr('disabled');
		}
		else {
			jQuery('#' + parentDivId).find("input").attr("disabled", "disabled");
		}
	}
</script>

<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
	<liferay-util:param name="tabs1" value="export-import" />
</liferay-util:include>

<c:choose>
	<c:when test="<%= supportsLAR || supportsSetup %>">

		<%
		String tabs2Names = "export,import";

		if (layout.getGroup().isStagingGroup()) {
			tabs2Names += ",staging";
		}
		%>

		<liferay-ui:tabs
			names="<%= tabs2Names %>"
			param="tabs2"
			url="<%= portletURL.toString() %>"
		/>

		<liferay-ui:error exception="<%= LARTypeException.class %>" message="please-import-a-valid-lar-file" />
		<liferay-ui:error exception="<%= LayoutImportException.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />
		<liferay-ui:error exception="<%= NoSuchLayoutException.class %>" message="an-error-occurred-because-the-live-group-does-not-have-the-current-page" />
		<liferay-ui:error exception="<%= PortletIdException.class %>" message="please-import-a-lar-file-for-the-current-portlet" />

		<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/export_import" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveData(); return false;">
		<input name="<portlet:namespace />tabs1" type="hidden" value="export_import">
		<input name="<portlet:namespace />tabs2" type="hidden" value="<%= HtmlUtil.escape(tabs2) %>">
		<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
		<input name="<portlet:namespace />plid" type="hidden" value="<%= layout.getPlid() %>">
		<input name="<portlet:namespace />groupId" type="hidden" value="<%= layout.getGroupId() %>">
		<input name="<portlet:namespace />portletResource" type="hidden" value="<%= HtmlUtil.escape(portletResource) %>">
		<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(currentURL) %>">

		<c:choose>
			<c:when test='<%= tabs2.equals("export") %>'>
				<liferay-ui:message key="export-the-selected-data-to-the-given-lar-file-name" />

				<br /><br />

				<div>
					<input name="<portlet:namespace />exportFileName" size="50" type="text" value="<%= StringUtil.replace(selPortlet.getDisplayName(), " ", "_") %>-<%= Time.getShortTimestamp() %>.portlet.lar">
				</div>

				<br />

				<script type="text/javascript">
					function toggleDateRange() {
						jQuery("#<portlet:namespace />startEndDate").toggle();
					}
				</script>

				<div>
					<liferay-ui:input-checkbox param="dateRange" onClick="toggleDateRange()" />

					<liferay-ui:message key="date-range" />

					<liferay-ui:icon-help message="export-date-range-help" />
				</div>

				<%
				Calendar today = CalendarFactoryUtil.getCalendar(timeZone, locale);

				Calendar yesterday = CalendarFactoryUtil.getCalendar(timeZone, locale);
				yesterday.add(Calendar.DATE, -1);
				%>

				<br />

				<table id="<portlet:namespace />startEndDate" class="lfr-table" style="display: none">
				<tr>
					<td>
						<liferay-ui:message key="start-date" />
					</td>
					<td>
						<liferay-ui:input-date
							disableNamespace="<%= true %>"
							monthParam="startDateMonth"
							monthValue="<%= yesterday.get(Calendar.MONTH) %>"
							dayParam="startDateDay"
							dayValue="<%= yesterday.get(Calendar.DATE) %>"
							yearParam="startDateYear"
							yearValue="<%= yesterday.get(Calendar.YEAR) %>"
							yearRangeStart="<%= yesterday.get(Calendar.YEAR) - 100 %>"
							yearRangeEnd="<%= yesterday.get(Calendar.YEAR) %>"
							firstDayOfWeek="<%= yesterday.getFirstDayOfWeek() - 1 %>"
							disabled="<%= false %>"
						/>

						&nbsp;

						<liferay-ui:input-time
							hourParam='<%= "startDateHour" %>'
							hourValue="<%= yesterday.get(Calendar.HOUR) %>"
							minuteParam='<%= "startDateMinute" %>'
							minuteValue="<%= yesterday.get(Calendar.MINUTE) %>"
							minuteInterval="1"
							amPmParam='<%= "startDateAmPm" %>'
							amPmValue="<%= yesterday.get(Calendar.AM_PM) %>"
							disabled="false"
						/>
					</td>
				</tr>
				<tr>
					<td>
						<liferay-ui:message key="end-date" />
					</td>
					<td>
						<liferay-ui:input-date
							disableNamespace="<%= true %>"
							monthParam="endDateMonth"
							monthValue="<%= today.get(Calendar.MONTH) %>"
							dayParam="endDateDay"
							dayValue="<%= today.get(Calendar.DATE) %>"
							yearParam="endDateYear"
							yearValue="<%= today.get(Calendar.YEAR) %>"
							yearRangeStart="<%= today.get(Calendar.YEAR) - 100 %>"
							yearRangeEnd="<%= today.get(Calendar.YEAR) %>"
							firstDayOfWeek="<%= today.getFirstDayOfWeek() - 1 %>"
							disabled="<%= false %>"
						/>

						&nbsp;

						<liferay-ui:input-time
							hourParam='<%= "endDateHour" %>'
							hourValue="<%= today.get(Calendar.HOUR) %>"
							minuteParam='<%= "endDateMinute" %>'
							minuteValue="<%= today.get(Calendar.MINUTE) %>"
							minuteInterval="1"
							amPmParam='<%= "endDateAmPm" %>'
							amPmValue="<%= today.get(Calendar.AM_PM) %>"
							disabled="false"
						/>
					</td>
				</tr>
				<tr>
					<td cellspan="2">
						<br />
					</td>
				</tr>
				</table>

				<liferay-ui:message key="what-would-you-like-to-export" />

				<br /><br />

				<%@ include file="/html/portlet/portlet_configuration/export_import_options.jspf" %>

				<br />

				<input type="button" value='<liferay-ui:message key="export" />' onClick="<portlet:namespace />exportData();" />
			</c:when>
			<c:when test='<%= tabs2.equals("import") %>'>
				<liferay-ui:message key="import-a-lar-file-to-overwrite-the-selected-data" />

				<br /><br />

				<div>
					<input name="<portlet:namespace />importFileName" size="50" type="file" />
				</div>

				<br />

				<liferay-ui:message key="what-would-you-like-to-import" />

				<br /><br />

				<%@ include file="/html/portlet/portlet_configuration/export_import_options.jspf" %>

				<br />

				<input type="button" value="<liferay-ui:message key="import" />" onClick="<portlet:namespace />importData();">
			</c:when>
			<c:when test='<%= tabs2.equals("staging") %>'>

				<%
				String errorMesageKey = StringPool.BLANK;

				Group stagingGroup = layout.getGroup();
				Group liveGroup = stagingGroup.getLiveGroup();

				Layout targetLayout = null;

				try {
					targetLayout = LayoutLocalServiceUtil.getLayout(liveGroup.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId());
				}
				catch (NoSuchLayoutException nsle) {
					errorMesageKey = "this-portlet-is-placed-in-a-page-that-does-not-exist-in-the-live-site-publish-the-page-first";
				}

				if (targetLayout != null) {
					LayoutType layoutType = targetLayout.getLayoutType();

					if (!(layoutType instanceof LayoutTypePortlet) || !((LayoutTypePortlet)layoutType).hasPortletId(selPortlet.getPortletId())) {
						errorMesageKey = "this-portlet-has-not-been-added-to-the-live-page-publish-the-page-first";
					}
				}

				boolean workflowEnabled = liveGroup.isWorkflowEnabled();

				TasksProposal proposal = null;

				if (workflowEnabled) {
					try {
						proposal = TasksProposalLocalServiceUtil.getProposal(Portlet.class.getName(), selPortletPrimaryKey);
					}
					catch (NoSuchProposalException nspe) {
					}
				}
				%>

				<c:choose>
					<c:when test="<%= Validator.isNull(errorMesageKey) %>">
						<liferay-ui:message key="what-would-you-like-to-copy-from-live-or-publish-to-live" />

						<br /><br />

						<%@ include file="/html/portlet/portlet_configuration/export_import_options.jspf" %>

						<br />

						<c:choose>
							<c:when test="<%= workflowEnabled %>">
								<c:if test="<%= proposal == null %>">

									<%
									PortletURL proposePublicationURL = new PortletURLImpl(request, PortletKeys.LAYOUT_MANAGEMENT, layout.getPlid(), PortletRequest.ACTION_PHASE);

									proposePublicationURL.setWindowState(WindowState.MAXIMIZED);
									proposePublicationURL.setPortletMode(PortletMode.VIEW);

									proposePublicationURL.setParameter("struts_action", "/layout_management/edit_proposal");
									proposePublicationURL.setParameter(Constants.CMD, Constants.ADD);
									proposePublicationURL.setParameter("redirect", currentURL);
									proposePublicationURL.setParameter("groupId", String.valueOf(liveGroup.getGroupId()));
									proposePublicationURL.setParameter("className", Portlet.class.getName());
									proposePublicationURL.setParameter("classPK", selPortletPrimaryKey);

									String[] workflowRoleNames = StringUtil.split(liveGroup.getWorkflowRoleNames());

									JSONArray jsonReviewers = new JSONArray();

									Role role = RoleLocalServiceUtil.getRole(company.getCompanyId(), workflowRoleNames[0]);

									LinkedHashMap userParams = new LinkedHashMap();

									userParams.put("usersGroups", new Long(liveGroup.getGroupId()));
									userParams.put("userGroupRole", new Long[] {new Long(liveGroup.getGroupId()), new Long(role.getRoleId())});

									List<User> reviewers = UserLocalServiceUtil.search(company.getCompanyId(), null, null, userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

									if (reviewers.size() == 0) {
										if (liveGroup.isCommunity()) {
											role = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleImpl.COMMUNITY_OWNER);
										}
										else {
											role = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleImpl.ORGANIZATION_OWNER);
										}

										userParams.put("userGroupRole", new Long[] {new Long(liveGroup.getGroupId()), new Long(role.getRoleId())});

										reviewers = UserLocalServiceUtil.search(company.getCompanyId(), null, null, userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
									}

									for (User reviewer : reviewers) {
										JSONObject jsonReviewer = new JSONObject();

										jsonReviewer.put("userId", reviewer.getUserId());
										jsonReviewer.put("fullName", reviewer.getFullName());

										jsonReviewers.put(jsonReviewer);
									}
									%>

									<input type="button" value="<liferay-ui:message key="propose-publication" />" onClick="Liferay.LayoutExporter.proposeLayout({url: '<%= proposePublicationURL.toString() %>', namespace: '<%= PortalUtil.getPortletNamespace(PortletKeys.LAYOUT_MANAGEMENT) %>', reviewers: <%= StringUtil.replace(jsonReviewers.toString(), '"', '\'') %>, title: '<liferay-ui:message key="proposal-description" />'});" />
								</c:if>
							</c:when>
							<c:when test="<%= themeDisplay.getURLPublishToLive() != null %>">
								<input type="button" value="<liferay-ui:message key="publish-to-live" />" onClick="<portlet:namespace />publishToLive();" />
							</c:when>
						</c:choose>

						<input type="button" value="<liferay-ui:message key="copy-from-live" />" onClick="<portlet:namespace />copyFromLive();" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="<%= errorMesageKey %>" />
					</c:otherwise>
				</c:choose>
			</c:when>
		</c:choose>

		</form>

		<script type="text/javascript">
			jQuery(function(){
				jQuery(".<portlet:namespace />handler-control input[@type=checkbox]:not([@checked])").parent().parent().parent(".<portlet:namespace />handler-control").children(".<portlet:namespace />handler-control").hide();

				jQuery(".<portlet:namespace />handler-control input[@type=checkbox]").unbind('click').click(function() {
					var input = jQuery(this).parents(".<portlet:namespace />handler-control:first");

					if (this.checked) {
						input.children(".<portlet:namespace />handler-control").show();
					}
					else {
						input.children(".<portlet:namespace />handler-control").hide();
					}
				});
			});
		</script>

	</c:when>
	<c:otherwise>
		<%= LanguageUtil.format(locale, "the-x-portlet-does-not-have-any-data-that-can-be-exported-or-does-not-include-support-for-it", PortalUtil.getPortletTitle(selPortlet, application, locale)) %>
	</c:otherwise>
</c:choose>

<%@ include file="/html/portlet/communities/render_controls.jspf" %>