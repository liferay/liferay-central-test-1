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

package com.liferay.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * <a href="InetAddressTask.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class InetAddressTask extends Task {

	public void execute() throws BuildException {
		try {
			InetAddress localHost = InetAddress.getLocalHost();

			if (_hostAddressProperty != null) {
				getProject().setUserProperty(
					_hostAddressProperty, localHost.getHostAddress());
			}

			if (_hostNameProperty != null) {
				getProject().setUserProperty(
					_hostNameProperty, localHost.getHostName());
			}
		}
		catch (UnknownHostException uhe) {
			throw new BuildException(uhe);
		}
	}

	public void setHostAddressProperty(String hostAddressProperty) {
		_hostAddressProperty = hostAddressProperty;
	}

	public void setHostNameProperty(String hostNameProperty) {
		_hostNameProperty = hostNameProperty;
	}

	private String _hostAddressProperty;
	private String _hostNameProperty;

}