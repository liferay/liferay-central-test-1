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

<%@ include file="/html/portal/init.jsp" %>

<%
response.setContentType(ContentTypes.TEXT_CSS);
%>

<%@ include file="/html/portal/css_cached_uniform.jsp" %>

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

.nojs .lfr-js-required {
	display: none;
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
	background: url(<%= themeDisplay.getPathThemeImages() %>/arrows/01_plus.png) no-repeat 100% 50%;
	border: none;
}

.lfr-add-content.expanded h2, .lfr-add-content .lfr-add-content.expanded h2 {
	background: url(<%= themeDisplay.getPathThemeImages() %>/arrows/01_minus.png) no-repeat 100% 50%;
}

.lfr-add-content h2 span {
	background: url(<%= themeDisplay.getPathThemeImages() %>/add_content/portlet_category.png) no-repeat 0 50%;
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
	background: url(<%= themeDisplay.getPathThemeImages() %>/arrows/04_left.png) no-repeat 5px 50%;
	padding-left: 12px;
}

.js .lfr-flyout.has-children.send-right {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/arrows/04_right.png);
}

.js .lfr-flyout.has-children ul {
	display: none;
	min-width: 150px;
	position: absolute;
	right: 100%;
	top: 0;
}

.js .lfr-flyout.has-children.send-right ul {
	left: 100%;
	right: auto;
}

/* ---------- Panel Page styles ---------- */

.lfr-panel .portal-add-content {
	padding: 0;
	padding-left: 4px;
}

.lfr-panel .panel-content {
	border-left: 1px solid #ccc;
	padding-left: 1em;
}

.lfr-panel .lfr-add-content h2 {
	border: 1px solid #ccc;
	border-right: none;
	padding: 1px;
}

.lfr-panel .lfr-add-content h2 span {
	background: #efefef;
	display: block;
	padding: 2px;
	padding-left: 5px;
}

.lfr-panel .lfr-add-content .lfr-content-category h2 {
	border: none;
	border-bottom: 1px solid #ccc;
}

.lfr-panel .lfr-add-content .lfr-content-category h2 span {
	background: none;
}

.lfr-panel.panel-frontpage .panel-content h2 {
	margin-top: 0;
}

/* ---------- Portlet item ---------- */

.lfr-portlet-item {
	background: url(<%= themeDisplay.getPathThemeImages() %>/add_content/portlet_item.png) no-repeat 0 50%;
	border: 1px solid #fff;
	cursor: move;
	font-size: 1.1em;
	margin-bottom: 3px;
	padding: 0 5px 0 20px;
}

.lfr-portlet-item.lfr-instanceable {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/add_content/portlet_item_instanceable.png);
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

/* ---------- Interactive Dock ---------- */

.js .interactive-mode {
	float: right;
	min-width: 150px;
	position: relative;
	right: 10px;
	top: 10px;
}

.js .interactive-mode h2 {
	background: url(<%= themeDisplay.getPathThemeImages() %>/dock/menu_bar.png) no-repeat 100% -30px;
	font-size: 1.2em;
	margin-bottom: 0;
	padding: 0 29px 0 0;
	position: relative;
	z-index: 82;
}

.js .interactive-mode h2 span {
	background: url(<%= themeDisplay.getPathThemeImages() %>/dock/menu_bar.png) no-repeat 0 0;
	display: block;
	font-size: 1.2em;
	margin-bottom: 0;
	min-height: 16px;
	min-width: 90px;
	padding: 0.5em 0.5em 0.5em 2em;
}

.js.ie6 .interactive-mode h2 span {
	height: 16px;
}

.js .lfr-dock.expanded .lfr-dock-list-container {
	border-top: none;
	top: -2px;
}

.js .interactive-mode ul {
	background: url(<%= themeDisplay.getPathThemeImages() %>/dock/menu_bg.png) no-repeat 0 0;
	display: none;
	float: none;
}

.js .interactive-mode li {
	display: block;
	float: none;
	margin-bottom: 0pt;
	margin-left: 0.2em;
}

.js .interactive-mode li a {
	background-position: 0.5em 50%;
	background-repeat: no-repeat;
	display: block;
	margin-left: 0.3em;
	padding: 0.5em 0pt 0.5em 2.5em;
	text-decoration: none;
}

.js .interactive-mode li a:hover {
	background-position: 1.5em 50%;
	padding-left: 3.5em;
}

.js .interactive-mode .my-places li.current a {
	background: transparent url(<%= themeDisplay.getPathThemeImages() %>/dock/my_place_current.png) no-repeat 1em 50%;
	margin: 0.1em 0 0 0.2em;
	padding-left: 3.5em;
}

.js .interactive-mode .lfr-flyout.has-children li {
	position: relative;
}

.js .interactive-mode .my-places li.public a {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/dock/my_places_public.png);
}

.js .interactive-mode .my-places li.private a {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/dock/my_places_private.png);
}

.js .interactive-mode .my-places ul li a.add-page {
	background: url(<%= themeDisplay.getPathThemeImages() %>/dock/page_settings.png) no-repeat;
	border: none;
	display: block;
	font-size: 0;
	height: 16px;
	padding: 0;
	position: absolute;
	right: 3px;
	text-indent: -9999em;
	top: 3px;
	width: 16px;
}

.ie6.js .lfr-dock.interactive-mode {
	white-space: nowrap;
	width: 150px;
}

.ie6.js .lfr-dock.interactive-mode li {
	height: 1%;
}

.ie6.js .lfr-dock.interactive-mode li a {
	height: 1%;
}

/* ---------- Portlet css editor ---------- */

#portlet-set-properties {
	display: none;
}

#portlet-set-properties .uni-form {
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

#portlet-set-properties .uni-form .text-input {
	margin-right: 5px;
	width: 80px;
}

#portlet-set-properties .uni-form fieldset.col {
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

#portlet-set-properties .uni-form #custom-css {
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

#portlet-set-properties .uni-form {
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
	background: url(<%= themeDisplay.getPathThemeImages() %>/trees/expand_all.png) no-repeat 0 50%;
}

.lfr-collapse {
	background: url(<%= themeDisplay.getPathThemeImages() %>/trees/collapse_all.png) no-repeat 0 50%;
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
	background-image: url(<%=themeDisplay.getPathThemeImages()%>/dock/add_content.png);
	background-repeat: no-repeat;
}

.lfr-upload-container a.upload-button {
	background-image: url(<%=themeDisplay.getPathThemeImages()%>/common/top.png);
	background-repeat: no-repeat;
}

.lfr-upload-container a.clear-uploads {
	background-image: url(<%=themeDisplay.getPathThemeImages()%>/portlet/refresh.png);
	background-repeat: no-repeat;
}

.lfr-upload-container a.cancel-uploads {
	background-image: url(<%=themeDisplay.getPathThemeImages()%>/common/close.png);
	background-repeat: no-repeat;
	float: right;
	margin-right: 0;
}

.lfr-upload-container .upload-file {
	background: #f0faf0 url(<%=themeDisplay.getPathThemeImages()%>/document_library/jpg.png) no-repeat 5px 50%;
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
	background-image: url(<%=themeDisplay.getPathThemeImages()%>/common/delete.png);
	background-repeat: no-repeat;
	position: absolute;
	right: 5px;
}

.lfr-upload-container .upload-complete {
	background-color: #E8EEF7;
	background-image: url(<%=themeDisplay.getPathThemeImages()%>/dock/my_place_current.png);
	font-weight: normal;
	opacity: 0.6;
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
	background: #8db2e3 url(<%=themeDisplay.getPathThemeImages()%>/progress_bar/complete_gradient.png) repeat-y 100% 0;
	display: block;
	height: 15px;
	width: 0;
}

.lfr-upload-container .file-uploading .progress-bar {
	display: block;
}

/* ---------- Portal ---------- */

/* ---------- Portal login ---------- */

.lfr-portal-login {
}

.lfr-portal-login:after {
	clear: both;
	content: ".";
	display: block;
	height: 0;
	visibility: hidden;
}

.ie .lfr-portal-login {
	height: 1%;
}

.lfr-portal-login form {
	float: left;
	margin-right: 10px;
	width: 300px;
}

.lfr-portal-login fieldset {
	padding-top: 0;
}

.lfr-portal-login .lfr-input-text {
	width: 150px;
}

.lfr-portal-login .uni-form legend {
	padding-top: 0;
}

/* ---------- Portal notifications styling ---------- */

.popup-alert-notice .notice-date {
	margin: 0 10px;
}

/* ---------- Tag selector styling ---------- */

.lfr-tag-select-container {
	height: 300px;
	position: relative;
}

.lfr-tag-container {
	height: 260px;
	margin-bottom: 1em;
	overflow: auto;
}

.lfr-tag-container label {
	cursor: pointer;
	display: block;
	float: left;
	margin-bottom: 5px;
	margin-right: 5px;
	overflow: hidden;
	white-space: pre;
	width: 25%;
}

.lfr-tag-container label input {
	margin-right: 5px;
}

.lfr-tag-select-container .save-button {
	bottom: 0;
	left: 0;
	position: absolute;
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
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/messages/alert.png);
	border-bottom-color: #fc0;
}

.popup-alert-warning {
	background-color: #fcc;
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/messages/error.png);
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

.js .controls-hidden .lfr-meta-actions, .js .controls-hidden .portlet-borderless-bar, .js .controls-hidden .portlet-icons {
	display: none;
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

.lfr-widget-bar {
	margin: 1em 0 0;
}

.lfr-widget-bar:after {
	clear: both;
	content: ".";
	display: block;
	height: 0;
	visibility: hidden;
}

.ie .lfr-widget-bar {
	height: 1%;
}

.lfr-widget-information {
	display: none;
}

.lfr-widget-bar .lfr-actions.left .lfr-trigger strong span, .lfr-widget-bar .lfr-actions.right .lfr-trigger strong span {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/common/sharing.png);
}

.lfr-widget-bar .lfr-actions.left .lfr-trigger  strong span {
	padding-right: 25px;
}

.lfr-widget-bar .lfr-actions.right .lfr-trigger  strong span {
	padding-left: 25px;
}

.portlet-minimized .portlet-content-container {
	display: none;
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

fieldset, .uni-form fieldset {
	margin-bottom: 2em;
}

fieldset:last-child, .uni-form fieldset:last-child {
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

.lfr-actions.right {
}

.lfr-actions.visible {
	position: relative;
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
	background: url(<%= themeDisplay.getPathThemeImages() %>/common/button_bg.png) no-repeat;
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
	background: url(<%= themeDisplay.getPathThemeImages() %>/common/action.png) no-repeat 5px 50%;
	padding: 2px 10px 2px 35px;
}

.lfr-actions.left .lfr-trigger strong span {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/common/action_right.png);
	background-position: 98% 50%;
	padding: 2px 35px 2px 10px;
}

.lfr-actions .lfr-trigger li {
	background: #fff;
	border-bottom: 1px solid #99b6db;
	padding: 5px;
}

.lfr-actions .lfr-trigger li.last {
	border-bottom: none;
}

.lfr-actions .lfr-trigger li a {
	display: block;
	font-weight: normal;
	position: relative;
	padding-left: 20px;
	text-decoration: none;
}

.lfr-actions .lfr-trigger li a img {
	position: absolute;
	left: 0;
}

.ie6 .lfr-actions .lfr-trigger li a img {
	left: -20px;
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
	background: url(<%= themeDisplay.getPathThemeImages() %>/calendar/event_indicator.png) no-repeat 50% 95%;
	padding-bottom: 5px;
}

.taglib-calendar .calendar-current-day.has-events a span {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/calendar/event_indicator_current.png);
}

/* ---------- Taglib discussion thread ---------- */

.taglib-discussion td img {
	vertical-align: middle;
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
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/ratings/thumbs_up_icon.png);
}

.taglib-ratings.thumbs .rate-up:hover, .taglib-ratings.thumbs .rate-up.rated {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/ratings/thumbs_up_icon_hover.png);
}

.taglib-ratings.thumbs .rate-down {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/ratings/thumbs_down_icon.png);
}

.taglib-ratings.thumbs .rate-down:hover, .taglib-ratings.thumbs .rate-down.rated {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/ratings/thumbs_down_icon_hover.png);
}

.taglib-ratings.thumbs .rate-up.rated:hover, .taglib-ratings.thumbs .rate-down.rated:hover {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/ratings/thumbs_cancel_icon.png);
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
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/arrows/paging_first.png);
	background-repeat: no-repeat;
	border-left: none;
}

.taglib-page-iterator .search-pages .page-links a.previous, .taglib-page-iterator .search-pages .page-links span.previous {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/arrows/paging_previous.png);
	background-repeat: no-repeat;
}

.taglib-page-iterator .search-pages .page-links a.next, .taglib-page-iterator .search-pages .page-links span.next {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/arrows/paging_next.png);
	background-repeat: no-repeat;
}

.taglib-page-iterator .search-pages .page-links a.last, .taglib-page-iterator .search-pages .page-links span.last {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/arrows/paging_last.png);
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

.taglib-page-iterator .search-pages .page-selector, .taglib-page-iterator .search-pages .page-links {
	float: left;
}

.taglib-page-iterator .search-pages .page-selector {
	margin-right: 10px;
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

.taglib-social-bookmarks li {
	float: left;
	margin-right: 1em;
}

.taglib-social-bookmarks:after {
	clear: both;
	content: ".";
	display: block;
	height: 0;
	visibility: hidden;
}

.ie .taglib-social-bookmarks {
	height: 1%;
}

.taglib-social-bookmarks a {
	background: url() no-repeat 0 50%;
	padding: 3px 2px 3px 20px;
}

a.taglib-social-bookmark-blinklist {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/social_bookmarks/blinklist.png);
}

a.taglib-social-bookmark-delicious {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/social_bookmarks/delicious.png);
}

a.taglib-social-bookmark-digg {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/social_bookmarks/digg.png);
}

a.taglib-social-bookmark-facebook {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/social_bookmarks/facebook.png);
}

a.taglib-social-bookmark-furl {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/social_bookmarks/furl.png);
}

a.taglib-social-bookmark-newsvine {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/social_bookmarks/newsvine.png);
}

a.taglib-social-bookmark-reddit {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/social_bookmarks/reddit.png);
}

a.taglib-social-bookmark-technorati {
	background-image: url(<%= themeDisplay.getPathThemeImages() %>/social_bookmarks/technorati.png);
}

/* ---------- Taglib user display ---------- */

.taglib-user-display .user-details .user-name {
	display: block;
	clear: both;
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

input[type="text"].openid_login {
	background: url(<%= themeDisplay.getPathThemeImages() %>/common/openid.gif) no-repeat;
	background-color: #fff;
	background-position: 0 50%;
	color: #000;
	padding-left: 18px;
}

.ie6 input.text.openid_login {
	background: url(<%= themeDisplay.getPathThemeImages() %>/common/openid.gif) no-repeat;
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

td.stretch {
	width: 99%;
}