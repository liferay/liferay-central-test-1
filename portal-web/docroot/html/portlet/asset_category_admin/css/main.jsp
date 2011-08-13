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

.portlet-asset-category-admin {
	.categories-admin-actions {
		clear: none;
		margin: 0;

		.permissions-button {
			margin-left: 1em;
		}
	}

	.categories-admin-container {
		.categories-actions-toolbar {
			margin-left: 1em;
		}

		.results-header {
			background: #F0F5F7;
			font-weight: bold;
			margin: 2px 0;
			padding: 5px 10px;
			position: relative;
		}
	}

	.categories-admin-content {
		li.vocabulary-category {
			padding: 1px 0;
		}

		li.vocabulary-category, li.vocabulary-item {
			font-weight: bold;
			list-style: none;
		}
	}

	.categories-admin-search, .categories-admin-select-search {
		.aui-field-content {
			display: inline-block;
		}
	}

	.categories-admin-search .aui-field-input {
		background-image: url(<%= themeImagesPath %>/common/search.png);
		background-repeat: no-repeat;
		background-position: 5px 50%;
		padding-left: 25px;
		width: 250px;
	}

	.categories-treeview-container {
		padding: 5px;
	}

	.select-vocabularies, .select-categories {
		border-color: #5F5F5F;
		float: left;
		line-height: 0;
		margin-right: 2px;
		padding: 0;

		.aui-field-element {
			padding: 5px 4px 4px;
			border: 1px solid #FFF;
			border-color: #F0F1F2 #B2B2B2 #949494 #F0F1F1;

			.aui-field-input-choice {
				margin: 0;
			}
		}
	}

	.vocabulary-list-container-content, .vocabulary-categories-container-content, .vocabulary-edit-category-content {
		padding: 0;
	}

	.vocabulary-content-wrapper {
		position: relative;
	}

	.vocabulary-content-wrapper, .header-title, .aui-tree-node-content .aui-tree-label {
		word-wrap: break-word;
	}

	.vocabulary-item-actions-trigger {
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

	.vocabulary-item-check {
		margin-left: 10px;
		position: static;
		margin-top: 10px;
		float: left;
	}

	.vocabulary-category {
		&:hover .vocabulary-item-actions-trigger, &:focus .vocabulary-item-actions-trigger, .vocabulary-item-actions-trigger:focus {
			clip: auto;
		}
	}

	.vocabulary-container .results-row .vocabulary-item-actions a {
		padding: 0;
	}

	.vocabulary-item.alt {
		background: #F0F2F4;
	}

	.vocabulary-item {
		&.selected {
			background: #AEB9BE;
		}

		a {
			display: block;
			padding: 8px 20px 8px 10px;
			text-decoration: none;
		}
	}

	.vocabulary-list {
		overflow: auto;
		overflow-x: hidden;

		.active-area {
			border: 1px solid #008000;

			a {
				background-color: #90EE90;
			}
		}

		li {
			border: 1px solid transparent;
		}

		.selected {
			a {
				color: #000;
				text-decoration: none;

				&:hover {
					color: #FFB683;
				}
			}

			a, .vocabulary-content-wrapper, .vocabulary-content-wrapper:hover {
				background-color: #6F7D83;
				color: #FFF;
			}
		}

		.vocabulary-content-wrapper {
			background-color: #F5F5F5;

			&:hover {
				background: #D3DADD;
			}
		}
	}

	.vocabulary-categories {
		overflow: auto;
	}

	.vocabulary-edit-category .category-view {
		padding: 0 5px 0 0px;
	}

	.category-view-close {
		position: absolute;
		right: 2px;
		text-align: right;
		top: 4px;

		span {
			cursor: pointer;
		}
	}

	#vocabulary-category-messages {
		margin: 10px;
	}

	.categories-treeview-container {
		.aui-tree-label {
			cursor: pointer;

			&:hover {
				color: #06c;
			}
		}

		.aui-tree-node-selected .aui-tree-label {
			background-color: #6F7D83;
			color: #FFF;
			cursor: move;
		}
	}

	.vocabulary-container .category-name {
		width: 300px;
	}

	.view-category {
		margin: 1em;

		label {
			display: block;
			font-weight: bold;
		}

		.category-field {
			clear: left;
			margin: 1em auto;
		}
	}
}

.portlet-asset-categories-admin-dialog {
	.aui-fieldset {
		margin-bottom: 0;
	}

	.lfr-panel-container {
		background-color: transparent;
		border-width: 0;
	}

	.asset-category-layer .aui-overlay {
		overflow: visible;
		width: 230px;
	}

	.yui3-widget-bd {
		.asset-category-layer {
			padding: 0 10px;

			.aui-field-content {
				margin-bottom: 10px;
			}

			label {
				display: block;
				font-weight: bold;
			}

			.aui-field {
				input, select {
					width: 200px;
				}
			}

			.button-holder {
				margin-top: 10px;
			}
		}
	}

	.yui3-widget-bd .aui-field.vocabulary-checkbox {
		input {
			width: 100%;
		}

		label {
			margin: 1.8em 0;
			display:inline-block;
		}
	}
}

.lfr-position-helper {
	z-index: 10000;
}

.ie6, .ie7 {
	.portlet-asset-category-admin {
		.vocabulary-content-wrapper {
			width: 100%;
		}

		.vocabulary-item-actions-trigger {
			cursor: pointer;
		}
	}
}

.ie {
	.portlet-asset-category-admin {
		.vocabulary-item a {
			zoom: 1;
		}
	}
}

.ie6 {
	.portlet-asset-categories-admin-dialog {
		.yui3-widget-bd {
			.asset-category-layer {
				width: 200px;
			}
		}
	}
}