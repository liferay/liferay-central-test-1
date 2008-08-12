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

package com.liferay.portlet.tags.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portlet.tags.DuplicateVocabularyException;
import com.liferay.portlet.tags.model.TagsVocabulary;
import com.liferay.portlet.tags.service.base.TagsVocabularyLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * <a href="TagsVocabularyLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Alvaro del Castillo
 *
 */
public class TagsVocabularyLocalServiceImpl
	extends TagsVocabularyLocalServiceBaseImpl {

	public TagsVocabulary addVocabulary(long userId, long groupId, String name)
		throws PortalException, SystemException {

		return addVocabulary(userId, groupId, name, false);
	}

	public TagsVocabulary addVocabulary(
			long userId, long groupId, String name, boolean folksonomy)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		name = name.trim();
		Date now = new Date();

		if (hasVocabulary(groupId, name)) {
			throw new DuplicateVocabularyException(
				"A vocabulary with the name " + name + " already exists");
		}

		long vocabularyId = counterLocalService.increment();

		TagsVocabulary vocabulary = tagsVocabularyPersistence.create(
			vocabularyId);

		vocabulary.setGroupId(groupId);
		vocabulary.setCompanyId(user.getCompanyId());
		vocabulary.setUserId(user.getUserId());
		vocabulary.setUserName(user.getFullName());
		vocabulary.setCreateDate(now);
		vocabulary.setModifiedDate(now);
		vocabulary.setName(name);
		vocabulary.setFolksonomy(folksonomy);

		tagsVocabularyPersistence.update(vocabulary, false);

		return vocabulary;

	}

	public void deleteVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		tagsEntryLocalService.deleteVocabularyEntries(vocabularyId);

		tagsVocabularyPersistence.remove(vocabularyId);
	}

	public List<TagsVocabulary> getCompanyVocabularies(
			long companyId, boolean folksonomy)
		throws SystemException {

		return tagsVocabularyPersistence.findByC_F(companyId, folksonomy);
	}

	public List<TagsVocabulary> getGroupVocabularies(
			long groupId, boolean folksonomy)
		throws SystemException {

		return tagsVocabularyPersistence.findByG_F(groupId, folksonomy);
	}

	public TagsVocabulary getGroupVocabulary(long groupId, String name)
		throws PortalException, SystemException {

		return tagsVocabularyPersistence.findByG_N(groupId, name);
	}

	public TagsVocabulary getVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		return tagsVocabularyPersistence.findByPrimaryKey(vocabularyId);
	}

	public TagsVocabulary updateVocabulary(
			long vocabularyId, String name, boolean folksonomy)
		throws PortalException, SystemException {

		name = name.trim();

		TagsVocabulary vocabulary = tagsVocabularyPersistence.findByPrimaryKey(
			vocabularyId);

		if (!vocabulary.getName().equals(name) &&
			hasVocabulary(vocabulary.getGroupId(), name)) {

			throw new DuplicateVocabularyException(name);
		}

		vocabulary.setModifiedDate(new Date());
		vocabulary.setName(name);
		vocabulary.setFolksonomy(folksonomy);

		tagsVocabularyPersistence.update(vocabulary, false);

		return vocabulary;
	}

	protected boolean hasVocabulary(long groupId, String name)
		throws SystemException {

		if (tagsVocabularyPersistence.countByG_N(groupId, name) == 0) {
			return false;
		}
		else {
			return true;
		}
	}

}