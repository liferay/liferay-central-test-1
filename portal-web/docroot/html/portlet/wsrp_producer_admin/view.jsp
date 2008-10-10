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

<%
/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */
%>

<%@ include file="/html/portlet/wsrp_producer_admin/init.jsp" %>

<%
int action = ParamUtil.getInteger(request, Constants.ACTION,AdminPortletAction.LIST);

String redirect = ParamUtil.getString(request, "redirect");
%>

<c:choose>
	<c:when test="<%= action == AdminPortletAction.LIST %>">
		<form action="<portlet:actionURL />" method="post" name="<portlet:namespace />fm">

		<liferay-ui:tabs names="producers" />

		<%
		ProducerElementBean[] producerBeans = (ProducerElementBean[])renderRequest.getAttribute("listProducerBeans");

		SearchContainer searchContainer = new SearchContainer();

		List<String> headerNames = new ArrayList<String>();

		headerNames.add("name");
		headerNames.add("status");
		headerNames.add("registration");
		headerNames.add(StringPool.BLANK);

		searchContainer.setHeaderNames(headerNames);
		searchContainer.setEmptyResultsMessage("there-are-no-producers");

		List<ProducerElementBean> results = null;

		if (producerBeans != null) {
			results = ListUtil.fromArray(producerBeans);
		}
		else {
			results = Collections.EMPTY_LIST;
		}

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			ProducerElementBean producerBean = results.get(i);

			ResultRow row = new ResultRow(producerBean, producerBean.getProducerKey(), i);

			// Name

			PortletURL portletURL = renderResponse.createActionURL();

			portletURL.setParameter(Constants.ACTION, String.valueOf(AdminPortletAction.GET_DETAILS));
			portletURL.setParameter("redirect", currentURL);
			portletURL.setParameter("producerId", producerBean.getProducerKey());

			row.addText(producerBean.getProducerKey(), portletURL);

			// Status

			row.addText(LanguageUtil.get(pageContext, producerBean.getIsEnabled() ? "enabled" : "disabled"), portletURL);

			// Registration

			row.addText(LanguageUtil.get(pageContext, producerBean.getRequiresRegistration() ? "required" : "not-required"), portletURL);

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/wsrp_producer_admin/producer_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<div>
			<input type="button" value="<liferay-ui:message key="add-producer" />" onClick="location.href = '<portlet:actionURL><portlet:param name="<%= Constants.ACTION %>" value="<%= String.valueOf(AdminPortletAction.GET_DEFAULT) %>" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>';" />
		</div>

		<br />

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />

		</form>
	</c:when>
	<c:when test="<%= action == AdminPortletAction.GET_DETAILS %>">

		<%
		ProducerBean producerBean = (ProducerBean)portletSession.getAttribute("producerBean");

		Map supportedVersions = (Map)portletSession.getAttribute(AdminPortletConstants.SUPPORTED_VERSIONS);

		Set<Map.Entry<String, String>> versions = supportedVersions.entrySet();

		PortletPreferences preferences = renderRequest.getPreferences();

		String producerHost = preferences.getValue("producerHost", "localhost");
		String producerPort = preferences.getValue("producerPort", "8080");

		String wsdl = "http://" + producerHost + ":" + producerPort + producerBean.getWsdlURL();
		%>

		<form action="<portlet:actionURL />" method="post" name="<portlet:namespace />fm">
		<input name="<portlet:namespace />publishedPortletString" type="hidden" value="" />
		<input name="<portlet:namespace /><%= Constants.ACTION %>" type="hidden" value="<%= AdminPortletAction.UPDATE %>" />
		<input name="<portlet:namespace />producerKey" type="hidden" value="<%= producerBean.getProducerKey() %>" />

		<liferay-ui:tabs names="producer" />

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="name" />
			</td>
			<td>
				<%= producerBean.getProducerKey() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="url" />
			</td>
			<td>
				<a href="<%= HtmlUtil.escape(wsdl) %>" target="_blank"><%= HtmlUtil.escape(wsdl) %></a>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="version" />
			</td>
			<td>
				<select name="<portlet:namespace /><%= AdminPortletConstants.PRODUCER_VERSION %>">

					<%
					String version = GetterUtil.getString(producerBean.getVersion());

					for (Map.Entry<String, String> entry : versions) {
					%>

						<option <%= version.equalsIgnoreCase(entry.getValue()) ? "selected" : "" %> value="<%= entry.getValue() %>"><%= _formatVersion(entry.getKey()) %></option>

					<%
					}
					%>

				</select>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="status" />
			</td>
			<td>
				<select name="<portlet:namespace />enabled">
					<c:if test="<%= !producerBean.getPublishedPortlets().isEmpty() %>">
						<option <%= producerBean.isEnabled() ? "selected" : "" %> value="true"><liferay-ui:message key="enabled" /></option>
					</c:if>

					<option <%= !producerBean.isEnabled() ? "selected" : "" %> value="false"><liferay-ui:message key="disabled" /></option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="registration" />
			</td>
			<td>
				<select name="<portlet:namespace />registrationRequired">
					<option <%= producerBean.isRegistrationRequired() ? "selected" : "" %> value="true"><liferay-ui:message key="required" /></option>
					<option <%= !producerBean.isRegistrationRequired() ? "selected" : "" %> value="false"><liferay-ui:message key="not-required" /></option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="inband-registration" />
			</td>
			<td>
				<select name="<portlet:namespace />inbandRegistrationSupported">
					<option <%= producerBean.isInbandRegistrationSupported() ? "selected" : "" %> value="true"><liferay-ui:message key="supported" /></option>
					<option <%= !producerBean.isInbandRegistrationSupported() ? "selected" : "" %> value="false"><liferay-ui:message key="not-supported" /></option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="registration-validator-class" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />registrationValidatorClass" type="text" value="<%= producerBean.getRegistrationValidatorClass() %>" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="registration-properties" />
			</td>
			<td>

				<%
				List<String> registrationPropertyDescriptions = producerBean.getRegistrationPropertyDescriptions();

				SearchContainer searchContainer = new SearchContainer();

				List<String> headerNames = new ArrayList<String>();

				headerNames.add("name");
				headerNames.add("description");

				searchContainer.setHeaderNames(headerNames);
				searchContainer.setEmptyResultsMessage("there-are-no-registration-properties");

				List<String> results = null;

				if (registrationPropertyDescriptions != null) {
					results = registrationPropertyDescriptions;
				}
				else {
					results = Collections.EMPTY_LIST;
				}

				List resultRows = searchContainer.getResultRows();

				for (int i = 0; i < results.size(); i++) {
					String registrationPropertyDescription = results.get(i);

					String[] propertyArray = registrationPropertyDescription.split(StringPool.EQUAL);

					String propertyName = propertyArray[0];
					String propertyDescription = propertyArray[1];

					ResultRow row = new ResultRow(propertyName, propertyName, i);

					// Name

					row.addText(propertyName);

					// Description

					row.addText(propertyDescription);

					// Add result row

					resultRows.add(row);
				}
				%>

				<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="publish-portlets" />
			</td>
			<td>

				<%

				// Left list

				List leftList = new ArrayList();

				List<String> unpublishedPortlets = producerBean.getUnpublishedPortlets();

				if (unpublishedPortlets != null) {
					for (String unpublishedPortlet : unpublishedPortlets) {
						leftList.add(new KeyValuePair(unpublishedPortlet, unpublishedPortlet));
					}
				}

				Collections.sort(leftList, new KeyValuePairComparator(false, true));

				// Right list

				List rightList = new ArrayList();

				List<String> publishedPortlets = producerBean.getPublishedPortlets();

				if (publishedPortlets != null) {
					for (String publishedPortlet : publishedPortlets) {
						rightList.add(new KeyValuePair(publishedPortlet, publishedPortlet));
					}
				}

				Collections.sort(rightList, new KeyValuePairComparator(false, true));
				%>

				<liferay-ui:input-move-boxes
					formName="fm"
					leftTitle="unpublished"
					rightTitle="published"
					leftBoxName="unpublishedPortlets"
					rightBoxName="publishedPortlets"
					leftList="<%= leftList %>"
					rightList="<%= rightList %>"
				/>
			</td>
		</tr>
		</table>

		<br />

		<input type="button" value="<liferay-ui:message key="save" />" onClick="document.<portlet:namespace />fm.<portlet:namespace />publishedPortletString.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />publishedPortlets,'$'); submitForm(document.<portlet:namespace />fm);" />

		<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

		</form>
	</c:when>
	<c:when test="<%= action == AdminPortletAction.GET_DEFAULT %>">
		<form action="<portlet:actionURL />" method="post" name="<portlet:namespace />fm">
		<input name="<portlet:namespace />action" type="hidden" value="<%= AdminPortletAction.CREATE %>" />

		<liferay-ui:tabs names="producer" />

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="name" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />name" type="text" value="" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="version" />
			</td>
			<td>
				<select name="<portlet:namespace /><%= AdminPortletConstants.PRODUCER_VERSION %>">

					<%
					Map supportedVersions = (Map)portletSession.getAttribute(AdminPortletConstants.SUPPORTED_VERSIONS);

					Set<Map.Entry<String, String>> versions = supportedVersions.entrySet();

					for (Map.Entry<String, String> entry : versions) {
					%>

						<option <%= entry.getKey().equalsIgnoreCase("V1andV2") ? "selected" : "" %> value="<%= entry.getValue() %>"><%= _formatVersion(entry.getKey()) %></option>

					<%
					}
					%>

				</select>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="registration" />
			</td>
			<td>
				<select name="<portlet:namespace />registration">
					<option value="1"><liferay-ui:message key="required" /></option>
					<option value="2"><liferay-ui:message key="not-required" /></option>
				</select>
			</td>
		</tr>
		<tr>
			<td><liferay-ui:message key="inband-registration" /></td>
			<td>
				<select name="<portlet:namespace />inbandRegistration">
					<option value="1"><liferay-ui:message key="supported" /></option>
					<option value="2"><liferay-ui:message key="not-supported" /></option>
				</select>
			</td>
		</tr>
		</table>

		<br />

		<input type="submit" value="<liferay-ui:message key="save" />" />

		<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

		</form>
	</c:when>
	<c:when test="<%= action == AdminPortletAction.LIST_CONSUMER_REGISTRATIONS %>">

		<%
		String producerId = ParamUtil.getString(request, "producerId");
		%>

		<form action="<portlet:actionURL />" method="post" name="<portlet:namespace />fm">

		<liferay-ui:tabs
			names="producers,consumer-registrations"
			url0="<%= redirect %>"
			url1="<%= currentURL %>"
		/>

		<%
		SearchContainer searchContainer = new SearchContainer();

		List<String> headerNames = new ArrayList<String>();

		headerNames.add("name");
		headerNames.add("registration-handle");
		headerNames.add("status");
		headerNames.add(StringPool.BLANK);

		searchContainer.setHeaderNames(headerNames);
		searchContainer.setEmptyResultsMessage("there-are-no-consumer-registrations");

		List resultRows = searchContainer.getResultRows();

		ConsumerRegistrationBean[] consumerRegistrations = (ConsumerRegistrationBean[])renderRequest.getAttribute("listConsumerRegistrations");

		for (int i = 0; (consumerRegistrations != null) && (i < consumerRegistrations.length); i++) {
			ConsumerRegistrationBean consumerRegistration = consumerRegistrations[i];

			ResultRow row = new ResultRow(consumerRegistration, consumerRegistration.getName(), i);

			// Name

			row.addText(consumerRegistration.getName());

			// Registration handle

			row.addText(consumerRegistration.getRegistrationHandle());

			// Status

			row.addText(LanguageUtil.get(pageContext, GetterUtil.getBoolean(consumerRegistration.getEnabled()) ? "enabled" : "disabled"));

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/wsrp_producer_admin/consumer_registration_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<div>
			<input type="button" value="<liferay-ui:message key="add-consumer-registration" />" onClick="location.href = '<portlet:actionURL><portlet:param name="<%= Constants.ACTION %>" value="<%= String.valueOf(AdminPortletAction.GET_DEFAULT_CONSUMER_REGISTRATION) %>" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="producerId" value="<%= producerId %>" /></portlet:actionURL>';" />
		</div>

		<br />

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />

		</form>
	</c:when>
	<c:when test="<%= action == AdminPortletAction.GET_DEFAULT_CONSUMER_REGISTRATION %>">

		<%
		String producerId = ParamUtil.getString(request, "producerId");
		%>

		<form action="<portlet:actionURL />" method="post" name="<portlet:namespace />fm">
		<input name="action" type="hidden" value="<%= AdminPortletAction.CREATE_CONSUMER_REGISTRATION %>" />
		<input name="producerId" type="hidden" value="<%= HtmlUtil.escape(producerId) %>" />

		<liferay-ui:tabs names="consumer-registration" />

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="name" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />name" type="text" value="" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="consumer-agent" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />agent" type="text" value="" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="http-method" />
			</td>
			<td>
				<select name="<portlet:namespace />methodGetSupported">
					<option value="false"><liferay-ui:message key="post" /></option>
					<option value="true"><liferay-ui:message key="get-or-post" /></option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="registration" />
			</td>
			<td>
				<select name="<portlet:namespace />status">
					<option value="true"><liferay-ui:message key="enabled" /></option>
					<option value="false"><liferay-ui:message key="disabled" /></option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="registration-properties" />
			</td>
			<td>

				<%
				List<String> registrationPropertyDescriptions = (List<String>)portletSession.getAttribute("regPropertiesList");

				SearchContainer searchContainer = new SearchContainer();

				List<String> headerNames = new ArrayList<String>();

				headerNames.add("name");
				headerNames.add("value");
				headerNames.add("description");

				searchContainer.setHeaderNames(headerNames);
				searchContainer.setEmptyResultsMessage("there-are-no-registration-properties");

				List<String> results = null;

				if (registrationPropertyDescriptions != null) {
					results = registrationPropertyDescriptions;
				}
				else {
					results = Collections.EMPTY_LIST;
				}

				List resultRows = searchContainer.getResultRows();

				for (int i = 0; i < results.size(); i++) {
					String registrationPropertyDescription = results.get(i);

					String[] propertyArray = registrationPropertyDescription.split(StringPool.EQUAL);

					String propertyName = propertyArray[0];
					String propertyDescription = propertyArray[1];

					ResultRow row = new ResultRow(propertyName, propertyName, i);

					// Name

					row.addText(propertyName);

					// Value

					String propertyValue = StringPool.BLANK;

					StringBuilder sb = new StringBuilder();

					sb.append("<input name=\"");
					sb.append(renderResponse.getNamespace());
					sb.append("regPropName");
					sb.append(i);
					sb.append("\" type=\"hidden\" value=\"");
					sb.append(propertyName);
					sb.append("\" />");

					sb.append("<input name=\"");
					sb.append(renderResponse.getNamespace());
					sb.append("regPropDescription");
					sb.append(i);
					sb.append("\" type=\"hidden\" value=\"");
					sb.append(propertyDescription);
					sb.append("\" />");

					sb.append("<input name=\"");
					sb.append(renderResponse.getNamespace());
					sb.append("regPropValue");
					sb.append(i);
					sb.append("\" type=\"text\" value=\"");
					sb.append(propertyValue);
					sb.append("\" />");

					row.addText(sb.toString());

					// Description

					row.addText(propertyDescription);

					// Add result row

					resultRows.add(row);
				}
				%>

				<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
			</td>
		</tr>

		<%
		ProducerBean producerBean = (ProducerBean)portletSession.getAttribute("producerBean");

		String version = producerBean.getVersion();
		boolean supportsInbandRegistration = producerBean.isInbandRegistrationSupported();
		%>

		<c:if test='<%= version.contains("V2") && !supportsInbandRegistration %>'>
			<tr>
				<td colspan="2">
					<br />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="lifetime" />
				</td>
				<td>
					<input id="<portlet:namespace />lifetimeSupplied" name="<portlet:namespace />lifetimeSupplied" type="checkbox" />
				</td>
			</tr>
			<tbody id="<portlet:namespace />lifetimeSettings">
				<tr>
					<td>
						<liferay-ui:message key="valid-days" />
					</td>
					<td>
						<input class="lfr-input-text" name="<portlet:namespace />lifetimeDays" size="5" type="text" />
					</td>
				</tr>
				<tr>
					<td>
						<liferay-ui:message key="valid-hours" />
					</td>
					<td>
						<input class="lfr-input-text" name="<portlet:namespace />lifetimeHours" size="5" type="text" />
					</td>
				</tr>
				<tr>
					<td>
						<liferay-ui:message key="valid-minutes" />
					</td>
					<td>
						<input class="lfr-input-text" name="<portlet:namespace />lifetimeMins" size="5" type="text" />
					</td>
				</tr>
			</tbody>
		</c:if>
		</table>

		<br />

		<input type="submit" value="<liferay-ui:message key="save" />" />

		<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

		</form>
	</c:when>
</c:choose>

<script type="text/javascript">
	jQuery(
		function() {
			Liferay.Util.toggleBoxes('<portlet:namespace />lifetimeSupplied', '<portlet:namespace />lifetimeSettings');
		}
	);
</script>

<%!
private String _formatVersion(String version) throws Exception {
	if (version.equals("V1")) {
		return "1.0";
	}
	else if (version.equals("V2")) {
		return "2.0";
	}
	else if (version.equals("V1andV2")) {
		return "1.0 and 2.0";
	}
	else {
		return version;
	}
}
%>
