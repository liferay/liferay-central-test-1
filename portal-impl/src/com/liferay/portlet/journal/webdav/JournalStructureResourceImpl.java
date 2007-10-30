/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.webdav;

import com.liferay.portal.webdav.BaseResourceImpl;
import com.liferay.portal.webdav.WebDAVException;
import com.liferay.portlet.journal.model.JournalStructure;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * <a href="JournalStructureResourceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Alexander Chow
 *
 */
public class JournalStructureResourceImpl extends BaseResourceImpl {

	public JournalStructureResourceImpl(
		JournalStructure structure, String href) {

		super(
			href, structure.getStructureId(), false, structure.getCreateDate(),
			structure.getModifiedDate(), structure.getXsd().length());

		setModel(structure);

		_structure = structure;
	}

	public InputStream getContentAsStream() throws WebDAVException {
		try {
			return new ByteArrayInputStream(
				_structure.getXsd().getBytes("UTF-8"));
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	private JournalStructure _structure;

}