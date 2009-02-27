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

package com.liferay.portalweb.portlet.imagegallery;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="ImageGalleryTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ImageGalleryTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddPageTest.class);
		testSuite.addTestSuite(AddPortletTest.class);
		testSuite.addTestSuite(AddFolderTest.class);
		testSuite.addTestSuite(AddSubfolderTest.class);
		testSuite.addTestSuite(AddImageTest.class);
		testSuite.addTestSuite(ViewSlideshowTest.class);
		testSuite.addTestSuite(BrowseImageTabsTest.class);
		testSuite.addTestSuite(SearchPortletTest.class);
		testSuite.addTestSuite(AddSecondTestFoldersTest.class);
		testSuite.addTestSuite(MoveImageTest.class);
		testSuite.addTestSuite(AddNullTitleFolderTest.class);
		testSuite.addTestSuite(AddInvalidTitleFolderTest.class);
		testSuite.addTestSuite(AddDuplicateTitleFolderTest.class);
		testSuite.addTestSuite(AddImageNameFolderTest.class);
		testSuite.addTestSuite(AddNullImageTest.class);
		testSuite.addTestSuite(AddDuplicateImageNameTest.class);
		testSuite.addTestSuite(AddIncorrectNameImageTest.class);
		testSuite.addTestSuite(AddInvalidImageTest.class);
		testSuite.addTestSuite(DeleteFoldersTest.class);
		testSuite.addTestSuite(ImportLARTest.class);
		testSuite.addTestSuite(AssertImportLARTest.class);
		testSuite.addTestSuite(ImageEdittingTest.class);
		testSuite.addTestSuite(TearDownTest.class);

		return testSuite;
	}

}