<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/css_init.jsp" %>

<%@ page import="com.liferay.portal.model.ModelHintsConstants" %>

<%@ include file="/html/portal/alloy/base.css.jsp" %>
<%@ include file="/html/portal/alloy/form.css.jsp" %>

/* ---------- Modules ---------- */

/* ---------- Generic module styling ---------- */

.lfr-grid {
	width: 100%;
}

.lfr-component, .lfr-component ul, .lfr-component li, .lfr-component dl, .lfr-component dt, .lfr-component dd {
	margin: 0;
	padding: 0;
	list-style: none;
}

.lfr-component li img, img.icon {
	vertical-align: middle;
}

#layout-grid.dragging .lfr-portlet-column.empty {
	padding: 20px;
}

.lfr-js-required {
	position: absolute;
	left: -9999em;
	top: -9999em;
}

.js .lfr-js-required {
	position: static;
}

/* ---------- Add content styles ---------- */

.portal-add-content-search {
	margin-bottom: 8px;
}

.portal-add-content .lfr-portlet-used {
	color: #ccc;
	cursor: default;
}

.portal-add-content .portlet-msg-info {
	color: #333;
	margin-bottom: 0;
	margin-top: 10px;
}

.portal-add-content .lfr-portlet-used a {
	display: none;
}

.lfr-add-content {
	margin-bottom: 0.5em;
}

.lfr-add-content.collapsed {
}

.lfr-add-content.expanded {
}

.lfr-add-content h2 {
	cursor: pointer;
	font-size: 1.1em;
	font-weight: bold;
	margin: 0;
}

.lfr-add-content.collapsed h2, .lfr-add-content .lfr-add-content.collapsed h2 {
	background: url(<%= themeImagesPath %>/arrows/01_plus.png) no-repeat 100% 50%;
	border: none;
}

.lfr-add-content.expanded h2, .lfr-add-content .lfr-add-content.expanded h2 {
	background: url(<%= themeImagesPath %>/arrows/01_minus.png) no-repeat 100% 50%;
}

.lfr-add-content h2 span {
	background: url(<%= themeImagesPath %>/add_content/portlet_category.png) no-repeat 0 50%;
	padding-left: 20px;
}

.lfr-install-more {
	border-top: 1px solid #ccc;
	margin: 10px 0 0;
	padding-top: 10px;
}

.lfr-install-more a {
	background: url(<%= themeImagesPath %>/common/install_more.png) no-repeat 0 50%;
	display: block;
	font-weight: bold;
	padding-left: 20px;
}

.lfr-content-category {
	padding-left: 10px;
	padding-top: 3px;
}

.lfr-content-category.hidden {
	display: none;
}

.lfr-content-category.visible {
	border-bottom: 1px solid #ddd;
	border-top: 1px solid #ddd;
	display: block;
}

.ie6 .lfr-content-category.visible {
	height: 1%;
}

.lfr-has-sidebar {
	padding-left: 270px;
}

/* ---------- Flyout ---------- */

.lfr-flyout ul {
	display: none;
}

.js li.lfr-flyout {
	display: block;
	position: relative;
}

.js .lfr-flyout li ul {
	display: none;
}

.js .lfr-flyout.has-children {
	background: url(<%= themeImagesPath %>/arrows/04_left.png) no-repeat 5px 50%;
	padding-left: 12px;
}

.js .lfr-flyout.has-children.send-right {
	background-image: url(<%= themeImagesPath %>/arrows/04_right.png);
}

.js .lfr-flyout.has-children ul {
	display: none;
	min-width: 150px;
	position: absolute;
	right: 100%;
	top: -1px;
}

.js .lfr-flyout.has-children.send-right ul {
	left: 100%;
	right: auto;
}

/* ---------- Panel component ---------- */

.lfr-panel.lfr-extended, .lfr-panel-container, .lfr-floating-container {
	border: 1px solid;
	border-color: #DEDEDE #BFBFBF #BFBFBF #DEDEDE;
}

.lfr-panel-container, .lfr-floating-container {
	background: #fff;
	clear: both;
}

.lfr-floating-container {
	position: relative;
}

.lfr-panel-container .lfr-panel {
	border: none;
}

.lfr-panel .lfr-panel-titlebar {
	margin-bottom: 0.5em;
	overflow: hidden;
}

.lfr-panel.lfr-collapsible .lfr-panel-titlebar {
	background: url(<%= themeImagesPath %>/arrows/05_down.png) no-repeat 2px 50%;
	cursor: pointer;
	padding-left: 15px;
}

.lfr-panel-titlebar .lfr-panel-title {
	float: left;
	font-size: 1.2em;
	font-weight: bold;
	margin: 0;
}

.lfr-panel-titlebar .lfr-panel-button {
	background: url(<%= themeImagesPath %>/application/panel_header_toggler_close.png) no-repeat 0 0;
	display: none;
	float: right;
	height: 22px;
	width: 19px;
}

.lfr-extended.lfr-collapsible .lfr-panel-button {
	display: block;
}

.lfr-panel.lfr-collapsed .lfr-panel-titlebar .lfr-panel-button {
	background-image: url(<%= themeImagesPath %>/application/panel_header_toggler_open.png);
}

.lfr-panel-titlebar .lfr-panel-button:hover {
	background-position: 0 100%;
}

.lfr-panel.lfr-collapsed .lfr-panel-titlebar {
	background-image: url(<%= themeImagesPath %>/arrows/05_right.png);
	margin-bottom: 0;
}

.lfr-panel.lfr-extended .lfr-panel-titlebar {
	background: #d6d6d6 url(<%= themeImagesPath %>/application/panel_header.png) repeat-x 0 0;
	border-bottom: 1px solid #cdcdcd;
	line-height: 1.6;
	padding: 2px;
}

.ie6 .lfr-extended.lfr-collapsible .lfr-panel-titlebar {
	height: 1%;
}

.lfr-extended.lfr-collapsed .lfr-panel-titlebar {
	border-bottom: none;
}

.lfr-panel-container .lfr-extended.lfr-collapsible .lfr-panel-titlebar {
	border-top: 1px solid #cecece;
}

.lfr-panel.lfr-collapsed .lfr-panel-content {
	display: none;
}

.js .lfr-floating-container {
	display: none;
	position: absolute;
}

.js .lfr-floating-trigger {
	background-image: url(<%= themeImagesPath %>/arrows/05_down.png);
	background-position: 100% 50%;
	background-repeat: no-repeat;
	padding: 3px;
	padding-right: 15px;
	text-decoration: none;
}

.js .lfr-trigger-selected {
	background-color: #069;
	color: #fff;
}

.lfr-floating-container .col {
	float: left;
	margin-right: 10px;
	width: auto;
}

.lfr-floating-container .lfr-form-row {
	border: none;
}

.lfr-panel .lfr-panel-content .undo-queue {
	border-left: none;
	border-right: none;
	border-top: none;
	margin: -0.5em 0 10px;
}

/* ---------- Panel pagination ---------- */

.js .lfr-panel-paging .lfr-panel-content ul {
	display: none;
}

.lfr-paging-container {
	background: #cfd2d5;
	border-bottom: 1px solid #dcdee0;
	border-top: 1px solid #dcdee0;
	text-align: center;
}

.lfr-panel-content .lfr-paging-container li {
	display: inline;
}

.ie .lfr-panel-content .lfr-paging-container li {
	zoom: 1;
}

.js .lfr-panel-paging .lfr-panel-content .current-set, .js .lfr-panel-paging .lfr-paging-container ul {
	display: block;
	position: relative;
	visibility: visible;
}

.js .lfr-panel-paging .lfr-paging-container ul {
	padding-top: 1px;
}

.lfr-panel-content .lfr-paging-container li a {
	background: none;
	border: 1px solid #CFD2D5;
	display: inline-block;
	float: none;
	padding: 5px 10px;
	text-align: center;
	width: auto;
}

.lfr-panel-content .lfr-paging-container li a:hover {
	background: #5094d7 url(<%= themeImagesPath %>/application/current_page_bg.png) repeat-x 0 0;
	border-color: #31659c #396ea8 #4c8ccb;
	color: #fff;
	font-weight: bold;
}

.lfr-panel-content .lfr-paging-container li.lfr-page-current a {
	background: #99a7b3 url(<%= themeImagesPath %>/application/current_page_hover_bg.png) repeat-x 0 0;
	border-color: #6b7785 #7c8994 #919fa9;
	color: #fff;
	font-weight: bold;
}

.lfr-panel-content .lfr-paging-container {
	overflow: hidden;
}

.ie6 .lfr-panel-content .lfr-paging-container {
	height: 1%;
}

/* ---------- Position helper ---------- */

.lfr-position-helper {
	position: absolute;
	z-index: 1000;
}


/* ---------- Auto row styles ---------- */

.lfr-form-row {
	border-bottom: 1px solid #CCC;
	margin-bottom: 10px;
	margin-top: 10px;
	overflow: hidden;
	padding: 5px;
	padding-top: 1px;
	position: relative;
}

.ie .lfr-form-row {
	width: 100%;
}

.lfr-form-row:hover {
	background-color: #DFFCCB;
	border: solid #B2FF3A;
	border-width: 1px 0;
	padding-top: 0;
}

.lfr-form-row .aui-ctrl-holder {
	clear: none;
	float: left;
}

.lfr-form-row .row-controls {
	bottom: 5px;
	float: right;
	position: absolute;
	right: 5px;
}

.lfr-form-row .row-controls a {
	background: url() no-repeat 2px 50%;
	display: block;
	float: left;
	font-size: 0;
	height: 16px;
	padding: 2px;
	text-indent: -9999em;
	width: 16px;
}

.lfr-form-row .row-controls .add-row {
	background-image: url(<%= themeImagesPath %>/common/add.png);
}

.lfr-form-row .row-controls .delete-row {
	background-image: url(<%= themeImagesPath %>/common/delete.png);
}

.lfr-form-row legend .field-label{
	float:left;
	margin-right: 10px;
}

.lfr-form-row .lfr-arrow-controls a {
	background: url() no-repeat 2px 50%;
	clear: none;
	display: block;
	float: left;
	font-size: 0;
	height: 16px;
	padding: 2px;
	text-indent: -9999em;
	width: 16px;
}

.lfr-form-row .handle-sort-vertical {
	background: url(<%= themeImagesPath %>/application/handle_sort_vertical.png) no-repeat 0 50%;
	cursor: move;
	padding-left: 20px;
}

.lfr-form-row fieldset {
	border: none;
	margin: 0;
	padding: 0;
}

/* ---------- Undo manager ---------- */

.undo-queue.queue-empty {
	display: none;
}

.ie6 .undo-queue.queue-empty {
	display: none;
}

.undo-queue .undo-action {
	float: left;
}

.undo-queue .clear-undos {
	float: right;
}

.undo-queue.queue-single .clear-undos {
	display: none;
}

.portlet-msg-info.undo-queue {
	overflow: hidden;
	margin: 10px auto;
}

.ie6 .portlet-msg-info.undo-queue {
	zoom: 1;
	overflow: visible;
}

/* ---------- Panel Page styles ---------- */

.lfr-panel-page .portal-add-content {
	padding: 0;
	padding-left: 4px;
}

.lfr-panel-page .panel-page-content {
	border-left: 1px solid #ccc;
	padding-left: 1em;
}

.lfr-panel-page .lfr-add-content h2 {
	border: 1px solid #ccc;
	border-right: none;
	padding: 1px;
}

.lfr-panel-page .lfr-add-content h2 span {
	background: #efefef;
	display: block;
	padding: 2px;
	padding-left: 5px;
}

.lfr-panel-page .lfr-add-content .lfr-content-category h2 {
	border: none;
	border-bottom: 1px solid #ccc;
}

.lfr-panel-page .lfr-add-content .lfr-content-category h2 span {
	background: none;
}

.lfr-panel-page.panel-page-frontpage .panel-page-content h2 {
	margin-top: 0;
}

/* ---------- Javascript template styles ---------- */

.lfr-template {
	display: none;
}

/* ---------- Portlet item ---------- */

.lfr-portlet-item {
	background: url(<%= themeImagesPath %>/add_content/portlet_item.png) no-repeat 0 50%;
	border: 1px solid #fff;
	cursor: move;
	font-size: 1.1em;
	margin-bottom: 3px;
	padding: 0 5px 0 20px;
}

.lfr-portlet-item.lfr-instanceable {
	background-image: url(<%= themeImagesPath %>/add_content/portlet_item_instanceable.png);
}

.lfr-portlet-item:hover, .lfr-portlet-item.over {
	background-color: #ffc;
	border-color: #fc0;
}

.ie .lfr-portlet-item {
	height: 1%;
}

.lfr-portlet-item p {
	font-size: 1em;
	margin: 0;
	padding-right: 30px;
	position: relative;
}

.lfr-portlet-item p a {
	cursor: pointer;
	font-size: 0.9em;
	font-weight: bold;
	position: absolute;
	right: 0;
	top: 0;
}

.ie .lfr-portlet-item p a {
	top: -2px;
}

.ie6 .lfr-portlet-item p a {
	right: 20px;
}

#layout_configuration_content {
	width: 95%;
}

/* ---------- Portlet css editor ---------- */

#portlet-set-properties {
	display: none;
}

#portlet-set-properties .aui-form {
	clear: both;
}

#portlet-set-properties fieldset {
	margin-bottom: 1em;
}

#portlet-set-properties fieldset fieldset {
	margin-top: 1em;
}

#portlet-set-properties fieldset fieldset legend {
	font-size: 1.1em;
}

#portlet-set-properties .aui-form .text-input {
	margin-right: 5px;
	width: 80px;
}

#portlet-set-properties .aui-form fieldset.col {
	width: 29%;
}

#portlet-set-properties .common {
	width: 27%;
}

#portlet-set-properties .extra {
	width: 20%;
}

#portlet-set-properties #lfr-border-width, #portlet-set-properties #lfr-border-style, #portlet-set-properties #lfr-border-color {
	float: left;
}

#portlet-set-properties #lfr-border-width {
	width: 25%;
}

#portlet-set-properties #lfr-border-style {
	width: 15%;
}

#portlet-set-properties #lfr-border-color {
	width: 20%;
}

#portlet-set-properties #lfr-padding, #portlet-set-properties #lfr-margin {
	width: 25%;
}

#portlet-set-properties .aui-form #custom-css {
	height: 300px;
	width: 400px;
}

#portlet-set-properties .form-hint {
	float: none;
}

#portlet-set-properties .lfr-bg-image-properties {
	display: none;
}

#portlet-set-properties #border-note {
	display: none;
	margin-top: 10px;
}

#portlet-set-properties .aui-form {
	clear: both;
}

/* ---------- Toolbar ---------- */

.lfr-toolbar {
	background-color: #e5e4e8;
	padding-top: 3px;
}

.lfr-toolbar:after {
	clear: both;
	content: ".";
	display: block;
	height: 0;
	visibility: hidden;
}

.ie .lfr-toolbar {
	height: 1%;
}

.ie6 .lfr-toolbar {
	width: 750px;
}

.lfr-toolbar .lfr-button, .lfr-emoticon-container .lfr-button {
	border: 1px solid #e5e4e8;
	cursor: pointer;
	margin: 0 2px;
	padding: 2px 4px;
}

.lfr-toolbar .lfr-button img {
	vertical-align: text-top;
}

.lfr-toolbar .lfr-button:focus {
	border: 1px solid #000;
}

.lfr-toolbar .lfr-button:hover {
	border-bottom-color: #777;
	border-right-color: #777;
	border: 1px solid #ccc;
}

.lfr-toolbar .lfr-separator {
	border-left: 1px solid #fff;
	border-right: 1px solid #ccc;
	font-size: 12px;
	height: 25px;
	margin: 0 5px;
}

.lfr-toolbar select, .lfr-toolbar .lfr-button, .lfr-toolbar .lfr-separator, .lfr-emoticon-container .lfr-button {
	float: left;
}

.lfr-toolbar select {
	margin: 0 5px;
}

.ie .lfr-toolbar select {
	margin-top: 3px;
}

.lfr-emoticon-container {
	background: #e5e4e8;
	border: 1px solid #ccc;
	display: none;
	padding-top: 5px;
	position: absolute;
	width: 180px;
}

.lfr-emoticon-container .lfr-button {
	margin: 0;
}

/* ---------- Tree ---------- */

.lfr-tree {
}

.lfr-tree .expand-image {
}

.lfr-tree a {
	text-decoration: none;
}

.lfr-tree li {
	margin-bottom: 2px;
	padding-left: 0;
}

.lfr-tree li ul {
}

.lfr-tree li ul li, .lfr-tree li.tree-item {
	padding-left: 0;
}

.lfr-tree img {
	vertical-align: middle;
}

.lfr-tree li.tree-item {
	padding-left: 5px;
}

.lfr-tree li.tree-item a img {
	cursor: move;
}

.lfr-tree li.tree-item li {
	padding-left: 20px;
}

.lfr-tree li.tree-item ul {
	margin-left: 0;
	margin-top: 5px;
}

.lfr-tree li.tree-item a, .lfr-tree li.tree-item .expand-image {
	cursor: pointer;
}

.lfr-tree .tree-item-hover {
	background: #7D93C1;
	padding: 5px;
}

li.toggle-expand {
	padding-bottom: 10px;
}

li.toggle-expand a {
	padding: 2px 0 2px 20px;
}

.lfr-expand {
	background: url(<%= themeImagesPath %>/trees/expand_all.png) no-repeat 0 50%;
}

.lfr-collapse {
	background: url(<%= themeImagesPath %>/trees/collapse_all.png) no-repeat 0 50%;
}

/* ---------- Uploader ---------- */

.lfr-upload-container {
	margin-bottom: 1em;
	width: 450px;
}

.lfr-upload-container .upload-target a {
	float: left;
	margin-right: 15px;
}

.lfr-upload-container a.browse-button {
	background-image: url(<%= themeImagesPath %>/dock/add_content.png);
	background-repeat: no-repeat;
}

.lfr-upload-container a.upload-button {
	background-image: url(<%= themeImagesPath %>/common/top.png);
	background-repeat: no-repeat;
}

.lfr-upload-container a.clear-uploads {
	background-image: url(<%= themeImagesPath %>/portlet/refresh.png);
	background-repeat: no-repeat;
}

.lfr-upload-container a.cancel-uploads {
	background-image: url(<%= themeImagesPath %>/common/close.png);
	background-repeat: no-repeat;
	float: right;
	margin-right: 0;
}

.lfr-upload-container .upload-file {
	background: #f0faf0 url(<%= themeImagesPath %>/document_library/jpg.png) no-repeat 5px 50%;
	border-bottom: 1px solid #ccc;
	display: block;
	font-weight: bold;
	margin-bottom: 1px;
	padding: 5px;
	padding-left: 25px;
	position: relative;
}

.lfr-upload-container .upload-list-info {
	margin: 5px 0;
}

.lfr-upload-container .upload-list-info h4 {
	font-size: 1.3em;
}

.lfr-upload-container .cancel-button {
	background-image: url(<%= themeImagesPath %>/common/delete.png);
	background-repeat: no-repeat;
	position: absolute;
	right: 5px;
}

.lfr-upload-container .upload-complete {
	background-color: #E8EEF7;
	background-image: url(<%= themeImagesPath %>/dock/my_place_current.png);
	font-weight: normal;
	opacity: 0.6;
}

.lfr-upload-container .upload-error {
	background-color: #FDD;
	background-image: url(<%= themeImagesPath %>/messages/error.png);
	font-weight: normal;
	opacity: 0.6;
}

.lfr-upload-container .upload-error .error-message {
	position: absolute;
	right: 5px;
}

.lfr-upload-container .upload-complete .cancel-button {
	display: none;
}

.lfr-upload-container .file-uploading {
	background-color: #ffc;
}

.lfr-upload-container .file-uploading .cancel-button {
	top: 0;
}

.lfr-upload-container .progress-bar {
	background: #fff;
	border: 1px solid #83a8d9;
	display: none;
	height: 15px;
}

.lfr-upload-container .progress {
	background: #8db2e3 url(<%= themeImagesPath %>/progress_bar/complete_gradient.png) repeat-y 100% 0;
	display: block;
	height: 15px;
	width: 0;
}

.lfr-upload-container .file-uploading .progress-bar {
	display: block;
}

/* ---------- Portal ---------- */

/* ---------- Page settings styling ---------- */

.page-extra-settings .lfr-panel-titlebar .lfr-panel-title {
	font-size: 1em;
	padding: 0.5em 0;
}

.page-extra-settings .lfr-extended .lfr-panel-titlebar {
	line-height: 1;
}

.page-extra-settings .lfr-extended .lfr-panel-titlebar {
	line-height: 1;
}

.page-extra-settings .lfr-extended .lfr-panel-content {
	padding: 5px;
}

/* ---------- Portal notifications styling ---------- */

.popup-alert-notice .notice-date {
	margin: 0 10px;
}

.popup-alert-notice .current-user-language {
	border: 1px solid #ccc;
	border-bottom: none;
	border-top: none;
	display: inline;
	margin: 0 10px;
	padding: 0 15px;
}

/* ---------- Asset categories selector styling ---------- */

.ui-dialog.lfr-asset-category-selector .ui-dialog-container .ui-dialog-content {
	padding: 0;
}

.lfr-asset-category-container {
	height: 260px;
	margin-bottom: 0.5em;
	overflow: auto;
}

.lfr-asset-category-container fieldset {
	margin: 5px;
}

.lfr-asset-category-container legend {
	font-weight: bold;
	margin-left: 5px;
}

.lfr-asset-category-container label {
	display: block;
}

.lfr-asset-category-container label input {
	vertical-align: middle;
}

.lfr-asset-category-container .lfr-asset-category-message {
	display: none;
}

.lfr-asset-category-search-container {
	background-color: #D3DADD;
	border-bottom: 1px solid #AEB8BC;
	margin: 3px 0;
	padding: 5px;
}

.lfr-asset-category-search-input {
	width: 250px;
}

.lfr-asset-category-container .no-matches {
	border-bottom: none;
	border-left: none;
	border-right: none;
	color: #999;
	margin-bottom: 0;
	margin-left: 0;
	margin-top: 0;
	padding: 0;
}

.lfr-asset-category-container.no-matches legend {
	padding-left: 0;
}

.lfr-asset-category-container.no-matches .lfr-asset-category-message {
	display: block;
}

/* ---------- Tag selector styling ---------- */

.ui-dialog.lfr-tag-selector .ui-dialog-container .ui-dialog-content {
	padding: 0;
}

.lfr-tag-container {
	height: 260px;
	margin-bottom: 0.5em;
	overflow: auto;
}

.lfr-tag-container fieldset {
	margin: 5px;
}

.lfr-tag-container legend {
	font-weight: bold;
	margin-left: 5px;
}

.lfr-tag-container label {
	display: block;
}

.lfr-tag-container label input {
	vertical-align: middle;
}

.lfr-tag-container .lfr-tag-message {
	display: none;
}

.lfr-tag-search-container {
	background-color: #D3DADD;
	border-bottom: 1px solid #AEB8BC;
	margin: 3px 0;
	padding: 5px;
}

.lfr-tag-search-input {
	width: 250px;
}

.lfr-tag-container .no-matches {
	border-bottom: none;
	border-left: none;
	border-right: none;
	color: #999;
	margin-bottom: 0;
	margin-left: 0;
	margin-top: 0;
	padding: 0;
}

.lfr-tag-container.no-matches legend {
	padding-left: 0;
}

.lfr-tag-container.no-matches .lfr-tag-message {
	display: block;
}

/* ---------- Generic styling ---------- */

.popup-alert-notice, .popup-alert-warning {
	background: #ffc url() no-repeat 5px 50%;
	border-bottom: 1px solid;
	font-size: 1.1em;
	left: 0;
	padding: 10px;
	padding-left: 25px;
	position: fixed;
	top: 0;
	width: 100%;
	z-index: 10000;
}

.popup-alert-notice {
	background-color: #ffc;
	background-image: url(<%= themeImagesPath %>/messages/alert.png);
	border-bottom-color: #fc0;
}

.popup-alert-warning {
	background-color: #fcc;
	background-image: url(<%= themeImagesPath %>/messages/error.png);
	border-bottom-color: #f00;
	font-weight: bold;
}

.ie6 .popup-alert-notice, .ie6 .popup-alert-warning {
	bottom: auto;
	left: expression( ( LFR_IGNORE_ME2 = document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft ) + 'px' );
	position: absolute;
	right: auto;
	top: expression( ( LFR_IGNORE_ME = document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop ) + 'px' );
}

.popup-alert-notice .countdown-timer {
	font-size: 1.1em;
	font-weight: bold;
}

.popup-alert-notice input, .popup-alert-warning input {
	vertical-align: middle;
}

#ui-datepicker-div {
	z-index: 500;
}

/* ---------- Portlets ---------- */

#lfr-look-and-feel fieldset {
	border:1px solid #BFBFBF;
}

/* ---------- Generic styling ---------- */

.breadcrumbs {
	margin-bottom: 10px;
	margin-left: 0px;
}

.js .lfr-fallback {
	display: none;
}

.lfr-button {
	background: url() no-repeat 0 50%;
	padding: 2px 0 2px 20px;
}

.js .controls-hidden .lfr-meta-actions, .js .controls-hidden .portlet-borderless-bar, .js .controls-hidden .portlet-icons, .js .controls-hidden .lfr-configurator-visibility {
	display: none;
}

.js .page-maximized .portlet-icons {
	display: block;
}

.float-container:after {
	clear: both;
	content: ".";
	display: block;
	height: 0;
	visibility: hidden;
}

.ie .float-container {
	height: 1%;
}

.lfr-meta-actions {
	padding-top: 1em;
}

.lfr-table {
	border-collapse: collapse;
	clear: both;
}

.lfr-table tr td, .lfr-table tr th {
	padding: 0 5px;
}

.lfr-table tr th {
	font-weight: bold;
}

.lfr-table tr td:first-child, .lfr-table tr td.first-child, .lfr-table tr th:first-child, .lfr-table tr th.first-child {
	padding-left: 0;
}

.lfr-table tr td:last-child, .lfr-table tr td.last-child, .lfr-table tr th:last-child, .lfr-table tr th.last-child {
	padding-right: 0;
}

.lfr-control-links a {
	margin-right: 1em;
}

.portlet-toolbar {
	margin-bottom: 1.5em;
}

.portlet-minimized .portlet-content-container {
	display: none;
}

.lfr-checkbox-preselected {
	opacity: 0.5;
}

.ie .lfr-checkbox-preselected {
	filter: alpha(opacity=50);
	zoom: 1;
}

.lfr-configurator-visibility {
	opacity: 0.5;
}

.ie .lfr-configurator-visibility {
	filter: alpha(opacity=50);
	zoom: 1;
}

/* ---------- Liferay forms ---------- */

.lfr-input-text {
	width: <%= ModelHintsConstants.TEXT_DISPLAY_WIDTH %>px;
}

.lfr-input-text.flexible {
	width: auto;
}

.lfr-textarea {
	height: <%= ModelHintsConstants.TEXTAREA_DISPLAY_HEIGHT %>px;
	width: <%= ModelHintsConstants.TEXTAREA_DISPLAY_WIDTH %>px;
}

fieldset, .aui-form fieldset {
	margin-bottom: 2em;
}

fieldset:last-child, .aui-form fieldset:last-child {
	margin-bottom: 0;
}

/* ---------- Separator ---------- */

.separator {
	margin: 15px auto;
}

/* ---------- Taglib action bar ---------- */

.lfr-actions {
	float: right;
	text-align: left;
}

.lfr-actions.left {
	float: left;
}

.lfr-actions ul {
	display: none;
	bottom: 0%;
	position: absolute;
	right: 100%;
	z-index: 99999;
}

.lfr-actions.visible ul {
	display: block;
}

.lfr-actions.left ul {
	right: auto;
	left: 100%;
}

.lfr-actions .lfr-trigger, .lfr-actions .lfr-trigger strong {
	background: url(<%= themeImagesPath %>/common/button_bg.png) no-repeat;
	font-weight: normal;
}

.lfr-actions .lfr-trigger {
	background-position: 100% -42px;
	cursor: pointer;
	padding-right: 3px;
}

.ie6 .lfr-actions {
	height: 15px;
}

.lfr-actions .lfr-trigger strong {
	display: block;
	min-width: 70px;
	padding: 2px 0 3px;
	text-align: center;
}

.ie6 .lfr-actions .lfr-trigger strong {
	display: inline-block;
}

.lfr-actions.visible .lfr-trigger {
	background-position: 100% 100%;
}

.lfr-actions.visible .lfr-trigger strong {
	background-position: 0 -21px;
}

.lfr-actions .lfr-trigger strong span {
	background: url(<%= themeImagesPath %>/common/action.png) no-repeat 5px 50%;
	padding: 2px 10px 2px 35px;
}

.lfr-actions.left .lfr-trigger strong span {
	background-image: url(<%= themeImagesPath %>/common/action_right.png);
	background-position: 98% 50%;
	padding: 2px 35px 2px 10px;
}

.lfr-menu-list ul {
	background: #fff url(<%= themeImagesPath %>/forms/button.png) repeat-x 0 100%;
	border: 1px solid;
	border-color: #DEDEDE #BFBFBF #BFBFBF #DEDEDE;
}

.lfr-menu-list li {
	background: transparent;
	border-bottom: 1px solid #DEDEDE;
	padding: 2px;
}

.lfr-menu-list li nobr {
	display: block;
}

.lfr-menu-list li a {
	padding: 5px 10px 5px 0;
	display: block;
	font-weight: normal;
	text-decoration: none;
}

.lfr-menu-list li a:hover {
	background-color: #828F95;
	color: #fff;
}

.lfr-menu-list li a img {
	padding: 0 3px;
}

.lfr-menu-list li.last {
	border-bottom: none;
}

/* ---------- Taglib asset categories summary ---------- */

.taglib-asset-categories-summary .asset-category {
	margin-bottom: 3px;
	margin-right: 3px;
	padding-right: 5px;
}


/* ---------- Taglib calendar ---------- */

.taglib-calendar {
	width: 190px;
}

.taglib-calendar table {
	border: 1px solid #999;
	width: 100%;
}

.taglib-calendar tr th, .taglib-calendar tr td {
	height: 25px;
	text-align: center;
	border-bottom: 1px solid #999;
}

.taglib-calendar tr.portlet-section-header th, .taglib-calendar tr td {
	border: 1px solid #999;
	border-bottom: none;
	border-top: none;
	padding: 0;
	width: 26px;
}

.taglib-calendar tr.portlet-section-header th.first, .taglib-calendar tr td.first {
	border-left: none;
}

.taglib-calendar tr.portlet-section-header th.last, .taglib-calendar tr td.last {
	border-right: none;
}

.taglib-calendar tr td a {
	display: block;
	height: 15px;
	padding: 5px 0;
}

.taglib-calendar a:hover, .taglib-calendar a:focus {
	background-color: #ccc;
}

.taglib-calendar .calendar-inactive {
	color: #999;
}

.taglib-calendar .calendar-current-day {
}

.taglib-calendar .calendar-current-day a {
}

.taglib-calendar .has-events a span {
	background: url(<%= themeImagesPath %>/calendar/event_indicator.png) no-repeat 50% 95%;
	padding-bottom: 5px;
}

.taglib-calendar .has-events.calendar-current-day a span {
	background-image: url(<%= themeImagesPath %>/calendar/event_indicator_current.png);
}

/* ---------- Taglib captcha ---------- */

.taglib-captcha {
	margin: 1em 0;
}

.taglib-captcha .captcha {
	display: block;
	margin-bottom: 1em;
}

/* ---------- Taglib discussion thread ---------- */

.taglib-discussion td img {
	vertical-align: middle;
}

/* ---------- Taglib diff HTML ---------- */

.taglib-diff-html div.diff-removed-image {
	background: #fdc6c6 url(<%= themeImagesPath %>/diff/minus.png);
}

.taglib-diff-html div.diff-added-image {
	background: #cfc url(<%= themeImagesPath %>/diff/plus.png) no-repeat;
}

.taglib-diff-html div.diff-removed-image, div.diff-added-image {
	height: 300px;
	margin: 2px;
	opacity: 0.55;
	position: absolute;
	width: 200px;
}

.gecko .taglib-diff-html div.diff-removed-image, .gecko div.diff-added-image {
	-moz-opacity: 0.55;
}

.ie .taglib-diff-html div.diff-removed-image, .ie div.diff-added-image {
	filter: alpha(opacity=55);
}

.taglib-diff-html span.diff-html-added img {
	border: 2px solid #cfc;
}

.taglib-diff-html span.diff-html-added {
	background-color: #cfc;
	font-size: 1em;
}

.taglib-diff-html span.diff-html-changed img {
	border: 2px dotted #009;
}

.taglib-diff-html span.diff-html-changed {
	background: url(<%= themeImagesPath %>/diff/underline.png) bottom repeat-x;
}

.taglib-diff-html span.diff-html-removed img {
	border: 2px solid #fdc6c6;
}

.taglib-diff-html span.diff-html-removed {
	background-color: #fdc6c6;
	font-size: 1em;
	text-decoration: line-through;
}

/* ---------- Taglib diff thread ---------- */

.taglib-diff-addedline, #taglib-diff-results ins {
	background: #E6FFE6;
}

.taglib-diff-context {
	background: #EEEEEE;
}

.taglib-diff-deletedline, #taglib-diff-results del {
	background: #FFE6E6;
}

.taglib-diff-table {
	border-collapse: separate;
	border-spacing: 5pt;
	width: 100%;
}

/* ---------- Taglib flags ---------- */

.taglib-flags {
	display: inline-block;
}

.taglib-flags span a {
	margin-right: 2px;
}

/* ---------- Taglib icon list ---------- */

.taglib-icon-list li {
	float: left;
	margin-right: 1em;
}

.taglib-icon-list:after {
	clear: both;
	content: ".";
	display: block;
	height: 0;
	visibility: hidden;
}

.ie .taglib-icon-list {
	height: 1%;
}

/* ---------- Taglib input localized ---------- */

.taglib-input-localized {
	float: left;
}

/* ---------- Taglib input move boxes ---------- */

.taglib-move-boxes {
}

.taglib-move-boxes .choice-selector {
	min-width: 150px;
}

.ie .taglib-move-boxes .choice-selector {
	width: expression(this.currentStyle.getAttribute('minWidth') || 150 + 'px');
}

.taglib-move-boxes .category-header {
	background: #ebf1f9;
	border-bottom: 1px solid #8db2f3;
	display: block;
	margin-bottom: 5px;
	padding: 5px;
}

/* ---------- Taglib ratings ---------- */

.taglib-ratings.score {
	white-space: nowrap;
}

.taglib-ratings.thumbs {
	float: left;
	margin: 0;
}

.ie .taglib-ratings.thumbs {
	height: 1%;
}

.taglib-ratings.thumbs:after {
	clear: both;
	content: ".";
	display: block;
	height: 0;
	visibility: hidden;
}

.taglib-ratings.thumbs li {
	float: left;
	list-style-image: none;
	list-style-position: outside;
	list-style-type: none;
}

.taglib-ratings.thumbs .total-entries {
	color: #777777;
}

.taglib-ratings.thumbs .total-rating {
	font-size: 1.1em;
	line-height: 18px;
	padding: 0 5px 0 0;
}

.taglib-ratings.thumbs .total-votes {
	color: #777777;
	font-size: xx-small;
	line-height: 2em;
	padding: 0 0 0 6px;
	text-align: right;
}

.taglib-ratings.thumbs .pos-total {
	color: #009900;
	font-weight: bold;
}

.taglib-ratings.thumbs .neg-total {
	color: #777777;
	font-weight: bold;
}

.taglib-ratings.thumbs .zero-total {
	color: #aaaaaa;
}

.taglib-ratings.thumbs .rating {
	background: url() no-repeat;
	display: block;
	float: left;
	font-size: 0;
	height: 18px;
	line-height: 0;
	text-indent: -9999em;
	width: 18px;
}

.taglib-ratings.thumbs .rate-up {
	background-image: url(<%= themeImagesPath %>/ratings/thumbs_up_icon.png);
}

.taglib-ratings.thumbs .rate-up:hover, .taglib-ratings.thumbs .rate-up.rated {
	background-image: url(<%= themeImagesPath %>/ratings/thumbs_up_icon_hover.png);
}

.taglib-ratings.thumbs .rate-down {
	background-image: url(<%= themeImagesPath %>/ratings/thumbs_down_icon.png);
}

.taglib-ratings.thumbs .rate-down:hover, .taglib-ratings.thumbs .rate-down.rated {
	background-image: url(<%= themeImagesPath %>/ratings/thumbs_down_icon_hover.png);
}

.taglib-ratings.thumbs .rate-up.rated:hover, .taglib-ratings.thumbs .rate-down.rated:hover {
	background-image: url(<%= themeImagesPath %>/ratings/thumbs_cancel_icon.png);
}

/* ---------- Taglib search iterator ---------- */

.taglib-search-iterator {
	border-collapse: collapse;
	width: 100%;
}

.taglib-search-iterator td {
	padding: 4px;
}

.taglib-search-iterator-highlighted {
	font-weight: bold;
}

.taglib-search-iterator-page-iterator-top {
	padding-bottom: 5px;
}

.taglib-search-iterator-page-iterator-bottom {
	padding-top: 5px;
}

.taglib-page-iterator {
	clear: both;
	height: auto;
	overflow: hidden;
	width: auto;
}

.taglib-page-iterator .search-results {
	float: left;
}

.taglib-page-iterator .search-pages {
	float: right;
}

.taglib-page-iterator .search-pages .page-links a, .taglib-page-iterator .search-pages .page-links span {
	background: url() no-repeat 0 0;
	border-left: 1px solid #ccc;
	padding: 2px 15px;
}

.taglib-page-iterator .search-pages .page-links span {
	background-position: 0 100%;
}

.taglib-page-iterator .search-pages .page-links a.first, .taglib-page-iterator .search-pages .page-links span.first {
	background-image: url(<%= themeImagesPath %>/arrows/paging_first.png);
	background-repeat: no-repeat;
	border-left: none;
}

.taglib-page-iterator .search-pages .page-links a.previous, .taglib-page-iterator .search-pages .page-links span.previous {
	background-image: url(<%= themeImagesPath %>/arrows/paging_previous.png);
	background-repeat: no-repeat;
}

.taglib-page-iterator .search-pages .page-links a.next, .taglib-page-iterator .search-pages .page-links span.next {
	background-image: url(<%= themeImagesPath %>/arrows/paging_next.png);
	background-repeat: no-repeat;
}

.taglib-page-iterator .search-pages .page-links a.last, .taglib-page-iterator .search-pages .page-links span.last {
	background-image: url(<%= themeImagesPath %>/arrows/paging_last.png);
	background-repeat: no-repeat;
}

.taglib-page-iterator .search-pages .page-links a.first, .taglib-page-iterator .search-pages .page-links span.first, .taglib-page-iterator .search-pages .page-links a.previous, .taglib-page-iterator .search-pages .page-links span.previous {
	padding-right: 5px;
}

.taglib-page-iterator .search-pages .page-links a.next, .taglib-page-iterator .search-pages .page-links span.next, .taglib-page-iterator .search-pages .page-links a.last, .taglib-page-iterator .search-pages .page-links span.last {
	background-position: 100% 0;
	padding-left: 5px;
}

.taglib-page-iterator .search-pages .page-links span.first, .taglib-page-iterator .search-pages .page-links span.previous {
	background-position: 0 100%;
}

.taglib-page-iterator .search-pages .page-links span.next, .taglib-page-iterator .search-pages .page-links span.last {
	background-position: 100% 100%;
}

.taglib-page-iterator .search-pages .delta-selector, .taglib-page-iterator .search-pages .page-selector, .taglib-page-iterator .search-pages .page-links {
	float: left;
}

.taglib-page-iterator .search-pages .page-selector {
	margin-left: 10px;
	margin-right: 10px;
	padding-left: 10px;
	padding-right: 10px;
}

/* ---------- Taglib social activities ---------- */

.taglib-social-activities .activity-separator {
	padding-bottom: 10px;
}

.taglib-social-activities .day-separator {
	border-bottom: 1px dotted #CCC;
	margin-bottom: 0px;
	margin-top: 10px;
	padding-bottom: 2px;
}

.taglib-social-activities .first-day-separator {
	border-bottom: 1px dotted #CCC;
	margin-bottom: 0px;
	margin-top: 0px;
	padding-bottom: 2px;
}

/* ---------- Taglib social bookmark ---------- */

.js .taglib-social-bookmarks.visible {
	background: #F0F5F7;
}

.js .taglib-social-bookmarks .show-bookmarks {
	display: block;
}

.js .taglib-social-bookmarks.visible .show-bookmarks {
}

.js .taglib-social-bookmarks ul {
	border: 1px solid #828F95;
	border-left: none;
	border-right: none;
	display: none;
}

.js .taglib-social-bookmarks.visible ul {
	display: block;
}

.taglib-social-bookmarks li {
	float: left;
	margin-right: 1em;
}

.taglib-social-bookmarks ul:after {
	clear: both;
	content: ".";
	display: block;
	height: 0;
	visibility: hidden;
}

.ie .taglib-social-bookmarks ul {
	height: 1%;
}

.taglib-social-bookmarks li a {
	background: url() no-repeat 0 50%;
	padding: 3px 2px 3px 20px;
}

.taglib-social-bookmarks a.taglib-social-bookmark-blinklist {
	background-image: url(<%= themeImagesPath %>/social_bookmarks/blinklist.png);
}

.taglib-social-bookmarks a.taglib-social-bookmark-delicious {
	background-image: url(<%= themeImagesPath %>/social_bookmarks/delicious.png);
}

.taglib-social-bookmarks a.taglib-social-bookmark-digg {
	background-image: url(<%= themeImagesPath %>/social_bookmarks/digg.png);
}

.taglib-social-bookmarks a.taglib-social-bookmark-facebook {
	background-image: url(<%= themeImagesPath %>/social_bookmarks/facebook.png);
}

.taglib-social-bookmarks a.taglib-social-bookmark-furl {
	background-image: url(<%= themeImagesPath %>/social_bookmarks/furl.png);
}

.taglib-social-bookmarks a.taglib-social-bookmark-newsvine {
	background-image: url(<%= themeImagesPath %>/social_bookmarks/newsvine.png);
}

.taglib-social-bookmarks a.taglib-social-bookmark-reddit {
	background-image: url(<%= themeImagesPath %>/social_bookmarks/reddit.png);
}

.taglib-social-bookmarks a.taglib-social-bookmark-technorati {
	background-image: url(<%= themeImagesPath %>/social_bookmarks/technorati.png);
}

/* ---------- Taglib tags summary ---------- */

.taglib-asset-tags-summary .tag {
	background: url(<%= themeImagesPath %>/common/tag.png) no-repeat 0 50%;
	margin-bottom: 3px;
	margin-right: 3px;
	padding: 2px 5px 2px 20px;
}

/* ---------- Taglib user display ---------- */

.taglib-user-display .user-details .user-name {
	clear: both;
	display: block;
}

/* ---------- Taglib webdav ---------- */

.js .taglib-webdav.visible {
	background: #F0F5F7;
}

.js .taglib-webdav .show-webdav {
	display: block;
}

.js .taglib-webdav.visible .show-webdav {
}

.js .taglib-webdav table {
	border: 1px solid #828F95;
	border-left: none;
	border-right: none;
	display: none;
}

.js .taglib-webdav.visible table {
	display: block;
}

.taglib-webdav table:after {
	clear: both;
	content: ".";
	display: block;
	height: 0;
	visibility: hidden;
}

.ie .taglib-webdav table {
	height: 1%;
}

/* ---------- jQuery plugins ---------- */

/* ---------- Tabs ---------- */

.tabs-hide {
	display: none;
}

.ie6 .tabs-nav {
	display: inline-block;
}

.ie6 .tabs-nav .tabs-disabled a {
	filter: alpha(opacity=40);
}

/* ----------- OpenID ---------- */

input[type="text"].openid-login {
	background: url(<%= themeImagesPath %>/common/openid.gif) no-repeat;
	background-color: #fff;
	background-position: 0 50%;
	color: #000;
	padding-left: 18px;
}

.ie6 input.text.openid-login {
	background: url(<%= themeImagesPath %>/common/openid.gif) no-repeat;
	background-color: #fff;
	background-position: 0 50%;
	color: #000;
	padding-left: 18px;
}

/* ----------- Accessibility ---------- */

img.label-icon {
	border: 0px;
	float: left;
}

img.icon {
	border: 0px;
	float: absmiddle;
}

img.avatar {
	border: 0px;
}

label.hidden-label {
	float: left;
	font-size: 0;
}

td.stretch {
	width: 99%;
}

.nobr {
	white-space: nowrap;
}