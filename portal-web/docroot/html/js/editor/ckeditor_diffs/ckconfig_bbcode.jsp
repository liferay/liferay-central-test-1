<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portlet.messageboards.util.BBCodeUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HttpUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringUtil" %>

<%
String cssPath = ParamUtil.getString(request, "cssPath");
String cssClasses = ParamUtil.getString(request, "cssClasses");
String emoticonsPath = HttpUtil.decodeURL(ParamUtil.getString(request, "emoticonsPath"));
String imagesPath = ParamUtil.getString(request, "imagesPath");
String jsPath = ParamUtil.getString(request, "jsPath");
String languageId = ParamUtil.getString(request, "languageId");
%>

CKEDITOR.config.height = 265;

CKEDITOR.config.removePlugins = [
	'elementspath',
	'save',
	'bidi',
	'div',
	'flash',
	'forms',
	'indent',
	'keystrokes',
	'link',
	'menu',
	'maximize',
	'newpage',
	'pagebreak',
	'preview',
	'print',
	'save',
	'scayt',
	'showblocks',
	'templates',
	'wsc'
].join(',');

CKEDITOR.config.toolbar_bbcode = [
	['Bold', 'Italic', 'Underline', 'Strike', '-', 'Link', 'Unlink'],
	['Image', 'Smiley', '-', 'TextColor', '-', 'NumberedList', 'BulletedList'],
	['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'Blockquote', '-', 'Code'],
	'/',
	['Font', 'FontSize', '-', 'Format', '-', 'Table', '-', 'Undo', 'Redo', '-', 'Source']
];

CKEDITOR.config.bodyClass = 'html-editor <%= cssClasses %>';

CKEDITOR.config.contentsCss = '<%= jsPath %>/editor/ckeditor/plugins/bbcode/css/main.jsp';

CKEDITOR.config.extraPlugins = 'bbcode,wikilink';

CKEDITOR.config.filebrowserBrowseUrl = '';

CKEDITOR.config.filebrowserImageBrowseLinkUrl = '';

CKEDITOR.config.filebrowserImageBrowseUrl = '';

CKEDITOR.config.filebrowserImageUploadUrl = '';

CKEDITOR.config.filebrowserUploadUrl = '';

CKEDITOR.config.fontSize_sizes = '10/10px;12/12px;16/16px;18/18px;24/24px;32/32px;48/48px';

CKEDITOR.config.format_tags = 'p;pre';

CKEDITOR.config.imagesPath = '<%= imagesPath %>/message_boards/';

CKEDITOR.config.language = '<%= HtmlUtil.escape(languageId) %>';

CKEDITOR.config.smiley_descriptions = ['<%= StringUtil.merge(BBCodeUtil.EMOTICONS_DESCRIPTIONS, "','") %>'];

CKEDITOR.config.smiley_images = ['<%= StringUtil.merge(BBCodeUtil.EMOTICONS_FILES, "','") %>'];

CKEDITOR.config.smiley_path = '<%= emoticonsPath %>' + '/';

CKEDITOR.config.smiley_symbols = ['<%= StringUtil.merge(BBCodeUtil.EMOTICONS_SYMBOLS, "','") %>'];