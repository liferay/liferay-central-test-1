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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.tags.NoSuchAssetException;
import com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="VerifyJournal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class VerifyJournal extends VerifyProcess {

	public void verify() throws VerifyException {
		_log.info("Verifying");

		try {
			verifyJournal();
		}
		catch (Exception e) {
			throw new VerifyException(e);
		}
	}

	protected void verifyJournal() throws Exception {

		// Structures

		List<JournalStructure> structures =
			JournalStructureLocalServiceUtil.getStructures();

		for (JournalStructure structure : structures) {
			ResourceLocalServiceUtil.addResources(
				structure.getCompanyId(), 0, 0,
				JournalStructure.class.getName(), structure.getId(), false,
				true, true);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Permissions verified for Journal structures");
		}

		// Templates

		List<JournalTemplate> templates =
			JournalTemplateLocalServiceUtil.getTemplates();

		for (JournalTemplate template : templates) {
			ResourceLocalServiceUtil.addResources(
				template.getCompanyId(), 0, 0,
				JournalTemplate.class.getName(), template.getId(), false, true,
				true);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Permissions verified for Journal templates");
		}

		// Articles

		List<JournalArticle> articles =
			JournalArticleLocalServiceUtil.getArticles();

		for (JournalArticle article : articles) {
			long groupId = article.getGroupId();
			String articleId = article.getArticleId();
			double version = article.getVersion();
			String structureId = article.getStructureId();

			if (article.getResourcePrimKey() <= 0) {
				article =
					JournalArticleLocalServiceUtil.checkArticleResourcePrimKey(
						groupId, articleId, version);
			}

			ResourceLocalServiceUtil.addResources(
				article.getCompanyId(), 0, 0, JournalArticle.class.getName(),
				article.getResourcePrimKey(), false, true, true);

			try {
				TagsAssetLocalServiceUtil.getAsset(
					JournalArticle.class.getName(),
					article.getResourcePrimKey());
			}
			catch (NoSuchAssetException nsae) {
				try {
					JournalArticleLocalServiceUtil.updateTagsAsset(
						article.getUserId(), article, new String[0],
						new String[0]);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to update tags asset for article " +
								article.getId() + ": " + e.getMessage());
					}
				}
			}

			String content = GetterUtil.getString(article.getContent());

			String newContent = HtmlUtil.replaceMsWordCharacters(content);

			if (Validator.isNotNull(structureId)) {
				/*JournalStructure structure =
					JournalStructureLocalServiceUtil.getStructure(
						groupId, structureId);

				newContent = JournalUtil.removeOldContent(
					newContent, structure.getXsd());*/
			}

			if (!content.equals(newContent)) {
				JournalArticleLocalServiceUtil.updateContent(
					groupId, articleId, version, newContent);
			}

			JournalArticleLocalServiceUtil.checkStructure(
				groupId, articleId, version);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Permissions and Tags assets verified for Journal articles");
		}
	}

	private static Log _log = LogFactory.getLog(VerifyJournal.class);

}