/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.documentlibrary.documenttype;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.documentlibrary.documenttype.adddldocumenttype.AddDLDocumentTypeTests;
import com.liferay.portalweb.portlet.documentlibrary.documenttype.adddldocumenttypemultiple.AddDLDocumentTypeMultipleTests;
import com.liferay.portalweb.portlet.documentlibrary.documenttype.deletedldocumenttype.DeleteDLDocumentTypeTests;
import com.liferay.portalweb.portlet.documentlibrary.documenttype.editdldocumenttype.EditDLDocumentTypeTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentTypeTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDLDocumentTypeTests.suite());
		testSuite.addTest(DeleteDLDocumentTypeTests.suite());
		testSuite.addTest(EditDLDocumentTypeTests.suite());
		testSuite.addTest(AddDLDocumentTypeMultipleTests.suite());

		return testSuite;
	}

}