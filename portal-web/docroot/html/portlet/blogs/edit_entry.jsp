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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

long entryId = BeanParamUtil.getLong(entry, request, "entryId");

String content = BeanParamUtil.getString(entry, request, "content");

Calendar displayDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

if (entry != null) {
	if (entry.getDisplayDate() != null) {
		displayDate.setTime(entry.getDisplayDate());
	}
}

boolean allowTrackbacks = BeanParamUtil.getBoolean(entry, request, "allowTrackbacks", true);
%>

<script type="text/javascript">
	var <portlet:namespace />saveDraftIntervalId = null;
	var <portlet:namespace />oldTitle = null;
	var <portlet:namespace />oldContent = null;

	function <portlet:namespace />clearSaveDraftIntervalId() {
		if (<portlet:namespace />saveDraftIntervalId != null) {
			clearInterval(<portlet:namespace />saveDraftIntervalId);
		}
	}

	function <portlet:namespace />getSuggestionsContent() {
		var content = '';

		content += document.<portlet:namespace />fm.<portlet:namespace/>title.value + ' ';
		content += window.<portlet:namespace />editor.getHTML();

		return content;
	}

	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(content) %>";
	}

	function <portlet:namespace />saveEntry(draft) {
		var title = document.<portlet:namespace />fm.<portlet:namespace />title.value;
		var content = window.<portlet:namespace />editor.getHTML();

		var saveButton = jQuery('#<portlet:namespace />saveButton');
		var cancelButton = jQuery('#<portlet:namespace />cancelButton');

		var saveStatus = jQuery('#<portlet:namespace />saveStatus');
		var saveText = '<%= UnicodeLanguageUtil.format(pageContext, "draft-saved-at-x", "[TIME]", false) %>';

		if (draft) {
			if ((title == '') || (content == '')) {
				return;
			}

			if ((<portlet:namespace />oldTitle == title) &&
				(<portlet:namespace />oldContent == content)) {

				return;
			}

			<portlet:namespace />oldTitle = title;
			<portlet:namespace />oldContent = content;

			var url = document.<portlet:namespace />fm.action;

			jQuery.ajax(
				{
					url: url,
					data: {
						<portlet:namespace /><%= Constants.CMD %>: '<%= Constants.ADD %>',
						<portlet:namespace />redirect: document.<portlet:namespace />fm.<portlet:namespace />redirect.value,
						<portlet:namespace />referringPortletResource: document.<portlet:namespace />fm.<portlet:namespace />referringPortletResource.value,
						<portlet:namespace />entryId: document.<portlet:namespace />fm.<portlet:namespace />entryId.value,
						<portlet:namespace />title: title,
						<portlet:namespace />content: content,
						<portlet:namespace />displayDateMonth: document.<portlet:namespace />fm.<portlet:namespace />displayDateMonth.value,
						<portlet:namespace />displayDateDay: document.<portlet:namespace />fm.<portlet:namespace />displayDateDay.value,
						<portlet:namespace />displayDateYear: document.<portlet:namespace />fm.<portlet:namespace />displayDateYear.value,
						<portlet:namespace />displayDateHour: document.<portlet:namespace />fm.<portlet:namespace />displayDateHour.value,
						<portlet:namespace />displayDateMinute: document.<portlet:namespace />fm.<portlet:namespace />displayDateMinute.value,
						<portlet:namespace />displayDateAmPm: document.<portlet:namespace />fm.<portlet:namespace />displayDateAmPm.value,
						<portlet:namespace />draft: 1,
						<portlet:namespace />tagsEntries: document.<portlet:namespace />fm.<portlet:namespace />tagsEntries.value
					},
					dataType: 'json',
					beforeSend: function() {
						saveButton.attr('disabled', true);

						saveStatus.attr('class', 'save-status portlet-msg-info pending');
						saveStatus.html('<%= UnicodeLanguageUtil.get(pageContext, "saving-draft") %>');
					},
					success: function(message) {
						document.<portlet:namespace />fm.<portlet:namespace />entryId.value = message.entryId;
						document.<portlet:namespace />fm.<portlet:namespace />redirect.value = message.redirect;

						saveButton.attr('disabled', false);

						var tabs1BackButton = jQuery('#<portlet:namespace />tabs1TabsBack');

						tabs1BackButton.attr('href', message.redirect);

						cancelButton.unbind('click.liferay');

						cancelButton.bind(
							'click.liferay',
							function() {
								location.href = message.redirect;
							}
						);

						var now = saveText.replace(/\[TIME\]/gim, (new Date()).toString());

						saveStatus.attr('class', 'save-status portlet-msg-success');
						saveStatus.html(now);
					},
					error: function() {
						saveStatus.attr('class', 'save-status portlet-msg-error');
						saveStatus.html('<%= UnicodeLanguageUtil.get(pageContext, "could-not-save-draft-to-the-server") %>');
					}
				}
			);
		}
		else {
			<portlet:namespace />clearSaveDraftIntervalId();

			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= entry == null ? Constants.ADD : Constants.UPDATE %>";
			document.<portlet:namespace />fm.<portlet:namespace />content.value = content;
			document.<portlet:namespace />fm.<portlet:namespace />draft.value = 0;
			submitForm(document.<portlet:namespace />fm);
		}
	}

	jQuery(
		function() {
			jQuery('#<portlet:namespace />cancelButton').click(
				function() {
					<portlet:namespace />clearSaveDraftIntervalId();

					location.href = '<%= HtmlUtil.escape(redirect) %>';
				}
			);

			<c:if test="<%= (entry == null) || entry.isDraft() %>">
				<portlet:namespace />saveDraftIntervalId = setInterval('<portlet:namespace />saveEntry(true)', 30000);
				<portlet:namespace />oldTitle = document.<portlet:namespace />fm.<portlet:namespace />title.value;
				<portlet:namespace />oldContent = <portlet:namespace />initEditor();
			</c:if>
		}
	);
</script>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/blogs/edit_entry" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveEntry(false); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
<input name="<portlet:namespace />referringPortletResource" type="hidden" value="<%= HtmlUtil.escape(referringPortletResource) %>" />
<input name="<portlet:namespace />entryId" type="hidden" value="<%= entryId %>" />
<input name="<portlet:namespace />draft" type="hidden" value="0" />

<liferay-ui:error exception="<%= EntryTitleException.class %>" message="please-enter-a-valid-title" />
<liferay-ui:tags-error />

<div class="breadcrumbs">
	<span class="first last"><a href="<portlet:renderURL />"><liferay-ui:message key="entries" /></a></span> &raquo;

	<span class="current-action"><liferay-ui:message key='<%= ((entry == null) ? Constants.ADD : Constants.UPDATE) + "-entry" %>' /></span>
</div>

<table class="lfr-table">

<c:if test="<%= (entry == null) || entry.isDraft() %>">
	<tr>
		<td colspan="2">
			<div class="save-status" id="<portlet:namespace />saveStatus"></div>
		</td>
	</tr>
</c:if>

<tr>
	<td class="lfr-label">
		<liferay-ui:message key="title" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= BlogsEntry.class %>" bean="<%= entry %>" field="title" />
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="display-date" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= BlogsEntry.class %>" bean="<%= entry %>" field="displayDate" defaultValue="<%= displayDate %>" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="content" />
	</td>
	<td>
		<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

		<input name="<portlet:namespace />content" type="hidden" value="" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="allow-incoming-trackbacks" />
	</td>
	<td>
		<liferay-ui:input-checkbox param="allowTrackbacks" defaultValue="<%= allowTrackbacks %>" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="trackbacks-to-send" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= BlogsEntry.class %>" bean="<%= entry %>" field="trackbacks" />
	</td>
</tr>

<c:if test="<%= (entry != null) && Validator.isNotNull(entry.getTrackbacks()) %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="trackbacks-already-sent" />
		</td>
		<td>

			<%
			for (String trackback : StringUtil.split(entry.getTrackbacks())) {
			%>

				<%= trackback %><br />

			<%
			}
			%>

		</td>
	</tr>
</c:if>

<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="tags" />
	</td>
	<td>

		<%
		long classPK = 0;

		if (entry != null) {
			classPK = entry.getEntryId();
		}
		%>

		<liferay-ui:tags-selector
			className="<%= BlogsEntry.class.getName() %>"
			classPK="<%= classPK %>"
			hiddenInput="tagsEntries"
			contentCallback='<%= renderResponse.getNamespace() + "getSuggestionsContent" %>'
		/>
	</td>
</tr>

<%--<c:if test="<%= entry == null %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td class="lfr-label">
			<liferay-ui:message key="permissions" />
		</td>
		<td>
			<liferay-ui:input-permissions
				modelName="<%= BlogsEntry.class.getName() %>"
			/>
		</td>
	</tr>
</c:if>--%>

</table>

<br />

<input id="<portlet:namespace />saveButton" type="submit" value="<liferay-ui:message key='<%= ((entry == null) || entry.isDraft()) ? "publish" : "save" %>' />" />

<input id="<portlet:namespace />cancelButton" type="button" value="<liferay-ui:message key="cancel" />" />

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />title);
	</script>
</c:if>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.blogs.edit_entry.jsp";
%>