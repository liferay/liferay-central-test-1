AUI().add(
	'liferay-staging',
	function(A) {
		var Lang = A.Lang;

		var Staging = {};

		var Branching = {
			init: function(options) {
				var instance = this;

				instance._namespace = options.namespace;
			},

			addBranch: function() {
				var instance = this;

				var branchDialog = instance._getBranchDialog();

				branchDialog.show();
			},

			addVariation: function(formAction) {
				var instance = this;

				var variationDialog = instance._getVariationDialog(formAction);

				variationDialog.show();
			},

			mergeBranch: function(options) {
				var instance = this;

				var mergeDialog = instance._getMergeDialog();
				var mergeDialogIO = mergeDialog.io;

				var data = mergeDialogIO.get('data');

				data.groupId = options.groupId;
				data.privateLayout = options.privateLayout;
				data.layoutSetBranchId = options.layoutSetBranchId;

				mergeDialogIO.set('data', data);
				mergeDialogIO.start();

				mergeDialog.show();
			},

			_getBranchDialog: function() {
				var instance = this;

				var branchDialog = instance._branchDialog;

				if (!branchDialog) {
					var namespace = instance._namespace;

					branchDialog = new A.Dialog(
						{
							align: {
								points: ['tc', 'tc']
							},
							bodyContent: A.one('#' + namespace + 'addBranch').show(),
							title: Liferay.Language.get('add-backstage'),
							modal: true,
							width: 530
						}
					).render();

					branchDialog.move(dialog.get('x'), dialog.get('y') + 100);

					instance._branchDialog = branchDialog;
				}

				return branchDialog;
			},

			_getVariationDialog: function(formAction) {
				var instance = this;

				var variationDialog = instance._variationDialog;

				if (!variationDialog) {
					var namespace = instance._namespace;

					variationDialog = new A.Dialog(
						{
							align: {
								points: ['tc', 'tc']
							},
							bodyContent: A.one('#' + namespace + 'addVariation').show(),
							title: Liferay.Language.get('new-page-variation'),
							modal: true,
							width: 530
						}
					).render();

					variationDialog.move(variationDialog.get('x'), variationDialog.get('y') + 100);

					instance._variationDialog = variationDialog;
				}

				return variationDialog;
			},

			_getMergeDialog: function() {
				var instance = this;

				var mergeDialog = instance._mergeDialog;

				if (!mergeDialog) {
					mergeDialog = new A.Dialog(
						{
							align: {
								points: ['tc', 'tc']
							},
							draggable: true,
							modal: true,
							title: Liferay.Language.get('merge-changes-from-branch'),
							width: 530
						}
					).plug(
						A.Plugin.IO,
						{
							autoLoad: false,
							data: {
								doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
								p_l_id: themeDisplay.getPlid(),
								redirect: Liferay.currentURL
							},
							uri: themeDisplay.getPathMain() + '/staging_bar/merge_layout_set_branch'
						}
					).render();

					mergeDialog.move(mergeDialog.get('x'), mergeDialog.get('y') + 100);

					mergeDialog.bodyNode.delegate(
						'click',
						function(event) {
							var node = event.currentTarget;

							instance._onMergeBranch(node)
						},
						'a.layout-set-branch'
					);

					instance._mergeDialog = mergeDialog;
				}

				return mergeDialog;
			},

			_onMergeBranch: function(node) {
				var instance = this;

				var namespace = instance._namespace;

				var addBranch = A.one('#' + namespace + 'addBranch');

				var mergeLayoutSetBranchId = node.attr('data-layoutSetBranchId');
				var mergeLayoutSetBranchName = node.attr('data-layoutSetBranchName');

				if (confirm(Liferay.Language.get('are-you-sure-you-want-to-merge-changes-from-backstage') + ' ' + mergeLayoutSetBranchName)) {
					var form = A.one('#' + namespace + 'fm4');

					form.one('#' + namespace + 'mergeLayoutSetBranchId').val(mergeLayoutSetBranchId);

					submitForm(form);
				}
			}
		};

		var Dockbar = {
			init: function(options) {
				var instance = this;

				var namespace = options.namespace;

				instance._namespace = namespace;

				Dockbar.backstageToolbar = new A.Toolbar(
					{
						activeState: false,
						boundingBox: '#' + namespace + 'backstageToolbar',
						children: [
							{
							type: 'ToolbarSpacer'
							},
							{
								handler: function(event) {
									instance._onViewHistory(event);
								},
								icon: 'clock',
								label: Liferay.Language.get('history')
							}
						]
					}
				).render();

				Dockbar.undoButton = new A.ButtonItem(
					{
						handler: function(event) {
							instance._onUndoRevision(event);
						},
						icon: 'arrowreturnthick-1-b',
						title: Liferay.Language.get('undo')
					}
				);
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
							width: 400
						}
					).plug(
						A.Plugin.IO,
						{
							autoLoad: false,
							data: {
								doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
								p_l_id: themeDisplay.getPlid(),
								redirect: Liferay.currentURL
							},
							uri: themeDisplay.getPathMain() + '/staging_bar/view_layout_revisions'
						}
					).render();

					graphDialog.move(graphDialog.get('x'), graphDialog.get('y') + 100);

					graphDialog.bodyNode.delegate(
						'click',
						instance._selectRevision,
						'li.layout-revision a.selection-handle'
					);

					instance._graphDialog = graphDialog;
				}

				return graphDialog;
			},

			_onUndoRevision: function(event) {
				var instance = this;

				if (confirm(Liferay.Language.get('are-you-sure-you-want-to-undo-your-last-changes'))) {
					var namespace = instance._namespace;

					var form = A.one('#' + namespace + 'fm');

					form.one('#' + namespace + 'cmd').val('delete_layout_revision');
					form.one('#' + namespace + 'updateRecentLayoutRevisionId').val(true);

					submitForm(form);
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

			_selectRevision: function(event) {
				var node = event.currentTarget;

				A.io.request(
					themeDisplay.getPathMain() + '/portal/update_layout',
					{
						data: {
							doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
							p_l_id: themeDisplay.getPlid(),
							cmd: 'select_layout_revision',
							layoutRevisionId: node.attr('data-layoutRevisionId'),
							layoutSetBranchId: node.attr('data-layoutSetBranchId')
						},
						on: {
							success: function(event, id, obj) {
								window.location.reload();
							}
						}
					}
				);
			},

			_updateMajor: function() {
				if (confirm(Liferay.Language.get('are-you-sure-you-want-to-save-your-changes-all-the-undo-steps-will-be-lost'))) {
					var namespace = instance._namespace;

					var form = A.one('#' + namespace + 'fm');

					form.one('#' + namespace + 'cmd').val('update_major');

					submitForm(form);
				}
			}
		};

		Staging.Branching = Branching;
		Staging.Dockbar = Dockbar;
		Liferay.Staging = Staging;
	},
	'',
	{
		requires: ['aui-dialog', 'aui-io-plugin', 'aui-toolbar', 'liferay-portlet-url']
	}
);