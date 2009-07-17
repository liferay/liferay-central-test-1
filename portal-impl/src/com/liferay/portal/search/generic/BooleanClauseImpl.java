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

package com.liferay.portal.search.generic;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Query;

/**
 * <a href="BooleanClauseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 *
 */
public class BooleanClauseImpl implements BooleanClause {

	public BooleanClauseImpl(
		Query query, BooleanClauseOccur occur, boolean prohibited,
		boolean required) {

		_query = query;
		_occur = occur;
		_prohibited = prohibited;
		_required = required;
	 }

	public BooleanClauseOccur getOccur() {
		return _occur;
	}

	public Query getQuery() {
		return _query;
	}

	public boolean isProhibited() {
		return _prohibited;
	}

	public boolean isRequired() {
		return _required;
	}

	private BooleanClauseOccur _occur;
	private boolean _prohibited;
	private Query _query;
	private boolean _required;

}