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

package com.liferay.portal.webdav;

import java.io.InputStream;

import java.util.Date;

/**
 * <a href="BaseResourceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BaseResourceImpl implements Resource {

	public BaseResourceImpl(
		String href, String displayName, boolean collection) {

		this(href, displayName, collection, null, null);
	}

	public BaseResourceImpl(String href, String displayName, boolean collection,
							Date createDate, Date modifiedDate) {

		this(href, displayName, collection, createDate, modifiedDate, 0);
	}

	public BaseResourceImpl(String href, String displayName, boolean collection,
							Date createDate, Date modifiedDate, int size) {

		_href = href;
		_displayName = displayName;
		_collection = collection;

		if (createDate == null) {
			_createDate = new Date();
		}
		else {
			_createDate = createDate;
		}

		if (modifiedDate == null) {
			_modifiedDate = new Date();
		}
		else {
			_modifiedDate = _createDate;
		}

		_size = size;
	}

 	public String getHREF() {
		return _href;
	}

	public String getDisplayName() {
		return _displayName;
	}

	public boolean isCollection() {
		return _collection;
	}

 	public Date getCreateDate() {
		return _createDate;
	}

 	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public int getSize() {
		return _size;
	}

	public Object getModel() {
		return _model;
	}

	public void setModel(Object model) {
		_model = model;
	}

	public InputStream getContentAsStream() throws WebDAVException {
		return null;
	}

	private String _href;
	private String _displayName;
	private boolean _collection;
	private Date _createDate;
	private Date _modifiedDate;
	private int _size;
	private Object _model;

}