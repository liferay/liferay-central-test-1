/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journalcontent.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ExportArticleAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class ExportArticleAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			String articleId = ParamUtil.getString(actionRequest, "articleId");

			String targetExtension = ParamUtil.getString(
				actionRequest, "targetExtension");

			PortletPreferences preferences = actionRequest.getPreferences();

			String[] allowedExtensions = preferences.getValues(
				"extensions", null);

			String languageId = LanguageUtil.getLanguageId(actionRequest);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			getFile(
				groupId, articleId, targetExtension, allowedExtensions,
				languageId, themeDisplay, request, response);

			setForward(actionRequest, ActionConstants.COMMON_NULL);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, actionRequest, actionResponse);
		}
	}

	protected void getFile(
			long groupId, String articleId, String targetExtension,
			String[] allowedExtensions, String languageId,
			ThemeDisplay themeDisplay, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		InputStream is = null;

		try {
			JournalArticleDisplay articleDisplay =
				JournalContentUtil.getDisplay(
					groupId, articleId, languageId, themeDisplay);

			StringBuilder sb = new StringBuilder();

			sb.append("<html>");

			sb.append("<head>");
			sb.append("<meta content=\"");
			sb.append(ContentTypes.TEXT_HTML_UTF8);
			sb.append("\" http-equiv=\"content-type\" />");
			sb.append("</head>");

			sb.append("<body>");

			sb.append(articleDisplay.getContent());

			int pages = articleDisplay.getNumberOfPages();

			for (int i = 2; i <= pages; i++) {
				articleDisplay = JournalContentUtil.getDisplay(
					groupId, articleId, languageId, themeDisplay, i);

				sb.append(articleDisplay.getContent());
			}

			sb.append("</body>");
			sb.append("</html>");

			is = new ByteArrayInputStream(
				sb.toString().getBytes(StringPool.UTF8));

			String title = articleDisplay.getTitle();
			String sourceExtension = "html";

			sb = new StringBuilder();

			sb.append(title);
			sb.append(StringPool.PERIOD);
			sb.append(sourceExtension);

			String fileName = sb.toString();

			if (Validator.isNotNull(targetExtension) &&
				ArrayUtil.contains(allowedExtensions, targetExtension)) {

				String id = DocumentConversionUtil.getTempFileId(
					articleDisplay.getId(), articleDisplay.getVersion());

				InputStream convertedIS = DocumentConversionUtil.convert(
					id, is, sourceExtension, targetExtension);

				if ((convertedIS != null) && (convertedIS != is)) {
					sb = new StringBuilder();

					sb.append(title);
					sb.append(StringPool.PERIOD);
					sb.append(targetExtension);

					fileName = sb.toString();

					is = convertedIS;
				}
			}

			String contentType = MimeTypesUtil.getContentType(fileName);

			ServletResponseUtil.sendFile(response, fileName, is, contentType);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			ServletResponseUtil.cleanUp(is);
		}
	}

	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

	private static Log _log = LogFactory.getLog(ExportArticleAction.class);

}