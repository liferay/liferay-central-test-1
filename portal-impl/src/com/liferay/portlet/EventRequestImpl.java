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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.PortletRequest;

/**
 * <a href="EventRequestImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EventRequestImpl
	extends PortletRequestImpl implements EventRequest {

	public Event getEvent() {
		return _event;
	}

	public void setEvent(Event event) {
		_event = event;
	}

	public String getLifecycle() {
		return PortletRequest.EVENT_PHASE;
	}

	protected EventRequestImpl() {
		if (_log.isDebugEnabled()) {
			_log.debug("Creating new instance " + hashCode());
		}
	}

	protected void recycle() {
		if (_log.isDebugEnabled()) {
			_log.debug("Recycling instance " + hashCode());
		}

		super.recycle();

		_event = null;
	}

	private static Log _log = LogFactoryUtil.getLog(EventRequestImpl.class);

	private Event _event;

}