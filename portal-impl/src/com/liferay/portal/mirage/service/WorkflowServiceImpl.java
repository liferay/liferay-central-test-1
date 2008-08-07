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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.mirage.service;

import com.liferay.portal.mirage.aop.WorkflowInvoker;
import com.liferay.portal.mirage.util.MirageLoggerUtil;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.Content;
import com.sun.portal.cms.mirage.service.custom.WorkflowService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="WorkflowServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prakash Reddy
 * @author Karthik Sudarshan
 * @author Joshna Reddy
 *
 */
public class WorkflowServiceImpl implements WorkflowService {

	public void submitContentToWorkflow(String contentUUID) {
		throw new UnsupportedOperationException();
	}

	public void updateWorkflowCommentsAccepted(String contentUUID) {
		throw new UnsupportedOperationException();
	}

	public void updateWorkflowComplete(Content content) throws CMSException {
		MirageLoggerUtil.enter(_log, _CLASS_NAME, "updateWorkflowComplete");

		process(content);

		MirageLoggerUtil.exit(_log, _CLASS_NAME, "updateWorkflowComplete");
	}

	public void updateWorkflowComplete(String contentUUID) {
		throw new UnsupportedOperationException();
	}

	public void updateWorkflowContentRejected(Content content)
		throws CMSException {

		MirageLoggerUtil.enter(
			_log, _CLASS_NAME, "updateWorkflowContentRejected");

		process(content);

		MirageLoggerUtil.exit(
			_log, _CLASS_NAME, "updateWorkflowContentRejected");
	}

	public void updateWorkflowContentRejected(
		String contentUUID, String comments) {

		throw new UnsupportedOperationException();
	}

	public void updateWorkflowStatus(String contentUUID, String status) {
		throw new UnsupportedOperationException();
	}

	protected void process(Content content) throws CMSException {
		WorkflowInvoker workflowInvoker = (WorkflowInvoker)content;

		workflowInvoker.invoke();
	}

	private static final String _CLASS_NAME =
		WorkflowServiceImpl.class.getName();

	private static final Log _log =
		LogFactory.getLog(WorkflowServiceImpl.class);
}