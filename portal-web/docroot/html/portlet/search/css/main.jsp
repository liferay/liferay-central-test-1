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

.portlet-search {
	.entry-tags {
		margin: 3px 0 0;
	}

	.entry-title {
		font-size: 1.3em;
	}

	.menu-column .search-layout-content {
		padding-left: 20em;

		.menu {
			margin-left: -20em;
			position: relative;
			width: 20em;
		}

		.result {
			display: inline-block;
			float: none;
			width: 100%;
		}
	}

	.more-results {
		font-size: 1.1em;
		padding: 5px 0;
	}

	.no-results {
		font-size: 1.3em;
		margin: 10px 0 0;
	}

	.open-search-panel {
		margin-top: 1em;
	}

	.search-layout .search-layout-content {
		.menu {
			.lfr-panel-container {
				border-width: 0;
			}

			.search-facet {
				.current-term {
					font-style: italic;
					font-weight: bolder;

					.frequency {
						display: none;
					}
				}

				.date .aui-calendar {
					margin: 0 auto;
				}

				.keywords {
					margin-top: 6px;
					width: 18em;
				}

				.aui-field-wrapper-content {
					margin: 0 0 1em;
				}

				ul {
					margin: 0;
				}

				&.search-asset_tags .lfr-panel-content ul {
					padding: 0;
				}

				&.search-vocabulary {
					li {
						list-style-type: none;
						margin: 0;
						padding: 0;
					}

					ul {
						list-style-type: none;
						margin: 0;
						padding: 0;
					}
				}
			}
		}

		.result {
			width: 100%;

			.full-query {
				border: 1px solid #CCC;
				table-layout: fixed;
				width: 100%;

				.container {
					max-height: 100px;
					overflow: auto;

					code {
						word-wrap: break-word;
					}
				}
			}

			.results-row td {
				.document {
					.document-fields {
						table-layout: fixed;
						width: 100%;

						th {
							background-color: #CCC;
							padding: 2px;

							&.key {
								width: 240px;
							}
						}

						td {
							border-bottom: 1px solid #CCC;
							padding: 2px;

							&.key {
							}

							&.value {
								.container {
									max-height: 100px;
									overflow: auto;

									code {
										word-wrap: break-word;
									}
								}
							}
						}
					}

					.document-name {
						font-size: 1.4em;
						font-weight: bold;
					}

					.entry-categories, .entry-tags, .document-type {
						float: left;
						padding-right: 6px;
					}

					.toggle-details {
						cursor: pointer;
						font-family: monospace;
						font-weight: bold;
					}
				}
			}

			.results-row.hover td {
				background: transparent;
			}
		}
	}

	.search-msg {
		border-bottom: 1px solid #DEDEDE;
		color: #777;
		margin: 15px 0;
		padding: 2px 5px;
		text-align: right;
	}

	.search-paginator-container {
		padding: 5px 0;
	}

	.section-title {
		font-size: 1.3em;
		font-weight: bold;
		margin: 10px 0;
	}

	.taglib-asset-categories-summary .asset-vocabulary {
		background: url(<%= themeImagesPath %>/common/folder.png) no-repeat 0 50%;
		padding: 2px 5px 2px 20px;
	}
}

.ie .portlet-search {
	.full-query .container, .result td.value .container {
		height: expression(this.scrollHeight > 100 ? "100px" : "auto");
	}
}