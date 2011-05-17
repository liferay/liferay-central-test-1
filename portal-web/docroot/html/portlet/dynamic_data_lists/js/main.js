AUI().add(
	'liferay-portlet-dynamic-data-lists',
	function(A) {
		var AArray = A.Array;

		var Lang = A.Lang;

		var DDL = Liferay.Service.DDL;

		var DDLRecord = DDL.DDLRecord;

		var DDLRecordSet = DDL.DDLRecordSet;

		var getObjectKeys = A.Object.keys;

		var JSON = A.JSON;

		var EMPTY_FN = A.Lang.emptyFn;

		var STR_EMPTY = '';

		var SpreadSheet = A.Component.create(
			{
				ATTRS: {
					portletNamespace: {
						value: STR_EMPTY,
						validator: Lang.isString
					},

					recordsetId: {
						value: 0,
						validator: Lang.isNumber
					}
				},

				EXTENDS: A.DataTable.Base,

				NAME: 'spreadsheet',

				prototype: {
					initializer: function() {
						var instance = this;

						var recordset = instance.get('recordset');

						recordset.on('update', instance._onRecordUpdate, instance);
					},

					addEmptyRows: function(num) {
						var instance = this;

						var columnset = instance.get('columnset');
						var recordset = instance.get('recordset');

						var emptyRows = SpreadSheet.buildEmptyRecords(num, getObjectKeys(columnset.keyHash));

						recordset.add(emptyRows);

						instance._uiSetRecordset(recordset);

						instance._fixPluginsUI();
					},

					addRecord: function(displayIndex, fieldsMap, callback) {
						var instance = this;

						callback = A.bind(callback || EMPTY_FN, instance);

						var recordsetId = instance.get('recordsetId');

						DDLRecord.addRecord(
							{
								groupId: themeDisplay.getScopeGroupId(),
								recordSetId: recordsetId,
								fieldsMap: JSON.stringify(fieldsMap),
								displayIndex: displayIndex,
								serviceContext: JSON.stringify(
									{
										scopeGroupId: themeDisplay.getScopeGroupId(),
										userId: themeDisplay.getUserId()
									}
								)
							},
							callback
						);
					},

					updateMinDisplayRows: function(minDisplayRows, callback) {
						var instance = this;

						callback = A.bind(callback || EMPTY_FN, instance);

						var recordsetId = instance.get('recordsetId');

						DDLRecordSet.updateMinDisplayRows(
							{
								recordSetId: recordsetId,
								minDisplayRows: minDisplayRows,
								serviceContext: JSON.stringify(
									{
										scopeGroupId: themeDisplay.getScopeGroupId(),
										userId: themeDisplay.getUserId()
									}
								)
							},
							callback
						);
					},

					updateRecord: function(recordId, displayIndex, fieldsMap, merge, callback) {
						var instance = this;

						callback = A.bind(callback || EMPTY_FN, instance);

						DDLRecord.updateRecord(
							{
								recordId: recordId,
								fieldsMap: JSON.stringify(fieldsMap),
								displayIndex: displayIndex,
								merge: merge,
								serviceContext: JSON.stringify(
									{
										scopeGroupId: themeDisplay.getScopeGroupId(),
										userId: themeDisplay.getUserId()
									}
								)
							},
							callback
						);
					},

					_normalizeRecordData: function(data) {
						var instance = this;

						var normalized = {};

						A.each(
							data,
							function(item, index, collection) {
								normalized[index] = instance._normalizeValue(item);
							}
						);

						delete normalized.displayIndex;
						delete normalized.recordId;

						return normalized;
					},

					_normalizeValue: function(value) {
						var instance = this;

						if (Lang.isArray(value)) {
							value = value.join();
						}

						return String(value);
					},

					_onRecordUpdate: function(event) {
						var instance = this;

						var recordIndex = event.index;

						AArray.each(
							event.updated,
							function(item, index, collection) {
								var data = item.get('data');

								var fieldsMap = instance._normalizeRecordData(data);

								if (data.recordId > 0) {
									instance.updateRecord(data.recordId, recordIndex, fieldsMap, true);
								}
								else {
									instance.addRecord(
										recordIndex,
										fieldsMap,
										function(json) {
											if (json.recordId > 0) {
												data.recordId = json.recordId;
											}
										}
									);
								}
							}
						);
					}
				},

				buildDataTableColumnset: function(columnset, structure) {
					var instance = this;

					AArray.each(
						columnset,
						function(item, index, collection) {
							item.key = item.name;

							if (item.editable) {
								var dataType = item.dataType;
								var label = item.label;
								var required = item.required;
								var type = item.type;

								var EditorClass = instance.TYPE_EDITOR[type] || A.TextCellEditor;

								var config = {
									validator: {
										rules: {}
									}
								};

								var elementName = 'value';

								if (type === 'checkbox') {
									elementName = label;

									config.options = [label];
								}
								else if ((type === 'radio') || (type === 'select')) {
									var structureField = instance.findStructureFieldByKey(structure, item.key);

									config.options = instance.getCellEditorOptions(structureField.options);
								}

								var validatorRules = config.validator.rules;
								var validatorRuleName = instance.DATATYPE_VALIDATOR[dataType];

								validatorRules[elementName] = {
									required: required
								};

								if (validatorRuleName) {
									validatorRules[elementName][validatorRuleName] = true;
								}

								item.editor = new EditorClass(config);
							}
						}
					);

					return columnset;
				},

				buildEmptyRecords: function(num, keys) {
					var instance = this;

					var emptyRows = [];

					for (var i = 0; i < num; i++) {
						emptyRows.push(instance.getRecordModel(keys));
					}

					return emptyRows;
				},

				findStructureFieldByKey: function(structure, key) {
					var found = null;

					AArray.some(
						structure,
						function(item, index, collection) {
							found = item;

							return (found.key === key);
						}
					);

					return found;
				},

				getCellEditorOptions: function(options) {
					var normalized = {};

					AArray.each(
						options,
						function(item, index, collection) {
							normalized[item.name] = item.value;
						}
					);

					return normalized;
				},

				getRecordModel: function(keys) {
					var instance = this;

					var recordModel = {};

					AArray.each(
						keys,
						function(item, index, collection) {
							recordModel[item] = STR_EMPTY;
						}
					);

					return recordModel;
				},

				DATATYPE_VALIDATOR: {
					'double': 'number',
					'integer': 'digits',
					'long': 'digits'
				},

				TYPE_EDITOR: {
					'checkbox': A.CheckboxCellEditor,
					'ddm-date': A.DateCellEditor,
					'ddm-decimal': A.TextCellEditor,
					'ddm-integer': A.TextCellEditor,
					'ddm-number': A.TextCellEditor,
					'radio': A.RadioCellEditor,
					'select': A.DropDownCellEditor,
					'text': A.TextCellEditor,
					'textarea': A.TextAreaCellEditor
				}
			}
		);

		Liferay.SpreadSheet = SpreadSheet;
	},
	'',
	{
		requires: ['aui-datatable']
	}
);