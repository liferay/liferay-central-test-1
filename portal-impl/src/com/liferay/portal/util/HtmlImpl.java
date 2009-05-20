/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.util;

import au.id.jericho.lib.html.Source;

import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * <a href="HtmlImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Clarence Shen
 * @author Harry Mark
 * @author Samuel Kong
 *
 */
public class HtmlImpl implements Html {

	public static final int ESCAPE_MODE_ATTRIBUTE = 1;

	public static final int ESCAPE_MODE_CSS = 2;

	public static final int ESCAPE_MODE_JS = 3;

	public static final int ESCAPE_MODE_TEXT = 4;

	public static final int ESCAPE_MODE_URL = 5;

	public String escape(String text) {
		if (text == null) {
			return null;
		}

		// Escape using XSS recommendations from
		// http://www.owasp.org/index.php/Cross_Site_Scripting
		// #How_to_Protect_Yourself

		StringBuilder sb = new StringBuilder(text.length());

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			switch (c) {
				case '<':
					sb.append("&lt;");

					break;

				case '>':
					sb.append("&gt;");

					break;

				case '&':
					sb.append("&amp;");

					break;

				case '"':
					sb.append("&#034;");

					break;

				case '\'':
					sb.append("&#039;");

					break;

				default:
					sb.append(c);

					break;
			}
		}

		return sb.toString();
	}

	public String escape(String text, int type) {
		if (text == null){
			return null;
		}

		String prefix = StringPool.BLANK;
		String postfix = StringPool.BLANK;

		if (type == ESCAPE_MODE_ATTRIBUTE) {
			prefix = "&#x";
			postfix = StringPool.SEMICOLON;
		}
		else if (type == ESCAPE_MODE_CSS) {
			prefix = StringPool.BACK_SLASH;
		}
		else if (type == ESCAPE_MODE_JS) {
			prefix = "\\x";
		}
		else if (type == ESCAPE_MODE_URL) {
			// prefix = StringPool.PERCENT;

			return HttpUtil.encodeURL(text, true);
		}
		else {
			return escape(text);
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			if (Character.isLetterOrDigit(c)) {
				sb.append(c);
			}
			else {
				sb.append(prefix);
				sb.append(Integer.toHexString(c));
				sb.append(postfix);
			}
		}

		return sb.toString();
	}

	public String escapeAttribute(String attribute) {
		return escape(attribute, ESCAPE_MODE_ATTRIBUTE);
	}

	public String escapeCSS(String css) {
		return escape(css, ESCAPE_MODE_CSS);
	}

	public String escapeJS(String js) {
		return escape(js, ESCAPE_MODE_JS);
	}

	public String escapeURL(String url) {
		return escape(url, ESCAPE_MODE_URL);
	}

	public String extractText(String html) {
		if (html == null) {
			return null;
		}

		Source source = new Source(html);

		return source.getTextExtractor().toString();
	}

	public String fromInputSafe(String text) {
		return StringUtil.replace(text, "&amp;", "&");
	}

	public String replaceMsWordCharacters(String text) {
		return StringUtil.replace(text, _MS_WORD_UNICODE, _MS_WORD_HTML);
	}

	public String stripBetween(String text, String tag) {
		return StringUtil.stripBetween(text, "<" + tag, "</" + tag + ">");
	}

	public String stripComments(String text) {
		return StringUtil.stripBetween(text, "<!--", "-->");
	}

	public String stripHtml(String text) {
		if (text == null) {
			return null;
		}

		text = stripComments(text);

		StringBuilder sb = new StringBuilder(text.length());

		int x = 0;
		int y = text.indexOf("<");

		while (y != -1) {
			sb.append(text.substring(x, y));
			sb.append(StringPool.SPACE);

			// Look for text enclosed by <script></script>

			boolean scriptFound = isScriptTag(text, y + 1);

			if (scriptFound) {
				int pos = y + _TAG_SCRIPT.length;

				// Find end of the tag

				pos = text.indexOf(">", pos);

				if (pos >= 0) {

					// Check if preceding character is / (i.e. is this instance
					// of <script/>)

					if (text.charAt(pos-1) != '/') {

						// Search for the ending </script> tag

						for (;;) {
							pos = text.indexOf("</", pos);

							if (pos >= 0) {
								if (isScriptTag(text, pos + 2)) {
									y = pos;

									break;
								}
								else {

									// Skip past "</"

									pos += 2;
								}
							}
							else {
								break;
							}
						}
					}
				}
			}

			x = text.indexOf(">", y);

			if (x == -1) {
				break;
			}

			x++;

			if (x < y) {

				// <b>Hello</b

				break;
			}

			y = text.indexOf("<", x);
		}

		if (y == -1) {
			sb.append(text.substring(x, text.length()));
		}

		return sb.toString();
	}

	public String toInputSafe(String text) {
		return StringUtil.replace(
			text,
			new String[] {"&", "\""},
			new String[] {"&amp;", "&quot;"});
	}

	public String unescape(String text) {
		if (text == null) {
			return null;
		}

		// Optimize this

		text = StringUtil.replace(text, "&lt;", "<");
		text = StringUtil.replace(text, "&gt;", ">");
		text = StringUtil.replace(text, "&amp;", "&");
		text = StringUtil.replace(text, "&#034;", "\"");
		text = StringUtil.replace(text, "&#039;", "'");
		text = StringUtil.replace(text, "&#040;", "(");
		text = StringUtil.replace(text, "&#041;", ")");
		text = StringUtil.replace(text, "&#035;", "#");
		text = StringUtil.replace(text, "&#037;", "%");
		text = StringUtil.replace(text, "&#059;", ";");
		text = StringUtil.replace(text, "&#043;", "+");
		text = StringUtil.replace(text, "&#045;", "-");

		return text;
	}

	protected boolean isScriptTag(String text, int pos) {
		if (pos + _TAG_SCRIPT.length + 1 <= text.length()) {
			char item;

			for (int i = 0; i < _TAG_SCRIPT.length; i++) {
				item = text.charAt(pos++);

				if (Character.toLowerCase(item) != _TAG_SCRIPT[i]) {
					return false;
				}
			}

			item = text.charAt(pos);

			// Check that char after "script" is not a letter (i.e. another tag)

			return !Character.isLetter(item);
		}
		else {
			return false;
		}
	}

	private static final String[] _MS_WORD_UNICODE = new String[] {
		"\u00ae", "\u2019", "\u201c", "\u201d"
	};

	private static final String[] _MS_WORD_HTML = new String[] {
		"&reg;", StringPool.APOSTROPHE, StringPool.QUOTE, StringPool.QUOTE
	};

	private static final char[] _TAG_SCRIPT = {'s', 'c', 'r', 'i', 'p', 't'};

}