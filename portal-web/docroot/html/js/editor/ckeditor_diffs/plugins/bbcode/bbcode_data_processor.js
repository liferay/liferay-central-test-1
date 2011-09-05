(function() {
	var NEW_LINE = '\n';

	var REGEX_BR = /<br>(<\/?(?:li|ol|ul|tr|td|th|table)(?: |>))/gi;

	var REGEX_COLOR_RGB = /^rgb\s*\(\s*([01]?\d\d?|2[0-4]\d|25[0-5])\,\s*([01]?\d\d?|2[0-4]\d|25[0-5])\,\s*([01]?\d\d?|2[0-4]\d|25[0-5])\s*\)$/;

	var REGEX_EM = /em$/i;

	var REGEX_ESCAPE_REGEX = /[-[\]{}()*+?.,\\^$|#\s]/g;

	var REGEX_LASTCHAR_NEWLINE = /(\r?\n\s*)$/;

	var REGEX_LIST_ALPHA = /(upper|lower)-alpha/i;

	var REGEX_NEWLINE = /\r?\n/g;

	var REGEX_NOT_WHITESPACE = /[^\t\n\r ]/;

	var REGEX_PERCENT = /%$/i;

	var REGEX_PX = /px$/i;

	var TAG_BLOCKQUOTE = 'blockquote';

	var TAG_CODE = 'code';

	var TAG_CITE = 'cite';

	var TAG_DIV = 'div';

	var TAG_LI = 'li';

	var TAG_PARAGRAPH = 'p';

	var TAG_PRE = 'pre';

	var TAG_TABLE = 'table';

	var TEMPLATE_IMAGE = '<img src="{image}">';

	CKEDITOR.plugins.add(
		'bbcode_data_processor',
		{
			requires: ['htmlwriter'],

			init: function(editor) {
				editor.dataProcessor = new CKEDITOR.htmlDataProcessor(editor);

				var writer = editor.dataProcessor.writer;

				writer.setRules(
					TAG_PARAGRAPH,
					{
						breakBeforeClose: false
					}
				);

				editor.on(
					'paste',
					function(event) {
						var data = event.data;

						var htmlData = data.html;

						htmlData = CKEDITOR.htmlDataProcessor.prototype.toDataFormat(htmlData);

						data.html = htmlData;
					},
					editor.element.$
				);

				editor.fire('customDataProcessorLoaded');
			}
		}
	);

	CKEDITOR.htmlDataProcessor.prototype = {
		toDataFormat: function(html, fixForBody ) {
			var instance = this;

			var data = instance._convert(html);

			return data;
		},

		toHtml: function(data, fixForBody) {
			var instance = this;

			if (!instance._bbcodeParser) {
				instance._bbcodeParser = new CKEDITOR.BBCodeParser();
			}

			data = instance._bbcodeParser.parse(data);
			data = data.replace(REGEX_BR, '$1');

			var emoticonImages = CKEDITOR.config.smiley_images;
			var emoticonSymbols = CKEDITOR.config.smiley_symbols;
			var imagePath = CKEDITOR.config.smiley_path;

			var length = emoticonSymbols.length;

			for (var i = 0; i < length; i++) {
				var image = TEMPLATE_IMAGE.replace('{image}', imagePath + emoticonImages[i]);

				var escapedSymbol = emoticonSymbols[i].replace(REGEX_ESCAPE_REGEX, "\\$&");

				data = data.replace(new RegExp(escapedSymbol, 'g'), image);
			}

			return data;
		},

		_allowNewLine: function(element) {
			var instance = this;

			var allowNewLine = true;

			if (!instance._inPRE) {
				var parentNode = element.parentNode;

				if (parentNode) {
					var parentTagName = parentNode.tagName;

					if (parentTagName) {
						parentTagName = parentTagName.toLowerCase();

						if (parentTagName == TAG_PARAGRAPH && parentNode.style.cssText ) {
							allowNewLine = false;
						}
					}
				}
			}

			return allowNewLine;
		},

		_convert: function(data) {
			var instance = this;

			var node = document.createElement(TAG_DIV);
			node.innerHTML = data;

			instance._handle(node);

			var endResult = instance._endResult.join('');

			instance._endResult = null;

			return endResult;
		},

		_convertRGBToHex: function(color) {
			var red, green, blue;

			color = color.replace(
				REGEX_COLOR_RGB,
				function(mstr, red, green, blue, offset, string) {
					var r = parseInt(red, 10).toString(16);
					var g = parseInt(green, 10).toString(16);
					var b = parseInt(blue, 10).toString(16);

					r = r.length == 1 ? '0' + r : r;
					g = g.length == 1 ? '0' + g : g;
					b = b.length == 1 ? '0' + b : b;

					color = "#" + r + g + b;

					return color;
				}
			);

			return color;
		},

		_getBodySize: function() {
			var body = document.body;
			var style;

			if (document.defaultView.getComputedStyle) {
				style = document.defaultView.getComputedStyle(body, null);
			}
			else if (body.currentStyle) {
				style = body.currentStyle;
			}

			return parseFloat(style.fontSize, 10);
		},

		_getEmoticonSymbol: function(element) {
			var instance = this;

			var imagePath = element.getAttribute('src');

			if (imagePath) {
				var image = imagePath.substring(imagePath.lastIndexOf('/') + 1);
				var imageIndex = instance._getImageIndex(CKEDITOR.config.smiley_images, image);

				if (imageIndex >= 0) {
					return CKEDITOR.config.smiley_symbols[imageIndex];
				}
			}

			return null;
		},

		_getFontSize: function(fontSize) {
			var instance = this;

			var bodySize;

			if (REGEX_PX.test(fontSize)) {
				return instance._getFontSizePX(fontSize);
			}
			else if (REGEX_EM.test(fontSize)) {
				bodySize = instance._getBodySize();

				fontSize = parseFloat(fontSize, 10);
				fontSize = Math.round((fontSize * bodySize)) + 'px';

				return instance._getFontSize(fontSize);
			}
			else if (REGEX_PERCENT.test(fontSize)) {
				bodySize = instance._getBodySize();

				fontSize = parseFloat(fontSize, 10);
				fontSize = Math.round(((fontSize * bodySize) / 100)) + 'px';

				return instance._getFontSize(fontSize);
			}

			return fontSize;
		},

		_getFontSizePX: function(fontSize) {
			fontSize = parseInt(fontSize, 10);

			if (fontSize <= 10) {
				return '1';
			}
			else if (fontSize <= 12) {
				return '2';
			}
			else if (fontSize <= 16) {
				return '3';
			}
			else if (fontSize <= 18) {
				return '4';
			}
			else if (fontSize <= 24) {
				return '5';
			}
			else if (fontSize <= 32) {
				return '6';
			}
			else {
				return '7';
			}
		},

		_getImageIndex: function(array, image) {
			if (array.lastIndexOf) {
				return array.lastIndexOf(image);
			}
			else {
				for (var i = array.length - 1; i >= 0; i--) {
					var item = array[i];

					if (image === item) {
						return i;
					}
				}
			}

			return -1;
		},

		_isAllWS: function(node) {
			return node.isElementContentWhitespace || !(REGEX_NOT_WHITESPACE.test(node.data));
		},

		_isIgnorable: function(node) {
			var instance = this;

			var nodeType = node.nodeType;

			var result = (node.isElementContentWhitespace || nodeType == 8) ||
				((nodeType == 3) && instance._isAllWS(node));

			return result;
		},

		_isLastItemNewLine: function() {
			var instance = this;

			var endResult = instance._endResult;

			return (endResult && REGEX_LASTCHAR_NEWLINE.test(endResult.slice(-1)));
		},

		_handle: function(node) {
			var instance = this;

			if (!instance._endResult) {
				instance._endResult = [];
			}

			var children = node.childNodes;
			var length = children.length;
			var pushTagList = instance._pushTagList;

			for (var i = 0; i < length; i++) {
				var listTagsIn = [];
				var listTagsOut = [];

				var stylesTagsIn = [];
				var stylesTagsOut = [];

				var child = children[i];

				if (instance._isIgnorable(child) && !instance._inPRE) {
					continue;
				}

				instance._handleElementStart(child, listTagsIn, listTagsOut);
				instance._handleStyles(child, stylesTagsIn, stylesTagsOut);

				pushTagList.call(instance, listTagsIn);
				pushTagList.call(instance, stylesTagsIn);

				instance._handle(child);

				instance._handleElementEnd(child, listTagsIn, listTagsOut);

				pushTagList.call(instance, stylesTagsOut.reverse());
				pushTagList.call(instance, listTagsOut);
			}

			instance._handleData(node.data, node);
		},

		_handleBreak: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			if (instance._inPRE) {
				listTagsIn.push(NEW_LINE);
			}
			else {
				if (instance._allowNewLine(element)) {
					listTagsIn.push(NEW_LINE);
				}
			}
		},

		_handleCite: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			var parentNode = element.parentNode;

			if (parentNode && (parentNode.tagName.toLowerCase() === TAG_BLOCKQUOTE)) {
				if (!parentNode.getAttribute(TAG_CITE)) {
					var endResult = instance._endResult;

					for (var i = (endResult.length - 1); i >= 0; i--) {
						if (endResult[i] === '[quote]') {
							endResult[i] = '[quote=';

							listTagsOut.push(']');

							break;
						}
					}
				}
			}
		},

		_handleData: function(data, element) {
			var instance = this;

			if (data) {
				if (!instance._allowNewLine(element)) {
					data = data.replace(REGEX_NEWLINE, '');
				}

				instance._endResult.push(data);
			}
		},

		_handleElementEnd: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			var tagName = element.tagName;

			if (tagName) {
				tagName = tagName.toLowerCase();

				if (tagName == TAG_LI) {
					if (!instance._isLastItemNewLine()) {
						instance._endResult.push(NEW_LINE);
					}
				}
				else if (tagName == TAG_PRE || tagName == TAG_CODE) {
					instance._inPRE = false;
				}
			}
		},

		_handleElementStart: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			var tagName = element.tagName;

			if (tagName) {
				tagName = tagName.toLowerCase();

				if (tagName == TAG_PARAGRAPH) {
					instance._handleParagraph(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'br') {
					instance._handleBreak(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'strong' || tagName == 'b') {
					instance._handleStrong(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'em' || tagName == 'i') {
					instance._handleEm(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'u') {
					instance._handleUnderline(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'a') {
					instance._handleLink(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'img') {
					instance._handleImage(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'strike' || tagName == 's') {
					instance._handleLineThrough(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'font') {
					instance._handleFont(element, listTagsIn, listTagsOut);
				}
				else if (tagName == TAG_BLOCKQUOTE) {
					instance._handleQuote(element, listTagsIn, listTagsOut);
				}
				else if (tagName == TAG_CITE) {
					instance._handleCite(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'ul') {
					instance._handleUnorderedList(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'ol') {
					instance._handleOrderedList(element, listTagsIn, listTagsOut);
				}
				else if (tagName == TAG_LI) {
					instance._handleListItem(element, listTagsIn, listTagsOut);
				}
				else if (tagName == TAG_PRE || tagName == TAG_CODE) {
					instance._handlePre(element, listTagsIn, listTagsOut);
				}
				else if (tagName == TAG_TABLE) {
					instance._handleTable(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'th') {
					instance._handleTableHeader(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'tr') {
					instance._handleTableRow(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'td') {
					instance._handleTableCell(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'caption') {
					instance._handleTableCaption(element, listTagsIn, listTagsOut);
				}
			}
		},

		_handleEm: function(element, listTagsIn, listTagsOut) {
			listTagsIn.push('[i]');
			listTagsOut.push('[/i]');
		},

		_handleFont: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			var size = element.size;

			if (size) {
				size = parseInt(size, 10);

				if (size >= 1 && size <= 7) {
					listTagsIn.push('[size=', size, ']');
					listTagsIn.push('[/size]');
				}
			}

			var color = element.color;

			if (color) {
				color = instance._convertRGBToHex(color);

				listTagsIn.push('[color=', color, ']');
				listTagsIn.push('[/color]');
			}
		},

		_handleImage: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			var emoticonSymbol = instance._getEmoticonSymbol(element);

			if (emoticonSymbol) {
				instance._endResult.push(emoticonSymbol);
			}
			else {
				var attrSrc = element.getAttribute('src');

				listTagsIn.push('[img]');

				listTagsIn.push(attrSrc);

				listTagsOut.push('[/img]');
			}
		},

		_handleLink: function(element, listTagsIn, listTagsOut) {
			var hrefAttribute = element.getAttribute('href');

			var decodedLink = decodeURIComponent(hrefAttribute);

			if (CKEDITOR.config.newThreadURL === decodedLink) {
				hrefAttribute = decodedLink;
			}

			listTagsIn.push('[url=', hrefAttribute, ']');

			listTagsOut.push('[/url]');
		},

		_handleListItem: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			if (!instance._isLastItemNewLine()) {
				listTagsIn.push(NEW_LINE);
			}

			listTagsIn.push('[*]');
		},

		_handleLineThrough: function(element, listTagsIn, listTagsOut) {
			listTagsIn.push('[s]');
			listTagsOut.push('[/s]');
		},

		_handleOrderedList: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			if (!instance._isLastItemNewLine()) {
				listTagsIn.push(NEW_LINE);
			}

			listTagsIn.push('[list=');

			var listStyleType = element.style.listStyleType;

			if (REGEX_LIST_ALPHA.test(listStyleType)) {
				listTagsIn.push('a]');
			}
			else {
				listTagsIn.push('1]');
			}

			listTagsOut.push('[/list]');
		},

		_handleParagraph: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			var newLinesAtEnd = REGEX_LASTCHAR_NEWLINE.exec(instance._endResult.slice(-2).join(''));
			var count = newLinesAtEnd ? newLinesAtEnd[1].length : 0;

			while (count++ < 2) {
				listTagsIn.push(NEW_LINE);
			}
		},

		_handlePre: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			instance._inPRE = true;

			listTagsIn.push('[code]');
			listTagsOut.push('[/code]');
		},

		_handleQuote: function(element, listTagsIn, listTagsOut) {
			var cite = element.getAttribute(TAG_CITE);

			var openTag = '[quote]';

			if (cite) {
				openTag = '[quote=' + cite + ']';
			}

			listTagsIn.push(openTag);
			listTagsOut.push('[/quote]');
		},

		_handleStrong: function(element, listTagsIn, listTagsOut) {
			listTagsIn.push('[b]');
			listTagsOut.push('[/b]');
		},

		_handleStyleAlignCenter: function(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			var alignment = style.textAlign.toLowerCase();

			if (alignment == 'center') {
				stylesTagsIn.push('[center]');
				stylesTagsOut.push('[/center]');
			}
		},

		_handleStyleAlignJustify: function(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			var alignment = style.textAlign.toLowerCase();

			if (alignment == 'justify') {
				stylesTagsIn.push('[justify]');
				stylesTagsOut.push('[/justify]');
			}
		},

		_handleStyleAlignLeft: function(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			var alignment = style.textAlign.toLowerCase();

			if (alignment == 'left') {
				stylesTagsIn.push('[left]');
				stylesTagsOut.push('[/left]');
			}
		},

		_handleStyleAlignRight: function(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			var alignment = style.textAlign.toLowerCase();

			if (alignment == 'right') {
				stylesTagsIn.push('[right]');
				stylesTagsOut.push('[/right]');
			}
		},

		_handleStyleBold: function(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			var fontWeight = style.fontWeight;

			if (fontWeight.toLowerCase() == 'bold') {
				stylesTagsIn.push('[b]');
				stylesTagsOut.push('[/b]');
			}
		},

		_handleStyleColor: function(element, stylesTagsIn, stylesTagsOut) {
			var instance = this;

			var style = element.style;

			var color = style.color;

			if (color) {
				color = instance._convertRGBToHex(color);

				stylesTagsIn.push('[color=', color, ']');
				stylesTagsOut.push('[/color]');
			}
		},

		_handleStyleFontFamily: function(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			var fontFamily = style.fontFamily;

			if (fontFamily) {
				stylesTagsIn.push('[font=', fontFamily, ']');
				stylesTagsOut.push('[/font]');
			}
		},

		_handleStyleFontSize: function(element, stylesTagsIn, stylesTagsOut) {
			var instance = this;

			var style = element.style;

			var fontSize = style.fontSize;

			if (fontSize) {
				fontSize = instance._getFontSize(fontSize);

				stylesTagsIn.push('[size=', fontSize, ']');
				stylesTagsOut.push('[/size]');
			}
		},

		_handleStyleItalic: function(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			var fontStyle = style.fontStyle;

			if (fontStyle.toLowerCase() == 'italic') {
				stylesTagsIn.push('[i]');
				stylesTagsOut.push('[/i]');
			}
		},

		_handleStyleTextDecoration: function(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			var textDecoration = style.textDecoration.toLowerCase();

			if (textDecoration == 'line-through') {
				stylesTagsIn.push('[s]');
				stylesTagsOut.push('[/s]');
			}
			else if (textDecoration == 'underline') {
				stylesTagsIn.push('[u]');
				stylesTagsOut.push('[/u]');
			}
		},

		_handleStyles: function(element, stylesTagsIn, stylesTagsOut) {
			var instance = this;

			var tagName = element.tagName;

			if (tagName && tagName.toLowerCase() == 'a') {
				return;
			}

			var style = element.style;

			if (style) {
				instance._handleStyleAlignCenter(element, stylesTagsIn, stylesTagsOut);

				instance._handleStyleAlignJustify(element, stylesTagsIn, stylesTagsOut);

				instance._handleStyleAlignLeft(element, stylesTagsIn, stylesTagsOut);

				instance._handleStyleAlignRight(element, stylesTagsIn, stylesTagsOut);

				instance._handleStyleBold(element, stylesTagsIn, stylesTagsOut);

				instance._handleStyleColor(element, stylesTagsIn, stylesTagsOut);

				instance._handleStyleFontFamily(element, stylesTagsIn, stylesTagsOut);

				instance._handleStyleFontSize(element, stylesTagsIn, stylesTagsOut);

				instance._handleStyleItalic(element, stylesTagsIn, stylesTagsOut);

				instance._handleStyleTextDecoration(element, stylesTagsIn, stylesTagsOut);
			}
		},

		_handleTable: function(element, listTagsIn, listTagsOut) {
			listTagsIn.push(NEW_LINE, '[table]', NEW_LINE);
			listTagsOut.push('[/table]', NEW_LINE);
		},

		_handleTableCaption: function(element, listTagsIn, listTagsOut) {
			var parentNode = element.parentNode;

			if (parentNode && parentNode.tagName === TAG_TABLE) {
				listTagsIn.push('[tr]', NEW_LINE, '[th]');
				listTagsOut.push('[/th]', NEW_LINE, '[/tr]', NEW_LINE);
			}
		},

		_handleTableCell: function(element, listTagsIn, listTagsOut) {
			listTagsIn.push('[td]');
			listTagsOut.push('[/td]', NEW_LINE);
		},

		_handleTableHeader: function(element, listTagsIn, listTagsOut) {
			listTagsIn.push('[th]');
			listTagsOut.push('[/th]', NEW_LINE);
		},

		_handleTableRow: function(element, listTagsIn, listTagsOut) {
			listTagsIn.push('[tr]', NEW_LINE);
			listTagsOut.push('[/tr]', NEW_LINE);
		},

		_handleUnderline: function(element, listTagsIn, listTagsOut) {
			listTagsIn.push('[u]');
			listTagsOut.push('[/u]');
		},

		_handleUnorderedList: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			if (!instance._isLastItemNewLine()) {
				listTagsIn.push(NEW_LINE);
			}

			listTagsIn.push('[list]');
			listTagsOut.push('[/list]');
		},

		_pushTagList: function(tagsList) {
			var instance = this;

			var endResult, i, length, tag;

			endResult = instance._endResult;
			length = tagsList.length;

			for (i = 0; i < length; i++) {
				tag = tagsList[i];

				endResult.push(tag);
			}
		},

		_endResult: null,

		_inPRE: false
	};
})();
