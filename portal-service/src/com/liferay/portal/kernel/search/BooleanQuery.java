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

package com.liferay.portal.kernel.search;

import java.util.List;

/**
 * <a href="BooleanQuery.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface BooleanQuery extends Query {

	public void add(Query query, BooleanClauseOccur occur)
		throws ParseException;

	public void addExactTerm(String field, boolean value);

	public void addExactTerm(String field, Boolean value);

	public void addExactTerm(String field, double value);

	public void addExactTerm(String field, Double value);

	public void addExactTerm(String field, int value);

	public void addExactTerm(String field, Integer value);

	public void addExactTerm(String field, long value);

	public void addExactTerm(String field, Long value);

	public void addExactTerm(String field, short value);

	public void addExactTerm(String field, Short value);

	public void addExactTerm(String field, String value);

	public void addRequiredTerm(String field, boolean value);

	public void addRequiredTerm(String field, Boolean value);

	public void addRequiredTerm(String field, double value);

	public void addRequiredTerm(String field, Double value);

	public void addRequiredTerm(String field, int value);

	public void addRequiredTerm(String field, Integer value);

	public void addRequiredTerm(String field, long value);

	public void addRequiredTerm(String field, Long value);

	public void addRequiredTerm(String field, short value);

	public void addRequiredTerm(String field, Short value);

	public void addRequiredTerm(String field, String value);

	public void addRequiredTerm(String field, String value, boolean like);

	public void addTerm(String field, long value) throws ParseException;

	public void addTerm(String field, String value) throws ParseException;

	public void addTerm(String field, String value, boolean like)
		throws ParseException;

	public void addTerms(String[] fields, String values) throws ParseException;

	public List<BooleanClause> clauses();

}