(function() {
	var NEW_LINE = '\n';

	var REGEX_HEADER = /^h([1-6])$/i;

	var REGEX_LASTCHAR_NEWLINE = /(\r?\n\s*)$/;

	var REGEX_NEWLINE = /\r?\n/g;

	var REGEX_NOT_WHITESPACE = /[^\t\n\r ]/;

	var SPACE = ' ';

	var STR_EQUALS = '=';

	var STR_PIPE = '|';

	var TAG_BOLD = '**';

	var TAG_EMPHASIZE = '//';

	var TAG_ORDERED_LIST = 'ol';

	var TAG_ORDERED_LIST_ITEM = '#';

	var TAG_PARAGRAPH = 'p';

	var TAG_PRE = 'pre';

	var TAG_UNORDERED_LIST = 'ul';

	var TAG_UNORDERED_LIST_ITEM = '*';

	CKEDITOR.plugins.add(
		'creole_data_processor',
		{
			requires: ['htmlwriter'],

			init: function(editor) {
				editor.dataProcessor = new CKEDITOR.htmlDataProcessor(editor);

				editor.dataProcessor.writer.setRules(
					'p',
					{
						breakBeforeClose: false
					}
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

			var div = document.createElement('div');

			if (!instance._creoleParser) {
				instance._creoleParser = new CKEDITOR.CreoleParser(
					{
						imagePrefix: CKEDITOR.config.attachments_prefix
					}
				);
			}

			instance._creoleParser.parse(div, data);

			data = div.innerHTML;

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

						allowNewLine = (parentTagName == TAG_PARAGRAPH);
					}
				}
			}

			return allowNewLine;
		},

		_convert: function(data) {
			var instance = this;

			var node = document.createElement('div');
			node.innerHTML = data;

			instance._handle(node);

			var endResult = instance._endResult.join('');

			instance._endResult = null;

			return endResult;
		},

		_handle: function(node) {
			var instance = this;

			if (!instance._endResult) {
				instance._endResult = [];
			}

			var children = node.childNodes;
			var pushTagList = instance._pushTagList;
			var length = children.length;

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
			}

			if (tagName == TAG_PARAGRAPH) {
				if (!instance._isLastItemNewLine()) {
					instance._endResult.push(NEW_LINE);
				}
			}
			else if (tagName == TAG_UNORDERED_LIST || tagName == TAG_ORDERED_LIST) {
				if (instance._ulLevel > 1) {
					if (!instance._isLastItemNewLine()) {
						instance._endResult.push(NEW_LINE);
					}
				}
				else {
					var newLinesAtEnd = REGEX_LASTCHAR_NEWLINE.exec(instance._endResult.slice(-2).join(''));
					var count = 0;

					if (newLinesAtEnd) {
						count = newLinesAtEnd[1].length;
					}

					while (count++ < 2) {
						instance._endResult.push(NEW_LINE);
					}
				}

				instance._ulLevel--;
			}
			else if (tagName == TAG_PRE) {
				if (!instance._isLastItemNewLine()) {
					instance._endResult.push(NEW_LINE);
				}

				instance._inPRE = false;
			}
			else if (tagName == 'table') {
				listTagsOut.push(NEW_LINE);
			}
		},

		_handleElementStart: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			var tagName = element.tagName;
			var params;

			if (tagName) {
				tagName = tagName.toLowerCase();

				if (tagName == TAG_PARAGRAPH) {
					instance._handleParagraph(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'br') {
					instance._handleBreak(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'a') {
					instance._handleLink(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'strong' || tagName == 'b') {
					instance._handleStrong(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'em' || tagName == 'i') {
					instance._handleEm(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'img') {
					instance._handleImage(element, listTagsIn, listTagsOut);
				}
				else if (tagName == TAG_UNORDERED_LIST) {
					instance._handleUnorderedList(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'li') {
					instance._handleListItem(element, listTagsIn, listTagsOut);
				}
				else if (tagName == TAG_ORDERED_LIST) {
					instance._handleOrderedList(element, listTagsIn, listTagsOut);
				}
				else if (tagName == 'hr') {
					instance._handleHr(element, listTagsIn, listTagsOut);
				}
				else if (tagName == TAG_PRE) {
					instance._handlePre(element, listTagsIn, listTagsOut);
				}
				else if ((params = REGEX_HEADER.exec(tagName))) {
					instance._handleHeader(element, listTagsIn, listTagsOut, params);
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
			}
		},

		_handleEm: function(element, listTagsIn, listTagsOut) {
			listTagsIn.push(TAG_EMPHASIZE);
			listTagsOut.push(TAG_EMPHASIZE);
		},

		_handleHeader: function(element, listTagsIn, listTagsOut, params) {
			var instance = this;

			var res = new Array(parseInt(params[1], 10) + 1);
			res = res.join(STR_EQUALS);

			if (instance._isDataAvailable() && !instance._isLastItemNewLine()) {
				listTagsIn.push(NEW_LINE);
			}

			listTagsIn.push(res, SPACE);
			listTagsOut.push(SPACE, res, NEW_LINE);
		},

		_handleHr: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			if (instance._isDataAvailable() && !instance._isLastItemNewLine()) {
				listTagsIn.push(NEW_LINE);
			}

			listTagsIn.push('----', NEW_LINE);
		},

		_handleImage: function(element, listTagsIn, listTagsOut) {
			var attrAlt = element.getAttribute('alt');
			var attrSrc = element.getAttribute('src');

			attrSrc = attrSrc.replace(CKEDITOR.config.attachments_prefix, '');

			listTagsIn.push('{{', attrSrc);

			if (attrAlt) {
				listTagsIn.push(STR_PIPE, attrAlt);
			}

			listTagsOut.push('}}');
		},

		_handleLink: function(element, listTagsIn, listTagsOut) {
			var hrefAttribute = element.getAttribute('href');

			listTagsIn.push('[[', hrefAttribute, STR_PIPE);

			listTagsOut.push(']]');
		},

		_handleListItem: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			var parentNode = element.parentNode;
			var tagName = parentNode.tagName.toLowerCase();
			var listItemTag = TAG_ORDERED_LIST_ITEM;

			if (tagName === TAG_UNORDERED_LIST) {
				listItemTag = TAG_UNORDERED_LIST_ITEM;
			}

			var res = new Array(instance._ulLevel + 1);
			res = res.join(listItemTag);

			if (instance._isDataAvailable() && !instance._isLastItemNewLine()) {
				listTagsIn.push(NEW_LINE);
			}

			listTagsIn.push(res, SPACE);
		},

		_handleOrderedList: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			instance._ulLevel++;
		},

		_handleParagraph: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			if (instance._isDataAvailable()) {
				var newLinesAtEnd = REGEX_LASTCHAR_NEWLINE.exec(instance._endResult.slice(-2).join(''));
				var count = 0;

				if (newLinesAtEnd) {
					 count = newLinesAtEnd[1].length;
				}

				while (count++ < 2) {
					listTagsIn.push(NEW_LINE);
				}
			}

			listTagsOut.push(NEW_LINE);
		},

		_handlePre: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			instance._inPRE = true;

			var endResult = instance._endResult;

			if (instance._isDataAvailable() && !instance._isLastItemNewLine()) {
				endResult.push(NEW_LINE);
			}

			listTagsIn.push('{{{', NEW_LINE);

			listTagsOut.push('}}}', NEW_LINE);
		},

		_handleStrong: function(element, listTagsIn, listTagsOut) {
			listTagsIn.push(TAG_BOLD);
			listTagsOut.push(TAG_BOLD);
		},

		_handleStyles: function(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			if (style) {
				if (style.fontWeight.toLowerCase() == 'bold') {
					stylesTagsIn.push(TAG_BOLD);
					stylesTagsOut.push(TAG_BOLD);
				}

				if (style.fontStyle.toLowerCase() == 'italic') {
					stylesTagsIn.push(TAG_EMPHASIZE);
					stylesTagsOut.push(TAG_EMPHASIZE);
				}
			}
		},

		_handleTableCell: function(element, listTagsIn, listTagsOut) {
			listTagsIn.push(STR_PIPE);
		},

		_handleTableHeader: function(element, listTagsIn, listTagsOut) {
			listTagsIn.push(STR_PIPE, STR_EQUALS);
		},

		_handleTableRow: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			if (instance._isDataAvailable()) {
				listTagsIn.push(NEW_LINE);
			}

			listTagsOut.push(STR_PIPE);
		},

		_handleUnorderedList: function(element, listTagsIn, listTagsOut) {
			var instance = this;

			instance._ulLevel++;
		},

		_isDataAvailable: function() {
			var instance = this;

			return instance._endResult && instance._endResult.length;
		},

		_isIgnorable: function(node) {
			var instance = this;

			var nodeType = node.nodeType;

			return (node.isElementContentWhitespace || nodeType == 8) ||
					((nodeType == 3) && instance._isWhitespace(node));
		},

		_isLastItemNewLine: function(node) {
			var instance = this;

			var endResult = instance._endResult;

			return endResult && REGEX_LASTCHAR_NEWLINE.test(endResult.slice(-1));
		},

		_isWhitespace: function(node) {
			return node.isElementContentWhitespace || !(REGEX_NOT_WHITESPACE.test(node.data));
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

		_inPRE: false,

		_ulLevel: 0
	};
})();