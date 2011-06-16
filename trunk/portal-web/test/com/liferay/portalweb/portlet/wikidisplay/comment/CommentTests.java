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

package com.liferay.portalweb.portlet.wikidisplay.comment;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.wikidisplay.comment.addwdfrontpagecomment.AddWDFrontPageCommentTests;
import com.liferay.portalweb.portlet.wikidisplay.comment.addwdfrontpagecommentbodynull.AddWDFrontPageCommentBodyNullTests;
import com.liferay.portalweb.portlet.wikidisplay.comment.addwdfrontpagecommentmultiple.AddWDFrontPageCommentMultipleTests;
import com.liferay.portalweb.portlet.wikidisplay.comment.deletewdfrontpagecomment.DeleteWDFrontPageCommentTests;
import com.liferay.portalweb.portlet.wikidisplay.comment.editwdfrontpagecommentbody.EditWDFrontPageCommentBodyTests;
import com.liferay.portalweb.portlet.wikidisplay.comment.ratewdfrontpagecomment.RateWDFrontPageCommentTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class CommentTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWDFrontPageCommentTests.suite());
		testSuite.addTest(AddWDFrontPageCommentBodyNullTests.suite());
		testSuite.addTest(AddWDFrontPageCommentMultipleTests.suite());
		testSuite.addTest(DeleteWDFrontPageCommentTests.suite());
		testSuite.addTest(EditWDFrontPageCommentBodyTests.suite());
		testSuite.addTest(RateWDFrontPageCommentTests.suite());

		return testSuite;
	}

}