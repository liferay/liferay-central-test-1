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

<%@ include file="/html/taglib/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

boolean paginate = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:search-iterator:paginate"));

String id = searchContainer.getId();
int start = searchContainer.getStart();
int end = searchContainer.getEnd();
int total = searchContainer.getTotal();
List resultRows = searchContainer.getResultRows();
List<String> headerNames = searchContainer.getHeaderNames();
Map orderableHeaders = searchContainer.getOrderableHeaders();
String emptyResultsMessage = searchContainer.getEmptyResultsMessage();
RowChecker rowChecker = searchContainer.getRowChecker();

if (end > total) {
	end = total;
}

if (rowChecker != null) {
	if (headerNames != null) {
		headerNames.add(0, rowChecker.getAllRowsCheckBox());
	}
}

if (resultRows.isEmpty() && (headerNames == null)) {
	headerNames = new ArrayList<String>();

	headerNames.add(StringPool.BLANK);
}

String url = StringPool.BLANK;

PortletURL iteratorURL = searchContainer.getIteratorURL();

if (iteratorURL != null) {
	url = iteratorURL.toString();
	url = HttpUtil.removeParameter(url, namespace + searchContainer.getOrderByColParam());
	url = HttpUtil.removeParameter(url, namespace + searchContainer.getOrderByTypeParam());
}

List<String> primaryKeys = new ArrayList<String>();
%>

<c:if test="<%= (resultRows.size() > 0) || ((resultRows.size() == 0) && (emptyResultsMessage != null)) %>">
	<c:if test="<%= paginate %>">
		<div class="taglib-search-iterator-page-iterator-top">
			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
		</div>
	</c:if>

	<div class="results-grid"
		<c:if test="<%= Validator.isNotNull(id) %>">
			id="<%= id %>searchContainer"
		</c:if>
	>
		<table class="taglib-search-iterator">
		<tr class="portlet-section-header results-header">

		<%
		for (int i = 0; (headerNames != null) && (i < headerNames.size()); i++) {
			String headerName = headerNames.get(i);

			String orderKey = null;
			String orderByType = null;
			boolean orderCurrentHeader = false;

			if (orderableHeaders != null) {
				orderKey = (String)orderableHeaders.get(headerName);

				if (orderKey != null) {
					orderByType = searchContainer.getOrderByType();

					if (orderKey.equals(searchContainer.getOrderByCol())) {
						orderCurrentHeader = true;
					}
				}
			}

			if (orderCurrentHeader) {
				if (orderByType.equals("asc")) {
					orderByType = "desc";
				}
				else {
					orderByType = "asc";
				}
			}
		%>

			<th class="col-<%= i + 1 %>"

				<%--

				// Maximize the width of the second column if and only if the first
				// column is a row checker and there is only one second column.

				--%>

				<c:if test="<%= (rowChecker != null) && (headerNames.size() == 2) && (i == 1) %>">
					width="95%"
				</c:if>
			>
				<c:if test="<%= orderKey != null %>">
					<a href="<%= url %>&<%= namespace %><%= searchContainer.getOrderByColParam() %>=<%= orderKey %>&<%= namespace %><%= searchContainer.getOrderByTypeParam() %>=<%= orderByType %>">
				</c:if>

				<c:if test="<%= orderCurrentHeader %>">
					<i>
				</c:if>

				<%
				String headerNameValue = LanguageUtil.get(pageContext, headerName);
				%>

				<c:choose>
					<c:when test="<%= Validator.isNull(headerNameValue) %>">
						<%= StringPool.NBSP %>
					</c:when>
					<c:otherwise>
						<%= headerNameValue %>
					</c:otherwise>
				</c:choose>

				<c:if test="<%= orderCurrentHeader %>">
					</i>
				</c:if>

				<c:if test="<%= orderKey != null %>">
					</a>
				</c:if>
			</th>

		<%
		}
		%>

		</tr>
		<tr class="lfr-template portlet-section-body results-row">

			<%
			for (int i = 0; (headerNames != null) && (i < headerNames.size()); i++) {
			%>

				<td></td>

			<%
			}
			%>

		</tr>

		<c:if test="<%= (resultRows.size() == 0) && (emptyResultsMessage != null) %>">
			<tr class="portlet-section-body results-row">
				<td align="center" colspan="<%= headerNames.size() %>">
					<%= LanguageUtil.get(pageContext, emptyResultsMessage) %>
				</td>
			</tr>
		</c:if>

		<%
		boolean allRowsIsChecked = true;

		for (int i = 0; i < resultRows.size(); i++) {
			ResultRow row = (ResultRow)resultRows.get(i);

			String className = "portlet-section-alternate results-row alt";
			String classHoverName = "portlet-section-alternate-hover results-row alt hover";

			primaryKeys.add(row.getPrimaryKey());

			if (MathUtil.isEven(i)) {
				className = "portlet-section-body results-row";
				classHoverName = "portlet-section-body-hover results-row hover";
			}

			if (Validator.isNotNull(row.getClassName())) {
				className += " " + row.getClassName();
			}

			if (Validator.isNotNull(row.getClassHoverName())) {
				classHoverName += " " + row.getClassHoverName();
			}

			if (row.isRestricted()) {
				className += " restricted";
				classHoverName += " restricted";
			}

			row.setClassName(className);
			row.setClassHoverName(classHoverName);

			request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW, row);

			List entries = row.getEntries();

			if (rowChecker != null) {
				boolean rowIsChecked = rowChecker.isChecked(row.getObject());

				if (!rowIsChecked) {
					allRowsIsChecked = false;
				}

				row.addText(0, rowChecker.getAlign(), rowChecker.getValign(), rowChecker.getColspan(), rowChecker.getRowCheckBox(rowIsChecked, row.getPrimaryKey()));
			}
		%>

			<tr class="<%= className %>"
				<c:if test="<%= searchContainer.isHover() %>">
					onmouseover="this.className = '<%= classHoverName %>';" onmouseout="this.className = '<%= className %>';"
				</c:if>
			>

			<%
			for (int j = 0; j < entries.size(); j++) {
				SearchEntry entry = (SearchEntry)entries.get(j);

				entry.setIndex(j);

				request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY, entry);
			%>

				<td align="<%= entry.getAlign() %>" class="col-<%= j + 1 %><%= row.isBold() ? " taglib-search-iterator-highlighted" : "" %>" colspan="<%= entry.getColspan() %>" valign="<%= entry.getValign() %>">

					<%
					entry.print(pageContext);
					%>

				</td>

			<%
			}
			%>

			</tr>

		<%
		}
		%>

		</table>
	</div>

	<c:if test="<%= (resultRows.size() > 10) && paginate %>">
		<div class="taglib-search-iterator-page-iterator-bottom">
			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
		</div>
	</c:if>

	<c:if test="<%= (rowChecker != null) && (resultRows.size() > 0) && Validator.isNotNull(rowChecker.getAllRowsId()) && allRowsIsChecked %>">
		<script type="text/javascript">
			document.<%= rowChecker.getFormName() %>.<%= rowChecker.getAllRowsId() %>.checked = true;
		</script>
	</c:if>
</c:if>

<c:if test="<%= Validator.isNotNull(id) %>">
	<input id="<%= id %>primaryKeys" name="<%= id %>primaryKeys" type="hidden" value="<%= StringUtil.merge(primaryKeys) %>" />

	<script type="text/javascript">
		jQuery(
			function () {
				new Liferay.SearchContainer(
					{
						id: '<%= id %>'
					}
				);
			}
		);
	</script>
</c:if>