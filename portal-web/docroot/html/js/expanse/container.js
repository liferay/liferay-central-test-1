(function() {
	var Dom = Expanse.Dom;
	var Event = Expanse.Event;
	var Widget = YAHOO.widget;

	Expanse.Module = new Expanse.Class(Widget.Module);
	Expanse.Overlay = new Expanse.Class(Widget.Overlay);
	Expanse.OverlayManager = new Expanse.Class(Widget.OverlayManager);
	Expanse.Dialog = new Expanse.Class(Widget.Dialog);
	Expanse.SimpleDialog = new Expanse.Class(Widget.SimpleDialog);
	Expanse.Tooltip = new Expanse.Class(Widget.Tooltip);
	Expanse.Panel = new Expanse.Class(Widget.Panel);

	Expanse.Panel = Expanse.Panel.extend(
		{
			buildMask: function() {
				var instance = this;

				instance._super.apply(instance, arguments);

				Dom.addClass(instance.mask, 'exp-mask');
			}
		}
	);

	Expanse.extend(
		Widget.Module,
		{
		    CSS_BODY: 'exp-body',
		    CSS_FOOTER: 'exp-footer',
		    CSS_HEADER: 'exp-header',
			CSS_MODULE: 'exp-module'
		}
	);

	Expanse.extend(
		Widget.Overlay,
		{
			CSS_OVERLAY: 'exp-overlay'
		}
	);

	Expanse.extend(
		Widget.Tooltip,
		{
			CSS_TOOLTIP: 'exp-tooltip'
		}
	);

	Expanse.extend(
		Widget.Panel,
		{
			CSS_PANEL: 'exp-panel',
			CSS_PANEL_CONTAINER: 'exp-panel-container'
		}
	);

	Expanse.extend(
		Widget.Dialog,
		{
			CSS_DIALOG: 'exp-dialog'
		}
	);

	Expanse.extend(
		Widget.SimpleDialog,
		{
			CSS_SIMPLEDIALOG: 'exp-simple-dialog',
			ICON_CSS_CLASSNAME: 'exp-icon'
		}
	);

	var IE_SYNC = (Liferay.Browser.isIe() && Liferay.Browser.getMajorVersion() < 7);

	Expanse.Popup = Expanse.Panel.extend(
		{
			initialize: function(options) {
				var instance = this;

				if (options.width && (options.width != 'auto')) {
					options.width = (parseInt(options.width, 10) || 300) + 'px';
				}

				if (options.height && (options.height != 'auto')) {
					options.height = (parseInt(options.height, 10) || 300) + 'px';
				}

				options.zIndex = options.zIndex || 9999;

				if (options.xy) {
					var position = options.xy;

					var x = position[0];
					var y = position[1];

					var centerValue = 'center';

					if (x == centerValue || y == centerValue) {
						var win = Expanse.getWindow();

						var width = parseInt(options.width, 10) || 0;
						var height = parseInt(options.width, 10) || 0;

						if (x == centerValue) {
							x = (win.width() / 2 - width / 2);
						}
						else if (y == centerValue) {
							y = (win.height() / 2 - height / 2);
						}

						options.xy = [x, y];
					}
				}

				var el = options.el || Expanse.generateId();

				instance._super(el, options);

				if (options.header) {
					instance.setHeader(options.header);
				}

				if (options.className) {
					Dom.addClass(instance.element, options.className);
				}

				if (options.body) {
					instance.setBody(options.body);
				}
				else if (options.url) {
					instance.setBody('<div class="loading-animation" />');

					var onSuccess = options.urlSuccess;
					var url = options.url.replace(/p_p_state=(maximized|pop_up)/g, 'p_p_state=exclusive');

					if (!onSuccess) {
						onSuccess = function(message) {
							instance.setBody('');

							jQuery(instance.body).html(message);
						};
					}

					jQuery.ajax(
						{
							url: url,
							data: options.urlData,
							success: onSuccess
						}
					);
				}

				if (options.onOpen) {
					instance.subscribe('render', options.onOpen, instance, true);
				}

				if (options.footer) {
					instance.setFooter(options.footer);
				}

				instance._options = options;
				instance._options._el = el;

				if (!options.deferRender) {
					instance.render(options.renderTo || document.body);

					instance.show();

					instance._onRender();
				}
				else {
					instance.renderEvent.subscribe(instance._onRender, instance, true);
				}

				Expanse.Popup.Manager.register(instance);

				return instance;
			},

			closePopup: function() {
				var instance = this;

				if (instance._destroyOnClose !== false) {
					instance.destroy();
				}
				else {
					instance.hide();
				}
			},

			_onRender: function() {
				var instance = this;

				var options = instance._options;
				var el = options._el;

				if (options.center) {
					instance.center();
				}

				var closeEvent = 'destroy';

				instance._destroyOnClose = options.destroyOnClose;

				if (options.destroyOnClose !== false) {
					Event.on(instance.close, 'click', instance.destroy, instance, true);
				}
				else {
					if (options.onClose) {
						closeEvent = 'hide';
					}
				}

				if (options.onClose) {
					instance.subscribe(closeEvent, options.onClose, instance, true);
				}

				if (options.messageId) {
					instance.body.id = options.messageId;
				}

				if (options.resizable !== false && Expanse.Resize) {
					var resize = new Expanse.Resize(
						el,
						{
							handles: options.handles || ['r', 'b', 'br'],
							height: options.height,
							proxy: true,
							width: options.width
						}
					);

					var paddingTop = Dom.getStyle(instance.body, 'padding-top');
					var paddingBottom = Dom.getStyle(instance.body, 'padding-bottom');

					paddingTop = parseInt(paddingTop, 10) || 10;
					paddingBottom = parseInt(paddingBottom, 10) || 10;

					instance._panelPaddingVertical = paddingTop + paddingBottom;

					resize.on('resize', instance._resizeBody, instance, true);

					if (options.onResize) {
						resize.on('resize', options.onResize, instance, true);
					}

					instance.resizable = resize;
				}

				if (options.draggable !== false) {
					var el = Dom.get(instance.id);

					Dom.setStyle(el, 'position', 'relative');
				}
			},

			_resizeBody: function(options) {
				var instance = this;

				var panelHeight = options.height;

				var headerHeight = instance.header.offsetHeight;
				var footerHeight = 0;

				if (instance.footer) {
					footerHeight = instance.footer.offsetHeight;
				}

				var bodyHeight = panelHeight - headerHeight - footerHeight;

				Dom.setStyle(instance.body, 'height', (bodyHeight - instance._panelPaddingVertical) + 'px');

				if (IE_SYNC) {
					instance.sizeUnderlay();
					instance.syncIframe();
				}
			}
		}
	);

	Expanse.Popup.Manager = new Expanse.OverlayManager();

	Expanse.extend(
		Expanse.Popup,
		{
			close: function(el) {
				var instance = this;

				var obj = el;

				if (!el.jquery) {
					obj = jQuery(el);
				}

				if (!obj.is('.exp-panel')) {
					obj = obj.parents('.exp-panel:first');
				}

				if (obj.length) {
					var id = obj[0].id;
					var popup = Expanse.Popup.Manager.find(id);

					if (popup) {
						popup.destroy();
					}
				}
			},

			update: function(id, url) {
				var instance = this;

				var obj = jQuery(id);

				obj.html('<div class="loading-animation"></div>');
				obj.load(url);
			}
		}
	);
})();