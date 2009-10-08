/*
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

package com.liferay.portal.kernel.audit.messaging;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

/**
 * <a href="AuditServiceMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Mika Koivisto
 */
public class AuditServiceMessageListener implements MessageListener {

	public void receive(Message message) {

		if (_log.isDebugEnabled()) {
			_log.debug("Received message: " + message);
		}

		try {
			_auditService.receiveAuditMessage(
				new AuditMessage((String)message.getPayload()));
		}
		catch (Exception e) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to process audit message: " + message, e);
			}
		}
	}

    public void setAuditService(AuditService audit) {
        _auditService = audit;
    }

    private static Log _log = LogFactoryUtil.getLog(
		AuditServiceMessageListener.class);

    private AuditService _auditService;
}
