YUI.Env.core.push('liferay-loader', 'liferay-browser-selectors');

YUI.add(
	'liferay-loader',
	function(A, NAME) {

		var LiferayAUI = Liferay.AUI;

		var javaScriptRootPath = LiferayAUI.getJavaScriptRootPath();

		A.mix(
			YUI.Env[A.version].modules,
			{
				/*
				 * Liferay Modules
				 */
				'liferay-app-view-folders': {
					fullpath: javaScriptRootPath + '/liferay/app_view_folders.js',
					requires: [
						'aui-base',
						'aui-parse-content',
						'liferay-app-view-move',
						'liferay-history-manager',
						'liferay-list-view',
						'liferay-node',
						'liferay-portlet-base'
					]
				},
				'liferay-app-view-move': {
					fullpath: javaScriptRootPath + '/liferay/app_view_move.js',
					requires: [
						'aui-base',
						'dd-constrain',
						'dd-delegate',
						'dd-drag',
						'dd-drop',
						'dd-proxy',
						'liferay-history-manager',
						'liferay-portlet-base',
						'liferay-util-list-fields'
					]
				},
				'liferay-app-view-paginator': {
					fullpath: javaScriptRootPath + '/liferay/app_view_paginator.js',
					requires: [
						'aui-pagination',
						'aui-parse-content',
						'liferay-history-manager',
						'liferay-portlet-base'
					]
				},
				'liferay-app-view-select': {
					fullpath: javaScriptRootPath + '/liferay/app_view_select.js',
					requires: [
						'liferay-app-view-move',
						'liferay-history-manager',
						'liferay-portlet-base',
						'liferay-util-list-fields'
					]
				},
				'liferay-asset-categories-selector': {
					fullpath: javaScriptRootPath + '/liferay/asset_categories_selector.js',
					requires: [
						'aui-tree',
						'liferay-asset-tags-selector'
					]
				},
				'liferay-asset-tags-selector': {
					fullpath: javaScriptRootPath + '/liferay/asset_tags_selector.js',
					requires: [
						'array-extras',
						'async-queue',
						'aui-autocomplete-deprecated',
						'aui-form-textfield-deprecated',
						'aui-io-plugin-deprecated',
						'aui-io-request',
						'aui-live-search-deprecated',
						'aui-modal',
						'aui-template-deprecated',
						'aui-textboxlist-deprecated',
						'datasource-cache',
						'liferay-service-datasource'
					]
				},
				'liferay-auto-fields': {
					fullpath: javaScriptRootPath + '/liferay/auto_fields.js',
					requires: [
						'aui-base',
						'aui-data-set-deprecated',
						'aui-io-request',
						'aui-parse-content',
						'sortable',
						'base',
						'liferay-undo-manager'
					]
				},
				'liferay-available-languages': {
					fullpath: javaScriptRootPath + '/liferay/available_languages.jsp?languageId=' + themeDisplay.getLanguageId(),
					requires: [
						'liferay-language'
					]
				},
				'liferay-browser-selectors': {
					fullpath: javaScriptRootPath + '/liferay/browser_selectors.js',
					requires: ['yui-base']
				},
				'liferay-ddm-repeatable-fields': {
					fullpath: javaScriptRootPath + '/liferay/ddm_repeatable_fields.js',
					requires: [
						'aui-base',
						'aui-io-request',
						'aui-parse-content'
					]
				},
				'liferay-dockbar': {
					fullpath: javaScriptRootPath + '/liferay/dockbar.js',
					requires: [
						'aui-node',
						'event-touch'
					]
				},
				'liferay-dockbar-add-content': {
					fullpath: javaScriptRootPath + '/liferay/dockbar_add_content.js',
					requires: [
						'aui-io-request',
						'aui-tooltip-deprecated',
						'autocomplete-base',
						'event-mouseenter',
						'liferay-dockbar',
						'liferay-dockbar-add-content-content-preview',
						'liferay-dockbar-add-content-drag-drop',
						'liferay-dockbar-add-content-search',
						'liferay-portlet-base'
					]
				},
				'liferay-dockbar-add-content-preview': {
					fullpath: javaScriptRootPath + '/liferay/dockbar_add_content_preview.js',
					requires: [
						'aui-io-request',
						'aui-tooltip-deprecated',
						'event-mouseenter'
					]
				},
				'liferay-dockbar-add-content-drag-drop': {
					fullpath: javaScriptRootPath + '/liferay/dockbar_add_content_drag_drop.js',
					requires: [
						'aui-base',
						'dd',
						'liferay-dockbar',
						'liferay-layout',
						'liferay-layout-column',
						'liferay-layout-freeform',
						'liferay-portlet-base'
					]
				},
				'liferay-dockbar-add-content-search': {
					fullpath: javaScriptRootPath + '/liferay/dockbar_add_content_search.js',
					requires: [
						'aui-base',
						'autocomplete-base',
						'autocomplete-filters',
						'liferay-dockbar'
					]
				},
				'liferay-dockbar-underlay': {
					fullpath: javaScriptRootPath + '/liferay/dockbar_underlay.js',
					requires: [
						'aui-button',
						'aui-io-plugin-deprecated',
						'aui-overlay-manager-deprecated'
					]
				},
				'liferay-dynamic-select': {
					fullpath: javaScriptRootPath + '/liferay/dynamic_select.js',
					requires: [
						'aui-base'
					]
				},
				'liferay-form': {
					fullpath: javaScriptRootPath + '/liferay/form.js',
					plugins: {
						'liferay-form-placeholders': {
							condition: {
								name: 'liferay-form-placeholders',
								test: function(A) {
									return 'placeholder' in document.createElement('input');
								},
								trigger: 'liferay-form'
							}
						}
					},
					requires: [
						'aui-base',
						'aui-form-validator'
					]
				},
				'liferay-form-placeholders': {
					fullpath: javaScriptRootPath + '/liferay/form_placeholders.js',
					requires: [
						'liferay-form',
						'plugin'
					]
				},
				'liferay-history': {
					fullpath: javaScriptRootPath + '/liferay/history.js',
					// TODO
					requires: (function() {
						var WIN = A.config.win;

						var HISTORY = WIN.history;

						var module = 'history-hash';

						if (HISTORY &&
							HISTORY.pushState &&
							HISTORY.replaceState &&
							('onpopstate' in WIN || A.UA.gecko >= 2)) {

							module = 'liferay-history-html5';
						}

						return [
							'querystring-parse-simple', module];
					})()
				},
				'liferay-history-html5': {
					fullpath: javaScriptRootPath + '/liferay/history_html5.js',
					requires: [
						'liferay-history',
						'history-html5',
						'querystring-stringify-simple'
					]
				},
				'liferay-history-manager': {
					fullpath: javaScriptRootPath + '/liferay/history_manager.js',
					requires: [
						'liferay-history'
					]
				},
				'liferay-hudcrumbs': {
					fullpath: javaScriptRootPath + '/liferay/hudcrumbs.js',
					requires: [
						'aui-base',
						'plugin'
					]
				},
				'liferay-icon': {
					fullpath: javaScriptRootPath + '/liferay/icon.js',
					requires: [
						'aui-base'
					]
				},
				'liferay-inline-editor-base': {
					fullpath: javaScriptRootPath + '/liferay/inline_editor_base.js',
					requires: [
						'aui-base',
						'aui-overlay-base-deprecated'
					]
				},
				'liferay-input-localized': {
					fullpath: javaScriptRootPath + '/liferay/input_localized.js',
					requires: [
						'aui-base',
						'aui-component',
						'aui-event-input',
						'aui-palette',
						'liferay-available-languages'
					]
				},
				'liferay-input-move-boxes': {
					fullpath: javaScriptRootPath + '/liferay/input_move_boxes.js',
					requires: [
						'aui-base',
						'aui-toolbar'
					]
				},
				'liferay-layout': {
					fullpath: javaScriptRootPath + '/liferay/layout.js'
				},
				'liferay-layout-column': {
					fullpath: javaScriptRootPath + '/liferay/layout_column.js',
					requires: [
						'aui-sortable-layout',
						'dd'
					]
				},
				'liferay-layout-freeform': {
					fullpath: javaScriptRootPath + '/liferay/layout_freeform.js',
					requires: [
						'aui-resize-deprecated',
						'liferay-layout-column'
					]
				},
				'liferay-list-view': {
					fullpath: javaScriptRootPath + '/liferay/list_view.js',
					requires: [
						'aui-base',
						'transition'
					]
				},
				'liferay-logo-editor': {
					fullpath: javaScriptRootPath + '/liferay/logo_editor.js',
					requires: [
						'aui-image-cropper',
						'aui-io-request',
						'liferay-portlet-base'
					]
				},
				'liferay-logo-selector': {
					fullpath: javaScriptRootPath + '/liferay/logo_selector.js',
					requires: [
						'aui-base'
					]
				},
				'liferay-look-and-feel': {
					fullpath: javaScriptRootPath + '/liferay/look_and_feel.js',
					requires: [
						'aui-color-picker-popover',
						'aui-io-plugin-deprecated',
						'aui-io-request',
						'aui-modal',
						'aui-tabview'
					]
				},
				'liferay-menu': {
					fullpath: javaScriptRootPath + '/liferay/menu.js',
					requires: [
						'aui-debounce',
						'aui-node'
					]
				},
				'liferay-message': {
					fullpath: javaScriptRootPath + '/liferay/message.js',
					requires: [
						'aui-base',
						'liferay-store'
					]
				},
				'liferay-navigation': {
					fullpath: javaScriptRootPath + '/liferay/navigation.js',
					plugins: {
						'liferay-navigation-touch': {
							condition: {
								name: 'liferay-navigation-touch',
								test: function(A) {
									return A.UA.touch;
								},
								trigger: 'liferay-navigation'
							}
						}
					}
				},
				'liferay-navigation-interaction': {
					fullpath: javaScriptRootPath + '/liferay/navigation_interaction.js',
					requires: [
						'node-focusmanager',
						'plugin'
					]
				},
				'liferay-navigation-touch': {
					fullpath: javaScriptRootPath + '/liferay/navigation_touch.js',
					requires: [
						'event-touch',
						'liferay-navigation'
					]
				},
				'liferay-notice': {
					fullpath: javaScriptRootPath + '/liferay/notice.js',
					requires: [
						'aui-base'
					]
				},
				'liferay-node': {
					fullpath: javaScriptRootPath + '/liferay/node.js',
					requires: [
						'dom-base'
					]
				},
				'liferay-poller': {
					fullpath: javaScriptRootPath + '/liferay/poller.js',
					requires: [
						'aui-base',
						'io',
						'json'
					]
				},
				'liferay-portlet-base': {
					fullpath: javaScriptRootPath + '/liferay/portlet_base.js',
					requires: [
						'aui-base',
						'liferay-node'
					]
				},
				'liferay-portlet-url': {
					fullpath: javaScriptRootPath + '/liferay/portlet_url.js',
					requires: [
						'aui-base',
						'aui-io-request',
						'querystring-stringify-simple'
					]
				},
				'liferay-preview': {
					fullpath: javaScriptRootPath + '/liferay/preview.js',
					requires: [
						'aui-base',
						'aui-overlay-mask-deprecated',
						'aui-toolbar'
					]
				},
				'liferay-progress': {
					fullpath: javaScriptRootPath + '/liferay/progress.js',
					requires: [
						'aui-progressbar'
					]
				},
				'liferay-ratings': {
					fullpath: javaScriptRootPath + '/liferay/ratings.js',
					requires: [
						'aui-io-request',
						'aui-rating'
					]
				},
				'liferay-restore-entry': {
					fullpath: javaScriptRootPath + '/liferay/restore_entry.js',
					requires: [
						'aui-io-plugin-deprecated',
						'aui-io-request',
						'aui-modal',
						'liferay-portlet-base'
					]
				},
				'liferay-search-container': {
					fullpath: javaScriptRootPath + '/liferay/search_container.js',
					requires: [
						'aui-base',
						'aui-datatable-core',
						'event-mouseenter'
					]
				},
				'liferay-service-datasource': {
					fullpath: javaScriptRootPath + '/liferay/service_datasource.js',
					requires: [
						'aui-base',
						'datasource-local'
					]
				},
				'liferay-session': {
					fullpath: javaScriptRootPath + '/liferay/session.js',
					requires: [
						'aui-io-request',
						'aui-task-manager',
						'cookie',
						'liferay-notice'
					]
				},
				'liferay-staging': {
					fullpath: javaScriptRootPath + '/liferay/staging.js',
					requires: [
						'aui-io-plugin-deprecated',
						'aui-modal'
					]
				},
				'liferay-staging-branch': {
					fullpath: javaScriptRootPath + '/liferay/staging_branch.js',
					requires: [
						'liferay-staging'
					]
				},
				'liferay-staging-version': {
					fullpath: javaScriptRootPath + '/liferay/staging_version.js',
					requires: [
						'aui-button',
						'liferay-staging'
					]
				},
				'liferay-store': {
					fullpath: javaScriptRootPath + '/liferay/store.js',
					requires: [
						'aui-io-request'
					]
				},
				'liferay-token-list': {
					fullpath: javaScriptRootPath + '/liferay/token_list.js',
					requires: [
						'aui-base',
						'aui-template'
					]
				},
				'liferay-translation-manager': {
					fullpath: javaScriptRootPath + '/liferay/translation_manager.js',
					requires: [
						'aui-base'
					]
				},
				'liferay-undo-manager': {
					fullpath: javaScriptRootPath + '/liferay/undo_manager.js',
					requires: [
						'aui-data-set-deprecated',
						'base'
					]
				},
				'liferay-upload': {
					fullpath: javaScriptRootPath + '/liferay/upload.js',
					requires: [
						'aui-io-request',
						'aui-template-deprecated',
						'collection',
						'liferay-portlet-base',
						'uploader'
					]
				},
				'liferay-util-list-fields': {
					fullpath: javaScriptRootPath + '/liferay/util_list_fields.js',
					requires: [
						'aui-base'
					]
				},
				'liferay-util-window': {
					fullpath: javaScriptRootPath + '/liferay/util_window.js',
					requires: [
						'aui-dialog-iframe-deprecated',
						'aui-modal'
					]
				},
				'liferay-xml-formatter': {
					fullpath: javaScriptRootPath + '/liferay/xml_formatter.js',
					requires: [
						'aui-base'
					]
				},
				/*
				 * Misc Modules
				 */
				'swfupload': {
					fullpath: javaScriptRootPath + '/misc/swfupload/swfupload.js'
				},
				'swfobject': {
					fullpath: javaScriptRootPath + '/misc/swfobject.js'
				},
				/*
				 * Portal Modules
				 */
				'portal-aui-lang': {
					fullpath: javaScriptRootPath + LiferayAUI.getLangPath(),
					requires: [
						'aui-calendar-deprecated'
					]
				}
			}
		);

		// addPlugin(
		// 	{
		// 		group: 'alloy',
		// 		name: 'portal-aui-lang',
		// 		test: function(A) {
		// 			return true;
		// 		},
		// 		trigger: 'calendar-deprecated'
		// 	}
		// );
	},
	'',
	{
		requires: []
	}
);