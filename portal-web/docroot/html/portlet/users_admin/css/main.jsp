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

.portlet-users-admin {
	.aui-form {
		fieldset {
			border-width: 0;
			padding: 0;
		}

		.aui-fieldset {
			textarea {
				width: 90%;
			}

			table.org-labor-table td .aui-field {
				margin-bottom: 0;
				padding: 3px 1px;
			}

			label, .label {
				font-weight: bold;

				input {
					vertical-align: middle;
				}
			}

			.aui-field {
				margin-bottom: 10px;

				&.mailing-ctrl {
					clear: both;

					span {
						margin-right: 0.5em;
					}
				}

				&.localized-language-selector {
					margin-bottom: 0;
				}

				&.action-ctrl, &.mailing-ctrl, &.primary-ctrl {
					margin: 1.8em 0;
				}
			}
		}
	}

	#header-bottom {
		background-color: #F6F8FB;
		height: 34px;
		margin: 0 0 10px;
	}

	#header-menu {
		background-color: #F3F5F5;
		font-size: 11px;
		line-height: 34px;
		margin: 0 0 10px;
		padding: 0 10px;
		text-align: right;
	}

	#header-title {
		background-color: #C1CABC;
		font-size: 20px;
		font-weight: 500;
		margin: 0 0 10px;
		padding: 7px 10px;
	}

	#details .avatar {
		border: 1px solid #88C5D9;
		clear: both;
		margin: 0 auto;
		width: 100px;
	}

	.lfr-change-logo img {
		border-width: 0;
		display: block;
		width: auto;
	}

	.company-logo {
		border: none;
		width: 100px;
	}

	.email-user-add .password-changed-notification {
		display: none;
	}

	.label-holder {
		font-weight: 700;
		padding: 15px 0 5px;
	}

	.organization-information {
		overflow: hidden;
	}

	.organization-search {
		float: right;
		margin: 0 0 0.5em 0.5em;
	}

	.section {
		float: left;
		margin-left: 10px;
		width: 47%;

		h3 {
			background: url() no-repeat scroll 2px 50%;
			border-bottom: 1px solid #CCC;
			line-height: 1.5;
			margin-bottom: 0.5em;
			padding-left: 25px;
		}

		li {
			list-style: none;
			margin: 0;
			padding-left: 25px;
		}

		ul {
			margin: 0;
		}
	}

	.entity-addresses {
		.primary {
			background-position: 3px 5px;
		}

		.mailing-name {
			display: block;
			font-style: italic;
		}

		h3 {
			background-image: url(<%= themeImagesPath %>/dock/home.png);
		}
	}

	.entity-details {
		clear: both;
	}

	.entity-comments h3 {
		background-image: url(<%= themeImagesPath %>/dock/welcome_message.png);
	}

	.entity-email-addresses h3 {
		background-image: url(<%= themeImagesPath %>/mail/unread.png);
	}

	.entity-phones h3 {
		background-image: url(<%= themeImagesPath %>/common/telephone.png);
	}

	.entity-websites h3 {
		background-image: url(<%= themeImagesPath %>/common/history.png);
	}

	.radio-holder {
		line-height: 12px;
	}

	.form-navigator {
		.user-info, .organization-info, .company-info {
			font-weight: bold;
			margin-bottom: 15px;
		}

		.user-info .user-name, .organization-info .organization-name, .company-info .company-name {
			color: #036;
			display: block;
			font-size: 14px;
		}

		.user-info, .organization-info {
			.user-logo, .organization-logo {
				float: left;
				margin-right: 10px;
				padding: 0;
				width: 35px;
			}
		}
	}

	.instant-messenger, .social-network {
		clear: both;
		overflow: hidden;

		img {
			margin-left: 1em;
			margin-top: 1.8em;
		}

		.aui-field {
			float: left;
		}
	}

	table.org-labor-table {
		margin-bottom: 30px;
	}

	.addresses .aui-field {
		float: none;
		width: auto;
	}

	.permission-scopes {
		display: block;

		&:after {
			clear: both;
			content: ".";
			display: block;
			height: 0;
			visibility: hidden;
		}

		&.empty {
			display: none;
		}

		.permission-scope {
			background: #DFF4FF;
			border: 1px solid #A7CEDF;
			float: left;
			margin-right: 3px;
			padding: 3px 20px 3px 6px;
			padding-right: 20px;
			position: relative;
		}

		.permission-scope:hover {
			border-color: #AEB8BC;
		}

		.permission-scope-delete {
			display: block;
			padding: 6px;
			position: absolute;
			right: 0;
			top: 3px;

			span {
				background: url(<%= themeImagesPath %>/application/close_small.png) no-repeat 0 0;
				cursor: pointer;
				display: block;
				font-size: 0;
				height: 7px;
				text-indent: -9999px;
				width: 7px;
			}

			&:hover span {
				background-position: 0 100%;
			}
		}
	}

	.permission-group {
		margin: 10px 0 0 10px;
	}
	
	.view-all-link {
		font-weight: normal;
	}
}

.lfr-floating-container {
	.aui-field {
		input, img {
			vertical-align: top;
		}
	}
}

.ie {
	.portlet-users-admin {
		.permission-scopes {
			height: 1%;
		}
	}
}

.ie6 {
	.portlet-users-admin {
		.organization-information {
			height: 1%;
		}
	}
}