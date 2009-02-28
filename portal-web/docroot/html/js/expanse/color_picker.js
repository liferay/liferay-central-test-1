(function() {
	var Dom = Expanse.Dom;
	var Event = Expanse.Event;

	Expanse.ColorPicker = new Expanse.Class(YAHOO.widget.ColorPicker);

	Expanse.ColorPicker.implement(
		{
			ID: {
			    R: 'exp-picker-r',
			    R_HEX: 'exp-picker-rhex',
			    G: 'exp-picker-g',
			    G_HEX: 'exp-picker-ghex',
			    B: 'exp-picker-b',
			    B_HEX: 'exp-picker-bhex',
			    H: 'exp-picker-h',
			    S: 'exp-picker-s',
			    V: 'exp-picker-v',
			    PICKER_BG: 'exp-picker-bg',
			    PICKER_THUMB: 'exp-picker-thumb',
			    HUE_BG: 'exp-picker-hue-bg',
			    HUE_THUMB: 'exp-picker-hue-thumb',
			    HEX: 'exp-picker-hex',
			    SWATCH: 'exp-picker-swatch',
			    WEBSAFE_SWATCH: 'exp-picker-websafe-swatch',
			    CONTROLS: 'exp-picker-controls',
			    RGB_CONTROLS: 'exp-picker-rgb-controls',
			    HSV_CONTROLS: 'exp-picker-hsv-controls',
			    HEX_CONTROLS: 'exp-picker-hex-controls',
			    HEX_SUMMARY: 'exp-picker-hex-summary',
			    CONTROLS_LABEL: 'exp-picker-controls-label'
			},

			IMAGE: {
				HUE_THUMB: themeDisplay.getPathThemeImages() + '/color_picker/color_indic.png',
				PICKER_THUMB: themeDisplay.getPathThemeImages() + '/color_picker/select.png'
			}
		}
	);

	Expanse.ColorPickerPanel = Expanse.Panel.extend(
		{
			initialize: function(options) {
				var instance = this;

				var defaults = {
					constraintoviewport: true,
					preventcontextoverlap: true,
					visible: false
				};

				options = Expanse.extend(defaults, options);

				instance.options = options;

				instance._onClose = options.onClose;
				instance._onChange = options.onChange;
				instance._buttonContext = options.buttonContext || document.body;
				instance._hasImage =  options.hasImage || false;
				instance._item = jQuery(options.item || '.use-colorpicker', instance._buttonContext);

				instance._insertImages();

				options.button = instance._button;

				options.zIndex = Liferay.zIndex.ALERT + 10000;

				options.container = instance.panel;

				if (options.button && !options.context) {
					options.button = Expanse.getEl(options.button);

					options.context = [options.button, 'tl', 'bl', ['show']];
				}

				instance._super(options.el || Dom.generateId(), options);

				var pickerId = Dom.generateId();

				instance.setBody('<div id="' + pickerId + '"></div>');

				instance._pickerId = pickerId;
				instance.renderEvent.subscribe(instance.createPicker);

				Dom.addClass(instance.element, 'exp-picker-panel');

				instance.render(document.body);

				var inputs = instance.element.getElementsByTagName('input');

				if (inputs.length && !inputs[0].getAttribute('type')) {
					var input;

					for (var i = inputs.length - 1; i >= 0; i--) {
						input = inputs[i];

						input.setAttribute('type', input.type || 'text');
					}
				}

				Event.on(options.button, 'click', instance.toggle, instance, true);
				Event.on(instance.element, 'click', instance._swallowEvent, instance, true);

				instance.picker.on('rgbChange', instance.onColorChange, instance, true);

				if (instance._onClose) {
					instance.hideEvent.subscribe(instance._onClose, instance, true);
				}
			},

			blur: function(event) {
				var instance = this;

				if (Event.getTarget(event) != instance._item[0]) {
					instance.hide();

					Event.un(document, 'click', arguments.callee);
				}
			},

			createPicker: function() {
				var instance = this;

				var options = instance.options;

				instance.picker = new Expanse.ColorPicker(instance._pickerId,
					{
						container: instance,
						showhexsummary: false,
						showhsvcontrols: false,
						showwebsafe: false
					}
				);
			},

			onColorChange: function(event, args) {
				var instance = this;

				var hexValue = instance.picker.get('hex');

				instance._currentInput.val('#' + hexValue);

				if (instance._onChange) {
					instance._onChange(args);
				}
			},

			show: function() {
				var instance = this;

				instance._super.apply(instance, arguments);

				Event.on(document, 'click', instance.blur, instance, true);
			},

			toggle: function(event) {
				var instance = this;

				var visible = instance.cfg.getProperty('visible');

				if (visible) {
					instance.hide();
				}
				else {
					instance.show();
				}

				if (event) {
					Event.stopEvent(event);
				}
			},

			_insertImages: function() {
				var instance = this;

				var buttonContext = instance._buttonContext;

				var items = instance._item;

				var colorPickerImgHTML = '<img alt="' + Liferay.Language.get('color-picker') + '" class="lfr-colorpicker-img" src="' + themeDisplay.getPathThemeImages() + '/color_picker/color_picker.png" title="' + Liferay.Language.get('color-picker') + '" />';

				var colorPickerImg;

				if (instance._hasImage) {
					colorPickerImg = items;
				}
				else {
					colorPickerImg = jQuery(colorPickerImgHTML);

					items.after(colorPickerImg);
				}

				instance._button = colorPickerImg;
				instance._currentInput = instance._button.prev();
			},

			_swallowEvent: function(event) {
				var instance = this;

				Event.stopEvent(event);
			}
		}
	);
})();