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

.portlet-staging-bar {
	.staging-tabview-list {
		a, .taglib-text {
			text-decoration: none
		}
	}

	.manage-layout-branches-tab {
		margin-left: 1em;

		&.aui-tab-label {
			margin-left: 0;
		}
	}

	.staging-tabview-list {
		border-width: 0;
		font-size: 1.3em;
		margin-bottom: 0;

		.aui-tab, .aui-tab-content {
			background: none;
			border-width: 0;
		}

		.aui-tab-active .aui-tab-content {
			background-color: #333;

			.aui-tab-label {
				text-shadow: -1px -1px 0 #000;
			}
		}
	}

	.site-variations-tabview-list {
		background-image: none;
		background-color: #999;
		border-width: 0;
		font-size: 1.2em;
		margin-bottom: 0;

		.aui-tab, .aui-tab-content {
			background: none;
			border-width: 0;

			.aui-tab-label {
				padding: 7px 10px;

				a {
					color: #BBB;
					text-shadow: none;
					text-decoration: underline;

					&:hover {
						text-decoration: none;
					}
				}
			}
		}

		.aui-tab-active .aui-tab-content {
			.aui-tab-label {
				color: #FFF;
				font-weight: bold;
				text-shadow: -1px -1px 0 #333;
			}
		}

		.manage-layout-set-branches-tab .manage-layout-set-branches a {
			border-left: 1px solid #999;
			color: #DDD;
			font-size: 0.9em;
			margin-left: 10px;
			padding-left: 10px;
			text-decoration: none;

			.taglib-text {
				text-decoration: none;

				&:hover {
					text-decoration: underline;
				}
			}
		}

		.go-to-layout-set-branches-tab .lfr-trigger strong a {
			background-image: url(<%= themeImagesPath %>/arrows/06_down.png);
			text-decoration: none;

			.taglib-text{
				color: #EEE;
			}
		}
	}

	.staging-icon-menu-container {
		display: inline-block;
		margin-top: -0.1em;
		overflow: hidden;
		position: relative;
		vertical-align: middle;

		a {
			display: block;
			float: right;
			min-height: 15px;
		}

		.staging-icon-menu.lfr-actions .lfr-trigger strong {
			min-width: 0;
			padding: 0;

			a {
				background-image: url(<%= themeImagesPath %>/arrows/06_down.png);
				background-position: 0 4px;
				padding: 0 5px;
				opacity: 0.5;
			}
		}
	}

	.aui-tab-active {
		.layoutset-branches-menu.lfr-actions {
			li .taglib-text {
				color: #FFF;
			}

			.lfr-trigger strong {
				padding: 0;
				text-shadow: 1px 1px #000;
			}
		}

		.staging-icon-menu.lfr-actions .lfr-trigger strong a {
			opacity: 1;
		}
	}

	.branch-results {
		min-height: 300px;
	}

	.layoutset-branches-menu.lfr-actions {
		float: none;

		.lfr-trigger strong {
			display: inline;
		}
	}

	.staging-bar {
		background: #888;

		.variations-tabview-list {
			background-color: #DDD;
		}

		.staging-tabview-content {
			border: solid #636364;
			border-top-color: #333;
			border-width: 1px 0;
			color: #EEE;
			margin-top: -1px;
			padding: 0.5em 1em;

			.staging-icon {
				float: left;
				margin-right: 1em;
			}

			.layout-set-branch-info {
				font-size: 1.1em;
				margin: 0.5em 0;
				text-indent: -0.5em;

				.layout-set-branch-description {
					border-right: 1px solid #AAA;
					padding: 0 0.5em 0;
					font-style: italic;
				}

				.layout-set-branch-pages {
					padding-left: 0.5em;
				}
			}

			.layout-info {
				.variations-tabview-list {
					margin: 0.5em 0;
				}

				.variations-tabview-content {
					overflow: hidden;

					.layout-branch-description {
						font-style: italic;
						margin-bottom: 0.5em;
					}

					.manage-layout-branches-tab a {
						color: #EEE;
					}

					.layout-actions, .layout-revision-details {
						float: left;
					}

					.layout-revision-id {
						font-size: 0.6em;
					}
				}

				.layout-title {
					font-size: 1.2em;

					label {
						float: left;
					}

					.layout-breadcrumb {
						font-size: 1em;

						.breadcrumbs a {
							color: #EEE;
						}

						.breadcrumbs-horizontal {
							margin-bottom: 0;
							overflow: hidden;
							padding-left: 0.5em;

							li {
								background-image: url(<%= themeImagesPath %>/arrows/09_right.png);

								&.last {
									background-image: none;
									font-weight: bold;
									position: relative;
									text-decoration: none;
									top: -0.2em;
								}
							}
						}
					}
				}
			}

			.last-publication-variation-details {
				font-size: 0.8em;

				.layout-version {
					background: url(<%= themeImagesPath %>/common/pages.png) no-repeat 0 50%;
					padding: 2px 0 2px 20px;
				}

				.variation-name {
					background: url(<%= themeImagesPath %>/common/signal_instance.png) no-repeat 0 50%;
					margin-right: 10px;
					padding: 2px 0 2px 20px;
				}
			}

			.taglib-workflow-status {
				clear: left;
				color: #FFF;
				float: left;
				margin-right: 5em;

				.workflow-status, .workflow-version {
					color: #FFF;
				}

				.workflow-status-approved, .workflow-status-ready-for-publication {
					color: #8BEC59;
				}

				.workflow-status-draft {
					color: #CAD8F3;
				}

				.workflow-status-expired {
					color: #FF8E8E;
				}
			}

			.last-publication-branch, .staging-live-group-name {
				display: block;
				font-size: 1.3em;
				padding: 5px 0 0;
			}

			.layout-revision-toolbar {
				float: left;
				padding-left: 1em;
			}
		}
	}
}

.ie6, .ie7 {
	.portlet-staging-bar {
		.staging-icon-menu-container .staging-icon-menu.lfr-actions .lfr-trigger strong a {
		  filter: alpha(opacity=50);
		}

		.aui-tab-active .staging-icon-menu.lfr-actions .lfr-trigger strong a {
		  filter: alpha(opacity=100);
		}
	}
}

.js .controls-hidden .staging-bar {
	display: none;
}