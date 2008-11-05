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

package com.liferay.portlet.messageboards.parentfinder;

import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.util.MBUtil;

import javax.mail.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="HeadersBasedParentMessageFinder.java.html">
 * <b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 *
 */
public class HeadersBasedParentMessageFinder implements ParentMessageFinder {

	public MBMessage getParentMessage(
		long categoryId, Message mailMessage) throws Exception {

		MBMessage parentMessage = null;

		long parentMessageId = MBUtil.getParentMessageId(mailMessage);

		if (_log.isDebugEnabled()) {
			_log.debug("Parent message id " + parentMessageId);
		}

		try {
			if (parentMessageId > 0) {
				parentMessage = MBMessageLocalServiceUtil.getMessage(
					parentMessageId);
			}
		}
		catch (NoSuchMessageException nsme) {
		}

		return parentMessage;
	}

	private static Log _log =
		LogFactory.getLog(HeadersBasedParentMessageFinder.class);

}
