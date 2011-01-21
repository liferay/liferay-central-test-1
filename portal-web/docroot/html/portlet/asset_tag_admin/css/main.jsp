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

<%@ include file="/html/portlet/css_init.jsp" %>

.portlet-asset-tag-admin .tags-admin-actions {
	clear: none;
	float: right;
	margin: 0;
}

.portlet-asset-tag-admin .tags-admin-container .results-header {
	background: #F0F5F7;
	font-weight: bold;
	margin: 2px 0;
	padding: 5px 10px;
	position: relative;
}

.portlet-asset-tag-admin .tags-admin-toolbar {
	background: #F6F8FB;
	border-bottom: 1px solid #dedede;
	overflow: hidden;
	padding: 5px 0;
}

.portlet-asset-tag-admin .tags-admin-search .aui-field-content {
	display: inline-block;
}

.portlet-asset-tag-admin .tags-admin-search .aui-field-input {
	background-image: url(<%= themeImagesPath %>/common/search.png);
	background-repeat: no-repeat;
	background-position: 5px 50%;
	padding-left: 25px;
	width: 250px;
}


.portlet-asset-tag-admin .tags-admin-content-wrapper {
	position: relative;
}

.ie6 .portlet-asset-tag-admin .tags-admin-content-wrapper, .ie7 .portlet-asset-tag-admin .tags-admin-content-wrapper {
	width: 100%;
}

.portlet-asset-tag-admin .tags-admin-content-wrapper, .portlet-asset-tag-admin .header-title, .portlet-asset-tag-admin .aui-tree-node-content .aui-tree-label  {
	word-wrap: break-word;
}

.portlet-asset-tag-admin .tags-admin-edit-tag .tag-view-container {
	padding: 0 0.5em 0 0.5em;
}

.portlet-asset-tag-admin .tags-admin-edit-tag .results-header {
	background: #AEB9BE;
}

.portlet-asset-tag-admin .tag-item a {
	display: block;
	padding: 8px 20px 8px 10px;
	text-decoration: none;
}

.ie .portlet-asset-tag-admin .tag-item a {
	zoom: 1;
}

.portlet-asset-tag-admin .tag-item-actions-trigger {
	background-image: url(<%= themeImagesPath %>/common/edit.png);
	background-repeat: no-repeat;
	clip: rect(0pt, 0pt, 0pt, 0pt);
	display: block;
	height: 16px;
	margin-top: -8px;
	padding: 0;
	position: absolute;
	right: 0;
	top: 50%;
	width: 16px;
}

.portlet-asset-tag-admin .tags-admin-content li.tag-item-container {
	padding: 1px 0;
}

.portlet-asset-tag-admin .tags-admin-content li.tag-item-container, .portlet-asset-tag-admin li.tag-item {
	font-weight: bold;
	list-style: none;
}

.portlet-asset-tag-admin .tag-item-container:hover .tag-item-actions-trigger, .portlet-asset-tag-admin .tag-item-container:focus .tag-item-actions-trigger, .portlet-asset-tag-admin .tag-item-container .tag-item-actions-trigger:focus {
	clip: auto;
}

.ie6 .portlet-asset-tag-admin .tag-item-actions-trigger, .ie7 .portlet-asset-tag-admin .tag-item-actions-trigger {
	cursor: pointer;
}

.portlet-asset-tag-admin .tags-admin-list li {
	border: 1px solid transparent;
}

.portlet-asset-tag-admin .tags-admin-list .selected a:hover {
	color: #FFB683;
}

.portlet-asset-tag-admin .tags-admin-list .selected a, .portlet-asset-tag-admin .tags-admin-list .selected .tags-admin-content-wrapper, .portlet-asset-tag-admin .tags-admin-list .selected .tags-admin-content-wrapper:hover {
	background-color: #6F7D83;
	color: #FFF;
}

.portlet-asset-tag-admin .tags-admin-list .active-area {
	border: 1px solid #008000;
}

.portlet-asset-tag-admin .tags-admin-list .active-area a {
	background-color: #90EE90;
	color: #90EE90;
}

.portlet-asset-tag-admin .tags-admin-list .tags-admin-content-wrapper {
	background-color: #F5F5F5;
}

.portlet-asset-tag-admin .tags-admin-list .tags-admin-content-wrapper:hover {
	background: #D3DADD;
}


.portlet-asset-tag-admin .tags-admin-list-container .results-header {
	background: #D3DADD;
}

.portlet-asset-tag-admin .tags-admin-search-bar {
	float: left;
}

.portlet-asset-tag-admin-dialog .aui-fieldset {
	margin-bottom: 0;
}

.portlet-asset-tag-admin-dialog .lfr-panel-container {
	background-color: transparent;
	border-width: 0;
}

.portlet-asset-tag-admin-dialog .asset-tag-layer .aui-overlay {
	overflow: visible;
	width: 230px;
}

.portlet-asset-tag-admin-dialog .aui-widget-bd .asset-tag-layer {
	padding: 0 10px;
}

.portlet-asset-tag-admin-dialog .aui-widget-bd .asset-tag-layer .aui-field-content {
	margin-bottom: 10px;
}

.portlet-asset-tag-admin-dialog .aui-widget-bd .asset-tag-layer label {
	display: block;
	font-weight: bold;
}

.portlet-asset-tag-admin-dialog .aui-widget-bd .asset-tag-layer .aui-field input, .portlet-asset-tag-admin-dialog .aui-widget-bd .asset-tag-layer .aui-field select {
	width: 200px;
}

.portlet-asset-tag-admin-dialog .aui-widget-bd .asset-tag-layer .button-holder {
	margin-top: 10px;
}

.ie6 .portlet-asset-tag-admin-dialog .aui-widget-bd .asset-tag-layer .aui-field, .ie6 .portlet-asset-tag-admin-dialog .aui-widget-bd .asset-tag-layer .aui-field {
	width: 200px;
}

.portlet-asset-tag-admin .view-tag {
	margin: 1em;
}

.portlet-asset-tag-admin .view-tag label {
	display: block;
	font-weight: bold;
}

.portlet-asset-tag-admin .view-tag .tag-field {
	clear: left;
	margin: 1em auto;
}

.tag-item-merge {
	border: 1px solid #4E4848;
	display: block;
	font-weight: bold;
	list-style: none;
	padding: 8px 20px 8px 10px;
}

.ie6 .tag-item-merge, .ie7 .tag-item-merge, .ie8 .tag-item-merge {
	font-weight: normal;
	font-size: 120%;
}

.tag-item-merge a {
	text-decoration: none;
}

.lfr-position-helper {
	z-index: 10000;
}