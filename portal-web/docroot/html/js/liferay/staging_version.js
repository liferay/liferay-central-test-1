AUI().add(
	'liferay-staging-version',
	function(A) {
		var Lang = A.Lang;

		var Stagingbar = Liferay.Stagingbar;

		var MAP_CMD_REVISION = {
			redo: 'redo_layout_revision',
			undo: 'undo_layout_revision'
		};

		A.mix(
			Stagingbar,
			{
				_onInit: function(options) {
					var instance = this;

					var namespace = instance._namespace;

					var layoutRevisionToolbar = new A.Toolbar(
						{
							activeState: false,
							boundingBox: '#' + namespace + 'layoutRevisionToolbar',
							children: [
								{
								type: 'ToolbarSpacer'
								},
								{
									handler: A.bind(instance._onViewHistory, instance),
									icon: 'clock',
									label: Liferay.Language.get('history')
								}
							]
						}
					).render();

					Stagingbar.layoutRevisionToolbar = layoutRevisionToolbar;

					var redoText = Liferay.Language.get('redo');
					var undoText = Liferay.Language.get('undo');

					Stagingbar.redoButton = new A.ButtonItem(
						{
							handler: A.bind(instance._onRevisionChange, instance, 'redo'),
							icon: 'arrowreturnthick-1-r',
							label: redoText,
							title: redoText
						}
					);

					Stagingbar.undoButton = new A.ButtonItem(
						{
							handler: A.bind(instance._onRevisionChange, instance, 'undo'),
							icon: 'arrowreturnthick-1-b',
							label: undoText,
							title: undoText
						}
					);

					var layoutRevisionDetails = A.one('#' + namespace + 'layoutRevisionDetails');

					if (layoutRevisionDetails) {
						Liferay.publish(
							'updatedLayout',
							{
								defaultFn: function(event) {
									A.io.request(
										themeDisplay.getPathMain() + '/staging_bar/view_layout_revision_details',
										{
											data: {
												p_l_id: themeDisplay.getPlid()
											},
											method: 'GET',
											on: {
												success: function(event, id, obj) {
													var response = this.get('responseData');

													layoutRevisionDetails.plug(A.Plugin.ParseContent);

													layoutRevisionDetails.setContent(response);
												}
											}
										}
									);
								}
							}
						);
					}
				},

				_getGraphDialog: function() {
					var instance = this;

					var graphDialog = instance._graphDialog;

					if (!graphDialog) {
						graphDialog = new A.Dialog(
							{
								align: {
									points: ['tc', 'tc']
								},
								draggable: true,
								modal: true,
								title: Liferay.Language.get('history'),
								width: 600
							}
						).plug(
							A.Plugin.IO,
							{
								autoLoad: false,
								data: {
									doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
									p_l_id: themeDisplay.getPlid(),
									p_p_isolated: true,
									redirect: Liferay.currentURL
								},
								uri: themeDisplay.getPathMain() + '/staging_bar/view_layout_revisions'
							}
						).render();

						graphDialog.move(graphDialog.get('x'), graphDialog.get('y') + 100);

						graphDialog.bodyNode.delegate(
							'click',
							function(event) {
								instance._selectRevision(event.currentTarget);
							},
							'a.layout-revision.selection-handle'
						);

						instance._graphDialog = graphDialog;
					}

					return graphDialog;
				},

				_onRevisionChange: function(type, event) {
					var instance = this;

					var confirmText = MAP_TEXT_REVISION[type];
					var cmd = MAP_CMD_REVISION[type];

					if (confirm(confirmText)) {
						var button = event.currentTarget.get('contentBox');

						instance._updateRevision(
							cmd,
							button.attr('data-layoutRevisionId'),
							button.attr('data-layoutSetBranchId')
						);
					}
				},

				_onViewHistory: function(event) {
					var instance = this;

					var namespace = instance._namespace;

					var form = A.one('#' + namespace + 'fm');

					var layoutRevisionId = form.one('#' + namespace + 'layoutRevisionId').val();
					var layoutSetBranchId = form.one('#' + namespace + 'layoutSetBranchId').val();

					var graphDialog = instance._getGraphDialog();

					var graphDialogIO = graphDialog.io;

					var data = graphDialogIO.get('data');

					data.layoutRevisionId = layoutRevisionId;
					data.layoutSetBranchId = layoutSetBranchId;

					graphDialogIO.set('data', data);
					graphDialogIO.start();

					graphDialog.show();
				},

				_selectRevision: function(node) {
					var instance = this;

					instance._updateRevision(
						node,
						node.attr('data-layoutRevisionId'),
						node.attr('data-layoutSetBranchId')
					);
				},

				_updateRevision: function(cmd, layoutRevisionId, layoutSetBranchId) {
					A.io.request(
						themeDisplay.getPathMain() + '/portal/update_layout',
						{
							data: {
								cmd: cmd,
								doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
								layoutRevisionId: layoutRevisionId,
								layoutSetBranchId: layoutSetBranchId,
								p_l_id: themeDisplay.getPlid()
							},
							on: {
								success: function(event, id, obj) {
									window.location.reload();
								}
							}
						}
					);
				}
			}
		);

		Liferay.on('initStagingbar', Stagingbar._onInit, Stagingbar);
	},
	'',
	{
		requires: ['aui-button-item', 'liferay-staging']
	}
);