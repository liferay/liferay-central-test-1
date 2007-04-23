<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/alfresco_content/init.jsp" %>

<%
String keywords = StringPool.BLANK;

ResultSetRow[] searchResults = new ResultSetRow[0];

try {
	keywords = ParamUtil.getString(request, "keywords");

	if (Validator.isNotNull(keywords)) {
		searchResults = AlfrescoContentUtil.getSearchResults(userId, password, keywords);
	}
}
catch (Exception e) {
	_log.error(e);
}
%>

<div class="portlet-alfresco-content">
	<form action="<liferay-portlet:actionURL portletConfiguration="true" />" class="uni-form" id="<portlet:namespace />fm1" method="post" name="<portlet:namespace />fm1">
	<input id="<portlet:namespace /><%= Constants.CMD %>" name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>">
	<input id="<portlet:namespace />uuid"  name="<portlet:namespace />uuid" type="hidden" value="<%= uuid %>">

	<fieldset class="block-labels">
		<div class="ctrl-holder">
			<label for="<portlet:namespace />userId"><bean:message key="user-id" /></label>
			
			<input class="text-input" id="<portlet:namespace />userId" name="<portlet:namespace />userId" type="text" value="<%= userId %>" />
		</div>

		<div class="ctrl-holder">
			<label for="<portlet:namespace />password"><bean:message key="password" /></label>
			
			<input class="text-input" id="<portlet:namespace />password" name="<portlet:namespace />password" type="password" value="<%= password %>">
		</div>

		<div class="ctrl-holder">
			<label for="<portlet:namespace />maximizeLinksCheckbox"><bean:message key="maximize-links" />
			
			<liferay-ui:input-checkbox param="maximizeLinks" defaultValue="<%= maximizeLinks %>" /></label>
		</div>

		<div class="button-holder">
			<input type="button" id="<portlet:namespace />saveFormButton" value="<bean:message key="save" />" />
			
			<input type="button" id="<portlet:namespace />clearCacheButton" value="<bean:message key="clear-cache" />" />
		 </div>
	</fieldset>

	</form>

	<script type="text/javascript">
		jQuery(
			function() {
				var form = jQuery('#<portlet:namespace />fm1');

				form.find('#<portlet:namespace />saveFormButton').click(
					function() {
						submitForm(document.<portlet:namespace />fm1);
					}
				);

				form.find('#<portlet:namespace />clearCacheButton').click(
					function() {
						jQuery('#<portlet:namespace /><%= Constants.CMD %>').val('clearCacheButton');
						submitForm(document.<portlet:namespace />fm1);
					}
				);

				jQuery('#<portlet:namespace />userId').trigger('focus');
			}
		);
	</script>

	<%
	String cmd = ParamUtil.getString(request, Constants.CMD);

	String selUuid = request.getParameter("uuid");

	if (Validator.isNotNull(cmd)) {
		selUuid = null;
	}
	%>

	<div class="separator"></div>

	<liferay-portlet:actionURL portletConfiguration="true" varImpl="portletURL" />

	<%

	try {
		Node node = AlfrescoContentUtil.getNode(userId, password, uuid);

		if (node != null) {
			NamedValue[] nodeNamedValues = node.getProperties();

			String nodeUuid = AlfrescoContentUtil.getNamedValue(nodeNamedValues, "node-uuid");
			String nodeName = AlfrescoContentUtil.getNamedValue(nodeNamedValues, org.alfresco.webservice.util.Constants.PROP_NAME);

			if (selUuid == null) {
				ResultSetRow[] parentNodes = AlfrescoContentUtil.getParentNodes(userId, password, uuid);

				if (parentNodes != null) {
					selUuid = parentNodes[0].getNode().getId();
				}
			}

			if (!nodeUuid.equals(nodeName)) {
	%>

				<div class="portlet-msg-info"><bean:message key="displaying-content" />: <%= nodeName %></div>

	<%
			}
		}
	}
	catch (Exception e) {
		_log.error(e);
	}

	String breadcrumbs = StringPool.BLANK;

	try {
		Node curNode = AlfrescoContentUtil.getNode(userId, password, selUuid);

		if (curNode != null) {
			NamedValue[] curNamedValues = curNode.getProperties();

			String curUuid = AlfrescoContentUtil.getNamedValue(curNamedValues, "node-uuid");
			String curName = AlfrescoContentUtil.getNamedValue(curNamedValues, org.alfresco.webservice.util.Constants.PROP_NAME);

			if (!curUuid.equals(curName)) {
				portletURL.setParameter("uuid", curUuid);

				breadcrumbs = "<a href='" + portletURL.toString() + "'>" + curName + "</a>";
			}

			curUuid = selUuid;

			ResultSetRow[] parentNodes = null;

			for (int i = 0;; i++) {
				parentNodes = AlfrescoContentUtil.getParentNodes(userId, password, curUuid);

				if (parentNodes == null) {
					break;
				}

				ResultSetRow resultSetRow = parentNodes[0];

				ResultSetRowNode node = resultSetRow.getNode();

				curNamedValues = resultSetRow.getColumns();

				curUuid = AlfrescoContentUtil.getNamedValue(curNamedValues, "node-uuid");
				curName = AlfrescoContentUtil.getNamedValue(curNamedValues, org.alfresco.webservice.util.Constants.PROP_NAME);

				if (!curUuid.equals(curName)) {
					portletURL.setParameter("uuid", curUuid);

					breadcrumbs = "<a href='" + portletURL.toString() + "'>" + curName + "</a>" + " &raquo; " + breadcrumbs;
				}
			}

			portletURL.setParameter("uuid", "");

			breadcrumbs = "<a href='" + portletURL.toString() + "'>Alfresco</a>" + " &raquo; " + breadcrumbs;
		}
	}
	catch (Exception e) {
		_log.error(e);
	}
	%>

	<c:if test="<%= (searchResults != null) && (searchResults.length == 0) %>">
		<c:if test="<%= Validator.isNotNull(breadcrumbs) %>">
			<div class="breadcrumbs">
				<%= breadcrumbs %>
			</div>
		</c:if>
	</c:if>

	<%
	SearchContainer searchContainer = new SearchContainer();

	List headerNames = new ArrayList();

	headerNames.add("name");

	searchContainer.setHeaderNames(headerNames);
	searchContainer.setEmptyResultsMessage("no-alfresco-content-was-found");

	ResultSetRow[] childNodes = new ResultSetRow[0];

	try {
		childNodes = AlfrescoContentUtil.getChildNodes(userId, password, selUuid);

		if (childNodes == null) {
			childNodes = new ResultSetRow[0];
		}

		if ((searchResults != null) && (searchResults.length > 0)) {
			childNodes = searchResults;
		}
	}
	catch (Exception e) {
		_log.error(e);
	}

	int total = childNodes.length;

	searchContainer.setTotal(total);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < childNodes.length; i++) {
		ResultSetRow resultSetRow = childNodes[i];

		ResultSetRowNode node = resultSetRow.getNode();

		ResultRow row = new ResultRow(node, node.getId(), i);

		NamedValue[] nodeNamedValues = resultSetRow.getColumns();

		StringMaker sm = new StringMaker();

		sm.append("javascript: ");

		String propContent = AlfrescoContentUtil.getNamedValue(nodeNamedValues, org.alfresco.webservice.util.Constants.PROP_CONTENT);

		if (propContent == null) {
			sm.append("document.");
			sm.append(renderResponse.getNamespace());
			sm.append("fm1.");
			sm.append(renderResponse.getNamespace());
			sm.append(Constants.CMD);
			sm.append(".value = ''; ");
		}

		sm.append("document.");
		sm.append(renderResponse.getNamespace());
		sm.append("fm1.");
		sm.append(renderResponse.getNamespace());
		sm.append("uuid.value = '");
		sm.append(node.getId());
		sm.append("'; ");
		sm.append("submitForm(document.");
		sm.append(renderResponse.getNamespace());
		sm.append("fm1);");

		String rowHREF = sm.toString();

		// Name

		sm = new StringMaker();

		sm.append("<img align=\"left\" border=\"0\" src=\"");
		sm.append(themeDisplay.getPathThemeImages());

		if (propContent == null) {
			sm.append("/common/folder.png");
		}
		else {
			sm.append("/trees/page.png");
		}

		sm.append("\">");

		String nodeName = AlfrescoContentUtil.getNamedValue(nodeNamedValues, org.alfresco.webservice.util.Constants.PROP_NAME);

		sm.append(nodeName);

		row.addText(sm.toString(), rowHREF);

		// Add result row

		if (!node.getId().equals(nodeName)) {
			resultRows.add(row);
		}
	}
	%>

	<form action="<liferay-portlet:actionURL portletConfiguration="true" />" class="uni-form block-labels" id="<portlet:namespace />fm2" method="post" name="<portlet:namespace />fm2">

	<fieldset class="block-labels">
		<div class="ctrl-holder">
			<input class="text-input" id="<portlet:namespace />userId" name="<portlet:namespace />userId" type="text" value="<%= userId %>" />

			<div class="button-holder">
				<input type="submit" value="<bean:message key="search" />">
			</div>
		</div>
	</fieldset>

	</form>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</div>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.alfresco_content.edit_configuration.jsp");
%>