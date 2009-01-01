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

package com.liferay.portal.messaging;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationEventListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.springframework.beans.factory.DisposableBean;

/**
 * <a href="PortalMessageBus.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 *
 */
public class PortalMessageBus implements DisposableBean, MessageBus {

	public void addDestination(Destination destination) {
		_messageBus.addDestination(destination);
	}

	public void addDestinationEventListener(DestinationEventListener listener) {
		_messageBus.addDestinationEventListener(listener);
	}

	public void destroy() throws Exception {
		shutdown(true);
	}

	public boolean hasDestination(String destination) {
		return _messageBus.hasDestination(destination);
	}

	public boolean hasMessageListener(String destination) {
		return _messageBus.hasMessageListener(destination);
	}

	public void registerMessageListener(
		String destination, MessageListener listener) {

		_messageBus.registerMessageListener(destination, listener);
	}

	public void removeDestination(String destination) {
		_messageBus.removeDestination(destination);
	}

	public void removeDestinationEventListener(
		DestinationEventListener listener) {
		_messageBus.removeDestinationEventListener(listener);
	}

	public void sendMessage(String destination, Message message) {
		_messageBus.sendMessage(destination, message);
	}

	public void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	public boolean unregisterMessageListener(
		String destination, MessageListener listener) {

		return _messageBus.unregisterMessageListener(destination, listener);
	}

	public void shutdown() {
		_messageBus.shutdown();
	}

	public void shutdown(boolean force) {
		_messageBus.shutdown(force);
	}

	private MessageBus _messageBus;

}