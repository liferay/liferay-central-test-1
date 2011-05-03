AUI().add(
	'liferay-list-view',
	function(A) {
		var Lang = A.Lang;
		var isString = Lang.isString;
		var getClassName = A.getClassName;

		var CONTENT_BOX = 'contentBox';

		var EVENT_ITEM_CHOSEN = 'itemChosen';

		var NAME = 'liferaylistview';

		var CSS_DATA_CONTAINER = getClassName(NAME, 'data', 'container');

		var STR_BOTTOM = 'bottom';

		var STR_LEFT = 'left';

		var STR_REGION = 'region';

		var STR_RIGHT = 'right';

		var STR_TOP = 'top';

		var TPL_DATA_CONTAINER = '<div class="' + CSS_DATA_CONTAINER + ' yui3-aui-helper-hidden"></div>';

		var ListView = A.Component.create(
			{
				ATTRS: {
					data: {
						setter: '_setData',
						validator: '_validateData',
						value: null
					},

					direction: {
						validator: '_validateDirection',
						value: STR_LEFT
					},

					itemChosen: {
						validator: isString,
						value: 'click'
					},

					itemSelector: {
						validator: isString,
						value: null
					},

					itemAttributes: {
						setter: A.Array,
						validator: '_validateItemAttributes',
						value: 'href'
					},

					transitionConfig: {
						validator: Lang.isObject,
						value: {
							duration: 0.3,
							easing: 'ease-out',
							left: 0,
							top: 0
						}
					},

					useTransition: {
						validator: Lang.isBoolean,
						value: true
					}
				},

				NAME: NAME,

				prototype: {
					initializer: function() {
						var instance = this;

						instance._transitionCompleteProxy = A.bind(instance._onTransitionCompleted, instance);
					},

					renderUI: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						var dataContainer = A.Node.create(TPL_DATA_CONTAINER);

						boundingBox.append(dataContainer);

						instance._dataContainer = dataContainer;
					},

					bindUI: function() {
						var instance = this;

						var contentBox = instance.get(CONTENT_BOX);

						var itemChosenEvent = instance.get(EVENT_ITEM_CHOSEN);
						var itemSelector = instance.get('itemSelector');

						instance._itemChosenHandle = contentBox.delegate(itemChosenEvent, instance._onItemChosen, itemSelector, instance);

						instance.after('dataChange', instance._afterDataChange);
					},

					destructor: function() {
						var instance = this;

						if (instance._itemChosenHandle) {
							instance._itemChosenHandle.detach();
						}

						if (instance._dataContainer) {
							instance._dataContainer.destroy(true);
						}
					},

					_afterDataChange: function(event) {
						var instance = this;

						var useTransition = instance.get('useTransition');

						var newData = event.newVal;

						if (useTransition) {
							instance._dataContainer.setContent(newData);

							instance._moveContainer();
						}
						else {
							instance.get(CONTENT_BOX).setContent(newData);
						}
					},

					_moveContainer: function() {
						var instance = this;

						var contentBox = instance.get(CONTENT_BOX);

						var targetRegion = contentBox.get(STR_REGION);

						instance._setDataContainerPosition(targetRegion);

						var dataContainer = instance._dataContainer;

						dataContainer.show();

						var transitionConfig = instance.get('transitionConfig');

						dataContainer.transition(transitionConfig, instance._transitionCompleteProxy);
					},

					_onItemChosen: function(event) {
						var instance = this;

						var itemAttributes = instance.get('itemAttributes');

						if (itemAttributes) {
							event.preventDefault();

							var attributesData = {};

							var target = event.currentTarget;

							A.Array.each(
								itemAttributes,
								function(item, index, collection) {
									var attributeData = target.getAttribute(item);

									attributesData[item] = attributeData;
								}
							);

							instance.fire(
								EVENT_ITEM_CHOSEN,
								{
									item: target,
									attributes: attributesData
								}
							);
						}
					},

					_onTransitionCompleted: function() {
						var instance = this;

						var dataContainer = instance._dataContainer;

						instance.get(CONTENT_BOX).html(dataContainer.html());

						dataContainer.hide();
						dataContainer.empty();
					},

					_setData: function(value) {
						if (isString(value)) {
							value = A.Node.create(value);
						}

						return value;
					},

					_setDataContainerPosition: function(targetRegion) {
						var instance = this;

						targetRegion = targetRegion || instance.get(CONTENT_BOX).get(STR_REGION);

						var direction = instance.get('direction');

						var dataContainer = instance._dataContainer;

						var styles = {
							left: 0,
							top: 0
						};

						var valid = true;

						if (direction == STR_LEFT) {
							styles.left = targetRegion.width;
						}
						else if (direction == STR_RIGHT) {
							styles.left = -(targetRegion.width);
						}
						else if (direction == STR_TOP) {
							styles.top = -(targetRegion.height);
						}
						else if (direction == STR_BOTTOM) {
							styles.top = targetRegion.height;
						}
						else {
							valid = false;
						}

						dataContainer.setStyles(styles);
					},

					_validateData: function(value) {
						return isString(value) || A.instanceOf(value, A.Node);
					},

					_validateDirection: function(value) {
						return value === STR_BOTTOM || value === STR_LEFT ||
							value === STR_RIGHT || value === STR_TOP;
					},

					_validateItemAttributes: function(value) {
						return isString(value) || Lang.isArray(value);
					}
				}
			}
		);

		Liferay.ListView = ListView;
	},
	'',
	{
		requires: ['aui-base']
	}
);