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

package com.liferay.portlet.messageboards.messaging;

/**
 * <a href="MailingListRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 */
public class MailingListRequest {

	public long getCategoryId() {

		return _categoryId;
	}

	public long getCompanyId() {

		return _companyId;
	}

	public String getInPassword() {

		return _inPassword;
	}

	public String getInProtocol() {

		return _inProtocol;
	}

	public String getInServerName() {

		return _inServerName;
	}

	public int getInServerPort() {

		return _inServerPort;
	}

	public String getInUserName() {

		return _inUserName;
	}

	public boolean getInUseSSL() {

		return _inUseSSL;
	}

	public long getUserId() {

		return _userId;
	}

	public void setCategoryId(long id) {

		_categoryId = id;
	}

	public void setCompanyId(long id) {

		_companyId = id;
	}

	public void setInPassword(String inPassword) {

		_inPassword = inPassword;
	}

	public void setInProtocol(String inProtocol) {

		_inProtocol = inProtocol;
	}

	public void setInServerName(String inServerName) {

		_inServerName = inServerName;
	}

	public void setInServerPort(int inServerPort) {

		_inServerPort = inServerPort;
	}

	public void setInUserName(String inUserName) {

		_inUserName = inUserName;
	}

	public void setInUseSSL(boolean inUseSSL) {

		_inUseSSL = inUseSSL;
	}

	public void setUserId(long id) {

		_userId = id;
	}

	private long _categoryId;
	private long _companyId;
	private String _inPassword;
	private String _inProtocol;
	private String _inServerName;
	private int _inServerPort;
	private String _inUserName;
	private boolean _inUseSSL;
	private long _userId;

}