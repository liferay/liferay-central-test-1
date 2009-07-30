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
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.monitoring.statistics.portlet;

import com.liferay.portal.monitoring.MonitoringException;
import com.liferay.portal.monitoring.RequestStatus;
import com.liferay.portal.monitoring.statistics.DataSampleProcessor;
import com.liferay.portal.monitoring.statistics.RequestStatistics;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="PortletStatistics.java.html"><b><i>View Source</i></b></a>
 *
 * @author Karthik Sudarshan
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PortletStatistics
	implements DataSampleProcessor<PortletRequestDataSample> {

	public PortletStatistics(
		String portletId, String portletName, String displayName) {

		_portletId = portletId;
		_portletName = portletName;
		_displayName = displayName;
		_actionRequestStatistics = new RequestStatistics(portletId);
		_eventRequestStatistics = new RequestStatistics(portletId);
		_renderRequestStatistics = new RequestStatistics(portletId);
		_resourceRequestStatistics = new RequestStatistics(portletId);

		_requestStatistics.put(
			PortletRequestType.ACTION, _actionRequestStatistics);
		_requestStatistics.put(
			PortletRequestType.EVENT, _eventRequestStatistics);
		_requestStatistics.put(
			PortletRequestType.RENDER, _renderRequestStatistics);
		_requestStatistics.put(
			PortletRequestType.RESOURCE, _resourceRequestStatistics);
	}

	public RequestStatistics getActionRequestStatistics() {
		return _actionRequestStatistics;
	}

	public String getDisplayName() {
		return _displayName;
	}

	public RequestStatistics getEventRequestStatistics() {
		return _eventRequestStatistics;
	}

	public String getPortletId() {
		return _portletId;
	}

	public String getPortletName() {
		return _portletName;
	}

	public RequestStatistics getRenderRequestStatistics() {
		return _renderRequestStatistics;
	}

	public RequestStatistics getResourceRequestStatistics() {
		return _resourceRequestStatistics;
	}

	public void processDataSample(
			PortletRequestDataSample portletRequestDataSample)
		throws MonitoringException {

		if (!portletRequestDataSample.getPortletId().equals(_portletId)) {
			return;
		}

		PortletRequestType portletRequestType =
			portletRequestDataSample.getRequestType();

		RequestStatistics requestStatistics =_requestStatistics.get(
			portletRequestType);

		if (requestStatistics == null) {
			throw new MonitoringException(
				"No statistics found for " + portletRequestDataSample);
		}

		RequestStatus requestStatus =
			portletRequestDataSample.getRequestStatus();

		if (requestStatus.equals(RequestStatus.ERROR)) {
			requestStatistics.incrementError();
		}
		else if (requestStatus.equals(RequestStatus.SUCCESS)) {
			requestStatistics.incrementSuccessDuration(
				portletRequestDataSample.getDuration());
		}
		else if (requestStatus.equals(RequestStatus.TIMEOUT)) {
			requestStatistics.incrementTimeout();
		}
	}

	public void reset() {
		_actionRequestStatistics.reset();
		_eventRequestStatistics.reset();
		_renderRequestStatistics.reset();
		_resourceRequestStatistics.reset();
	}

	private RequestStatistics _actionRequestStatistics;
	private String _displayName;
	private RequestStatistics _eventRequestStatistics;
	private String _portletId;
	private String _portletName;
	private RequestStatistics _renderRequestStatistics;
	private Map<PortletRequestType, RequestStatistics> _requestStatistics =
		new HashMap<PortletRequestType, RequestStatistics>();
	private RequestStatistics _resourceRequestStatistics;

}