/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;

import java.sql.Types;

/**
 * @author Brian Wing Shun Chan
 */
public class PollsVoteChoiceIdUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public PollsVoteChoiceIdUpgradeColumnImpl(
		UpgradeColumn questionIdColumn, ValueMapper pollsChoiceIdMapper) {

		super("choiceId", new Integer(Types.VARCHAR));

		_questionIdColumn = questionIdColumn;
		_pollsChoiceIdMapper = pollsChoiceIdMapper;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		Long oldQuestionId = (Long)_questionIdColumn.getOldValue();

		String oldChoiceIdValue =
			"{questionId=" + oldQuestionId + ", choiceId=" + oldValue + "}";

		return _pollsChoiceIdMapper.getNewValue(oldChoiceIdValue);
	}

	private UpgradeColumn _questionIdColumn;
	private ValueMapper _pollsChoiceIdMapper;

}