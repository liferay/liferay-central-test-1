<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/taglib/ddm/html/init.jsp" %>

<div class="lfr-ddm-container" id="<portlet:namespace /><%= containerId %>">
	<c:if test="<%= Validator.isNotNull(xsd) %>">
		<%= DDMXSDUtil.getHTML(pageContext, xsd, fields, fieldsNamespace, readOnly, locale) %>

		<c:if test="<%= repeatable %>">
			<aui:input id='<%= containerId + "repeatabaleFieldsMap" %>' name="__repeatabaleFieldsMap" type="hidden" />

			<aui:script use="liferay-ddm-repeatable-fields">
				new Liferay.DDM.RepeatableFields(
					{
						classNameId: <%= classNameId %>,
						classPK: <%= classPK %>,
						container: '#<portlet:namespace /><%= containerId %>',
						fieldsMapInput: '#<portlet:namespace /><%= containerId %>repeatabaleFieldsMap',
						namespace: '<portlet:namespace />'
					}
				);
			</aui:script>
		</c:if>
	</c:if>