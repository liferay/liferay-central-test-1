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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.util.ByteArrayMaker;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.velocity.VelocityResourceListener;
import com.liferay.util.PwdGenerator;

import java.io.StringReader;

import java.util.Locale;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * <a href="JournalXslUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Raymond Augé
 *
 */
public class JournalXslUtil {

	public static String transform(
			Map<String, String> tokens, String viewMode, String languageId,
			String xml, String script)
		throws Exception {

		ByteArrayMaker bam = new ByteArrayMaker();

		long companyId = GetterUtil.getLong(tokens.get("company_id"));
		Company company = CompanyLocalServiceUtil.getCompanyById(companyId);
		long groupId = GetterUtil.getLong(tokens.get("group_id"));
		String journalTemplatesPath =
			VelocityResourceListener.JOURNAL_SEPARATOR + StringPool.SLASH +
				companyId + StringPool.SLASH + groupId;
		String randomNamespace =
			PwdGenerator.getPassword(PwdGenerator.KEY3, 4) +
				StringPool.UNDERLINE;
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		JournalXslErrorListener errorListener = new JournalXslErrorListener(
			companyId, locale);

		StreamSource xmlSource = new StreamSource(new StringReader(xml));

		TransformerFactory transformerFactory =
			TransformerFactory.newInstance();

		transformerFactory.setURIResolver(new URIResolver(tokens, languageId));
		transformerFactory.setErrorListener(errorListener);

		try {
			StreamSource scriptSource = new StreamSource(
				new StringReader(script));

			Transformer transformer = transformerFactory.newTransformer(
				scriptSource);

			transformer.setParameter("company", company);
			transformer.setParameter("companyId", new Long(companyId));
			transformer.setParameter("groupId", String.valueOf(groupId));
			transformer.setParameter(
				"journalTemplatesPath", journalTemplatesPath);
			transformer.setParameter("viewMode", viewMode);
			transformer.setParameter("locale", locale);
			transformer.setParameter(
				"permissionChecker",
				PermissionThreadLocal.getPermissionChecker());
			transformer.setParameter("randomNamespace", randomNamespace);

			transformer.transform(xmlSource, new StreamResult(bam));
		}
		catch (Exception e1) {
			String errorTemplate = ContentUtil.get(
				PropsValues.JOURNAL_ERROR_TEMPLATE_XSL);

			StreamSource scriptSource = new StreamSource(
				new StringReader(errorTemplate));

			Transformer transformer = transformerFactory.newTransformer(
				scriptSource);

			transformer.setParameter("company", company);
			transformer.setParameter("companyId", new Long(companyId));
			transformer.setParameter("groupId", String.valueOf(groupId));
			transformer.setParameter(
				"journalTemplatesPath", journalTemplatesPath);
			transformer.setParameter("locale", locale);
			transformer.setParameter("randomNamespace", randomNamespace);

			transformer.setParameter(
				"exception", errorListener.getMessageAndLocation());
			transformer.setParameter("script", script);

			if (errorListener.getLocation() != null) {
				transformer.setParameter(
					"column", new Integer(errorListener.getColumnNumber()));
				transformer.setParameter(
					"line", new Integer(errorListener.getLineNumber()));
			}

			transformer.transform(xmlSource, new StreamResult(bam));
		}

		return bam.toString(StringPool.UTF8);
	}

}