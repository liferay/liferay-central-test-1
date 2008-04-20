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

<%@ include file="/html/portlet/xsl_content/init.jsp" %>

<%
try {
	String variablePropertyKey = StringPool.BLANK;
	String variablePropertyValue = StringPool.BLANK;

	int bracketBegin = xmlURL.indexOf("[");
	int bracketEnd = -1;

	if (bracketBegin > -1) {
		bracketEnd = xmlURL.indexOf("]", bracketBegin);

		if (bracketEnd > -1 && ((bracketEnd - bracketBegin) > 0)) {
			String[] compilerEntries = (String[])request.getAttribute(WebKeys.TAGS_COMPILER_ENTRIES);

			if (compilerEntries.length > 0) {
				String category = null;
				String propertyName = null;

				variablePropertyKey = xmlURL.substring(bracketBegin + 1, bracketEnd);

				category = variablePropertyKey;

				int pos = variablePropertyKey.indexOf(StringPool.PERIOD);

				if (pos != -1) {
					category = variablePropertyKey.substring(0, pos);
					propertyName = variablePropertyKey.substring(pos + 1);
				}

				for (String compilerEntry : compilerEntries) {
					try {
						TagsEntry entry = TagsEntryLocalServiceUtil.getEntry(company.getCompanyId(), compilerEntry);

						TagsProperty property = TagsPropertyLocalServiceUtil.getProperty(entry.getEntryId(), "category");

						variablePropertyValue = property.getValue();

						if (category.equals(variablePropertyValue)) {
							if (pos == -1) {
								variablePropertyValue = entry.getName();
							}
							else {
								property = TagsPropertyLocalServiceUtil.getProperty(entry.getEntryId(), propertyName);

								variablePropertyValue = property.getValue();
							}

							xmlURL = StringUtil.replace(xmlURL, "[" + variablePropertyKey + "]", variablePropertyValue.toUpperCase());

							break;
						}
					}
					catch (NoSuchEntryException nsee) {
						_log.warn(nsee);
					}
					catch (NoSuchPropertyException nspe) {
						_log.warn(nspe);
					}
				}
			}
		}
	}

	String content = XSLContentUtil.transform(new URL(xmlURL), new URL(xslURL));
%>

	<%= content %>

<%
}
catch (Exception e) {
	_log.error(e.getMessage());
%>

	<span class="portlet-msg-error">
	<liferay-ui:message key="an-error-occurred-while-processing-your-xml-and-xsl" />
	</span>

<%
}
%>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.xsl_content.view.jsp");
%>