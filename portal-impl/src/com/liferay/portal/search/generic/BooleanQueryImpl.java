/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.generic;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="BooleanQueryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class BooleanQueryImpl implements BooleanQuery {

	public void add(Query query, BooleanClauseOccur occur) {
		_clauses.add(new BooleanClauseImpl(query, occur));
	}

	public void addExactTerm(String field, boolean value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, Boolean value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, double value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, Double value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, int value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, Integer value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, long value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, Long value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, short value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, Short value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, String value) {
		TermQueryImpl termQuery = new TermQueryImpl(
			new QueryTermImpl(field, String.valueOf(value)));

		add(termQuery, BooleanClauseOccur.SHOULD);
	}

	public void addKeywords(String[] fields, String keywords) {
		if (Validator.isNotNull(keywords)) {
			String value = "";

			String[] replacePatterns = {"$1$2$3", "$1"};

			List<String> values = new ArrayList<String>();

			for (String field : fields) {
				String[] patterns = {
					"(?i)^.*" + field + ":([\"\'])(.+?)(\\1).*$",
					"(?i)^.*" + field + ":([^\\s\"']*).*$"
				};

				String duplicate = "";

				for (int i = 0; i < patterns.length; i++) {
					while (keywords.matches(patterns[i])) {
						value = keywords.replaceAll(
							patterns[i], replacePatterns[i]);

						values.add(value);

						duplicate = "(?i)\\s*" + field + ":" + value +
							"\\s*";

						keywords = keywords.replaceAll(duplicate, " ");

						keywords = keywords.trim();
					}
				}

				while (!values.isEmpty()) {
					addTerm(field, values.remove(0));
				}
			}

			if (keywords.trim().length() > 0) {
				for (String field : fields) {
					addTerm(field, keywords);
				}
			}
		}
	}

	public void addRequiredTerm(String field, boolean value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, Boolean value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, double value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, Double value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, int value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, Integer value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, long value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, Long value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, short value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, Short value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, String value) {
		addRequiredTerm(field, value, false);
	}

	public void addRequiredTerm(String field, String value, boolean like) {
		Query query = null;

		if (like) {
			query = new WildcardQueryImpl(
				new QueryTermImpl(field, String.valueOf(value)));
		}
		else {
			query = new TermQueryImpl(
				new QueryTermImpl(field, String.valueOf(value)));
		}

		add(query , BooleanClauseOccur.MUST);
	}

	public void addTerm(String field, long value) {
		addTerm(field, String.valueOf(value), false);
	}

	public void addTerm(String field, String value) {
		addTerm(field, value, false);
	}

	public void addTerm(String field, String value, boolean like) {
		Query query = null;

		if (like) {
			query = new WildcardQueryImpl(
				new QueryTermImpl(field, String.valueOf(value)));
		}
		else {
			query = new TermQueryImpl(
				new QueryTermImpl(field, String.valueOf(value)));
		}

		add(query , BooleanClauseOccur.SHOULD);
	}

	public List<BooleanClause> clauses() {
		return Collections.unmodifiableList(_clauses);
	}

	private List<BooleanClause> _clauses = new ArrayList<BooleanClause>();

}